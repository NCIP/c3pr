<%@tag%>
<%@attribute name="url" required="true"%>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!-- SUBJECT SEARCH RESULTS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			id="additionalList">
			<c:if test="${participants!=null}">
				<tr align="center" class="label">
					<td>Last Name, First Name</td>
					<td>Primary Identifier</td>
					<td>Gender</td>
					<td>Race</td>
					<td>Birth Date</td>
					<td></td>
				</tr>
			</c:if>
			<c:if test="${participants!=null && fn:length(participants)==0}">
				<tr>
					Sorry, no matches were found
				</tr>
			</c:if>
			<%int i=0; %>
			<c:forEach items="${participants}" var="participant">
				<tr align="center" id="row<%= i++ %>" class="results"
					onMouseOver="navRollOver('row<%= i-1 %>', 'on')"
					onMouseOut="navRollOver('row<%= i-1 %>', 'off')"
					onClick="
						<c:choose>
							<c:when test="${!empty studySiteId}">
								document.location='createRegistration?studySite=${studySiteId}&participant=${participant.id}&resumeFlow=true&_page=2&_target3=3'
							</c:when>
							<c:when test="${!empty inRegistration}">
								document.location='searchStudy?inRegistration=true&subjectId=${participant.id}'
							</c:when>
							<c:otherwise>
								document.location='editParticipant?participantId=${participant.id}'
							</c:otherwise>
						</c:choose>
					">
					<td>${participant.lastName},${participant.firstName}</td>
					<td>${participant.primaryIdentifier}</td>
					<td>${participant.administrativeGenderCode}</td>
					<td>${participant.raceCode}</td>
					<td>${participant.birthDateStr}</td>
					</a>
				</tr>
			</c:forEach>
		</table>
		</td>
	</tr>
</table>
<!-- SUBJECT SEARCH RESULTS END HERE -->
