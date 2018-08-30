package org.eclipse.controller.commentaire;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.model.Commentaire;
import org.eclipse.model.Cours;
import org.eclipse.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Servlet implementation class AddCommentServlet
 */
@WebServlet("/add-comment")
public class AddCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCommentServlet() {
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
    		session.setAttribute("AlertErrorMsg", "Vous n'êtes pas autorisé(e) à faire cette action !");
			response.sendRedirect("home");
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean error = false;
		HttpSession session = request.getSession();
		
		/*----- Preparation de la connexion -----*/
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		Session sessionFact = sFactory.openSession();
		Transaction tx = sessionFact.beginTransaction();
		
		/*----- Recupération des inputs -----*/
		String content = request.getParameter("content");
		
		System.out.println(content + " / ");
		
		/*----- Test confirmité des mots de passe -----*/
		if(content==null) {
			request.setAttribute("inputError", "Veuillez remplir le champs commentaire");
			doGet(request, response);
		} else {
			/*----- Validation du formulaire -----*/
			try {
				verifChaine(content);
			} catch (Exception e) {
				request.setAttribute("contentIncorrect", "Le commentaire" + e.getMessage() );
				error = true;
			} 
			if(error) {
				/*----- Recuperation des inputs en cas d'erreur + redirection-----*/
				request.setAttribute("newContent", content);
				System.out.println("Error add commentaire");
				this.getServletContext().getRequestDispatcher("/WEB-INF/CoursViews/showCours.jsp").forward(request, response);
			} else {	
				Commentaire commentaire = new Commentaire();
				commentaire.setContenuCommentaire(content);
				commentaire.setDateCommentaire(new Date());
				User user = (User) session.getAttribute("user_auth");
				Cours cours = (Cours) session.getAttribute("cours");
				commentaire.setUser(user);
				commentaire.setCours(cours);
				sessionFact.save(commentaire);
				tx.commit();
				/*----- Fermeture connexion + Redirection -----*/
				sessionFact.close();
				sFactory.close();
				session.setAttribute("AlertSuccessMsg", "Commentaire bien enregistrée");
				response.sendRedirect("cours?idC="+cours.getIdCours());
				
			}
		}
	}
	public boolean verifChaine(String s) throws Exception {
		if (s.length() < 2)
			throw new Exception(" doit comporter au moins deux caracteres");
		return true;
	}
	

}
