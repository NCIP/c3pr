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
