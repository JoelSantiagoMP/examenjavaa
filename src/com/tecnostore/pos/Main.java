/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.tecnostore.pos;

import com.tecnostore.pos.persistencia.ConexionDB;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Jorge Gómez
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== TecnoStore POS — Prueba de conexión ===");

        try (Connection con = ConexionDB.getInstancia().obtenerConexion()) {

            if (con != null && !con.isClosed()) {
                System.out.println("✅ Conexión exitosa a la base de datos.");
                System.out.println("   URL:      " + con.getMetaData().getURL());
                System.out.println("   Usuario:  " + con.getMetaData().getUserName());
                System.out.println("   Driver:   " + con.getMetaData().getDriverName()
                                   + " v" + con.getMetaData().getDriverVersion());
            }

        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            System.err.println("   Código SQL:  " + e.getErrorCode());
            System.err.println("   Estado SQL:  " + e.getSQLState());
        }

        System.out.println("===========================================");
    }
}
