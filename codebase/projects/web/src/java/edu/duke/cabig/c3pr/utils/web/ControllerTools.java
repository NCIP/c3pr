package edu.duke.cabig.c3pr.utils.web;

import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;

import edu.duke.cabig.c3pr.dao.BaseDao;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;


/**
 * @author Priyatam
 */
public class ControllerTools {

    // TODO: make date format externally configurable
    public static PropertyEditor getDateEditor(boolean required) {
        // note that date formats are not threadsafe, so we have to create a new one each time
        return new CustomDateEditor(createDateFormat(), !required);
    }

    // TODO: make date format externally configurable
    public static DateFormat createDateFormat() {
        return new SimpleDateFormat("MM/dd/yyyy");
    }

    public static void registerDomainObjectEditor(ServletRequestDataBinder binder, String field, BaseDao dao) {
        binder.registerCustomEditor(dao.domainClass(), field, new CustomDaoEditor(dao));
    }

    public static void registerDomainObjectEditor(ServletRequestDataBinder binder, BaseDao dao) {
        binder.registerCustomEditor(dao.domainClass(), new CustomDaoEditor(dao));
    }
    
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return header != null && "XMLHttpRequest".equals(header);
    }

    private ControllerTools() { }
}
