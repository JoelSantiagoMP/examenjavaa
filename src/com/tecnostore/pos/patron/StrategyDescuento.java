/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnostore.pos.patron;

/**
 *
 * @author Jorge Gómez
 */
import java.math.BigDecimal;

public class StrategyDescuento {
    private EstrategiaDescuento estrategia;

    public StrategyDescuento(EstrategiaDescuento estrategia) {
        this.estrategia = estrategia;
    }

    public void setEstrategia(EstrategiaDescuento estrategia) {
        this.estrategia = estrategia;
    }

    public BigDecimal aplicarDescuento(BigDecimal precio) {
        return estrategia.aplicar(precio);
    }

    public String getDescripcion() {
        return estrategia.descripcion();
    }
}