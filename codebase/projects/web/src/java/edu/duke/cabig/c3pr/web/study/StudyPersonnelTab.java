package edu.duke.cabig.c3pr.web.study;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;


import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 3:11:26 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyPersonnelTab extends StudyTab {
	
	private StudyValidator studyValidator;
	private PersonnelService personnelService;

    public void setPersonnelService(PersonnelService personnelService) {
		this.personnelService = personnelService;
	}

	public StudyPersonnelTab() {
        this(false);
    }

    public StudyPersonnelTab(boolean editMode){
        super("Personnel", "Personnel", editMode?"study/study_personnel_edit":"study/study_personnel");
    }
    

    @Override
    public Map referenceData(HttpServletRequest request, Study study) {
        Map<String, Object> refdata = super.referenceData(study);    //To change body of overridden methods use File | Settings | File Templates.
        for(int i=0; i< study.getStudyOrganizations().size(); i ++) {
        	for (int j=0; j < study.getStudyOrganizations().get(i).getStudyPersonnel().size();j++){
        		try {
        			List<String> groupRoles = new ArrayList<String>();
        			Iterator<C3PRUserGroupType> groupIterator =personnelService.getGroups(study.getStudyOrganizations().get(i).getStudyPersonnel().get(j).getResearchStaff()).iterator();
        			C3PRUserGroupType userGroup;
        			while(groupIterator.hasNext()){
        				userGroup = groupIterator.next();
        				groupRoles.add(userGroup.getDisplayName());
        			}
					refdata.put("studyOrganizations["+i+"].studyPersonnel["+j+"].roleData",groupRoles);
				} catch (C3PRBaseException e) {
					e.printStackTrace();
				}
        	}
        }
        
        if((request.getParameter("_selectedSite")==null) || (request.getParameter("_selectedSite")!=null && StringUtils.isBlank(request.getParameter("_selectedSite").toString()))){
        	refdata.put("selectedSite", 0);
        }
        
        addConfigMapToRefdata(refdata, "studyPersonnelRoleRefData");
        addConfigMapToRefdata(refdata, "studyPersonnelStatusRefData");

        return refdata;
    }
    

    @Override
	public void validate(Study study, Errors errors) {
		// TODO Auto-generated method stub
		super.validate(study, errors);
		this.studyValidator.validateStudyPersonnel(study, errors);
	}

    @Override
    public void postProcessOnValidation(HttpServletRequest httpServletRequest, Study study, Errors errors) {
        if ("siteChange".equals(httpServletRequest.getParameter("_action"))) {
            httpServletRequest.getSession().setAttribute("selectedSite", httpServletRequest.getParameter("_selectedSite"));
        }
    }

	public StudyValidator getStudyValidator() {
		return studyValidator;
	}

	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}
    
}