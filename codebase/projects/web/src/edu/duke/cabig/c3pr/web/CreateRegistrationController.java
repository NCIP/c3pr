package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab;

/**
 * @author Ramakrishna
 * 
 */

public class CreateRegistrationController extends RegistrationController {

	private static Log log = LogFactory.getLog(CreateRegistrationController.class);
	
	private String isBroadcastEnable="true";
	private MessageBroadcastServiceImpl messageBroadcaster;

	public MessageBroadcastServiceImpl getMessageBroadcaster() {
		return messageBroadcaster;
	}

	public void setMessageBroadcaster(
			MessageBroadcastServiceImpl messageBroadcaster) {
		this.messageBroadcaster = messageBroadcaster;
	}

	public String getIsBroadcastEnable() {
		return isBroadcastEnable;
	}

	public void setIsBroadcastEnable(String isBroadcastEnable) {
		this.isBroadcastEnable = isBroadcastEnable;
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
		flow.addTab(new Tab<StudyParticipantAssignment>("Check Eligibility", "Check Eligibility","registration/reg_check_eligibility"));
		flow.addTab(new Tab<StudyParticipantAssignment>("Stratify", "Stratify","registration/reg_stratify"));
		flow.addTab(new Tab<StudyParticipantAssignment>("Review & Submit", "Review & Submit","registration/reg_submit"));
		flow.getTab(0).setShowSummary("false");
		flow.getTab(1).setShowSummary("false");
		flow.getTab(2).setShowSummary("false");
		flow.getTab(6).setShowSummary("false");
		flow.getTab(0).setShowLink("false");
		flow.getTab(1).setShowLink("false");
		flow.getTab(2).setShowLink("false");
		flow.getTab(6).setShowLink("false");
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
		studyParticipantAssignment.setStartDate(new Date());
		studyParticipantAssignment.setEligibilityIndicator(true);
		studyParticipantAssignment.setEligibilityWaiverReasonText("Type Eligibility Waiver Reason.");
		removeAlternateDisplayFlow(request);
		request.getSession().setAttribute("registrationFlow", getFlow());
		request.getSession().setAttribute("studyParticipantAssignments", studyParticipantAssignment);
		System.out.println("------------------------registration flow set------------------");			
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
			System.out.println("---------ResumeFlow---------------------");
			System.out.println("building the command object..");
			System.out.println("extracting eligibility criteria from study...");
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
//			studyParticipantAssignment.getParticipant().getIdentifiers();
//			studyParticipantAssignment.getStudySite().getStudy().getIdentifiers();
			studyParticipantAssignment.setStartDate(new Date());
			studyParticipantAssignment.setStudyParticipantIdentifier("SYS_GEN1");
			System.out.println("studyParticipantAssignment.getParticipant().getPrimaryIdentifier()"+studyParticipantAssignment.getParticipant().getPrimaryIdentifier());
		}
		if(tabShortTitle.equalsIgnoreCase("Enrollment Details")){
			System.out.println("-------In Enrollment Details post process-----------");
			System.out.println("---------studyParticipantAssignment.getEligibilityIndicator():"+studyParticipantAssignment.getEligibilityIndicator()+"---------");
		}
		if(tabShortTitle.equalsIgnoreCase("Check Eligibility")){
			System.out.println("-------In CheckEligibility post process-----------");
			System.out.println("---------studyParticipantAssignment.getEligibilityIndicator():"+studyParticipantAssignment.getEligibilityIndicator()+"---------");
//			studyParticipantAssignment.setEligibilityIndicator(!studyParticipantAssignment.getEligibilityIndicator());
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
/*	@Override
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, String tabShortTitle) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Inside postProcessPage.......tabShortTitle is:"+tabShortTitle);
		String viewName = request.getParameter("nextView");
		if(tabShortTitle.equalsIgnoreCase("SearchSubjectStudy")){
		}
		if (viewName.equalsIgnoreCase("confirmationView")) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("postProcessPage(HttpServletRequest, Object, Errors, int) - In postProcessPage()..."); //$NON-NLS-1$
			}
			if (logger.isDebugEnabled()) {
				logger
						.debug("postProcessPage(HttpServletRequest, Object, Errors, int) - no of registered pages: " + getPages().length); //$NON-NLS-1$
			}
			if (logger.isDebugEnabled()) {
				logger
						.debug("postProcessPage(HttpServletRequest, Object, Errors, int) - " + viewName); //$NON-NLS-1$
			}
			StudyParticipantAssignment studyParticipantAssignment = (StudyParticipantAssignment) command;
			int size = studyParticipantAssignment.getParticipant()
					.getStudyParticipantAssignments().size();
			if (logger.isDebugEnabled()) {
				logger
						.debug("postProcessPage(HttpServletRequest, Object, Errors, int) - -------------postProcessPage() participant.getStudyParticipantAssignments().size() is " + size + "---------------"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			int size1 = studyParticipantAssignment.getStudySite()
					.getStudyParticipantAssignments().size();
			if (logger.isDebugEnabled()) {
				logger
						.debug("postProcessPage(HttpServletRequest, Object, Errors, int) - -------------postProcessPage() studySite.getStudyParticipantAssignments().size() is " + size1 + "---------------"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			studyParticipantAssignment.getParticipant()
					.addStudyParticipantAssignment(studyParticipantAssignment);
			studyParticipantAssignment.setStartDate(new Date());
			studyParticipantAssignment
					.setStudyParticipantIdentifier("SYS_GEN1");
			participantDao.save(studyParticipantAssignment.getParticipant());
			studyParticipantAssignment
					.setStudyParticipantIdentifier(studyParticipantAssignment
							.getId()
							+ "");
			if(isBroadcastEnable.equalsIgnoreCase("true")){
				String xml = "";
				try {
					xml = XMLUtils.toXml(studyParticipantAssignment);
					System.out
							.println("--------------------XML for Registration--------------------");
					System.out.println(xml);
					messageBroadcaster.initialize();
					messageBroadcaster.broadcast(xml);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (viewName.equalsIgnoreCase("randomizeView")) {
			StudyParticipantAssignment studyParticipantAssignment = (StudyParticipantAssignment) command;
			Vector result = messageBroadcaster.getBroadcastStatus();
			if (result != null) {
				for (int i = 0; i < result.size(); i++) {
					String msg = (String) result.get(i);
					System.out.println((i + 1) + ".");
					System.out.println(msg);
					System.out.println("--------");
				}
			}
			if (request.getParameter("actionType") == null) {
				ScheduledArm scheduledArm = new ScheduledArm();
				scheduledArm.setEligibilityIndicator("true");
				scheduledArm.setStartDate(new Date());
				Arm arm = new Arm();
				scheduledArm.setArm(arm);
				scheduledArm
						.setStudyParticipantAssignment(studyParticipantAssignment);
				studyParticipantAssignment.addScheduledArm(scheduledArm);
				return;
			}
			if (!request.getParameter("actionType").equals("save")) {
				System.out
						.println("--------------------Recieved randomizeView with actionType not save---------------------------");
				ScheduledArm scheduledArm = new ScheduledArm();
				scheduledArm.setEligibilityIndicator("true");
				scheduledArm.setStartDate(new Date());
				Arm arm = new Arm();
				scheduledArm.setArm(arm);
				scheduledArm
						.setStudyParticipantAssignment(studyParticipantAssignment);
				studyParticipantAssignment.addScheduledArm(scheduledArm);
				return;
			}
			System.out
					.println("--------------------Recieved randomizeView with actionType as save---------------------------");
			int scheduledArmsSize = studyParticipantAssignment
					.getScheduledArms().size();
			int armId = studyParticipantAssignment.getScheduledArms().get(
					scheduledArmsSize - 1).getArm().getId();
			if (armId >= 0) {
				System.out
						.println("------------------Randomization selected---------------------");
				Arm arm = null;
				List<Epoch> list = studyParticipantAssignment.getStudySite()
						.getStudy().getEpochs();
				if (logger.isDebugEnabled()) {
					logger
							.debug("postProcessPage(HttpServletRequest, Object, Errors, int) - " + list.size()); //$NON-NLS-1$
				}
				List<Arm> arms = null;
				for (int i = 0; i < list.size(); i++) {
					Epoch e = (Epoch) list.get(i);
					if (logger.isDebugEnabled()) {
						logger
								.debug("postProcessPage(HttpServletRequest, Object, Errors, int) - " + e.getName()); //$NON-NLS-1$
					}
					if (e.getName().equals("Treatment")) {
						for (int j = 0; j < e.getArms().size(); j++) {
							if (logger.isDebugEnabled()) {
								logger
										.debug("postProcessPage(HttpServletRequest, Object, Errors, int) - " + e.getArms().get(j).getName()); //$NON-NLS-1$
							}
							if (logger.isDebugEnabled()) {
								logger
										.debug("postProcessPage(HttpServletRequest, Object, Errors, int) - --------------------------------------postProcessPage() Arms available-----------------------------------------"); //$NON-NLS-1$
							}

						}
						arms = (List<Arm>) (studyParticipantAssignment
								.getStudySite().getStudy().getEpochs().get(i)
								.getArms());
					}
				}
				boolean flag = true;
				for (int i = 0; i < arms.size(); i++) {
					if (arms.get(i).getId() == armId) {
						arm = arms.get(i);
						flag = false;
					}
				}
				if (flag)
					arm = armDao.getById(armId);
				studyParticipantAssignment.getScheduledArms().get(
						scheduledArmsSize - 1).setArm(arm);
				System.out
						.println("-----------------Saving scheduled arm------------");
				participantDao
						.save(studyParticipantAssignment.getParticipant());
				System.out
						.println("-----------------Saved scheduled arm------------");
			} else {
				System.out
						.println("------------------No Randomization done---------------------");
				studyParticipantAssignment.getScheduledArms().remove(
						scheduledArmsSize - 1);
			}

		}
		if (viewName.equalsIgnoreCase("identifiersView")) {
			System.out
					.println("--------------------Recieved Idententifiers View---------------------------");
			StudyParticipantAssignment studyParticipantAssignment = (StudyParticipantAssignment) command;
			if (request.getParameter("_action") != null) {
				System.out
						.println("--------------------Recieved Idententifiers View with _action---------------------------");
				if (request.getParameter("_action").equals("addIdentifier")) {
					System.out
							.println("--------------------Recieved Idententifiers View with _action as addIdentifier---------------------------");
					studyParticipantAssignment.addIdentifier(new Identifier());
				} else if (request.getParameter("_action").equals(
						"removeIdentifier")) {
					System.out
							.println("--------------------Recieved Idententifiers View with _action as removeIdentifier---------------------------");
					studyParticipantAssignment.getIdentifiers()
							.remove(
									Integer.parseInt(request
											.getParameter("_selected")));
				}
				return;
			}
			System.out
					.println("--------------------Recieved Idententifiers View with _action as null---------------------------");
			try {
				Vector result = messageBroadcaster.getBroadcastStatus();
				System.out.println("Messages from ESB.....");
				if (result != null) {
					for (int i = 0; i < result.size(); i++) {
						String msg = (String) result.get(i);
						System.out.println((i + 1) + ".");
						System.out.println(msg);
						Document document;
						XPath xpath;
						String source = "";
						document = new SAXBuilder()
								.build(new StringReader(msg));
						xpath = XPath
								.newInstance("/ns:registration/ns:identifier/ns:source");
						xpath.addNamespace("ns",
								"http://semanticbits.com/registration.xsd");
						source = xpath.valueOf(document);
						if (source.equalsIgnoreCase("C3D")) {
							System.out.println("Message from C3D recieved...");
							xpath = XPath
									.newInstance("/ns:registration/ns:identifier/ns:value");
							String value = xpath.valueOf(document);
							if (value.indexOf("-1") == 0) {
								System.out
										.println("Patient position not found by C3D");
							} else {
								xpath = XPath
										.newInstance("/ns:registration/ns:identifier/ns:type");
								String type = xpath.valueOf(document);
								Identifier id = new Identifier();
								id.setSource(source);
								id.setType(type);
								id.setValue(value);
								studyParticipantAssignment.addIdentifier(id);
								participantDao.save(studyParticipantAssignment.getParticipant());
							}
							System.out.println(msg);
							System.out.println("--------");
						}else {
							System.out.println("ESB message not from..");
						}
					}
				} else {
					System.out.println("no avalbale result from ESB..");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(studyParticipantAssignment.getIdentifiers().size()==0)
				studyParticipantAssignment.addIdentifier(new Identifier());
		}
	}
*/	
	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException arg3)
			throws Exception {
		// TODO Auto-generated method stub
		StudyParticipantAssignment studyParticipantAssignment = (StudyParticipantAssignment) command;
		System.out.println("----------------in process finish--------------");
		studyParticipantAssignment.getParticipant().getStudyParticipantAssignments().size();
		studyParticipantAssignment.getParticipant().addStudyParticipantAssignment(studyParticipantAssignment);
		studyParticipantAssignment.setRegistrationStatus(evaluateStatus(studyParticipantAssignment));
		participantDao.save(studyParticipantAssignment.getParticipant());
		studyParticipantAssignment.setStudyParticipantIdentifier(studyParticipantAssignment.getId()+ "");
		if(isBroadcastEnable.equalsIgnoreCase("true")){
			String xml = "";
			try {
				xml = XMLUtils.toXml(studyParticipantAssignment);
				System.out
						.println("--------------------XML for Registration--------------------");
				System.out.println(xml);
				messageBroadcaster.initialize();
				messageBroadcaster.broadcast(xml);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		}else if(studyParticipantAssignment.getTreatingPhysician().equals("")){
			return "Incomplete";
		}else if(studyParticipantAssignment.getEligibilityIndicator()){
			List<SubjectEligibilityAnswer> criterias=studyParticipantAssignment.getSubjectEligibilityAnswers();
			System.out.println("studyParticipantAssignment.getEligibilityIndicator():"+studyParticipantAssignment.getEligibilityIndicator());
			studyParticipantAssignment.setEligibilityWaiverReasonText("");
			System.out.println("printing answers.....");
			for(int i=0 ; i<criterias.size() ; i++){
				System.out.print("question : "+criterias.get(i).getEligibilityCriteria().getQuestionText());
				System.out.println("----- answer : "+criterias.get(i).getAnswerText());
				if(criterias.get(i).getAnswerText()==null){
					if(criterias.get(i).getAnswerText().equals("")){
						return "Incomplete";
					}
				}
			}
		}else if(!studyParticipantAssignment.getEligibilityIndicator()&&studyParticipantAssignment.getEligibilityWaiverReasonText()!=null){
			if(studyParticipantAssignment.getEligibilityWaiverReasonText().equals(""))
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
		if(request.getParameter("resumeFlow")!=null)
			return true;
		return false;
	}
}

