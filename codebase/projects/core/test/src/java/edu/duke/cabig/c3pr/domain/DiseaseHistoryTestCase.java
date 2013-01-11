/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class DiseaseHistoryTestCase.
 */
public class DiseaseHistoryTestCase extends AbstractTestCase {

	/**
	 * Test get primary disease str other primary disease.
	 */
	public void testGetPrimaryDiseaseStrOtherPrimaryDisease(){
		DiseaseHistory diseaseHistory= new DiseaseHistory();
		diseaseHistory.setOtherPrimaryDiseaseCode("Test");
		assertEquals("Test", diseaseHistory.getPrimaryDiseaseStr());
	}
	
	/**
	 * Test get primary disease str from disease term.
	 */
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
	
	/**
	 * Test get primary disease str from disease term.
	 * studyDisease: null
	 */
	public void testGetPrimaryDiseaseStrNullStudyDisease(){
		DiseaseHistory diseaseHistory= new DiseaseHistory();
		assertEquals("", diseaseHistory.getPrimaryDiseaseStr());
		verifyMocks();
	}
	
	/**
	 * Test get primary disease site str other primary disease site.
	 */
	public void testGetPrimaryDiseaseSiteStrOtherPrimaryDiseaseSite(){
		DiseaseHistory diseaseHistory= new DiseaseHistory();
		diseaseHistory.setOtherPrimaryDiseaseSiteCode("Test");
		assertEquals("Test", diseaseHistory.getPrimaryDiseaseSiteStr());
	}
	
	/**
	 * Test get primary disease site str from anatomic site.
	 */
	public void testGetPrimaryDiseaseSiteStr(){
		DiseaseHistory diseaseHistory= new DiseaseHistory();
		ICD9DiseaseSite icd9DiseaseSite= registerMockFor(ICD9DiseaseSite.class);
		diseaseHistory.setIcd9DiseaseSite(icd9DiseaseSite);
		EasyMock.expect(icd9DiseaseSite.getName()).andReturn("TestSite");
		replayMocks();
		assertEquals("TestSite", diseaseHistory.getPrimaryDiseaseSiteStr());
		verifyMocks();
	}
	
	/**
	 * Test get primary disease site str from anatomic site.
	 * studyDisease: null
	 */
	public void testGetPrimaryDiseaseSiteStrNullStudyDisease(){
		DiseaseHistory diseaseHistory= new DiseaseHistory();
		assertEquals("", diseaseHistory.getPrimaryDiseaseSiteStr());
		verifyMocks();
	}
	
}
