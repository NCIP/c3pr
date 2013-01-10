/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Summary3Report;
import edu.duke.cabig.c3pr.domain.Summary3ReportDiseaseSite;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

// TODO: Auto-generated Javadoc
/**
 * The Class Summary3ReportDao.
 */
public class Summary3ReportDao extends GridIdentifiableDao<Summary3Report> implements MutableDomainObjectDao<Summary3Report> {

	/** The log. */
	Logger log = Logger.getLogger(Summary3Report.class);

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
	 */
	@Override
	public Class<Summary3Report> domainClass() {
		return Summary3Report.class;
	}

	/**
	 * Gets the newly enrolled therapeutic study patients for given icd9 disease site.
	 * 
	 * @param summary3ReportDiseaseSite the disease site
	 * @param hcs the hcs
	 * @param startDate the start date
	 * @param endDate the end date
	 * 
	 * @return the newly enrolled therapeutic study patients for given anatomic site
	 */
	public int getNewlyEnrolledTherapeuticStudySubjectCountForGivenSummary3ReportDiseaseSite(
			Summary3ReportDiseaseSite summary3ReportDiseaseSite, HealthcareSite hcs, Date startDate,
			Date endDate) {
		
		 return  getHibernateTemplate().find("from StudySubject ss,StudySubjectStudyVersion ssv where ssv=any elements(ss.studySubjectStudyVersions) and " +
		 		"ss.startDate >= ? and ss.startDate <= ? and ss.regWorkflowStatus != 'PENDING' and " +
		 		"ss.diseaseHistoryInternal.icd9DiseaseSite.summary3ReportDiseaseSite.name = ? and ssv.studySiteStudyVersion.studySite.studyInternal.therapeuticIntentIndicator = '1' " +
		 		"and ssv.studySiteStudyVersion.studySite.healthcareSite.id in " +
		 		"(select h.id from HealthcareSite h where " +
  			    "h.identifiersAssignedToOrganization.value=? and h.identifiersAssignedToOrganization.primaryIndicator = '1')",
                new Object[] {startDate, endDate,summary3ReportDiseaseSite.getName(), hcs.getCtepCode()}).size();
	}

	/**
	 * Gets the newly registered patients for given anatomic site.
	 * 
	 * @param summary3ReportDiseaseSite the disease site
	 * @param hcs the hcs
	 * @param startDate the start date
	 * @param endDate the end date
	 * 
	 * @return the newly registered patients for given anatomic site
	 */
	public int getNewlyRegisteredSubjectCountForGivenSummary3ReportDiseaseSite(Summary3ReportDiseaseSite summary3ReportDiseaseSite,
			HealthcareSite hcs, Date startDate, Date endDate) {
		//commented out because C3PR is not the source of truth for newly registered patients.
		 /*return  getHibernateTemplate().find("from StudySubject ss,StudySubjectStudyVersion ssv where ssv=any elements(ss.studySubjectStudyVersions) and" +
		 		" ss.startDate >= ? and ss.startDate <= ? and ss.regWorkflowStatus != 'PENDING' and " +
		 		"ss.diseaseHistoryInternal.icd9DiseaseSite.name = ? and ssv.studySiteStudyVersion.studySite.healthcareSite.id in " +
		 		"(select h.id from HealthcareSite h where " +
		 		"h.identifiersAssignedToOrganization.value=? and h.identifiersAssignedToOrganization.primaryIndicator = '1'))",
                new Object[] {startDate, endDate, summary3ReportDiseaseSite.getName(), hcs.getCtepCode()}).size();*/
		
		return 0;
	}
	
	/**
	 * Gets the newly enrolled therapeutic study patients.
	 * 
	 * @param hcs the hcs
	 * @param startDate the start date
	 * @param endDate the end date
	 * 
	 * @return the newly enrolled therapeutic study patients
	 */
	public int getNewlyEnrolledTherapeuticStudySubjectCount(HealthcareSite hcs, Date startDate,
			Date endDate) {
		
		 return  getHibernateTemplate().find("from StudySubject ss, StudySubjectStudyVersion ssv where ssv=any elements(ss.studySubjectStudyVersions)and " +
		 		"ss.startDate >= ? and ss.startDate <= ? and ss.regWorkflowStatus != 'PENDING' and " +
		 		"ssv.studySiteStudyVersion.studySite.studyInternal.therapeuticIntentIndicator = '1' and ssv.studySiteStudyVersion.studySite.healthcareSite.id in " +
		 		"(select h.id from HealthcareSite h where " +
		 		"h.identifiersAssignedToOrganization.value=? and h.identifiersAssignedToOrganization.primaryIndicator = '1'))",
                new Object[] {startDate, endDate, hcs.getCtepCode()}).size();
	}
	
	/**
	 * Gets the newly registered patients.
	 * 
	 * @param hcs the hcs
	 * @param startDate the start date
	 * @param endDate the end date
	 * 
	 * @return the newly registered patients
	 */
	public int getNewlyRegisteredSubjectCount(HealthcareSite hcs, Date startDate, Date endDate) {
		
		 return  getHibernateTemplate().find("from StudySubject ss, StudySubjectStudyVersion ssv where ssv=any elements(ss.studySubjectStudyVersions)and" +
		 		" ss.startDate >= ? and ss.regWorkflowStatus != 'PENDING' and ss.startDate <= ? and " +
			 		"ssv.studySiteStudyVersion.studySite.healthcareSite.id in " +
			 		"(select h.id from HealthcareSite h, Identifier I where " +
			 		"h.identifiersAssignedToOrganization.value=? and h.identifiersAssignedToOrganization.primaryIndicator = '1'))",
	                new Object[] {startDate, endDate, hcs.getCtepCode()}).size();
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao#save(gov.nih.nci.cabig.ctms.domain.MutableDomainObject)
	 */
	public void save(Summary3Report arg0) {
		// not required
	}
	
	 public List<Summary3ReportDiseaseSite> getAllOrderedByName(){
	    	Criteria summary3ReportDiseaseSiteCriteria =getSession().createCriteria(Summary3ReportDiseaseSite.class);
	    	summary3ReportDiseaseSiteCriteria.addOrder(Order.asc("name"));
	    	summary3ReportDiseaseSiteCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	    	return summary3ReportDiseaseSiteCriteria.list();
	    }

}
