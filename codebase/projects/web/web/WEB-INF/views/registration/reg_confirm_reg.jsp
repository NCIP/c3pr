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

function manageCompanions(){
	$('manageCompanion').submit();
}
</script>
</head>
<body>
<form action="../registration/manageRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>" method="post" id="manageCompanion">
	<input type="hidden" name="_page2" id="_page" value="2"/>
	<input type="hidden" name="_target2" id="_target" value="2"/>
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
<tags:panelBox title="Confirmation Message" boxId="ConfMessage">

<br/>
  <!--  newRegistration: ${newRegistration}<br>
	reg_registered :${reg_registered }<br>
	reg_nonenrolled:${reg_nonenrolled }<br>
	reg_pending:${reg_pending }<br>
	reg_disapproved:${ reg_disapproved}<br>
	reg_reserved:${reg_reserved }<br>
	reg_unregistered:${reg_unregistered }<br>
	reg_unrandomized:${ reg_unrandomized}<br>
	epoch_registered :${epoch_approved }<br>
	epoch_nonenrolled:${epoch_nonenrolled }<br>
	epoch_pending:${epoch_pending }<br>
	epoch_disapproved:${ epoch_disapproved}<br>
	epoch_unapproved:${epoch_unapproved }<br>
	hasParent:${hasParent }<br>
	hasCompanions:${ hasCompanions}<br>
	isDataEntryComplete:${isDataEntryComplete }<br>
	epoch_unrandomized:${ epoch_unrandomized}<br>
	actionRequired :${actionRequired}
	actionLabel:${actionLabel}
	registerableWithCompanions :${registerableWithCompanions}
	requiresMultiSite:${requiresMultiSite}
	has_mandatory_companions:${has_mandatory_companions}
	has_child_registrations:${has_child_registrations} 
	mandatoryCompanionsNotCreated:${command.studySubject.workPendingOnMandatoryCompanionRegistrations}  --> 
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
	<c:when test="${newRegistration}">
		<c:choose>
		<c:when test="${command.studySubject.regWorkflowStatus == 'ENROLLED' && command.studySubject.currentScheduledEpoch.scEpochWorkflowStatus == 'REGISTERED' && command.studySubject.currentScheduledEpoch.epoch.enrollmentIndicator == 'true' && !hasParent && !has_mandatory_companions}">
			<font color='<fmt:message key="REGISTRATION.SUCCESS.COLOR"/>'><strong><fmt:message key="REGISTRATION.ENROLLED"/></strong></font>
		</c:when>
		<c:when test="${command.studySubject.regWorkflowStatus == 'REGISTERED_BUT_NOT_ENROLLED' && command.studySubject.currentScheduledEpoch.scEpochWorkflowStatus == 'REGISTERED' && command.studySubject.currentScheduledEpoch.epoch.enrollmentIndicator == 'false' && !hasParent && !hasCompanions}">
			<font color='<fmt:message key="REGISTRATION.SUCCESS.COLOR"/>'><strong><fmt:message key="REGISTRATION.SUCCESS"/></strong></font>
		</c:when>
		<c:when test="${command.studySubject.regDataEntryStatus.code == 'Incomplete'}">
			<font color='<fmt:message key="REGISTRATION.INCOMPLETE.COLOR"/>'><strong><fmt:message key="REGISTRATION.INCOMPLETE"/></strong></font>
		</c:when>
		<c:when test="${reg_registered && hasCompanions && has_child_registrations && command.studySubject.currentScheduledEpoch.scEpochWorkflowStatus == 'REGISTERED'}">
			<font color='<fmt:message key="REGISTRATION.COMPANION.PARENT.REGISTERED.COLOR"/>'><strong><fmt:message key="REGISTRATION.COMPANION.PARENT.REGISTERED"/> Please <a href="javascript:C3PR.printElement('printable');">print</a>
			and save this confirmation in the subject study records </strong></font></c:when>
		<c:when test="${epoch_disapproved && command.studySubject.studySite.study.blindedIndicator && registerableWithCompanions }">
			<font color='<fmt:message key="REGISTRATION.NO_BLINDED_ARM_ASSIGNMENT.COLOR"/>'><strong><fmt:message key="REGISTRATION.NO_BLINDED_ARM_ASSIGNMENT"/></strong></font></c:when>
		<c:when test="${epoch_disapproved && command.studySubject.studySite.study.randomizationType == 'BOOK' && registerableWithCompanions}">
			<font color='<fmt:message key="REGISTRATION.RANDOMIZATION.BOOK.COLOR"/>'><strong><fmt:message key="REGISTRATION.RANDOMIZATION.BOOK"/></strong></font></c:when>
		<c:when test="${reg_registered}">
			<font color='<fmt:message key="REGISTRATION.SUCCESS.COLOR"/>'><strong><fmt:message key="REGISTRATION.SUCCESS"/> Please <a href="javascript:C3PR.printElement('printable');">print</a>
			and save this confirmation in the subject study records </strong></font></c:when>
		<c:when test="${reg_pending}">
			<font color='<fmt:message key="REGISTRATION.PENDING.COLOR"/>'><strong><fmt:message key="REGISTRATION.PENDING"/> </strong></font></c:when>
		<c:when test="${reg_disapproved}">
			<font color='<fmt:message key="REGISTRATION.DISAPPROVED.COLOR"/>'><strong><fmt:message key="REGISTRATION.DISAPPROVED"/> </strong></font></c:when>
		<c:when test="${reg_reserved}">
			<font color='<fmt:message key="REGISTRATION.RESERVED.COLOR"/>'><strong><fmt:message key="REGISTRATION.RESERVED"/> Please <a href="javascript:C3PR.printElement('printable');">print</a>
			and save this confirmation in the subject study records </strong></font></c:when>
		<c:when test="${reg_nonenrolled }">
			<font color='<fmt:message key="REGISTRATION.NONENROLLED.COLOR"/>'><strong><fmt:message key="REGISTRATION.NONENROLLED"/></strong></font></c:when>			
		<c:when test="${reg_unregistered && hasCompanions && !registerableWithCompanions}">
			<font color='<fmt:message key="REGISTRATION.COMPANION.PARENT.INCOMPLETE.COLOR"/>'><strong><fmt:message key="REGISTRATION.COMPANION.PARENT.INCOMPLETE"/></strong></font></c:when>
		<c:when test="${reg_unregistered && hasCompanions && registerableWithCompanions}">
			<font color='<fmt:message key="REGISTRATION.COMPANION.PARENT.READY_FOR_REGISTRATION.COLOR"/>'><strong><fmt:message key="REGISTRATION.COMPANION.PARENT.READY_FOR_REGISTRATION"/></strong></font></c:when>
		<c:when test="${isDataEntryComplete && hasParent}">
			<font color='<fmt:message key="REGISTRATION.COMPANION.CHILD.INCOMPLETE.COLOR"/>'><strong><fmt:message key="REGISTRATION.COMPANION.CHILD.INCOMPLETE"/></strong></font></c:when>	
		<c:when test="${reg_unrandomized}">
			<font color='<fmt:message key="REGISTRATION.UNRANDOMIZED.COLOR"/>'><strong><fmt:message key="REGISTRATION.UNRANDOMIZED"/></strong></font></c:when>
		<c:when test="${command.studySubject.workPendingOnMandatoryCompanionRegistrations}">
			<font color='<fmt:message key="REGISTRATION.COMPANIONS.INCOMPLETE.COLOR"/>'><strong><fmt:message key="REGISTRATION.COMPANIONS.INCOMPLETE"/></strong></font></c:when>
		<c:otherwise>
			<font color='<fmt:message key="REGISTRATION.INCOMPLETE.COLOR"/>'><strong><fmt:message key="REGISTRATION.INCOMPLETE"/></strong></font></c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:choose>
		<c:when test="${command.studySubject.regDataEntryStatus.code == 'Incomplete'}">
			<font color='<fmt:message key="REGISTRATION.INCOMPLETE.COLOR"/>'><strong><fmt:message key="REGISTRATION.INCOMPLETE"/></strong></font></c:when>
		<c:when test="${ command.studySubject.regWorkflowStatus.code == 'Enrolled' && has_mandatory_companions && registerableWithCompanions && command.studySubject.currentScheduledEpoch.scEpochWorkflowStatus == 'REGISTERED'}">
			<font color='<fmt:message key="REGISTRATION.COMPANION.PARENT.REGISTERED.COLOR"/>'><strong><fmt:message key="REGISTRATION.COMPANION.PARENT.REGISTERED"/> Please <a href="javascript:C3PR.printElement('printable');">print</a>
			and save this confirmation in the subject study records </strong></font></c:when>
		<c:when test="${epoch_disapproved && command.studySubject.studySite.study.blindedIndicator && registerableWithCompanions }">
			<font color='<fmt:message key="REGISTRATION.NO_BLINDED_ARM_ASSIGNMENT.COLOR"/>'><strong><fmt:message key="REGISTRATION.NO_BLINDED_ARM_ASSIGNMENT"/></strong></font></c:when>
		<c:when test="${epoch_disapproved && command.studySubject.studySite.study.randomizationType == 'BOOK' && registerableWithCompanions}">
			<font color='<fmt:message key="REGISTRATION.RANDOMIZATION.BOOK.COLOR"/>'><strong><fmt:message key="REGISTRATION.RANDOMIZATION.BOOK"/></strong></font></c:when>
		<c:when test="${hasCompanions && !registerableWithCompanions}">
			<font color='<fmt:message key="REGISTRATION.COMPANION.PARENT.INCOMPLETE.COLOR"/>'><strong><fmt:message key="REGISTRATION.COMPANION.PARENT.INCOMPLETE"/></strong></font></c:when>
		<c:when test="${hasCompanions && registerableWithCompanions}">
			<font color='<fmt:message key="REGISTRATION.COMPANION.PARENT.READY_FOR_REGISTRATION.COLOR"/>'><strong><fmt:message key="REGISTRATION.COMPANION.PARENT.READY_FOR_REGISTRATION"/></strong></font></c:when>
		<c:when test="${isDataEntryComplete && hasParent}">
			<font color='<fmt:message key="REGISTRATION.COMPANION.CHILD.INCOMPLETE.COLOR"/>'><strong><fmt:message key="REGISTRATION.COMPANION.CHILD.INCOMPLETE"/></strong></font></c:when>	
		<c:when test="${epoch_approved}">
			<font color='<fmt:message key="TRANSFER.SUCCESS.COLOR"/>'><strong><fmt:message key="TRANSFER.SUCCESS"/></strong></font></c:when>
		<c:when test="${epoch_pending}">
			<font color='<fmt:message key="TRANSFER.PENDING.COLOR"/>'><strong><fmt:message key="TRANSFER.PENDING"/></strong></font></c:when>
		<c:when test="${epoch_nonenrolled}">
			<font color='<fmt:message key="TRANSFER.NONENROLLED.COLOR"/>'><strong><fmt:message key="TRANSFER.NONENROLLED"/></strong></font></c:when>
		<c:when test="${epoch_unrandomiized}">
			<font color='<fmt:message key="TRANSFER.UNRANDOMIZED.COLOR"/>'><strong><fmt:message key="TRANSFER.UNRANDOMIZED"/></strong></font></c:when>
		<c:otherwise>
			<font color='<fmt:message key="TRANSFER.INCOMPLETE.COLOR"/>'><strong><fmt:message key="TRANSFER.INCOMPLETE"/></strong></font></c:otherwise>
		</c:choose>
	</c:otherwise>
	</c:choose>
	<br><br>
	<div id="printable">
		<div class="row">
			<div class="label"><b><fmt:message key="participant.subjectMRN"/></b> &nbsp; :&nbsp;</div>
			<div class="value">${command.studySubject.participant.primaryIdentifier}</div>
		</div>
		<div class="row">
            <div class="label"><b><fmt:message key="registration.registrationIdentifier"/></b>&nbsp; :&nbsp;</div>
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
			<div class="label"><b><fmt:message key="study.shortTitle"/></b>&nbsp; :&nbsp;</div>
			<div class="value">${command.studySubject.studySite.study.shortTitleText}</div>
		</div>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.registrationStatus"/></b>&nbsp; :&nbsp;</div>
			<div class="value">${command.studySubject.regWorkflowStatus.code }</div>
		</div>		
		<div class="row">
			<div class="label"><b><fmt:message key="registration.currentEpoch"/></b>&nbsp; :&nbsp;</div>
			<div class="value">${command.studySubject.scheduledEpoch.epoch.name}</div>
		</div>
		<c:if test="${!empty armAssigned}">
			<div class="row">
				<div class="label"><b>${armAssignedLabel }</b>&nbsp; :&nbsp;</div>
				<div class="value">${armAssigned}</div>
			</div>
		</c:if>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.site"/></b>&nbsp; :&nbsp;</div>
			<div class="value">${command.studySubject.studySite.healthcareSite.name}</div>
		</div>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.startDate"/></b>&nbsp; :&nbsp;</div>
			<div class="value">${command.studySubject.startDateStr }</div>
		</div>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.consentSignedDate"/></b>&nbsp; :&nbsp;</div>
			<div class="value">${command.studySubject.informedConsentSignedDateStr}</div>
		</div>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.consentVesion"/></b>&nbsp; :&nbsp;</div>
			<div class="value">${command.studySubject.informedConsentVersion}</div>
		</div>
		<div class="row">
			<div class="label"><b><fmt:message key="registration.enrollingPhysician"/></b>&nbsp; :&nbsp;</div>
			<c:choose>
				<c:when test="${!empty command.studySubject.treatingPhysicianFullName}">
					<div class="value">${command.studySubject.treatingPhysicianFullName}</div>
				</c:when>
				<c:otherwise>
					<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
				</c:otherwise>
			</c:choose>
		</div>
	<br>
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
	<c:if test="${command.studySubject.dataEntryStatusString!='Incomplete' && empty command.studySubject.parentStudySubject}">
		<div align="right">
			<form id="manage" name="manage" action="../registration/manageRegistration" method="get">
				<input type="hidden" name="registrationId" value="${command.studySubject.systemAssignedIdentifiers[0] }"/>
				<input type="button" value="Manage this registration" onClick='doManage("${formType}",paramString)'/>
			</form>
		</div>
	</c:if>
	<br>
	<div align="right">
		<c:if test="${hasCompanions && command.studySubject.dataEntryStatusString=='Complete' && command.studySubject.scheduledEpoch.epoch.enrollmentIndicator=='true'}">
			<input type="button" id="manageCompanionStudy" value="Manage Companion Registration" onclick="manageCompanions();"/>
		</c:if>	
	</div>
	<div align="right">
		<c:if test="${not empty command.studySubject.parentStudySubject}">
			<input type="button" name="close" value="Close" onclick="parent.closePopup();">
		</c:if>
	</div>

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

</body>
</html>