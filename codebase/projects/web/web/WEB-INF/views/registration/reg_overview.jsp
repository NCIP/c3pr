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
    	
        function show() {
            new Effect.SlideDown('OffStudyStatus');
        }
        function hide() {
            new Effect.SlideUp('OffStudyStatus');
        }
        function getBroadcastStatus() {

            $('viewDetails').disable('broadcastBtn');
            $('viewDetails').disable('broadcastStatusBtn');

        <tags:tabMethod method="getMessageBroadcastStatus" onComplete="onBroadcastComplete"
        viewName="/ajax/broadcast_res" divElement="'broadcastResponse'"
        formName="'viewDetails'" params="dontSave=true"/>
        }

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
    </script>
</head>
<body>
	<tags:printPageLink link="" />
<form action="../registration/createRegistration" method="post" id="create">
	<input type="hidden" name="_page" id="_page0" value="0"/>
	<input type="hidden" name="_target1" id="_target1" value="1"/>
	<input type="hidden" name="studySite" id="create_studySite" value=""/>
	<input type="hidden" name="participant" id="create_participant" value=""/>
	<input type="hidden" name=parentRegistrationId id="create_parent_id" value=""/>
	<input type="hidden" name="create_companion" value=""/>
	<!-- <input type="hidden" name="scheduledEpoch" id="create_scheduledEpoch" value=""/>-->
</form>
<form id="hotlinksForm" action="" method="get">
<input type="hidden" name="assignment" value="${command.studySubject.gridId }"/>
</form>
<form:form method="post">
    <tags:tabFields tab="${tab}"/>
</form:form>
<c:if test="${command.shouldRegister && command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code == 'Pending' && !command.studySubject.scheduledEpoch.epoch.enrollmentIndicator  && !command.studySubject.scheduledEpoch.epoch.reservationIndicator}">
<tags:panelBox title="Register">
	<registrationTags:register registration="${command.studySubject}" newReg="${newRegistration}" actionButtonLabel="Register" requiresMultiSite="${requiresMultiSite}"/>
</tags:panelBox>
</c:if>

<c:if test="${registerableWithCompanions &&(command.shouldRandomize) && command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code == 'Registered But Not Randomized' && command.studySubject.regWorkflowStatus.code != 'Enrolled'}">
<tags:panelBox title="Enroll & Randomize">
	<registrationTags:register registration="${command.studySubject}" newReg="${newRegistration}" actionButtonLabel="Enroll & Randomize" requiresMultiSite="${requiresMultiSite}"/>
</tags:panelBox>
</c:if>

<c:if test="${!command.shouldRegister && registerableWithCompanions &&(!command.shouldRandomize || hasCompanions) && command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code == 'Pending' && command.studySubject.scheduledEpoch.epoch.enrollmentIndicator}">
<tags:panelBox title="Enroll">
	<registrationTags:register registration="${command.studySubject}" newReg="${newRegistration}" actionButtonLabel="Enroll" requiresMultiSite="${requiresMultiSite}"/>
</tags:panelBox>
</c:if>

<c:if test="${command.shouldReserve}">
<tags:panelBox title="Reserve">
	<registrationTags:register registration="${command.studySubject}" newReg="${newRegistration}" actionButtonLabel="Reserve" requiresMultiSite="${requiresMultiSite}"/>
</tags:panelBox>
</c:if>

<c:if test="${registerableWithCompanions &&(command.shouldRandomize) && command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code == 'Registered But Not Randomized' && command.studySubject.regWorkflowStatus.code == 'Enrolled'}">
<tags:panelBox title="Transfer & Randomize">
	<registrationTags:register registration="${command.studySubject}" newReg="${newRegistration}" actionButtonLabel="Transfer & Randomize" requiresMultiSite="${requiresMultiSite}"/>
</tags:panelBox>
</c:if>

<c:if test="${!command.shouldRegister && registerableWithCompanions &&(!command.shouldRandomize || hasCompanions) && command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code == 'Pending' && command.studySubject.regWorkflowStatus.code == 'Enrolled'}">
<tags:panelBox title="Transfer">
	<registrationTags:register registration="${command.studySubject}" newReg="${newRegistration}" actionButtonLabel="Transfer" requiresMultiSite="${requiresMultiSite}"/>
</tags:panelBox>
</c:if>


	<div id="registrationSummary">
