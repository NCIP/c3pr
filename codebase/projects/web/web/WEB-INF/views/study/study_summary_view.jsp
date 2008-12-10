<%@ include file="taglibs.jsp" %>

<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}"/></title>
    <script language="JavaScript" type="text/JavaScript">

        function doExportAction() {
            document.viewDetails._action.value = "export";
            document.viewDetails.submit();
            document.viewDetails._action.value = "";
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

        <tags:tabMethod method="sendMessageToESB" onComplete="onBroadcastComplete"
            viewName="/ajax/broadcast_res" divElement="'broadcastResponse'"
            formName="'viewDetails'"/>
        }

        function onBroadcastComplete() {
            $('viewDetails').enable('broadcastBtn');
            $('viewDetails').enable('broadcastStatusBtn');
        }


        function statusChangeCallback(statusCode) {
            elmt = document.getElementById('amendButtonDisplayDiv');
            if (statusCode == 'Active' || statusCode == 'Amendment Pending') {
                elmt.style.display = "";
            } else {
                elmt.style.display = "none";
            }
        }

        function reloadCompanion() {
        <tags:tabMethod method="reloadCompanion" divElement="'companionDiv'" formName="'tabMethodForm'"  viewName="/study/companionSection"/>
        }

        function changeStudyStatus(status) {

        	if (${fn:length(errors)} > 0){
        		var d = $('errorsDiv');
        		Dialog.alert(d.innerHTML, 
        		{width:500, height:200, okLabel: "close", ok:function(win) {debug("validate alert panel"); return true;}});
        	} else {
	            $('statusChange').value = status;
	            $('command').submit();
        	}

        }
    </script>
</head>

<body>
<form:form>
    <input type="hidden" name="_target${tab.number}" id="_target"/>
    <input type="hidden" name="_page" value="${tab.number}" id="_page"/>
    <input type="hidden" name="statusChange" id="statusChange"/>
</form:form>
<form:form id="viewDetails" name="viewDetails">
<tags:tabFields tab="${tab}"/>
<chrome:box title="Study Summary">
<div>
    <input type="hidden" name="_finish" value="true"/> <input
        type="hidden" name="_action" value=""></div>

<div id="printable">
<chrome:division id="study-details" title="Study Details">
<table class="tablecontent" width="60%">
<tr>
    <td width="35%" class="alt" align="left"><b>Short Title</b></td>
    <td class="alt" align="left">${command.study.shortTitleText}</td>
</tr>
<tr>
    <td class="alt" align="left"><b>Primary Identifier</b></td>
    <td class="alt" align="left">${command.study.primaryIdentifier}</td>
</tr>
<tr>
    <td class="alt" align="left"><b>Target Accrual Number</b></td>
    <td class="alt" align="left">
        <tags:inPlaceEdit value="${command.study.targetAccrualNumber}" path="study.changedTargetAccrualNumber"
                          id="changedTargetAccrualNumber"
                          validations="validate-notEmpty"/>
        <csmauthz:accesscontrol domainObject="${command.study}" hasPrivileges="UPDATE"
                                authorizationCheckName="domainObjectAuthorizationCheck">
            &nbsp; <input type="button" value="Edit"
                          onclick="editor_changedTargetAccrualNumber.enterEditMode('click')"/>
        </csmauthz:accesscontrol>
    </td>
</tr>
<tr>
    <td class="alt" align="left"><b>Phase</b></td>
    <td class="alt" align="left">${command.study.phaseCode}</td>
</tr>
<tr>
    <td class="alt" align="left"><b>Data Entry Status</b></td>
    <td class="alt" align="left">${command.study.dataEntryStatus.code}</td>
