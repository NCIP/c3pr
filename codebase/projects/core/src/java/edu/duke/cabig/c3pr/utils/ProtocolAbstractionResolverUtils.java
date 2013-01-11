/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;

import com.semanticbits.coppasimulator.util.CoppaPAObjectFactory;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.esb.Metadata;
import edu.duke.cabig.c3pr.esb.OperationNameEnum;
import edu.duke.cabig.c3pr.esb.ServiceTypeEnum;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import gov.nih.nci.coppa.services.pa.StudyOverallStatus;


/**This is a ProtocolAbstraction related utility class used mainly by the Study related Resolvers.
 * @author Vinay
 *
 */
public class ProtocolAbstractionResolverUtils {
	
	/** The message broadcaster. */
	private CCTSMessageBroadcaster coppaMessageBroadcaster;
	
	/** The exception helper. */
	protected C3PRExceptionHelper exceptionHelper;

	/** The c3pr error messages. */
	private MessageSource c3prErrorMessages;
	
	/**
	 * The Limit value for PA searches
	 */
	private int paLimitValue;
	
	/** The log. */
    private static Log log = LogFactory.getLog(ProtocolAbstractionResolverUtils.class);
    
    /*HashMap for CoppaStatus - C3PR status*/
	public static LinkedHashMap<String, CoordinatingCenterStudyStatus> statusMap = new LinkedHashMap<String, CoordinatingCenterStudyStatus>();
	
	/*HashMap for Study Protocol Phase Code - C3PR Study Phase Code */
	public static LinkedHashMap<String, String> phaseCodeMap = new LinkedHashMap<String, String>(); 
	
