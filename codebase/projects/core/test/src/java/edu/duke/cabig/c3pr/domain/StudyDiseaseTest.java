/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;

public class StudyDiseaseTest extends AbstractTestCase{

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testHashCode() throws Exception{

		StudyDisease studyDisease1 = new StudyDisease();
		DiseaseTerm diseaseTerm1 = new DiseaseTerm();
		diseaseTerm1.setCtepTerm("ctep_term1");
		studyDisease1.setDiseaseTerm(diseaseTerm1);

		assertEquals("Wrong hash code",31 * 1 + (diseaseTerm1.hashCode()),studyDisease1.hashCode());
	}

	public void testEquals1() throws Exception{

		StudyDisease studyDisease1 = new StudyDisease();
		StudyDisease studyDisease2 = new StudyDisease();

		assertTrue("The two study diseases should have been equal",studyDisease1.equals(studyDisease1));
		assertTrue("The two study diseases should have been equal",studyDisease1.equals(studyDisease2));
		assertFalse("The two objects cannot be equal",studyDisease1.equals(new ICD9DiseaseSite()));

		Study study = new LocalStudy();
		study.addStudyDisease(studyDisease2);

		assertTrue("The two study diseases are equal",studyDisease1.equals(studyDisease2));
	}

	public void testEquals2() throws Exception{

		StudyDisease studyDisease1 = new StudyDisease();
		StudyDisease studyDisease2 = new StudyDisease();

		Study study = new LocalStudy();
		study.addStudyDisease(studyDisease2);

		study.addStudyDisease(studyDisease1);

		assertTrue("The two study diseases should have been equal",studyDisease1.equals(studyDisease2));

		DiseaseTerm diseaseTerm1 = new DiseaseTerm();
		diseaseTerm1.setCtepTerm("ctep_term1");
		studyDisease1.setDiseaseTerm(diseaseTerm1);

		assertFalse("The two study diseases cannot be equal",studyDisease1.equals(studyDisease2));
		assertFalse("The two study diseases cannot be equal",studyDisease2.equals(studyDisease1));

	}

	/**
	 * Test equals3.
	 *
	 * @throws Exception the exception
	 */
	public void testEquals3() throws Exception{

		StudyDisease studyDisease1 = new StudyDisease();
		StudyDisease studyDisease2 = new StudyDisease();

		Study study = new LocalStudy();
		study.addStudyDisease(studyDisease2);

		study.addStudyDisease(studyDisease1);

		DiseaseTerm diseaseTerm1 = new DiseaseTerm();
		diseaseTerm1.setCtepTerm("ctep_term1");
		studyDisease1.setDiseaseTerm(diseaseTerm1);

		DiseaseTerm diseaseTerm2 = new DiseaseTerm();
		diseaseTerm2.setCtepTerm("ctep_term2");
		studyDisease2.setDiseaseTerm(diseaseTerm2);

		assertFalse("The two study diseases cannot be equal",studyDisease1.equals(studyDisease2));

		diseaseTerm2.setCtepTerm("ctep_term1");
		assertTrue("The two study diseases should have been equal",studyDisease1.equals(studyDisease2));
	}

}
