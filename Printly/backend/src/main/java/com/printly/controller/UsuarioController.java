package com.printly.controller;

import com.printly.model.Usuario;
import com.printly.dao.UsuarioDAO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UsuarioController {
    
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @PostMapping("/usuarios/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            int id = usuarioDAO.guardarUsuario(usuario);
            return ResponseEntity.ok()
                .body(String.format("{\"id\": %d, \"mensaje\": \"Usuario registrado correctamente\"}", id));
        } catch (SQLException e) {
            return ResponseEntity.badRequest()
                .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}