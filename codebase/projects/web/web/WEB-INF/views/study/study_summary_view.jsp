<%@ include file="taglibs.jsp"%>

<html>
<head>
	<title><studyTags:htmlTitle study="${command}" /></title>
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


        function activateInPlaceEditing(arrayElements) {
            for (aE = 0; aE < arrayElements.length; aE++) {
                arrayElements[aE].enterEditMode('click');
            }
        }

        function statusChangeCallback(statusCode) {
            elmt = document.getElementById('amendButtonDisplayDiv');
            if (statusCode == 'Active' || statusCode == 'Amendment Pending') {
                elmt.style.display = "";
            } else {
                elmt.style.display = "none";
            }
        }

      	function reloadCompanion(){
   			<tags:tabMethod method="reloadCompanion" divElement="'companionAssociationsDiv'" formName="'tabMethodForm'" viewName="/study/companionSection"/>
   		}
    </script>
</head>

<body>
<form:form id="viewDetails" name="viewDetails">
<tags:tabFields tab="${tab}"/>
<chrome:box title="Study Summary">
<div><input type="hidden" name="_finish" value="true"/> <input
        type="hidden" name="_action" value=""></div>

<div id="printable">
<chrome:division id="study-details" title="Study Details">
    <table class="tablecontent" width="60%">
        <tr>
            <td width="35%" class="alt" align="left"><b>Short Title</b></td>
            <td class="alt" align="left">${command.shortTitleText}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Primary Identifier</b></td>
            <td class="alt" align="left">${command.primaryIdentifier}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Target Accrual Number</b></td>
            <td class="alt" align="left">
                <tags:inPlaceEdit value="${command.targetAccrualNumber}" path="changedTargetAccrualNumber"
                                  validations="validate-notEmpty"/>
                       <csmauthz:accesscontrol domainObject="${command}" hasPrivileges="UPDATE"
                            authorizationCheckName="domainObjectAuthorizationCheck">
        					&nbsp; <input type="button" value="Edit"
                      					onclick="editor_changedTargetAccrualNumber.enterEditMode('click')"/>
    				   </csmauthz:accesscontrol>
            </td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Phase</b></td>
            <td class="alt" align="left">${command.phaseCode}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Data Entry Status</b></td>
            <td class="alt" align="left">${command.dataEntryStatus.code}</td>
        </tr>
        <tr>
            <td class="alt" align="left" rows="2"><b>Status</b></td>
            <c:set var="commanSepOptVal"
                   value="[['Active','Active'],
						['Closed To Accrual And Treatment','Closed To Accrual And Treatment'],['Closed To Accrual','Closed To Accrual'],
						['Temporarily Closed To Accrual And Treatment','Temporarily Closed To Accrual And Treatment'],
						['Temporarily Closed To Accrual','Temporarily Closed To Accrual']]">
			</c:set>
			<c:set var="commanSepOptValForEmbeded"
                   value="[['Ready For Activation','Ready For Activation'],
						['Closed To Accrual And Treatment','Closed To Accrual And Treatment'],['Closed To Accrual','Closed To Accrual'],
						['Temporarily Closed To Accrual And Treatment','Temporarily Closed To Accrual And Treatment'],
						['Temporarily Closed To Accrual','Temporarily Closed To Accrual']]"></c:set>
            <td>
				<c:forEach items="${command.parentStudyAssociations}" var="parentStudyAssociation">
					<c:if test="${!(parentStudyAssociation.parentStudy.coordinatingCenterStudyStatus.name == 'ACTIVE')}">
							<c:set var="noActiveStatusForCompanion" value="true"></c:set>
					</c:if>
				</c:forEach>
				<c:choose> 
					<c:when test="${!empty noActiveStatusForCompanion && noActiveStatusForCompanion == 'true' && command.companionIndicator == true && command.standaloneIndicator == false}"> 
						<c:set var="tempSelectOpts" value="${commanSepOptValForEmbeded}"></c:set> 
					</c:when>  
					<c:otherwise> 
						<c:set var="tempSelectOpts" value="${commanSepOptVal}"></c:set>
					</c:otherwise> 
				</c:choose>
                <tags:inPlaceSelect
                        value="${command.coordinatingCenterStudyStatus.code}"
                        path="changedCoordinatingCenterStudyStatus"
                        commanSepOptVal="${tempSelectOpts}">
				</tags:inPlaceSelect>
                <csmauthz:accesscontrol domainObject="${command}" hasPrivileges="UPDATE"
                                        authorizationCheckName="domainObjectAuthorizationCheck">
                    &nbsp; <input type="button" value="Change Status"
                                  onclick="editor_changedCoordinatingCenterStudyStatus.enterEditMode('click')"/>
                </csmauthz:accesscontrol>
                <c:if test="${isRegistrar}">
	                 <script type="text/javascript">
	                 				  editor_changedTargetAccrualNumber.dispose();
	                 				  editor_changedCoordinatingCenterStudyStatus.dispose();
	                 </script>
                </c:if>
            </td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Type</b></td>
            <td class="alt" align="left">${command.type}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Phase</b></td>
            <td class="alt" align="left">${command.phaseCode}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Multi Institution</b></td>
            <td class="alt" align="left">${command.multiInstitutionIndicator=="true"?"Yes":"No"}</td>
        </tr>

        <tr>
            <td class="alt" align="left"><b>Blinded</b></td>
            <td class="alt" align="left">${command.blindedIndicator=="true"?"Yes":"No"}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Consent Version/Date</b></td>
            <td class="alt" align="left">${command.consentVersion}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Stratified</b></td>
            <td class="alt" align="left">${command.stratificationIndicator=="true"?"Yes":"No"}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Randomized</b></td>
            <td class="alt" align="left">${command.randomizedIndicator=="true"?"Yes":"No"}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Randomization Type</b></td>
            <td class="alt" align="left">${command.randomizationType.displayName}</td>
        </tr>
    </table>

