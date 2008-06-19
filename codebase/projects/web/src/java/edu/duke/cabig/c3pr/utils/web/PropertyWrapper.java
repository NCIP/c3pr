package edu.duke.cabig.c3pr.utils.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: ion
 * Date: Jun 17, 2008
 * Time: 12:04:50 PM
 */

public class PropertyWrapper {

    protected static final Log log = LogFactory.getLog(PropertyWrapper.class);
    private Properties p;

    public PropertyWrapper(Properties p) {
        this.p = p;
    }

    public Properties getP() {
        return p;
    }

    public Set getKeys() {
        return p.keySet();
    }

}
