package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the matiere database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Matiere.findAll", query="SELECT m FROM Matiere m"),
	@NamedQuery(name="Matiere.findById", query="SELECT m FROM Matiere m WHERE m.idMatiere = :idMatiere ORDER BY m.nomMatiere ASC")
})
public class Matiere implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int idMatiere;

	private String nomMatiere;

	//bi-directional many-to-one association to Cour
	@OneToMany(mappedBy="matiere",cascade=CascadeType.ALL)
	private List<Cours> cours;

	public Matiere() {
	}

	public int getIdMatiere() {
		return this.idMatiere;
	}

	public void setIdMatiere(int idMatiere) {
		this.idMatiere = idMatiere;
	}

	public String getNomMatiere() {
		return this.nomMatiere;
	}

	public void setNomMatiere(String nomMatiere) {
		this.nomMatiere = nomMatiere;
	}

	public List<Cours> getCours() {
		return this.cours;
	}

	public void setCours(List<Cours> cours) {
		this.cours = cours;
	}

	public Cours addCour(Cours cour) {
		getCours().add(cour);
		cour.setMatiere(this);

		return cour;
	}

	public Cours removeCour(Cours cour) {
		getCours().remove(cour);
		cour.setMatiere(null);

		return cour;
	}

	@Override
	public String toString() {
		return "Matiere [idMatiere=" + idMatiere + ", nomMatiere=" + nomMatiere + ", cours=" + cours + "]";
	}

}