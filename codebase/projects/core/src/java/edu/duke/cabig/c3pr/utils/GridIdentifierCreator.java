/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

/**
 * Interface to have multiple implementations of Grid Identifiers (local/site)
 * 
 * @author Priyatam
 */
public interface GridIdentifierCreator {
    String getGridIdentifier(String uniqueObjectId);
}
