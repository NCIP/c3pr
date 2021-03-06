/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import org.acegisecurity.Authentication;

import edu.duke.cabig.c3pr.tools.Configuration;
import gov.nih.nci.cabig.ctms.acegi.csm.authorization.CSMAuthorizationCheck;

/**
 * User: ion
 * Date: Jul 3, 2008
 * Time: 3:14:12 PM
 */

public class LoginAuthorizationCheck implements CSMAuthorizationCheck {

	private Configuration configuration;
	
    public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public boolean checkAuthorization(Authentication authentication, String s, Object o) {
		if(configuration.get(Configuration.AUTHENTICATION_MODEL).equalsIgnoreCase("webSSO")){
			return true;
		}
		return (authentication != null);
    }

    public boolean checkAuthorizationForObjectId(Authentication authentication, String s, String s1) {
        return false;
    }

    public boolean checkAuthorizationForObjectIds(Authentication authentication, String s, String[] strings) {
        return false;  
    }
}

