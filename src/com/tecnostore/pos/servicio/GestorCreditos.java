package com.tecnostore.pos.servicio;

import com.tecnostore.pos.modelo.Credito;
import com.tecnostore.pos.persistencia.CreditoDAO;
import com.tecnostore.pos.persistencia.ICreditoDAO;
import java.sql.SQLException;
import java.util.List;


public class GestorCreditos {

    private ICreditoDAO creditoDAO;

    public GestorCreditos() {
        this.creditoDAO = new CreditoDAO();
    }

    public void registrar(Credito credito) throws SQLException {
        creditoDAO.insertar(credito);
    }

    public void actualizarSaldo(Credito credito) throws SQLException {
        Credito existente = creditoDAO.buscarPorId(credito.getId());
        if (existente == null) {
            throw new IllegalArgumentException("Crédito no encontrado.");
        }
        creditoDAO.actualizarSaldo(credito);
    }

    public List<Credito> listarPendientes() throws SQLException {
        return creditoDAO.listarPendientes();
    }

    public Credito buscarPorId(Long id) throws SQLException {
        return creditoDAO.buscarPorId(id);
    }
}