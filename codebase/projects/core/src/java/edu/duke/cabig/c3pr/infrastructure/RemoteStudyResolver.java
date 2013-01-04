/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iso._21090.DSETII;
import org.iso._21090.NullFlavor;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.util.CoppaObjectFactory;
import com.semanticbits.coppasimulator.util.CoppaPAObjectFactory;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.domain.RemoteStudy;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyFundingSponsor;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.PersonOrganizationResolverUtils;
import edu.duke.cabig.c3pr.utils.ProtocolAbstractionResolverUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.services.pa.DocumentWorkflowStatus;
import gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol;
import gov.nih.nci.coppa.services.pa.StudyContact;
import gov.nih.nci.coppa.services.pa.StudyOverallStatus;
import gov.nih.nci.coppa.services.pa.StudyProtocol;
import gov.nih.nci.coppa.services.pa.StudySiteContact;

public class RemoteStudyResolver implements RemoteResolver {

	/** The log. */
	private static Log log = LogFactory.getLog(RemoteStudyResolver.class);
	
	/** The function codes for StudySite */
	public static final String LEAD_ORGANIZATION = "Lead Organization";
	public static final String SPONSOR = "Sponsor";
	public static final String TREATING_SITE = "Treating Site";
	public static final String NULLIFIED = "Nullified";
	
	/* List of values for studySiteContact. The studySiteContact has to have one of these to be eligible for fetching as an Investigator. */
	public static final List<String> SITE_INVESTIGATOR_LIST = Arrays.asList("Principal Investigator", "Coordinating Investigator", "Sub Investigator");
	
	/* Study level PI role code*/
	public static final String STUDY_PRINCIPAL_INVESTIGATOR = "Study Principal Investigator";
	
	public static final String CTEP_PERSON = "Cancer Therapy Evaluation Program Person Identifier";
	
	/* List of values for DocumentWorkflowStatus. The study has to have one of these to be eligible for fetching. */
	public static final List<String> DOCUMENT_WORKFLOW_STATUS_LIST = Arrays.asList("Abstracted", "Verification Pending", "Abstraction Verified Response", "Abstraction Verified No Response");

	public static final String RANDOMIZED_CONTROLLED_TRIAL = "Randomized Controlled Trial";
	
	public static final int SHORT_TITLE_LENGTH = 190;
	public static final int LONG_TITLE_LENGTH = 990;
	public static final int DESCRIPTION_TEXT_LENGTH = 1990;
	
	
	/** The PA utils which has all the serialize/deserialze and broadcast methods */
	private ProtocolAbstractionResolverUtils protocolAbstractionResolverUtils = null;
	
	/** The PO utils which has all the serialize/deserialze and broadcast methods */
	private PersonOrganizationResolverUtils personOrganizationResolverUtils = null;
	
	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 */
	public Object getRemoteEntityByUniqueId(String externalId) {
		log.debug("Entering getRemoteEntityByUniqueId() for:" + this.getClass() + " - ExtId: " +externalId);
		RemoteStudy remoteStudy = null;
		try{
			String paPayLoad = CoppaPAObjectFactory.getPAIdXML(CoppaPAObjectFactory.getPAId(externalId));
			String resultXml = protocolAbstractionResolverUtils.broadcastStudyProtocolGetById(paPayLoad);
			
			List<gov.nih.nci.coppa.services.pa.StudyProtocol> studyProtocols = getStudyProtocolsFromResultXml(resultXml);
			if(studyProtocols.size() > 0){
				remoteStudy = getRemoteAttributesOnlyFromStudyProtocol(studyProtocols.get(0));
			}
		} catch (C3PRCodedException e) {
			log.error(e.getMessage());
		} catch(Exception e){
			log.error(e.getMessage());
		}
		log.debug("Exiting getRemoteEntityByUniqueId() for:" + this.getClass());
		return remoteStudy;
	}
	
	/**This method is a condensed version of getRemoteStudyFromStudyProtocol().
	 * This is used only by getRemoteEntityByUniqueID
	 * 
	 * @param interventionalStudyProtocol
	 * @return
	 */
	private RemoteStudy getRemoteAttributesOnlyFromStudyProtocol(StudyProtocol studyProtocol) {
		//Set remote attributes from COPPA Object
		RemoteStudy remoteStudy = new RemoteStudy();
		remoteStudy.setType(protocolAbstractionResolverUtils.getStudyTypeFromCoppaStudyType(CoppaPAObjectFactory.getPrimaryPurposeFromStudyProtocol(studyProtocol)));
		remoteStudy.setPhaseCode(protocolAbstractionResolverUtils.getPhaseCodeFromCoppaPhaseCode(CoppaPAObjectFactory.getPhaseCodeFromStudyProtocol(studyProtocol)));
		remoteStudy.setExternalId(studyProtocol.getIdentifier().getExtension());
		
		//DONT Set NCI identifier, StudyOrganizations, PI or status for Study
		return remoteStudy;
	}
	
