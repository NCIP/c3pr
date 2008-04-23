<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs" %>
<%@ taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
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
        function activateInPlaceEditing(arrayElements) {
            for (aE = 0; aE < arrayElements.length; aE++) {
                arrayElements[aE].enterEditMode('click');
            }
            new Effect.Appear('OffStudyDiv');
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
        formName="'viewDetails'"/>
        }


        function doSendMessageToESB() {
            $('broadcastResponse').innerHTML = 'Sending Message...';

            $('viewDetails').disable('broadcastBtn');
            $('viewDetails').disable('broadcastStatusBtn');

        <tags:tabMethod method="broadcastRegistration"
       viewName="/ajax/broadcast_res" onComplete="onBroadcastComplete"
       divElement="'broadcastResponse'" formName="'tabMethodForm'"params="dontSave=true"/>
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
    </script>
</head>
<body>
<form id="hotlinksForm" action="" method="get">
<input type="hidden" name="assignment" value="${command.gridId }"/>
</form>
<form:form method="post">
    <tags:tabFields tab="${tab}"/>
</form:form>
<c:if test="${actionRequired}">
    <registrationTags:register registration="${command}" newReg="${newRegistration}" actionButtonLabel="${actionLabel}"
                               requiresMultiSite="${requiresMultiSite}"/>
</c:if>
<tags:panelBox>
<br/>
<div id="printable">
<chrome:division id="Subject Information" title="Subject">
    <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
        <tr>
            <td width="35%" class="label">First Name</td>
            <td>${command.participant.firstName}</td>
        </tr>
        <tr>
            <td class="label">Last Name</td>
            <td>${command.participant.lastName}</td>
        </tr>
        <tr>
            <td class="label">Gender</td>
            <td>${command.participant.administrativeGenderCode}</td>
        </tr>
        <tr>
            <td class="label">MRN</td>
            <td>${command.participant.primaryIdentifier }</td>
        </tr>
        <tr>
            <td class="label">Birth Date</td>
            <td>${command.participant.birthDateStr}</td>
        </tr>
        <tr>
            <td class="label">Ethnicity</td>
            <td>${command.participant.ethnicGroupCode}</td>
        </tr>
        <tr>
            <td class="label">Race(s)</td>
            <td>
            	<c:forEach items="${command.participant.raceCodes}" var="raceCode">
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
            <td>${command.studySite.study.coordinatingCenterStudyStatus.code}</td>
        </tr>
        <tr>
            <td width="35%" class="label">Short Title</td>
            <td>${command.studySite.study.shortTitleText}</td>
        </tr>
        <tr>
            <td class="label">Long Title</td>
            <td>${command.studySite.study.longTitleText}</td>
        </tr>
        <tr>
            <td class="label">Randomized</td>
            <td>${command.studySite.study.randomizedIndicator?'Yes':'No'}</td>
        </tr>
        <tr>
            <td class="label">Multi Institutional</td>
            <td>${command.studySite.study.multiInstitutionIndicator?'Yes':'No'}</td>
        </tr>
        <tr>
            <td class="label"> Phase</td>
            <td>${command.studySite.study.phaseCode}</td>
        </tr>
        <tr>
            <td width="25%" class="label">Coordinating Center Identifier</td>
            <td>${command.studySite.study.identifiers[0].value}</td>
        </tr>
    </table>
</chrome:division>
<chrome:division id="Study Site Information:" title="Study Site">
    <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
        <tr>
            <td width="35%" class="label">Name</td>
            <td>${command.studySite.healthcareSite.name}</td>
        </tr>
        <tr>
            <td class="label">Address</td>
            <td>${command.studySite.healthcareSite.address.streetAddress},
                    ${command.studySite.healthcareSite.address.city},
                    ${command.studySite.healthcareSite.address.stateCode},
                    ${command.studySite.healthcareSite.address.postalCode}</td>
        </tr>
        <tr>
            <td class="label">Status</td>
            <td>${command.studySite.siteStudyStatus.code}</td>
        </tr>
        <tr>
            <td class="label">NCI Institution Code</td>
            <td>${command.studySite.healthcareSite.nciInstituteCode}</td>
        </tr>
        <tr>
            <td class="label">IRB Approval Date</td>
            <td>${command.studySite.irbApprovalDateStr}</td>
        </tr>
    </table>
</chrome:division>
<chrome:division id="Current Epoch Information" title="Current Epoch">
    <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
        <tr>
            <td width="35%" class="label">Current Epoch</td>
            <td>${command.scheduledEpoch.epoch.name}</td>
        </tr>
        <tr>
            <td class="label">Type</td>
            <td>${command.ifTreatmentScheduledEpoch?'Treatment':'Non Treatment'}</td>
        </tr>
        <tr>
            <td class="label">Enrolling</td>
            <td>${!command.ifTreatmentScheduledEpoch?studySubject.scheduledEpoch.epoch.enrollmentIndicator:'True'}</td>
        </tr>
        <tr>
            <td class="label">Epoch Status</td>
            <td>${command.scheduledEpoch.scEpochWorkflowStatus.code}</td>
        </tr>
    </table>
</chrome:division>
<chrome:division id="enrollment" title="Enrollment Details">
    <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
        <tr>
            <td class="label">Registration Start Date</td>
            <td>
                <tags:inPlaceEdit value="${command.startDate }" path="startDate" required="true"/>
            </td>
        </tr>
        <tr>
            <td width="35%" class="label">Registration Status</td>
            <td>${command.regWorkflowStatus.code}&nbsp;
                <c:if test="${command.regWorkflowStatus=='REGISTERED' && command.scheduledEpoch.scEpochWorkflowStatus=='APPROVED'}">
                    <input type="button" value="Take subject off study"
                           onclick="new Effect.SlideDown('OffStudyStatus')">
                </c:if><br/><br/>
                <div id="OffStudyStatus">
                    <form:form id="offStudyStatusForm">
                        <input type="hidden" name="_page" value="${tab.number}" id="_page"/>
                        <input type="hidden" name="regWorkflowStatus" value="OFF_STUDY" id="regWorkflowStatus"/>
                        Reason:
                        <form:textarea path="offStudyReasonText" rows="2" cols="40"
                                       cssClass="validate-notEmpty"></form:textarea>
                        <br /><br />
                        Date: &nbsp;&nbsp;&nbsp;
                        <tags:dateInput path="offStudyDate" cssClass="validate-notEmpty"/>
                        <em> (mm/dd/yyyy)</em><br /><br />
                        <c:if test="${command.regWorkflowStatus!='OFF_STUDY'}"><input type="submit" value="ok"/>
                            <input type="button" value="cancel" onclick="new Effect.SlideUp('OffStudyStatus')"/></c:if>
                    </form:form>
                </div>
                <script type="text/javascript">new Element.hide('OffStudyStatus');</script>
            </td>
        </tr>
        <c:if test="${command.regWorkflowStatus=='OFF_STUDY'}">
            <tr>
                <td class="label" width="35%">Off Study Reason</td>
                <td>${command.offStudyReasonText }</td>
            </tr>
            <tr>
                <td class="label">Off Study Date</td>
                <td>${command.offStudyDate }</td>
            </tr>
        </c:if>
        <tr>
            <td class="label">Informed Consent Signed Date</td>
            <td>
                <tags:inPlaceEdit value="${command.informedConsentSignedDateStr }" path="informedConsentSignedDate"
                                  required="true"/>
            </td>
        </tr>
        <tr>
            <td class="label">Informed Consent Version</td>
            <td>
                <tags:inPlaceEdit value="${command.informedConsentVersion}" path="informedConsentVersion"
                                  required="true"/>
            </td>
        </tr>
        <tr>
            <td width="35%" class="label">Treating Physician</td>
            <c:set var="options" value=""></c:set>
            <c:set var="values" value=""></c:set>
            <c:set var="commanSepOptVal" value="["></c:set>
            <c:forEach items="${command.studySite.activeStudyInvestigators}" var="physician" varStatus="temp">
                <c:set var="commanSepOptVal"
                       value="${commanSepOptVal}[${physician.id},'${physician.healthcareSiteInvestigator.investigator.fullName}']"></c:set>
                <c:if test="${!temp.last}">
                    <c:set var="commanSepOptVal" value="${commanSepOptVal},"></c:set>
                </c:if>
            </c:forEach>
            <c:set var="commanSepOptVal" value="${commanSepOptVal}]"></c:set>
            <td>
                <tags:inPlaceSelect value="${command.treatingPhysicianFullName}" path="treatingPhysician"
                                    commanSepOptVal="${commanSepOptVal}" pathToGet="treatingPhysicianFullName"/>
                &nbsp;</td>
        </tr>
        <tr>
            <td width="35%" class="label">Registration Identifier</td>
            <td>
                <tags:inPlaceEdit value="${command.coOrdinatingCenterIdentifier}" path="coOrdinatingCenterIdentifier"
                                  required="true"/>
                &nbsp;</td>
        </tr>
        <tr>
            <td class="label">Primary Disease</td>
            <td>${command.diseaseHistory.primaryDiseaseStr }</td>
        </tr>
        <tr>
            <td class="label">Primary Disease Site</td>
            <td>${command.diseaseHistory.primaryDiseaseSiteStr }</td>
        </tr>
        <tr>
        <td class="label">Payment Method</td>
            <td>${command.paymentMethod}</td>
        </tr>
    </table>
    <c:if test="${command.regWorkflowStatus!='OFF_STUDY'}"><br>

        <div align="right"><input type="button" value="Edit" onclick="activateInPlaceEditing(eArray)"/></div>
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
        <c:forEach var="orgIdentifier" items="${command.organizationAssignedIdentifiers}"
                   begin="1" varStatus="organizationStatus">
            <tr>
                <td>${orgIdentifier.healthcareSite.name}</td>
                <td>${orgIdentifier.type}</td>
                <td>${orgIdentifier.value}</td>
                <td>${orgIdentifier.primaryIndicator}
                    <form:radiobutton value="true" cssClass="identifierRadios"
                                      path="command.organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator"/></td>
            </tr>
        </c:forEach>
        <c:forEach items="${command.systemAssignedIdentifiers}" varStatus="status" var="sysIdentifier">
            <tr>
                <td>${sysIdentifier.systemName}</td>
                <td>${sysIdentifier.type}</td>
                <td>${sysIdentifier.value}</td>
                <td>${sysIdentifier.primaryIndicator}
                    <form:radiobutton value="true" cssClass="identifierRadios"
                                      path="command.systemAssignedIdentifiers[${status.index}].primaryIndicator"/></td>
            </tr>
        </c:forEach>
    </table>
</chrome:division>
<c:if test="${command.ifTreatmentScheduledEpoch}">
    <chrome:division id="Eligibility" title="Eligibility">
        <table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
            <tr>
                <td width="35%" class="label">Eligible
                </td>
                <td>${command.scheduledEpoch.eligibilityIndicator?'True':'False' }</td>
            </tr>
        </table>
        <c:choose>
            <c:when test="${fn:length(command.scheduledEpoch.inclusionEligibilityAnswers) == 0 && fn:length(command.scheduledEpoch.exclusionEligibilityAnswers) == 0}">
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
                        <c:forEach items="${command.scheduledEpoch.inclusionEligibilityAnswers}" var="criteria">
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
                        <c:forEach items="${command.scheduledEpoch.exclusionEligibilityAnswers}" var="criteria">
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
            <c:when test="${fn:length(command.scheduledEpoch.subjectStratificationAnswers) == 0}">
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
                        <th scope="col" align="left"><b>Answer</th>
                    </tr>
                    <c:forEach items="${command.scheduledEpoch.subjectStratificationAnswers}" var="criteria">
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
</c:if>
</div>
<c:if test="${command.regWorkflowStatus=='REGISTERED' && hotlinkEnable}">
    <chrome:division title="CCTS Workflow">
        <form:form id="viewDetails" name="viewDetails">
            <div class="content">
                <div class="row">
                    <div class="label">
                        Broadcast Status:
                    </div>

                    <div class="value">
                        <span id="broadcastResponse">
                                ${command.cctsWorkflowStatus.displayName}
                        </span>
                        <input type="button" id="broadcastStatusBtn" value="Refresh"
                               onclick="getBroadcastStatus();"/>
                        <input type="button" id="broadcastBtn" value="Broadcast"
                               onclick="doSendMessageToESB();"/>
                    </div>
                </div>
            </div>
        </form:form>
    </chrome:division>
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
