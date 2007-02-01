package com.ibm.wps.samples.jsr;

import java.io.*;
import javax.portlet.*;


public class Bookmark_03 extends GenericPortlet {
	
  private PortletContext context;

  public void init(PortletConfig config) throws PortletException{
        super.init(config);
        context = config.getPortletContext();  	
  }

  public void doView(RenderRequest request, RenderResponse response)
       throws PortletException, IOException{
  
    response.setContentType("text/html");
    String jspName = getPortletConfig().getInitParameter("jspView");
    PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher(jspName);
    rd.include(request,response);
    
  }
  
  public void doEdit(RenderRequest request, RenderResponse response)
                      throws PortletException, IOException{

    response.setContentType("text/html");
    PortletURL addUrl = response.createActionURL();
    addUrl.setPortletMode(PortletMode.VIEW);
    addUrl.setParameter("add","add");
    request.setAttribute("addUrl",addUrl.toString());

    PortletURL cancelUrl = response.createRenderURL();
    cancelUrl.setPortletMode(PortletMode.VIEW);
    request.setAttribute("cancelUrl",cancelUrl.toString());

    String jspName = getPortletConfig().getInitParameter("jspEdit");
    PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher(jspName);
    rd.include(request,response);

  }

  public void processAction(ActionRequest request, ActionResponse response)
       throws PortletException, IOException{
       	
    try{
      String removeName = request.getParameter("remove");
      if (removeName != null){
        PortletPreferences prefs = request.getPreferences();
        prefs.reset(removeName);
        prefs.store();
      }
    }
    catch ( IOException ioe ) {
       context.log("An IO error occured when trying to remove a bookmark.");
    }
    catch ( PortletException pe ) {
       context.log("A portlet exception was thrown when trying to remove a bookmark.");
    }
    
    try{
      String add = request.getParameter("add");
      if (add != null){
        PortletPreferences prefs = request.getPreferences();
        prefs.setValue(request.getParameter("name"),request.getParameter("value"));
        prefs.store();
      }
    }
    catch ( IOException ioe ){
       context.log("An IO error occured when trying to add a bookmark.");    
    }
    catch ( PortletException pe ) {
       context.log("A portlet exception was thrown when trying to add a bookmark.");    
    }    
  }

}
