package edu.duke.cabig.c3pr.domain;
/**
 * Extension Interface from DomainObject with a Grid Identifier
 * @author Priyatam
 */
public interface DomainObjectWithGridId extends DomainObject{
    /**
     * @return the grid-scoped unique identifier for this object
     */
    String getGridId();

    /**
     * Specify the grid-scoped unique identifier for this object
     * @param bigId
     */
    void setGridId(String bigId);
}
