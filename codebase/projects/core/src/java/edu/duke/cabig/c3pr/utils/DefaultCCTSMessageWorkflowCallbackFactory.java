package edu.duke.cabig.c3pr.utils;

import edu.duke.cabig.c3pr.dao.C3PRBaseDao;
import edu.duke.cabig.c3pr.domain.CCTSAbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.esb.MessageWorkflowCallback;
import org.apache.log4j.Logger;

/**
 * Will track the CCTS message worfklow for a "given" domain object
 * <p/>
 * Uses the factory pattern so that dao can be set at the factory
 * level and injected for each instance of the workflow callback tracker
 * <p/>
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 19, 2007
 * Time: 12:17:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultCCTSMessageWorkflowCallbackFactory {

    private C3PRBaseDao dao;
    private Logger log = Logger.getLogger(DefaultCCTSMessageWorkflowCallbackFactory.class);


    public MessageWorkflowCallback createWorkflowCallback(CCTSAbstractMutableDeletableDomainObject domainObject) {
        DefaultCCTSMessageWorkflowCallbackImpl callback = new DefaultCCTSMessageWorkflowCallbackImpl(domainObject);
        callback.setDao(dao);
        return callback;
    }


    public C3PRBaseDao getDao() {
        return dao;
    }

    public void setDao(C3PRBaseDao dao) {
        this.dao = dao;
    }


    private class DefaultCCTSMessageWorkflowCallbackImpl implements MessageWorkflowCallback {

        private C3PRBaseDao dao;
        private CCTSAbstractMutableDeletableDomainObject domainObject;

        private DefaultCCTSMessageWorkflowCallbackImpl(CCTSAbstractMutableDeletableDomainObject domainObject) {
            this.domainObject = domainObject;
        }

        /**
         * Handle message sent to ESB successfully
         *
         * @param objectId
         */
        public void messageSendSuccessful(String objectId) {
            log.debug("Recording successful send for objectId" + objectId);
            domainObject.setCctsWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_SEND);
            dao.save(domainObject);
        }

        public void messageSendFailed(String objectId) {
            log.debug("Recording send failed for objectId" + objectId);
            domainObject.setCctsWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_SEND_FAILED);
            dao.save(domainObject);
        }

        /**
         * Confirm that message was sent to CCTS Hub and confirmation
         * was received
         *
         * @param objectId id of the domain object
         */
        public void messageSendConfirmed(String objectId) {
            log.debug("Recording send confirmed for objectId" + objectId);
            domainObject.setCctsWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_SEND_CONFIRMED);
            dao.save(domainObject);
        }


        public C3PRBaseDao getDao() {
            return dao;
        }

        public void setDao(C3PRBaseDao dao) {
            this.dao = dao;
        }
    }
}
