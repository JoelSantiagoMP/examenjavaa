package com.tecnostore.pos.persistencia;

import com.tecnostore.pos.modelo.Credito;
import java.sql.SQLException;
import java.util.List;

public interface ICreditoDAO {
    void insertar(Credito credito) throws SQLException;
    void actualizarSaldo(Credito credito) throws SQLException;
    List<Credito> listarPendientes() throws SQLException;
    Credito buscarPorId(Long id) throws SQLException;
}