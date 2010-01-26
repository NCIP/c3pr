package edu.duke.cabig.c3pr.web.study;

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

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.WebUtils;
import edu.duke.cabig.c3pr.web.SearchCommand;
import edu.duke.cabig.c3pr.web.ajax.StudyAjaxFacade;

public class SearchStudyController extends SimpleFormController {

    private static Log log = LogFactory.getLog(SearchStudyController.class);

    private ConfigurationProperty configurationProperty;

    private StudyDao studyDao;

    private StudyAjaxFacade studyAjaxFacade;
    
    private ResearchStaffDao researchStaffDao;

    @Override
    protected boolean isFormSubmission(HttpServletRequest request) {
        if (request.getParameter("test") != null) return true;
        return super.isFormSubmission(request);
    }

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
                                    Object oCommand, BindException errors) throws Exception {
        SearchCommand searchStudyCommand = (SearchCommand) oCommand;
        Study study = new LocalStudy(true);
        String type = searchStudyCommand.getSearchType();
        String searchtext = searchStudyCommand.getSearchText().trim();

        log.debug("search string = " + searchtext + "; type = " + type);

        if ("id".equals(type)) {
            SystemAssignedIdentifier id = new SystemAssignedIdentifier();
            id.setValue(searchtext);
            study.addIdentifier(id);
        } else if ("shortTitle".equals(type)) {
            study.setShortTitleText(searchtext);
        }

        if (WebUtils.hasSubmitParameter(request, "fromRegistration")) {
        	 study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
            if (request.getParameter("standaloneOnly").toString().equalsIgnoreCase("true")) {
                study.setStandaloneIndicator(true);
            }
        }
        
        
        List<Study> exampleStudies = new ArrayList<Study>();
        List<Study> studies = new ArrayList<Study>();
        if ("status".equals(type)) {
        	exampleStudies = studyDao.searchByStatus(study, searchtext, true);
        } else if (WebUtils.hasSubmitParameter(request, "fromStudyRegistrations")) {
        	if (WebUtils.hasSubmitParameter(request, "studyId")){
        		Integer studyId = Integer.parseInt((request.getParameter("studyId")));
        		exampleStudies.add(studyDao.getById(studyId));
            }
        }else {
        	exampleStudies = studyDao.searchByExample(study, true);
        }
        
        
        //This will get me all the studies that have the logged in user's organization as a studyOrganization.
        //However in the REG flow we should only show studies that have the logged in user's organization as
        //the coordinating center of the study site. 
        //In other words a funding sponsor org which would otherwise be able to see the study in the other flows 
        //should not be able to see the study in the REG flow unless its a studySite
        String fromRegistration = "";
        List<Study> studiesViewableFromRegFlow = new ArrayList<Study>();
        if (WebUtils.hasSubmitParameter(request, "fromRegistration")) {
        	fromRegistration = request.getParameter("fromRegistration").toString();
        }
        if(fromRegistration.equalsIgnoreCase("true")){
        	for(int i=0 ; i<exampleStudies.size() ; i++){
            	if(exampleStudies.get(i).getStudySites().size()!=0 && 
            			exampleStudies.get(i).getCoordinatingCenterStudyStatus() != CoordinatingCenterStudyStatus.PENDING){
            		studies.add(exampleStudies.get(i));
            	}
            }
        	gov.nih.nci.security.authorization.domainobjects.User user = (gov.nih.nci.security.authorization.domainobjects.User) request
					.getSession().getAttribute("userObject");
			ResearchStaff rStaff = researchStaffDao.getByEmailAddress(user.getEmailId());
			if (rStaff != null) {
				String nciCodeOfUserOrg = rStaff.getHealthcareSite()
						.getPrimaryIdentifier();
				Boolean shouldDelete;
				for (Study filteredStudy : studies) {
					shouldDelete = Boolean.TRUE;
					for (StudyCoordinatingCenter scc : filteredStudy
							.getStudyCoordinatingCenters()) {
						if (scc.getHealthcareSite().getPrimaryIdentifier()
								.equals(nciCodeOfUserOrg)) {
							//if users Org is coordinating center dont delete study
							shouldDelete = Boolean.FALSE;
						}
					}

					for (StudySite ss : filteredStudy.getStudySites()) {
						if (ss.getHealthcareSite().getPrimaryIdentifier()
								.equals(nciCodeOfUserOrg)) {
							//if users Org is one of the  study sites dont delete study
							shouldDelete = Boolean.FALSE;
						}
					}

					// if users org is either scc or ss then add study to the list to be displayed
					if(!shouldDelete){
						studiesViewableFromRegFlow.add(filteredStudy);
					}
				}
			} else {
				//super admin case.
				//copy the list as is
	        	studiesViewableFromRegFlow = studies;
			}
        } else {
        	//copy the list as is
        	studiesViewableFromRegFlow = exampleStudies;
        }
        

        log.debug("Search results size " + studies.size());
        Map<String, List<Lov>> configMap = configurationProperty.getMap();

        Map map = errors.getModel();
        map.put("studyResults", studiesViewableFromRegFlow);
        map.put("searchTypeRefData", configMap.get("studySearchType"));
        Object viewData = studyAjaxFacade.getTableForExport(map, request);
        request.setAttribute("studies", viewData);
        if (WebUtils.hasSubmitParameter(request, "async")) {
            return new ModelAndView("/registration/studyResultsAsync", map);
        }
        ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
        return modelAndView;
    }

    protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
        Map<String, Object> refdata = new HashMap<String, Object>();
        Map<String, List<Lov>> configMap = configurationProperty.getMap();
        refdata.put("searchTypeRefData", configMap.get("studySearchType"));
        refdata.put("studyStatus", WebUtils.collectOptions(CoordinatingCenterStudyStatus.values(), "Please Select"));
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

	public ResearchStaffDao getResearchStaffDao() {
		return researchStaffDao;
	}

	public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
		this.researchStaffDao = researchStaffDao;
	}
}
