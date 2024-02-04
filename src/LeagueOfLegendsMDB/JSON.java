package LeagueOfLegendsMDB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.bson.Document;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Clase JSON.
 * Esta clase se encarga de leer ficheros .json y apartir de ellos crear documentos de campeones 
 * @author Carlos Velasco, Alvaro Aparicio, Diego Hernando, Jose Julian Saavedra
 * @since 2023 - 2024
 * @version 1.0
 */
public class JSON {
	private static final Logger LOGGER = LoggerFactory.getLogger(JSON.class);

    /**
     * Metodo leerContenidoArchivo
     * Recibe por parametro un string que sera la ruta del fichero json que utilizara para leerlo
     * y devolvera un String
     * @param ruta
     * @return
     */
    public static String leerContenidoArchivo(String ruta) {
        try {
            byte[] contenido = Files.readAllBytes(Paths.get(ruta));
            return new String(contenido);
        } catch (IOException e) {
        	LOGGER.error("Error durante la lectura del Fichero JSON");
            return null;
        }
    }

    /**
     *  Crea un objeto Document de MongoDB a partir de un JSONObject que recibe por parametro y que representa
	 * la información de un campeón.
	 *
	 * @param campeon 
	 * @return Document
	 */
    public static Document crearDocumentoCampeon(JSONObject campeon) {
        String nombre = campeon.getString("Campeon");
        String alias = campeon.getString("Alias");
        String clase = campeon.getString("Clase");
        String carril = campeon.getString("Carril");
        String region = campeon.getString("Region");

        Document estadisticas = new Document();
        JSONObject estadisticasJSON = campeon.getJSONObject("Estadisticas");
        for (String key : estadisticasJSON.keySet()) {
            estadisticas.append(key, estadisticasJSON.get(key));
        }

        // Crea el documento para el campeón
        return new Document("Campeon", nombre).append("Alias", alias).append("Clase", clase)
                .append("Carril", carril).append("Region", region).append("Estadisticas", estadisticas);
    }

}