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
	  <c:if test="${command.studySubject.dataEntryStatusString!='Incomplete' && empty command.studySubject.parentStudySubject}">
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
			<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="REGISTRATION.ENROLLED"/></div>
		</c:when>
		<c:when test="${command.studySubject.regWorkflowStatus == 'REGISTERED_BUT_NOT_ENROLLED' && command.studySubject.currentScheduledEpoch.scEpochWorkflowStatus == 'REGISTERED' && command.studySubject.currentScheduledEpoch.epoch.enrollmentIndicator == 'false' && !hasParent && !hasCompanions}">
			<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="REGISTRATION.SUCCESS"/></div>
		</c:when>
		<c:when test="${command.studySubject.regDataEntryStatus.code == 'Incomplete'}">
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="REGISTRATION.INCOMPLETE"/></div>
		</c:when>
		<c:when test="${reg_registered && hasCompanions && has_child_registrations && command.studySubject.currentScheduledEpoch.scEpochWorkflowStatus == 'REGISTERED'}">
			<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="REGISTRATION.COMPANION.PARENT.REGISTERED"/> Please print.
			and save this confirmation in the subject study records </div></c:when>
		<c:when test="${epoch_disapproved && command.studySubject.studySite.study.blindedIndicator && registerableWithCompanions }">
			<div id="flash-message"><fmt:message key="REGISTRATION.NO_BLINDED_ARM_ASSIGNMENT"/></div></c:when>
		<c:when test="${epoch_disapproved && command.studySubject.studySite.study.randomizationType == 'BOOK' && registerableWithCompanions}">
			<div id="flash-message"><fmt:message key="REGISTRATION.RANDOMIZATION.BOOK"/></div></c:when>
		<c:when test="${reg_registered}">
			<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="REGISTRATION.SUCCESS"/> You may <a href="javascript:launchPrint();">print</a>
			and save this confirmation in the subject study records </div></c:when>
		<c:when test="${reg_pending}">
			<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="REGISTRATION.PENDING"/> </div></c:when>
		<c:when test="${reg_disapproved}">
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="REGISTRATION.DISAPPROVED"/> </div></c:when>
		<c:when test="${reg_reserved}">
			<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="REGISTRATION.RESERVED"/> You may <a href="javascript:launchPrint();">print</a>
			and save this confirmation in the subject study records.</div></c:when>
		<c:when test="${reg_nonenrolled }">
			<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="REGISTRATION.NONENROLLED"/></div></c:when>			
		<c:when test="${reg_unregistered && hasCompanions && !registerableWithCompanions}">
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="REGISTRATION.COMPANION.PARENT.INCOMPLETE"/></div></c:when>
		<c:when test="${reg_unregistered && hasCompanions && registerableWithCompanions}">
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="REGISTRATION.COMPANION.PARENT.READY_FOR_REGISTRATION"/></div></c:when>
		<c:when test="${isDataEntryComplete && hasParent}">
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="REGISTRATION.COMPANION.CHILD.INCOMPLETE"/></div></c:when>	
		<c:when test="${reg_unrandomized}">
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="REGISTRATION.UNRANDOMIZED"/></div></c:when>
		<c:when test="${command.studySubject.workPendingOnMandatoryCompanionRegistrations}">
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="REGISTRATION.COMPANIONS.INCOMPLETE"/></div></c:when>
		<c:otherwise>
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="REGISTRATION.INCOMPLETE"/></div></c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:choose>
		<c:when test="${command.studySubject.regDataEntryStatus.code == 'Incomplete'}">
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="REGISTRATION.INCOMPLETE"/></div></c:when>
		<c:when test="${ command.studySubject.regWorkflowStatus.code == 'Enrolled' && has_mandatory_companions && registerableWithCompanions && command.studySubject.currentScheduledEpoch.scEpochWorkflowStatus == 'REGISTERED'}">
			<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="REGISTRATION.COMPANION.PARENT.REGISTERED"/> You may <a href="javascript:launchPrint();">print</a>
			and save this confirmation in the subject study records </div></c:when>
		<c:when test="${epoch_disapproved && command.studySubject.studySite.study.blindedIndicator && registerableWithCompanions }">
			<div id="flash-message"><fmt:message key="REGISTRATION.NO_BLINDED_ARM_ASSIGNMENT"/></div></c:when>
		<c:when test="${epoch_disapproved && command.studySubject.studySite.study.randomizationType == 'BOOK' && registerableWithCompanions}">
			<div id="flash-message"><fmt:message key="REGISTRATION.RANDOMIZATION.BOOK"/></div></c:when>
		<c:when test="${hasCompanions && !registerableWithCompanions}">
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="REGISTRATION.COMPANION.PARENT.INCOMPLETE"/></div></c:when>
		<c:when test="${hasCompanions && registerableWithCompanions}">
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="REGISTRATION.COMPANION.PARENT.READY_FOR_REGISTRATION"/></div></c:when>
		<c:when test="${isDataEntryComplete && hasParent}">
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="REGISTRATION.COMPANION.CHILD.INCOMPLETE"/></div></c:when>	
		<c:when test="${epoch_approved}">
			<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="TRANSFER.SUCCESS"/></div></c:when>
		<c:when test="${epoch_pending}">
			<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="TRANSFER.PENDING"/></div></c:when>
		<c:when test="${epoch_nonenrolled}">
			<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="TRANSFER.NONENROLLED"/></div></c:when>
		<c:when test="${epoch_unrandomiized}">
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="TRANSFER.UNRANDOMIZED"/></div></c:when>
		<c:otherwise>
			<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:top;" /> <fmt:message key="TRANSFER.INCOMPLETE"/></div></c:otherwise>
		</c:choose>
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