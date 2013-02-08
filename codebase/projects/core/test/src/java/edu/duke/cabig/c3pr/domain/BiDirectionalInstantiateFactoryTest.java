/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class BiDirectionalInstantiateFactoryTest.
 */
public class BiDirectionalInstantiateFactoryTest extends AbstractTestCase{
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	
	/**
	 * Test bi directional instantiate factory.
	 * 
	 * @throws Exception the exception
	 */
	public void testBiDirectionalInstantiateFactory() throws Exception{
		StudySite studySite = new StudySite();
		BiDirectionalInstantiateFactory<StudyInvestigator> studyInvestigator = new BiDirectionalInstantiateFactory<StudyInvestigator>(StudyInvestigator.class,studySite,"StudyOrganization");
		assertEquals("Wrong parent class",StudySite.class,studyInvestigator.getParent().getClass());
	}
	
	/**
	 * Test bi directional instantiate factory create.
	 * 
	 * @throws Exception the exception
	 */
	public void testBiDirectionalInstantiateFactoryCreate() throws Exception{
		StudySite studySite = new StudySite();
		BiDirectionalInstantiateFactory<StudyInvestigator> studyInvestigator = new BiDirectionalInstantiateFactory<StudyInvestigator>(StudyInvestigator.class,studySite,"StudyOrganization");
		assertEquals("Wrong parent class",StudySite.class,studyInvestigator.getParent().getClass());
		assertNotNull("Study Investigator creation failed",studySite.getStudyInvestigators().get(0));
		
	}

}
