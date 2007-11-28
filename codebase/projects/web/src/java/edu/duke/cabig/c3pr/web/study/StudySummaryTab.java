package edu.duke.cabig.c3pr.web.study;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Study;
/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 3:35:54 PM
 * To change this template use File | Settings | File Templates.
 */
class StudySummaryTab extends StudyTab {

    public StudySummaryTab() {
        super("Study Summary", "Summary", "study/study_reviewsummary");
    }

		 
}
