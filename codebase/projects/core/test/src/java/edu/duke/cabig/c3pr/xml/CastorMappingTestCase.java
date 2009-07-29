package edu.duke.cabig.c3pr.xml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.StatusType;
import edu.duke.cabig.c3pr.constants.StudyPart;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentVersion;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalInvestigator;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PhoneCallRandomization;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyFundingSponsor;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;

public class CastorMappingTestCase extends AbstractTestCase{

	private XmlMarshaller marshaller;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		marshaller= new XmlMarshaller("c3pr-study-xml-castor-mapping.xml");
	}
	
	public void testStudyMarshalling() throws Exception{
		Study study= DomainObjectCreationHelper.getStudyWithDetails(RandomizationType.BOOK);
		DomainObjectCreationHelper.addConsent(study);
		DomainObjectCreationHelper.addStudyDesign(study);
		Epoch epoch= study.getEpochs().get(0);
		DomainObjectCreationHelper.addEligibility(epoch);
		DomainObjectCreationHelper.addStratification(epoch);
		DomainObjectCreationHelper.addRandomization(study, epoch);
		DomainObjectCreationHelper.addCompanions(study);
		DomainObjectCreationHelper.addDisease(study);
		DomainObjectCreationHelper.addStudySites(study);
		DomainObjectCreationHelper.addInvestigators(study.getStudyCoordinatingCenter());
		String xml= marshaller.toXML(study);
		System.out.println(xml);
		assertNotNull(xml);
	}
	
	public void testStudyAmendmentMarshalling() throws Exception{
		Study study= DomainObjectCreationHelper.getStudyWithDetails(RandomizationType.BOOK);
		DomainObjectCreationHelper.addConsent(study);
		DomainObjectCreationHelper.addStudyDesign(study);
		Epoch epoch= study.getEpochs().get(0);
		DomainObjectCreationHelper.addEligibility(epoch);
		DomainObjectCreationHelper.addStratification(epoch);
		DomainObjectCreationHelper.addRandomization(study, epoch);
		DomainObjectCreationHelper.addCompanions(study);
		DomainObjectCreationHelper.addDisease(study);
		DomainObjectCreationHelper.addStudySites(study);
		DomainObjectCreationHelper.addInvestigators(study.getStudyCoordinatingCenter());
		StudyVersion studyVersion= study.getStudyVersion();
		studyVersion.setVersionStatus(StatusType.IN);
		studyVersion.setName("1.1");
		studyVersion.setVersionDate(new Date());
		List<StudyPart> studyParts= new ArrayList<StudyPart>();
		studyParts.add(StudyPart.COMPANION);
		studyParts.add(StudyPart.CONSENT);
		studyParts.add(StudyPart.DESIGN);
		studyParts.add(StudyPart.DETAIL);
		studyParts.add(StudyPart.DISEASE);
		studyParts.add(StudyPart.ELIGIBILITY);
		studyParts.add(StudyPart.RANDOMIZATION);
		studyParts.add(StudyPart.STRATIFICATION);
		studyVersion.setAmendmentReasons(studyParts);
		String xml= marshaller.toXML(studyVersion);
		System.out.println(xml);
		assertNotNull(xml);
	}
	
	public void testParticipantMarshalling() throws Exception{
		Participant participant= DomainObjectCreationHelper.getParticipantWithAddress();
		String xml= marshaller.toXML(participant);
		System.out.println(xml);
		assertNotNull(xml);
	}
	
}
