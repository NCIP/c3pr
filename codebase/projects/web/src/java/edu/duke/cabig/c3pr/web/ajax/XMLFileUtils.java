package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.Collection;

import edu.duke.cabig.c3pr.domain.CCTSAbstractMutableDeletableDomainObject;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Nov 26, 2007 Time: 2:22:43 PM To change this template
 * use File | Settings | File Templates.
 */
public class XMLFileUtils {

    /**
     * Thread safe method to retreive list of CCTSAbstractMutableDeletableDomainObject from a List
     * of imported CCTSAbstractMutableDeletableDomainObject objectes <p/> The returned list is a
     * copy and does not modify the passed reference
     * 
     * @param importedObjects
     * @return
     */

    public static Collection<? extends CCTSAbstractMutableDeletableDomainObject> getFilteredCopy(
                    Collection<? extends CCTSAbstractMutableDeletableDomainObject> importedObjects) {
        Collection<CCTSAbstractMutableDeletableDomainObject> newCollection = new ArrayList<CCTSAbstractMutableDeletableDomainObject>();

        synchronized (importedObjects) {

            for (CCTSAbstractMutableDeletableDomainObject domainObject : (Iterable<CCTSAbstractMutableDeletableDomainObject>) importedObjects) {
                // remove invalid studies
                if (!domainObject.isImportErrorFlag()) {
                    newCollection.add(domainObject);
                }
            }
        }
        return newCollection;
    }

    /**
     * Thread safe method to retreive invalid CCTSAbstractMutableDeletableDomainObject from a List
     * of imported CCTSAbstractMutableDeletableDomainObject objectes <p/> Does not modify the
     * original list
     * 
     * @param importedObjects
     * @return
     */
    public static Collection<? extends CCTSAbstractMutableDeletableDomainObject> getInvalidImports(
                    Collection<? extends CCTSAbstractMutableDeletableDomainObject> importedObjects) {
        Collection<CCTSAbstractMutableDeletableDomainObject> newCollection = new ArrayList<CCTSAbstractMutableDeletableDomainObject>();

        synchronized (importedObjects) {

            for (CCTSAbstractMutableDeletableDomainObject domainObject : (Iterable<CCTSAbstractMutableDeletableDomainObject>) importedObjects) {
                // remove invalid studies
                if (domainObject.isImportErrorFlag()) {
                    newCollection.add(domainObject);
                }
            }
        }
        return newCollection;
    }
}
