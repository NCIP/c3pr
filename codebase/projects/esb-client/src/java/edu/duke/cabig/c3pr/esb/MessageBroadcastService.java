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
     * @param edu.duke.cabig.c3pr.esb.Metadata localMetadata
     * @throws BroadcastException
     */
    public void broadcast(String message, edu.duke.cabig.c3pr.esb.Metadata localMetadata) throws BroadcastException;
    
    /**
     * Broadcast coppa message.
     * 
     * @param cctsDomainObjectXML the ccts domain object xml
     * @param localMetadata the local metadata
     * 
     * @return the string
     * 
     * @throws BroadcastException the broadcast exception
     */
    public String broadcastCoppaMessage(String cctsDomainObjectXML, edu.duke.cabig.c3pr.esb.Metadata localMetadata) throws BroadcastException;
}
