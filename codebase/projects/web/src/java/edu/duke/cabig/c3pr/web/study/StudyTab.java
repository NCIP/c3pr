package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 14, 2007
 * Time: 12:43:28 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class StudyTab extends Tab<Study> {
    protected ConfigurationProperty configurationProperty = new ConfigurationProperty();


    public StudyTab() {
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
}
