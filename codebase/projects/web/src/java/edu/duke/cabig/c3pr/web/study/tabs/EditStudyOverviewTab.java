package edu.duke.cabig.c3pr.web.study.tabs;

import edu.duke.cabig.c3pr.domain.Study;
import org.springframework.validation.Errors;

public class EditStudyOverviewTab extends StudyOverviewTab {

    public EditStudyOverviewTab(String longTitle, String shortTitle,
                                String viewName) {
        super(longTitle, shortTitle, viewName);
    }

    public void validate(Study study, Errors errors) {
        try {
            study.setDataEntryStatus(true);
        }
        catch (Exception e) {
            errors.rejectValue("coordinatingCenterStudyStatus", "dummyCode", e.getMessage());
        }
    }

}