	/**
	 * Searches Coppa database for orgs simliar to the example RemoteHelathcareSite that is passed into it.
	 * 
	 * @param Object the remote HealthcareSite
	 * @return the object list; list of remoteHealthcareSites
	 */
	public List<Object> find(Object example) {
		log.debug("Entering find() for:" + this.getClass());
		List<Object> remoteStudies = new ArrayList<Object>();
		try{
			RemoteStudy remoteStudyExample = (RemoteStudy)example;
			String identifierValue = "";
			String shortTitle = "";
			
			//Get protocolSearchPayload using the search criteria specified in the example Study.
			if(remoteStudyExample.getCoordinatingCenterAssignedIdentifier() != null && 
					!remoteStudyExample.getCoordinatingCenterAssignedIdentifier().getValue().matches("%+")){
				//Get identifier if its not just '%'s.
				identifierValue = remoteStudyExample.getCoordinatingCenterAssignedIdentifier().getValue();
			}
			if(!remoteStudyExample.getShortTitleText().matches("%+")){
				//Get short Title if its not just '%'s.
				shortTitle = remoteStudyExample.getShortTitleText();
			}
			
			if(!StringUtils.isBlank(shortTitle) || !StringUtils.isBlank(identifierValue)){
				String payLoad = CoppaPAObjectFactory.getStudyProtocolSearchXML(shortTitle, identifierValue, null);
				String resultXml  = protocolAbstractionResolverUtils.broadcastStudyProtocolSearch(payLoad);
				
				List<gov.nih.nci.coppa.services.pa.StudyProtocol> studyProtocols = getStudyProtocolsFromResultXml(resultXml);
				log.debug("Coppa Search returned " + studyProtocols.size() + " Protocols");
				for (gov.nih.nci.coppa.services.pa.StudyProtocol studyProtocol : studyProtocols) {
					RemoteStudy remoteStudy = getRemoteStudyFromStudyProtocol(studyProtocol);
					if (remoteStudy != null) {
						remoteStudies.add(remoteStudy);
					}
				}
			}
		} catch (C3PRCodedException e) {
				log.error(e);
		} catch(Exception e){
			log.error(e);
		}
		log.debug("Exiting find() for:" + this.getClass());
		return remoteStudies;
		
	}
	
	/**This method takes a interventionalStudyProtocol and converts it to a RemoteStudy which C3PR understands.
	 * This is responsible for mapping all the attributes/associations that we fetch from COPPA.
	 * 
	 * @param interventionalStudyProtocol
	 * @return
	 */
	private RemoteStudy getRemoteStudyFromStudyProtocol(StudyProtocol studyProtocol) {
		RemoteStudy remoteStudy = new RemoteStudy();
		
		//Set core attributes from COPPA Object
		remoteStudy.setShortTitleText(StringUtils.getTrimmedText(CoppaPAObjectFactory.getShortTitleFromStudyProtocol(studyProtocol), SHORT_TITLE_LENGTH));
		remoteStudy.setLongTitleText(StringUtils.getTrimmedText(CoppaPAObjectFactory.getLongTitleFromStudyProtocol(studyProtocol), LONG_TITLE_LENGTH));
		remoteStudy.setDescriptionText(StringUtils.getTrimmedText(CoppaPAObjectFactory.getPublicDescriptionFromStudyProtocol(studyProtocol), DESCRIPTION_TEXT_LENGTH));
		//Using the primary purpose code to set the study type. TODO: get the type and save it in a separate field in the c3pr study.
		remoteStudy.setType(protocolAbstractionResolverUtils.getStudyTypeFromCoppaStudyType(CoppaPAObjectFactory.getPrimaryPurposeFromStudyProtocol(studyProtocol)));
		remoteStudy.setPhaseCode(protocolAbstractionResolverUtils.getPhaseCodeFromCoppaPhaseCode(CoppaPAObjectFactory.getPhaseCodeFromStudyProtocol(studyProtocol)));
		remoteStudy.setExternalId(studyProtocol.getIdentifier().getExtension());
		remoteStudy.setTargetAccrualNumber(CoppaPAObjectFactory.getStudyTargetAccrualNumberFromStudyProtocol(studyProtocol));
		//If study is randomized, default to blinded and phone call type.
		if(isRandomized(studyProtocol)){
			remoteStudy.setRandomizationType(RandomizationType.PHONE_CALL);
			remoteStudy.setRandomizedIndicator(Boolean.TRUE);
			remoteStudy.setBlindedIndicator(Boolean.TRUE);
		} else {
			remoteStudy.setRandomizedIndicator(Boolean.FALSE);
			remoteStudy.setBlindedIndicator(Boolean.FALSE);
		}
		
		
		//Set StudyOrganizations
		boolean hasCoordinatingCenter = false;
		List<StudyOrganization> remoteStudyOrganizations = getStudyOrganizationsForStudyProtocol(remoteStudy);
		for(StudyOrganization studyOrganization: remoteStudyOrganizations){
			if(studyOrganization instanceof StudyCoordinatingCenter){
				hasCoordinatingCenter = true;
				break;
			}
		}
		if(hasCoordinatingCenter){
			remoteStudy.addStudyOrganizations(remoteStudyOrganizations);
		} else {
			//return null if no StudyCoordinatingCenter could be matched
			log.error("Rejecting this study based on its missing StudyCoordinatingCenter. Short Title: " + remoteStudy.getShortTitleText());
			return null;
		}
		

		//Set status
		CoordinatingCenterStudyStatus coordinatingCenterStudyStatus = getStudyCoordinatingCenterStudyStatus(studyProtocol);
		if(coordinatingCenterStudyStatus != null){
			remoteStudy.setCoordinatingCenterStudyStatus(coordinatingCenterStudyStatus);
		} else {
			//return null if no status could be matched
			log.error("Rejecting this study based on its Document Workflow Status. Short Title: " + remoteStudy.getShortTitleText());
			return null;
		}
		
		//Set PI for Study
		boolean hasPrincipalInvestigator = false;
		setPrincipalInvestigator(remoteStudy, studyProtocol.getIdentifier().getExtension());
		for(StudyOrganization studyOrganization: remoteStudy.getStudyOrganizations()){
			for(StudyInvestigator studyInvestigator: studyOrganization.getStudyInvestigators()){
				if(studyInvestigator.getRoleCode().equalsIgnoreCase(StudyInvestigator.PRINCIPAL_INVESTIGATOR)){
					hasPrincipalInvestigator = true;
				}
			}
		}
		if(!hasPrincipalInvestigator){
			//return null if no PI could be matched
			log.error("Rejecting this study based on its Missing Principal Investigator. Short Title: " + remoteStudy.getShortTitleText());
			return null;
		}
		
		//Set NCI identifier for Study
		setStudyNciIdentifier(remoteStudy, studyProtocol.getAssignedIdentifier().getExtension());
		
		//Set the Epoch and Arms
		setEpochAndArms(remoteStudy);
		
		//Set Eligibility criteria
		setEligibilityCriteria(remoteStudy);
		
		//Set default values in RemoteStudy
		remoteStudy.setStratificationIndicator(Boolean.FALSE);
		remoteStudy.setCompanionIndicator(Boolean.FALSE);
		remoteStudy.setStandaloneIndicator(Boolean.TRUE);
		
		return remoteStudy;
	}
	
