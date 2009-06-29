package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.StudyPart;


@Entity
@Table(name = "amendment_reasons")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "AMENDMENT_REASON_ID_SEQ") })
public class AmendmentReason extends AbstractMutableDeletableDomainObject implements Comparable<AmendmentReason> {

	public int compareTo(AmendmentReason o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private StudyPart studyPart;
	
	@Enumerated(EnumType.STRING)
	public StudyPart getStudyPart() {
		return studyPart;
	}

	public void setStudyPart(StudyPart studyPart) {
		this.studyPart = studyPart;
	}
}
