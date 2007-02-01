/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: PortNumbers.java,v 1.1.1.1 2007-02-01 20:42:23 kherm Exp $
 */
package org.gridlab.gridsphere.services.util;

public class PortNumbers {

    /**
     * Returns the default port for the given keyword, typically a protocol name like "http".
     * @param keyword The keyword (protocol or service name)
     * @return The default port associated with that keyword
     */
    public static int getDefaultPort(String keyword) {
        return 0;
    }
}
