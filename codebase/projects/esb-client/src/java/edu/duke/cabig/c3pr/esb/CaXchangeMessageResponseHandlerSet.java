/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.esb;

import java.util.HashSet;

import gov.nih.nci.caxchange.Response;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 26, 2007
 * Time: 10:30:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class CaXchangeMessageResponseHandlerSet extends HashSet<CaXchangeMessageResponseHandler> {

    public void notifyAll(String objectId, Response response) {
        for (CaXchangeMessageResponseHandler handler : (Iterable<CaXchangeMessageResponseHandler>) this) {
            handler.handleMessageResponse(objectId, response);

        }
    }
}
