package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RaceCode;
import edu.duke.cabig.c3pr.domain.customfield.BooleanCustomField;
import edu.duke.cabig.c3pr.domain.customfield.CustomField;

// TODO: Auto-generated Javadoc
/**
 * The Class ParticipantTest.
 */
public class ParticipantTest extends TestCase{
	
	/**
	 * Test remove identifier.
	 * 
	 * @throws Exception the exception
	 */
	public void testRemoveIdentifier() throws Exception{
		
		Participant participant = new Participant();
		
		OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
		orgIdentifier.setHealthcareSite(new LocalHealthcareSite());
		orgIdentifier.setValue("NCI_1232");
		
		SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
		sysIdentifier.setSystemName("Sys");
		sysIdentifier.setValue("sys_123");
		
		participant.addIdentifier(orgIdentifier);
		participant.addIdentifier(sysIdentifier);
		
		assertEquals("Wrong number of identifiers",2,participant.getIdentifiers().size());
		participant.removeIdentifier(orgIdentifier);
		assertEquals("Wrong number of identifiers: identifiers was not removed",1,participant.getIdentifiers().size());
		assertEquals("Wrong identifier removed",sysIdentifier,participant.getIdentifiers().get(0));
	}
	
	/**
	 * Test get race code.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetRaceCode() throws Exception{
		
		Participant participant = new Participant();
		assertEquals("Unexpected race code :should have been blank string","",participant.getRaceCode());
		
		RaceCode raceCode = RaceCode.Asian;
		participant.setRaceCode(raceCode.getCode());
		
		assertEquals("Wrong race code","Asian",participant.getRaceCode());
	}
	
	/**
	 * Test set race codes.
	 * 
	 * @throws Exception the exception
	 */
	public void testSetRaceCodes() throws Exception{
		
		Participant participant = new Participant();
		assertEquals("Wrong number of race codes",0,participant.getRaceCodes().size());
		
		RaceCode raceCode1 = RaceCode.Asian;
		RaceCode raceCode2 = RaceCode.Native_Hawaiian_or_Pacific_Islander;
		RaceCode raceCode3 = RaceCode.American_Indian_or_Alaska_Native;
		
		List<RaceCode> raceCodes = new ArrayList<RaceCode>();
		raceCodes.add(raceCode1);raceCodes.add(raceCode2);raceCodes.add(raceCode3);
		participant.setRaceCodes(raceCodes);
		assertEquals("Wrong number of race codes",3,participant.getRaceCodes().size());
		
	}
	
	/**
	 * Test get birth date str.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetBirthDateStr() throws Exception {
		Participant participant = new Participant();
		assertEquals("Unexpected birthdate: should have been blank","",participant.getBirthDateStr());
		Date birthDate = new Date();
		participant.setBirthDate(birthDate);
		assertNotSame("Wrong birthdate","",participant.getBirthDateStr());
	}
	
	
	/**
	 * Test add study subject.
	 * 
	 * @throws Exception the exception
	 */
	public void testAddStudySubject() throws Exception{
		Participant participant = new Participant();
		
		assertEquals("Unexpected study subject for participant",0,participant.getStudySubjects().size());
		
		StudySubject studySubject = new StudySubject();
		participant.addStudySubject(studySubject);
		
		assertEquals("Study Subject not found for participant",1,participant.getStudySubjects().size());
		
	}
	
	/**
	 * Test remove study subject.
	 * 
	 * @throws Exception the exception
	 */
	public void testRemoveStudySubject() throws Exception{
		Participant participant = new Participant();
		
		StudySubject studySubject1 = new StudySubject();
		participant.addStudySubject(studySubject1);
		
		StudySubject studySubject2 = new StudySubject();
		participant.addStudySubject(studySubject2);
		
		assertEquals("Wrong number of Study Subjects for participant",2,participant.getStudySubjects().size());
		
		participant.removeStudySubject(studySubject1);
		
		assertEquals("Wrong number of Study Subjects for participant",1,participant.getStudySubjects().size());
	}
	
	/**
	 * Test compare to.
	 * 
	 * @throws Exception the exception
	 */
	public void testCompareTo() throws Exception{
		
		Participant participant1 = new Participant();
		OrganizationAssignedIdentifier orgIdentifier1 = new OrganizationAssignedIdentifier();
		orgIdentifier1.setType(OrganizationIdentifierTypeEnum.MRN);
		orgIdentifier1.setValue("1232");
		participant1.addIdentifier(orgIdentifier1);
		
		Participant participant2 = new Participant();
		OrganizationAssignedIdentifier orgIdentifier2 = new OrganizationAssignedIdentifier();
		orgIdentifier2.setType(OrganizationIdentifierTypeEnum.MRN);
		orgIdentifier2.setValue("123");
		participant2.addIdentifier(orgIdentifier2);
		
		Participant participant3 = new Participant();
		OrganizationAssignedIdentifier orgIdentifier3 = new OrganizationAssignedIdentifier();
		orgIdentifier3.setType(OrganizationIdentifierTypeEnum.MRN);
		orgIdentifier3.setValue("1232");
		participant3.addIdentifier(orgIdentifier3);
		
		assertEquals("The 2 participants cannot be same",1, participant1.compareTo(participant2));
		assertEquals("The 2 epochs should have been same",0, participant1.compareTo(participant3));
	}
	
	/**
	 * Test get primary identifier.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetPrimaryIdentifier() throws Exception{
		Participant participant1 = new Participant();
		OrganizationAssignedIdentifier orgIdentifier1 = new OrganizationAssignedIdentifier();
		orgIdentifier1.setPrimaryIndicator(true);
		orgIdentifier1.setType(OrganizationIdentifierTypeEnum.MRN);
		orgIdentifier1.setValue("1232");
		participant1.addIdentifier(orgIdentifier1);
		
		assertEquals("Wrong primary identifier","1232",participant1.getPrimaryIdentifier());
	}
	
	
	/**
	 * Test validate participant.
	 * 
	 * @throws Exception the exception
	 */
	public void testValidateParticipant() throws Exception{
		Participant participant = new Participant();
		participant.setFirstName("xyz");
		participant.setLastName("unknown");
		participant.setBirthDate(new Date());

		assertFalse("Expected false",participant.validateParticipant());
	}
	
	/**
	 * Test add custom fields.
	 * 
	 * @throws Exception the exception
	 */
	public void testAddCustomFields() throws Exception{
		Participant participant = new Participant();
		CustomField customField = new BooleanCustomField();
		assertEquals("Unexpected customfields",0,participant.getCustomFields().size());
		
		participant.addCustomField(customField);
		assertEquals("Wrong number of customfields",1,participant.getCustomFields().size());
	}

}
