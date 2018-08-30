package org.eclipse.controller.authentification;

import java.io.IOException;
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
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*----- Preparation de la connexion pour charger les groupes de formation + perparation des variables + renvoie vers vue formUser -----*/
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		Session sessionFact = sFactory.openSession();
		HttpSession session = request.getSession();
		
		Query query = sessionFact.createNamedQuery("Groupe.findAll");
		List<Groupe> groupes = (List<Groupe>) query.getResultList();
		
		request.setAttribute("groupes",groupes);
		request.setAttribute("type","Créer");
		request.setAttribute("action","register");
		session.setAttribute("user_auth", null);
		this.getServletContext().getRequestDispatcher("/WEB-INF/Authentification/formUser.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean error = false;
		HttpSession session = request.getSession();
		/*----- Preparation de la connexion -----*/
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		Session sessionFact = sFactory.openSession();
		Transaction tx = sessionFact.beginTransaction();
		
		/*----- Recupération des inputs -----*/
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String login = request.getParameter("login");
		String mdp = request.getParameter("mdp");
		String confirmMdp = request.getParameter("confirmMdp");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String idGroupe = request.getParameter("idGroupe");
		String statut = request.getParameter("statut");
		
		System.out.println(login + " / " + mdp + " / " + confirmMdp + " / " + nom + " / " + prenom + " / " + email + " / " + tel);
		
		/*----- Test confirmité des mots de passe -----*/
		if(login==null || mdp == null || confirmMdp == null || nom == null || prenom == null || email == null || tel == null ) {
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
		try {
			veriftel(tel);
		} catch (Exception e) {
			request.setAttribute("telIncorrect", e.getMessage() );
			error = true;
		}
		if(error) {
			/*----- Recuperation des inputs en cas d'erreur + redirection-----*/
			request.setAttribute("newNom", nom);
			request.setAttribute("newPrenom",prenom);
			request.setAttribute("newLogin", login);
			request.setAttribute("newEmail", email);
			request.setAttribute("newTel",tel);
			System.out.println("Error register");
			doGet(request, response);
		} else {	
			User user = new User();
			user.setIdentifiant(login);
			user.setMotDePasse(mdp);
			user.setNomUser(nom);
			user.setTelephone(tel);
			user.setEmail(email);
			user.setPrenomUser(prenom);
			user.setStatut(statut);
			sessionFact.save(user);
			Groupe groupe = (Groupe) sessionFact.get(Groupe.class, Integer.parseInt(idGroupe));
			groupe.addUser(user);
			tx.commit();
			/*----- Fermeture connexion + Redirection -----*/
			sessionFact.close();
			sFactory.close();
			session.setAttribute("AlertSuccessMsg", "Vous êtes inscrit ! Vous pouvez vous connecter");
			response.sendRedirect("login");
		}

	}
	
	public boolean verifChaine(String s) throws Exception {
		char c = s.charAt(0);
		if (s.length() < 2)
			throw new Exception(" doit comporter au moins deux caracteres");
		if(!(c >= 'A' && c <= 'Z'))
			throw new Exception(" doit commencer par une lettre en majuscule");
		for(int i = 0 ; i< s.length(); i++) {
			c = s.charAt(i);
			if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && (c != '-') && !Character.isWhitespace(c))
				throw new Exception(" ne peut contenir que des lettres, tirés et espaces");
		}
		return true;
	}
	public boolean veriftel(String s) throws Exception {
		char c = s.charAt(0);
		if (s.length() != 10)
			throw new Exception("Le téléphone doit contenir exactement 10 nombres");
		return true;
	}
}
