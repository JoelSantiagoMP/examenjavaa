package com.tecnostore.pos.servicio;

import com.tecnostore.pos.modelo.Celular;
import com.tecnostore.pos.modelo.Credito;
import com.tecnostore.pos.modelo.Cliente;
import com.tecnostore.pos.modelo.TipoCredito;
import com.tecnostore.pos.modelo.Venta;
import com.tecnostore.pos.persistencia.ICreditoDAO;
import com.tecnostore.pos.persistencia.CreditoDAO;
import com.tecnostore.pos.util.ReporteGlobalUtils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ReporteService {

    // ── Singleton ──────────────────────────────────────────────────────────
    private static volatile ReporteService instancia;

    private ReporteService() {}

    public static ReporteService getInstancia() {
        if (instancia == null) {
            synchronized (ReporteService.class) {
                if (instancia == null) {
                    instancia = new ReporteService();
                }
            }
        }
        return instancia;
    }

    // ── Dependencias ───────────────────────────────────────────────────────
    private final GestorVentas gestorVentas         = new GestorVentas();
    private final GestorCelulares gestorCelulares   = new GestorCelulares();
    private final GestorCreditos gestorCreditos     = new GestorCreditos();

    private static final String NOMBRE_ARCHIVO = "reporte_global.txt";
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // ── Procesamiento de pago ──────────────────────────────────────────────
    public void procesarPago(Venta venta, BigDecimal montoPagado) throws SQLException {
        BigDecimal total = venta.getTotal();
        int comparacion = montoPagado.compareTo(total);

        if (comparacion == 0) {
            // Pago exacto, no se genera credito
            return;
        }

        BigDecimal diferencia = total.subtract(montoPagado).abs();
        TipoCredito tipo = comparacion < 0 ? TipoCredito.DEUDA : TipoCredito.CREDITO_A_FAVOR;
        String descripcion = tipo == TipoCredito.DEUDA
                ? "Saldo pendiente por pago incompleto"
                : "Credito a favor por pago en exceso";

        Credito credito = new Credito(
                null,
                venta.getId(),
                venta.getCliente(),
                diferencia,
                tipo,
                descripcion
        );
        gestorCreditos.registrar(credito);
    }

    // ── Generacion del reporte ─────────────────────────────────────────────
    public void generarReporteGlobal() throws SQLException, IOException {

        // 1. Obtener datos
        List<Venta>   ventas    = gestorVentas.listarTodas();
        List<Celular> celulares = gestorCelulares.listarTodos();
        List<Credito> creditos  = gestorCreditos.listarPendientes();

        // 2. Calcular con Streams
        BigDecimal           totalFacturado    = ReporteGlobalUtils.totalFacturado(ventas);
        int                  totalUnidades     = ReporteGlobalUtils.totalUnidadesVendidas(ventas);
        Map<String, Integer> vendidosPorModelo = ReporteGlobalUtils.vendidosPorModelo(ventas);
        List<Credito>        deudas            = ReporteGlobalUtils.filtrarPorTipo(creditos, TipoCredito.DEUDA);
        List<Credito>        creditosAFavor    = ReporteGlobalUtils.filtrarPorTipo(creditos, TipoCredito.CREDITO_A_FAVOR);
        List<Celular>        stockBajo         = ReporteGlobalUtils.productosStockBajo(celulares);

        // 3. Escribir archivo
        escribirReporte(ventas, totalFacturado, totalUnidades,
                vendidosPorModelo, deudas, creditosAFavor, celulares, stockBajo);

        System.out.println("Reporte generado: " + NOMBRE_ARCHIVO);
    }

    // ── Escritura ──────────────────────────────────────────────────────────
    private void escribirReporte(
            List<Venta>          ventas,
            BigDecimal           totalFacturado,
            int                  totalUnidades,
            Map<String, Integer> vendidosPorModelo,
            List<Credito>        deudas,
            List<Credito>        creditosAFavor,
            List<Celular>        celulares,
            List<Celular>        stockBajo) throws IOException {

        try (BufferedWriter w = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))) {

            linea(w, "========================================");
            linea(w, "    REPORTE GLOBAL - TECNOSTORE         ");
            linea(w, "Generado: " + LocalDateTime.now().format(FMT));
            linea(w, "========================================");
            w.newLine();

            // Seccion 1 — Resumen de ventas
            linea(w, "--- 1. RESUMEN DE VENTAS ---");
            if (ventas.isEmpty()) {
                linea(w, "  No hay ventas registradas.");
            } else {
                linea(w, "  Total facturado   : $" + totalFacturado);
                linea(w, "  Unidades vendidas : " + totalUnidades);
            }
            w.newLine();

            // Seccion 2 — Ventas por modelo
            linea(w, "--- 2. CELULARES VENDIDOS POR MODELO ---");
            if (vendidosPorModelo.isEmpty()) {
                linea(w, "  Sin datos de ventas por modelo.");
            } else {
                vendidosPorModelo.entrySet().stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                        .forEach(e -> {
                            try {
                                linea(w, String.format("  %-35s %d uds.", e.getKey(), e.getValue()));
                            } catch (IOException ex) {
                                throw new UncheckedIOException(ex);
                            }
                        });
            }
            w.newLine();

            // Seccion 3 — Deudas de clientes
            linea(w, "--- 3. CLIENTES CON DEUDAS PENDIENTES ---");
            if (deudas.isEmpty()) {
                linea(w, "  No hay deudas pendientes.");
            } else {
                for (Credito cr : deudas) {
                    linea(w, String.format("  %-25s Venta #%-5d $%s",
                            cr.getCliente().getNombre(),
                            cr.getIdVenta(),
                            cr.getSaldo()));
                }
            }
            w.newLine();

            // Seccion 4 — Creditos a favor
            linea(w, "--- 4. CLIENTES CON CREDITO A FAVOR ---");
            if (creditosAFavor.isEmpty()) {
                linea(w, "  No hay creditos a favor.");
            } else {
                for (Credito cr : creditosAFavor) {
                    linea(w, String.format("  %-25s Venta #%-5d $%s",
                            cr.getCliente().getNombre(),
                            cr.getIdVenta(),
                            cr.getSaldo()));
                }
            }
            w.newLine();

            // Seccion 5 — Stock actual
            linea(w, "--- 5. STOCK ACTUAL ---");
            for (Celular c : celulares) {
                String alerta = c.getStock() < ReporteGlobalUtils.STOCK_MINIMO ? "  *** STOCK BAJO ***" : "";
                linea(w, String.format("  %-35s stock: %d%s",
                        c.getMarca() + " " + c.getModelo(),
                        c.getStock(),
                        alerta));
            }
            w.newLine();

            // Resumen final
            linea(w, "========================================");
            linea(w, "Productos con stock bajo : " + stockBajo.size());
            linea(w, "Deudas pendientes        : " + deudas.size());
            linea(w, "Creditos a favor         : " + creditosAFavor.size());
            linea(w, "========================================");
        }
    }

    private void linea(BufferedWriter w, String texto) throws IOException {
        w.write(texto);
        w.newLine();
    }
}