package edu.duke.cabig.c3pr.web.registration;

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

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.SearchRegistrationCommand;

/**
 * 
 * @author Ramakrishna
 */
public class SearchRegistrationController extends SimpleFormController {

    private static Log log = LogFactory.getLog(SearchRegistrationController.class);

    private StudySubjectDao studySubjectDao;

    private ParticipantDao participantDao;

    private StudyDao studyDao;

    private StudySiteDao studySiteDao;

    private ConfigurationProperty configurationProperty;

    public SearchRegistrationController() {
        setCommandClass(SearchRegistrationCommand.class);
        this.setFormView("registration_search");
        this.setSuccessView("registration_search_results");
    }

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
                    Object oCommand, BindException errors) throws Exception {

        SearchRegistrationCommand searchRegistrationCommand = (SearchRegistrationCommand) oCommand;
        StudySubject registration = new StudySubject(true);
        String text = searchRegistrationCommand.getSearchText();
        if(StringUtils.equals(text, "(Begin typing here)")){
        	text = "" ;
        }
        String type = searchRegistrationCommand.getSearchType();
        List<StudySubject> registrations = new ArrayList<StudySubject>();
        log.debug(" Search string is :" + text);
        Integer id = null;
    	if (request.getParameter("selected-id") != null
                && !(request.getParameter("selected-id").equals(""))) {
    			id = Integer.parseInt(request.getParameter("selected-id"));
    	}
        
    	if (request.getParameter("select").equals("Subject")) {
            Participant participant = new Participant();
            String subjectOption = request.getParameter("subjectOption") ;
            if (id == null) {
            	registrations = studySubjectDao.getAll();
            }else{
            	 if (StringUtils.equals(subjectOption,"N") || StringUtils.equals(subjectOption,"F")) {
                 	registrations = studySubjectDao.searchByParticipantId(id);
                 }
                 else if(StringUtils.equals(subjectOption, "Identifier")){
                 	participant = participantDao.searchByIdentifier(id).get(0) ;
                 	registrations = studySubjectDao.searchByParticipantId(participant.getId());
                 }
            }
           
        }
        else if (request.getParameter("select").equals("Study")) {
            Study study = new Study(true);
            String studyOption = request.getParameter("studyOption");
            if (id == null) {
            	registrations = studySubjectDao.getAll();
            }else{
                if (StringUtils.equals(studyOption, "shortTitle")) {
                	registrations = studySubjectDao.searchByStudyId(id);
                }
                else if(StringUtils.equals(studyOption, "id")){
                	study = studyDao.searchByIdentifier(id).get(0);
                	registrations = studySubjectDao.searchByStudyId(study.getId());
                }
            }
        }
        else if (request.getParameter("select").equals("Id")) {
            if(id == null ){
            	registrations = studySubjectDao.searchByExample(registration, true);
            }else{
               	registrations = studySubjectDao.searchByIdentifier(id);
            }
            
        }

        log.debug("Search registrations result size: " + registrations.size());
        Map<String, List<Lov>> configMap = configurationProperty.getMap();
        Map map = errors.getModel();
        map.put("registrations", registrations);
        map.put("searchTypeRefDataPrt", configMap.get("participantSearchType"));
        map.put("searchTypeRefDataStudy", configMap.get("studySearchTypeForRegistration"));
        ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
        return modelAndView;
    }

    protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
        Map<String, Object> refdata = new HashMap<String, Object>();
        Map<String, List<Lov>> configMap = configurationProperty.getMap();

        refdata.put("searchTypeRefDataPrt", configMap.get("participantSearchType"));
        refdata.put("searchTypeRefDataStudy", configMap.get("studySearchTypeForRegistration"));
        return refdata;
    }

    public ConfigurationProperty getConfigurationProperty() {
        return configurationProperty;
    }

    public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
        this.configurationProperty = configurationProperty;
    }

    public ParticipantDao getParticipantDao() {
        return participantDao;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    public StudySiteDao getStudySiteDao() {
        return studySiteDao;
    }

    public void setStudySiteDao(StudySiteDao studySiteDao) {
        this.studySiteDao = studySiteDao;
    }

    public StudySubjectDao getStudySubjectDao() {
        return studySubjectDao;
    }

    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

}