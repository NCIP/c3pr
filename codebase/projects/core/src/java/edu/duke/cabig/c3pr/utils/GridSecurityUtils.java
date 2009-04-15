package edu.duke.cabig.c3pr.utils;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;


public class GridSecurityUtils {
	
	/** The user details service. */
	private UserDetailsService userDetailsService;
	
	/**
	 * Gets the user id from grid identity.
	 * 
	 * @param gridIdentity
	 *            the grid identity
	 * 
	 * @return the user id from grid identity
	 */
	public String getUserIdFromGridIdentity(String gridIdentity) {
		String[] sections = gridIdentity.split("=");
		return sections[sections.length - 1];
	}

	/**
	 * Load user authentication.
	 * 
	 * @param username
	 *            the username
	 * 
	 * @return the user
	 * 
	 * @throws RemoteException
	 *             the remote exception
	 */
	public User loadUserAuthentication(String username) throws RemoteException {
		UserDetails userDetails = userDetailsService
				.loadUserByUsername(username);
		if (userDetails == null) {
			throw new RemoteException(
					"Cannot authorize. No security credential found for "
							+ username);
		}
		User user = new User(userDetails.getUsername(), userDetails
				.getPassword(), true, true, true, true, userDetails
				.getAuthorities());
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				user, "password", user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);
		return user;
	}

	/**
	 * Gets the roles.
	 * 
	 * @param callerIdentity
	 *            the caller identity
	 * 
	 * @return the roles
	 * 
	 * @throws RemoteException
	 *             the remote exception
	 */
	public ArrayList<RoleTypes> getRoles(String callerIdentity)
			throws RemoteException {
		User user = loadUserAuthentication(getUserIdFromGridIdentity(callerIdentity));
		GrantedAuthority[] groups = user.getAuthorities();
		ArrayList<RoleTypes> roles = new ArrayList<RoleTypes>();
		for (GrantedAuthority ga : groups) {
			if (RoleTypes.getByCode(ga.getAuthority()) == null)
				throw new RemoteException("Could not recognize role: "
						+ ga.getAuthority());
			roles.add(RoleTypes.getByCode(ga.getAuthority()));
		}
		return roles;
	}

	/**
	 * Gets the roles as string.
	 * 
	 * @param roles
	 *            the roles
	 * 
	 * @return the roles as string
	 */
	public String getRolesAsString(ArrayList<RoleTypes> roles) {
		String rolesString = "{";
		for (RoleTypes role : roles) {
			rolesString += role.getDisplayName() + ",";
		}
		rolesString += "}";
		return rolesString;
	}
	
	/**
	 * Sets the user details service.
	 * 
	 * @param userDetailsService
	 *            the new user details service
	 */
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
}
