package org.eclipse.controller.matiere;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.model.Matiere;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Servlet implementation class AddMatiereServlet
 */
@WebServlet("/addMatiere")
public class AddMatiereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMatiereServlet() {
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
    		
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Matiere matiere = new Matiere();
		matiere.setNomMatiere("JEE");
//		Configuration configuration = new Configuration().configure().addAnnotatedClass(Matiere.class);
//		SessionFactory sFactory = configuration.buildSessionFactory();
//		Session session = sFactory.openSession();
//		Transaction tx = session.beginTransaction();
//		session.save(matiere); // on peut aussi utiliser persist
//		tx.commit();
		doGet(request, response);
	}

}
