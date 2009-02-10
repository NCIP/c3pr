<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>

    <style type="text/css">
		#main {
			top:30px;
		}
    </style>

    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <script>
    	function showEndpointError(){
			Dialog.alert({url: $('command').action, options: {method: 'post', parameters:"decorator=nullDecorator&_asynchronous=true&_asyncMethodName=showEndpointMessage&_asyncViewName=/registration/asynchronous/endpoint_display&_target${tab.number}=${tab.number}&_page=${tab.number}", asynchronous:true, evalScripts:true}},              
							{className: "alphacube", width:540, okLabel: "Done"});
		}
    	
        function getBroadcastStatus() {
            $('viewDetails').disable('broadcastBtn');
            $('viewDetails').disable('broadcastStatusBtn');

	        <tags:tabMethod method="getMessageBroadcastStatus" onComplete="onBroadcastComplete"
	        viewName="/ajax/broadcast_res" divElement="'broadcastResponse'"
	        formName="'viewDetails'" params="dontSave=true"/>
        }

      paramString="<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>";
        
      function doSendMessageToESB() {
          $('broadcastResponse').innerHTML = 'Sending Message...';

          $('viewDetails').disable('broadcastBtn');
          $('viewDetails').disable('broadcastStatusBtn');

	        <tags:tabMethod method="broadcastRegistration"
	       viewName="/ajax/broadcast_res" onComplete="onBroadcastComplete"
	       divElement="'broadcastResponse'" formName="'tabMethodForm'" params="dontSave=true"/>
        }

        function onBroadcastComplete() {
            $('viewDetails').enable('broadcastBtn');
            $('viewDetails').enable('broadcastStatusBtn');
        }
        
		function accessApp(url,targetWindow){
			$('hotlinksForm').target=targetWindow;
			$('hotlinksForm').action=url;
			$('hotlinksForm').submit();
		}
		function createReg(studySite, participant, parentRegistrationId){
			$('create_studySite').value=studySite;
			$('create_participant').value=participant;
			$('create_parent_id').value=parentRegistrationId;
			$('create').submit();
		}

		function closePopup() {
			win.close();
		}

		function updateConsentVersion(registartionId){
			win = new Window({title: "Reconsent", 
				zIndex:100, width:500, height:180 , minimizable:false, maximizable:false,
				recenterAuto:true, className :"mac_os_x",
				url: "<c:url value='/pages/registration/updateConsentVersion?decorator=noheaderDecorator&registartionId='/>" + registartionId, 
				showEffectOptions: {duration:1.5}
					}
				) 
			win.showCenter(true)
		}
	
		function takeSubjectOffStudy(registartionId){
			win = new Window({title: "Take subject off study", 
				zIndex:100, width:400, height:200 ,minimizable:false, maximizable:false,
				recenterAuto:true, className :"mac_os_x",
				url: "<c:url value='/pages/registration/takeSubjectOffStudy?decorator=noheaderDecorator&registartionId='/>" + registartionId, 
				showEffectOptions: {duration:1.5}
					}
				) 
			win.showCenter(true)
		}

		function changeEpochPopup(){
			var arr= $$("#changeEpoch");
			win = new Window({maximizable: false, className :"mac_os_x", title: "Change Epoch", 
									hideEffect:Element.hide, 
									zIndex:100, width:850, height:350 , minimizable:false, maximizable:false,
									showEffect:Element.show, 
									}) 
			win.setContent(arr[0]) ;
			win.showCenter(true);
	 	}

		function editRegistrationPopup(){
			var arr= $$("#editRegistration");
			win = new Window({maximizable: false, className :"mac_os_x", title: "Edit Registration", 
									hideEffect:Element.hide, 
									zIndex:100, width:550, height:400 , minimizable:false, maximizable:false,
									showEffect:Element.show, 
									}) 
			win.setContent(arr[0]) ;
			win.showCenter(true);
	 	}

		function refreshEnrollmentSection(regId){
			<tags:tabMethod method="refreshEnrollmentSection" divElement="'enrollmentSection'" formName="'command'"  viewName="/registration/enrollmentSection" javaScriptParam="'registrationId='+regId"/>
		}
    </script>
