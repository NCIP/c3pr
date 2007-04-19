package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.emory.mathcs.backport.java.util.Collections;

public class StudyParticipantAssignmentDao extends
		AbstractBaseDao<StudyParticipantAssignment> {

	private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays
			.asList("treatingPhysician");

	private static final List<String> EXACT_MATCH_PROPERTIES = Collections
			.emptyList();

	public StudyParticipantAssignmentDao() {
	}

	@Override
	public Class<StudyParticipantAssignment> domainClass() {
		return StudyParticipantAssignment.class;
	}

	/**
	 *  * @return list of matching registration objects based on your sample
	 *         registration object
	 * @param registration
	 * @return
	 */
	public List<StudyParticipantAssignment> searchByExample(
			StudyParticipantAssignment registration, boolean isWildCard) {
		
		Example example = Example.create(registration).excludeZeroes()
				.ignoreCase();
		Criteria registrationCriteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(StudyParticipantAssignment.class);
		if (isWildCard) {
			example.excludeProperty("doNotUse").enableLike(MatchMode.ANYWHERE);
			registrationCriteria.add(example);
			if (registration.getIdentifiers().size() > 0) {
				registrationCriteria.createCriteria("identifiers").add(
						Restrictions.like("value", registration
								.getIdentifiers().get(0).getValue()
								+ "%"));
			}else
				if (registration.getParticipant()!=null) {
						registrationCriteria.createCriteria("participant").add(
							Restrictions.like("lastName", registration.getParticipant().getLastName()+ "%"));
																
				}
				else
					if (registration.getStudySite()!=null) {
						registrationCriteria.createCriteria("studySite.study").add(
								Restrictions.like("shortTitleText", registration.getStudySite().getStudy().getShortTitleText()+ "%"));
																	
					}
			return registrationCriteria.list();

		}
		return registrationCriteria.add(example).list();

	}
		
		public List<StudyParticipantAssignment> getAll() throws DataAccessException {
			return getHibernateTemplate().find("from StudyParticipantAssignmet");
		}
	public List<StudyParticipantAssignment> searchByExample(StudyParticipantAssignment registration) {
		return searchByExample(registration, true);
	}

	public StudyParticipantAssignment getById(int id, boolean withIdentifiers) {

		StudyParticipantAssignment registration = (StudyParticipantAssignment) getHibernateTemplate()
				.get(domainClass(), id);
		if (withIdentifiers) {
			List<Identifier> identifiers = registration.getIdentifiers();
			int size = identifiers.size();
		}
		return registration;

	}

	public List<StudyParticipantAssignment> getBySubnames(String[] subnames) {
		return findBySubname(subnames, SUBSTRING_MATCH_PROPERTIES,
				EXACT_MATCH_PROPERTIES);
	}

}
