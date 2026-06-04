package com.tecnostore.pos.persistencia;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gestor de conexión a la base de datos TecnoStore.
 * Implementa el patrón de diseño Singleton: existe una única instancia
 * de esta clase en toda la aplicación.
 *
 * @author Jorge Gómez
 */
public class ConexionDB {

    // ---- Patrón Singleton ----
    private static volatile ConexionDB instancia;

    // ---- Configuración de conexión (cargada desde config.properties) ----
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

    /**
     * Constructor PRIVADO: impide que otras clases hagan 'new ConexionDB()'.
     * Es el corazón del patrón Singleton.
     */
    private ConexionDB() {}

    /**
     * Devuelve la única instancia de ConexionDB (patrón Singleton).
     * Usa Double-Checked Locking para ser seguro en entornos multihilo.
     *
     * @return instancia única de ConexionDB
     */
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

    /**
     * Abre y devuelve una conexión NUEVA a la base de datos,
     * leyendo los datos desde config.properties.
     * Quien la use debe cerrarla (con try-with-resources).
     *
     * @return conexión activa a la base de datos
     * @throws SQLException si no se puede establecer la conexión
     */
    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(
            props.getProperty("db.url"),
            props.getProperty("db.user"),
            props.getProperty("db.password")
        );
    }
}