	/**
	 * Checks if study is randomized.
	 * 
	 * @param studyProtocol the study protocol
	 * @return true, if is randomized
	 */
	private boolean isRandomized(StudyProtocol studyProtocol) {
		String paIdPayLoad = CoppaPAObjectFactory.getPAIdXML(CoppaPAObjectFactory.getPAId(studyProtocol.getIdentifier().getExtension()));
		String interventionalProtocolXml  = null;
		try {
			interventionalProtocolXml  = protocolAbstractionResolverUtils.broadcastInterventionalStudyProtocolSearch(paIdPayLoad);
			
			List<String> results = XMLUtils.getObjectsFromCoppaResponse(interventionalProtocolXml);
			InterventionalStudyProtocol interventionalStudyProtocol;
			if (results.size() > 0) {
				interventionalStudyProtocol = CoppaPAObjectFactory.getInterventionalStudyProtocol(results.get(0));
				if(interventionalStudyProtocol.getAllocationCode() != null && interventionalStudyProtocol.getAllocationCode().getCode() != null
						&& interventionalStudyProtocol.getAllocationCode().getCode().equalsIgnoreCase(RANDOMIZED_CONTROLLED_TRIAL)){
					return Boolean.TRUE;
				}
			}
		} catch (C3PRCodedException e) {
			log.error(e);
		}
		return Boolean.FALSE;
		
	}

	/**
	* This method will fetch the Eligibility from PA. Set them in the existing epoch.
	* If no epoch is found then eligibility is ignored.
	*
	* @param remoteStudy
	*/
	private void setEligibilityCriteria(RemoteStudy remoteStudy) {
		
		if(remoteStudy.getEpochs().size() == 0){
			log.error("Not attaching eligibility as No epochs were created for this study."+remoteStudy.getShortTitleText());
		} else {
			String eligibilityPayLoad = CoppaPAObjectFactory.getPAIdXML(CoppaPAObjectFactory.getPAId(remoteStudy.getExternalId()));
			
			String eligibilityResultXml  = "";
			try {
				eligibilityResultXml  = protocolAbstractionResolverUtils.broadcastEligibilityGetByStudyProtocol(eligibilityPayLoad);
			} catch (C3PRCodedException e) {
				log.error("Error during fetching Eligibility." +e.getMessage());
			}
			
			List<String> results = XMLUtils.getObjectsFromCoppaResponse(eligibilityResultXml);
			gov.nih.nci.coppa.services.pa.PlannedEligibilityCriterion plannedEligibility = null;
			InclusionEligibilityCriteria inclusionEligibilityCriteria = null;
			ExclusionEligibilityCriteria exclusionEligibilityCriteria = null;
			for (String result:results) {
				plannedEligibility = CoppaPAObjectFactory.getPlannedEligibilityCriterion(result);
				if(plannedEligibility != null){
					boolean isNull =(plannedEligibility.getInclusionIndicator() == null || plannedEligibility.getInclusionIndicator().isValue() == null )
									&& (plannedEligibility.getInclusionIndicator().getNullFlavor() == null 
					  				|| plannedEligibility.getInclusionIndicator().getNullFlavor().equals(NullFlavor.NI));
					
					if(!isNull && plannedEligibility.getInclusionIndicator().isValue()){
						//its an inclusion criteria
						inclusionEligibilityCriteria = new InclusionEligibilityCriteria();
						if(plannedEligibility.getDisplayOrder() != null){
							inclusionEligibilityCriteria.setQuestionNumber(plannedEligibility.getDisplayOrder().getValue());
						}
						if(plannedEligibility.getTextDescription() != null && !StringUtils.isBlank(plannedEligibility.getTextDescription().getValue())){
							inclusionEligibilityCriteria.setQuestionText(StringUtils.getBlankIfNull(plannedEligibility.getTextDescription().getValue()));
							remoteStudy.getEpochs().get(0).getInclusionEligibilityCriteria().add(inclusionEligibilityCriteria);
						}
					} else {
						//its an exclusion criteria
						exclusionEligibilityCriteria = new ExclusionEligibilityCriteria();
						if(plannedEligibility.getDisplayOrder() != null){
							exclusionEligibilityCriteria.setQuestionNumber(plannedEligibility.getDisplayOrder().getValue());
						}	
						if(plannedEligibility.getTextDescription() != null && !StringUtils.isBlank(plannedEligibility.getTextDescription().getValue())){
							exclusionEligibilityCriteria.setQuestionText(StringUtils.getBlankIfNull(plannedEligibility.getTextDescription().getValue()));
							remoteStudy.getEpochs().get(0).getExclusionEligibilityCriteria().add(exclusionEligibilityCriteria);
						}
					}
				}
			}
		}
	}

