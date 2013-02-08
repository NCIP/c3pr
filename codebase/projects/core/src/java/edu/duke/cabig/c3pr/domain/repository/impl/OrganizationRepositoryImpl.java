/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.repository.impl;

import edu.duke.cabig.c3pr.dao.BaseOrganizationDataContainerDao;
import edu.duke.cabig.c3pr.domain.BaseOrganizationDataContainer;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.repository.OrganizationRepository;

public class OrganizationRepositoryImpl implements OrganizationRepository{
	
	private BaseOrganizationDataContainerDao baseOrganizationDataContainerDao;

	public BaseOrganizationDataContainerDao getBaseOrganizationDataContainerDao() {
		return baseOrganizationDataContainerDao;
	}

	public void setBaseOrganizationDataContainerDao(
			BaseOrganizationDataContainerDao baseOrganizationDataContainerDao) {
		this.baseOrganizationDataContainerDao = baseOrganizationDataContainerDao;
	}

	public BaseOrganizationDataContainer convertLocalToRemote(
			LocalHealthcareSite localHealthcareSite,RemoteHealthcareSite remoteHealthcareSite) {
		BaseOrganizationDataContainer baseOrganizationDataContainer = baseOrganizationDataContainerDao.getById(localHealthcareSite.getId());
		baseOrganizationDataContainer.setDtype("Remote");
		baseOrganizationDataContainer.setName(remoteHealthcareSite.getName());
		baseOrganizationDataContainer.setDescriptionText(remoteHealthcareSite.getDescriptionText());
		baseOrganizationDataContainer.setExternalId(remoteHealthcareSite.getExternalId());
		baseOrganizationDataContainerDao.save(baseOrganizationDataContainer);
		return baseOrganizationDataContainer;
	}

}
