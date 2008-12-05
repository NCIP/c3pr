package edu.duke.cabig.c3pr.web.registration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.dao.AnatomicSiteDao;
import edu.duke.cabig.c3pr.dao.ArmDao;
import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.ScheduledEpochDao;
import edu.duke.cabig.c3pr.dao.StratificationCriterionAnswerDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudyInvestigatorDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.AnatomicSite;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Companion;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.ObjectGraphBasedEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AutomaticSaveAjaxableFormController;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * @author Ramakrishna
 */

public abstract class RegistrationController<C extends StudySubjectWrapper> extends
                AutomaticSaveAjaxableFormController<C, StudySubject, StudySubjectDao> {

    private static Log log = LogFactory.getLog(RegistrationController.class);

    protected ParticipantDao participantDao;

    protected StudySubjectDao studySubjectDao;

    protected HealthcareSiteDao healthcareSiteDao;

    protected StudySiteDao studySiteDao;

    protected EpochDao epochDao;

    protected ArmDao armDao;

    protected StudyInvestigatorDao studyInvestigatorDao;

    protected AnatomicSiteDao anatomicSiteDao;

    protected ConfigurationProperty configurationProperty;

    protected StudySubjectService studySubjectService;

    protected StratificationCriterionAnswerDao stratificationAnswerDao;

    protected ScheduledEpochDao scheduledEpochDao;

    protected StudyDao studyDao;

    protected StudySubjectRepository studySubjectRepository;
    
    protected RegistrationControllerUtils registrationControllerUtils;
    
    protected C3PRExceptionHelper exceptionHelper;
    
    public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}


	public void setRegistrationControllerUtils(
			RegistrationControllerUtils registrationControllerUtils) {
		this.registrationControllerUtils = registrationControllerUtils;
	}

    
    public void setStudySubjectRepository(StudySubjectRepository studySubjectRepository) {
        this.studySubjectRepository = studySubjectRepository;
    }

    public ScheduledEpochDao getScheduledEpochDao() {
        return scheduledEpochDao;
    }

    public void setScheduledEpochDao(ScheduledEpochDao scheduledEpochDao) {
        this.scheduledEpochDao = scheduledEpochDao;
    }

    public StratificationCriterionAnswerDao getStratificationAnswerDao() {
        return stratificationAnswerDao;
    }

    public void setStratificationAnswerDao(StratificationCriterionAnswerDao stratificationAnswerDao) {
        this.stratificationAnswerDao = stratificationAnswerDao;
    }

    public StudySubjectDao getStudySubjectDao() {
        return studySubjectDao;
    }

    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

    public void setStudySubjectService(StudySubjectService studySubjectService) {
        this.studySubjectService = studySubjectService;
    }

    public RegistrationController(String flowName) {
        setCommandClass(StudySubject.class);
        Flow<StudySubject> flow = new Flow<StudySubject>(flowName);
        intializeFlows(flow);
    }

