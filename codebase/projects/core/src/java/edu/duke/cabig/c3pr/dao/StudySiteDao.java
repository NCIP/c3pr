package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.StudySite;

/**
 * Hibernate implementation of StudySiteDao
 * @see edu.duke.cabig.c3pr.dao.StudySiteDao
 * @author Priyatam
 */
public class StudySiteDao extends GridIdentifiableDao<StudySite> {

	@Override
	public Class<StudySite> domainClass() {
		return StudySite.class;
	 }
	
	/*
	 * Returns all StudySite objects
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.StudySite#getAll()
	 */
	 public List<StudySite> getAll() {
		 return getHibernateTemplate().find("from StudySite");
	 }

     public void reassociate(StudySite ss) {
	        getHibernateTemplate().update(ss);
	     }

    public List<StudySite> getByNciInstituteCode(String nciInstituteCode){
          return  getHibernateTemplate().find("from StudySite s where s.healthcareSite.nciInstituteCode = ?", nciInstituteCode);
    }
	 	 	
}
