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
//	private Participant participant;
//	private StudySubjectDemographics studySubjectDemographics ;
//	
//	public Participant getParticipant() {
//		return participant;
//	}
//
//	public void setParticipant(Participant participant) {
//		this.participant = participant;
//	}
//
//	public StudySubjectDemographics getStudySubjectDemographics() {
//		return studySubjectDemographics;
//	}
//
//	public void setStudySubjectDemographics(
//			StudySubjectDemographics studySubjectDemographics) {
//		this.studySubjectDemographics = studySubjectDemographics;
//	}

	public void setRaceCode(RaceCodeEnum raceCode) {
		this.raceCode = raceCode;
	}
	
	@Enumerated(EnumType.STRING)
	public RaceCodeEnum getRaceCode() {
		return raceCode;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final RaceCodeAssociation that = (RaceCodeAssociation) o;

        if (raceCode != null ? !raceCode.equals(that.raceCode) : that.raceCode != null) return false;
        return true;
	}

}
