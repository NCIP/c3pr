/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.esb;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 14, 2007
 * Time: 3:09:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MessageResponseRetreiverService {

    /*
     * This method can be used to retrieve the responses
     */
    public Vector getResponses();
    
}
