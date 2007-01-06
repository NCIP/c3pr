package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ParticipantIdentifier;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;


/**
 * @author Kulasekaran
 * 
 */
public class CreateParticipantController extends SimpleFormController {

	private ParticipantDao participantDao;
	private HealthcareSiteDao healthcareSiteDao;
	
	public CreateParticipantController() {
		setCommandClass(Participant.class);
	}

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest) throws Exception {
    	// Currently the static data is a hack, once DB design is approved for an LOV this will be
    	// replaced with LOVDao to get the static data from individual tables
    	Map<String, Object> refdata = new HashMap<String, Object>();
    	
    	refdata.put("administritativeGenderCode", getAdministritativeGenderCodeList());
        refdata.put("ethnicGroupCode", getEthnicGroupCodeList());
        refdata.put("raceCode", getRaceCodeList());
        refdata.put("healthcareSite", healthcareSiteDao.getAll());
        
        return refdata;
    }

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {		
		Participant participant = (Participant) super.formBackingObject(request);
		for (int i = 0; i < 5; i++) {
			participant.addParticipantIdentifier(new ParticipantIdentifier());
		}
		
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
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object oCommand, BindException errors)
			throws Exception {
		Participant command = (Participant) oCommand;
	
				
		participantDao.save(command);

		ModelAndView modelAndView = new ModelAndView(getSuccessView());
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
		col.add(new LOV("A", "Asian"));
		col.add(new LOV("W", "White"));
		col.add(new LOV("B", "Black or African American"));
		col.add(new LOV("Al", "American Indian or Alaska Native"));
		col.add(new LOV("H", "Native Hawaiian or other Pacific Islander"));
		col.add(new LOV("N", "Not Reported"));
		col.add(new LOV("U", "Unknown"));
		
		return col;
	}

	private List<LOV> getEthnicGroupCodeList() {
		List<LOV> col = new ArrayList<LOV>();
		
		col.add(new LOV("-", "--"));
		col.add(new LOV("H", "Hispanic or Latino"));
		col.add(new LOV("NH", "Non Hispanic or Latino"));
		col.add(new LOV("N", "Not Reported"));
		col.add(new LOV("U", "Unknown"));
		
		return col;
	}
	
	private List<LOV> getAdministritativeGenderCodeList() 
	{
		List<LOV> col = new ArrayList<LOV>();
		
		col.add(new LOV("-", "--"));
		col.add(new LOV("M", "Male"));
		col.add(new LOV("F", "Female"));
		col.add(new LOV("N", "Not Reported"));
		col.add(new LOV("U", "Unknown"));
		
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
