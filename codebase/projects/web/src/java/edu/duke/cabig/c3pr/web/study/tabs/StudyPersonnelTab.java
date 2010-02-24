package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:11:26 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyPersonnelTab extends StudyTab {

    private StudyValidator studyValidator;

    private PersonnelService personnelService;

    private ResearchStaffDao researchStaffDao;

    public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
        this.researchStaffDao = researchStaffDao;
    }

    public void setPersonnelService(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    public StudyPersonnelTab() {
        this(false);
    }

    public StudyPersonnelTab(boolean editMode) {
        super("Personnel", "Personnel",  "study/study_personnel");
    }

    @Override
    public Map referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        Study study = wrapper.getStudy();
        
        for(StudySite studySite : study.getStudySites()){
        	for(StudyPersonnel studyPersonnel : studySite.getStudyPersonnel()){
        		ResearchStaff researchStaff = studyPersonnel.getResearchStaff();
        		List<C3PRUserGroupType> groupRoles = new ArrayList<C3PRUserGroupType>();
					groupRoles = getGroups(researchStaff);
					researchStaff.setGroups(groupRoles);
        	} 
        }
        addConfigMapToRefdata(refdata, "studyPersonnelStatusRefData");
        return refdata;
    }

    private List<C3PRUserGroupType> getGroups(ResearchStaff researchStaff) {
    	List<C3PRUserGroupType> list = new ArrayList<C3PRUserGroupType>();
		try {
			list = personnelService.getGroups(researchStaff);
		} catch (C3PRBaseException e) {
			e.printStackTrace();
		}
		return list ; 
	}

	@Override
    public void validate(StudyWrapper wrapper, Errors errors) {
        super.validate(wrapper, errors);
        this.studyValidator.validateStudyPersonnel(wrapper.getStudy(), errors);
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper,
                                        Errors errors) {
    	if(!WebUtils.hasSubmitParameter(request, "_actionx")){
    		return;
    	}
    	Integer selectedStudyOrganizationId = Integer.parseInt(request.getParameter("_selectedStudyOrganization"));
    	StudyOrganization selectedStudyOrganization = null;
    	Integer selectedSiteIndex=null;
    	for(int i=0 ; i<wrapper.getStudy().getStudyOrganizations().size() ; i++ ){
    		StudyOrganization studyOrganization = wrapper.getStudy().getStudyOrganizations().get(i);
    		if(studyOrganization.getId().equals(selectedStudyOrganizationId)){
    			selectedStudyOrganization = studyOrganization;
    			selectedSiteIndex = i;
    			break;
    		}
    	}
    	if(selectedStudyOrganization == null){
    		throw new C3PRBaseRuntimeException("Invalid submit.");
    	}
    	String action = request.getParameter("_actionx");
    	if(action.equals("addStudyPersonnel")){
    		String[] rsIds = wrapper.getStudyPersonnelIds();
            if (rsIds.length > 0) {
                ResearchStaff researchStaff = null;
                log.debug("Study PersonnelIds Size : "+ rsIds.length);
                for (String rsId : rsIds) {
                    log.debug("Research Staff Id : " + rsId);
                    StudyPersonnel sPersonnel = new StudyPersonnel();
                    researchStaff = researchStaffDao.getById(new Integer(rsId).intValue());
                    if (researchStaff != null) {
                        sPersonnel.setResearchStaff(researchStaff);
                        sPersonnel.setStatusCode("Active");
                        sPersonnel.setStudyOrganization(selectedStudyOrganization);
                        selectedStudyOrganization.getStudyPersonnel().add(sPersonnel);
                        studyValidator.validateStudyPersonnel(wrapper.getStudy(), errors);
                        if (errors.hasErrors()) {
                        	selectedStudyOrganization.getStudyPersonnel().remove(sPersonnel);
                        }
                    } else {
                        log.error("StudyPersonnelTab - postProcessOnValidation(): researchStaffDao.getById() returned null");
                    }
                }
            }
    	}else if (action.equals("removeStudyPersonnel")) {
    		for(StudyPersonnel studyPersonnel : selectedStudyOrganization.getStudyPersonnel()){
    			if(studyPersonnel.getResearchStaff().getAssignedIdentifier().equals(request.getParameter("_selectedPersonnelAssignedId"))){
    				selectedStudyOrganization.getStudyPersonnel().remove(studyPersonnel);
    				break;
    			}
    		}
        }
    	request.setAttribute("selectedStudyOrganization", selectedStudyOrganization);
    	request.setAttribute("selected_site_index", selectedSiteIndex);
    }

    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

}