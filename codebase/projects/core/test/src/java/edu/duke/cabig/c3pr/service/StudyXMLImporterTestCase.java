package edu.duke.cabig.c3pr.service;

import static edu.duke.cabig.c3pr.C3PRUseCase.IMPORT_STUDY;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import edu.duke.cabig.c3pr.C3PRUseCases;
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
	

	public void testStudyValidation() throws Exception {
		Study study = getDao().getById(1000);

			try {
				InputStream is = getClass().getClassLoader()
						.getResourceAsStream("c3pr-sample-study.xml");
				String str = is.toString();
				byte[] b = str.getBytes();
			//	xmlParser.validate(b);
			} catch (Exception e) {
				e.printStackTrace();
				fail("Unable to Validate");
			}
	}

	public void testGetStudies() throws Exception {
		for (int i = 1000; i < 1003; i++) {
			Study study = getDao().getById(i);
			getDao().initialize(study);
			interruptSession();
			// have to set the coordinating center identifier to something
			// differnt to prevent duplicate study exception.
			// The studies in daoTest.xml have already been inserted into
			// database.
			HealthcareSite healthcareSite = healthcareSitedao.getById(i);
			study.getCoordinatingCenterAssignedIdentifier().setHealthcareSite(
					healthcareSite);
			study.getCoordinatingCenterAssignedIdentifier().setValue("abc" + i);
			if (study.getFundingSponsorAssignedIdentifier() != null)
				study.getFundingSponsorAssignedIdentifier().setValue("abc" + i);
			for (CompanionStudyAssociation companionStudyAssociation : study
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
			File outputXMLFile = new File("dummyOutput.xml");

			List<Study> studies = studyImporter.importStudies(StringUtils
					.getInputStream(studyXml), outputXMLFile);

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
