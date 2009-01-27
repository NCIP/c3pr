<%@tag%><%@attribute name="registrations" required="true" type="java.util.Collection" %>
<%@tag%><%@attribute name="url" required="true"%>

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
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		id="additionalList">
		<tr align="center" class="label">
			<td width="11">&nbsp;</td>
			<td>Registration Identifier</td>
			<td>Short Title</td>
			<td>Study Identifier</td>
			<td>Site</td>
			<td><fmt:message key="registration.registrationStatus"/></td>
			<td><fmt:message key="registration.startDate"/></td>
			<td>Treating Physician</td>
		</tr>
			
		<c:set var="i" value="0"/>
		<c:forEach items="${participantAssignments}" var="registration" vatStatus="status">
			<script>
					paramString_${status.index }="<tags:identifierParameterString identifier='${registration.systemAssignedIdentifiers[0] }'/>";
			</script>
			<tr align="center" id="row${i}" class="results"
				onMouseOver="navRollOver('row${i}', 'on')"
				onMouseOut="navRollOver('row${i}', 'off')"
				onClick="document.location='${url}?'+paramString_${status.index }">
				<td width="11">&nbsp;</td>
				<td>${registration.primaryIdentifier}</td>
				<td>${registration.studySite.study.trimmedShortTitleText}</td>
				<td>${registration.studySite.study.primaryIdentifier}</td>
				<td>${registration.studySite.site.name}</td>
				<td>${registration.registrationStatus}</td>
				<td>${registration.informedConsentSignedDateStr}</td>
				<td>${registration.treatingPhysicianFullName}</td>
				</a>
			</tr>
		</c:forEach>
	</table>
