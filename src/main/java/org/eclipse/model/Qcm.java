package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the qcm database table.
 * 
 */
@Entity
@Table(name="qcm")
@NamedQuery(name="Qcm.findAll", query="SELECT q FROM Qcm q")
public class Qcm implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int idQCM;

	private String nomQCM;

	private int numeroQCM;

	//bi-directional many-to-one association to Cour
	@ManyToOne
	@JoinColumn(name="idCours")
	private Cours cours;

	//bi-directional many-to-one association to Question
	@OneToMany(targetEntity=Question.class, mappedBy="qcm",cascade=CascadeType.ALL)
	@ElementCollection
	private List<Question> questions;
	
	@ElementCollection
	private Set<UserQcm> userQcms = new HashSet<UserQcm>(0);

	public Qcm() {
	}
	
	public int getIdQCM() {
		return this.idQCM;
	}

	public void setIdQCM(int idQCM) {
		this.idQCM = idQCM;
	}

	public String getNomQCM() {
		return this.nomQCM;
	}

	public void setNomQCM(String nomQCM) {
		this.nomQCM = nomQCM;
	}

	public int getNumeroQCM() {
		return this.numeroQCM;
	}

	public void setNumeroQCM(int numeroQCM) {
		this.numeroQCM = numeroQCM;
	}

	public Cours getCours() {
		return this.cours;
	}

	public void setCours(Cours cour) {
		this.cours = cour;
	}
	@Transient
	public List<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Question addQuestion(Question question) {
		getQuestions().add(question);
		question.setQcm(this);

		return question;
	}

	public Question removeQuestion(Question question) {
		getQuestions().remove(question);
		question.setQcm(null);

		return question;
	}
	//bi-directional many-to-one association to UserQcm
	@OneToMany(mappedBy = "pk.qcm", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	public Set<UserQcm> getUserQcms() {
		return this.userQcms;
	}

	public void setUserQcms(Set<UserQcm> userQcms) {
		this.userQcms = userQcms;
	}

	public UserQcm addUserQcm(UserQcm userQcm) {
		getUserQcms().add(userQcm);
		userQcm.setQcm(this);

		return userQcm;
	}

	public UserQcm removeUserQcm(UserQcm userQcm) {
		getUserQcms().remove(userQcm);
		userQcm.setQcm(null);

		return userQcm;
	}

}