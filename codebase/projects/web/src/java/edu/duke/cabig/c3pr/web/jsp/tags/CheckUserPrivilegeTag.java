/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.jsp.tags;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.utils.SecurityUtils;

public class CheckUserPrivilegeTag extends TagSupport{

	private String hasPrivileges;
	
	@Override
	public int doStartTag() throws JspException {
		List<UserPrivilegeType> privileges = new ArrayList<UserPrivilegeType>();
		for(String privilegeString : hasPrivileges.split(",")){
			privileges.add(UserPrivilegeType.valueOf(privilegeString.trim()));
		}
		if(SecurityUtils.hasAnyPrivilege(privileges)){
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	public String getHasPrivileges() {
		return hasPrivileges;
	}

	public void setHasPrivileges(String hasPrivileges) {
		this.hasPrivileges = hasPrivileges;
	}

}
