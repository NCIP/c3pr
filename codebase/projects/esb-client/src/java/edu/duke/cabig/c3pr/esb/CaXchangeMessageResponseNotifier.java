/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.esb;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 26, 2007
 * Time: 10:27:14 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CaXchangeMessageResponseNotifier {

    public void addResponseHandler(CaXchangeMessageResponseHandler handler);
}
