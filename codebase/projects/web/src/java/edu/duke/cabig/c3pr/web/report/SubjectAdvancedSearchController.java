package edu.duke.cabig.c3pr.web.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;

/*
 * @author Himanshu Gupta 
 * 
 */
public class SubjectAdvancedSearchController extends SimpleFormController {

    private static Log log = LogFactory.getLog(SubjectAdvancedSearchController.class);
    private ConfigurationProperty configurationProperty;
    private SubjectAdvancedSearchCommand subjectAdvancedSearchCommand;
    private ParticipantDao participantDao;

	public ParticipantDao getParticipantDao() {
		return participantDao;
	}
	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
	public SubjectAdvancedSearchCommand getSubjectAdvancedSearchCommand() {
		return subjectAdvancedSearchCommand;
	}
	public void setSubjectAdvancedSearchCommand(
			SubjectAdvancedSearchCommand subjectAdvancedSearchCommand) {
		this.subjectAdvancedSearchCommand = subjectAdvancedSearchCommand;
	}

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}
	public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}
	
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, List<Lov>> configMap = configurationProperty.getMap();
        Map<String, Object> refdata = new HashMap<String, Object>();

        refdata.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
        refdata.put("ethnicGroupCodes", configMap.get("ethnicGroupCode"));
        refdata.put("raceCodes", configMap.get("raceCode"));

        return refdata;
	}
	
	@Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
                    throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
    }
	
	
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			map.put("subjectList", participantDao.getAll());
		}catch (DataAccessException e) {
		}
		ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
        return modelAndView ;
	}
	
}