
package edu.duke.cabig.c3pr.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.domain.Amendment;
import edu.duke.cabig.c3pr.domain.Institution;
import edu.duke.cabig.c3pr.domain.Protocol;
import edu.duke.cabig.c3pr.domain.ProtocolArm;
import edu.duke.cabig.c3pr.domain.ProtocolInstitution;
import edu.duke.cabig.c3pr.domain.ProtocolParticipantRole;
import edu.duke.cabig.c3pr.domain.ProtocolRole;
import edu.duke.cabig.c3pr.domain.ProtocolStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.UserCredentials;
import edu.duke.cabig.c3pr.dto.ProtocolSearchCriteria;


/**
 * @author Priyatam
 *
 *  Interface defining methods by which the C3PR Application will access the data store.  All data
 *  pertaiing to the Protocol data aggregate should be accessed via an implementation of this
 *  interface.
 *
 *  These methods throw org.springframework.dao.DataAccessException which is a Run-time Exception
 *  that should be handled in one of the calling classes.  See the Spring Framework API for a
 *  hierarchy of the DataAccessException.

 */
public interface ProtocolDao extends BaseDao
{	
	/**
	 * saveProtocol:  Add new protocol to the data store.
	 * @param cmprotocol
	 * @throws Exception
	 * 
	 */
	public void saveProtocol(Protocol protocol) throws Exception;
	/**
	 * amendProtocol : Amend protocol. Add/Update Amendments to the protocol.
	 * Note: The Amendment object is expected to have a Protocol object with Protocol ID populated in it. 
	 * This is to identify the protocol which is being amended.
	 * @param cmprotocol
	 * @throws Exception
	 * 
	 */
	public void amendProtocol(Amendment amendment) throws Exception;
		
	/**
	 * Updates a protoocol and it's aggregrate data in the data store.  Any data that has changed will
	 * be updated.
	 * 
	 * @param protocol
	 * @throws DataAccessException
	 */
	public void updateProtocol(Protocol protocol) throws Exception;
	
	
	/**
	 * Retrieves a set of Protocols that match the search criteria 
	 * 
	 * @param sc
	 * @return jav.util.List of Protocol objects
	 * @throws DataAccessException
	 */
	public List<Protocol> retreiveProtocols(ProtocolSearchCriteria sc) throws Exception;
	
	
	/**
	 * Retrieves a Protocol object whose primary id is passed in.
	 * @param protocolId
	 * @return Protocol object
	 */
	public Protocol retrieveProtocol(Integer protocolId);
	
	/**
	 * Retreieves a collection of all the protocols available for registration. 
	 * 
	 * Business rule for creating a list of valid protocols for registration is as follows
	 * 1) Protocol should have current status as open
	 * 2) Protocol should have been approved by IRB in past
	 * 3) Current amendment of protocol should not require eligibility criteria check list
	 * 		OR
	 * 	  if Current amendment of protocol required EC check list then check list should be present and approved 
	 * 
	 * @return
	 */
	public Collection<Protocol> retrieveProtocolsAvailableForRegistration(String protocolFilter);

	
	
	/**
	 * Retrieves a collection of Institution for a particular protocol based on the protocol identifier
	 * 
	 * @param protocol
	 * @return
	 */
	public Collection<Institution> retrieveInstitutionForProtocol(Protocol protocol);
	
	/**
	 * It is a variation of retrieveProtocolsAvailableForRegistration() method which further restricts the resule based on the institution
	 * 
	 * @param institution
	 * @return
	 */
	public Collection<Protocol> retrieveProtocolsAvailableForRegistration(Institution institution, String protocolFilter);

	/**
	 * Retrieves an Amendment object whose amendment id is passed in.
	 * @param protocolId
	 * @return Collection of Amendment objects
	 */
	public Amendment retrieveAmendment(Integer amendmentId);
	
	/**
	 * Retrieves a collection of Amendment objects whose protocol id is passed in.
	 * @param protocolId
	 * @return Collection of Amendment objects
	 */
	public Collection<Amendment> retrieveAmendments(Integer protocolId);
	
	/**
	 * retrieves Collection of ProtocolStatus 
	 * @param protocolId
	 * @return Collection<ProtocolStatus>
	 */
	public Collection<ProtocolStatus> retrieveProtocolStatus(Integer protocolId);
	
	
	/**
	 * retrieves Collection of ProtocolInstitution
	 * @param intProtocolId
	 * @return
	 */
	public Collection<ProtocolInstitution> retrieveProtocolInstitution(Integer intProtocolId);
	
	
	/**
	 * Retrieves institution based on the primary id
	 * @param primaryId
	 * @return
	 */
	public Institution retrieveInstitution(String primaryId);
	
	
	/**
	 * Retrieves a ProtocolArm object based on the primary Id
	 * @param primaryId
	 * @return
	 */
	public ProtocolArm retrieveProtocolArm(Integer primaryId);
	
	/**
	 * Retrieves all associatable Protocol Roles.
	 * @return
	 */
	public List<ProtocolRole> retrieveAssociatableProtocolRole();
	
	

	/**
	 * Retrieves UserCredentials for External System based on a primary Id
	 * @param primaryId
	 * @return
	 */
	public UserCredentials retrieveUserCredentials(Integer primaryId);
	
	/**
	 * Retrieves all UserCredentials.
	 * @return
	 */
	public List<UserCredentials> retrieveUserCredentialsByUser(String c3prUser);
	

	/**
	 * Saves a UserCRetrieves all associatable Protocol Roles.
	 * @return
	 */
	public void saveUserCredentials(UserCredentials userCredentials);
	
	/**
	 * Retrieves all ProtocolParticipantRole objects for the given participant id.
	 * @return
	 */	
	public Collection<ProtocolParticipantRole> retrieveProtocolParticipantRole(Integer participantId);
	
	public List<Protocol> getAll() throws Exception;
}
