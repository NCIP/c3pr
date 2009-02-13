<%@ include file="taglibs.jsp"%>
<%@ taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>

<html>
<head>
    <title><participanttags:htmlTitle subject="${command}" /></title>
    
<style type="text/css">
        .labelR { width: 12em; text-align: right; padding: 4px; }
</style>
<style type="text/css">
        .label { width: 12em; text-align: left; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">

function updateTargetPage(target){
	document.formName._target0.value=s;
	document.formName.submit();
}

</script>
</head>
<body>
<form:form>
	<tags:tabFields tab="${tab}" />
	<tags:errors path="*"/> 

	<chrome:box title="Subject Summary">
		<div><input type="hidden" name="_finish" value="true" /> <input type="hidden" name="_action" value=""></div>
		<div id="printable">
		<chrome:division id="subject-details" title="Basic Details">
			<table class="tablecontent" width="60%">
				<tr>
					<td width="35%" class="alt" align="left"><b><fmt:message key="c3pr.common.firstName"/></b></td>
					<td class="alt" align="left">${command.firstName}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b><fmt:message key="c3pr.common.lastName"/></b></td>
					<td class="alt" align="left">${command.lastName}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b><fmt:message key="c3pr.common.middleName"/></b></td>
					<td class="alt" align="left">${command.middleName}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b><fmt:message key="c3pr.common.maidenName"/></b></td>
					<td class="alt" align="left">${command.maidenName}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b><fmt:message key="participant.gender"/></b></td>
					<td class="alt" align="left">${command.administrativeGenderCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b><fmt:message key="participant.birthDate"/></b></td>
					<td class="alt" align="left">${command.birthDateStr}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b><fmt:message key="participant.ethnicity"/></b></td>
					<td class="alt" align="left">${command.ethnicGroupCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b><fmt:message key="participant.race"/></b></td>
						<td class="alt" align="left">
						<c:forEach items="${command.raceCodes}" var="raceCode">
				            <div class="row">
				                <div class="left">
				                        ${raceCode.displayName}
				                </div>
				            </div>
				        </c:forEach>
					</td>
				</tr>
			</table>
		</chrome:division>

		<chrome:division title="Address">
			<table class="tablecontent" width="60%">
				<tr>
					<td width="35%" class="alt" align="left"><b><fmt:message key="c3pr.common.streetAddress"/></b></td>
					<td class="alt" align="left">${command.address.streetAddress}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b><fmt:message key="c3pr.common.city"/></b></td>
					<td class="alt" align="left">${command.address.city}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b><fmt:message key="c3pr.common.state"/></b></td>
					<td class="alt" align="left">${command.address.stateCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b><fmt:message key="c3pr.common.country"/></b></td>
					<td class="alt" align="left">${command.address.countryCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b><fmt:message key="c3pr.common.zip"/></b></td>
					<td class="alt" align="left">${command.address.postalCode}</td>
				</tr>
			</table>
		</chrome:division>
		<chrome:division title="Contact Information">
			<table class="tablecontent" width="60%">
				<tr>
					<th width="40%" scope="col" align="left"><fmt:message key="c3pr.common.email"/></th>
					<th width="30%" scope="col" align="left"><fmt:message key="c3pr.common.phone"/></th>
					<th width="30%" scope="col" align="left"><fmt:message key="c3pr.common.fax"/></th>
				</tr>
				<tr class="results">
					<c:forEach items="${command.contactMechanisms}"
						var="contactMechanism">
						<td class="alt" align="left">${contactMechanism.value}</td>
					</c:forEach>
				</tr>
			</table>
		</chrome:division>

		<chrome:division title="Identifiers">

			<h4>Organization Assigned Identifiers</h4>
			<br>

			<table class="tablecontent" width="60%">
				<tr>
					<th width="50%" scope="col" align="left"><fmt:message key="c3pr.common.assigningAuthority"/></th>
					<th width="35%" scope="col" align="left"><fmt:message key="c3pr.common.identifierType"/></th>
					<th width="15%" scope="col" align="left"><fmt:message key="c3pr.common.identifier"/></th>
				</tr>
				<c:forEach items="${command.organizationAssignedIdentifiers}"
					var="orgIdentifier">
					<tr class="results">
						<td class="alt" align="left">${orgIdentifier.healthcareSite.name}</td>
						<td class="alt" align="left">${orgIdentifier.type}</td>
						<td class="alt" align="left">${orgIdentifier.value}</td>
					</tr>
				</c:forEach>
			</table>
			<br>
			<h4>System Assigned Identifiers</h4>

			<br>

			<table class="tablecontent" width="60%">
				<tr>
					<th width="50%" scope="col" align="left"><fmt:message key="c3pr.common.systemName"/></th>
					<th width="35%" scope="col" align="left"><fmt:message key="c3pr.common.identifierType"/></th>
					<th width="15%" scope="col" align="left"><fmt:message key="c3pr.common.identifier"/></th>
				</tr>
				<c:forEach items="${command.systemAssignedIdentifiers}"
					var="identifier">
					<tr class="results">
						<td class="alt" align="left">${identifier.systemName}</td>
						<td class="alt" align="left">${identifier.type}</td>
						<td class="alt" align="left">${identifier.value}</td>
					</tr>
				</c:forEach>
			</table>
		</chrome:division>
		<c:if test="${flowType == 'VIEW_SUBJECT'}">
		<csmauthz:accesscontrol domainObject="${command}" hasPrivileges="UPDATE"
	                            authorizationCheckName="domainObjectAuthorizationCheck">
			<div class="content buttons autoclear">
			<div class="flow-buttons"><span class="next"> <input type="button"
				value="Edit Subject"
				onclick="document.location='editParticipant?participantId=${command.id}'" />
				<input type="button" value="Print" onClick="javascript:C3PR.printElement('printable');"/>
			</span></div>
		</div>
		</csmauthz:accesscontrol>
		</c:if>

        </div>
	</chrome:box>
</form:form>
</body>
</html>
