package com.printly;

import com.printly.dao.UsuarioDAO;

public class ExportarUsuarios {
    public static void main(String[] args) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            String rutaArchivo = "C:/Users/paubercer/Documents/GitHub/Printly-F/usuarios_export.csv";
            dao.exportarUsuariosACSV(rutaArchivo);
            System.out.println("Archivo CSV generado exitosamente en: " + rutaArchivo);
        } catch (Exception e) {
            System.err.println("Error al exportar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }
}