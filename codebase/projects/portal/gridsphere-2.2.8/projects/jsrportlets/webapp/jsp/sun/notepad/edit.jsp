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

<%@ page import="com.sun.portal.portlet.samples.notepad.NotepadPortlet" %>
<%@ page import="com.sun.portal.portlet.samples.notepad.NoteCategoryManager" %>

<%
RenderRequest pReq = (RenderRequest)request.getAttribute("javax.portlet.request");
RenderResponse rRes = (RenderResponse)request.getAttribute("javax.portlet.response");
PortletPreferences pref = pReq.getPreferences();
NoteCategoryManager mgr = new NoteCategoryManager( pref );
String[] categories = mgr.getAllCategories();

PortletURL actionURL = rRes.createActionURL();
%>

<%@ include file="error.jsp" %>

<FORM METHOD="POST" ACTION="<%=actionURL.toString()%>">

<table width=100%>

<% if( categories != null && categories.length > 0 ) { %>
  <tr>                
    <td bgcolor="#666699" colspan="2"><font face="Sans-serif" color="#FFFFFF" size="+1"><b>Remove note categories:</b></font></td>
  </tr>

<% for( int i = 0; i < categories.length; i++ ) { %>
  <tr>
    <td align=right><input type=checkbox name=<%=NotepadPortlet.LOCATION%> value=<%= i %>/></td>
    <td><%=categories[i]%> (<%=mgr.getNoteCount(categories[i])%> note)</td>
  </tr>
<% } %>
  <tr>
    <td colspan=2 align=center>
      <INPUT TYPE="SUBMIT" NAME="<%=NotepadPortlet.DELETE_CATEGORY%>" VALUE="Delete Selected Category"/>
    </td>
  </tr>
  <tr>
    <td colspan=2 align=center><b>Note:</b> All the notes will be deleted with their corresponding categories.
  </tr>
    <td>&nbsp;</td>
  </tr>
<% } %>

  <tr>                
    <td bgcolor="#666699" colspan="2"><font face="Sans-serif" color="#FFFFFF" size="+1"><b>Add a new note category:</b></font></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>

  <tr>
    <td align=right><LABEL FOR="categoryName">Category Name:</LABEL></td>
    <td><input type=text name=<%=NotepadPortlet.CATEGORY_NAME%> size=30 ID="categoryName"/></td>
  </tr>
  <tr>
    <td colspan=2 align=center>
      <INPUT TYPE="SUBMIT" NAME="<%=NotepadPortlet.ADD_CATEGORY%>" VALUE="Add Category" />
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>                
    <td bgcolor="#666699" colspan="2"><font face="Sans-serif" color="#FFFFFF" size="+1"><b>Change portlet preferences:</b></font></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>

  <tr>
    <td align=right><LABEL FOR="maxNotes">Maximum no. of note per screen:</LABEL></td>
    <td><input type=text name=<%=NotepadPortlet.DISPLAY_MAX%> size=5 value=<%=mgr.getDisplayMax()%> ID="maxNotes"/></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan=2 align=center>
      <INPUT TYPE="SUBMIT" NAME="<%=NotepadPortlet.FINISHED_EDIT%>" VALUE="Finished" />
      &nbsp;&nbsp;
      <INPUT TYPE="SUBMIT" NAME="<%=NotepadPortlet.CANCEL_EDIT%>" VALUE="Cancel" />
    </td>
  </tr>

</table>

</FORM>
