<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<tabs:division id="Summary" title="Summary">
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		id="table1">
		<tr>
			<td valign="top" width="35%" align="right"><b>Registration ID:&nbsp;</b></td>
			<td valign="bottom" align="left">${studyParticipantAssignments.id }</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Short Title:&nbsp;</b></td>
			<td valign="bottom" align="left">${studyParticipantAssignments.studySite.study.shortTitleText
			}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Status:&nbsp;</b></td>
			<td valign="bottom" align="left">${studyParticipantAssignments.studySite.study.status
			}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Disease Code:&nbsp;</b></td>
			<td valign="bottom" align="left">${studyParticipantAssignments.studySite.study.diseaseCode
			}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Phase Code:&nbsp;</b></td>
			<td valign="bottom" align="left">${studyParticipantAssignments.studySite.study.phaseCode
			}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Sponsor Code:&nbsp;</b></td>
			<td valign="bottom" align="left">${studyParticipantAssignments.studySite.study.sponsorCode
			}</td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
	</table>
</tabs:division>
