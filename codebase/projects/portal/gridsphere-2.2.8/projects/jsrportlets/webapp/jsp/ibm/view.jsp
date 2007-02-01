<%@ page import="javax.portlet.*, java.util.*" session="false"%>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portletAPI" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %> 

<portletAPI:defineObjects />
<fmt:setBundle basename="nls.Text"/>

<%
ResourceBundle myText = ResourceBundle.getBundle("nls.Text", request.getLocale());
%>

<h4><fmt:message key="available_bookmarks"/></h4>

<%
   PortletPreferences prefs = renderRequest.getPreferences();
   Enumeration e = prefs.getNames();
   if (!e.hasMoreElements())   { // no bookmarks 
%>    
<p><fmt:message key="no_bookmarks"/></p>
<% }  
   else{
%>  
<p>
<% }   
   while (e.hasMoreElements()) {
      String name = (String)e.nextElement();
      String value = prefs.getValue(name,"<"+myText.getString("undefined")+">");
%>
<a href=<%=value%> target="_blank"><%=name%></a><br />
<%
   }
%>
</p>
