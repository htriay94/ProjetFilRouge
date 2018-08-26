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
@WebServlet("/add-cours")
public class AddCoursServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCoursServlet() {
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
    		if(user_auth.getStatut().equals("admin") || user_auth.getStatut().equals("formateur")) {
    			request.setAttribute("type","Créer");
    			//request.setAttribute("id",id);
        		request.setAttribute("action","add-cours");
        		this.getServletContext().getRequestDispatcher("/WEB-INF/CoursViews/formCours.jsp").forward(request, response);
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
		int idMat = Integer.parseInt(request.getParameter("idMat"));
		
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
				Matiere matiere = (Matiere) sessionFact.get(Matiere.class, idMat);
				cours.setMatiere(matiere);
				if(support!=null)
					cours.setSupport(support);
				sessionFact.save(cours);
				tx.commit();
				/*----- Fermeture connexion + Redirection -----*/
				sessionFact.close();
				sFactory.close();
				session.setAttribute("AlertSuccessMsg", "Cours bien enregistré");
				response.sendRedirect("cours?id="+idMat+"&nom="+matiere.getNomMatiere());
				
			}
		}
	}
	public boolean verifChaine(String s) throws Exception {
		char c = s.charAt(0);
		if (s.length() < 2)
			throw new Exception(" doit comporter au moins deux caracteres");
		if(!(c >= 'A' && c <= 'Z'))
			throw new Exception(" chaine doit commencer par une lettre en majuscule");
		return true;
	}
}
