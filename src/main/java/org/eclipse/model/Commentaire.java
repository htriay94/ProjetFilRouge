package org.eclipse.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


/**
 * The persistent class for the commentaire database table.
 * 
 */
@Entity
@Table(name="commentaire")
@NamedQueries({
	@NamedQuery(name="Commentaire.findAll", query="SELECT c FROM Commentaire c"),
	@NamedQuery(name="Commentaire.findAllByUser", query="SELECT c FROM Commentaire c WHERE c.user.idUser = :idUser"),
	@NamedQuery(name="Commentaire.findAllByCours", query="SELECT c FROM Commentaire c WHERE c.cours.idCours = :idCours")
})
public class Commentaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idCommentaire;

	private String contenuCommentaire;

	@Temporal(TemporalType.DATE)
	private Date dateCommentaire;
	
	//bi-directional many-to-one association to Cour
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idCours")
	private Cours cours;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;
	
	public Commentaire() {
	}

	public String getContenuCommentaire() {
		return this.contenuCommentaire;
	}

	public void setContenuCommentaire(String contenuCommentaire) {
		this.contenuCommentaire = contenuCommentaire;
	}

	public Date getDateCommentaire() {
		return this.dateCommentaire;
	}

	public void setDateCommentaire(Date dateCommentaire) {
		this.dateCommentaire = dateCommentaire;
	}
	
	public Cours getCours() {
		return this.cours;
	}

	public void setCours(Cours cours) {
		this.cours = cours;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}