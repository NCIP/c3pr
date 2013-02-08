/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.beans;

import java.lang.reflect.Method;

public interface Associater {
    public void associate(Object entity1, Object entity2, Method method) throws Exception;
}
