<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
<title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
<script>
	function gotoManageRegistration(){
		document.location = "manageRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>";
	}
</script>
</head>
<body>
<tags:controlPanel>
<tags:oneControlPanelItem imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_manageThisReg.png" linktext="Manage registration" linkhref="javascript:gotoManageRegistration();"/>
</tags:controlPanel>
<chrome:box title="Confirmation" autopad="true">
	<chrome:division id="Subject Information" title="Subject">
		<div class="row">
			<div class="label"><fmt:message key="participant.fullName"/>:</div>
			<div class="value">${command.studySubject.participant.fullName}</div>
		</div>
		<div class="row">
			<div class="label"><fmt:message key="participant.MRN"/>:</div>
			<div class="value">${command.studySubject.participant.MRN.value }</div>
		</div>
	</chrome:division>
	<chrome:division id="Registration Information" title="${command.studySubject.studySite.study.shortTitleText} (${command.studySubject.studySite.study.primaryIdentifier})">
		<div class="row">
            <div class="label"><b><fmt:message key="registration.registrationIdentifier"/></b>:</div>
			<c:choose>
				<c:when test="${!empty command.studySubject.coOrdinatingCenterIdentifier.value}">
					<div class="value">${command.studySubject.coOrdinatingCenterIdentifier.value}</div>
				</c:when>
				<c:otherwise>
					<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.notGenerated"/></span></div>
				</c:otherwise>
			</c:choose>
        </div>
		 <div class="row">
			<div class="label"><b><fmt:message key="study.version.name"/></b>:</div>
			<div class="value">${command.studySubject.studySiteVersion.studyVersion.name}</div>
		</div>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.registrationStatus"/></b>:</div>
			<div class="value">${command.studySubject.regWorkflowStatus.code }</div>
		</div>	
		<div class="row">
			<div class="label"><b><fmt:message key="registration.startDate"/></b>:</div>
			<c:choose>
				<c:when test="${!empty command.studySubject.startDateStr}">
					<div class="value">${command.studySubject.startDateStr}</div>
				</c:when>
				<c:otherwise>
					<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.enrollingPhysician"/></b>:</div>
			<c:choose>
				<c:when test="${!empty command.studySubject.treatingPhysicianFullName}">
					<div class="value">${command.studySubject.treatingPhysicianFullName}</div>
				</c:when>
				<c:otherwise>
					<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.enrollingSite"/></b>:</div>
			<div class="value">${command.studySubject.studySite.healthcareSite.name}</div>
		</div>
	</chrome:division>
</chrome:box>
</body>
</html>
