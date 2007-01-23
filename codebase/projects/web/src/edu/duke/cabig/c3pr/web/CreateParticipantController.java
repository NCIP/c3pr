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

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;


/**
 * @author Kulasekaran
 * 
 */
public class CreateParticipantController extends AbstractWizardFormController {

	private ParticipantDao participantDao;	
	
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
    	    refdata.put("source", getSourceList());
    		refdata.put("searchType", getSearchType());
    		if(httpServletRequest.getParameter("studySiteId")!=null){
    			if(!httpServletRequest.getParameter("studySiteId").equals("")){
    				refdata.put("studySiteId", httpServletRequest.getParameter("studySiteId"));
    			}
    		}
    	}
    	
        return refdata;
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		
		//FIXME: small hack
		if(request.getParameter("url")!=null){
			if(request.getParameter("studySiteId")!=null){
				setPages(new String[]{"reg_create_patient_study","reg_create_patient_address_study","reg_create_patient_submit_study"});
			}else{
				setPages(new String[]{"reg_create_patient","reg_create_patient_address","reg_create_patient_submit"});
			}
			request.getSession().setAttribute("url", request.getParameter("url"));
			request.getSession().setAttribute("studySiteId", request.getParameter("studySiteId"));
		}else{
			setPages(new String[]{"participant","participant_address","participant_submit"});
			request.getSession().removeAttribute("url");	
			request.getSession().removeAttribute("studySiteId");
		}
		
		Participant participant = (Participant) super.formBackingObject(request);
		for (int i = 0; i < 5; i++) {
			Identifier temp=new Identifier();
			temp.setPrimaryIndicator(false);
			participant.addIdentifier(temp);
		}
		participant.setAddress(new Address());
		return participant;
	}
	
	@Override
	protected void initBinder(HttpServletRequest req,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM-dd-yyyy"), true));		
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object oCommand, BindException errors)
			throws Exception {
		Participant command = (Participant) oCommand;
		
		Iterator<Identifier> iterator = command.getIdentifiers().iterator();

		while(iterator.hasNext())
		{
			if(iterator.next().getType().trim() == "" || iterator.next().getType().trim() == "")
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
		response.sendRedirect("/c3pr/SearchAndRegister.do");
		return null;
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
	
	 private List<LOV> getSearchType(){
			List<LOV> col = new ArrayList<LOV>();
			LOV lov1 = new LOV("N", "LastName");
			LOV lov2 = new LOV("MRN", "MRN");
			
			col.add(lov1);
			col.add(lov2);	    	    	
	    	return col;
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

	private List<LOV> getSourceList() 
	{
		List<LOV> col = new ArrayList<LOV>();
		
		col.add(new LOV("Duke University Comprehensive Cancer Center", "Duke University Comprehensive Cancer Center"));
		col.add(new LOV("Warren Grant Magnuson Clinical Center", "Warren Grant Magnuson Clinical Center"));
		
		return col;
	}
	
	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}	
}
