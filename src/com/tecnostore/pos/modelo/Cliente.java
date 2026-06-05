package com.tecnostore.pos.modelo;

import com.tecnostore.pos.util.Validador;

/**
 * Representa un cliente de la tienda
 */
public class Cliente {
    private Long id;
    private String nombre;
    private String identificacion;
    private String correo;
    private String telefono;

    public Cliente() {}

    public Cliente(Long id, String nombre, String identificacion, String correo, String telefono) {
        this.id = id;
        setNombre(nombre);
        setIdentificacion(identificacion);
        setCorreo(correo);
        setTelefono(telefono);
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public final void setNombre(String nombre) {
        if (!Validador.esTextoValido(nombre)) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public String getIdentificacion() { return identificacion; }

    public final void setIdentificacion(String identificacion) {
        if (!Validador.esTextoValido(identificacion)) {
            throw new IllegalArgumentException("La identificación no puede estar vacía");
        }
        this.identificacion = identificacion;
    }

    public String getCorreo() { return correo; }

    public final void setCorreo(String correo) {
        if (correo != null && !correo.isEmpty() && !Validador.esCorreoValido(correo)) {
            throw new IllegalArgumentException("El formato del correo electrónico es inválido");
        }
        this.correo = correo;
    }

    public String getTelefono() { return telefono; }

    public final void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
