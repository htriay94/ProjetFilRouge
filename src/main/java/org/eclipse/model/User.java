package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@NamedQueries({
		@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
		@NamedQuery(name="User.findByLoginAndMdp", query="SELECT u FROM User u WHERE u.identifiant = :login AND u.motDePasse = :mdp"),
		@NamedQuery(name="User.findByEmail", query="SELECT u FROM User u WHERE u.email = :email"),
		@NamedQuery(name="User.findByGroupe", query="SELECT u FROM User u WHERE u.groupe.idGroupe = :idGroupe"),
		@NamedQuery(name="User.findByNotGroupe", query="SELECT u FROM User u WHERE u.groupe = null"),
		@NamedQuery(name="User.findByLogin", query="SELECT u FROM User u WHERE u.identifiant = :login")
})

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int idUser;
	@Column(unique=true)
	private String email;
	@Column(unique=true)
	private String identifiant;

	private String motDePasse;

	private String nomUser;

	private String prenomUser;

	private String telephone;

	private String statut;

	//bi-directional many-to-one association to Cour
	@OneToMany(mappedBy="user")
	private List<Cours> cours;

	//bi-directional many-to-one association to Evaluation
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	private List<Evaluation> evaluations;

	//bi-directional many-to-one association to Groupe
	@ManyToOne
	@JoinColumn(name="idGroupe")
	private Groupe groupe;
	
	@ElementCollection
	private Set<UserQcm> userQcms = new HashSet<UserQcm>(0);
	
	//bi-directional many-to-one association to Commentaire
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<Commentaire> commentaires;

	public User() {
	}
	
	public int getIdUser() {
		return this.idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentifiant() {
		return this.identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getMotDePasse() {
		return this.motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getNomUser() {
		return this.nomUser;
	}

	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}

	public String getPrenomUser() {
		return this.prenomUser;
	}

	public void setPrenomUser(String prenomUser) {
		this.prenomUser = prenomUser;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Transient
	public List<Cours> getCours() {
		return this.cours;
	}

	public void setCours(List<Cours> cours) {
		this.cours = cours;
	}

	public Cours addCour(Cours cour) {
		getCours().add(cour);
		cour.setUser(this);

		return cour;
		
	}

	public Cours removeCour(Cours cour) {
		getCours().remove(cour);
		cour.setUser(null);

		return cour;
	}
	@Transient
	public List<Evaluation> getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public Evaluation addEvaluation(Evaluation evaluation) {
		getEvaluations().add(evaluation);
		evaluation.setUser(this);

		return evaluation;
	}

	public Evaluation removeEvaluation(Evaluation evaluation) {
		getEvaluations().remove(evaluation);
		evaluation.setUser(null);

		return evaluation;
	}

	public Groupe getGroupe() {
		return this.groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	//bi-directional many-to-one association to UserQcm
	@OneToMany(mappedBy = "pk.user", cascade = CascadeType.ALL, fetch=FetchType.LAZY) 
	public Set<UserQcm> getUserQcms() {
		return this.userQcms;
	}
	
	public void setUserQcms(Set<UserQcm> userQcms) {
		this.userQcms = userQcms;
	}

	public UserQcm addUserQcm(UserQcm userQcm) {
		getUserQcms().add(userQcm);
		userQcm.setUser(this);

		return userQcm;
	}

	public UserQcm removeUserQcm(UserQcm userQcm) {
		getUserQcms().remove(userQcm);
		userQcm.setUser(null);

		return userQcm;
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
		commentaire.setUser(this);

		return commentaire;
	}

	public Commentaire removeCommentaire(Commentaire commentaire) {
		getCommentaires().remove(commentaire);
		commentaire.setUser(null);

		return commentaire;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

}