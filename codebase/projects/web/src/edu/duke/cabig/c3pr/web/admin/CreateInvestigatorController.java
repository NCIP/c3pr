package edu.duke.cabig.c3pr.web.admin;

import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AbstractTabbedFlowFormController;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Ramakrishna
 */
public class CreateInvestigatorController extends
		AbstractTabbedFlowFormController<Investigator> {

	private InvestigatorDao investigatorDao;

	private HealthcareSiteDao healthcareSiteDao;

	private ConfigurationProperty configurationProperty;

	private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

	public CreateInvestigatorController() {
		setCommandClass(Investigator.class);

		Flow<Investigator> flow = new Flow<Investigator>("Create Investigator");

		flow.addTab(new Tab<Investigator>("Enter Investigator Information",
				"New Investigator", "admin/investigator_details") {
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty
						.getMap();

				Map<String, Object> refdata = new HashMap<String, Object>();

				refdata.put("studySiteStatusRefData", configMap
						.get("studySiteStatusRefData"));
				refdata.put("healthcareSites", healthcareSiteDao.getAll());
				refdata.put("action", "New");
				return refdata;
			}

			@Override
			public boolean isAllowDirtyForward() {
				return false;
			}
		});

		setFlow(flow);
	}

	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		binder.registerCustomEditor(Date.class, ControllerTools
				.getDateEditor(true));
		binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(
				healthcareSiteDao));
	}

	/**
	 * Create a nested object graph that Create Investigator Design needs
	 * 
	 * @param request -
	 *            HttpServletRequest
	 * @throws ServletException
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		return createInvestigatorWithDesign();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish
	 *      (javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		Investigator inv = (Investigator) command;

		Iterator<ContactMechanism> cMIterator = inv.getContactMechanisms()
				.iterator();
		StringUtils strUtil = new StringUtils();
		while (cMIterator.hasNext()) {
			ContactMechanism contactMechanism = cMIterator.next();
			if (strUtil.isBlank(contactMechanism.getValue()))
				cMIterator.remove();
		}

		investigatorDao.save(inv);
		// response.sendRedirect("createInvestigator?fullName="
		// + inv.getFullName() + "&type=confirm");
		return new ModelAndView("redirect:createInvestigator?fullName="
				+ inv.getFullName() + "&type=confirm");

	}

	@Override
	protected void postProcessPage(HttpServletRequest request, Object command,
			Errors arg2, int pageNo) throws Exception {

		switch (pageNo) {
		case 0:
			handleRowAction((Investigator) command, request
					.getParameter("_action"), request.getParameter("_selected"));
			break;

		default:
			// do nothing
		}
	}

	private void handleRowAction(Investigator investigator, String action,
			String selected) {
		if ("addSite".equals(action)) {
			HealthcareSiteInvestigator healthcareSiteInvestigator = new HealthcareSiteInvestigator();
			investigator
					.addHealthcareSiteInvestigator(healthcareSiteInvestigator);
		} else if ("removeSite".equals(action)
				&& (investigator.getHealthcareSiteInvestigators().size() > 1)) {

			investigator.getHealthcareSiteInvestigators().remove(
					Integer.parseInt(selected));
		} else if ("addContact".equals(action)) {
			ContactMechanism contactMechanism = new ContactMechanism();
			investigator.addContactMechanism(contactMechanism);
		} else if ("removeContact".equals(action)) {
			investigator.getContactMechanisms().remove(
					Integer.parseInt(selected));
		}

	}

	private Investigator createInvestigatorWithDesign() {

		Investigator investigator = new Investigator();
		HealthcareSiteInvestigator healthcareSiteInvestigator = new HealthcareSiteInvestigator();
		investigator.addHealthcareSiteInvestigator(healthcareSiteInvestigator);
		investigator = CreateInvestigatorWithContacts(investigator);

		return investigator;
	}

	private Investigator CreateInvestigatorWithContacts(Investigator inv) {

		ContactMechanism contactMechanismEmail = new ContactMechanism();
		ContactMechanism contactMechanismPhone = new ContactMechanism();
		ContactMechanism contactMechanismFax = new ContactMechanism();
		contactMechanismEmail.setType("Email");
		contactMechanismPhone.setType("Phone");
		contactMechanismFax.setType("Fax");
		inv.addContactMechanism(contactMechanismEmail);
		inv.addContactMechanism(contactMechanismPhone);
		inv.addContactMechanism(contactMechanismFax);
		return inv;
	}

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
		return healthcareSiteInvestigatorDao;
	}

	public void setHealthcareSiteInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
		this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
	}

	public InvestigatorDao getInvestigatorDao() {
		return investigatorDao;
	}

	public void setInvestigatorDao(InvestigatorDao investigatorDao) {
		this.investigatorDao = investigatorDao;
	}

	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}

	public void setConfigurationProperty(
			ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}
}