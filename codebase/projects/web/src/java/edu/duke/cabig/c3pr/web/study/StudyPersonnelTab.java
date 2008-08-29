package edu.duke.cabig.c3pr.web.study;

import java.util.ArrayList;
import java.util.Iterator;
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

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:11:26 PM To change this template
 * use File | Settings | File Templates.
 */
class StudyPersonnelTab extends StudyTab {

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
        super("Personnel", "Personnel", editMode ? "study/study_personnel_edit"
                        : "study/study_personnel");
    }

    @Override
    public Map referenceData(HttpServletRequest request, Study study) {
        Map<String, Object> refdata = super.referenceData(study); // To change body of overridden
                                                                    // methods use File | Settings |
                                                                    // File Templates.
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
                    refdata.put("studyOrganizations[" + i + "].studyPersonnel[" + j + "].roleData",groupRoles);
                }
                catch (C3PRBaseException e) {
                    e.printStackTrace();
                }
            }
        }
        
        if (StringUtils.isBlank(request.getParameter("_selectedSite"))) {
            refdata.put("selectedSite", 0);
        }

        addConfigMapToRefdata(refdata, "studyPersonnelRoleRefData");
        addConfigMapToRefdata(refdata, "studyPersonnelStatusRefData");

        return refdata;
    }

    @Override
    public void validate(Study study, Errors errors) {
        super.validate(study, errors);
        this.studyValidator.validateStudyPersonnel(study, errors);
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest httpServletRequest, Study study,
                    Errors errors) {
    	
    	 String selected = httpServletRequest.getParameter("_selected");
         String action = httpServletRequest.getParameter("_actionx");
         Object selectedSite = httpServletRequest.getParameter("_selectedSite");
         StudyOrganization so = null;
         

         // get the StudyOrganization to which we will add/remove research staff.
         List<StudyOrganization> soList = study.getStudyOrganizations();
         if (selectedSite != null && !selectedSite.toString().equals("")) {
             selectedSite = httpServletRequest.getParameter("_selectedSite").toString();
             so = soList.get(new Integer(selectedSite.toString()).intValue());
         }
    	
         if (!errors.hasErrors()) {

             if ("siteChange".equals(action)) {
                 httpServletRequest.getSession().setAttribute("_selectedSite", selectedSite);
                 return;
             }

             if ("addStudyDisease".equals(action) && so != null) {
                 String[] rsIds = so.getStudyPersonnelIds();
                 if (rsIds.length > 0) {
                     ResearchStaff rs = null;
                     log
                                     .debug("Study PersonnelIds Size : "
                                                     + so.getStudyPersonnelIds().length);
                     for (String rsId : rsIds) {
                         log.debug("Research Staff Id : " + rsId);
                         StudyPersonnel sPersonnel = new StudyPersonnel();
                         rs = researchStaffDao.getById(new Integer(rsId).intValue());
                         if (rs != null) {
                             rs.getStudyPersonnels().add(sPersonnel);
                             sPersonnel.setResearchStaff(rs);
                             sPersonnel.setRoleCode("C3pr Admin");
                             sPersonnel.setStatusCode("Active");
                             sPersonnel.setStudyOrganization(so);
                             so.getStudyPersonnel().add(sPersonnel);
                             studyValidator.validateStudyPersonnel(study, errors);
                             if (errors.hasErrors()) {
                                 so.getStudyPersonnel().remove(sPersonnel);
                             }
                         }
                         else {
                             log
                                             .error("StudyPersonnelTab - postProcessOnValidation(): researchStaffDao.getById() returned null");
                         }
                     }
                 }
                 return;
             }
         }

         if ("removeStudyDisease".equals(action) && so != null) {
             so.getStudyPersonnel().remove(Integer.parseInt(selected));
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