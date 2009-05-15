package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

public class StudyDiseaseTest extends AbstractTestCase{
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test compare to.
	 * 
	 * @throws Exception the exception
	 */
	public void testCompareTo() throws Exception{
		
		StudyDisease studyDisease1 = new StudyDisease();
		DiseaseTerm diseaseTerm1 = new DiseaseTerm();
		diseaseTerm1.setCtepTerm("ctep_term1");
		studyDisease1.setDiseaseTerm(diseaseTerm1);

		StudyDisease studyDisease2 = new StudyDisease();
		DiseaseTerm diseaseTerm2 = new DiseaseTerm();
		diseaseTerm2.setCtepTerm("ctep_term2");
		studyDisease2.setDiseaseTerm(diseaseTerm2);
		
		assertEquals("Both the study diseases should not have been same",1,studyDisease1.compareTo(studyDisease2));
		diseaseTerm2.setCtepTerm("ctep_term1");
		assertEquals("Both the study diseases should have been same",0,studyDisease1.compareTo(studyDisease2));
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
		
		assertTrue("The two study diseases should have been equal",studyDisease1.equals(studyDisease2));
		
		Study study = new Study();
		studyDisease2.setStudy(study);
		
		assertFalse("The two study diseases cannot be equal",studyDisease1.equals(studyDisease2));
	}
	
	public void testEquals2() throws Exception{
		
		StudyDisease studyDisease1 = new StudyDisease();
		StudyDisease studyDisease2 = new StudyDisease();
		
		Study study = new Study();
		studyDisease2.setStudy(study);
		
		studyDisease1.setStudy(study);
		
		assertTrue("The two study diseases should have been equal",studyDisease1.equals(studyDisease2));
		
		DiseaseTerm diseaseTerm1 = new DiseaseTerm();
		diseaseTerm1.setCtepTerm("ctep_term1");
		studyDisease1.setDiseaseTerm(diseaseTerm1);
		
		assertFalse("The two study diseases cannot be equal",studyDisease1.equals(studyDisease2));
		
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
		studyDisease2.setStudy(study);
		studyDisease1.setStudy(study);
		
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
