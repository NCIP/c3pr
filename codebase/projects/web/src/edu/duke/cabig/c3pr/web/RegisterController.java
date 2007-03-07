/**
 * 
 */
package edu.duke.cabig.c3pr.web;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import com.semanticbits.security.grid.GridLoginContext;

import edu.duke.cabig.c3pr.dao.ArmDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;

/**
 * @author Kruttik 
 * 
 */
public class RegisterController extends AbstractWizardFormController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(RegisterController.class);

	private static Log log = LogFactory
			.getLog(RegisterController.class);

	private final String[] pages = { "reg_registration_details",
			"reg_check_eligibility", "reg_stratify", "reg_randomize",
			"reg_submit", "reg_confirm_reg", "reg_identifiers" };

	private final String[] viewNames = { "enrollView", "checkEligibilityView",
			"stratifyView", "randomizeView", "reviewAndSubmitView",
			"confirmationView", "identifiersView" };

	private ParticipantDao participantDao;

	private String isBroadcastEnable="true";
	private StudySiteDao studySiteDao;

	private ArmDao armDao;

	private MessageBroadcastServiceImpl messageBroadcaster;

	public MessageBroadcastServiceImpl getMessageBroadcaster() {
		return messageBroadcaster;
	}

	public void setMessageBroadcaster(
			MessageBroadcastServiceImpl messageBroadcaster) {
		this.messageBroadcaster = messageBroadcaster;
	}

	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(false));	
	}

	@Override
	protected boolean isFinishRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (request.getParameter("nextView") == null
				|| request.getParameter("nextView").equals(""))
			return false;
		String viewName = request.getParameter("nextView");
		if (viewName.equalsIgnoreCase("processFinish"))
			return true;
		return false;
	}

	public StudySiteDao getStudySiteDao() {
		return studySiteDao;
	}

	public void setStudySiteDao(StudySiteDao studySiteDao) {
		this.studySiteDao = studySiteDao;
	}

	@Override
	protected int getTargetPage(HttpServletRequest request, int no) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("getTargetPage(HttpServletRequest, int) - getTargetPage() function called...."); //$NON-NLS-1$
		}
		String viewName = request.getParameter("nextView");
		for (int i = 0; i < viewNames.length; i++) {
			if (viewNames[i].equals(viewName)) {
				if (logger.isDebugEnabled()) {
					logger
							.debug("getTargetPage(HttpServletRequest, int) - ViewName in request is : " + viewName + " at index " + i); //$NON-NLS-1$ //$NON-NLS-2$
				}
				return i;
			}
		}
		return 0;
		// TODO Auto-generated method stub

	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		StudyParticipantAssignment studyParticipantAssignment = new StudyParticipantAssignment();
		studyParticipantAssignment.setEligibilityIndicator(new Boolean(false));
		StudySite studySite = null;
		Participant participant = null;
		if (request.getParameter("studySiteId") != null
				&& request.getParameter("participantId") != null) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("formBackingObject(HttpServletRequest) - Parameters found as.." + request.getParameter("studySiteId") + "  and " + request.getParameter("participantId")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
			if (studySiteDao != null) {
				studySite = studySiteDao.getById(Integer.parseInt(request
						.getParameter("studySiteId")));
				if (logger.isDebugEnabled()) {
					logger
							.debug("formBackingObject(HttpServletRequest) - RoleCode = " + studySite.getRoleCode()); //$NON-NLS-1$
				}
			} else if (logger.isDebugEnabled()) {
				logger
						.debug("formBackingObject(HttpServletRequest) - studySiteDao is null"); //$NON-NLS-1$
			}
			if (participantDao != null) {
				participant = participantDao.getById(Integer.parseInt(request
						.getParameter("participantId")),true);
				if (logger.isDebugEnabled()) {
					logger
							.debug("formBackingObject(HttpServletRequest) - First Name = " + participant.getFirstName()); //$NON-NLS-1$
				}
			} else if (logger.isDebugEnabled()) {
				logger
						.debug("formBackingObject(HttpServletRequest) - participantDao is null"); //$NON-NLS-1$
			}
			int size = participant.getStudyParticipantAssignments().size();
			if (logger.isDebugEnabled()) {
				logger
						.debug("formBackingObject(HttpServletRequest) - -------------participant.getStudyParticipantAssignments().size() is " + size + "---------------"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			int size1 = studySite.getStudyParticipantAssignments().size();
			if (logger.isDebugEnabled()) {
				logger
						.debug("formBackingObject(HttpServletRequest) - -------------studySite.getStudyParticipantAssignments().size() is " + size1 + "---------------"); //$NON-NLS-1$ //$NON-NLS-2$
			}

			studyParticipantAssignment.setStudySite(studySite);
			studyParticipantAssignment.setParticipant(participant);
		}

		List<Epoch> list = studyParticipantAssignment.getStudySite().getStudy()
				.getEpochs();
		if (logger.isDebugEnabled()) {
			logger
					.debug("formBackingObject(HttpServletRequest) - " + list.size()); //$NON-NLS-1$
		}
		for (int i = 0; i < list.size(); i++) {
			Epoch e = (Epoch) list.get(i);
			if (logger.isDebugEnabled()) {
				logger
						.debug("formBackingObject(HttpServletRequest) - " + e.getName()); //$NON-NLS-1$
			}
			if (e.getName().equals("Treatment")) {
				for (int j = 0; j < e.getArms().size(); j++) {
					if (logger.isDebugEnabled()) {
						logger
								.debug("formBackingObject(HttpServletRequest) - " + e.getArms().get(j).getName()); //$NON-NLS-1$
					}
					if (logger.isDebugEnabled()) {
						logger
								.debug("formBackingObject(HttpServletRequest) - --------------------------------------Arms available-----------------------------------------"); //$NON-NLS-1$
					}

				}
				return studyParticipantAssignment;
			}
		}
		/*
		 * if(studyParticipantAssignment.getStudySite().getStudy().getEpochs().get(0).getArms().size()!=0){
		 * System.out.println("--------------------------------------Arms
		 * available-----------------------------------------"); return
		 * studyParticipantAssignment; }
		 */if (logger.isDebugEnabled()) {
			logger
					.debug("formBackingObject(HttpServletRequest) - --------------------------------------Arms not available-----------------------------------------"); //$NON-NLS-1$
		}
		Arm arm1 = new Arm();
		arm1.setId(11);
		arm1.setName("A_MOCK");
		Arm arm2 = new Arm();
		arm2.setName("B_MOCK");
		arm2.setId(22);
		ArrayList<Arm> arms = new ArrayList<Arm>();
		arms.add(arm1);
		arms.add(arm2);
		studyParticipantAssignment.getStudySite().getStudy().getEpochs().get(0)
				.setArms(arms);
		return studyParticipantAssignment;
	}

	@Override
	protected Map referenceData(HttpServletRequest request, int page)
			throws Exception {
		// TODO Auto-generated method stub
		Map refData = new HashMap();
		if (viewNames[page].equals("identifiersView")) {
			refData.put("identifiersSourceRefData",
					getIdentifiersSourceRefData());
			refData.put("identifiersTypeRefData", getIdentifiersTypeRefData());
		}
		if (viewNames[page].equals("confirmationView")) {
			String defaultProxy ="-1";
			if (request.getSession().getAttribute("gridProxy") != null)				
			{
				defaultProxy =(String)request.getSession().getAttribute("gridProxy");
			}
			refData.put("proxy", defaultProxy);
		}
		return refData;
	}

	private List<LOV> getIdentifiersSourceRefData() {
		List<LOV> col = new ArrayList<LOV>();
		LOV lov1 = new LOV("C3D", "C3D");
		col.add(lov1);
		return col;
	}

	private List<LOV> getIdentifiersTypeRefData() {
		List<LOV> col = new ArrayList<LOV>();
		LOV lov1 = new LOV("Patient Position", "Patient Position");
		col.add(lov1);
		return col;
	}

	private List<StringBean> getRandomizedList() {
		List<StringBean> col = new ArrayList<StringBean>();
		col.add(new StringBean("Y"));
		col.add(new StringBean("N"));
		return col;
	}

	public class LOV {

		private String code;

		private String desc;

		LOV(String code, String desc) {
			this.code = code;
			this.desc = desc;

		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	public class StringBean {

		String str;

		StringBean(String str) {
			this.str = str;
		}

		public void setStr(String str) {
			this.str = str;
		}

		public String getStr() {
			return str;
		}
	}

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

	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	public ArmDao getArmDao() {
		return armDao;
	}

	public void setArmDao(ArmDao armDao) {
		this.armDao = armDao;
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

	public String getIsBroadcastEnable() {
		return isBroadcastEnable;
	}

	public void setIsBroadcastEnable(String isBroadcastEnable) {
		this.isBroadcastEnable = isBroadcastEnable;
	}


}