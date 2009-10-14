package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.PhoneCallRandomization;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.web.WebUtils;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 14, 2007 Time: 12:43:28 PM To change this
 * template use File | Settings | File Templates.
 */
public abstract class StudyTab extends InPlaceEditableTab<StudyWrapper> {
    protected ConfigurationProperty configurationProperty;

    private HealthcareSiteDao healthcareSiteDao;

    protected StudyRepository studyRepository;

    protected StudyService studyService;
    
    protected StudySiteDao studySiteDao;
    
    protected StudyDao studyDao;

    protected static final Log log = LogFactory.getLog(StudyTab.class);

    // public static final String DISABLE_FORM_DESIGN = "DISABLE_FORM_DESIGN";
    public static final String DISABLE_FORM_DETAILS = "DISABLE_FORM_DETAILS";

    public static final String DISABLE_FORM_EPOCH_AND_ARMS = "DISABLE_FORM_EPOCH_AND_ARMS";
    
    public static final String DISABLE_FORM_CONSENT = "DISABLE_FORM_CONSENT";
    
    public static final String DISABLE_FORM_ELIGIBILITY = "DISABLE_FORM_ELIGIBILITY";

    public static final String DISABLE_FORM_STRATIFICATION = "DISABLE_FORM_STRATIFICATION";

    public static final String DISABLE_FORM_RANDOMIZATION = "DISABLE_FORM_RANDOMIZATION";

    public static final String DISABLE_FORM_DISEASES = "DISABLE_FORM_DISEASES";

    public static final String DISABLE_FORM_SITES = "DISABLE_FORM_SITES";

    public static final String DISABLE_FORM_IDENTIFIERS = "DISABLE_FORM_IDENTIFIERS";

    public static final String DISABLE_FORM_NOTIFICATION = "DISABLE_FORM_NOTIFICATION";

    public static final String DISABLE_FORM_COMPANION = "DISABLE_FORM_COMPANION";

    // public static final String DISABLE_FORM_INVESTIGATORS = "DISABLE_FORM_INVESTIGATORS";
    // public static final String DISABLE_FORM_PERSONNEL = "DISABLE_FORM_PERSONNEL";

    public StudyTab() {

    }

    public StudyTab(String longTitle, String shortTitle, String viewName) {
        super(longTitle, shortTitle, viewName);
    }

    public StudyTab(String longTitle, String shortTitle, String viewName, Boolean willSave) {
        super(longTitle, shortTitle, viewName, willSave);
    }

    /*
     * This method sets the study.randomizationIndicator, study.RandomizationType and
     * epoch.randomization nased on teh values selected. This can be called from both the details
     * and the design tab.
     */
    public void updateRandomization(Study study) {

    	if (study.getBlindedIndicator()) {
            study.setRandomizedIndicator(true);
            study.setRandomizationType(RandomizationType.PHONE_CALL);
        }

        if (!study.getRandomizedIndicator()) {
            study.setRandomizationType(null);
        }
        // Instantiating the appropriate randomization class and setting it in the epoch.

        if (!study.getRandomizedIndicator()) {
            List<Epoch> epochs = study.getEpochs();
            for (Epoch epoch : epochs) {
                if (epoch.getRandomizedIndicator()) {
                    epoch.setRandomizedIndicator(false);
                    if(epoch.getRandomization() instanceof BookRandomization){
                    	((BookRandomization)epoch.getRandomization()).getBookRandomizationEntry().clear();
                    }else if(epoch.getRandomization() instanceof PhoneCallRandomization){
                    	((PhoneCallRandomization)epoch.getRandomization()).setPhoneNumber(null);
                    }
                }
            }
        }

        if (study.getEpochs() instanceof List) {
            List epochList = study.getEpochs();
            Epoch tEpoch;
            Iterator iter = epochList.iterator();
            while (iter.hasNext()) {
                tEpoch = (Epoch) iter.next();
                if (study.getRandomizedIndicator() && study.getRandomizationType() != null
                                && tEpoch.getRandomizedIndicator() != null
                                && tEpoch.getRandomizedIndicator()) {
                    if (study.getRandomizationType().equals(RandomizationType.BOOK)) {
                        if (tEpoch.getRandomization() instanceof BookRandomization) {
                            // do nothing. This happens if nothing is chnaged during the edit flow
                        }
                        else {
                            tEpoch.setRandomization(new BookRandomization());
                        }
                    }
                    if (study.getRandomizationType().equals(RandomizationType.PHONE_CALL)) {
                        if (tEpoch.getRandomization() instanceof PhoneCallRandomization) {
                            // do nothing. This happens if nothing is chnaged during the edit flow
                        }
                        else {
                            tEpoch.setRandomization(new PhoneCallRandomization());
                        }
                    }
                }
                else {
                    tEpoch.setRandomization(null);
                }
            }
        }
    }

