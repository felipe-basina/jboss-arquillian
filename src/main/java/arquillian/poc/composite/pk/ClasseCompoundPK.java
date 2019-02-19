package arquillian.poc.composite.pk;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "COMPOUND_PK")
public class ClasseCompoundPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5818036941950509877L;

	@EmbeddedId
	private ClassePK pk;

	@Column(name = "CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(name = "DESCRIPTION")
	private String description;

	public ClassePK getPk() {
		return pk;
	}

	public void setPk(ClassePK pk) {
		this.pk = pk;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClasseCompoundPK [pk=").append(pk).append(", creationDate=").append(creationDate)
				.append(", description=").append(description).append("]");
		return builder.toString();
	}

	public ClasseCompoundPK(ClassePK pk, Date creationDate, String description) {
		super();
		this.pk = pk;
		this.creationDate = creationDate;
		this.description = description;
	}

	public ClasseCompoundPK() {
		super();
	}

}
