package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.emory.mathcs.backport.java.util.Collections;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;


public class StudySubjectDao extends
		GridIdentifiableDao<StudySubject> implements MutableDomainObjectDao<StudySubject>{

	private List<String> SUBSTRING_MATCH_PROPERTIES = Arrays
			.asList("studySite.study.shortTitleText");

	private List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

	public StudySubjectDao() {
	}

	@Override
	public Class<StudySubject> domainClass() {
		return StudySubject.class;
	}

	/**
	 * @return list of matching registration objects based on the date 
	 * present in the sample object that is passsed in.
	 * Also takes the date range(startDate, endDate) and gets all objects 
	 * having their informedConsentSignedDate between these two dates.
	 * @param registration, startDate and endDate
	 * @return list of StudySubject objects.
	 */
	public List<StudySubject> advancedSearch(StudySubject registration, Date startDate, Date endDate, String ccId) {


		Criteria registrationCriteria = getHibernateTemplate()
				.getSessionFactory().getCurrentSession().createCriteria(
						StudySubject.class);

		Criteria studySiteCriteria = registrationCriteria.createCriteria("studySite");
		Criteria participantCriteria = registrationCriteria.createCriteria("participant");
		Criteria studyCriteria = studySiteCriteria.createCriteria("study");
		Criteria siteCriteria = studySiteCriteria.createCriteria("site");
		Criteria identifiersCriteria = studyCriteria.createCriteria("identifiersInternal");
		
		//Study Criteria
		if(registration.getStudySite().getStudy().getShortTitleText() != null && !registration.getStudySite().getStudy().getShortTitleText().equals("")){
			studyCriteria.add(Expression.like("shortTitleText", "%"+registration.getStudySite().getStudy().getShortTitleText()+"%"));
		}
		
		//Site criteria
		if(registration.getStudySite().getSite().getName() != null && !registration.getStudySite().getSite().getName().equals("")){
			siteCriteria.add(Expression.like("name", "%"+registration.getStudySite().getSite().getName()+"%"));
		}
		if(registration.getStudySite().getSite().getNciInstituteCode() != null && !registration.getStudySite().getSite().getNciInstituteCode().equals("")){
			siteCriteria.add(Expression.like("nciInstituteCode", "%"+registration.getStudySite().getSite().getNciInstituteCode()+"%"));
		}
					
		//registration criteria
		if(startDate != null && endDate != null){
			registrationCriteria.add(Expression.between("informedConsentSignedDate", startDate, endDate));
		}			
		
		//participant/subject criteria
		if(registration.getParticipant().getBirthDate() != null){
			participantCriteria.add(Expression.eq("birthDate", registration.getParticipant().getBirthDate()));
		}
		if(registration.getParticipant().getRaceCode() != null && !registration.getParticipant().getRaceCode().equals("")){
			participantCriteria.add(Expression.eq("raceCode", registration.getParticipant().getRaceCode()));
		}
		
		if(ccId != null && !ccId.equals("")){
//				identifiersCriteria.add(Expression.eq("type","Coordinating Center Identifier"));
			identifiersCriteria.add(Expression.eq("value", ccId));
		}
		registrationCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		registrationCriteria.addOrder(Order.asc("id"));
		return registrationCriteria.list();

	}

	/**
	 * *
	 * 
	 * @return list of matching registration objects based on your sample
	 *         registration object
	 * @param registration
	 * @return
	 */
	public List<StudySubject> searchByExample(
			StudySubject registration, boolean isWildCard) {

		Example example = Example.create(registration).excludeZeroes()
				.ignoreCase();
		Criteria registrationCriteria = getHibernateTemplate()
				.getSessionFactory().getCurrentSession().createCriteria(
						StudySubject.class);
		if (isWildCard) {
			example.excludeProperty("doNotUse").enableLike(MatchMode.ANYWHERE);
			registrationCriteria.add(example);
			if (registration.getIdentifiers().size() > 0) {
				registrationCriteria.createCriteria("identifiers").add(
						Restrictions.like("value", registration
								.getIdentifiers().get(0).getValue()
								+ "%"));
			} 
			return registrationCriteria.list();

		}
		return registrationCriteria.add(example).list();

	}

	public List<StudySubject> getAll() throws DataAccessException {
		return getHibernateTemplate().find("from StudySubject");
	}

	public List<StudySubject> searchByExample(
			StudySubject registration) {
		return searchByExample(registration, true);
	}

	public StudySubject getById(int id, boolean withIdentifiers) {

		StudySubject registration = (StudySubject) getHibernateTemplate()
				.get(domainClass(), id);
		if (withIdentifiers) {
			List<Identifier> identifiers = registration.getIdentifiers();
			int size = identifiers.size();
		}
		return registration;

	}

	public List<StudySubject> getBySubnames(String[] subnames,
			int criterionSelector) {

		switch (criterionSelector) {
		case 0:
			SUBSTRING_MATCH_PROPERTIES = Arrays.asList("participant.lastName");
			break;
		case 1:
			SUBSTRING_MATCH_PROPERTIES = Arrays.asList("participant.lastName");
			break;
		case 2:
			SUBSTRING_MATCH_PROPERTIES = Arrays
					.asList("studySite.study.shortTitleText");
			break;
		case 3:
			SUBSTRING_MATCH_PROPERTIES = Arrays
					.asList("studySite.study.longTitleText");
			break;
		case 4:
			SUBSTRING_MATCH_PROPERTIES = Arrays
					.asList("studySite.study.status");
		default:
			SUBSTRING_MATCH_PROPERTIES = Arrays
					.asList("studySite.study.status");

			break;
		}

		return findBySubname(subnames, SUBSTRING_MATCH_PROPERTIES,
				EXACT_MATCH_PROPERTIES);
	}
	public void reassociate(StudySubject spa) {
        getHibernateTemplate().lock(spa,LockMode.NONE);
     }

	public void save(StudySubject obj) {
		getHibernateTemplate().saveOrUpdate(obj);		
	}
	public StudySubject merge(StudySubject obj) {
		return (StudySubject)getHibernateTemplate().merge(obj);		
	}

}