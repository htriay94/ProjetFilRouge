package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;
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
	@OneToMany(mappedBy="groupe")
	private List<User> users;

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