<%@tag%><%@attribute name="url" required="true"%>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- STUDY SEARCH RESULTS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td>			
		<table width="100%" border="0" cellspacing="0" cellpadding="0" id="additionalList">
			<c:if test="${studyResults!=null}">
			<tr align="center" class="label">
				<td>Primary Identifier</td>				
				<td>Short Title</td>
				<td>Status</td>
				<td>Sponsor</td>
				<td>Phase</td>			
				<td>Target Accrual</td>
			</tr>
			</c:if>
			<c:if test="${studyResults!=null && fn:length(studyResults)==0}">
				<tr>
					Sorry, no matches were found
				</tr>
			</c:if>
			
			<%int i=0; %>
			<c:forEach items="${studyResults}" var="study">
			<tr align="center" id="row<%= i++ %>" class="results" onMouseOver="navRollOver('row<%= i-1 %>', 'on')"
				onMouseOut="navRollOver('row<%= i-1 %>', 'off')"
				onClick="
					<c:choose>
						<c:when test="${!empty subjectId}">
							document.location='${url}?studySite=${study.studySites[0].id}&participant=${subjectId }&resumeFlow=true&_page=1&_target3=3'
						</c:when>
						<c:when test="${!empty inRegistration}">
							document.location='${url}?studySiteId=${study.studySites[0].id}'
						</c:when>
						<c:otherwise>
							document.location='${url}?studyId=${study.id}'
						</c:otherwise>
					</c:choose>
				">
				<td>${study.primaryIdentifier}</td>
				<td>${study.trimmedShortTitleText}</td>
				<td>${study.status}</td>
				<td>${study.identifiers[0].value}</td>
				<td>${study.phaseCode}</td>
				<td>${study.targetAccrualNumber}</td>
				</a>
			</tr>
			</c:forEach>
		</table>
	</td>
</tr>
</table>
<!-- STUDY SEARCH RESULTS END HERE -->