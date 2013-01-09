/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for StudySiteDao.
 * 
 * @author Priyatam
 * @testType unit
 */
public class StudySiteDaoTest extends DaoTestCase {
    
    /** The dao. */
    private StudySiteDao dao;
    
    /**
     * Test for loading a Study by Id.
     * 
     * @throws Exception the exception
     */
    
    protected void setUp() throws Exception {
    	super.setUp();
    	dao = (StudySiteDao) getApplicationContext().getBean("studySiteDao");
    }
    
    /**
     * Test get by id.
     * 
     * @throws Exception the exception
     */
    public void testGetById() throws Exception {
        StudySite studySite = dao.getById(1000);
        assertNotNull("StudySite 1000 not found", studySite);
        assertEquals("StudySiteStudyVersions not found for studysite",1,studySite.getStudySiteStudyVersions().size());
    }

    /**
     * Test get by nci institute code.
     * 
     * @throws Exception the exception
     */
    public void testGetByNciInstituteCode() throws Exception {
        List<StudySite> sites = dao.getBySitePrimaryIdentifier("code");
        assertTrue(sites.size() == 1);
        for (StudySite site : sites) {
            assertEquals(site.getHealthcareSite().getCtepCode(), "code");
        }
    }
    
    public void testStudySiteStatusChange() {
    	 List<StudySite> sites = dao.getBySitePrimaryIdentifier("code");
    	 StudySite studySite=sites.get(0);
    	 int id = studySite.getId();
    	 assertEquals(studySite.getSiteStudyStatus(), SiteStudyStatus.PENDING);
    	 studySite = dao.merge(studySite);
    	 interruptSession();
    	 studySite = dao.getById(id);
    	 assertEquals(studySite.getSiteStudyStatus(), SiteStudyStatus.PENDING);
    	 studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.ACTIVE);
    	 assertEquals(studySite.getSiteStudyStatus(), SiteStudyStatus.ACTIVE);
    	 studySite = dao.merge(studySite);
    	 interruptSession();
    	 studySite = dao.getById(id);
    	 assertEquals(studySite.getSiteStudyStatus(), SiteStudyStatus.ACTIVE);
    }
    
    /**
     * Test get by String studyId and String sitePrimaryId.
     */
    public void testGetByStudyAndSitePrimaryIdentifier(){
    	List<StudySite> sites = dao.getBySitePrimaryIdentifierAndStudyCoordinatingCenterIdentifier("grid", "code");
    	assertEquals(1, sites.size());
    }
    
//    public void testGetStudySitesByShortTitle(){
//    	List<StudySite> sites = dao.getStudySitesByShortTitle("short_title");
//    	assertEquals(2, sites.size());
//    }
    
    
}

