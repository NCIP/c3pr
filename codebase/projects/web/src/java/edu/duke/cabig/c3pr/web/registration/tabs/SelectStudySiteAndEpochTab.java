/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

public class SelectStudySiteAndEpochTab extends RegistrationTab<StudySubjectWrapper> {

    public SelectStudySiteAndEpochTab() {
        super("Study Site And Epoch", "Study Site And Epoch", "registration/select_studysite_and _epoch");
        setShowSummary("false");
    }
    
    @Override
    public Map referenceData(HttpServletRequest request, StudySubjectWrapper command) {
    	Map refdata=super.referenceData(request, command);

    	String studyId = request.getParameter("study") ;
		String parentRegistrationId = request.getParameter("parentRegistrationId");
		
		Study companionStudy = studyDao.getById(Integer.parseInt(studyId));
		StudySubject parentRegistration = studySubjectDao.getById(Integer.parseInt(parentRegistrationId));
        List<StudySite> studySites = getStudySites(parentRegistration.getStudySite().getStudy(), companionStudy) ;
		
        refdata.put("mandatory", "true");
		refdata.put("studySites", studySites);
		refdata.put("epochs", getEnrollingEpochs(companionStudy));
		refdata.put("companionStudy", companionStudy);
		refdata.put("parentRegistrationId", request.getParameter("parentRegistrationId"));

    	return refdata;
    }
    
    private List<Epoch> getEnrollingEpochs(Study companionStudy) {
		List<Epoch> epochs = new ArrayList<Epoch>();
		for(Epoch epoch : companionStudy.getEpochs()){
			if(epoch.getEnrollmentIndicator()){
				epochs.add(epoch);
			}
		}
		return epochs;
	}

	private List<StudySite> getStudySites(Study study, Study companionStudy) {
		List<StudySite> studySites = new ArrayList<StudySite>();
		for(StudySite studySite : companionStudy.getStudySites()){
			for(StudySite parentStudySite : study.getAccruingStudySites()){
				if(studySite.getHealthcareSite().equals(parentStudySite.getHealthcareSite())){
					studySites.add(studySite);
					break;
				}
			}
		}
		return studySites;
	}
    
    @Override
    public void postProcess(HttpServletRequest request, StudySubjectWrapper command, Errors error) {
    	String studySiteId = request.getParameter("studySite");
    	StudySite studySite = studySiteDao.getById(Integer.parseInt(studySiteId));
        command.getStudySubject().setStudySite(studySite);
    	
        Integer id = Integer.parseInt(request.getParameter("epoch"));
        
        Epoch epoch = epochDao.getById(id);
        epochDao.initialize(epoch);
        if(epoch.getArms() != null){
        	(epoch).getArms().size();	
        }
        ScheduledEpoch scheduledEpoch = new ScheduledEpoch();
        scheduledEpoch.setEpoch(epoch);
        if (command.getStudySubject().getScheduledEpochs().size() == 0) {
        	command.getStudySubject().getScheduledEpochs().add(0,scheduledEpoch);
        } else {
            command.getStudySubject().getScheduledEpochs().set(0, scheduledEpoch);
        }
        registrationControllerUtils.buildCommandObject(command.getStudySubject());
        registrationControllerUtils.addConsents(command.getStudySubject());
    }

	public ModelAndView checkEpochAccrualCeiling(HttpServletRequest request, Object commandObj, Errors error) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        int id = Integer.parseInt(request.getParameter("epochId"));
        map.put("alertForCeiling",new Boolean(studySubjectRepository.isEpochAccrualCeilingReached(id)));
        return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
    }

}
