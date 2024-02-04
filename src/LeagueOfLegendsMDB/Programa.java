package LeagueOfLegendsMDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;


/**
 * 
 * Clase principal del programa. 
 * En ella se ejecuta la logica del programa. 
 * El programa consiste en la conexion con una base de datos no relacional mediante la cual se puede interactuar de distintas formas. 
 * Se permite la insercion, eliminacion, modificacion y consulta de datos.
 * @author Carlos Velasco, Alvaro Aparicio, Diego Hernando, Jose Julian Saavedra
 * @since 2023 - 2024
 * @version 1.0
 * 
 */
public class Programa {

	private static final Logger LOGGER = LoggerFactory.getLogger(Programa.class);

	private static MongoDB db = new MongoDB();
	private static InputStreamReader isr = new InputStreamReader(System.in);
	private static BufferedReader br = new BufferedReader(isr);
	
	/**
	 * Metodo Main. 
	 * Este metodo consta con un menu que da acceso a las distintas funciones del programa.
	 * Contiene los metodos: crearCampeon(), crearRegion(), consultarCampeon();, consultarRegion(), actualizarCampeon(), eliminarCampeon(), eliminarRegion(), insertarConjuntoCampeones().
	 * @author Carlos Velasco, Alvaro Aparicio, Diego Hernando, Jose Julian Saavedra
	 * @since 2023 - 2024
	 * @version 1.0
	 */
	public static void main(String[] args) {

		
			
			LOGGER.info("Se han cargado campeones");
			int numero = 0;
			
			do {
				System.out.println("1- Crear Campeon");
				System.out.println("2- Crear Region");
				System.out.println("3- Leer Campeon");
				System.out.println("4- Leer Region");
				System.out.println("5- Actualizar Campeon");
				System.out.println("6- Borrar Campeon");
				System.out.println("7- Borrar Region");
				System.out.println("8- Insertar Conjunto de campeones desde un fichero JSON");
				System.out.println("9- Salir");
				try {

					System.out.println("Introduce un numero para realizar una accion sobre la base de datos");
					numero = Integer.valueOf(br.readLine());

					switch (numero) {
					case 1:
						crearCampeon();
						break;
					case 2:
						crearRegion();
						break;
					case 3:
						consultaCampeon();
						break;
					case 4:
						consultaRegion();
						break;
					case 5:
						actualizarCampeon();
						break;
					case 6:
						eliminarCampeon();
						break;
					case 7:
						eliminarRegion();
						break;
					case 8:insertarConjuntoCampeones();
						break;
					case 9: break;
					default:
						LOGGER.warn("introduce un numero que este en el menu");
						
					}
				} catch (NumberFormatException e) {
					LOGGER.warn("Introduce NUMEROS NO LETRAS");
				} catch (Exception e) {
					LOGGER.error("Execepcion en el menu principal. " + e.getMessage());
				}
			} while (numero > 9 || numero < 9);
			try {
				br.close();
				isr.close();
			} catch (IOException e) {
				LOGGER.error("Error al cerrar el InputStreamReader o el BufferedReader");
			}
			LOGGER.info("FIN DEL PROGRAMA");

	}

