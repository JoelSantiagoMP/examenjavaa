/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tecnostore.pos.persistencia;

/**
 *
 * @author camper
 */
import com.tecnostore.pos.modelo.Venta;
import java.sql.SQLException;
import java.util.List;

public interface IVentaDAO {
    void registrarVenta(Venta venta) throws SQLException;
    List<Venta> listarTodas() throws SQLException;
}
