package LeagueOfLegendsMDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;


public class Programa {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Programa.class);
	
	private static MongoDB db = new MongoDB();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br= new BufferedReader(isr);
		try {
			
			int numero;
			do {
				System.out.println("1- Crear Campeon");
				System.out.println("2- Crear Region");
				System.out.println("3- Leer Campeon");
				System.out.println("4- Leer Region");
				System.out.println("5- Actualizar Campeon");
				System.out.println("6- Borrar Campeon");
				System.out.println("7- Borrar Region");
				System.out.println("8- Salir");
				
				System.out.println("Introduce un numero para realizar una accion sobre la base de datos");
				numero=Integer.valueOf(br.readLine());
				
				switch(numero){
				case 1:crearCampeon();
					break;
				case 2:crearRegion();
					break;
				case 6:eliminarCampeon();
					break;
				case 7:eliminarRegion();
					break;
				}
				
			}while(numero>0&&numero<7);
			
		}catch(Exception e) {
			
		}
	}
	public static void crearCampeon() {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br= new BufferedReader(isr);
		MongoCollection<Document> collecion = db.getMongoDatabase().getCollection("Campeones");
		
		String nombre, alias, clase, carril, region;
		double vida=0,regenVida=0,armor=0,mr=0,ad=0,atkspeed=0;
		
		try {
			System.out.println("Nombre del campeon");
			nombre=br.readLine();
			System.out.println("Alias del campeon");
			alias=br.readLine();
			
			do {
				System.out.println("A que clase pertenece el campeon");
				String[] clases= {"Tirador","Coloso","Tanque","Asesino","Hechizero","Apoyo"};
				for (String x : clases) {
					System.out.println(x);
				}
				clase=br.readLine();
			}while(!(clase.equalsIgnoreCase("Tirador")||clase.equalsIgnoreCase("Coloso")||clase.equalsIgnoreCase("Asesino")||
					clase.equalsIgnoreCase("Hechizero")||clase.equalsIgnoreCase("Apoyo")));
			
			do {
			System.out.println("Carril al que pertenece el campeon");
			String[] carriles= {"Superior","Jungla","Central","Inferior","Apoyo"};
			for(String y: carriles) {
				System.out.println(y);
			}
			carril=br.readLine();
			}while(!(carril.equalsIgnoreCase("Superior")||carril.equalsIgnoreCase("Jungla")||carril.equalsIgnoreCase("Central")||
					carril.equalsIgnoreCase("Inferior")||carril.equalsIgnoreCase("Apoyo")));
			System.out.println("Region a la que pertenece el campeon");
			
			do {
			String[] regionesFicticias = {"Demacia", "Noxus", "Piltover", "Zaun", "Freljord", "Jonia", "Shadow Isles", "Bandle City", "Aguasestancadas", "Targon","El Vacio","Desconocido"};

	        for (String regionFicticia : regionesFicticias) {
	            System.out.println(regionFicticia);
	        }
	        region=br.readLine();
			}while(!(region.equalsIgnoreCase("Demacia")||region.equalsIgnoreCase("Noxus")||region.equalsIgnoreCase("Piltover")||
					region.equalsIgnoreCase("Zaun")||region.equalsIgnoreCase("Freljord")||region.equalsIgnoreCase("Jonia")||
					region.equalsIgnoreCase("Shadow Isles")||region.equalsIgnoreCase("Bandle City")||
					region.equalsIgnoreCase("Aguasestancadas")||region.equalsIgnoreCase("Targon")||region.equalsIgnoreCase("El Vacio")||region.equalsIgnoreCase("Desconocido")));
			
			boolean comprobante=false;			
			do {
				try {
				
				System.out.println("Estadisticas base del campeon");
				System.out.println("Vida: ");
				vida=Double.valueOf(br.readLine());
				System.out.println("Regeneracion de vida:");
				regenVida=Double.valueOf(br.readLine());
				System.out.println("Armadura:");
				armor=Double.valueOf(br.readLine());
				System.out.println("Resistencia magica:");
				mr=Double.valueOf(br.readLine());
				System.out.println("Daño de ataque:");
				ad=Double.valueOf(br.readLine());
				System.out.println("Velocidad de ataque:");
				atkspeed=Double.valueOf(br.readLine());
				
				comprobante=true;
				}catch(NumberFormatException e) {
					LOGGER.error("Mete numeros no letras!!!");
					comprobante=false;
				}
			}while(comprobante==false);
			
			Document campeon= new Document("Campeon",nombre)
					.append("Alias", alias)
					.append("Clase", clase)
					.append("Carril", carril)
					.append("Region", region)
					.append("Estadisticas", new Document("Vida",vida)
							.append("Regeneracion de Vida",regenVida )
							.append("Armadura", armor)
							.append("Resistencia magica", mr)
							.append("Daño de ataque", ad)
							.append("Velocidad de ataque", atkspeed));
			collecion.insertOne(campeon);
			
		} catch (IOException e) {
			LOGGER.error("Error de E/S");
		}
		
	}
	public static void crearRegion() {
		MongoCollection<Document> collection = db.getMongoDatabase().getCollection("Regiones");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br= new BufferedReader(isr);
		ArrayList<String> lista=new ArrayList<String>();
		String nombre;
		int campeones;
		try {
		System.out.println("Nombre de la region");
		do {
			String[] regionesFicticias = {"Demacia", "Noxus", "Piltover", "Zaun", "Freljord", "Jonia", "Shadow Isles", "Bandle City", "Aguasestancadas", "Targon","El Vacio","Desconocido"};

	        for (String regionFicticia : regionesFicticias) {
	            System.out.println(regionFicticia);
	        }
	        nombre=br.readLine();
			}while(!(nombre.equalsIgnoreCase("Demacia")||nombre.equalsIgnoreCase("Noxus")||nombre.equalsIgnoreCase("Piltover")||
					nombre.equalsIgnoreCase("Zaun")||nombre.equalsIgnoreCase("Freljord")||nombre.equalsIgnoreCase("Jonia")||
					nombre.equalsIgnoreCase("Shadow Isles")||nombre.equalsIgnoreCase("Bandle City")||
					nombre.equalsIgnoreCase("Aguasestancadas")||nombre.equalsIgnoreCase("Targon")||nombre.equalsIgnoreCase("El Vacio")||nombre.equalsIgnoreCase("Desconocido")));
			
		
		System.out.println("Campeones pertenecientes a la region");
		try {
		do {
			campeones = Integer.valueOf(br.readLine());
			for(int i=0;i<campeones;i++) {
				System.out.println("Lista de campeones: ");
				String campeon=br.readLine();
				lista.add(campeon);
			}
		}
		while(campeones<0);
		}catch(NumberFormatException e) {
			System.out.println("Mete numeros");
		}
		Document region=new Document("Region",nombre)
				.append("Lista de Campeones",lista );
		collection.insertOne(region);
		
		} catch (IOException e) {
			LOGGER.error("Error de E/S");
		}
	}
	
	public static boolean eliminarCampeon() {
		MongoCollection<Document> collection = db.getMongoDatabase().getCollection("Campeones");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br= new BufferedReader(isr);
		String nombre;
	
		try {
		System.out.println("Indique el nombre del campeon que desea eliminar: ");
		nombre = br.readLine();
		
		DeleteResult deleteResult = collection.deleteOne(Filters.eq("Campeon", nombre));
		
		if(deleteResult.getDeletedCount() > 0) {
			LOGGER.info("Campeon " + nombre + " eliminado");
		}
		else {
			LOGGER.warn("Campeon " + nombre + " no encontrado");
		}
		
		
		}
		catch(IOException e) {
			LOGGER.error("Error de E/S");
		}
		return true;
	}
	public static boolean eliminarRegion() {
		MongoCollection<Document> collection = db.getMongoDatabase().getCollection("Regiones");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br= new BufferedReader(isr);
		String nombre;
	
		try {
		System.out.println("Indique el nombre de la Region que desea eliminar: ");
		nombre = br.readLine();
		
		DeleteResult deleteResult = collection.deleteOne(Filters.eq("Region", nombre));
		
		if(deleteResult.getDeletedCount() > 0) {
			LOGGER.info("Region " + nombre + " eliminada");
		}
		else {
			LOGGER.warn("Region " + nombre + " no encontrada");
		}
		
		}
		catch(IOException e) {
			LOGGER.error("Error de E/S");
		}
		return true;
	}

	
}
