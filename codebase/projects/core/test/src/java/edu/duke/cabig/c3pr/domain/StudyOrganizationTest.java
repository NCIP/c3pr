package edu.duke.cabig.c3pr.domain;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.constants.ServiceName;
import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.EndPointType;
import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;;


/**
 * The Class StudyOrganizationTest.
 */
public class StudyOrganizationTest extends AbstractTestCase{
	
	/**
	 * Test set retired indicator as true.
	 */
	public void testGetActiveStudyInvestigators(){
		StudyOrganization studyOrganization = new StudySite();
		
		StudyInvestigator studyInvestigator = new StudyInvestigator();
		
		Investigator investigator = new LocalInvestigator();
		investigator.setFirstName("John");
		
		HealthcareSiteInvestigator healthcareSiteInvestigator = new HealthcareSiteInvestigator();
		healthcareSiteInvestigator.setInvestigator(investigator);
		healthcareSiteInvestigator.setStatusCode(InvestigatorStatusCodeEnum.AC);
		studyInvestigator.setHealthcareSiteInvestigator(healthcareSiteInvestigator);
		studyInvestigator.setStatusCode(InvestigatorStatusCodeEnum.AC);
		
		StudyInvestigator studyInvestigator1 = new StudyInvestigator();
		
		Investigator investigator1 = new LocalInvestigator();
		investigator1.setFirstName("Jane");
		
		HealthcareSiteInvestigator healthcareSiteInvestigator1 = new HealthcareSiteInvestigator();
		healthcareSiteInvestigator1.setInvestigator(investigator1);
		healthcareSiteInvestigator1.setStatusCode(InvestigatorStatusCodeEnum.IN);
		studyInvestigator1.setHealthcareSiteInvestigator(healthcareSiteInvestigator1);
		studyInvestigator1.setStatusCode(InvestigatorStatusCodeEnum.IN);
		
		studyOrganization.addStudyInvestigator(studyInvestigator);
		studyOrganization.addStudyInvestigator(studyInvestigator1);
		
		assertEquals(1, studyOrganization.getActiveStudyInvestigators().size());
	}
	
	
	/**
	 * Test equals.
	 */
	public void testEquals(){
		
		StudyOrganization studyOrganization1 = new StudySite();
		Study study1 = new Study();
		HealthcareSite healthcareSite1 = new LocalHealthcareSite();
		
		studyOrganization1.setStudy(study1);
		studyOrganization1.setHealthcareSite(healthcareSite1);
		
		
		StudyOrganization studyOrganization2 = new StudySite();
		Study study2 = new Study();
		HealthcareSite healthcareSite2 = new LocalHealthcareSite();
		
		studyOrganization2.setStudy(study2);
		studyOrganization2.setHealthcareSite(healthcareSite2);
		
		assertTrue(studyOrganization1.equals(studyOrganization1));
		assertFalse(studyOrganization1.equals(studyOrganization2));
	}
	
	public void testHashcode(){
		StudyOrganization studyOrganization1 = new StudySite();
		Study study1 = new Study();
		HealthcareSite healthcareSite1 = new LocalHealthcareSite();
		
		studyOrganization1.setStudy(study1);
		studyOrganization1.setHealthcareSite(healthcareSite1);
		
		
		StudyOrganization studyOrganization2 = new StudySite();
		Study study2 = new Study();
		HealthcareSite healthcareSite2 = new LocalHealthcareSite();
		
		studyOrganization2.setStudy(study2);
		studyOrganization2.setHealthcareSite(healthcareSite2);
		
		assertTrue(studyOrganization1.hashCode() == studyOrganization2.hashCode());
	}
	
	
	/**
	 * Test get study end points.
	 */
	public void testGetStudyEndPoints(){
		StudyOrganization studyOrganization = getStudyOrgWithEndPoints();
		assertEquals(1, studyOrganization.getStudyEndpoints().size());
	}

	/**
	 * Test get reg end points.
	 */
	public void testGetRegEndPoints(){
		StudyOrganization studyOrganization = getStudyOrgWithEndPoints();
		assertEquals(1, studyOrganization.getRegistrationEndpoints().size());
	}
	
	/**
	 * Test get last attempted registration endpoint.
	 */
	public void testGetLastAttemptedRegistrationEndpoint(){
		StudyOrganization studyOrganization = getStudyOrgWithEndPoints();
		
		GridEndPoint endPointReg1 = new GridEndPoint();
		endPointReg1.setServiceName(ServiceName.REGISTRATION);
		endPointReg1.setAttemptDate( new GregorianCalendar(2005, 1, 28).getTime());
		studyOrganization.addEndPoint(endPointReg1);
		
		assertEquals(endPointReg1, studyOrganization.getLastAttemptedRegistrationEndpoint());
	}
	
