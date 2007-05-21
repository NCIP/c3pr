package edu.duke.cabig.c3pr.web;

import java.util.Date;
import java.util.Enumeration;
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
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.service.ParticipantService;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab;

/**
 * @author Ramakrishna
 * 
 */

public class CreateRegistrationController extends RegistrationController {
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

	protected void intializeFlows(Flow<StudyParticipantAssignment> flow) {
		flow.addTab(new Tab<StudyParticipantAssignment>("Search Subject or Study", "SearchSubjectStudy","registration/home","false"){
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty.getMap();
				Map<String, Object> refdata = new HashMap<String, Object>();
				refdata.put("searchTypeRefDataStudy", configMap.get("studySearchType"));
				refdata.put("searchTypeRefDataPrt", configMap.get("participantSearchType"));
				return refdata;
			}
		});
		flow.addTab(new Tab<StudyParticipantAssignment>("Select Study", "Select Study"));
		flow.addTab(new Tab<StudyParticipantAssignment>("Select Subject", "Select Subject"));
		flow.addTab(new Tab<StudyParticipantAssignment>("Enrollment Details", "Enrollment Details","registration/reg_registration_details"));
		flow.addTab(new Tab<StudyParticipantAssignment>("Diseases", "Diseases","registration/reg_diseases"));
		flow.addTab(new Tab<StudyParticipantAssignment>("Check Eligibility", "Check Eligibility","registration/reg_check_eligibility"));
		flow.addTab(new Tab<StudyParticipantAssignment>("Stratify", "Stratify","registration/reg_stratify"));
		flow.addTab(new Tab<StudyParticipantAssignment>("Randomize", "Randomize","registration/reg_randomize"));
		flow.addTab(new Tab<StudyParticipantAssignment>("Review & Submit", "Review & Submit","registration/reg_submit"));
		flow.getTab(0).setShowSummary("false");
		flow.getTab(1).setShowSummary("false");
		flow.getTab(2).setShowSummary("false");
		flow.getTab(8).setShowSummary("false");
		flow.getTab(0).setShowLink("false");
		flow.getTab(1).setShowLink("false");
		flow.getTab(2).setShowLink("false");
		flow.getTab(0).setSubFlow("true");
		flow.getTab(1).setSubFlow("true");
		flow.getTab(2).setSubFlow("true");
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
		studyParticipantAssignment.addScheduledArm(new ScheduledArm());
		return studyParticipantAssignment;
	}
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest, int page) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> refdata = new HashMap<String, Object>();
		refdata.put("registrationTab", getFlow().getTab(page));
		return refdata;
	}
	
	@Override
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, String tabShortTitle) throws Exception {
		// TODO Auto-generated method stub
		StudyParticipantAssignment studyParticipantAssignment=(StudyParticipantAssignment)command;
		if(isResumeFlow(request)){
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
/*			for(SubjectStratificationAnswer subjectStratificationAnswer : studyParticipantAssignment.getSubjectStratificationAnswers()){
				System.out.println(subjectStratificationAnswer.getStratificationCriterion().getQuestionText()+" : "+subjectStratificationAnswer.getStratificationCriterionAnswer()!=null?subjectStratificationAnswer.getStratificationCriterionAnswer().getPermissibleAnswer():"Unanswered");
			}
*/		}
		if(tabShortTitle.equalsIgnoreCase("Diseases")){
			if (logger.isDebugEnabled()) {
				logger.debug("postProcessPage(HttpServletRequest, Object, Errors, String) - Request Params"); //$NON-NLS-1$
			}
			Enumeration e=request.getParameterNames();
			while(e.hasMoreElements()){
				String param=(String)e.nextElement();
				if (logger.isDebugEnabled()) {
					logger.debug("postProcessPage(HttpServletRequest, Object, Errors, String) - " + param + " : " + request.getParameter(param)); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			DiseaseHistory dh=studyParticipantAssignment.getDiseaseHistory();
			if(dh==null)
				return;
		}
	}
	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException arg3)
			throws Exception {
		// TODO Auto-generated method stub
		StudyParticipantAssignment studyParticipantAssignment = (StudyParticipantAssignment) command;
		if (logger.isDebugEnabled()) {
			logger.debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - in process finish"); //$NON-NLS-1$
		}
		studyParticipantAssignment.getParticipant().getStudyParticipantAssignments().size();
		studyParticipantAssignment.getParticipant().addStudyParticipantAssignment(studyParticipantAssignment);
		studyParticipantAssignment.setRegistrationStatus(evaluateStatus(studyParticipantAssignment));
		if(!hasDiseaseHistory(studyParticipantAssignment.getDiseaseHistory())){
			studyParticipantAssignment.setDiseaseHistory(null);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - Calling participant service"); //$NON-NLS-1$
		}
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

	private String evaluateStatus(StudyParticipantAssignment studyParticipantAssignment){
		String status="Complete";
		if(studyParticipantAssignment.getInformedConsentSignedDateStr().equals("")){
			return "Incomplete";
		}else if(studyParticipantAssignment.getTreatingPhysician()==null){
			return "Incomplete";
		}else if(studyParticipantAssignment.getScheduledArms().get(studyParticipantAssignment.getScheduledArms().size()-1).getArm()==null){
			studyParticipantAssignment.getScheduledArms().remove(studyParticipantAssignment.getScheduledArms().size()-1);
			return "Incomplete";
		}else if(!evaluateStratificationIndicator(studyParticipantAssignment)){
			return "Incomplete";
		}else if(studyParticipantAssignment.getEligibilityIndicator()){
			List<SubjectEligibilityAnswer> criterias=studyParticipantAssignment.getSubjectEligibilityAnswers();
			if (logger.isDebugEnabled()) {
				logger.debug("evaluateStatus(StudyParticipantAssignment) - studyParticipantAssignment.getEligibilityIndicator():" + studyParticipantAssignment.getEligibilityIndicator()); //$NON-NLS-1$
			}
			studyParticipantAssignment.setEligibilityWaiverReasonText("");
			if (logger.isDebugEnabled()) {
				logger.debug("evaluateStatus(StudyParticipantAssignment) - printing answers....."); //$NON-NLS-1$
			}
			for(int i=0 ; i<criterias.size() ; i++){
				if (logger.isDebugEnabled()) {
					logger.debug("evaluateStatus(StudyParticipantAssignment) - question : " + criterias.get(i).getEligibilityCriteria().getQuestionText()); //$NON-NLS-1$
				}
				if (logger.isDebugEnabled()) {
					logger.debug("evaluateStatus(StudyParticipantAssignment) - ----- answer : " + criterias.get(i).getAnswerText()); //$NON-NLS-1$
				}
			}
		}else if(!studyParticipantAssignment.getEligibilityIndicator()){
			if(studyParticipantAssignment.getEligibilityWaiverReasonText()==null||studyParticipantAssignment.getEligibilityWaiverReasonText().equals(""))
			return "Incomplete";
		}
		return status;
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
		if(studyParticipantAssignment.getScheduledArms().size()==0){
			studyParticipantAssignment.addScheduledArm(new ScheduledArm());
		}
	}
	
	private void checkCollections(StudyParticipantAssignment studyParticipantAssignment){
		studyParticipantAssignment.getStudySite().getStudy().getStudyDiseases().size();
		for(Epoch e:studyParticipantAssignment.getStudySite().getStudy().getEpochs()){
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
	}
	private boolean isNewRegistration(HttpServletRequest request){
		if(request.getParameter("registrationId")!=null){
			return false;
		}
		return true;
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
	private boolean evaluateStratificationIndicator(StudyParticipantAssignment studyParticipantAssignment){
		List<SubjectStratificationAnswer> answers=studyParticipantAssignment.getSubjectStratificationAnswers();
		for(SubjectStratificationAnswer subjectStratificationAnswer:answers){
			if(subjectStratificationAnswer.getStratificationCriterionAnswer()==null){
				return false;
			}
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
	private boolean hasDiseaseHistory(DiseaseHistory diseaseHistory){
		if(diseaseHistory.getAnatomicSite()==null&&(diseaseHistory.getOtherPrimaryDiseaseSiteCode()==null||diseaseHistory.getOtherPrimaryDiseaseSiteCode().equals(""))&&
				(diseaseHistory.getOtherPrimaryDiseaseCode()==null||diseaseHistory.getOtherPrimaryDiseaseCode().equals(""))&&diseaseHistory.getStudyDisease()==null)
				return false;
		return true;
	}
}