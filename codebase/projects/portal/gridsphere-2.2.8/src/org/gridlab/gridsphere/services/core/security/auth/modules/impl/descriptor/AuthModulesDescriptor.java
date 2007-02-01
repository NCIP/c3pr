/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: AuthModulesDescriptor.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * The <code>AuthModulesDescriptor</code> 
 */
public class AuthModulesDescriptor {

    private AuthModuleCollection authModules = null;
    private PersistenceManagerXml pmXML = null;

    /**
     * Constructor disallows non-argument instantiation
     */
    private AuthModulesDescriptor() {
    }

    public AuthModulesDescriptor(String descriptorFile, String mappingFile) throws IOException, PersistenceManagerException {
        pmXML = PersistenceManagerFactory.createPersistenceManagerXml(descriptorFile, mappingFile);
        authModules = (AuthModuleCollection) pmXML.load();
    }

    /**
     * Returns the collection of auth module definitions
     *
     * @return the collection of auth module definitions
     */
    public AuthModuleCollection getCollection() {
        return authModules;
    }

    /**
     * Sets the collection of auth module definitions
     *
     * @param authModules the collection of auth module definitions
     */
    public void setCollection(AuthModuleCollection authModules) {
        this.authModules = authModules;
    }

    /**
     * Sets the auth module definition
     *
     * @param definition the auth module definition
     */
    public void setModuleDefinition(AuthModuleDefinition definition) {
        List serviceDefs = authModules.getAuthModulesList();
        Iterator it = serviceDefs.iterator();
        while (it.hasNext()) {
            AuthModuleDefinition def = (AuthModuleDefinition) it.next();
            if (definition.getModuleName().equals(def.getModuleName())) {
                def.setConfigParamList(definition.getConfigParamList());
            }
        }
    }

    /**
     * Saves the auth module descriptor
     *
     * @throws IOException                 if an I/O error occurs
     * @throws PersistenceManagerException if a Castor error occurs during the marshalling
     */
    public void save() throws IOException, PersistenceManagerException {
        pmXML.save(authModules);
    }

}
