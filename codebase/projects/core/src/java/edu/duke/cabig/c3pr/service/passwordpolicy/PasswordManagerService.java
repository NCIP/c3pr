/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.service.passwordpolicy;

import edu.duke.cabig.c3pr.exception.C3PRBaseException;


public interface PasswordManagerService {
    /**
     * Creates a token that can be used to set the password of the specified user. There is no
     * guarantee the token will be valid at the time of setting the password.
     * 
     * @return a token String to be used for setting the password of a user.
     * @throws CaaersSyystemException
     *                 if the user does not exist.
     */
    public void addUserToken(String userName) throws C3PRBaseException;

    /**
     * Set the password for the specified user.
     * 
     * @param token
     *                should have been generated using requestToken on the same user.
     * @throws CaaersSystemException
     *                 if the password could not be set for the user.
     */
    public void setPassword(String userName, String password, String token)
                    throws C3PRBaseException;
}
