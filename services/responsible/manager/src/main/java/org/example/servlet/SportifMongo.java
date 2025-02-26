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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import org.example.mongoPojo.Adresse;
import org.example.mongoPojo.Federation;
import org.example.mongoPojo.Sportif;


import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class SportifMongo
 */
public class SportifMongo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//MongoCollection<Federation> federations;
	MongoCollection<Sportif> sportifs;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SportifMongo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String operation = request.getParameter("operation");
		if (operation.equals("listeSportifMongo")) {
			CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
			CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

			ConnectionString connectionString = new ConnectionString("mongodb://obiwan.univ-brest.fr:27017");
			MongoClient mongoClient = MongoClients.create(connectionString);
			MongoDatabase database = mongoClient.getDatabase("e22102349").withCodecRegistry(pojoCodecRegistry);
			System.out.println("Connexion �tablie\n");

			this.sportifs = database.getCollection("sportifs", Sportif.class);

			request.setAttribute("sportifs", this.getListeSportifs());

			getServletConfig().getServletContext().getRequestDispatcher("/afficheSportifsMongo.jsp")
				.forward(request, response);
		}
	}
	
	public List<Sportif> getListeSportifs(){
		List<Sportif> sportifs = new ArrayList<>();
		
		for (Sportif sportif : this.sportifs.find()) {
			System.out.println(sportif);
			sportifs.add(sportif);
		}
		
		return sportifs;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
