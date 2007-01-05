package edu.duke.cabig.c3pr.utils.editors;

import java.beans.PropertyEditorSupport;

import edu.duke.cabig.c3pr.dao.BaseDao;
import edu.duke.cabig.c3pr.domain.DomainObject;

/**
 * A {@link java.beans.PropertyEditor} that supports binding domain objects by their IDs
 *
 * @author Kulasekaran
 */
public class DaoBasedEditor extends PropertyEditorSupport {
    private BaseDao dao;

    public DaoBasedEditor(BaseDao dao) {
        this.dao = dao;
    }

    @Override
    public String getAsText() {
        DomainObject domainObj = (DomainObject) getValue();
        if (domainObj == null) {
            return null;
        } else {
            return domainObj.getId().toString();
        }
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        DomainObject newValue;
        if (text == null) {
            newValue = null;
        } else {
            Integer id = new Integer(text);
            newValue = dao.getById(id);
            if (newValue == null) {
                throw new IllegalArgumentException("There is no " + dao.domainClass().getSimpleName() + " with id=" + id);
            }
        }
        setValue(newValue);
    }
}

