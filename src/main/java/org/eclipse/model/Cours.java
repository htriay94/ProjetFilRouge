package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the cours database table.
 * 
 */
@Entity
@Table(name="cours")
@NamedQueries({
	@NamedQuery(name="Cours.findAll", query="SELECT c FROM Cours c"),
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

	//bi-directional many-to-one association to Qcm
	@OneToMany(mappedBy="cours")
	private List<Qcm> qcms;
	
	@ElementCollection
	private Set<Commentaire> commentaires = new HashSet<Commentaire>(0);
	
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
	
	//bi-directional many-to-one association to UserQcm
	@OneToMany(mappedBy = "pk_com.cours", cascade = CascadeType.ALL, fetch=FetchType.LAZY) 
	public Set<Commentaire> getCommentaires() {
		return this.commentaires;
	}
	
	public void setCommentaires(Set<Commentaire> commentaires) {
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