    public void updateBlindedRandomization(Study study) {
    	if (study.getBlindedIndicator()) {
            study.setRandomizedIndicator(true);
            study.setRandomizationType(RandomizationType.PHONE_CALL);
        }	
    	if (!study.getRandomizedIndicator()) {
            study.setRandomizationType(null);
        }
	}

	public void updateStratification(Study study) {
        if (!study.getStratificationIndicator()) {
            List<Epoch> epochs = study.getEpochs();
            for (Epoch epoch : epochs) {
                if (epoch.getStratificationIndicator()) {
                    epoch.setStratificationIndicator(false);
                    epoch.getStratificationCriteria().clear();
                    epoch.getStratumGroups().clear();
                }
            }
        }
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

    public void disableAll(HttpServletRequest request) {
        request.getSession().setAttribute(DISABLE_FORM_DETAILS, new Boolean(true));
        request.getSession().setAttribute(DISABLE_FORM_CONSENT, new Boolean(true));
        request.getSession().setAttribute(DISABLE_FORM_EPOCH_AND_ARMS, new Boolean(true));
        request.getSession().setAttribute(DISABLE_FORM_ELIGIBILITY, new Boolean(true));
        request.getSession().setAttribute(DISABLE_FORM_STRATIFICATION, new Boolean(true));
        request.getSession().setAttribute(DISABLE_FORM_RANDOMIZATION, new Boolean(true));
        request.getSession().setAttribute(DISABLE_FORM_DISEASES, new Boolean(true));
        request.getSession().setAttribute(DISABLE_FORM_COMPANION, new Boolean(true));
    }

    public void enableAll(HttpServletRequest request) {
        request.getSession().setAttribute(DISABLE_FORM_DETAILS, new Boolean(false));
        request.getSession().setAttribute(DISABLE_FORM_CONSENT, new Boolean(false));
        request.getSession().setAttribute(DISABLE_FORM_EPOCH_AND_ARMS, new Boolean(false));
        request.getSession().setAttribute(DISABLE_FORM_ELIGIBILITY, new Boolean(false));
        request.getSession().setAttribute(DISABLE_FORM_STRATIFICATION, new Boolean(false));
        request.getSession().setAttribute(DISABLE_FORM_RANDOMIZATION, new Boolean(false));
        request.getSession().setAttribute(DISABLE_FORM_DISEASES, new Boolean(false));
        request.getSession().setAttribute(DISABLE_FORM_COMPANION, new Boolean(false));
    }

    public Boolean isAdmin() {
        return WebUtils.isAdmin();
    }
    
    protected Map<String, Object> canDisableTab(HttpServletRequest request, Map<String, Object> refdata, String tabName){
		Object amendFlow = request.getAttribute("amendFlow");
		Object editFlow = request.getAttribute("editFlow");
		Object tabAttribute = request.getSession().getAttribute(tabName);
		
		if ((amendFlow != null && amendFlow.toString().equals("true")) || (editFlow != null && editFlow.toString().equals("true"))) {
			if (tabAttribute != null && !isAdmin()) {
				refdata.put("disableForm", tabAttribute);
			} else {
				refdata.put("disableForm", new Boolean(false));
			}
		}
		return refdata ;
	}

    @Override
    public final void postProcess(HttpServletRequest request, StudyWrapper wrapper, Errors errors) {
        if(!errors.hasErrors()){
        	postProcessOnValidation(request, wrapper, errors);
        }
    }

    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper,
                    Errors errors) {

    }

    public StudyRepository getStudyRepository() {
        return studyRepository;
    }

    public void setStudyRepository(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public void setStudyService(StudyService studyService) {
        this.studyService = studyService;
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

}