</chrome:division>
<br>

<chrome:division title="Epochs &amp; Arms">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%"><b>Epochs</b></th>
            <th><b>Arms</b>
        </th></tr>
        <c:forEach items="${command.epochs}" var="epoch">
            <tr>
                <td class="alt">${epoch.name}</td>
				<c:if test="${not empty epoch.arms}">
	                <td>
	                        <table border="0" cellspacing="0" cellpadding="0" class="tablecontent">
								
	                            <tr>
	                                <th><b>Name</b></th>
	                                <th><b>Target Accrual No</b>
	                            </th></tr>
								
	                            <tr>
	                                <c:forEach items="${epoch.arms}" var="arm">
	                            <tr>
	                                <td>${arm.name}</td>
	                                <td>${arm.targetAccrualNumber}</td>
	                            </tr>
								
	                            </c:forEach>
	                        </tr></table>
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
        <c:forEach items="${command.epochs}" var="epoch">
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
        <c:forEach items="${command.epochs}" var="epoch">
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
        <c:forEach items="${command.studyDiseases}" var="studyDisease" varStatus="status">
            <tr class="results">
                <td class="alt">${studyDisease.diseaseTerm.ctepTerm}</td>
                <td class="alt">${studyDisease.leadDisease=="true"?"Yes":"No"}</td>
            </tr>
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Sites">
    <table class="tablecontent" width="65%">
        <tr>
            <th width="40%" scope="col" align="left">Study Site</th>
            <th width="15%" scope="col" align="left">Status</th>
            <th width="15%" scope="col" align="left">Role</th>
            <th width="10%" scope="col" align="left">Start Date</th>
            <th width="20%" scope="col" align="left">IRB Approval Date</th>
        </tr>
        <c:set var="commanSepOptValSite"
               value="[['Active','Active'],
						['Closed To Accrual And Treatment','Closed To Accrual And Treatment'],['Closed To Accrual','Closed To Accrual'],
						['Temporarily Closed To Accrual And Treatment','Temporarily Closed To Accrual And Treatment'],
						['Temporarily Closed To Accrual','Temporarily Closed To Accrual']]"></c:set>
        <c:forEach items="${command.studySites}" var="studySite"
                   varStatus="status">
            <tr class="results">
                <td class="alt" align="left">${studySite.healthcareSite.name}</td>
                <td>
                    <tags:inPlaceSelect
                            value="${command.studySites[status.index].siteStudyStatus.code}"
                            path="changedSiteStudyStatus_${status.index}"
                            commanSepOptVal="${commanSepOptValSite}"/>
                </td>
                <td class="alt" align="left">${studySite.roleCode}</td>
                <td class="alt" align="left">
                    <tags:inPlaceEdit value="${studySite.startDateStr}" path="changedSiteStudyStartDate_${status.index}"
                                      validations="validate-notEmpty&&DATE"/>
                </td>
                <td class="alt" align="left">
                    <tags:inPlaceEdit value="${studySite.irbApprovalDateStr}"
                                      path="changedSiteStudyIrbApprovalDate_${status.index}" validations="validate-notEmpty&&DATE"/>
                </td>
                <td>
	                <csmauthz:accesscontrol domainObject="${command}" hasPrivileges="UPDATE"
	                                        authorizationCheckName="domainObjectAuthorizationCheck">
	                    &nbsp; <input type="button" value="Edit" onclick="activateInPlaceEditing(eArray_${status.index})"/>
	                </csmauthz:accesscontrol>
                    <script>
                        eArray_${status.index} = new Array();
                        eArray_${status.index}.push(editor_changedSiteStudyStatus_${status.index});
                        eArray_${status.index}.push(editor_changedSiteStudyStartDate_${status.index});
                        eArray_${status.index}.push(editor_changedSiteStudyIrbApprovalDate_${status.index});
                    </script>
                    <c:if test="${isRegistrar}">
	                 <script type="text/javascript">
	                 				  editor_changedSiteStudyStatus_${status.index}.dispose();
	                 				  editor_changedSiteStudyStartDate_${status.index}.dispose();
	                 				  editor_changedSiteStudyIrbApprovalDate_${status.index}.dispose();
	                 </script>
                </c:if>
                </td>
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
        <c:forEach items="${command.organizationAssignedIdentifiers}" var="orgIdentifier">
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
        <c:forEach items="${command.systemAssignedIdentifiers}" var="identifier">
            <tr class="results">
                <td class="alt" align="left">${identifier.systemName}</td>
                <td class="alt" align="left">${identifier.type}</td>
                <td class="alt" align="left">${identifier.value}</td>
            </tr>
        </c:forEach>
    </table>
