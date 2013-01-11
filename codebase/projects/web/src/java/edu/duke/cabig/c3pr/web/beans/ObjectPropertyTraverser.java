/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.beans;

import org.springframework.beans.PropertyValues;

public interface ObjectPropertyTraverser extends ObjectPropertyReader {

    public void traversePropertyValues(PropertyValues pvs) throws Exception;

    public void traversePropertyPath() throws Exception;
}
