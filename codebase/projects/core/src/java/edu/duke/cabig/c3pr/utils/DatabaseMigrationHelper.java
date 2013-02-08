/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.OffEpochReasonDao;
import edu.duke.cabig.c3pr.dao.ReasonDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.OffEpochReason;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;

public class DatabaseMigrationHelper extends HibernateDaoSupport {
	
	private EpochDao epochDao;
	
	private OffEpochReasonDao offEpochReasonDao;
	
	private ReasonDao reasonDao;
	
	private StudySubjectDao studySubjectDao;

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	public void setReasonDao(ReasonDao reasonDao) {
		this.reasonDao = reasonDao;
	}

	public void setEpochDao(EpochDao epochDao) {
		this.epochDao = epochDao;
	}

	public boolean isDatabaseMigrationNeeded(){
		return getMigratableStudies().size()>0 || getMigratableStudySubjects().size()>0;
	}
	
	public void updateEpochWithType(int epochId, EpochType epochType){
		Epoch epoch = epochDao.getById(epochId);
		epoch.setType(epochType);
		epochDao.merge(epoch);
	}
	
	public void updateOffEpochReasonWithReason(int offEpochReasonId, int reasonId){
		OffEpochReason offEpochReason = offEpochReasonDao.getById(offEpochReasonId);
		offEpochReason.setReason(reasonDao.getById(reasonId));
		offEpochReasonDao.save(offEpochReason);
	}
	
	public void updateOffStudyWithNotRegistered(){
		List<StudySubject> studySubjects = (List<StudySubject>)getHibernateTemplate().find(
				"select ss from StudySubject ss where ss.regWorkflowStatus='OFF_STUDY'");
		for(StudySubject studySubject : studySubjects){
			boolean hasEnrollingScheduledEpoch = false;
			for(ScheduledEpoch scheduledEpoch : studySubject.getScheduledEpochs()){
				if(scheduledEpoch.getEpoch().getEnrollmentIndicator()){
					hasEnrollingScheduledEpoch = true;
					break;
				}
			}
			if(!hasEnrollingScheduledEpoch){
				studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.NOT_REGISTERED);
				studySubjectDao.merge(studySubject);
			}
		}
	}
	
	public List<Study> getMigratableStudies(){
		return getHibernateTemplate().find("select distinct s from Study s join s.studyVersionsInternal sv join sv.epochsInternal e where e.type is null");
	}
	
	public List<StudySubject> getMigratableStudySubjects(){
		return getHibernateTemplate().find("select distinct ss from StudySubject ss join ss.studySubjectStudyVersions ssv join ssv.scheduledEpochs se join se.offEpochReasons oer where oer.reason is null");
	}

	public void setOffEpochReasonDao(OffEpochReasonDao offEpochReasonDao) {
		this.offEpochReasonDao = offEpochReasonDao;
	}
}
