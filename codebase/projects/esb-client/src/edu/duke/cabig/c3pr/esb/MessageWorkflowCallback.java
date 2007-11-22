package edu.duke.cabig.c3pr.esb;

/**
 * Callback methods for tracking message through
 * the ESB
 * <p/>
 * <p/>
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 15, 2007
 * Time: 11:28:07 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MessageWorkflowCallback {

    /**
     * Handle message sent to ESB successfully
     *
     * @param message
     */
    public void messageSendSuccessful(String message);

    public void messageSendFailed(String message);

    /**
     * Confirm that message was sent to CCTS Hub and confirmation
     * was received
     *
     * @param objectIdentifier id of the domain object
     */
    public void messageSendConfirmed(String objectIdentifier);
}
