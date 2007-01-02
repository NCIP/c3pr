package edu.duke.cabig.c3pr.web;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;

/**
 * Used in conjunction for creation of Study pages
 * Refers Study, StudySite, Epoch, Arm domain objects
 * @author Priyatam
 *
 */
public class StudyDesignCommand {
	
	// Nested properties should be non-null values!
	private Study study = new Study();
	private StudySite studySite = new StudySite();
	private Epoch epoch = new Epoch();
	private Arm arm = new Arm();
	
	public Arm getArm() {
		return arm;
	}
	public void setArm(Arm arm) {
		this.arm = arm;
	}
	public Epoch getEpoch() {
		return epoch;
	}
	public void setEpoch(Epoch epoch) {
		this.epoch = epoch;
	}
	public Study getStudy() {
		return study;
	}
	public void setStudy(Study study) {
		this.study = study;
	}
	public StudySite getStudySite() {
		return studySite;
	}
	public void setStudySite(StudySite studySite) {
		this.studySite = studySite;
	}
	

}
