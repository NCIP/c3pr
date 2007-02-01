package org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: PersistencePreference.java,v 1.1.1.1 2007-02-01 20:50:38 kherm Exp $
 */

public class PersistencePreference {

    private String oid = null;
    private boolean readonly = false;
    private String name = null;
    private String[] values = null;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }


}
