package edu.duke.cabig.c3pr.web.ajax;

import edu.duke.cabig.c3pr.dao.*;
import edu.duke.cabig.c3pr.domain.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.HttpSessionRequiredException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Priyatam
 */
public class StudyAjaxFacade extends BaseStudyAjaxFacade {
    private StudyDao studyDao;
    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
    private StudyPersonnelDao studyPersonnelDao;
    private ResearchStaffDao researchStaffDao;
    private DiseaseCategoryDao diseaseCategoryDao;
    private DiseaseTermDao diseaseTermDao;
    private OrganizationDao organizationDao;
	private HealthcareSiteDao healthcareSiteDao;

    private static Log log = LogFactory.getLog(StudyAjaxFacade.class);

    @SuppressWarnings("unchecked")
    private <T> T buildReduced(T src, List<String> properties) {
        T dst = null;
        try {
            // it doesn't seem like this cast should be necessary
            dst = (T) src.getClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        }

        BeanWrapper source = new BeanWrapperImpl(src);
        BeanWrapper destination = new BeanWrapperImpl(dst);
        for (String property : properties) {
            destination.setPropertyValue(
                    property,
                    source.getPropertyValue(property)
            );
        }
        return dst;
    }

    /**
     * Add export to the search results table
     *
     * @param model
     * @param studies
     * @param title
     * @param action
     * @return
     * @throws Exception
     */
    @Override
    public Object build(TableModel model, Collection studies, String title, String action) throws Exception {

        Export export = model.getExportInstance();
        export.setView(TableConstants.VIEW_XLS);
        export.setViewResolver(TableConstants.VIEW_XLS);
        export.setImageName(TableConstants.VIEW_XLS);
        export.setText(TableConstants.VIEW_XLS);
        export.setFileName("study_report.xls");
        model.addExport(export);

        return super.build(model, studies, title, action);
    }


