package org.eclipse.controller.cours;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.model.Cours;
import org.eclipse.model.Matiere;
import org.eclipse.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Servlet implementation class AddCourServlet
 */
@WebServlet("/remove-cours")
public class RemoveCoursServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveCoursServlet() {
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
    			Transaction tx = sessionFact.beginTransaction();
    			/*----- Recuperation cours -------*/
    			Cours cours = (Cours) sessionFact.get(Cours.class, Integer.parseInt(request.getParameter("idC")));
    			String nomMat = cours.getMatiere().getNomMatiere();
    			/*----- Suppression cours -------*/
    			sessionFact.remove(cours);
    			/*----- commit -------*/
    			tx.commit();
    			/*----- Fermeture connexion + Redirection -----*/
    			sessionFact.close();
    			sFactory.close();
    			response.sendRedirect("cours?id="+request.getParameter("idMat")+"&nom="+nomMat);
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
		Boolean error = false;
		HttpSession session = request.getSession();
		/*----- Preparation de la connexion -----*/
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		Session sessionFact = sFactory.openSession();
		Transaction tx = sessionFact.beginTransaction();
		
		/*----- Recupération des inputs -----*/
		String nom = request.getParameter("nom");
		String support = request.getParameter("support");
		int id = Integer.parseInt(request.getParameter("id"));
		
		System.out.println(nom + " / " + support);
		
		/*----- Test confirmité des mots de passe -----*/
		if(nom==null) {
			request.setAttribute("inputError", "Veuillez remplir le champs nom");
			doGet(request, response);
		} else {
			/*----- Validation du formulaire -----*/
			try {
				verifChaine(nom);
			} catch (Exception e) {
				request.setAttribute("nomIncorrect", "Le nom" + e.getMessage() );
				error = true;
			} 
			if(error) {
				/*----- Recuperation des inputs en cas d'erreur + redirection-----*/
				request.setAttribute("newNom", nom);
				System.out.println("Error add cours");
				doGet(request, response);
			} else {	
				Cours cours = new Cours();
				cours.setNomCours(nom);
				cours.setDateCours(new Date());
				Matiere matiere = (Matiere) sessionFact.get(Matiere.class, id);
				cours.setMatiere(matiere);
				if(support!=null)
					cours.setSupport(support);
				sessionFact.save(cours);
				tx.commit();
				session.setAttribute("AlertSuccessMsg", "Cours bien enregistré");
				response.sendRedirect("cours?id="+id+"&nom="+nom);
			}
		}
	}
	public boolean verifChaine(String s) throws Exception {
		char c = s.charAt(0);
		if (s.length() < 2)
			throw new Exception(" doit comporter au moins deux caracteres");
		if(!(c >= 'A' && c <= 'Z'))
			throw new Exception(" chaine doit commencer par une lettre en majuscule");
		for(int i = 0 ; i< s.length(); i++) {
			c = s.charAt(i);
			if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && (c != '-') && !Character.isWhitespace(c))
				throw new Exception(" ne peut contenir que des lettres, tirés et espaces");
		}
		return true;
	}
}
