package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab;
import edu.duke.cabig.c3pr.web.registration.CreateRegistrationController;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class StratificationTab extends RegistrationTab<StudySubject>{

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(StratificationTab.class);

	public StratificationTab() {
		super("Stratify", "Stratify","registration/reg_stratify");
	}
	@Override
	public Map<String, Object> referenceData(StudySubject command) {
		Map ref=new HashMap();
		boolean requiresStratification=false;
		if(command.getIfTreatmentScheduledEpoch())
			if(((TreatmentEpoch)command.getScheduledEpoch().getEpoch()).getStratificationCriteria().size()>0)
				requiresStratification=true;
		ref.put("requiresStratification", requiresStratification);
		return ref;
	}
	
	@Override
	public void validate(StudySubject ss, Errors errors){
		super.validate(ss, errors);
		if(ss.getIfTreatmentScheduledEpoch()){
			ScheduledTreatmentEpoch ste = (ScheduledTreatmentEpoch)ss.getScheduledEpoch();
			if(ste.getSubjectStratificationAnswers().size() > 0){
				//ensuring an answer has been selected for every qs before calling getGroups.
				for(SubjectStratificationAnswer ssa: ste.getSubjectStratificationAnswers()){
					if(ssa.getStratificationCriterionAnswer() == null){
						return;
					}
				}
				try{
					if(ss.getStratumGroup() != null){
						logger.debug("Valid Stratification Answer selected.");
					}else {
						logger.debug("StratificationTab: Invalid Stratification Answer selected.");
						errors.reject("InvalidStratificationAnswer","No corresponding Stratum Group exists. Please select different stratification answer(s).");
					}
				}catch(C3PRBaseException cbe){
					logger.debug("StratificationTab: Invalid Stratification Answer selected.");
					errors.reject("InvalidStratificationAnswer", "No corresponding Stratum Group exists. Please select different stratification answer(s).");
				}
			}
		}
  	}
}
