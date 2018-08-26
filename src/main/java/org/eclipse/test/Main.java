package org.eclipse.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.eclipse.model.Commentaire;
import org.eclipse.model.Cours;
import org.eclipse.model.Matiere;
import org.eclipse.model.Qcm;
import org.eclipse.model.User;
import org.eclipse.model.UserQcm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

	public static void main(String[] args) {
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		Session session = sFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		// TODO Auto-generated method stub
//		Matiere matiere = new Matiere();
//		matiere.setNomMatiere("JEE");
//		
//		Qcm qcm  = new Qcm();
//		qcm.setNomQCM("QCM n°2");
//		session.save(qcm);
////		List<Qcm> qcms = new ArrayList<Qcm>();
////		qcms.add(qcm);
////		Cour cour = new Cour();
////		cour.setDateCours(new Date());
////		cour.setMatiere(matiere);
////		cour.setNomCours("Cour 1");
////		cour.setQcms(qcms);
		
//		User user = new User();
//		user.setIdentifiant("htriay");
//		user.setMotDePasse("1234");
//		user.setNomUser("Triay");
//		user.setPrenomUser("Hugo");
//		user.setType(0);
		
////		List<Cour> cours = new ArrayList<Cour>();
////		cours.add(cour);
////		user.setCours(cours);
		
//		UserQcm userQcm = new UserQcm();
//		userQcm.setQcm(qcm);
//		userQcm.setUser(user);
//		userQcm.setDateReponseQCM(new Date());
//		user.addUserQcm(userQcm);
//		session.save(user);
		//session.save(userQcm);
		
//		Query query = (Query) session.createNativeQuery("Select * from elsilio.user_qcm where idQcm = 15", UserQcm.class);
//		List<UserQcm> userQcms = (List<UserQcm>) query.getResultList();
//		System.out.println(userQcms);
//		for(UserQcm userQcm : userQcms) {
//			System.out.println(userQcm.getDateReponseQCM());
//		}
//		
////	Cour cour = new Cour();
////	cour.setDateCours(new Date());
////	cour.setMatiere(matiere);
////	cour.setNomCours("Cour 1");
////	cour.setQcms(qcms);
	
	User user = new User();
	user.setIdentifiant("htriay2");
	user.setMotDePasse("12341");
	user.setNomUser("Triay");
	user.setPrenomUser("Hugo");
	
	Cours cours = new Cours();
	cours.setDateCours(new Date());
	cours.setNomCours("Cour 2");
	session.save(cours);
	
	Commentaire commentaire = new Commentaire();
	commentaire.setCours(cours);
	commentaire.setUser(user);
	commentaire.setContenuCommentaire("J'aime pas");
	user.addCommentaire(commentaire);
	session.save(user);
	//session.save(commentaire);
	tx.commit();
	session.close();
	sFactory.close();

	}

}
