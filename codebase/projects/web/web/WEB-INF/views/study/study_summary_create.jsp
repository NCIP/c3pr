<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><studyTags:htmlTitle study="${command}" /></title>
    
<script>

function activateAndSaveStudy(){
document.getElementById("_activate").value="true";
document.getElementById("command").submit();
}

</script>

</head>

<body>
<tags:c3prCustomTabForm tab="${tab}" flow="${flow}"
              title="Study Overview" willSave="${willSave}" formName="review" needReset="false">
<jsp:attribute name="repeatingFields">
<div>
    <input type="hidden" name="_finish" value="true"/>
    <div>
    <input type="hidden" name="_activate" id="_activate" value="false"/>
</div>
</div>
<div id="printable">
<chrome:division id="study-details" title="Basic Details" >
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
            <td class="alt" align="left">${command.targetAccrualNumber}</td>
        </tr>
         <tr>
            <td class="alt" align="left"><b>Data Entry Status</b></td>
            <td class="alt" align="left">${command.dataEntryStatus.code}</td>
        </tr> 
         <tr>
            <td class="alt" align="left"><b>Status</b></td>
            <td class="alt" align="left">${command.coordinatingCenterStudyStatus.code}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Phase</b></td>
            <td class="alt" align="left">${command.phaseCode}</td>
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
            <td class="alt" align="left"><b>Randomized</b></td>
            <td class="alt" align="left">${command.randomizedIndicator=="true"?"Yes":"No"}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Randomization Type</b></td>
            <td class="alt" align="left">${command.randomizationType.displayName}</td>
        </tr>
    </table>
</chrome:division>

<chrome:division title="Epochs & Arms">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%"><b>Epochs</b></th>
            <th><b>Arms</b>
        </tr>
        <c:forEach items="${command.epochs}" var="epoch">
        <c:if
                    test="${epoch.displayRole!='NonTreatment'}">
            <tr>
                <td>${epoch.name}</td>
                <td>
                        <table border="0" cellspacing="0" cellpadding="0" class="tablecontent">
                            <tr>
                                <th><b>Name</b></th>
                                <th><b>Target Accrual No.</b>
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
            </tr>
            
            </c:if>
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Stratification Factors">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%" scope="col" align="left"><b>Strata</b></th>
            <th scope="col" align="left"><b>Permissible Answers</b></th>

        </tr>
        <c:forEach items="${command.epochs}" var="epoch">
            <c:if
                    test="${epoch.displayRole!='NonTreatment'}">
                <c:forEach items="${epoch.stratificationCriteria}" var="strat">
                    <tr>
                        <td class="alt">${strat.questionText}</td>
                        <td class="alt">
                            <table border="0" cellspacing="0" cellpadding="0" class="tablecontent">
                                <c:forEach items="${strat.permissibleAnswers}" var="ans">
                                    <tr>
                                        <td class="alt" align="left">${ans.permissibleAnswer}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Stratum Groups">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%" scope="col" align="left"><b>Stratum Group Number</b></th>
            <th scope="col" align="left"><b>Answer Combination</b></th>

        </tr>
        <c:forEach items="${command.epochs}" var="epoch">
            <c:if
                    test="${epoch.displayRole!='NonTreatment'}">
                <c:forEach items="${epoch.stratumGroups}" var="stratGrp">
                    <tr>
                        <td class="alt">${stratGrp.stratumGroupNumber}</td>
                        <td class="alt">
                            ${stratGrp.answerCombinations}
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Diseases">
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
    <table class="tablecontent" width="60%">
        <tr>
            <th width="40%" scope="col" align="left">Study Site</th>
            <th width="15%" scope="col" align="left">Status</th>
            <th width="15%" scope="col" align="left">Role</th>
            <th width="10%" scope="col" align="left">Start Date</th>
            <th width="20%" scope="col" align="left">IRB Approval Date</th>
        </tr>
        <c:forEach items="${command.studySites}" var="studySite">
            <tr class="results">
                <td class="alt" align="left">${studySite.healthcareSite.name}</td>
                <td class="alt" align="left">${studySite.siteStudyStatus.code}</td>
                <td class="alt" align="left">${studySite.roleCode}</td>
                <td class="alt" align="left">${studySite.startDateStr}</td>
                <td class="alt" align="left">${studySite.irbApprovalDateStr}</td>
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
			<c:forEach items="${command.organizationAssignedIdentifiers}"
				var="orgIdentifier">
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
		<c:forEach items="${command.systemAssignedIdentifiers}"
		var="identifier">
			<tr class="results">
				<td class="alt" align="left">${identifier.systemName}</td>
				<td class="alt" align="left">${identifier.type}</td>
				<td class="alt" align="left">${identifier.value}</td>
			</tr>
		</c:forEach>
	</table>
</chrome:division>

<chrome:division title="Investigators">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="20%" scope="col" align="left">Name</th>
            <th width="18%" scope="col" align="left">Role</th>
            <th width="17%" scope="col" align="left">Status</th>
            <th width="45%" scope="col" align="left">Organization</th>
        </tr>
        <c:forEach items="${command.studyOrganizations}" var="studyOrganization" varStatus="status">
            <c:forEach items="${studyOrganization.studyInvestigators}" var="studyInvestigator" varStatus="status">
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
        <c:forEach items="${command.studyOrganizations}" var="studyOrganization" varStatus="status">
            <c:forEach items="${studyOrganization.studyPersonnel}" var="studyPersonnel" varStatus="status">
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
        <c:forEach items="${command.notifications}" var="notification">
            <tr>
                <td class="alt">${notification.threshold}</td>
                <td class="alt">${notification.emailAddresses}</td>
                <td class="alt">${notification.roles}</td>
   	        </tr>	           
        </c:forEach>
    </table>
</chrome:division>
<div <c:if test="${command.companionIndicator=='true'}">style="display:none;"</c:if>>
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
   	        </tr>	           
        </c:forEach>
    </table>
</chrome:division>
</div>
</div>
			<div class="content buttons autoclear">
			<div class="flow-buttons"><span class="next"> 
				<input type="button" value="Save and Activate" id="saveActiveButtonDisplayDiv" onclick="activateAndSaveStudy();return false;"/>
				<input type="button" value="Print" onClick="javascript:C3PR.printElement('printable');"/>
 			</span></div>
			</div>

   </jsp:attribute>
</tags:c3prCustomTabForm>
</body>
</html>
