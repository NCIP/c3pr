package edu.duke.cabig.c3pr.domain.repository;

import edu.duke.cabig.c3pr.domain.ConverterOrganization;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;

public interface OrganizationRepository {
	
	public ConverterOrganization convertLocalToRemote(LocalHealthcareSite localHealthcareSite,RemoteHealthcareSite remoteHealthcareSite);

}