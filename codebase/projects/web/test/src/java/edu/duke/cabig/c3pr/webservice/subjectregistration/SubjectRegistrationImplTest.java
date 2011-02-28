package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.RegistryStatusDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.DiseaseHistory;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.webservice.helpers.SubjectRegistryRelatedTestCase;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.subjectregistration.impl.SubjectRegistrationImpl;
import edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.subjectregistry.impl.SubjectRegistryImpl;

public class SubjectRegistrationImplTest extends SubjectRegistryRelatedTestCase {

	private SubjectRegistrationJAXBToDomainObjectConverter converter;
	private ParticipantDao participantDao;
	private StudyRepository studyRepository;
	private StudySubjectRepository studySubjectRepository;
	private StudySubjectDao studySubjectDao;
	private SubjectRegistrationImpl subjectRegistrationImpl;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		subjectRegistrationImpl = new SubjectRegistrationImpl();
		participantDao = registerMockFor(ParticipantDao.class);
		studyRepository = registerMockFor(StudyRepository.class);
		studySubjectRepository = registerMockFor(StudySubjectRepository.class);
		studySubjectDao = registerDaoMockFor(StudySubjectDao.class);
		converter = registerMockFor(SubjectRegistrationJAXBToDomainObjectConverter.class);
		C3PRExceptionHelper exceptionHelper = new C3PRExceptionHelper(
				getMessageSourceMock());
		subjectRegistrationImpl.setExceptionHelper(exceptionHelper);
		subjectRegistrationImpl.setParticipantDao(participantDao);
		subjectRegistrationImpl.setStudyRepository(studyRepository);
		subjectRegistrationImpl.setStudySubjectDao(studySubjectDao);
		subjectRegistrationImpl.setStudySubjectRepository(studySubjectRepository);
		subjectRegistrationImpl.setConverter(converter);
	}
	
	
}
