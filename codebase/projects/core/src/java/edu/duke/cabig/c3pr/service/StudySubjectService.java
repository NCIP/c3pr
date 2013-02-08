/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.service;

import java.util.List;

import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * @author Kulasekaran,Ramakrishna
 * @version 1.0
 */
public interface StudySubjectService extends CCTSWorkflowService, MultiSiteWorkflowService {

    /**
     * Search using a sample. Populate a Participant object
     * 
     * @param StudySubject
     *                object
     * @return List of Participant objects based on the sample participant object
     * @throws Runtime
     *                 exception
     */
	
	public String getLocalNCIInstituteCode();

    public void setHostedMode(boolean hostedMode);

    public boolean requiresExternalApprovalForRegistration(StudySubject studySubject);

}