	/*HashMap for Study Protocol Type - C3PR Study Type*/
	public static LinkedHashMap<String, String> studyTypeMap = new LinkedHashMap<String, String>(); 
	
	
	public ProtocolAbstractionResolverUtils() {
		statusMap.put("In Review", CoordinatingCenterStudyStatus.PENDING);
		statusMap.put("Disapproved", CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		statusMap.put("Approved", CoordinatingCenterStudyStatus.PENDING);
		statusMap.put("Active", CoordinatingCenterStudyStatus.PENDING);
		statusMap.put("Closed to Accrual", CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
		statusMap.put("Closed to Accrual and Intervention", CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		statusMap.put("Temporarily Closed to Accrual", CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL);
		statusMap.put("Temporarily Closed to Accrual and Intervention", CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);
		statusMap.put("Withdrawn", CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
		statusMap.put("Administratively Complete", CoordinatingCenterStudyStatus.PENDING);
		statusMap.put("Complete", CoordinatingCenterStudyStatus.PENDING);
		
		//PhaseCode Mappings
		phaseCodeMap.put("0", "Phase 0 Trial");
		phaseCodeMap.put("I", "Phase I Trial");
		phaseCodeMap.put("II", "Phase II Trial");
		phaseCodeMap.put("I/II", "Phase I/II Trial");
		phaseCodeMap.put("III", "Phase III Trial");
		phaseCodeMap.put("II/III", "Phase II/III Trial");
		phaseCodeMap.put("IV", "Phase IV Trial");
		phaseCodeMap.put("Pilot", "Pilot");
		phaseCodeMap.put("N/A", "N/A");
		phaseCodeMap.put("Other", "Other");
		
		//studyTypeMap mappings
		studyTypeMap.put("Treatment", "Treatment");
		studyTypeMap.put("Prevention", "Prevention");
		studyTypeMap.put("Diagnostic", "Diagnostic");
		studyTypeMap.put("Early Detection", "Early Detection");
		studyTypeMap.put("Supportive", "Supportive");
		studyTypeMap.put("Supportive Care", "Supportive Care");
		studyTypeMap.put("Screening", "Screening");
		studyTypeMap.put("Epidemiologic", "Epidemiologic");
		studyTypeMap.put("Observational", "Observational");
		studyTypeMap.put("Outcome", "Outcome");
		studyTypeMap.put("Ancillary", "Ancillary");
		studyTypeMap.put("Correlative", "Correlative");
		studyTypeMap.put("Health Services Research", "Health Services Research");
		studyTypeMap.put("Basic Science", "Basic Science");
		studyTypeMap.put("Other", "Other");
		studyTypeMap.put("Genetic Therapeutic", "Genetic Therapeutic");
		studyTypeMap.put("Interventional", "Interventional");
		studyTypeMap.put("Primary Treatment", "Primary Treatment");
		studyTypeMap.put("Genetic Non-therapeutic", "Genetic Non-therapeutic");
		studyTypeMap.put("AE", "AE");
	}

	
	/**
	 * Gets the coordinating center study status from coppa overall status.
	 * 
	 * @param status the status
	 * 
	 * @return the coordinating center study status from coppa overall status
	 */
	public CoordinatingCenterStudyStatus getCoordinatingCenterStudyStatusFromCoppaOverallStatus(StudyOverallStatus status) {
		if(statusMap.containsKey(status.getStatusCode().getCode())){
			return statusMap.get(status.getStatusCode().getCode());
		} else{
			return CoordinatingCenterStudyStatus.PENDING;
		}
	}
	
	/**
	 * Gets the C3PR Phase code from coppa PhaseCode.
	 * 
	 * @param status the status
	 * 
	 * @return the coordinating center study status from coppa overall status
	 */
	public String getPhaseCodeFromCoppaPhaseCode(String phaseCode) {
		if(phaseCodeMap.containsKey(phaseCode)){
			return phaseCodeMap.get(phaseCode);
		} else{
			return "N/A";
		}
	}
	
	/**
	 * Gets the C3PR Phase code from coppa PhaseCode.
	 * 
	 * @param status the status
	 * 
	 * @return the coordinating center study status from coppa overall status
	 */
	public String getStudyTypeFromCoppaStudyType(String studyType) {
		if(studyTypeMap.containsKey(studyType)){
			return studyTypeMap.get(studyType);
		} else{
			return "N/A";
		}
	}
	
	/**
	 * Broadcast Interventional Study Protocol search.
	 * 
	 * @param gov.nih.nci.coppa.po.Organization coppa organization example to search by.
	 * @return the List<Object> list of coppa organizations
	 * @throws C3PRCodedException the c3pr coded exception
	 */
	public String broadcastStudyProtocolSearch(String studyProtocolXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.search.getName() + "   Service -->" +ServiceTypeEnum.STUDY_PROTOCOL.getName());
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.STUDY_PROTOCOL.getName());
        
        List<String> cctsDomainObjectXMLList = new ArrayList<String>();
        cctsDomainObjectXMLList.add(studyProtocolXml);
        return broadcastCoppaMessage(cctsDomainObjectXMLList, mData, true);
	}

	/**
	 * Broadcast  Study Protocol getById.
	 * 
	 * @param gov.nih.nci.coppa.po.Organization coppa organization example to search by.
	 * @return the List<Object> list of coppa organizations
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
	public String broadcastStudyProtocolGetById(String studyProtocolXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getStudyProtocol.getName() + "   Service -->" +ServiceTypeEnum.STUDY_PROTOCOL.getName());
        Metadata mData = new Metadata(OperationNameEnum.getStudyProtocol.getName(), "extId", ServiceTypeEnum.STUDY_PROTOCOL.getName());
        return broadcastCoppaMessage(studyProtocolXml, mData);
	}
	
	
	/**
	 * Broadcast Interventional Study Protocol search.
	 * 
	 * @param gov.nih.nci.coppa.po.Organization coppa organization example to search by.
	 * @return the List<Object> list of coppa organizations
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
	public String broadcastInterventionalStudyProtocolSearch(String healthcareSiteXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getInterventionalStudyProtocol.getName() + "   Service -->" +ServiceTypeEnum.STUDY_PROTOCOL.getName());
        Metadata mData = new Metadata(OperationNameEnum.getInterventionalStudyProtocol.getName(), "extId", ServiceTypeEnum.STUDY_PROTOCOL.getName());
        return broadcastCoppaMessage(healthcareSiteXml, mData);
	}	

	public String broadcastStudySiteGetByStudyProtocol(String studySiteXml) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getByStudyProtocol.getName() + "   Service -->" +ServiceTypeEnum.STUDY_SITE.getName());
        Metadata mData = new Metadata(OperationNameEnum.getByStudyProtocol.getName(), "extId", ServiceTypeEnum.STUDY_SITE.getName());
        return broadcastCoppaMessage(studySiteXml, mData);
	}
	
	public String broadcastStudyOverallStatusGetByStudyProtocol(String studySiteXml) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getCurrentByStudyProtocol.getName() + "   Service -->" +ServiceTypeEnum.STUDY_OVERALL_STATUS.getName());
        Metadata mData = new Metadata(OperationNameEnum.getCurrentByStudyProtocol.getName(), "extId", ServiceTypeEnum.STUDY_OVERALL_STATUS.getName());
        return broadcastCoppaMessage(studySiteXml, mData);
	}
	
	public String broadcastDocumentWorkflowStatusGetByStudyProtocol(String payLoad)  throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getCurrentByStudyProtocol.getName() + "   Service -->" +ServiceTypeEnum.DOCUMENT_WORKFLOW_STATUS.getName());
        Metadata mData = new Metadata(OperationNameEnum.getCurrentByStudyProtocol.getName(), "extId", ServiceTypeEnum.DOCUMENT_WORKFLOW_STATUS.getName());
        return broadcastCoppaMessage(payLoad, mData);
	}
	
	public String broadcastResearchOrganizationGetById(String researchOrganizationXml) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getById.getName() + "   Service -->" +ServiceTypeEnum.RESEARCH_ORGANIZATION.getName());
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "extId", ServiceTypeEnum.RESEARCH_ORGANIZATION.getName());
        return broadcastCoppaMessage(researchOrganizationXml, mData);
	}
	
	public String broadcastStudyContactGetByStudyProtocol(String payLoad) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getByStudyProtocol.getName() + "   Service -->" +ServiceTypeEnum.STUDY_CONTACT.getName());
        Metadata mData = new Metadata(OperationNameEnum.getByStudyProtocol.getName(), "extId", ServiceTypeEnum.STUDY_CONTACT.getName());
        return broadcastCoppaMessage(payLoad, mData);
	}
	
	public String broadcastStudySiteContactGetByStudySite(String studySiteXml) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getByStudySite.getName() + "   Service -->" +ServiceTypeEnum.STUDY_SITE_CONTACT.getName());
		Metadata mData = new Metadata(OperationNameEnum.getByStudySite.getName(), "extId", ServiceTypeEnum.STUDY_SITE_CONTACT.getName());
        return broadcastCoppaMessage(studySiteXml, mData);
	}
	
	public String broadcastArmGetByStudyProtocol(String payLoad) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getByStudyProtocol.getName() + "   Service -->" +ServiceTypeEnum.ARM.getName());
		Metadata mData = new Metadata(OperationNameEnum.getByStudyProtocol.getName(), "extId", ServiceTypeEnum.ARM.getName());
        return broadcastCoppaMessage(payLoad, mData);
	}
	
	public String broadcastEligibilityGetByStudyProtocol(String payLoad) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getPlannedEligibilityCriterionByStudyProtocol.getName() + "   Service -->" +ServiceTypeEnum.PLANNED_ACTIVITY.getName());
		Metadata mData = new Metadata(OperationNameEnum.getPlannedEligibilityCriterionByStudyProtocol.getName(), "extId", ServiceTypeEnum.PLANNED_ACTIVITY.getName());
        return broadcastCoppaMessage(payLoad, mData);
	}
	
	/**
	 * Broadcast coppa message. The actual call to the esb-client which takes String.
	 * 
	 * @param healthcareSiteXml the healthcare site xml
	 * @param mData the m data
	 * @return the string
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
	private String broadcastCoppaMessage(String healthcareSiteXml, Metadata mData) throws C3PRCodedException {
		String caXchangeResponseXml = null;
		try {
            caXchangeResponseXml = getCoppaMessageBroadcaster().broadcastCoppaMessage(healthcareSiteXml, mData);
        }
        catch (Exception e) {
            log.error(e);
            throw this.exceptionHelper.getException(
                    getCode("C3PR.EXCEPTION.ORGANIZATION.SEARCH.BROADCAST.SEND_ERROR"), e);
        }
		return caXchangeResponseXml;
	}
	
	/**
	 * Broadcast coppa message. The actual call to the esb-client which takes a List of Strings.
	 * NOTE: Users of this method do not need to specify the Limit, a default value is added by this method.
	 * It is important to note that the pauload needs to be the first element in the list and the Limit needs to be
	 * the second element, otherwise it results in a parse exception.
	 * 
	 * @param healthcareSiteXml the healthcare site xml
	 * @param mData the m data
	 * @return the string
	 * @throws C3PRCodedException the c3pr coded exception
	 */
	private String broadcastCoppaMessage(List<String> cctsDomainObjectXMLList, Metadata mData, boolean setLimit) throws C3PRCodedException {
		String caXchangeResponseXml = null;
		//adding a default limit-offset setting incase its not already specified
		if(setLimit){
			cctsDomainObjectXMLList.add(CoppaPAObjectFactory.getLimitOffsetXML(paLimitValue, 0));
		}
		
		try {
            caXchangeResponseXml = getCoppaMessageBroadcaster().broadcastCoppaMessage(cctsDomainObjectXMLList, mData);
        }
        catch (Exception e) {
            log.error(e);
            throw this.exceptionHelper.getException(
                    getCode("C3PR.EXCEPTION.ORGANIZATION.SEARCH.BROADCAST.SEND_ERROR"), e);
        }
		return caXchangeResponseXml;
	}
	
	/**
     * Gets the error code which is used to retrieve the exception message.
     * 
     * @param errortypeString the errortype string
     * @return the code
     */
    public int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }
    
	public C3PRExceptionHelper getExceptionHelper() {
		return exceptionHelper;
	}

	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}

	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}

	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}

	public CCTSMessageBroadcaster getCoppaMessageBroadcaster() {
		return coppaMessageBroadcaster;
	}

	public void setCoppaMessageBroadcaster(
			CCTSMessageBroadcaster coppaMessageBroadcaster) {
		this.coppaMessageBroadcaster = coppaMessageBroadcaster;
	}

	public int getPaLimitValue() {
		return paLimitValue;
	}

	public void setPaLimitValue(int paLimitValue) {
		this.paLimitValue = paLimitValue;
	}

}
