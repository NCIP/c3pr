<%--
  Copyright 2003 Sun Microsystems, Inc.  All rights reserved.
  PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
--%>
<%@ page import="javax.portlet.RenderRequest" %>
<%@ page import="javax.portlet.RenderResponse" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Arrays" %>

<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<%@ page session="false" %>

<portlet:defineObjects/>


<%
Locale locale = renderRequest.getLocale();
if (locale == null) {
   locale = Locale.getDefault();
}
PortletPreferences pref = renderRequest.getPreferences();
String userTZ = pref.getValue("timezone", "");
if (userTZ == null || userTZ.length() == 0) { 
   Map uiMap = (Map)renderRequest.getAttribute("javax.portlet.userinfo");
   if ( uiMap != null && uiMap.containsKey("timezone")) { 
      userTZ = (String)uiMap.get("timezone");
   } else {
      userTZ = TimeZone.getDefault().getID();
   }
}

String [] ids = TimeZone.getAvailableIDs();
Arrays.sort(ids);
String selectedTimeZone = ""; 
%>

<portlet:actionURL var="aURL" portletMode="VIEW">
</portlet:actionURL>

<FORM METHOD="POST" ACTION="<%=aURL.toString()%>">
<TABLE WIDTH="100%">
  <tr>           
    <td bgcolor="#333366">
      <font face="Sans-serif" color="#FFFFFF"><b>Edit TimeZone Information</b></font>
    </td>
    <td bgcolor="#333366">&nbsp;</TD>
  </tr>
 <TR><TD>&nbsp;</TD></TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP>
<font face="Sans-serif" color="#000000"><b>Timezone:</b></font>
</TD>
<TD >
<SELECT NAME="TIMEZONE">
<%
for (int i = 0; i < ids.length; i++) {
        TimeZone tz = TimeZone.getTimeZone(ids[i]);
        if (ids[i].equals(userTZ)) {
          selectedTimeZone = "SELECTED";
        } else {
          selectedTimeZone = "";
        }
%>
<OPTION <%=selectedTimeZone%> VALUE="<%=ids[i]%>">
<%=tz.getID()%>(<%=tz.getDisplayName(false, TimeZone.SHORT, locale)%>)[<%=tz.getDisplayName(false, TimeZone.LONG, locale)%>]
</OPTION>
<%
}
%>
</SELECT>
</TD>
</TR>
</TABLE>

<BR>
<CENTER>
<FONT FACE="sans-serif">
<b>Note:</b> 
Timezones marked with an * may vary <BR> 
from their standard GMT offset due to <BR>
daylight savings time adjustments. <BR><BR>

<INPUT TYPE="SUBMIT" NAME="Submit" VALUE="Finished">
</font>
</CENTER>
<br>
<P>



</FORM>
