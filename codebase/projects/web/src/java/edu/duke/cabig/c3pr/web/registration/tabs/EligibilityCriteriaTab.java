package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class EligibilityCriteriaTab extends RegistrationTab<StudySubjectWrapper> {

    public EligibilityCriteriaTab() {
        super("Eligibility", "Eligibility", "registration/reg_check_eligibility");
    }

    @Override
    public Map<String, Object> referenceData(StudySubjectWrapper command) {
        Map ref = new HashMap();
        boolean requiresEligibility = false;
        if (command.getStudySubject().getScheduledEpoch()!=null) if (( command.getStudySubject()
                        .getScheduledEpoch().getEpoch()).getEligibilityCriteria().size() > 0) requiresEligibility = true;
        ref.put("requiresEligibility", requiresEligibility);
        return ref;
    }

    @Override
    public void postProcess(HttpServletRequest request, StudySubjectWrapper command, Errors error) {
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command ;
    	StudySubject studySubject = wrapper.getStudySubject();
        // The following code for building scheduled epoch after moving subject to a new epoch.
        if(WebUtils.hasSubmitParameter(request, "epoch")){
        	Integer id;
	        try {
	            id = Integer.parseInt(request.getParameter("epoch"));
	        }
	        catch (RuntimeException e) {
	            return;
	        }
	      
	        Epoch epoch = epochDao.getById(id);
	        epochDao.initialize(epoch);
	        ScheduledEpoch scheduledEpoch;
	        if (epoch.getType() == EpochType.TREATMENT) {
	            (epoch).getArms().size();
	            scheduledEpoch = new ScheduledEpoch();
	        }
	        else {
	            scheduledEpoch = new ScheduledEpoch();
	        }
	        scheduledEpoch.setEpoch(epoch);
	        studySubject.getScheduledEpochs().add(scheduledEpoch);
	        studySiteDao.initialize(studySubject.getStudySite());
        }
    }

    
}
