package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;


/**
 * Hibernate implementation of StudyDao
 * @author Priyatam
 */
public class StudyDao extends AbstractBaseDao<Study> {
	
	@Override
	public Class<Study> domainClass() {
		return Study.class;
	 }
	
	public Study getStudyDesignById(int id) {				
        Study study =  (Study) getHibernateTemplate().get(domainClass(), id);
        study.getIdentifiers().size();
        study.getStudySites().size();
        List<Epoch> epochs = study.getEpochs();
        epochs.size();
        for (Epoch epoch : epochs) {
			epoch.getArms().size();
		}
        return study;
    }
	
	/*
	 * Searches based on an example object. Typical usage from your service class: -
	 * If you want to search based on diseaseCode, monitorCode,
	 * <li><code>Study study = new Study();</li></code>
	 * <li>code>study.setDiseaseCode("diseaseCode");</li></code>
	 * <li>code>study.setDMonitorCode("monitorCode");</li></code>
	 * <li>code>studyDao.searchByExample(study)</li></code>
	 * @return list of matching study objects based on your sample study object
	 */
	public List<Study> searchByExample(Study study, boolean isWildCard) {
		Example example = Example.create(study).excludeZeroes().ignoreCase();
		Criteria studyCriteria = getSession().createCriteria(Study.class);
	
		if (isWildCard)
		{
			example.excludeProperty("doNotUse").enableLike(MatchMode.ANYWHERE);
			studyCriteria.add(example);
			if (study.getIdentifiers().size() > 0) {
				studyCriteria.createCriteria("identifiers")
					.add(Restrictions.like("value", study.getIdentifiers().get(0)
					.getValue()+ "%"));
			} 
			return studyCriteria.list();
		}
		return studyCriteria.add(example).list();
	}
	
	/**
	 * Default Search without a Wildchar
	 * @param study
	 * @return Search Results
	 */
	public List<Study> searchByExample(Study study) {
		return searchByExample(study, false);
	}
	
	/**
	 * Returns all study objects
	 * @return list of study objects
	 */
	public List<Study> getAll(){
		 return getHibernateTemplate().find("from Study");
	 }

	/**
	 * Get all Arms associated with all of this study's epochs 
	 * @param studyId the study id
	 * @return list of Arm objects given a study id 
	 */
	public List<Arm> getArmsForStudy(Integer studyId) {
		return getHibernateTemplate().find("select a from Study s join s.epochs e join e.arms a " +
		"where s.id = ?", studyId);
	}
	
	/**
	 * Get all Assignments associated with the given study 
	 * @param studyId the study id
	 * @return list of StudyParticipantAssignments 
	 */
	 public List<StudyParticipantAssignment> getStudyParticipantAssignmentsForStudy(Integer studyId) {
		 return getHibernateTemplate().find("select a from Study s join s.studySites ss " +
		 	"join ss.studyParticipantAssignments a where s.id = ?", studyId);
	 }
}
