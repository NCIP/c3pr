/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.service.passwordpolicy;

import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;

public interface PasswordPolicyService {
    /**
     * This method will return the stored password poicy from xml configuration file
     */
    public PasswordPolicy getPasswordPolicy();

    /**
     * This method serializes the PasswordPolicy Object to xml file and updates any cached
     * PasswordPolicy Object
     */
    public void setPasswordPolicy(PasswordPolicy passwordPolicy);

    /**
     * This method will return a string in a readble format.
     */
    public String publishPasswordPolicy();

    /**
     * This method will apply to xslt to the password policy xml file and return in the desired
     * format. This can be very useful when publishing the password policy on web pages for users
     */
    public String publishPasswordPolicy(String xsltFileName);

    public boolean validatePasswordAgainstCreationPolicy(User user, String password) throws C3PRBaseException;
}
