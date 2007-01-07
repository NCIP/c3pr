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

import edu.duke.cabig.c3pr.dao.CaDSRDataDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Address;
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
	private HealthcareSiteDao healthcareSiteDao;
	
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(true));
        ControllerTools.registerDomainObjectEditor(binder, healthcareSiteDao);
    }
	
	/**
	 * Create a nested object graph that Create Study Design needs 
	 * @param request - HttpServletRequest
	 * @throws ServletException
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {	
		return createDefaultStudyWithDesign();		         
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

	protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest, int page) 
  		throws Exception {
		CaDSRDataDao caDsr = new CaDSRDataDao();
  	// Currently the static data is a hack for an LOV this will be replaced with 
  	// LOVDao to get the static data from individual tables
		Map<String, Object> refdata = new HashMap<String, Object>();
	  	if (page == 0) {
	  		refdata.put("diseaseCode", caDsr.getCADsrData("diseaseCode"));
	  		refdata.put("monitorCode", caDsr.getCADsrData("monitorCode"));
	  		refdata.put("phaseCode", caDsr.getCADsrData("monitorCode"));
	  		refdata.put("sponsorCode", caDsr.getCADsrData("sponsorCode"));
	  		refdata.put("status", caDsr.getCADsrData("status"));
	  		refdata.put("type", caDsr.getCADsrData("type"));
	  		refdata.put("multiInstitutionIndicator", getBooleanList());
	  		refdata.put("randomizedIndicator", getBooleanList());
	  		refdata.put("blindedIndicator", getBooleanList());
	  		refdata.put("nciIdentifier", getBooleanList());
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
	
	/**
	 * Need to create a default study with 3 epochs and associated arms
	 * this is shown to the User for the first time
	 * @return Study the default study
	 */
	private Study createDefaultStudyWithDesign()
	{
		Study study = new Study(); 
	
		study.addEpoch(Epoch.create("Screening"));
		study.addEpoch(Epoch.create("Treatment", "Arm A", "Arm B", "Arm C"));
		study.addEpoch(Epoch.create("Follow up"));
          
		StudySite studySite = new StudySite();
		study.addStudySite(studySite);
		
		HealthcareSite healthCaresite = new HealthcareSite();		
		healthCaresite.setAddress(new Address());		
		studySite.setSite(healthCaresite);
		
		return study;
	}	

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}
	
	
	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public StudyService getStudyService() {
		return studyService;
	}

	public void setStudyService(StudyService studyService) {
		this.studyService = studyService;
	}	
	

	private List<StringBean> getBooleanList(){
		List<StringBean> col = new ArrayList<StringBean>();		
    	col.add(new StringBean("YES"));
    	col.add(new StringBean("NO"));
    	return col;
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
}