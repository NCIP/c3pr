<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<tabs:division id="Summary" title="Current Registration">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		id="table1">
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Registration Identifier:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studyParticipantAssignments[0].primaryIdentifier}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Short Title:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studyParticipantAssignments[0].studySite.study.trimmedShortTitleText
			}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Study Identifier:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studyParticipantAssignments[0].studySite.study.primaryIdentifier
			}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Site:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studyParticipantAssignments[0].studySite.site.name
			}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Status:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studyParticipantAssignments[0].studySite.study.status
			}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Phase Code:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studyParticipantAssignments[0].studySite.study.phaseCode
			}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Registration Status:&nbsp;</b></td>
			<td valign="bottom" align="left">Registered</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Treating Physician:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studyParticipantAssignments[0].treatingPhysician}
			</td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
		
	</table>
	
</tabs:division>
