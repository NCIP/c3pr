/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import org.acegisecurity.Authentication;

public interface DomainObjectSecurityFilterer {
	Object filter(Authentication authentication, String permission,
			Filterer returnObject);
}
