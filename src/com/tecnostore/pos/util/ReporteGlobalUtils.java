package com.tecnostore.pos.util;

import com.tecnostore.pos.modelo.Celular;
import com.tecnostore.pos.modelo.Credito;
import com.tecnostore.pos.modelo.ItemVenta;
import com.tecnostore.pos.modelo.TipoCredito;
import com.tecnostore.pos.modelo.Venta;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteGlobalUtils {

    public static final int STOCK_MINIMO = 5;

    /**
     * Suma total de todos los montos facturados
     */
    public static BigDecimal totalFacturado(List<Venta> ventas) {
        return ventas.stream()
                .map(Venta::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Cantidad total de unidades vendidas
     */
    public static int totalUnidadesVendidas(List<Venta> ventas) {
        return ventas.stream()
                .flatMap(v -> v.getDetalles().stream())
                .mapToInt(ItemVenta::getCantidad)
                .sum();
    }

    /**
     * Unidades vendidas agrupadas por marca y modelo
     */
    public static Map<String, Integer> vendidosPorModelo(List<Venta> ventas) {
        return ventas.stream()
                .flatMap(v -> v.getDetalles().stream())
                .collect(Collectors.groupingBy(
                        item -> item.getCelular().getMarca() + " " + item.getCelular().getModelo(),
                        Collectors.summingInt(ItemVenta::getCantidad)
                ));
    }

    /**
     * Créditos pendientes filtrados por tipo
     */
    public static List<Credito> filtrarPorTipo(List<Credito> creditos, TipoCredito tipo) {
        return creditos.stream()
                .filter(cr -> cr.getTipo() == tipo)
                .collect(Collectors.toList());
    }

    /**
     * Productos con stock por debajo del mínimo
     */
    public static List<Celular> productosStockBajo(List<Celular> celulares) {
        return celulares.stream()
                .filter(c -> c.getStock() < STOCK_MINIMO)
                .collect(Collectors.toList());
    }
}