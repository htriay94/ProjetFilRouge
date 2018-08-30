package org.eclipse.controller.groupe;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.model.Cours;
import org.eclipse.model.Groupe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Servlet implementation class ShowCoursServlet
 */
@WebServlet("/liste-groupes")
public class ShowListeGroupe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowListeGroupe() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();		
		/*----- Check autorisation de la connexion -----*/
		if(session.getAttribute("connected") == null)
			/*----- Redirection vers la vue de login -----*/
			this.getServletContext().getRequestDispatcher("/WEB-INF/Authentification/formAuth.jsp").forward(request, response);
    	else {
    		/*----- Configuration connexion db -----*/
    		Configuration configuration = new Configuration().configure();
    		SessionFactory sFactory = configuration.buildSessionFactory();
    		Session sessionFact = sFactory.openSession();
    		/*----- Requête db liste cours d'une matiere -----*/
    		Query query = sessionFact.createNamedQuery("Groupe.findAll");
    		List<Groupe> groupes = (List<Groupe>) query.getResultList();
    		
    		/*----- Fermeture connexion db -----*/
    		sessionFact.close();
    		sFactory.close();
    		
    		/*----- Préparation des variables ------*/
    		session.removeAttribute("groupes");
    		request.setAttribute("groupes",groupes);
    		
    		/*----- Redirection vers la vue -----*/
    		this.getServletContext().getRequestDispatcher("/WEB-INF/GroupeViews/showListeGroupe.jsp").forward(request, response);
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
