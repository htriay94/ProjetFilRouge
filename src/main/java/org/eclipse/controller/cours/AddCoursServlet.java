package org.eclipse.controller.cours;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.eclipse.model.Cours;
import org.eclipse.model.Groupe;
import org.eclipse.model.Matiere;
import org.eclipse.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class AddCourServlet
 */
@WebServlet("/add-cours")
@MultipartConfig   
public class AddCoursServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_LOCATION_PROPERTY_KEY="C:/Users/Stagiaire/Documents/JEE/ProjetFilRouge/src/main/webapp/uploads";
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
    			/*----- Preparation de la connexion -----*/
    			Configuration configuration = new Configuration().configure();
    			SessionFactory sFactory = configuration.buildSessionFactory();
    			Session sessionFact = sFactory.openSession();
    			Query query = sessionFact.createNamedQuery("Groupe.findAll");
    			List<Groupe> groupes = (List<Groupe>) query.getResultList();
    			
    			request.setAttribute("groupes",groupes);
    			request.setAttribute("type","Créer");
    			//request.setAttribute("id",id);
        		request.setAttribute("action","add-cours");
        		request.setAttribute("date","date");
        		session.removeAttribute("cours");
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
		String dateCoursSTR = request.getParameter("dateCours");
		String idGroupe = request.getParameter("idGroupe");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//surround below line with try catch block as below code throws checked exception
		Date dateCours = null;
		try {
			dateCours = sdf.parse(dateCoursSTR);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int idMat = (Integer)session.getAttribute("idMat");
		
		System.out.println(nom + " / "+ dateCours);
		
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
				Groupe groupe = (Groupe) sessionFact.get(Groupe.class, Integer.parseInt(idGroupe));
				cours.setNomCours(nom);
				cours.setDateCours(dateCours);
				Matiere matiere = (Matiere) sessionFact.get(Matiere.class, idMat);
				cours.setMatiere(matiere);
				cours.setSupport(savePDF(request,matiere.getNomMatiere(),cours.getNomCours()));
				User user_auth = (User) session.getAttribute("user_auth");
				cours.setUser(user_auth);
				cours.setGroupe(groupe);
				sessionFact.save(cours);
				tx.commit();
				/*----- Fermeture connexion + Redirection -----*/
				sessionFact.close();
				sFactory.close();
				session.setAttribute("AlertSuccessMsg", "Cours bien enregistré");
				response.sendRedirect("liste-cours?id="+idMat+"&nom="+matiere.getNomMatiere());
				
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
	public String savePDF(HttpServletRequest request, String nomMatiere, String nomCours) throws IOException, ServletException {
		/*----- Enregistrement PDF -----*/
		Part filePart = request.getPart("support"); // Retrieves <input type="file" name="file">
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); 
	    File uploads = new File(UPLOAD_LOCATION_PROPERTY_KEY + "/" + nomMatiere + "/" + nomCours);
	    uploads.mkdirs();
	    System.out.println(UPLOAD_LOCATION_PROPERTY_KEY);
	    File file = new File(uploads, fileName);
	    try (InputStream fileContent = filePart.getInputStream()) {
	        Files.copy(fileContent, file.toPath());
	    }
	    return fileName;
    }
}
