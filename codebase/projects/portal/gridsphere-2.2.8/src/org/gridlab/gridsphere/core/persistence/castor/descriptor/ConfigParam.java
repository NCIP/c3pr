/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ConfigParam.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridlab.gridsphere.core.persistence.castor.descriptor;

/**
 * A <code>ConfigParam</code> is an attribute representation used
 * to define attribute names and values in a descriptor by Castor.
 */
public class ConfigParam {

    private String paramName = "";
    private String paramValue = "";

    /**
     * Constructs an instance of ConfigParam
     */
    public ConfigParam() {
    }

    /**
     * Constructs an instance of ConfigParam with a parameter name and value
     *
     * @param paramName  the parameter name
     * @param paramValue the parameter value
     */
    public ConfigParam(String paramName, String paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    /**
     * Returns the parameter name
     *
     * @return the parameter name
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * Sets the parameter name
     *
     * @param paramName the parameter name
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * Returns the parameter value
     *
     * @return the parameter value
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * Sets the parameter value
     *
     * @param paramValue the parameter value
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

}

