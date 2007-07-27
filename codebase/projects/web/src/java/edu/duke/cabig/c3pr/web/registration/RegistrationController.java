package edu.duke.cabig.c3pr.web.registration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;

import edu.duke.cabig.c3pr.dao.AnatomicSiteDao;
import edu.duke.cabig.c3pr.dao.ArmDao;
import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.ScheduledEpochDao;
import edu.duke.cabig.c3pr.dao.StratificationCriterionAnswerDao;
import edu.duke.cabig.c3pr.dao.StudyInvestigatorDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.AnatomicSite;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.service.impl.StudySubjectServiceImpl;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.ObjectGraphBasedEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AutomaticSaveAjaxableFormController;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlow;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * @author Ramakrishna
 * 
 */

public abstract class RegistrationController <C extends StudySubject> extends AutomaticSaveAjaxableFormController<C, StudySubject, StudySubjectDao> {

	private static Log log = LogFactory
	.getLog(RegistrationController.class);

	protected ParticipantDao participantDao;

	protected StudySubjectDao studySubjectDao;

	protected HealthcareSiteDao healthcareSiteDao;

	protected StudySiteDao studySiteDao;
	
	protected EpochDao epochDao;
	
	protected ArmDao armDao;
	
	protected StudyInvestigatorDao studyInvestigatorDao;
	
	protected AnatomicSiteDao anatomicSiteDao;
	
	protected ConfigurationProperty configurationProperty;

	protected StudySubjectServiceImpl studySubjectService;
	
	protected StratificationCriterionAnswerDao stratificationAnswerDao;
	
	protected ScheduledEpochDao scheduledEpochDao;
	
	public ScheduledEpochDao getScheduledEpochDao() {
		return scheduledEpochDao;
	}


	public void setScheduledEpochDao(ScheduledEpochDao scheduledEpochDao) {
		this.scheduledEpochDao = scheduledEpochDao;
	}


	public StratificationCriterionAnswerDao getStratificationAnswerDao() {
		return stratificationAnswerDao;
	}


	public void setStratificationAnswerDao(
			StratificationCriterionAnswerDao stratificationAnswerDao) {
		this.stratificationAnswerDao = stratificationAnswerDao;
	}


	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}


	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}


	public StudySubjectServiceImpl getStudySubjectService() {
		return studySubjectService;
	}


	public void setStudySubjectService(StudySubjectServiceImpl studySubjectService) {
		this.studySubjectService = studySubjectService;
	}

	public RegistrationController(String flowName) {
		setCommandClass(StudySubject.class);
		Flow<StudySubject> flow = new SubFlow<StudySubject>(flowName);
		intializeFlows(flow);
	}
	@Override
	protected Object currentFormObject(HttpServletRequest request, Object sessionFormObject) throws Exception {
		// TODO Auto-generated method stub
		StudySubject command=(StudySubject) sessionFormObject;
		if (sessionFormObject != null) {
			if(command.getId()!=null){
				getDao().reassociate(command);
				if(command.getScheduledEpoch()!=null && command.getScheduledEpoch().getEpoch()!=null)
					epochDao.reassociate(command.getScheduledEpoch().getEpoch());
				return command;
			}
			if(command.getParticipant()!=null)
				getParticipantDao().reassociate(command.getParticipant());
			if(command.getStudySite()!=null)
				getStudySiteDao().reassociate(command.getStudySite());
		}
		return sessionFormObject;
	}
	
	@Override
	protected boolean shouldSave(HttpServletRequest request, C command, Tab<C> tab) {
		if(getPrimaryDomainObject(command)==null)
			return false;
		return getPrimaryDomainObject(command).getId() != null;
	}

	abstract protected void intializeFlows(Flow<StudySubject> flow);
	
	@Override
	protected StudySubject getPrimaryDomainObject(C command) {
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
		if ((request.getParameter("registrationId") != null)
				&& (request.getParameter("registrationId") != "")) {
			studySubject = studySubjectDao.getById(Integer.parseInt(request
					.getParameter("registrationId")), true);
		}else{
			studySubject= new StudySubject();
			System.out.println("------------Command set to new Command------------------");
		}
		return studySubject;
	}
	
	protected void updateRegistration(StudySubject registration){
		studySubjectDao.save(registration);
	}
	protected void handleIdentifierAction(StudySubject registration, String action,
			String selected) {
		if ("addIdentifier".equals(action)) {
			log.debug("Requested Add Identifier");
			Identifier id = new Identifier();
			id.setSource("<enter value>");
			id.setType("<enter value>");
			id.setValue("<enter value>");
			registration.addIdentifier(id);
		} else if ("removeIdentifier".equals(action)) {
			log.debug("Requested Remove Identifier");
			registration.getIdentifiers().remove(Integer.parseInt(selected));
		}
	}
	@Override
	protected Map<String, Object> referenceData(
			HttpServletRequest httpServletRequest, int page) throws Exception {
		// Currently the static data is a hack, once DB design is approved for
		// an LOV this will be
		// replaced with LOVDao to get the static data from individual tables
		Map<String, Object> refdata = new HashMap<String, Object>();
		Map<String, List<Lov>> configMap = configurationProperty.getMap();
		refdata
				.put("searchTypeRefData", configMap
						.get("participantSearchType"));
		refdata.put("identifiersTypeRefData", configMap
				.get("participantIdentifiersType"));

		return refdata;
	}

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM/dd/yyyy"), true));
		binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(
				healthcareSiteDao));
		binder.registerCustomEditor(StudySite.class, new CustomDaoEditor(
				studySiteDao));
		binder.registerCustomEditor(EligibilityCriteria.class, new CustomDaoEditor(
				studySiteDao));
		binder.registerCustomEditor(Participant.class, new CustomDaoEditor(
				participantDao));
		binder.registerCustomEditor(AnatomicSite.class, new CustomDaoEditor(
				anatomicSiteDao));
		binder.registerCustomEditor(Arm.class, new CustomDaoEditor(
				armDao));
		binder.registerCustomEditor(Epoch.class, new CustomDaoEditor(
				epochDao));
		binder.registerCustomEditor(StratificationCriterionPermissibleAnswer.class, new CustomDaoEditor(
				stratificationAnswerDao));
		Object command=binder.getTarget();
/*		binder.registerCustomEditor(StudyInvestigator.class, new ObjectGraphBasedEditor(
				command,"studySite.studyInvestigators"));
*/		binder.registerCustomEditor(StudyDisease.class, new ObjectGraphBasedEditor(
				command,"studySite.study.studyDiseases"));
		binder.registerCustomEditor(StudyInvestigator.class, new CustomDaoEditor(
			studyInvestigatorDao));
		binder.registerCustomEditor(ScheduledEpoch.class, new CustomDaoEditor(
				scheduledEpochDao));
	}

	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}

	public void setConfigurationProperty(
			ConfigurationProperty configurationProperty) {
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

}