</chrome:division><chrome:division title="Investigators">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="20%" scope="col" align="left">Name</th>
            <th width="18%" scope="col" align="left">Role</th>
            <th width="17%" scope="col" align="left">Status</th>
            <th width="45%" scope="col" align="left">Organization</th>

        </tr>
        <c:forEach items="${command.studyOrganizations}"
                   var="studyOrganization" varStatus="status">
            <c:forEach items="${studyOrganization.studyInvestigators}"
                       var="studyInvestigator" varStatus="status">
                <tr class="results">
                    <td class="alt"
                        align="left">${studyInvestigator.healthcareSiteInvestigator.investigator.fullName}</td>
                    <td class="alt" align="left">${studyInvestigator.roleCode}</td>
                    <td class="alt" align="left">${studyInvestigator.statusCode}</td>
                    <td class="alt">${studyInvestigator.studyOrganization.healthcareSite.name}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Personnel">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="20%" scope="col" align="left">Name</th>
            <th width="18%" scope="col" align="left">Role</th>
            <th width="17%" scope="col" align="left">Status</th>
            <th width="45%" scope="col" align="left">Organization</th>
        </tr>
        <c:forEach items="${command.studyOrganizations}"
                   var="studyOrganization" varStatus="status">
            <c:forEach items="${studyOrganization.studyPersonnel}"
                       var="studyPersonnel" varStatus="status">
                <tr class="results">
                    <td class="alt">${studyPersonnel.researchStaff.fullName}</td>
                    <td class="alt">${studyPersonnel.roleCode}</td>
                    <td class="alt">${studyPersonnel.statusCode}</td>
                    <td class="alt">${studyPersonnel.studyOrganization.healthcareSite.name}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</chrome:division>



<chrome:division title="Notifications">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="15%" scope="col" align="left"><b>Threshold</b></th>
            <th width="45%" scope="col" align="left"><b>Email</b></th>
            <th width="40%" scope="col" align="left"><b>Role</b></th>
        </tr>
        <c:forEach items="${command.plannedNotifications}" var="notification">
            <tr>
                <td class="alt">${notification.threshold}</td>
                <td class="alt">${notification.emailAddresses}</td>
                <td class="alt">${notification.roles}</td>
            </tr>
        </c:forEach>
    </table>
</chrome:division>

<div id="companionAssociationsDiv" <c:if test="${command.companionIndicator=='true'}">style="display:none;"</c:if>>
<chrome:division title="Companion Studies">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%" scope="col" align="left"><b>Companion Study Short Title</b></th>
            <th width="25%" scope="col" align="left"><b>Status</b></th>
            <th width="25%" scope="col" align="left"><b>Mandatory</b></th>
        </tr>
        <c:forEach items="${command.companionStudyAssociations}" var="companionStudyAssociation">
            <tr>
                <td class="alt">${companionStudyAssociation.companionStudy.shortTitleText}</td>
                <td class="alt">${companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.code}</td>
                <td class="alt">${companionStudyAssociation.mandatoryIndicator=="true"?"Yes":"No"}</td>
                <td class="alt">
					<c:choose>
						<c:when test="${(companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.name == 'ACTIVE') || (companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.name == 'READY_FOR_ACTIVATION')}">                	
							<input type="button" id="manageCompanionStudy" value="Manage" onclick="javascript:document.location='<c:url value='/pages/study/viewStudy?studyId=${companionStudyAssociation.companionStudy.id}' />'"/>
						</c:when>
						<c:otherwise>    
						<c:if test="${not empty editAuthorizationTask}">  
							<csmauthz:accesscontrol domainObject="${editAuthorizationTask}" authorizationCheckName="taskAuthorizationCheck">
								<input type="button" id="editCompanionStudy" value="Edit" onclick="javascript:document.location='<c:url value='/pages/study/editCompanionStudy?studyId=${companionStudyAssociation.companionStudy.id}' />'"/>
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
<div <c:if test="${command.companionIndicator=='false' || (command.companionIndicator=='true' && command.standaloneIndicator=='true')}">style="display:none;"</c:if>>
   <chrome:division title="Parent Study" >
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%" scope="col" align="left"><b>Short Title</b></th>
            <th width="25%" scope="col" align="left"><b>Status</b></th>
        </tr>
        <c:forEach items="${command.parentStudyAssociations}" var="parentStudyAssociation">
            <tr>
                <td class="alt">${parentStudyAssociation.parentStudy.shortTitleText}</td>
                <td class="alt">${parentStudyAssociation.parentStudy.coordinatingCenterStudyStatus.code}</td>
                <td class="alt">
					<input type="button" id="manageParentStudy" value="Manage" onclick="javascript:document.location='<c:url value='/pages/study/viewStudy?studyId=${parentStudyAssociation.parentStudy.id}' />'"/>
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
        <c:forEach items="${command.studyAmendments}" var="amendment">
            <tr class="results">
                <td class="alt" align="left">${amendment.amendmentVersion}</td>
                <td class="alt" align="left">${amendment.amendmentDateStr}</td>
                <td class="alt" align="left">${amendment.comments}</td>
            </tr>
        </c:forEach>
    </table>
