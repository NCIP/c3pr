/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service;

import static edu.duke.cabig.c3pr.C3PRUseCase.IMPORT_STUDY;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.service.impl.StudyXMLImporterServiceImpl;
import edu.duke.cabig.c3pr.utils.MasqueradingDaoTestCase;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.xml.XMLParser;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 4, 2007 Time: 2:33:16 PM To
 * change this template use File | Settings | File Templates.
 */
@C3PRUseCases( { IMPORT_STUDY })
public class StudyXMLImporterTestCase extends MasqueradingDaoTestCase<StudyDao> {

	private StudyXMLImporterServiceImpl studyImporter;

	private HealthcareSiteDao healthcareSitedao;
	
	public XMLParser xmlParser;
	
	XmlMarshaller marshaller;

	protected void setUp() throws Exception {
		super.setUp(); // To change body of overridden methods use File |
						// Settings | File
		// Templates.
		healthcareSitedao = (HealthcareSiteDao) getApplicationContext()
				.getBean("healthcareSiteDao");
		marshaller = new XmlMarshaller("c3pr-study-xml-castor-mapping.xml");
		studyImporter = (StudyXMLImporterServiceImpl) getApplicationContext()
				.getBean("studyXMLImporterService");
		
		xmlParser = (XMLParser)getApplicationContext().getBean("xmlParser");
	
	}
	

	public void testGetStudies() throws Exception {
		for (int i = 1000; i < 1003; i++) {
			Study study = getDao().getById(i);
			getDao().initialize(study);
			// have to set the coordinating center identifier to something
			// different to prevent duplicate study exception.
			// The studies in daoTest.xml have already been inserted into
			// database.
			HealthcareSite healthcareSite = healthcareSitedao.getById(i);
			healthcareSitedao.initialize(healthcareSite);
			interruptSession();
			
			// set status to pending to prevent "canOpen" validations.
			study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
			study.getCoordinatingCenterAssignedIdentifier().setHealthcareSite(
					healthcareSite);
			study.getCoordinatingCenterAssignedIdentifier().setValue("abc" + i);
			if (study.getFundingSponsorAssignedIdentifier() != null)
				study.getFundingSponsorAssignedIdentifier().setValue("abc" + i);
			for (CompanionStudyAssociation companionStudyAssociation : study.getStudyVersion()
					.getCompanionStudyAssociations()) {
				Study companionStudy = companionStudyAssociation
						.getCompanionStudy();
				companionStudy.getCoordinatingCenterAssignedIdentifier()
						.setValue("pqr" + i);
				if (companionStudy.getFundingSponsorAssignedIdentifier() != null)
					companionStudy.getFundingSponsorAssignedIdentifier()
							.setValue("pqr" + i);
			}
			
			String[] xmlStudy = (marshaller.toXML(study)).split(">", 2);
			String studyXml = xmlStudy[0] + "><studies>" + xmlStudy[1]
					+ "</studies> ";

			System.out.println(studyXml);
			List<Study> studies = studyImporter.importStudies(StringUtils
					.getInputStream(studyXml), getErrorsMock());

			assertNotNull(studies);
			// studies.get(0).getStudyDiseases().get(0);
			assertTrue(studies.size() > 0);

			for (Study loadedStudy : studies) {
				assertNotNull(loadedStudy);
				assertEquals(loadedStudy.getStudyOrganizations().size(), study
						.getStudyOrganizations().size());

				for (StudyOrganization organization : loadedStudy
						.getStudyOrganizations()) {
					assertNotNull(organization.getHealthcareSite());
				}
			}
		}
	}

	private Errors getErrorsMock() {
		return new Errors() {
			
			public void setNestedPath(String nestedPath) {
				// TODO Auto-generated method stub
				
			}
			
			public void rejectValue(String field, String errorCode, Object[] errorArgs,
					String defaultMessage) {
				// TODO Auto-generated method stub
				
			}
			
			public void rejectValue(String field, String errorCode,
					String defaultMessage) {
				// TODO Auto-generated method stub
				
			}
			
			public void rejectValue(String field, String errorCode) {
				// TODO Auto-generated method stub
				
			}
			
			public void reject(String errorCode, Object[] errorArgs,
					String defaultMessage) {
				log.error("Reject called: "+errorCode+", "+ArrayUtils.toString(errorArgs)+", "+defaultMessage);
				
			}
			
			public void reject(String errorCode, String defaultMessage) {
				log.error("Reject called: "+errorCode+", "+defaultMessage);
				
			}
			
			public void reject(String errorCode) {
				log.error("Reject called: "+errorCode);
				
			}
			
			public void pushNestedPath(String subPath) {
				// TODO Auto-generated method stub
				
			}
			
			public void popNestedPath() throws IllegalStateException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean hasGlobalErrors() {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean hasFieldErrors(String field) {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean hasFieldErrors() {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean hasErrors() {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getObjectName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getNestedPath() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public List getGlobalErrors() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getGlobalErrorCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public ObjectError getGlobalError() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getFieldValue(String field) {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Class getFieldType(String field) {
				// TODO Auto-generated method stub
				return null;
			}
			
			public List getFieldErrors(String field) {
				// TODO Auto-generated method stub
				return null;
			}
			
			public List getFieldErrors() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getFieldErrorCount(String field) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getFieldErrorCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public FieldError getFieldError(String field) {
				// TODO Auto-generated method stub
				return null;
			}
			
			public FieldError getFieldError() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getErrorCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public List getAllErrors() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void addAllErrors(Errors errors) {
				// TODO Auto-generated method stub
				
			}
		};
	}


	/**
	 * What dao class is the test trying to Masquerade
	 * 
	 * @return
	 */
	public Class<StudyDao> getMasqueradingDaoClassName() {
		return StudyDao.class;
	}

	public StudyXMLImporterServiceImpl getStudyImporter() {
		return studyImporter;
	}

	public void setStudyImporter(StudyXMLImporterServiceImpl studyImporter) {
		this.studyImporter = studyImporter;
	}

	public XmlMarshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(XmlMarshaller marshaller) {
		this.marshaller = marshaller;
	}

}
