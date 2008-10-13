package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class SearchStudySubjectTab extends RegistrationTab<StudySubjectWrapper> {

    private static final Logger logger = Logger.getLogger(SearchStudySubjectTab.class);

    private EpochDao epochDao;
    
    private StudyDao studyDao;
    
    private ParticipantDao participantDao;

    private StudySubjectService studySubjectService;

    private StudySubjectDao studySubjectDao;

    private HealthcareSiteDao healthcareSiteDao;
    
    private StudySiteDao studySiteDao;

    public EpochDao getEpochDao() {
        return epochDao;
    }

    public void setEpochDao(EpochDao epochDao) {
        this.epochDao = epochDao;
    }

    public SearchStudySubjectTab() {
        super("Select Subject & Study", "Select Subject & Study",
                        "registration/select_study_or_subject");
        setShowSummary("false");
    }

    @Override
    public Map<String, Object> referenceData(StudySubjectWrapper command) {
        Map<String, Object> refdata = new HashMap<String, Object>();
        Map<String, List<Lov>> configMap = configurationProperty.getMap();

        refdata.put("searchTypeRefDataPrt", configMap.get("participantSearchType"));
        refdata.put("searchTypeRefDataStudy", configMap.get("studySearchType"));
        refdata.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
        refdata.put("ethnicGroupCode", configMap.get("ethnicGroupCode"));
        refdata.put("raceCode", configMap.get("raceCode"));
        refdata.put("identifiersTypeRefData", configMap.get("participantIdentifiersType"));
        //refdata.put("source", healthcareSiteDao.getAll());
        refdata.put("mandatory", "true");
        if (command.getStudySubject().getId() != null) {
            refdata.put("disableForm", new Boolean(true));
        }
        return refdata;

    }

    @Override
    public void postProcess(HttpServletRequest request, StudySubjectWrapper command, Errors error) {
        if (WebUtils.hasSubmitParameter(request, "registrationId")) {
            if (WebUtils.hasSubmitParameter(request, "epoch")) {
                ScheduledEpoch scheduledEpoch;
                Integer id = Integer.parseInt(request.getParameter("epoch"));
                Epoch epoch = epochDao.getById(id);
                epochDao.initialize(epoch);
                if (epoch.getTreatmentIndicator()) {
                    (epoch).getArms().size();
                    scheduledEpoch = new ScheduledEpoch();
                }
                else {
                    scheduledEpoch = new ScheduledEpoch();
                }
                scheduledEpoch.setEpoch(epoch);
                command.getStudySubject().addScheduledEpoch(scheduledEpoch);
                buildCommandObject(command.getStudySubject());
            }
            studySiteDao.initialize(command.getStudySubject().getStudySite());
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
        buildCommandObject(command.getStudySubject());
        studySiteDao.initialize(command.getStudySubject().getStudySite());
    }

    private void buildCommandObject(StudySubject studySubject) {
		Study study = studyDao.getById(studySubject.getStudySite().getStudy().getId());
	    studyDao.initialize(study);
	    participantDao.initialize(studySubject.getParticipant());
        if (studySubject.getScheduledEpoch()!=null) {
            ScheduledEpoch scheduledEpoch = studySubject
                            .getScheduledEpoch();
            List criterias = scheduledEpoch.getEpoch()
                            .getInclusionEligibilityCriteria();
            for (int i = 0; i < criterias.size(); i++) {
                SubjectEligibilityAnswer subjectEligibilityAnswer = new SubjectEligibilityAnswer();
                subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria) criterias
                                .get(i));
                scheduledEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
            }
            criterias = scheduledEpoch.getEpoch()
                            .getExclusionEligibilityCriteria();
            for (int i = 0; i < criterias.size(); i++) {
                SubjectEligibilityAnswer subjectEligibilityAnswer = new SubjectEligibilityAnswer();
                subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria) criterias
                                .get(i));
                scheduledEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
            }
            List<StratificationCriterion> stratifications = scheduledEpoch
                            .getEpoch().getStratificationCriteria();
            for (StratificationCriterion stratificationCriterion : stratifications) {
                stratificationCriterion.getPermissibleAnswers().size();
                SubjectStratificationAnswer subjectStratificationAnswer = new SubjectStratificationAnswer();
                subjectStratificationAnswer.setStratificationCriterion(stratificationCriterion);
                scheduledEpoch
                                .addSubjectStratificationAnswers(subjectStratificationAnswer);
            }
            scheduledEpoch.getScheduledArms().size();
            scheduledEpoch.getEpoch().getStratumGroups().size();
            Iterator<StratumGroup> iter = scheduledEpoch.getEpoch()
                            .getStratumGroups().iterator();
            while (iter.hasNext()) {
                StratumGroup stratumGroup = iter.next();
                stratumGroup.getStratificationCriterionAnswerCombination().size();
                stratumGroup.getBookRandomizationEntry().size();
            }
        }
    }

    public StudySubjectDao getStudySubjectDao() {
        return studySubjectDao;
    }

    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

    public ModelAndView checkEpochAccrualCeiling(HttpServletRequest request, Object commandObj,
                    Errors error) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        int id = Integer.parseInt(request.getParameter("epochId"));
        map.put("alertForCeiling",
                        new Boolean(studySubjectRepository.isEpochAccrualCeilingReached(id)));
        return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
    }

    public StudySubjectService getStudySubjectService() {
        return studySubjectService;
    }

    public void setStudySubjectService(StudySubjectService studySubjectService) {
        this.studySubjectService = studySubjectService;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

	public StudySiteDao getStudySiteDao() {
		return studySiteDao;
	}

	public void setStudySiteDao(StudySiteDao studySiteDao) {
		this.studySiteDao = studySiteDao;
	}
}
