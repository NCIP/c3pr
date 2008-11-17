<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>

    <style type="text/css">
        .labelR {
            text-align: right;
            padding: 4px;
            font-weight: bold;
        }
    </style>
    <style type="text/css">
        .label {
            text-align: left;
            padding: 4px;
            font-weight: bold;
        }
    </style>

    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <script>
        function activateInPlaceEditing(localEditEvent) {
            for (aE = 0; aE < eArray.length; aE++) {
                eArray[aE].enterEditMode(localEditEvent);
            }
            new Effect.Appear('OffStudyDiv');
        }
        
        Event.observe(window, "load", function() {
    		Event.observe("editInPlace", "click", activateInPlaceEditing);
    	})
    	
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
        
        function doMultiSiteBroadcast() {
          $('multiSiteResponse').innerHTML = 'Sending Message...';

          $('multisiteDetails').disable('broadcastMultiSiteBtn');

        <tags:tabMethod method="broadcastMultiSiteRegistration"
       viewName="/ajax/broadcast_multisite_res" onComplete="onMultiSiteBroadcastComplete"
       divElement="'multiSiteResponse'" formName="'tabMethodForm'"params="dontSave=true"/>
        }

        function onMultiSiteBroadcastComplete() {
            $('multisiteDetails').enable('broadcastMultiSiteBtn');
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
<c:if test="${registerableWithCompanions &&(actionRequired || hasCompanions) && command.studySubject.dataEntryStatusString=='Complete' && command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code == 'Unapproved'}">
<tags:panelBox title="${actionLabel}">
    <registrationTags:register registration="${command.studySubject}" newReg="${newRegistration}" actionButtonLabel="${actionLabel}"
                               requiresMultiSite="${requiresMultiSite}"/>
</tags:panelBox>
</c:if>
<tags:panelBox>
<br/>
<div id="printable">
<chrome:division id="Subject Information" title="Subject">
    <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
        <tr>
            <td width="35%" class="label">First Name</td>
            <td>${command.studySubject.participant.firstName}</td>
        </tr>
        <tr>
            <td class="label">Last Name</td>
            <td>${command.studySubject.participant.lastName}</td>
        </tr>
        <tr>
            <td class="label">Gender</td>
            <td>${command.studySubject.participant.administrativeGenderCode}</td>
        </tr>
        <tr>
            <td class="label">MRN</td>
            <td>${command.studySubject.participant.primaryIdentifier }</td>
        </tr>
        <tr>
            <td class="label">Birth Date</td>
            <td>${command.studySubject.participant.birthDateStr}</td>
        </tr>
        <tr>
            <td class="label">Ethnicity</td>
            <td>${command.studySubject.participant.ethnicGroupCode}</td>
        </tr>
        <tr>
            <td class="label">Race(s)</td>
            <td>
            	<c:forEach items="${command.studySubject.participant.raceCodes}" var="raceCode">
		            <div class="row">
		                <div class="left">${raceCode.displayName}</div>
		            </div>
		        </c:forEach>
		    </td>
        </tr>
    </table>
</chrome:division>
<chrome:division id="Study Information" title="Study">
    <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
        <tr>
            <td class="label">Status</td>
            <td>${command.studySubject.studySite.study.coordinatingCenterStudyStatus.displayName}</td>
        </tr>
        <tr>
            <td width="35%" class="label">Short Title</td>
            <td>${command.studySubject.studySite.study.shortTitleText}</td>
        </tr>
        <tr>
            <td class="label">Long Title</td>
            <td>${command.studySubject.studySite.study.longTitleText}</td>
        </tr>
        <tr>
            <td class="label">Randomized</td>
            <td>${command.studySubject.studySite.study.randomizedIndicator?'Yes':'No'}</td>
        </tr>
        <tr>
            <td class="label">Multi Institutional</td>
            <td>${command.studySubject.studySite.study.multiInstitutionIndicator?'Yes':'No'}</td>
        </tr>
        <tr>
            <td class="label"> Phase</td>
            <td>${command.studySubject.studySite.study.phaseCode}</td>
        </tr>
        <tr>
            <td width="25%" class="label">Coordinating Center Identifier</td>
            <td>${command.studySubject.studySite.study.identifiers[0].value}</td>
        </tr>
    </table>
</chrome:division>
<chrome:division id="Study Site Information:" title="Study Site">
    <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
        <tr>
            <td width="35%" class="label">Name</td>
            <td>${command.studySubject.studySite.healthcareSite.name}</td>
        </tr>
        <tr>
            <td class="label">Address</td>
            <td>${command.studySubject.studySite.healthcareSite.address.streetAddress},
                    ${command.studySubject.studySite.healthcareSite.address.city},
                    ${command.studySubject.studySite.healthcareSite.address.stateCode},
                    ${command.studySubject.studySite.healthcareSite.address.postalCode}</td>
        </tr>
        <tr>
            <td class="label">Status</td>
            <td>${command.studySubject.studySite.siteStudyStatus.code}</td>
        </tr>
        <tr>
            <td class="label">NCI Institution Code</td>
            <td>${command.studySubject.studySite.healthcareSite.nciInstituteCode}</td>
        </tr>
        <tr>
            <td class="label">IRB Approval Date</td>
            <td>${command.studySubject.studySite.irbApprovalDateStr}</td>
        </tr>
    </table>
</chrome:division>
<chrome:division id="Current Epoch Information" title="Current Epoch">
    <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
        <tr>
            <td width="35%" class="label">Current Epoch</td>
            <td>${command.studySubject.scheduledEpoch.epoch.name}</td>
        </tr>
        <tr>
            <td class="label">Enrolling</td>
            <td>${command.studySubject.scheduledEpoch.epoch.enrollmentIndicator?'Yes':'No'}</td>
        </tr>
        <tr>
            <td class="label">Epoch Status</td>
            <td>${command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code}</td>
        </tr>
    </table>
</chrome:division>

<div <c:if test="${empty command.studySubject.parentStudySubject}">style="display:none;"</c:if>>
<chrome:division title="Parent Study">
    <table class="tablecontent" width="50%">
        <tr>
            <th width="75%" scope="col" align="left"><b>Short Title</b></th>
			<th width="75%" scope="col" align="left"><b>Primary Identifier</b></th>
        </tr>
            <tr>
                <td class="alt">${command.studySubject.parentStudySubject.studySite.study.shortTitleText}</td>
				<td class="alt">${command.studySubject.parentStudySubject.studySite.study.primaryIdentifier}</td>
                <td class="alt">
                	<input type="button" id="manageParentRegistration" value="Manage" onclick="javascript:document.location='<c:url value='/pages/registration/manageRegistration?registrationId=${command.studySubject.parentStudySubject.id}' />'"/>
                </td>
   	        </tr>	           
    </table>
</chrome:division>
</div>


<chrome:division id="enrollment" title="Enrollment Details">
    <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
        <tr>
            <td class="label">Registration Start Date</td>
            <td> 
            <tags:inPlaceEdit value="${command.studySubject.startDateStr }" path="studySubject.startDate" id="startDate" validations="validate-notEmpty&&DATE"/>
            </td>
        </tr>
        <tr>
            <td width="35%" class="label">Registration Status</td>
            <td>${command.studySubject.regWorkflowStatus.code}&nbsp;
            	
            	<csmauthz:accesscontrol domainObject="${command.studySubject}" hasPrivileges="UPDATE"
                            authorizationCheckName="domainObjectAuthorizationCheck">
	                <c:if test="${command.studySubject.regWorkflowStatus=='REGISTERED' && command.studySubject.scheduledEpoch.scEpochWorkflowStatus=='APPROVED'}">
	                    <input type="button" value="Take subject off study"
	                           onclick="new Effect.SlideDown('OffStudyStatus')">
	                </c:if><br/><br/>
	                <div id="OffStudyStatus">
	                    <form:form id="offStudyStatusForm">
	                        <input type="hidden" name="_page" value="${tab.number}" id="_page"/>
	                        <input type="hidden" name="regWorkflowStatus" value="OFF_STUDY" id="regWorkflowStatus"/>
	                        Reason:
	                        <form:textarea path="studySubject.offStudyReasonText" rows="2" cols="40"
	                                       cssClass="validate-notEmpty"></form:textarea>
	                        <br /><br />
	                        Date: &nbsp;&nbsp;&nbsp;
	                        <tags:dateInput path="studySubject.offStudyDate" cssClass="validate-notEmpty&&DATE"/>
	                        <em> (mm/dd/yyyy)</em><br /><br />
	                        <c:if test="${command.studySubject.regWorkflowStatus!='OFF_STUDY'}"><input type="submit" value="ok"/>
	                            <input type="button" value="cancel" onclick="new Effect.SlideUp('OffStudyStatus')"/></c:if>
	                    </form:form>
	                </div>
                	<script type="text/javascript">new Element.hide('OffStudyStatus');</script>
               </csmauthz:accesscontrol>
            </td>
        </tr>
        <c:if test="${command.studySubject.regWorkflowStatus=='OFF_STUDY'}">
            <tr>
                <td class="label" width="35%">Off Study Reason</td>
                <td>${command.studySubject.offStudyReasonText }</td>
            </tr>
            <tr>
                <td class="label">Off Study Date</td>
                <td>${command.studySubject.offStudyDate }</td>
            </tr>
        </c:if>
        <tr>
            <td class="label">Informed Consent Signed Date</td>
            <td>
                <tags:inPlaceEdit value="${command.studySubject.informedConsentSignedDateStr }" path="studySubject.informedConsentSignedDate" id="informedConsentSignedDate"
                                  validations="validate-notEmpty&&DATE"/>
            </td>
        </tr>
        <tr>
            <td class="label">Informed Consent Version</td>
            <td>
                <tags:inPlaceEdit value="${command.studySubject.informedConsentVersion}" path="studySubject.informedConsentVersion" id="informedConsentVersion"
                                  validations="validate-notEmpty"/>
            </td>
        </tr>
        <tr>
            <td width="35%" class="label">Treating Physician</td>
            <c:set var="options" value=""></c:set>
            <c:set var="values" value=""></c:set>
            <c:set var="commanSepOptVal" value="["></c:set>
            <c:forEach items="${command.studySubject.studySite.activeStudyInvestigators}" var="physician" varStatus="temp">
                <c:set var="commanSepOptVal"
                       value="${commanSepOptVal}[${physician.id},'${physician.healthcareSiteInvestigator.investigator.fullName}']"></c:set>
                <c:if test="${!temp.last}">
                    <c:set var="commanSepOptVal" value="${commanSepOptVal},"></c:set>
                </c:if>
            </c:forEach>
            <c:set var="commanSepOptVal" value="${commanSepOptVal}]"></c:set>
            <td>
                <tags:inPlaceSelect value="${command.studySubject.treatingPhysicianFullName}" path="studySubject.treatingPhysician" id="treatingPhysician"
                                    commanSepOptVal="${commanSepOptVal}" pathToGet="treatingPhysicianFullName"/>
                &nbsp;</td>
        </tr>
        <tr>
            <td width="35%" class="label">Registration Identifier</td>
            <td>
                <tags:inPlaceEdit value="${command.studySubject.coOrdinatingCenterIdentifier}" path="studySubject.coOrdinatingCenterIdentifier" id="coOrdinatingCenterIdentifier"
                                  validations="validate-notEmpty"/>
                &nbsp;</td>
        </tr>
        <tr>
            <td class="label">Primary Disease</td>
            <td>${command.studySubject.diseaseHistory.primaryDiseaseStr }</td>
        </tr>
        <tr>
            <td class="label">Primary Disease Site</td>
            <td>${command.studySubject.diseaseHistory.primaryDiseaseSiteStr }</td>
        </tr>
        <tr>
        <td class="label">Payment Method</td>
            <td>${command.studySubject.paymentMethod}</td>
        </tr>
    </table>
    <c:if test="${command.studySubject.regWorkflowStatus!='OFF_STUDY'}"><br>
    	<csmauthz:accesscontrol domainObject="${command.studySubject}" hasPrivileges="UPDATE"
                            authorizationCheckName="domainObjectAuthorizationCheck">
        	<div align="right"><input type="button" value="Edit" id="editInPlace"/></div>
        </csmauthz:accesscontrol>
    </c:if>
    <script>
        eArray = new Array();
        eArray.push(editor_startDate);
        eArray.push(editor_informedConsentSignedDate);
        eArray.push(editor_informedConsentVersion);
        eArray.push(editor_treatingPhysician);
        eArray.push(editor_coOrdinatingCenterIdentifier);
    </script>
</chrome:division>
<chrome:division id="identifiers" title="Identifiers">
    <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
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
                    <form:radiobutton value="true" cssClass="identifierRadios"
                                      path="command.studySubject.systemAssignedIdentifiers[${status.index}].primaryIndicator"/></td>
            </tr>
        </c:forEach>
    </table>
</chrome:division>
    <chrome:division id="Eligibility" title="Eligibility">
        <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
            <tr>
                <td width="35%" class="label">Eligible
                </td>
                <td>${command.studySubject.scheduledEpoch.eligibilityIndicator?'True':'False' }</td>
            </tr>
        </table>
        <c:choose>
            <c:when test="${fn:length(command.studySubject.scheduledEpoch.inclusionEligibilityAnswers) == 0 && fn:length(command.studySubject.scheduledEpoch.exclusionEligibilityAnswers) == 0}">
                There is no eligibility check list available for this epoch
            </c:when>
            <c:otherwise>
                <br>
                <strong>Inclusion Criteria</strong>

                <div class="review">
                    <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
                        <tr>
                            <th scope="col" align="left">Question</th>
                            <th scope="col" align="left">Answer</th>
                        </tr>
                        <c:forEach items="${command.studySubject.scheduledEpoch.inclusionEligibilityAnswers}" var="criteria">
                            <tr class="results">
                                <td class="alt" align="left">${ criteria.eligibilityCriteria.questionText}</td>
                                <td class="alt"
                                    align="left">${criteria.answerText==''?'<span class="red"><b>Unanswered</span>':criteria.answerText }</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <br>
                <strong>Exclusion Criteria</strong>

                <div class="review">
                    <table border="0" cellspacing="0" cellpadding="0" width="50%" class="tablecontent">
                        <tr>
                            <th scope="col" align="left">Question</th>
                            <th scope="col" align="left">Answer</th>
                        </tr>
                        <c:forEach items="${command.studySubject.scheduledEpoch.exclusionEligibilityAnswers}" var="criteria">
                            <tr class="results">
                                <td class="alt" align="left">${ criteria.eligibilityCriteria.questionText}</td>
                                <td class="alt"
                                    align="left">${criteria.answerText==''?'<span class="red"><b>Unanswered</span>':criteria.answerText }</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </chrome:division>
    <chrome:division id="stratification" title="Stratification">
        <c:choose>
            <c:when test="${fn:length(command.studySubject.scheduledEpoch.subjectStratificationAnswers) == 0}">
                <table width="50%" border="0" cellspacing="0" cellpadding="0" id="table1">
                    <tr>
                        <td class="label" align=left>The Selected Epoch does not have any stratification factors</td>
                    </tr>
                </table>
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
            <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
                <tr>
                    <td width="35%" class="label">${armAssignedLabel }</td>
                    <td>${armAssigned}</td>
                </tr>
            </table>
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
                        <b>Broadcast Status:</b>
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
<c:if test='${command.studySubject.scheduledEpoch.scEpochWorkflowStatus=="PENDING" && multisiteEnable}'>
    <chrome:division title="MultiSite Workflow">
        <form:form id="multisiteDetails" name="multisiteDetails">
            <div class="content">
                <div class="row">
                    <div class="label">
                        Broadcast Status:
                    </div>

                    <div class="value">
                        <span id="multiSiteResponse">
                                ${command.studySubject.multisiteWorkflowStatus.displayName}
                        </span>
                        <c:if test='${command.studySubject.multisiteWorkflowStatus.code=="MESSAGE_SEND_FAILED" || command.studySubject.multisiteWorkflowStatus.code=="MESSAGE_REPLY_FAILED"}'>
                        	<c:set var="multiSiteLabel" value='${command.studySubject.multisiteWorkflowStatus.code=="MESSAGE_SEND_FAILED"?"Send request":"Send Response"}' />
	                        <input type="button" id="broadcastMultiSiteBtn" value="${multiSiteLabel }"
	                               onclick="doMultiSiteBroadcast();"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </form:form>
    </chrome:division>
</c:if>

<div align="right">
	<input type="button" value="Print" onClick="javascript:C3PR.printElement('printable');"/>
    <input type="button" value="Export" onClick="$('exportForm')._target.name='xxxx';$('exportForm').submit();"/>
</div>

</tags:panelBox>
<form:form id="exportForm" method="post">
    <tags:tabFields tab="${tab}"/>
    <input type="hidden" name="_action" value="export"/>
</form:form>
</body>
</html>
