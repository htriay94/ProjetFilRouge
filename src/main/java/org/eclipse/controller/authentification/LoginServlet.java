package org.eclipse.controller.authentification;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
    	if(session.getAttribute("connected") == null) {
    		this.getServletContext().getRequestDispatcher("/WEB-INF/Authentification/formAuth.jsp").forward(request, response);
    		return;
    	}
    	else {
	    	boolean co = (boolean) session.getAttribute("connected");
			if(co) {
				response.sendRedirect("home");
				return;
			}
			this.getServletContext().getRequestDispatcher("/WEB-INF/Authentification/formAuth.jsp").forward(request, response);
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*----- Preparation de la connexion -----*/
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		Session sessionFact = sFactory.openSession();
		HttpSession session = request.getSession();
		
		/*----- Recupération des inputs & tests-----*/
		String login = request.getParameter("login");
		String mdp = request.getParameter("mdp");
		System.out.println("login :"+login);
		System.out.println("mdp :"+mdp);
		
		/*----- Recuperation des inputs en cas d'erreur -----*/
		request.setAttribute("login", login);
		request.setAttribute("mdp",mdp);
		
		/*----- Execution requete pour récupérer le supposé user -----*/
		Query query = sessionFact.createNamedQuery("User.findByLoginAndMdp");
		query.setParameter("login", login);
		query.setParameter("mdp", mdp);
		
		/*----- Recupérer l'erreur si l'user décrit de ces inputs n'existe pas -----*/
		User user = (User) query.getResultList().stream().findFirst().orElse(null);
		if(user == null) {
			request.setAttribute("msgLog", "Login ou mot de passe incorrect");
			System.out.println("login failed ");
		} else {
			System.out.println(user.getIdUser());
			System.out.println("login success");
			session.setAttribute("connected",true);
			session.setAttribute("user_auth",user);
			session.setAttribute("AlertSuccessMsg", "<p>Bienvenue <b>"+ user.getPrenomUser() +" "+ user.getNomUser()+"</b>!</p><p>Vous vous êtes connecté avec succes !</p>");
			session.setAttribute("msgMenu", user.getPrenomUser() + " " + user.getNomUser());
		}
		/*----- Fermeture connexion + Redirection -----*/
		sessionFact.close();
		sFactory.close();
		doGet(request, response);
	}
}