<br/>
<div id="printable">
<chrome:division id="Subject Information" title="Subject">
    <div class="leftpanel">
        <div class="row">
            <div class="label">First name:</div>
            <div class="value">${command.studySubject.participant.firstName}</div>
        </div>
        <div class="row">
            <div class="label">Last name:</div>
            <div class="value">${command.studySubject.participant.lastName}</div>
        </div>
        <div class="row">
            <div class="label">Gender:</div>
            <div class="value">${command.studySubject.participant.administrativeGenderCode}</div>
        </div>
        <div class="row">
            <div class="label">Medical record number:</div>
            <div class="value">${command.studySubject.participant.primaryIdentifier }</div>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label">Birth date:</div>
            <div class="value">${command.studySubject.participant.birthDateStr}</div>
        </div>
        <div class="row">
            <div class="label">Ethnicity:</div>
            <div class="value">${command.studySubject.participant.ethnicGroupCode}</div>
        </div>
        <div class="row">
            <div class="label">Race(s):</div>
            	<c:forEach items="${command.studySubject.participant.raceCodes}" var="raceCode">
		                <div class="value">${raceCode.displayName}</div>
		        </c:forEach>

        </div>
    </div>
</chrome:division>
<chrome:division id="Study Information" title="Study">
    <div class="leftpanel">
        <div class="row">
            <div class="label">Status:</div>
            <div class="value">${command.studySubject.studySite.study.coordinatingCenterStudyStatus.displayName}</div>
        </div>
        <div class="row">
            <div class="label">Short title:</div>
            <div class="value">${command.studySubject.studySite.study.shortTitleText}</div>
        </div>
        <div class="row">
            <div class="label">Long title:</div>
            <div class="value">${command.studySubject.studySite.study.longTitleText}</div>
        </div>
        <div class="row">
            <div class="label">Randomized:</div>
            <div class="value">${command.studySubject.studySite.study.randomizedIndicator?'Yes':'No'}</div>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label">Multi institutional:</div>
            <div class="value">${command.studySubject.studySite.study.multiInstitutionIndicator?'Yes':'No'}</div>
        </div>
        <div class="row">
            <div class="label"> Phase:</div>
            <div class="value">${command.studySubject.studySite.study.phaseCode}</div>
        </div>
        <div class="row">
            <div class="label">Coordinating center identifier:</div>
            <div class="value">${command.studySubject.studySite.study.identifiers[0].value}</div>
        </div>
   </div>
</chrome:division>
<chrome:division id="Study Site Information:" title="Study Site">
   	<div class="leftpanel">
        <div class="row">
            <div class="label">Name:</div>
            <div class="value">${command.studySubject.studySite.healthcareSite.name}</div>
        </div>
        <div class="row">
            <div class="label">Address:</div>
            <div class="value">${command.studySubject.studySite.healthcareSite.address.streetAddress},
                    ${command.studySubject.studySite.healthcareSite.address.city},
                    ${command.studySubject.studySite.healthcareSite.address.stateCode},
                    ${command.studySubject.studySite.healthcareSite.address.postalCode}</div>
        </div>
        <div class="row">
            <div class="label">Status:</div>
            <div class="value">${command.studySubject.studySite.siteStudyStatus.code}</div>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label">NCI institution code:</div>
            <div class="value">${command.studySubject.studySite.healthcareSite.nciInstituteCode}</div>
        </div>
        <div class="row">
            <div class="label">IRB approval date:</div>
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
        <c:if test="${!empty armAssigned}">
       	    <div class="row">
                <div class="label">${armAssignedLabel}:</div>
                <div class="value">${armAssigned}</div>
            </div>
   		</c:if>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label">Enrolling:</div>
            <div class="value">${command.studySubject.scheduledEpoch.epoch.enrollmentIndicator?'Yes':'No'}</div>
        </div>
        <div class="row">
            <div class="label">Epoch status:</div>
            <div class="value">${command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code}</div>
        </div>
    </div>
</chrome:division>

<div <c:if test="${empty command.studySubject.parentStudySubject}">style="display:none;"</c:if>>
<chrome:division title="Parent Study">
    <table class="tablecontent" width="50%">
        <tr>
            <th width="75%" scope="col" align="left"><b>Short title:</b></th>
			<th width="75%" scope="col" align="left"><b>Primary identifier:</b></th>
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


