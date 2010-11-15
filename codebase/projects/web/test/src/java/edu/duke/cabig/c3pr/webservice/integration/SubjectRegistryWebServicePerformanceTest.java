package edu.duke.cabig.c3pr.webservice.integration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.yourkit.api.Controller;
import com.yourkit.api.ProfilingModes;

import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.DSETAdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DSETStudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.ImportStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubjectConsentVersion;
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
		if (!noEmbeddedTomcat) {
			super.tearDown();
		}
	}
	
	@Override
	public void testSubjectRegistryUtility() throws InterruptedException,
			IOException, Exception {
		super.testSubjectRegistryUtility();
		try {
			importStudySubjects();
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
		dsetAdvanceSearchCriterionParameter.getItem().add(createAdvaceSearchParam());
		request.setSearchParameter(dsetAdvanceSearchCriterionParameter);
		
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		startProfiling();
		DSETStudySubject studySubjects = service.querySubjectRegistry(request).getStudySubjects();
		stopProfiling();
		assertNotNull(studySubjects);
		assertEquals(1, studySubjects.getItem().size());
		
		dsetAdvanceSearchCriterionParameter.getItem().clear();
		dsetAdvanceSearchCriterionParameter.getItem().add(createAdvaceSearchParam("like", "%%%"));
		context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		startProfiling();
		studySubjects = service.querySubjectRegistry(request).getStudySubjects();
		stopProfiling();
		assertNotNull(studySubjects);
		assertEquals(42, studySubjects.getItem().size());
	}
	
	private void importStudySubjects() throws Exception{
		SubjectRegistry service = getService();

		// successful creation
		final ImportStudySubjectRegistryRequest request = new ImportStudySubjectRegistryRequest();
		request.setStudySubjects(new DSETStudySubject());
		for(int study=500 ; study<502 ; study++){
			for(int subject=500 ; subject<520 ; subject++){
				StudySubject studySubject = createStudySubjectForImport();
				//Override some values
				((Person)studySubject.getEntity()).setRaceCode(iso.DSETCD(iso.CD(RACE_WHITE)));
				((Person)studySubject.getEntity()).setTelecomAddress(iso.BAGTEL(iso.TEL(TEST_EMAIL_ADDR_ISO)));
				((Person)studySubject.getEntity()).getBiologicEntityIdentifier().get(0).getIdentifier().setExtension(subject+"");
				
				studySubject.getSubjectIdentifier().get(0).getIdentifier().setExtension(500+ (subject-500)+ 20*(study-500)+"");
				
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
		assertEquals(40, createdStudySubjects.getItem().size());
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
	public static AdvanceSearchCriterionParameter createAdvaceSearchParam(String predicate, String identifierValue) {
		AdvanceSearchCriterionParameter param = new AdvanceSearchCriterionParameter();
		param.setAttributeName(iso.ST(TEST_ATTRIBUTE_NAME));
		param.setObjectContextName(iso.ST(TEST_OBJ_CTX_NAME));
		param.setObjectName(iso.ST(TEST_OBJ_NAME));
		param.setPredicate(iso.CD(predicate));
		param.setValues(iso.DSETST(Arrays.asList(new ST[] {iso.ST(identifierValue) })));
		return param;
	}
}
