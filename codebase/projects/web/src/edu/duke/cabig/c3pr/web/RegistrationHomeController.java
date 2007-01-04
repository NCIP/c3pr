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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;

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
	
	
	
	public RegistrationHomeController() {
		setPages(pages);
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
/*		if(no!=null){
			System.out.println("Spring's page number is : "+ no);
		}
*/		String viewName=request.getParameter("nextView");
		System.out.println("ViewName in request is : "+ viewName);
		for(int i=0 ;i< viewNames.length ; i++){
			if(viewNames[i].equals(viewName)){
				return i;
			}
		}
		return 0;
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		boolean test=false;
		if(test){
			StudyParticipantAssignment studyParticipantAssignment= new StudyParticipantAssignment();
			StudySite studySite=new StudySite();
			Study study= new Study();
			Epoch epoch=new Epoch();
			Arm arm1 = new Arm();
			arm1.setName("A");
			Arm arm2 = new Arm();
			arm2.setName("B");
			epoch.addArm(arm1);
			epoch.addArm(arm2);
			study.addEpoch(epoch);
			studySite.setStudy(study);
			ScheduledArm scheduledArm=new ScheduledArm();
			studyParticipantAssignment.setStudySite(studySite);
			studyParticipantAssignment.addScheduledArm(scheduledArm);
			return studyParticipantAssignment;
		}
			
		StudyParticipantAssignment studyParticipantAssignment= new StudyParticipantAssignment();
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
		scheduledArm.setStudyParticipantAssignment(studyParticipantAssignment);
		studyParticipantAssignment.addScheduledArm(scheduledArm);
		studyParticipantAssignment.getStudySite().getStudy().getEpochs().get(0).getArms();
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
	protected ModelAndView processFinish(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, BindException arg3) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public ParticipantDao getParticipantDao() {
		return participantDao;
	}


	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
}