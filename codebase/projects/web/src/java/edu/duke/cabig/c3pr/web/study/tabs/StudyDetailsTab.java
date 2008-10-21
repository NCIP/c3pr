package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.study.AmendStudyController;
import edu.duke.cabig.c3pr.web.study.CreateStudyController;
import edu.duke.cabig.c3pr.web.study.EditStudyController;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 13, 2007 Time: 7:27:09 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyDetailsTab extends StudyTab {

    private StudyValidator studyValidator;

    private StudyDao studyDao;

    public StudyDetailsTab() {
        super("Details", "Details", "study/study_details");
    }

    public ModelAndView embedCompanion(HttpServletRequest request, Object commandObj,
                                       Errors error) {
        int rowCount = Integer.parseInt(request.getParameter("rowCount"));
        Study parentStudy = getParentStudy(request);
        Study companionStudy = ((StudyWrapper) commandObj).getStudy();
        Map map = new HashMap();
        CompanionStudyAssociation companionStudyAssociation = parentStudy.getCompanionStudyAssociations().get(rowCount);
        companionStudyAssociation.setCompanionStudy(companionStudy);
        map.put(AjaxableUtils.getFreeTextModelName(), "");
        if (companionStudyAssociation.getParentStudy().getId() != null) {
            companionStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
        }
        return new ModelAndView("", map);
    }

    private Study getParentStudy(HttpServletRequest request) {
        String flowType = request.getParameter("flowType");
        String commandObject = "";
        StudyWrapper wrapper = null ;
        if ("CREATE_STUDY".equals(flowType)) {
            commandObject = (CreateStudyController.class).getName() + ".FORM.command";
            wrapper = (StudyWrapper) request.getSession().getAttribute(commandObject);
        } else if ("EDIT_STUDY".equals(flowType)) {
                commandObject = (EditStudyController.class).getName() + ".FORM.command";
                wrapper = (StudyWrapper) request.getSession().getAttribute(commandObject);
        } else if ("AMEND_STUDY".equals(flowType)) {
                commandObject = (AmendStudyController.class).getName() + ".FORM.command";
                wrapper = (StudyWrapper) request.getSession().getAttribute(commandObject);
        }
        return wrapper.getStudy();
    }

    @Override
    public Map<String, Object> referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData();
        addConfigMapToRefdata(refdata, "studySearchType");
        addConfigMapToRefdata(refdata, "diseaseCodeRefData");
        addConfigMapToRefdata(refdata, "monitorCodeRefData");
        addConfigMapToRefdata(refdata, "phaseCodeRefData");
        addConfigMapToRefdata(refdata, "sponsorCodeRefData");
        addConfigMapToRefdata(refdata, "statusRefData");
        addConfigMapToRefdata(refdata, "typeRefData");
        addConfigMapToRefdata(refdata, "coordinatingCenters");
        addConfigMapToRefdata(refdata, "yesNo");

        boolean isAdmin = isAdmin();

        if (request.getAttribute("amendFlow") != null
                && request.getAttribute("amendFlow").toString().equalsIgnoreCase("true")) {
            // amend-flow: set the disableForm refData for the amend flow.
            if (request.getSession().getAttribute(DISABLE_FORM_DETAILS) != null && !isAdmin) {
                refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_DETAILS));
            } else {
                refdata.put("disableForm", new Boolean(false));
                refdata.put("mandatory", "true");
            }
        } else if (request.getAttribute("editFlow") != null
                && request.getAttribute("editFlow").toString().equalsIgnoreCase("true")) {
            // edit-flow: disable all unless in PENDING STATE.
            if (!(wrapper.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING)
                    && !isAdmin) {
                disableAll(request);
            } else {
                // all states other than pending
                enableAll(request);
                refdata.put("mandatory", "true");
            }
            // set the disableForm refData for the edit flow.
            if (request.getSession().getAttribute(DISABLE_FORM_DETAILS) != null && !isAdmin) {
                refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_DETAILS));
            } else {
                refdata.put("disableForm", new Boolean(false));
                refdata.put("mandatory", "true");
            }
        } else {
            // this must be the create flow
            enableAll(request);
            refdata.put("mandatory", "true");
        }

        return refdata;
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper, Errors errors) {
    	Study study = wrapper.getStudy() ;
        super.postProcessOnValidation(request, wrapper, errors);
        if (request.getParameter("deletedSponsor") != null && request.getParameter("deletedSponsor").equals("delete")) {
            if (study.getFundingSponsorIdentifierIndex() != -1) {
                study.getOrganizationAssignedIdentifiers().remove(
                        study.getFundingSponsorIdentifierIndex());
            }
            if ((study.getStudyFundingSponsors().size() > 0)) {
                study.getStudyFundingSponsors().remove(0);
            }
        } else
        if (request.getParameter("deletedSponsorIdentifier") != null && request.getParameter("deletedSponsorIdentifier").equals("delete")) {
            if (study.getFundingSponsorIdentifierIndex() != -1) {
                study.getOrganizationAssignedIdentifiers().remove(
                        study.getFundingSponsorIdentifierIndex());
            }

        }
        updateRandomization(study);
        updateStratification(study);
        wrapper.setStudy(study);
        /*if (study.getFundingSponsorAssignedIdentifier()!= null){
        	studyDao.refreshFundingSposorIdentifier(study);
        }*/
    }

    @Override
    public void validate(StudyWrapper wrapper, Errors errors) {
        super.validate(wrapper, errors);
        studyValidator.validateStudyCoordinatingCetnterIdentifier(wrapper.getStudy(), errors);
        studyValidator.validateStudyFundingSponsorIdentifier(wrapper.getStudy(), errors);

    }

    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }
}
