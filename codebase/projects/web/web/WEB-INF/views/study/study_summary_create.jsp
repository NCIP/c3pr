<%@ include file="taglibs.jsp" %>

<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}"/></title>

<script>
function activateAndSaveStudy(){
	if (${fn:length(errors)} > 0){
		var d = $('errorsOpenDiv');
		Dialog.alert(d.innerHTML, 
		{width:500, height:200, okLabel: "close", ok:function(win) {debug("validate alert panel"); return true;}});
	} else {
		document.getElementById("_activate").value="true";
		document.getElementById("_action").value="open";
		document.getElementById("viewDetails").submit();
	}
}

function createStudy(){
	if (${fn:length(errors)} > 0){
		var d = $('errorsCreateDiv');
		Dialog.alert(d.innerHTML, 
		{width:500, height:200, okLabel: "close", ok:function(win) {debug("validate alert panel"); return true;}});
	} else {
		document.getElementById("_activate").value="false";
		document.getElementById("_action").value="create";
		document.getElementById("viewDetails").submit();
	}
}
</script>
</head>

<body>
<form:form id="viewDetails" name="viewDetails">
<tags:tabFields tab="${tab}"/>
<chrome:box title="Study Summary">
<tags:instructions code="study_summary_create" />
<div>
    <input type="hidden" name="_finish" value="true"/>
    <div>
    <input type="hidden" name="_activate" id="_activate" value="false"/>
    <input type="hidden" name="_action" id="_action"/>
</div>
</div>
<tags:errors path="*"/>
<chrome:division id="study-details" title="Basic Details">
    <table class="tablecontent" width="60%">
        <tr>
            <td width="35%" class="alt" align="left"><b><fmt:message key="study.shortTitle"/></b></td>
            <td class="alt" align="left">${command.study.shortTitleText}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b><fmt:message key="c3pr.common.primaryIdentifier"/></b></td>
            <td class="alt" align="left">${command.study.primaryIdentifier}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b><fmt:message key="c3pr.common.targetAccrual"/></b></td>
            <td class="alt" align="left">${command.study.targetAccrualNumber}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b><fmt:message key="c3pr.common.dataEntryStatus"/></b></td>
            <td class="alt" align="left">${command.study.dataEntryStatus.code}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b><fmt:message key="c3pr.common.status"/></b></td>
            <td class="alt" align="left">${command.study.coordinatingCenterStudyStatus.displayName}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b><fmt:message key="study.phase"/></b></td>
            <td class="alt" align="left">${command.study.phaseCode}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b><fmt:message key="c3pr.common.type"/></b></td>
            <td class="alt" align="left">${command.study.type}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b><fmt:message key="study.multiInstitution"/></b></td>
            <td class="alt" align="left">${command.study.multiInstitutionIndicator=="true"?"Yes":"No"}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b><fmt:message key="study.blinded"/></b></td>
            <td class="alt" align="left">${command.study.blindedIndicator=="true"?"Yes":"No"}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b><fmt:message key="study.consentVersionDate"/></b></td>
            <td class="alt" align="left">${command.study.consentVersion}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b><fmt:message key="study.randomized"/></b></td>
            <td class="alt" align="left">${command.study.randomizedIndicator=="true"?"Yes":"No"}</td>
        </tr>
        <tr>
            <td class="alt" align="left"><b><fmt:message key="study.randomizationType"/></b></td>
            <td class="alt" align="left">${command.study.randomizationType.displayName}</td>
        </tr>
    </table>
</chrome:division>

<chrome:division title="Epochs & Arms">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%"><b><fmt:message key="study.epochs"/></b></th>
            <th><b><fmt:message key="study.epoch.arms"/></b>
        </tr>
        <c:forEach items="${command.study.epochs}" var="epoch">
            <tr>
                <td>${epoch.name}</td>
                <c:if test="${not empty epoch.arms}">
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0" class="tablecontent">
                            <tr>
                                <th><b><fmt:message key="c3pr.common.name"/></b></th>
                                <th><b><fmt:message key="c3pr.common.targetAccrual"/></b>
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
            <th width="50%" scope="col" align="left"><b><fmt:message key="study.criterion"/></b></th>
            <th scope="col" align="left"><b><fmt:message key="study.answers"/></b></th>

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
            <th width="50%" scope="col" align="left"><b><fmt:message key="registration.stratumGroupNumber"/></b></th>
            <th scope="col" align="left"><b><fmt:message key="study.answerCombination"/></b></th>

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
                <th width="45%" scope="col" align="left"><b><fmt:message key="study.companionStudyShortTitle"/></b></th>
                <th width="30%" scope="col" align="left"><b><fmt:message key="c3pr.common.dataEntryStatus"/></b></th>
                <th width="15%" scope="col" align="left"><b><fmt:message key="c3pr.common.status"/></b></th>
                <th width="10%" scope="col" align="left"><b><fmt:message key="c3pr.common.mandatory"/></b></th>
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

<chrome:division title="Identifiers">
    <h4>Coordinating Assigned Identifier</h4>
    <br>
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%" scope="col" align="left"><fmt:message key="c3pr.common.assigningAuthority"/></th>
            <th width="35%" scope="col" align="left"><fmt:message key="c3pr.common.identifierType"/></th>
            <th scope="col" align="left"><fmt:message key="c3pr.common.identifier"/></th>
        </tr>
        <c:if test="${command.study.coordinatingCenterAssignedIdentifier != null}">
        <tr class="results">
            <td class="alt" align="left">${command.study.coordinatingCenterAssignedIdentifier.healthcareSite.name}</td>
            <td class="alt" align="left">${command.study.coordinatingCenterAssignedIdentifier.type}</td>
            <td class="alt" align="left">${command.study.coordinatingCenterAssignedIdentifier.value}</td>
            </c:if>
    </table>
    <br>

    <h4>Funding Sponsor Identifier</h4>
    <br>
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%" scope="col" align="left"><fmt:message key="c3pr.common.assigningAuthority"/></th>
            <th width="35%" scope="col" align="left"><fmt:message key="c3pr.common.identifierType"/></th>
            <th scope="col" align="left"><fmt:message key="c3pr.common.identifier"/></th>
        </tr>
        <c:if test="${command.study.fundingSponsorAssignedIdentifier != null}">
            <tr class="results">
                <td class="alt" align="left">${command.study.fundingSponsorAssignedIdentifier.healthcareSite.name}</td>
                <td class="alt" align="left">${command.study.fundingSponsorAssignedIdentifier.type}</td>
                <td class="alt" align="left">${command.study.fundingSponsorAssignedIdentifier.value}</td>
            </tr>
        </c:if>
    </table>
    <br>
</chrome:division>
<div class="content buttons autoclear">
			<div class="flow-buttons"><span class="next"> 
				<c:if test="${!(command.study.companionIndicator && !command.study.standaloneIndicator)}"><input type="button" value="Open" id="saveActiveButtonDisplayDiv" onClick="activateAndSaveStudy();return false;"/></c:if>
				<input type="button" value="Create" id="createButtonDisplayDiv" onClick="createStudy();return false;"/>
<input type="button" value="Manage" id="manageStudy"
                       onclick="javascript:document.location='<c:url value='/pages/study/viewStudy?studyId=${command.study.id}' />'"/>
			</span></div>
			</div>
</chrome:box>
<div id="errorsOpenDiv" style="display:none">
	<div class="value" align="left">
		<font size="2" face="Verdana" color="red">
			Cannot Open Study. Please review the data.
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
<div id="errorsCreateDiv" style="display:none">
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
