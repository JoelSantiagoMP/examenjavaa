package com.tecnostore.pos.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una venta realizada en la tienda.
 */
public class Venta {
    private Long id;
    private Cliente cliente;
    private List<ItemVenta> items;
    private LocalDateTime date;
    private BigDecimal totalAmount;

    /**
     * Constructor por defecto.
     * Inicializa la lista de ítems, la fecha actual y el monto total en cero.
     */
    public Venta() {
        this.items = new ArrayList<>();
        this.date = LocalDateTime.now();
        this.totalAmount = BigDecimal.ZERO;
    }

    /**
     * Constructor con atributos. Calcula el monto total automáticamente.
     *
     * @param id Identificador único de la venta
     * @param cliente Cliente asociado a la venta
     * @param items Lista de ítems de la venta
     * @param date Fecha y hora de la venta
     */
    public Venta(Long id, Cliente cliente, List<ItemVenta> items, LocalDateTime date) {
        this.id = id;
        this.cliente = cliente;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        this.date = date != null ? date : LocalDateTime.now();
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
     * Establece el cliente. Valida que no sea nulo.
     */
    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        this.cliente = cliente;
    }

    /**
     * Obtiene una copia defensiva de los ítems de venta.
     */
    public List<ItemVenta> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Establece los ítems de venta y recalcula el monto total.
     */
    public void setItems(List<ItemVenta> items) {
        if (items == null) {
            throw new IllegalArgumentException("La lista de ítems no puede ser nula");
        }
        this.items = new ArrayList<>(items);
        calculateTotalAmount();
    }

    /**
     * Añade un ítem a la venta y recalcula el monto total.
     */
    public void addItem(ItemVenta item) {
        if (item == null) {
            throw new IllegalArgumentException("El ítem no puede ser nulo");
        }
        this.items.add(item);
        calculateTotalAmount();
    }

    /**
     * Elimina un ítem de la venta y recalcula el monto total.
     */
    public void removeItem(ItemVenta item) {
        if (item != null) {
            this.items.remove(item);
            calculateTotalAmount();
        }
    }

    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Establece la fecha de la venta. Valida que no sea nula.
     */
    public void setDate(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        this.date = date;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    /**
     * Permite establecer manualmente el monto total.
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Calcula el monto total sumando los subtotales de los ítems de la venta.
     */
    private void calculateTotalAmount() {
        this.totalAmount = this.items.stream()
            .map(ItemVenta::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
