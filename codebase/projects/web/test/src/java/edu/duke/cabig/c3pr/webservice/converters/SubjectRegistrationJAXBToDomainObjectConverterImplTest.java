/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.converters;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.DiseaseHistory;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.ApplicationTestCase;
import edu.duke.cabig.c3pr.webservice.helpers.SubjectRegistryRelatedTestCase;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.II;
import edu.duke.cabig.c3pr.webservice.subjectregistration.HealthcareProvider;
import edu.duke.cabig.c3pr.webservice.subjectregistration.PerformedDiagnosis;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudyCondition;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudyInvestigator;
import edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverterImpl;
import edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverterImpl;

public class SubjectRegistrationJAXBToDomainObjectConverterImplTest extends ApplicationTestCase {

	protected SubjectRegistrationJAXBToDomainObjectConverterImpl converter;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		converter = new SubjectRegistrationJAXBToDomainObjectConverterImpl();
	}
	
	public void testConvertDiseaseHistory() {
		PerformedDiagnosis diseaseHistory = createDiseaseHistory();
		DiseaseHistory convertedDiseaseHistory = converter.convertDiseaseHistory(diseaseHistory);
		
		assertNotNull(convertedDiseaseHistory);
		assertEquals(convertedDiseaseHistory.getPrimaryDiseaseStr(), diseaseHistory.getDisease().getConditionCode().getCode());
		assertEquals(convertedDiseaseHistory.getPrimaryDiseaseSiteStr(), diseaseHistory.getTargetAnatomicSiteCode().getCode());
	}


	public void testConvertStudyInvestigator(){
		//studyInvestigator with an assignedIdentifer
		StudyInvestigator studyInvestigator = createStudyInvestigator();
		edu.duke.cabig.c3pr.domain.StudyInvestigator convertedStudyInvestigator = converter.convertStudyInvestigator(studyInvestigator);
		
		assertNotNull(convertedStudyInvestigator);
		assertEquals(convertedStudyInvestigator.getHealthcareSiteInvestigator().getInvestigator().getAssignedIdentifier(), 
							studyInvestigator.getHealthcareProvider().getIdentifier().getExtension());
	}
	
	
	/**
	 * Test convert scheduled epoch. Incomplete implementation.
	 */
	public void testConvertScheduledEpoch(){
		// TODO Auto-generated method stub
	}
	
	
	private PerformedDiagnosis createDiseaseHistory() {
		PerformedDiagnosis pd = new PerformedDiagnosis();
		StudyCondition studyCondition = new StudyCondition();
		CD cd = new CD();
		cd.setCode("dTerm");
		studyCondition.setConditionCode(cd);
		
		pd.setDisease(studyCondition);
		CD targetAnatomicSiteCode = new CD();
		targetAnatomicSiteCode.setCode("ICD9");
		pd.setTargetAnatomicSiteCode(targetAnatomicSiteCode);
		
		return pd;
	}
	
	/**
	 * Creates the study investigator. Incomplete implementation.
	 *
	 * @return the study investigator
	 */
	public StudyInvestigator createStudyInvestigator(){
		StudyInvestigator studyInvestigator = new StudyInvestigator();
		
		HealthcareProvider healthcareProvider = new HealthcareProvider(); 
		II ii = new II();
		ii.setExtension("assignedId");
		healthcareProvider.setIdentifier(ii);
		healthcareProvider.setPerson(SubjectRegistryRelatedTestCase.createPerson());
		
		studyInvestigator.setHealthcareProvider(healthcareProvider);
		
		return studyInvestigator;
	}

	
}
