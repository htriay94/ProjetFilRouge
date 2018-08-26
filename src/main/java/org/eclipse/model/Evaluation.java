package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the evaluation database table.
 * 
 */
@Entity
@NamedQuery(name="Evaluation.findAll", query="SELECT e FROM Evaluation e")
public class Evaluation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int idEvaluation;

	@Lob
	private String contenuEvaluation;

	@Temporal(TemporalType.DATE)
	private Date dateEvaluation;

	private String titreEvaluation;

	//bi-directional many-to-one association to Formation
	@ManyToOne
	@JoinColumn(name="idFormation")
	private Formation formation;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;

	public Evaluation() {
	}

	public int getIdEvaluation() {
		return this.idEvaluation;
	}

	public void setIdEvaluation(int idEvaluation) {
		this.idEvaluation = idEvaluation;
	}

	public String getContenuEvaluation() {
		return this.contenuEvaluation;
	}

	public void setContenuEvaluation(String contenuEvaluation) {
		this.contenuEvaluation = contenuEvaluation;
	}

	public Date getDateEvaluation() {
		return this.dateEvaluation;
	}

	public void setDateEvaluation(Date dateEvaluation) {
		this.dateEvaluation = dateEvaluation;
	}

	public String getTitreEvaluation() {
		return this.titreEvaluation;
	}

	public void setTitreEvaluation(String titreEvaluation) {
		this.titreEvaluation = titreEvaluation;
	}

	public Formation getFormation() {
		return this.formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}