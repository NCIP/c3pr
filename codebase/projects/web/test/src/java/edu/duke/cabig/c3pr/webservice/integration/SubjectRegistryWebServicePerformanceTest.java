package edu.duke.cabig.c3pr.webservice.integration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.yourkit.api.Controller;
import com.yourkit.api.ProfilingModes;

import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.DSETAdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DSETStudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.ImportStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.SubjectRegistry;

public class SubjectRegistryWebServicePerformanceTest extends SubjectRegistryWebServiceTest {

	private static final QName SERVICE_NAME = new QName(
			"http://enterpriseservices.nci.nih.gov/SubjectRegistryService",
			"SubjectRegistryService");	
	private static final String WS_ENDPOINT_SERVLET_PATH = "/services/services/SubjectRegistry";

	private URL endpointURL;

	private URL wsdlLocation;
	
	private Controller controller = null;
	
	private boolean noEmbeddedTomcat = Boolean.valueOf(System.getProperty(
			"noEmbeddedTomcat", "false"));
	
	@Override
	protected void setUp() throws Exception {
		if (noEmbeddedTomcat) {
			endpointURL = new URL(
					"https://localhost:8443/c3pr/services/services/SubjectRegistry");
			controller = new Controller("localhost",10001);
			initDataSourceFile();
		} else {
			super.setUp();
			endpointURL = new URL("https://"
					+ InetAddress.getLocalHost().getHostName() + ":" + sslPort
					+ C3PR_CONTEXT + WS_ENDPOINT_SERVLET_PATH);
			controller = new Controller(InetAddress.getLocalHost().getHostName(),10001);
		}
		wsdlLocation = new URL(endpointURL.toString() + "?wsdl");
		logger.info("endpointURL: " + endpointURL);
		logger.info("wsdlLocation: " + wsdlLocation);
	}

	@Override
	protected void tearDown() throws Exception {
//		if (!noEmbeddedTomcat) {
//			super.tearDown();
//		}
	}
	
	@Override
	public void testSubjectRegistryUtility() throws InterruptedException,
			IOException, Exception {
//		super.testSubjectRegistryUtility();
		try {
//			importStudySubjects(500, 505, 500, 520, "");
//			importStudySubjects(500, 510, 500, 520, "");
//			importStudySubjects(505, 510, 500, 520, "");
			executeBulkQuerySubjectRegistryTest();
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getFullStackTrace(e));
			fail(ExceptionUtils.getFullStackTrace(e));
		}
		
	}
	
	private void executeBulkQuerySubjectRegistryTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final QueryStudySubjectRegistryRequest request = new QueryStudySubjectRegistryRequest();
		DSETAdvanceSearchCriterionParameter dsetAdvanceSearchCriterionParameter = new DSETAdvanceSearchCriterionParameter();
		dsetAdvanceSearchCriterionParameter.getItem().add(createAdvanceSearchParam("like", "%%%"));
		request.setSearchParameter(dsetAdvanceSearchCriterionParameter);
		
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		startProfiling();
		List<StudySubject> studySubjects = service.querySubjectRegistry(request).getStudySubjects().getItem();
		stopProfiling();
		assertEquals(200, studySubjects.size());
