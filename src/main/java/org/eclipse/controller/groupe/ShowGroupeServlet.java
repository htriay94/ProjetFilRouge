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

import org.eclipse.model.Commentaire;
import org.eclipse.model.Cours;
import org.eclipse.model.Groupe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Servlet implementation class ShowCoursServlet
 */
@WebServlet("/groupe")
public class ShowGroupeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowGroupeServlet() {
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
    		/*----- Requete recup groupe by idgrp -----*/
    		Groupe groupe = (Groupe) sessionFact.get(Groupe.class, Integer.parseInt(request.getParameter("idGrp")));
    		/*----- Fermeture connexion db -----*/
    		sessionFact.close();
    		sFactory.close();
    		
    		session.removeAttribute("groupe");
    		/*----- Préparation des variables ------*/
    		session.setAttribute("groupe",groupe);
    		
    		/*----- Redirection vers la vue -----*/
    		this.getServletContext().getRequestDispatcher("/WEB-INF/GroupeViews/showGroupe.jsp").forward(request, response);
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
