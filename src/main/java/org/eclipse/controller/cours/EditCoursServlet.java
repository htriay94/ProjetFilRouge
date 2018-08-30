package org.eclipse.controller.cours;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.eclipse.model.Cours;
import org.eclipse.model.Groupe;
import org.eclipse.model.Matiere;
import org.eclipse.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Servlet implementation class AddCourServlet
 */
@WebServlet("/edit-cours")
@MultipartConfig
public class EditCoursServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_LOCATION_PROPERTY_KEY="C:/Users/Stagiaire/Documents/JEE/ProjetFilRouge/src/main/webapp/uploads";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCoursServlet() {
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
    		Cours cours = (Cours) session.getAttribute("cours");
    		if(user_auth.getStatut().equals("admin") || (user_auth.getStatut().equals("formateur") && user_auth.getIdUser() == cours.getUser().getIdUser())) {
    			/*----- Preparation de la connexion -----*/
    			Configuration configuration = new Configuration().configure();
    			SessionFactory sFactory = configuration.buildSessionFactory();
    			Session sessionFact = sFactory.openSession();

    			Query query = sessionFact.createNamedQuery("Groupe.findAll");
    			List<Groupe> groupes = (List<Groupe>) query.getResultList();
    			
    			request.setAttribute("groupes",groupes);
    			request.setAttribute("type","Editer");
    			request.setAttribute("date","text");
        		request.setAttribute("action","edit-cours");
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
//		
//		/*----- Recupération des inputs -----*/
//		FileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		// Parse the request
//		List<FileItem> uploadItems = null;
//		try {
//			uploadItems = upload.parseRequest(request);
//		} catch (FileUploadException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		String nom = null;
//		String supportChecked = null;
//		String support = null;
//		for( FileItem uploadItem : uploadItems )
//		{
//			/*---- nom / support / supportChecked ----*/
//			if( uploadItem.isFormField() && uploadItem.getFieldName().equals("nom"))
//				  nom = uploadItem.getString();
//			if( uploadItem.isFormField() && uploadItem.getFieldName().equals("support"))
//				  support = uploadItem.getString();
//			if( uploadItem.isFormField() && uploadItem.getFieldName().equals("supportChecked"))
//				  supportChecked = uploadItem.getString();
//		}
		
//		System.out.println(nom + " / " + supportChecked + " / " + support + " / ");
		String nom = request.getParameter("nom");
		String dateCoursSTR = request.getParameter("dateCours");
		String idGroupe = request.getParameter("idGroupe");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//surround below line with try catch block as below code throws checked exception
		Date dateCours = null;
		try {
			dateCours = sdf.parse(dateCoursSTR);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		System.out.println(nom +  " / ");

		
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
				/*----- erreur + redirection-----*/
				System.out.println("Error edit cours");
				doGet(request, response);
			} else {
				Cours cours = (Cours) session.getAttribute("cours");
				Groupe old_groupe = cours.getGroupe();
				Groupe new_groupe = (Groupe) sessionFact.get(Groupe.class, Integer.parseInt(idGroupe));
				String nomMat = cours.getMatiere().getNomMatiere();
				int idMat = cours.getMatiere().getIdMatiere();
				cours.setNomCours(nom);
				cours.setDateCours(dateCours);
//				
//				if(!old_groupe.equals(new_groupe)) {
//					if(cours.getGroupe() != null)
//						System.out.println("AVANT REMOVE -------- " + cours.getGroupe().getNomGroupe() + " liste --- " + old_groupe.getCours().contains(cours));
//					else
//						System.out.println("AVANT REMOVE -------- NULL liste --- " + old_groupe.getCours().contains(cours));
//					
//					old_groupe.removeCours(cours);
//					
//					if(cours.getGroupe() != null)
//						System.out.println("APRES REMOVE -------- " + cours.getGroupe().getNomGroupe() + " liste --- " + old_groupe.getCours().contains(cours));
//					else
//						System.out.println("APRES REMOVE -------- NULL liste --- " + old_groupe.getCours().contains(cours));
//					
//					new_groupe.addCours(cours);
//					
//					System.out.println("APRES ADD -------- " + cours.getGroupe().getNomGroupe() + " liste --- " + new_groupe.getCours().contains(cours));
//				}
				sessionFact.saveOrUpdate(cours);
				tx.commit();
				
				/*if(supportChecked!=null) {
						File uploads = new File(UPLOAD_LOCATION_PROPERTY_KEY + "/" + cours.getMatiere().getNomMatiere() + "/" + old_nom);
		    		    File file = new File(uploads, cours.getSupport());
		    		    file.delete();
		    		    uploads.delete();
	    		    cours.setSupport(savePDF(request,nomMat,cours.getNomCours()));
				}*/
				
				session.removeAttribute("cours");
				session.setAttribute("cours",cours);
				session.setAttribute("AlertSuccessMsg", "Cours bien modifié");
				
				/*----- Fermeture connexion + Redirection -----*/
    			sessionFact.close();
    			sFactory.close();
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
	
	public String savePDF(HttpServletRequest request, String nomMatiere, String nomCours) throws IOException, ServletException {
		/*----- Enregistrement PDF -----*/
		Part filePart = request.getPart("support");
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); 
	    File uploads = new File(UPLOAD_LOCATION_PROPERTY_KEY + "/" + nomMatiere + "/" + nomCours);
	    System.out.println(UPLOAD_LOCATION_PROPERTY_KEY);
	    File file = new File(uploads, fileName);
	    try (InputStream fileContent = filePart.getInputStream()) {
	        Files.copy(fileContent, file.toPath());
	    }
	    return fileName;
    }
}