	/**
	 * Gets the study org with end points.
	 * 
	 * @return the study org with end points
	 */
	private StudyOrganization getStudyOrgWithEndPoints() {
		
		StudyOrganization studyOrganization = new StudySite();
		GridEndPoint endPointStudy = new GridEndPoint();
		endPointStudy.setServiceName(ServiceName.STUDY);

		EndPointConnectionProperty endPointConnectionProperty = new EndPointConnectionProperty();
		endPointConnectionProperty.setEndPointType(EndPointType.GRID);
		endPointConnectionProperty.setUrl("https://url.com");
		
		endPointStudy.setEndPointProperty(endPointConnectionProperty);
		endPointStudy.setApiName(APIName.ACTIVATE_STUDY_SITE);
		studyOrganization.addEndPoint(endPointStudy);
		
		GridEndPoint endPointReg = new GridEndPoint();
		EndPointConnectionProperty endPointConnectionProperty1 = new EndPointConnectionProperty();
		endPointConnectionProperty1.setEndPointType(EndPointType.GRID);
		endPointConnectionProperty1.setUrl("https://url.com");
		
		endPointReg.setEndPointProperty(endPointConnectionProperty1);
		endPointReg.setApiName(APIName.GET_REGISTRATIONS);
		endPointReg.setStatus(WorkFlowStatusType.MESSAGE_SEND_CONFIRMED);
		endPointReg.setServiceName(ServiceName.REGISTRATION);
		endPointReg.setAttemptDate(Calendar.getInstance().getTime());
		studyOrganization.addEndPoint(endPointReg);
		
		return studyOrganization;
	}
	
	/**
	 * Test if study investigator exists.
	 */
	public void testIfStudyInvestigatorExists(){
		StudyOrganization studyOrganization = new StudySite();
		
		StudyInvestigator studyInvestigator = new StudyInvestigator();
		
		Investigator investigator = new LocalInvestigator();
		investigator.setFirstName("John");
		
		HealthcareSiteInvestigator healthcareSiteInvestigator = new HealthcareSiteInvestigator();
		healthcareSiteInvestigator.setInvestigator(investigator);
		healthcareSiteInvestigator.setStatusCode(InvestigatorStatusCodeEnum.AC);
		studyInvestigator.setHealthcareSiteInvestigator(healthcareSiteInvestigator);
		studyInvestigator.setStatusCode(InvestigatorStatusCodeEnum.AC);
		studyOrganization.getStudyInvestigators().add(studyInvestigator);
		
		assertTrue(studyOrganization.ifStudyInvestigatorExists(healthcareSiteInvestigator));
	}
	
	
	/**
	 * Test is successfull send.
	 */
	public void testIsSuccessfullSend(){
		StudyOrganization studyOrganization = getStudyOrgWithEndPoints();
		assertTrue(studyOrganization.isSuccessfullSend(APIName.GET_REGISTRATIONS));
	}
	
	/**
	 * Test get is coordinating center.
	 */
	public void testGetIsCoordinatingCenter(){
		Study study = new Study();
		StudyCoordinatingCenter studyCoordinatingCenter = new StudyCoordinatingCenter();
		HealthcareSite healthcareSite = new LocalHealthcareSite();
		studyCoordinatingCenter.setHealthcareSite(healthcareSite);
		healthcareSite.setNciInstituteCode("NCIID");
		
		study.getStudyCoordinatingCenters().add(studyCoordinatingCenter);
		
		StudyOrganization studyOrganization = new StudySite();
		studyOrganization.setHealthcareSite(healthcareSite);
		studyOrganization.setStudy(study);
		
		assertTrue(studyOrganization.getIsCoordinatingCenter());
	}
	
	
	/**
	 * Test add study personnel.
	 */
	public void testAddStudyPersonnel(){
		StudyOrganization studyOrganization = new StudySite();
		StudyPersonnel studyPersonnel = new StudyPersonnel();
		studyPersonnel.setRoleCode("Inactive");
		studyOrganization.addStudyPersonnel(studyPersonnel);
		
		assertEquals("Inactive", studyOrganization.getStudyPersonnel().get(0).getRoleCode());
	}
	
}



