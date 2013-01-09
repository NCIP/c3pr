/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.beans;

import javax.servlet.http.HttpServletRequest;

public interface HttpServletLazyCollectionInitializer extends LazyCollectionInitializer {

    public void lazilyInitializeCollections(HttpServletRequest request) throws Exception;

    public void addAssociator(Class c, String methodName);

}
