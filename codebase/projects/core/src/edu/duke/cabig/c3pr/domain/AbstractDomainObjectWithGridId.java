package edu.duke.cabig.c3pr.domain;

import javax.persistence.MappedSuperclass;

/**
 * Domain Object with a Grid Identifier
 * @author Priyatam
 */
@MappedSuperclass
public class AbstractDomainObjectWithGridId extends AbstractDomainObject implements DomainObjectWithGridId {
    private String bigId;

    ////// LOGIC

    public boolean hasBigId() {
        return getGridId() != null;
    }

    ////// BEAN PROPERTIES

    public String getGridId() {
        return bigId;
    }

    public void setGridId(String bigId) {
        this.bigId = bigId;
    }
}
