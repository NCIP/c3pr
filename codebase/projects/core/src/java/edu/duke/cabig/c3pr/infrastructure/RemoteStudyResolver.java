package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.iso._21090.DSETII;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.util.CoppaObjectFactory;
import com.semanticbits.coppasimulator.util.CoppaPAObjectFactory;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
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
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.services.pa.DocumentWorkflowStatus;
import gov.nih.nci.coppa.services.pa.StudyContact;
import gov.nih.nci.coppa.services.pa.StudyOverallStatus;
import gov.nih.nci.coppa.services.pa.StudyProtocol;
import gov.nih.nci.coppa.services.pa.StudySiteContact;

public class RemoteStudyResolver implements RemoteResolver {

	/** The log. */
	private Logger log = Logger.getLogger(RemoteStudyResolver.class);
	
	/** The function codes for StudySite */
	public static final String LEAD_ORGANIZATION = "Lead Organization";
	public static final String SPONSOR = "Sponsor";
	public static final String TREATING_SITE_1 = "Funding Source";
	public static final String TREATING_SITE_2 = "Agent Source";
	public static final String TREATING_SITE_3 = "Laboratory";
	public static final String TREATING_SITE_4 = "Treating Site";
	public List<String> TREATING_SITE_LIST = Arrays.asList(TREATING_SITE_1, TREATING_SITE_2, TREATING_SITE_3, TREATING_SITE_4);
	
	/* List of values for studySiteContact. The studySiteContact has to have one of these to be eligible for fetching as an Investigator. */
	public static final List<String> SITE_INVESTIGATOR_LIST = Arrays.asList("Principal Investigator", "Coordinating Investigator", "Sub Investigator");
	
	/* Study level PI role code*/
	public static final String STUDY_PRINCIPAL_INVESTIGATOR = "Study Principal Investigator";
	
	/* List of values for DocumentWorkflowStatus. The study has to have one of these to be eligible for fetching. */
	public static final List<String> DOCUMENT_WORKFLOW_STATUS_LIST = Arrays.asList("Abstracted", "Abstraction Verified Response", "Abstraction Verified No Response");
	
	/** The PA utils which has all the serialize/deserialze and broadcast methods */
	private ProtocolAbstractionResolverUtils protocolAbstractionResolverUtils = null;
	
	/** The PO utils which has all the serialize/deserialze and broadcast methods */
	private PersonOrganizationResolverUtils personOrganizationResolverUtils = null;
	
	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 */
	public Object getRemoteEntityByUniqueId(String externalId) {
		String paPayLoad = CoppaPAObjectFactory.getPAIdXML(CoppaPAObjectFactory.getPAId(externalId));
		String resultXml  = "";
		try {
			resultXml  = protocolAbstractionResolverUtils.broadcastStudyProtocolGetById(paPayLoad);
		} catch (C3PRCodedException e) {
			log.error(e);
		}
		List<gov.nih.nci.coppa.services.pa.StudyProtocol> studyProtocols = getStudyProtocolsFromResultXml(resultXml);
		RemoteStudy remoteStudy = null;
		if(studyProtocols.size() > 0){
			remoteStudy = getRemoteStudyFromStudyProtocol(studyProtocols.get(0));
		}
		
		return remoteStudy;
	}
	
