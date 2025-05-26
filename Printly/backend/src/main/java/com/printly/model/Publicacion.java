/**
 * Clase Publicacion.
 *
 * Representa una publicación realizada por un usuario en la aplicación Printly.
 * Cada publicación contiene un identificador único, el identificador del usuario que la creó,
 * un título, una descripción y un tipo (por ejemplo, "modelo3D" o "objeto").
 *
 * @author Gautier Nacho Pau
 * @version 1.0
 */
public class Publicacion {
    private int publicacionId;
    private int usuarioId;
    private String titulo;
    private String descripcion;
    private String tipo;
    
    /**
     * Constructor de la clase Publicacion.
     *
     * @param publicacionId Identificador único de la publicación.
     * @param usuarioId Identificador del usuario que crea la publicación.
     * @param titulo Título de la publicación.
     * @param descripcion Descripción del contenido publicado.
     * @param tipo Tipo de publicación (e.g., "modelo3D" o "objeto").
     */
    public Publicacion(int publicacionId, int usuarioId, String titulo, String descripcion, String tipo) {
        this.publicacionId = publicacionId;
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }
    
    /**
     * Obtiene el identificador de la publicación.
     *
     * @return publicacionId.
     */
    public int getPublicacionId() {
        return publicacionId;
    }
    
    /**
     * Obtiene el identificador del usuario que creó la publicación.
     *
     * @return usuarioId.
     */
    public int getUsuarioId() {
        return usuarioId;
    }
    
    /**
     * Obtiene el título de la publicación.
     *
     * @return titulo.
     */
    public String getTitulo() {
        return titulo;
    }
    
    /**
     * Obtiene la descripción de la publicación.
     *
     * @return descripcion.
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Obtiene el tipo de la publicación.
     *
     * @return tipo.
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Genera una representación en cadena de la publicación, útil para depuración o impresión.
     *
     * @return Cadena con los detalles de la publicación.
     */
    public String toString() {
        return "Publicacion [id=" + publicacionId + ", usuarioId=" + usuarioId + ", titulo=" + titulo + ", tipo=" + tipo + "]";
    }
}
