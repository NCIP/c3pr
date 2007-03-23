<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<tabs:division id="Summary" title="Subjects Assigned">
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
	<tr align="center" class="label">
		<td width="35%" align="center">Last Name</td>
		<td width="35%" align="center">Primary Id</td>
	</tr>
	<c:forEach items="${participantAssignments}" var="partAssgn">
		<tr align="center" class="results">
		<td>${partAssgn.participant.lastName}</td>
		<td>${partAssgn.participant.primaryIdentifier}</td>
		</tr>
	</c:forEach>
	<tr>
		<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
		class="heightControl"></td>
	</tr>
</table>
</tabs:division>