package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyParticipantAssignmentDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;

/**
 * @author Ramakrishna
 *
 */

public class RegistrationDetailsController extends AbstractWizardFormController{
	
	private static Log log = LogFactory.getLog(RegistrationDetailsController.class);

	private ParticipantDao participantDao;
	
	private StudyParticipantAssignmentDao registrationDao;

	private HealthcareSiteDao healthcareSiteDao;
	
	private StudySiteDao studySiteDao;

	protected ConfigurationProperty configurationProperty;

	public RegistrationDetailsController() {
		setCommandClass(StudyParticipantAssignment.class);
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, BindException arg3) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		StudyParticipantAssignment registration = null;
		System.out.println(" registration...id is " + request
					.getParameter("registrationId"));
		if ((request.getParameter("registrationId") != null) &&(request.getParameter("registrationId") != "") ) {
			System.out.println(" Request URl  is:"
					+ request.getRequestURL().toString());
			System.out.println( " RegistrationId is: " + Integer.parseInt(request
					.getParameter("registrationId")));
			System.out.println(" registration Dao is :" + registrationDao.toString());
			registration = registrationDao.getById(Integer.parseInt(request
					.getParameter("registrationId")),true);
			System.out.println(" Registration ID is:" + registration.getId());
			}
		
		else return new StudyParticipantAssignment();
		
		return registration;
	}
	
	@Override
	protected Map<String, Object> referenceData(
			HttpServletRequest httpServletRequest, int page) throws Exception {
		// Currently the static data is a hack, once DB design is approved for
		// an LOV this will be
		// replaced with LOVDao to get the static data from individual tables
		Map<String, Object> refdata = new HashMap<String, Object>();
		Map<String, List<Lov>> configMap = configurationProperty.getMap();
		refdata.put("source", healthcareSiteDao.getAll());
		refdata.put("searchTypeRefData", configMap.get("participantSearchType"));
		refdata.put("identifiersTypeRefData", configMap.get("participantIdentifiersType"));

		return refdata;
	}
	@Override
	protected void initBinder(HttpServletRequest req,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM/dd/yyyy"), true));
		binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(
				healthcareSiteDao));
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


}
