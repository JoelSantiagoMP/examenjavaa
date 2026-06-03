package com.tecnostore.pos.modelo;

import java.util.regex.Pattern;

/**
 * Representa un cliente de la tienda.
 */
public class Cliente {
    private Long id;
    private String name;
    private String identification;
    private String email;
    private String phone;

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
     * @param name Nombre del cliente
     * @param identification Identificación (DNI/Cédula)
     * @param email Correo electrónico
     * @param phone Número de teléfono
     */
    public Cliente(Long id, String name, String identification, String email, String phone) {
        this.id = id;
        setName(name);
        setIdentification(identification);
        setEmail(email);
        setPhone(phone);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del cliente. Valida que no sea nulo ni vacío.
     */
    public final void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    /**
     * Establece la identificación del cliente. Valida que no sea nula ni vacía.
     */
    public final void setIdentification(String identification) {
        if (identification == null || identification.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación no puede estar vacía");
        }
        this.identification = identification;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico. Valida el formato usando una expresión regular.
     */
    public final void setEmail(String email) {
        if (email != null && !email.isEmpty() && !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("El formato del correo electrónico es inválido");
        }
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public final void setPhone(String phone) {
        this.phone = phone;
    }
}
