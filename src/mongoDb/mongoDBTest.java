package mongoDb;

import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
public class mongoDBTest {

	public static void main(String[] args) {
		//ConnectionString connectionString=new ConnectionString("mongodb://localhost:27017/");
		try {
		MongoClient mongoClient=new MongoClient("localhost",27017);
		//No hace falta utilizar la funcion createDatabase, con utilizar getDatabase ya se crea la base de datos
		MongoDatabase db= mongoClient.getDatabase("pruebasJava");
		System.out.println("Coleccion creada");
		MongoCollection<Document> coll=db.getCollection("pruebasJava");
		System.out.println("Coleccion seleccionada");
		
		Document d1 = new Document("Name","Carlos");
		d1.append("Surname", "Velasco");
		d1.append("num", 1);
		Document d2 = new Document("Name","Alvaro");
		d2.append("Surname", "Aparicio");
		d2.append("num", 2);
		Document d3 = new Document("Name","Jose Julian");
		d3.append("Surname", "Saavedra");
		d3.append("num", 3);
		Document d4 = new Document("Name","Diego");
		d4.append("Surname", "Hernando");
		d4.append("num", 4);
		coll.insertOne(d1);
		coll.insertOne(d2);
		coll.insertOne(d3);
		coll.insertOne(d4);
		System.out.println("Se han insertado los documentos a la coleccion");
		MongoCursor<String>dbsCursor=mongoClient.listDatabaseNames().iterator();
		while(dbsCursor.hasNext()) {
			System.out.println(dbsCursor.next());
		}
		mongoClient.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