	/**
	 * Metodo crearCampeon().
	 * Este metodo accede a la coleccion de campeones en la base de datos y mediante la recoleccion de datos por parte del usuario inserta el documento en la base de datos.
	 * @author Carlos Velasco, Alvaro Aparicio, Diego Hernando, Jose Julian Saavedra
	 * @since 2023 - 2024
	 * @version 1.0
	 */
	public static void crearCampeon() {
		
		MongoCollection<Document> collecion = db.getMongoDatabase().getCollection("Campeones");

		String nombre, alias, clase, carril, region;
		double vida = 0, regenVida = 0, armor = 0, mr = 0, ad = 0, atkspeed = 0;

		try {
			System.out.println("Nombre del campeon");
			nombre = br.readLine();
			System.out.println("Alias del campeon");
			alias = br.readLine();

			do {
				System.out.println("A que clase pertenece el campeon");
				String[] clases = { "Tirador", "Coloso", "Tanque", "Asesino", "Hechizero", "Apoyo" };
				for (String x : clases) {
					System.out.println(x);
				}
				clase = br.readLine();
			} while (!(clase.equalsIgnoreCase("Tirador") || clase.equalsIgnoreCase("Coloso")
					|| clase.equalsIgnoreCase("Tanque") || clase.equalsIgnoreCase("Asesino")
					|| clase.equalsIgnoreCase("Hechizero") || clase.equalsIgnoreCase("Apoyo")));

			do {
				System.out.println("Carril al que pertenece el campeon");
				String[] carriles = { "Superior", "Jungla", "Central", "Inferior", "Apoyo" };
				for (String y : carriles) {
					System.out.println(y);
				}
				carril = br.readLine();
			} while (!(carril.equalsIgnoreCase("Superior") || carril.equalsIgnoreCase("Jungla")
					|| carril.equalsIgnoreCase("Central") || carril.equalsIgnoreCase("Inferior")
					|| carril.equalsIgnoreCase("Apoyo")));
			

			do {
				System.out.println("Region a la que pertenece el campeon");
				String[] regionesFicticias = { "Demacia", "Noxus", "Piltover", "Zaun", "Freljord", "Jonia",
						"Shadow Isles", "Bandle City", "Aguasestancadas", "Targon", "El Vacio", "Desconocido" };

				for (String regionFicticia : regionesFicticias) {
					System.out.println(regionFicticia);
				}
				region = br.readLine();
			} while (!(region.equals("Demacia") || region.equals("Noxus")
					|| region.equals("Piltover") || region.equals("Zaun")
					|| region.equals("Freljord") || region.equals("Jonia")
					|| region.equals("Shadow Isles") || region.equals("Bandle City")
					|| region.equals("Aguasestancadas") || region.equals("Targon")
					|| region.equals("El Vacio") || region.equals("Desconocido")));

			boolean comprobante = false;
			do {
				try {

					System.out.println("Estadisticas base del campeon");
					System.out.println("Vida: ");
					vida = Double.valueOf(br.readLine());
					System.out.println("Regeneracion de vida:");
					regenVida = Double.valueOf(br.readLine());
					System.out.println("Armadura:");
					armor = Double.valueOf(br.readLine());
					System.out.println("Resistencia magica:");
					mr = Double.valueOf(br.readLine());
					System.out.println("Daño de ataque:");
					ad = Double.valueOf(br.readLine());
					System.out.println("Velocidad de ataque:");
					atkspeed = Double.valueOf(br.readLine());

					comprobante = true;
				} catch (NumberFormatException e) {
					LOGGER.error("Mete numeros no letras!!!");
					comprobante = false;
				}
			} while (comprobante == false);

			Document campeon = new Document("Campeon", nombre).append("Alias", alias).append("Clase", clase)
					.append("Carril", carril).append("Region", region).append("Estadisticas",
							new Document("Vida", vida).append("Regeneracion de Vida", regenVida)
									.append("Armadura", armor).append("Resistencia magica", mr)
									.append("Daño de ataque", ad).append("Velocidad de ataque", atkspeed));
			collecion.insertOne(campeon);
			
			LOGGER.info("Campeon correctamente creado");
			
		} catch (IOException e) {
			LOGGER.error("Error de E/S");
		}

	}

