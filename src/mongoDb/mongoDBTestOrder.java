package mongoDb;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
public class mongoDBTestOrder {

	public static void main(String[] args) {
		try {
			MongoClient mongoClient = new MongoClient("localhost",27017);
			
			//Acediendo a la base de datos Order que no hemos creado pero se creara sola
			MongoDatabase orderDatabase = mongoClient.getDatabase("order");
			System.out.println("Database Name: "+orderDatabase.getName());
			
			//Recuperando la coleccion producto que se creara sola
			MongoCollection<Document>productCollection=orderDatabase.getCollection("product");
			System.out.println("coleccion productos seleccionada con exito");
			
//Insertar varios productos a la vez---------------------------------------------------------------------
	/*
			Document iphoneDocument = new Document("productName", "IPhone")
					.append("description", "IPhone14")
					.append("price", 70000)
					.append("color", "white");	
			
			Document samsungTVDocument = new Document("productName", "Samsung LED TV")
					.append("description", "65 inch LED TV")
					.append("price", 120000)
					.append("Support OTT", "Yes");	
			
			Document alexaDocument = new Document("productName", "Alexa")
					.append("description", "Can Speak")
					.append("price", 5000)
					.append("Support Music", "Yes");
			
			List<Document> documentList = new ArrayList<Document>();
			documentList.add(iphoneDocument);
			documentList.add(samsungTVDocument);
			documentList.add(alexaDocument);
			
			//Inserting the documents
			productCollection.insertMany(documentList);
		*/	
//Recuperar un producto de la coleccion producto-----------------------------------------------
			
			//Getting the iterable object
			FindIterable<Document> findIterable=productCollection.find();
			//Getting the iterator
			Iterator<Document> iterator =findIterable.iterator();
			while(iterator.hasNext()) {
				Document document =iterator.next();
				System.out.println(document);
				System.out.println("Product name= "+document.get("productName"));
			}

			System.out.println("Documents recovered successfully");
//------------------------------------------------------------------	
//Como obtener y seleccionar una coleccion y actualizar un SOLO documento de la coleccion
		/*productCollection.updateOne(Filters.eq("productName","IPhone"),Updates.set("price", 9000));
		System.out.println("Document updated sucessfully....");
*/
//------------------------------------------------------------

//Como obtener y seleccionar una coleccion y actualizar VARIOS documentos de la coleccion
		/*	
		productCollection.updateMany(Filters.eq("price",2000), Updates.set("price", 80000));
		System.out.println("Documents Updated succesfully");
		*/
//Como obtener y seleccionar una coleccion y leer un documento especifico
		/*Document productDocument = productCollection.find(new Document("productName","Alexa")).first();
		System.out.println("productDocument= "+productDocument);
		*/
//----------------------------------------------------------------

//Como obtener y seleccionar una coleccion y leer un rango de documentos
		/*
		//El filtro es que el precio del documento debe ser mayor o igual a 10000 por lo que debe recuperar 2 documentos
		FindIterable<Document> findIterable2=productCollection.find(Filters.gte("price", 10000));
		MongoCursor<Document> mongoCursor2= findIterable2.iterator();
		while(mongoCursor2.hasNext()) {
			System.out.println(mongoCursor2.next().toJson());
		}
		*/
//Como obtener y seleccionar una coleccion y leer un rango de documentos y añadiendo los documentos en una ArrayList o Consumer(interfaz funcional)
			/*
			System.out.println("Get the range of documents as a List");
			List<Document> productList = productCollection.find(Filters.gte("price",10000)).into(new ArrayList<>());
			for(Document productDocument:productList) {
				System.out.println(productDocument.toJson());
			}
			
			System.out.println("\nGet the range of documents using which is a functional interface");
			Consumer<Document> printConsumer = document -> System.out.println(document.toJson());
			productCollection.find(Filters.gt("price",10000)).forEach(printConsumer);
			*/
//Como obtener y seleccionar una coleccion actualizar un documento de la coleccion usando findOneandUpdate method
			//Devuelve la version antigua del documento antes de ser actualizado
		/*Document oldProductDocument = productCollection.findOneAndUpdate(Filters.eq("productName","IPhone"), Updates.set("price", 900000));
		System.out.println("old Doc = "+oldProductDocument);
		
		//Obtener la nueva version
		FindOneAndUpdateOptions optionAfter = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
		Document newProductDocument = productCollection.findOneAndUpdate(Filters.eq("productName","Alexa"), Updates.set("price", 10000),optionAfter);
		System.out.println("latest update doc= "+newProductDocument);
		*/
//Como obtener y seleccionar una coleccion y borrar un documento de la coleccion
		/*
			DeleteResult deleteResult = productCollection.deleteOne(Filters.eq("productName","Alexa"));
		System.out.println("Deleted Document count= "+deleteResult.getDeletedCount());
*/
//Como obtener y seleccionar una coleccion y borrar VARIOS documentos de la coleccion
		/*
			DeleteResult deleteResult = productCollection.deleteMany(Filters.eq("price",10000));
		System.out.println("Deleted documents  count = "+deleteResult.getDeletedCount());
		*/
//Como obtener y seleccionar una coleccion y borrar un documento de la coleccion usando findOneAndDelete
		
			/*Document document= productCollection.findOneAndDelete(Filters.eq("productName","Alexa"));
		System.out.println(document.toJson(JsonWriterSettings.builder().indent(true).build()));
*/
		
//Como conectarse a mongo y obtener todas las bases de datos
			List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
			databases.forEach(db-> System.out.println(db.toJson()));
			
//Como listar todas las colecciones en MOngodb
		MongoIterable<String>listOfCollectionName= orderDatabase.listCollectionNames();
		for(String collectionName:listOfCollectionName) {
			System.out.println(collectionName);
		}
//Como borrar una coleccion de mongodb
		//Borrar todos los documentos de una coleccion no eliminara la coleccion ya que esta contiene metadatos
		//Drop collection 
		productCollection.drop();//drop elimina por completo la colecccion junto con los metadatos
		System.out.println("Collection removed sucessfully");
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
