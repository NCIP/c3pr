package edu.duke.cabig.c3pr.domain.repository.impl;

import edu.duke.cabig.c3pr.dao.OrganizationConverterDao;
import edu.duke.cabig.c3pr.domain.ConverterOrganization;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.repository.OrganizationRepository;

public class OrganizationRepositoryImpl implements OrganizationRepository{
	
	private OrganizationConverterDao organizationConverterDao;

	public OrganizationConverterDao getOrganizationConverterDao() {
		return organizationConverterDao;
	}

	public void setOrganizationConverterDao(
			OrganizationConverterDao organizationConverterDao) {
		this.organizationConverterDao = organizationConverterDao;
	}

	public ConverterOrganization convertLocalToRemote(
			LocalHealthcareSite localHealthcareSite,RemoteHealthcareSite remoteHealthcareSite) {
		ConverterOrganization converterOganization = organizationConverterDao.getById(localHealthcareSite.getId());
		converterOganization.setDtype("Remote");
		converterOganization.setName(remoteHealthcareSite.getName());
		converterOganization.setDescriptionText(remoteHealthcareSite.getDescriptionText());
		converterOganization.setExternalId(remoteHealthcareSite.getExternalId());
		organizationConverterDao.save(converterOganization);
		return converterOganization;
	}

}
