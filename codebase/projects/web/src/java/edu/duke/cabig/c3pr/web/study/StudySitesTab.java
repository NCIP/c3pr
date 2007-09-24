package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Study;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 1:39:34 PM
 * To change this template use File | Settings | File Templates.
 */
class StudySitesTab extends StudyTab {

    public StudySitesTab() {
        super("Sites", "Sites", "study/study_studysites");
    }

    @Override
    public Map<String, Object> referenceData(HttpServletRequest request, Study study) {
        Map<String, Object> refdata = super.referenceData(study);
        addConfigMapToRefdata(refdata, "studySiteStatusRefData");
        addConfigMapToRefdata(refdata, "studySiteRoleCodeRefData");
        refdata.put("healthCareSites", getHealthcareSiteDao().getAll());
        if(request.getAttribute("amendFlow") != null &&
    			request.getAttribute("amendFlow").toString().equals("true")) 
    	{
        	if(request.getSession().getAttribute(DISABLE_FORM_SITES) != null){
				refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_SITES));
			} else {
				refdata.put("disableForm", new Boolean(false));
			}
    	}
        return refdata;

    }

}



    

