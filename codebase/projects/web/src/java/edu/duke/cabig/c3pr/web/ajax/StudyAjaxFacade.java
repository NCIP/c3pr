package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.HttpSessionRequiredException;

import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.dao.DiseaseCategoryDao;
import edu.duke.cabig.c3pr.dao.DiseaseTermDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorGroupDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudyPersonnelDao;
import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.DiseaseCategory;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.SiteInvestigatorGroupAffiliation;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.web.study.AmendCompanionStudyController;
import edu.duke.cabig.c3pr.web.study.AmendStudyController;
import edu.duke.cabig.c3pr.web.study.CreateCompanionStudyController;
import edu.duke.cabig.c3pr.web.study.CreateStudyController;
import edu.duke.cabig.c3pr.web.study.EditCompanionStudyController;
import edu.duke.cabig.c3pr.web.study.EditStudyController;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

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

    private HealthcareSiteDao healthcareSiteDao;

    private InvestigatorGroupDao investigatorGroupDao;

    private InvestigatorDao investigatorDao;
    
    private PersonnelService personnelSerivice;

    private static Log log = LogFactory.getLog(StudyAjaxFacade.class);

    @SuppressWarnings("unchecked")
    private <T> T buildReduced(T src, List<String> properties) {
        T dst = null;
        try {
            // it doesn't seem like this cast should be necessary
            dst = (T) src.getClass().newInstance();
        }
        catch (InstantiationException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        }

        BeanWrapper source = new BeanWrapperImpl(src);
        BeanWrapper destination = new BeanWrapperImpl(dst);
        for (String property : properties) {
            // only for nested props
            String[] individualProps = property.split("\\.");
            String temp = "";

            for (int i = 0; i < individualProps.length - 1; i++) {
                temp += (i != 0 ? "." : "") + individualProps[i];
                Object o = source.getPropertyValue(temp);
                if (destination.getPropertyValue(temp) == null) {
                    try {
                        destination.setPropertyValue(temp, o.getClass().newInstance());
                    }
                    catch (BeansException e) {
                        log.error(e.getMessage());
                    }
                    catch (InstantiationException e) {
                        log.error(e.getMessage());
                    }
                    catch (IllegalAccessException e) {
                        log.error(e.getMessage());
                    }
                }
            }
            // for single and nested props
            destination.setPropertyValue(property, source.getPropertyValue(property));
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
    public Object build(TableModel model, Collection studies, String title, String action)
                    throws Exception {

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
            return build(model, studies, "Search Result", "/pages/study/searchStudy").toString();
        }
        catch (Exception e) {
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

        // do a new search
        Study study = new LocalStudy(true);
        String type = ((List) parameterMap.get("searchType")).get(0).toString();
        String searchtext = ((List) parameterMap.get("searchText")).get(0).toString();

        log.debug("search string = " + searchtext + "; type = " + type);

        if ("status".equals(type)) study
                        .setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        else if ("id".equals(type)) {
            SystemAssignedIdentifier id = new SystemAssignedIdentifier();
            id.setValue(searchtext);
            study.addIdentifier(id);
        }
        else if ("shortTitle".equals(type)) study.setShortTitleText(searchtext);

        try {
            studies = studyDao.searchByExample(study, true, 0);
            return build(model, studies, "", action).toString();
        }
        catch (Exception e) {
            log.debug(e.getMessage());
        }

        return "";
    }

    public List<DiseaseTerm> matchDiseaseTerms(String text) {
        List<DiseaseTerm> diseaseTerms = diseaseTermDao.getBySubnames(extractSubnames(text));
        List<DiseaseTerm> reducedList = new ArrayList<DiseaseTerm>(diseaseTerms.size());
        for (DiseaseTerm diseaseTerm: diseaseTerms) {
        	reducedList .add(buildReduced(diseaseTerm, Arrays.asList("id", "ctepTerm")));
        }
        return reducedList;
    }

    public List<HealthcareSiteInvestigator> matchStudyOrganizationInvestigators(String text,
                    int siteIndex, HttpServletRequest request) throws Exception {
        StudyWrapper studyWrapper = (StudyWrapper) getCommandOnly(request);
        Study study = studyWrapper.getStudy();
        int siteId = study.getStudyOrganizations().get(siteIndex).getHealthcareSite().getId();
        List<HealthcareSiteInvestigator> inv = healthcareSiteInvestigatorDao.getBySubnames(
                        extractSubnames(text), siteId);
        List<HealthcareSiteInvestigator> reducedInv = new ArrayList<HealthcareSiteInvestigator>(inv
                        .size());
        for (HealthcareSiteInvestigator hcInv : inv) {
            // creating a new temp HSI and calling build reduced twice as
            // Arrays.aslist doesnt understand
            // the dot operator (in other words...something like inv.firstName
            // doesnt work)
            // Also calling build reduced with specific params instead of the
            // whole HSI object to prevent
            // hibernate from retrieving every nested object.
            HealthcareSiteInvestigator temp;
            temp = buildReduced(hcInv, Arrays.asList("id"));
            temp.setInvestigator(buildReduced(hcInv.getInvestigator(), Arrays.asList("firstName",
                            "lastName", "maidenName")));
            reducedInv.add(temp);

        }

        return reducedInv;
    }

    public List<HealthcareSiteInvestigator> matchStudyOrganizationInvestigatorsGivenOrganizationId(
                    String text, int organizationId, HttpServletRequest request) throws Exception {
        List<HealthcareSiteInvestigator> inv = healthcareSiteInvestigatorDao.getBySubnames(
                        extractSubnames(text), organizationId);
        List<HealthcareSiteInvestigator> reducedInv = new ArrayList<HealthcareSiteInvestigator>(inv
                        .size());
        for (HealthcareSiteInvestigator hcInv : inv) {

            if (hcInv.getStatusCode().equals(InvestigatorStatusCodeEnum.AC)) {
                HealthcareSiteInvestigator temp;
                temp = buildReduced(hcInv, Arrays.asList("id"));
                temp.setInvestigator(buildReduced(hcInv.getInvestigator(), Arrays.asList(
                                "firstName", "lastName", "maidenName","nciIdentifier")));
                reducedInv.add(temp);
            }

        }

        return reducedInv;
    }

    public List<StudyPersonnel> matchStudyPersonnels(String text, int siteIndex,
                    HttpServletRequest request) throws Exception {
        StudyWrapper wrapper = (StudyWrapper) getCommandOnly(request);
        Study study = wrapper.getStudy();
        int siteId = study.getStudyOrganizations().get(siteIndex).getHealthcareSite().getId();
        List<StudyPersonnel> personnel = studyPersonnelDao.getBySubnames(extractSubnames(text),
                        siteId);
        List<StudyPersonnel> reducedPersonnel = new ArrayList<StudyPersonnel>(personnel.size());
        for (StudyPersonnel hcInv : personnel) {
            reducedPersonnel.add(buildReduced(hcInv, Arrays.asList("id", "researchStaff")));
        }

        return reducedPersonnel;
    }

    public List<ResearchStaff> matchResearchStaffs(String text, int siteIndex,
                    HttpServletRequest request) throws Exception {
        StudyWrapper wrapper = (StudyWrapper) getCommandOnly(request);
        Study study = wrapper.getStudy();
        int siteId = study.getStudyOrganizations().get(siteIndex).getHealthcareSite().getId();
        List<ResearchStaff> staffCol = researchStaffDao
                        .getBySubnames(extractSubnames(text), siteId);
        List<ResearchStaff> reducedStaffCol = new ArrayList<ResearchStaff>(staffCol.size());
        for (ResearchStaff staff : staffCol) {
            reducedStaffCol.add(buildReduced(staff, Arrays.asList("id", "firstName", "lastName")));
        }

        return reducedStaffCol;
    }

    public List<String> matchResearchStaffRoles(String id, HttpServletRequest request)
                    throws Exception {
        List<C3PRUserGroupType> groups = new ArrayList<C3PRUserGroupType>();
        groups = personnelSerivice.getGroups(researchStaffDao.getById(Integer.parseInt(id)));
        List<String> reducedGroups = new ArrayList<String>();
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getDisplayName().equals("Study coordinator")) {
                reducedGroups.add("Study Coordinator");
            }
            else if (groups.get(i).getDisplayName().equals("Site coordinator")) {
                reducedGroups.add("Site Coordinator");
            }
            else if (groups.get(i).getDisplayName().equals("C3pr admin")) {
                reducedGroups.add("C3pr Admin");
            }
            else {
                reducedGroups.add(groups.get(i).getDisplayName());
            }
        }
        return reducedGroups;
    }

    public List<DiseaseCategory> matchDiseaseCategories(String text, Integer categoryId) {
        List<DiseaseCategory> diseaseCategories = diseaseCategoryDao.getBySubname(
                        extractSubnames(text), categoryId);
        return diseaseCategories;
    }

    public List<DiseaseCategory> matchDiseaseCategoriesByParentId(Integer parentCategoryId) {
        List<DiseaseCategory> diseaseCategories = diseaseCategoryDao
                        .getByParentId(parentCategoryId);
        return diseaseCategories;
    }

    public List<DiseaseTerm> matchDiseaseTermsByCategoryId(Integer categoryId) {
        List<DiseaseTerm> diseaseTerms = diseaseTermDao.getByCategoryId(categoryId);
        return diseaseTerms;
    }

    public List<InvestigatorGroup> matchActiveGroupsByOrganizationId(Integer hcsId,
                    HttpServletRequest request) throws Exception {
        HealthcareSite hcs = healthcareSiteDao.getById(hcsId);
        List<InvestigatorGroup> invGroups = hcs.getInvestigatorGroups();
        List<InvestigatorGroup> reducedInvGroups = new ArrayList<InvestigatorGroup>();
        for (InvestigatorGroup iGrp : invGroups) {
            if (iGrp.getEndDate() == null || iGrp.getEndDate().after(new Date())) {
                reducedInvGroups.add(buildReduced(iGrp, Arrays.asList("id", "name")));
            }
        }
        return reducedInvGroups;
    }

    public List<HealthcareSiteInvestigator> matchActiveInvestigatorsByGroupId(Integer groupId,
                    HttpServletRequest request) throws Exception {

    	if(groupId == null){
    		return new ArrayList<HealthcareSiteInvestigator>();
    	}
        InvestigatorGroup iGrp = investigatorGroupDao.getById(groupId);
        List<SiteInvestigatorGroupAffiliation> sigaList = null;
        HealthcareSiteInvestigator inv = null;
        sigaList = iGrp.getSiteInvestigatorGroupAffiliations();
        List<HealthcareSiteInvestigator> reducedInvList = new ArrayList<HealthcareSiteInvestigator>();
        for (SiteInvestigatorGroupAffiliation siga : sigaList) {
            if (siga.getEndDate() == null || siga.getEndDate().after(new Date())) {
                inv = siga.getHealthcareSiteInvestigator();
                reducedInvList.add(buildReduced(inv, Arrays.asList("id", "investigator.firstName",
                                "investigator.lastName", "investigator.nciIdentifier")));
            }
        }
        return reducedInvList;
    }

    public List<HealthcareSiteInvestigator> getActiveSiteInvestigators(Integer hcsId)
                    throws Exception {

        HealthcareSite hcs = healthcareSiteDao.getById(hcsId);
        List<HealthcareSiteInvestigator> hcsInvList = hcs.getHealthcareSiteInvestigators();
        List<HealthcareSiteInvestigator> reducedHcsInvList = new ArrayList<HealthcareSiteInvestigator>();
        for (HealthcareSiteInvestigator inv : hcsInvList) {
            if (inv.getStatusCode() != null && inv.getStatusCode().equals(InvestigatorStatusCodeEnum.AC)) {
                reducedHcsInvList.add(buildReduced(inv, Arrays.asList("id",
                                "investigator.firstName", "investigator.lastName", "investigator.nciIdentifier")));
            }
        }
        return reducedHcsInvList;
    }
    
    
    public List<ResearchStaff> getSitePersonnel(Integer hcsId)
    throws Exception {
		HealthcareSite hcs = healthcareSiteDao.getById(hcsId);

		//replaced the following method with a dao call and a resolver call....for coppa data
		//List<ResearchStaff> hcsRSList = hcs.getResearchStaffs();
		List<ResearchStaff> hcsRSList = researchStaffDao.getResearchStaffByOrganizationNCIInstituteCode(hcs);
//		hcsRSList.addAll(researchStaffDao.);
		
		List<ResearchStaff> reducedHcsRsList = new ArrayList<ResearchStaff>();
		for (ResearchStaff rs : hcsRSList) {
		reducedHcsRsList.add(buildReduced(rs, Arrays.asList("id",
		                "firstName","lastName","nciIdentifier")));
		}
		return reducedHcsRsList;
	}

    public List<HealthcareSite> matchHealthcareSites(String text) throws Exception {

        List<HealthcareSite> healthcareSites = healthcareSiteDao
                        .getBySubnames(extractSubnames(text));

        List<HealthcareSite> reducedHealthcareSites = new ArrayList<HealthcareSite>(healthcareSites
                        .size());
        for (HealthcareSite healthcareSite : healthcareSites) {
        	if(healthcareSite instanceof RemoteHealthcareSite){
        		reducedHealthcareSites.add(buildReduced(healthcareSite, Arrays.asList("id", "name",
                "ctepCode","externalId")));
        	}
        	else {reducedHealthcareSites.add(buildReduced(healthcareSite, Arrays.asList("id", "name",
                            "ctepCode")));
        	}
        }
        return reducedHealthcareSites;

    }
    
    public List<Study> matchComapanionStudies(String text, HttpServletRequest request) throws Exception {
    	StudyWrapper wrapper = (StudyWrapper) getCommandOnly(request) ;
    	Study parentStudy = wrapper.getStudy();
    	List<Study> companionStudies = studyDao.getStudiesBySubnamesWithExtraConditionsForPrimaryIdentifier(extractSubnames(text));

        List<Study> reducedCompanionStudies = new ArrayList<Study>(companionStudies.size());
        for (Study companionStudy : companionStudies) {
        	if(companionStudy.getCompanionIndicator() )
	        	if((companionStudy.getStandaloneIndicator() && companionStudy.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.OPEN) || (!companionStudy.getStandaloneIndicator() && !isCompanionAssociatedToAnyStudy(companionStudy))){
	        		reducedCompanionStudies.add(buildReduced(companionStudy, Arrays.asList("id", "shortTitleText", "coordinatingCenterStudyStatus")));
	        	}
        	}
        return reducedCompanionStudies ;
    }
    
    private boolean isCompanionAssociatedToAnyStudy(Study companionStudy){
    	List<CompanionStudyAssociation> parentStudyAssoc = companionStudy.getParentStudyAssociations();
    	if(parentStudyAssoc.size() > 0){
			return true ;
    	}
    	return false ;
    }

	private final Object getCommandOnly(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new HttpSessionRequiredException(
                            "Must have session when trying to bind (in session-form mode)");
        }
        String formAttrName = getFormSessionAttributeName();
        Object sessionFormObject = session.getAttribute(formAttrName);
        if (sessionFormObject == null) {
            formAttrName = getFormSessionAttributeNameAgain();
            sessionFormObject = session.getAttribute(formAttrName);
            if (sessionFormObject == null) {
                formAttrName = getFormSessionAttributeNameAmend();
                sessionFormObject = session.getAttribute(formAttrName);
            }if (sessionFormObject == null) {
                formAttrName = getFormSessionAttributeNameCreateCompanion();
                sessionFormObject = session.getAttribute(formAttrName);
                if (sessionFormObject == null) {
                    formAttrName = getFormSessionAttributeNameEditCompanion();
                    sessionFormObject = session.getAttribute(formAttrName);
                    if (sessionFormObject == null) {
                        formAttrName = getFormSessionAttributeNameAmendCompanion();
                        sessionFormObject = session.getAttribute(formAttrName);
                    }
                }
            }
            return sessionFormObject;
        }

        return sessionFormObject;
    }

    private String getFormSessionAttributeNameAmend() {
        return AmendStudyController.class.getName() + ".FORM.command.to-replace";
    }

    private String getFormSessionAttributeName() {
        return CreateStudyController.class.getName() + ".FORM.command.to-replace";
    }

    private String getFormSessionAttributeNameAgain() {
        return EditStudyController.class.getName() + ".FORM.command.to-replace";
    }
    
    private String getFormSessionAttributeNameAmendCompanion() {
        return AmendCompanionStudyController.class.getName() + ".FORM.command.to-replace";
    }

    private String getFormSessionAttributeNameCreateCompanion() {
        return CreateCompanionStudyController.class.getName() + ".FORM.command.to-replace";
    }

    private String getFormSessionAttributeNameEditCompanion() {
        return EditCompanionStudyController.class.getName() + ".FORM.command.to-replace";
    }

    private String[] extractSubnames(String text) {
        return text.split("\\s+");
    }

    // //// CONFIGURATION

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

    public InvestigatorGroupDao getInvestigatorGroupDao() {
        return investigatorGroupDao;
    }

    public void setInvestigatorGroupDao(InvestigatorGroupDao investigatorGroupDao) {
        this.investigatorGroupDao = investigatorGroupDao;
    }

    public InvestigatorDao getInvestigatorDao() {
        return investigatorDao;
    }

    public void setInvestigatorDao(InvestigatorDao investigatorDao) {
        this.investigatorDao = investigatorDao;
    }

    public void setPersonnelSerivice(PersonnelService personnelSerivice) {
        this.personnelSerivice = personnelSerivice;
    }
    
    public List<DiseaseCategory> getChildCategories(Integer categoryId) throws Exception {
       return diseaseCategoryDao.getByParentId(categoryId);
    }
    
    public List<DiseaseTerm> getDiseaseTerms(Integer categoryId) throws Exception {
    	DiseaseCategory diseaseCategoty = diseaseCategoryDao.getById(categoryId);
    	return diseaseCategoty.getTerms();
    }

}
