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
import edu.duke.cabig.c3pr.dao.ScheduledEpochDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.NonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.web.registration.CreateRegistrationController;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class SearchStudySubjectTab extends RegistrationTab<StudySubject>{
	
	private static final Logger logger = Logger.getLogger(SearchStudySubjectTab.class);
	private EpochDao epochDao;
	private StudySubjectService studySubjectService;
	private StudySubjectDao studySubjectDao;
	private HealthcareSiteDao healthcareSiteDao;

	public EpochDao getEpochDao() {
		return epochDao;
	}

	public void setEpochDao(EpochDao epochDao) {
		this.epochDao = epochDao;
	}

	public SearchStudySubjectTab() {
		super("Search Subject or Study", "Select Subject & Study","registration/select_study_or_subject");
		setShowSummary("false");
	}
	
	@Override
	public Map<String, Object> referenceData() {
		Map<String, Object> refdata = new HashMap<String, Object>();
		Map<String, List<Lov>> configMap = configurationProperty.getMap();

		refdata.put("searchTypeRefDataPrt", configMap
				.get("participantSearchType"));
		refdata.put("searchTypeRefDataStudy", configMap.get("studySearchType"));
		refdata.put("administrativeGenderCode", configMap
				.get("administrativeGenderCode"));
		refdata
				.put("ethnicGroupCode", configMap
						.get("ethnicGroupCode"));
		refdata.put("raceCode", configMap.get("raceCode"));
		refdata.put("identifiersTypeRefData", configMap.get("participantIdentifiersType"));
		refdata.put("source", healthcareSiteDao.getAll());
		refdata.put("mandatory", "true");
		return refdata;
	}

	@Override
	public void postProcess(HttpServletRequest request, StudySubject command, Errors error) {
		if(WebUtils.hasSubmitParameter(request, "registrationId")){
			if(WebUtils.hasSubmitParameter(request, "epoch")){
				ScheduledEpoch scheduledEpoch;
				Integer id=Integer.parseInt(request.getParameter("epoch"));
				Epoch epoch=epochDao.getById(id);
				if (epoch instanceof TreatmentEpoch) {
					((TreatmentEpoch)epoch).getArms().size();
					scheduledEpoch=new ScheduledTreatmentEpoch();
				}else{
					scheduledEpoch=new ScheduledNonTreatmentEpoch();
				}
				scheduledEpoch.setEpoch(epoch);
				command.addScheduledEpoch(scheduledEpoch);
				buildCommandObject(command);
			}
			return;
		}
		if(command.getParticipant()==null||command.getStudySite()==null){
			request.setAttribute("alreadyRegistered", new Boolean(true));
			return;
		}
		StudySubject exampleSS=new StudySubject(true);
		exampleSS.setParticipant(command.getParticipant());
		exampleSS.setStudySite(command.getStudySite());
		List registrations=studySubjectDao.searchBySubjectAndStudySite(exampleSS);
		if(registrations.size()>0){
			request.setAttribute("alreadyRegistered", new Boolean(true));
			return;
		}
		Integer id;
		try {
			id=Integer.parseInt(request.getParameter("epoch"));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			return;
		}
		Epoch epoch=epochDao.getById(id);
		ScheduledEpoch scheduledEpoch;
		if (epoch instanceof TreatmentEpoch) {
			((TreatmentEpoch)epoch).getArms().size();
			scheduledEpoch=new ScheduledTreatmentEpoch();
		}else{
			scheduledEpoch=new ScheduledNonTreatmentEpoch();
		}
		scheduledEpoch.setEpoch(epoch);
		command.getScheduledEpochs().add(0,scheduledEpoch);
		buildCommandObject(command);
	}

	private void buildCommandObject(StudySubject studySubject){
		if(studySubject.getIfTreatmentScheduledEpoch()){
			ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)studySubject.getScheduledEpoch();
			List criterias=scheduledTreatmentEpoch.getTreatmentEpoch().getInclusionEligibilityCriteria();
			for(int i=0 ; i<criterias.size() ; i++){
				SubjectEligibilityAnswer subjectEligibilityAnswer=new SubjectEligibilityAnswer();
				subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria)criterias.get(i));
				scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
			}
			criterias=scheduledTreatmentEpoch.getTreatmentEpoch().getExclusionEligibilityCriteria();
			for(int i=0 ; i<criterias.size() ; i++){
				SubjectEligibilityAnswer subjectEligibilityAnswer=new SubjectEligibilityAnswer();
				subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria)criterias.get(i));
				scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
			}
			List<StratificationCriterion> stratifications=scheduledTreatmentEpoch.getTreatmentEpoch().getStratificationCriteria();
			for(StratificationCriterion stratificationCriterion : stratifications){
				stratificationCriterion.getPermissibleAnswers().size();
				SubjectStratificationAnswer subjectStratificationAnswer=new SubjectStratificationAnswer();
				subjectStratificationAnswer.setStratificationCriterion(stratificationCriterion);
				scheduledTreatmentEpoch.addSubjectStratificationAnswers(subjectStratificationAnswer);
			}
			scheduledTreatmentEpoch.getScheduledArms().size();
			scheduledTreatmentEpoch.getTreatmentEpoch().getStratumGroups().size();
			Iterator <StratumGroup> iter=scheduledTreatmentEpoch.getTreatmentEpoch().getStratumGroups().iterator();
			while(iter.hasNext()){
				StratumGroup stratumGroup=iter.next();
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
	
	public ModelAndView checkEpochAccrualCeiling(HttpServletRequest request, Object commandObj, Errors error){
		Map<String, Boolean> map=new HashMap<String, Boolean>();
		int id=Integer.parseInt(request.getParameter("epochId"));
		map.put("alertForCeiling", new Boolean(studySubjectService.isEpochAccrualCeilingReached(id)));
		return new ModelAndView(getAjaxViewName(request),map);
	}

	public StudySubjectService getStudySubjectService() {
		return studySubjectService;
	}

	public void setStudySubjectService(StudySubjectService studySubjectService) {
		this.studySubjectService = studySubjectService;
	}
}
