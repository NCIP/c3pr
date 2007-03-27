<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<tabs:division id="Summary" title="Current Registration">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		id="table1">
		<tr align="center" class="label">
			<a
				href="../registration/registrationDetails?registrationId=${command.studyParticipantAssignments[0].id}"><img
				src="<tags:imageUrl name="viewRegistrationHistory.gif"/>"
				alt="View Registration Information" width="140" height="16"
				border="0" align="right"></a>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Registration ID:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studyParticipantAssignments[0].id }</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Short Title:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studyParticipantAssignments[0].studySite.study.shortTitleText
			}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Status:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studyParticipantAssignments[0].studySite.study.status
			}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Disease Code:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studyParticipantAssignments[0].studySite.study.diseaseCode
			}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Phase Code:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studyParticipantAssignments[0].studySite.study.phaseCode
			}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Sponsor Code:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studyParticipantAssignments[0].studySite.study.sponsorCode
			}</td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
	</table>
</tabs:division>