	/**
	 * Metodo crearRegion().
	 * Este metodo accede a la coleccion de regiones en la base de datos y mediante la recoleccion de datos por parte del usuario inserta el documento en la base de datos.
	 * @author Carlos Velasco, Alvaro Aparicio, Diego Hernando, Jose Julian Saavedra
	 * @since 2023 - 2024
	 * @version 1.0
	 */
	public static void crearRegion() {
		MongoCollection<Document> collection = db.getMongoDatabase().getCollection("Regiones");
		
		ArrayList<String> lista = new ArrayList<String>();
		String nombre;
		boolean comp=false;
		int campeones=-1;
		try {
			do {
				System.out.println("Nombre de la region");
				String[] regionesFicticias = { "Demacia", "Noxus", "Piltover", "Zaun", "Freljord", "Jonia",
						"Shadow Isles", "Bandle City", "Aguasestancadas", "Targon", "El Vacio", "Desconocido" };

				for (String regionFicticia : regionesFicticias) {
					System.out.println(regionFicticia);
				}
				nombre = br.readLine();
			} while (!(nombre.equals("Demacia") || nombre.equals("Noxus")
					|| nombre.equals("Piltover") || nombre.equals("Zaun")
					|| nombre.equals("Freljord") || nombre.equals("Jonia")
					|| nombre.equals("Shadow Isles") || nombre.equals("Bandle City")
					|| nombre.equals("Aguasestancadas") || nombre.equals("Targon")
					|| nombre.equals("El Vacio") || nombre.equals("Desconocido")));

			
			do {
				System.out.println("Campeones pertenecientes a la region");
				try {
						campeones = Integer.valueOf(br.readLine());
						comp=true;
						if(comp ==true) {
							System.out.println("Lista de Campeones");
							for (int i = 1; i <= campeones; i++) {
								System.out.println("Campeon "+i+":");
								String campeon = br.readLine();
								lista.add(campeon);
								comp=true;
							}
							comp=true;
						}else {
							System.out.println("Vuelve a introducir el numero de campeones que pertenecen a la region");
						}
						
				} catch (NumberFormatException e) {
					System.out.println("Mete numeros vuelve a introducir la cantidad de campeones");
					comp=false;
				}
			} while (comp==false);
			Document region = new Document("Region", nombre).append("Lista de Campeones", lista);
			collection.insertOne(region);
			
			LOGGER.info("Region creada con exito");
			
		} catch (IOException e) {
			LOGGER.error("Error de E/S");
		}
	}
	
	/**
	 * Metodo consultarCampeon().
	 * Este metodo accede a la coleccion de campeones en la base de datos para mostrar los detalles de un elemento determinado.
	 * En este caso la busqueda se hace midiante el nombre del elemento.
	 * @author Carlos Velasco, Alvaro Aparicio, Diego Hernando, Jose Julian Saavedra
	 * @since 2023 - 2024
	 * @version 1.0
	 */
	private static void consultaCampeon() {
		
		MongoCollection<Document> collecion = db.getMongoDatabase().getCollection("Campeones");
		
		String nombre;
		
		System.out.println("Dime un campeon para enseñarte sus estadisticas");
		
		try {
			nombre = br.readLine();
			
			FindIterable<Document> result = collecion.find(Filters.eq("Campeon", nombre));
			
			Document doc1 = result.first();
			
			if (doc1 != null) {
				for (Document doc : result) {
					leerCampeon(doc);
				}
			}
			else {
				LOGGER.warn("Campeon no encontrado");
		    }
			 
		} catch (IOException e) {
			LOGGER.error("Error de E/S");
		}
		
	}
	/**
	 * Metodo leerCampeon().
	 * Este metodo formatea la informacion recibida de la base de datos para mostrarla con un formato especifico
	 * @author Carlos Velasco, Alvaro Aparicio, Diego Hernando, Jose Julian Saavedra
	 * @since 2023 - 2024
	 * @version 1.0
	 */
	private static void leerCampeon (Document doc) {
		String campeon = doc.getString("Campeon");
		Object estadisticas = doc.get("Estadisticas");
		
		LOGGER.info("Estas son las Estadisticas del campeon " + campeon  + ": " + estadisticas.toString());
		
	}
	
	/**
	 * Metodo consultarRegion().
	 * Este metodo accede a la coleccion de regiones en la base de datos para mostrar los detalles de un elemento determinado.
	 * En este caso la busqueda se hace midiante el nombre del elemento.
	 * @author Carlos Velasco, Alvaro Aparicio, Diego Hernando, Jose Julian Saavedra
	 * @since 2023 - 2024
	 * @version 1.0
	 */
	private static void consultaRegion() {
		
		MongoCollection<Document> regiones = db.getMongoDatabase().getCollection("Regiones");
		
		String region;
		
		
		try {
			do {
				System.out.println("Dime una region para enseñarte todos los campeones que estan en su region");
				String[] regionesFicticias = { "Demacia", "Noxus", "Piltover", "Zaun", "Freljord", "Jonia",
						"Shadow Isles", "Bandle City", "Aguasestancadas", "Targon", "El Vacio", "Desconocido" };

				for (String regionFicticia : regionesFicticias) {
					System.out.println(regionFicticia);
				}
				region = br.readLine();
			}while((!(region.equals("Demacia") || region.equals("Noxus")
					|| region.equals("Piltover") || region.equals("Zaun")
					|| region.equals("Freljord") || region.equals("Jonia")
					|| region.equals("Shadow Isles") || region.equals("Bandle City")
					|| region.equals("Aguasestancadas") || region.equals("Targon")
					|| region.equals("El Vacio") || region.equals("Desconocido"))));
			

			FindIterable<Document> regionEncontrada=regiones.find(Filters.eq("Region", region));
			
			MongoCursor<Document> mongoCursor=regionEncontrada.iterator();
			
			Document doc1 = regionEncontrada.first();
			if (doc1 != regionEncontrada.first()) {
				while(mongoCursor.hasNext()) {
			 		 leerRegion(mongoCursor.next());
			 	 }
			}
			else { 
				LOGGER.warn("Esta region no se ha creado en la base de datos");
			}
			
		} catch (IOException e) {
			LOGGER.error("Error de E/S");
		}
		
	}
	
