/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service;



/**
 * Interface for Services on Study related domain object
 * 
 * @author priyatam
 */
public interface StudyService extends CCTSWorkflowService{
    public String getLocalNCIInstituteCode();
    
    public boolean isStudyOrganizationLocal(String nciInstituteCode);
    
    //public <T extends AbstractMutableDomainObject> void handleMultiSiteBroadcast(List<StudyOrganization> studyOrganizations, ServiceName multisiteServiceName, APIName multisiteAPIName, List<T> domainObjects);
}
