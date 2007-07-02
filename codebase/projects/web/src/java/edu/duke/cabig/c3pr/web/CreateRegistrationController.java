package edu.duke.cabig.c3pr.web;

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
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.service.ParticipantService;
import edu.duke.cabig.c3pr.service.impl.ParticipantServiceImpl;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * @author Ramakrishna
 * 
 */

public class CreateRegistrationController<C extends StudyParticipantAssignment> extends RegistrationController<C> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CreateRegistrationController.class);

	private static Log log = LogFactory.getLog(CreateRegistrationController.class);
	
	private ParticipantService participantService;
	
	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService;
	}

	public CreateRegistrationController() {
		super("Create Registration");
	}


	@Override
	protected Object currentFormObject(HttpServletRequest request, Object sessionFormObject) throws Exception {
		// TODO Auto-generated method stub
		StudyParticipantAssignment command=(StudyParticipantAssignment) sessionFormObject;
		if (sessionFormObject != null) {
			if(command.getId()!=null){
				getDao().reassociate(command);
				return command;
			}
			if(command.getParticipant()!=null)
				getParticipantDao().reassociate(command.getParticipant());
			if(command.getStudySite()!=null)
				getStudySiteDao().reassociate(command.getStudySite());
		}
		return sessionFormObject;
	}
	
	@Override
	protected boolean shouldSave(HttpServletRequest request, C command, Tab<C> tab) {
		if(getPrimaryDomainObject(command)==null)
			return false;
		return getPrimaryDomainObject(command).getId() != null;
	}
	@Override
	protected void intializeFlows(Flow flow) {
		SubFlowTab subflow=new SubFlowTab<StudyParticipantAssignment>("Search Subject or Study", "SearchSubjectStudy","registration/home","false"){
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty.getMap();
				Map<String, Object> refdata = new HashMap<String, Object>();
				refdata.put("searchTypeRefDataStudy", configMap.get("studySearchType"));
				refdata.put("searchTypeRefDataPrt", configMap.get("participantSearchType"));
				return refdata;
			}
		};
		subflow.setShowSummary("false");
		subflow.setShowLink("false");
		subflow.setSubFlow("true");
		flow.addTab(subflow);
		subflow=new SubFlowTab<StudyParticipantAssignment>("Select Study", "Select Study");
		subflow.setShowSummary("false");
		subflow.setShowLink("false");
		subflow.setSubFlow("true");
		flow.addTab(subflow);
		subflow=new SubFlowTab<StudyParticipantAssignment>("Select Subject", "Select Subject");
		subflow.setShowSummary("false");
		subflow.setShowLink("false");
		subflow.setSubFlow("true");
		flow.addTab(subflow);
		subflow=new SubFlowTab<StudyParticipantAssignment>("Enrollment Details", "Enrollment Details","registration/reg_registration_details");
		flow.addTab(subflow);
		subflow=new SubFlowTab<StudyParticipantAssignment>("Diseases", "Diseases","registration/reg_diseases");
		flow.addTab(subflow);
		subflow=new SubFlowTab<StudyParticipantAssignment>("Check Eligibility", "Check Eligibility","registration/reg_check_eligibility");
		flow.addTab(subflow);
		subflow=new SubFlowTab<StudyParticipantAssignment>("Stratify", "Stratify","registration/reg_stratify");
		flow.addTab(subflow);
		subflow=new SubFlowTab<StudyParticipantAssignment>("Randomize", "Randomize","registration/reg_randomize");
		flow.addTab(subflow);
		subflow=new SubFlowTab<StudyParticipantAssignment>("Review & Submit", "Review & Submit","registration/reg_submit");
		subflow.setShowSummary("false");
		flow.addTab(subflow);
		setFlow(flow);
	}

	@Override
	protected boolean isFormSubmission(HttpServletRequest request) {
		return super.isFormSubmission(request) || isResumeFlow(request);
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		StudyParticipantAssignment studyParticipantAssignment=new StudyParticipantAssignment();
		studyParticipantAssignment.setStudyParticipantIdentifier("SYS_GEN1");
		studyParticipantAssignment.setStartDate(new Date());
		studyParticipantAssignment.setEligibilityWaiverReasonText("Type Eligibility Waiver Reason.");
		removeAlternateDisplayFlow(request);
		request.getSession().setAttribute("registrationFlow", getFlow());
		studyParticipantAssignment.setDiseaseHistory(new DiseaseHistory());
		studyParticipantAssignment.addScheduledArm(new ScheduledArm(studyParticipantAssignment));
		return studyParticipantAssignment;
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
		StudyParticipantAssignment studyParticipantAssignment=(StudyParticipantAssignment)command;
		if(!super.isFormSubmission(request)&&isResumeFlow(request)){
			if(isNewRegistration(request))
				buildCommandObject(studyParticipantAssignment);
			else
				updateCommandObject(studyParticipantAssignment);
			checkCollections(studyParticipantAssignment);
		}
		if(tabShortTitle.equalsIgnoreCase("Check Eligibility")){
			studyParticipantAssignment.setEligibilityIndicator(evaluateEligibilityIndicator(studyParticipantAssignment));
		}
		if(tabShortTitle.equalsIgnoreCase("Stratify")){
			handleStratification(request,studyParticipantAssignment);
		}
		studyParticipantAssignment.setRegistrationStatus(ParticipantServiceImpl.evaluateStatus(studyParticipantAssignment));
		super.postProcessPage(request, command, errors, page);
	}
	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException arg3)
			throws Exception {
		// TODO Auto-generated method stub
		StudyParticipantAssignment studyParticipantAssignment = (StudyParticipantAssignment) command;
		participantService.createRegistration(studyParticipantAssignment);
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
					StudyParticipantAssignment command=registrationDao.getById(regId);
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
	
	private void buildCommandObject(StudyParticipantAssignment studyParticipantAssignment){
		if (logger.isDebugEnabled()) {
			logger.debug("buildCommandObject(StudyParticipantAssignment studyParticipantAssignment) - ResumeFlow"); //$NON-NLS-1$
		}
		if (logger.isDebugEnabled()) {
			logger.debug("buildCommandObject(StudyParticipantAssignment studyParticipantAssignment) - extracting eligibility criteria from study..."); //$NON-NLS-1$
		}
		List criterias=studyParticipantAssignment.getStudySite().getStudy().getIncCriterias();
		for(int i=0 ; i<criterias.size() ; i++){
			SubjectEligibilityAnswer subjectEligibilityAnswer=new SubjectEligibilityAnswer();
			subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria)criterias.get(i));
			studyParticipantAssignment.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
		}
		criterias=studyParticipantAssignment.getStudySite().getStudy().getExcCriterias();
		for(int i=0 ; i<criterias.size() ; i++){
			SubjectEligibilityAnswer subjectEligibilityAnswer=new SubjectEligibilityAnswer();
			subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria)criterias.get(i));
			studyParticipantAssignment.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("buildCommandObject(StudyParticipantAssignment studyParticipantAssignment) - studyParticipantAssignment.getParticipant().getPrimaryIdentifier()" + studyParticipantAssignment.getParticipant().getPrimaryIdentifier()); //$NON-NLS-1$
		}
		List<StratificationCriterion> stratifications=studyParticipantAssignment.getStudySite().getStudy().getStratificationCriteria();
		for(StratificationCriterion stratificationCriterion : stratifications){
			stratificationCriterion.getPermissibleAnswers().size();
			SubjectStratificationAnswer subjectStratificationAnswer=new SubjectStratificationAnswer();
			subjectStratificationAnswer.setStratificationCriterion(stratificationCriterion);
			studyParticipantAssignment.addSubjectStratificationAnswers(subjectStratificationAnswer);
		}
	}
	
	private void updateCommandObject(StudyParticipantAssignment studyParticipantAssignment){
		/*if(studyParticipantAssignment.getScheduledArms().size()==0){
			studyParticipantAssignment.addScheduledArm(new ScheduledArm(studyParticipantAssignment));
		}*/
		if(studyParticipantAssignment.getDiseaseHistory()==null){
			studyParticipantAssignment.setDiseaseHistory(new DiseaseHistory());
		}
	}
	
	private void checkCollections(StudyParticipantAssignment studyParticipantAssignment){
		studyParticipantAssignment.getStudySite().getStudy().getStudyDiseases().size();
		for(TreatmentEpoch e:studyParticipantAssignment.getStudySite().getStudy().getTreatmentEpochs()){
			e.getArms().size();
		}
		studyParticipantAssignment.getParticipant().getStudyParticipantAssignments().size();
		studyParticipantAssignment.getStudySite().getStudyParticipantAssignments().size();
		studyParticipantAssignment.getSubjectEligibilityAnswers().size();
		studyParticipantAssignment.getSubjectStratificationAnswers().size();
		for(StratificationCriterion stratificationCriterion:studyParticipantAssignment.getStudySite().getStudy().getStratificationCriteria()){
			stratificationCriterion.getPermissibleAnswers().size();
		}
		studyParticipantAssignment.getStudySite().getStudy().getIncCriterias().size();
		studyParticipantAssignment.getStudySite().getStudy().getExcCriterias().size();
		studyParticipantAssignment.getStudySite().getStudy().getIdentifiers().size();
	}
	private boolean isNewRegistration(HttpServletRequest request){
		if(request.getParameter("registrationId")!=null){
			return false;
		}
		return true;
	}
	private void handleStratification(HttpServletRequest request, StudyParticipantAssignment studyParticipantAssignment){
		for(int i=0 ; i<studyParticipantAssignment.getSubjectStratificationAnswers().size() ; i++){
			String id=request.getParameter("subjectStratificationAnswers["+i+"].stratificationCriterionAnswer");
			if(StringUtils.isEmpty(id))
				return;
			int tempId=Integer.parseInt(id);
			for(StratificationCriterionPermissibleAnswer answer : studyParticipantAssignment.getSubjectStratificationAnswers().get(i).getStratificationCriterion().getPermissibleAnswers()){
				if(answer.getId()==tempId)
					studyParticipantAssignment.getSubjectStratificationAnswers().get(i).setStratificationCriterionAnswer(answer);
			}
		}
	}
	private boolean evaluateEligibilityIndicator(StudyParticipantAssignment studyParticipantAssignment){
		boolean flag=true;
		List<SubjectEligibilityAnswer> answers=studyParticipantAssignment.getInclusionEligibilityAnswers();
		for(SubjectEligibilityAnswer subjectEligibilityAnswer:answers){
			String answerText=subjectEligibilityAnswer.getAnswerText();
			if(answerText==null||answerText.equalsIgnoreCase("")||(!answerText.equalsIgnoreCase("Yes")&&!answerText.equalsIgnoreCase("NA"))){
				flag=false;
				break;
			}
		}
		if(flag){
			answers=studyParticipantAssignment.getExclusionEligibilityAnswers();
			for(SubjectEligibilityAnswer subjectEligibilityAnswer:answers){
				String answerText=subjectEligibilityAnswer.getAnswerText();
				if(answerText==null||answerText.equalsIgnoreCase("")||(!answerText.equalsIgnoreCase("No")&&!answerText.equalsIgnoreCase("NA"))){
					flag=false;
					break;
				}
			}
		}
		return flag;
	}
	
	protected String getResumedFlowAttrName(){
		return this.getClass().getName()+"-ResumedFlow";
	}
}