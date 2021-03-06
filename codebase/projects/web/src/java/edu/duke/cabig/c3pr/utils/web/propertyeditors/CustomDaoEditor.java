/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.propertyeditors;

import java.beans.PropertyEditorSupport;

import edu.duke.cabig.c3pr.dao.GridIdentifiableDao;
import gov.nih.nci.cabig.ctms.domain.DomainObject;

/**
 * A {@link java.beans.PropertyEditor} that supports binding domain objects by their IDs
 * 
 * @author Rhett Sutphin
 */
public class CustomDaoEditor extends PropertyEditorSupport {
    private GridIdentifiableDao dao;

    public CustomDaoEditor(GridIdentifiableDao dao) {
        this.dao = dao;
    }

    /*@Override
    public void setValue(Object value) {
        if (value != null && !(dao.domainClass().isAssignableFrom(value.getClass()))) {
            throw new IllegalArgumentException("This editor only handles instances of "
                            + dao.domainClass().getName());
        }
        setValue((DomainObject) value);
    }*/

    private void setValue(DomainObject value) {
        if (value != null && value.getId() == null) {
            throw new IllegalArgumentException("This editor can't handle values without IDs");
        }
        super.setValue(value);
    }

    @Override
    public String getAsText() {
        DomainObject domainObj = (DomainObject) getValue();
        if (domainObj == null) return null;
        return domainObj.getId().toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        DomainObject newValue;
        if (text == null || text.equals("")) {
            newValue = null;
        }
        else {
            Integer id = new Integer(text);
            newValue = dao.getById(id);
            if (newValue == null) {
                throw new IllegalArgumentException("There is no "
                                + dao.domainClass().getSimpleName() + " with id=" + id);
            }
        }
        setValue(newValue);
    }
}
