<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><studyTags:htmlTitle study="${command.study}" /></title>
<jwr:script src="/js/tabbedflow.js" />

<script>
function amendmentTypeChanged(){
	$("gracePeriodInput").className="" ;
	if($('study.currentStudyAmendment.amendmentType').value == 'IMMEDIATE_AFTER_GRACE_PERIOD'){
		$('gracePeriodDiv').style.display = "";
		 $("gracePeriodInput").className="validate-notEmpty&&NONZERO_NUMERIC";
	} else {
		$('gracePeriodDiv').style.display = "none";
	}
}

ValidationManager.submitPostProcess= function(formElement, continueSubmission){
	    if(formElement.id=="command" && continueSubmission){
	 	}
	return continueSubmission;
} 

function validateAmendmentReasons(){
	var alreadyChecked = false ;
	$$('.amendmentReason').each(function(element){
		if(element.checked ){
			alreadyChecked = true ;
		}
	});
	if(!alreadyChecked){
		Dialog.alert("Please select atleast one amendment reason", {className: "alphacube", width:240, okLabel: "Done" });
		return;
	}
}


</script>
</head>
<body>
<form:form name="myform" cssClass="standard">
	<tags:tabFields tab="${tab}" />
	<chrome:box title="Amendment Details">
		<tags:errors path="study.studyVersions" />
		<chrome:division id="study-amendments">
			<div class="row">
				<div class="label"><tags:requiredIndicator /><fmt:message key="study.versionNameNumber" /></div>
				<div class="value"><form:input
					path="study.currentStudyAmendment.name" size="25" cssClass="validate-NOTEMPTY" /><tags:hoverHint keyProp="study.versionNameNumber"/></div>
			</div>
			<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="study.amendmentType" /></div>
			<div class="value">
				<form:select path="study.currentStudyAmendment.amendmentType" cssClass="validate-notEmpty" onchange="amendmentTypeChanged();">
                	<form:options items="${amendmentTypeOptions}" itemLabel="desc" itemValue="code" />
            	</form:select>
            	<tags:hoverHint keyProp="study.amendmentType"/>
			</div>
			</div>
			<div class="row" style="<c:if test="${command.study.currentStudyAmendment.amendmentType != 'IMMEDIATE_AFTER_GRACE_PERIOD'}">display:none</c:if>" id="gracePeriodDiv">
				<div class="label"><tags:requiredIndicator /><fmt:message key="study.gracePeriod" /></div>
				<div class="value">
					<form:input id="gracePeriodInput" path="study.currentStudyAmendment.gracePeriod" size="6" /><tags:hoverHint keyProp="study.gracePeriod"/></div>
			</div>
			<c:if test="${command.study.currentStudyAmendment.amendmentType == 'IMMEDIATE_AFTER_GRACE_PERIOD'}">
			<script>
					$("gracePeriodInput").className="validate-notEmpty&&NONZERO_NUMERIC";
			</script>
			</c:if>
			<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="study.amendmentDate" /></div>
			<div class="value">
				<tags:dateInput path="study.currentStudyAmendment.versionDate" validateDate="true" cssClass="validate-NOTEMPTY"/><tags:hoverHint keyProp="study.version.date"/></div>
			</div>
			<div class="row">
			<div class="label"><fmt:message key="c3pr.common.comments" /></div>
			<div class="value"><form:textarea
				path="study.currentStudyAmendment.comments" rows="2" cols="44" />
			</div>
			</div>
		</chrome:division>
		<chrome:division id="study-amendments"
			title="Reasons for Amendment (Atleast One)">
			<table>
                      <tr>
                      	<td width="25%"><b><fmt:message key="study.basicDetails"/></b></td>
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="DETAIL" cssClass="amendmentReason"/></td>
                          <td width="25%"><b><fmt:message key="study.epoch&Arms"/></b></td>
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="DESIGN" cssClass="amendmentReason"/></td>
                      </tr>
                      <tr>
                      	<td width="25%"><b><fmt:message key="study.eligibility"/></b></td>
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="ELIGIBILITY" cssClass="amendmentReason"/></td>
                          <td><b><fmt:message key="study.stratification"/></b></td>
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="STRATIFICATION" cssClass="amendmentReason"/></td>
                      </tr>
                      <tr>
                          <td><b><fmt:message key="study.randomization"/></b></td>
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="RANDOMIZATION" cssClass="amendmentReason"/></td>
                          <td><b><fmt:message key="study.diseases"/></b></td>
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="DISEASE" cssClass="amendmentReason"/></td>
                      </tr>
                      <tr>
                          <td><b><fmt:message key="study.consent"/></b></td>
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="CONSENT" cssClass="amendmentReason"/></td>
                          <td <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>><b><fmt:message key="study.companionStudy"/></b></td>
                          <td <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="COMPANION" cssClass="amendmentReason"/></td>
                      </tr>
                  </table>
		</chrome:division>
	</chrome:box>
	<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" />
</form:form>
</body>
</html>
