package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

@Entity
@Table(name = "race_codes")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "race_codes_id_seq") })
public class RaceCode extends AbstractMutableDomainObject{
	private Integer id ;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private RaceCodeEnum raceCode ;

	public void setRaceCode(RaceCodeEnum raceCode) {
		this.raceCode = raceCode;
	}

	public RaceCodeEnum getRaceCode() {
		return raceCode;
	}

	
}
