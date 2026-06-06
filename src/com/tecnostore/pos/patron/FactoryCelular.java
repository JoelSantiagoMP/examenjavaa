package com.tecnostore.pos.patron;

import com.tecnostore.pos.modelo.CategoriaGama;
import com.tecnostore.pos.modelo.Celular;
import com.tecnostore.pos.modelo.SistemaOperativo;
import java.math.BigDecimal;

public class FactoryCelular {

    private static final BigDecimal PRECIO_GAMA_ALTA = new BigDecimal("3000001");
    private static final BigDecimal PRECIO_GAMA_MEDIA = new BigDecimal("1000001");

    public static Celular crear(String marca, String modelo, BigDecimal precio,
                                int stock, SistemaOperativo so) {
        CategoriaGama gama;
        if (precio.compareTo(PRECIO_GAMA_ALTA) >= 0) {
            gama = CategoriaGama.ALTA;
        } else if (precio.compareTo(PRECIO_GAMA_MEDIA) >= 0) {
            gama = CategoriaGama.MEDIA;
        } else {
            gama = CategoriaGama.BAJA;
        }
        return new Celular(null, marca, modelo, precio, stock, so, gama);
    }
}