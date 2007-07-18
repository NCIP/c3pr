/*package edu.duke.cabig.c3pr.web.registration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.DiseaseHistory;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.service.ParticipantService;
import edu.duke.cabig.c3pr.service.impl.ParticipantServiceImpl;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlowTab;
import edu.duke.cabig.c3pr.web.registration.tabs.DiseasesDetailsTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EligibilityCriteriaTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EnrollmentDetailsTab;
import edu.duke.cabig.c3pr.web.registration.tabs.RandomizationTab;
import edu.duke.cabig.c3pr.web.registration.tabs.ReviewSubmitTab;
import edu.duke.cabig.c3pr.web.registration.tabs.SearchStudySubjectTab;
import edu.duke.cabig.c3pr.web.registration.tabs.SelectStudyTab;
import edu.duke.cabig.c3pr.web.registration.tabs.SelectSubjectTab;
import edu.duke.cabig.c3pr.web.registration.tabs.StratificationTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

*//**
 * @author Ramakrishna
 * 
 *//*

public class CreateRegistrationController_old<C extends StudySubject> extends RegistrationController<C> {
	*//**
	 * Logger for this class
	 *//*
	private static final Logger logger = Logger.getLogger(CreateRegistrationController.class);

	private static Log log = LogFactory.getLog(CreateRegistrationController.class);
	
	private ParticipantService participantService;
	
	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService;
	}

	public CreateRegistrationController_old() {
		super("Create Registration");
	}


	@Override
	protected void intializeFlows(Flow flow) {
		flow.addTab(new SearchStudySubjectTab());
		flow.addTab(new SelectStudyTab());
		flow.addTab(new SelectSubjectTab());
		flow.addTab(new EnrollmentDetailsTab());
		flow.addTab(new DiseasesDetailsTab());
		flow.addTab(new EligibilityCriteriaTab());
		flow.addTab(new StratificationTab());
		flow.addTab(new RandomizationTab());
		flow.addTab(new ReviewSubmitTab());
		setFlow(flow);
	}

	@Override
	protected boolean isFormSubmission(HttpServletRequest request) {
		return super.isFormSubmission(request) || isResumeFlow(request);
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		StudySubject studySubject=new StudySubject();
		studySubject.setStudyParticipantIdentifier("SYS_GEN1");
		studySubject.setStartDate(new Date());
		studySubject.setEligibilityWaiverReasonText("Type Eligibility Waiver Reason.");
		removeAlternateDisplayFlow(request);
		request.getSession().setAttribute("registrationFlow", getFlow());
		studySubject.setDiseaseHistory(new DiseaseHistory());
		studySubject.addScheduledArm(new ScheduledArm(studySubject));
		return studySubject;
	}
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest, int page) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> refdata = new HashMap<String, Object>();
		refdata.put("registrationTab", (SubFlowTab)getFlow().getTab(page));
		return refdata;
	}
	
	@Override
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
		// TODO Auto-generated method stub
		String tabShortTitle=getFlow().getTab(page).getShortTitle();
		StudySubject studySubject=(StudySubject)command;
		if(!super.isFormSubmission(request)&&isResumeFlow(request)){
			if(isNewRegistration(request))
				buildCommandObject(studySubject);
			else
				updateCommandObject(studySubject);
			checkCollections(studySubject);
		}
		super.postProcessPage(request, command, errors, page);
		studySubject.setRegistrationStatus(ParticipantServiceImpl.evaluateStatus(studySubject));
	}
	
	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException arg3)
			throws Exception {
		// TODO Auto-generated method stub
		StudySubject studySubject = (StudySubject) command;
		participantService.createRegistration(studySubject);
		if (logger.isDebugEnabled()) {
			logger.debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - participant service call over"); //$NON-NLS-1$
		}
		removeAlternateDisplayFlow(request);
		request.getSession().removeAttribute("registrationFlow");
		request.setAttribute("command", command);
		RequestDispatcher rd = request.getRequestDispatcher("confirm?type=confirm");
    	rd.forward(request, response);
    	return null;

	}

	private void setAlternateDisplayOrder(HttpServletRequest request, List order){
		request.getSession().setAttribute("registrationAltOrder", order);
	}
	private void removeAlternateDisplayFlow(HttpServletRequest request){
		request.getSession().removeAttribute("registrationAltFlow");
	}
	private boolean isResumeFlow(HttpServletRequest request){
		if(request.getParameter("resumeFlow")!=null){
			if(!isNewRegistration(request)){
				String id=request.getParameter("registrationId");
				if(id!=null){
					int regId=Integer.parseInt(id);
					StudySubject command=studySubjectDao.getById(regId);
					String formAttrName = getFormSessionAttributeName(request);
					request.getSession().setAttribute(formAttrName, command);
					if(request.getSession().getAttribute("registrationFlow")==null)
						request.getSession().setAttribute("registrationFlow", getFlow());
				}
			}
			return true;
		}
		return false;
	}
	
	private void buildCommandObject(StudySubject studySubject){
		if (logger.isDebugEnabled()) {
			logger.debug("buildCommandObject(StudySubject studySubject) - ResumeFlow"); //$NON-NLS-1$
		}
		if (logger.isDebugEnabled()) {
			logger.debug("buildCommandObject(StudySubject studySubject) - extracting eligibility criteria from study..."); //$NON-NLS-1$
		}
		List criterias=studySubject.getStudySite().getStudy().getIncCriterias();
		for(int i=0 ; i<criterias.size() ; i++){
			SubjectEligibilityAnswer subjectEligibilityAnswer=new SubjectEligibilityAnswer();
			subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria)criterias.get(i));
			studySubject.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
		}
		criterias=studySubject.getStudySite().getStudy().getExcCriterias();
		for(int i=0 ; i<criterias.size() ; i++){
			SubjectEligibilityAnswer subjectEligibilityAnswer=new SubjectEligibilityAnswer();
			subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria)criterias.get(i));
			studySubject.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("buildCommandObject(StudySubject studySubject) - studySubject.getParticipant().getPrimaryIdentifier()" + studySubject.getParticipant().getPrimaryIdentifier()); //$NON-NLS-1$
		}
		List<StratificationCriterion> stratifications=studySubject.getStudySite().getStudy().getStratificationCriteria();
		for(StratificationCriterion stratificationCriterion : stratifications){
			stratificationCriterion.getPermissibleAnswers().size();
			SubjectStratificationAnswer subjectStratificationAnswer=new SubjectStratificationAnswer();
			subjectStratificationAnswer.setStratificationCriterion(stratificationCriterion);
			studySubject.addSubjectStratificationAnswers(subjectStratificationAnswer);
		}
	}
	
	private void updateCommandObject(StudySubject studySubject){
		if(studySubject.getScheduledArms().size()==0){
			studySubject.addScheduledArm(new ScheduledArm(studySubject));
		}
		if(studySubject.getDiseaseHistory()==null){
			studySubject.setDiseaseHistory(new DiseaseHistory());
		}
	}
	
	private void checkCollections(StudySubject studySubject){
		studySubject.getStudySite().getStudy().getStudyDiseases().size();
		for(TreatmentEpoch e:studySubject.getStudySite().getStudy().getTreatmentEpochs()){
			e.getArms().size();
		}
		studySubject.getParticipant().getStudySubjects().size();
		studySubject.getStudySite().getStudySubjects().size();
		studySubject.getSubjectEligibilityAnswers().size();
		studySubject.getSubjectStratificationAnswers().size();
		for(StratificationCriterion stratificationCriterion:studySubject.getStudySite().getStudy().getStratificationCriteria()){
			stratificationCriterion.getPermissibleAnswers().size();
		}
		studySubject.getStudySite().getStudy().getIncCriterias().size();
		studySubject.getStudySite().getStudy().getExcCriterias().size();
		studySubject.getStudySite().getStudy().getIdentifiers().size();
	}
	private boolean isNewRegistration(HttpServletRequest request){
		if(request.getParameter("registrationId")!=null){
			return false;
		}
		return true;
	}
	
	protected String getResumedFlowAttrName(){
		return this.getClass().getName()+"-ResumedFlow";
	}
}*/