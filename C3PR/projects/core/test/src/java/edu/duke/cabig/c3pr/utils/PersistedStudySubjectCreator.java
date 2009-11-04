package edu.duke.cabig.c3pr.utils;

import java.util.Date;

import org.springframework.context.ApplicationContext;

import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySubject;

public class PersistedStudySubjectCreator extends StudySubjectCreatorHelper {

    private StudySubjectDao studySubjectDao;
    private StudyDao studyDao;
    private StudySiteDao studySiteDao;
    private ParticipantDao participantDao;
    private StudySubject studySubject;
    private HealthcareSiteDao healthcareSiteDao;
    
    public PersistedStudySubjectCreator(ApplicationContext context){
        studySubjectDao=(StudySubjectDao)context.getBean("studySubjectDao");
        studyDao=(StudyDao)context.getBean("studyDao");
        studySiteDao=(StudySiteDao)context.getBean("studySiteDao");
        participantDao=(ParticipantDao)context.getBean("participantDao");
        healthcareSiteDao=(HealthcareSiteDao)context.getBean("healthcareSiteDao");
        studySubject=new StudySubject();
        Date startDate = new Date();
        startDate.setMonth(startDate.getMonth()+1);
        studySubject.setStartDate(startDate);
    }
    
    public StudySubject getPersistedLocalNonRandomizedStudySubject(Boolean reserving, Boolean enrolling, boolean makeStudysiteCoCenter) {
        studySubject.setStudySite(getLocalNonRandomizedStudySite(reserving, enrolling, makeStudysiteCoCenter));
        prepareToPersistNewStudySubject(studySubject);
        studySubjectDao.save(studySubject);
        return studySubject;
    }
    
    public StudySubject getPersistedLocalNonRandomizedWithArmStudySubject(boolean makeStudysiteCoCenter) {
        studySubject.setStudySite(getLocalNonRandomizedTreatmentWithArmStudySite( makeStudysiteCoCenter));
        prepareToPersistNewStudySubject(studySubject);
        studySubjectDao.save(studySubject);
        return studySubject;
    }
    
    public StudySubject getPersistedLocalRandomizedStudySubject(RandomizationType randomizationType,
                    boolean makeStudysiteCoCenter) throws Exception {
        studySubject.setStudySite(getLocalRandomizedStudySite(randomizationType, makeStudysiteCoCenter));
        prepareToPersistNewStudySubject(studySubject);
        studySubjectDao.save(studySubject);
        return studySubject;
    }
    
    public StudySubject getPersistedMultiSiteNonRandomizedStudySubject(Boolean reserving, Boolean enrolling,
                    boolean makeStudysiteCoCenter) {
        studySubject.setStudySite(getMultiSiteNonRandomizedStudySite(reserving, enrolling, makeStudysiteCoCenter));
        prepareToPersistNewStudySubject(studySubject);
        studySubjectDao.save(studySubject);
        return studySubject;
    }
    
    public StudySubject getPersistedMultiSiteNonRandomizedWithArmStudySubject(boolean makeStudysiteCoCenter) {
        studySubject.setStudySite(getMultiSiteNonRandomizedWithArmStudySite(makeStudysiteCoCenter));
        prepareToPersistNewStudySubject(studySubject);
        studySubjectDao.save(studySubject);
        return studySubject;
    }
    
    public StudySubject getPersistedMultiSiteRandomizedStudySubject(RandomizationType randomizationType,
                    boolean makeStudysiteCoCenter) throws Exception {
        studySubject.setStudySite(getMultiSiteRandomizedStudySite(randomizationType, makeStudysiteCoCenter));
        prepareToPersistNewStudySubject(studySubject);
        studySubjectDao.save(studySubject);
        return studySubject;
    }
    
    public void prepareToPersistNewStudySubject(StudySubject studySubject){
        for(StudyOrganization studyOrganization:studySubject.getStudySite().getStudy().getStudyOrganizations())
            healthcareSiteDao.save(studyOrganization.getHealthcareSite());
        Consent consent = new Consent();
        consent.setName("new consent");
        studySubject.getStudySite().getStudy().getStudyVersion().addConsent(consent);
        studyDao.save(studySubject.getStudySite().getStudy());
        studySubject.setParticipant(createNewParticipant());
        addMRNIdentifierToSubject(studySubject.getParticipant(), studySubject.getStudySite().getHealthcareSite());
        participantDao.save(studySubject.getParticipant());
    }
    
    public StudySubject getLocalNonRandomizedStudySubject(Boolean reserving, Boolean enrolling,
                    boolean makeStudysiteCoCenter) {
        studySubject.setStudySite(getLocalNonRandomizedStudySite(reserving, enrolling, makeStudysiteCoCenter));
        prepareToPersistNewStudySubject(studySubject);
        return studySubject;
    }
    
    public StudySubject getLocalNonRandomizedTrestmentWithArmStudySubject(boolean makeStudysiteCoCenter) {
        studySubject.setStudySite(getLocalNonRandomizedTreatmentWithArmStudySite( makeStudysiteCoCenter));
        prepareToPersistNewStudySubject(studySubject);
        return studySubject;
    }
    
    public StudySubject getLocalNonRandomizedTrestmentWithoutArmStudySubject(boolean makeStudysiteCoCenter) {
        studySubject.setStudySite(getLocalNonRandomizedTreatmentWithoutArmStudySite( makeStudysiteCoCenter));
        prepareToPersistNewStudySubject(studySubject);
        return studySubject;
    }
    
    public StudySubject getLocalRandomizedStudySubject(RandomizationType randomizationType,
                    boolean makeStudysiteCoCenter) throws Exception {
        studySubject.setStudySite(getLocalRandomizedStudySite(randomizationType, makeStudysiteCoCenter));
        prepareToPersistNewStudySubject(studySubject);
        return studySubject;
    }
    
    public StudySubject getMultiSiteNonRandomizedStudySubject(Boolean reserving, Boolean enrolling,
                    boolean makeStudysiteCoCenter) {
        studySubject.setStudySite(getMultiSiteNonRandomizedStudySite(reserving, enrolling, makeStudysiteCoCenter));
        prepareToPersistNewStudySubject(studySubject);
        return studySubject;
    }
    
    public StudySubject getMultiSiteNonRandomizedWithArmStudySubject(boolean makeStudysiteCoCenter) {
        studySubject.setStudySite(getMultiSiteNonRandomizedWithArmStudySite(makeStudysiteCoCenter));
        prepareToPersistNewStudySubject(studySubject);
        return studySubject;
    }
    
    public StudySubject getMultiSiteRandomizedStudySubject(RandomizationType randomizationType,
                    boolean makeStudysiteCoCenter) throws Exception {
        studySubject.setStudySite(getMultiSiteRandomizedStudySite(randomizationType, makeStudysiteCoCenter));
        prepareToPersistNewStudySubject(studySubject);
        return studySubject;
    }
}
