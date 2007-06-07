package edu.duke.cabig.c3pr.domain;

import javax.persistence.MappedSuperclass;

/**
 * Domain Object with a Grid Identifier
 * @author Priyatam
 */
@MappedSuperclass
public class AbstractGridIdentifiableDomainObject extends AbstractDomainObject implements GridIdentifiable {
    private String gridId;

    /// LOGIC

    public boolean hasGridId() {
        return getGridId() != null;
    }

    /// BEAN PROPERTIES

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }
}
