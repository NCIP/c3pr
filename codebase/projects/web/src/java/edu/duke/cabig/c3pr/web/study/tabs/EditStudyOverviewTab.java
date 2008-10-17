package edu.duke.cabig.c3pr.web.study.tabs;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.web.study.StudyWrapper;

public class EditStudyOverviewTab extends StudyOverviewTab {

    public EditStudyOverviewTab(String longTitle, String shortTitle,
                                String viewName) {
        super(longTitle, shortTitle, viewName);
    }
    
    @Override
    public void validate(StudyWrapper wrapper, Errors errors) {
        try {
           // wrapper.getStudy().updateDataEntryStatus();
        }
        catch (Exception e) {
            errors.rejectValue("study.coordinatingCenterStudyStatus", "dummyCode", e.getMessage());
        }
    }

}
