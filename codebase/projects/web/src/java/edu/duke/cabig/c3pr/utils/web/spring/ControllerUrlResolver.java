/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.spring;

/**
 * @author Rhett Sutphin
 */
/* TODO: much of this class is shared with PSC. Refactor into a shared library. */
public interface ControllerUrlResolver {
    ResolvedControllerReference resolve(String controllerBeanName);
}
