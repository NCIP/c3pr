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
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import edu.duke.cabig.c3pr.dao.ArmDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.Arm;
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
    private static Log log = LogFactory.getLog(RegistrationHomeController.class);

    private final String[] pages={"reg_check_eligibility","reg_stratify","reg_randomize","reg_submit"};
    private final String[] viewNames={"checkEligibilityView","stratifyView","randomizeView","reviewAndSubmitView"};
	private ParticipantDao participantDao;
	private StudySiteDao studySiteDao;
	private ArmDao armDao;

	public RegistrationHomeController() {
		setPages(pages);
	}
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(true));
        ControllerTools.registerDomainObjectEditor(binder, armDao);
    }

	@Override
	protected boolean isFinishRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		if(request.getParameter("nextView")==null||request.getParameter("nextView").equals(""))
			return false;
		String viewName=request.getParameter("nextView");
		if(viewName.equalsIgnoreCase("processFinish"))
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
		String viewName=request.getParameter("nextView");
		for(int i=0 ;i< viewNames.length ; i++){
			if(viewNames[i].equals(viewName)){
				System.out.println("ViewName in request is : "+ viewName+"at index "+i);
				return i;
			}
		}
		return 0;
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		StudyParticipantAssignment studyParticipantAssignment= new StudyParticipantAssignment();
		studyParticipantAssignment.setStudyParticipantIdentifier("TESTID");
		studyParticipantAssignment.setStartDate(new Date());
		StudySite studySite=null;
		Participant participant=null;
		if(request.getParameter("studySiteId")!=null && request.getParameter("participantId")!=null){
			System.out.println("Parameters found as.."+request.getParameter("studySiteId")+"  and "+request.getParameter("participantId"));
			if(studySiteDao!=null){
				studySite=studySiteDao.getById(Integer.parseInt(request.getParameter("studySiteId")));
				System.out.println("RoleCode = "+studySite.getRoleCode());
			}
			else
				System.out.println("studySiteDao is null");
			if(participantDao!=null){
				participant=participantDao.getById(Integer.parseInt(request.getParameter("participantId")));
				System.out.println("First Name = "+participant.getFirstName());
			}
			else
				System.out.println("participantDao is null");
			studyParticipantAssignment.setStudySite(studySite);
			studyParticipantAssignment.setParticipant(participant);
		}
		ScheduledArm scheduledArm=new ScheduledArm();
		scheduledArm.setEligibilityIndicator("true");
		scheduledArm.setStartDate(new Date());
		scheduledArm.setArm(new Arm());
		scheduledArm.setStudyParticipantAssignment(studyParticipantAssignment);
		studyParticipantAssignment.addScheduledArm(scheduledArm);
		Arm arm1 = new Arm();
		arm1.setId(11);
		arm1.setName("A");
		Arm arm2 = new Arm();
		arm2.setName("B");
		arm2.setId(22);
		ArrayList<Arm> arms=new ArrayList<Arm>();
		arms.add(arm1);
		arms.add(arm2);
//		studyParticipantAssignment.getStudySite().getStudy().getEpochs().get(0).addArm(arm1);
//		studyParticipantAssignment.getStudySite().getStudy().getEpochs().get(0).addArm(arm2);
		studyParticipantAssignment.getStudySite().getStudy().getEpochs().get(0).setArms(arms);
		return studyParticipantAssignment;
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest) throws Exception {
    	// Currently the static data is a hack, once DB design is approved for an LOV this will be
    	// replaced with LOVDao to get the static data from individual tables
    	Map<String, Object> refdata = new HashMap<String, Object>();
        return refdata;
    }

	
	private List<StringBean> getRandomizedList(){
		List<StringBean> col = new ArrayList<StringBean>();		
    	col.add(new StringBean("Y"));
    	col.add(new StringBean("N"));
    	return col;
	}
	
	public class LOV {
		
		private String code;
		private String desc;
		
		LOV(String code, String desc)
		{
			this.code=code;
			this.desc=desc;
			
		}
		
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
		
		public String getDesc(){
			return desc;
		}
			
		public void setDesc(String desc){
			this.desc=desc;
		}
	}
	
	public class StringBean {
	
		String str;
		
		StringBean(String str)
		{
			this.str=str;
		}
		
		public void setStr(String str){
			this.str=str;
		}
		
		public String getStr(){
			return str;
		}
		
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException arg3) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("In process Finish...");
		StudyParticipantAssignment studyParticipantAssignment=(StudyParticipantAssignment)command;
		ArrayList<StudyParticipantAssignment> stArrayList=new ArrayList<StudyParticipantAssignment>();
		stArrayList.add(studyParticipantAssignment);
		studyParticipantAssignment.getParticipant().setStudyParticipantAssignments(stArrayList);
		studyParticipantAssignment.getStudySite().setStudyParticipantAssignments(stArrayList);
		int armId=studyParticipantAssignment.getScheduledArms().get(0).getArm().getId();
		Arm arm=armDao.getById(armId);
		studyParticipantAssignment.getScheduledArms().get(0).setArm(arm);
		participantDao.save(studyParticipantAssignment.getParticipant());
		response.sendRedirect("http://www.google.com");
		return null;
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
}