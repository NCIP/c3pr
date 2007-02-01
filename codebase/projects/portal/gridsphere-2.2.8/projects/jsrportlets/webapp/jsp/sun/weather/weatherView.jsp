<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<%@ page session="false" %>

<jsp:useBean id="weather" scope="request"
class="com.sun.portal.portlet.samples.weather.Weather" />

<b>Weather Information</b>
<br><p>
<table>
<tr valign=top>
<td><b>Zip Code: </b></td>
<td colspan="2"><jsp:getProperty name="weather" property="zip"/></td>
</tr>

<tr><td>&nbsp;</td></tr>

<tr valign=top><td><b>Current Time:</b></td>
<td colspan="2"><jsp:getProperty name="weather" property="time"/></td>
</tr>

<tr><td>&nbsp;</td></tr>

<tr valign=top>
<td><b>Temperature unit:</b></td>
<td colspan="2"><jsp:getProperty name="weather" property="unit"/></td>
</tr>

<tr><td>&nbsp;</td></tr>

<tr valign=top>
<td><b>Current Temperature:</b></td>
<td colspan="2"><jsp:getProperty name="weather" property="currentTemp"/></td>
</tr>

<tr><td>&nbsp;</td></tr>

<tr  valign=top>
<form method="post" action="<portlet:actionURL/>">
  <td><b><LABEL FOR="zipCode">Enter Zip Code:</LABEL></b></td>
  <td><input type="text" name="zip" value="" ID="zipCode"></td>
  <td><input type="submit" name="submit" value="Search"></td>
</tr>
</form>
</table>

