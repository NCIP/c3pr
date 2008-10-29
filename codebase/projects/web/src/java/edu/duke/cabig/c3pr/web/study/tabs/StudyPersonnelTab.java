package edu.duke.cabig.c3pr.web.study.tabs;

import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        
        for (int i = 0; i < study.getStudyOrganizations().size(); i++) {
            for (int j = 0; j < study.getStudyOrganizations().get(i).getStudyPersonnel().size(); j++) {
                try {
                    List<String> groupRoles = new ArrayList<String>();
                    Iterator<C3PRUserGroupType> groupIterator = personnelService.getGroups(study.getStudyOrganizations().get(i).getStudyPersonnel().get(j).getResearchStaff()).iterator();
                    C3PRUserGroupType userGroup;
                    while (groupIterator.hasNext()) {
                        userGroup = groupIterator.next();
                        groupRoles.add(userGroup.getDisplayName());
                    }
                    refdata.put("studyOrganizations[" + i + "].studyPersonnel[" + j + "].roleData", groupRoles);
                }
                catch (C3PRBaseException e) {
                    e.printStackTrace();
                }
            }
        }

        addConfigMapToRefdata(refdata, "studyPersonnelRoleRefData");
        addConfigMapToRefdata(refdata, "studyPersonnelStatusRefData");

        return refdata;
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
                return;
            }else if (StringUtils.equals("addStudyDisease", action) && studyOrganization != null) {
                String[] rsIds = studyOrganization.getStudyPersonnelIds();
                if (rsIds.length > 0) {
                    ResearchStaff rs = null;
                    log
                            .debug("Study PersonnelIds Size : "
                                    + studyOrganization.getStudyPersonnelIds().length);
                    for (String rsId : rsIds) {
                        log.debug("Research Staff Id : " + rsId);
                        StudyPersonnel sPersonnel = new StudyPersonnel();
                        rs = researchStaffDao.getById(new Integer(rsId).intValue());
                        if (rs != null) {
                            sPersonnel.setResearchStaff(rs);
                            sPersonnel.setRoleCode("C3pr Admin");
                            sPersonnel.setStatusCode("Active");
                            sPersonnel.setStudyOrganization(studyOrganization);
                            studyOrganization.getStudyPersonnel().add(sPersonnel);
                            studyValidator.validateStudyPersonnel(wrapper.getStudy(), errors);
                            if (errors.hasErrors()) {
                                studyOrganization.getStudyPersonnel().remove(sPersonnel);
                            }
                        } else {
                            log
                                    .error("StudyPersonnelTab - postProcessOnValidation(): researchStaffDao.getById() returned null");
                        }
                    }
                }
                return;
            }
        }

        if (StringUtils.equals("removeStudyDisease", action) && studyOrganization != null) {
            studyOrganization.getStudyPersonnel().remove(Integer.parseInt(selected));
            return;
        }
    }

    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

}