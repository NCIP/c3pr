/**
 * 
 */
package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.ProtocolDao;
import edu.duke.cabig.c3pr.domain.Protocol;
import edu.duke.cabig.c3pr.domain.ProtocolType;
import edu.duke.cabig.c3pr.service.ProtocolService;
import edu.duke.cabig.c3pr.utils.propertyeditors.CustomProtocolPhaseCodeEditor;

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

    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) throws Exception {
    		binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(true));
           // ControllerTools.registerDomainObjectEditor(binder, "protocolType", protocolDao);
            binder.registerCustomEditor(edu.duke.cabig.c3pr.domain.ProtocolPhase.class, "protocolPhase", new CustomProtocolPhaseCodeEditor());
            
            super.initBinder(request, binder);
        }
    
    protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest) throws Exception {
    	//String userName = ApplicationSecurityManager.getUser(httpServletRequest);
    	
    	Map<String, Object> refdata = new HashMap<String, Object>();
    	
    	refdata.put("protocoltypecode", getProtocolTypeCode());
        refdata.put("multiInstitutional", getMultiInstitutionalList());
        return refdata;
    }

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object oCommand, BindException errors) throws Exception {
    	Protocol protocol = (Protocol) oCommand;
    	protocolService.saveProtocol(protocol);      
    	 	
    	return new ModelAndView(getSuccessView());
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
	
	private List <ProtocolType> getProtocolTypeCode(){
		List <ProtocolType> protocolTypeCode = new ArrayList<ProtocolType>();
		ProtocolType pt = new ProtocolType();
		pt.setId(new Integer(1));
		pt.setCode("D");
		pt.setDescription("Diagnostic");
		ProtocolType pt2 = new ProtocolType();
		pt2.setId(new Integer(2));
		pt2.setCode("GN");
		pt2.setDescription("Genetic Non Therapeutic");
		
		protocolTypeCode.add(pt);
		protocolTypeCode.add(pt2);
		
		String str = new String();
		
    	return protocolTypeCode;
	}
	
	private List<StringBean> getMultiInstitutionalList(){
		List<StringBean> multiInstitutional = new ArrayList<StringBean>();
		StringBean str1 = new StringBean();
		str1.setStr("YES");
		StringBean str2 = new StringBean();
		str1.setStr("NO");		
    	multiInstitutional.add(str1);
    	multiInstitutional.add(str2);
    	return multiInstitutional;
	}
	
	public class StringBean {
		
		private String str;

		public String getStr() {
			return str;
		}

		public void setStr(String str) {
			this.str = str;
		}
	}

	public ProtocolDao getProtocolDao() {
		return protocolDao;
	}

	public void setProtocolDao(ProtocolDao protocolDao) {
		this.protocolDao = protocolDao;
	}
	
}