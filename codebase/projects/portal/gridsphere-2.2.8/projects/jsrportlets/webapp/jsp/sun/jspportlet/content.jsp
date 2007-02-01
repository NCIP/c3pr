<%--
  Copyright 2003 Sun Microsystems, Inc.  All rights reserved.
  PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
--%>

<%@ page import="javax.portlet.RenderRequest" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="javax.portlet.RenderResponse" %>
<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="javax.portlet.PortletMode" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Arrays" %>

<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<%@ page session="false" %>

<portlet:defineObjects/>

<%
Map uiMap = (Map)renderRequest.getAttribute("javax.portlet.userinfo");
Locale locale = renderRequest.getLocale();
if (locale == null) {
   locale = Locale.getDefault();
}
String timezone = renderRequest.getParameter("TIMEZONE");
PortletPreferences pref = renderRequest.getPreferences();
if (timezone != null && timezone.length() != 0) {
   pref.setValue("timezone", timezone);
   pref.store();
} else {
   timezone = pref.getValue("timezone", "");
   if (timezone == null || timezone.length() == 0) { 
      if ( uiMap != null && uiMap.containsKey("timezone")) { 
         timezone = (String)uiMap.get("timezone");
      } else {
         timezone = TimeZone.getDefault().getID();
      }
   }
}
TimeZone tz = TimeZone.getTimeZone(timezone);
DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT, locale);
df.setTimeZone(tz);

String [] ids = TimeZone.getAvailableIDs();
Arrays.sort(ids);

%>
<portlet:renderURL var="rURL">
</portlet:renderURL>

<table>
<TR valign=top>
<td><B> 
TimeZone: 
</B> 
</td>
<TD><%=tz.getID()%>(<%=tz.getDisplayName(false, TimeZone.LONG, locale)%>)</TD>
<TD>&nbsp;</TD>
</tr>
<TR valign=top>
<TD><B>
Current Time:
</B>
</TD>
<TD NOWRAP>
<%=df.format(new Date())%>
</TD>
<TD>&nbsp;</TD>
</TR>
</TABLE>
<%
String timezone1 = renderRequest.getParameter("TIMEZONE1");
String timezone2 = renderRequest.getParameter("TIMEZONE2");

if (timezone1 == null || timezone1.length() == 0) {
   timezone1 = timezone;
} 

if (timezone2 == null || timezone2.length() == 0) {
   timezone2 = timezone;
}
%>

<%@ include file="timezoneComparison.jsp" %>
<%
String time_diff = renderRequest.getParameter("TIMEZONE_DIFF");
if (time_diff != null && time_diff.equals("1")) {
%>
<%@ include file="results.jsp" %>
<%
}
%>
