/**
 * 
 */
package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.ProtocolDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.ProtocolService;

/**
 * @author Priyatam
 *
 */
public class CreateProtocolController extends SimpleFormController {
    private static Log log = LogFactory.getLog(CreateProtocolController.class);

	private ProtocolService protocolService;
	private ProtocolDao protocolDao;
	
    public CreateProtocolController() {
        setCommandClass(CreateProtocolController.class);
    }

//    protected void initBinder(HttpServletRequest request,
//            ServletRequestDataBinder binder) throws Exception {
//    		binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(true));
//            ControllerTools.registerDomainObjectEditor(binder, "protocolType", protocolDao);
//            binder.registerCustomEditor(edu.duke.cabig.c3pr.domain.ProtocolPhase.class, "protocolPhase", new CustomProtocolPhaseCodeEditor());            
//            super.initBinder(request, binder);
//        }
//    
    protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest) throws Exception {
    	// Currently the referenceData is a hack, once DB design is approved for an LOV this will be
    	// replaced with LOVDao to get the static data 
    	Map<String, Object> refdata = new HashMap<String, Object>();
    	
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

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object oCommand, BindException errors) throws Exception {
    	Study study = (Study) oCommand;
    	protocolService.saveStudy(study);   
    	ModelAndView modelAndView= new ModelAndView(getSuccessView());
    	modelAndView.addAllObjects(errors.getModel());
    	return modelAndView;
    }

  
	/**
	 * @return the protocolService
	 */
	public ProtocolService getProtocolService() {
		return protocolService;
	}

	/**
	 * @param protocolService the protocolService to set
	 */
	public void setProtocolService(ProtocolService protocolService) {
		this.protocolService = protocolService;
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
    	col.add(new StringBean("Y"));
    	col.add(new StringBean("N"));
    	return col;
	}
	private List<StringBean> getRandomizedList(){
		List<StringBean> col = new ArrayList<StringBean>();		
    	col.add(new StringBean("Y"));
    	col.add(new StringBean("N"));
    	return col;
	}
	
	private List<StringBean> getBlindedIndicator(){
		List<StringBean> col = new ArrayList<StringBean>();		
    	col.add(new StringBean("Y"));
    	col.add(new StringBean("N"));
    	return col;
	}
	
	private List<StringBean> getNciIdentifier(){
		List<StringBean> col = new ArrayList<StringBean>();		
    	col.add(new StringBean("Y"));
    	col.add(new StringBean("N"));
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

	public ProtocolDao getProtocolDao() {
		return protocolDao;
	}

	public void setProtocolDao(ProtocolDao protocolDao) {
		this.protocolDao = protocolDao;
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