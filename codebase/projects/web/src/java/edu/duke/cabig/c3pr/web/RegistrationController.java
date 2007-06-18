package edu.duke.cabig.c3pr.web;

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
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyInvestigatorDao;
import edu.duke.cabig.c3pr.dao.StudyParticipantAssignmentDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.AnatomicSite;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.ObjectGraphBasedEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlow;
import gov.nih.nci.cabig.ctms.web.tabs.AutomaticSaveFlowFormController;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

/**
 * @author Ramakrishna
 * 
 */

public abstract class RegistrationController <C extends StudyParticipantAssignment> extends AutomaticSaveFlowFormController<C, StudyParticipantAssignment, StudyParticipantAssignmentDao> {

	private static Log log = LogFactory
	.getLog(RegistrationController.class);

	protected ParticipantDao participantDao;

	protected StudyParticipantAssignmentDao registrationDao;

	protected HealthcareSiteDao healthcareSiteDao;

	protected StudySiteDao studySiteDao;
	
	protected ArmDao armDao;
	
	protected StudyInvestigatorDao studyInvestigatorDao;
	
	protected AnatomicSiteDao anatomicSiteDao;
	
	protected ConfigurationProperty configurationProperty;

	public RegistrationController(String flowName) {
		setCommandClass(StudyParticipantAssignment.class);
		Flow<StudyParticipantAssignment> flow = new SubFlow<StudyParticipantAssignment>(flowName);
		intializeFlows(flow);
	}

	abstract protected void intializeFlows(Flow<StudyParticipantAssignment> flow);
	
	@Override
	protected StudyParticipantAssignment getPrimaryDomainObject(C command) {
		 return command;
	}
	
    @Override
    protected StudyParticipantAssignmentDao getDao() {
        return registrationDao;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		StudyParticipantAssignment studyParticipantAssignment = null;
		if ((request.getParameter("registrationId") != null)
				&& (request.getParameter("registrationId") != "")) {
			System.out.println(" Request URl  is:"
					+ request.getRequestURL().toString());
			System.out.println(" RegistrationId is: "
					+ Integer.parseInt(request.getParameter("registrationId")));
			System.out.println(" registration Dao is :"
					+ registrationDao.toString());
			studyParticipantAssignment = registrationDao.getById(Integer.parseInt(request
					.getParameter("registrationId")), true);
			System.out.println(" Registration ID is:" + studyParticipantAssignment.getId());
		}else{
			studyParticipantAssignment= new StudyParticipantAssignment();
			System.out.println("------------Command set to new Command------------------");
		}
		return studyParticipantAssignment;
	}
	
	protected void updateRegistration(StudyParticipantAssignment registration){
		registrationDao.save(registration);
	}
	protected void handleIdentifierAction(StudyParticipantAssignment registration, String action,
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
		Object command=binder.getTarget();
/*		binder.registerCustomEditor(StudyInvestigator.class, new ObjectGraphBasedEditor(
				command,"studySite.studyInvestigators"));
*/		binder.registerCustomEditor(StudyDisease.class, new ObjectGraphBasedEditor(
				command,"studySite.study.studyDiseases"));
		binder.registerCustomEditor(StudyInvestigator.class, new CustomDaoEditor(
			studyInvestigatorDao));

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

	public StudyParticipantAssignmentDao getRegistrationDao() {
		return registrationDao;
	}

	public void setRegistrationDao(StudyParticipantAssignmentDao registrationDao) {
		this.registrationDao = registrationDao;
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

}
