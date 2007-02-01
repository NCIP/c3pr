/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: SnipStorageService.java,v 1.1.1.1 2007-02-01 20:08:33 kherm Exp $
 */
package org.gridlab.gridsphere.extras.services.wiki;

import org.gridlab.gridsphere.portlet.User;
import org.radeox.api.engine.WikiRenderEngine;

import java.util.List;

public interface SnipStorageService{
    /**
     * Adds a snip to the storage
     * @param username
     * @param name
     * @param text
     */
    String createSnip(String username, String name, String text);

    /**
     * Deletes the given note
     * @param snip
     */
    void deleteSnip(Snip snip);

    /**
     * returns the sheet with the oid
     * @param Oid
     * @return
     */
    Snip getSnipByOid(String Oid);

    /**
     * Gets alls notes for this user
     * @param username
     * @return
     */
    List getSnips(String username);

    /**
     * updates the given sheet
     * @param snip
     */
    String update(Snip snip);

    /**
     * retrns als notes containing the searchstring
     * @param searchstring
     * @return
     */
    List searchSnips(String searchstring);

    boolean snipExists(String name);

    Snip getSnip(String snipname);

    void deleteSnip(String snipname);

}
