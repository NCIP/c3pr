package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableTab;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class ManageEpochTab<C extends StudySubject> extends InPlaceEditableTab<C>{

	public ManageEpochTab() {
		super("Manage Epochs", "Change Current Epoch", "registration/reg_manage_epochs");
	}

	private EpochDao epochDao;
	public EpochDao getEpochDao() {
		return epochDao;
	}

	public void setEpochDao(EpochDao epochDao) {
		this.epochDao = epochDao;
	}

	public ModelAndView getEpochSection(HttpServletRequest request, Object commandObj, Errors error){
		C command=(C)commandObj;
		int id=-1;
		Map<String, Object> map=new HashMap<String, Object>();
		id=Integer.parseInt(request.getParameter("epochId"));
		Epoch epoch=epochDao.getById(id);
		map.put("epoch", epoch);
		map.put("alreadyRegistered", new Boolean(false));
		map.put("requiresEligibility", new Boolean(false));
		map.put("requiresStratification", new Boolean(false));
		map.put("requiresRandomization", new Boolean(false));
		map.put("isCurrentScheduledEpoch", new Boolean(false));
		if (epoch instanceof TreatmentEpoch) {
			map.put("epochType", "Treatment");
			if(((TreatmentEpoch)epoch).getEligibilityCriteria().size()>0){
				map.put("requiresEligibility", new Boolean(true));
			}
			if(((TreatmentEpoch)epoch).getStratificationCriteria().size()>0){
				map.put("requiresStratification", new Boolean(true));
			}
			if(((TreatmentEpoch)epoch).getArms().size()>1){
				map.put("requiresRandomization", new Boolean(true));
			}
		}
		for(ScheduledEpoch scheduledEpoch:command.getScheduledEpochs()){
			if(scheduledEpoch.getEpoch().getId()==epoch.getId()){
				map.put("alreadyRegistered", new Boolean(true));
			}
		}
		if(command.getCurrentScheduledEpoch().getEpoch().getId()==epoch.getId())
			map.put("isCurrentScheduledEpoch", new Boolean(true));
		return new ModelAndView(getAjaxViewName(request),map);
	}
	
	public ModelAndView createNewScheduledEpochSubject(HttpServletRequest request, Object commandObj, Errors error){
		StudySubject command=(StudySubject)commandObj;
		Map map=new HashMap();
		ScheduledEpoch scheduledEpoch;
		Integer id=Integer.parseInt(request.getParameter("epoch"));
		Epoch epoch=epochDao.getById(id);
		if (epoch instanceof TreatmentEpoch) {
			scheduledEpoch=new ScheduledTreatmentEpoch();
			if(((TreatmentEpoch)epoch).getArms().size()==1){
				((ScheduledTreatmentEpoch)scheduledEpoch).getScheduledArms().get(0).setArm(((TreatmentEpoch)epoch).getArms().get(0));
			}
		}else{
			scheduledEpoch=new ScheduledNonTreatmentEpoch();
		}
		scheduledEpoch.setEpoch(epoch);
		command.addScheduledEpoch(scheduledEpoch);
//		intializeEpochCollection(command);
		map.put(getFreeTextModelName(), "Subject registered successfully..");
		return new ModelAndView("",map);
	}
	
	private void intializeEpochCollection(StudySubject ss){
		if(ss.getIfTreatmentScheduledEpoch()){
			TreatmentEpoch te=(TreatmentEpoch)ss.getScheduledEpoch().getEpoch();
			te.getArms().size();
			te.getEligibilityCriteria().size();
			te.getStratificationCriteria().size();
		}
	}
}
