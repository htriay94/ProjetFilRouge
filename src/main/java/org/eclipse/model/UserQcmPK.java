package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the user_qcm database table.
 * 
 */
@Embeddable
public class UserQcmPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Qcm qcm;

	private User user;

	public UserQcmPK() {
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	public Qcm getQcm() {
		return qcm;
	}
	
	public void setQcm(Qcm qcm) {
		this.qcm = qcm;
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

        UserQcmPK that = (UserQcmPK) o;

        if (qcm != null ? !qcm.equals(that.qcm) : that.qcm != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (qcm != null ? qcm.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}