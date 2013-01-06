package edu.duke.cabig.c3pr.webservice.subjectregistry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.RegistryStatusDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.webservice.helpers.SubjectRegistryRelatedTestCase;
import edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.subjectregistry.impl.SubjectRegistryImpl;

public class SubjectRegistryImplTest extends SubjectRegistryRelatedTestCase {

	private SubjectRegistryJAXBToDomainObjectConverter converter;
	private ParticipantDao participantDao;
	private StudyRepository studyRepository;
	private StudySubjectRepository studySubjectRepository;
	private StudySubjectDao studySubjectDao;
	private RegistryStatusDao registryStatusDao;
	private SubjectRegistryImpl subjectRegistryImpl;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		subjectRegistryImpl = new SubjectRegistryImpl();
		participantDao = registerMockFor(ParticipantDao.class);
		studyRepository = registerMockFor(StudyRepository.class);
		studySubjectRepository = registerMockFor(StudySubjectRepository.class);
		studySubjectDao = registerDaoMockFor(StudySubjectDao.class);
		registryStatusDao = registerMockFor(RegistryStatusDao.class);
		converter = registerMockFor(SubjectRegistryJAXBToDomainObjectConverter.class);
		C3PRExceptionHelper exceptionHelper = new C3PRExceptionHelper(
				getMessageSourceMock());
		subjectRegistryImpl.setExceptionHelper(exceptionHelper);
		subjectRegistryImpl.setParticipantDao(participantDao);
		subjectRegistryImpl.setRegistryStatusDao(registryStatusDao);
		subjectRegistryImpl.setStudyRepository(studyRepository);
		subjectRegistryImpl.setStudySubjectDao(studySubjectDao);
		subjectRegistryImpl.setStudySubjectRepository(studySubjectRepository);
		subjectRegistryImpl.setConverter(converter);
	}
	
	public void testInitiateSubjectRegistry() throws Exception{
		StudySubject input = createStudySubjectJAXBObject();
		InitiateStudySubjectRegistryRequest request = new InitiateStudySubjectRegistryRequest();
		request.setStudySubject(input);
//		UpdateSubjectRegistryStatusRequest request = new UpdateSubjectRegistryStatusRequest();
//		request.setStudySubjectIdentifier(createSubjectId());
//		request.setStudySubjectStatus(createStatus());
//		InitiateSubjectRegistryRequest param = new InitiateSubjectRegistryRequest();
//		param.setStudySubject(input);
//		EasyMock.expect(converter.convertSubjectIdentifiers(input.getSubjectIdentifier())).andReturn(Arrays.asList(new Identifier[]{new OrganizationAssignedIdentifier()}));
//		EasyMock.expect(studySubjectRepository.getUniqueStudySubject(EasyMock.isA(Identifier.class))).andThrow(new C3PRCodedRuntimeException(231,"some message"));
//		EasyMock.expect(converter.convertBiologicIdentifiers(input.getEntity().getBiologicEntityIdentifier())).andReturn(Arrays.asList(new Identifier[]{new OrganizationAssignedIdentifier()}));
//		EasyMock.expect(participantDao.getByIdentifiers(EasyMock.isA(List.class))).andReturn(Arrays.asList(new Participant[]{new Participant()}));
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
	}
	
}
