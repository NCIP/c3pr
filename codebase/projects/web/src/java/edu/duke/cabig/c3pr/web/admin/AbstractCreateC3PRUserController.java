package edu.duke.cabig.c3pr.web.admin;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 7, 2007
 * Time: 7:11:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractCreateC3PRUserController<D extends C3PRUser> extends SimpleFormController {

    private HealthcareSiteDao healthcareSiteDao;
    private ConfigurationProperty configurationProperty;


    protected Map<String, Object> referenceData(HttpServletRequest request) {

        Map<String, Object> configMap = configurationProperty.getMap();
        Map<String, Object> refdata = new HashMap<String, Object>();
        refdata.put("studySiteStatusRefData", configMap
                .get("studySiteStatusRefData"));
        refdata.put("healthcareSites", healthcareSiteDao.getAll());
        return refdata;
    }

    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Date.class, ControllerTools
                .getDateEditor(true));
        binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(
                healthcareSiteDao));
    }


    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

    public ConfigurationProperty getConfigurationProperty() {
        return configurationProperty;
    }

    public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
        this.configurationProperty = configurationProperty;
    }
}
