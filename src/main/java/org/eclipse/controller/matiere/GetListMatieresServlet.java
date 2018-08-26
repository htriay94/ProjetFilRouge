package org.eclipse.controller.matiere;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.model.Matiere;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Servlet implementation class GetListMatieresServlet
 */
@WebServlet("/GetListMatieresServlet")
public class GetListMatieresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListMatieresServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//HttpSession session = request.getSession();		
		//if(session.getAttribute("connected") != null) {
			String matieresXML = "<matieres>";
			String matiereEltXML = "";
			response.setContentType("text/xml");
			Configuration configuration = new Configuration().configure();
			SessionFactory sFactory = configuration.buildSessionFactory();
			Session sessionFact = sFactory.openSession();
			
			/*----- Execution requete pour récupérer le supposé user -----*/
			Query query = sessionFact.createNamedQuery("Matiere.findAll");
			List<Matiere> matieres = (List<Matiere>) query.getResultList();
			
			for (Matiere matiere : matieres) {
				System.out.println(matiere);
				matiereEltXML = "<matiere id=\""+matiere.getIdMatiere()+"\">";
				matiereEltXML += "<nom>"+matiere.getNomMatiere()+"</nom>";
				matiereEltXML += "</matiere>";
				matieresXML += matiereEltXML;
			}
			matieresXML += "</matieres>";
			sessionFact.close();
			sFactory.close();
			System.out.println(matieresXML);
			response.getWriter().write(matieresXML);
		//}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
