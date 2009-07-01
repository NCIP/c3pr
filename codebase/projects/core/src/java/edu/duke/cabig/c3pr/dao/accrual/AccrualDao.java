package edu.duke.cabig.c3pr.dao.accrual;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.dao.AnatomicSiteDao;
import edu.duke.cabig.c3pr.dao.GridIdentifiableDao;
import edu.duke.cabig.c3pr.domain.AnatomicSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.accrual.Accrual;
import edu.duke.cabig.c3pr.domain.accrual.DiseaseSiteAccrualReport;
import edu.duke.cabig.c3pr.domain.accrual.SiteAccrualReport;
import edu.duke.cabig.c3pr.domain.accrual.StudyAccrualReport;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

/**
 * The Class AccrualDao.
 */
public class AccrualDao extends GridIdentifiableDao<Accrual> implements
		MutableDomainObjectDao<Accrual> {

	/** The anatomic site dao. */
	private AnatomicSiteDao anatomicSiteDao;

	/**
	 * Sets the anatomic site dao.
	 * 
	 * @param anatomicSiteDao the new anatomic site dao
	 */
	public void setAnatomicSiteDao(AnatomicSiteDao anatomicSiteDao) {
		this.anatomicSiteDao = anatomicSiteDao;
	}

	/**
	 * Gets the study accrual reports.
	 * 
	 * @param nciInstituteCode the nci institute code
	 * @param shortTitleText the short title text
	 * 
	 * @return the study accrual reports
	 */
	public List<StudyAccrualReport> getStudyAccrualReports(String nciInstituteCode,String shortTitleText) {
		List<Study> studies = new ArrayList<Study>();
		if(shortTitleText== null){
			studies = (List<Study>) getHibernateTemplate().find(
					"Select s from Study s, StudySite ss where ss = any elements(s.studyOrganizations) and ss.healthcareSite.id in " +
					"(select h.id from HealthcareSite h, Identifier I where " +
	    			"I.value=? and I.typeInternal=? and I=any elements(h.identifiersAssignedToOrganization))",
					new Object[]{nciInstituteCode, OrganizationIdentifierTypeEnum.CTEP.getName()});
		} else {
			studies = (List<Study>) getHibernateTemplate().find(
					"Select s from Study s, StudySite ss, StudyVersion sv where ss = any elements(s.studyOrganizations) and sv.shortTitleText = ? and ss.healthcareSite.id in " +
					"(select h.id from HealthcareSite h, Identifier I where " +
	    			"I.value=? and I.typeInternal=? and I=any elements(h.identifiersAssignedToOrganization))",
					new Object[]{shortTitleText, nciInstituteCode, OrganizationIdentifierTypeEnum.CTEP.getName()});
		}

		List<StudyAccrualReport> studyAccrualReports = new ArrayList<StudyAccrualReport>();
		for (Study study : studies) {
			StudyAccrualReport studyAccrualReport = new StudyAccrualReport();
			studyAccrualReport.setShortTitle(study.getShortTitleText());
			studyAccrualReport.setIdentifier(study.getPrimaryIdentifier());

			List<DiseaseSiteAccrualReport> diseaseSiteAccrualReports = new ArrayList<DiseaseSiteAccrualReport>();
			for (AnatomicSite diseaseSite : study.getDiseaseSites()) {
				DiseaseSiteAccrualReport diseaseSiteAccrualReport = new DiseaseSiteAccrualReport();
				diseaseSiteAccrualReport.setName(diseaseSite.getName());
				Accrual accrual = new Accrual();
				accrual.setValue(getAccrual(nciInstituteCode,shortTitleText,diseaseSite.getName()));
				diseaseSiteAccrualReport.setAccrual(accrual);
				diseaseSiteAccrualReports.add(diseaseSiteAccrualReport);
			}
			studyAccrualReport
					.setDiseaseSiteAccrualReports(diseaseSiteAccrualReports);
			studyAccrualReports.add(studyAccrualReport);
		}

		return studyAccrualReports;
	}

	/**
	 * Gets the site accrual.
	 * 
	 * @param siteAccrualReport the site accrual report
	 * @param diseaseSiteName the disease site name
	 * @param studyShortTitleText the study short title text
	 * @param startDate the start date
	 * @param endDate the end date
	 * 
	 * @return the site accrual
	 */
	public int getSiteAccrual(SiteAccrualReport siteAccrualReport,
			String diseaseSiteName,
			String studyShortTitleText, Date startDate, Date endDate) {

		Criteria registrationCriteria = getHibernateTemplate()
				.getSessionFactory().getCurrentSession().createCriteria(
						StudySubject.class);

		Criteria studySiteCriteria = registrationCriteria
				.createCriteria("studySite");
		Criteria healthcareSiteCriteria = studySiteCriteria
				.createCriteria("healthcareSite");
		Criteria identifiersAssignedToOrganizationCriteria = healthcareSiteCriteria.createCriteria("identifiersAssignedToOrganization");
		
		Criteria studyCriteria = studySiteCriteria.createCriteria("study");

		Criteria diseaseHistoryCriteria = registrationCriteria
				.createCriteria("diseaseHistoryInternal");
		Criteria anatomicSiteCriteria = diseaseHistoryCriteria
				.createCriteria("anatomicSite");

		// Study Criteria
		
		if(studyShortTitleText!=null){
			studyCriteria.add(Expression.eq("shortTitleText",studyShortTitleText));
		}

		// Site criteria
		//healthcareSiteCriteria.add(Expression.eq("nciInstituteCode", siteAccrualReport.getCtepId()));
		identifiersAssignedToOrganizationCriteria.add(Expression.ilike("value", "%"
		                    + siteAccrualReport.getCtepId() + "%"));
		
		// registration criteria
		
		if(startDate!=null && endDate!=null){
			registrationCriteria.add(Expression.between("startDate", startDate,
				endDate));
		}else if (startDate!=null && endDate==null){
			registrationCriteria.add(Expression.ge("startDate", startDate));
		}else if (startDate==null && endDate!=null){
			registrationCriteria.add(Expression.le("startDate", endDate));
		}

		// disease site criteria
		
		if(diseaseSiteName!=null){
			anatomicSiteCriteria.add(Expression.eq("name", diseaseSiteName));
		}

		registrationCriteria
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		registrationCriteria.addOrder(Order.asc("id"));
		return registrationCriteria.list().size();
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
	 */
	@Override
	public Class<Accrual> domainClass() {
		return Accrual.class;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao#save(gov.nih.nci.cabig.ctms.domain.MutableDomainObject)
	 */
	public void save(Accrual arg0) {
		// TODO Auto-generated method stub

	}
	
	public List<DiseaseSiteAccrualReport> getAllDiseaseSiteAccrualReports(String nciInstituteCode){
		List<AnatomicSite> anatomicSites = (List<AnatomicSite>) getHibernateTemplate().find("from AnatomicSite order by name");
		
		List<DiseaseSiteAccrualReport> diseaseSiteAccrualReports = new ArrayList<DiseaseSiteAccrualReport>();
		
		for(AnatomicSite anatomicSite:anatomicSites){
			DiseaseSiteAccrualReport diseaseSiteAccrualReport = new DiseaseSiteAccrualReport();
			diseaseSiteAccrualReport.setName(anatomicSite.getName());
			
			Accrual accrual = new Accrual();
			accrual.setValue(getAccrual(nciInstituteCode, null, anatomicSite.getName()));
			diseaseSiteAccrualReport.setAccrual(accrual);
			
			diseaseSiteAccrualReports.add(diseaseSiteAccrualReport);
		}
		
		return diseaseSiteAccrualReports;
	}
	
	public int getAccrual(String nciInstituteCode, String shortTitleText,String diseaseSteName){
    	int accrual = 0;
    	
    	if(shortTitleText != null){
    		accrual = getHibernateTemplate().find(
    				"Select ss from StudySubject ss where ss.studySite.study.shortTitleText = ? " +
    				"and ss.diseaseHistoryInternal.anatomicSite.name = ? and ss.studySite.healthcareSite.id in " +
    				"(select h.id from HealthcareSite h, Identifier I where " +
	    			"I.value=? and I.typeInternal=? and I=any elements(h.identifiersAssignedToOrganization))",
					new Object[]{shortTitleText, diseaseSteName, nciInstituteCode, OrganizationIdentifierTypeEnum.CTEP.getName()}).size();
    	}else {
    		accrual = getHibernateTemplate().find(
    				"Select ss from StudySubject ss where ss.diseaseHistoryInternal.anatomicSite.name = ? and " +
    				"ss.studySite.healthcareSite.id in " +
    				"(select h.id from HealthcareSite h, Identifier I where " +
	    			"I.value=? and I.typeInternal=? and I=any elements(h.identifiersAssignedToOrganization))",
    				new Object[]{diseaseSteName, nciInstituteCode, OrganizationIdentifierTypeEnum.CTEP.getName()}).size();
    	}
    	
    	return accrual;
    }
	
}
