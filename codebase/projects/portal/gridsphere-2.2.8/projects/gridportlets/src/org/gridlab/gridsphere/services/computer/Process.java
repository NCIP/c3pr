/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: Process.java,v 1.1.1.1 2007-02-01 20:39:56 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.impl.BaseResource;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

public class Process extends BaseResource {

    public static final String PID = "PROCESS_PID";
    public static final String NICE = "PROCESS_NICE";
    public static final String COMMAND = "PROCESS_COMMAND";
    public static final String USER = "PROCESS_USER";
    public static final String TIME = "PROCESS_TIME";
    public static final String CPU = "PROCESS_CPU";
    public static final String MEMORY = "PROCESS_MEMORY";

    private static PortletLog log = SportletLog.getInstance(Process.class);

    public Process() {
        super();
        setResourceType(ProcessType.INSTANCE);
    }

    public Process(String pid) {
        super();
        setResourceType(ProcessType.INSTANCE);
        setPid(pid);
    }

    public String getLabel() {
        return getPid();
    }

    public void setLabel(String label) {
        setPid(label);
    }

    public String getPid() {
        return getResourceAttributeValue(PID);
    }

    public void setPid(String pid) {
        putResourceAttribute(PID, pid);
    }

    public String getCommand() {
        return getResourceAttributeValue(COMMAND);
    }

    public void setCommand(String value) {
        putResourceAttribute(COMMAND, value);
    }

    public String getUserId() {
        return getResourceAttributeValue(USER);
    }

    public void setUserId(String value) {
        putResourceAttribute(USER, value);
    }

    public String getTime() {
        return getResourceAttributeValue(TIME);
    }

    public void setTime(String value) {
        putResourceAttribute(TIME, value);
    }

    public int getNice() {
        return getResourceAttributeAsInt(NICE, 0);
    }

    public void setNice(int value) {
        setResourceAttributeAsInt(NICE, value);
    }

    public double getCpu() {
        return getResourceAttributeAsDbl(CPU, 0.0);
    }

    public void setCpu(double value) {
        setResourceAttributeAsDbl(CPU, value);
    }

    public double getMemory() {
        return getResourceAttributeAsDbl(MEMORY, 0.0);
    }

    public void setMemory(double value) {
        setResourceAttributeAsDbl(MEMORY, value);
    }
}
