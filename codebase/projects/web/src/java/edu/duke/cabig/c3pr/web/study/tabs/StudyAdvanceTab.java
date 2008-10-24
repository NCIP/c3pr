package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 1:39:34 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyAdvanceTab extends StudyTab {

    public StudyAdvanceTab() {
        super("Advance Configuration", "Advance Configuration", "study/study_advance");
    }
}
