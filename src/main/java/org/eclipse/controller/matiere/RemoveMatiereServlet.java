package org.eclipse.controller.matiere;

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
@WebServlet("/remove-matiere")
public class RemoveMatiereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveMatiereServlet() {
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
    			/*----- Recuperation matiere -------*/
    			Matiere matiere = (Matiere) sessionFact.get(Matiere.class, Integer.parseInt(request.getParameter("idMat")));
    			/*----- Suppression matiere -------*/
    			sessionFact.remove(matiere);
    			/*----- commit -------*/
    			tx.commit();
    			/*----- Fermeture connexion + Redirection -----*/
    			sessionFact.close();
    			sFactory.close();
    			session.setAttribute("AlertSuccessMsg", "Mati�re bien supprim�e");
    			response.sendRedirect("home");
    		} else {
    			session.setAttribute("AlertErrorMsg", "Vous n'�tes pas autoris�(e) � faire cette action !");
    			response.sendRedirect("home");
    		}
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
}
