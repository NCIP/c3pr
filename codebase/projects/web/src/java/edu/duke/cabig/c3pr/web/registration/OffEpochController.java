/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.registration;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.web.registration.tabs.OffEpochConfirmationTab;
import edu.duke.cabig.c3pr.web.registration.tabs.OffEpochTab;
import edu.duke.cabig.c3pr.web.registration.tabs.SelectEpochToChangeTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

public class OffEpochController<C extends StudySubjectWrapper> extends RegistrationController<C> {

	public OffEpochController() {
        super("Off Epoch");
    }
	
	public void setStudySubjectRepository(
			StudySubjectRepository studySubjectRepository) {
		this.studySubjectRepository = studySubjectRepository;
	}

	@Override
	protected void intializeFlows(Flow flow) {
		flow.addTab(new OffEpochTab());
		flow.addTab(new SelectEpochToChangeTab());
		flow.addTab(new OffEpochConfirmationTab());
		setFlow(flow);
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		if(request.getParameter("changeEpochId") == null){
			throw new C3PRBaseRuntimeException("Invalid submit. No epoch id found.");
		}
		StudySubjectWrapper studySubjectWrapper = (StudySubjectWrapper)command;
		studySubjectRepository.takeSubjectOffCurrentEpoch(studySubjectWrapper.getStudySubject().getUniqueIdentifier(), studySubjectWrapper.getOffEpochReasons(), studySubjectWrapper.getOffEpochDate());
		String redirectURL = null;
		if(studySubjectWrapper.getStudySubject().getParentStudySubject() == null){
			redirectURL = "transferEpochRegistration?";
		}else{
			redirectURL = "transferEpochCompanionRegistration?";
		}
		redirectURL += "epoch="+request.getParameter("changeEpochId")+"&";
		redirectURL += ControllerTools.createParameterString(studySubjectWrapper.getStudySubject().getSystemAssignedIdentifiers().get(0));
		return new ModelAndView("redirect:"+redirectURL);
	}
	
}
