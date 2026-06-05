package com.tecnostore.pos.persistencia;

import com.tecnostore.pos.modelo.ItemVenta;
import com.tecnostore.pos.modelo.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * * @author Jorge Gómez
 */
public class VentaDAO {
    
    public void registrarVenta(Venta venta) throws SQLException {
        String sqlVenta = "INSERT INTO ventas (id_cliente, fecha, total) VALUES (?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_ventas (id_venta, id_celular, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        String sqlStock = "UPDATE celulares SET stock = stock - ? WHERE id = ?";

        
        try (Connection con = ConexionDB.getInstancia().obtenerConexion()) {
            
            
            con.setAutoCommit(false);

            try (PreparedStatement psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);
                 PreparedStatement psStock = con.prepareStatement(sqlStock)) {
                
                psVenta.setLong(1, venta.getCliente().getId());
                
                psVenta.setDate(2, java.sql.Date.valueOf(venta.getFecha().toLocalDate()));
                psVenta.setBigDecimal(3, venta.getTotal());
                
                psVenta.executeUpdate();
                
                try (ResultSet rs = psVenta.getGeneratedKeys()) {
                    if (rs.next()) {
                        venta.setId(rs.getLong(1));
                    } else {
                        throw new SQLException("No se pudo obtener el ID autogenerado de la venta.");
                    }
                }
                
                for (ItemVenta detalle : venta.getDetalles()) {
                    
                    psDetalle.setLong(1, venta.getId());
                    psDetalle.setLong(2, detalle.getCelular().getId());
                    psDetalle.setInt(3, detalle.getCantidad());
                    psDetalle.setBigDecimal(4, detalle.getSubtotal());
                    psDetalle.executeUpdate();
                    
                    psStock.setInt(1, detalle.getCantidad());
                    psStock.setLong(2, detalle.getCelular().getId());
                    psStock.executeUpdate();
                }
                
                con.commit();

            } catch (SQLException e) {
                
                con.rollback();
                throw e;
            } finally {
                
                con.setAutoCommit(true);
            }
        }
    }
}