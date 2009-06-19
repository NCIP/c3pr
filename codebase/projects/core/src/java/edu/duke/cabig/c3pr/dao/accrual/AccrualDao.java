package edu.duke.cabig.c3pr.dao.accrual;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

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

public class AccrualDao extends GridIdentifiableDao<Accrual> implements
		MutableDomainObjectDao<Accrual> {

	private AnatomicSiteDao anatomicSiteDao;

	public void setAnatomicSiteDao(AnatomicSiteDao anatomicSiteDao) {
		this.anatomicSiteDao = anatomicSiteDao;
	}

	public List<StudyAccrualReport> getStudyAccrualReports() {
		List<Study> studies = new ArrayList<Study>();
		studies = (List<Study>) getHibernateTemplate().find("from Study");

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
				accrual.setValue(anatomicSiteDao.getTotalAccrual(diseaseSite));
				diseaseSiteAccrualReport.setAccrual(accrual);
				diseaseSiteAccrualReports.add(diseaseSiteAccrualReport);
			}
			studyAccrualReport
					.setDiseaseSiteAccrualReports(diseaseSiteAccrualReports);
			studyAccrualReports.add(studyAccrualReport);
		}

		return studyAccrualReports;
	}

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
		healthcareSiteCriteria.add(Expression.eq("nciInstituteCode", siteAccrualReport.getCtepId()));

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

	@Override
	public Class<Accrual> domainClass() {
		return Accrual.class;
	}

	public void save(Accrual arg0) {
		// TODO Auto-generated method stub

	}
}
