package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.domain.OffFollowupReason;
import edu.duke.cabig.c3pr.domain.OffReservingReason;
import edu.duke.cabig.c3pr.domain.OffScreeningReason;
import edu.duke.cabig.c3pr.domain.OffStudyReason;
import edu.duke.cabig.c3pr.domain.OffTreatmentReason;
import edu.duke.cabig.c3pr.domain.Reason;
import gov.nih.nci.logging.api.util.StringUtils;


/**
 * The Class ReasonDao.
 */
public class ReasonDao extends GridIdentifiableDao<Reason> {

   /** The log. */
    private static Log log = LogFactory.getLog(ReasonDao.class);
 
    
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
    
    /**
     * Gets the reason by code and type. Returns null if no match is found.
     *
     * @param code the code
     * @param classname the classname
     * @return the reason by code and type
     */
    public Reason getReasonByCodeAndType(String code, String className){
    	if(StringUtils.isBlank(className)){
    		return null;
    	}
    	String query = getClassConditionPartOfSearchQuery(className);
    	List<Reason> reasonList = getHibernateTemplate().find("from Reason r where code = ?" + query,
    															new Object[] {code});
    	if(reasonList.size() > 0){
    		if(reasonList.size() > 1){
    			log.error("Reason table has more then one record with code: "+code+ " and dtype: "+className);
    		}
    		return reasonList.get(0);
    	}
    	return null;
    }

	private String getClassConditionPartOfSearchQuery(String className) {
		String modifiedClassName = className.substring(className.lastIndexOf(".")+1);
		return "and r.class = "+modifiedClassName;
	}
    
}

