package edu.duke.cabig.c3pr.web.study;

import org.springframework.validation.Errors;
import edu.duke.cabig.c3pr.domain.Study;

public class EditStudyOverviewTab extends StudyOverviewTab{
	
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
