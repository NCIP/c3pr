package edu.duke.cabig.c3pr.web.registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Companion;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;

public class RegistrationControllerUtils {

	private StudySubjectService studySubjectService;
	
	private StudySubjectRepository studySubjectRepository;
	
	private StudyDao studyDao;
	private ParticipantDao participantDao;
	
	private Configuration configuration;

	
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setStudySubjectService(StudySubjectService studySubjectService) {
		this.studySubjectService = studySubjectService;
	}

	public boolean isRegisterableOnPage(StudySubject studySubject) {
        return studySubject.isDataEntryComplete()
                        && !studySubjectService.requiresExternalApprovalForRegistration(studySubject)
                        && (studySubject.getScheduledEpoch().getEpoch().isEnrolling()?registrableStandalone(studySubject):true);
    }
    
    public static boolean registrableStandalone(StudySubject studySubject){
    	return studySubject.getParentStudySubject()==null && studySubject.getStudySite().getStudy().getCompanionStudyAssociations().size()==0;
    }
    public Map buildMap(StudySubject studySubject) {
		Map map = new HashMap();
		boolean reg_unregistered = false;
		boolean reg_registered = false;
		boolean reg_unapproved = false;
		boolean reg_pending = false;
		boolean reg_reserved = false;
		boolean reg_disapproved = false;
		boolean reg_nonenrolled = false;
		boolean reg_unrandomized = false;
		boolean epoch_unapproved = false;
		boolean epoch_pending = false;
		boolean epoch_approved = false;
		boolean epoch_nonenrolled = false;
		boolean epoch_unrandomized = false;
		boolean epoch_disapproved = false;
		boolean newRegistration = true;
		boolean has_mandatory_companions = false;
		
		String armAssigned = "";
		String armAssignedLabel = "";
		if ((studySubject.getScheduledEpoch()).getScheduledArm() != null) {
			if (studySubject.getStudySite().getStudy().getBlindedIndicator()  && studySubject.getScheduledEpoch().getRequiresRandomization()) {
				armAssigned = (studySubject.getScheduledEpoch())
						.getScheduledArm().getKitNumber();
				armAssignedLabel = "Kit assigned";
			} else if ((studySubject.getScheduledEpoch()).getScheduledArm()
					.getArm() != null) {
				armAssigned = (studySubject.getScheduledEpoch())
						.getScheduledArm().getArm().getName();
				armAssignedLabel = "Arm assigned";
			}

		}

		int count = 0;
		for (ScheduledEpoch scheduledEpoch : studySubject.getScheduledEpochs()) {
				count++;
		}
		if (studySubject.getScheduledEpoch().getEpoch().isEnrolling()) {
			count--;
		} else if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.REGISTERED) {
			reg_nonenrolled = true;
			epoch_nonenrolled = true;
		}
		if (studySubject.getScheduledEpochs().size() > 1){
			newRegistration = false;			
		}
		if (studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.COMPLETE
				&& studySubject.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE
				&& studySubject.getScheduledEpoch().getRequiresRandomization()
				&& studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING) {
			reg_unrandomized = true;
			epoch_unrandomized = true;
		}
		switch (studySubject.getRegWorkflowStatus()) {
		case ENROLLED:
			reg_registered = true;
			break;
		case PENDING:
			reg_pending = true;
			break;
		case RESERVED:
			reg_reserved = true;
			break;
		}
		switch (studySubject.getScheduledEpoch().getScEpochWorkflowStatus()) {
		case PENDING:
			epoch_unapproved = true;
			break;
		case REGISTERED_BUT_NOT_RANDOMIZED:
			epoch_disapproved = true;
			break;
		case REGISTERED:
			epoch_approved = true;
			break;
		}
		map.put("reg_unregistered", reg_unregistered);
		map.put("reg_registered", reg_registered);
		map.put("reg_unapproved", reg_unapproved);
		map.put("reg_pending", reg_pending);
		map.put("reg_reserved", reg_reserved);
		map.put("reg_disapproved", reg_disapproved);
		map.put("epoch_unapproved", epoch_unapproved);
		map.put("epoch_pending", epoch_pending);
		map.put("epoch_approved", epoch_approved);
		map.put("epoch_disapproved", epoch_disapproved);
		map.put("newRegistration", newRegistration);
		map.put("armAssigned", armAssigned);
		map.put("armAssignedLabel", armAssignedLabel);
		map.put("reg_nonenrolled", reg_nonenrolled);
		map.put("reg_unrandomized", reg_unrandomized);
		map.put("epoch_nonenrolled", epoch_nonenrolled);
		map.put("epoch_unrandomized", epoch_unrandomized);
		map.put("hasParent", studySubject.getParentStudySubject()!=null);
		map.put("hasCompanions", studySubject.getStudySite().getStudy().getCompanionStudyAssociations().size()>0);
		map.put("registerableWithCompanions", registerableAsorWithCompanion(studySubject));
		map.put("isDataEntryComplete", studySubject.isDataEntryComplete());
		map.put("has_mandatory_companions", studySubject.hasMandatoryCompanions());
		map.put("has_child_registrations", studySubject.getChildStudySubjects()==null?false:studySubject.getChildStudySubjects().size()>0?true:false);
		return map;
	}

	public void addAppUrls(Map<String, Object> map) {
		if (this.configuration.get(this.configuration.AUTHENTICATION_MODEL)
				.equals("webSSO")) {
			map.put("hotlinkEnable", new Boolean(true));
			if (!StringUtils.getBlankIfNull(
					this.configuration.get(this.configuration.PSC_BASE_URL))
					.equalsIgnoreCase("")) {
				map.put("pscBaseUrl", this.configuration
						.get(this.configuration.PSC_BASE_URL));
				map.put("psc_window", this.configuration.get(this.configuration.PSC_WINDOW_NAME));
			}
			if (!StringUtils.getBlankIfNull(
					this.configuration.get(this.configuration.CAAERS_BASE_URL))
					.equalsIgnoreCase("")) {
				map.put("caaersBaseUrl", this.configuration
						.get(this.configuration.CAAERS_BASE_URL));
				map.put("caaers_window", this.configuration.get(this.configuration.CAAERS_WINDOW_NAME));
			}
			if (!StringUtils.getBlankIfNull(
					this.configuration.get(this.configuration.C3D_BASE_URL))
					.equalsIgnoreCase("")) {
				map.put("c3dBaseUrl", this.configuration
						.get(this.configuration.C3D_BASE_URL));
				map.put("c3d_window", this.configuration.get(this.configuration.C3D_WINDOW_NAME));
			}
		} else {
			map.put("hotlinkEnable", new Boolean(false));
		}
	}

	public boolean registerableAsorWithCompanion(StudySubject studySubject) {
		if(studySubject.getParentStudySubject()!=null)
			return false;
		if (studySubject.getStudySite().getStudy().getCompanionStudyAssociations().size() > 0) {
			Map<Integer, Object> compIds = new HashMap<Integer, Object>();
			for (CompanionStudyAssociation companionStudyAssociation : studySubject.getStudySite().getStudy().getCompanionStudyAssociations()) {
				if (companionStudyAssociation.getMandatoryIndicator()) {
					compIds.put(companionStudyAssociation.getCompanionStudy().getId(), new Object());
				}
			}
			Set<RegistrationWorkFlowStatus> status = new HashSet<RegistrationWorkFlowStatus>();
			status.add(RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED);
			status.add(RegistrationWorkFlowStatus.ENROLLED);
			for (StudySubject stSubject : studySubject.getChildStudySubjects()) {
				if (!status.contains(stSubject.getRegWorkflowStatus())) {
					return false;
				}
				compIds.remove(stSubject.getStudySite().getStudy().getId());
			}
			if (compIds.size() > 0)
				return false;
		}
		return true;

	}
	
	public void updateStatusForEmbeddedStudySubjet(StudySubject studySubject){
		studySubject.setRegDataEntryStatus(studySubject.evaluateRegistrationDataEntryStatus());
		studySubject.getScheduledEpoch().setEligibilityIndicator(evaluateEligibilityIndicator(studySubject));
		studySubject.getScheduledEpoch().setScEpochDataEntryStatus(studySubject.evaluateScheduledEpochDataEntryStatus());
		if(studySubject.getParentStudySubject()!=null && studySubject.isDataEntryComplete()){
        	studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED);
        	studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
		}
	}
	
	public boolean evaluateEligibilityIndicator(StudySubject studySubject) {
        boolean flag = true;
        List<SubjectEligibilityAnswer> answers = (studySubject
                        .getScheduledEpoch()).getInclusionEligibilityAnswers();
        for (SubjectEligibilityAnswer subjectEligibilityAnswer : answers) {
            String answerText = subjectEligibilityAnswer.getAnswerText();
            if (answerText == null
                            || answerText.equalsIgnoreCase("")
                            || (!answerText.equalsIgnoreCase("Yes") && !answerText
                                            .equalsIgnoreCase("NA"))) {
                flag = false;
                break;
            }
        }
        if (flag) {
            answers = (studySubject.getScheduledEpoch())
                            .getExclusionEligibilityAnswers();
            for (SubjectEligibilityAnswer subjectEligibilityAnswer : answers) {
                String answerText = subjectEligibilityAnswer.getAnswerText();
                if (answerText == null
                                || answerText.equalsIgnoreCase("")
                                || (!answerText.equalsIgnoreCase("No") && !answerText
                                                .equalsIgnoreCase("NA"))) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }
	
	public void buildCommandObject(StudySubject studySubject) {
//		Study study = studyDao.getById(studySubject.getStudySite().getStudy().getId());
//	    studyDao.initialize(study);
//	    participantDao.initialize(studySubject.getParticipant());
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

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
	
   public String getActionButtonLabel(StudySubjectWrapper wrapper){
    	StudySubject studySubject = wrapper.getStudySubject();
    	
    	String actionLabel = "" ;
    	if (studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.ENROLLED) {
    		if(wrapper.getShouldReserve()){
	    		actionLabel = "Reserve" ;
	    	}else if(wrapper.getShouldRegister()){
	    		actionLabel = "Register" ;
	    	}else if(wrapper.getShouldRandomize()){
	    		actionLabel = "Randomize & Enroll" ;
	    	}else if(wrapper.getShouldEnroll()){
	    		actionLabel = "Enroll" ;
	    	}
    	}else{
    		actionLabel = "Change Epoch" ;
    		if(wrapper.getShouldReserve()){
	    		actionLabel = "Reserve & Change Epoch" ;
	    	}else if(wrapper.getShouldRegister()){
	    		actionLabel ="Register & Change Epoch" ;
	    	}else if(wrapper.getShouldRandomize()){
	    		actionLabel = "Randomize & Change Epoch" ;
	    	}
    	}
    	return actionLabel ;
    }
    
    public String getTabTitle(StudySubjectWrapper wrapper){
    	StudySubject studySubject = wrapper.getStudySubject();
    	String tabTitle = "" ;
    	if (studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.ENROLLED) {
    		if(wrapper.getShouldReserve()){
	    		tabTitle = "Review & Reserve" ;
	    	}else if(wrapper.getShouldRegister()){
	    		tabTitle = "Review & Register" ;
	    	}else if(wrapper.getShouldRandomize()){
	    		tabTitle = "Randomize & Enroll" ;
	    	}else if(wrapper.getShouldEnroll()){
	    		tabTitle = "Review & Enroll" ;
	    	}
    	}else{
    		tabTitle = "Change Epoch" ;
    		if(wrapper.getShouldReserve()){
    			tabTitle = "Reserve & Change Epoch" ;
	    	}else if(wrapper.getShouldRegister()){
	    		tabTitle ="Register & Change Epoch" ;
	    	}else if(wrapper.getShouldRandomize()){
	    		tabTitle = "Randomize & Change Epoch" ;
	    	}
    	}
    	return tabTitle ;
    }

	public StudySubjectRepository getStudySubjectRepository() {
		return studySubjectRepository;
	}

	public void setStudySubjectRepository(
			StudySubjectRepository studySubjectRepository) {
		this.studySubjectRepository = studySubjectRepository;
	}
	
	public List<Companion> getCompanionStudySubject(Identifier identifier, StudySubject studySubject){
    	List<Companion> companions = new ArrayList<Companion>();
    	if(identifier != null){
    		List<Identifier> identifiers=new ArrayList<Identifier>();
    		identifiers.add(identifier);
//    		StudySubject studySubject=studySubjectRepository.getUniqueStudySubjects(identifiers);
    		for(CompanionStudyAssociation companionStudyAssoc : studySubject.getStudySite().getStudy().getCompanionStudyAssociations()){
    			Companion companion = new Companion();
    			Study companionStudy = companionStudyAssoc.getCompanionStudy();
    			companion.setCompanionStudyShortTitle(companionStudy.getShortTitleText());
    			companion.setCompanionStudyPrimaryIdentifier(companionStudy.getPrimaryIdentifier());
    			companion.setCompanionStudyId(companionStudy.getId());
    			companion.setMandatoryIndicator(companionStudyAssoc.getMandatoryIndicator());
				for (StudySubject cStudySubject : studySubject.getChildStudySubjects()) {
					if (companionStudy.getId() == cStudySubject.getStudySite().getStudy().getId()) {
						companion.setRegistrationId(cStudySubject.getId());
						companion.setCompanionRegistrationUrl(ControllerTools.createParameterString(cStudySubject.getSystemAssignedIdentifiers().get(0)));
						companion.setRegistrationStatus(cStudySubject.getRegWorkflowStatus().getDisplayName());
						companion.setRegistrationDataEntryStatus(cStudySubject.getRegDataEntryStatus().getCode());
					}
				}
    			companions.add(companion);
    		}
    	}
    	return companions;
    }
	
	public List<String> getConfirmationMessage(StudySubject studySubject){
		List<String> imageAndMessage = new ArrayList<String>();
		boolean hasCompanion = false ;
		boolean isTransfer = false ;
		if(studySubject.getStudySite().getStudy().getCompanionStudyAssociations().size() > 0){
			hasCompanion = true ;
		}
		int epochs = studySubject.getScheduledEpochs().size() ; 
		if( epochs > 1){
			isTransfer = true ;
		}
		if(!hasCompanion){
			if(studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.REGISTERED){
				imageAndMessage.add("check.png");
				if(studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.ENROLLED){
					if(isTransfer){
						imageAndMessage.add("TRANSFER.ENROLLING.EPOCH.SUCCESS") ;
						return imageAndMessage ;
					}else{
						imageAndMessage.add("REGISTRATION.ENROLLED") ;
						return imageAndMessage ;
					}
				}else if(studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.RESERVED){
					imageAndMessage.add("REGISTRATION.RESERVED") ;
					return imageAndMessage ;
				}else if(studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED){
					if(isTransfer){
						imageAndMessage.add("TRANSFER.NONENROLLED") ;
						return imageAndMessage ;
					}else{
						imageAndMessage.add("REGISTRATION.SUCCESS") ;
						return imageAndMessage ;
					}
				}
			}else if(studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING){
				imageAndMessage.add("error-red.png");
				if(isTransfer){
					imageAndMessage.add("TRANSFER.INCOMPLETE") ;
					return imageAndMessage ;
				}else{
					imageAndMessage.add("REGISTRATION.INCOMPLETE") ;
					return imageAndMessage ;
				}
			}
		}else{
			if(studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.REGISTERED){
				imageAndMessage.add("check.png");
				if(studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.ENROLLED){
					if(isTransfer){
						ScheduledEpoch previousScheduledEpoch = studySubject.getScheduledEpochs().get(epochs - 2);
						if(previousScheduledEpoch.getEpoch().getEnrollmentIndicator()){
							imageAndMessage.add("TRANSFER.ENROLLING.EPOCH.SUCCESS") ;
							return imageAndMessage ;
						}else{
							imageAndMessage.add("REGISTRATION.COMPANION.ENROLLED") ;
							return imageAndMessage ;
						}
					}else{
						imageAndMessage.add("REGISTRATION.COMPANION.ENROLLED") ;
						return imageAndMessage ;
					}
				}else if(studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.RESERVED){
					imageAndMessage.add("REGISTRATION.RESERVED") ;
					return imageAndMessage ;
				}else if(studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED){
					if(isTransfer){
						imageAndMessage.add("TRANSFER.NONENROLLED") ;
						return imageAndMessage ;
					}else{
						imageAndMessage.add("REGISTRATION.SUCCESS") ;
						return imageAndMessage ;
					}
				}
			}else if(studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING){
				imageAndMessage.add("error-red.png");
				if(isTransfer){
					if(studySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator()){
						imageAndMessage.add("TRANSFER.COMPANIONS.INCOMPLETE") ;
						return imageAndMessage ;
					}else{
						imageAndMessage.add("TRANSFER.INCOMPLETE") ;
						return imageAndMessage ;
					}
				}else{
					if(studySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator()){
						imageAndMessage.add("REGISTRATION.COMPANIONS.INCOMPLETE") ;
						return imageAndMessage ;
					}else{
						imageAndMessage.add("REGISTRATION.INCOMPLETE") ;
						return imageAndMessage ;
					}
				}
			}
		}
		return imageAndMessage ;
	}

}
