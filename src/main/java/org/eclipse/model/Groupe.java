package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;


/**
 * The persistent class for the groupe database table.
 * 
 */
@Entity
@NamedQuery(name="Groupe.findAll", query="SELECT g FROM Groupe g")
public class Groupe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int idGroupe;

	private String nomGroupe;

	//bi-directional many-to-one association to Formation
	@ManyToOne
	@JoinColumn(name="idFormation")
	private Formation formation;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="groupe",cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<User> users;
	
	//bi-directional many-to-one association to Cour
	@OneToMany(mappedBy="groupe",cascade = CascadeType.PERSIST)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Cours> cours;
	
	public Groupe() {
	}

	public int getIdGroupe() {
		return this.idGroupe;
	}

	public void setIdGroupe(int idGroupe) {
		this.idGroupe = idGroupe;
	}

	public String getNomGroupe() {
		return this.nomGroupe;
	}

	public void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
	}

	public Formation getFormation() {
		return this.formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}
	@Transient
	public List<Cours> getCours() {
		return this.cours;
	}

	public void setCours(List<Cours> cours) {
		this.cours = cours;
	}

	public Cours addCours(Cours cour) {
		cour.setGroupe(this);
		getCours().add(cour);
		return cour;
	}

	public Cours removeCours(Cours cour) {
		cour.setGroupe(null);
		getCours().remove(cour);
		return cour;
	}
	
	@Transient
	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setGroupe(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setGroupe(null);

		return user;
	}

}