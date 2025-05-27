package com.printly.controller;

import com.printly.model.Usuario;
import com.printly.model.util.DBConnection;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.sql.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        if (!validarDatos(usuario)) {
            return ResponseEntity.badRequest()
                    .body("{\"error\": \"Datos inválidos\"}");
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Verificar si el email ya existe
            if (emailExiste(conn, usuario.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("{\"error\": \"El email ya está registrado\"}");
            }

            // Insertar nuevo usuario
            String sql = "INSERT INTO USUARIOS (usuario_id, nombre, email, password, fecha_registro) " +
                        "VALUES (?, ?, ?, ?, SYSDATE)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int id = getNextId(conn);
                pstmt.setInt(1, id);
                pstmt.setString(2, usuario.getNombre());
                pstmt.setString(3, usuario.getEmail());
                pstmt.setString(4, usuario.getPassword());
                
                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    return ResponseEntity.ok()
                            .body("{\"id\": " + id + ", \"mensaje\": \"Usuario registrado correctamente\"}");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al registrar usuario\"}");
        }
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\": \"Error desconocido\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getPassword() == null) {
            return ResponseEntity.badRequest()
                    .body("{\"error\": \"Email y contraseña son requeridos\"}");
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT usuario_id, nombre FROM USUARIOS WHERE email = ? AND password = ?";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, usuario.getEmail());
                pstmt.setString(2, usuario.getPassword());
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return ResponseEntity.ok()
                                .body(String.format(
                                    "{\"id\": %d, \"nombre\": \"%s\", \"mensaje\": \"Login exitoso\"}",
                                    rs.getInt("usuario_id"),
                                    rs.getString("nombre")
                                ));
                    }
                }
            }
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\": \"Credenciales inválidas\"}");
            
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error en el servidor\"}");
        }
    }

    private boolean validarDatos(Usuario usuario) {
        return usuario != null &&
               usuario.getNombre() != null && !usuario.getNombre().isEmpty() &&
               usuario.getEmail() != null && usuario.getEmail().contains("@") &&
               usuario.getPassword() != null && usuario.getPassword().length() >= 4;
    }

    private boolean emailExiste(Connection conn, String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM USUARIOS WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private int getNextId(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT NVL(MAX(usuario_id), 0) + 1 FROM USUARIOS")) {
            return rs.next() ? rs.getInt(1) : 1;
        }
    }
}