package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "stu_sub_stu_versions")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STU_SUB_STU_VERSION_ID_SEQ") })
public class StudySubjectStudyVersion extends AbstractMutableDeletableDomainObject implements Comparable<StudySubjectStudyVersion>{

	private StudySubject studySubject;
	private StudySiteStudyVersion studySiteStudyVersion;
	private List<ScheduledEpoch> scheduledEpochs = new ArrayList<ScheduledEpoch>();


	@ManyToOne
    @JoinColumn(name = "stu_sub_id", nullable=false)
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE})
	public StudySubject getStudySubject() {
		return studySubject;
	}

	public void setStudySubject(StudySubject studySubject) {
		this.studySubject = studySubject;
	}

	@ManyToOne
	 @JoinColumn(name = "stu_site_stu_ver_id", nullable=false)
	 @Cascade( { CascadeType.LOCK })
	public StudySiteStudyVersion getStudySiteStudyVersion() {
		return studySiteStudyVersion;
	}

	public void setStudySiteStudyVersion(
			StudySiteStudyVersion studySiteStudyVersion) {
		this.studySiteStudyVersion = studySiteStudyVersion;
	}

	@OneToMany
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "stu_sub_stu_ver_id")
	public List<ScheduledEpoch> getScheduledEpochs() {
		return scheduledEpochs;
	}

	public void setScheduledEpochs(List<ScheduledEpoch> scheduledEpochs) {
		this.scheduledEpochs = scheduledEpochs;
	}

	public void addScheduledEpoch(ScheduledEpoch scheduledEpoch) {
		getScheduledEpochs().add(scheduledEpoch);
	}

	/**
	 * Gets the current scheduled epoch.
	 *
	 * @return the current scheduled epoch
	 */
	@Transient
	public ScheduledEpoch getCurrentScheduledEpoch() {
		List<ScheduledEpoch> tempList = new ArrayList<ScheduledEpoch>();
		tempList.addAll(getScheduledEpochs());
		Collections.sort(tempList);
		if (tempList.size() == 0)
			return null;
		return tempList.get(tempList.size() - 1);
	}
	
	/**
	 * If scheduled epoch created for this epoch.
	 *
	 * @param epoch the epoch
	 *
	 * @return true, if successful
	 */
	public boolean hasScehduledEpoch(Epoch epoch) {
		for (ScheduledEpoch scheduledEpoch : this.getScheduledEpochs())
			if (scheduledEpoch.getEpoch().equals(epoch)) {
				return true;
			}

		return false;
	}
	
	/**
	 * Gets the matching scheduled epoch.
	 *
	 * @param epoch the epoch
	 *
	 * @return the matching scheduled epoch
	 */
	public ScheduledEpoch getScheduledEpoch(Epoch epoch) {
		for (ScheduledEpoch scheduledEpoch : this.getScheduledEpochs())
			if (scheduledEpoch.getEpoch().equals(epoch)) {
				return scheduledEpoch;
			}
		return null;
	}
	
	/**
	 * Checks if is transferrable.
	 *
	 * @return true, if is transferrable
	 */
	@Transient
	public boolean isTransferrable() {
		if(getScheduledEpochs().size() < 2) return false;
		List<ScheduledEpoch> tempList = new ArrayList<ScheduledEpoch>();
		tempList.addAll(getScheduledEpochs());
		Collections.sort(tempList);
		if (tempList.get(tempList.size() - 1).getEpoch().getEpochOrder() < tempList
				.get(tempList.size() - 2).getEpoch().getEpochOrder()) {
			return false;
		}
		return true;
	}
	
	public int compareTo(StudySubjectStudyVersion studySubjectStudyVersion) {
		return this.getStudySiteStudyVersion().getStudyVersion().compareTo(studySubjectStudyVersion.getStudySiteStudyVersion().getStudyVersion());
	}

}
