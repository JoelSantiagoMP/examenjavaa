/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnostore.pos.patron;

/**
 *
 * @author Jorge Gómez
 */
import com.tecnostore.pos.modelo.CategoriaGama;
import com.tecnostore.pos.modelo.Celular;
import com.tecnostore.pos.modelo.SistemaOperativo;
import java.math.BigDecimal;

public class FactoryCelular {

    public static Celular crear(String marca, String modelo, BigDecimal precio,
                                int stock, SistemaOperativo so, CategoriaGama gama) {
        switch (gama) {
            case ALTA:
                if (precio.compareTo(new BigDecimal("2000000")) < 0) {
                    throw new IllegalArgumentException(
                        "Un celular de gama alta debe tener precio mayor a $2.000.000");
                }
                break;
            case MEDIA:
                if (precio.compareTo(new BigDecimal("800000")) < 0 ||
                    precio.compareTo(new BigDecimal("2000000")) >= 0) {
                    throw new IllegalArgumentException(
                        "Un celular de gama media debe tener precio entre $800.000 y $2.000.000");
                }
                break;
            case BAJA:
                if (precio.compareTo(new BigDecimal("800000")) >= 0) {
                    throw new IllegalArgumentException(
                        "Un celular de gama baja debe tener precio menor a $800.000");
                }
                break;
        }
        return new Celular(null, marca, modelo, precio, stock, so, gama);
    }
}