//    @Override
//    protected boolean isFormSubmission(HttpServletRequest request) {
//    	if (WebUtils.hasSubmitParameter(request, "registrationId")&& WebUtils.hasSubmitParameter(request, "goToTab")) {
//            try {
//                request.getSession(false).setAttribute(getFormSessionAttributeName(),
//                                formBackingObject(request));
//                return true;
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    	return super.isFormSubmission(request);
//    }
    
    @Override
    protected Object currentFormObject(HttpServletRequest request,
    		Object command) throws Exception {
    	return command;
    }
    
    @Override
    protected boolean shouldPersist(HttpServletRequest request, C command, Tab<C> tab) {
        if (WebUtils.hasSubmitParameter(request, "dontSave")) return false;
        return true;
    }
    
    @Override
    protected boolean isNextPageSavable(HttpServletRequest request, C command, Tab<C> tab) {
    	return true;
    }

    abstract protected void intializeFlows(Flow<StudySubject> flow);

    @Override
    protected StudySubject getPrimaryDomainObject(C command) {
        return command.getStudySubject();
    }

    @Override
    protected C save(C command, Errors arg1) {
    	StudySubject merged =null;
    	if(getPrimaryDomainObject(command).getId()==null)
    		merged=studySubjectRepository.create(getPrimaryDomainObject(command));
    	else
    		merged = (StudySubject) getDao().merge(getPrimaryDomainObject(command));
        studyDao.initialize(merged.getStudySite().getStudy());
        studySiteDao.initialize(merged.getStudySite());
        command.setStudySubject(merged);
        return command;
    }

    @Override
    protected StudySubjectDao getDao() {
        return studySubjectDao;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        StudySubject studySubject = null;
        StudySubjectWrapper wrapper = new StudySubjectWrapper();
        if (WebUtils.hasSubmitParameter(request, ControllerTools.IDENTIFIER_VALUE_PARAM_NAME)) {
        	Identifier identifier=ControllerTools.getIdentifierInRequest(request);
        	List<Identifier> identifiers=new ArrayList<Identifier>();
        	identifiers.add(identifier);
        	studySubject=studySubjectRepository.getUniqueStudySubjects(identifiers);
//            studySubject = studySubjectDao.getById(Integer.parseInt(request
//                            .getParameter("registrationId")), true);
            studySubjectDao.initialize(studySubject);
            Study study = studyDao.getById(studySubject.getStudySite().getStudy().getId());
    	    studyDao.initialize(study);
    	    for(CompanionStudyAssociation companionStudyAssoc : study.getCompanionStudyAssociations()){
    	    	Study companionStudy = companionStudyAssoc.getCompanionStudy();
    	    	studyDao.initialize(companionStudy);
    	    }
        }
        else {
            studySubject = new StudySubject();
            log.debug("------------Command set to new Command------------------");
        }
        wrapper.setStudySubject(studySubject);
        return wrapper;
    }

    
    @Override
    protected void postProcessPage(HttpServletRequest request, Object command, Errors errors,
                    int page) throws Exception {
        // TODO Auto-generated method stub
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command;
        StudySubject studySubject = wrapper.getStudySubject();
    	super.postProcessPage(request, command, errors, page);
        if (studySubject.getScheduledEpoch() != null) {
            studySubject.updateDataEntryStatus();
        }
    }
    
    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
                    throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(
                        "MM/dd/yyyy"), true));
        binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(healthcareSiteDao));
        binder.registerCustomEditor(StudySite.class, new CustomDaoEditor(studySiteDao));
        binder.registerCustomEditor(EligibilityCriteria.class, new CustomDaoEditor(studySiteDao));
        binder.registerCustomEditor(Participant.class, new CustomDaoEditor(participantDao));
        binder.registerCustomEditor(AnatomicSite.class, new CustomDaoEditor(anatomicSiteDao));
        binder.registerCustomEditor(Arm.class, new CustomDaoEditor(armDao));
        binder.registerCustomEditor(Epoch.class, new CustomDaoEditor(epochDao));
        binder.registerCustomEditor(StratificationCriterionPermissibleAnswer.class,
                        new CustomDaoEditor(stratificationAnswerDao));
        Object command = binder.getTarget();
        binder.registerCustomEditor(StudyDisease.class, new ObjectGraphBasedEditor(command,
                        "studySite.study.studyDiseases"));
        binder.registerCustomEditor(StudyInvestigator.class, new CustomDaoEditor(
                        studyInvestigatorDao));
        binder.registerCustomEditor(ScheduledEpoch.class, new CustomDaoEditor(scheduledEpochDao));
        binder.registerCustomEditor(RandomizationType.class, new EnumByNameEditor(
                        RandomizationType.class));
        binder.registerCustomEditor(RegistrationDataEntryStatus.class, new EnumByNameEditor(
                        RegistrationDataEntryStatus.class));
        binder.registerCustomEditor(RegistrationWorkFlowStatus.class, new EnumByNameEditor(
                        RegistrationWorkFlowStatus.class));
        binder.registerCustomEditor(ScheduledEpochDataEntryStatus.class, new EnumByNameEditor(
                        ScheduledEpochDataEntryStatus.class));
        binder.registerCustomEditor(ScheduledEpochWorkFlowStatus.class, new EnumByNameEditor(
                        ScheduledEpochWorkFlowStatus.class));
    }

    public ConfigurationProperty getConfigurationProperty() {
        return configurationProperty;
    }

    public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
        this.configurationProperty = configurationProperty;
    }

    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

    public ParticipantDao getParticipantDao() {
        return participantDao;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

    public StudySubjectDao getRegistrationDao() {
        return studySubjectDao;
    }

    public void setRegistrationDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

    public StudySiteDao getStudySiteDao() {
        return studySiteDao;
    }

    public void setStudySiteDao(StudySiteDao studySiteDao) {
        this.studySiteDao = studySiteDao;
    }

    public ArmDao getArmDao() {
        return armDao;
    }

    public void setArmDao(ArmDao armDao) {
        this.armDao = armDao;
    }

    public StudyInvestigatorDao getStudyInvestigatorDao() {
        return studyInvestigatorDao;
    }

    public void setStudyInvestigatorDao(StudyInvestigatorDao studyInvestigatorDao) {
        this.studyInvestigatorDao = studyInvestigatorDao;
    }

    public AnatomicSiteDao getAnatomicSiteDao() {
        return anatomicSiteDao;
    }

    public void setAnatomicSiteDao(AnatomicSiteDao anatomicSiteDao) {
        this.anatomicSiteDao = anatomicSiteDao;
    }

    public EpochDao getEpochDao() {
        return epochDao;
    }

    public void setEpochDao(EpochDao epochDao) {
        this.epochDao = epochDao;
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }
    
}
