package LeagueOfLegendsMDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDB {

	private MongoClient mongoClient = null;
	private MongoDatabase mongoDatabase = null;
	
	public MongoDB() {
		
		Properties fichProperties = new Properties();
		try {
			
			fichProperties.load(new FileInputStream(new File("mongodb.properties")));
			mongoClient = new MongoClient(fichProperties.getProperty("ip"), Integer.parseInt(fichProperties.getProperty("port")));
			mongoDatabase = mongoClient.getDatabase(fichProperties.getProperty("database"));
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public MongoDatabase getMongoDatabase() {
		return mongoDatabase;
	}

	
}
