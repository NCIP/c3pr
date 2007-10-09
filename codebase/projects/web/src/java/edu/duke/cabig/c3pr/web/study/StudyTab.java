package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 14, 2007
 * Time: 12:43:28 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class StudyTab extends InPlaceEditableTab<Study> {
    protected ConfigurationProperty configurationProperty;
    private HealthcareSiteDao healthcareSiteDao;
    protected static final Log log = LogFactory.getLog(StudyTab.class);
    
//    public static final String DISABLE_FORM_DESIGN = "DISABLE_FORM_DESIGN";
    public static final String DISABLE_FORM_DETAILS = "DISABLE_FORM_DETAILS";
    public static final String DISABLE_FORM_EPOCH_AND_ARMS = "DISABLE_FORM_EPOCH_AND_ARMS";
    public static final String DISABLE_FORM_ELIGIBILITY = "DISABLE_FORM_ELIGIBILITY";
    public static final String DISABLE_FORM_STRATIFICATION = "DISABLE_FORM_STRATIFICATION";
    public static final String DISABLE_FORM_RANDOMIZATION = "DISABLE_FORM_RANDOMIZATION";
    public static final String DISABLE_FORM_DISEASES = "DISABLE_FORM_DISEASES";
    public static final String DISABLE_FORM_SITES = "DISABLE_FORM_SITES";
    public static final String DISABLE_FORM_IDENTIFIERS = "DISABLE_FORM_IDENTIFIERS";
//    public static final String DISABLE_FORM_INVESTIGATORS = "DISABLE_FORM_INVESTIGATORS";
//    public static final String DISABLE_FORM_PERSONNEL = "DISABLE_FORM_PERSONNEL";
    
    public StudyTab(){
    	
    }
    
    public StudyTab(String longTitle, String shortTitle, String viewName) {
        super(longTitle, shortTitle, viewName);
    }

    public ConfigurationProperty getConfigurationProperty() {
        return configurationProperty;
    }

    public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
        this.configurationProperty = configurationProperty;
    }

    protected void addConfigMapToRefdata(Map<String, Object> refdata, String name) {
        refdata.put(name, getConfigurationProperty().getMap().get(name));
    }

    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }
    
    public void disableAll(HttpServletRequest request){
//    	request.getSession().setAttribute(DISABLE_FORM_DESIGN, new Boolean(true));
    	request.getSession().setAttribute(DISABLE_FORM_DETAILS, new Boolean(true));
    	request.getSession().setAttribute(DISABLE_FORM_EPOCH_AND_ARMS, new Boolean(true));
    	request.getSession().setAttribute(DISABLE_FORM_ELIGIBILITY, new Boolean(true));
    	request.getSession().setAttribute(DISABLE_FORM_STRATIFICATION, new Boolean(true));
    	request.getSession().setAttribute(DISABLE_FORM_RANDOMIZATION, new Boolean(true));
    	request.getSession().setAttribute(DISABLE_FORM_DISEASES, new Boolean(true));
    	request.getSession().setAttribute(DISABLE_FORM_SITES, new Boolean(true));
//    	request.getSession().setAttribute(DISABLE_FORM_IDENTIFIERS, new Boolean(true));
//    	request.getSession().setAttribute(DISABLE_FORM_INVESTIGATORS, new Boolean(true));
//    	request.getSession().setAttribute(DISABLE_FORM_PERSONNEL, new Boolean(true));
    }
}