</tr>
<tr>
    <td class="alt" align="left"><b>Status</b></td>
    <td class="alt" align="left">${command.study.coordinatingCenterStudyStatus.code}
        <c:forEach items="${command.study.possibleStatusTransitions}" var="coCenterStatus">
            <c:if test="${coCenterStatus=='READY_TO_OPEN'}">
                <c:set var="readyToOpen" value="Create"></c:set>
            </c:if>
            <c:if test="${coCenterStatus=='OPEN'}">
                <c:set var="open" value="Open Study"></c:set>
            </c:if>
            <c:if test="${coCenterStatus=='CLOSED_TO_ACCRUAL'}">
                <c:set var="closed" value="Close Study"></c:set>
                <c:set var="commanSepOptVal"
                       value="[['Closed To Accrual And Treatment','Closed To Accrual And Treatment'],
                   		['Closed To Accrual','Closed To Accrual']]">
                </c:set>
            </c:if>
            <c:if test="${coCenterStatus=='TEMPORARILY_CLOSED_TO_ACCRUAL'}">
                <c:set var="closed" value="Close study"></c:set>
                <c:set var="commanSepOptVal"
                       value="[['Closed To Accrual And Treatment','Closed To Accrual And Treatment'],['Closed To Accrual','Closed To Accrual'],
						['Temporarily Closed To Accrual And Treatment','Temporarily Closed To Accrual And Treatment'],
						['Temporarily Closed To Accrual','Temporarily Closed To Accrual']]">
                </c:set>
            </c:if>
        </c:forEach>
        <%--<c:choose>
            <c:when test="${!empty closed}">
                <tags:inPlaceSelect id="changedCoordinatingCenterStudyStatus"
                                    value="${command.study.coordinatingCenterStudyStatus.code}"
                                    path="study.changedCoordinatingCenterStudyStatus"
                                    commanSepOptVal="${commanSepOptVal}">
                </tags:inPlaceSelect>
            </c:when>
            <c:otherwise>
                ${command.study.coordinatingCenterStudyStatus.code}
            </c:otherwise>
        </c:choose>
        <csmauthz:accesscontrol domainObject="${command.study}" hasPrivileges="UPDATE"
                                authorizationCheckName="domainObjectAuthorizationCheck">
            &nbsp;
            <c:if test="${!empty readyToOpen}">
                <input type="button" value="${readyToOpen }"
                       onclick="changeStudyStatus('readyToOpen')"/>
            </c:if>
            <c:if test="${!empty closed}">
                <input type="button" value="${closed }"
                       onclick="editor_changedCoordinatingCenterStudyStatus.enterEditMode('click')"/>
            </c:if>
        </csmauthz:accesscontrol>
        <c:if test="${isRegistrar &&  !empty closed}">
            <script type="text/javascript">
                editor_changedTargetAccrualNumber.dispose();
                editor_study.changedCoordinatingCenterStudyStatus.dispose();
            </script>
        </c:if>--%>
    </td>
</tr>
<tr>
    <td class="alt" align="left"><b>Type</b></td>
    <td class="alt" align="left">${command.study.type}</td>
</tr>
<tr>
    <td class="alt" align="left"><b>Phase</b></td>
    <td class="alt" align="left">${command.study.phaseCode}</td>
</tr>
<tr>
    <td class="alt" align="left"><b>Multi Institution</b></td>
    <td class="alt" align="left">${command.study.multiInstitutionIndicator=="true"?"Yes":"No"}</td>
</tr>

<tr>
    <td class="alt" align="left"><b>Blinded</b></td>
    <td class="alt" align="left">${command.study.blindedIndicator=="true"?"Yes":"No"}</td>
</tr>
<tr>
    <td class="alt" align="left"><b>Consent Version/Date</b></td>
    <td class="alt" align="left">${command.study.consentVersion}</td>
</tr>
<tr>
    <td class="alt" align="left"><b>Stratified</b></td>
    <td class="alt" align="left">${command.study.stratificationIndicator=="true"?"Yes":"No"}</td>
</tr>
<tr>
    <td class="alt" align="left"><b>Randomized</b></td>
    <td class="alt" align="left">${command.study.randomizedIndicator=="true"?"Yes":"No"}</td>
