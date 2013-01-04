/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr;

/**
 * @author Rhett Sutphin
 */
public interface UseCase {
    int getMajor();

    int getMinor();

    String getTitle();
}
