/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.studyutility;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.classextension.EasyMock.createNiceMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.reset;
import static org.easymock.classextension.EasyMock.verify;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.test.AssertThrows;

import edu.duke.cabig.c3pr.dao.ConsentDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.BeanUtils;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.Consent;
import edu.duke.cabig.c3pr.webservice.common.DSETAdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.helpers.WebServiceRelatedTestCase;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.UpdateMode;
import edu.duke.cabig.c3pr.webservice.studyutility.impl.StudyUtilityImpl;

/**
 * @author dkrylov
 * 
 */
public class StudyUtilityImplTest extends WebServiceRelatedTestCase {

	private StudyRepository studyRepository;

	private ConsentDao consentDao;

	private StudyUtilityImpl service;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		studyRepository = createNiceMock(StudyRepository.class);
		consentDao = createNiceMock(ConsentDao.class);

		service = new StudyUtilityImpl();
		service.setConsentDao(consentDao);
		service.setConverter(converter);
		service.setRegistryStatusDao(registryStatusDao);
		service.setStudyRepository(studyRepository);

	}

	public void commented_testCreateStudy() throws C3PRCodedException,
			StudyUtilityFaultMessage {

		// successful study creation
		StudyProtocolVersion version = createStudy();
		final CreateStudyAbstractRequest request = new CreateStudyAbstractRequest();
		request.setStudy(version);
		expect(studyRepository.getByPrimaryIdentifier(isA(Identifier.class))).andReturn(null);
		studyRepository.save(isA(Study.class));
		replay(studyRepository);
		CreateStudyAbstractResponse response = service.createStudyAbstract(request);
		assertNotNull(response.getStudy());
		assertTrue(BeanUtils.deepCompare(version, response.getStudy()));
		verify(studyRepository);
		reset(studyRepository);

		// study already exists
		expect(studyRepository.getByPrimaryIdentifier(isA(Identifier.class))).andReturn(
				(Study) (new LocalStudy()));
		replay(studyRepository);
		new AssertThrows(StudyUtilityFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.createStudyAbstract(request);
			}
		}.runTest();
		verify(studyRepository);
		reset(studyRepository);

		// empty identifiers
		version.getStudyProtocolDocument().getDocument()
				.getDocumentIdentifier().clear();
		new AssertThrows(StudyUtilityFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.createStudyAbstract(request);
			}
		}.runTest();
		reset(studyRepository);

	}

	public void testQueryStudyAbstract() throws StudyUtilityFaultMessage {

		// successful search
		QueryStudyAbstractRequest request = new QueryStudyAbstractRequest();
		AdvanceSearchCriterionParameter param = createAdvaceSearchParam();
		request.setParameters(new DSETAdvanceSearchCriterionParameter());
		request.getParameters().getItem().add(param);
		Study study = createDomainStudy();

		expect(studyRepository.search(isA(List.class))).andReturn(
				Arrays.asList(new Study[] { study }));
		replay(studyRepository);

		QueryStudyAbstractResponse response = service
				.queryStudyAbstract(request);
		DSETStudyProtocolVersion subjects = response.getStudies();
		List<StudyProtocolVersion> listOfSubjects = subjects.getItem();
		assertTrue(CollectionUtils.isNotEmpty(listOfSubjects));
		assertEquals(1, listOfSubjects.size());
		assertTrue(BeanUtils.deepCompare(converter.convert(study),
				listOfSubjects.get(0)));

		verify(studyRepository);
		reset(studyRepository);

	}

	public void testQueryStudyRegistryStatus()
			throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage {

		final QueryStudyRegistryStatusRequest request = new QueryStudyRegistryStatusRequest();
		DocumentIdentifier studyId = createStudyPrimaryIdentifier();
		request.setStudyIdentifier(studyId);
		Study study = createDomainStudy();

		expect(studyRepository.getByPrimaryIdentifier(isA(Identifier.class))).andReturn(
				study);
		replay(studyRepository);

		List<PermissibleStudySubjectRegistryStatus> list = service
				.queryStudyRegistryStatus(request).getStatuses().getItem();
		assertEquals(1, list.size());
		assertTrue(BeanUtils.deepCompare(list.get(0), converter.convert(study
				.getPermissibleStudySubjectRegistryStatuses().get(0))));
		verify(studyRepository);
		reset(studyRepository);

		final AssertThrows assertThrows = new AssertThrows(
				StudyUtilityFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.queryStudyRegistryStatus(request);
			}
		};

		testGetSingleStudySemantics(assertThrows);

	}

	/**
	 * @param assertThrows
	 */
	private void testGetSingleStudySemantics(final AssertThrows assertThrows) {
		testStudyNotFound(assertThrows);

		// more than one study
		expect(studyRepository.getByIdentifiers(isA(List.class))).andReturn(
				Arrays.asList(new Study[] { createDomainStudy(),
						createDomainStudy() }));
		replay(studyRepository);

		assertThrows.runTest();
		reset(studyRepository);
	}

	/**
	 * @param assertThrows
	 */
	private void testStudyNotFound(final AssertThrows assertThrows) {
		// study not found.
		expect(studyRepository.getByIdentifiers(isA(List.class))).andReturn(
				Arrays.asList(new Study[] {}));
		replay(studyRepository);
		assertThrows.runTest();
		reset(studyRepository);
	}

	public void testQueryRegistryStatus() throws SecurityExceptionFaultMessage,
			StudyUtilityFaultMessage {

		// single status by code
		final QueryRegistryStatusRequest request = new QueryRegistryStatusRequest();
		CD cd = iso.CD(TEST_REGISTRY_STATUS);
		request.setStatusCode(cd);
		DSETRegistryStatus list = service.queryRegistryStatus(request)
				.getRegistryStatuses();
		assertEquals(1, list.getItem().size());
		assertTrue(BeanUtils.deepCompare(list.getItem().get(0),
				converter.convert(registryStatus)));

		// all statuses
		cd.setCode(null);
		cd.setNullFlavor(NullFlavor.NI);
		list = service.queryRegistryStatus(request).getRegistryStatuses();
		assertEquals(2, list.getItem().size());
		assertTrue(BeanUtils.deepCompare(list.getItem().get(0),
				converter.convert(registryStatus)));
		assertTrue(BeanUtils.deepCompare(list.getItem().get(1),
				converter.convert(registryStatus2)));

	}

	public void testUpdateStudyStatus() throws C3PRCodedException,
			SecurityExceptionFaultMessage, StudyUtilityFaultMessage {

		// successful update.
		final UpdateStudyStatusRequest request = new UpdateStudyStatusRequest();
		DocumentIdentifier studyId = createStudyPrimaryIdentifier();
		PermissibleStudySubjectRegistryStatus status = createPermissibleStudySubjectRegistryStatus();
		Study study = createDomainStudy();
		request.setStudyIdentifier(studyId);
		request.setStatus(status);
		request.setUpdateMode(UpdateMode.R);

		expect(studyRepository.getByPrimaryIdentifier(isA(Identifier.class))).andReturn(
				study);
		studyRepository.save(study);
		replay(studyRepository);

		PermissibleStudySubjectRegistryStatus updatedStatus = service
				.updateStudyStatus(request).getStatus();
		assertTrue(BeanUtils.deepCompare(updatedStatus, status));
		assertEquals(1, study
				.getPermissibleStudySubjectRegistryStatusesInternal().size());
		assertTrue(BeanUtils.deepCompare(converter.convert(study
				.getPermissibleStudySubjectRegistryStatusesInternal().get(0)),
				updatedStatus));
		verify(studyRepository);
		reset(studyRepository);

		final AssertThrows assertThrows = new AssertThrows(
				StudyUtilityFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.updateStudyStatus(request);
			}
		};
		testGetSingleStudySemantics(assertThrows);

	}

	public void testQueryConsent() throws SecurityExceptionFaultMessage,
			StudyUtilityFaultMessage {

		Study study = createDomainStudy();
		List<edu.duke.cabig.c3pr.domain.Consent> domainConsents = study
				.getConsents();

		// consent by example.
		final QueryStudyConsentRequest request = new QueryStudyConsentRequest();
		DocumentIdentifier studyId = createStudyPrimaryIdentifier();
		Consent consent = createConsent();
		request.setStudyIdentifier(studyId);
		request.setConsent(consent);

		expect(studyRepository.getByPrimaryIdentifier(isA(Identifier.class))).andReturn(
				study);
		expect(
				consentDao.searchByExampleAndStudy(
						isA(edu.duke.cabig.c3pr.domain.Consent.class),
						isA(Study.class))).andReturn(domainConsents);
		replay(studyRepository, consentDao);

		List<Consent> list = service.queryStudyConsent(request).getConsents()
				.getItem();
		assertEquals(1, list.size());
		assertTrue(BeanUtils.deepCompare(list.get(0),
				converter.convertConsent(domainConsents.get(0))));
		verify(studyRepository, consentDao);
		reset(studyRepository, consentDao);

		// all 2 consents of a study
		domainConsents.add(createDomainConsent());
		request.setConsent(null);
		expect(studyRepository.getByPrimaryIdentifier(isA(Identifier.class))).andReturn(
				study);
		replay(studyRepository);
		list = service.queryStudyConsent(request).getConsents().getItem();
		assertEquals(2, list.size());
		assertTrue(BeanUtils.deepCompare(list.get(0),
				converter.convertConsent(domainConsents.get(0))));
		assertTrue(BeanUtils.deepCompare(list.get(1),
				converter.convertConsent(domainConsents.get(1))));
		verify(studyRepository);
		reset(studyRepository);

		final AssertThrows assertThrows = new AssertThrows(
				StudyUtilityFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.queryStudyConsent(request);
			}
		};
		testGetSingleStudySemantics(assertThrows);

	}

	/**
	 * @throws C3PRCodedException
	 * @throws SecurityExceptionFaultMessage
	 * @throws StudyUtilityFaultMessage
	 */
	public void testUpdateConsent() throws C3PRCodedException,
			SecurityExceptionFaultMessage, StudyUtilityFaultMessage {

		Study study = createDomainStudy();

		// successful update.
		final UpdateStudyConsentRequest request = new UpdateStudyConsentRequest();
		DocumentIdentifier studyId = createStudyPrimaryIdentifier();
		Consent consent = createConsent();
		request.setStudyIdentifier(studyId);
		request.setConsent(consent);
		request.setUpdateMode(UpdateMode.U);

		expect(studyRepository.getByPrimaryIdentifier(isA(Identifier.class))).andReturn(
				study);
		studyRepository.save(study);
		replay(studyRepository);

		Consent updatedConsent = service.updateStudyConsent(request).getConsent();
//		consent.getVersionNumberText().setUpdateMode(null);
		assertTrue(BeanUtils.deepCompare(updatedConsent, consent));
		assertEquals(1, study.getConsents().size());
		assertTrue(BeanUtils.deepCompare(
				converter.convertConsent(study.getConsents().get(0)),
				(updatedConsent)));
		verify(studyRepository);
		reset(studyRepository);

		final AssertThrows assertThrows = new AssertThrows(
				StudyUtilityFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.updateStudyConsent(request);
			}
		};
		testGetSingleStudySemantics(assertThrows);
	}

	public void commented_testUpdateStudy() throws C3PRCodedException,
			StudyUtilityFaultMessage {

		// successful study update
		StudyProtocolVersion version = createStudy();
		final Study existentStudy = converter.convert(version);
		version.getStudyProtocolDocument().setPublicTitle(
				iso.ST(TEST_STUDY_DESCR + "CHANGED"));
		final UpdateStudyAbstractRequest request = new UpdateStudyAbstractRequest();
		request.setStudy(version);
		expect(studyRepository.getByPrimaryIdentifier(isA(Identifier.class))).andReturn(
				existentStudy);
		studyRepository.save(isA(Study.class));
		replay(studyRepository);
		UpdateStudyAbstractResponse response = service.updateStudyAbstract(request);
		assertNotNull(response.getStudy());
		assertTrue(BeanUtils.deepCompare(version, response.getStudy()));
		verify(studyRepository);
		reset(studyRepository);

		// study does not exist
		final AssertThrows assertThrows = new AssertThrows(
				StudyUtilityFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.updateStudyAbstract(request);
			}
		};
		testStudyNotFound(assertThrows);

		// empty identifiers
		version.getStudyProtocolDocument().getDocument()
				.getDocumentIdentifier().clear();
		new AssertThrows(StudyUtilityFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.updateStudyAbstract(request);
			}
		}.runTest();
		reset(studyRepository);

	}

}
