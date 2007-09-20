 package edu.duke.cabig.c3pr.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.CalloutRandomization;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PhonecallRandomization;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyAmendment;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.service.StudyService;

/**
 * Services for Study related domain object
 * @see edu.duke.cabig.c3pr.service.StudyService
 * @author Priyatam
 */
public class StudyServiceImpl implements StudyService {

    StudyDao studyDao;
    StudySubjectDao studySubjectDao;

    //TODO hook esb call
    // ProtocolBroadcastService esbCreateProtocol;

    public CoordinatingCenterStudyStatus evaluateCoordinatingCenterStudyStatus(Study study) throws Exception {
		if (evaluateDataEntryStatus(study)!=StudyDataEntryStatus.COMPLETE){
			return CoordinatingCenterStudyStatus.PENDING;
		}
		if (evaluateAmendmentStatus(study)!=StudyDataEntryStatus.COMPLETE){
			return CoordinatingCenterStudyStatus.AMENDMENT_PENDING;
		}
		if(study.getRandomizedIndicator().equals("true"));
		if (evaluateRandomizationDataEntryStatus(study)!=StudyDataEntryStatus.COMPLETE){			
		return CoordinatingCenterStudyStatus.PENDING;
		}		
		return CoordinatingCenterStudyStatus.ACTIVE;
	}
    
    public StudyDataEntryStatus evaluateStratificationDataEntryStatus(Study study) throws Exception{
    	if (study.hasStratification()){
    	return StudyDataEntryStatus.COMPLETE;}
    	return StudyDataEntryStatus.COMPLETE;
    }
    
    public StudyDataEntryStatus evaluateRandomizationDataEntryStatus(Study study) throws Exception{
    	
    	if (study.getRandomizationType()==(RandomizationType.BOOK)){
    		for (TreatmentEpoch treatmentEpoch:study.getTreatmentEpochs()){
    			if(treatmentEpoch.hasBookRandomizationEntry()){
    				if(!treatmentEpoch.hasStratumGroups())
    					return StudyDataEntryStatus.INCOMPLETE;
    			}
    		}
    		return StudyDataEntryStatus.COMPLETE;
    	}
    	
    	if (study.getRandomizationType()==(RandomizationType.PHONE_CALL)){
    		for (TreatmentEpoch treatmentEpoch:study.getTreatmentEpochs()){
    			Randomization randomization = treatmentEpoch.getRandomization();
    			if (randomization instanceof PhonecallRandomization)
    			{
    				if((((PhonecallRandomization)randomization).getPhoneNumber())!= null){
    					return StudyDataEntryStatus.COMPLETE;
    				}
    			}
    		}
    	}
    	
    	if (study.getRandomizationType()==(RandomizationType.CALL_OUT)){
    		for (TreatmentEpoch treatmentEpoch:study.getTreatmentEpochs()){
    			Randomization randomization = treatmentEpoch.getRandomization();
    			if (randomization instanceof PhonecallRandomization)
    			{
    				if((((CalloutRandomization)randomization).getCalloutUrl())!= null){
    					return StudyDataEntryStatus.COMPLETE;
    				}
    			}
    		}
    	}
    	return StudyDataEntryStatus.COMPLETE;
    }
    
    public StudyDataEntryStatus evaluateEligibilityDataEntryStatus(Study study) throws Exception{
    	return StudyDataEntryStatus.COMPLETE;
    }
    
    public StudyDataEntryStatus evaluateAmendmentStatus(Study study) throws Exception{
    	
    	if (study.getStudyAmendments().size()>0){
    		StudyAmendment latestAmendment = study.getStudyAmendments().get(study.getStudyAmendments().size()-1);
    		
    		if((latestAmendment.getIrbApprovalDate()==null)||(latestAmendment.getVersion()==null) ){
   			 return StudyDataEntryStatus.INCOMPLETE;
   		 }
   	        if ((latestAmendment.getConsentChangedIndicator()==true)||(latestAmendment.getDiseasesChangedIndicator()==true)||(latestAmendment.getEligibilityChangedIndicator()==true)||(latestAmendment.getEpochAndArmsChangedIndicator()==true)
   	        		||(latestAmendment.getStratificationChangedIndicator()==true)||(latestAmendment.getPrincipalInvestigatorChangedIndicator()==true))
   	        return StudyDataEntryStatus.COMPLETE;
   	        
   	        return StudyDataEntryStatus.COMPLETE;
    		
    	    	
    	}
    	return StudyDataEntryStatus.COMPLETE;
    }
        

