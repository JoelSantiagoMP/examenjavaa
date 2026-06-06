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

public class SinDescuento implements EstrategiaDescuento {
    @Override
    public BigDecimal aplicar(BigDecimal precioOriginal) {
        return precioOriginal;
    }

    @Override
    public String descripcion() {
        return "Sin descuento";
    }
}
