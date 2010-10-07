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

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.AssertThrows;

import edu.duke.cabig.c3pr.dao.ConsentDao;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.BeanUtils;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.helpers.WebServiceRelatedTestCase;
import edu.emory.mathcs.backport.java.util.Arrays;

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

	public void testCreateStudy() throws C3PRCodedException,
			StudyUtilityFaultMessage {

		// successful study creation
		StudyProtocolVersion version = createStudy();
		final CreateStudyRequest request = new CreateStudyRequest();
		request.setStudy(version);
		expect(studyRepository.getByIdentifiers(isA(List.class))).andReturn(
				new ArrayList<Study>());
		studyRepository.save(isA(Study.class));
		replay(studyRepository);
		CreateStudyResponse response = service.createStudy(request);
		assertNotNull(response.getStudy());
		assertTrue(BeanUtils.deepCompare(version, response.getStudy()));
		verify(studyRepository);
		reset(studyRepository);

		// study already exists
		expect(studyRepository.getByIdentifiers(isA(List.class))).andReturn(
				Arrays.asList(new Study[] { new LocalStudy() }));
		replay(studyRepository);
		new AssertThrows(StudyUtilityFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.createStudy(request);
			}
		}.runTest();
		verify(studyRepository);
		reset(studyRepository);
		
		// empty identifiers
		version.getStudyProtocolDocument().getDocument().getDocumentIdentifier().clear();
		new AssertThrows(StudyUtilityFaultMessage.class) {
			@Override
			public void test() throws Exception {
				service.createStudy(request);
			}
		}.runTest();		
		reset(studyRepository);		

	}

}
