/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: Processor.java,v 1.1.1.1 2007-02-01 20:39:56 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.impl.BaseResource;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

public class Processor extends BaseResource {

    public static final String MODEL = "PROCESSOR_MODEL";
    public static final String CACHE = "PROCESSOR_CACHE";
    public static final String LOAD1 = "PROCESSOR_LOAD1";
    public static final String LOAD5 = "PROCESSOR_LOAD5";
    public static final String LOAD15 = "PROCESSOR_LOAD15";

    private static PortletLog log = SportletLog.getInstance(Processor.class);

    public Processor() {
        super();
        setResourceType(ProcessorType.INSTANCE);
    }

    public String getLabel() {
        return getModel();
    }

    public void setLabel(String label) {
        setModel(label);
    }

    public String getModel() {
        return getResourceAttributeValue(MODEL);
    }

    public void setModel(String model) {
        getResourceAttributeValue(MODEL, model);
    }

    public int getCache() {
        return getResourceAttributeAsInt(CACHE, 0);
    }

    public void setCache(int cache) {
        getResourceAttributeAsInt(CACHE, cache);
    }

    public double getLoad1() {
        return getResourceAttributeAsDbl(LOAD1, 0.0);
    }

    public void setLoad1(double value) {
        setResourceAttributeAsDbl(LOAD1, value);
    }

    public double getLoad5() {
        return getResourceAttributeAsDbl(LOAD5, 0.0);
    }

    public void setLoad5(double value) {
        setResourceAttributeAsDbl(LOAD5, value);
    }

    public double getLoad15() {
        return getResourceAttributeAsDbl(LOAD15, 0.0);
    }

    public void setLoad15(double value) {
        setResourceAttributeAsDbl(LOAD15, value);
    }
}