</head>
<body>
	<tags:controlPanel>
		<c:if test="${canEdit}">
			<tags:oneControlPanelItem linkhref="javascript:editRegistrationPopup();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit" />
		</c:if>
		<csmauthz:accesscontrol domainObject="${command.studySubject}" hasPrivileges="UPDATE" authorizationCheckName="domainObjectAuthorizationCheck">
			<tags:oneControlPanelItem linkhref="javascript:changeEpochPopup();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_changeEpoch.png" linktext="Change Epoch" />
			<c:if test="${reconsentRequired}">
				<tags:oneControlPanelItem linkhref="javascript:updateConsentVersion(${command.studySubject.id});" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_reconsent.png" linktext="Reconsent" />
			</c:if>
	    	<c:if test="${takeSubjectOffStudy}">
				<tags:oneControlPanelItem linkhref="javascript:takeSubjectOffStudy(${command.studySubject.id});" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_takesubjoff.png" linktext="Take subject off study" />
			</c:if>
    	</csmauthz:accesscontrol>
		<tags:oneControlPanelItem linkhref="javascript:$('exportForm')._target.name='xxxx';$('exportForm').submit();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_xml.png" linktext="Export XML" />
		<tags:oneControlPanelItem linkhref="javascript:launchPrint()" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_printer.png" linktext="Print" />
	</tags:controlPanel>
<form action="../registration/createRegistration" method="post" id="create">
	<input type="hidden" name="_page" id="_page0" value="0"/>
	<input type="hidden" name="_target1" id="_target1" value="1"/>
	<input type="hidden" name="studySite" id="create_studySite" value=""/>
	<input type="hidden" name="participant" id="create_participant" value=""/>
	<input type="hidden" name="parentRegistrationId" id="create_parent_id" value=""/>
	<input type="hidden" name="create_companion" value=""/>
	<!-- <input type="hidden" name="scheduledEpoch" id="create_scheduledEpoch" value=""/>-->
</form>
<form id="hotlinksForm" action="" method="get">
<input type="hidden" name="assignment" value="${command.studySubject.gridId }"/>
</form>
<form:form method="post" action="manageRegistration">
    <tags:tabFields tab="${tab}"/>
</form:form>

<div id="registrationSummary">
<br/>
<div id="printable">
<chrome:division id="Subject Information" title="Subject">
    <div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="participant.firstName"/>:</div>
            <div class="value">${command.studySubject.participant.firstName}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="participant.lastName"/>:</div>
            <div class="value">${command.studySubject.participant.lastName}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="participant.gender"/>:</div>
            <div class="value">${command.studySubject.participant.administrativeGenderCode}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="participant.medicalRecordNumber"/>:</div>
            <div class="value">${command.studySubject.participant.primaryIdentifier }</div>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label"><fmt:message key="participant.birthDate"/>:</div>
            <div class="value">${command.studySubject.participant.birthDateStr}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="participant.ethnicity"/>:</div>
            <div class="value">${command.studySubject.participant.ethnicGroupCode}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="participant.race"/>:</div>
            	<c:forEach items="${command.studySubject.participant.raceCodes}" var="raceCode">
		                <div class="value">${raceCode.displayName}</div>
		        </c:forEach>

        </div>
    </div>
</chrome:division>
<chrome:division id="Study Information" title="Study">
    <div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="study.status"/>:</div>
            <div class="value">${command.studySubject.studySite.study.coordinatingCenterStudyStatus.displayName}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="study.shortTitle"/>:</div>
            <div class="value">${command.studySubject.studySite.study.shortTitleText}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="study.longTitle"/>:</div>
            <div class="value">${command.studySubject.studySite.study.longTitleText}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="study.randomized"/>:</div>
            <div class="value">${command.studySubject.studySite.study.randomizedIndicator?'Yes':'No'}</div>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label"><fmt:message key="study.multiInstitutional"/>:</div>
            <div class="value">${command.studySubject.studySite.study.multiInstitutionIndicator?'Yes':'No'}</div>
        </div>
        <div class="row">
            <div class="label"> <fmt:message key="study.phase"/>:</div>
            <div class="value">${command.studySubject.studySite.study.phaseCode}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.primaryIdentifier"/>:</div>
            <div class="value">${command.studySubject.studySite.study.primaryIdentifier}</div>
        </div>
   </div>