</tr>
<tr>
    <td class="alt" align="left"><b>Randomization Type</b></td>
    <td class="alt" align="left">${command.study.randomizationType.displayName}</td>
</tr>
</table>

</chrome:division>
<br>

<chrome:division title="Epochs &amp; Arms">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%"><b>Epochs</b></th>
            <th><b>Arms</b>
            </th>
        </tr>
        <c:forEach items="${command.study.epochs}" var="epoch">
            <tr>
                <td class="alt">${epoch.name}</td>
                <c:if test="${not empty epoch.arms}">
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0" class="tablecontent">

                            <tr>
                                <th><b>Name</b></th>
                                <th><b>Target Accrual No</b>
                                <th/>
                            </tr>

                            <tr>
                                <c:forEach items="${epoch.arms}" var="arm">
                                <tr>
                                    <td>${arm.name}</td>
                                    <td>${arm.targetAccrualNumber}</td>
                                </tr>
                                </c:forEach>
                        </table>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</chrome:division><chrome:division title="Stratification Factors">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%" scope="col" align="left"><b>Strata</b></th>
            <th scope="col" align="left"><b>Permissible Answers</b></th>
        </tr>
        <c:forEach items="${command.study.epochs}" var="epoch">
            <c:forEach items="${epoch.stratificationCriteria}" var="strat">
                <tr>
                    <td class="alt">${strat.questionText}</td>
                    <td class="alt">
                        <table border="0" cellspacing="0" cellpadding="0" class="tablecontent">
                            <c:forEach items="${strat.permissibleAnswers}" var="ans">
                                <tr>
                                    <td>${ans.permissibleAnswer}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</chrome:division><chrome:division title="Stratum Groups">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%" scope="col" align="left"><b>Stratum Group Number</b></th>
            <th scope="col" align="left"><b>Answer Combination</b></th>

        </tr>
        <c:forEach items="${command.study.epochs}" var="epoch">
            <c:forEach items="${epoch.stratumGroups}" var="stratGrp">
                <tr>
                    <td class="alt">${stratGrp.stratumGroupNumber}</td>
                    <td class="alt">${stratGrp.answerCombinations}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</chrome:division><chrome:division title="Diseases">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%" scope="col" align="left"><b>Disease Term</b></th>
            <th scope="col" align="left"><b>Primary</b></th>
        </tr>
        <c:forEach items="${command.study.studyDiseases}" var="studyDisease" varStatus="status">
            <tr class="results">
                <td class="alt">${studyDisease.diseaseTerm.ctepTerm}</td>
                <td class="alt">${studyDisease.leadDisease=="true"?"Yes":"No"}</td>
            </tr>
        </c:forEach>
    </table>
</chrome:division>
<chrome:division title="Identifiers">
    <h4>Organization Assigned Identifiers</h4>
    <br>
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%" scope="col" align="left">Assigning Authority</th>
            <th width="35%" scope="col" align="left">Identifier Type</th>
            <th scope="col" align="left">Identifier</th>
        </tr>
        <c:forEach items="${command.study.organizationAssignedIdentifiers}" var="orgIdentifier">
            <tr class="results">
                <td class="alt" align="left">${orgIdentifier.healthcareSite.name}</td>
                <td class="alt" align="left">${orgIdentifier.type}</td>
                <td class="alt" align="left">${orgIdentifier.value}</td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <h4>System Assigned Identifiers</h4>
    <br>
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%" scope="col" align="left">System Name</th>
            <th width="35%" scope="col" align="left">Identifier Type</th>
            <th scope="col" align="left">Identifier</th>
        </tr>
        <c:forEach items="${command.study.systemAssignedIdentifiers}" var="identifier">
            <tr class="results">
                <td class="alt" align="left">${identifier.systemName}</td>
                <td class="alt" align="left">${identifier.type}</td>
                <td class="alt" align="left">${identifier.value}</td>
            </tr>
        </c:forEach>
    </table>
