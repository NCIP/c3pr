/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.repository;

import edu.duke.cabig.c3pr.domain.BaseOrganizationDataContainer;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;

public interface OrganizationRepository {
	
	public BaseOrganizationDataContainer convertLocalToRemote(LocalHealthcareSite localHealthcareSite,RemoteHealthcareSite remoteHealthcareSite);

}