</chrome:division>
<chrome:division id="Study Site Information:" title="Study Site">
   	<div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.name"/>:</div>
            <div class="value">${command.studySubject.studySite.healthcareSite.name}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="site.address"/>:</div>
            <div class="value">${command.studySubject.studySite.healthcareSite.address.addressString}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="site.status"/>:</div>
            <div class="value">${command.studySubject.studySite.siteStudyStatus.code}</div>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label"><fmt:message key="site.NCIInstitutionCode"/>:</div>
            <div class="value">${command.studySubject.studySite.healthcareSite.nciInstituteCode}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="site.IRBApprovalDate"/>:</div>
            <div class="value">${command.studySubject.studySite.irbApprovalDateStr}</div>
        </div>
  </div>
</chrome:division>
<chrome:division id="Current Epoch Information" title="Epoch & Arm">
    <div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="registration.currentEpoch"/>:</div>
            <div class="value">${command.studySubject.scheduledEpoch.epoch.name}</div>
        </div>
       	<div class="row">
            <div class="label"><fmt:message key="study.epoch.arm"/>:</div>
            <c:choose>
            	<c:when test="${empty armAssignedLabel}">
            		<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.notApplicable"/></span></div>
            	</c:when>
            	<c:otherwise>
            		<div class="value">${armAssigned}</div>
            	</c:otherwise>
            </c:choose>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label"><fmt:message key="registration.enrolling"/>:</div>
            <div class="value">${command.studySubject.scheduledEpoch.epoch.enrollmentIndicator?'Yes':'No'}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.epochStatus"/>:</div>
            <div class="value">${command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code}</div>
        </div>
    </div>
</chrome:division>

<div <c:if test="${empty command.studySubject.parentStudySubject}">style="display:none;"</c:if>>
<chrome:division title="Parent Study">
    <table class="tablecontent" width="90%">
        <tr>
            <th width="75%" scope="col" align="left"><b><fmt:message key="study.shortTitle"/></b></th>
			<th width="75%" scope="col" align="left"><b><fmt:message key="c3pr.common.primaryIdentifier"/></b></th>
        </tr>
            <tr>
                <td class="alt">${command.studySubject.parentStudySubject.studySite.study.shortTitleText}</td>
				<td class="alt">${command.studySubject.parentStudySubject.studySite.study.primaryIdentifier}</td>
                <td class="alt">
                	<input type="button" id="manageParentRegistration" value="Manage" onClick="javascript:document.location='<c:url value='/pages/registration/manageRegistration?registrationId=${command.studySubject.parentStudySubject.id}' />'"/>
                </td>
   	        </tr>	           
    </table>
</chrome:division>
</div>

<div id="enrollmentSection">
<chrome:division id="enrollment" title="Enrollment Details">
<div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="registration.startDate"/>:</div>
            <div class="value">${command.studySubject.startDateStr}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.registrationStatus"/>:</div>
            <div class="value">${command.studySubject.regWorkflowStatus.code}
            </div>
        </div>
        <c:if test="${command.studySubject.regWorkflowStatus=='OFF_STUDY'}">
            <div class="row">
                <div class="label"><fmt:message key="registration.offStudyReason"/>:</div>
                <div class="value">${command.studySubject.offStudyReasonText }</div>
            </div>
            <div class="row">
                <div class="label"><fmt:message key="registration.offStudyDate"/>:</div>
                <div class="value">${command.studySubject.offStudyDateStr }</div>
            </div>
        </c:if>
        <div class="row">
            <div class="label"><fmt:message key="registration.consentSignedDate"/>:</div>
            <div class="value">${command.studySubject.informedConsentSignedDateStr }</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.consentVersion"/>:</div>
            <div class="value">${command.studySubject.informedConsentVersion}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.enrollingPhysician"/>:</div>
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
	<div class="rightpanel">
        <div class="row">
            <div class="label"><fmt:message key="registration.registrationIdentifier"/>:</div>
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
            <div class="label"><fmt:message key="registration.primaryDisease"/>:</div>
            	<c:choose>
						<c:when test="${!empty command.studySubject.diseaseHistory.primaryDiseaseStr}">
							<div class="value">${command.studySubject.diseaseHistory.primaryDiseaseStr}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.primaryDiseaseSite"/>:</div>
            <c:choose>
						<c:when test="${!empty command.studySubject.diseaseHistory.primaryDiseaseSiteStr }">
							<div class="value">${command.studySubject.diseaseHistory.primaryDiseaseSiteStr }</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
        </div>
        <div class="row">
        <div class="label"><fmt:message key="registration.paymentMethod"/>:</div>
            <c:choose>
						<c:when test="${!empty command.studySubject.paymentMethod}">
							<div class="value">${command.studySubject.paymentMethod}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
        </div>
