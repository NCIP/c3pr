<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><registrationTags:htmlTitle registration="${command}" /></title>
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

function manageCompanions(registrationId){
	$('manageCompanion').submit();
}
</script>
</head>
<body>
<form action="../registration/manageRegistration?registrationId=${command.id }" method="post" id="manageCompanion">
	<input type="hidden" name="_page" id="_page0" value="0"/>
	<input type="hidden" name="_target2" id="_target2" value="2"/>
	<input type="hidden" name="goToTab" id="goToTab" value="true"/>
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
	registerableWithCompanions :${registerableWithCompanions}  --> 
	<c:choose>
	<c:when test="${newRegistration}">
		<c:choose>
		<c:when test="${reg_registered && hasCompanions}">
			<font color='<fmt:message key="REGISTRATION.COMPANION.PARENT.REGISTERED.COLOR"/>'><strong><fmt:message key="REGISTRATION.COMPANION.PARENT.REGISTERED"/> Please <a href="javascript:C3PR.printElement('printable');">print</a>
			and save this confirmation in the subject study records </strong></font></c:when>
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
		<c:otherwise>
			<font color='<fmt:message key="REGISTRATION.INCOMPLETE.COLOR"/>'><strong><fmt:message key="REGISTRATION.INCOMPLETE"/></strong></font></c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:choose>
		<c:when test="${reg_registered && hasCompanions}">
			<font color='<fmt:message key="REGISTRATION.COMPANION.PARENT.REGISTERED.COLOR"/>'><strong><fmt:message key="REGISTRATION.COMPANION.PARENT.REGISTERED"/> Please <a href="javascript:C3PR.printElement('printable');">print</a>
			and save this confirmation in the subject study records </strong></font></c:when>
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
		<c:when test="${epoch_disapproved}">
			<font color='<fmt:message key="TRANSFER.DISAPPROVED.COLOR"/>'><strong><fmt:message key="TRANSFER.DISAPPROVED"/></strong></font></c:when>
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
	<table width="50%" class="tablecontent">
		<tr>
			<td width="35%" align="left"><b>Subject MRN</b></td>
			<td>${command.participant.primaryIdentifier}</td>
		</tr>
		<tr>
			<td align="left"><b>Study Sponsor Identifier</b></td>
			<td>${command.studySite.study.organizationAssignedIdentifiers[0].value}</td>
		</tr>
		<tr>
			<td align="left"><b>Study Short Title</b></td>
			<td valign="top">${command.studySite.study.shortTitleText}</td>
		</tr>
		<tr>
			<td align="left"><b>Registration Status</b></td>
			<td valign="top">${command.regWorkflowStatus.code }</td>
		</tr>		
		<tr>
			<td align="left"><b>Current Epoch</b></td>
			<td valign="top">${command.scheduledEpoch.epoch.name}</td>
		</tr>
		<tr>
			<td align="left"><b>Current Epoch Status</b></td>
			<td valign="top">${command.scheduledEpoch.scEpochWorkflowStatus.code}</td>
		</tr>
		<c:if test="${!empty armAssigned}">
			<tr>
				<td align="left"><b>${armAssignedLabel }</b></td>
				<td valign="top">${armAssigned}</td>
			</tr>
		</c:if>
		<tr>
			<td align="left"><b>Data Entry Status</b></td>
			<td valign="top">${command.dataEntryStatusString }</td>
		</tr>
		<tr>
			<td align="left"><b>Site</b></td>
			<td>${command.studySite.healthcareSite.name}</td>
		</tr>
		<tr>
			<td align="left"><b>Registration Date</b></td>
			<td>${command.startDateStr }</td>
		</tr>
		<tr>
			<td align="left"><b>Enrolling Physician</b></td>
			<td>${command.treatingPhysicianFullName}</td>
		</tr>
	</table>
	<br>
	</div>

	
	<c:if test="${hotlinkEnable}">
	<table width="60%">
		<c:if test="${!empty caaersBaseUrl}">
		<tr>
			<td align="left"><a
				href="javascript:accessApp('${caaersBaseUrl }','_caaers');">
			<b>Adverse Event Reporting</b></a> </td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
		</c:if>
		<c:if test="${!empty pscBaseUrl}">
		<tr>
			<td align="left"><a
				href="javascript:accessApp('${pscBaseUrl }','_psc');">
			<b>Study Calendar</b></a></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
		</c:if>
		<c:if test="${!empty c3dBaseUrl}">
		<tr>
			<td align="left"><a
				href="javascript:accessApp('${c3dBaseUrl }','_c3d');">
			<b>Clinical Database</b></a></td>
		</tr>
		</c:if>
	</table>
	</c:if>
	<c:if test="${command.dataEntryStatusString!='Incomplete' && empty command.parentStudySubject}">
		<div align="right">
			<form id="manage" name="manage" action="../registration/manageRegistration" method="get">
				<input type="hidden" name="registrationId" value="${command.id }"/>
				<input type="submit" value="Manage this registration"/>
			</form>
		</div>
	</c:if>
	<br>
	<div align="right">
		<c:if test="${hasCompanions && command.dataEntryStatusString=='Complete' && command.scheduledEpoch.epoch.enrollmentIndicator=='true'}">
			<input type="button" id="manageCompanionStudy" value="Manage Companion Registration" onclick="manageCompanions('${command.id}');"/>
		</c:if>	
	</div>
	<div align="right">
		<c:if test="${not empty command.parentStudySubject}">
			<input type="button" name="close" value="Close" onclick="parent.closePopup();">
		</c:if>
	</div>

<c:choose>
	<c:when test="${param.create_companion != 'true'}">
	</div>
	
	<c:if test="${hotlinkEnable}">
	<%--<table width="60%">
		<c:if test="${!empty caaersBaseUrl}">
		<tr>
			<td align="left"><a
				href="javascript:accessApp('${caaersBaseUrl }','_caaers');">
			<b>Adverse Event Reporting</b></a> </td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
		</c:if>
		<c:if test="${!empty pscBaseUrl}">
		<tr>
			<td align="left"><a
				href="javascript:accessApp('${pscBaseUrl }','_psc');">
			<b>Study Calendar</b></a></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
		</c:if>
		<c:if test="${!empty c3dBaseUrl}">
		<tr>
			<td align="left"><a
				href="javascript:accessApp('${c3dBaseUrl }','_c3d');">
			<b>Clinical Database</b></a></td>
		</tr>
		</c:if>
	</table>--%>
	  <ul>
    	<c:if test="${!empty caaersBaseUrl}">
	    <li><a href="${caaersBaseUrl }" target="${caaers_window }"><b>Adverse Event Reporting System</a></li>
	    </c:if>
		<c:if test="${!empty pscBaseUrl}">
	    <li><a href="${pscBaseUrl }" target="${psc_window }">Patient Study Calendar</a></li>
	    </c:if>
		<c:if test="${!empty c3dBaseUrl}">
	    <li><a href="${c3dBaseUrl }" target="${c3d_window }">Cancer Central Clinical Database</a></li>
	    </c:if>
	  </ul>
	</c:if>
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>
</tags:panelBox>
<form id="hotlinksForm" action="" method="get">
<input type="hidden" name="assignment" value="${command.gridId }"/>
</form>
<c:if test="${registerableWithCompanions &&(actionRequired || hasCompanions)}">
<tags:panelBox>
	<registrationTags:register registration="${command}" newReg="${newRegistration}" actionButtonLabel="${actionLabel}" requiresMultiSite="${requiresMultiSite}"/>
</tags:panelBox>
</c:if>
</body>
</html>