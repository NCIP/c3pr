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

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletPreferences;
import javax.portlet.UnavailableException;
import javax.portlet.PortletMode;
import javax.portlet.ValidatorException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import com.sun.portal.portlet.samples.jspportlet.JSPPortlet;

/**
 * This portlet uses the JAXRPC API to get to a RPC based weather web service.
 * The weather web service gets a zip code as input, and returns the
 * current temperature as output.
 */
public class WeatherPortlet extends JSPPortlet {

    private WeatherService _weatherService;
    
    public void init(PortletConfig config) throws PortletException, UnavailableException {
        String weatherWSURLString;

        super.init(config);

        weatherWSURLString = config.getInitParameter("weather.url");
        if (weatherWSURLString == null) {
            throw new UnavailableException("Weather Service URL not found",-1);
        }
        try {
            _weatherService = new WeatherService(weatherWSURLString);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new UnavailableException("Weather Service can not be initialized");
        }
    }

    public void processAction(ActionRequest aReq,ActionResponse aRes) throws PortletException, IOException {
        PortletPreferences prefs = aReq.getPreferences();
        String zip = aReq.getParameter("zip");
        
        if (aReq.getPortletMode().equals(PortletMode.VIEW)) {
            if (zip==null) {
                zip = prefs.getValue("zip","10000");
            }         
            aRes.setRenderParameter("zip",zip); 
        }
        else
        if (aReq.getPortletMode().equals(PortletMode.EDIT)) {            
            boolean editOK;
            String errorMsg = null;            
            String unit = aReq.getParameter("unit");            
            prefs.setValue("zip",zip);
            prefs.setValue("unit",unit);
            try {
                prefs.store();
                editOK = true;
            }
            catch (ValidatorException ex) {
                editOK = false;
                errorMsg = ex.getMessage();
            }                    
            if (editOK) {
                aRes.setPortletMode(PortletMode.VIEW);
            }
            else {
                aRes.setRenderParameter("error",errorMsg);
            }
        }
               
    }

    public void doView(RenderRequest rReq,RenderResponse rRes) throws PortletException, IOException {
        PortletPreferences prefs = rReq.getPreferences();
        String zip = rReq.getParameter("zip");
        if (zip==null) {             
           zip = prefs.getValue("zip","10000");
        }
        String unit = prefs.getValue("unit","F");
        try {
            Weather weather = _weatherService.getWeather(zip,unit.toUpperCase().charAt(0));
            rReq.setAttribute("weather",weather);
            rRes.setContentType(rReq.getResponseContentType());
            super.doView(rReq, rRes);
        }
        catch (Exception ex) {
            rRes.setProperty("expiration-cache","0");
            PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher("/jsp/weather/weatherServiceUnavailable.html");
            rd.include(rReq,rRes);
        }
    }

    public void doEdit(RenderRequest rReq,RenderResponse rRes) throws
    PortletException {

        PortletPreferences prefs = rReq.getPreferences();        
        String zip = prefs.getValue("zip","1000");
        String unit = prefs.getValue("unit","F");
        rReq.setAttribute("zip",zip);
        rReq.setAttribute("unit",unit);

        super.doEdit(rReq, rRes);
        
    }

    public void destroy() {
        if (_weatherService != null) _weatherService.destroy();
        _weatherService = null;
    }
    
}
