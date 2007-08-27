package edu.duke.cabig.c3pr.web.report;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.web.ajax.CreateReportFacade;
import edu.duke.cabig.c3pr.web.ajax.CreateStudyReportFacade;


public class CreateStudyReportController extends SimpleFormController {

	private static Log log = LogFactory.getLog(CreateStudyReportController.class);
	private StudySubjectDao studySubjectDao;
//	private ConfigurationProperty configurationProperty;
	private StudyReportCommand studyReportCommand;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
//	public ConfigurationProperty getConfigurationProperty() {
//		return configurationProperty;
//	}
//
//
//	public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
//		this.configurationProperty = configurationProperty;
//	}

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);	

//		binder.registerCustomEditor(Date.class, new CustomDateEditor(
//				new SimpleDateFormat("MM/dd/yyyy"), true));
		
		if(request.getMethod().equals(METHOD_GET))
		{
			CreateReportFacade studyFacade = new CreateReportFacade();
			Context context = null;
			context = new HttpServletRequestContext(request);
        
			TableModel model = new TableModelImpl(context);
			Object viewData = null;
			try {
				viewData = studyFacade.build(model, null);	          
			} catch (Exception e) {
				e.printStackTrace();
			}		
			request.setAttribute("assembler", viewData);
		} 		
	}
	
	
//	protected Map<String, Object> referenceData(HttpServletRequest request) {
//
//		Map<String, Object> configMap = configurationProperty.getMap();
//		Map<String, Object> refdata = new HashMap<String, Object>();
//		
//		refdata.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
//		refdata.put("ethnicGroupCode", configMap.get("ethnicGroupCode"));
//		refdata.put("raceCode", configMap.get("raceCode"));
//
//		return refdata;
//	}
	
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

		CreateStudyReportFacade studyReportFacade = new CreateStudyReportFacade();
		Context context = new HttpServletRequestContext(request);
		TableModel model = new TableModelImpl(context);
		String [] params = studyReportCommand.getParams();
		
		Study study = new Study();   	
		study.setShortTitleText(params[0].toString());
		
		SystemAssignedIdentifier id;
		id = new SystemAssignedIdentifier();
		id.setValue(params[1].toString());
        study.addIdentifier(id);
		
		Participant participant = new Participant();
		id = new SystemAssignedIdentifier();
        id.setValue(params[2].toString());
        participant.addIdentifier(id);       

		participant.setFirstName(params[3].toString());
		participant.setLastName(params[4].toString());     
        
        StudySite studySite = new StudySite();
        studySite.setStudy(study);
        
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySite);
        studySubject.setParticipant(participant);        
        
		List<StudySubject> registrationResults = studySubjectDao.advancedStudySearch(studySubject);
		Object viewData = null;
		try {
			viewData = studyReportFacade.build(model, registrationResults);
		} catch (Exception e) {
			e.printStackTrace();
		} 			
		request.setAttribute("assembler", viewData);		
		
		Map map = errors.getModel();
//		map.put("raceCode", getConfigurationProperty().getMap().get("raceCode"));  
    	ModelAndView modelAndView= new ModelAndView(getSuccessView(), map);
    	return modelAndView;
	}			


	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}


	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}


	public StudyReportCommand getStudyReportCommand() {
		return studyReportCommand;
	}

	public void setStudyReportCommand(StudyReportCommand studyReportCommand) {
		this.studyReportCommand = studyReportCommand;
	}

}
