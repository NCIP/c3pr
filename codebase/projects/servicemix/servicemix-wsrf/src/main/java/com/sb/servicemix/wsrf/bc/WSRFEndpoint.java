/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sb.servicemix.wsrf.bc;

import org.apache.servicemix.common.BaseLifeCycle;
import org.apache.servicemix.common.Endpoint;
import org.apache.servicemix.common.ExchangeProcessor;

import javax.jbi.component.ComponentContext;
import javax.jbi.management.DeploymentException;
import javax.jbi.messaging.*;
import javax.jbi.messaging.MessageExchange.Role;
import javax.jbi.servicedesc.ServiceEndpoint;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;


/**
 * @org.apache.xbean.XBean element="endpoint"
 */
public class WSRFEndpoint extends Endpoint implements ExchangeProcessor {

    private ServiceEndpoint activated;
    private DeliveryChannel channel;
    private MessageExchangeFactory exchangeFactory;


    // custom tags for the endpoint
    private String epr;
    private MessageExchangeProcessor exchangeProcessor;


    /* (non-Javadoc)
    * @see org.apache.servicemix.common.Endpoint#getRole()
    */
    public Role getRole() {
        return Role.PROVIDER;
    }

    public void activate() throws Exception {
        logger = this.serviceUnit.getComponent().getLogger();
        ComponentContext ctx = getServiceUnit().getComponent().getComponentContext();
        channel = ctx.getDeliveryChannel();
        exchangeFactory = channel.createExchangeFactory();
        activated = ctx.activateEndpoint(service, endpoint);
        start();
    }

    public void deactivate() throws Exception {
        stop();
        ServiceEndpoint ep = activated;
        activated = null;
        ComponentContext ctx = getServiceUnit().getComponent().getComponentContext();
        ctx.deactivateEndpoint(ep);
    }

    public ExchangeProcessor getProcessor() {
        return this;
    }

    public void validate() throws DeploymentException {
    }

    protected void send(MessageExchange me) throws MessagingException {
        if (me.getRole() == MessageExchange.Role.CONSUMER &&
                me.getStatus() == ExchangeStatus.ACTIVE) {
            BaseLifeCycle lf = (BaseLifeCycle) getServiceUnit().getComponent().getLifeCycle();
            lf.sendConsumerExchange(me, (Endpoint) this);
        } else {
            channel.send(me);
        }
    }

    protected void done(MessageExchange me) throws MessagingException {
        me.setStatus(ExchangeStatus.DONE);
        send(me);
    }

    protected void fail(MessageExchange me, Exception error) throws MessagingException {
        me.setError(error);
        send(me);
    }

    public void start() throws Exception {
    }

    public void stop() {
    }

    public void process(MessageExchange exchange) throws Exception {
        // The component acts as a provider, this means that another component has requested our service
        // As this exchange is active, this is either an in or a fault (out are send by this component)
        if (exchange.getRole() == MessageExchange.Role.PROVIDER) {
        	System.out.print("Role as Provider...");
            // Check here if the mep is supported by this component
            if (exchange instanceof InOut == false) {
                throw new UnsupportedOperationException("Unsupported MEP: " + exchange.getPattern());
            }
            // In message
            if (exchange.getMessage("in") != null) {

                //hand over exchange to an exchange processor
                if (exchangeProcessor == null){
                    throw new Exception("No valid ExchangeProcessor available");
                }

                try {
                    exchangeProcessor.process(exchange,channel,epr);
                } catch (Exception e) {
                    //do nothing as a failure message is already sent back
                }
//                exchange.setStatus(ExchangeStatus.DONE);
                System.out.println("Calling exchangeProcessor() over. Sending the exchange in channel with out message as +"+exchange.getMessage("out")+"...");
                System.out.println("Status is :"+ exchange.getStatus().toString());
                channel.send(exchange);

                //
                // Fault message
            } else if (exchange.getFault() != null) {
                //handle fault
                exchange.setStatus(ExchangeStatus.DONE);
                System.out.print("Fault Detected.....");
                channel.send(exchange);
                // This is not compliant with the default MEPs
            } else {
                throw new IllegalStateException("Provider exchange is ACTIVE, but no in or fault is provided");
            }
            // The component acts as a consumer, this means this exchange is received because
            // we sent it to another component.  As it is active, this is either an out or a fault
            // If this component does not create / send exchanges, you may just throw an UnsupportedOperationException
        } else if (exchange.getRole() == MessageExchange.Role.CONSUMER) {
            // Exchange is finished
        	System.out.print("Role as Cosumer...");
            if (exchange.getStatus() == ExchangeStatus.DONE) {
                return;
                // Exchange has been aborted with an exception
            } else if (exchange.getStatus() == ExchangeStatus.ERROR) {
                return;
                // Exchange is active
            } else {
                // Out message
                if (exchange.getMessage("out") != null) {
                    // TODO ... handle the response
                    exchange.setStatus(ExchangeStatus.DONE);
                    channel.send(exchange);
                    // Fault message
                } else if (exchange.getFault() != null) {
                    // TODO ... handle the fault
                    exchange.setStatus(ExchangeStatus.DONE);
                    channel.send(exchange);
                    // This is not compliant with the default MEPs
                } else {
                    throw new IllegalStateException("Consumer exchange is ACTIVE, but no out or fault is provided");
                }
            }
            // Unknown role
        } else {
            throw new IllegalStateException("Unkown role: " + exchange.getRole());
        }
    }

    protected static Source createSource(String msg) {
        return new StreamSource(new ByteArrayInputStream(msg.getBytes()));
    }


    public MessageExchangeProcessor getExchangeProcessor() {
        return exchangeProcessor;
    }

    public void setExchangeProcessor(MessageExchangeProcessor exchangeProcessor) {
        this.exchangeProcessor = exchangeProcessor;
    }

    public String getEpr() {
        return epr;
    }

    public void setEpr(String epr) {
        this.epr = epr;
    }


}
