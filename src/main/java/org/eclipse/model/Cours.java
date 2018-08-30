package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cours database table.
 * 
 */
@Entity
@Table(name="cours")
@NamedQueries({
	@NamedQuery(name="Cours.findAll", query="SELECT c FROM Cours c"),
	@NamedQuery(name="Cours.findAllOrderByDate", query="SELECT c FROM Cours c ORDER BY c.dateCours DESC"),
	@NamedQuery(name="Cours.findByIdMatiere", query="SELECT c FROM Cours c WHERE c.matiere.idMatiere = :idMatiere ORDER BY c.dateCours DESC")
})
public class Cours implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int idCours;

	@Temporal(TemporalType.DATE)
	private Date dateCours;

	private String nomCours;

	private String support;

	//bi-directional many-to-one association to Matiere
	@ManyToOne
	@JoinColumn(name="idMatiere")
	private Matiere matiere;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;
	
	//bi-directional many-to-one association to Groupe
	@ManyToOne
	@JoinColumn(name="idGroupe")
	private Groupe groupe;

	//bi-directional many-to-one association to Qcm
	@OneToMany(mappedBy="cours")
	private List<Qcm> qcms;
	
	//bi-directional many-to-one association to Commentaire
	@OneToMany(mappedBy="cours")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Commentaire> commentaires = new ArrayList<Commentaire>();
	
	public Cours() {
	}

	public int getIdCours() {
		return this.idCours;
	}

	public void setIdCours(int idCours) {
		this.idCours = idCours;
	}

	public Date getDateCours() {
		return this.dateCours;
	}

	public void setDateCours(Date dateCours) {
		this.dateCours = dateCours;
	}

	public String getNomCours() {
		return this.nomCours;
	}

	public void setNomCours(String nomCours) {
		this.nomCours = nomCours;
	}

	public String getSupport() {
		return this.support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public Matiere getMatiere() {
		return this.matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	@Transient
	public List<Qcm> getQcms() {
		return this.qcms;
	}

	public void setQcms(List<Qcm> qcms) {
		this.qcms = qcms;
	}

	public Qcm addQcm(Qcm qcm) {
		getQcms().add(qcm);
		qcm.setCours(this);

		return qcm;
	}

	public Qcm removeQcm(Qcm qcm) {
		getQcms().remove(qcm);
		qcm.setCours(null);

		return qcm;
	}
	
	@Transient
	public List<Commentaire> getCommentaires() {
		return this.commentaires;
	}

	public void setCommentaires(List<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}

	public Commentaire addCommentaire(Commentaire commentaire) {
		getCommentaires().add(commentaire);
		commentaire.setCours(this);

		return commentaire;
	}

	public Commentaire removeCommentaire(Commentaire commentaire) {
		getCommentaires().remove(commentaire);
		commentaire.setCours(null);

		return commentaire;
	}

}