//		startProfiling();
//		request.setReduceGraph(iso.BL(true));
//		service.querySubjectRegistry(request).getStudySubjects();
//		stopProfiling();
//		assertEquals(200, studySubjects.size());
	}
	
	private void executeBulkGradedQuerySubjectRegistryTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final QueryStudySubjectRegistryRequest request = new QueryStudySubjectRegistryRequest();
		DSETAdvanceSearchCriterionParameter dsetAdvanceSearchCriterionParameter = new DSETAdvanceSearchCriterionParameter();
		dsetAdvanceSearchCriterionParameter.getItem().add(createAdvanceSearchParam("=", "500"));
		request.setSearchParameter(dsetAdvanceSearchCriterionParameter);
		
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		startProfiling();
		service.querySubjectRegistry(request).getStudySubjects();
		stopProfiling();
		startProfiling();
		DSETStudySubject studySubjects = service.querySubjectRegistry(request).getStudySubjects();
		stopProfiling();
		assertNotNull(studySubjects);
		assertEquals(20, studySubjects.getItem().size());
		dsetAdvanceSearchCriterionParameter.getItem().add(createAdvanceSearchParam("like", "%-100"));
		startProfiling();
		studySubjects = service.querySubjectRegistry(request).getStudySubjects();
		stopProfiling();
		assertNotNull(studySubjects);
		assertEquals(100, studySubjects.getItem().size());
		dsetAdvanceSearchCriterionParameter.getItem().add(createAdvanceSearchParam("like", "%-200"));
		startProfiling();
		studySubjects = service.querySubjectRegistry(request).getStudySubjects();
		stopProfiling();
		assertNotNull(studySubjects);
		assertEquals(200, studySubjects.getItem().size());
		dsetAdvanceSearchCriterionParameter.getItem().add(createAdvanceSearchParam("like", "%-300"));
		startProfiling();
		studySubjects = service.querySubjectRegistry(request).getStudySubjects();
		stopProfiling();
		assertNotNull(studySubjects);
		assertEquals(300, studySubjects.getItem().size());
		dsetAdvanceSearchCriterionParameter.getItem().add(createAdvanceSearchParam("like", "%-400"));
		startProfiling();
		studySubjects = service.querySubjectRegistry(request).getStudySubjects();
		stopProfiling();
		assertNotNull(studySubjects);
		assertEquals(400, studySubjects.getItem().size());
	}
	
	private void importStudySubjects(int sbegin, int send, int pbegin, int pend, String batch) throws Exception{
		SubjectRegistry service = getService();

		// successful creation
		final ImportStudySubjectRegistryRequest request = new ImportStudySubjectRegistryRequest();
		request.setStudySubjects(new DSETStudySubject());
		//String batch = "";
		for(int start=sbegin ; start<send ; start++){
			int study = start;
			//batch += "-"+(400-20*(study-500));
			for(int subject=pbegin ; subject<pend ; subject++){
				StudySubject studySubject = createStudySubjectForImport();
				//Override some values
				((Person)studySubject.getEntity()).setRaceCode(iso.DSETCD(iso.CD(RACE_WHITE)));
				((Person)studySubject.getEntity()).setTelecomAddress(iso.BAGTEL(iso.TEL(TEST_EMAIL_ADDR_ISO)));
				((Person)studySubject.getEntity()).getBiologicEntityIdentifier().get(0).getIdentifier().setExtension(subject+"");
				
				studySubject.getSubjectIdentifier().get(0).getIdentifier().setExtension(RandomStringUtils.randomAlphanumeric(6)+"--"+batch);
				studySubject.getSubjectIdentifier().get(0).setPrimaryIndicator(iso.BL(true));
				
				studySubject.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().
					getStudyProtocolDocument().getDocument().getDocumentIdentifier().get(0).getIdentifier().setExtension(study+"");
				
				//add 1st consent
				studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().clear();
				StudySubjectConsentVersion studySubjectConsentVersion = new StudySubjectConsentVersion();
				studySubjectConsentVersion.setConsentDeliveryDate(iso.TSDateTime(TEST_CONSENT_DELIVERY_DATE1));
				studySubjectConsentVersion.setInformedConsentDate(iso.TSDateTime(TEST_CONSENT_SIGNED_DATE1));
				studySubjectConsentVersion.setConsentingMethod(iso.CD(TEST_CONSENTING_METHOD1));
				studySubjectConsentVersion.setConsentPresenter(iso.ST(TEST_CONSENT_PRESENTER1));
				studySubjectConsentVersion.setConsent(new DocumentVersion());
				studySubjectConsentVersion.getConsent().setOfficialTitle(iso.ST(TEST_CONSENT_NAME1));
				studySubjectConsentVersion.getConsent().setVersionNumberText(iso.ST(TEST_CONSENT_VERSION1));
				PerformedStudySubjectMilestone subjectAnswer = new PerformedStudySubjectMilestone();
				subjectAnswer.setMissedIndicator(iso.BL(TEST_CONSENT_ANS11));
				subjectAnswer.setConsentQuestion(new DocumentVersion());
				subjectAnswer.getConsentQuestion().setOfficialTitle(iso.ST(TEST_CONSENT_QUES11));
				studySubjectConsentVersion.getSubjectConsentAnswer().add(subjectAnswer);
				studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().add(studySubjectConsentVersion);
				
				request.getStudySubjects().getItem().add(studySubject);
			}
		}
		
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		DSETStudySubject createdStudySubjects = service.importSubjectRegistry(request).getStudySubjects();
		assertNotNull(createdStudySubjects);
//		assertEquals(scount*pcount, createdStudySubjects.getItem().size());
	}
	
	private void startProfiling(){
		if (!noEmbeddedTomcat){
			try {
				controller.startCPUProfiling(ProfilingModes.CPU_SAMPLING|ProfilingModes.CPU_J2EE, "");
			} catch (Exception e) {
				System.out.println("Error profiling embedded tomcat using yourkit.");
				e.printStackTrace();
				System.out.println("skipping yourkit profiling");
			}
		}
	}
	
	private void stopProfiling(){
		if (!noEmbeddedTomcat){
			try {
				String snapshotFilePath = controller.captureSnapshot(ProfilingModes.SNAPSHOT_WITHOUT_HEAP);
				controller.stopCPUProfiling();
				System.out.println(snapshotFilePath);
			} catch (Exception e) {
				// yourkit profiler error. Continue without reporting.
			}
		}
	}
	/**
	 * @return
	 */
	public static AdvanceSearchCriterionParameter createAdvanceSearchParam(String predicate, String identifierValue) {
		AdvanceSearchCriterionParameter param = new AdvanceSearchCriterionParameter();
		param.setAttributeName(iso.ST("value"));
		param.setObjectContextName(iso.ST("StudySubject"));
		param.setObjectName(iso.ST("edu.duke.cabig.c3pr.domain.Identifier"));
		param.setPredicate(iso.CD(predicate));
		param.setValues(iso.DSETST(Arrays.asList(new ST[] {iso.ST(identifierValue) })));
		return param;
	}
}