</div>
</chrome:division>
</div>
<chrome:division id="identifiers" title="Identifiers">
    <table width="90%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
        <tr>
            <th><fmt:message key="c3pr.common.assigningAuthority"/></th>
            <th><fmt:message key="c3pr.common.identifierType"/></th>
            <th><fmt:message key="c3pr.common.identifier"/></th>
            <th><fmt:message key="c3pr.common.primaryIndicator"/></th>
            <th></th>
        </tr>
        <c:forEach var="orgIdentifier" items="${command.studySubject.organizationAssignedIdentifiers}"
                  varStatus="organizationStatus">
            <tr>
                <td>${orgIdentifier.healthcareSite.name}</td>
                <td>${orgIdentifier.type}</td>
                <td>${orgIdentifier.value}</td>
                <td>${orgIdentifier.primaryIndicator?'Yes':'No'}
                    <%--<form:radiobutton value="true" cssClass="identifierRadios"
                                      path="command.studySubject.organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator"/></td> --%>
            </tr>
        </c:forEach>
        <c:forEach items="${command.studySubject.systemAssignedIdentifiers}" varStatus="status" var="sysIdentifier">
            <tr>
                <td>${sysIdentifier.systemName}</td>
                <td>${sysIdentifier.type}</td>
                <td>${sysIdentifier.value}</td>
                <td>${sysIdentifier.primaryIndicator?'Yes':'No'}
                    <%-- <form:radiobutton value="true" cssClass="identifierRadios"
                                      path="command.studySubject.systemAssignedIdentifiers[${status.index}].primaryIndicator"/>--%></td>
            </tr>
        </c:forEach>
    </table>
</chrome:division>
<chrome:division id="Eligibility" title="Eligibility">
	<div class="leftpanel">
		<div class="row">
    		<div class="label"><fmt:message key="registration.eligibilty"/>:</div>
        	<c:choose>
				<c:when test="${command.studySubject.scheduledEpoch.eligibilityIndicator}">
					<div class="value"><fmt:message key="c3pr.common.yes"/></div>
				</c:when>
				<c:otherwise>
					<div class="value"><fmt:message key="c3pr.common.no"/></div>
					<div align="left"><span class="red"><fmt:message key="registartion.eligibiltyRequired"/></span></div>
				</c:otherwise>
			</c:choose>
         </div>
		 </div>
    </chrome:division>
    <chrome:division id="stratification" title="Stratification">
        <c:choose>
            <c:when test="${fn:length(command.studySubject.scheduledEpoch.subjectStratificationAnswers) == 0}">
                <div align="left"><span class="no-selection"><fmt:message key="registartion.stratificationNotAvailable"/></span></div>
            </c:when>
            <c:otherwise>
	          	<c:if test="${command.studySubject.scheduledEpoch.epoch.stratificationIndicator}">
	          		<div class="leftpanel">
					<div class="row">
						<div class="label"><fmt:message key="registration.stratumGroupNumber"/>:</div>
						<div class="value"> ${command.studySubject.scheduledEpoch.stratumGroupNumber}</div>
					</div>
				</c:if>
				</div>
				<div class="rightpanel">&nbsp;</div>
                <table border="0" cellspacing="0" cellpadding="0" class="tablecontent" width="90%">
                    <tr>
                        <th width="35%" scope="col" align="left"><fmt:message key="study.criterion"/></th>
                        <th scope="col" align="left"><b><fmt:message key="study.answers"/></b></th>
                    </tr>
                    <c:forEach items="${command.studySubject.scheduledEpoch.subjectStratificationAnswers}" var="criteria">
                        <tr class="results">
                            <td class="alt" align="left">${criteria.stratificationCriterion.questionText}</td>
                            <td class="alt"
                                align="left">${criteria.stratificationCriterionAnswer.permissibleAnswer==''?'<span class="red"><b>Unanswered</span>':criteria.stratificationCriterionAnswer.permissibleAnswer }</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </chrome:division>

