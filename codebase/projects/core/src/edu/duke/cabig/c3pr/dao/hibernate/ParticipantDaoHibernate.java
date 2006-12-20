package edu.duke.cabig.c3pr.dao.hibernate;

import edu.duke.cabig.c3pr.dao.AbstractBaseDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Participant;

/**
 * @author kulasekaran
 * @version 1.0
 */
public class ParticipantDaoHibernate extends AbstractBaseDao<Participant> implements ParticipantDao {
	
	public Class<Participant> domainClass() {
	        return Participant.class;
	 }
	
	 public void saveParticipant(Participant participant) throws Exception {
		 getHibernateTemplate().saveOrUpdate(participant);		
	}
}
