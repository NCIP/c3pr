package edu.duke.cabig.c3pr.accesscontrol;

import org.acegisecurity.Authentication;

public interface DomainObjectSecurityFilterer {
	Object filter(Authentication authentication, String permission,
			Filterer returnObject);
}