<chrome:division id="enrollment" title="Enrollment Details">
<div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="registration.startDate"/>:</div>
            <div class="value">${command.studySubject.startDateStr}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.registrationStatus"/>:</div>
            <div class="value">${command.studySubject.regWorkflowStatus.code}
            	<csmauthz:accesscontrol domainObject="${command.studySubject}" hasPrivileges="UPDATE" authorizationCheckName="domainObjectAuthorizationCheck">
	                <c:if test="${command.studySubject.regWorkflowStatus.code=='Enrolled' && command.studySubject.scheduledEpoch.scEpochWorkflowStatus=='REGISTERED'}">
	                    <input type="button" value="Take subject off study" onclick="new Effect.SlideDown('OffStudyStatus')">
	                </c:if>
	                <br/><br/>
	                <div id="OffStudyStatus">
	                    <form:form id="offStudyStatusForm">
	                        <input type="hidden" name="_page" value="${tab.number}" id="_page"/>
	                        <input type="hidden" name="regWorkflowStatus" value="OFF_STUDY" id="regWorkflowStatus"/>Reason:
	                        <form:textarea path="studySubject.offStudyReasonText" rows="2" cols="40" cssClass="validate-notEmpty"></form:textarea>
	                        <br /><br />
	                        Date: &nbsp;&nbsp;&nbsp;
	                        <tags:dateInput path="studySubject.offStudyDate" cssClass="validate-notEmpty&&DATE"/>
	                        <em> (mm/dd/yyyy)</em><br /><br />
	                        <c:if test="${command.studySubject.regWorkflowStatus!='OFF_STUDY'}"><input type="submit" value="ok"/>
	                            <input type="button" value="cancel" onClick="new Effect.SlideUp('OffStudyStatus')"/>
	                        </c:if>
	                    </form:form>
	                </div>
                	<script type="text/javascript">new Element.hide('OffStudyStatus');</script>
               </csmauthz:accesscontrol>
            </div>
        </div>
        <c:if test="${command.studySubject.regWorkflowStatus=='OFF_STUDY'}">
            <div class="row">
                <div class="label">Off study reason:</div>
                <div class="value">${command.studySubject.offStudyReasonText }</div>
            </div>
            <div class="row">
                <div class="label">Off study date:</div>
                <div class="value">${command.studySubject.offStudyDate }</div>
            </div>
        </c:if>
        <div class="row">
            <div class="label"><fmt:message key="registration.consentSignedDate"/>:</div>
            <div class="value">${command.studySubject.informedConsentSignedDateStr }</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.consentVesion"/>:</div>
            <div class="value">${command.studySubject.informedConsentVersion}</div>
        </div>
	</div>
	<div class="rightpanel">
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
<chrome:division id="identifiers" title="Identifiers">
    <table width="90%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
        <tr>
            <th>Assigning Authority</th>
            <th>Identifier Type</th>
            <th>Identifier</th>
            <th>Primary&nbsp;Indicator</th>
            <th></th>
        </tr>
        <c:forEach var="orgIdentifier" items="${command.studySubject.organizationAssignedIdentifiers}"
                   begin="1" varStatus="organizationStatus">
            <tr>
                <td>${orgIdentifier.healthcareSite.name}</td>
                <td>${orgIdentifier.type}</td>
                <td>${orgIdentifier.value}</td>
                <td>${orgIdentifier.primaryIndicator}
                    <form:radiobutton value="true" cssClass="identifierRadios"
                                      path="command.studySubject.organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator"/></td>
            </tr>
        </c:forEach>
        <c:forEach items="${command.studySubject.systemAssignedIdentifiers}" varStatus="status" var="sysIdentifier">
            <tr>
                <td>${sysIdentifier.systemName}</td>
                <td>${sysIdentifier.type}</td>
                <td>${sysIdentifier.value}</td>
                <td>${sysIdentifier.primaryIndicator}
                    <%-- <form:radiobutton value="true" cssClass="identifierRadios"
                                      path="command.studySubject.systemAssignedIdentifiers[${status.index}].primaryIndicator"/></td>--%>
            </tr>
        </c:forEach>
    </table>
</chrome:division>
<chrome:division id="Eligibility" title="Eligibility">
		<div class="row">
    		<div class="label"><fmt:message key="registration.eligibilty"/></div>
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
    </chrome:division>
    <chrome:division id="stratification" title="Stratification">
        <c:choose>
            <c:when test="${fn:length(command.studySubject.scheduledEpoch.subjectStratificationAnswers) == 0}">
                
                    <div class="row">
                        <td class="label" align=left>The selected epoch does not have any stratification factors.</td>
                    </div>
                
            </c:when>
            <c:otherwise>
                <table border="0" cellspacing="0" cellpadding="0" class="tablecontent" width="50%">
                    <tr>
                        <th width="35%" scope="col" align="left">Strata</th>
                        <th scope="col" align="left"><b>Answer</b></th>
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
    <c:if test="${!empty armAssigned}">
        <chrome:division id="${armAssignedLabel }" title="${armAssignedLabel }">
                <div class="row">
                    <div class="label">${armAssignedLabel}:</div>
                    <div class="value">${armAssigned}</div>
                </tr>
        </chrome:division>
    </c:if>
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

<div align="right">
	<input type="button" value="Print" onClick="javascript:C3PR.printElement('printable');"/>
    <input type="button" value="Export" onClick="$('exportForm')._target.name='xxxx';$('exportForm').submit();"/>
</div>

</div>
<form:form id="exportForm" method="post">
    <tags:tabFields tab="${tab}"/>
    <input type="hidden" name="_action" value="export"/>
</form:form>
</body>
</html>
