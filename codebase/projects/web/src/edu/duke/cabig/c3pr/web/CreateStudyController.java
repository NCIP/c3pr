package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.springframework.web.servlet.view.RedirectView;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;

/**
 * @author Priyatam
 *
 */
public class CreateStudyController extends AbstractWizardFormController {
    private static Log log = LogFactory.getLog(CreateStudyController.class);
	private StudyService studyService;
	private StudyDao studyDao;
	
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(true));
        ControllerTools.registerDomainObjectEditor(binder, "study", studyDao);
    }
	
	/**
	 * Create a nested object graph that Create Study Design needs 
	 * @param request - HttpServletRequest
	 * @throws ServletException
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		Study study = new Study(); 
		Epoch epoch = new Epoch();
		epoch.addArm(new Arm());
		study.addEpoch(epoch);
			
		StudySite studySite = new StudySite();
		study.addStudySite(studySite);
		
		HealthcareSite healthCaresite = new HealthcareSite();		
		healthCaresite.setAddress(new Address());		
		studySite.setSite(healthCaresite);
		
		return study;		          
	}
	
	/**
	* Method for custom validation logic for individual pages
	* @param command - form object with the current wizard state
	* @param errors - validation errors holder
	* @param page - number of page to validate
	*/
//	protected void validatePage(Object command, Errors errors, int page) {
//		StudyDesignCommand StudyDesignCommand = (StudyDesignCommand) command;
//		StudyDesignCommand StudyDesignValidator = (StudyDesignCommand) getValidator();
//		switch (page) {
//		case 0:
//		//	StudyDesignValidator.validateExclusiveOrShared(addJob, errors);
//		break;
//		}
//	}
	
