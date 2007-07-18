package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class SearchStudySubjectTab extends RegistrationTab<StudySubject>{

	public SearchStudySubjectTab() {
		super("Search Subject or Study", "SearchSubjectStudy","registration/reg_selectStudySubject");
		setShowSummary("false");
	}
	
	@Override
	public Map<String, Object> referenceData() {
		Map<String, List<Lov>> configMap = configurationProperty.getMap();
		Map<String, Object> refdata = new HashMap<String, Object>();
		refdata.put("searchTypeRefDataStudy", configMap.get("studySearchType"));
		refdata.put("searchTypeRefDataPrt", configMap.get("participantSearchType"));
		return refdata;
	}
	@Override
	public void postProcess(HttpServletRequest arg0, StudySubject arg1, Errors arg2) {
	}

	@Override
	protected String postProcessAsynchronous(HttpServletRequest request, StudySubject command, Errors error) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void postProcessSynchronous(HttpServletRequest request, StudySubject command, Errors error) {
		// TODO Auto-generated method stub
		
	}
}
