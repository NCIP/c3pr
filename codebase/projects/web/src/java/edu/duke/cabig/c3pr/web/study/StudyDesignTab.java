package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.CalloutRandomization;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
class StudyDesignTab extends StudyTab {

	public StudyDesignTab() {
		super("Epochs and Arms", "Epochs & Arms", "study/study_design");
	}

	@Override
	public Map<String, Object> referenceData(Study study) {
		Map<String, Object> refdata = super.referenceData(study);
		refdata.put("currentOperation", getConfigurationProperty().getMap()
				.get("inclusion"));

		return refdata;
	}

	@Override
	public void postProcess(HttpServletRequest httpServletRequest, Study study,
			Errors errors) {

		String selectedEpoch = httpServletRequest
				.getParameter("_selectedEpoch");
		String action = httpServletRequest.getParameter("_action");
		String selectedArm = httpServletRequest.getParameter("_selectedArm");

		{
			if ("addEpoch".equals(action)) {
				log.debug("Requested Add Epoch");
				study.addEpoch(Epoch.createTreatmentEpoch(
						"New Treatment Epoch", "Arm A", "Arm B", "Arm C"));				
			} else if ("addArm".equals(action)) {
				log.debug("Requested Add Arm");
				TreatmentEpoch epoch = (TreatmentEpoch) study
						.getTreatmentEpochs().get(
								Integer.parseInt(selectedEpoch));
				if (epoch.getName().equals("") || (epoch.getName() == null)) {
					epoch.setName("Treatment Epoch");
				}
				Arm newArm = new Arm();
				newArm.setName("New Arm");
				epoch.addArm(newArm);
			} else if ("removeEpoch".equals(action)) {
				log.debug("Requested Remove Epoch");
				study.getEpochs().remove(Integer.parseInt(selectedEpoch));
			} else if ("removeArm".equals(action)) {
				log.debug("Requested Remove Arm");
				TreatmentEpoch epoch = (TreatmentEpoch) study.getEpochs().get(
						Integer.parseInt(selectedEpoch));
				epoch.getArms().remove(Integer.parseInt(selectedArm));
			} else if ("addNonTreatmentEpoch".equals(action)) {
				log.debug("Requested Add NonTreatmentEpoch");
				study.addEpoch(Epoch
						.createNonTreatmentEpoch("New Non Treatment Epoch"));
			}			
		}
		//Instantiating the appropriate randomization class and setting it in the epoch.
		ArrayList epochList = (ArrayList)study.getEpochs();
		Epoch epoch;
		TreatmentEpoch tEpoch;
		Iterator iter = epochList.iterator();
		while(iter.hasNext()){
			epoch = (Epoch)iter.next();
			if(epoch instanceof TreatmentEpoch){
				tEpoch = (TreatmentEpoch)epoch;
				if(study.getRandomizationType().equals(RandomizationType.BOOK)){
					tEpoch.setRandomization(new BookRandomization());
		    	}
				if(study.getRandomizationType().equals(RandomizationType.CALL_OUT)){
					tEpoch.setRandomization(new CalloutRandomization());
		    	}
				if(study.getRandomizationType().equals(RandomizationType.PHONE_CALL)){
		    		//no class for this yet.
		    	}
			}
		}
		
	}
}
