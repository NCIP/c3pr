<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
<tabs:division id="Summary" title="Registration History">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		id="additionalList">
		<tr align="center" class="label">
			<td width="11">&nbsp;</td>
			<td><fmt:message key="registration.registrationIdentifier"/></td>
			<td><fmt:message key="study.shortTitle"/></td>
			<td><fmt:message key="study.studyIdentifier"/></td>
			<td><fmt:message key="study.site"/></td>
			<td><fmt:message key="registration.registrationStatus"/></td>
			<td><fmt:message key="registration.startDate"/></td>
			<td><fmt:message key="registration.enrollingPhysician"/></td>
		</tr>
		<c:set var="i" value="0"/>
		<c:forEach items="${command.studySubject.participant.studySubjects}" var="registration">
			<tr align="center" id="row${i}" class="results"
				onMouseOver="navRollOver('row${i}', 'on')"
				onMouseOut="navRollOver('row${i}', 'off')"
				onClick="document.location='../registration/registrationDetails?registrationId=${registration.id}'">
				<td width="11">&nbsp;</td>
				<td>${registration.primaryIdentifier}</td>
				<td>${registration.studySite.study.trimmedShortTitleText}</td>
				<td>${registration.studySite.study.primaryIdentifier}</td>
				<td>${registration.studySite.site.name}</td>
				<td>${registration.registrationStatus}</td>
				<td>${registration.informedConsentSignedDateStr}</td>
				<td>${registration.treatingPhysicianFullName}</td>

			</tr>
		</c:forEach>






	</table>
</tabs:division>
