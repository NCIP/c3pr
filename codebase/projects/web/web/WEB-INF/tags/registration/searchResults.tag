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
//function submitLocalForm(formName, regId ,schEphId){
//	registrationElement=formName+'_registrationId';
//	$(registrationElement).value=regId;
//	$(formName).submit();
//}

function submitLocalForm(formName, idParamStr){
	if(formName=='manage'){
		document.location="../registration/manageRegistration?"+idParamStr;
	}else if(formName=='create'){
		document.location="../registration/createRegistration?"+idParamStr;
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
					<td class="tableHeader">Study <br>
					Short Title</td>
					<td class="tableHeader">Study <br>
					Identifier</td>
					<td class="tableHeader">Subject <br>
					Last Name</b></td>
					<td class="tableHeader">Subject <br>
					Primary Identifier</td>
					<td class="tableHeader">Subject <br>
					Medical Record Number</td>
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
                <csmauthz:accesscontrol domainObject="${registration.studySite.healthcareSite}"
                                                      hasPrivileges="ACCESS"  authorizationCheckName="siteAuthorizationCheck">

            <% String currClass=i%2==0? "odd":"even"; %>
            <c:set var="paramString" value="<tags:identifierParameterString identifier='${registration.systemAssignedIdentifiers[0] }'/>"/>
				<c:choose>
				<c:when test="${registration.dataEntryStatusString=='Incomplete'}">
					<c:set var="formType"
					value="create" />
				</c:when>
				<c:when test="${registration.scheduledEpoch.scEpochWorkflowStatus=='REGISTERED_BUT_NOT_RANDOMIZED'}">
					<c:set var="formType"
					value="confirm" />
				</c:when>
				<c:otherwise>
					<c:set var="formType"
					value="manage" />	
				</c:otherwise>
				</c:choose>
				<tr id="row<%= i++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'"
				onMouseOut="this.className='<%= currClass %>'" style="cursor:pointer"
					onClick='submitLocalForm("${formType}","${paramString }")'>
					<td>${registration.studySite.study.trimmedShortTitleText}</td>
					<td>${registration.studySite.study.primaryIdentifier}</td>
					<td>${registration.participant.lastName}</td>
					<td>${registration.participant.primaryIdentifier}</td>
					<td>${registration.participant.MRN.value}</td>
					<td>${registration.studySite.healthcareSite.name}</td>
					<td>${registration.regWorkflowStatus.code}</td>
					<td>${registration.startDateStr}</td>
					<td>${registration.treatingPhysicianFullName}</td>
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
