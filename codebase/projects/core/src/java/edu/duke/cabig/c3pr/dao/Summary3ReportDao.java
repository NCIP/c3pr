package edu.duke.cabig.c3pr.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.domain.AnatomicSite;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.Summary3Report;
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
	 * Gets the newly enrolled therapeutic study patients for given anatomic site.
	 * 
	 * @param diseaseSite the disease site
	 * @param hcs the hcs
	 * @param startDate the start date
	 * @param endDate the end date
	 * 
	 * @return the newly enrolled therapeutic study patients for given anatomic site
	 */
	public int getNewlyEnrolledTherapeuticStudyPatientsForGivenAnatomicSite(
			AnatomicSite diseaseSite, HealthcareSite hcs, Date startDate,
			Date endDate) {
		
		 return  getHibernateTemplate().find("from StudySubject ss,StudySubjectStudyVersion ssv where ssv=any elements(ss.studySubjectStudyVersions) and " +
		 		"ss.startDate >= ? and ss.startDate <= ? and " +
		 		"ss.diseaseHistoryInternal.anatomicSite.name = ? and ssv.studySiteStudyVersion.studySite.studyInternal.type = ? " +
		 		"and ssv.studySiteStudyVersion.studySite.healthcareSite.id in " +
		 		"(select h.id from HealthcareSite h where " +
  			    "h.identifiersAssignedToOrganization.value=? and h.identifiersAssignedToOrganization.primaryIndicator = 'TRUE')",
                new Object[] {startDate, endDate, diseaseSite.getName(), "Genetic Therapeutic", hcs.getCtepCode()}).size();
	}

	/**
	 * Gets the newly registered patients for given anatomic site.
	 * 
	 * @param diseaseSite the disease site
	 * @param hcs the hcs
	 * @param startDate the start date
	 * @param endDate the end date
	 * 
	 * @return the newly registered patients for given anatomic site
	 */
	public int getNewlyRegisteredPatientsForGivenAnatomicSite(AnatomicSite diseaseSite,
			HealthcareSite hcs, Date startDate, Date endDate) {
		
		 return  getHibernateTemplate().find("from StudySubject ss,StudySubjectStudyVersion ssv where ssv=any elements(ss.studySubjectStudyVersions) and" +
		 		" ss.startDate >= ? and ss.startDate <= ? and " +
		 		"ss.diseaseHistoryInternal.anatomicSite.name = ? and ssv.studySiteStudyVersion.studySite.healthcareSite.id in " +
		 		"(select h.id from HealthcareSite h where " +
		 		"h.identifiersAssignedToOrganization.value=? and h.identifiersAssignedToOrganization.primaryIndicator = 'TRUE'))",
                new Object[] {startDate, endDate, diseaseSite.getName(), hcs.getCtepCode()}).size();
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
	public int getNewlyEnrolledTherapeuticStudyPatients(HealthcareSite hcs, Date startDate,
			Date endDate) {
		
		 return  getHibernateTemplate().find("from StudySubject ss, StudySubjectStudyVersion ssv where ssv=any elements(ss.studySubjectStudyVersions)and " +
		 		"ss.startDate >= ? and ss.startDate <= ? and " +
		 		"ssv.studySiteStudyVersion.studySite.studyInternal.type = ? and ssv.studySiteStudyVersion.studySite.healthcareSite.id in " +
		 		"(select h.id from HealthcareSite h where " +
		 		"h.identifiersAssignedToOrganization.value=? and h.identifiersAssignedToOrganization.primaryIndicator = 'TRUE'))",
                new Object[] {startDate, endDate, "Genetic Therapeutic", hcs.getCtepCode()}).size();
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
	public int getNewlyRegisteredPatients(HealthcareSite hcs, Date startDate, Date endDate) {
		
		 return  getHibernateTemplate().find("from StudySubject ss, StudySubjectStudyVersion ssv where ssv=any elements(ss.studySubjectStudyVersions)and" +
		 		" ss.startDate >= ? and ss.startDate <= ? and " +
			 		"ssv.studySiteStudyVersion.studySite.healthcareSite.id in " +
			 		"(select h.id from HealthcareSite h, Identifier I where " +
			 		"h.identifiersAssignedToOrganization.value=? and h.identifiersAssignedToOrganization.primaryIndicator = 'TRUE'))",
	                new Object[] {startDate, endDate, hcs.getCtepCode()}).size();
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao#save(gov.nih.nci.cabig.ctms.domain.MutableDomainObject)
	 */
	public void save(Summary3Report arg0) {
		// not required
	}

}
