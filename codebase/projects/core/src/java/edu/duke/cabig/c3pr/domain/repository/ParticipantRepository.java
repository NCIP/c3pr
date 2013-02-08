/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.repository;

import java.util.List;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;

public interface ParticipantRepository {
    public Participant getUniqueParticipant(List<Identifier> identifiers);
    
    public void save(Participant participant);
    
    public Participant merge(Participant participant);
    
    public List<Participant> searchByIdentifier(Identifier identifier);
    
    /**
     * This search does not include all associated entities of the {@link Participant}, such as {@link Address} or {@link ContactMechanism}.
     * Identifiers are included, if present.
     * @see #searchByFullExample(Participant)
     * @param participant
     * @return
     */
    public List<Participant> searchByExample(Participant participant);
    
    /**
     * This search takes {@link Address} and {@link ContactMechanism} into account, if set.
     * @param participant
     * @return
     */
    public List<Participant> searchByFullExample(Participant participant);
    
	/**
	 * Search using advanced query builder.
	 * 
	 * @param searchParameters
	 * @return
	 */
	public List<Participant> search(List<AdvancedSearchCriteriaParameter> searchParameters);
	
	/**TODO: Uncomment after implementing participantDao.searchByPrimaryIdentifier()
	 * Search by primary identifier.
	 *
	 * @param identifier the identifier
	 * @return the participant
	public Participant searchByPrimaryIdentifier(Identifier identifier);
    */
}