	/**
	 * Searches Coppa database for orgs simliar to the example RemoteHelathcareSite that is passed into it.
	 * 
	 * @param Object the remote HealthcareSite
	 * @return the object list; list of remoteHealthcareSites
	 */
	public List<Object> find(Object example) {

		RemoteStudy remoteStudyExample = (RemoteStudy)example;
		String resultXml  = "";
		//hard-coded id for PA ..... String payLoad = CoppaPAObjectFactory.getPAIdXML(CoppaPAObjectFactory.getPAId("27633"));
		//Get protocolSearchPayload using the search criteria specified in the example Study.
		String payLoad = CoppaPAObjectFactory.getStudyProtocolSearchXML(remoteStudyExample.getShortTitleText(), remoteStudyExample.getCoordinatingCenterAssignedIdentifier().getValue(), null);
		try {
			resultXml  = protocolAbstractionResolverUtils.broadcastStudyProtocolSearch(payLoad);
		} catch (C3PRCodedException e) {
			log.error(e);
		}
		
		List<gov.nih.nci.coppa.services.pa.StudyProtocol> studyProtocols = getStudyProtocolsFromResultXml(resultXml);

		List<Object> remoteStudies = new ArrayList<Object>();
		for (gov.nih.nci.coppa.services.pa.StudyProtocol studyProtocol : studyProtocols) {
			RemoteStudy remoteStudy = getRemoteStudyFromStudyProtocol(studyProtocol);
			if (remoteStudy != null) {
				remoteStudies.add(remoteStudy);
			}
		}
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
		remoteStudy.setShortTitleText(CoppaPAObjectFactory.getShortTitleFromStudyProtocol(studyProtocol));
		remoteStudy.setLongTitleText(CoppaPAObjectFactory.getLongTitleFromStudyProtocol(studyProtocol));
		remoteStudy.setType(CoppaPAObjectFactory.getTypeFromStudyProtocol(studyProtocol));
		remoteStudy.setPhaseCode(protocolAbstractionResolverUtils.getPhaseCodeFromCoppaPhaseCode(studyProtocol.getPhaseCode().getCode()));
		remoteStudy.setExternalId(studyProtocol.getIdentifier().getExtension());
		
		//Set NCI identifier for Study
		setStudyNciIdentifier(remoteStudy, studyProtocol.getAssignedIdentifier().getExtension());
		
		//Set StudyOrganizations
		List<StudyOrganization> remoteStudyOrganizations = getStudyOrganizationsForStudyProtocol(studyProtocol);
		remoteStudy.addStudyOrganizations(remoteStudyOrganizations);
		for(StudyOrganization studyOrganization : remoteStudyOrganizations){
			studyOrganization.setStudy(remoteStudy);
		}

		//Set PI for Study
		setPrincipalInvestigator(remoteStudy, studyProtocol.getIdentifier().getExtension());
		
		//Set status
		remoteStudy.setCoordinatingCenterStudyStatus(getStudyCoordinatingCenterStudyStatus(studyProtocol));
		
		//Set default values in RemoteStudy
		remoteStudy.setRandomizationType(RandomizationType.PHONE_CALL);
		remoteStudy.setRandomizedIndicator(Boolean.TRUE);
		remoteStudy.setStratificationIndicator(Boolean.FALSE);
		remoteStudy.setCompanionIndicator(Boolean.FALSE);
		remoteStudy.setStandaloneIndicator(Boolean.TRUE);
		
		return remoteStudy;
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
	private List<StudyOrganization> getStudyOrganizationsForStudyProtocol(StudyProtocol studyProtocol) {
		//call search on StudySite using the interventionalStudyProtocol II. use the returned list
		String payLoad = CoppaPAObjectFactory.getPAIdXML(CoppaPAObjectFactory.getPAId(studyProtocol.getIdentifier().getExtension()));
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
						studyCoordinatingCenter.getHealthcareSite().getIdentifiersAssignedToOrganization().add(organizationAssignedIdentifier);
					}
					studyOrganizationList.add(studyCoordinatingCenter);
					
					//Now get the assciated investigators and build the studyInv/healthcareSiteInvestigator and link them to the healthcareSite.
					List<StudyInvestigator> studyInvestigatorList = getStudyInvestigators(studySiteTemp, studyCoordinatingCenter);
					for(StudyInvestigator studyInvestigator : studyInvestigatorList){
						studyCoordinatingCenter.addStudyInvestigator(studyInvestigator);
					}
				}
			}
			if(studySiteTemp.getFunctionalCode().getCode().equals(SPONSOR)){
				//Fetch the Funding sponsor(ResearchOrganziation) and add to the list to be returned
				StudyFundingSponsor studyFundingSponsor = getFundingSponsorFromCoppaStudySite(studySiteTemp);
				if(studyFundingSponsor != null){
					organizationAssignedIdentifier = getOrganizationAssignedIdentifierFromCoppaStudySite(studySiteTemp, OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER);
					if(organizationAssignedIdentifier  != null){
						studyFundingSponsor.getHealthcareSite().getIdentifiersAssignedToOrganization().add(organizationAssignedIdentifier);
					}
					studyOrganizationList.add(studyFundingSponsor);
					
					//Now get the assciated investigators and build the studyInv/healthcareSiteInvestigator and link them to the healthcareSite.
					List<StudyInvestigator> studyInvestigatorList = getStudyInvestigators(studySiteTemp, studyFundingSponsor);
					for(StudyInvestigator studyInvestigator : studyInvestigatorList){
						studyFundingSponsor.addStudyInvestigator(studyInvestigator);
					}
				}
			}
			if(studySiteTemp.getFunctionalCode().getCode().equals(TREATING_SITE_4)){
				//Fetch the Study Site(HealthcareFacility) and add to the list to be returned
				StudySite studySite = getStudysiteFromCoppaStudySite(studySiteTemp);
				if(studySite != null){
					organizationAssignedIdentifier = getOrganizationAssignedIdentifierFromCoppaStudySite(studySiteTemp, OrganizationIdentifierTypeEnum.CTEP);
					if(organizationAssignedIdentifier  != null){
						studySite.getHealthcareSite().getIdentifiersAssignedToOrganization().add(organizationAssignedIdentifier);
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
			gov.nih.nci.coppa.po.ResearchOrganization researchOrganization = CoppaObjectFactory.getResearchOrganization(roResults.get(0));

			//Assuming here that the playerII in the RO is the Organization II
			gov.nih.nci.coppa.po.Organization coppaOrganization = getOrganizationFromExtension(researchOrganization.getPlayerIdentifier().getExtension());
			
			RemoteHealthcareSite remoteHealthcareSite = 
						personOrganizationResolverUtils.getRemoteHealthcareSiteFromCoppaOrganization(coppaOrganization, false);
			//set the CTEP Id for the Org from the Structural Role(HealthcareFacility).
			personOrganizationResolverUtils.setCtepCodeFromStructuralRoleIIList(remoteHealthcareSite, researchOrganization.getIdentifier().getItem());
			return remoteHealthcareSite;
		} catch (C3PRCodedException e) {
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
			
			//Assuming here that the payerII in the HCF is the Organization II
			gov.nih.nci.coppa.po.Organization coppaOrganization = getOrganizationFromExtension(healthcareFacility.getPlayerIdentifier().getExtension());
			RemoteHealthcareSite remoteHealthcareSite = 
					personOrganizationResolverUtils.getRemoteHealthcareSiteFromCoppaOrganization(coppaOrganization, false);
			//set the CTEP Id for the Org from the Structural Role(HealthcareFacility).
			personOrganizationResolverUtils.setCtepCodeFromStructuralRoleIIList(remoteHealthcareSite, healthcareFacility.getIdentifier().getItem());
			
			studySite.setHealthcareSite(remoteHealthcareSite);
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
		String paIdPayLoad = CoppaPAObjectFactory.getPAIdXML(CoppaPAObjectFactory.getPAId(studyProtocol.getIdentifier().getExtension()));
		try{
			//Call search on DocumentWorkflowStatus using the StudyProtocol II. 
			String documentWorkflowResultXml  = protocolAbstractionResolverUtils.broadcastDocumentWorkflowStatusGetByStudyProtocol(paIdPayLoad);
			List<String> documentWorkflowResults = XMLUtils.getObjectsFromCoppaResponse(documentWorkflowResultXml);
			DocumentWorkflowStatus documentWorkflowStatus = CoppaPAObjectFactory.getDocumentWorkflowStatus(documentWorkflowResults.get(0));
			
			//If the DocumentWorkflowStatus does not have ABSTRACTED keyword in it then dont bother getting the overallStatus
			if(DOCUMENT_WORKFLOW_STATUS_LIST.contains(documentWorkflowStatus.getStatusCode().getCode())){
				//Call search on StudyOverallStatus using the StudyProtocol II. 
				String overallStatusResultXml = protocolAbstractionResolverUtils.broadcastStudyOverallStatusGetByStudyProtocol(paIdPayLoad);
				
				List<String> overallStatusResults = XMLUtils.getObjectsFromCoppaResponse(overallStatusResultXml);
				StudyOverallStatus studyOverallStatus = CoppaPAObjectFactory.getStudyOverallStatus(overallStatusResults.get(0));

				return protocolAbstractionResolverUtils.getCoordinatingCenterStudyStatusFromCoppaOverallStatus(studyOverallStatus);
			}
		} catch(C3PRCodedException e){
			log.error(e.getMessage());
		}
		//Default to Pending if something doesnt click.
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
				if(studySiteContact != null && SITE_INVESTIGATOR_LIST.contains(studySiteContact.getRoleCode().getCode())){
					HealthCareProvider healthCareProvider = getHealthCareProviderFromExtension(studySiteContact.getHealthCareProvider().getExtension());
					if(healthCareProvider != null){ 
						gov.nih.nci.coppa.po.Person coppaPerson = getCoppaPersonFromPersonIdExtension(healthCareProvider.getPlayerIdentifier().getExtension());
						StudyInvestigator studyInvestigator = getPopulatedStudyInvestigator(coppaPerson, studyOrganization, StudyInvestigator.SITE_INVESTIGATOR);
						studyInvestigatorList.add(studyInvestigator);
					}        
				}
			}
		} catch (C3PRCodedException e) {
		   log.error(e);
		}
		return studyInvestigatorList;
	}
	
	
	/**
	 * Gets the remote investigator.
	 * 
	 * @param coppaPerson the coppa person
	 * @param healthcareSite the healthcare site
	 * 
	 * @return the remote investigator
	 */
	private StudyInvestigator getPopulatedStudyInvestigator(Person coppaPerson, StudyOrganization studyOrganization, String roleCode) {
		RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
	    remoteInvestigator = (RemoteInvestigator)personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, remoteInvestigator);
	    remoteInvestigator.setNciIdentifier(coppaPerson.getIdentifier().getExtension());
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
						    newStudyOrganization.getStudyInvestigators().add(studyInvestigator);
							
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
	 * Gets the health care provider from extension.
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
		return CoppaObjectFactory.getCoppaHealthCareProvider(healthCareProviderResults.get(0));
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
		
		/*SetPI() code
		 * String orgIdXml = CoppaObjectFactory.getCoppaIIXml(healthCareProvider.getScoperIdentifier());
	    String organizationResultXml = personOrganizationResolverUtils.broadcastOrganizationGetById(orgIdXml);
	    List<String> organizationResults = XMLUtils.getObjectsFromCoppaResponse(organizationResultXml);
	    gov.nih.nci.coppa.po.Organization coppaOrganization = CoppaObjectFactory.getCoppaOrganization(organizationResults.get(0));*/
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
