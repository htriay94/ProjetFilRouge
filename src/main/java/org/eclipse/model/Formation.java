package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the formation database table.
 * 
 */
@Entity
@NamedQuery(name="Formation.findAll", query="SELECT f FROM Formation f")
public class Formation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int idFormation;

	@Temporal(TemporalType.DATE)
	private Date dateDebut;

	@Temporal(TemporalType.DATE)
	private Date dateFin;

	private String nomFormation;

	//bi-directional many-to-one association to Evaluation
	@OneToMany(mappedBy="formation")
	private List<Evaluation> evaluations;

	//bi-directional many-to-one association to Groupe
	@OneToMany(mappedBy="formation")
	private List<Groupe> groupes;

	public Formation() {
	}

	public int getIdFormation() {
		return this.idFormation;
	}

	public void setIdFormation(int idFormation) {
		this.idFormation = idFormation;
	}

	public Date getDateDebut() {
		return this.dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getNomFormation() {
		return this.nomFormation;
	}

	public void setNomFormation(String nomFormation) {
		this.nomFormation = nomFormation;
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
		evaluation.setFormation(this);

		return evaluation;
	}

	public Evaluation removeEvaluation(Evaluation evaluation) {
		getEvaluations().remove(evaluation);
		evaluation.setFormation(null);

		return evaluation;
	}
	@Transient
	public List<Groupe> getGroupes() {
		return this.groupes;
	}

	public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}

	public Groupe addGroupe(Groupe groupe) {
		getGroupes().add(groupe);
		groupe.setFormation(this);

		return groupe;
	}

	public Groupe removeGroupe(Groupe groupe) {
		getGroupes().remove(groupe);
		groupe.setFormation(null);

		return groupe;
	}

}