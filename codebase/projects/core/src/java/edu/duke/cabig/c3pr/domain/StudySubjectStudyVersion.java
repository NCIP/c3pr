package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@Table(name = "study_subject_versions")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "study_subject_versions_id_seq") })
public class StudySubjectStudyVersion extends AbstractMutableDeletableDomainObject implements Comparable<StudySubjectStudyVersion>{

	private StudySubject studySubject;
	private StudySiteStudyVersion studySiteStudyVersion;
	private List<ScheduledEpoch> scheduledEpochs = new ArrayList<ScheduledEpoch>();

	/** The lazy list helper. */
	private LazyListHelper lazyListHelper;


	public StudySubjectStudyVersion() {
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(StudySubjectConsentVersion.class,
				new InstantiateFactory<StudySubjectConsentVersion>(StudySubjectConsentVersion.class));
	}

	@OneToMany(orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	@JoinColumn(name = "study_subject_ver_id")
	@Cascade(value = { CascadeType.ALL})
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy("id")
	public List<StudySubjectConsentVersion> getStudySubjectConsentVersionsInternal() {
		return lazyListHelper.getInternalList(StudySubjectConsentVersion.class);
	}

	@Transient
	public List<StudySubjectConsentVersion> getStudySubjectConsentVersions() {
		return lazyListHelper.getLazyList(StudySubjectConsentVersion.class);
	}

	public void setStudySubjectConsentVersionsInternal(List<StudySubjectConsentVersion> studySubjectConsentVersions) {
		lazyListHelper.setInternalList(StudySubjectConsentVersion.class,studySubjectConsentVersions);
	}

	public void addStudySubjectConsentVersion(StudySubjectConsentVersion studySubjectConsentVersion) {
		this.getStudySubjectConsentVersions().add(studySubjectConsentVersion);
	}

	public void setStudySubjectConsentVersions(List<StudySubjectConsentVersion> studySubjectConsentVersions) {
		setStudySubjectConsentVersionsInternal(studySubjectConsentVersions);
	}

	@ManyToOne
    @JoinColumn(name = "spa_id", nullable=false)
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE})
    @Where(clause = "reg_workflow_status  != 'INVALID'")
	public StudySubject getStudySubject() {
		return studySubject;
	}

	public void setStudySubject(StudySubject studySubject) {
		this.studySubject = studySubject;
	}

	@ManyToOne (fetch = FetchType.LAZY)
	 @JoinColumn(name = "study_site_ver_id", nullable=false)
	 @Cascade( { CascadeType.LOCK })
	public StudySiteStudyVersion getStudySiteStudyVersion() {
		return studySiteStudyVersion;
	}

	public void setStudySiteStudyVersion(
			StudySiteStudyVersion studySiteStudyVersion) {
		this.studySiteStudyVersion = studySiteStudyVersion;
	}

	@OneToMany(orphanRemoval=true)
	@Cascade( { CascadeType.ALL})
	@JoinColumn(name = "study_subject_ver_id")
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
	@Transient
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
	
	
	@Transient
	public boolean hasSignedConsent(Consent consent){
		for(StudySubjectConsentVersion studySubjectConsentVersion : getStudySubjectConsentVersions()){
			if (studySubjectConsentVersion.getConsent().equals(consent) && studySubjectConsentVersion.getInformedConsentSignedDate()!=null){
				return true;
			}
		}
		return false;
	}



}
