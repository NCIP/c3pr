package edu.duke.cabig.c3pr.utils.web.filter;

import gov.nih.nci.cabig.ctms.web.filters.ContextRetainingFilterAdapter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class XSSFilter extends ContextRetainingFilterAdapter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    	throws IOException, ServletException {

       	chain.doFilter(new RequestWrapper((HttpServletRequest) request), response);

    }
    
    class RequestWrapper extends HttpServletRequestWrapper {

    	private HTMLInputCleaner cleaner = new HTMLInputCleaner();
    	
    	public RequestWrapper(HttpServletRequest servletRequest) {
    		super(servletRequest);
    	}

    	public String[] getParameterValues(String parameter) {

    	  String[] values = super.getParameterValues(parameter);
    	  if (values==null)  {
                      return null;
              }
    	  int count = values.length;
    	  String[] encodedValues = new String[count];
    	  for (int i = 0; i < count; i++) {
                     encodedValues[i] = cleaner.filter(values[i]);
    	   }
    	  return encodedValues;
    	}

    	public String getParameter(String parameter) {
    		  String value = super.getParameter(parameter);
    		  if (value == null) {
    		         return null;
                      }
    		  return cleaner.filter(value);
    	}

    	public String getHeader(String name) {
    	    String value = super.getHeader(name);
    	    if (value == null)
    	        return null;
    	    return cleaner.filter(value);

    	}

    }
}


