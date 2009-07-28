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

</script>
</head>
<body>
	<chrome:box id="study-amendments" title="Previous Amendments">
		<table id="study.studyAmendments" class="tablecontent">
			<tr>
				<th><fmt:message key="study.amendmentVersion" /></th>
				<th><fmt:message key="study.amendmentDate" /></th>
				<th><fmt:message key="c3pr.common.comments" /></th>
			</tr>
			<c:forEach items="${command.study.previousStudyAmendments}"
				var="studyVersion" varStatus="status">
				<tr id="study.studyAmendments-${status.index}">
					<td>${studyVersion.name}</td>
					<td>${studyVersion.versionDateStr}</td>
					<td>${studyVersion.comments}</td>
				</tr>
			</c:forEach>
		</table>
		</chrome:box>
		<form:form name="myform" cssClass="standard">
			<tags:tabFields tab="${tab}" />
			<input type="hidden" name="_actionx" value="_actionx" />
			<input type="hidden" id="_selected" name="_selected" value="" />
			<input type="hidden" id="_selectedSite" name="_selectedSite" value="" />
			<c:set var="title" value="${resumeAmendment?'Resume Amendment':'New Amendment'}"></c:set>
			<chrome:box title="${title}">
				<chrome:division id="study-amendments" title="Basic Details">
					<div class="row">
					<div id="errorMsg1" style="display: none"><span id='sid1'
						style='color: #EE3324'>Please enter the Version# or
					Amendment Date.</span><br />
					</div>
					</div>
					<div class="row">
					<div class="label"><fmt:message key="study.amendmentVersion" /></div>
					<div class="value"><form:input
						path="study.currentStudyAmendment.name" size="5" /></div>
					</div>
					<div class="row">
					<div class="label"><fmt:message key="study.amendmentDate" /></div>
					<div class="value"><tags:dateInput
						path="study.currentStudyAmendment.versionDate" /></div>
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
