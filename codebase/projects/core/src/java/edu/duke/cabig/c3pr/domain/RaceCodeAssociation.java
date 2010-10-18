package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.RaceCodeEnum;

@Entity
@Table(name = "race_code_assocn")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "race_code_assocn_id_seq") })
public class RaceCodeAssociation extends AbstractMutableDeletableDomainObject {
	private RaceCodeEnum raceCode;

	public void setRaceCode(RaceCodeEnum raceCode) {
		this.raceCode = raceCode;
	}
	
	@Enumerated(EnumType.STRING)
	public RaceCodeEnum getRaceCode() {
		return raceCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((raceCode == null) ? 0 : raceCode.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof RaceCodeAssociation)) {
			return false;
		}
		RaceCodeAssociation other = (RaceCodeAssociation) obj;
		if (raceCode != other.raceCode) {
			return false;
		}
		return true;
	}
	

}
