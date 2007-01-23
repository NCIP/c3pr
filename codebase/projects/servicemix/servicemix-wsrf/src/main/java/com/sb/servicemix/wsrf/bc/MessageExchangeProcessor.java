package com.sb.servicemix.wsrf.bc;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.DeliveryChannel;

/**
 * Message exchange process interface.
 * 
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jan 22, 2007
 * Time: 12:19:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MessageExchangeProcessor {

    void process(MessageExchange exchange, DeliveryChannel channel, String epr) throws Exception;
}
