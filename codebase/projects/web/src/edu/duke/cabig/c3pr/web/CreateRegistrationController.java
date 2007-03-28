package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyParticipantAssignmentDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AbstractTabbedFlowFormController;
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
		flow.addTab(new Tab<StudyParticipantAssignment>("Details", "Details",
				"registration/reg_details_study_participant") {
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty
						.getMap();

				Map<String, Object> refdata = new HashMap<String, Object>();

				refdata.put("administrativeGenderCode", configMap
						.get("administrativeGenderCode"));
				refdata
						.put("ethnicGroupCode", configMap
								.get("ethnicGroupCode"));
				refdata.put("raceCode", configMap.get("raceCode"));
				refdata.put("source", healthcareSiteDao.getAll());
				refdata.put("searchTypeRefData", configMap
						.get("participantSearchType"));
				refdata.put("identifiersTypeRefData", configMap
						.get("participantIdentifiersType"));
				;

				return refdata;
			}
		});
		flow.addTab(new Tab<StudyParticipantAssignment>("Identifiers",
				"Identifiers", "registration/reg_details_identifiers") {
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty
				.getMap();

		Map<String, Object> refdata = new HashMap<String, Object>();

		refdata.put("administrativeGenderCode", configMap
				.get("administrativeGenderCode"));
		refdata
				.put("ethnicGroupCode", configMap
						.get("ethnicGroupCode"));
		refdata.put("raceCode", configMap.get("raceCode"));
		refdata.put("source", healthcareSiteDao.getAll());
		refdata.put("searchTypeRefData", configMap
				.get("participantSearchType"));
		refdata.put("identifiersTypeRefData", configMap
				.get("participantIdentifiersType"));
		;

				return refdata;
			}
		});
		flow.addTab(new Tab<StudyParticipantAssignment>("Eligibility",
				"Eligibility", "registration/reg_details_eligibility") {
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty
				.getMap();

		Map<String, Object> refdata = new HashMap<String, Object>();

		refdata.put("administrativeGenderCode", configMap
				.get("administrativeGenderCode"));
		refdata
				.put("ethnicGroupCode", configMap
						.get("ethnicGroupCode"));
		refdata.put("raceCode", configMap.get("raceCode"));
		refdata.put("source", healthcareSiteDao.getAll());
		refdata.put("searchTypeRefData", configMap
				.get("participantSearchType"));
		refdata.put("identifiersTypeRefData", configMap
				.get("participantIdentifiersType"));
		;

				return refdata;
			}
		});
		flow.addTab(new Tab<StudyParticipantAssignment>("Stratification",
				"Stratification", "registration/reg_details_stratification") {
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty
				.getMap();

		Map<String, Object> refdata = new HashMap<String, Object>();

		refdata.put("administrativeGenderCode", configMap
				.get("administrativeGenderCode"));
		refdata
				.put("ethnicGroupCode", configMap
						.get("ethnicGroupCode"));
		refdata.put("raceCode", configMap.get("raceCode"));
		refdata.put("source", healthcareSiteDao.getAll());
		refdata.put("searchTypeRefData", configMap
				.get("participantSearchType"));
		refdata.put("identifiersTypeRefData", configMap
				.get("participantIdentifiersType"));
		;

				return refdata;
			}

		});
		flow.addTab(new Tab<StudyParticipantAssignment>("Randomization",
				"Randomization", "registration/reg_details_randomization") {
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty
				.getMap();

		Map<String, Object> refdata = new HashMap<String, Object>();

		refdata.put("administrativeGenderCode", configMap
				.get("administrativeGenderCode"));
		refdata
				.put("ethnicGroupCode", configMap
						.get("ethnicGroupCode"));
		refdata.put("raceCode", configMap.get("raceCode"));
		refdata.put("source", healthcareSiteDao.getAll());
		refdata.put("searchTypeRefData", configMap
				.get("participantSearchType"));
		refdata.put("identifiersTypeRefData", configMap
				.get("participantIdentifiersType"));
		;

				return refdata;
			}

		});
		flow.addTab(new Tab<StudyParticipantAssignment>("Status", "Status",
				"registration/reg_details_status") {
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty
				.getMap();

		Map<String, Object> refdata = new HashMap<String, Object>();

		refdata.put("administrativeGenderCode", configMap
				.get("administrativeGenderCode"));
		refdata
				.put("ethnicGroupCode", configMap
						.get("ethnicGroupCode"));
		refdata.put("raceCode", configMap.get("raceCode"));
		refdata.put("source", healthcareSiteDao.getAll());
		refdata.put("searchTypeRefData", configMap
				.get("participantSearchType"));
		refdata.put("identifiersTypeRefData", configMap
				.get("participantIdentifiersType"));
		;

				return refdata;
			}

		});
		setFlow(flow);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void postProcessPage(HttpServletRequest request, Object command,
			Errors arg2, int pageNo) throws Exception {
		// TODO Auto-generated method stub
		String viewName = request.getParameter("nextView");
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
/*			try {
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
*/			if(studyParticipantAssignment.getIdentifiers().size()==0)
				studyParticipantAssignment.addIdentifier(new Identifier());
		}
	}
	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException arg3)
			throws Exception {
		// TODO Auto-generated method stub
		StudyParticipantAssignment studyParticipantAssignment = (StudyParticipantAssignment) command;
		participantDao.save(studyParticipantAssignment.getParticipant());
		response.sendRedirect("searchAndRegister");
		return null;
	}

}
