import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase DBConnection.
 *
 * Se encarga de establecer la conexión con la base de datos Oracle.
 * Proporciona un método estático para obtener una conexión utilizando los parámetros definidos:
 *   - URL: Cadena de conexión JDBC a la base de datos.
 *   - USERNAME: Nombre de usuario de la base de datos.
 *   - PASSWORD: Contraseña del usuario.
 *
 * @author Gautier Nacho Pau
 * @version 1.0
 */
public class DBConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";
    
    /**
     * Obtiene una conexión a la base de datos Oracle.
     *
     * @return Objeto Connection que representa la conexión establecida.
     * @throws SQLException En caso de que falle la conexión.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