//	protected int getTargetPage(HttpServletRequest request, Object command, Errors errors,
//			int currentPage) {
//		if (request.getParameter(PARAM_TARGET) != null) {
//			return new Integer(request.getParameter(PARAM_TARGET)).intValue();
//		}
//		if (currentPage == 0) {
//			return 1;
//		}
//		else {
//			return 2;
//		}
//	}

	protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest, int page) 
  		throws Exception {
  	// Currently the static data is a hack for an LOV this will be replaced with 
  	// LOVDao to get the static data from individual tables
		Map<String, Object> refdata = new HashMap<String, Object>();
	  	if (page == 0) {
	  		refdata.put("diseaseCode", getDiseaseCodeList());
	  		refdata.put("monitorCode", getMonitorCodeList());
	  		refdata.put("phaseCode", getDiseaseCodeList());
	  		refdata.put("sponsorCode", getSponsorCodeList() );
	  		refdata.put("status", getStatusList());
	  		refdata.put("type", getTypeList() );
	  		refdata.put("multiInstitutionIndicator", getMultinstitutionList());
	  		refdata.put("randomizedIndicator", getRandomizedList());
	  		refdata.put("blindedIndicator", getBlindedIndicator());
	  		refdata.put("nciIdentifier", getNciIdentifier());
	  		return refdata;
	  	}
	  	
	  	return refdata;
	}
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish
	 * (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, 
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, 
			Object command, BindException errors) throws Exception {
		Study study = (Study) command;
		studyService.save(study);   
    	ModelAndView modelAndView= new ModelAndView(new RedirectView("searchstudy.do"));
    	//modelAndView.addAllObjects(errors.getModel());
    	return modelAndView;
	}
 
	private List<LOV> getDiseaseCodeList(){
		List<LOV> col = new ArrayList<LOV>();
		LOV lov1 = new LOV("10028534", "Myelodysplastic syndrome NOS");
		LOV lov2 = new LOV("10038272", "Refractory anemia with ringed sideroblasts");
		LOV lov3 = new LOV("10024536", "Lip and/or oral cavity cancer stage 0");
		LOV lov4 = new LOV("10031098", "Oropharyngeal cancer recurrent");
		
		col.add(lov1);
    	col.add(lov2);
    	col.add(lov3);
    	col.add(lov4);
    	
    	return col;
	}
	
	private List<LOV> getMonitorCodeList(){
		List<LOV> col = new ArrayList<LOV>();
		LOV lov1 = new LOV("10028534", "Monitor Code 1");
		LOV lov2 = new LOV("10038272", "Monitor Code 2");
		LOV lov3 = new LOV("10024536", "Monitor Code 3");
		LOV lov4 = new LOV("10031098", "Monitor Code List 4");
		
		col.add(lov1);
    	col.add(lov2);
    	col.add(lov3);
    	col.add(lov4);
    	
    	return col;
	}
	
	private List<LOV> getPhaseCodeList(){
		List<LOV> col = new ArrayList<LOV>();
		LOV lov1 = new LOV("100", "Phase I");
		LOV lov2 = new LOV("101", "Phase I/II");
		LOV lov3 = new LOV("102", "Phase III");
		LOV lov4 = new LOV("103", "NOT APPLICABLE");
		
		col.add(lov1);
    	col.add(lov2);
    	col.add(lov3);
    	col.add(lov4);
    	
    	return col;
	}
	
	
	private List<LOV> getSponsorCodeList(){
		List<LOV> col = new ArrayList<LOV>();
		LOV lov1 = new LOV("200", "Sponsor 1 - Duke");
		LOV lov2 = new LOV("201", "Sponsor 2 - Nci");
		LOV lov3 = new LOV("202", "Sponsor 3 - FDA");
		
		col.add(lov1);
    	col.add(lov2);
    	col.add(lov3);
    		
    	return col;
	}
	
	private List<LOV> getStatusList(){
		List<LOV> col = new ArrayList<LOV>();
		LOV lov1 = new LOV("C", "Closed");
		LOV lov2 = new LOV("O", "Open");
		LOV lov3 = new LOV("S", "Suspended");
		LOV lov4 = new LOV("T", "Terminated");
		LOV lov5 = new LOV("I", "IRB Approved");
		
		col.add(lov1);
    	col.add(lov2);
    	col.add(lov3);
    	col.add(lov4);
    	col.add(lov5);
    	
    	return col;
	}

	private List<LOV> getTypeList(){
		List<LOV> col = new ArrayList<LOV>();
		LOV lov1 = new LOV("C", "Diagnostic");
		LOV lov2 = new LOV("GN", "Genetic Non-therapeutic");
		LOV lov3 = new LOV("GT", "Genetic Non-therapeutic");
		LOV lov4 = new LOV("N", "Non-therapeutic");
		LOV lov5 = new LOV("P", "Primary Treatment");
		LOV lov6 = new LOV("S", "Supportive");
		LOV lov7 = new LOV("P", "Preventive'");
		
		col.add(lov1);
    	col.add(lov2);
    	col.add(lov3);
    	col.add(lov4);
    	col.add(lov5);
    	col.add(lov6);
    	col.add(lov7);
    	
    	return col;
	}
	
	private List<StringBean> getMultinstitutionList(){
		List<StringBean> col = new ArrayList<StringBean>();		
    	col.add(new StringBean("YES"));
    	col.add(new StringBean("NO"));
    	return col;
	}
	private List<StringBean> getRandomizedList(){
		List<StringBean> col = new ArrayList<StringBean>();		
    	col.add(new StringBean("YES"));
    	col.add(new StringBean("NO"));
    	return col;
	}
	
	private List<StringBean> getBlindedIndicator(){
		List<StringBean> col = new ArrayList<StringBean>();		
    	col.add(new StringBean("YES"));
    	col.add(new StringBean("NO"));
    	return col;
	}
	
	private List<StringBean> getNciIdentifier(){
		List<StringBean> col = new ArrayList<StringBean>();		
    	col.add(new StringBean("YES"));
    	col.add(new StringBean("NO"));
    	return col;
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
	
	public class StringBean {
	
		String str;
		
		StringBean(String str)
		{
			this.str=str;
		}
		
		public void setStr(String str){
			this.str=str;
		}
		
		public String getStr(){
			return str;
		}
		
	}

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public StudyService getStudyService() {
		return studyService;
	}

	public void setStudyService(StudyService studyService) {
		this.studyService = studyService;
	}	
	
}