/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.StudyVersionDao;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.domain.SubjectConsentQuestionAnswer;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.WebUtils;
import edu.duke.cabig.c3pr.web.registration.tabs.ReConsentConfirmationTab;
import edu.duke.cabig.c3pr.web.registration.tabs.ReConsentTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

public class ReConsentController<C extends StudySubjectWrapper> extends RegistrationController<C> {

	public ReConsentController() {
        super("Re Consent");
    }
	
	public void setStudySubjectRepository(
			StudySubjectRepository studySubjectRepository) {
		this.studySubjectRepository = studySubjectRepository;
	}
	
	private StudyVersionDao studyVersionDao;
	
	
	public void setStudyVersionDao(StudyVersionDao studyVersionDao) {
		this.studyVersionDao = studyVersionDao;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		StudySubjectWrapper wrapper =  (StudySubjectWrapper) super.formBackingObject(request);
		StudyVersion reConsentingStudyVersion = null;
		if(!StringUtils.isBlank(request.getParameter("studyVersion"))){
			reConsentingStudyVersion = studyVersionDao.getById(Integer.parseInt(request.getParameter("studyVersion")));
			wrapper.setReConsentingVersion(reConsentingStudyVersion);
		}
		if(reConsentingStudyVersion == null){
			StudyVersion latestStudyVersion = wrapper.getStudySubject().getStudySite().getStudy().
				getLatestActiveStudyVersion();
			wrapper.setReConsentingVersion(latestStudyVersion);
		}
		
		for (Consent consent :wrapper.getReConsentingVersion().getConsents()){
    		StudySubjectConsentVersion studySubjectConsentVersion = new StudySubjectConsentVersion();
    		studySubjectConsentVersion.setConsent(consent);
    		for(ConsentQuestion question:consent.getQuestions()){
    			SubjectConsentQuestionAnswer subjectConsentQuestionAnswer = new SubjectConsentQuestionAnswer();
    			subjectConsentQuestionAnswer.setConsentQuestion(question);
    			studySubjectConsentVersion.addSubjectConsentAnswer(subjectConsentQuestionAnswer);
    		}
    		wrapper.addReConsentingStudySubjectConsentVersion(studySubjectConsentVersion);
    	}
		
		return wrapper;
	}

	@Override
	protected void intializeFlows(Flow flow) {
		flow.addTab(new ReConsentTab());
		flow.addTab(new ReConsentConfirmationTab());
		setFlow(flow);
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		StudySubjectWrapper studySubjectWrapper = (StudySubjectWrapper) command;
		studySubjectWrapper.setReConsentingVersion(null);
		studySubjectWrapper.setReConsentingStudySubjectConsentVersons(null);
		String redirectURL = "manageRegistrationController?";
		redirectURL += ControllerTools.createParameterString(studySubjectWrapper.getStudySubject().getSystemAssignedIdentifiers().get(0));
		return new ModelAndView("redirect:"+redirectURL);
	}
	
}
