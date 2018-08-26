package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the commentaire database table.
 * 
 */
@Embeddable
public class CommentairePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Cours cours;

	private User user;
	
	public CommentairePK() {
	}
	@ManyToOne(cascade = CascadeType.ALL)
	public Cours getCours() {
		return cours;
	}
	
	public void setCours(Cours cours) {
		this.cours = cours;
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentairePK that = (CommentairePK) o;

        if (cours != null ? !cours.equals(that.cours) : that.cours != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (cours != null ? cours.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

}