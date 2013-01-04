/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.security;

import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.acegi.csm.authorization.CSMAuthorizationCheck;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import java.util.Map;

import org.acegisecurity.Authentication;
import org.springframework.beans.factory.annotation.Required;


public class StudyOrganizationTabAuthorizationCheck implements TabAuthroizationCheck{

	private Map<String, String> tabBasedPrivilegeMap;
	
	private Map<String, String> tabBasedScopeMap;
	
	private CSMAuthorizationCheck csmAuthorizationCheck;
	
	public boolean checkAuthorization(Authentication authentication, String privilege , Tab tab,
			Object domainObject, String scope) {
		if(StringUtils.getBlankIfNull(privilege).equals("")){
			privilege = UserPrivilegeType.getByCode(tabBasedPrivilegeMap.get(getKeyValue(tab))).name();
		}
		if(StringUtils.getBlankIfNull(scope).equals("")){
			scope = tabBasedScopeMap.get(getKeyValue(tab));
		}
		if(StringUtils.getBlankIfNull(scope).equals("")){
			throw new RuntimeException("Invalid scope.");
		}
		Study study = (Study) domainObject;
		if(scope.equals(COCENTER_SCOPED_AUTHRORIZATION_CHECK)){
			return csmAuthorizationCheck.checkAuthorization(authentication, privilege, study.getStudyCoordinatingCenter().getHealthcareSite());
		}else if(scope.equals(STUDYSITE_SCOPED_AUTHRORIZATION_CHECK)){
			if(csmAuthorizationCheck.checkAuthorization(authentication, privilege, study.getStudyCoordinatingCenter().getHealthcareSite())){
				return true;
			}
			for(StudySite studySite : study.getStudySites()){
				if(csmAuthorizationCheck.checkAuthorization(authentication, privilege, studySite.getHealthcareSite())){
					return true;
				}
			}
			return false;
		}else if(scope.equals(STUDYORGANIZATION_SCOPED_AUTHRORIZATION_CHECK)){
			for(StudyOrganization studyOrganization : study.getStudyOrganizations()){
				if(csmAuthorizationCheck.checkAuthorization(authentication, privilege, studyOrganization.getHealthcareSite())){
					return true;
				}
			}
			return false;
		}else{
			throw new RuntimeException("Invalid scope.");
		}
	}

	protected String getKeyValue(Tab tab) {
        return tab.getClass().getName();
    }
	
	public void setTabBasedScopeMap(Map<String, String> tabBasedScopeMap) {
		this.tabBasedScopeMap = tabBasedScopeMap;
	}

	@Required
	public void setCsmAuthorizationCheck(CSMAuthorizationCheck csmAuthorizationCheck) {
		this.csmAuthorizationCheck = csmAuthorizationCheck;
	}

	@Required
	public void setTabBasedPrivilegeMap(Map<String, String> tabBasedPrivilegeMap) {
		this.tabBasedPrivilegeMap = tabBasedPrivilegeMap;
	}
}

