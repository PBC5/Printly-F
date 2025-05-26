import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Aplicación Printly – Red social para compartir y gestionar modelos 3D.
 *
 * Esta clase principal contiene el método main que muestra un menú interactivo en la consola,
 * permitiendo registrar usuarios y publicaciones en la base de datos, listar registros existentes
 * y probar la conexión a la base de datos. Además, se registran en un archivo log (log.txt) las acciones importantes.
 *
 * Operaciones disponibles:
 *   1. Registrar Usuario en BD
 *   2. Listar Usuarios desde BD
 *   3. Registrar Publicación en BD
 *   4. Listar Publicaciones desde BD
 *   5. Test Conexión a la BD
 *   6. Salir
 *
 * @author Gautier Nacho Pau
 * @version 1.0
 */
public class PrintlyApp {
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * Método principal de la aplicación.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        int opcion = 0;
        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch(opcion) {
                    case 1:
                        registrarUsuarioDB();
                        break;
                    case 2:
                        listarUsuariosDB();
                        break;
                    case 3:
                        registrarPublicacionDB();
                        break;
                    case 4:
                        listarPublicacionesDB();
                        break;
                    case 5:
                        testConexionBD();
                        break;
                    case 6:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch(NumberFormatException e) {
                System.out.println("Por favor ingresa un número válido.");
            }
        } while(opcion != 6);
    }
    
    /**
     * Muestra el menú de opciones en la consola.
     */
    private static void mostrarMenu() {
        System.out.println("==== Printly App ====");
        System.out.println("1. Registrar Usuario en BD");
        System.out.println("2. Listar Usuarios desde BD");
        System.out.println("3. Registrar Publicación en BD");
        System.out.println("4. Listar Publicaciones desde BD");
        System.out.println("5. Test Conexión a la BD");
        System.out.println("6. Salir");
        System.out.print("Elige una opción: ");
    }
    
    /**
     * Obtiene el siguiente ID disponible para una tabla y columna específicas.
     *
     * Se consulta la base de datos para obtener el máximo valor del identificador y se suma 1,
     * utilizando la función Oracle NVL para manejar valores nulos.
     *
     * @param table Nombre de la tabla.
     * @param column Nombre de la columna del identificador.
     * @return El siguiente ID disponible.
     */
    private static int getNextId(String table, String column) {
        int nextId = 0;
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT NVL(MAX(" + column + "), 0) + 1 AS next_id FROM " + table)) {
            if (rs.next()) {
                nextId = rs.getInt("next_id");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el próximo ID: " + e.getMessage());
        }
        return nextId;
    }
    
    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * Solicita al usuario los datos necesarios (nombre, email y contraseña), obtiene un ID
     * disponible, inserta el registro en la tabla USUARIOS y escribe un log con la acción.
     */
    private static void registrarUsuarioDB() {
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            int id = getNextId("USUARIOS", "usuario_id");
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO USUARIOS (usuario_id, nombre, email, password, fecha_registro) VALUES (?, ?, ?, ?, SYSDATE)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Usuario registrado en la BD con id: " + id);
                escribirLog("Usuario registrado en BD: " + nombre);
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error en registrar usuario en BD: " + e.getMessage());
        }
    }
    
    /**
     * Lista todos los usuarios registrados en la base de datos.
     *
     * Recupera la información de la tabla USUARIOS ordenada por el identificador y muestra cada registro en la consola.
     */
    private static void listarUsuariosDB() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT usuario_id, nombre, email FROM USUARIOS ORDER BY usuario_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("usuario_id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                System.out.println("ID: " + id + " - Nombre: " + nombre + " - Email: " + email);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error en listar usuarios: " + e.getMessage());
        }
    }
    
    /**
     * Registra una nueva publicación en la base de datos.
     *
     * Solicita los datos (ID del usuario, título, descripción y tipo de publicación) e inserta el registro
     * en la tabla PUBLICACIONES, además de escribir un log con la acción realizada.
     */
    private static void registrarPublicacionDB() {
        try {
            System.out.print("ID del usuario: ");
            int usuarioId = Integer.parseInt(scanner.nextLine());
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Descripción: ");
            String descripcion = scanner.nextLine();
            System.out.print("Tipo (modelo3D/objeto): ");
            String tipo = scanner.nextLine();
            int id = getNextId("PUBLICACIONES", "publicacion_id");
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO PUBLICACIONES (publicacion_id, usuario_id, titulo, descripcion, fecha_publicacion, tipo) VALUES (?, ?, ?, ?, SYSDATE, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setInt(2, usuarioId);
            pstmt.setString(3, titulo);
            pstmt.setString(4, descripcion);
            pstmt.setString(5, tipo);
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Publicación registrada en la BD con id: " + id);
                escribirLog("Publicación registrada en BD: " + titulo);
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error en registrar publicación en BD: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("ID de usuario debe ser un número.");
        }
    }
    
    /**
     * Lista todas las publicaciones registradas en la base de datos.
     *
     * Recupera y muestra en la consola los registros de la tabla PUBLICACIONES ordenados por su identificador.
     */
    private static void listarPublicacionesDB() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT publicacion_id, usuario_id, titulo, tipo FROM PUBLICACIONES ORDER BY publicacion_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("publicacion_id");
                int usuarioId = rs.getInt("usuario_id");
                String titulo = rs.getString("titulo");
                String tipo = rs.getString("tipo");
                System.out.println("ID: " + id + " - Usuario ID: " + usuarioId + " - Título: " + titulo + " - Tipo: " + tipo);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error en listar publicaciones: " + e.getMessage());
        }
    }
    
    /**
     * Realiza una prueba de conexión a la base de datos.
     *
     * Muestra un mensaje en la consola indicando si la conexión fue exitosa o no.
     */
    private static void testConexionBD() {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                System.out.println("Conexión exitosa a la base de datos.");
                conn.close();
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }
    
    /**
     * Escribe un mensaje de log en el archivo "log.txt" junto con la fecha actual.
     *
     * @param mensaje Mensaje a registrar en el log.
     */
    private static void escribirLog(String mensaje) {
        try (FileWriter fw = new FileWriter("log.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(new java.util.Date() + " - " + mensaje);
        } catch (IOException e) {
            System.out.println("Error al escribir en log: " + e.getMessage());
        }
    }
}