	public StudyDataEntryStatus evaluateDataEntryStatus(Study study) throws Exception {
		
		if((study.getStudySites().size()==0) ||(!study.hasEnrollingEpoch()))
		return StudyDataEntryStatus.INCOMPLETE;
		
		if(study.hasElligibility()){
		if(evaluateEligibilityDataEntryStatus(study)!=StudyDataEntryStatus.COMPLETE)
			return StudyDataEntryStatus.INCOMPLETE;
		}
		if (study.hasStratification()){
		if(evaluateStratificationDataEntryStatus(study)!=StudyDataEntryStatus.COMPLETE)
			return StudyDataEntryStatus.INCOMPLETE;
		}
		
		return StudyDataEntryStatus.COMPLETE;
	}
	
	public SiteStudyStatus evaluateSiteStudyStatus(StudySite studySite)	{
		
		if(studySite.getStudy().getCoordinatingCenterStudyStatus()==CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL){
			return SiteStudyStatus.CLOSED_TO_ACCRUAL;		
		}
		if(studySite.getStudy().getCoordinatingCenterStudyStatus()==CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT){
			return SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT;
		}
		
		if(studySite.getStudy().getCoordinatingCenterStudyStatus()==CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL){
			return SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL;		
		}
		if(studySite.getStudy().getCoordinatingCenterStudyStatus()==CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT){
			return SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT;		
		}
		
		if(studySite.getStudy().getCoordinatingCenterStudyStatus()==CoordinatingCenterStudyStatus.AMENDMENT_PENDING){
			return SiteStudyStatus.AMENDMENT_PENDING;		
		}
		if(studySite.getStudy().getCoordinatingCenterStudyStatus()==CoordinatingCenterStudyStatus.ACTIVE){
			Date currentDate = new Date();
			if(((studySite.getIrbApprovalDate())!=null) && (studySite.getIrbApprovalDate().before(currentDate)))
			return SiteStudyStatus.ACTIVE;		
		}
			
		return SiteStudyStatus.PENDING;
	}


	/**
     * Search using a sample populate Study object
     * @param study the study object
     * @return List of Study objects based on the sample study object
     * @throws Exception runtime exception object
     */
    public List<Study> search(Study study) throws Exception {
        return studyDao.searchByExample(study, true);
    }

    /**
     * Saves a study object
     * @param study the study object
     * @throws Exception runtime exception object
     */
    public void save(Study study) throws Exception {
        //TODO call esb to broadcast protocol, POC
        studyDao.save(study);
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }


    public StudySubjectDao getStudyParticipantDao() {
        return studySubjectDao;
    }

    public void setStudyParticipantDao(StudySubjectDao studyParticipantDao) {
        this.studySubjectDao = studyParticipantDao;
    }

    /**
     * Assigns a Participant to a Study at a particular Site.
     * The Study and Site must already exist and be associated.
     *
     * @param study
     * @param participant
     * @param site
     * @return StudySubject for the Participant
     */
    public StudySubject assignParticipant(Study study, Participant participant,
                                                        HealthcareSite site, Date enrollmentDate) {
// new assignment       
        StudySubject assignment = new StudySubject();

// study shld exist
        Study assignedStudy = studyDao.getByGridId(study.getGridId());

        assignment.setParticipant(participant);
        assignment.setStartDate(enrollmentDate);
        studySubjectDao.save(assignment);

        return assignment;
    }
    
    public Study setStatuses(Study study){
    	
    	try{
    		study.setDataEntryStatus(evaluateDataEntryStatus(study));
    		study.setCoordinatingCenterStudyStatus(evaluateCoordinatingCenterStudyStatus(study));
    		for(int i=0; i<study.getStudySites().size();i++){
    			study.getStudySites().get(i).setSiteStudyStatus(evaluateSiteStudyStatus(study.getStudySites().get(i)));
    		}
    		}
    		catch(Exception ex){
    			ex.printStackTrace();
    		}
    	
    	
    	return study;
    }

	public Study merge(Study study) {
		study = setStatuses(study);
		return studyDao.merge(study);
	}
	
	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}
}
