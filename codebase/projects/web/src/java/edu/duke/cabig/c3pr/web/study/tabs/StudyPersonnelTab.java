package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.utils.StringUtils;
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
        super("Study Personnel", "Study Personnel",  "study/study_personnel");
    }

    @Override
    public Map referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        Study study = wrapper.getStudy();
        
        for(StudyOrganization studyOrganization : study.getStudyOrganizations()){
        	for(StudyPersonnel studyPersonnel : studyOrganization.getStudyPersonnel()){
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

        String selected = request.getParameter("_selected");
        String action = request.getParameter("_actionx");
        String selectedSite = request.getParameter("_selectedSite");
        StudyOrganization studyOrganization = null;

        // get the StudyOrganization to which we will add/remove research staff.
        List<StudyOrganization> studyOrganizationList = wrapper.getStudy().getStudyOrganizations();
        if (!StringUtils.isBlank(selectedSite)) {
            studyOrganization = studyOrganizationList.get(Integer.parseInt(selectedSite));
        }

        if (!errors.hasErrors()) {
            if (StringUtils.equals("siteChange", action)) {
                request.getSession().setAttribute("_selectedSite", selectedSite);
            }else if (StringUtils.equals("addStudyDisease", action) && studyOrganization != null) {
                String[] rsIds = studyOrganization.getStudyPersonnelIds();
                if (rsIds.length > 0) {
                    ResearchStaff researchStaff = null;
                    log.debug("Study PersonnelIds Size : "+ studyOrganization.getStudyPersonnelIds().length);
                    for (String rsId : rsIds) {
                        log.debug("Research Staff Id : " + rsId);
                        StudyPersonnel sPersonnel = new StudyPersonnel();
                        researchStaff = researchStaffDao.getById(new Integer(rsId).intValue());
                        if (researchStaff != null) {
                            sPersonnel.setResearchStaff(researchStaff);
                            sPersonnel.setRoleCode(getGroups(researchStaff).get(0).getDisplayName());
                            sPersonnel.setStatusCode("Active");
                            sPersonnel.setStudyOrganization(studyOrganization);
                            studyOrganization.getStudyPersonnel().add(sPersonnel);
                            studyValidator.validateStudyPersonnel(wrapper.getStudy(), errors);
                            if (errors.hasErrors()) {
                                studyOrganization.getStudyPersonnel().remove(sPersonnel);
                            }
                        } else {
                            log.error("StudyPersonnelTab - postProcessOnValidation(): researchStaffDao.getById() returned null");
                        }
                    }
                }
            }
        }

        if (StringUtils.equals("removeStudyDisease", action) && studyOrganization != null) {
            studyOrganization.getStudyPersonnel().remove(Integer.parseInt(selected));
        }
    }

    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

}