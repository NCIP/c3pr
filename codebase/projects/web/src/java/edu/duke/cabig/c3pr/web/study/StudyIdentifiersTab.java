package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;

import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 12:51:05 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyIdentifiersTab extends StudyTab {


    public StudyIdentifiersTab() {
        super("Study Identifier", "Identifiers", "study/study_identifiers");
    }

    @Override
    public Map<String, Object> referenceData(HttpServletRequest request, Study study) {
        Map<String, Object> refdata = super.referenceData();
        addConfigMapToRefdata(refdata, "identifiersTypeRefData");
        refdata.put("identifiersSourceRefData", getHealthcareSiteDao().getAll());
        if(request.getAttribute("amendFlow") != null &&
    			request.getAttribute("amendFlow").toString().equals("true")) 
    	{
	        if(request.getSession().getAttribute(DISABLE_FORM_IDENTIFIERS) != null){
				refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_IDENTIFIERS));
			} else {
				refdata.put("disableForm", new Boolean(false));
			}
    	}
	    return refdata;
    }

    @Override
    public void postProcess(HttpServletRequest httpServletRequest, Study study, Errors errors) {
        String action = httpServletRequest.getParameter("_action");

        if ("addIdentifier".equals(action)) {
            log.debug("Requested Add Identifier");
            SystemAssignedIdentifier id = new SystemAssignedIdentifier();
            id.setValue("<enter value>");
            id.setSystemName("<enter value>");
            study.addIdentifier(id);
        } else if ("removeIdentifier".equals(action)) {
            int selected = Integer.parseInt(httpServletRequest.getParameter("_selected"));
            log.debug("Requested Remove Identifier");
            Identifier id = study.getIdentifiers().get(selected);
            study.removeIdentifier(id);
        }
    }


}
