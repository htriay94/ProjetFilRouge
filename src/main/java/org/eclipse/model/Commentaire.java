package org.eclipse.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the commentaire database table.
 * 
 */
@Entity
@Table(name="commentaire")
@AssociationOverrides({
	@AssociationOverride(name = "pk_com.cours", 
		joinColumns = @JoinColumn(name = "idCours")),
	@AssociationOverride(name = "pk_com.user", 
		joinColumns = @JoinColumn(name = "idUser")) })
public class Commentaire implements Serializable {
	private static final long serialVersionUID = 1L;

	private CommentairePK pk_com = new CommentairePK();

	private String contenuCommentaire;

	@Temporal(TemporalType.DATE)
	private Date dateCommentaire;

	public Commentaire() {
	}

	@EmbeddedId
	public CommentairePK getPk_com() {
		return this.pk_com;
	}

	public void setPk_com(CommentairePK pk_com) {
		this.pk_com = pk_com;
	}
	public String getContenuCommentaire() {
		return this.contenuCommentaire;
	}

	public void setContenuCommentaire(String contenuCommentaire) {
		this.contenuCommentaire = contenuCommentaire;
	}

	public Date getDateCommentaire() {
		return this.dateCommentaire;
	}

	public void setDateCommentaire(Date dateCommentaire) {
		this.dateCommentaire = dateCommentaire;
	}
	
	@Transient
	public Cours getCours() {
		return getPk_com().getCours();
	}

	public void setCours(Cours cours) {
		getPk_com().setCours(cours);
	}
	
	@Transient
	public User getUser() {
		return getPk_com().getUser();
	}

	public void setUser(User user) {
		getPk_com().setUser(user);
	}
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Commentaire that = (Commentaire) o;

		if (getPk_com() != null ? !getPk_com().equals(that.getPk_com())
				: that.getPk_com() != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (getPk_com() != null ? getPk_com().hashCode() : 0);
	}
}