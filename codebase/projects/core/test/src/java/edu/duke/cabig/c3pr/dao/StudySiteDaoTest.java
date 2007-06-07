package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for StudySiteDao
 * @author Priyatam
 * @testType unit
 */
public class StudySiteDaoTest extends DaoTestCase {
    private StudySiteDao dao = (StudySiteDao) getApplicationContext()
    	.getBean("studySiteDao");
  
    /**
	 * Test for loading a Study by Id 
	 * @throws Exception
	 */
    public void testGetById() throws Exception {
    	StudySite studySite = dao.getById(1000);
        assertNotNull("StudySite 1000 not found", studySite);
    }   
}