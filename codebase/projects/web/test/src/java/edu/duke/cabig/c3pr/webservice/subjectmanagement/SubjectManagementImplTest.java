/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isA;
import static org.easymock.classextension.EasyMock.createNiceMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.reset;
import static org.easymock.classextension.EasyMock.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.test.AssertThrows;
import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.ParticipantStateCode;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.repository.ParticipantRepository;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.utils.BeanUtils;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.DSETAdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.helpers.WebServiceRelatedTestCase;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.impl.SubjectManagementImpl;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.impl.SubjectManagementImpl.ParticipantValidationError;

/**
 * @author dkrylov
 * 
 */
public class SubjectManagementImplTest extends WebServiceRelatedTestCase {

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
	 * {@link edu.duke.cabig.c3pr.webservice.subjectmanagement.impl.SubjectManagementImpl#createSubject(edu.duke.cabig.c3pr.webservice.subjectmanagement.CreateSubjectRequest)}
	 * .
	 */
	public void testCreateSubject() throws Exception {
		Person person = createPerson();
		Subject subject = createSubject(person);

		// test successful subject creation.
		final CreateSubjectRequest request = new CreateSubjectRequest();
		request.setSubject(subject);

		expect(participantRepository.searchByIdentifier(isA(Identifier.class)))
				.andReturn(new ArrayList<Participant>());
		validator.validate(anyObject(), isA(Errors.class));
		participantRepository.save(isA(Participant.class));
		replay(participantRepository, validator);

		CreateSubjectResponse response = service.createSubject(request);
		Subject createdSubject = response.getSubject();
		assertTrue(BeanUtils.deepCompare(subject, createdSubject));

		verify(participantRepository, validator);
		reset(participantRepository, validator);

		// test validation exception handling.
		person = createPerson();
		subject = createSubject(person);
		request.setSubject(subject);

		expect(participantRepository.searchByIdentifier(isA(Identifier.class)))
				.andReturn(new ArrayList<Participant>());
		validator.validate(anyObject(), isA(Errors.class));
		expectLastCall().andThrow(new ParticipantValidationError());
		replay(participantRepository, validator);

		new AssertThrows(InvalidSubjectDataExceptionFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.createSubject(request);
			}
		}.runTest();
		verify(participantRepository, validator);
		reset(participantRepository, validator);

		// test handling of existent subject situation
		person = createPerson();
		subject = createSubject(person);
		request.setSubject(subject);

		expect(participantRepository.searchByIdentifier(isA(Identifier.class)))
				.andReturn(
						Arrays.asList(new Participant[] { new Participant() }));
		replay(participantRepository, validator);
		new AssertThrows(SubjectAlreadyExistsExceptionFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.createSubject(request);
			}
		}.runTest();
		verify(participantRepository, validator);
		reset(participantRepository, validator);

	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.subjectmanagement.impl.SubjectManagementImpl#querySubject(edu.duke.cabig.c3pr.webservice.subjectmanagement.QuerySubjectRequest)}
	 * .
	 * 
	 * @throws InvalidSubjectDataExceptionFaultMessage
	 * @throws InsufficientPrivilegesExceptionFaultMessage
	 */
	public void testQuerySubject()
			throws SecurityExceptionFaultMessage,
			InvalidSubjectDataExceptionFaultMessage {
		final QuerySubjectRequest request = new QuerySubjectRequest();

		Person person = createPerson();
		Subject subject = createSubject(person);
		request.setSubject(subject);

		Participant searchResult = createParticipant();
		expect(
				participantRepository
						.searchByFullExample(isA(Participant.class)))
				.andReturn(Arrays.asList(new Participant[] { searchResult }));
		replay(participantRepository);

		QuerySubjectResponse response = service.querySubject(request);
		DSETSUBJECT subjects = response.getSubjects();
		List<Subject> listOfSubjects = subjects.getItem();
		assertTrue(CollectionUtils.isNotEmpty(listOfSubjects));
		assertEquals(1, listOfSubjects.size());
		assertTrue(BeanUtils.deepCompare(subject, listOfSubjects.get(0)));

		verify(participantRepository);
		reset(participantRepository);

	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.subjectmanagement.impl.SubjectManagementImpl#advancedQuerySubject(AdvancedQuerySubjectRequest)}
	 * 
	 * @throws InsufficientPrivilegesExceptionFaultMessage
	 * @throws InvalidSubjectDataExceptionFaultMessage
	 */
	public void testAdvancedQuerySubject()
			throws SecurityExceptionFaultMessage,
			InvalidSubjectDataExceptionFaultMessage {

		final AdvancedQuerySubjectRequest request = new AdvancedQuerySubjectRequest();

		Person person = createPerson();
		Subject subject = createSubject(person);
		Participant searchResult = createParticipant();
		AdvanceSearchCriterionParameter param = createAdvaceSearchParam();
		request.setParameters(new DSETAdvanceSearchCriterionParameter());
		request.getParameters().getItem().add(param);

		expect(participantRepository.search(isA(List.class))).andReturn(
				Arrays.asList(new Participant[] { searchResult }));
		replay(participantRepository);

		AdvancedQuerySubjectResponse response = service.advancedQuerySubject(request);
		DSETSUBJECT subjects = response.getSubjects();
		List<Subject> listOfSubjects = subjects.getItem();
		assertTrue(CollectionUtils.isNotEmpty(listOfSubjects));
		assertEquals(1, listOfSubjects.size());
		assertTrue(BeanUtils.deepCompare(subject, listOfSubjects.get(0)));

		verify(participantRepository);
		reset(participantRepository);

	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.subjectmanagement.impl.SubjectManagementImpl#updateSubject(edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectRequest)}
	 * .
	 * 
	 * @throws UnableToCreateOrUpdateSubjectExceptionFaultMessage
	 * @throws NoSuchSubjectExceptionFaultMessage
	 * @throws InvalidSubjectDataExceptionFaultMessage
	 * @throws InsufficientPrivilegesExceptionFaultMessage
	 */
	public void testUpdateSubject()
			throws SecurityExceptionFaultMessage,
			InvalidSubjectDataExceptionFaultMessage,
			NoSuchSubjectExceptionFaultMessage,
			UnableToCreateOrUpdateSubjectExceptionFaultMessage {
		Person person = createPerson();
		Subject subject = createSubject(person);

		// test successful subject update.
		final UpdateSubjectRequest request = new UpdateSubjectRequest();
		request.setSubject(subject);
		Participant existentSubject = createParticipantWithIdAndStateOnly();

		// to test proper race code handling
		existentSubject
				.setRaceCodes(Arrays
						.asList(new RaceCodeEnum[] { RaceCodeEnum.American_Indian_or_Alaska_Native }));

		expect(participantRepository.searchByIdentifier(isA(Identifier.class)))
				.andReturn(Arrays.asList(new Participant[] { existentSubject }));
		validator.validate(anyObject(), isA(Errors.class));
		participantRepository.save(isA(Participant.class));
		replay(participantRepository, validator);

		UpdateSubjectResponse response = service.updateSubject(request);
		Subject updatedSubject = response.getSubject();
		assertTrue(BeanUtils.deepCompare(subject, updatedSubject));

		verify(participantRepository, validator);
		reset(participantRepository, validator);

		// handle non-existent subject
		person = createPerson();
		subject = createSubject(person);
		request.setSubject(subject);

		expect(participantRepository.searchByIdentifier(isA(Identifier.class)))
				.andReturn(new ArrayList<Participant>());
		replay(participantRepository, validator);
		new AssertThrows(NoSuchSubjectExceptionFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.updateSubject(request);
			}
		}.runTest();
		verify(participantRepository, validator);
		reset(participantRepository, validator);

		// handle inactive subject
		person = createPerson();
		subject = createSubject(person);
		request.setSubject(subject);

		Participant inactiveSubject = createParticipantWithIdAndStateOnly();
		inactiveSubject.setStateCode(ParticipantStateCode.INACTIVE);

		expect(participantRepository.searchByIdentifier(isA(Identifier.class)))
				.andReturn(Arrays.asList(new Participant[] { inactiveSubject }));
		replay(participantRepository, validator);
		new AssertThrows(NoSuchSubjectExceptionFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.updateSubject(request);
			}
		}.runTest();
		verify(participantRepository, validator);
		reset(participantRepository, validator);

		// test validation error.
		person = createPerson();
		subject = createSubject(person);
		request.setSubject(subject);

		existentSubject = createParticipantWithIdAndStateOnly();

		expect(participantRepository.searchByIdentifier(isA(Identifier.class)))
				.andReturn(Arrays.asList(new Participant[] { existentSubject }));
		validator.validate(anyObject(), isA(Errors.class));
		expectLastCall().andThrow(new ParticipantValidationError());
		replay(participantRepository, validator);
		new AssertThrows(InvalidSubjectDataExceptionFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.updateSubject(request);
			}
		}.runTest();

	}

	protected Participant createParticipantWithIdAndStateOnly() {
		Participant p = new Participant();

		OrganizationAssignedIdentifier id = new OrganizationAssignedIdentifier();
		id.setHealthcareSite(healthcareSite);
		id.setPrimaryIndicator(true);
		id.setType(OrganizationIdentifierTypeEnum.MRN);
		id.setValue(TEST_BIO_ID);
		p.addIdentifier(id);

		p.setStateCode(ParticipantStateCode.ACTIVE);
		return p;
	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.subjectmanagement.impl.SubjectManagementImpl#updateSubjectState(edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectStateRequest)}
	 * .
	 * 
	 * @throws NoSuchSubjectExceptionFaultMessage
	 * @throws InvalidStateTransitionExceptionFaultMessage
	 * @throws InsufficientPrivilegesExceptionFaultMessage
	 */
	public void testUpdateSubjectState()
			throws SecurityExceptionFaultMessage,
			InvalidStateTransitionExceptionFaultMessage,
			NoSuchSubjectExceptionFaultMessage {
		final UpdateSubjectStateRequest request = new UpdateSubjectStateRequest();

		BiologicEntityIdentifier bioId = createBioEntityId();
		ST newState = new ST(STATE_INACTIVE);
		request.setBiologicEntityIdentifier(bioId);
		request.setNewState(newState);

		Participant existentSubject = createParticipant();
		expect(participantRepository.searchByIdentifier(isA(Identifier.class)))
				.andReturn(Arrays.asList(new Participant[] { existentSubject }));
		participantRepository.save(isA(Participant.class));
		replay(participantRepository);

		Subject updatedSubject = service.updateSubjectState(request)
				.getSubject();
		assertEquals(STATE_INACTIVE, updatedSubject.getStateCode().getValue());
		verify(participantRepository);
		reset(participantRepository);

		// handle non-existent subject.
		bioId = createBioEntityId();
		newState = new ST(STATE_INACTIVE);
		request.setBiologicEntityIdentifier(bioId);
		request.setNewState(newState);

		expect(participantRepository.searchByIdentifier(isA(Identifier.class)))
				.andReturn(new LinkedList<Participant>());
		replay(participantRepository);

		new AssertThrows(NoSuchSubjectExceptionFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.updateSubjectState(request);
			}
		}.runTest();
		verify(participantRepository);
		reset(participantRepository);

		// handle wrong state code
		bioId = createBioEntityId();
		newState = new ST(BAD_STATE_CODE);
		request.setBiologicEntityIdentifier(bioId);
		request.setNewState(newState);

		existentSubject = createParticipant();
		expect(participantRepository.searchByIdentifier(isA(Identifier.class)))
				.andReturn(Arrays.asList(new Participant[] { existentSubject }));
		replay(participantRepository);

		new AssertThrows(InvalidStateTransitionExceptionFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.updateSubjectState(request);
			}
		}.runTest();
		verify(participantRepository);
		reset(participantRepository);

	}

}
