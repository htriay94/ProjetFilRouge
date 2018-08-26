package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the reponse database table.
 * 
 */
@Entity
@NamedQuery(name="Reponse.findAll", query="SELECT r FROM Reponse r")
public class Reponse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int idReponse;

	private String contenu;

	private int numeroReponse;

	//bi-directional many-to-one association to Question
	@ManyToOne
	@JoinColumn(name="idQuestion")
	private Question question;

	public Reponse() {
	}

	public int getIdReponse() {
		return this.idReponse;
	}

	public void setIdReponse(int idReponse) {
		this.idReponse = idReponse;
	}

	public String getContenu() {
		return this.contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public int getNumeroReponse() {
		return this.numeroReponse;
	}

	public void setNumeroReponse(int numeroReponse) {
		this.numeroReponse = numeroReponse;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}