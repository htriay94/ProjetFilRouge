package org.eclipse.controller.matiere;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.model.Matiere;
import org.eclipse.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Servlet implementation class AddMatiereServlet
 */
@WebServlet("/edit-matiere")
public class EditMatiereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditMatiereServlet() {
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
    			int idMat = Integer.parseInt(request.getParameter("idMat"));
    			/*----- Preparation de la connexion -----*/
    			Configuration configuration = new Configuration().configure();
    			SessionFactory sFactory = configuration.buildSessionFactory();
    			Session sessionFact = sFactory.openSession();
    			Matiere matiere = (Matiere) sessionFact.get(Matiere.class, idMat);
    			
    			session.setAttribute("matiere",matiere);
    			request.setAttribute("type","Modifier");
        		request.setAttribute("action","edit-matiere");
        		this.getServletContext().getRequestDispatcher("/WEB-INF/MatiereViews/formMatiere.jsp").forward(request, response);
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
		
		System.out.println(nom + " / ");
		
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
				System.out.println("Error add matière");
				doGet(request, response);
			} else {	
				Matiere matiere = (Matiere) session.getAttribute("matiere");
				matiere.setNomMatiere(nom);
				sessionFact.merge(matiere);
				tx.commit();
				/*----- Fermeture connexion + Redirection -----*/
				sessionFact.close();
				sFactory.close();
				session.setAttribute("AlertSuccessMsg", "Matière bien modifiée");
				
				int idMat = matiere.getIdMatiere();
				String nomMat = matiere.getNomMatiere();
				session.removeAttribute("matiere");
				
				response.sendRedirect("liste-cours?id="+idMat+"&nom="+nomMat);
				
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
