<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><studyTags:htmlTitle study="${command.study}" /></title>
<jwr:script src="/js/tabbedflow.js" />
<style>
#main {
	top: 33px;
}
</style>
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
</script>
</head>
<body>
<form:form name="myform" cssClass="standard">
	<tags:tabFields tab="${tab}" />
	<chrome:box title="Amendment Details">
		<chrome:division id="study-amendments">
			<div class="row">
				<div id="errorMsg1" style="display: none"><span id='sid1' style='color: #EE3324'>Please enter the Version# or Amendment Date.</span><br /></div>
			</div>
			<div class="row">
				<div class="label"><fmt:message key="study.versionNameNumber" /></div>
				<div class="value"><form:input
					path="study.currentStudyAmendment.name" size="25" cssClass="validate-NOTEMPTY" /></div>
			</div>
			<div class="row">
			<div class="label"><fmt:message key="study.amendmentType" /></div>
			<div class="value">
				<form:select path="study.currentStudyAmendment.amendmentType" cssClass="validate-notEmpty" onchange="amendmentTypeChanged();">
                	<form:options items="${amendmentTypeOptions}" itemLabel="desc" itemValue="code" />
            	</form:select>
			</div>
			</div>
			<div class="row" style="<c:if test="${empty command.study.currentStudyAmendment.gracePeriod}">display:none</c:if>" id="gracePeriodDiv">
				<div class="label"><fmt:message key="study.gracePeriod" /></div>
				<div class="value">
					<form:input id="gracePeriodInput" path="study.currentStudyAmendment.gracePeriod" size="6" /></div>
			</div>
			<script>
				if($('gracePeriodDiv').style==""){
					$("gracePeriodInput").className="validate-notEmpty&&NONZERO_NUMERIC";
				}
			</script>
			<div class="row">
			<div class="label"><fmt:message key="study.amendmentDate" /></div>
			<div class="value"><tags:dateInput
				path="study.currentStudyAmendment.versionDate" cssClass="validate-NOTEMPTY"/></div>
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
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="DETAIL"/></td>
                          <td width="25%"><b><fmt:message key="study.epoch&Arms"/></b></td>
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="DESIGN"/></td>
                      </tr>
                      <tr>
                      	<td width="25%"><b><fmt:message key="study.eligibility"/></b></td>
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="ELIGIBILITY"/></td>
                          <td><b><fmt:message key="study.stratification"/></b></td>
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="STRATIFICATION"/></td>
                      </tr>
                      <tr>
                          <td><b><fmt:message key="study.randomization"/></b></td>
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="RANDOMIZATION"/></td>
                          <td><b><fmt:message key="study.diseases"/></b></td>
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="DISEASE"/></td>
                      </tr>
                      <tr>
                          <td><b><fmt:message key="study.consent"/></b></td>
                          <td width="25%"><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="CONSENT"/></td>
                          <td <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>><b><fmt:message key="study.companionStudy"/></b></td>
                          <td <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>><form:checkbox path="study.currentStudyAmendment.amendmentReasons" value="COMPANION"/></td>
                      </tr>
                  </table>
		</chrome:division>
	</chrome:box>
	<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" />
</form:form>
</body>
</html>
