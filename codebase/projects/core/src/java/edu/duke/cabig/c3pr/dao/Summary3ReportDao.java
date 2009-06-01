package edu.duke.cabig.c3pr.dao;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import edu.duke.cabig.c3pr.domain.AnatomicSite;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.Summary3Report;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

public class Summary3ReportDao extends GridIdentifiableDao<Summary3Report> implements MutableDomainObjectDao<Summary3Report> {

	Logger log = Logger.getLogger(Summary3Report.class);

	@Override
	public Class<Summary3Report> domainClass() {
		return Summary3Report.class;
	}

	public int getNewlyEnrolledTherapeuticStudyPatientsForGivenAnatomicSite(
			AnatomicSite diseaseSite, HealthcareSite hcs, Date startDate,
			Date endDate) {
		
		Criteria registrationCriteria = getHibernateTemplate()
				.getSessionFactory().getCurrentSession().createCriteria(
						StudySubject.class);

		Criteria studySiteCriteria = registrationCriteria
				.createCriteria("studySite");
		Criteria healthcareSiteCriteria= studySiteCriteria
				.createCriteria("healthcareSite");
		Criteria studyCriteria = studySiteCriteria.createCriteria("study");
		
		Criteria diseaseHistoryCriteria = registrationCriteria.createCriteria("diseaseHistoryInternal");
		Criteria anatomicSiteCriteria = diseaseHistoryCriteria.createCriteria("anatomicSite");
		

		// Study Criteria
			studyCriteria.add(Expression.ilike("type","Genetic Therapeutic"));

		// Site criteria
			healthcareSiteCriteria.add(Expression.eq("nciInstituteCode",hcs.getNciInstituteCode()));

		// registration criteria
			registrationCriteria.add(Expression.between(
					"startDate", startDate, endDate));

		// disease site criteria
			anatomicSiteCriteria.add(Expression.eq("name", diseaseSite.getName()));

		registrationCriteria
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		registrationCriteria.addOrder(Order.asc("id"));
		return registrationCriteria.list().size();

	}

	public int getNewlyRegisteredPatientsForGivenAnatomicSite(AnatomicSite diseaseSite,
			HealthcareSite hcs, Date startDate, Date endDate) {
		

		Criteria registrationCriteria = getHibernateTemplate()
				.getSessionFactory().getCurrentSession().createCriteria(
						StudySubject.class);

		Criteria studySiteCriteria = registrationCriteria
				.createCriteria("studySite");
		Criteria healthcareSiteCriteria= studySiteCriteria
				.createCriteria("healthcareSite");
		
		Criteria diseaseHistoryCriteria = registrationCriteria.createCriteria("diseaseHistoryInternal");
		Criteria anatomicSiteCriteria = diseaseHistoryCriteria.createCriteria("anatomicSite");
		
		// Site criteria
			healthcareSiteCriteria.add(Expression.ilike("nciInstituteCode",hcs.getNciInstituteCode()));

			// registration criteria
			registrationCriteria.add(Expression.between(
					"startDate", startDate, endDate));

		// participant/subject criteria
			anatomicSiteCriteria.add(Expression.eq("name",diseaseSite.getName()));

		registrationCriteria
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		registrationCriteria.addOrder(Order.asc("id"));
		return registrationCriteria.list().size();
	}
	
	public int getNewlyEnrolledTherapeuticStudyPatients(HealthcareSite hcs, Date startDate,
			Date endDate) {
		
		Criteria registrationCriteria = getHibernateTemplate()
				.getSessionFactory().getCurrentSession().createCriteria(
						StudySubject.class);

		Criteria studySiteCriteria = registrationCriteria
				.createCriteria("studySite");
		Criteria healthcareSiteCriteria= studySiteCriteria
				.createCriteria("healthcareSite");
		Criteria studyCriteria = studySiteCriteria.createCriteria("study");
		
		// Study Criteria
			studyCriteria.add(Expression.ilike("type","Genetic Therapeutic"));

		// Site criteria
			healthcareSiteCriteria.add(Expression.eq("nciInstituteCode",hcs.getNciInstituteCode()));

		// registration criteria
			registrationCriteria.add(Expression.between(
					"startDate", startDate, endDate));

		registrationCriteria
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		registrationCriteria.addOrder(Order.asc("id"));
		return registrationCriteria.list().size();

	}

	
	public int getNewlyRegisteredPatients(HealthcareSite hcs, Date startDate, Date endDate) {
		

		Criteria registrationCriteria = getHibernateTemplate()
				.getSessionFactory().getCurrentSession().createCriteria(
						StudySubject.class);

		Criteria studySiteCriteria = registrationCriteria
				.createCriteria("studySite");
		Criteria healthcareSiteCriteria= studySiteCriteria
				.createCriteria("healthcareSite");
		
		// Site criteria
			healthcareSiteCriteria.add(Expression.ilike("nciInstituteCode",hcs.getNciInstituteCode()));

			// registration criteria
			registrationCriteria.add(Expression.between(
					"startDate", startDate, endDate));

		registrationCriteria
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		registrationCriteria.addOrder(Order.asc("id"));
		return registrationCriteria.list().size();
	}

	public void save(Summary3Report arg0) {
		// not required
		
	}


}
