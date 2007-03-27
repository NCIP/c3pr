<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<tabs:division id="Summary" title="Summary">

<table width="100%" border="0" cellspacing="2" cellpadding="0" id="table1">
<tr>
	<td><b>First Name:</b>&nbsp;${command.firstName}</td>
</tr>
<tr>
	<td><b>Last Name:</b>&nbsp;${command.lastName}</td>
</tr>
<tr>
	<td><b>Gender:</b>&nbsp;${command.administrativeGenderCode }</td>
</tr>
<tr>
	<td><b>Ethnicity:</b>&nbsp;${command.ethnicGroupCode }</td>
</tr>
<tr>
	<td><b>Races(s):</b>&nbsp;${command.raceCode }</td>
</tr>
<tr>
	<td><b>Primary Identifier:</b>&nbsp;${command.primaryIdentifier }</td>
</tr>

</table>
</tabs:division>