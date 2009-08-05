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

		assertEquals("Wrong hash code",31*(31 +diseaseTerm1.hashCode()),studyDisease1.hashCode());
	}

	public void testEquals1() throws Exception{

		StudyDisease studyDisease1 = new StudyDisease();
		StudyDisease studyDisease2 = new StudyDisease();

		assertTrue("The two study diseases should have been equal",studyDisease1.equals(studyDisease1));
		assertTrue("The two study diseases should have been equal",studyDisease1.equals(studyDisease2));
		assertFalse("The two objects cannot be equal",studyDisease1.equals(new AnatomicSite()));

		Study study = new Study();
		study.addStudyDisease(studyDisease2);

		assertFalse("The two study diseases cannot be equal",studyDisease1.equals(studyDisease2));
	}

	public void testEquals2() throws Exception{

		StudyDisease studyDisease1 = new StudyDisease();
		StudyDisease studyDisease2 = new StudyDisease();

		Study study = new Study();
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

		Study study = new Study();
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

	/**
	 * Test equals3.
	 *
	 * @throws Exception the exception
	 */
	public void testEquals4() throws Exception{

		StudyDisease studyDisease1 = new StudyDisease();
		StudyDisease studyDisease2 = new StudyDisease();

		Study study1 = new Study();


		study1.addStudyDisease(studyDisease1);
		OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
		orgIdentifier.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
		orgIdentifier.setHealthcareSite(new LocalHealthcareSite());

		study1.addIdentifier(orgIdentifier);

		Study study2 = new Study();
		study2.addStudyDisease(studyDisease2);
		assertFalse("The two study diseases cannot be equal",studyDisease1.equals(studyDisease2));
	}

}
