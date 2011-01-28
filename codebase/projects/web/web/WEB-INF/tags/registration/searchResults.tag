<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@attribute name="registrations" required="true"
	type="java.util.Collection"%>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}

function submitLocalForm(idParamStr){
	document.location="<c:url value='/pages/registration/manageRegistration'/>?"+idParamStr;
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
					<td class="tableHeader"><fmt:message key="participant.subjectLastName"/></td>
					<td class="tableHeader"><fmt:message key="participant.subjectPrimaryidentifier"/></td>
					<td class="tableHeader"><fmt:message key="study.studyShortTitle"/></td>
					<td class="tableHeader"><fmt:message key="study.studyIdentifier"/></td>
					<td class="tableHeader"><fmt:message key="study.site"/></td>
					<td class="tableHeader"><fmt:message key="registration.registrationStatus"/></td>
					<td class="tableHeader"><fmt:message key="registration.startDate"/></td>
					<td class="tableHeader"><fmt:message key="registration.registrationIdentifier"/></td>
				</tr>
			</c:if>
			</thead>
            <tbody class="tableBody">
			<%int i=0; %>
			<c:forEach items="${registrations}" var="registration" varStatus="status">
				<c:if test="${registration.regWorkflowStatus.code != 'Invalid' }">
	                <csmauthz:accesscontrol domainObject="${registration}" hasPrivileges="UI_STUDYSUBJECT_SEARCH"  authorizationCheckName="studySubjectAuthorizationCheck">
	            	<% String currClass=i%2==0? "odd":"even"; %>
	            	<script>
						paramString_${status.index }="<tags:identifierParameterString identifier='${registration.systemAssignedIdentifiers[0] }'/>";
					</script>
					<tr id="row<%= i++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'"
					onMouseOut="this.className='<%= currClass %>'" style="cursor:pointer"
						onClick='submitLocalForm(paramString_${status.index })'>
						<td>${registration.participant.lastName}</td>
						<td>${registration.participant.primaryIdentifierValue}</td>
						<td>${registration.studySite.study.trimmedShortTitleText}</td>
						<td>${registration.studySite.study.primaryIdentifier}</td>
						<td>${registration.studySite.healthcareSite.name}</td>
						<td>${registration.regWorkflowStatus.code}</td>
						<td>${registration.startDateStr}</td>
						<td>${registration.coOrdinatingCenterIdentifier.value}</td>
					</tr>
					<c:set var="i" value="${i+1}"></c:set>
	                    
	                </csmauthz:accesscontrol>
               </c:if>
            </c:forEach>
			</tbody>
		</table>
		</td>
	</tr>
</table>
</div>
<!-- REGISTRATION SEARCH RESULTS END HERE -->
