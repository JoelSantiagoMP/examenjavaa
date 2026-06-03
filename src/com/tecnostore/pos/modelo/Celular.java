package com.tecnostore.pos.modelo;

import java.math.BigDecimal;

/**
 * Representa un celular disponible para la venta.
 */
public class Celular {
    private Long id;
    private String brand;
    private String model;
    private BigDecimal price;
    private int stock;
    private SistemaOperativo sistemaOperativo;
    private CategoriaGama categoriaGama;

    /**
     * Constructor por defecto.
     */
    public Celular() {}

    /**
     * Constructor con todos los atributos.
     *
     * @param id Identificador único del celular
     * @param brand Marca del celular
     * @param model Modelo del celular
     * @param price Precio del celular
     * @param stock Cantidad en inventario
     * @param sistemaOperativo Sistema operativo del celular
     * @param categoriaGama Gama del celular
     */
    public Celular(Long id, String brand, String model, BigDecimal price, int stock,
                   SistemaOperativo sistemaOperativo, CategoriaGama categoriaGama) {
        this.id = id;
        setBrand(brand);
        setModel(model);
        setPrice(price);
        setStock(stock);
        setSistemaOperativo(sistemaOperativo);
        setCategoriaGama(categoriaGama);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    /**
     * Establece la marca. Valida que no sea nula ni vacía.
     */
    public final void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacía");
        }
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    /**
     * Establece el modelo. Valida que no sea nulo ni vacío.
     */
    public final void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede estar vacío");
        }
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Establece el precio. Valida que no sea nulo y sea un valor positivo.
     */
    public final void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio debe ser un valor positivo");
        }
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    /**
     * Establece el stock. Valida que no sea negativo.
     */
    public final void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.stock = stock;
    }

    public SistemaOperativo getSistemaOperativo() {
        return sistemaOperativo;
    }

    /**
     * Establece el sistema operativo. Valida que no sea nulo.
     */
    public final void setSistemaOperativo(SistemaOperativo sistemaOperativo) {
        if (sistemaOperativo == null) {
            throw new IllegalArgumentException("El sistema operativo no puede ser nulo");
        }
        this.sistemaOperativo = sistemaOperativo;
    }

    public CategoriaGama getCategoriaGama() {
        return categoriaGama;
    }

    /**
     * Establece la gama. Valida que no sea nula.
     */
    public final void setCategoriaGama(CategoriaGama categoriaGama) {
        if (categoriaGama == null) {
            throw new IllegalArgumentException("La categoría de gama no puede ser nula");
        }
        this.categoriaGama = categoriaGama;
    }
}
