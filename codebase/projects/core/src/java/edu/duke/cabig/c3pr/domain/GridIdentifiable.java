package edu.duke.cabig.c3pr.domain;
/**
 * Interface which identifies a Grid Identifier. Typically implemented
 * by Domain Objects which need a Grid Identifier.
 * @author Priyatam
 */
public interface GridIdentifiable {
    /**
     * @return the grid-scoped unique identifier for this object
     */
    String getGridId();

    /**
     * Specify the grid-scoped unique identifier for this object
     * @param gridId
     */
    void setGridId(String gridId);
}
