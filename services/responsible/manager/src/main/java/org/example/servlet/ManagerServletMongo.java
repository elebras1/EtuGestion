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

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.example.mongoPojo.Manager;

import com.mongodb.ConnectionString;
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
	
	private int maxId;

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

		// ConnectionString connectionString = new
		// ConnectionString("mongodb://obiwan.univ-brest.fr:27017");
		ConnectionString connectionString = new ConnectionString("mongodb://127.0.0.1:27017");
		this.mongoClient = MongoClients.create(connectionString);
		MongoDatabase database = mongoClient.getDatabase("e22102349").withCodecRegistry(pojoCodecRegistry);
		System.out.println("Connexion établie\n");

		this.managerDB = database.getCollection("Manager", Manager.class);
		
		maxId = this.getMaxIdManagerMongo();
	}

	public List<Manager> getListeManagers() {
		List<Manager> managers = new ArrayList<>();

		for (Manager manager : this.managerDB.find()) {
			managers.add(manager);
		}

		return managers;
	}
	
	public int getMaxIdManagerMongo() {
		List<Manager> managers = this.getListeManagers();
		
		int maxId = 0, tmp = 0;
		
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

			int id = Integer.parseInt(pathInfo);
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

		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		
		// Id incrémenté pour la création d'un nouveau responsable
		maxId++;
		
		Manager manager = new Manager(maxId, nom, prenom, email);
		
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
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo != null && pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}

		if (pathInfo != null && !pathInfo.isEmpty()) {
			try {
				int id = Integer.parseInt(pathInfo);

				long delete = this.managerDB.deleteOne(Filters.eq("id", id)).getDeletedCount();

				if (delete > 0) {
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json");
					response.getWriter().write("{\"description\":\"Responsable supprimé avec succès.\"}");
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					response.setContentType("application/json");
					response.getWriter().write("{\"description\":\"Responsable non trouvé.\"}");
				}
			} catch (NumberFormatException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.setContentType("application/json");
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

	/*
	 * /**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 * response)
	 */
	/*
	 * protected void doPost(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { // TODO Auto-generated
	 * method stub doGet(request, response); }
	 */
}