	private static void leerRegion (Document doc) {
		String region = doc.getString("Region");
		Object campeones = doc.get("Lista de Campeones");
		
		LOGGER.info("Estas son los campeones que hay en la region " + region  + ": " + campeones.toString());
		
	}

	/**
	 * Metodo actualizarCampeon().
	 * Este metodo accede a la coleccion de campeones en la base de datos y mediante un menu permite modificar datos especificos de un elemento concreto.
	 * @author Carlos Velasco, Alvaro Aparicio, Diego Hernando, Jose Julian Saavedra
	 * @since 2023 - 2024
	 * @version 1.0
	 */
	private static void actualizarCampeon() {
		MongoCollection<Document> collection = db.getMongoDatabase().getCollection("Campeones");
		
		String champ;
		boolean comp = false;// ES false al principio, si es true siginifica que se ha encontrado el nombre
								// del campeon en la lista
		int num = 0;
		double nuevaStat;
		ArrayList<String> campeones = new ArrayList<String>();
		// Mostrar los campeones
		try {
			FindIterable<Document> findIterable = collection.find();
			Iterator<Document> iterator = findIterable.iterator();
			System.out.println("Campeones");
			while (iterator.hasNext()) {
				Document document = iterator.next();
				System.out.println(document.get("Campeon"));
				campeones.add((String) document.get("Campeon"));
			}
			System.out.println("Introduce el nombre del campeon que quieras actualizar");
			do {

				champ = br.readLine();
				for (int i = 0; i < campeones.size(); i++) {
					if (champ.equals(campeones.get(i))) {
						comp = true;
						break;
					}
				}
			} while (comp == false);
			do {
				System.out.println("Actualiza las estadisticas del campeon: " + champ);
				System.out.println("1-Vida");
				System.out.println("2-Regeneracion de vida");
				System.out.println("3-Armadura");
				System.out.println("4-Resistencia magia");
				System.out.println("5-Dano de ataque");
				System.out.println("6-Velocidad de ataque");
				System.out.println("7-Salir del menu");
				try {

					num = Integer.valueOf(br.readLine());
					switch (num) {
					case 1:
						System.out.println("Introduce la cantidad de vida que tendra:");
						nuevaStat = Double.valueOf(br.readLine());
						collection.updateOne(Filters.eq("Campeon", champ), 
								Updates.set("Estadisticas.Vida", nuevaStat));
						break;
					case 2:
						System.out.println("Introduce la cantidad de regeneracion de vida que tendra:");
						nuevaStat = Double.valueOf(br.readLine());
						collection.updateOne(Filters.eq("Campeon", champ),
								Updates.set("Estadisticas.Regeneracion de Vida", nuevaStat));
						break;
					case 3:
						System.out.println("Introduce la cantidad de armadura que tendra:");
						nuevaStat = Double.valueOf(br.readLine());
						collection.updateOne(Filters.eq("Campeon", champ), 
								Updates.set("Estadisticas.Armadura", nuevaStat));
						break;
					case 4:
						System.out.println("Introduce la cantidad de resistencia magica que tendra:");
						nuevaStat = Double.valueOf(br.readLine());
						collection.updateOne(Filters.eq("Campeon", champ),
								Updates.set("Estadisticas.Resistencia magica", nuevaStat));
						break;
					case 5:
						System.out.println("Introduce la cantidad de daño de ataque que tendra:");
						nuevaStat = Double.valueOf(br.readLine());
						collection.updateOne(Filters.eq("Campeon", champ),
								Updates.set("Estadisticas.Daño de ataque", nuevaStat));
						break;
					case 6:
						System.out.println("Introduce la cantidad de velocidad de ataque que tendra:");
						nuevaStat = Double.valueOf(br.readLine());
						collection.updateOne(Filters.eq("Campeon", champ),
								Updates.set("Estadisticas.Velocidad de ataque", nuevaStat));
						break;
					case 7:
						LOGGER.info("Has salido del menu y esta es la informacion del campeon actualizado: ");
						break;
					default:
						LOGGER.warn("Introduce una opcion del menu");
					}
				} catch (NumberFormatException e) {
					LOGGER.error("Introduce un numero no letras");
				}
			} while (num != 7);
			Document nuevosDatosCampeon = collection.find(Filters.eq("Campeon", champ)).first();
			System.out.println(nuevosDatosCampeon);
		} catch (IOException e) { 
			LOGGER.error("Error de E/S");
		}
	}

