<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<tabs:division id="Summary" title="Registrations">
<table width="80%" border="0" cellspacing="0" cellpadding="0" id="table1">
	<tr align="center" class="label">
		<td width="20%" align="left"><b>Primary Id</b></td>	
		<td width="20%" align="left"><b>Study Site</b></td>
		<td width="20%" align="left"><b>Registration Date</b></td>	
		<td width="20%" align="left"><b>Informed Consent Signed Date</b></td>		
		<td width="20%" align="left"><b>Subject</b></td>
	</tr>
	<c:forEach items="${participantAssignments}" var="partAssgn">
		<tr align="center" class="results">
		<td>${partAssgn.participant.primaryIdentifier}</td>			
		<td>${partAssgn.studySite}</td>
		<td>${partAssgn.participant.startDate}</td>
		<td>${partAssgn.participant.informedConsentSignedDate}</td>
		<td>${partAssgn.participant.lastName}</td>
		</tr>
	</c:forEach>
	<tr>
		<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
		class="heightControl"></td>
	</tr>
</table>
</tabs:division>