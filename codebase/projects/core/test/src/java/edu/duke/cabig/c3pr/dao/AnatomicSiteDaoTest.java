package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.AnatomicSite;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * The Class AnatomicSiteDaoTest.
 */
public class AnatomicSiteDaoTest extends DaoTestCase {

	/** The anatomic site dao. */
	private AnatomicSiteDao anatomicSiteDao;
	
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.utils.DaoTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		anatomicSiteDao=(AnatomicSiteDao)getApplicationContext().getBean("anatomicSiteDao");
	}
	
	/**
	 * Test domain class.
	 */
	public void testDomainClass() {
		assertEquals("Wrong Domain Class", AnatomicSite.class, anatomicSiteDao.domainClass());
	}
	
	/**
	 * Test get by subnames null string search.
	 */
	public void testGetBySubnamesNullStringSearch() {
		List<AnatomicSite> anatomicSites=anatomicSiteDao.getBySubnames(new String[] { "" });
		assertEquals("Wrong size", 2, anatomicSites.size());
	}
	
	/**
	 * Test get by subnames exact string.
	 */
	public void testGetBySubnamesExactString() {
		List<AnatomicSite> anatomicSites=anatomicSiteDao.getBySubnames(new String[] { "skin" });
		assertEquals("Wrong size", 1, anatomicSites.size());
	}
	
	/**
	 * Test get by subnames wildcard.
	 */
	public void testGetBySubnamesWildcard() {
		List<AnatomicSite> anatomicSites=anatomicSiteDao.getBySubnames(new String[] { "%" });
		assertEquals("Wrong size", 2, anatomicSites.size());
	}

}
