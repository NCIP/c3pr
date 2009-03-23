<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title>
    	<participanttags:htmlTitle subject="${command}" />
    </title>
    <script>
    	function editParticipant(){
    		document.location="editParticipant?<tags:identifierParameterString identifier='${command.organizationAssignedIdentifiers[0] }'/>";
        	}
    </script>
<style>
#workflow-tabs {
top:83px;
}
</style>
</head>
<body>
<div id="controlPanel">
	<c:if test="${flowType == 'VIEW_SUBJECT'}">
		<tags:controlPanel>
			<csmauthz:accesscontrol domainObject="${command}" hasPrivileges="UPDATE" authorizationCheckName="domainObjectAuthorizationCheck">
				<tags:oneControlPanelItem linkhref="javascript:editParticipant();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit" />
			</csmauthz:accesscontrol>
			<tags:oneControlPanelItem linkhref="javascript:launchPrint()" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_printer.png" linktext="Print" />
		</tags:controlPanel>
	</c:if>
</div>
<form:form>
	<tags:tabFields tab="${tab}" />
	<tags:errors path="*"/> 
		<div id="summary">
		<div><input type="hidden" name="_finish" value="true" /> <input type="hidden" name="_action" value=""></div>
		<div id="printable">
		<chrome:division id="subject-details" title="Basic Details">
			<div class="leftpanel">
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.firstName"/>:</div>
						<tags:value value="${command.firstName}"></tags:value>
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
		<chrome:division title="Address">
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
		<chrome:division title="Contact Information">
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
		<chrome:division title="Identifiers">
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
		<br>
        </div>
	</div>	
</form:form>
</body>
</html>