package org.eclipse.controller.authentification;

import java.io.IOException;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Servlet implementation class CheckDoublonEmail
 */
@WebServlet("/CheckDoublonValueUser")
public class CheckDoublonValueUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckDoublonValueUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*-------- Préparation de la réponse --------*/
		String color = "green";
		String resultat = "Disponible";
		String id = "emailDispo";
		response.setContentType("text/html;charset=UTF-8");
		String value = request.getParameter("value");
		String column = request.getParameter("column");
		
		/*-------- Préparation de la connexion --------*/
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		Session sessionFact = sFactory.openSession();
		
		/*-------- Requête --------*/
		Query query = sessionFact.createNamedQuery("User.findByLogin");
		if(column.equals("login")) {
			query.setParameter("login", value);
			id = "loginDispo";
		} else if (column.equals("email")){
			query = sessionFact.createNamedQuery("User.findByEmail");
			query.setParameter("email", value);
			id = "emailDispo";
		}
		
		/*----- Recupérer l'erreur si l'user décrit de ces inputs n'existe pas -----*/
		User user = (User) query.getResultList().stream().findFirst().orElse(null);
		if(user != null) {
			color = "red";
			resultat = "Indisponible";
		}
		response.getWriter().write("<p id='" + id + "' style='color:" + color + ";'>"+resultat+"</p>");	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
