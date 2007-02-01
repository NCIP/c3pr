<%
TimeZone tz1 = TimeZone.getTimeZone(timezone1);
DateFormat df1 = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT, locale);
df1.setTimeZone(tz1);

TimeZone tz2 = TimeZone.getTimeZone(timezone2);
DateFormat df2 = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT, locale);
df2.setTimeZone(tz2);

%>
<BR>
<table>
<TR valign=top>
<td COLSPAN=3>
<%=df1.format(new Date())%> in  <%=tz1.getID()%> 
<BR>
is 
<BR>
<%=df2.format(new Date())%> in <%=tz2.getID()%>
</TD>
</tr>
</TABLE>

