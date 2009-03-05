<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link href="resources/styles.css" rel="stylesheet" type="text/css">
    <link href="resources/search.css" rel="stylesheet" type="text/css">
<script>
function accessApp(url,targetWindow){
	$('hotlinksForm').target=targetWindow;
	$('hotlinksForm').action=url;
	$('hotlinksForm').submit();
}
function submitLocalForm(formName, regId ,schEphId){
	registrationElement=formName+'_registrationId';
	$(registrationElement).value=regId;
	$(formName).submit();
}
function createReg(studySite, participant, parentRegistrationId){
	$('create_studySite').value=studySite;
	$('create_participant').value=participant;
	$('create_parent_id').value=parentRegistrationId;
	$('create').submit();
}

</script>
<style>
	#main {
		top:35px;
	}
</style>
</head>
<body>
	<c:choose>
				<c:when test="${command.studySubject.dataEntryStatusString=='Incomplete'}">
					<c:set var="formType"
					value="edit" />
				</c:when>
				<c:otherwise>
					<c:set var="formType"
					value="manage" />	
				</c:otherwise>
	</c:choose>
	<tags:controlPanel>
	  <c:if test="${command.studySubject.dataEntryStatusString!='Incomplete' }">
			<form id="manage" name="manage" action="../registration/manageRegistration" method="get" style="display:inline;">
				<input type="hidden" name="registrationId" value="${command.studySubject.systemAssignedIdentifiers[0] }"/>
				<c:if test="${command.studySubject.regWorkflowStatus != 'PENDING'}">
					<tags:oneControlPanelItem imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_manageThisReg.png" linktext="Manage this registration" linkhref="javascript:doManage('${formType}',paramString)"/>
				</c:if>
			</form>
	  </c:if>
	  <tags:oneControlPanelItem linkhref="javascript:launchPrint()" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_printer.png" linktext="Print" />
	</tags:controlPanel>
<form action="../registration/manageRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>" method="post" id="manageCompanion">
	<input type="hidden" name="_page1" id="_page" value="1"/>
	<input type="hidden" name="_target1" id="_target" value="1"/>
</form>
<form action="../registration/createRegistration" method="post" id="create">
	<input type="hidden" name="_page" id="_page0" value="0"/>
	<input type="hidden" name="_target1" id="_target1" value="1"/>
	<input type="hidden" name="studySite" id="create_studySite" value=""/>
	<input type="hidden" name="participant" id="create_participant" value=""/>
	<input type="hidden" name=parentRegistrationId id="create_parent_id" value=""/>
	<!-- <input type="hidden" name="scheduledEpoch" id="create_scheduledEpoch" value=""/>-->
