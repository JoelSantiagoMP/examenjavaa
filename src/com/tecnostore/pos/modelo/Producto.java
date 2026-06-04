/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnostore.pos.modelo;

import java.math.BigDecimal;

/**
 *
 * @author camper
 */
public abstract class Producto {
    private Long id;
    private String marca;
    private String modelo;
    private BigDecimal precio;
    private int stock;

    /** Constructor por defecto. */
    protected Producto() {}

    /**
     * Constructor con atributos base del producto.
     *
     * @param id     Identificador único
     * @param marca  Marca del producto
     * @param modelo Modelo del producto
     * @param precio Precio de venta
     * @param stock  Unidades en inventario
     */
    protected Producto(Long id, String marca, String modelo,
                       BigDecimal precio, int stock) {
        this.id = id;
        setMarca(marca);
        setModelo(modelo);
        setPrecio(precio);
        setStock(stock);
    }

    // ---- Getters y setters con validación ----

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getMarca() { return marca; }

    public final void setMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacía");
        }
        this.marca = marca;
    }

    public String getModelo() { return modelo; }

    public final void setModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede estar vacío");
        }
        this.modelo = modelo;
    }

    public BigDecimal getPrecio() { return precio; }

    public final void setPrecio(BigDecimal precio) {
        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio debe ser un valor positivo");
        }
        this.precio = precio;
    }

    public int getStock() { return stock; }

    public final void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.stock = stock;
    }

    /**
     * Representación textual del producto.
     * Las subclases deben implementar este método con sus atributos propios.
     */
    @Override
    public abstract String toString();
}