</div>
<c:if test="${command.studySubject.regWorkflowStatus=='REGISTERED' && hotlinkEnable}">
    <chrome:division title="CCTS Workflow">
        <form:form id="viewDetails" name="viewDetails">
            <div class="content">
                <div class="row">
                    <table width="50%"><tr>
                    	<td width="25%" align="right">
                        <b>Broadcast status:</b>
                        </td>
						<td width="75%" align="left">
						<div id="broadcastResponse">
                            ${command.studySubject.cctsWorkflowStatus.displayName}
                            <c:if test="${command.studySubject.cctsWorkflowStatus=='MESSAGE_SEND_FAILED'}">
                        	<a href="javascript:C3PR.showCCTSError();">Click here to see the error message</a>
                        	<div id="cctsErrorMessage" style="display: none;">${ command.studySubject.cctsErrorString}</div>
                        	</c:if>
                        </div>
                        </td>
                    </tr><tr><td colspan="2">&nbsp;</td></tr><tr>
                        <td colspan="2" align="center">
                        <input type="button" id="broadcastStatusBtn" value="Refresh"
                               onclick="getBroadcastStatus();"/>
                        <input type="button" id="broadcastBtn" value="Broadcast"
                               onclick="doSendMessageToESB();"/>
						
						</td>
					</tr></table>
                </div>
            </div>
        </form:form>
        <div id="built-cctsErrorMessage" style="display: none;"/>
    </chrome:division>
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

<%--<c:if test='${(command.studySubject.multisiteWorkflowStatus=="MESSAGE_SEND_FAILED" || command.studySubject.multisiteWorkflowStatus=="MESSAGE_REPLY_FAILED") && multisiteEnable}'>--%>
<div id="Endpoint-Msgs">
<c:if test='${hasEndpointMessages}'>
	<c:set var="siteEndpoint" value="${command.studySubject.studySite.study.studyCoordinatingCenter}" />
    <chrome:division title="MultiSite Messages">
        <form:form id="multisiteDetails" name="multisiteDetails">
            <div class="content">
                <div class="row">
					<c:choose>
						<c:when test="${siteEndpoint.lastAttemptedRegistrationEndpoint.status=='MESSAGE_SEND_FAILED'}">
							<font color="red">${siteEndpoint.lastAttemptedEndpoint.status.code}</font><br>
							Click <a href="javascript:showEndpointError();">here</a> to see the error messages
						</c:when>
						<c:otherwise>
							<font color="green">${siteEndpoint.lastAttemptedRegistrationEndpoint.status.code}</font><br>
							Click <a href="javascript:showEndpointError();">here</a> to see the messages
						</c:otherwise>
					</c:choose>
                </div>
            </div>
        </form:form>
    </chrome:division>
</c:if>
</div>
</div>
<div id="epochSelection" style="display:none;">
<div id="changeEpoch" >
	<%@ include file="reg_change_epoch.jsp"%>
</div>
</div>
<div id="editRegistrationSection" style="display:none;">
<div id="editRegistration" >
	<%@ include file="reg_edit_registration.jsp"%>
</div>
</div>
<form:form id="exportForm" method="post">
    <tags:tabFields tab="${tab}"/>
    <input type="hidden" name="_action" value="export"/>
</form:form>
</body>
</html>