	/**
	 * Metodo eliminarCampeon().
	 * Este metodo accede a la coleccion de campeones en la base de datos para proceder a la eliminacion de un elemento especifico.
	 * En este caso la busqueda se hace midiante el nombre del elemento.
	 * @author Carlos Velasco, Alvaro Aparicio, Diego Hernando, Jose Julian Saavedra
	 * @since 2023 - 2024
	 * @version 1.0
	 */
	public static boolean eliminarCampeon() {
		MongoCollection<Document> collection = db.getMongoDatabase().getCollection("Campeones");
		
		String nombre;

		try {
			System.out.println("Indique el nombre del campeon que desea eliminar: ");
			nombre = br.readLine();

			DeleteResult deleteResult = collection.deleteMany(Filters.eq("Campeon", nombre));

			if (deleteResult.getDeletedCount() > 0) {
				LOGGER.info("Campeon " + nombre + " eliminado");
			} else {
				LOGGER.warn("Campeon " + nombre + " no encontrado");
			}
		} catch (IOException e) {
			LOGGER.error("Error de E/S");
		}
		return true;
	}

	/**
	 * Metodo eliminarRegion().
	 * Este metodo accede a la coleccion de regiones en la base de datos para proceder a la eliminacion de un elemento especifico.
	 * En este caso la busqueda se hace midiante el nombre del elemento.
	 * @author Carlos Velasco, Alvaro Aparicio, Diego Hernando, Jose Julian Saavedra
	 * @since 2023 - 2024
	 * @version 1.0
	 */
	public static boolean eliminarRegion() {
		MongoCollection<Document> collection = db.getMongoDatabase().getCollection("Regiones");
		
		String nombre;

		try {
			System.out.println("Indique el nombre de la Region que desea eliminar: ");
			nombre = br.readLine();

			DeleteResult deleteResult = collection.deleteMany(Filters.eq("Region", nombre));

			if (deleteResult.getDeletedCount() > 0) {
				LOGGER.info("Region " + nombre + " eliminada");
			} else {
				LOGGER.warn("Region " + nombre + " no encontrada");
			}

		} catch (IOException e) {
			LOGGER.error("Error de E/S");
		}
		return true;
	}
	
	/**
	 * Metodo insertarConjuntoCampeones().
	 * Este metodo es un añadido para la prueba del programa. Se trata de un metodo que carga datos de ejemplo en la base de datos para hacer pruebas
	 * @author Carlos Velasco, Alvaro Aparicio, Diego Hernando, Jose Julian Saavedra
	 * @since 2023 - 2024
	 * @version 1.0
	 */
	private static void insertarConjuntoCampeones() {
		String jsonString = JSON.leerContenidoArchivo("Campeones.json");
		JSONArray campeonesArray = new JSONArray(jsonString);

		// Procesa cada campeón
		List<Document> campeonesList = new ArrayList<>();
		for (int i = 0; i < campeonesArray.length(); i++) {
			JSONObject campeon = campeonesArray.getJSONObject(i);
			// Procesa y guarda la información del campeón en MongoDB
			Document campeonDocument = JSON.crearDocumentoCampeon(campeon);
			campeonesList.add(campeonDocument);
		}
		MongoCollection<Document> campeonesCollection = db.getMongoDatabase().getCollection("Campeones");
		campeonesCollection.insertMany(campeonesList);
		LOGGER.info("Se ha insertado los campeones a la Base de datos");
	}

}
