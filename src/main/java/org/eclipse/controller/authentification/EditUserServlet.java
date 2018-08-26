package org.eclipse.controller.authentification;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Servlet implementation class PersonneServlet
 */
@WebServlet("/edit-user")
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();		
		if(session.getAttribute("connected") == null)
			this.getServletContext().getRequestDispatcher("/WEB-INF/Authentification/formAuth.jsp").forward(request, response);
    	else {
    		
    	}
		request.setAttribute("type","Modifier");
		request.setAttribute("action","edit-user");
		this.getServletContext().getRequestDispatcher("/WEB-INF/Authentification/formUser.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*----- Preparation de la connexion -----*/
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		Session sessionFact = sFactory.openSession();
		Transaction tx = sessionFact.beginTransaction();
		HttpSession session = request.getSession();
		
		/*----- Recupération des inputs -----*/
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String mdp = request.getParameter("mdp");
		String confirmMdp = request.getParameter("confirmMdp");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String statut = request.getParameter("statut");
		Boolean error = false;
		System.out.println(" / " + mdp + " / " + confirmMdp + " / " + nom + " / " + prenom + " / " + email + " / " + tel);

		/*----- Test confirmité des mots de passe -----*/
		if(mdp == null || confirmMdp == null || nom == null || prenom == null || email == null || tel == null ) {
			request.setAttribute("mdpError", "Veuillez remplir tous les champs");
			doGet(request, response);
		} else {
			if(!mdp.equals(confirmMdp)) {
				request.setAttribute("mdpError", "Les mots de passe ne correspondent pas");
				error = true;
			}
		}
		/*----- Validation du formulaire -----*/
		try {
			verifChaine(nom);
		} catch (Exception e) {
			request.setAttribute("nomIncorrect", "Le nom" + e.getMessage() );
			error = true;
		} 
		try {
			verifChaine(prenom);
		} catch (Exception e) {
			request.setAttribute("prenomIncorrect", "Le prénom" + e.getMessage() );
			error = true;
		}
		if(error) {
			/*----- Recuperation des inputs en cas d'erreur + redirection-----*/
			request.setAttribute("newNom", nom);
			request.setAttribute("newPrenom",prenom);
			request.setAttribute("newEmail", email);
			request.setAttribute("newTel",tel);
			System.out.println("Error register");
			doGet(request, response);
		} else {
			User user_auth = (User) session.getAttribute("user_auth");
			user_auth.setMotDePasse(mdp);
			user_auth.setNomUser(nom);
			user_auth.setPrenomUser(prenom);
			user_auth.setTelephone(tel);
			user_auth.setEmail(email);
			user_auth.setStatut(statut);
			
			session.setAttribute("msgMenu", "Bienvenue "+ user_auth.getPrenomUser() + " " + user_auth.getNomUser());
			session.setAttribute("user_auth",user_auth);
			
			sessionFact.flush();
			tx.commit();
			session.setAttribute("AlertSuccessMsg", "Les modifications ont été prises en compte");
			response.sendRedirect("home");
		}

	}
	
	public boolean verifChaine(String s) throws Exception {
		char c = s.charAt(0);
		if (s.length() < 2)
			throw new Exception("La chaine doit comporter au moins deux caracteres");
		if(!(c >= 'A' && c <= 'Z'))
			throw new Exception("La chaine doit commencer par une lettre en majuscule");
		for(int i = 0 ; i< s.length(); i++) {
			c = s.charAt(i);
			if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && (c != '-') && !Character.isWhitespace(c))
				throw new Exception("La chaine ne peut contenir que des lettres, tirés et espaces");
		}
		return true;
	}
}
