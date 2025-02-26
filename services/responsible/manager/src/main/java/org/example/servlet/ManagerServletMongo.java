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

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.example.mongoPojo.Manager;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class ManagerServletMongo
 */
public class ManagerServletMongo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	MongoCollection<Manager> manager;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerServletMongo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
			CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
			CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

			ConnectionString connectionString = new ConnectionString("mongodb://obiwan.univ-brest.fr:27017");
			MongoClient mongoClient = MongoClients.create(connectionString);
			MongoDatabase database = mongoClient.getDatabase("e22102349").withCodecRegistry(pojoCodecRegistry);
			System.out.println("Connexion établie\n");
			
			this.manager = database.getCollection("Manager", Manager.class);
			
			request.setAttribute("Manager", this.getListeManagers());

			getServletConfig().getServletContext().getRequestDispatcher("/afficheManagersMongo.jsp")
				.forward(request, response);
		
	}
	
	public List<Manager> getListeManagers(){
		List<Manager> managers = new ArrayList<>();
		
		for (Manager mana : this.manager.find()) {
			System.out.println(mana);
			managers.add(mana);
		}
		
		return managers;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
