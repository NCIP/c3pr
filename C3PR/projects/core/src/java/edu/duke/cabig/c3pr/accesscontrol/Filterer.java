package edu.duke.cabig.c3pr.accesscontrol;

import java.util.Iterator;

/**
 * Filter strategy interface.
 * 
 * @author Ben Alex
 * @author Paulo Neves
 * @version $Id: Filterer.java 1784 2007-02-24 21:00:24Z luke_t $
 */
interface Filterer {
    // ~ Methods
    // ========================================================================================================

    /**
     * Gets the filtered collection or array.
     * 
     * @return the filtered collection or array
     */
    Object getFilteredObject();

    /**
     * Returns an iterator over the filtered collection or array.
     * 
     * @return an Iterator
     */
    Iterator iterator();

    /**
     * Removes the the given object from the resulting list.
     * 
     * @param object
     *                the object to be removed
     */
    void remove(Object object);
}