    /**
     * Used to export table. Called by the controller
     *
     * @param parameterMap
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getTableForExport(Map parameterMap, HttpServletRequest request) {
        Context context = new HttpServletRequestContext(request);


        TableModel model = new TableModelImpl(context);
        Collection<Study> studies = (Collection<Study>) parameterMap.get("studyResults");
        try {
            return build(model, studies, "Search Result", null).toString();
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return "";
    }


    /**
     * Get the Search Table
     *
     * @param parameterMap
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getTable(Map parameterMap, HttpServletRequest request) {
        Context context = new HttpServletRequestContext(request, parameterMap);
        String action = "/pages/study/searchStudy";

        TableModel model = new TableModelImpl(context);
        Collection<Study> studies = null;

        //do a new search
        Study study = new Study();
        String type = ((List) parameterMap.get("searchType")).get(0).toString();
        String searchtext = ((List) parameterMap.get("searchText")).get(0).toString();

        log.debug("search string = " + searchtext + "; type = " + type);

        if ("status".equals(type))
            study.setStatus(searchtext);
        else if ("id".equals(type)) {
            Identifier id = new Identifier();
            id.setValue(searchtext);
            study.addIdentifier(id);
        } else if ("shortTitle".equals(type))
            study.setShortTitleText(searchtext);

        try {
            studies = studyDao.searchByExample(study, true);
            return build(model, studies, "", action).toString();
        } catch (Exception e) {
            log.debug(e.getMessage());
        }

        return "";
    }


    public List<Study> matchStudies(String text) {
        List<Study> studies = studyDao.getBySubnames(extractSubnames(text));
        //  cut down objects for serialization
        List<Study> reducedStudies = new ArrayList<Study>(studies.size());
        for (Study study : studies) {
            reducedStudies.add(buildReduced(study, Arrays.asList("id", "shortTitleText"))
            );
        }
        return reducedStudies;
    }

    public List<HealthcareSiteInvestigator> matchSiteInvestigators(String text, int siteIndex,
                                                                   HttpServletRequest request) throws Exception {
        Study study = (Study) getCommandOnly(request);
        int siteId = study.getStudySites().get(siteIndex).getHealthcareSite().getId();
        List<HealthcareSiteInvestigator> inv = healthcareSiteInvestigatorDao
                .getBySubnames(extractSubnames(text), siteId);
        List<HealthcareSiteInvestigator> reducedInv = new ArrayList<HealthcareSiteInvestigator>(inv.size());
        for (HealthcareSiteInvestigator hcInv : inv) {
            reducedInv.add(buildReduced(hcInv, Arrays.asList("id", "investigator"))
            );
        }

        return reducedInv;
    }

    public List<StudyPersonnel> matchStudyPersonnels(String text, int siteIndex,
                                                     HttpServletRequest request) throws Exception {
        Study study = (Study) getCommandOnly(request);
        int siteId = study.getStudySites().get(siteIndex).getHealthcareSite().getId();
        List<StudyPersonnel> personnel = studyPersonnelDao.getBySubnames(extractSubnames(text), siteId);
        List<StudyPersonnel> reducedPersonnel = new ArrayList<StudyPersonnel>(personnel.size());
        for (StudyPersonnel hcInv : personnel) {
            reducedPersonnel.add(buildReduced(hcInv, Arrays.asList("id", "researchStaff"))
            );
        }

        return reducedPersonnel;
    }

    public List<ResearchStaff> matchResearchStaffs(String text, int siteIndex,
                                                   HttpServletRequest request) throws Exception {
        Study study = (Study) getCommandOnly(request);
        int siteId = study.getStudySites().get(siteIndex).getHealthcareSite().getId();
        List<ResearchStaff> staffCol = researchStaffDao.getBySubnames(extractSubnames(text), siteId);
        List<ResearchStaff> reducedStaffCol = new ArrayList<ResearchStaff>(staffCol.size());
        for (ResearchStaff staff : staffCol) {
            reducedStaffCol.add(buildReduced(staff, Arrays.asList("id", "firstName", "lastName"))
            );
        }

        return reducedStaffCol;
    }

    public List<DiseaseCategory> matchDiseaseCategories(String text, Integer categoryId) {
        List<DiseaseCategory> diseaseCategories = diseaseCategoryDao.getBySubname(extractSubnames(text), categoryId);
        return diseaseCategories;
    }

    public List<DiseaseCategory> matchDiseaseCategoriesByParentId(Integer parentCategoryId) {
        List<DiseaseCategory> diseaseCategories = diseaseCategoryDao.getByParentId(parentCategoryId);
        return diseaseCategories;
    }

    public List<DiseaseTerm> matchDiseaseTermsByCategoryId(Integer categoryId) {
        List<DiseaseTerm> diseaseTerms = diseaseTermDao.getByCategoryId(categoryId);
        return diseaseTerms;
    }
    
    public List<HealthcareSite> matchHealthcareSites(String text) throws Exception {
    	
    	 List<HealthcareSite> healthcareSites = healthcareSiteDao.getBySubnames(extractSubnames(text));

		List<HealthcareSite> reducedHealthcareSites = new ArrayList<HealthcareSite>(
				healthcareSites.size());
		for (HealthcareSite healthcareSite : healthcareSites) {
			reducedHealthcareSites.add(buildReduced(healthcareSite, Arrays.asList(
					"id", "name")));
		}
		return reducedHealthcareSites;

	}

    private final Object getCommandOnly(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new HttpSessionRequiredException("Must have session when trying to bind (in session-form mode)");
        }
        String formAttrName = getFormSessionAttributeName();
        Object sessionFormObject = session.getAttribute(formAttrName);
        if (sessionFormObject == null) {
            formAttrName = getFormSessionAttributeNameAgain();
            sessionFormObject = session.getAttribute(formAttrName);
            return sessionFormObject;
        }

        return sessionFormObject;
    }

    private String getFormSessionAttributeName() {
        return "edu.duke.cabig.c3pr.web.study.CreateStudyController.FORM.command";
    }

    private String getFormSessionAttributeNameAgain() {
        return "edu.duke.cabig.c3pr.web.study.EditStudyController.FORM.command";
    }

    private String[] extractSubnames(String text) {
        return text.split("\\s+");
    }

    ////// CONFIGURATION

    @Required
    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
        return healthcareSiteInvestigatorDao;
    }

    public void setHealthcareSiteInvestigatorDao(
            HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
        this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
    }

    public StudyPersonnelDao getStudyPersonnelDao() {
        return studyPersonnelDao;
    }

    public void setStudyPersonnelDao(StudyPersonnelDao studyPersonnelDao) {
        this.studyPersonnelDao = studyPersonnelDao;
    }

    public ResearchStaffDao getResearchStaffDao() {
        return researchStaffDao;
    }

    public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
        this.researchStaffDao = researchStaffDao;
    }

    public DiseaseCategoryDao getDiseaseCategoryDao() {
        return diseaseCategoryDao;
    }

    public void setDiseaseCategoryDao(DiseaseCategoryDao diseaseCategoryDao) {
        this.diseaseCategoryDao = diseaseCategoryDao;
    }

    public DiseaseTermDao getDiseaseTermDao() {
        return diseaseTermDao;
    }

    public void setDiseaseTermDao(DiseaseTermDao diseaseTermDao) {
        this.diseaseTermDao = diseaseTermDao;
    }

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}


}