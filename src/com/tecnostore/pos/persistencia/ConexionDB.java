/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnostore.pos.persistencia;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 *
 * @author Jorge Gómez
 */
public class ConexionDB {
    private static volatile ConexionDB instancia;
    private Connection conexion; // Ya no estará "sin leer" al usarla abajo
    private static final Properties props = new Properties();

    static {
        // Asegúrate de que el archivo se llame exactamente así en tu proyecto
        try (InputStream input = ConexionDB.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Error: No se encontró config.properties");
            }
            props.load(input);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Error al cargar propiedades: " + e.getMessage());
        }
    }

    private ConexionDB() {}

    public static ConexionDB getInstancia() {
        if (instancia == null) {
            synchronized (ConexionDB.class) {
                if (instancia == null) {
                    instancia = new ConexionDB();
                }
            }
        }
        return instancia;
    }

    // Aquí es donde el IDE dejará de marcar error, porque ahora sí estamos usando 'conexion'
    public Connection obtenerConexion() throws SQLException {
        if (this.conexion == null || this.conexion.isClosed()) {
            synchronized (ConexionDB.class) {
                if (this.conexion == null || this.conexion.isClosed()) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        this.conexion = DriverManager.getConnection(
                            props.getProperty("db.url"),
                            props.getProperty("db.user"),
                            props.getProperty("db.password")
                        );
                    } catch (ClassNotFoundException e) {
                        throw new SQLException("Driver MySQL no encontrado", e);
                    }
                }
            }
        }
        return this.conexion;
    }
}
