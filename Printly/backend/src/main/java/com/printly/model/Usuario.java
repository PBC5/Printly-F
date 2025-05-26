/**
 * Clase Usuario.
 *
 * Representa a un usuario de la aplicación Printly.
 * Cada usuario contiene un identificador único, nombre, correo electrónico y contraseña.
 *
 * @author Gautier Nacho Pau
 * @version 1.0
 */
public class Usuario {
    private int usuarioId;
    private String nombre;
    private String email;
    private String password;
    
    /**
     * Constructor de la clase Usuario.
     *
     * @param usuarioId Identificador único del usuario.
     * @param nombre Nombre del usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     */
    public Usuario(int usuarioId, String nombre, String email, String password) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }
    
    /**
     * Obtiene el identificador del usuario.
     *
     * @return usuarioId.
     */
    public int getUsuarioId() {
        return usuarioId;
    }
    
    /**
     * Obtiene el nombre del usuario.
     *
     * @return nombre.
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return email.
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Obtiene la contraseña del usuario.
     *
     * @return password.
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Genera una representación en cadena del usuario, omitiendo la contraseña por seguridad.
     *
     * @return Cadena con los detalles del usuario.
     */
    public String toString() {
        return "Usuario [id=" + usuarioId + ", nombre=" + nombre + ", email=" + email + "]";
    }
}
