/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.propertyeditors;

import org.apache.commons.lang.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author Rhett Sutphin
 */
public class EnumByNameEditor<E extends Enum<E>> extends PropertyEditorSupport {
    Class<E> enumClass;

    public EnumByNameEditor(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || StringUtils.isBlank(text)) {
            setValue(null);
        }
        else {
            setValue(Enum.valueOf(enumClass, text));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getAsText() {
        Enum<E> v = (Enum<E>) getValue();
        if (v == null) {
            return null;
        }
        else {
            return v.name();
        }
    }
}
