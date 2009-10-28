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

</head>
<body>
<tags:controlPanel>
 <c:if test="${command.studySubject.dataEntryStatusString!='Incomplete' }">
	<form id="manage" name="manage" action="../registration/manageRegistration" method="get" style="display:inline;">
		<input type="hidden" name="registrationId" value="${command.studySubject.systemAssignedIdentifiers[0] }"/>
		<c:if test="${command.studySubject.scheduledEpoch.scEpochWorkflowStatus != 'REGISTERED'}">
			<tags:oneControlPanelItem imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit registration" linkhref="javascript:doManage('edit',paramString)"/>
		</c:if>
		<c:if test="${command.studySubject.regWorkflowStatus != 'PENDING'}">
			<tags:oneControlPanelItem imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_manageThisReg.png" linktext="Manage registration" linkhref="javascript:doManage('manage',paramString)"/>
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
		<div id="flash-message" class="${imageAndMessageList[0]}">
			<img src="<tags:imageUrl name="${imageAndMessageList[0] == 'info' ? 'check.png' :'error-red.png'}" />" alt="" style="vertical-align:top;" /> 
			<fmt:message key="${imageAndMessageList[1]}"/>&nbsp;
			<c:if test="${fn:contains(imageAndMessageList[1],'site.action.error')}">
					${imageAndMessageList[2] }&nbsp;
			</c:if>
			<c:if test="${imageAndMessageList[0] == 'info'}">
				Please <a href="javascript:launchPrint();">print</a> and save this confirmation in the subject's records.
			</c:if>
		</div>
	<div id="printable">
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
		<chrome:division id="Parent Registration Information" title="${command.studySubject.studySite.study.shortTitleText} (${command.studySubject.studySite.study.primaryIdentifier})">
			<div class="row">
				<div class="label"><b><fmt:message key="study.shortTitle"/></b>:</div>
				<div class="value">${command.studySubject.studySite.study.shortTitleText}</div>
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
				<div class="label"><b><fmt:message key="c3pr.common.epoch"/></b>:</div>
				<div class="value">${command.studySubject.scheduledEpoch.epoch.name}</div>
			</div>
			<div class="row">
				<div class="label"><b><fmt:message key="scheduledEpoch.startDate"/></b>:</div>
				<c:choose>
					<c:when test="${!empty command.studySubject.scheduledEpoch.startDateStr}">
						<div class="value">${command.studySubject.scheduledEpoch.startDateStr}</div>
					</c:when>
					<c:otherwise>
						<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
					</c:otherwise>
				</c:choose>
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
					<div class="label"><b>Consents </b>:</div>
					<div class="value">
						<c:set var="size" value="${fn:length(command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions)}"></c:set>
						<c:forEach items="${command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="studySubjectConsentVersion" varStatus="status">
							${studySubjectConsentVersion.informedConsentSignedDateStr} (${studySubjectConsentVersion.consent.name})
							<c:if test="${size - status.index > 1}">,<br></c:if>
						</c:forEach>
						
					</div>
			</div>
		</chrome:division>
		<c:forEach items="${command.studySubject.childStudySubjects}" var="childStudySubject" varStatus="status">
			<c:if test="${newRegistration || (!newRegistration && !previous_epoch_enrollment_indicator )}">
			<chrome:division id="companionRegInfo${status.index}" title="${childStudySubject.studySite.study.shortTitleText} (${childStudySubject.studySite.study.primaryIdentifier})">
				<div class="row">
		            <div class="label"><b><fmt:message key="registration.registrationIdentifier"/></b>:</div>
					<c:choose>
						<c:when test="${!empty childStudySubject.coOrdinatingCenterIdentifier.value}">
							<div class="value">${childStudySubject.coOrdinatingCenterIdentifier.value}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.notGenerated"/></span></div>
						</c:otherwise>
					</c:choose>
		        </div>
				<div class="row">
					<div class="label"><b><fmt:message key="study.shortTitle"/></b>:</div>
					<div class="value">${childStudySubject.studySite.study.shortTitleText}</div>
				</div>
				<div class="row">
					<div class="label"><b><fmt:message key="registration.registrationStatus"/></b>:</div>
					<div class="value">${childStudySubject.regWorkflowStatus.code }</div>
				</div>		
				<div class="row">
					<div class="label"><b><fmt:message key="c3pr.common.epoch"/></b>:</div>
					<div class="value">${childStudySubject.scheduledEpoch.epoch.name}</div>
				</div>
				<c:if test="${!empty armAssigned}">
					<div class="row">
						<div class="label"><b>${armAssignedLabel }</b>:</div>
						<div class="value">${armAssigned}</div>
					</div>
				</c:if>
				<div class="row">
					<div class="label"><b><fmt:message key="registration.enrollingSite"/></b>:</div>
					<div class="value">${childStudySubject.studySite.healthcareSite.name}</div>
				</div>
				<div class="row">
					<div class="label"><b><fmt:message key="registration.startDate"/></b>:</div>
					<div class="value">${childStudySubject.startDateStr}</div>
				</div>
				<c:forEach items="${childStudySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="studySubjectConsentVersion" varStatus="status">
					<div class="row">
						<div class="label"><b>Informed Consent ${status.index+1}</b>:</div>
						<div class="value">${studySubjectConsentVersion.informedConsentSignedDateStr} (${studySubjectConsentVersion.consent.name})</div>
					</div>
				</c:forEach>
					
				<div class="row">
					<div class="label"><b><fmt:message key="registration.consentVersion"/></b>:</div>
					<div class="value">${childStudySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].consent.name}</div>
				</div>
				<div class="row">
					<div class="label"><b><fmt:message key="registration.enrollingPhysician"/></b>:</div>
					<c:choose>
						<c:when test="${!empty childStudySubject.treatingPhysicianFullName}">
							<div class="value">${childStudySubject.treatingPhysicianFullName}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
				</div>
			</chrome:division>
			</c:if>
		</c:forEach>	
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