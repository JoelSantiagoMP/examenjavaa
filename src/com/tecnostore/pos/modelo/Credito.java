package com.tecnostore.pos.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa un crédito o deuda pendiente de un cliente
 * originado a partir de una venta
 */
public class Credito {
    private Long id;
    private Long idVenta;
    private Cliente cliente;
    private BigDecimal saldo;
    private TipoCredito tipo;
    private String descripcion;
    private LocalDateTime fecha;

    public Credito() {
        this.fecha = LocalDateTime.now();
    }

    public Credito(Long id, Long idVenta, Cliente cliente, BigDecimal saldo,
                   TipoCredito tipo, String descripcion) {
        this.id = id;
        this.idVenta = idVenta;
        setCliente(cliente);
        setSaldo(saldo);
        setTipo(tipo);
        this.descripcion = descripcion;
        this.fecha = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdVenta() { return idVenta; }
    public void setIdVenta(Long idVenta) { this.idVenta = idVenta; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        this.cliente = cliente;
    }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) {
        if (saldo == null || saldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El saldo debe ser un valor positivo");
        }
        this.saldo = saldo;
    }

    public TipoCredito getTipo() { return tipo; }
    public void setTipo(TipoCredito tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de crédito no puede ser nulo");
        }
        this.tipo = tipo;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        this.fecha = fecha;
    }

    public boolean estaSaldado() {
        return saldo.compareTo(BigDecimal.ZERO) == 0;
    }
}