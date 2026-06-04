package com.tecnostore.pos.persistencia;

import com.tecnostore.pos.modelo.ItemVenta;
import com.tecnostore.pos.modelo.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase de Persistencia (DAO) para la gestión transaccional de Ventas.
 * Asegura la atomicidad al registrar ventas, detalles y actualizar inventario.
 * * @author Jorge Gómez
 */
public class VentaDAO {

    /**
     * Registra una venta completa en la base de datos de manera transaccional.
     * Inserta la cabecera, los detalles y actualiza el stock de los celulares.
     * * @param venta Objeto Venta que contiene el cliente, fecha, total y lista de detalles.
     * @throws SQLException Si ocurre un error en la base de datos o si falla el stock (CHECK constraint).
     */
    public void registrarVenta(Venta venta) throws SQLException {
        String sqlVenta = "INSERT INTO ventas (id_cliente, fecha, total) VALUES (?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_ventas (id_venta, id_celular, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        String sqlStock = "UPDATE celulares SET stock = stock - ? WHERE id = ?";

        // Abrimos la conexión principal
        try (Connection con = ConexionDB.getInstancia().obtenerConexion()) {
            
            // DESACTIVAMOS EL AUTOCOMMIT para iniciar una transacción manual (ACID)
            con.setAutoCommit(false);

            try (PreparedStatement psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);
                 PreparedStatement psStock = con.prepareStatement(sqlStock)) {

                // 1. Insertar la cabecera de la Venta
                psVenta.setLong(1, venta.getCliente().getId());
                // Convertimos LocalDateTime a java.sql.Date para la columna DATE de la BD
                psVenta.setDate(2, java.sql.Date.valueOf(venta.getFecha().toLocalDate()));
                psVenta.setBigDecimal(3, venta.getTotal());
                
                psVenta.executeUpdate();

                // Recuperar el ID autogenerado de la venta
                try (ResultSet rs = psVenta.getGeneratedKeys()) {
                    if (rs.next()) {
                        venta.setId(rs.getLong(1));
                    } else {
                        throw new SQLException("No se pudo obtener el ID autogenerado de la venta.");
                    }
                }

                // 2. Procesar cada detalle del ítem de venta
                for (ItemVenta detalle : venta.getDetalles()) {
                    
                    // A. Insertar el registro en 'detalle_ventas'
                    psDetalle.setLong(1, venta.getId()); // ID recién generado
                    psDetalle.setLong(2, detalle.getCelular().getId());
                    psDetalle.setInt(3, detalle.getCactidad()); // Atributo unificado en paso 0 (getCantidad)
                    psDetalle.setBigDecimal(4, detalle.getSubtotal());
                    psDetalle.executeUpdate();

                    // B. Actualizar (descontar) el Stock en 'celulares'
                    psStock.setInt(1, detalle.getCantidad());
                    psStock.setLong(2, detalle.getCelular().getId());
                    psStock.executeUpdate();
                }

                // Si todo se ejecutó correctamente, confirmamos los cambios en la BD
                con.commit();

            } catch (SQLException e) {
                // Si algo falla en cualquier punto, hacemos ROLLBACK absoluto
                con.rollback();
                throw e; // Relanzamos la excepción para que sea controlada en la capa superior
            } finally {
                // Restauramos el comportamiento por defecto de la conexión
                con.setAutoCommit(true);
            }
        }
    }
}