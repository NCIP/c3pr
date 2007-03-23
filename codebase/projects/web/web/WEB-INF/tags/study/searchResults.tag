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
			<tr align="center" class="label">
				<td>Short Title</td>
				<td>Primary Identifier</td>
				<td>Status</td>
				<td>Sponsor Code</td>
				<td>Multi Institution Indicator</td>
				<td>Target Accrual Number</td>
				<td>Long Title</td>
			</tr>
			<%int i=0; %>
			<c:forEach items="${studyResults}" var="study">
			<tr align="center" id="row<%= i++ %>" class="results" onMouseOver="navRollOver('row<%= i-1 %>', 'on')"
				onMouseOut="navRollOver('row<%= i-1 %>', 'off')"
				onClick="document.location='${url}?studyId=${study.id}'">
				<td>${study.trimmedShortTitleText}</td>
				<td>${study.primaryIdentifier}</td>
				<td>${study.status}</td>
				<td>${study.sponsorCode}</td>
				<td>${study.multiInstitutionIndicator}</td>
				<td>${study.targetAccrualNumber}</td>
				<td>${study.longTitleText}</td>
				</a>
			</tr>
			</c:forEach>
		</table>
	</td>
</tr>
</table>
<!-- STUDY SEARCH RESULTS END HERE -->