/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.exolab.castor.mapping.GeneralizedFieldHandler;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Mar 18, 2007 Time: 8:53:53 PM To change this template
 * use File | Settings | File Templates.
 */
public class StandardFormatDateFieldHandler extends GeneralizedFieldHandler {

    private static final String FORMAT = "MM-dd-yyyy";

    public StandardFormatDateFieldHandler() {
        super();
    }

    public Object convertUponGet(Object value) {
        if (value == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
        Date date = (Date) value;
        return formatter.format(date);
    }

    public Object convertUponSet(Object value) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
        Date date = null;
        try {
            date = formatter.parse((String) value);
        }
        catch (ParseException px) {
            throw new IllegalArgumentException(px.getMessage());
        }
        return date;
    }

    public Class getFieldType() {
        return Date.class;
    }
}
