<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><participanttags:htmlTitle subject="${command}" /></title>
<style type="text/css">
#workflow-tabs {
top:83px;
}
</style>
<script language="JavaScript" type="text/JavaScript">

function updateTargetPage(target){
	document.studyDesignForm._target0.value=s;
	document.studyDesignForm.submit();
}

</script>
</head>
<body>
<form:form>
<div id="summary">
	<br>
    <chrome:flashMessage/>
	<tags:tabFields tab="${tab}"/>
	<input type="hidden" name="_finish" value="true"/>
    <tags:instructions code="participant_submit" />
		<div><input type="hidden" name="_finish" value="true" /></div>
		<chrome:division id="subject-details" title="Basic Details" link="javascript:document.getElementById('flowredirect-target').name='_target0';document.getElementById('flowredirect').submit()">
			<div class="leftpanel">
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.firstName"/>:</div>
						<tags:value value="${command.firstName}" ></tags:value>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.lastName"/>:</div>
						<tags:value value="${command.lastName}" ></tags:value>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.middleName"/>:</div>
						<tags:value value="${command.middleName}" ></tags:value>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.maidenName"/>:</div>
						<tags:value value="${command.maidenName}" ></tags:value>
					</div>
				
			</div>
			<div class="rightpanel">
				<div class="row">
					<div class="label"><fmt:message key="participant.gender"/>:</div>
					<tags:value value="${command.administrativeGenderCode}" ></tags:value>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="participant.birthDate"/>:</div>
					<tags:value value="${command.birthDateStr}" ></tags:value>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="participant.ethnicity"/>:</div>
					<tags:value value="${command.ethnicGroupCode}" ></tags:value>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="participant.race"/>:</div>
						<div class="value">
						<c:forEach items="${command.raceCodes}" var="raceCode">
				            <div class="row">
				                <div class="left">
				                        ${raceCode.displayName}
				                </div>
				            </div>
				        </c:forEach>
					</div>
				</div>
			</div>
		</chrome:division>
		<chrome:division title="Identifiers"  link="javascript:document.getElementById('flowredirect-target').name='_target0';document.getElementById('flowredirect').submit()">
		    <table width="90%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
	        <tr>
	            <th><fmt:message key="c3pr.common.assigningAuthority"/></th>
	            <th><fmt:message key="c3pr.common.identifierType"/></th>
	            <th><fmt:message key="c3pr.common.identifier"/></th>
	            <th></th>
	        </tr>
	        <c:forEach var="orgIdentifier" items="${command.organizationAssignedIdentifiers}"
	                  varStatus="organizationStatus">
	            <tr>
	                <td>${orgIdentifier.healthcareSite.name}</td>
	                <td>${orgIdentifier.type}</td>
	                <td>${orgIdentifier.value}</td>
	            </tr>
	        </c:forEach>
	        <c:forEach items="${command.systemAssignedIdentifiers}" varStatus="status" var="sysIdentifier">
	            <tr>
	                <td>${sysIdentifier.systemName}</td>
	                <td>${sysIdentifier.type}</td>
	                <td>${sysIdentifier.value}</td>
	            </tr>
	        </c:forEach>
	    </table>
		</chrome:division>
		<chrome:division title="Address" link="javascript:document.getElementById('flowredirect-target').name='_target1';document.getElementById('flowredirect').submit()">
			<c:choose>
			<c:when test="${command.address.addressString != ''}">
				<div class="leftpanel">
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.streetAddress"/>:</div>
						<tags:value value="${command.address.streetAddress}" ></tags:value>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.city"/>:</div>
						<tags:value value="${command.address.city}" ></tags:value>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.state"/>:</div>
						<tags:value value="${command.address.stateCode}" ></tags:value>
					</div>
				</div>
				<div class="rightpanel">
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.country"/>:</div>
						<tags:value value="${command.address.countryCode}" ></tags:value>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.zip"/>:</div>
						<tags:value value="${command.address.postalCode}" ></tags:value>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.addressNotSpecified"/></span></div>
			</c:otherwise>
			</c:choose>
			
		</chrome:division>
		<chrome:division title="Contact Information" link="javascript:document.getElementById('flowredirect-target').name='_target1';document.getElementById('flowredirect').submit()" condition="${flowType != 'VIEW_SUBJECT'}">
			<c:choose>
			<c:when test="${empty command.email && empty command.phone && empty command.fax}">
				<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.contactInfoNotProvided"/></span></div>				
			</c:when>
			<c:otherwise>
				<div class="leftpanel">
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.email"/>:</div>
						<tags:value value="${command.email}" ></tags:value>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.phone"/>:</div>
						<tags:value value="${command.phone}" ></tags:value>
					</div>
				</div>
				<div class="rightpanel">
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.fax"/>:</div>
						<tags:value value="${command.fax}" ></tags:value>
					</div>
				</div>
			</c:otherwise>
			</c:choose>
		</chrome:division>
		<br>
		<div class="division"></div>
</div>
</form:form>


</body>
</html>
