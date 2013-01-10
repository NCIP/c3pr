/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections.iterators.ArrayIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A filter used to filter arrays.
 * 
 * @author Ben Alex
 * @author Paulo Neves
 * @version $Id: ArrayFilterer.java 1784 2007-02-24 21:00:24Z luke_t $
 */
class ArrayFilterer implements Filterer {
    // ~ Static fields/initializers
    // =====================================================================================

    protected static final Log logger = LogFactory.getLog(ArrayFilterer.class);

    // ~ Instance fields
    // ================================================================================================

    private Set removeList;

    private Object[] list;

    // ~ Constructors
    // ===================================================================================================

    ArrayFilterer(Object[] list) {
        this.list = list;

        // Collect the removed objects to a HashSet so that
        // it is fast to lookup them when a filtered array
        // is constructed.
        removeList = new HashSet();
    }

    // ~ Methods
    // ========================================================================================================

    /**
     * 
     * @see org.acegisecurity.afterinvocation.Filterer#getFilteredObject()
     */
    public Object getFilteredObject() {
        // Recreate an array of same type and filter the removed objects.
        int originalSize = list.length;
        int sizeOfResultingList = originalSize - removeList.size();
        Object[] filtered = (Object[]) Array.newInstance(list.getClass().getComponentType(),
                        sizeOfResultingList);

        for (int i = 0, j = 0; i < list.length; i++) {
            Object object = list[i];

            if (!removeList.contains(object)) {
                filtered[j] = object;
                j++;
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Original array contained " + originalSize + " elements; now contains "
                            + sizeOfResultingList + " elements");
        }

        return filtered;
    }

    /**
     * 
     * @see org.acegisecurity.afterinvocation.Filterer#iterator()
     */
    public Iterator iterator() {
        return new ArrayIterator(list);
    }

    /**
     * 
     * @see org.acegisecurity.afterinvocation.Filterer#remove(java.lang.Object)
     */
    public void remove(Object object) {
        removeList.add(object);
    }
}
