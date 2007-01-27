package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.springframework.web.servlet.view.RedirectView;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;

/**
 * @author Ramakrishna
 * 
 */
public class EditParticipantController extends AbstractWizardFormController {

	private static Log log = LogFactory.getLog(EditParticipantController.class);

	private ParticipantDao participantDao;

	private HealthcareSiteDao healthcareSiteDao;

	public EditParticipantController() {
		setCommandClass(Participant.class);

	}

	@Override
	protected Map<String, Object> referenceData(
			HttpServletRequest httpServletRequest, int page) throws Exception {
		// Currently the static data is a hack, once DB design is approved for
		// an LOV this will be
		// replaced with LOVDao to get the static data from individual tables
		Map<String, Object> refdata = new HashMap<String, Object>();
		refdata.put("source", getSourceList());
		refdata.put("administrativeGenderCode",
				getAdministrativeGenderCodeList());
		refdata.put("ethnicGroupCode", getEthnicGroupCodeList());
		refdata.put("raceCode", getRaceCodeList());
		refdata.put("healthcareSite", healthcareSiteDao.getAll());
		refdata.put("searchType", getSearchType());
		refdata.put("identifiersTypeRefData", getIdentifiersList());

		refdata.put("updateMessageRefData", getUpdateMessageList().get(8));
		if (("Update Subject").equals((httpServletRequest
				.getParameter("_target0")))) {
			refdata.put("updateMessageRefData", getUpdateMessageList().get(0));
		} else if (("Update Identifiers").equals(httpServletRequest
				.getParameter("_target1"))) {
			refdata.put("updateMessageRefData", getUpdateMessageList().get(2));
		} else if (("Update Addresses").equals(httpServletRequest
				.getParameter("_target2"))) {
			refdata.put("updateMessageRefData", getUpdateMessageList().get(4));
		} else if (("Update Contact Information").equals(httpServletRequest
				.getParameter("_target3"))) {
			refdata.put("updateMessageRefData", getUpdateMessageList().get(6));
		}

		return refdata;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		Participant participant = null;

		if (request.getParameter("participantId") != null) {
			System.out.println(" Request URl  is:"
					+ request.getRequestURL().toString());
			participant = participantDao.getById(Integer.parseInt(request
					.getParameter("participantId")));
			System.out.println(" Participant's ID is:" + participant.getId());
		}
		return participant;
	}

	@Override
	protected void initBinder(HttpServletRequest req,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM/dd/yyyy"), true));
		binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(
				healthcareSiteDao));
	}

	@Override
	protected void postProcessPage(HttpServletRequest request, Object oCommand,
			Errors errors, int page) throws Exception {
		Participant participant = (Participant) oCommand;
		if (page == 1) {
			handleIdentifierAction(participant,
					request.getParameter("_action"), request
							.getParameter("_selected"));
		}
		try {
			participantDao.save(participant);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object oCommand, BindException errors)
			throws Exception {

		ModelAndView modelAndView = new ModelAndView(new RedirectView(
				"searchparticipant.do"));
		return modelAndView;
	}

	private void handleIdentifierAction(Participant participant, String action,
			String selected) {
		if ("addIdentifier".equals(action)) {
			log.debug("Requested Add Identifier");
			Identifier id = new Identifier();
			id.setSource("Duke");
			id.setValue("<enter value>");
			participant.addIdentifier(id);
		} else if ("removeIdentifier".equals(action)) {
			log.debug("Requested Remove Identifier");
			participant.getIdentifiers().remove(Integer.parseInt(selected));
		}
	}

	public class LOV {

		private String code;

		private String desc;

		LOV(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	private List<LOV> getSourceList() {
		List<LOV> col = new ArrayList<LOV>();

		col.add(new LOV("Duke", "Duke"));
		col.add(new LOV("Northwestern", "Northwestern"));

		return col;
	}

	private List<LOV> getIdentifiersList() {
		List<LOV> col = new ArrayList<LOV>();

		col.add(new LOV("Protocol Authority", "Protocol Authority Identifier"));
		col.add(new LOV("Co-ordinating Center", "Co-ordinating Center"));
		col.add(new LOV("Site", "Site"));
		col.add(new LOV("Site IRB", "Site IRB"));

		return col;
	}

	private List<LOV> getSearchType() {
		List<LOV> col = new ArrayList<LOV>();
		LOV lov1 = new LOV("N", "Last Name");
		LOV lov2 = new LOV("Identifier", "Identifier");

		col.add(lov1);
		col.add(lov2);
		return col;
	}

	private List<LOV> getRaceCodeList() {
		List<LOV> col = new ArrayList<LOV>();

		col.add(new LOV("-", "--"));
		col.add(new LOV("Asian", "Asian"));
		col.add(new LOV("White", "White"));
		col.add(new LOV("Black or African American",
				"Black or African American"));
		col.add(new LOV("American Indian or Alaska Native",
				"American Indian or Alaska Native"));
		col.add(new LOV("Native Hawaiian or Pacific Islander",
				"Native Hawaiian or other Pacific Islander"));
		col.add(new LOV("Not Reported", "Not Reported"));
		col.add(new LOV("Unknown", "Unknown"));

		return col;
	}

	private List<LOV> getEthnicGroupCodeList() {
		List<LOV> col = new ArrayList<LOV>();

		col.add(new LOV("-", "--"));
		col.add(new LOV("Hispanic or Latino", "Hispanic or Latino"));
		col.add(new LOV("Non Hispanic or Latino", "Non Hispanic or Latino"));
		col.add(new LOV("Not Reported", "Not Reported"));
		col.add(new LOV("Unknown", "Unknown"));

		return col;
	}

	private List<LOV> getAdministrativeGenderCodeList() {
		List<LOV> col = new ArrayList<LOV>();

		col.add(new LOV("-", "--"));
		col.add(new LOV("Male", "Male"));
		col.add(new LOV("Female", "Female"));
		col.add(new LOV("Not Reported", "Not Reported"));
		col.add(new LOV("Unknown", "Unknown"));

		return col;
	}

	private List<LOV> getUpdateMessageList() {
		List<LOV> col = new ArrayList<LOV>();

		col.add(new LOV("UpdatedDetailsSuccessMessage",
				"The Subject's Details are updated successfully"));
		col.add(new LOV("UpdatedDetailsFailureMessage",
						"Sorry, failed to update Subject's Details, Please Try Again!"));
		col.add(new LOV("UpdatedIdentifiersSuccessMessage",
				"The Subject's Identifiers are updated successfully"));
		col.add(new LOV("UpdatedIdentifiersFailureMessage",
						"Sorry, failed to update Subject's Identifiers, Please Try Again!"));
		col.add(new LOV("UpdatedAddressSuccessMessage",
				"The Subject's Address is updated successfully"));
		col.add(new LOV("UpdatedAddressFailureMessage",
						"Sorry, failed to update subject's Address, Please Try Again!"));
		col.add(new LOV("UpdatedContactInfoSuccessMessage",
				"The Subject's Contact Information is updated successfully"));
		col.add(new LOV("UpdatedContactInfoFailureMessage",
						"Sorry, failed to update Subject's Contact Information, Please Try Again!"));
		col.add(new LOV("", ""));

		return col;
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
}
