package com.tecnostore.pos.persistencia;

import com.tecnostore.pos.modelo.Credito;
import com.tecnostore.pos.modelo.Cliente;
import com.tecnostore.pos.modelo.TipoCredito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CreditoDAO implements ICreditoDAO {

    @Override
    public void insertar(Credito credito) throws SQLException {
        String sql = "INSERT INTO creditos (id_venta, id_cliente, saldo, tipo, descripcion, fecha) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.getInstancia().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, credito.getIdVenta());
            ps.setLong(2, credito.getCliente().getId());
            ps.setBigDecimal(3, credito.getSaldo());
            ps.setString(4, credito.getTipo().name());
            ps.setString(5, credito.getDescripcion());
            ps.setDate(6, java.sql.Date.valueOf(credito.getFecha().toLocalDate()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    credito.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public void actualizarSaldo(Credito credito) throws SQLException {
        String sql = "UPDATE creditos SET saldo = ? WHERE id = ?";
        try (Connection con = ConexionDB.getInstancia().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBigDecimal(1, credito.getSaldo());
            ps.setLong(2, credito.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Credito> listarPendientes() throws SQLException {
        List<Credito> lista = new ArrayList<>();
        String sql = "SELECT cr.id, cr.id_venta, cr.saldo, cr.tipo, cr.descripcion, cr.fecha, "
                   + "cl.id AS cliente_id, cl.nombre, cl.identificacion, cl.correo, cl.telefono "
                   + "FROM creditos cr "
                   + "JOIN clientes cl ON cr.id_cliente = cl.id "
                   + "WHERE cr.saldo > 0";
        try (Connection con = ConexionDB.getInstancia().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCredito(rs));
            }
        }
        return lista;
    }

    @Override
    public Credito buscarPorId(Long id) throws SQLException {
        String sql = "SELECT cr.id, cr.id_venta, cr.saldo, cr.tipo, cr.descripcion, cr.fecha, "
                   + "cl.id AS cliente_id, cl.nombre, cl.identificacion, cl.correo, cl.telefono "
                   + "FROM creditos cr "
                   + "JOIN clientes cl ON cr.id_cliente = cl.id "
                   + "WHERE cr.id = ?";
        try (Connection con = ConexionDB.getInstancia().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCredito(rs);
                }
            }
        }
        return null;
    }

    private Credito mapearCredito(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("cliente_id"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setIdentificacion(rs.getString("identificacion"));
        cliente.setCorreo(rs.getString("correo"));
        cliente.setTelefono(rs.getString("telefono"));

        Credito credito = new Credito();
        credito.setId(rs.getLong("id"));
        credito.setIdVenta(rs.getLong("id_venta"));
        credito.setCliente(cliente);
        credito.setSaldo(rs.getBigDecimal("saldo"));
        credito.setTipo(TipoCredito.valueOf(rs.getString("tipo").toUpperCase()));
        credito.setDescripcion(rs.getString("descripcion"));
        credito.setFecha(rs.getDate("fecha").toLocalDate().atStartOfDay());
        return credito;
    }
}