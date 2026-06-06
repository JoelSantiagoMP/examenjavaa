/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnostore.pos.util;

import com.tecnostore.pos.modelo.Celular;
import com.tecnostore.pos.modelo.ItemVenta;
import com.tecnostore.pos.modelo.Venta;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Jorge Gómez
 */
public class ReporteUtils {

    public static List<Celular> celularesConStockBajo(List<Celular> celulares) {
        return celulares.stream()
                .filter(c -> c.getStock() < 5)
                .collect(Collectors.toList());
    }

    public static List<Celular> top3MasVendidos(List<Venta> ventas) {
        return ventas.stream()
                .flatMap(v -> v.getDetalles().stream())
                .collect(Collectors.groupingBy(
                        ItemVenta::getCelular,
                        Collectors.summingInt(ItemVenta::getCantidad)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<Celular, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static Map<String, BigDecimal> ventasTotalesPorMes(List<Venta> ventas) {
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getFecha().getYear() + "-" +
                             String.format("%02d", v.getFecha().getMonthValue()),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Venta::getTotal,
                                BigDecimal::add
                        )
                ));
    }
}
