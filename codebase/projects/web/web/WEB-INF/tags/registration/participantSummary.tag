<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<tabs:division id="Summary" title="Subject Summary">

<table width="100%" height="100%" border="0" cellspacing="2"
		cellpadding="0" id="table1">

		<tr>
			<td valign="top" width="35%" align="right"><b>First Name:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.participant.firstName}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Last Name:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.participant.lastName}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Gender:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.participant.administrativeGenderCode}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Ethnicity:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.participant.ethnicGroupCode}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Races(s):&nbsp;</b></td>
			<td valign="bottom" align="left">${command.participant.raceCode}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Primary Identifier:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.participant.primaryIdentifier }</td>
		</tr>
		<c:forEach begin="1" end="22">
			<tr>
				<td>&nbsp;</td>
			</tr>
		</c:forEach>

	</table>
</tabs:division>