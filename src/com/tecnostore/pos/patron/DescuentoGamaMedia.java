/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnostore.pos.patron;

/**
 *
 * @author camper
 */
import java.math.BigDecimal;

public class DescuentoGamaMedia implements EstrategiaDescuento {
    private static final BigDecimal PORCENTAJE = new BigDecimal("0.10");

    @Override
    public BigDecimal aplicar(BigDecimal precioOriginal) {
        return precioOriginal.subtract(precioOriginal.multiply(PORCENTAJE));
    }

    @Override
    public String descripcion() {
        return "Descuento gama media 10%";
    }
}
