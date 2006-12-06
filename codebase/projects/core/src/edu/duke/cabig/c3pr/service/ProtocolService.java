package edu.duke.cabig.c3pr.service;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Amendment;
import edu.duke.cabig.c3pr.domain.Protocol;

/**
 * @author priyatam
 * @version 1.0
 */
public interface ProtocolService 
{

    public List<Protocol> getAllProtocols() throws Exception;	
	
    public void saveProtocol(Protocol protocol) throws Exception;

	/**
	 * Adds a new protocol in the data store.  This method also executes the 
	 * business logic workflow associated with adding a new protocol.
	 * 
	 * @throws Exception
	 */
    public void addProtocol(Protocol protocol) throws Exception;

   

    /**
	 * Ammends a protocol in the data store.  This method also executes the 
	 * business logic workflow associated with ammending a protocol.
	 * 
	 * @throws Exception
	 */
	public void amendProtocol(Amendment amendment ) throws Exception;

}