</chrome:division>
<div id="companionDiv">
<div id="companionAssociationsDiv"
     <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>>
    <chrome:division title="Companion Studies">
        <table class="tablecontent" width="60%">
            <tr>
                <th width="45%" scope="col" align="left"><b>Companion Study Short Title</b></th>
                <th width="30%" scope="col" align="left"><b>Data Entry Status</b></th>
                <th width="15%" scope="col" align="left"><b>Status</b></th>
                <th width="10%" scope="col" align="left"><b>Mandatory</b></th>
            </tr>
            <c:forEach items="${command.study.companionStudyAssociations}" var="companionStudyAssociation">
                <tr>
                    <td class="alt">${companionStudyAssociation.companionStudy.shortTitleText}</td>
                    <td class="alt">${companionStudyAssociation.companionStudy.dataEntryStatus.code}</td>
                    <td class="alt">${companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.code}</td>
                    <td class="alt">${companionStudyAssociation.mandatoryIndicator=="true"?"Yes":"No"}</td>
                    <td class="alt">
                        <c:choose>
                            <c:when test="${(companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.name == 'OPEN') || (companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.name == 'READY_TO_OPEN')}">
                                <input type="button" id="manageCompanionStudy" value="View"
                                       onclick="javascript:document.location='<c:url value='/pages/study/viewStudy?studyId=${companionStudyAssociation.companionStudy.id}' />'"/>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${not empty editAuthorizationTask}">
                                    <csmauthz:accesscontrol domainObject="${editAuthorizationTask}"
                                                            authorizationCheckName="taskAuthorizationCheck">
                                        <input type="button" id="editCompanionStudy" value="Edit"
                                               onclick="javascript:document.location='<c:url value='/pages/study/editCompanionStudy?studyId=${companionStudyAssociation.companionStudy.id}' />'"/>
                                    </csmauthz:accesscontrol>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </chrome:division>
</div>
</div>
<div
        <c:if test="${command.study.companionIndicator=='false' || (command.study.companionIndicator=='true' && command.study.standaloneIndicator=='true')}">style="display:none;"</c:if>>
    <chrome:division title="Parent Study">
        <table class="tablecontent" width="60%">
            <tr>
                <th width="50%" scope="col" align="left"><b>Short Title</b></th>
                <th width="25%" scope="col" align="left"><b>Status</b></th>
            </tr>
            <c:forEach items="${command.study.parentStudyAssociations}" var="parentStudyAssociation">
                <tr>
                    <td class="alt">${parentStudyAssociation.parentStudy.shortTitleText}</td>
                    <td class="alt">${parentStudyAssociation.parentStudy.coordinatingCenterStudyStatus.code}</td>
                    <td class="alt">
                        <input type="button" id="manageParentStudy" value="View"
                               onclick="javascript:document.location='<c:url value='/pages/study/viewStudy?studyId=${parentStudyAssociation.parentStudy.id}' />'"/>
                    </td>

                </tr>
            </c:forEach>
        </table>
    </chrome:division>
</div>
<chrome:division title="Amendments">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="15%" scope="col" align="left">Version #</th>
            <th width="30%" scope="col" align="left">Amendment Date</th>
            <th width="55%" scope="col" align="left">Comments</th>
        </tr>
        <c:forEach items="${command.study.studyAmendments}" var="amendment">
            <tr class="results">
                <td class="alt" align="left">${amendment.amendmentVersion}</td>
                <td class="alt" align="left">${amendment.amendmentDateStr}</td>
                <td class="alt" align="left">${amendment.comments}</td>
            </tr>
        </c:forEach>
    </table>
</chrome:division>
</div>

