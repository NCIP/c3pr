package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Arm;

/**
 * Hibernate implementation of ArmDao
 * @see edu.duke.cabig.c3pr.dao.ArmDao
 * @author Priyatam
 */
public class ArmDao extends AbstractBaseDao<Arm>{

	@Override
	public Class<Arm> domainClass() {
		return Arm.class;
	 }
	
	/*
	 * Returns all Arm objects
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.Arm#getAll()
	 */
	 public List<Arm> getAll(){
		 return getHibernateTemplate().find("from Arm");
	 }
}
