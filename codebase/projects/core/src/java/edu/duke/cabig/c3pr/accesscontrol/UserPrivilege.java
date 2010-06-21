package edu.duke.cabig.c3pr.accesscontrol;

/**
 * The Class UserPrivilege.
 */
public class UserPrivilege {

	private String objectId;
	
	private String privilege;
	
	public static final String SEPARATOR = ":";
	
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
	
	
}
