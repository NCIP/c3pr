<%@ page import="javax.portlet.RenderRequest" %>
<%@ page import="javax.portlet.RenderResponse" %>
<%@ taglib uri="/portlet" prefix="portlet" %>
<%@ page session="false" %>

<portlet:defineObjects/>

<%
        String error = renderRequest.getParameter("error");
        if (error!=null) {
%>
        <font color="red"><b>ERROR: </b><%=error%>
        </font>
<%
        }
%>

<form method="post" action="<portlet:actionURL/>">
<table border=0 cellspacing=0 cellpadding=2 width="100%">
  <tr>                
    <td bgcolor="#666699"><font face="sans-serif" color="#FFFFFF" size="+1"><b>Edit Weather Preferences</b></font></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>

<center>
<table border=0 cellspacing=1 cellpadding=0 width="40%" align="center">
  <tr>
    <td align="right" nowrap><font face="sans-serif" size=+0><b><LABEL FOR="zip">ZIP: </LABEL></b></font></td>
    <td><font face="sans-serif" size=+0><input type="text" name="zip" value="<%=renderRequest.getAttribute("zip")%>" ID="zip"></font></td>
  </tr>
  <tr>
    <td align="right" nowrap><font face="sans-serif" size=+0><b><LABEL FOR="unit">UNIT: </LABEL></b></font></td>
    <td><font face="sans-serif" size=+0><input type="text" name="unit" value="<%=renderRequest.getAttribute("unit")%>" ID="unit"></font></td>
  </tr>
</table>
</center>
<br>

<center>
    <input type="submit" name="Submit" onClick="finish.value='true';" value="Finished"> 
    <input type="reset" onClick="reset.value='true';"  value="Reset">
<br>
<p>

</form>