</form>
<c:choose>
<c:when test="${not empty registrationException}">
<tags:panelBox title="Error Registering" boxId="ConfMessage">
<font color="Red"> <strong>Error registering subject.<br> ${registrationException.message}</strong></font>
</tags:panelBox>
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>
<form name="navigationForm" id="navigationForm" method="post"></form>
<tags:panelBox title="Confirmation" boxId="ConfMessage">
	<c:choose>
	<c:when test="${fn:length(command.studySubject.studySite.registrationEndpoints)>0 && command.studySubject.studySite.lastAttemptedRegistrationEndpoint.status=='MESSAGE_SEND_FAILED'} ">
		<font color='<fmt:message key="REGISTRATION.MULTISITE.ERROR.COlOR"/>'><strong><fmt:message key="REGISTRATION.MULTISITE.ERROR"/> Please <a href="javascript:showEndpointError();">click</a> here to see the detail error message.</strong></font>
		<div id="endpoint-error" style="display: none;">
			<chrome:box title="Multisite registration messages for ${command.studySubject.studySite.healthcareSite.name}">
			<chrome:division title="${command.studySubject.studySite.lastAttemptedRegistrationEndpoint.apiName.displayName } at ${command.studySubject.studySite.healthcareSite.name }" style="text-align: left;">
			<div align="left" style="font-size: 1.4em; color: red">${command.studySubject.studySite.lastAttemptedRegistrationEndpoint.status.code }</div>
			<tags:displayErrors id="endpoint-errors" errors="${command.studySubject.studySite.lastAttemptedRegistrationEndpoint.errors}"></tags:displayErrors>
			</chrome:division>
			</chrome:box>
			<script>
				function showEndpointError(){
					Dialog.alert($("endpoint-error").innerHTML,{className: "alphacube", width:540, okLabel: "Done"});
				}
			</script>
		</div>
	</c:when>
	<c:otherwise>		
		<div id="flash-message" class="${imageAndMessageList[0]}">
			<img src="<tags:imageUrl name="${imageAndMessageList[0] == 'info' ? 'check.png' :'error-red.png'}" />" alt="" style="vertical-align:top;" /> 
			<fmt:message key="${imageAndMessageList[1]}"/> 
			<c:if test="${imageAndMessageList[0] == 'info'}">
				Please <a href="javascript:launchPrint();">print</a> and save this confirmation in the subject study records.
			</c:if>
		</div>
	</c:otherwise>
	</c:choose>
	
	<div id="printable">
		<div class="row">
			<div class="label"><b><fmt:message key="participant.subjectMRN"/></b>:</div>
			<div class="value">${command.studySubject.participant.primaryIdentifier}</div>
		</div>
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
			<div class="label"><b><fmt:message key="study.shortTitle"/></b>:</div>
			<div class="value">${command.studySubject.studySite.study.shortTitleText}</div>
		</div>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.registrationStatus"/></b>:</div>
			<div class="value">${command.studySubject.regWorkflowStatus.code }</div>
		</div>		
		<div class="row">
			<div class="label"><b><fmt:message key="c3pr.common.epoch"/></b>:</div>
			<div class="value">${command.studySubject.scheduledEpoch.epoch.name}</div>
		</div>
		<c:if test="${!empty armAssigned}">
			<div class="row">
				<div class="label"><b>${armAssignedLabel }</b>:</div>
				<div class="value">${armAssigned}</div>
			</div>
		</c:if>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.enrollingSite"/></b>:</div>
			<div class="value">${command.studySubject.studySite.healthcareSite.name}</div>
		</div>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.startDate"/></b>:</div>
			<div class="value">${command.studySubject.startDateStr}</div>
		</div>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.consentSignedDate"/></b>:</div>
			<div class="value">${command.studySubject.informedConsentSignedDateStr}</div>
		</div>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.consentVersion"/></b>:</div>
			<div class="value">${command.studySubject.informedConsentVersion}</div>
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
	
	</div>

	<script>
		function doManage(formName, idParamStr){
			if(formName=='manage'){
				document.location="../registration/manageRegistration?"+idParamStr;
			}else if(formName=='edit'){
				document.location="../registration/editRegistration?"+idParamStr;
			}else if(formName=='confirm'){
				document.location="../registration/confirm?"+idParamStr;
			}
		}
		paramString="<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>";
	</script>


	<c:if test="${hotlinkEnable}">
	<%--<table width="60%">
		<c:if test="${!empty caaersBaseUrl}">
		<div class="row">
			<div align="left"><a
				href="javascript:accessApp('${caaersBaseUrl }','_caaers');">
			<b>Adverse Event Reporting</b></a> </div>
		</div>
		<div class="row">
			<div><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></div>
			<div><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></div>
		</div>
		</c:if>
		<c:if test="${!empty pscBaseUrl}">
		<div class="row">
			<div align="left"><a
				href="javascript:accessApp('${pscBaseUrl }','_psc');">
			<b>Study Calendar</b></a></div>
		</div>
		<div class="row">
			<div><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></div>
			<div><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></div>
		</div>
		</c:if>
		<c:if test="${!empty c3dBaseUrl}">
		<div class="row">
			<div align="left"><a
				href="javascript:accessApp('${c3dBaseUrl }','_c3d');">
			<b>Clinical Database</b></a></div>
		</div>
		</c:if>
	</table>--%>
	  <ul>
    	<c:if test="${!empty caaersBaseUrl}">
	    <li><a href="${caaersBaseUrl }?assignment=${command.studySubject.gridId }" target="${caaers_window }"><b>Adverse Event Reporting System</a></li>
	    </c:if>
		<c:if test="${!empty pscBaseUrl}">
	    <li><a href="${pscBaseUrl }?assignment=${command.studySubject.gridId }" target="${psc_window }">Patient Study Calendar</a></li>
	    </c:if>
		<c:if test="${!empty c3dBaseUrl}">
	    <li><a href="${c3dBaseUrl }?assignment=${command.studySubject.gridId }" target="${c3d_window }">Cancer Central Clinical Database</a></li>
	    </c:if>
	  </ul>
	</c:if>
</tags:panelBox>
<form id="hotlinksForm" action="" method="get">
<input type="hidden" name="assignment" value="${command.studySubject.gridId }"/>
</form>
<!-- 
<c:if test="${registerableWithCompanions &&(command.shouldRandomize || hasCompanions) && command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code == 'Registered But Not Randomized' && command.studySubject.parentStudySubject == null && command.studySubject.regWorkflowStatus.code != 'Enrolled'}">
<tags:panelBox title="Enroll & Randomize">
	<registrationTags:register registration="${command.studySubject}" newReg="${newRegistration}" actionButtonLabel="Enroll & Randomize" requiresMultiSite="${requiresMultiSite}"/>
</tags:panelBox>
</c:if>

<c:if test="${registerableWithCompanions &&(command.shouldRandomize) && command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code == 'Registered But Not Randomized' && command.studySubject.regWorkflowStatus.code == 'Enrolled'}">
<tags:panelBox title="Transfer & Randomize">
	<registrationTags:register registration="${command.studySubject}" newReg="${newRegistration}" actionButtonLabel="Transfer & Randomize" requiresMultiSite="${requiresMultiSite}"/>
</tags:panelBox>
</c:if>
 -->
</body>
</html>