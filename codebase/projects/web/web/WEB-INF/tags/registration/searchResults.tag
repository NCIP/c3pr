<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@attribute name="registrations" required="true"
	type="java.util.Collection"%>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
<!-- REGISTRATION SEARCH RESULTS START HERE -->
<div class="eXtremeTable" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table class="tableRegion" width="100%" border="0" cellspacing="0" cellpadding="0">
		<thead>
			<c:if test="${registrations!=null}">
				<tr>
					<td class="tableHeader">Registration <br>
					Identifier</td>
					<td class="tableHeader">Study <br>
					Short Title</td>
					<td class="tableHeader">Study <br>
					Identifier</td>
					<td class="tableHeader">Subject <br>
					Last Name</b></td>
					<td class="tableHeader">Subject <br>
					Primary Identifier</td>
					<td class="tableHeader">Site</td>
					<td class="tableHeader">Registration <br>
					Status</td>
					<td class="tableHeader">Registration <br>
					Date</td>
					<td class="tableHeader">Treating <br>
					Physician</td>
				</tr>
			</c:if>
			</thead>
			<tbody class="tableBody">
			<%int i=0; %>
			<c:forEach items="${registrations}" var="registration">
			<% String currClass=i%2==0? "odd":"even"; %>
				<c:set var="localUrl"
					value="../registration/${registration.registrationStatus=='Incomplete'?'createRegistration':'overview'}?resumeFlow=true&_page=1&_target3=3&registrationId=${registration.id}" />
				<tr id="row<%= i++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'"
				onMouseOut="this.className='<%= currClass %>'"
					onClick='document.location="${localUrl }"	'>
					<td><a href="${url}?registrationId=${registration.id}">${registration.primaryIdentifier}</a></td>
					<td>${registration.studySite.study.trimmedShortTitleText}</td>
					<td>${registration.studySite.study.primaryIdentifier}</td>
					<td>${registration.participant.lastName}</td>
					<td>${registration.participant.primaryIdentifier}</td>
					<td>${registration.studySite.site.name}</td>
					<td>${registration.registrationStatus}</td>
					<td>${registration.informedConsentSignedDateStr}</td>
					<td>${registration.treatingPhysician.healthcareSiteInvestigator.investigator.fullName}</td>
					</a>
				</tr>
				<c:set var="i" value="${i+1}"></c:set>
			</c:forEach>
			</tbody>
		</table>
		</td>
	</tr>
</table>
</div>
<!-- REGISTRATION SEARCH RESULTS END HERE -->
