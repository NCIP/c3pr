/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.UUID;

/**
 * A local implementation of GridIdentifierCreator, suitable for use in environments where there is
 * no external identifier service. The each identifier returned is the string version of a call to
 * {UUID#randomUUID}.
 * 
 * @see {UUID}
 * @author Priyatam
 */
public class LocalGridIdentifierCreator implements GridIdentifierCreator {
    public String getGridIdentifier(String uniqueObjectId) {
        return UUID.randomUUID().toString();
    }
}
