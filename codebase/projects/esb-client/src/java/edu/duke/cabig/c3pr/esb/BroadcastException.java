/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.esb;

/**
 * Will be thrown when message cannot
 * be broadcasted to the ESB
 * <p/>
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Dec 11, 2006
 * Time: 10:52:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class BroadcastException extends java.lang.Exception {


    public BroadcastException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BroadcastException(String string) {
        super(string);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BroadcastException(String string, Throwable throwable) {
        super(string, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BroadcastException(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
