package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.example.mongoPojo.Manager;

import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * Servlet implementation class ManagerServletMongo
 */
@WebServlet("/managers/*")
public class ManagerServletMongo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MongoClient mongoClient;
	private MongoCollection<Manager> managerDB;

	private long maxId;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManagerServletMongo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

		ConnectionString connectionString = new ConnectionString("mongodb://mongodb:27017");
		
		try {
            this.mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase("e22102349").withCodecRegistry(pojoCodecRegistry);

            // vérifie si bien connecté à la base
            mongoClient.getDatabase("admin").runCommand(new Document("ping", 1));
            System.out.println("Connexion MongoDB réussie");

            this.managerDB = database.getCollection("Manager", Manager.class);

            maxId = this.getMaxIdManagerMongo();
        } catch (MongoException e) {
            System.out.println("Erreur de connexion à MongoDB : " + e.getMessage());
        }
	}

	public List<Manager> getListeManagers() {
		List<Manager> managers = new ArrayList<>();

		for (Manager manager : this.managerDB.find()) {
			managers.add(manager);
		}

		return managers;
	}

	public long getMaxIdManagerMongo() {
		List<Manager> managers = this.getListeManagers();

		long maxId = 0, tmp = 0;

		for (Manager manager : managers) {
			tmp = manager.getId();
			if (maxId < tmp) {
				maxId = tmp;
			}
		}
		System.out.println(maxId);
		return maxId;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo != null && pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		if (pathInfo != null && !pathInfo.isEmpty()) {

			long id = Integer.parseInt(pathInfo);
			Manager manager = this.managerDB.find(Filters.eq("id", id)).first();

			if (manager != null) {
				ObjectMapper objectMap = new ObjectMapper();
				String json = objectMap.writeValueAsString(manager);
				response.getWriter().write(json);
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.getWriter().write("{\"description\":\"Responsable non trouvé.\"}");
			}
		} else {
			List<Manager> managers = this.getListeManagers();
			ObjectMapper objectMap = new ObjectMapper();
			String json = objectMap.writeValueAsString(managers);
			response.getWriter().write(json);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Id incrémenté pour la création d'un nouveau responsable
		maxId++;

		ObjectMapper objectMap = new ObjectMapper();
		Manager manager = objectMap.readValue(request.getReader(), Manager.class);
		manager.setId(maxId);

		try {
			if (manager.getId() <= 0 || manager.getEmail() == null || manager.getNom() == null
					|| manager.getPrenom() == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"description\":\"Requête invalide.\"}");
				return;
			}

			this.managerDB.insertOne(manager);

			response.setStatus(HttpServletResponse.SC_CREATED);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("{\"description\":\"Responsable créé avec succès\"}");
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"description\":\"Erreur création du manager.\"}");
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo != null && pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		Manager manager = objectMapper.readValue(request.getReader(), Manager.class);

		if (pathInfo != null && !pathInfo.isEmpty()) {

			try {
				long id = Integer.parseInt(pathInfo);

				// document qui permet de stocker les données à modifier
				Document update = new Document();

				if (manager.getNom() != null) {
					update.append("nom", manager.getNom());
				}
				if (manager.getPrenom() != null) {
					update.append("prenom", manager.getPrenom());
				}
				if (manager.getEmail() != null) {
					update.append("email", manager.getEmail());
				}

				if (!update.isEmpty()) {
					long modif = this.managerDB.updateOne(Filters.eq("id", id), new Document("$set", update))
							.getModifiedCount();

					if (modif > 0) {
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write("{\"description\":\"La responsable a été mis à jour avec succès.\"}");
					} else {
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter()
								.write("{\"description\":\"Responsable non trouvé ou aucune modification apportée.\"}");
					}
				}
			} catch (NumberFormatException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("{\"description\":\"L'id n'est pas valide.\"}");
			}
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo != null && pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}

		if (pathInfo != null && !pathInfo.isEmpty()) {
			try {
				long id = Integer.parseInt(pathInfo);

				long delete = this.managerDB.deleteOne(Filters.eq("id", id)).getDeletedCount();

				if (delete > 0) {
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("{\"description\":\"Responsable supprimé avec succès.\"}");
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("{\"description\":\"Responsable non trouvé.\"}");
				}
			} catch (NumberFormatException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("{\"description\":\"L'id n'est pas valide, doit être un entier.\"}");
			}
		}
	}

	@Override
	public void destroy() {
		if (mongoClient != null) {
			mongoClient.close();
			System.out.println("Connexion MongoDB fermée.");
		}
		super.destroy();
	}
}
