import java.io.*;
import java.nio.file.*;
import org.json.*;

public class ExportarPosts {
    public static void main(String[] args) {
        String inputPath = "posts.json";
        String outputPath = "posts_exportados.txt";

        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(inputPath)));
            JSONArray posts = new JSONArray(jsonString);

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.getJSONObject(i);
                writer.write("Nombre: " + post.optString("usuario", "") + "\n");
                writer.write("Descripción: " + post.optString("titulo", "") + "\n");
                writer.write("Extensión: " + post.optString("ext", "") + "\n");
                writer.write("-----\n");
            }
            writer.close();
            System.out.println("Exportación completada en: " + outputPath);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}