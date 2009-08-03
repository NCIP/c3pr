package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "stu_sub_stu_versions")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STU_SUB_STU_VERSION_ID_SEQ") })
public class StudySubjectStudyVersion extends AbstractMutableDeletableDomainObject{

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

}
