package com.tecnostore.pos.modelo;

import java.math.BigDecimal;

/**
 * Representa un ítem dentro de una venta.
 */
public class ItemVenta {
    private Long id;
    private Celular celular;
    private int quantity;
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
     * @param quantity Cantidad de unidades
     */
    public ItemVenta(Long id, Celular celular, int quantity) {
        this.id = id;
        setCelular(celular);
        setQuantity(quantity);
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

    public int getQuantity() {
        return quantity;
    }

    /**
     * Establece la cantidad. Valida que sea mayor a cero y recalcula el subtotal.
     */
    public final void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        this.quantity = quantity;
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
        if (this.celular != null && this.celular.getPrecio() != null && this.quantity > 0) {
            this.subtotal = this.celular.getPrecio().multiply(BigDecimal.valueOf(this.quantity));
        } else {
            this.subtotal = BigDecimal.ZERO;
        }
    }
}
