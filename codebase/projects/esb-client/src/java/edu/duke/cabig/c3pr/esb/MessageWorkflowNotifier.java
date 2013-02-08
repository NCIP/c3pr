/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.esb;

/**
 * Denotes that the implementation will notify
 * the MessageWorkflowCallback through the
 * message lifecycle
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 14, 2007
 * Time: 8:30:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MessageWorkflowNotifier {

    public void setNotificationHandler(MessageWorkflowCallback handler);
}
