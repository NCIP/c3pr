/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.rules.repository;

import java.io.File;
import java.net.URL;

/**
 * This bean when startup will delete the workspace directory of Rules. 
 * Note:- The cleaning of repository will be done only once.
 * @author admin
 *
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RepositoryCleaner {

    private static Object lock;

    private static final Log log = LogFactory.getLog(RepositoryCleaner.class);

    public RepositoryCleaner(String path) {
        log.debug("Going to delete the contents inside the local rules repository [" + path + "]");
        // run this only once per JVM/classloader
        if (lock == null) {
            try {
                URL url = new URL(path);
                File f = new File(url.getFile());
                RepositoryCleaner.deleteDirectoryContents(f);
            } catch (Exception e) {
                // ignore
                log
                                .debug("Error while emptying the contents of rules repository ["
                                                + path + "]", e);
            } finally {
                lock = new Object();
            }
        }
        log.debug("Completed deletion of conents #*#");
    }

    /**
     * Recursively empty the directory.
     * 
     * @param path
     */
    static public void deleteDirectoryContents(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) deleteDirectoryContents(files[i]);
                files[i].delete();
            }
        }

    }

}
