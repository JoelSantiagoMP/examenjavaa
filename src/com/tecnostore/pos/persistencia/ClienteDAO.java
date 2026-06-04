package com.tecnostore.pos.persistencia;

import com.tecnostore.pos.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de Persistencia (DAO) para la gestión de la entidad Cliente en MySQL.
 * Sigue las pautas de control de recursos automáticos exigidas en el proyecto.
 * * @author Jorge Gómez
 */
public class ClienteDAO {

    /**
     * Registra un nuevo cliente y recupera el ID autogenerado por la base de datos.
     * * @param cliente Objeto con los datos del cliente a insertar.
     * @throws SQLException Si ocurre un error de inserción o violación de restricciones UNIQUE.
     */
    public void insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nombre, identificacion, correo, telefono) VALUES (?, ?, ?, ?)";
        
        try (Connection con = ConexionDB.getInstancia().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getIdentificacion());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());
            
            ps.executeUpdate();
            
            // Recuperamos la llave primaria generada automáticamente por MySQL
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getLong(1));
                }
            }
        }
    }

    /**
     * Actualiza la información de un cliente existente en base a su ID.
     * * @param cliente Objeto con los datos actualizados.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public void actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nombre = ?, identificacion = ?, correo = ?, telefono = ? WHERE id = ?";
        
        try (Connection con = ConexionDB.getInstancia().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getIdentificacion());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());
            ps.setLong(5, cliente.getId());
            
            ps.executeUpdate();
        }
    }

    /**
     * Elimina un cliente del sistema usando su identificador único numérico.
     * * @param id Identificador del cliente a eliminar.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        
        try (Connection con = ConexionDB.getInstancia().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Busca un cliente en la base de datos mediante su ID interno.
     * * @param id ID del cliente.
     * @return El objeto Cliente mapeado, o null si no se encuentra.
     * @throws SQLException Si ocurre un error en la consulta.
     */
    public Cliente buscarPorId(Long id) throws SQLException {
        String sql = "SELECT id, nombre, identificacion, correo, telefono FROM clientes WHERE id = ?";
        
        try (Connection con = ConexionDB.getInstancia().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }
        return null;
    }

    /**
     * Busca un cliente por su número de documento de identificación (Cédula/DNI).
     * Requerimiento clave para validar unicidad en la capa de servicios.
     * * @param identificacion Cadena de texto con el documento.
     * @return El objeto Cliente correspondiente, o null si está disponible.
     * @throws SQLException Si ocurre un error en la consulta.
     */
    public Cliente buscarPorIdentificacion(String identificacion) throws SQLException {
        String sql = "SELECT id, nombre, identificacion, correo, telefono FROM clientes WHERE identificacion = ?";
        
        try (Connection con = ConexionDB.getInstancia().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, identificacion);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }
        return null;
    }

    /**
     * Obtiene la lista completa de clientes registrados en TecnoStore.
     * * @return Lista de clientes.
     * @throws SQLException Si ocurre un error en la consulta.
     */
    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, identificacion, correo, telefono FROM clientes";
        
        try (Connection con = ConexionDB.getInstancia().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        }
        return lista;
    }

    /**
     * Método helper privado para evitar duplicidad de código al extraer 
     * información de los ResultSets de las consultas SQL.
     */
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setIdentificacion(rs.getString("identificacion"));
        cliente.setCorreo(rs.getString("correo"));
        cliente.setTelefono(rs.getString("telefono"));
        return cliente;
    }
}