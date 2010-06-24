package edu.duke.cabig.c3pr.security.vote;

import edu.duke.cabig.c3pr.accesscontrol.UserPrivilege;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;
import gov.nih.nci.security.acegi.csm.authorization.CSMPrivilegeGenerator;

import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.vote.AccessDecisionVoter;

public class C3PRPrivilegeBasedAccessDecisionVoter implements AccessDecisionVoter{

	private String processConfigAttribute;
	
	private CSMPrivilegeGenerator privilegeGenerator;
	
	private CSMObjectIdGenerator objectIdGenerator;
	
	public void setObjectIdGenerator(CSMObjectIdGenerator objectIdGenerator) {
		this.objectIdGenerator = objectIdGenerator;
	}

	public void setPrivilegeGenerator(CSMPrivilegeGenerator privilegeGenerator) {
		this.privilegeGenerator = privilegeGenerator;
	}

	public void setProcessConfigAttribute(String processConfigAttribute) {
		this.processConfigAttribute = processConfigAttribute;
	}

	public boolean supports(ConfigAttribute configattribute) {
		return configattribute.getAttribute().equals(processConfigAttribute);
	}

	public boolean supports(Class class1) {
		return true;
	}

	public int vote(Authentication authentication, Object object,
			ConfigAttributeDefinition configattributedefinition) {
		String resolvedPrivilege = privilegeGenerator.generatePrivilege(object);
		String resolvedObjectId = objectIdGenerator.generateId(object);
		if(!StringUtils.getBlankIfNull(resolvedPrivilege).equals("") && !StringUtils.getBlankIfNull(resolvedObjectId).equals("")){
			if(SecurityUtils.hasPrivilege(new UserPrivilege(resolvedObjectId , resolvedPrivilege))){
				return AccessDecisionVoter.ACCESS_GRANTED;
			}else{
				return AccessDecisionVoter.ACCESS_DENIED;
			}
		}
		return 0;
	}

}
