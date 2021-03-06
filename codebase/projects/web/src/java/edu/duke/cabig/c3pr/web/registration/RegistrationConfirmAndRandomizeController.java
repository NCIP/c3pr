/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.ArmDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.impl.StudySubjectServiceImpl;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;

public class RegistrationConfirmAndRandomizeController extends
		SimpleFormController {

	private StudySubjectDao studySubjectDao;

	private StudySubjectServiceImpl studySubjectService;

	private ArmDao armDao;

	private RegistrationControllerUtils registrationControllerUtils;
	
	private StudySubjectRepository studySubjectRepository;
	
	private StudyDao studyDao;
	
	private Logger log = Logger.getLogger(RegistrationConfirmAndRandomizeController.class);
	
    public void setRegistrationControllerUtils(
			RegistrationControllerUtils registrationControllerUtils) {
		this.registrationControllerUtils = registrationControllerUtils;
	}
	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	public StudySubjectServiceImpl getStudySubjectService() {
		return studySubjectService;
	}

	public void setStudySubjectService(
			StudySubjectServiceImpl studySubjectService) {
		this.studySubjectService = studySubjectService;
	}

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Arm.class, new CustomDaoEditor(armDao));
	}

	@Override
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {
		StudySubjectWrapper wrapper= (StudySubjectWrapper) command;
		StudySubject studySubject = wrapper.getStudySubject();
		Map map = registrationControllerUtils.buildMap(studySubject);
		map.put("requiresMultiSite", studySubjectService.requiresExternalApprovalForRegistration(studySubject));
		map.put("imageAndMessageList", registrationControllerUtils.getConfirmationMessage(studySubject));
		registrationControllerUtils.addAppUrls(map);
		return map;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		StudySubject studySubject = null;
		StudySubjectWrapper wrapper = new StudySubjectWrapper();
		 if (WebUtils.hasSubmitParameter(request, ControllerTools.IDENTIFIER_VALUE_PARAM_NAME)) {
	        	studySubject=studySubjectRepository.getUniqueStudySubject(ControllerTools.getIdentifierInRequest(request));
	        	// setting the participant in StudySubjectWrapper. Using studySubject.participant until 
	        	// studySubject.studySubjectDemographics is valid, using studySubject.studySubjectDemographics once 
	        	// it becomes valid
	        	if (!studySubject.getStudySubjectDemographics().getValid()){
	        		wrapper.setParticipant(studySubject.getParticipant());
	        	}else{
	        		wrapper.setParticipant(studySubject.getStudySubjectDemographics());
	        	}
	            studySubjectDao.initialize(studySubject);
	            Study study = studyDao.getById(studySubject.getStudySite().getStudy().getId());
	    	    studyDao.initialize(study);
	    	    for(CompanionStudyAssociation companionStudyAssoc : study.getStudyVersion().getCompanionStudyAssociations()){
	    	    	Study companionStudy = companionStudyAssoc.getCompanionStudy();
	    	    	studyDao.initialize(companionStudy);
	    	    }
	        }
	        else {
	            throw new C3PRBaseRuntimeException("Could not load the study subject. Make sure the identifier is correct");
	        }
		wrapper.setStudySubject(studySubject);
		return wrapper ;
	}

	public void setStudySubjectRepository(
			StudySubjectRepository studySubjectRepository) {
		this.studySubjectRepository = studySubjectRepository;
	}
	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		StudySubjectWrapper wrapper= (StudySubjectWrapper) command;
		StudySubject studySubject = wrapper.getStudySubject();
		Map map = new HashMap();
		map.put("actionRequired", false);
		try {
			studySubject = studySubjectRepository.enroll(studySubject);
		} catch (Exception e) {
			map.put("registrationException", e);
			return new ModelAndView(getSuccessView(), map);

		}
		if (logger.isDebugEnabled()) {
			logger
					.debug("onSubmit(HttpServletRequest, HttpServletResponse, Object, BindException) - registration service call over"); //$NON-NLS-1$
		}
		map.putAll(registrationControllerUtils.buildMap(studySubject));
		map.put("command", command);
		registrationControllerUtils.addAppUrls(map);
		return new ModelAndView(getSuccessView(), map);
	}

	public ArmDao getArmDao() {
		return armDao;
	}

	public void setArmDao(ArmDao armDao) {
		this.armDao = armDao;
	}
	private boolean validSubmit(StudySubject studySubject) {
		return studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING_RANDOMIZATION_ON_EPOCH;
	}
}
