/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 * OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */


package com.sun.portal.portlet.samples.weather;

import java.util.Date;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.ServiceFactory;
import javax.xml.rpc.Service;
import javax.xml.rpc.Call;
import javax.xml.rpc.ParameterMode;
import javax.xml.namespace.QName;
import java.io.PrintWriter;

/**
 * This class generates the JAXRPC invocation call for the weather service
 */
public class WeatherService {

    private static String ENCODING_STYLE_PROPERTY =
        "javax.xml.rpc.encodingstyle.namespace.uri";
    private static String NS_XSD =
        "http://www.w3.org/2001/XMLSchema";
    private static String URI_ENCODING =
         "http://schemas.xmlsoap.org/soap/encoding/";
    private static String NAMESPACE_URI = "urn:xmethods-Temperature";
    private static String WEATHER_SERVICE = "TemperatureService";
    private static String WEATHER_PORT = "TemperaturePort";

    private Service _service;
    private QName _port;
    private String _endPointAddr;
    private QName _qnameTypeFloat;
    private QName _qnameNamespaceURI;
    private QName _qnameNS_XSD;
    private Float _defTemp;

    public WeatherService(String endPointAddr) throws
    WeatherServiceException {
        try {
            ServiceFactory factory = ServiceFactory.newInstance();
            _service = factory.createService(new QName(WEATHER_SERVICE));
            _port = new QName(WEATHER_PORT);
        } catch (ServiceException se) {
            se.printStackTrace();
            throw new WeatherServiceException("Can not create weather service: " +  se.getMessage());
        }

        _endPointAddr = endPointAddr;
        _qnameTypeFloat = new QName(NS_XSD, "float");
        _qnameNamespaceURI = new QName(NAMESPACE_URI, "getTemp");
        _qnameNS_XSD = new QName(NS_XSD, "string");
        _defTemp = new Float(-999);
    }

    public Weather getWeather(String zip,char unit)
    throws WeatherServiceException {
        Object result = null;
        String[] params = {zip};
        
	try {
            Call call = _service.createCall(_port);
            call.setTargetEndpointAddress(_endPointAddr);
            call.setProperty(Call.SOAPACTION_USE_PROPERTY,
                 Boolean.TRUE);
            call.setProperty(Call.SOAPACTION_URI_PROPERTY, "");
            call.setProperty(ENCODING_STYLE_PROPERTY, URI_ENCODING);
            call.setOperationName(_qnameNamespaceURI);
            call.addParameter("String_1", _qnameNS_XSD, ParameterMode.IN);
            
            call.setReturnType(_qnameTypeFloat);
            result = call.invoke((Object[])params);
	} catch (Exception ex) {
            throw new WeatherServiceException("Can not get temperature: " +  ex.getMessage());
            
	}

        Float temp = _defTemp;
        if (result != null) {
            temp = new Float(result.toString());
        }
        
        return new Weather(zip, unit,new Date(),temp.intValue());
    }

    public void destroy() {
        _service = null;
        _port = null;
        _endPointAddr = null;
        _qnameTypeFloat = null;
        _qnameNamespaceURI = null;
        _qnameNS_XSD = null;
        _defTemp = null;
    }
    
}
