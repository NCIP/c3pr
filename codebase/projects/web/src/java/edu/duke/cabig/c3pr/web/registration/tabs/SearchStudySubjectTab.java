package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class SearchStudySubjectTab extends RegistrationTab<StudySubjectWrapper> {

    private static final Logger logger = Logger.getLogger(SearchStudySubjectTab.class);

    public SearchStudySubjectTab() {
        super("Subject & Study", "Subject & Study",
                        "registration/select_study_or_subject");
        setShowSummary("false");
    }
   
    @Override
    public Map referenceData(HttpServletRequest request,
    		StudySubjectWrapper command) {
    	Map refdata=super.referenceData(request, command);
    	Map<String, List<Lov>> configMap = configurationProperty.getMap();
        refdata.put("identifiersTypeRefData", configMap.get("participantIdentifiersType"));
        refdata.put("searchTypeRefDataPrt", configMap.get("participantSearchType"));
        refdata.put("searchTypeRefDataStudy", configMap.get("studySearchTypeForRegistration"));
        refdata.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
        refdata.put("ethnicGroupCode", configMap.get("ethnicGroupCode"));
        refdata.put("raceCode", configMap.get("raceCode"));
        refdata.put("identifiersTypeRefData", configMap.get("participantIdentifiersType"));
        if (command.getStudySubject().getSystemAssignedIdentifiers()!= null && command.getStudySubject().getSystemAssignedIdentifiers().size()>0) {
            refdata.put("disableForm", new Boolean(true));
        }
        refdata.put("mandatory", "true");
    	return refdata;
    }
    
    @Override
    public void postProcess(HttpServletRequest request, StudySubjectWrapper command, Errors error) {
    	if (WebUtils.hasSubmitParameter(request, "epochId")) {
            return;
        }
        if (command.getStudySubject().getParticipant() == null || command.getStudySubject().getStudySite() == null) {
            request.setAttribute("alreadyRegistered", new Boolean(true));
            return;
        }
        
        StudySubject exampleSS = new StudySubject(true);
        exampleSS.setParticipant(command.getStudySubject().getParticipant());
        exampleSS.setStudySite(command.getStudySubject().getStudySite());
        List registrations = studySubjectDao.searchBySubjectAndStudySite(exampleSS);
        if (registrations.size() > 0) {
            request.setAttribute("alreadyRegistered", new Boolean(true));
            return;
        }
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
        if (epoch.getTreatmentIndicator()) {
            (epoch).getArms().size();
            scheduledEpoch = new ScheduledEpoch();
        }
        else {
            scheduledEpoch = new ScheduledEpoch();
        }
        scheduledEpoch.setEpoch(epoch);
        if (command.getStudySubject().getScheduledEpochs().size() == 0) command.getStudySubject().getScheduledEpochs().add(0,
                        scheduledEpoch);
        else {
            command.getStudySubject().getScheduledEpochs().set(0, scheduledEpoch);
        }
        registrationControllerUtils.buildCommandObject(command.getStudySubject());
        registrationControllerUtils.addConsents(command.getStudySubject());
        studySiteDao.initialize(command.getStudySubject().getStudySite());
    }

    @Override
	public void validate(StudySubjectWrapper command, Errors errors) {
		super.validate(command, errors);
		StudySubject studySubject = ((StudySubjectWrapper)command).getStudySubject();
		if(studySubject.getId()==null){
    			List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    			studySubjects=studySubjectRepository.findRegistrations(studySubject);
    			if (studySubjects.size() > 0) {
    				errors.reject("duplicateRegistration","Subject already registered on this study");
    	        }
		}
	}

	public ModelAndView checkEpochAccrualCeiling(HttpServletRequest request, Object commandObj,
                    Errors error) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        int id = Integer.parseInt(request.getParameter("epochId"));
        map.put("alertForCeiling",
                        new Boolean(studySubjectRepository.isEpochAccrualCeilingReached(id)));
        return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
    }
}
