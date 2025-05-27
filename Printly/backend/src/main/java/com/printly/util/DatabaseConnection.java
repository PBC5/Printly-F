package com.printly.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://db.bit.io:5432/yourusername/your-db";
    private static final String USER = "yourusername";
    private static final String PASS = "your-password";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se pudo cargar el driver de PostgreSQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new SQLException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
    }
}
