package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Reason;
import edu.duke.cabig.c3pr.domain.RegistryStatus;

/**
 * Hibernate implementation of ArmDao
 * 
 * @see RegistryStatusDao
 * @author Priyatam
 */
public class RegistryStatusDao extends GridIdentifiableDao<RegistryStatus> {

    @Override
    public Class<RegistryStatus> domainClass() {
        return RegistryStatus.class;
    }

    public List<RegistryStatus> getAll() {
        return getHibernateTemplate().find("from RegistryStatus");
    }
    
    public RegistryStatus getRegistryStatusByCode(String code){
    	return (RegistryStatus)getHibernateTemplate().find("from RegistryStatus where code = ?",code).get(0);
    } 
}
