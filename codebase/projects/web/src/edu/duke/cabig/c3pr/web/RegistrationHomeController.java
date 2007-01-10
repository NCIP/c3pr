/**
 * 
 */
package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import edu.duke.cabig.c3pr.dao.ArmDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;

/**
 * @author Kruttik
 * 
 */
public class RegistrationHomeController extends AbstractWizardFormController {
	private static Log log = LogFactory
			.getLog(RegistrationHomeController.class);

	private final String[] pages = { "reg_check_eligibility", "reg_stratify",
			"reg_randomize", "reg_submit", "reg_confirm_reg" };

	private final String[] viewNames = { "checkEligibilityView",
			"stratifyView", "randomizeView", "reviewAndSubmitView",
			"confirmationView" };

	private ParticipantDao participantDao;

	private StudySiteDao studySiteDao;

	private ArmDao armDao;

	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		binder.registerCustomEditor(Date.class, ControllerTools
				.getDateEditor(true));
		ControllerTools.registerDomainObjectEditor(binder, armDao);
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
		System.out.println("getTargetPage() function called....");
		String viewName = request.getParameter("nextView");
		for (int i = 0; i < viewNames.length; i++) {
			if (viewNames[i].equals(viewName)) {
				System.out.println("ViewName in request is : " + viewName
						+ " at index " + i);
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
		studyParticipantAssignment
				.setStudyParticipantIdentifier("TESTID_Kruttik_BIG");
		studyParticipantAssignment.setStartDate(new Date());
		studyParticipantAssignment.setEligibilityIndicator(new Boolean(false));
		StudySite studySite = null;
		Participant participant = null;
		if (request.getParameter("studySiteId") != null
				&& request.getParameter("participantId") != null) {
			System.out.println("Parameters found as.."
					+ request.getParameter("studySiteId") + "  and "
					+ request.getParameter("participantId"));
			if (studySiteDao != null) {
				studySite = studySiteDao.getById(Integer.parseInt(request
						.getParameter("studySiteId")));
				System.out.println("RoleCode = " + studySite.getRoleCode());
			} else
				System.out.println("studySiteDao is null");
			if (participantDao != null) {
				participant = participantDao.getById(Integer.parseInt(request
						.getParameter("participantId")));
				System.out
						.println("First Name = " + participant.getFirstName());
			} else
				System.out.println("participantDao is null");
			int size = participant.getStudyParticipantAssignments().size();
			System.out
					.println("-------------participant.getStudyParticipantAssignments().size() is "
							+ size + "---------------");
			int size1 = studySite.getStudyParticipantAssignments().size();
			System.out
					.println("-------------studySite.getStudyParticipantAssignments().size() is "
							+ size1 + "---------------");

			studyParticipantAssignment.setStudySite(studySite);
			studyParticipantAssignment.setParticipant(participant);
		}

		ScheduledArm scheduledArm = new ScheduledArm();
		scheduledArm.setEligibilityIndicator("true");
		scheduledArm.setStartDate(new Date());
		Arm arm = new Arm();
		// arm.setEpoch(studyParticipantAssignment.getStudySite().getStudy().getEpochs().get(0));
		scheduledArm.setArm(arm);
		studyParticipantAssignment.addScheduledArm(scheduledArm);
		scheduledArm.setStudyParticipantAssignment(studyParticipantAssignment);
		List<Epoch> list = studyParticipantAssignment.getStudySite().getStudy()
				.getEpochs();
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			Epoch e = (Epoch) list.get(i);
			System.out.println(e.getName());
			if (e.getName().equals("Treatment")) {
				for (int j = 0; j < e.getArms().size(); j++) {
					System.out.println(e.getArms().get(j).getName());
					System.out
							.println("--------------------------------------Arms available-----------------------------------------");

				}
				return studyParticipantAssignment;
			}
		}
		/*
		 * if(studyParticipantAssignment.getStudySite().getStudy().getEpochs().get(0).getArms().size()!=0){
		 * System.out.println("--------------------------------------Arms
		 * available-----------------------------------------"); return
		 * studyParticipantAssignment; }
		 */System.out
				.println("--------------------------------------Arms not available-----------------------------------------");
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

	protected Map<String, Object> referenceData(
			HttpServletRequest httpServletRequest) throws Exception {
		// Currently the static data is a hack, once DB design is approved for
		// an LOV this will be
		// replaced with LOVDao to get the static data from individual tables
		Map<String, Object> refdata = new HashMap<String, Object>();
		return refdata;
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
		if(viewName.equalsIgnoreCase("confirmationView")){
			System.out.println("In postProcessPage()...");
			System.out.println("no of registered pages: "+getPages().length);
			System.out.println(viewName);
			StudyParticipantAssignment studyParticipantAssignment = (StudyParticipantAssignment) command;
			int size = studyParticipantAssignment.getParticipant()
					.getStudyParticipantAssignments().size();
			System.out
					.println("-------------postProcessPage() participant.getStudyParticipantAssignments().size() is "
							+ size + "---------------");
			int size1 = studyParticipantAssignment.getStudySite()
					.getStudyParticipantAssignments().size();
			System.out
					.println("-------------postProcessPage() studySite.getStudyParticipantAssignments().size() is "
							+ size1 + "---------------");
			studyParticipantAssignment.getParticipant()
					.addStudyParticipantAssignment(studyParticipantAssignment);
			int scheduledArmsSize = studyParticipantAssignment.getScheduledArms()
					.size();
			// System.out.println("------------------------ScheduledArmSize is
			// "+scheduledArmsSize+"-------------------------");
			int armId = studyParticipantAssignment.getScheduledArms().get(
					scheduledArmsSize - 1).getArm().getId();
			Arm arm = null;
			List<Epoch> list = studyParticipantAssignment.getStudySite().getStudy()
					.getEpochs();
			System.out.println(list.size());
			List<Arm> arms = null;
			for (int i = 0; i < list.size(); i++) {
				Epoch e = (Epoch) list.get(i);
				System.out.println(e.getName());
				if (e.getName().equals("Treatment")) {
					for (int j = 0; j < e.getArms().size(); j++) {
						System.out.println(e.getArms().get(j).getName());
						System.out
								.println("--------------------------------------postProcessPage() Arms available-----------------------------------------");

					}
					arms = (List<Arm>) (studyParticipantAssignment.getStudySite()
							.getStudy().getEpochs().get(i).getArms());
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
			studyParticipantAssignment.getScheduledArms()
					.get(scheduledArmsSize - 1).setArm(arm);
			// ArrayList<StudyParticipantAssignment> stArrayList=new
			// ArrayList<StudyParticipantAssignment>();
			// stArrayList.add(studyParticipantAssignment);
			// studyParticipantAssignment.getParticipant().setStudyParticipantAssignments(stArrayList);
			// studyParticipantAssignment.getStudySite().setStudyParticipantAssignments(stArrayList);
			participantDao.save(studyParticipantAssignment.getParticipant());
			// response.sendRedirect("/c3pr");
			// return null;
//			return new ModelAndView("reg_confrm_registration");
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
	protected ModelAndView processFinish(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, BindException arg3) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}