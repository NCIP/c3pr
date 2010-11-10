package edu.duke.cabig.c3pr.web.report;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.domain.repository.AdvancedSearchRepository;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;

public class AdvancedSearchController extends SimpleFormController {
	
	private String targetObject ;
	private AdvancedSearchRepository advancedSearchRepository ;
	private ConfigurationProperty configurationProperty;
    
	/**
	 * @return the configurationProperty
	 */
	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}
	/**
	 * @param configurationProperty the configurationProperty to set
	 */
	public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}
	/**
	 * @return the targetObject
	 */
	public String getTargetObject() {
		return targetObject;
	}
	/**
	 * @param targetObject the targetObject to set
	 */
	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}
	/**
	 * @return the advancedSearchRepository
	 */
	public AdvancedSearchRepository getAdvancedSearchRepository() {
		return advancedSearchRepository;
	}
	/**
	 * @param advancedSearchRepository the advancedSearchRepository to set
	 */
	public void setAdvancedSearchRepository(AdvancedSearchRepository advancedSearchRepository) {
		this.advancedSearchRepository = advancedSearchRepository;
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) {

        Map<String, Object> configMap = getConfigurationProperty().getMap();
        Map<String, Object> refdata = new HashMap<String, Object>();
        
        refdata.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
        refdata.put("ethnicGroupCodes", configMap.get("ethnicGroupCode"));
        refdata.put("raceCodes", configMap.get("raceCode"));
        
        return refdata;
    }
}
