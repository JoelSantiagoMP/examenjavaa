/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tecnostore.pos.persistencia;

/**
 *
 * @author camper
 */
import com.tecnostore.pos.modelo.Celular;
import java.sql.SQLException;
import java.util.List;

public interface ICelularDAO {
    void insertar(Celular celular) throws SQLException;
    Celular buscarPorId(Long id) throws SQLException;
    List<Celular> listarTodos() throws SQLException;
    void actualizar(Celular celular) throws SQLException;
    void eliminar(Long id) throws SQLException;
}
