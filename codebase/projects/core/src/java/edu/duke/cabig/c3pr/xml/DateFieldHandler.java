package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.GeneralizedFieldHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 18, 2007
 * Time: 8:53:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateFieldHandler extends GeneralizedFieldHandler {

    private static final String FORMAT = "yyyy-MM-dd";

    public DateFieldHandler() {
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
