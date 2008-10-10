package edu.duke.cabig.c3pr.web.study.tabs;

import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class StudyAmendmentTab extends StudyTab {

    private StudyService studyService;

    public StudyAmendmentTab() {
        super("Amendment details", "Amendments", "study/study_amendments");
    }

    @Override
    public Map referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        // this will ensure the amendment form isnt disabled.
        refdata.put("disableForm", new Boolean(false));
        // this will ensure all other tabs/forms are disabled
        refdata.put("mandatory", "true");
        disableAll(request);
        return refdata;
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper, Errors errors) {

        int size = wrapper.getStudy().getStudyAmendments().size();
        StudyAmendment sAmendment = (StudyAmendment) wrapper.getStudy().getStudyAmendments().get(size - 1);

        if (sAmendment != null) {
            // Note: the epochsIndicator will be null if the checkbox is not checked.
            // In case the epochsIndicator is not null and its value is true.....set the disable for
            // that tab to false.
            // Else(epochsIndicator is null which corresponds to false or not checking the checkbox)
            // set the disable for that tab to true and
            // explicitly set the epochsIndicator in the studyAmendment instance to false.
            if (sAmendment.getEaChangedIndicator() != null && sAmendment.getEaChangedIndicator()) {
                request.getSession().setAttribute(DISABLE_FORM_EPOCH_AND_ARMS, new Boolean(false));
            } else {
                request.getSession().setAttribute(DISABLE_FORM_EPOCH_AND_ARMS, new Boolean(true));
                sAmendment.setEaChangedIndicator(false);
            }

            if (sAmendment.getEligibilityChangedIndicator() != null
                    && sAmendment.getEligibilityChangedIndicator()) {
                request.getSession().setAttribute(DISABLE_FORM_ELIGIBILITY, new Boolean(false));
            } else {
                request.getSession().setAttribute(DISABLE_FORM_ELIGIBILITY, new Boolean(true));
                sAmendment.setEligibilityChangedIndicator(false);
            }

            if (sAmendment.getStratChangedIndicator() != null
                    && sAmendment.getStratChangedIndicator()) {
                request.getSession().setAttribute(DISABLE_FORM_STRATIFICATION, new Boolean(false));
            } else {
                request.getSession().setAttribute(DISABLE_FORM_STRATIFICATION, new Boolean(true));
                sAmendment.setStratChangedIndicator(false);
            }

            if (sAmendment.getDiseasesChangedIndicator() != null
                    && sAmendment.getDiseasesChangedIndicator()) {
                request.getSession().setAttribute(DISABLE_FORM_DISEASES, new Boolean(false));
            } else {
                request.getSession().setAttribute(DISABLE_FORM_DISEASES, new Boolean(true));
                sAmendment.setDiseasesChangedIndicator(false);
            }

            if (sAmendment.getRandomizationChangedIndicator() != null
                    && sAmendment.getRandomizationChangedIndicator()) {
                request.getSession().setAttribute(DISABLE_FORM_RANDOMIZATION, new Boolean(false));
            } else {
                request.getSession().setAttribute(DISABLE_FORM_RANDOMIZATION, new Boolean(true));
                sAmendment.setRandomizationChangedIndicator(false);
            }

            if (sAmendment.getCompanionChangedIndicator() != null && sAmendment.getCompanionChangedIndicator()) {
                request.getSession().setAttribute(DISABLE_FORM_COMPANION, new Boolean(false));
            } else {
                request.getSession().setAttribute(DISABLE_FORM_COMPANION, new Boolean(true));
                sAmendment.setCompanionChangedIndicator(false);
            }

            // Change the status from Active to Pending_amendment.
            try {
                // Changing the status only if it is currently Active.
                if (wrapper.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.ACTIVE) {
                    wrapper.getStudy()
                            .setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.AMENDMENT_PENDING);
                    for (StudySite studySite : wrapper.getStudy().getStudySites()) {
                        if (studySite.getSiteStudyStatus() == SiteStudyStatus.ACTIVE) {
                            studySite.setSiteStudyStatus(SiteStudyStatus.AMENDMENT_PENDING);
                        }
                    }
                }

            }
            catch (Exception e) {
                log.error("Unable to eval status" + e.getMessage());
            }

        }

    }

    public StudyService getStudyService() {
        return studyService;
    }

    public void setStudyService(StudyService studyService) {
        this.studyService = studyService;
    }

}
