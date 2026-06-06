/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tecnostore.pos.persistencia;

/**
 *
 * @author camper
 */
import com.tecnostore.pos.modelo.Cliente;
import java.sql.SQLException;
import java.util.List;

public interface IClienteDAO {
    void insertar(Cliente cliente) throws SQLException;
    void actualizar(Cliente cliente) throws SQLException;
    void eliminar(Long id) throws SQLException;
    Cliente buscarPorId(Long id) throws SQLException;
    Cliente buscarPorIdentificacion(String identificacion) throws SQLException;
    List<Cliente> listarTodos() throws SQLException;
}