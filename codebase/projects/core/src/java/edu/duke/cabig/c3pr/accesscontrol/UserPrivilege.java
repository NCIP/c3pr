/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import edu.duke.cabig.c3pr.domain.Organization;

/**
 * The Class UserPrivilege.
 */
public class UserPrivilege {

	private String objectId;
	
	private String privilege;
	
	public static final String SEPARATOR = ":";
	
	
	public UserPrivilege(String objectId, String privilege) {
		super();
		this.objectId = objectId;
		this.privilege = privilege;
	}
	
	public UserPrivilege(String privilegeString) {
		super();
		String[] privilegeDetails = privilegeString.split(":");
		this.objectId = privilegeDetails[0];
		this.privilege = privilegeDetails[1];
	}

	/**
	 * Gets the granted privilege.
	 * This is used from the UI to check against the auth tags.
	 *
	 * @return the granted privilege
	 */
	public String getGrantedPrivilege(){
		return objectId + SEPARATOR + privilege;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getPrivilege() {
		return privilege;
	}
	
	@Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((objectId == null) ? 0 : objectId.hashCode()) + ((privilege == null) ? 0 : privilege.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final UserPrivilege other = (UserPrivilege) obj;
        if (objectId == null) {
            if (other.objectId != null) return false;
        }
        if (privilege == null) {
            if (other.privilege != null) return false;
        }
        else if (!objectId.equals(other.objectId) || !privilege.equals(other.privilege)) return false;
        return true;
    }
	
}
