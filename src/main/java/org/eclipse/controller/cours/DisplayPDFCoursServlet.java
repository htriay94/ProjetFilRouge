package org.eclipse.controller.cours;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.model.Cours;
import org.hibernate.Session;

/**
 * Servlet implementation class DisplayPDFCoursServlet
 */

@WebServlet("/display-pdf")
public class DisplayPDFCoursServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_LOCATION_PROPERTY_KEY="C:/Users/Stagiaire/Documents/JEE/ProjetFilRouge/src/main/webapp/uploads";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayPDFCoursServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Cours cours = (Cours) session.getAttribute("cours");
		File download = new File(UPLOAD_LOCATION_PROPERTY_KEY + "/" + cours.getMatiere().getNomMatiere() + "/" + cours.getNomCours());
		File file = new File(download, cours.getSupport());
		response.setHeader("Content-Type",    getServletContext().getMimeType(file.getName()));
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "inline; filename=\""+cours.getSupport()+"\"");
		Files.copy(file.toPath(), response.getOutputStream());
	}

}
