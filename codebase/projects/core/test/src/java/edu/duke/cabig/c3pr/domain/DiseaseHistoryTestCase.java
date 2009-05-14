package edu.duke.cabig.c3pr.domain;

import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;

public class DiseaseHistoryTestCase extends AbstractTestCase {

	public void testGetPrimaryDiseaseStrOtherPrimaryDisease(){
		DiseaseHistory diseaseHistory= new DiseaseHistory();
		diseaseHistory.setOtherPrimaryDiseaseCode("Test");
		assertEquals("Test", diseaseHistory.getPrimaryDiseaseStr());
	}
	
	public void testGetPrimaryDiseaseStr(){
		DiseaseHistory diseaseHistory= new DiseaseHistory();
		DiseaseTerm diseaseTerm= registerMockFor(DiseaseTerm.class);
		StudyDisease studyDisease= registerMockFor(StudyDisease.class);
		diseaseHistory.setStudyDisease(studyDisease);
		EasyMock.expect(studyDisease.getDiseaseTerm()).andReturn(diseaseTerm);
		EasyMock.expect(diseaseTerm.getTerm()).andReturn("TestTerm");
		replayMocks();
		assertEquals("TestTerm", diseaseHistory.getPrimaryDiseaseStr());
		verifyMocks();
	}
	
	public void testGetPrimaryDiseaseSiteStrOtherPrimaryDiseaseSite(){
		DiseaseHistory diseaseHistory= new DiseaseHistory();
		diseaseHistory.setOtherPrimaryDiseaseSiteCode("Test");
		assertEquals("Test", diseaseHistory.getPrimaryDiseaseSiteStr());
	}
	
	public void testGetPrimaryDiseaseSiteStr(){
		DiseaseHistory diseaseHistory= new DiseaseHistory();
		AnatomicSite anatomicSite= registerMockFor(AnatomicSite.class);
		diseaseHistory.setAnatomicSite(anatomicSite);
		EasyMock.expect(anatomicSite.getName()).andReturn("TestSite");
		replayMocks();
		assertEquals("TestSite", diseaseHistory.getPrimaryDiseaseSiteStr());
		verifyMocks();
	}
	
}
