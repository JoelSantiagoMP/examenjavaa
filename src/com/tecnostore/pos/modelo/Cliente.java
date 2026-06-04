package com.tecnostore.pos.modelo;

import java.util.regex.Pattern;

/**
 * Representa un cliente de la tienda.
 */
public class Cliente {
    private Long id;
    private String nombre;
    private String identificacion;
    private String correo;
    private String telefono;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    /**
     * Constructor por defecto.
     */
    public Cliente() {}

    /**
     * Constructor con todos los atributos.
     *
     * @param id Identificador único del cliente
     * @param nombre Nombre del cliente
     * @param identificacion Identificación (DNI/Cédula)
     * @param correo Correo electrónico
     * @param telefono Número de teléfono
     */
    public Cliente(Long id, String nombre, String identificacion, String correo, String telefono) {
        this.id = id;
        setNombre(nombre);
        setIdentificacion(identificacion);
        setCorreo(correo);
        setTelefono(telefono);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente. Valida que no sea nulo ni vacío.
     */
    public final void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * Establece la identificación del cliente. Valida que no sea nula ni vacía.
     */
    public final void setIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación no puede estar vacía");
        }
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico. Valida el formato usando una expresión regular.
     */
    public final void setCorreo(String correo) {
        if (correo != null && !correo.isEmpty() && !EMAIL_PATTERN.matcher(correo).matches()) {
            throw new IllegalArgumentException("El formato del correo electrónico es inválido");
        }
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public final void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
