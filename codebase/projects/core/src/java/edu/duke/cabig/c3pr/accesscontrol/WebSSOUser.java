/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import org.acegisecurity.GrantedAuthority;
import org.globus.gsi.GlobusCredential;

import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.RolePrivilege;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Dec 11, 2007 Time: 9:58:53 PM To change this template
 * use File | Settings | File Templates.
 */
public class WebSSOUser extends AuthorizedUser {

    private String gridId;

    private String delegatedEPR;

    private String firstName;

    private String lastName;

    private GlobusCredential gridCredential;

    public WebSSOUser(String string, String string1, boolean b, boolean b1, boolean b2, boolean b3,
                    GrantedAuthority[] grantedAuthorities, ProvisioningSession provisioningSession, RolePrivilege rolePrivileges[], PersonUser researchStaff) throws IllegalArgumentException {
        super(string, string1, b, b1, b2, b3, grantedAuthorities, provisioningSession, rolePrivileges, researchStaff);
    }

    public WebSSOUser(AuthorizedUser user) {
        this(user.getUsername(), user.getPassword(), true, true, true, true, user.getAuthorities(), 
        		user.getProvisioningSession(), user.getRolePrivileges(), user.getPersonUser());
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }

    public String getDelegatedEPR() {
        return delegatedEPR;
    }

    public void setDelegatedEPR(String delegatedEPR) {
        this.delegatedEPR = delegatedEPR;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GlobusCredential getGridCredential() {
        return gridCredential;
    }

    public void setGridCredential(GlobusCredential gridCredential) {
        this.gridCredential = gridCredential;
    }
}
