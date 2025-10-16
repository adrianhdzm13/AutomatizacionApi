package co.com.apirest.project.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class JsonFileReader {

    private static final Gson gson = new Gson();

    private JsonFileReader() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Lee un archivo JSON completo desde el classpath
     * @param nombreArchivo nombre del archivo JSON
     * @return contenido del archivo como String
     */
    public static String leerJsonCompleto(String nombreArchivo) {
        try (InputStream inputStream = JsonFileReader.class.getClassLoader()
                .getResourceAsStream(nombreArchivo);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            if (inputStream == null) {
                throw new IllegalArgumentException("Archivo no encontrado: " + nombreArchivo);
            }

            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo JSON: " + nombreArchivo, e);
        }
    }

    /**
     * Lee un nodo específico de un archivo JSON
     * @param nombreArchivo nombre del archivo JSON
     * @param nodo nombre del nodo a extraer (ejemplo: "usuarioExitoso")
     * @return contenido del nodo como String JSON
     */
    public static String leerNodoJson(String nombreArchivo, String nodo) {
        try (InputStream inputStream = JsonFileReader.class.getClassLoader()
                .getResourceAsStream(nombreArchivo);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            if (inputStream == null) {
                throw new IllegalArgumentException("Archivo no encontrado: " + nombreArchivo);
            }

            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            if (!jsonObject.has(nodo)) {
                throw new IllegalArgumentException("Nodo no encontrado: " + nodo + " en archivo: " + nombreArchivo);
            }

            return gson.toJson(jsonObject.get(nodo));
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo JSON: " + nombreArchivo, e);
        }
    }

    /**
     * Lee un nodo específico y lo retorna como JsonObject para manipulación adicional
     * @param nombreArchivo nombre del archivo JSON
     * @param nodo nombre del nodo a extraer
     * @return JsonObject del nodo especificado
     */
    public static JsonObject leerNodoComoObjeto(String nombreArchivo, String nodo) {
        try (InputStream inputStream = JsonFileReader.class.getClassLoader()
                .getResourceAsStream(nombreArchivo);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            if (inputStream == null) {
                throw new IllegalArgumentException("Archivo no encontrado: " + nombreArchivo);
            }

            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            if (!jsonObject.has(nodo)) {
                throw new IllegalArgumentException("Nodo no encontrado: " + nodo + " en archivo: " + nombreArchivo);
            }

            return jsonObject.getAsJsonObject(nodo);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo JSON: " + nombreArchivo, e);
        }
    }
}