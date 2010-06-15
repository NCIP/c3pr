package edu.duke.cabig.c3pr.accesscontrol;

import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.User;


/**
 * The Class AuthorizedUser.
 * This class populates the protectionGroupRoleContext from CSM so that the "filterers" can use it instead 
 * of going to CSM for every search and filter operation.
 * 
 * 
 * @author Vinay G
 */
public class AuthorizedUser extends User {

	private List<Object> protectionGroupRoleContextList;

    public AuthorizedUser(String string, String string1, boolean b, boolean b1, boolean b2, boolean b3,
                    GrantedAuthority[] grantedAuthorities, List<Object> protectionGroupRoleContextList) throws IllegalArgumentException {
        super(string, string1, b, b1, b2, b3, grantedAuthorities);
        
        setProtectionGroupRoleContextList(protectionGroupRoleContextList);
    }

//    public AuthorizedUser(UserDetails user) {
//        this(user.getUsername(), user.getPassword(), true, true, true, true, user.getAuthorities(), );
//    }

	public List<Object> getProtectionGroupRoleContextList() {
		return protectionGroupRoleContextList;
	}

	public void setProtectionGroupRoleContextList(
			List<Object> protectionGroupRoleContextList) {
		this.protectionGroupRoleContextList = protectionGroupRoleContextList;
	}

 }
