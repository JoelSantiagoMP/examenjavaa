/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnostore.pos.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 *
 * @author Jorge Gómez
 */
public class Validador {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    private static final Pattern IDENTIFICACION_PATTERN = Pattern.compile(
            "^[0-9]{6,15}$"
    );

    /**
     * Valida que un texto no sea nulo ni vacío
     */
    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    /**
     * Valida el formato de un correo electrónico
     */
    public static boolean esCorreoValido(String correo) {
        if (correo == null || correo.isEmpty()) return false;
        return EMAIL_PATTERN.matcher(correo).matches();
    }

    /**
     * Valida que la identificación contenga solo números y tenga entre 6 y 15 dígitos
     */
    public static boolean esIdentificacionValida(String identificacion) {
        if (identificacion == null || identificacion.isEmpty()) return false;
        return IDENTIFICACION_PATTERN.matcher(identificacion).matches();
    }

    /**
     * Valida que un precio sea positivo
     */
    public static boolean esPrecioValido(BigDecimal precio) {
        return precio != null && precio.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Valida que un stock sea un entero no negativo
     */
    public static boolean esStockValido(int stock) {
        return stock >= 0;
    }
}
