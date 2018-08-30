package org.eclipse.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.model.Cours;
import org.eclipse.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet("/calendar")
public class CalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();	
		User user_auth = (User) session.getAttribute("user_auth");
		/*----- Check autorisation de la connexion -----*/
		if(session.getAttribute("connected") == null)
			/*----- Redirection vers la vue de login -----*/
			this.getServletContext().getRequestDispatcher("/WEB-INF/Authentification/formAuth.jsp").forward(request, response);
    	else {
    		/*----- Configuration connexion db -----*/
    		Configuration configuration = new Configuration().configure();
    		SessionFactory sFactory = configuration.buildSessionFactory();
    		Session sessionFact = sFactory.openSession();
    		
    		/*----- Requête db cours de l'utilisateur co -----*/
    		List<Cours> cours = new ArrayList<Cours>();
    		if(!user_auth.getStatut().equals("admin")) {
	    		cours = user_auth.getGroupe().getCours();
    		} else {
    			/*----- Cas admin on a tous les cours -----*/
        		Query query = sessionFact.createNamedQuery("Cours.findAll");
        		cours = (List<Cours>) query.getResultList();
    		}
    		/*----- Fermeture connexion db -----*/
    		sessionFact.close();
    		sFactory.close();
    		
    		/*----- Préparation des variables ------*/
    		session.removeAttribute("listeCours");
    		session.setAttribute("listeCours",cours);
    		
    		this.getServletContext().getRequestDispatcher("/WEB-INF/CoursViews/calendar.jsp").forward(request, response);
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