	/**
	* This method will fetch all the Arms from PA. Set them in the default treatment epoch with order 1.
	* For each Arm a TreatmentAssignment object is created and added to the Study.
	*
	* @param remoteStudy
	*/
	public void setEpochAndArms(RemoteStudy remoteStudy){
		String armsPayLoad = CoppaPAObjectFactory.getPAIdXML(CoppaPAObjectFactory.getPAId(remoteStudy.getExternalId()));
		
		String armsResultXml  = "";
		try {
			armsResultXml  = protocolAbstractionResolverUtils.broadcastArmGetByStudyProtocol(armsPayLoad);
		} catch (C3PRCodedException e) {
			log.error("Error during fetching Arms." +e.getMessage());
		}
		
		Epoch epoch = new Epoch();
		//setting default values in epoch
		epoch.setType(EpochType.TREATMENT);
		epoch.setEnrollmentIndicator(true);
		epoch.setName("Treatment Epoch");
		epoch.setEpochOrder(1);
		
		if(remoteStudy.getRandomizedIndicator()){
			epoch.setRandomizedIndicator(Boolean.TRUE);
		} else {
			epoch.setRandomizedIndicator(Boolean.FALSE);
		}
		
	    gov.nih.nci.coppa.services.pa.Arm remoteArm = null;
	    Arm localArm = null;
		List<String> results = XMLUtils.getObjectsFromCoppaResponse(armsResultXml);
		for (String result:results) {
			remoteArm = CoppaPAObjectFactory.getArm(result);
			if(remoteArm != null){
				localArm = new Arm();
				localArm.setName(remoteArm.getName().getValue());
				if(remoteArm.getDescriptionText() != null){
					localArm.setDescriptionText(remoteArm.getDescriptionText().getValue());
				}
				epoch.getArms().add(localArm);
			}
		}
		
		remoteStudy.getEpochs().add(epoch);
	}


	
	/**
	 * Sets the study NCI identifier.
	 * 
	 * @param remoteStudy the remote study
	 * @param extension the extension
	 */
	private void setStudyNciIdentifier(RemoteStudy remoteStudy, String extension) {
		OrganizationAssignedIdentifier organizationAssignedIdentifier = new OrganizationAssignedIdentifier();
		organizationAssignedIdentifier.setType(OrganizationIdentifierTypeEnum.NCI);
		organizationAssignedIdentifier.setValue(extension);
		organizationAssignedIdentifier.setPrimaryIndicator(true);
		//Cant get dao here...hence doing this in the studyDao.updateStudies method.
		//organizationAssignedIdentifier.setHealthcareSite(healthcareSiteDao.getNCIOrganization());
		
		remoteStudy.getIdentifiers().add(organizationAssignedIdentifier);
	}

