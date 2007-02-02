<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<link href="resources/styles.css" rel="stylesheet" type="text/css">
<link href="resources/search.css" rel="stylesheet" type="text/css">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
</head>
<body>
<portlet:defineObjects />

<ui:form>

	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		bgcolor="white">
		<tr>

			<!-- LEFT CONTENT STARTS HERE -->

			<td valign="top" class="additionals"><!-- LEFT FORM STARTS HERE -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="additionalList">
				<tr align="center" class="label">
					<td>Last Name, First Name</td>
					<td>Primary Identifier</td>
					<td>Gender</td>
					<td>Race</td>
					<td>Birth Date</td>
					<td></td>
				</tr>
				<%int i=0; %>
				<c:forEach var="participant" items="${participants}">
					<a href="editparticipant.do?participantId=${participant.id}"
						onMouseOver="navRollOver('row<%= i %>', 'on')"
						onMouseOut="navRollOver('row<%= i %>', 'off')">
					<tr align="center" id="row<%= i++ %>" class="results">
						<td>${participant.lastName},${participant.firstName}</td>
						<td>${participant.id}</td>
						<td>${participant.administrativeGenderCode}</td>
						<td>${participant.raceCode}</td>
						<td>${participant.birthDate}</td>
					</tr>
					</a>
				</c:forEach>
			</table>
			<!-- LEFT FORM ENDS HERE --></td>
			<!-- LEFT CONTENT ENDS HERE -->
		</tr>
	</table>
</ui:form>
</body>
</html>
