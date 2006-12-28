package edu.duke.cabig.c3pr.dao.hibernate;

import java.util.List;

import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.dao.AbstractBaseDao;
import edu.duke.cabig.c3pr.dao.ArmDao;
import edu.duke.cabig.c3pr.domain.Arm;

/**
 * Hibernate implementation of ArmDao
 * @see edu.duke.cabig.c3pr.dao.ArmDao
 * @author Priyatam
 */
public class ArmDaoHibernate extends AbstractBaseDao<Arm> implements ArmDao{

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
