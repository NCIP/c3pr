package edu.duke.cabig.c3pr.utils;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.esb.MessageWorkflowCallback;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 19, 2007
 * Time: 12:40:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class CCTSMessageWorkflowCallbackTestCase extends MasqueradingDaoTestCase<StudySubjectDao> {

    private DefaultCCTSMessageWorkflowCallbackFactory cctsMessageWorkflowCallbackFactory;


    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
        cctsMessageWorkflowCallbackFactory = (DefaultCCTSMessageWorkflowCallbackFactory) getApplicationContext().getBean("cctsMessageWorkflowCallbackFactory");
    }

    public void testCallback() {
        for (StudySubject subject : getDao().getAll()) {
            MessageWorkflowCallback callback = cctsMessageWorkflowCallbackFactory.createWorkflowCallback(subject);
            callback.messageSendSuccessful("success");

            StudySubject reloaded = getDao().getById(subject.getId());
            reloaded.getCctsWorkflowStatus().equals(CCTSWorkflowStatusType.MESSAGE_SEND);

            callback.messageSendConfirmed("confirmed");
            reloaded.getCctsWorkflowStatus().equals(CCTSWorkflowStatusType.MESSAGE_SEND_CONFIRMED);
        }

    }

    /**
     * What dao class is the test trying to Masquerade
     *
     * @return
     */
    public Class<StudySubjectDao> getMasqueradingDaoClassName() {
        return StudySubjectDao.class;
    }


}
