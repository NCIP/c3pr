package edu.duke.cabig.c3pr.web.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.StudyParticipantAssignmentDao;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.web.ajax.CreateReportFacade;


/*
 * @author Vinay Gangoli 
 * This controller produces a extreme components table based search results which can be 
 * downloaded as an excel report.
 */
public class CreateReportController extends SimpleFormController {

//	private static Log log = LogFactory.getLog(CreateReportController.class);
	private StudyParticipantAssignmentDao registrationDao;
	private ConfigurationProperty configurationProperty;

	
	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}


	public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);	

		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM/dd/yyyy"), true));

		Enumeration en=request.getParameterNames();
		
		if(request.getMethod().equals(METHOD_GET))
		{
			CreateReportFacade studyFacade = new CreateReportFacade();
			Context context = null;
			context = new HttpServletRequestContext(request);
        
			TableModel model = new TableModelImpl(context);
			Object viewData = null;
			try {
				viewData = studyFacade.build(model, new ArrayList());	          
			} catch (Exception e) {
				e.printStackTrace();
			} 			
			
			request.setAttribute("assembler", viewData);
		} 		
	}
	
	
	protected Map<String, Object> referenceData(HttpServletRequest request) {

		Map<String, Object> configMap = configurationProperty.getMap();
		Map<String, Object> refdata = new HashMap<String, Object>();
		
		refdata.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
		refdata.put("ethnicGroupCode", configMap.get("ethnicGroupCode"));
		refdata.put("raceCode", configMap.get("raceCode"));

		return refdata;
	}
	
//	@Override
//	protected Object formBackingObject(HttpServletRequest request) throws ServletException {	
//		SearchStudyCommand sCommand = new SearchStudyCommand();
//		sCommand.addSearchCriterion(new SearchCommand());
//		return sCommand;
//	}	

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object oCommand,
			BindException errors) throws Exception
	{		
		CreateReportFacade reportFacade = new CreateReportFacade();
		Context context = null;
		context = new HttpServletRequestContext(request);
    
		TableModel model = new TableModelImpl(context);
		Object viewData = null;
		try {
			viewData = reportFacade.build(model, new ArrayList());	          
		} catch (Exception e) {
			e.printStackTrace();
		} 			
		request.setAttribute("assembler", viewData);
	
//		int index = Integer.parseInt(request.getParameter("_selected"));
//		String action = request.getParameter("_action");
		
//		if("addCriteria".equals(action))
//		{
//			((SearchStudyCommand)oCommand).getSearchCriteria().add(new SearchCommand());
//		}
//		else if ("removeCriteria".equals(action))
//		{
//			((SearchStudyCommand)oCommand).getSearchCriteria().remove(index);
//		}		
		
		Map map = errors.getModel();
//		map.put("studySearchType",getConfigurationProperty().getMap().get("studySearchType"));  
    	ModelAndView modelAndView= new ModelAndView(getSuccessView(), map);
     	
    	// needed for saving session state
    	request.getSession().setAttribute(getFormSessionAttributeName(), oCommand);
    	
    	return modelAndView;
	}			

	public StudyParticipantAssignmentDao getRegistrationDao() {
		return registrationDao;
	}

	public void setRegistrationDao(StudyParticipantAssignmentDao registrationDao) {
		this.registrationDao = registrationDao;
	}
   	
	
}
