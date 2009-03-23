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
						<div class="value">${command.firstName}</div>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.lastName"/>:</div>
						<div class="value">${command.lastName}</div>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.middleName"/>:</div>
						<div class="value">${command.middleName}</div>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.maidenName"/>:</div>
						<div class="value">${command.maidenName}</div>
					</div>
				
			</div>
			<div class="rightpanel">
				<div class="row">
					<div class="label"><fmt:message key="participant.gender"/>:</div>
					<div class="value">${command.administrativeGenderCode}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="participant.birthDate"/>:</div>
					<div class="value">${command.birthDateStr}</div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="participant.ethnicity"/>:</div>
					<div class="value">${command.ethnicGroupCode}</div>
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
		<chrome:division title="Address" link="javascript:document.getElementById('flowredirect-target').name='_target1';document.getElementById('flowredirect').submit()">
			<c:choose>
			<c:when test="${command.address.addressString != ''}">
				<div class="leftpanel">
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.streetAddress"/>:</div>
						<div class="value">${command.address.streetAddress}</div>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.city"/>:</div>
						<div class="value">${command.address.city}</div>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.state"/>:</div>
						<div class="value">${command.address.stateCode}</div>
					</div>
				</div>
				<div class="rightpanel">
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.country"/>:</div>
						<div class="label">${command.address.countryCode}</div>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.zip"/>:</div>
						<div class="value">${command.address.postalCode}</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.addressNotSpecified"/></span></div>
			</c:otherwise>
			</c:choose>
			
		</chrome:division>
		<chrome:division title="Contact Information"  link="javascript:document.getElementById('flowredirect-target').name='_target1';document.getElementById('flowredirect').submit()">
			<div id="noContactMechanism" class="value"><span class="no-selection"><fmt:message key="c3pr.common.contactInfoNotProvided"/></span></div>
			<table id='contactMechanism' class="tablecontent" width="60%" style="display:none">
				<tr>
					<th width="40%" scope="col" align="left"><fmt:message key="c3pr.common.email"/></th>
					<th width="30%" scope="col" align="left"><fmt:message key="c3pr.common.phone"/></th>
					<th width="30%" scope="col" align="left"><fmt:message key="c3pr.common.fax"/></th>
				</tr>
				<tr class="results">
					<c:forEach items="${command.contactMechanisms}"
						var="contactMechanism">
						<td class="alt" align="left">${contactMechanism.valueString}</td>
						<c:if test="${contactMechanism.valueString != ''}">
							<script>
								Element.show('contactMechanism');
								Element.hide('noContactMechanism');
							</script>
						</c:if>
					</c:forEach>
				</tr>
			</table>
		</chrome:division>
		<chrome:division title="Identifiers"  link="javascript:document.getElementById('flowredirect-target').name='_target0';document.getElementById('flowredirect').submit()">
			<h4><u><fmt:message key="c3pr.common.orgAssignedIdentifier"/></u></h4>
			<br>
			<c:choose>
			<c:when test="${fn:length(command.organizationAssignedIdentifiers) > 0}">
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
			</c:when>
			<c:otherwise>
				<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.orgAssignedIdentifierNotSpecified"/></span></div>
			</c:otherwise>
			</c:choose>
			<br>
			<h4><u><fmt:message key="c3pr.common.systemAssignedIdentifier"/></u></h4>
			<br>
			<c:choose>
			<c:when test="${fn:length(command.systemAssignedIdentifiers) > 0}">
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
			</c:when>
			<c:otherwise>
				<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.systemAssignedIdentifierNotSpecified"/></span></div>
			</c:otherwise>
			</c:choose>
		</chrome:division>
</div>
<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}">
	<jsp:attribute name="submitButton">
		<tags:button type="submit" color="green" icon="subject" value="Create Subject" />				
	</jsp:attribute>
</tags:tabControls>
</form:form>


</body>
</html>
