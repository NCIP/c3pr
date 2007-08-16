package edu.duke.cabig.c3pr.web;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.RowManager;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import gov.nih.nci.cabig.ctms.web.tabs.AbstractTabbedFlowFormController;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author Kulasekaran, Priyatam
 * 
 */
public class CreateParticipantController extends
		AbstractTabbedFlowFormController<Participant> {

	protected static final Log log = LogFactory
			.getLog(CreateParticipantController.class);

	private ParticipantDao participantDao;

	protected ConfigurationProperty configurationProperty;

	private ParticipantValidator participantValidator;

	private HealthcareSiteDao healthcareSiteDao;

	public CreateParticipantController() {
		setCommandClass(Participant.class);
		Flow<Participant> flow = new Flow<Participant>("Create Subject");
		intializeFlows(flow);
	}

	protected void intializeFlows(Flow<Participant> flow) {
		flow.addTab(new Tab<Participant>("Details", "Details",
				"participant/participant") {
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty
						.getMap();

				Map<String, Object> refdata = new HashMap<String, Object>();

				refdata.put("administrativeGenderCode", configMap
						.get("administrativeGenderCode"));
				refdata
						.put("ethnicGroupCode", configMap
								.get("ethnicGroupCode"));
				refdata.put("raceCode", configMap.get("raceCode"));
				refdata.put("source", getHealthcareSites());
				refdata.put("searchTypeRefData", configMap
						.get("participantSearchType"));
				refdata.put("identifiersTypeRefData", configMap
						.get("participantIdentifiersType"));
				refdata.put("action", "createParticipant");

				return refdata;
			}
		});
		flow.addTab(new Tab<Participant>("Address & Contact Info",
				"Address & ContactInfo", "participant/participant_address") {
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty
						.getMap();

				Map<String, Object> refdata = new HashMap<String, Object>();
				refdata.put("contactMechanismType", configMap
						.get("contactMechanismType"));

				return refdata;
			}
		});
		flow.addTab(new Tab<Participant>("Review and Submit ",
				"Review and Submit ", "participant/participant_submit"));

		setFlow(flow);
	}

	@Override
	protected Map<String, Object> referenceData(
			HttpServletRequest httpServletRequest, int page) throws Exception {
		// Currently the static data is a hack, once DB design is approved for
		// an LOV this will be
		// replaced with LOVDao to get the static data from individual tables
		System.out.println("------------Create Reference Data-------------");
		Map<String, Object> refdata = new HashMap<String, Object>();
		Map<String, List<Lov>> configMap = configurationProperty.getMap();
		return refdata;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {

		// FIXME: small hack
		Participant participant = (Participant) super
				.formBackingObject(request);
		participant = createParticipantWithContacts(participant);
		return participant;
	}

	private Participant createParticipantWithContacts(Participant participant) {

		ContactMechanism contactMechanismEmail = new ContactMechanism();
		ContactMechanism contactMechanismPhone = new ContactMechanism();
		ContactMechanism contactMechanismFax = new ContactMechanism();
		contactMechanismEmail.setType("Email");
		contactMechanismPhone.setType("Phone");
		contactMechanismFax.setType("Fax");
		participant.addContactMechanism(contactMechanismEmail);
		participant.addContactMechanism(contactMechanismPhone);
		participant.addContactMechanism(contactMechanismFax);
		return participant;
	}

	@Override
	protected void initBinder(HttpServletRequest req,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM/dd/yyyy"), true));
		binder.registerCustomEditor(HealthcareSite.class,
				new CustomDaoEditor(healthcareSiteDao));
	}

	@Override
	protected void postProcessPage(HttpServletRequest request, Object Command,
			Errors errors, int page) {
	}

	@Override
	protected void onBind(HttpServletRequest request, Object command,
			BindException errors) throws Exception {
		// TODO Auto-generated method stub
		super.onBind(request, command, errors);
		new RowManager().handleRowDeletion(request, command);
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object oCommand, BindException errors)
			throws Exception {
		Participant command = (Participant) oCommand;

		Iterator<Identifier> iterator = command.getIdentifiers().iterator();

		while (iterator.hasNext()) {
			Identifier identifier = iterator.next();
			if (identifier.getType().trim().length() == 0
					|| identifier.getValue().trim().length() == 0) {
				iterator.remove();
			}
		}

		Iterator<ContactMechanism> cMIterator = command.getContactMechanisms()
				.iterator();
		StringUtils strUtil = new StringUtils();
		while (cMIterator.hasNext()) {
			ContactMechanism contactMechanism = cMIterator.next();
			if (strUtil.isBlank(contactMechanism.getValue()))
				cMIterator.remove();
		}

		participantDao.save(command);

		ModelAndView modelAndView = null;
		if(request.getParameter("async")!=null){
			response.getWriter().print(command.getFirstName()+" "+command.getLastName()+" (" + command.getIdentifiers().get(0).getType() +" - "+command.getIdentifiers().get(0).getValue() + ")"
					+"||"+command.getId());
			return null;
		}
		response.sendRedirect("confirmCreateParticipant?lastName="
				+ command.getLastName()+ "&firstName="+ command.getFirstName()+"&middleName="+command.getMiddleName()+"&primaryIdentifier="+command.getPrimaryIdentifier()+ "&type=confirm");
		return null;
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

	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	public ParticipantValidator getParticipantValidator() {
		return participantValidator;
	}

	public void setParticipantValidator(
			ParticipantValidator participantValidator) {
		this.participantValidator = participantValidator;
	}

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

}
