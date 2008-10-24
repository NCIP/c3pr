package edu.duke.cabig.c3pr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.dao.GridIdentifiableDao;
import edu.duke.cabig.c3pr.domain.InteroperableAbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.WorkFlowStatusType;
import edu.duke.cabig.c3pr.domain.EndPointConnectionProperty;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.esb.MessageBroadcastService;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.CCTSWorkflowService;
import edu.duke.cabig.c3pr.service.MultiSiteHandlerService;
import edu.duke.cabig.c3pr.service.MultiSiteWorkflowService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.DefaultCCTSMessageWorkflowCallbackFactory;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.xml.XMLTransformer;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Dec 6, 2007 Time: 4:01:40 PM To change this template
 * use File | Settings | File Templates.
 */
public abstract class WorkflowServiceImpl implements CCTSWorkflowService, MultiSiteWorkflowService {

    private Logger log = Logger.getLogger(CCTSWorkflowService.class);

    private GridIdentifiableDao dao;

    private edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster messageBroadcaster;

    protected XmlMarshaller cctsXmlUtility;
    
    private DefaultCCTSMessageWorkflowCallbackFactory cctsMessageWorkflowCallbackFactory;

    private C3PRExceptionHelper exceptionHelper;

    private Configuration configuration;

    private MessageSource c3prErrorMessages;
    
    //private MessageBroadcastService jmsCoOrdinatingCenterBroadcaster;
    
    //private MessageBroadcastService jmsAffiliateSiteBroadcaster;
    
    private String cctsXSLTName;
    
    private XMLTransformer xmlTransformer; 
    
    private MultiSiteHandlerService multiSiteHandlerService;

    public void setMultiSiteHandlerService(MultiSiteHandlerService multiSiteHandlerService) {
        this.multiSiteHandlerService = multiSiteHandlerService;
    }

    public void setCctsXSLTName(String cctsXSLTName) {
        this.cctsXSLTName = cctsXSLTName;
    }
//
//    public void setJmsCoOrdinatingCenterBroadcaster(
//                    MessageBroadcastService jmsCoOrdinatingCenterBroadcaster) {
//        this.jmsCoOrdinatingCenterBroadcaster = jmsCoOrdinatingCenterBroadcaster;
//    }
//
//    public void setJmsAffiliateSiteBroadcaster(MessageBroadcastService jmsAffiliateSiteBroadcaster) {
//        this.jmsAffiliateSiteBroadcaster = jmsAffiliateSiteBroadcaster;
//    }

    public MessageSource getC3prErrorMessages() {
        return c3prErrorMessages;
    }

    public void setC3prErrorMessages(MessageSource errorMessages) {
        c3prErrorMessages = errorMessages;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
        this.exceptionHelper = exceptionHelper;
    }

    public WorkFlowStatusType getCCTSWofkflowStatus(
                    InteroperableAbstractMutableDeletableDomainObject cctsObject) {
        InteroperableAbstractMutableDeletableDomainObject loadedCCTSObject = (InteroperableAbstractMutableDeletableDomainObject) dao
                        .getByGridId(cctsObject);
        return loadedCCTSObject.getCctsWorkflowStatus();
    }

    public void broadcastMessage(InteroperableAbstractMutableDeletableDomainObject cctsObject)
                    throws C3PRCodedException {
        if (getIsBroadcastEnable().equalsIgnoreCase("true")) {
            messageBroadcaster.setNotificationHandler(cctsMessageWorkflowCallbackFactory
                            .createWorkflowCallback(cctsObject));
            String xml = "";
            try {
                xml = cctsXmlUtility.toXML(cctsObject);
            }
            catch (XMLUtilityException e) {
                e.printStackTrace();
                throw this.exceptionHelper.getException(
                                getCode("C3PR.EXCEPTION.REGISTRATION.BROADCAST.XML_ERROR"), e);
            }
            if (log.isDebugEnabled()) {
                log.debug(" - XML for Registration"); //$NON-NLS-1$
            }
            if (log.isDebugEnabled()) {
                log.debug(" - " + xml); //$NON-NLS-1$
            }
            try {
                // messageBroadcaster.initialize();
                String transformedXML=xmlTransformer.transform(StringUtils.readFile(cctsXSLTName),xml);
                log.debug("Transformed Message-----------"+transformedXML);
                messageBroadcaster.broadcast(transformedXML, cctsObject.getGridId());
            }
            catch (Exception e) {
                e.printStackTrace();
                throw this.exceptionHelper.getException(
                                getCode("C3PR.EXCEPTION.REGISTRATION.BROADCAST.SEND_ERROR"), e);
            }
        }
        else {
            throw this.exceptionHelper
                            .getException(getCode("C3PR.EXCEPTION.REGISTRATION.BROADCAST.DISABLED"));
        }
    }

    public GridIdentifiableDao getDao() {
        return dao;
    }

    public void setDao(GridIdentifiableDao dao) {
        this.dao = dao;
    }

    public CCTSMessageBroadcaster getMessageBroadcaster() {
        return messageBroadcaster;
    }

    public void setMessageBroadcaster(CCTSMessageBroadcaster messageBroadcaster) {
        this.messageBroadcaster = messageBroadcaster;
    }

    public DefaultCCTSMessageWorkflowCallbackFactory getCctsMessageWorkflowCallbackFactory() {
        return cctsMessageWorkflowCallbackFactory;
    }

