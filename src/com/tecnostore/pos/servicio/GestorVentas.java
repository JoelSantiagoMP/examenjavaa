/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnostore.pos.servicio;

import com.tecnostore.pos.modelo.Celular;
import com.tecnostore.pos.modelo.Cliente;
import com.tecnostore.pos.modelo.ItemVenta;
import com.tecnostore.pos.modelo.Venta;
import com.tecnostore.pos.persistencia.CelularDAO;
import com.tecnostore.pos.persistencia.ClienteDAO;
import com.tecnostore.pos.persistencia.ICelularDAO;
import com.tecnostore.pos.persistencia.IClienteDAO;
import com.tecnostore.pos.persistencia.IVentaDAO;
import com.tecnostore.pos.persistencia.VentaDAO;
import java.sql.SQLException;
import java.util.List;

public class GestorVentas {

    private IClienteDAO clienteDAO;
    private ICelularDAO celularDAO;
    private IVentaDAO ventaDAO;

    public GestorVentas() {
        this.clienteDAO = new ClienteDAO();
        this.celularDAO = new CelularDAO();
        this.ventaDAO = new VentaDAO();
    }

    public void registrarVenta(Venta venta) throws SQLException {
        if (venta.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un ítem.");
        }
        verificarCliente(venta.getCliente().getId());
        for (ItemVenta item : venta.getDetalles()) {
            verificarStock(item.getCelular().getId(), item.getCantidad());
        }
        ventaDAO.registrarVenta(venta);
    }

    public List<Venta> listarTodas() throws SQLException {
        return ventaDAO.listarTodas();
    }

    private void verificarCliente(Long idCliente) throws SQLException {
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }
    }

    private void verificarStock(Long idCelular, int cantidad) throws SQLException {
        Celular celular = celularDAO.buscarPorId(idCelular);
        if (celular == null) {
            throw new IllegalArgumentException("Celular no encontrado.");
        }
        if (celular.getStock() < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente.");
        }
    }
}