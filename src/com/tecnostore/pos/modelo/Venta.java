package com.tecnostore.pos.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una venta realizada en la tienda
 */
public class Venta {
    private Long id;
    private Cliente cliente;
    private List<ItemVenta> detalles;
    private LocalDateTime fecha;
    private BigDecimal total;

    public Venta() {
        this.detalles = new ArrayList<>();
        this.fecha = LocalDateTime.now();
        this.total = BigDecimal.ZERO;
    }

    public Venta(Long id, Cliente cliente, List<ItemVenta> detalles, LocalDateTime fecha) {
        this.id = id;
        this.cliente = cliente;
        this.detalles = detalles != null ? new ArrayList<>(detalles) : new ArrayList<>();
        this.fecha = fecha != null ? fecha : LocalDateTime.now();
        calculateTotalAmount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente y valida que no sea nulo
     */
    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        this.cliente = cliente;
    }

    /**
     * Obtiene una copia de los detalles de venta
     */
    public List<ItemVenta> getDetalles() {
        return new ArrayList<>(detalles);
    }

    /**
     * Establece los detalles de venta y recalcula el monto total
     */
    public void setDetalles(List<ItemVenta> detalles) {
        if (detalles == null) {
            throw new IllegalArgumentException("La lista de detalles no puede ser nula");
        }
        this.detalles = new ArrayList<>(detalles);
        calculateTotalAmount();
    }

    /**
     * Añade un detalle a la venta y recalcula el monto total
     */
    public void addDetalle(ItemVenta detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException("El detalle no puede ser nulo");
        }
        this.detalles.add(detalle);
        calculateTotalAmount();
    }

    /**
     * Elimina un detalle de la venta y recalcula el monto total
     */
    public void removeDetalle(ItemVenta detalle) {
        if (detalle != null) {
            this.detalles.remove(detalle);
            calculateTotalAmount();
        }
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la venta y valida que no sea nula
     */
    public void setFecha(LocalDateTime fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }
    
    /**
     * Permite establecer manualmente el monto total
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * Calcula el monto total sumando los subtotales de los detalles de la venta
     */
    private void calculateTotalAmount() {
        this.total = this.detalles.stream()
            .map(ItemVenta::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}