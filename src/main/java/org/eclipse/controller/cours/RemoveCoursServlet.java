package org.eclipse.controller.cours;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.eclipse.model.Commentaire;
import org.eclipse.model.Cours;
import org.eclipse.model.Matiere;
import org.eclipse.model.Qcm;
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
	private static final String UPLOAD_LOCATION_PROPERTY_KEY="C:/Users/Stagiaire/Documents/JEE/ProjetFilRouge/src/main/webapp/uploads";
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
    		    File uploads = new File(UPLOAD_LOCATION_PROPERTY_KEY + "/" + cours.getMatiere().getNomMatiere() + "/" + cours.getNomCours());
    		    File file = new File(uploads, cours.getSupport());
    		    if(file.delete())
    		    	System.out.println("File deleted successfully");
    	        else
    	        	System.out.println("Failed to delete the file");
    		    uploads.delete();
    			
				/*----- Suppression cours -------*/
    		    cours.getGroupe().setCours(null);
    		    cours.getMatiere().removeCour(cours);
    		    //cours.getQcms().clear();
    		    //cours.getCommentaires().clear();
    		    
    		    for(Commentaire commentaire : cours.getCommentaires() ) {
    		    	commentaire.setCours(null);
    		    }

    		    for(Qcm qcm: cours.getQcms()) {
    		    	qcm.setCours(null);
    		    }
    			sessionFact.remove(cours);
    			/*----- commit -------*/
    			tx.commit();
    			/*----- Fermeture connexion + Redirection -----*/
    			sessionFact.close();
    			sFactory.close();
    			session.setAttribute("AlertSuccessMsg", "Cours bien supprimé");
    			response.sendRedirect("liste-cours?id="+request.getParameter("idMat")+"&nom="+nomMat);
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
		
	}
}