	/**
	 * Gets the study organizations for interventional study protocol.
	 * 
	 * @param interventionalStudyProtocol the interventional study protocol
	 * 
	 * @return the study organizations for interventional study protocol
	 */
	private List<StudyOrganization> getStudyOrganizationsForStudyProtocol(RemoteStudy remoteStudy) {
		//call search on StudySite using the interventionalStudyProtocol II. use the returned list
		String payLoad = CoppaPAObjectFactory.getPAIdXML(CoppaPAObjectFactory.getPAId(remoteStudy.getExternalId()));
		String resultXml  = "";
		try {
			resultXml  = protocolAbstractionResolverUtils.broadcastStudySiteGetByStudyProtocol(payLoad);
		} catch (C3PRCodedException e) {
			log.error(e);
		}

		List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		List<gov.nih.nci.coppa.services.pa.StudySite> studySiteList = new ArrayList<gov.nih.nci.coppa.services.pa.StudySite>();
		
		for (String result:results) {
			studySiteList.add(CoppaPAObjectFactory.getStudySite(result));
		}

		List<StudyOrganization> studyOrganizationList = new ArrayList<StudyOrganization>();
		OrganizationAssignedIdentifier organizationAssignedIdentifier;
		//go thru the returned list. we need the ones who have functional Code as LeadOrg(ResearchOrganization)/TreatingSite(HealthcareFacility)/Funding source(Sponsor)
		for(gov.nih.nci.coppa.services.pa.StudySite studySiteTemp: studySiteList){
			organizationAssignedIdentifier = null;
			if(studySiteTemp.getFunctionalCode().getCode().equals(LEAD_ORGANIZATION)){
				//Fetch the coordinating center(ResearchOrganziation) and add to the list to be returned
				StudyCoordinatingCenter studyCoordinatingCenter = getCoordinatingCenterFromCoppaStudySite(studySiteTemp);
				if(studyCoordinatingCenter != null){
					organizationAssignedIdentifier = getOrganizationAssignedIdentifierFromCoppaStudySite(studySiteTemp, OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
					if(organizationAssignedIdentifier  != null){
						//studyCoordinatingCenter.getHealthcareSite().getIdentifiersAssignedToOrganization().add(organizationAssignedIdentifier);
						organizationAssignedIdentifier.setHealthcareSite(studyCoordinatingCenter.getHealthcareSite());
						remoteStudy.getIdentifiers().add(organizationAssignedIdentifier);
					}
					studyOrganizationList.add(studyCoordinatingCenter);
					
					//Now get the assGciated investigators and build the studyInv/healthcareSiteInvestigator and link them to the healthcareSite.
					List<StudyInvestigator> studyInvestigatorList = getStudyInvestigators(studySiteTemp, studyCoordinatingCenter);
					for(StudyInvestigator studyInvestigator : studyInvestigatorList){
						studyCoordinatingCenter.addStudyInvestigator(studyInvestigator);
					}
				}
			}
			if(studySiteTemp.getFunctionalCode().getCode().equals(SPONSOR)){
				if(!studySiteTemp.getStatusCode().getCode().equalsIgnoreCase(NULLIFIED)){
					//Fetch the Funding sponsor(ResearchOrganziation) and add to the list to be returned
					StudyFundingSponsor studyFundingSponsor = getFundingSponsorFromCoppaStudySite(studySiteTemp);
					if(studyFundingSponsor != null){
						organizationAssignedIdentifier = getOrganizationAssignedIdentifierFromCoppaStudySite(studySiteTemp, OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER);
						if(organizationAssignedIdentifier  != null){
							//studyFundingSponsor.getHealthcareSite().getIdentifiersAssignedToOrganization().add(organizationAssignedIdentifier);
							organizationAssignedIdentifier.setHealthcareSite(studyFundingSponsor.getHealthcareSite());
							remoteStudy.getIdentifiers().add(organizationAssignedIdentifier);
						}
						studyOrganizationList.add(studyFundingSponsor);
						
						//Now get the associated investigators and build the studyInv/healthcareSiteInvestigator and link them to the healthcareSite.
						List<StudyInvestigator> studyInvestigatorList = getStudyInvestigators(studySiteTemp, studyFundingSponsor);
						for(StudyInvestigator studyInvestigator : studyInvestigatorList){
							studyFundingSponsor.addStudyInvestigator(studyInvestigator);
						}
					}
				}
			}
			if(studySiteTemp.getFunctionalCode().getCode().equals(TREATING_SITE)){
				if(!studySiteTemp.getStatusCode().getCode().equalsIgnoreCase(NULLIFIED)){
					//Fetch the Study Site(HealthcareFacility) and add to the list to be returned
					StudySite studySite = getStudysiteFromCoppaStudySite(studySiteTemp);
					if(studySite != null){
						organizationAssignedIdentifier = getOrganizationAssignedIdentifierFromCoppaStudySite(studySiteTemp, OrganizationIdentifierTypeEnum.CTEP);
						if(organizationAssignedIdentifier  != null){
							//studySite.getHealthcareSite().getIdentifiersAssignedToOrganization().add(organizationAssignedIdentifier);
							organizationAssignedIdentifier.setHealthcareSite(studySite.getHealthcareSite());
							remoteStudy.getIdentifiers().add(organizationAssignedIdentifier);
						}
						studyOrganizationList.add(studySite);
						
						//Now get the assciated investigators and build the studyInv/healthcareSiteInvestigator and link them to the healthcareSite.
						List<StudyInvestigator> studyInvestigatorList = getStudyInvestigators(studySiteTemp, studySite);
						for(StudyInvestigator studyInvestigator : studyInvestigatorList){
							studySite.addStudyInvestigator(studyInvestigator);
						}
					}
				}
			}
		}
		return studyOrganizationList;
	}


	/**
	 * Gets the organization assigned identifier from study site.
	 * 
	 * @param StudySite the study site
	 * 
	 * @return the organization assigned identifier from study site
	 */
	private OrganizationAssignedIdentifier getOrganizationAssignedIdentifierFromCoppaStudySite(gov.nih.nci.coppa.services.pa.StudySite studySite, OrganizationIdentifierTypeEnum type) {
		if(!StringUtils.isEmpty(studySite.getLocalStudyProtocolIdentifier().getValue())){
			OrganizationAssignedIdentifier organizationAssignedIdentifier = new OrganizationAssignedIdentifier();
			organizationAssignedIdentifier.setType(type);
			organizationAssignedIdentifier.setValue(studySite.getLocalStudyProtocolIdentifier().getValue());
			//organizationAssignedIdentifier.setHealthcareSite(healthcareSiteDao.getCTEPOrganization());
			
			return organizationAssignedIdentifier;
		}
		return null;
	}

	/**
	 * Gets the coordinating center from the StudySite.
	 * 
	 * @param studySiteTemp the study site temp
	 * 
	 * @return the coordinating center
	 */
	private StudyCoordinatingCenter getCoordinatingCenterFromCoppaStudySite(gov.nih.nci.coppa.services.pa.StudySite studySiteTemp) {
		RemoteHealthcareSite remoteHealthcareSite = getHealthcareSiteFromCoppaStudySite(studySiteTemp);
		if(remoteHealthcareSite != null){
			StudyCoordinatingCenter studyCoordinatingCenter = new StudyCoordinatingCenter();
			studyCoordinatingCenter.setHealthcareSite(remoteHealthcareSite);
			return studyCoordinatingCenter;
		}
		return null;
	}

	/**
	 * Gets the funding sponsor from study site.
	 * 
	 * @param studySiteTemp the study site temp
	 * 
	 * @return the funding sponsor from study site
	 */
	private StudyFundingSponsor getFundingSponsorFromCoppaStudySite(gov.nih.nci.coppa.services.pa.StudySite studySiteTemp) {
		RemoteHealthcareSite remoteHealthcareSite = getHealthcareSiteFromCoppaStudySite(studySiteTemp);
		if(remoteHealthcareSite != null){
			StudyFundingSponsor studyFundingSponsor = new StudyFundingSponsor();
			studyFundingSponsor.setHealthcareSite(remoteHealthcareSite);
			return studyFundingSponsor;
		}
		return null;
	}
	
	
	/**
	 * Gets the healthcare site from study site.
	 * 
	 * @param studySiteTemp the study site temp
	 * 
	 * @return the healthcare site from study site
	 */
	private RemoteHealthcareSite getHealthcareSiteFromCoppaStudySite(gov.nih.nci.coppa.services.pa.StudySite studySiteTemp) {
		String roPayLoad = CoppaObjectFactory.getResearchOrganizationIdXML
						(CoppaObjectFactory.getResearchOrganizationId(studySiteTemp.getResearchOrganization().getExtension()));
		try {
			String roResultXml  = personOrganizationResolverUtils.broadcastResearchOrganizationGetById(roPayLoad);
			List<String> roResults = XMLUtils.getObjectsFromCoppaResponse(roResultXml);
			if(roResults.size() == 0){
				log.error("Received a bad ResearchOrganizationId from Coppa StudySite- "+studySiteTemp.getResearchOrganization().getExtension());
				return null;
			}
			gov.nih.nci.coppa.po.ResearchOrganization researchOrganization = CoppaObjectFactory.getResearchOrganization(roResults.get(0));

			//Assuming here that the playerII in the RO is the Organization II
			gov.nih.nci.coppa.po.Organization coppaOrganization = getOrganizationFromExtension(researchOrganization.getPlayerIdentifier().getExtension());
			
			RemoteHealthcareSite remoteHealthcareSite = 
						personOrganizationResolverUtils.getRemoteHealthcareSiteFromCoppaOrganization(coppaOrganization, false);
			//set the CTEP Id for the Org from the Structural Role(HealthcareFacility).
			personOrganizationResolverUtils.setCtepCodeFromStructuralRoleIIList(remoteHealthcareSite, researchOrganization.getIdentifier().getItem());
			if(StringUtils.isBlank(remoteHealthcareSite.getCtepCode())){
				log.error("Rejecting Organization as it has not CTEP ID - " + remoteHealthcareSite.getName());
				return null;
			}
			return remoteHealthcareSite;
		} catch (C3PRCodedException e) {
			log.error(e);
		} catch (Exception e){
			log.error(e);
		}
		return null;
	}
	
	
	/**
	 * Gets the studysite from Coppa study site.
	 * 
	 * @param studySiteTemp the study site temp
	 * 
	 * @return the studysite from study site
	 */
	private StudySite getStudysiteFromCoppaStudySite(gov.nih.nci.coppa.services.pa.StudySite studySiteTemp) {
		StudySite studySite = new StudySite();
		String hcfResultXml  = "";
		String healthcareFacilityExtension = studySiteTemp.getHealthcareFacility().getExtension();
		String hcfPayLoad = CoppaObjectFactory.getHealthcareFacilityIdXML(CoppaObjectFactory.getHealthcareFacilityId(healthcareFacilityExtension));
		
		try {
			hcfResultXml  = personOrganizationResolverUtils.broadcastHealthcareFacilityGetById(hcfPayLoad);
			List<String> hcfResults = XMLUtils.getObjectsFromCoppaResponse(hcfResultXml);
			gov.nih.nci.coppa.po.HealthCareFacility healthcareFacility = CoppaObjectFactory.getHealthcareFacility(hcfResults.get(0));
			
			//Assuming here that the playerII in the HCF is the Organization II
			gov.nih.nci.coppa.po.Organization coppaOrganization = getOrganizationFromExtension(healthcareFacility.getPlayerIdentifier().getExtension());
			RemoteHealthcareSite remoteHealthcareSite = 
					personOrganizationResolverUtils.getRemoteHealthcareSiteFromCoppaOrganization(coppaOrganization, false);
			//set the CTEP Id for the Org from the Structural Role(HealthcareFacility).
			personOrganizationResolverUtils.setCtepCodeFromStructuralRoleIIList(remoteHealthcareSite, healthcareFacility.getIdentifier().getItem());
			if(StringUtils.isBlank(remoteHealthcareSite.getCtepCode())){
				log.error("Rejecting Organization as it has not CTEP ID - " + remoteHealthcareSite.getName());
				return null;
			}
			studySite.setHealthcareSite(remoteHealthcareSite);
			studySite.setTargetAccrualNumber(CoppaPAObjectFactory.getSiteTargetAccrualNumberFromCoppaStudySite(studySiteTemp));
			return studySite;
		
		} catch (C3PRCodedException e) {
			log.error(e);
		}
		return null;
	}
	
	
	/**
	 * Gets the study coordinating center study status.
	 * 
	 * @param studyProtocol the study protocol
	 * 
	 * @return the study coordinating center study status
	 */
	private CoordinatingCenterStudyStatus getStudyCoordinatingCenterStudyStatus(StudyProtocol studyProtocol){
		boolean process = false;
		String paIdPayLoad = CoppaPAObjectFactory.getPAIdXML(CoppaPAObjectFactory.getPAId(studyProtocol.getIdentifier().getExtension()));
		try{
			//Call search on DocumentWorkflowStatus using the StudyProtocol II. 
			String documentWorkflowResultXml  = protocolAbstractionResolverUtils.broadcastDocumentWorkflowStatusGetByStudyProtocol(paIdPayLoad);
			List<String> documentWorkflowResults = XMLUtils.getObjectsFromCoppaResponse(documentWorkflowResultXml);
			
			List<DocumentWorkflowStatus> dwsList = new ArrayList<DocumentWorkflowStatus>();
			for(String documentWorkflowResult:documentWorkflowResults){
				dwsList.add(CoppaPAObjectFactory.getDocumentWorkflowStatus(documentWorkflowResult));
			}
			
			for(int i=0;i<dwsList.size();i++){
				if(DOCUMENT_WORKFLOW_STATUS_LIST.contains(dwsList.get(i).getStatusCode().getCode())){
					process = true;
				}
			}
			
			//If the DocumentWorkflowStatus doesn't map to our list, reject study immediately.
			if(process){
				//Call search on StudyOverallStatus using the StudyProtocol II. 
				String overallStatusResultXml = protocolAbstractionResolverUtils.broadcastStudyOverallStatusGetByStudyProtocol(paIdPayLoad);
				
				List<String> overallStatusResults = XMLUtils.getObjectsFromCoppaResponse(overallStatusResultXml);
				StudyOverallStatus studyOverallStatus = CoppaPAObjectFactory.getStudyOverallStatus(overallStatusResults.get(0));

				return protocolAbstractionResolverUtils.getCoordinatingCenterStudyStatusFromCoppaOverallStatus(studyOverallStatus);
			} else {
				log.error("Rejecting study as it had invalid status.");
				return null;
			}
		} catch(C3PRCodedException e){
			log.error(e.getMessage());
		}
		//Default to Pending if something doesn't click.
		return CoordinatingCenterStudyStatus.PENDING;
	}
	
	
	/**     
	 * This method invokes STUDY_SITE_CONTACT.getByStudySite to fetch Site Investigator for a Study.
	 * roleCode's supported are in SITE_INVESTIGATOR_LIST.      
	 * @param studyProtocol     
	 */     
	public List<StudyInvestigator> getStudyInvestigators(gov.nih.nci.coppa.services.pa.StudySite coppaStudySite, StudyOrganization studyOrganization){
		List<StudyInvestigator> studyInvestigatorList = new ArrayList<StudyInvestigator>();
		String studySiteContactResultXml = null;
		String studySitePayLoad = CoppaPAObjectFactory.getPAIdXML(CoppaPAObjectFactory.getStudySiteId(coppaStudySite.getIdentifier().getExtension()));        
		try{
			studySiteContactResultXml = protocolAbstractionResolverUtils.broadcastStudySiteContactGetByStudySite(studySitePayLoad);
			StudySiteContact studySiteContact = null;
			   
			List<String> results = XMLUtils.getObjectsFromCoppaResponse(studySiteContactResultXml);        
			for(String studySiteContactXml : results){            
				studySiteContact = CoppaPAObjectFactory.getStudySiteContact(studySiteContactXml);
				if(!studySiteContact.getStatusCode().getCode().equalsIgnoreCase(NULLIFIED)){
					if(studySiteContact != null && SITE_INVESTIGATOR_LIST.contains(studySiteContact.getRoleCode().getCode())){
						HealthCareProvider healthCareProvider = getHealthCareProviderFromExtension(studySiteContact.getHealthCareProvider().getExtension());
						if(healthCareProvider != null){ 
							gov.nih.nci.coppa.po.Person coppaPerson = getCoppaPersonFromPersonIdExtension(healthCareProvider.getPlayerIdentifier().getExtension());
							StudyInvestigator studyInvestigator = getPopulatedStudyInvestigator(coppaPerson, studyOrganization, StudyInvestigator.SITE_INVESTIGATOR);
							if(studyInvestigator != null){
								studyInvestigatorList.add(studyInvestigator);
							}
						}        
					}
				}	
			}
		} catch (C3PRCodedException e) {
		   log.error(e);
		}
		return studyInvestigatorList;
	}
	
	
	/**
	 * Gets the remote investigator. returns null if the investigator doesnt have a valid email address.
	 * 
	 * @param coppaPerson the coppa person
	 * @param healthcareSite the healthcare site
	 * 
	 * @return the remote investigator
	 */
	private StudyInvestigator getPopulatedStudyInvestigator(Person coppaPerson, StudyOrganization studyOrganization, String roleCode) {
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemoteInvestigator());
	    if(object == null){
	    	return null;
	    } else {
	    	RemoteInvestigator remoteInvestigator = (RemoteInvestigator) object;
	    	List<IdentifiedPerson> identifiedPersonsList = personOrganizationResolverUtils.getIdentifiedPerson(coppaPerson.getIdentifier());
            String assignedIdentifier = null;
            for(IdentifiedPerson identifiedPerson: identifiedPersonsList){
            	if (identifiedPerson != null && identifiedPerson.getAssignedId().getRoot().equalsIgnoreCase(CTEP_PERSON)) {
                    assignedIdentifier = identifiedPerson.getAssignedId().getExtension();
                    break;
                }
            }
            if(assignedIdentifier == null){
            	assignedIdentifier = coppaPerson.getIdentifier().getExtension();
            }
            remoteInvestigator.setAssignedIdentifier(assignedIdentifier);
            
	    	remoteInvestigator.setExternalId(coppaPerson.getIdentifier().getExtension());
		    HealthcareSiteInvestigator healthcareSiteInvestigator = new HealthcareSiteInvestigator();
		    healthcareSiteInvestigator.setHealthcareSite(studyOrganization.getHealthcareSite());
		    healthcareSiteInvestigator.setInvestigator(remoteInvestigator);
		    healthcareSiteInvestigator.setStatusCode(InvestigatorStatusCodeEnum.AC);
			
		    remoteInvestigator.getHealthcareSiteInvestigators().add(healthcareSiteInvestigator);
			
		    StudyInvestigator studyInvestigator = new StudyInvestigator();
			studyInvestigator.setHealthcareSiteInvestigator(healthcareSiteInvestigator);
			studyInvestigator.setStudyOrganization(studyOrganization);
			studyInvestigator.setStatusCode(InvestigatorStatusCodeEnum.AC);
			studyInvestigator.setRoleCode(roleCode);
		    return studyInvestigator;
	    }
	}

	
	/**
	 * Sets the principal investigator for the Study.
	 * 
	 * @param remoteStudy the remote study
	 * @param extension the extension
	 */
	private void setPrincipalInvestigator(RemoteStudy remoteStudy, String extension) {

		//Call search on StudySite using the interventionalStudyProtocol II. use the returned list
		StudyInvestigator studyInvestigator = null;
		boolean isStudyOrganizationNew = true;
		String paPayLoad = CoppaPAObjectFactory.getPAIdXML(CoppaPAObjectFactory.getPAId(extension));
		try {
			String scResultXml  = protocolAbstractionResolverUtils.broadcastStudyContactGetByStudyProtocol(paPayLoad);
			List<String> scResults = XMLUtils.getObjectsFromCoppaResponse(scResultXml);
			StudyContact studyContact = null;
			if(scResults != null){
				for(String studyContactXml : scResults){
					studyContact = (StudyContact)CoppaPAObjectFactory.getStudyContact(studyContactXml);
					if(studyContact != null && studyContact.getRoleCode().getCode().equalsIgnoreCase(STUDY_PRINCIPAL_INVESTIGATOR)){
						HealthCareProvider healthCareProvider = getHealthCareProviderFromExtension(studyContact.getHealthCareProvider().getExtension());
					    if(healthCareProvider != null){
						    //The scoper id is the Id of the organization . use it to get the Organization Id
					    	String coppaOrganizationExtension = healthCareProvider.getScoperIdentifier().getExtension();
						    
						    //Add the studySite to the remoteStudy
						    StudyOrganization newStudyOrganization = null;
						    //If the coppaOrganization's externalId matches that of the existing studyOrgs in the study then ignore it.
						    for(StudyOrganization existingStudyOrganization : remoteStudy.getStudyOrganizations()){
						    	if(existingStudyOrganization.getHealthcareSite() instanceof RemoteHealthcareSite){
						    		RemoteHealthcareSite existingRemoteHealthcareSite = (RemoteHealthcareSite)existingStudyOrganization.getHealthcareSite();
						    		if(coppaOrganizationExtension.equals(existingRemoteHealthcareSite.getExternalId())){
						    			isStudyOrganizationNew = false;
						    			newStudyOrganization = existingStudyOrganization;
						    			break;
						    		}
						    	}
						    }
						    //If the coppaOrganization's externalId DOES NOT match an existing studyOrg then set get the corresponding hcs and set it.
						    if(newStudyOrganization == null){
						    	gov.nih.nci.coppa.po.Organization coppaOrganization = getOrganizationFromExtension(healthCareProvider.getScoperIdentifier().getExtension());
						    	RemoteHealthcareSite remoteHealthcareSiteForPI = 
							   		personOrganizationResolverUtils.getRemoteHealthcareSiteFromCoppaOrganization(coppaOrganization, true);
						    
						    	newStudyOrganization = new StudySite();
						    	newStudyOrganization.setHealthcareSite(remoteHealthcareSiteForPI);
						    }
						    
						    //The player id is the Id of a Person . use it to get the Person by Id.
						    gov.nih.nci.coppa.po.Person coppaPerson = getCoppaPersonFromPersonIdExtension(healthCareProvider.getPlayerIdentifier().getExtension());
						    studyInvestigator = getPopulatedStudyInvestigator(coppaPerson, newStudyOrganization, StudyInvestigator.PRINCIPAL_INVESTIGATOR);
						    if(studyInvestigator != null){
						    	newStudyOrganization.getStudyInvestigators().add(studyInvestigator);
						    } else {
						    	return;
						    }
							
							//add the studySite with the loaded PI to the remoteStudy which is passed in.
						    if(isStudyOrganizationNew){
						    	remoteStudy.addStudyOrganization(newStudyOrganization);
						    }
					    }
				     }
				}
			}
		} catch (C3PRCodedException e) {
			log.error(e);
		}
	}

