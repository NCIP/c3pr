/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.classextension.EasyMock.createNiceMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.repository.ParticipantRepository;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.webservice.helpers.SubjectManagementRelatedTestCase;

/**
 * @author dkrylov
 * 
 */
public class SubjectManagementImplTest extends SubjectManagementRelatedTestCase {

	private SubjectManagementImpl service;
	private ParticipantValidator validator;
	private ParticipantRepository participantRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		validator = createNiceMock(ParticipantValidator.class);
		participantRepository = createNiceMock(ParticipantRepository.class);

		service = new SubjectManagementImpl();
		service.setConverter(converter);
		service.setParticipantRepository(participantRepository);
		service.setParticipantValidator(validator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementImpl#createSubject(edu.duke.cabig.c3pr.webservice.subjectmanagement.CreateSubjectRequest)}
	 * .
	 */
	public void testCreateSubject() throws Exception {
		Person person = createPerson();
		Subject subject = createSubject(person);

		// test successful subject creation.
		CreateSubjectRequest request = new CreateSubjectRequest();
		request.setSubject(subject);

		expect(participantRepository.searchByIdentifier(isA(Identifier.class)))
				.andReturn(new ArrayList<Participant>());
		validator.validate(anyObject(), isA(Errors.class));
		participantRepository.save(isA(Participant.class));
		replay(participantRepository, validator);

		CreateSubjectResponse response = service.createSubject(request);
		Subject createdSubject = response.getSubject();		
		
		verify(participantRepository, validator);
	}


	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementImpl#querySubject(edu.duke.cabig.c3pr.webservice.subjectmanagement.QuerySubjectRequest)}
	 * .
	 */
	public void testQuerySubject() {

	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementImpl#updateSubject(edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectRequest)}
	 * .
	 */
	public void testUpdateSubject() {

	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementImpl#updateSubjectState(edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectStateRequest)}
	 * .
	 */
	public void testUpdateSubjectState() {

	}

}
