package com.tecnostore.pos.persistencia;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gestor de conexión a la base de datos TecnoStore
 * @author Jorge Gómez
 */
public class ConexionDB {

    // Patrón Singleton
    private static volatile ConexionDB instancia;

    // Configuración de conexión (cargada desde config.properties)
    private static final Properties props = new Properties();

    static {
        try (InputStream input = ConexionDB.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Error: No se encontró config.properties");
            }
            props.load(input);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(
                "Error al cargar propiedades: " + e.getMessage());
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

    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(
            props.getProperty("db.url"),
            props.getProperty("db.user"),
            props.getProperty("db.password")
        );
    }
}
