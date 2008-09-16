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
     * @param objectId id of the domain object (external Identifier)
     */
    public void messageSendSuccessful(String objectId);

    /**
     * Message failed
     *
     * @param objectId id of the domain object (external Identifier)
     */
    public void messageSendFailed(String objectId);

    /**
     * Confirm that message was sent to CCTS Hub and confirmation
     * was received
     *
     * @param objectId id of the domain object (external Identifier)
     */
    public void messageSendConfirmed(String objectId);
    
    /**
     * Records any error if received
     *
     * @param objectId id of the domain object (external Identifier)
     */
    public void recordError(String objectId, ResponseErrors errors);
}
