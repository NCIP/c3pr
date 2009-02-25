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
//function submitLocalForm(formName, regId ,schEphId){
//	registrationElement=formName+'_registrationId';
//	$(registrationElement).value=regId;
//	$(formName).submit();
//}

function submitLocalForm(formName, idParamStr, companion){
	if(formName=='manage'){
		document.location="../registration/manageRegistration?"+idParamStr;
	}else if(formName=='edit' && !companion){
		document.location="../registration/editRegistration?"+idParamStr;
	}else if(formName=='edit' && companion){
		document.location="../registration/editCompanionRegistration?"+idParamStr;
	}else if(formName=='confirm'){
		document.location="../registration/confirm?"+idParamStr;
	}
}

</script>
<!-- REGISTRATION SEARCH RESULTS START HERE -->
<%--<form id="manage" name="manage" action="../registration/manageRegistration" method="get">
	<input type="hidden" name="registrationId" id="manage_registrationId" value=""/>
	<!-- <input type="hidden" name="scheduledEpoch" id="manage_scheduledEpoch" value=""/>-->
</form>
<form action="../registration/createRegistration" method="post" id="create">
	<input type="hidden" name="_page" id="_page0" value="0"/>
	<input type="hidden" name="_target1" id="_target1" value="1"/>
	<input type="hidden" name="registrationId" id="create_registrationId" value=""/>
	<input type="hidden" name="goToTab" id="goToTab" value="true"/>
	<!-- <input type="hidden" name="scheduledEpoch" id="create_scheduledEpoch" value=""/>-->
</form>--%>
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
                <csmauthz:accesscontrol domainObject="${registration.studySite.healthcareSite}" hasPrivileges="ACCESS"  authorizationCheckName="siteAuthorizationCheck">

            <% String currClass=i%2==0? "odd":"even"; %>
            	<script>
					paramString_${status.index }="<tags:identifierParameterString identifier='${registration.systemAssignedIdentifiers[0] }'/>";
				</script>
				<c:choose>
					<c:when test="${registration.dataEntryStatusString=='Incomplete'}">
						<c:set var="formType" value="edit" />
					</c:when>
					<c:when test="${registration.dataEntryStatusString=='Complete' && registration.scheduledEpoch.scEpochWorkflowStatus != 'REGISTERED'}">
						<c:set var="formType" value="edit" />
					</c:when>
					<c:otherwise>
						<c:set var="formType" value="manage" />	
					</c:otherwise>
				</c:choose>
				<tr id="row<%= i++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'"
				onMouseOut="this.className='<%= currClass %>'" style="cursor:pointer"
					onClick='submitLocalForm("${formType}",paramString_${status.index }, "${not empty registration.parentStudySubject}")'>
					<td>${registration.participant.lastName}</td>
					<td>${registration.participant.primaryIdentifier}</td>
					<td>${registration.studySite.study.trimmedShortTitleText}</td>
					<td>${registration.studySite.study.primaryIdentifier}</td>
					<td>${registration.studySite.healthcareSite.name}</td>
					<td>${registration.regWorkflowStatus.code}</td>
					<td>${registration.startDateStr}</td>
					<td>${registration.coOrdinatingCenterIdentifier.value}</td>
				</tr>
				<c:set var="i" value="${i+1}"></c:set>
                    
                </csmauthz:accesscontrol>
            </c:forEach>
			</tbody>
		</table>
		</td>
	</tr>
</table>
</div>
<!-- REGISTRATION SEARCH RESULTS END HERE -->
