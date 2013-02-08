/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.integration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;

import edu.duke.cabig.c3pr.webservice.common.Consent;
import edu.duke.cabig.c3pr.webservice.common.Document;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.RegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.studyutility.CreateStudyAbstractRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility;
import edu.duke.cabig.c3pr.webservice.studyutility.StudyUtilityService;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.CreateSubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementService;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject;

public class SubjectRegistryMayoUseCaseIntegrationTest extends
		SubjectRegistryWebServiceTest {

	private static final String TEST_TARGET_REG_SYS = "C3PR";
	private static final String TEST_VERSION_NUMBER = "1.0";
	private static final String TEST_VERSION_DATE_ISO = "20101005000000";
	private static final String TEST_CONSENT_QUESTION_RELATIONSHIP = "CONSENT_QUESTION";
	private static final String TEST_CONSENT_RELATIONSHIP = "CONSENT";
	private static final String TEST_DEATH_DATE_ISO = "19900101000000";
	
	private static final QName STUDY_UTILITY_SERVICE_NAME = new QName(
			"http://enterpriseservices.nci.nih.gov/StudyUtilityService",
			"StudyUtilityService");
	private static final String STUDY_UTILITY_WS_ENDPOINT_SERVLET_PATH = "/services/services/StudyUtility";
	private URL studyUtilityEndpointURL; 
	private URL studyUtilityWsdlLocation;

	private static final QName SUBJECT_MANAGEMENT_SERVICE_NAME = new QName(
			"http://enterpriseservices.nci.nih.gov/SubjectManagementService",
			"SubjectManagementService");
	private static final String SUBJECT_MANAGEMENT_WS_ENDPOINT_SERVLET_PATH = "/services/services/SubjectManagement";
	private URL subjectManagementEndpointURL;
	private URL subjectManagementWsdlLocation;
	
	private static final ISO21090Helper iso = new ISO21090Helper();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (noEmbeddedTomcat) {
			studyUtilityEndpointURL = new URL(
					"https://localhost:8443/c3pr/services/services/StudyUtility");
			subjectManagementEndpointURL = new URL(
					"https://localhost:8443/c3pr/services/services/SubjectManagement");
		} else {
			studyUtilityEndpointURL = new URL("https://"
					+ InetAddress.getLocalHost().getHostName() + ":" + sslPort
					+ C3PR_CONTEXT + STUDY_UTILITY_WS_ENDPOINT_SERVLET_PATH);
			subjectManagementEndpointURL = new URL("https://"
					+ InetAddress.getLocalHost().getHostName() + ":" + sslPort
					+ C3PR_CONTEXT + SUBJECT_MANAGEMENT_WS_ENDPOINT_SERVLET_PATH);
		}
		studyUtilityWsdlLocation = new URL(studyUtilityEndpointURL.toString() + "?wsdl");
		subjectManagementWsdlLocation = new URL(subjectManagementEndpointURL.toString() + "?wsdl");
		
		logger.info("studyUtilityEndpointURL: " + studyUtilityEndpointURL);
		logger.info("studyUtilityWsdlLocation: " + studyUtilityWsdlLocation);
		logger.info("subjectManagementEndpointURL: " + subjectManagementEndpointURL);
		logger.info("subjectManagementWsdlLocation: " + subjectManagementWsdlLocation);

	}
	
	@Override
	public void testSubjectRegistryUtility() throws InterruptedException,
			IOException, Exception {
		executeCreateStudyTest();
		executeCreateSubjectTest();
		executeInitiateStudySubjectTest();
		executeUpdateStudySubjectConsentTest();
		executeUpdateStudySubjectRegistryStatusTest();
		executeUpdateStudySubjectRegistryStatusHistoryTest();
		executeUpdatetudySubjectTest();
		executeQuerySubjectRegistryTest();
		executeUpdateStudySubjectDemographyTest();
		executeRetrieveStudySubjectDemographyHistoryTest();
		executeQueryStudySubjectRegistryStatusHistoryTest();
		executeQuerySubjectRegistryByRegistryStatusTest();
		executeQuerySubjectRegistryByConsentTest();
		executeQueryConsentsByStudySubjectTest();
	}
	
	private void executeCreateStudyTest() throws SQLException, Exception {
		StudyUtility service = getStudyUtilityService();

		// successful creation
		final CreateStudyAbstractRequest request = new CreateStudyAbstractRequest();
		StudyProtocolVersion study = createStudy();
		request.setStudy(study);
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.studyutility");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		System.out.println();
		StudyProtocolVersion createdStudy = service.createStudyAbstract(request)
				.getStudy();
		assertNotNull(createdStudy);
	}
	
	private void executeCreateSubjectTest() throws DataSetException,
	IOException, SQLException, DatabaseUnitException, Exception {
		SubjectManagement service = getSubjectManagementService();
		
		// successful subject creation.
		final CreateSubjectRequest request = new CreateSubjectRequest();
		Subject subject = createSubject();
		request.setSubject(subject);
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectmanagement");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		System.out.println();
		Subject createdSubject = service.createSubject(request).getSubject();
		assertNotNull(createdSubject);
	}
	
	/**
	 * @param person
	 * @return
	 */
	protected Subject createSubject() {
		Subject s = new Subject();
		s.setEntity(createPerson());
		s.getEntity().setDeathDate(iso.TSDateTime(TEST_DEATH_DATE_ISO));
		s.setStateCode(iso.ST("ACTIVE"));
		return s;
	}

	/**
	 * 
	 */
	private StudyUtility getStudyUtilityService() {
		StudyUtilityService service = new StudyUtilityService(studyUtilityWsdlLocation,
				STUDY_UTILITY_SERVICE_NAME);
		SOAPUtils.installSecurityHandler(service);
		StudyUtility client = service.getStudyUtility();
		return client;
	}
	
	private SubjectManagement getSubjectManagementService() {
		SubjectManagementService service = new SubjectManagementService(
				subjectManagementWsdlLocation, SUBJECT_MANAGEMENT_SERVICE_NAME);
		SOAPUtils.installSecurityHandler(service);
		SubjectManagement client = service.getSubjectManagement();
		return client;
	}

	
	public StudyProtocolVersion createStudy() {
		StudyProtocolVersion study = new StudyProtocolVersion();
		study.setTargetRegistrationSystem(iso.ST(TEST_TARGET_REG_SYS));
		study.setStudyProtocolDocument(createStudyProtocolDocument());
		study.getPermissibleStudySubjectRegistryStatus().addAll(
				createPermissibleStudySubjectRegistryStatuses());
		return study;
	}

	protected List<PermissibleStudySubjectRegistryStatus> createPermissibleStudySubjectRegistryStatuses() {
		List<PermissibleStudySubjectRegistryStatus> list = new ArrayList<PermissibleStudySubjectRegistryStatus>();
		PermissibleStudySubjectRegistryStatus stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus("Pre-Enrolled"));
		list.add(stat);
		stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus("Enrolled"));
		list.add(stat);
		stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus("Screen Failed"));
		stat.getSecondaryReason().add(createSecondaryRegistryStatusReason("Lab_Out_Of_Range1","FAILED INCLUSION"));
		stat.getSecondaryReason().add(createSecondaryRegistryStatusReason("Lab_Out_Of_Range2","FAILED EXCLUSION"));
		list.add(stat);
		stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus("Accrued"));
		list.add(stat);
		stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus("Active Intervention"));
		list.add(stat);
		stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus("Long-Term Followup"));
		list.add(stat);
		stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus("Observation"));
		list.add(stat);
		stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus("Withdrawn"));
		stat.getSecondaryReason().add(createSecondaryRegistryStatusReason("Distance","UNWILLING"));
		stat.getSecondaryReason().add(createSecondaryRegistryStatusReason("Schedule","UNWILLING"));
		list.add(stat);
		stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus("Declined Consent"));
		list.add(stat);
		stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus("Completed"));
		return list;
	}

	protected RegistryStatus createRegistryStatus(String status) {
		RegistryStatus stat = new RegistryStatus();
		stat.setCode(iso.CD(status));
		stat.setDescription(iso.ST(status));
		return stat;
	}

	protected RegistryStatusReason createSecondaryRegistryStatusReason(String reason, String primaryStatusReason) {
		RegistryStatusReason r = new RegistryStatusReason();
		r.setCode(iso.CD(reason));
		r.setDescription(iso.ST(reason));
		r.setPrimaryIndicator(iso.BL(false));
		RegistryStatusReason p = new RegistryStatusReason();
		p.setCode(iso.CD(primaryStatusReason));
		p.setDescription(iso.ST(primaryStatusReason));
		p.setPrimaryIndicator(iso.BL(true));
		r.setPrimaryReason(p);
		return r;
	}

	
	
	protected StudyProtocolDocumentVersion createStudyProtocolDocument() {
		StudyProtocolDocumentVersion doc = new StudyProtocolDocumentVersion();
		doc.setOfficialTitle(iso.ST(TEST_SHORTTITLE));
		doc.setPublicDescription(iso.ST(TEST_DESC));
		doc.setPublicTitle(iso.ST(TEST_LONGTITLE));
		doc.setText(iso.ED(TEST_DESC));
		doc.setVersionDate(iso.TSDateTime(TEST_VERSION_DATE_ISO));
		doc.setVersionNumberText(iso.ST(TEST_VERSION_NUMBER));
		doc.setDocument(createStudyDocument());
		doc.getDocumentVersionRelationship().addAll(
				createConsentRelationships());
		return doc;
	}

	protected List<DocumentVersionRelationship> createConsentRelationships() {
		List<DocumentVersionRelationship> list = new ArrayList<DocumentVersionRelationship>();
		
		DocumentVersionRelationship rel = new DocumentVersionRelationship();
		rel.setTypeCode(iso.CD(TEST_CONSENT_RELATIONSHIP));
		Consent consent = new Consent();
		consent.setMandatoryIndicator(iso.BL(true));
		consent.setOfficialTitle(iso.ST(TEST_CONSENT_NAME1));
		consent.setText(iso.ED(TEST_CONSENT_DESC1));
		consent.setVersionNumberText(iso.ST(TEST_CONSENT_VERSION1));
		consent.setDocument(new Document());
		DocumentVersionRelationship dvr = new DocumentVersionRelationship();
		dvr.setTypeCode(iso.CD(TEST_CONSENT_QUESTION_RELATIONSHIP));
		DocumentVersion q = new DocumentVersion();
		q.setOfficialTitle(iso.ST(TEST_CONSENT_QUES11));
		q.setText(iso.ED(TEST_CONSENT_QUES11));
		q.setDocument(new Document());
		dvr.setTarget(q);
		consent.getDocumentVersionRelationship().add(dvr);
		dvr = new DocumentVersionRelationship();
		dvr.setTypeCode(iso.CD(TEST_CONSENT_QUESTION_RELATIONSHIP));
		q = new DocumentVersion();
		q.setOfficialTitle(iso.ST(TEST_CONSENT_QUES12));
		q.setText(iso.ED(TEST_CONSENT_QUES12));
		q.setDocument(new Document());
		dvr.setTarget(q);
		consent.getDocumentVersionRelationship().add(dvr);
		rel.setTarget(consent);
		list.add(rel);
		
		rel = new DocumentVersionRelationship();
		rel.setTypeCode(iso.CD(TEST_CONSENT_RELATIONSHIP));
		consent = new Consent();
		consent.setMandatoryIndicator(iso.BL(true));
		consent.setOfficialTitle(iso.ST(TEST_CONSENT_NAME2));
		consent.setText(iso.ED(TEST_CONSENT_DESC2));
		consent.setVersionNumberText(iso.ST(TEST_CONSENT_VERSION2));
		consent.setDocument(new Document());
		dvr = new DocumentVersionRelationship();
		dvr.setTypeCode(iso.CD(TEST_CONSENT_QUESTION_RELATIONSHIP));
		q = new DocumentVersion();
		q.setOfficialTitle(iso.ST(TEST_CONSENT_QUES21));
		q.setText(iso.ED(TEST_CONSENT_QUES21));
		q.setDocument(new Document());
		dvr.setTarget(q);
		consent.getDocumentVersionRelationship().add(dvr);
		dvr = new DocumentVersionRelationship();
		dvr.setTypeCode(iso.CD(TEST_CONSENT_QUESTION_RELATIONSHIP));
		q = new DocumentVersion();
		q.setOfficialTitle(iso.ST(TEST_CONSENT_QUES22));
		q.setText(iso.ED(TEST_CONSENT_QUES22));
		q.setDocument(new Document());
		dvr.setTarget(q);
		consent.getDocumentVersionRelationship().add(dvr);
		rel.setTarget(consent);
		list.add(rel);
		
		return list;
	}

	protected Document createStudyDocument() {
		Document doc = new Document();
		doc.getDocumentIdentifier().add(createStudyPrimaryIdentifier());
		doc.getDocumentIdentifier().add(createSiteIdentifier());
		doc.getDocumentIdentifier().add(createStudyFundingSponsorIdentifier());
		return doc;
	}

	protected DocumentIdentifier createStudyFundingSponsorIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(iso.II(TEST_STUDY_ID));
		docId.setPrimaryIndicator(iso.BL(false));
		docId.setTypeCode(iso.CD("STUDY_FUNDING_SPONSOR"));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}

	protected DocumentIdentifier createStudyPrimaryIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(iso.II(TEST_STUDY_ID));
		docId.setPrimaryIndicator(iso.BL(true));
		docId.setTypeCode(iso.CD("COORDINATING_CENTER_IDENTIFIER"));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}

	protected DocumentIdentifier createSiteIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(iso.II(TEST_STUDY_ID));
		docId.setPrimaryIndicator(iso.BL(false));
		docId.setTypeCode(iso.CD("SITE_IDENTIFIER"));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}
	
	@Override
	public StudySubject createExpectedStudySubject() {
		StudySubject studySubject = super.createExpectedStudySubject();
		studySubject.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().clear();
		studySubject.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().add(createStudyPrimaryIdentifier());
		studySubject.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().add(createSiteIdentifier());
		studySubject.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().add(createStudyFundingSponsorIdentifier());
		return studySubject;
	}
	
	@Override
	public StudySubject createStudySubjectJAXBObjectModified() {
		StudySubject studySubject = super.createStudySubjectJAXBObjectModified();
		studySubject.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().clear();
		studySubject.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().add(createStudyPrimaryIdentifier());
		studySubject.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().add(createSiteIdentifier());
		studySubject.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().add(createStudyFundingSponsorIdentifier());
		return studySubject;
	}
}
