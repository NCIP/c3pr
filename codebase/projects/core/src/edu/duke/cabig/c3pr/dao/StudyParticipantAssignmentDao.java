package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;

public class StudyParticipantAssignmentDao extends AbstractBaseDao<StudyParticipantAssignment> {
    public StudyParticipantAssignmentDao() {
    }
    
    @Override
    public Class<StudyParticipantAssignment> domainClass() {
            return StudyParticipantAssignment.class;
     }
    
    /**
	 * /* Searches based on an example object. Typical usage from your service
	 * class: - If you want to search based on diseaseCode, monitorCode,
	 * <li><code>Participant participant = new Participant();</li></code>
	 * <li>code>participant.setLastName("last_namee");</li>
	 * </code>
	 * <li>code>participantDao.searchByExample(study)</li>
	 * </code>
	 * 
	 * @return list of matching participant objects based on your sample
	 *         participant object
	 * @param participant
	 * @return
	 */
	public List<StudyParticipantAssignment> searchByExample(StudyParticipantAssignment registration, boolean isWildCard) {
		Example example = Example.create(registration).excludeZeroes().ignoreCase();
		Criteria registrationCriteria = getSession().createCriteria(
			StudyParticipantAssignment.class);
		if (isWildCard) {
			example.excludeProperty("doNotUse").enableLike(MatchMode.ANYWHERE);
			registrationCriteria.add(example);
			if (registration.getIdentifiers().size() > 0) {
				registrationCriteria.createCriteria("identifiers").add(
					Restrictions.like("value", registration.getIdentifiers()
					.get(0).getValue()+ "%"));
			}
			return registrationCriteria.list();

		}
		return registrationCriteria.add(example).list();

	}
    
    public StudyParticipantAssignment getById(int id, boolean withIdentifiers) {

		StudyParticipantAssignment registration = (StudyParticipantAssignment) getHibernateTemplate().get(
				domainClass(), id);
		if (withIdentifiers) {
			List<Identifier> identifiers = registration.getIdentifiers();
			int size = identifiers.size();
		}

		return registration ;

	}
}
