/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnostore.pos.persistencia;

import com.tecnostore.pos.modelo.Celular;
import com.tecnostore.pos.persistencia.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.tecnostore.pos.modelo.SistemaOperativo;
import com.tecnostore.pos.modelo.CategoriaGama;
/**
 *
 * @author Jorge Gómez
 */
public class CelularDAO {
    public void insertar(Celular celular) throws SQLException {
        
        String sql = "INSERT INTO celulares (marca, modelo, sistema_operativo, gama, precio, stock) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionDB.getInstancia().obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, celular.getMarca());
            ps.setString(2, celular.getModelo());
            ps.setString(3, celular.getSistemaOperativo().name());
            ps.setString(4, celular.getCategoriaGama().name());
            ps.setBigDecimal(5, celular.getPrecio());
            ps.setInt(6, celular.getStock());
            ps.executeUpdate();
            
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                celular.setId(keys.getLong(1));
            }
        }
    }
    
    public Celular buscarPorId(Long id) throws SQLException {
        String sql = "SELECT id, marca, modelo, sistema_operativo, gama, precio, stock "
                + "FROM celulares WHERE id = ?";
        
        try (Connection con = ConexionDB.getInstancia().obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Celular celular = new Celular();
                celular.setId(rs.getLong("id"));
                celular.setMarca(rs.getString("marca"));
                celular.setModelo(rs.getString("modelo"));
                celular.setSistemaOperativo(
                        SistemaOperativo.valueOf(rs.getString("sistema_operativo")));
                celular.setCategoriaGama(CategoriaGama.valueOf(rs.getString("gama")));
                celular.setPrecio(rs.getBigDecimal("precio"));
                celular.setStock(rs.getInt("stock"));
                return celular;
            }
            return null;
        }
    }
    
    public List<Celular> listarTodos() throws SQLException {
        List<Celular> lista = new ArrayList<>();
        
        String sql = "SELECT id, marca, modelo, sistema_operativo, gama, precio, stock "
           + "FROM celulares";
        
        try (Connection con = ConexionDB.getInstancia().obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Celular celular = new Celular();
                celular.setId(rs.getLong("id"));
                celular.setMarca(rs.getString("marca"));
                celular.setModelo(rs.getString("modelo"));
                celular.setSistemaOperativo(
                        SistemaOperativo.valueOf(rs.getString("sistema_operativo")));
                celular.setCategoriaGama(CategoriaGama.valueOf(rs.getString("gama")));
                celular.setPrecio(rs.getBigDecimal("precio"));
                celular.setStock(rs.getInt("stock"));
                lista.add(celular);
            }
            return lista;
        }
    }
}
