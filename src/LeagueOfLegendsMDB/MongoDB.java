package LeagueOfLegendsMDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Clase MongoDB.
 * Esta clase gestiona la conexion con la base de datos.
 * Se accede a las credenciales de la base de datos mediante un archivo properties.
 * @author Carlos Velasco, Alvaro Aparicio, Diego Hernando, Jose Julian Saavedra
 * @since 2023 - 2024
 * @version 1.0
 */
public class MongoDB {

	private MongoClient mongoClient = null;
	private MongoDatabase mongoDatabase = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoDB.class);
	
	public MongoDB() {
		
		Properties fichProperties = new Properties();
		try {
			
			fichProperties.load(new FileInputStream(new File("mongodb.properties")));
			mongoClient = new MongoClient(fichProperties.getProperty("ip"), Integer.parseInt(fichProperties.getProperty("port")));
			mongoDatabase = mongoClient.getDatabase(fichProperties.getProperty("database"));
		
		
		} catch (IOException e) {
			LOGGER.error("Error E/S");
		}
		
		
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public MongoDatabase getMongoDatabase() {
		return mongoDatabase;
	}

	
}
