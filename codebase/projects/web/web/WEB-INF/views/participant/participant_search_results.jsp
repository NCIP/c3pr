<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
</head>
<body>

<tags:search action="searchParticipant"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

		<td id="current">Subject Search Results</td>
		<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
	</tr>
	<tr>

		<td class="display"><!-- TABS LEFT START HERE -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>

				<!-- LEFT CONTENT STARTS HERE -->

				<td valign="top" class="additionals"><!-- LEFT FORM STARTS HERE -->


				<br>
				<br>
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
					<c:forEach items="${participants}" var="participant">
						<tr align="center" id="row<%= i++ %>" class="results" onMouseOver="navRollOver('row<%= i-1 %>', 'on')"
							onMouseOut="navRollOver('row<%= i-1 %>', 'off')"
							onClick="document.location='editParticipant?participantId=${participant.id}'">
							<td>${participant.lastName},${participant.firstName}</td>
							<td>${participant.primaryIdentifier}</td>
							<td>${participant.administrativeGenderCode}</td>
							<td>${participant.raceCode}</td>
							<td>${participant.birthDateStr}</td>
						</a>
						</tr>
					</c:forEach>
				</table>
				<br>
				<!-- LEFT FORM ENDS HERE --></td>
				<!-- LEFT CONTENT ENDS HERE -->
			</tr>
		</table>
		</td>
	</tr>
</table>
<div id="copyright">
</div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
