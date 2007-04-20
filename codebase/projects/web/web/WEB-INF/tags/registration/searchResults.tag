<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
<!-- SUBJECT SEARCH RESULTS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			id="additionalList">
			<c:if test="${fn:length(registrations) > 0}">
				<tr align="center">
					<td width="11">&nbsp;</td>
					<td><b>Registration Identifier</b></td>
					<td><b>Short Title</b></td>
					<td><b>Study Identifier</b></td>
					<td><b>Site</b></td>
					<td><b>Registration Status</b></td>
					<td><b>Registration Date</b></td>
					<td><b>Treating Physician</b></td>
				</tr>
			</c:if>
			<c:set var="i" value="0" />
			<c:forEach items="${registrations}" var="registration">
				<tr align="center" id="row${i}" class="results"
					onMouseOver="navRollOver('row${i}', 'on')"
					onMouseOut="navRollOver('row${i}', 'off')"
					onClick="document.location='../registration/registrationDetails?registrationId=${registration.id}'">
					<td width="11">&nbsp;</td>
					<td>${registration.primaryIdentifier}</td>
					<td>${registration.studySite.study.trimmedShortTitleText}</td>
					<td>${registration.studySite.study.primaryIdentifier}</td>
					<td>${registration.studySite.site.name}</td>
					<td></td>
					<td>${registration.informedConsentSignedDateStr}</td>
					<td></td>
					</a>
				</tr>
			</c:forEach>
		</table>
		</td>
	</tr>
</table>
<!-- SUBJECT SEARCH RESULTS END HERE -->