</chrome:division>
</div>

<c:if test="${command.coordinatingCenterStudyStatus == 'ACTIVE' && isCCTSEnv}">
    <chrome:division title="CCTS Workflow">
        <div class="content">
            <div class="row">
            	<table width="60%"><tr>
                   	<td width="15%" align="right">
                       <b>Broadcast Status:</b>
                       </td>
					<td width="85%" align="left">
					<div id="broadcastResponse">
                       ${!empty command.cctsWorkflowStatus.displayName?command.cctsWorkflowStatus.displayName:'The study has not yet been broadcasted. Click on the broadcast button to create study in other CTMS Application'}
                       <c:if test="${command.cctsWorkflowStatus=='MESSAGE_SEND_FAILED'}">
                    	<a href="javascript:C3PR.showCCTSError();">Click here to see the error message</a>
                    	<div id="cctsErrorMessage" style="display: none;">${command.cctsErrorString}</div>
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
        <div id="built-cctsErrorMessage" style="display: none;"/>
    </chrome:division>
</c:if>


<%--Optionally display edit mode buttons--%>
<c:if test="${not empty editAuthorizationTask && empty flowType}">
    <div class="content buttons autoclear" <c:if test="${command.companionIndicator=='true'}">style="display:none;"</c:if>>
        <div class="flow-buttons">
        <span class="next">
            <!--export study-->
                <input type="button"
                       value="Export Study" onclick="doExportAction();"/>
                <csmauthz:accesscontrol domainObject="${editAuthorizationTask}" authorizationCheckName="taskAuthorizationCheck">
	                    <input type="button" value="Edit Study" onclick="document.location='../study/editStudy?studyId=${command.id}'"/>
	                    <input type="button" value="Amend Study" id="amendButtonDisplayDiv"  <c:if test="${command.coordinatingCenterStudyStatus != 'ACTIVE' &&
	  							command.coordinatingCenterStudyStatus != 'AMENDMENT_PENDING'}">style="display:none" </c:if>
	                           onclick="document.location='../study/amendStudy?studyId=${command.id}'"/>
                </csmauthz:accesscontrol> 
        </span>
        </div>
        </div>
        <div class="content buttons autoclear"  <c:if test="${command.companionIndicator=='false'}">style="display:none;"</c:if>> 
        <div class="flow-buttons">
        <span class="next">
            <!--export study-->
                <input type="button"
                       value="Export Study" onclick="doExportAction();"/>
                <csmauthz:accesscontrol domainObject="${editAuthorizationTask}" authorizationCheckName="taskAuthorizationCheck">
	                    <input type="button" value="Edit Study" onclick="document.location='../study/editCompanionStudy?studyId=${command.id}'"/>
	                    <input type="button" value="Amend Study" id="amendButtonDisplayDiv"  <c:if test="${command.coordinatingCenterStudyStatus != 'ACTIVE' &&
	  							command.coordinatingCenterStudyStatus != 'AMENDMENT_PENDING'}">style="display:none" </c:if>
	                           onclick="document.location='../study/amendCompanionStudy?studyId=${command.id}'"/>
                </csmauthz:accesscontrol> 
        </span>
        </div>
    </div>

</c:if>
<br/>
<div align="right">
    <input type="button" value="Print" onClick="javascript:C3PR.printElement('printable');"/>
</div>
</chrome:box>

</form:form>
</body>
</html>