	/**
	 * Gets the health care provider from extension. return null if nothing is found.
	 * 
	 * @param extension the extension
	 * 
	 * @return the health care provider from extension
	 * 
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
	private HealthCareProvider getHealthCareProviderFromExtension(String extension) throws C3PRCodedException{
		String coppaHealthCareProviderXml = CoppaObjectFactory.getHealthCareProviderIdXML(extension);
		String healthCareProviderResult  = personOrganizationResolverUtils.broadcastHealthcareProviderGetById(coppaHealthCareProviderXml);
		List<String> healthCareProviderResults = XMLUtils.getObjectsFromCoppaResponse(healthCareProviderResult);
		if(healthCareProviderResults.size() > 0){
			return CoppaObjectFactory.getCoppaHealthCareProvider(healthCareProviderResults.get(0));
		} else {
			log.error("Rejecting HealthCareProvider as the given extension never existed" );
			return null;
		}
	}
	
	/**
	 * Gets the coppa person from person id extension.
	 * 
	 * @param extension the extension
	 * 
	 * @return the coppa person from person id extension
	 * 
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
	private Person getCoppaPersonFromPersonIdExtension(String extension) throws C3PRCodedException{
		String idXml = CoppaObjectFactory.getCoppaPersonIdXML(extension);                
		//above player id is the Id of a Person. Now get the Person by Id.
		String personResultXml = personOrganizationResolverUtils.broadcastPersonGetById(idXml);
		List<String> persons = XMLUtils.getObjectsFromCoppaResponse(personResultXml);
		if(persons.size() > 0){
			return CoppaObjectFactory.getCoppaPerson(persons.get(0));
		}
		return null;
	}
	
	/**
	 * Gets the organization from extension.
	 * 
	 * @param extension the extension
	 * 
	 * @return the organization from extension
	 * 
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
	private Organization getOrganizationFromExtension(String extension) throws C3PRCodedException {
		DSETII dsetii = CoppaObjectFactory.getDSETIISearchCriteria(extension);
		String organizationPayload = CoppaObjectFactory.getCoppaIIXml(dsetii);
		String oResultXml = personOrganizationResolverUtils.broadcastOrganizationGetById(organizationPayload);
		List<String> results = XMLUtils.getObjectsFromCoppaResponse(oResultXml);
		return CoppaObjectFactory.getCoppaOrganization(results.get(0));
	}
	
	
	/**
	 * Gets the study protocols from result xml.
	 * 
	 * @param resultXml the result xml
	 * 
	 * @return the study protocols from result xml
	 */
	public List<gov.nih.nci.coppa.services.pa.StudyProtocol> getStudyProtocolsFromResultXml(String resultXml){
		List<gov.nih.nci.coppa.services.pa.StudyProtocol> studyProtocols = 
			new ArrayList<gov.nih.nci.coppa.services.pa.StudyProtocol>();
		if(!StringUtils.isEmpty(resultXml)){
			List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
			for (String result:results) {
				studyProtocols.add(CoppaPAObjectFactory.getStudyProtocolObject(result));
			}
		}
		return studyProtocols;
	}

	
	public Object saveOrUpdate(Object example) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ProtocolAbstractionResolverUtils getProtocolAbstractionResolverUtils() {
		return protocolAbstractionResolverUtils;
	}

	public void setProtocolAbstractionResolverUtils(
			ProtocolAbstractionResolverUtils protocolAbstractionResolverUtils) {
		this.protocolAbstractionResolverUtils = protocolAbstractionResolverUtils;
	}

	public PersonOrganizationResolverUtils getPersonOrganizationResolverUtils() {
		return personOrganizationResolverUtils;
	}

	public void setPersonOrganizationResolverUtils(
			PersonOrganizationResolverUtils personOrganizationResolverUtils) {
		this.personOrganizationResolverUtils = personOrganizationResolverUtils;
	}

}
