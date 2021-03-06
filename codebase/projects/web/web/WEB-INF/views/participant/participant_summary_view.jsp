<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title>
    	<participanttags:htmlTitle subject="${command.participant}" />
    </title>
    <script>
    	function editParticipant(){
    		document.location="editParticipant?<tags:identifierParameterString identifier='${command.participant.primaryIdentifier }'/>";
        	}
    </script>
</head>
<body>
<c:if test="${flowType == 'VIEW_SUBJECT'}">
<div id="controlPanel">
	
		<tags:controlPanel>
			<csmauthz:accesscontrol domainObject="${command.participant}" hasPrivileges="UI_SUBJECT_UPDATE" authorizationCheckName="subjectAuthorizationCheck">
				<tags:oneControlPanelItem linkhref="javascript:editParticipant();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit" />
			</csmauthz:accesscontrol>
			<tags:oneControlPanelItem linkhref="javascript:launchPrint()" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_printer.png" linktext="Print" />
		</tags:controlPanel>
</div>
</c:if>
<form:form>
	<tags:tabFields tab="${tab}" />
	<tags:errors path="*"/> 
		<div id="summary">
		<div><input type="hidden" name="_finish" value="true" /> <input type="hidden" name="_action" value=""></div>
		<div id="printable">
		<chrome:division id="subject-details" title="Basic Details" link="javascript:document.getElementById('flowredirect-target').name='_target0';document.getElementById('flowredirect').submit()" condition="${flowType != 'VIEW_SUBJECT'}">
			<div class="leftpanel">
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.firstName"/>:</div>
						<tags:value value="${command.participant.firstName}"></tags:value>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.lastName"/>:</div>
						<tags:value value="${command.participant.lastName}" ></tags:value>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.middleName"/>:</div>
						<tags:value value="${command.participant.middleName}" ></tags:value>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.maidenName"/>:</div>
						<tags:value value="${command.participant.maidenName}" ></tags:value>
					</div>
				
			</div>
			<div class="rightpanel">
				<div class="row">
					<div class="label"><fmt:message key="participant.gender"/>:</div>
					<tags:value value="${command.participant.administrativeGenderCode}" ></tags:value>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="participant.birthDate"/>:</div>
					<tags:value value="${command.participant.birthDateStr}" ></tags:value>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="participant.ethnicity"/>:</div>
					<tags:value value="${command.participant.ethnicGroupCode}" ></tags:value>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="participant.race"/>:</div>
						<div class="value">
						<c:forEach items="${command.participant.raceCodeAssociations}" var="raceCode">
				            <div class="row">
				                <div class="left">
				                        ${raceCode.raceCode.displayName}
				                </div>
				            </div>
				        </c:forEach>
					</div>
				</div>
			</div>
		</chrome:division>
			
		<chrome:division title="Familial Relationships">
			 <table width="90%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
	        <tr>
	            <th><fmt:message key="participant.subjectName"/></th>
	            <th><fmt:message key="participant.familialRelationship.name"/></th>
	        </tr>
	        <c:forEach var="familyMember" items="${command.participant.relatedTo}"
	                  varStatus="familyMemberStatus">
	            <tr>
	                <td>${familyMember.secondaryParticipant.fullName}</td>
	                <td>${familyMember.name.displayName}</td>
	            </tr>
	        </c:forEach>
	    </table>
		</chrome:division>
		
		
		<chrome:division title="Identifiers"  link="javascript:document.getElementById('flowredirect-target').name='_target0';document.getElementById('flowredirect').submit()" condition="${flowType != 'VIEW_SUBJECT'}">
		    <table width="90%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
	        <tr>
	            <th><fmt:message key="c3pr.common.assigningAuthority"/></th>
	            <th><fmt:message key="c3pr.common.identifierType"/></th>
	            <th><fmt:message key="c3pr.common.identifier"/></th>
	        </tr>
	        <c:forEach var="orgIdentifier" items="${command.participant.organizationAssignedIdentifiers}"
	                  varStatus="organizationStatus">
	            <tr>
	                <td>${orgIdentifier.healthcareSite.name}</td>
	                <td>${orgIdentifier.type}</td>
	                <td>${orgIdentifier.value}</td>
	            </tr>
	        </c:forEach>
	        <c:forEach items="${command.participant.systemAssignedIdentifiers}" varStatus="status" var="sysIdentifier">
	            <tr>
	                <td>${sysIdentifier.systemName}</td>
	                <td>${sysIdentifier.type}</td>
	                <td>${sysIdentifier.value}</td>
	            </tr>
	        </c:forEach>
	    </table>
		</chrome:division>
		<chrome:division title="Addresses" link="javascript:document.getElementById('flowredirect-target').name='_target1';document.getElementById('flowredirect').submit()" condition="${flowType != 'VIEW_SUBJECT'}">
			<table width="90%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
	        <tr>
	            <th width="75%"><fmt:message key="c3pr.common.address"/></th>
	            <th><fmt:message key="c3pr.common.startDate"/></th>
	            <th><fmt:message key="c3pr.common.endDate"/></th>
	        </tr>
	        <c:forEach var="address" items="${command.addresses}"
	                  varStatus="status">
					<c:if test="${address.addressString != ''}">
			            <tr>
			                <td>${address.addressString}</td>
			                <td>${address.startDateStr}</td>
			                <td>${address.endDateStr}</td>
			            </tr>
		            </c:if>
	        </c:forEach>
	   	 </table>
			
		</chrome:division>
		<chrome:division title="Contact Information" link="javascript:document.getElementById('flowredirect-target').name='_target1';document.getElementById('flowredirect').submit()" condition="${flowType != 'VIEW_SUBJECT'}">
			<c:choose>
			<c:when test="${empty command.participant.email && empty command.participant.phone && empty command.participant.fax}">
				<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.contactInfoNotProvided"/></span></div>				
			</c:when>
			<c:otherwise>
				<div class="leftpanel">
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.email"/>:</div>
						<tags:value value="${command.participant.email}" ></tags:value>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.phone"/>:</div>
						<tags:value value="${command.participant.phone}" ></tags:value>
					</div>
				</div>
				<div class="rightpanel">
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.fax"/>:</div>
						<tags:value value="${command.participant.fax}" ></tags:value>
					</div>
				</div>
			</c:otherwise>
			</c:choose>
		</chrome:division>
	<br />
		<div class="division"></div>
        </div>
	</div>	
</form:form>
</body>
</html>
