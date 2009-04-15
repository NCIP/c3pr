package edu.duke.cabig.c3pr.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.EndpointDao;
import edu.duke.cabig.c3pr.dao.GridIdentifiableDao;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.InteroperableAbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.WorkFlowStatusType;
import edu.duke.cabig.c3pr.domain.factory.EndPointFactory;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.esb.Metadata;
import edu.duke.cabig.c3pr.esb.OperationNameEnum;
import edu.duke.cabig.c3pr.esb.ServiceTypeEnum;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.CCTSWorkflowService;
import edu.duke.cabig.c3pr.service.MultiSiteWorkflowService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.DefaultCCTSMessageWorkflowCallbackFactory;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.xml.XMLTransformer;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Dec 6, 2007 Time: 4:01:40 PM To change this template
 * use File | Settings | File Templates.
 */
@Transactional
public abstract class WorkflowServiceImpl implements CCTSWorkflowService, MultiSiteWorkflowService {

    private Logger log = Logger.getLogger(CCTSWorkflowService.class);

    protected GridIdentifiableDao dao;
    
    protected EndpointDao endpointDao;

    private edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster messageBroadcaster;
    
    protected XmlMarshaller cctsXmlUtility;
    
    private DefaultCCTSMessageWorkflowCallbackFactory cctsMessageWorkflowCallbackFactory;

    protected C3PRExceptionHelper exceptionHelper;

    protected Configuration configuration;

    private MessageSource c3prErrorMessages;
    
    //private MessageBroadcastService jmsCoOrdinatingCenterBroadcaster;
    
    //private MessageBroadcastService jmsAffiliateSiteBroadcaster;
    
    private String cctsXSLTName;
    
    private XMLTransformer xmlTransformer; 
    
    private EndPointFactory endPointFactory;
    
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
                        .getByGridId(cctsObject.getGridId());
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
                //build metadata with operation name and the external Id and pass it to the broadcast method.
                Metadata mData = new Metadata(OperationNameEnum.NA.getName(), cctsObject.getGridId());
                messageBroadcaster.broadcast(transformedXML, mData);
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

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public EndPoint handleMultiSiteBroadcast(StudyOrganization studyOrganization, ServiceName multisiteServiceName, APIName multisiteAPIName, List domainObjects) {
        if(!this.canMultisiteBroadcast(studyOrganization)){
            throw exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.MULTISITE.NOT_HOSTED_MODE.CODE"));
        }
        EndPoint endPoint=endPointFactory.getEndPoint(multisiteServiceName, multisiteAPIName, studyOrganization);
        try {
            endPoint.invoke(domainObjects);
        }catch(Exception e){
        	e.printStackTrace();
        	log.error(e);
        }
        finally{
        	endpointDao.save(endPoint);
        	return endPoint;
        	
        }
    }
    
    public boolean canMultisiteBroadcast(StudyOrganization studyOrganization){
        return !studyOrganization.getHostedMode() && isMultisiteEnable();
    }
    
    public boolean isMultisiteEnable(){
        return this.configuration.get(Configuration.MULTISITE_ENABLE).equalsIgnoreCase("true");
    }
    
    public void setEndPointFactory(EndPointFactory endPointFactory) {
        this.endPointFactory = endPointFactory;
    }

	public void setEndpointDao(EndpointDao endpointDao) {
		this.endpointDao = endpointDao;
	}
    
}
