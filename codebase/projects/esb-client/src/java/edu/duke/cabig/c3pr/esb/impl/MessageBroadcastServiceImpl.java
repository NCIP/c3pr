/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.esb.impl;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.JmsService;
import edu.duke.cabig.c3pr.esb.MessageBroadcastService;
import edu.duke.cabig.c3pr.esb.MessageResponseRetreiverService;
import edu.duke.cabig.c3pr.esb.Metadata;


public class MessageBroadcastServiceImpl extends JmsService implements MessageBroadcastService, MessageResponseRetreiverService {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(MessageBroadcastServiceImpl.class);

    public void broadcast(String message) throws BroadcastException {
        if (!isProvider()) {
            if (logger.isDebugEnabled()) {
                logger.debug("broadcast(String) - no send queue provided ");
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("broadcast(String) - calling sendJms method...");
            }
            sendJms(message);
        }
    }


    public void broadcast(String message, String externalId) throws BroadcastException {
        broadcast(message, DUMMY_ID);
    }
    
    public void broadcast(String message, Metadata localMetadata) throws BroadcastException {
        broadcast(message, DUMMY_ID);
    }

    public Vector getResponses() {
        if (!isConsumer()) {
            if (logger.isDebugEnabled()) {
                logger.debug("getBroadcastStatus() - no recieve queue provided ");
            }
        }
        return messages;
    }

    public String broadcastCoppaMessage(String cctsDomainObjectXML, Metadata localMetadata) throws BroadcastException {
    	// TODO Auto-generated method stub
    	return null;
    }


	public String broadcastCoppaMessage(List<String> cctsDomainObjectXMLList, Metadata localMetadata) throws BroadcastException {
		// TODO Auto-generated method stub
		return null;
	}
}
