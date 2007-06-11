package edu.duke.cabig.c3pr.web.admin;

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

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlow;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.AbstractTabbedFlowFormController;

/**
 * @author Ramakrishna
 */
public class CreateResearchStaffController extends
		AbstractTabbedFlowFormController<ResearchStaff> {

	private ResearchStaffDao researchStaffDao;

	private HealthcareSiteDao healthcareSiteDao;

	private ConfigurationProperty configurationProperty;

	public CreateResearchStaffController() {
		setCommandClass(ResearchStaff.class);

		SubFlow<ResearchStaff> flow = new SubFlow<ResearchStaff>(
				"Create Research Staff");

		flow.addTab(new SubFlowTab<ResearchStaff>("Enter Research Staff Information",
				"New Research Staff", "admin/research_staff_details") {
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
		binder.registerCustomEditor(healthcareSiteDao.domainClass(),
				new CustomDaoEditor(healthcareSiteDao));

	}

	/**
	 * Create a nested object graph that Create Research Staff needs
	 * 
	 * @param request -
	 *            HttpServletRequest
	 * @throws ServletException
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		ResearchStaff researchStaff = new ResearchStaff();
		researchStaff = createResearchStaffWithContacts(researchStaff);

		return researchStaff;
	}

	private ResearchStaff createResearchStaffWithContacts(ResearchStaff rs) {

		ContactMechanism contactMechanismEmail = new ContactMechanism();
		ContactMechanism contactMechanismPhone = new ContactMechanism();
		ContactMechanism contactMechanismFax = new ContactMechanism();
		contactMechanismEmail.setType("Email");
		contactMechanismPhone.setType("Phone");
		contactMechanismFax.setType("Fax");
		rs.addContactMechanism(contactMechanismEmail);
		rs.addContactMechanism(contactMechanismPhone);
		rs.addContactMechanism(contactMechanismFax);
		return rs;
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

		ResearchStaff researchStaff = (ResearchStaff) command;

		Iterator<ContactMechanism> cMIterator = researchStaff
				.getContactMechanisms().iterator();
		StringUtils strUtil = new StringUtils();
		while (cMIterator.hasNext()) {
			ContactMechanism contactMechanism = cMIterator.next();
			if (strUtil.isBlank(contactMechanism.getValue()))
				cMIterator.remove();
		}

		researchStaffDao.save(researchStaff);

		return new ModelAndView("forward:createResearchStaff?fullName="
				+ researchStaff.getFullName() + "&type=confirm");

	}

	@Override
	protected void postProcessPage(HttpServletRequest request, Object command,
			Errors arg2, int pageNo) throws Exception {

		switch (pageNo) {
		case 0:
			handleRowAction((ResearchStaff) command, request
					.getParameter("_action"), request.getParameter("_selected"));
			break;

		default:
			// do nothing
		}
	}

	private void handleRowAction(ResearchStaff researchStaff, String action,
			String selected) {
		if ("addContact".equals(action)) {
			ContactMechanism contactMechanism = new ContactMechanism();
			researchStaff.addContactMechanism(contactMechanism);
		} else if ("removeContact".equals(action)) {
			researchStaff.getContactMechanisms().remove(
					Integer.parseInt(selected));
		}

	}

	protected List<HealthcareSite> getHealthcareSites() {
		return healthcareSiteDao.getAll();
	}

	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}

	public void setConfigurationProperty(
			ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}

	public ResearchStaffDao getResearchStaffDao() {
		return researchStaffDao;
	}

	public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
		this.researchStaffDao = researchStaffDao;
	}

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}
}
