package com.tecnostore.pos.modelo;

import java.math.BigDecimal;

/**
 * Representa un teléfono celular disponible para la venta
 */
public class Celular extends Producto {

    private SistemaOperativo sistemaOperativo;
    private CategoriaGama categoriaGama;

    public Celular() {
        super();
    }

    public Celular(Long id, String marca, String modelo, BigDecimal precio,
                   int stock, SistemaOperativo sistemaOperativo,
                   CategoriaGama categoriaGama) {
        super(id, marca, modelo, precio, stock);
        setSistemaOperativo(sistemaOperativo);
        setCategoriaGama(categoriaGama);
    }

    public SistemaOperativo getSistemaOperativo() { return sistemaOperativo; }

    public final void setSistemaOperativo(SistemaOperativo sistemaOperativo) {
        if (sistemaOperativo == null) {
            throw new IllegalArgumentException("El sistema operativo no puede ser nulo");
        }
        this.sistemaOperativo = sistemaOperativo;
    }

    public CategoriaGama getCategoriaGama() { return categoriaGama; }

    public final void setCategoriaGama(CategoriaGama categoriaGama) {
        if (categoriaGama == null) {
            throw new IllegalArgumentException("La categoría de gama no puede ser nula");
        }
        this.categoriaGama = categoriaGama;
    }

    @Override
    public String toString() {
        return String.format("Celular{id=%d, marca='%s', modelo='%s', " +
                "precio=%s, stock=%d, so=%s, gama=%s}",
                getId(), getMarca(), getModelo(),
                getPrecio(), getStock(),
                sistemaOperativo, categoriaGama);
    }
}
