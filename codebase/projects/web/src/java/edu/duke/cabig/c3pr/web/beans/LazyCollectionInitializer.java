/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.beans;

import java.lang.reflect.Field;

public interface LazyCollectionInitializer {

    public void processLazyInitialization(Object object, int key, String collectionType)
                    throws Exception;

    public void laziliyInitialize(Object collection, Field collectionField, int index)
                    throws Exception;
}
