package edu.duke.cabig.c3pr.web.study;

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
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.web.SearchCommand;
import edu.duke.cabig.c3pr.web.ajax.StudyAjaxFacade;

public class SearchStudyController extends SimpleFormController {

    private static Log log = LogFactory.getLog(SearchStudyController.class);
    private ConfigurationProperty configurationProperty;
    private StudyDao studyDao;
    private StudyAjaxFacade studyAjaxFacade;


    @Override
    protected boolean isFormSubmission(HttpServletRequest request) {
        // TODO Auto-generated method stub
        if (request.getParameter("test") != null)
            return true;
        return super.isFormSubmission(request);
    }

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object oCommand, BindException errors) throws Exception {
        SearchCommand searchStudyCommand = (SearchCommand) oCommand;
        Study study = new Study();
        String type = searchStudyCommand.getSearchType();
        String searchtext = searchStudyCommand.getSearchText().trim();

        log.debug("search string = " + searchtext + "; type = " + type);

        if ("status".equals(type))
            study.setStatus(searchtext);
        else if ("id".equals(type)) {
            SystemAssignedIdentifier id = new SystemAssignedIdentifier();
            id.setValue(searchtext);
            study.addIdentifier(id);
        } else if ("shortTitle".equals(type))
            study.setShortTitleText(searchtext);

        List<Study> studies = studyDao.searchByExample(study, true);
        log.debug("Search results size " + studies.size());
        Map<String, List<Lov>> configMap = configurationProperty.getMap();

        Map map = errors.getModel();
        map.put("studyResults", studies);
        map.put("searchTypeRefData", configMap.get("studySearchType"));
        Object viewData = studyAjaxFacade.getTableForExport(map, request);
        request.setAttribute("studies", viewData);
        if(WebUtils.hasSubmitParameter(request, "async")){
        	return new ModelAndView("/registration/studyResultsAsync",map);
        }
        ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
        return modelAndView;
    }

    protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
        Map<String, Object> refdata = new HashMap<String, Object>();
        Map<String, List<Lov>> configMap = configurationProperty.getMap();
        refdata.put("searchTypeRefData", configMap.get("studySearchType"));
        return refdata;
    }

    public ConfigurationProperty getConfigurationProperty() {
        return configurationProperty;
    }

    public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
        this.configurationProperty = configurationProperty;
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    public StudyAjaxFacade getStudyAjaxFacade() {
        return studyAjaxFacade;
    }

    public void setStudyAjaxFacade(StudyAjaxFacade studyAjaxFacade) {
        this.studyAjaxFacade = studyAjaxFacade;
    }
}
