/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnostore.pos.util;

import com.tecnostore.pos.modelo.ItemVenta;
import com.tecnostore.pos.modelo.Venta;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Jorge Gómez
 */
public class ArchivoUtils {
 
    private static final String NOMBRE_ARCHIVO = "reporte_ventas.txt";
    private static final DateTimeFormatter FORMATO_FECHA = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    /**
     * Genera el archivo reporte_ventas.txt
     */
    public static void generarReporte(List<Venta> ventas) throws IOException {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))) {
            
            writer.write("========================================");
            writer.newLine();
            writer.write("     REPORTE DE VENTAS - TECNOSTORE     ");
            writer.newLine();
            writer.write("========================================");
            writer.newLine();
            writer.newLine();

            if (ventas.isEmpty()) {
                writer.write("No hay ventas registradas.");
                writer.newLine();
                return;
            }

            for (Venta venta : ventas) {
                writer.write("Venta ID : " + venta.getId());
                writer.newLine();
                writer.write("Fecha    : " + venta.getFecha().format(FORMATO_FECHA));
                writer.newLine();
                writer.write("Cliente  : " + venta.getCliente().getNombre());
                writer.newLine();
                writer.write("----------------------------------------");
                writer.newLine();

                for (ItemVenta item : venta.getDetalles()) {
                    writer.write(String.format("  %-20s x%d  $%s",
                            item.getCelular().getMarca() + " " + item.getCelular().getModelo(),
                            item.getCantidad(),
                            item.getSubtotal()));
                    writer.newLine();
                }

                writer.write("----------------------------------------");
                writer.newLine();
                writer.write("TOTAL: $" + venta.getTotal());
                writer.newLine();
                writer.newLine();
            }

            writer.write("========================================");
            writer.newLine();
            writer.write("Total de ventas registradas: " + ventas.size());
            writer.newLine();
            writer.write("========================================");
        }
    }
    
}
