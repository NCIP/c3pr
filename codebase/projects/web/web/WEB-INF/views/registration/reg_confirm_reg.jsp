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
function createReg(studyId,studySiteName, studySiteVersionId){
	$('from_reg_confirmation').value=true;
	$('create_studyId').value=studyId;
	$('create_studySiteName').value= studySiteName;
	$('create_studySiteStudyVersionId').value=studySiteVersionId;
	$('create').submit();
}

</script>

</head>
<body>
<tags:controlPanel>
	<form id="manage" name="manage" action="../registration/manageRegistration" method="get" style="display:inline;">
		<input type="hidden" name="registrationId" value="${command.studySubject.systemAssignedIdentifiers[0] }"/>
		<tags:oneControlPanelItem imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Register another subject" linkhref="javascript:doManage('create',paramString)"/>
		<c:if test="${command.studySubject.scheduledEpoch.scEpochWorkflowStatus != 'ON_EPOCH'}">
			<tags:oneControlPanelItem imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit registration" linkhref="javascript:doManage('edit',paramString)"/>
		</c:if>
		<c:if test="${command.studySubject.regWorkflowStatus != 'PENDING'}">
			<tags:oneControlPanelItem imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_manageThisReg.png" linktext="Manage registration" linkhref="javascript:doManage('manage',paramString)"/>
		</c:if>
	</form>
 <tags:oneControlPanelItem linkhref="javascript:launchPrint()" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_printer.png" linktext="Print" />
</tags:controlPanel>
<form action="../registration/manageRegistration?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>" method="post" id="manageCompanion">
	<input type="hidden" name="_page1" id="_page" value="1"/>
	<input type="hidden" name="_target1" id="_target" value="1"/>
