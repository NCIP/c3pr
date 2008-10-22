<%@ include file="taglibs.jsp"%>


<html>
<head>
<title><studyTags:htmlTitle study="${command.study}" /></title>
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
            <td class="alt" align="left">${command.study.shortTitleText}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Primary Identifier</b></td>
            <td class="alt" align="left">${command.study.primaryIdentifier}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Target Accrual Number</b></td>
            <td class="alt" align="left">${command.study.targetAccrualNumber}</td>
        </tr>
         <tr>
            <td class="alt" align="left"><b>Data Entry Status</b></td>
            <td class="alt" align="left">${command.study.dataEntryStatus.code}</td>
        </tr> 
         <tr>
            <td class="alt" align="left"><b>Status</b></td>
            <td class="alt" align="left">${command.study.coordinatingCenterStudyStatus.displayName}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Phase</b></td>
            <td class="alt" align="left">${command.study.phaseCode}</td>
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
            <td class="alt" align="left"><b>Randomized</b></td>
            <td class="alt" align="left">${command.study.randomizedIndicator=="true"?"Yes":"No"}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b>Randomization Type</b></td>
            <td class="alt" align="left">${command.study.randomizationType.displayName}</td>
        </tr>
    </table>
</chrome:division>

<chrome:division title="Epochs & Arms">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%"><b>Epochs</b></th>
            <th><b>Arms</b>
        </tr>
        <c:forEach items="${command.study.epochs}" var="epoch">
            <tr>
                <td>${epoch.name}</td>
				<c:if test="${not empty epoch.arms}">
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
				</c:if>
            </tr>
            
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Stratification Factors">
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
                                        <td class="alt" align="left">${ans.permissibleAnswer}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                </c:forEach>
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Stratum Groups">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%" scope="col" align="left"><b>Stratum Group Number</b></th>
            <th scope="col" align="left"><b>Answer Combination</b></th>

        </tr>
        <c:forEach items="${command.study.epochs}" var="epoch">
                <c:forEach items="${epoch.stratumGroups}" var="stratGrp">
                    <tr>
                        <td class="alt">${stratGrp.stratumGroupNumber}</td>
                        <td class="alt">
                            ${stratGrp.answerCombinations}
                        </td>
                    </tr>
                </c:forEach>
        </c:forEach>
    </table>
</chrome:division>

<div <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>>
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
                <td class="alt">${companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.displayName}</td>
                <td class="alt">${companionStudyAssociation.mandatoryIndicator=="true"?"Yes":"No"}</td>
   	        </tr>	           
        </c:forEach>
    </table>
</chrome:division>
</div>

<chrome:division title="Sites">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="40%" scope="col" align="left">Study Site</th>
            <th width="15%" scope="col" align="left">Status</th>
            <th width="15%" scope="col" align="left">Role</th>
            <th width="10%" scope="col" align="left">Start Date</th>
            <th width="20%" scope="col" align="left">IRB Approval Date</th>
        </tr>
        <c:forEach items="${command.study.studySites}" var="studySite">
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
<h4>Coordinating Assigned Identifier</h4>
	<br>
		<table class="tablecontent" width="60%">
			<tr>
				<th width="50%" scope="col" align="left">Assigning Authority</th>
	            <th width="35%" scope="col" align="left">Identifier Type</th>
	            <th scope="col" align="left">Identifier</th>
			</tr>
			<c:if test="${command.study.coordinatingCenterIdentifier != null}"
				var="orgIdentifier">
				<tr class="results">
					<td class="alt" align="left">${orgIdentifier.healthcareSite.name}</td>
					<td class="alt" align="left">${orgIdentifier.type}</td>
					<td class="alt" align="left">${orgIdentifier.value}</td>
				</tr>
			</c:if>
		</table>
	<br>
</chrome:division>


</div>
			<div class="content buttons autoclear">
			<div class="flow-buttons"><span class="next"> 
				<input type="button" value="Open" id="saveActiveButtonDisplayDiv" onclick="activateAndSaveStudy();return false;"/>
				<input type="button" value="Print" onClick="javascript:C3PR.printElement('printable');"/>
 			</span></div>
			</div>

   </jsp:attribute>
</tags:c3prCustomTabForm>
</body>
</html>
