/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.dao.ReasonDao;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

public class OffEpochTab extends RegistrationTab<StudySubjectWrapper> {

	private ReasonDao reasonDao;
	
	public OffEpochTab() {
        super("Off Epoch", "Off Epoch", "registration/reg_off_epoch");
    }
	
	@Override
	public Map referenceData(HttpServletRequest request, StudySubjectWrapper command) {
		StudySubject studySubject = ((StudySubjectWrapper)command).getStudySubject();
    	Map<String, Object> map = new HashMap<String, Object>();
    	String operationType = request.getParameter(OPERATION_TYPE_PARAM_NAME);
    	map.put("operationType", operationType);
    	if(operationType.equals(OFF_STUDY_OPERATION)){
    		map.put("offEpochReasons", reasonDao.getOffStudyReasons());
    	}else if(operationType.equals(FAIL_SCREENING_OPERATION)){
    		map.put("offEpochReasons", reasonDao.getOffScreeningReasons());
    	}else if(operationType.equals(CHANGE_EPOCH_OPERATION)){
    		switch(studySubject.getScheduledEpoch().getEpoch().getType()){
    		case TREATMENT: map.put("offEpochReasons", reasonDao.getOffTreatmentReasons());
    						break;
    		case SCREENING: map.put("offEpochReasons", reasonDao.getOffScreeningReasons());
							break;
    		case FOLLOWUP: map.put("offEpochReasons", reasonDao.getOffFollowupReasons());
							break;
    		case RESERVING: map.put("offEpochReasons", reasonDao.getOffReservingReasons());
							break;
    		}
    	}
    	return map;
	}
	
	 @Override
	public void validate(StudySubjectWrapper command, Errors errors) {
		super.validate(command, errors);
		if((command.getOffEpochDate()).before(command.getStudySubject().getScheduledEpoch().getStartDate())){
			errors.reject("tempProperty","Off epoch date cannot be prior to epoch start date " + command.getStudySubject().getScheduledEpoch().getStartDateStr());
		}
	}

	@Override
	public void postProcess(HttpServletRequest request, StudySubjectWrapper command, Errors errors) {
		StudySubject studySubject = command.getStudySubject();
		if(request.getParameter(ACTION_PARAM_NAME).equals("saveOffEpochReasons")){
			String operationType = request.getParameter(OPERATION_TYPE_PARAM_NAME);
			if(operationType.equals(OFF_STUDY_OPERATION)){
				studySubject = studySubjectRepository.takeSubjectOffStudy(studySubject.getUniqueIdentifier(), command.getOffEpochReasons(), command.getOffEpochDate());
			}else if(operationType.equals(FAIL_SCREENING_OPERATION)){
				studySubject = studySubjectRepository.failScreening(studySubject.getUniqueIdentifier(), command.getOffEpochReasons(), command.getOffEpochDate());
			}else if(operationType.equals(CHANGE_EPOCH_OPERATION)){
				studySubject = studySubjectRepository.takeSubjectOffCurrentEpoch(studySubject.getUniqueIdentifier(), command.getOffEpochReasons(), command.getOffEpochDate());
			}
		}
		command.setStudySubject(studySubject);
	}

	public void setReasonDao(ReasonDao reasonDao) {
		this.reasonDao = reasonDao;
	}
	
}