</form>
<form action="../registration/createRegistration" method="get" id="create">
	<input type="hidden" name="_target0" id="_target" value="0"/>
	<input type="hidden" name="from_reg_confirmation" id="from_reg_confirmation" value="false"/>
	<input type="hidden" name="create_studySiteName" id="create_studySiteName" value=""/>
	<input type="hidden" name="create_studyId" id="create_studyId" value=""/>
	<input type="hidden" name=create_studySiteStudyVersionId id="create_studySiteStudyVersionId" value=""/>
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
	<div id="registrationSummary">
		<chrome:division id="Subject Information" title="Subject">
			<div class="row">
				<div class="label"><fmt:message key="participant.fullName"/>:</div>
				<div class="value">${command.participant.fullName}</div>
			</div>
			<div class="row">
				<div class="label"><fmt:message key="participant.MRN"/>:</div>
				<div class="value">${command.participant.MRN.value }</div>
			</div>
		</chrome:division>
		<chrome:division id="Parent Registration Information" title="${command.studySubject.studySite.study.shortTitleText} (${command.studySubject.studySite.study.primaryIdentifier})">
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
							<c:if test="${size - status.index > 1}"><br></c:if>
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
				<div class="row">
					<div class="label"><b><fmt:message key="registration.enrollingSite"/></b>:</div>
					<div class="value">${childStudySubject.studySite.healthcareSite.name}</div>
				</div>
				<div class="row">
					<div class="label"><b><fmt:message key="registration.startDate"/></b>:</div>
					<div class="value">${childStudySubject.startDateStr}</div>
				</div>			
				<div class="row">
					<div class="label"><b>Consents </b>:</div>
					<div class="value">
						<c:set var="size" value="${fn:length(childStudySubject.studySubjectStudyVersion.studySubjectConsentVersions)}"></c:set>
						<c:forEach items="${childStudySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="studySubjectConsentVersion" varStatus="status">
							${studySubjectConsentVersion.informedConsentSignedDateStr} (${studySubjectConsentVersion.consent.name})
							<c:if test="${size - status.index > 1}"><br></c:if>
						</c:forEach>
					</div>
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
	<div id="printable" style="display:none;">
	<h3><b>Subject</b></h3>
		<hr></hr>
		<table>
			<tr>
				<td align="right"><b><fmt:message key="participant.fullName"/>:</b></td>
				<td align="left">${command.participant.fullName}</td>
			</tr>
			<tr>
				<td align="right"><b><fmt:message key="participant.MRN"/>:</b></td>
				<td align="left">${command.participant.MRN.value }</td>
			</tr>
		</table> 
		
	<h3><b>${command.studySubject.studySite.study.shortTitleText} (${command.studySubject.studySite.study.primaryIdentifier})</b></h3>
		<hr></hr>
		<table>
			<tr>
				<td align="right"><b><fmt:message key="registration.registrationIdentifier"/>:</b></td>
				<td align="left">${command.studySubject.coOrdinatingCenterIdentifier.value}</td>
			</tr>
			<tr>
				<td align="right"><b><fmt:message key="study.version.name"/>:</b></td>
				<td align="left">${command.studySubject.studySiteVersion.studyVersion.name }</td>
			</tr>
			<tr>
				<td align="right"><b><fmt:message key="registration.registrationStatus"/>:</b></td>
				<td align="left">${command.studySubject.regWorkflowStatus.code}</td>
			</tr>
			
			<tr>
				<td align="right"><b><fmt:message key="registration.startDate"/>:</b></td>
				<td align="left">${command.studySubject.startDateStr }</td>
			</tr>
			
			<tr>
				<td align="right"><b><fmt:message key="registration.enrollingPhysician"/>:</b></td>
				<c:choose>
					<c:when test="${!empty command.studySubject.treatingPhysicianFullName}">
						<td align="left">${command.studySubject.treatingPhysicianFullName }</td>
					</c:when>
					<c:otherwise>
						<td align="left"><i><fmt:message key="c3pr.common.noSelection"/></i></td>
					</c:otherwise>
				</c:choose>
			</tr>
			
			<tr>
				<td align="right"><b><fmt:message key="c3pr.common.epoch"/>:</b></td>
				<td align="left">${command.studySubject.scheduledEpoch.epoch.name }</td>
			</tr>
			<c:if test="${!empty armAssigned}">
				<tr>
					<td align="right"><b><fmt:message key="armAssignedLabel"/>:</b></td>
					<td align="left">${armAssigned}</td>
				</tr>
			</c:if>
			<tr>
				<td align="right"><b><fmt:message key="scheduledEpoch.startDate"/>:</b></td>
				<c:choose>
					<c:when test="${!empty command.studySubject.scheduledEpoch.startDateStr}">
						<td align="left">${command.studySubject.scheduledEpoch.startDateStr }</td>
					</c:when>
					<c:otherwise>
						<td align="left"><i><fmt:message key="c3pr.common.noSelection"/></i></td>
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td align="right"><b><fmt:message key="registration.enrollingSite"/>:</b></td>
				<td align="left">${command.studySubject.studySite.healthcareSite.name }</td>
			</tr>
			
			<tr>
				<td align="right"><b>Consents </b></td>
				<td align="left"><c:set var="size" value="${fn:length(command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions)}"></c:set>
						<c:forEach items="${command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="studySubjectConsentVersion" varStatus="status">
							${studySubjectConsentVersion.informedConsentSignedDateStr} (${studySubjectConsentVersion.consent.name})
							<c:if test="${size - status.index > 1}"><br></c:if>
						</c:forEach>
				</td>
			</tr>
			
		</table> 
		
		<c:forEach items="${command.studySubject.childStudySubjects}" var="childStudySubject" varStatus="status">
			<c:if test="${newRegistration || (!newRegistration && !previous_epoch_enrollment_indicator )}">
				<h3><b>${childStudySubject.studySite.study.shortTitleText} (${childStudySubject.studySite.study.primaryIdentifier})</b></h3>
				<hr></hr>
				<table>
					<tr>
						<td align="right"><b><fmt:message key="registration.registrationIdentifier"/>:</b></td>
						<td align="left">${childStudySubject.coOrdinatingCenterIdentifier.value}</td>
					</tr>
					<tr>
						<td align="right"><b><fmt:message key="study.shortTitle"/>:</b></td>
						<td align="left">${childStudySubject.studySite.study.shortTitleText }</td>
					</tr>
					<tr>
						<td align="right"><b><fmt:message key="registration.registrationStatus"/>:</b></td>
						<td align="left">${childStudySubject.regWorkflowStatus.code}</td>
					</tr>
					<tr>
						<td align="right"><b><fmt:message key="c3pr.common.epoch"/>:</b></td>
						<td align="left">${childStudySubject.scheduledEpoch.epoch.name }</td>
					</tr>
					<c:if test="${!empty childStudySubject.scheduledEpoch.scheduledArm}">
						<tr>
							<td align="right"><b><fmt:message key="armAssignedLabel"/>:</b></td>
							<td align="left">${childStudySubject.scheduledEpoch.scheduledArm.arm.name}</td>
						</tr>
					</c:if>
					<tr>
						<td align="right"><b><fmt:message key="registration.enrollingSite"/>:</b></td>
						<td align="left">${childStudySubject.studySite.healthcareSite.name }</td>
					</tr>
					<tr>
						<td align="right"><b><fmt:message key="registration.startDate"/>:</b></td>
						<td align="left">${childStudySubject.startDateStr }</td>
					</tr>
					
					<c:forEach items="${childStudySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="childStudySubjectConsentVersion" varStatus="status">
						<tr>
						<td align="right"><b>Informed Consent ${status.index+1}:</b></td>
						<td align="left">${childStudySubjectConsentVersion.informedConsentSignedDateStr} (${childStudySubjectConsentVersion.consent.name})</td>
					</tr>
					</c:forEach>
					
					<tr>
						<td align="right"><b><fmt:message key="registration.enrollingPhysician"/>:</b></td>
						<c:choose>
							<c:when test="${!empty childStudySubject.treatingPhysicianFullName}">
								<td align="left">${childStudySubject.treatingPhysicianFullName }</td>
							</c:when>
							<c:otherwise>
								<td align="left"><i><fmt:message key="c3pr.common.noSelection"/></i></td>
							</c:otherwise>
						</c:choose>
					</tr>
				</table> 
			</c:if>
		</c:forEach>
	</div>
	<script>
		function doManage(formName, idParamStr){
			if(formName=='create'){
				var studyId = ${command.studySubject.studySubjectStudyVersion.studySiteStudyVersion.studyVersion.study.id};
				var studySiteName = ''+ '${command.studySubject.studySite.healthcareSite.name}' + '';
				var studySiteStudyVersionId = ${command.studySubject.studySubjectStudyVersion.studySiteStudyVersion.id};
				createReg(studyId,studySiteName,studySiteStudyVersionId);
			}else if(formName=='manage'){
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
</body>
</html>