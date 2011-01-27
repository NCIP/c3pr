package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_PARTICIPANT;
import static edu.duke.cabig.c3pr.C3PRUseCase.SEARCH_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.UPDATE_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.VERIFY_SUBJECT;

import java.util.Arrays;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.constants.AddressUse;
import edu.duke.cabig.c3pr.constants.ContactMechanismUse;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for ParticipantDao
 *
 * @author Priyatam
 * @testType Integration
 */
@C3PRUseCases( { CREATE_PARTICIPANT, UPDATE_SUBJECT, VERIFY_SUBJECT, SEARCH_SUBJECT })
public class StudySubjectDemographicsDaoTest extends ContextDaoTestCase<StudySubjectDemographicsDao> {
    private ParticipantDao participantDao;

    private StudySubjectDemographicsDao studySubjectDemographicsDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		participantDao = (ParticipantDao) getApplicationContext().getBean("participantDao");
    	studySubjectDemographicsDao = (StudySubjectDemographicsDao) getApplicationContext().getBean("studySubjectDemographicsDao");
	}
    /**
	 * Test domain class.
	 */
	public void testDomainClass() {
		assertEquals("Wrong Domain Class", StudySubjectDemographics.class, getDao().domainClass());
	}

    public void testSave(){
    	Participant participant = participantDao.getById(1000);
    	StudySubjectDemographics studySubjectDemographics = participant.createStudySubjectDemographics();
    	studySubjectDemographicsDao.save(studySubjectDemographics);
    	interruptSession();
    	studySubjectDemographics = studySubjectDemographicsDao.getById(studySubjectDemographics.getId());
    	assertNotNull(studySubjectDemographics);
    	assertEquals(4, studySubjectDemographics.getContactMechanisms().size());
    	assertEquals(2, studySubjectDemographics.getEmailContactMechanism().getContactUses().size());
    	assertEquals(Arrays.asList(ContactMechanismUse.H, ContactMechanismUse.HP), studySubjectDemographics.getEmailContactMechanism().getContactUses());
    	assertEquals(2, studySubjectDemographics.getAddress().getAddressUses().size());
    	assertEquals(Arrays.asList(AddressUse.H, AddressUse.HP), studySubjectDemographics.getAddress().getAddressUses());
    }
}