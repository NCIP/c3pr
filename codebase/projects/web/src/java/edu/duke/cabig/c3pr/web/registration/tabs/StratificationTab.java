package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class StratificationTab extends RegistrationTab<StudySubjectWrapper> {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(StratificationTab.class);

    public StratificationTab() {
        super("Stratification", "Stratification", "registration/reg_stratify");
    }

    @Override
    public Map<String, Object> referenceData(StudySubjectWrapper command) {
    	StudySubject studySubject = command.getStudySubject();
        Map ref = new HashMap();
        boolean requiresStratification = false;
        if (studySubject.getScheduledEpoch()!=null){ 
        	if (studySubject.getScheduledEpoch().getEpoch().getStratificationCriteria().size() > 0){
        		requiresStratification = true;	
        	}
        }
        ref.put("requiresStratification", requiresStratification);
        return ref;
    }

    @Override
    public void validate(StudySubjectWrapper wrapper, Errors errors) {
    	super.validate(wrapper, errors);
    	StudySubject ss = wrapper.getStudySubject();
        if (ss.getScheduledEpoch()!=null) {
            ScheduledEpoch ste = ss.getScheduledEpoch();
            if (ste.getSubjectStratificationAnswers().size() > 0) {
                // ensuring an answer has been selected for every qs before calling getGroups.
                for (SubjectStratificationAnswer ssa : ste.getSubjectStratificationAnswers()) {
                    if (ssa.getStratificationCriterionAnswer() == null) {
                        //this line added make sure if no stratum group is slected , it will allow user to go forward. 
                    	return;
                    }
                }
                try {
                    if (ss.getScheduledEpoch().getStratumGroup() != null) {
                        logger.debug("Valid Stratification Answer selected.");
                    }
                    else {
                        logger.debug("StratificationTab: Invalid Stratification Answer selected.");
                        errors
                                        .reject("InvalidStratificationAnswer",
                                                        "No corresponding Stratum Group exists. Please select different stratification answer(s).");
                    }
                }
                catch (Exception cbe) {
                    logger.debug("StratificationTab: Invalid Stratification Answer selected.");
                    errors
                                    .reject("InvalidStratificationAnswer",
                                                    "No corresponding Stratum Group exists. Please select different stratification answer(s).");
                }
            }
        }
    }

	@Override
	public void postProcess(HttpServletRequest request,
			StudySubjectWrapper command, Errors errors) {
		super.postProcess(request, command, errors);
		StudySubject studySubject = ((StudySubjectWrapper) command).getStudySubject();
		if(!errors.hasErrors()){
			if(studySubject.getScheduledEpoch().getEpoch().getStratificationIndicator()){
				try {
					if(studySubject.getScheduledEpoch().getStratumGroup() != null){
						studySubject.getScheduledEpoch().setStratumGroupNumber(studySubject.getScheduledEpoch().getStratumGroup().getStratumGroupNumber());
					}
				} catch (C3PRBaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
    
    
}
