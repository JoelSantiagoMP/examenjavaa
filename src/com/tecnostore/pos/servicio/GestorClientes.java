/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnostore.pos.servicio;

import com.tecnostore.pos.modelo.Cliente;
import com.tecnostore.pos.persistencia.ClienteDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Jorge Gómez
 */
public class GestorClientes {
    private ClienteDAO clienteDAO;

    public GestorClientes() {
        this.clienteDAO = new ClienteDAO();
    }

    public void registrar(Cliente cliente) throws SQLException {
        Cliente existente = clienteDAO.buscarPorIdentificacion(cliente.getIdentificacion());
        if (existente != null) {
            throw new IllegalArgumentException("Ya existe un cliente con esa identificación.");
        }
        clienteDAO.insertar(cliente);
    }

    public void actualizar(Cliente cliente) throws SQLException {
        Cliente existente = clienteDAO.buscarPorId(cliente.getId());
        if (existente == null) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }
        clienteDAO.actualizar(cliente);
    }

    public void eliminar(Long id) throws SQLException {
        Cliente existente = clienteDAO.buscarPorId(id);
        if (existente == null) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }
        clienteDAO.eliminar(id);
    }

    public Cliente buscarPorId(Long id) throws SQLException {
        return clienteDAO.buscarPorId(id);
    }

    public List<Cliente> listarTodos() throws SQLException {
        return clienteDAO.listarTodos();
    }
}
