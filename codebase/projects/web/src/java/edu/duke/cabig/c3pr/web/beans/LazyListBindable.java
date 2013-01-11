/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.beans;

import javax.servlet.http.HttpServletRequest;

public interface LazyListBindable {
    public void initLazyListBinder(HttpServletRequest request,
                    HttpServletLazyCollectionInitializer lazyCollectionInitializer)
                    throws Exception;
}
