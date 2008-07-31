package edu.duke.cabig.c3pr.web.registration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.ArmDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.impl.StudySubjectServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;

public class RegistrationConfirmAndRandomizeController extends
		SimpleFormController {

	private StudySubjectDao studySubjectDao;

	private StudySubjectServiceImpl studySubjectService;

	private ArmDao armDao;

	private RegistrationControllerUtils registrationControllerUtils;
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
		StudySubject studySubject = ((StudySubject) command);
		Map map = registrationControllerUtils.buildMap(studySubject);
		boolean actionRequired = false;
		String actionLabel = "";
		if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.UNAPPROVED
				&& studySubject.isDataEntryComplete()) {
			actionRequired = true;
			if (studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.REGISTERED) {
				if (studySubject.getScheduledEpoch().getEpoch().isEnrolling())
					actionLabel = "Register";
				else
					actionLabel = "Save";
			} else
				actionLabel = "Transfer Subject";
			if (studySubject.getScheduledEpoch().getRequiresRandomization()) {
				actionLabel += " & Randomize";
			}
		}
		map.put("actionRequired", actionRequired);
		map.put("actionLabel", actionLabel);
		map.put("requiresMultiSite", studySubjectService
				.requiresExternalApprovalForRegistration(studySubject));
		registrationControllerUtils.addAppUrls(map);
		return map;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		return studySubjectDao.getById(Integer.parseInt(request
				.getParameter("registrationId")));
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		StudySubject studySubject = (StudySubject) command;
		if (!validSubmit(studySubject)) {
			throw new Exception(
					"Subject is either already registered or the subject registration requires QC");
		}
		Map map = new HashMap();
		map.put("actionRequired", false);
		try {
			studySubject = studySubjectService.register(studySubject);
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
		return studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.UNAPPROVED;
	}
}
