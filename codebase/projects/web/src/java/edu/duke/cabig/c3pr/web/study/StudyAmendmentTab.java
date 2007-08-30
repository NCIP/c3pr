package edu.duke.cabig.c3pr.web.study;


import edu.duke.cabig.c3pr.domain.Study;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.validation.Errors;

public class StudyAmendmentTab extends StudyTab {


	public StudyAmendmentTab() {
		super("Amendment details", "Amendments", "study/study_amendments");
	}

}