    public void setCctsMessageWorkflowCallbackFactory(
                    DefaultCCTSMessageWorkflowCallbackFactory cctsMessageWorkflowCallbackFactory) {
        this.cctsMessageWorkflowCallbackFactory = cctsMessageWorkflowCallbackFactory;
    }

    public String getIsBroadcastEnable() {
        return this.configuration.get(Configuration.ESB_ENABLE);
    }

    public String getIsMultiSiteEnable() {
        return this.configuration.get(Configuration.MULTISITE_ENABLE);
    }
    
    public int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }

    public C3PRExceptionHelper getExceptionHelper() {
        return exceptionHelper;
    }

//    public CCTSWorkflowStatusType getMultiSiteWofkflowStatus(
//                    InteroperableAbstractMutableDeletableDomainObject cctsObject) {
//        InteroperableAbstractMutableDeletableDomainObject loadedCCTSObject = (InteroperableAbstractMutableDeletableDomainObject) dao
//        .getByGridId(cctsObject);
//        return loadedCCTSObject.getMultisiteWorkflowStatus();
//    }
//
//    public void sendRegistrationRequest(StudySubject studySubject) {
//        try {
//            jmsCoOrdinatingCenterBroadcaster.broadcast(multisiteXmlUtility.toXML(studySubject));
//            studySubject.setMultisiteWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_SEND_CONFIRMED);
//        }
//        catch (Exception e) {
//            studySubject.setMultisiteWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_SEND_FAILED);
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void sendRegistrationResponse(StudySubject studySubject) {
//        if (getIsMultiSiteEnable().equalsIgnoreCase("true")) {
//            try {
//                jmsAffiliateSiteBroadcaster.broadcast(multisiteXmlUtility.toXML(studySubject));
//                studySubject.setMultisiteWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_REPLY_CONFIRMED);
//            }
//            catch (Exception e) {
//                studySubject.setMultisiteWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_REPLY_FAILED);
//                throw new RuntimeException(e);
//            }
//        }else {
//            throw new RuntimeException(this.exceptionHelper
//            .getException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTISITE.DISABLED")));
//        }
//    }

    public void setCctsXmlUtility(XmlMarshaller cctsXmlUtility) {
        this.cctsXmlUtility = cctsXmlUtility;
    }

    public void setXmlTransformer(XMLTransformer xmlTransformer) {
        this.xmlTransformer = xmlTransformer;
    }

    public void handleMultiSiteBroadcast(List studyOrganizations, ServiceName multisiteServiceName, APIName multisiteAPIName, List domainObjects) {
        if(studyOrganizations.size()==0){
            log.error("There are no study organizations to bradcast to.");
        }
        boolean errorBroadcast=false;
        for(int i=0 ; i<studyOrganizations.size() ; i++){
            StudyOrganization studyOrganization=(StudyOrganization)studyOrganizations.get(i);
            try {
                EndPointConnectionProperty endPointProperty=multisiteServiceName==ServiceName.STUDY?studyOrganization.getHealthcareSite().getStudyEndPointProperty():studyOrganization.getHealthcareSite().getRegistrationEndPointProperty();
                multiSiteHandlerService.handle(multisiteServiceName, multisiteAPIName, endPointProperty, domainObjects);
                studyOrganization.setMultisiteWorkflowStatus(WorkFlowStatusType.MESSAGE_SEND_CONFIRMED);
            }
            catch (C3PRCodedException e) {
                errorBroadcast=true;
                log.error("Error bradcasting the message to "+studyOrganization.getHealthcareSite().getName(), e);
                studyOrganization.setMultisiteWorkflowStatus(WorkFlowStatusType.MESSAGE_SEND_FAILED);
            }
        }
        if(multisiteServiceName==ServiceName.STUDY){
            Study study=((StudyOrganization)studyOrganizations.get(0)).getStudy();
            if(errorBroadcast){
                study.setMultisiteWorkflowStatus(WorkFlowStatusType.MESSAGE_SEND_FAILED);
                //study.setMultisiteErrorString("Error Broadcasting.");
            }
            else{
                study.setMultisiteWorkflowStatus(WorkFlowStatusType.MESSAGE_SEND_CONFIRMED);
                //study.setMultisiteErrorString("");
            }
            dao.merge(study);
        }
        
    }
    
    public void handleAffiliateSiteBroadcast(String nciInstituteCode, Study study, APIName multisiteAPIName, List domainObjects){
        List<AbstractMutableDomainObject> studyOrganizations=new ArrayList<AbstractMutableDomainObject>();
        studyOrganizations.add(study.getStudySite(nciInstituteCode));
        handleMultiSiteBroadcast(studyOrganizations, getMultisiteServiceName(), multisiteAPIName, domainObjects);
    }
    
    public void handleAffiliateSitesBroadcast(Study study, APIName multisiteAPIName, List domainObjects){
        handleMultiSiteBroadcast(study.getAffiliateStudySites(), getMultisiteServiceName(), multisiteAPIName, domainObjects);
    }
    
    public void handleCoordinatingCenterBroadcast(Study study, APIName multisiteAPIName, List domainObjects){
        handleMultiSiteBroadcast(study.getStudyCoordinatingCenters(), getMultisiteServiceName(), multisiteAPIName, domainObjects);
    }
    
    public abstract ServiceName getMultisiteServiceName();
}
