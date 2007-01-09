package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ParticipantIdentifier;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;


/**
 * @author Kulasekaran
 * 
 */
public class CreateParticipantController extends AbstractWizardFormController {

	private ParticipantDao participantDao;
	private HealthcareSiteDao healthcareSiteDao;
	
	public CreateParticipantController() {
		setCommandClass(Participant.class);
	}

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest, int page) throws Exception {
    	// Currently the static data is a hack, once DB design is approved for an LOV this will be
    	// replaced with LOVDao to get the static data from individual tables
    	Map<String, Object> refdata = new HashMap<String, Object>();
    	
    	if(page==0)
    	{
    		refdata.put("administrativeGenderCode", getAdministrativeGenderCodeList());
    		refdata.put("ethnicGroupCode", getEthnicGroupCodeList());
    		refdata.put("raceCode", getRaceCodeList());
    		refdata.put("healthcareSite", healthcareSiteDao.getAll());
    	}
    	
        return refdata;
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		
		//FIXME: small hack
		request.getSession().setAttribute("url", request.getParameter("url"));	
		request.getSession().setAttribute("studySiteId", request.getParameter("studySiteId"));
		
		Participant participant = (Participant) super.formBackingObject(request);
		for (int i = 0; i < 5; i++) {
			participant.addParticipantIdentifier(new ParticipantIdentifier());
		}
		participant.setAddress(new Address());
		return participant;
	}
	
	@Override
	protected void initBinder(HttpServletRequest req,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM-dd-yyyy"), true));
		// binder.registerCustomEditor(ParticipantIdentifier.class, new ParticipantIdentifierEditor());
		binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(healthcareSiteDao));
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object oCommand, BindException errors)
			throws Exception {
		Participant command = (Participant) oCommand;
		
		Iterator<ParticipantIdentifier> iterator = command.getParticipantIdentifiers().iterator();

		while(iterator.hasNext())
		{
			if(iterator.next().getMedicalRecordNumber().trim() == "")
			{
				iterator.remove();
			}
		}
			
		participantDao.save(command);
		
		ModelAndView modelAndView = null;
		//FIXME: small hack
		String url = null;
		if((url = (String) request.getSession().getAttribute("url")) != null)
		{
			url+="?participantId=" + Integer.toString(command.getId());
			if(request.getSession().getAttribute("studySiteId") != null)
				url+="&studySiteId="+(String)request.getSession().getAttribute("studySiteId");
			response.sendRedirect(url);
			return null;
		}
		
		modelAndView = new ModelAndView("success");		 
		modelAndView.addAllObjects(errors.getModel());
		return modelAndView;
	}	
	
	public class LOV {
		
		private String code;
		private String desc;
		
		LOV(String code, String desc)
		{ 
			this.code=code; 
			this.desc=desc; 
		}
		
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
		
		public String getDesc(){
			return desc;
		}
			
		public void setDesc(String desc){
			this.desc=desc;
		}
	}
	
	private List<LOV> getRaceCodeList() {
		List<LOV> col = new ArrayList<LOV>();
		
		col.add(new LOV("-", "--"));
		col.add(new LOV("Asian", "Asian"));
		col.add(new LOV("White", "White"));
		col.add(new LOV("Black or African American", "Black or African American"));
		col.add(new LOV("American Indian or Alaska Native", "American Indian or Alaska Native"));
		col.add(new LOV("Native Hawaiian or Pacific Islander", "Native Hawaiian or other Pacific Islander"));
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
	
	private List<LOV> getAdministrativeGenderCodeList() 
	{
		List<LOV> col = new ArrayList<LOV>();
		
		col.add(new LOV("-", "--"));
		col.add(new LOV("Male", "Male"));
		col.add(new LOV("Female", "Female"));
		col.add(new LOV("Not Reported", "Not Reported"));
		col.add(new LOV("Unknown", "Unknown"));
		
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
