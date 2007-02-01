<%@ page import="javax.portlet.*, java.util.*" session="false" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %> 

<jsp:useBean id="addUrl" scope="request" class="java.lang.String" />
<jsp:useBean id="cancelUrl" scope="request" class="java.lang.String" />
<portlet:defineObjects/>

<%
ResourceBundle myText = ResourceBundle.getBundle("nls.Text", request.getLocale());
%>
<fmt:setBundle basename="nls.Text"/>

<h4><fmt:message key="available_bookmarks"/></h4>

<table class="wpsTable">
  <tr class="wpsTableRow">
    <th class="wpsTableHead"><fmt:message key="name"/></th>
    <th class="wpsTableHead"><fmt:message key="url"/></th>
    <td></td>
  </tr>

<%
  PortletPreferences prefs = renderRequest.getPreferences();
  Enumeration e = prefs.getNames();
   if (!e.hasMoreElements())   { // no bookmarks 
%>    
<p><fmt:message key="no_bookmarks"/></p>
<% }  
   else{
  while (e.hasMoreElements()) {
    String name = (String)e.nextElement();
    String value = prefs.getValue(name,"<"+myText.getString("undefined")+">");
%>
  <tr class="wpsTableRow">
    <td> <%=name%>  </td>
    <td> <%=value%> </td>
    <td> <portlet:actionURL var="removeUrl">
             <portlet:param name="remove" value="<%=name%>"/>
         </portlet:actionURL>
        <a href="<%=removeUrl.toString()%>">(<fmt:message key="delete"/>)</a>
    </td>
  </tr>
<%
  }
}
%>

<form action="<%=addUrl%>" method="POST">
  <tr class="wpsTableRow">
    <td><input name="name"  type="text"   class="portlet-form-input-field"></td>
    <td><input name="value" type="text"   class="portlet-form-input-field"></td>
    <td><input name="add"   type="submit" value='<fmt:message key="add"/>' class="portlet-form-button"></td>
  </tr>
</form>
</table>

<form action="<%=cancelUrl%>" method="post">
  <input name="cancel"  type="submit" value="<fmt:message key="cancel"/>" class="portlet-form-button">
</form>



