package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class RandomizationTab extends RegistrationTab<StudySubject>{

	public RandomizationTab() {
		super("Randomize", "Randomize","registration/reg_randomize");
	}
	
		
	public ModelAndView randomize(HttpServletRequest request, Object commandObj, Errors error){
		StudySubject ss = (StudySubject)commandObj;
		ScheduledTreatmentEpoch ste = (ScheduledTreatmentEpoch)ss.getScheduledEpoch();
		
		switch(ss.getStudySite().getStudy().getRandomizationType()){
		
			case BOOK:  ScheduledArm sa = new ScheduledArm();
						sa.setArm(ss.getStratumGroup().getNextArm());
						ste.addScheduledArm(sa);
						break;
				
			case CALL_OUT://not implemented yet
							break;
				
			case PHONE_CALL://Doing nothing for now.
							break;
				
			default://Should never come here
				break;
		}
		String message = "";		
		if(ste.getScheduledArm().getArm() == null){
			message = "Error during Randomization. Arm not assigned.";
		}else {
			message = "Subject Randomized succesfully. Assigned arm is <b>"+ste.getScheduledArm().getArm().getName()+"</b>";
		}		
		Map map = new HashMap();
		map.put(getFreeTextModelName(), message);
		return new ModelAndView("",map);
		
	}
}
