package com.tecnostore.pos;

import com.tecnostore.pos.modelo.*;
import com.tecnostore.pos.patron.DescuentoGamaBaja;
import com.tecnostore.pos.patron.DescuentoGamaMedia;
import com.tecnostore.pos.patron.FactoryCelular;
import com.tecnostore.pos.patron.SinDescuento;
import com.tecnostore.pos.patron.StrategyDescuento;
import com.tecnostore.pos.servicio.GestorCelulares;
import com.tecnostore.pos.servicio.GestorClientes;
import com.tecnostore.pos.servicio.GestorVentas;
import com.tecnostore.pos.util.ArchivoUtils;
import com.tecnostore.pos.util.ReporteUtils;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final GestorCelulares gestorCelulares = new GestorCelulares();
    private static final GestorClientes gestorClientes = new GestorClientes();
    private static final GestorVentas gestorVentas = new GestorVentas();

    public static void main(String[] args) {
        System.out.println("Bienvenido a TecnoStore POS");
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Opcion no valida, ingrese un numero.");
            } catch (SQLException e) {
                System.out.println("Error de base de datos: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
        System.out.println("Hasta luego.");
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Gestion de Celulares");
        System.out.println("2. Gestion de Clientes");
        System.out.println("3. Registrar Venta");
        System.out.println("4. Reportes");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private static void procesarOpcion(int opcion) throws Exception {
        switch (opcion) {
            case 1: menuCelulares(); break;
            case 2: menuClientes(); break;
            case 3: registrarVenta(); break;
            case 4: menuReportes(); break;
            case 0: break;
            default: System.out.println("Opcion no valida.");
        }
    }

    // CELULARES

    private static void menuCelulares() throws Exception {
        System.out.println("\n--- Gestion de Celulares ---");
        System.out.println("1. Registrar celular");
        System.out.println("2. Listar celulares");
        System.out.println("3. Actualizar celular");
        System.out.println("4. Eliminar celular");
        System.out.print("Seleccione una opcion: ");
        int op = Integer.parseInt(scanner.nextLine().trim());
        switch (op) {
            case 1: registrarCelular(); break;
            case 2: listarCelulares(); break;
            case 3: actualizarCelular(); break;
            case 4: eliminarCelular(); break;
            default: System.out.println("Opcion no valida.");
        }
    }

    private static void registrarCelular() throws Exception {
        System.out.print("Marca: ");
        String marca = scanner.nextLine().trim().toUpperCase();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine().trim().toUpperCase();
        System.out.print("Precio: ");
        BigDecimal precio = new BigDecimal(scanner.nextLine().trim());
        System.out.print("Stock: ");
        int stock = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Sistema operativo (ANDROID/IOS/HARMONYOS): ");
        SistemaOperativo so = SistemaOperativo.valueOf(scanner.nextLine().trim().toUpperCase());

        Celular celular = FactoryCelular.crear(marca, modelo, precio, stock, so);
        gestorCelulares.registrar(celular);
        System.out.println("Celular registrado con ID: " + celular.getId()
                + " | Gama asignada: " + celular.getCategoriaGama());
    }

    private static void listarCelulares() throws SQLException {
        List<Celular> lista = gestorCelulares.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay celulares registrados.");
            return;
        }
        System.out.println("\n--- Catalogo de Celulares ---");
        for (Celular c : lista) {
            System.out.println(c);
        }
    }

    private static void actualizarCelular() throws Exception {
        System.out.print("ID del celular a actualizar: ");
        Long id = Long.parseLong(scanner.nextLine().trim());
        Celular celular = gestorCelulares.buscarPorId(id);
        if (celular == null) {
            System.out.println("Celular no encontrado.");
            return;
        }
        System.out.print("Nuevo precio (actual: " + celular.getPrecio() + "): ");
        celular.setPrecio(new BigDecimal(scanner.nextLine().trim()));
        System.out.print("Nuevo stock (actual: " + celular.getStock() + "): ");
        celular.setStock(Integer.parseInt(scanner.nextLine().trim()));
        gestorCelulares.actualizar(celular);
        System.out.println("Celular actualizado correctamente.");
    }

    private static void eliminarCelular() throws Exception {
        System.out.print("ID del celular a eliminar: ");
        Long id = Long.parseLong(scanner.nextLine().trim());
        gestorCelulares.eliminar(id);
        System.out.println("Celular eliminado correctamente.");
    }

    // CLIENTES

    private static void menuClientes() throws Exception {
        System.out.println("\n--- Gestion de Clientes ---");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Listar clientes");
        System.out.print("Seleccione una opcion: ");
        int op = Integer.parseInt(scanner.nextLine().trim());
        switch (op) {
            case 1: registrarCliente(); break;
            case 2: listarClientes(); break;
            default: System.out.println("Opcion no valida.");
        }
    }

    private static void registrarCliente() throws Exception {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Identificacion: ");
        String identificacion = scanner.nextLine().trim();
        System.out.print("Correo: ");
        String correo = scanner.nextLine().trim();
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine().trim();

        Cliente cliente = new Cliente(null, nombre, identificacion, correo, telefono);
        gestorClientes.registrar(cliente);
        System.out.println("Cliente registrado con ID: " + cliente.getId());
    }

    private static void listarClientes() throws SQLException {
        List<Cliente> lista = gestorClientes.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println("\n--- Clientes registrados ---");
        for (Cliente c : lista) {
            System.out.println("ID: " + c.getId() + " | Nombre: " + c.getNombre()
                    + " | Identificacion: " + c.getIdentificacion()
                    + " | Correo: " + c.getCorreo());
        }
    }

    // VENTAS

    private static void registrarVenta() throws Exception {
        System.out.print("ID del cliente: ");
        Long idCliente = Long.parseLong(scanner.nextLine().trim());
        Cliente cliente = gestorClientes.buscarPorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        Venta venta = new Venta();
        venta.setCliente(cliente);

        boolean agregarMas = true;
        while (agregarMas) {
            System.out.print("ID del celular: ");
            Long idCelular = Long.parseLong(scanner.nextLine().trim());
            Celular celular = gestorCelulares.buscarPorId(idCelular);
            if (celular == null) {
                System.out.println("Celular no encontrado.");
                continue;
            }

            StrategyDescuento strategy;
            switch (celular.getCategoriaGama()) {
                case MEDIA: strategy = new StrategyDescuento(new DescuentoGamaMedia()); break;
                case BAJA:  strategy = new StrategyDescuento(new DescuentoGamaBaja());  break;
                default:    strategy = new StrategyDescuento(new SinDescuento());       break;
            }
            BigDecimal precioFinal = strategy.aplicarDescuento(celular.getPrecio());
            System.out.println("Descuento aplicado: " + strategy.getDescripcion());
            System.out.println("Precio final: $" + precioFinal);
            celular.setPrecio(precioFinal);

            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine().trim());
            venta.addDetalle(new ItemVenta(null, celular, cantidad));

            System.out.print("Agregar otro celular? (s/n): ");
            agregarMas = scanner.nextLine().trim().equalsIgnoreCase("s");
        }

        gestorVentas.registrarVenta(venta);
        System.out.println("Venta registrada. Total: $" + venta.getTotal());
    }

    // REPORTES

    private static void menuReportes() throws Exception {
        System.out.println("\n--- Reportes ---");
        System.out.println("1. Celulares con stock bajo");
        System.out.println("2. Top 3 mas vendidos");
        System.out.println("3. Ventas totales por mes");
        System.out.println("4. Generar reporte_ventas.txt");
        System.out.print("Seleccione una opcion: ");
        int op = Integer.parseInt(scanner.nextLine().trim());

        List<Celular> celulares = gestorCelulares.listarTodos();
        List<Venta> ventas = gestorVentas.listarTodas();

        switch (op) {
            case 1:
                List<Celular> stockBajo = ReporteUtils.celularesConStockBajo(celulares);
                if (stockBajo.isEmpty()) {
                    System.out.println("No hay celulares con stock bajo.");
                } else {
                    for (Celular c : stockBajo) {
                        System.out.println(c.getMarca() + " " + c.getModelo()
                                + " - Stock: " + c.getStock());
                    }
                }
                break;
            case 2:
                List<Celular> top3 = ReporteUtils.top3MasVendidos(ventas);
                if (top3.isEmpty()) {
                    System.out.println("No hay ventas registradas.");
                } else {
                    for (Celular c : top3) {
                        System.out.println(c.getMarca() + " " + c.getModelo());
                    }
                }
                break;
            case 3:
                Map<String, BigDecimal> porMes = ReporteUtils.ventasTotalesPorMes(ventas);
                if (porMes.isEmpty()) {
                    System.out.println("No hay ventas registradas.");
                } else {
                    for (Map.Entry<String, BigDecimal> entry : porMes.entrySet()) {
                        System.out.println(entry.getKey() + ": $" + entry.getValue());
                    }
                }
                break;
            case 4:
                ArchivoUtils.generarReporte(ventas);
                System.out.println("Archivo reporte_ventas.txt generado.");
                break;
            default:
                System.out.println("Opcion no valida.");
        }
    }
}