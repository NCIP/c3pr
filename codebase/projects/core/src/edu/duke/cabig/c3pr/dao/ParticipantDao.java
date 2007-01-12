package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;

/**
 * @author Priyatam, kulasekaran
 */
public class ParticipantDao extends AbstractBaseDao<Participant> {

	public Class<Participant> domainClass() {
		return Participant.class;
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
	public List<Participant> searchByExample(Participant participant,
			boolean isWildCard) {
		Example example = Example.create(participant).excludeZeroes();
		Criteria participantCriteria = getSession().createCriteria(
				Participant.class);
		if (isWildCard) {
			example.ignoreCase().excludeProperty("doNotUse").enableLike(
					MatchMode.ANYWHERE);
			participantCriteria.add(example);
	//		FIXME:
			if (participant.getIdentifiers().size() > 0) {
				participantCriteria.createCriteria("identifiers")
						.add(
								Restrictions.like("value",
										participant.getIdentifiers()
												.get(0)
												.getValue()
												+ "%"));
			} 
			return participantCriteria.list();

		}
		return participantCriteria.add(example).list();

	}

	/**
	 * Default Search without a Wildchar
	 * 
	 * @see edu.duke.cabig.c3pr.dao.searchByExample(Participant participant,
	 *      boolean isWildCard)
	 * @param participant
	 * @return Search results
	 */
	public List<Participant> searchByExample(Participant participant) {
		return searchByExample(participant, true);
	}

	/**
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List<Participant> getAll() throws DataAccessException {
		return getHibernateTemplate().find("from Participant");
	}
}
