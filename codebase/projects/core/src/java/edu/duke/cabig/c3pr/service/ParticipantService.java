/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service;

import java.util.List;

import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

/**
 * @author Kulasekaran,Ramakrishna
 * @version 1.0
 */
public interface ParticipantService {

    /**
     * Search using a sample. Populate a Participant object
     * 
     * @param Participant
     *                object
     * @return List of Participant objects based on the sample participant object
     * @throws Runtime
     *                 exception
     */
    public List<Participant> search(Participant participant) throws Exception;

    public List<Participant> searchByMRN(OrganizationAssignedIdentifier identifier)
                    throws C3PRCodedException;

}
