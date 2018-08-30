package org.eclipse.controller.groupe;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.model.Groupe;
import org.eclipse.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Servlet implementation class LinkUserToGroupe
 */
@WebServlet("/linkGroupeUser")
public class LinkUserToGroupe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LinkUserToGroupe() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();		
		if(session.getAttribute("connected") == null)
			this.getServletContext().getRequestDispatcher("/WEB-INF/Authentification/formAuth.jsp").forward(request, response);
    	else {
    		/*--- Verification des droits ----*/
    		User user_auth = (User) session.getAttribute("user_auth");
    		if(user_auth.getStatut().equals("admin")) {
    			/*----- Preparation de la connexion -----*/
    			Configuration configuration = new Configuration().configure();
    			SessionFactory sFactory = configuration.buildSessionFactory();
    			Session sessionFact = sFactory.openSession();
    			Groupe groupe = (Groupe) session.getAttribute("groupe");
    			Query query = sessionFact.createNamedQuery("User.findByNotGroupe");
    			//query.setParameter("idGroupe", groupe.getIdGroupe());
        		List<User> users = (List<User>) query.getResultList();
        		request.setAttribute("users",users);
        		this.getServletContext().getRequestDispatcher("/WEB-INF/GroupeViews/formLinkUserGroupe.jsp").forward(request, response);
    		} else {
    			session.setAttribute("AlertErrorMsg", "Vous n'êtes pas autorisé(e) à faire cette action !");
    			response.sendRedirect("home");
    		}
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		/*----- Preparation de la connexion -----*/
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		Session sessionFact = sFactory.openSession();
		Transaction tx = sessionFact.beginTransaction();
		
		/*----- Recupération des inputs -----*/
		String[] idUsers =  request.getParameterValues("idUsers");
		Groupe groupe = (Groupe) session.getAttribute("groupe");
		
		for(String idUser : idUsers) {
			System.out.println(idUser + " / ");
			User user=(User)sessionFact.get(User.class, Integer.parseInt(idUser));
			groupe.addUser(user);
		}
		
		sessionFact.update(groupe);
		tx.commit();
		/*----- Fermeture connexion + Redirection -----*/
		sessionFact.close();
		sFactory.close();
		session.setAttribute("AlertSuccessMsg", "Matière bien modifiée");
		session.removeAttribute("groupe");
		
		response.sendRedirect("groupe?idGrp="+groupe.getIdGroupe());
	}
}
