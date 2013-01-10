/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.constants.ICD9DiseaseSiteCodeDepth;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * The Class AnatomicSiteDaoTest.
 */
public class ICD9DiseaseSiteDaoTest extends DaoTestCase {

	/** The anatomic site dao. */
	private ICD9DiseaseSiteDao icd9DiseaseSiteDao;
	
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.utils.DaoTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		icd9DiseaseSiteDao=(ICD9DiseaseSiteDao)getApplicationContext().getBean("icd9DiseaseSiteDao");
	}
	
	/**
	 * Test domain class.
	 */
	public void testDomainClass() {
		assertEquals("Wrong Domain Class", ICD9DiseaseSite.class, icd9DiseaseSiteDao.domainClass());
	}
	
	/**
	 * Test get by subnames null string search.
	 */
	public void testGetBySubnamesNullStringSearch() {
		List<ICD9DiseaseSite> icd9DiseaseSites=icd9DiseaseSiteDao.getBySubnames(new String[] { "" });
		assertEquals("Wrong size", 5, icd9DiseaseSites.size());
	}
	
	/**
	 * Test get by subnames exact string.
	 */
	public void testGetBySubnamesExactString() {
		List<ICD9DiseaseSite> icd9DiseaseSites=icd9DiseaseSiteDao.getBySubnames(new String[] { "skin" });
		assertEquals("Wrong size", 3, icd9DiseaseSites.size());
	}
	
	/**
	 * Test get by subnames wildcard.
	 */
	public void testGetBySubnamesWildcard() {
		List<ICD9DiseaseSite> icd9DiseaseSites=icd9DiseaseSiteDao.getBySubnames(new String[] { "%" });
		assertEquals("Wrong size", 5, icd9DiseaseSites.size());
	}
	
	/**
	 * Test evict.
	 */
	public void testEvict() {
		List<ICD9DiseaseSite> icd9DiseaseSites=icd9DiseaseSiteDao.getBySubnames(new String[] { "%" });
		icd9DiseaseSiteDao.evict(icd9DiseaseSites.get(0));
	}
	
	public void testGetByName(){
		List<ICD9DiseaseSite> icd9DiseaseSites= icd9DiseaseSiteDao.getByName("Neoplasm2");
		assertEquals("Wrong size", 1, icd9DiseaseSites.size());
	}
	
	public void testGetByCode(){
		ICD9DiseaseSite icd9DiseaseSite= icd9DiseaseSiteDao.getByCode("200.1");
		assertNotNull("Error finding ICD9 disease site", icd9DiseaseSite);
	}
	
	public void testGetByLevel(){
		List<ICD9DiseaseSite> icd9DiseaseSites= icd9DiseaseSiteDao.getByLevel(ICD9DiseaseSiteCodeDepth.LEVEL4);
		assertEquals("Wrong size", 1, icd9DiseaseSites.size());
	}
	
	public void testGetSelectables(){
		List<ICD9DiseaseSite> icd9DiseaseSites= icd9DiseaseSiteDao.getSelectableSites();
		assertEquals("Wrong number of selectable sites retrieved", 2, icd9DiseaseSites.size());
	}
	
	public void testGetNonSelectables(){
		List<ICD9DiseaseSite> icd9DiseaseSites= icd9DiseaseSiteDao.getNonSelectableSites();
		assertEquals("Wrong number of selectable sites retrieved", 3, icd9DiseaseSites.size());
	}
	
	public void testGetParent(){
		ICD9DiseaseSite icd9DiseaseSite= icd9DiseaseSiteDao.getById(1003);
		assertNotNull("Object with the give Id not found",icd9DiseaseSite);
		assertNotNull("Parent site not found", icd9DiseaseSite.getParentSite());
		assertEquals("Wrong parent site retrieved","skin neoplasm", icd9DiseaseSite.getParentSite().getName());
	}
	
	public void testGetChildren(){
		ICD9DiseaseSite icd9DiseaseSite= icd9DiseaseSiteDao.getById(1001);
		assertNotNull("ICD9 disease site with the give Id not found",icd9DiseaseSite);
		assertNotNull("Child sites not retrieved",icd9DiseaseSite.getChildSites());
		assertEquals("Wrong number of children retrieved",2, icd9DiseaseSite.getChildSites().size());
		assertEquals("Wrong child site retrieved","200.2", icd9DiseaseSite.getChildSites().get(0).getCode());
	}

}
