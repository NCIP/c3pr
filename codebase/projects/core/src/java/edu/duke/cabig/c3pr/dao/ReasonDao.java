/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.OffFollowupReason;
import edu.duke.cabig.c3pr.domain.OffReservingReason;
import edu.duke.cabig.c3pr.domain.OffScreeningReason;
import edu.duke.cabig.c3pr.domain.OffStudyReason;
import edu.duke.cabig.c3pr.domain.OffTreatmentReason;
import edu.duke.cabig.c3pr.domain.Reason;

/**
 * Hibernate implementation of ScheduledEpoch
 * 
 * @see edu.duke.cabig.c3pr.dao.ScheduledEpoch
 * @author Priyatam
 */
public class ReasonDao extends GridIdentifiableDao<Reason> {

    @Override
    public Class<Reason> domainClass() {
        return Reason.class;
    }
    
    public List<OffTreatmentReason> getOffTreatmentReasons(){
    	return getHibernateTemplate().find("from OffTreatmentReason");
    }
    
    public List<OffStudyReason> getOffStudyReasons(){
    	return getHibernateTemplate().find("from OffStudyReason");
    }
    
    public List<OffScreeningReason> getOffScreeningReasons(){
    	return getHibernateTemplate().find("from OffScreeningReason");
    }
    
    public List<OffReservingReason> getOffReservingReasons(){
    	return getHibernateTemplate().find("from OffReservingReason");
    }
    
    public List<OffFollowupReason> getOffFollowupReasons(){
    	return getHibernateTemplate().find("from OffFollowupReason");
    }
    
    public Reason getReasonByCode(String code){
    	return (Reason)getHibernateTemplate().find("from Reason where code = ?",code).get(0);
    }
}
