package edu.duke.cabig.c3pr.esb;

public interface MessageBroadcastService {

    public static final String DUMMY_ID = "dummy_grid_id";


    /**
     * Will just use a dummy id to broadcast message
     *
     * @param message
     * @throws BroadcastException
     */
    public void broadcast(String message) throws BroadcastException;

    /**
     * Send message to ESB with the externalID
     *
     * @param message
     * @param externalId
     * @throws BroadcastException
     */
    public void broadcast(String message, String externalId) throws BroadcastException;
}
