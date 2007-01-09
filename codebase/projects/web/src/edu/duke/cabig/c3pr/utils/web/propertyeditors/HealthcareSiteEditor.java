package edu.duke.cabig.c3pr.utils.web.propertyeditors;

import java.beans.PropertyEditorSupport;

import edu.duke.cabig.c3pr.dao.BaseDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.DomainObject;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.StudySite;

/**
 * Property Editor to convert HealthCareSite to an object and viceversa
 * Note that this PropertyEditor takes care of setting the paretn studySite
 * object back to retain the object hierarchy
 * @author Priyatam
 */
public class HealthcareSiteEditor extends PropertyEditorSupport {
    private BaseDao dao;
    private StudySite site;

    public HealthcareSiteEditor(BaseDao dao, StudySite site) {
        this.dao = dao;
        this.site=site;        
    }

    @Override
    public void setValue(Object value) {
        if (value != null && !(dao.domainClass().isAssignableFrom(value.getClass()))) {
            throw new IllegalArgumentException("This editor only handles instances of " + dao.domainClass().getName());
        }
        setValue((DomainObject) value);
    }

    private void setValue(DomainObject value) {
        if (value != null && value.getId() == null) {
            throw new IllegalArgumentException("This editor can't handle values without IDs");
        }
        super.setValue(value);
    }

    @Override
    public String getAsText() {
        DomainObject domainObj = (DomainObject) getValue();
        if (domainObj == null) 
            return null;
        return domainObj.getId().toString();        
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
    	HealthcareSite newValue;
        if (text == null) {
            newValue = null;
        } else {
            Integer id = new Integer(text);
            newValue = (HealthcareSite)dao.getById(id);
            if (newValue == null) {
                throw new IllegalArgumentException("There is no " + dao.domainClass().getSimpleName() + " with id=" + id);
            }
        }
        site.setSite(newValue);
        setValue(newValue);
    }
}
