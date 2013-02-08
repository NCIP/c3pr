/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils.web;

import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.dao.GridIdentifiableDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;

/**
 * @author Priyatam
 */
public class ControllerTools {

	public static final String ASSIGNED_BY_PARAM_NAME="assignedBy";
	public static final String ASSIGNED_BY_ORG_VALUE="organization";
	public static final String ASSIGNED_BY_SYS_VALUE="system";
	public static final String SYSYEM_NAME_PARAM_NAME="systemName";
	public static final String ORG_NCI_PARAM_NAME="organizationNciId";
	public static final String IDENTIFIER_VALUE_PARAM_NAME="identifier";
	public static final String IDENTIFIER_TYPE_PARAM_NAME="identifierType";
	
    public static PropertyEditor getDateEditor(boolean required) {
        // note that date formats are not threadsafe, so we have to create a new one each time
        return new CustomDateEditor(createDateFormat(), !required);
    }

    public static DateFormat createDateFormat() {
        return new SimpleDateFormat("MM/dd/yyyy");
    }

    public static void registerDomainObjectEditor(ServletRequestDataBinder binder, String field,
                    GridIdentifiableDao dao) {
        binder.registerCustomEditor(dao.domainClass(), field, new CustomDaoEditor(dao));
    }

    public static void registerDomainObjectEditor(ServletRequestDataBinder binder,
                    GridIdentifiableDao dao) {
        binder.registerCustomEditor(dao.domainClass(), new CustomDaoEditor(dao));
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return header != null && "XMLHttpRequest".equals(header);
    }

    public static String createParameterString(Identifier identifier){
    	if (identifier instanceof OrganizationAssignedIdentifier) {
			OrganizationAssignedIdentifier organizationAssignedIdentifier = (OrganizationAssignedIdentifier) identifier;
			 return ASSIGNED_BY_PARAM_NAME+"="+ASSIGNED_BY_ORG_VALUE+"&"+ORG_NCI_PARAM_NAME+"="+organizationAssignedIdentifier.getHealthcareSite().getPrimaryIdentifier()
					+"&"+IDENTIFIER_TYPE_PARAM_NAME+"="+identifier.getTypeInternal()+"&"+IDENTIFIER_VALUE_PARAM_NAME+"="+identifier.getValue();
		}
    	SystemAssignedIdentifier systemAssignedIdentifier=(SystemAssignedIdentifier)identifier;
    	return ASSIGNED_BY_PARAM_NAME+"="+ASSIGNED_BY_SYS_VALUE+"&"+SYSYEM_NAME_PARAM_NAME+"="+systemAssignedIdentifier.getSystemName()
		+"&"+IDENTIFIER_TYPE_PARAM_NAME+"="+identifier.getTypeInternal()+"&"+IDENTIFIER_VALUE_PARAM_NAME+"="+identifier.getValue();
    }
    
    public static Identifier getIdentifierInRequest(HttpServletRequest request){
    	Identifier identifier=null;
    	if(WebUtils.hasSubmitParameter(request, IDENTIFIER_VALUE_PARAM_NAME)){
    		if(request.getParameter(ASSIGNED_BY_PARAM_NAME).equals(ASSIGNED_BY_ORG_VALUE)){
    			OrganizationAssignedIdentifier orgAssignedIdentifier = new OrganizationAssignedIdentifier();
    			orgAssignedIdentifier.setHealthcareSite(new LocalHealthcareSite());
    			orgAssignedIdentifier.getHealthcareSite().setCtepCode("null".equals(request.getParameter(ORG_NCI_PARAM_NAME))?null:request.getParameter(ORG_NCI_PARAM_NAME));
    			identifier=orgAssignedIdentifier;
    		}else if(request.getParameter(ASSIGNED_BY_PARAM_NAME).equals(ASSIGNED_BY_SYS_VALUE)){
    			SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
    			sysIdentifier.setSystemName(request.getParameter(SYSYEM_NAME_PARAM_NAME));
    			identifier=sysIdentifier;
    		}else{
    			throw new RuntimeException("Incomplete URL. The identifier information in the URL is incomplete.");
    		}
    		identifier.setValue(request.getParameter(IDENTIFIER_VALUE_PARAM_NAME));
    		identifier.setTypeInternal(request.getParameter(IDENTIFIER_TYPE_PARAM_NAME));
    	}
    	return identifier;
    }
    
    private ControllerTools() {
    }
}
