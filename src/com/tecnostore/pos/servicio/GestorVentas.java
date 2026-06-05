/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnostore.pos.servicio;

import com.tecnostore.pos.modelo.Celular;
import com.tecnostore.pos.modelo.Cliente;
import com.tecnostore.pos.persistencia.CelularDAO;
import com.tecnostore.pos.persistencia.ClienteDAO;
import com.tecnostore.pos.persistencia.VentaDAO;
import com.tecnostore.pos.modelo.Venta;
import java.sql.SQLException;

/**
 *
 * @author Jorge Gómez
 */
public class GestorVentas {
    
    private ClienteDAO clienteDAO;
    private CelularDAO celularDAO;
    private VentaDAO ventaDAO;
    
    public GestorVentas() {
        this.clienteDAO = new ClienteDAO();
        this.celularDAO = new CelularDAO();
        this.ventaDAO = new VentaDAO();
    }
    
    public void registrarVenta(Venta venta) throws SQLException {
        
    }
    
    private void verificarCliente(Long idCliente) throws SQLException {
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if(cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }
    }
    
    private void verificarStock(Long idCelular, int cantidad) throws SQLException {
        Celular celular = celularDAO.buscarPorId(idCelular);
        if(celular == null) {
            throw new IllegalArgumentException("Celular no encontrado.");
            
        } if (celular.getStock() < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente.");
        } 
    }
}
