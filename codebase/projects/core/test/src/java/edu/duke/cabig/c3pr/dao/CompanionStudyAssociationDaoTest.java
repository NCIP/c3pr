/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for CompanionStudyAssociationDao.
 *
 * @author Ramakrishna
 * @testType unit
 */

public class CompanionStudyAssociationDaoTest extends ContextDaoTestCase<StudyDao> {

    /** The dao. */
    private CompanionStudyAssociationDao companionStudyAssociationDao;
    private StudyDao studyDao;

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.utils.DaoTestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        companionStudyAssociationDao = (CompanionStudyAssociationDao) getApplicationContext().getBean("companionStudyAssociationDao");
        studyDao = (StudyDao) getApplicationContext().getBean("studyDao");
    }

    public void testDomainClass() throws Exception{
    	assertEquals("Wrong domain class",CompanionStudyAssociation.class,companionStudyAssociationDao.domainClass());
    }

    public void testSave() throws Exception{
    	CompanionStudyAssociation companionStudyAssociation =new CompanionStudyAssociation();
    	companionStudyAssociation.setMandatoryIndicator(false);
    	companionStudyAssociation.setCompanionStudy(studyDao.getById(1001));
    	companionStudyAssociation.setParentStudyVersion(studyDao.getById(1000).getStudyVersion());
    	assertNull("companionStudyAssociation should not have an Id",companionStudyAssociation.getId());
    	companionStudyAssociationDao.save(companionStudyAssociation);

    	Integer savedId = companionStudyAssociation.getId();
    	CompanionStudyAssociation loadedCompanionStudyAssociation = companionStudyAssociationDao.getById(savedId);

    	assertEquals("Parent study wrong or not found","short_title_text",loadedCompanionStudyAssociation.getParentStudy().getShortTitleText());
    	assertEquals("Companion study wrong or not found","short_title_text2",loadedCompanionStudyAssociation.getCompanionStudy().getShortTitleText());
    }

}
