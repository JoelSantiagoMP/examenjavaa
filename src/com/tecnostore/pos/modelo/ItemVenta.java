package com.tecnostore.pos.modelo;

import java.math.BigDecimal;

/**
 * Representa un ítem dentro de una venta.
 */
public class ItemVenta {
    private Long id;
    private Celular celular;
    private int cantidad;
    private BigDecimal subtotal;

    /**
     * Constructor por defecto.
     */
    public ItemVenta() {}

    /**
     * Constructor con todos los atributos requeridos. Calcula el subtotal automáticamente.
     *
     * @param id Identificador único del ítem
     * @param celular Celular asociado al ítem
     * @param cantidad Cantidad de unidades
     */
    public ItemVenta(Long id, Celular celular, int cantidad) {
        this.id = id;
        setCelular(celular);
        setCantidad(cantidad);
        calculateSubtotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Celular getCelular() {
        return celular;
    }

    /**
     * Establece el celular. Valida que no sea nulo y recalcula el subtotal.
     */
    public final void setCelular(Celular celular) {
        if (celular == null) {
            throw new IllegalArgumentException("El celular no puede ser nulo");
        }
        this.celular = celular;
        calculateSubtotal();
    }

    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad. Valida que sea mayor a cero y recalcula el subtotal.
     */
    public final void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        this.cantidad = cantidad;
        calculateSubtotal();
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    /**
     * Permite establecer manualmente el subtotal, aunque generalmente se calcula.
     */
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Calcula el subtotal multiplicando el precio del celular por la cantidad.
     */
    private void calculateSubtotal() {
        if (this.celular != null && this.celular.getPrecio() != null && this.cantidad > 0) {
            this.subtotal = this.celular.getPrecio().multiply(BigDecimal.valueOf(this.cantidad));
        } else {
            this.subtotal = BigDecimal.ZERO;
        }
    }
}