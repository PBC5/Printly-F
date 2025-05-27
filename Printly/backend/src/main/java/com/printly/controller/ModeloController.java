package com.printly.controller;

import com.printly.model.Usuario;
import com.printly.dao.UsuarioDAO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/modelos")
public class ModeloController {

    private final String uploadDir = "uploads/modelos";

    @PostMapping("/subir")
    public ResponseEntity<?> subirModelo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("titulo") String titulo,
            @RequestParam("usuario") String usuario) {
        
        try {
            // Crear directorio si no existe
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Guardar archivo
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + File.separator + fileName);
            Files.write(path, file.getBytes());

            return ResponseEntity.ok()
                .body("{\"mensaje\": \"Modelo subido correctamente\", \"ruta\": \"" + fileName + "\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("{\"error\": \"Error al subir el modelo: " + e.getMessage() + "\"}");
        }
    }
}