<c:if test="${command.study.coordinatingCenterStudyStatus == 'OPEN' && isCCTSEnv}">
    <chrome:division title="CCTS Workflow">
        <div class="content">
            <div class="row">
                <table width="60%">
                    <tr>
                        <td width="15%" align="right">
                            <b>Broadcast Status:</b>
                        </td>
                        <td width="85%" align="left">
                            <div id="broadcastResponse">
                                    ${!empty command.study.cctsWorkflowStatus.displayName?command.study.cctsWorkflowStatus.displayName:'The study has not yet been broadcasted. Click on the broadcast button to create study in other CTMS Application'}
                                <c:if test="${command.study.cctsWorkflowStatus=='MESSAGE_SEND_FAILED'}">
                                    <a href="javascript:C3PR.showCCTSError();">Click here to see the error message</a>

                                    <div id="cctsErrorMessage"
                                         style="display: none;">${command.study.cctsErrorString}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="button" id="broadcastStatusBtn" value="Refresh"
                                   onclick="getBroadcastStatus();"/>
                            <input type="button" id="broadcastBtn" value="Broadcast"
                                   onclick="doSendMessageToESB();"/>

                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="built-cctsErrorMessage" style="display: none;"/>
    </chrome:division>
</c:if>

<c:if test="${not empty editAuthorizationTask}">
    <div class="content buttons autoclear">
        <div class="flow-buttons">
        <span class="next">
        	<csmauthz:accesscontrol domainObject="${command.study}" hasPrivileges="UPDATE"
                                    authorizationCheckName="domainObjectAuthorizationCheck">
                &nbsp;
                <c:if test="${!empty open}">
                    <input type="button" value="${open }"
                           onclick="changeStudyStatus('open')"/>
                </c:if>
                <c:if test="${!empty readyToOpen}">
	                <input type="button" value="${readyToOpen }"
	                       onclick="changeStudyStatus('readyToOpen')"/>
	            </c:if>
	            <c:if test="${!empty closed}">
	                <input type="button" value="${closed }"
	                       onclick="changeStudyStatus('close')"/>
	            </c:if>
            </csmauthz:accesscontrol>
                <c:choose>
                    <c:when test="${command.study.companionIndicator=='true'}">
                        <c:set var="amendURL" value="amendCompanionStudy"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="amendURL" value="amendStudy"/>
                    </c:otherwise>
                </c:choose>
            	<c:if test="${empty flowType}">
	                <csmauthz:accesscontrol domainObject="${editAuthorizationTask}" authorizationCheckName="taskAuthorizationCheck">
	                    <input type="button" value="Edit Study" onclick="document.location='../study/editStudy?studyId=${command.study.id}'"/>
	                    <input type="button" value="Amend Study" id="amendButtonDisplayDiv" 
	                    	<c:if test="${command.study.coordinatingCenterStudyStatus != 'OPEN' && command.study.coordinatingCenterStudyStatus != 'AMENDMENT_PENDING'}">style="display:none" </c:if>
	                                                            onclick="document.location='../study/${amendURL }?studyId=${command.study.id}'"/>
	                </csmauthz:accesscontrol>
                </c:if> 
                <c:if test="${not empty flowType}">
                	<input type="button" value="Manage Study" onclick="document.location='../study/viewStudy?studyId=${command.study.id}'"/>
                </c:if>
        </span>
        </div>
    </div>
</c:if>
<br/>
<c:if test="${empty flowType}">
<div align="right">
 	<input type="button" value="Export Study" onclick="doExportAction();"/>
    <input type="button" value="Print" onClick="javascript:C3PR.printElement('printable');"/>
</div>
</c:if>
</chrome:box>

<div id="errorsDiv" style="display:none">
	<div class="value" align="left">
		<font size="2" face="Verdana" color="red">
			Cannot Create Study. Please review the data.
		</font>
	</div>
	
	<br>
	
	<c:forEach items="${errors}" var="error" >
		<div class="value" align="left">
			<font size="1" face="Verdana" color="black">
				${error.errorMessage}
			</font>
		</div>
	</c:forEach>
</div>

</form:form>
</body>
</html>
