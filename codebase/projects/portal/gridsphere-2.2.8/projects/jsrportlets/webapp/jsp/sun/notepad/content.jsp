<%--
  Copyright 2003 Sun Microsystems, Inc.  All rights reserved.
  PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
--%>
<%@ page session="false" %>

<%@ page import="javax.portlet.RenderRequest" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="javax.portlet.RenderResponse" %>
<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="javax.portlet.PortletMode" %>
<%@ page import="javax.portlet.WindowState" %>

<%@ page import="java.util.Enumeration" %>
<%@ page import="java.net.URLEncoder" %>

<%@ page import="com.sun.portal.portlet.samples.notepad.NotepadManager" %>
<%@ page import="com.sun.portal.portlet.samples.notepad.NotepadPortlet" %>

<%
RenderRequest pReq = (RenderRequest)request.getAttribute("javax.portlet.request");
RenderResponse rRes = (RenderResponse)request.getAttribute("javax.portlet.response");
PortletPreferences pref = pReq.getPreferences();
String mode = pReq.getParameter( NotepadPortlet.MODE );
String category = pReq.getParameter( NotepadPortlet.CATEGORY );
String strPage = pReq.getParameter( NotepadPortlet.PAGE_NO );
int pageNo = 0;
if( strPage!=null && strPage.length()>0 ) {
  try {
    pageNo = Integer.parseInt( strPage );
  } catch( Exception e ) {
  }
}
NotepadManager mgr = new NotepadManager( pref, category, pageNo );
pageNo = mgr.getPage();
category = mgr.getCategory();
String[] categories = mgr.getAllCategories();

PortletURL actionURL = rRes.createActionURL();
%>

<%@ include file="error.jsp" %>

<% if( categories != null && categories.length>0 ) { %>

    <FORM NAME=NOTEPAD METHOD="POST" ACTION="<%=actionURL.toString()%>">

        <% if( mode !=null && (mode.equals( "Add" ) || mode.equals( "Edit" )) ) { %>
            <%@ include file="editNote.jsp" %>
        <% } else { %>
            <%@ include file="viewNotes.jsp" %>
        <% } %>

        <input type=hidden name=<%=NotepadPortlet.CATEGORY%> value="<%=category%>">
        <input type=hidden name=<%=NotepadPortlet.PAGE_NO%> value="<%=pageNo%>">
    </FORM>

<% } else { %>
The notepad is empty.  To begin creating note, go the

<% PortletURL editURL = rRes.createRenderURL();
   editURL.setPortletMode( PortletMode.EDIT );
%>

<a href=<%=editURL.toString()%>>Edit</a>
page and add a new note category.
<% } %>
