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
            throw new IllegalArgumentException("La categoria de gama no puede ser nula");
        }
        this.categoriaGama = categoriaGama;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Celular)) return false;
        Celular celular = (Celular) o;
        return getId() != null && getId().equals(celular.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s %s | Precio: $%s | Stock: %d | SO: %s | Gama: %s",
                getId(), getMarca(), getModelo(),
                getPrecio(), getStock(),
                sistemaOperativo, categoriaGama);
    }
}