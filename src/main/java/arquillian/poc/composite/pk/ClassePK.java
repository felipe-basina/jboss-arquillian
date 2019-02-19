package arquillian.poc.composite.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ClassePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3980704805143219798L;

	@Column(name = "FIRST_ID")
	private String firstId;

	@Column(name = "SECOND_ID")
	private String secondId;

	@Column(name = "THIRD_ID")
	private Integer thirdId;

	public String getFirstId() {
		return firstId;
	}

	public void setFirstId(String firstId) {
		this.firstId = firstId;
	}

	public String getSecondId() {
		return secondId;
	}

	public void setSecondId(String secondId) {
		this.secondId = secondId;
	}

	public Integer getThirdId() {
		return thirdId;
	}

	public void setThirdId(Integer thirdId) {
		this.thirdId = thirdId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClassePK [firstId=").append(firstId).append(", secondId=").append(secondId).append(", thirdId=")
				.append(thirdId).append("]");
		return builder.toString();
	}

	public ClassePK(String firstId, String secondId, Integer thirdId) {
		super();
		this.firstId = firstId;
		this.secondId = secondId;
		this.thirdId = thirdId;
	}

	public ClassePK() {
		super();
	}

}
