<FORM NAME=TZ_DIFF METHOD="POST" ACTION="<%=rURL.toString()%>">
<INPUT TYPE=HIDDEN NAME="TIMEZONE_DIFF" VALUE="1"/>
<TABLE>
<TR VALIGN="TOP"><TD COLSPAN=3 NOWRAP>
<B>Compare time in two TimeZones:</B>
</TD>
</TR>
<TR VALIGN="TOP">
<TD ALIGN="RIGHT" NOWRAP>
<SELECT NAME="TIMEZONE1">
<%
String selectedTZ1 = "";
String selectedTZ2 = "";
for (int i = 0; i < ids.length; i++) {
        TimeZone tz1 = TimeZone.getTimeZone(ids[i]);
        if (ids[i].equals(timezone1)) {
          selectedTZ1 = "SELECTED";
        } else {
          selectedTZ1 = "";
        }
%>
<OPTION <%=selectedTZ1%> VALUE="<%=ids[i]%>">
<%=tz1.getID()%>
</OPTION>
<%
}
%>
</SELECT></TD>
<TD ALIGN="RIGHT" NOWRAP>
<SELECT NAME="TIMEZONE2">
<%
for (int i = 0; i < ids.length; i++) {
        TimeZone tz2 = TimeZone.getTimeZone(ids[i]);
        if (ids[i].equals(timezone2)) {
          selectedTZ2 = "SELECTED";
        } else {
          selectedTZ2 = "";
        }
%>
<OPTION <%=selectedTZ2%> VALUE="<%=ids[i]%>">
<%=tz2.getID()%>
</OPTION>
<%
}
%>
</SELECT>
</TD>
<TD ALIGN="LEFT">
<INPUT TYPE="SUBMIT" NAME="Submit" VALUE="Compare" >
</TD>
</TR>
</TABLE>
</FORM>

