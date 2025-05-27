package com.printly.dao;

import com.printly.model.Usuario;
import com.printly.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public void guardarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (id, nombre, email, password) VALUES (usuarios_seq.NEXTVAL, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getPassword());
            
            pstmt.executeUpdate();
        }
    }

    public List<Usuario> obtenerTodosUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY fecha_registro DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("password")
                );
                usuario.setFechaRegistro(rs.getDate("fecha_registro"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public void exportarUsuariosACSV(String rutaArchivo) throws SQLException {
        List<Usuario> usuarios = obtenerTodosUsuarios();
        
        try (java.io.PrintWriter writer = new java.io.PrintWriter(rutaArchivo)) {
            // Escribir encabezados
            writer.println("ID,Nombre,Email,Fecha Registro");
            
            // Escribir datos
            for (Usuario usuario : usuarios) {
                writer.println(String.format("%d,%s,%s,%s",
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getFechaRegistro()
                ));
            }
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el archivo CSV", e);
        }
    }
}
