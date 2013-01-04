/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.esb;

import gov.nih.nci.caxchange.Response;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 26, 2007
 * Time: 10:27:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CaXchangeMessageResponseHandler {

    public void handleMessageResponse(String objectId, Response response);
}
