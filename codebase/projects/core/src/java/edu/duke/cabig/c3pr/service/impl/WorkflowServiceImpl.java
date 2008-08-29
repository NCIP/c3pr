package edu.duke.cabig.c3pr.service.impl;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.dao.GridIdentifiableDao;
import edu.duke.cabig.c3pr.domain.CCTSAbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.esb.MessageBroadcastService;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.CCTSWorkflowService;
import edu.duke.cabig.c3pr.service.MultiSiteWorkflowService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.DefaultCCTSMessageWorkflowCallbackFactory;
import edu.duke.cabig.c3pr.xml.XMLTransformer;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Dec 6, 2007 Time: 4:01:40 PM To change this template
 * use File | Settings | File Templates.
 */
public class WorkflowServiceImpl implements CCTSWorkflowService, MultiSiteWorkflowService {

    private Logger log = Logger.getLogger(CCTSWorkflowService.class);

    private GridIdentifiableDao dao;

    private edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster messageBroadcaster;

    protected XmlMarshaller cctsXmlUtility;
    
    protected XmlMarshaller multisiteXmlUtility;

    private DefaultCCTSMessageWorkflowCallbackFactory cctsMessageWorkflowCallbackFactory;

    private C3PRExceptionHelper exceptionHelper;

    private Configuration configuration;

    private MessageSource c3prErrorMessages;
    
    private MessageBroadcastService jmsCoOrdinatingCenterBroadcaster;
    
    private MessageBroadcastService jmsAffiliateSiteBroadcaster;
    
    private String cctsXSLTName;
    
    private XMLTransformer xmlTransformer; 

    public void setCctsXSLTName(String cctsXSLTName) {
        this.cctsXSLTName = cctsXSLTName;
    }

    public void setJmsCoOrdinatingCenterBroadcaster(
                    MessageBroadcastService jmsCoOrdinatingCenterBroadcaster) {
        this.jmsCoOrdinatingCenterBroadcaster = jmsCoOrdinatingCenterBroadcaster;
    }

    public void setJmsAffiliateSiteBroadcaster(MessageBroadcastService jmsAffiliateSiteBroadcaster) {
        this.jmsAffiliateSiteBroadcaster = jmsAffiliateSiteBroadcaster;
    }

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

    public CCTSWorkflowStatusType getCCTSWofkflowStatus(
                    CCTSAbstractMutableDeletableDomainObject cctsObject) {
        CCTSAbstractMutableDeletableDomainObject loadedCCTSObject = (CCTSAbstractMutableDeletableDomainObject) dao
                        .getByGridId(cctsObject);
        return loadedCCTSObject.getCctsWorkflowStatus();
    }

    public void broadcastMessage(CCTSAbstractMutableDeletableDomainObject cctsObject)
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
                messageBroadcaster.broadcast(xmlTransformer.transform(cctsXSLTName,xml), cctsObject.getGridId());
            }
            catch (BroadcastException e) {
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

    public CCTSWorkflowStatusType getMultiSiteWofkflowStatus(
                    CCTSAbstractMutableDeletableDomainObject cctsObject) {
        CCTSAbstractMutableDeletableDomainObject loadedCCTSObject = (CCTSAbstractMutableDeletableDomainObject) dao
        .getByGridId(cctsObject);
        return loadedCCTSObject.getMultisiteWorkflowStatus();
    }

    public void sendRegistrationRequest(StudySubject studySubject) {
        try {
            jmsCoOrdinatingCenterBroadcaster.broadcast(multisiteXmlUtility.toXML(studySubject));
            studySubject.setMultisiteWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_SEND_CONFIRMED);
        }
        catch (Exception e) {
            studySubject.setMultisiteWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_SEND_FAILED);
            throw new RuntimeException(e);
        }
    }

    public void sendRegistrationResponse(StudySubject studySubject) {
        if (getIsMultiSiteEnable().equalsIgnoreCase("true")) {
            try {
                jmsAffiliateSiteBroadcaster.broadcast(multisiteXmlUtility.toXML(studySubject));
                studySubject.setMultisiteWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_REPLY_CONFIRMED);
            }
            catch (Exception e) {
                studySubject.setMultisiteWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_REPLY_FAILED);
                throw new RuntimeException(e);
            }
        }else {
            throw new RuntimeException(this.exceptionHelper
            .getException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTISITE.DISABLED")));
        }
    }

    public void setMultisiteXmlUtility(XmlMarshaller multisiteXmlUtility) {
        this.multisiteXmlUtility = multisiteXmlUtility;
    }

    public void setCctsXmlUtility(XmlMarshaller cctsXmlUtility) {
        this.cctsXmlUtility = cctsXmlUtility;
    }
    
}
