package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the question database table.
 * 
 */
@Entity
@NamedQuery(name="Question.findAll", query="SELECT q FROM Question q")
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int idQuestion;

	private String enonce;

	private int numBonneReponse;

	private int numeroQuestion;

	//bi-directional many-to-one association to Qcm
	@ManyToOne
	@JoinColumn(name="idQCM")
	private Qcm qcm;

	//bi-directional many-to-one association to Reponse
	@OneToMany(mappedBy="question",cascade=CascadeType.ALL)
	private List<Reponse> reponses;

	public Question() {
	}

	public int getIdQuestion() {
		return this.idQuestion;
	}

	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}

	public String getEnonce() {
		return this.enonce;
	}

	public void setEnonce(String enonce) {
		this.enonce = enonce;
	}

	public int getNumBonneReponse() {
		return this.numBonneReponse;
	}

	public void setNumBonneReponse(int numBonneReponse) {
		this.numBonneReponse = numBonneReponse;
	}

	public int getNumeroQuestion() {
		return this.numeroQuestion;
	}

	public void setNumeroQuestion(int numeroQuestion) {
		this.numeroQuestion = numeroQuestion;
	}

	public Qcm getQcm() {
		return this.qcm;
	}

	public void setQcm(Qcm qcm) {
		this.qcm = qcm;
	}
	@Transient
	public List<Reponse> getReponses() {
		return this.reponses;
	}

	public void setReponses(List<Reponse> reponses) {
		this.reponses = reponses;
	}

	public Reponse addRepons(Reponse repons) {
		getReponses().add(repons);
		repons.setQuestion(this);

		return repons;
	}

	public Reponse removeRepons(Reponse repons) {
		getReponses().remove(repons);
		repons.setQuestion(null);

		return repons;
	}

}