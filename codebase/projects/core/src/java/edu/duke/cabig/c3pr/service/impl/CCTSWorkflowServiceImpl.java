package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.dao.GridIdentifiableDao;
import edu.duke.cabig.c3pr.domain.CCTSAbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.CCTSWorkflowService;
import edu.duke.cabig.c3pr.utils.DefaultCCTSMessageWorkflowCallbackFactory;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Dec 6, 2007
 * Time: 4:01:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class CCTSWorkflowServiceImpl implements CCTSWorkflowService {

    private Logger log = Logger.getLogger(CCTSWorkflowService.class);

    private GridIdentifiableDao dao;
    private boolean broadcastEnable;
    private edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster messageBroadcaster;
    private XmlMarshaller xmlUtility;
    private DefaultCCTSMessageWorkflowCallbackFactory cctsMessageWorkflowCallbackFactory;

    public CCTSWorkflowStatusType getCCTSWofkflowStatus(CCTSAbstractMutableDeletableDomainObject cctsObject) {
        CCTSAbstractMutableDeletableDomainObject loadedCCTSObject = (CCTSAbstractMutableDeletableDomainObject) dao.getByGridId(cctsObject);
        return loadedCCTSObject.getCctsWorkflowStatus();
    }

    public void broadcastMessage(CCTSAbstractMutableDeletableDomainObject cctsObject) throws C3PRBaseException {
        if (broadcastEnable) {
            messageBroadcaster.setNotificationHandler(cctsMessageWorkflowCallbackFactory.createWorkflowCallback(cctsObject));
            try {
                messageBroadcaster.broadcast(xmlUtility.toXML(cctsObject), cctsObject.getGridId());
            } catch (Exception e) {
                log.error("Could not send message to ESB", e);
                throw new C3PRBaseException("Could not send message to ESB");
            }
        } else {
            throw new C3PRBaseException("ESB Broadcast is turned off");
        }
    }


    public GridIdentifiableDao getDao() {
        return dao;
    }

    public void setDao(GridIdentifiableDao dao) {
        this.dao = dao;
    }

    public boolean isBroadcastEnable() {
        return broadcastEnable;
    }

    public void setBroadcastEnable(boolean broadcastEnable) {
        this.broadcastEnable = broadcastEnable;
    }

    public CCTSMessageBroadcaster getMessageBroadcaster() {
        return messageBroadcaster;
    }

    public void setMessageBroadcaster(CCTSMessageBroadcaster messageBroadcaster) {
        this.messageBroadcaster = messageBroadcaster;
    }

    public XmlMarshaller getXmlUtility() {
        return xmlUtility;
    }

    public void setXmlUtility(XmlMarshaller xmlUtility) {
        this.xmlUtility = xmlUtility;
    }

    public DefaultCCTSMessageWorkflowCallbackFactory getCctsMessageWorkflowCallbackFactory() {
        return cctsMessageWorkflowCallbackFactory;
    }

    public void setCctsMessageWorkflowCallbackFactory(DefaultCCTSMessageWorkflowCallbackFactory cctsMessageWorkflowCallbackFactory) {
        this.cctsMessageWorkflowCallbackFactory = cctsMessageWorkflowCallbackFactory;
    }
}
