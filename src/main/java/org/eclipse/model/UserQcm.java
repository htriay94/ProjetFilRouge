package org.eclipse.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user_qcm database table.
 * 
 */
@Entity
@Table(name="user_qcm")
@AssociationOverrides({
	@AssociationOverride(name = "pk.qcm", 
		joinColumns = @JoinColumn(name = "idQCM")),
	@AssociationOverride(name = "pk.user", 
		joinColumns = @JoinColumn(name = "idUser")) })
public class UserQcm implements Serializable {
	private static final long serialVersionUID = 1L;

	private UserQcmPK pk = new UserQcmPK();

	@Temporal(TemporalType.DATE)
	private Date dateReponseQCM;

	public UserQcm() {
	}
	
	@EmbeddedId
	public UserQcmPK getPk() {
		return this.pk;
	}

	public void setPk(UserQcmPK pk) {
		this.pk = pk;
	}

	public Date getDateReponseQCM() {
		return this.dateReponseQCM;
	}

	public void setDateReponseQCM(Date dateReponseQCM) {
		this.dateReponseQCM = dateReponseQCM;
	}
	
	@Transient
	public Qcm getQcm() {
		return getPk().getQcm();
	}

	public void setQcm(Qcm qcm) {
		getPk().setQcm(qcm);
	}
	
	@Transient
	public User getUser() {
		return getPk().getUser();
	}

	public void setUser(User user) {
		getPk().setUser(user);
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserQcm that = (UserQcm) o;

		if (getPk() != null ? !getPk().equals(that.getPk())
				: that.getPk() != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}

}