<%@ include file="taglibs.jsp"%>
<html>
<head>
	 <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}

</script>
</head>
<body>
<tags:instructions code="reg_stratify" />
<registrationTags:goToTab currentTab="3" registration="${command.studySubject}" />
<registrationTags:backToTab currentTab="3" registration="${command.studySubject}" />
<c:set var="custonButton" value ="${param.customButton}" ></c:set>
<tags:formPanelBox tab="${tab}" flow="${flow}">

	<c:choose>
	<c:when test="${command.studySubject.scheduledEpoch.epoch.stratificationIndicator == 'false' || fn:length(command.studySubject.scheduledEpoch.subjectStratificationAnswers)==0}">
		<br/><br><div align="center"><fmt:message key="REGISTRATION.NO_STRATIFICATION"/></div><br><br>
	</c:when>
	<c:otherwise>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<th align="left"><b>Criteria</b></th>
			<th align="left"><b>Answers</b></th>
		</tr>
		<c:forEach var="criteria" varStatus="status" items="${command.studySubject.scheduledEpoch.subjectStratificationAnswers}">
			<tr>
				<td >
					${criteria.stratificationCriterion.questionText}
				</td>
				<td width="15%">
					<form:select path="studySubject.scheduledEpoch.subjectStratificationAnswers[${status.index}].stratificationCriterionAnswer" >
					<option value="">Please select</option>
					<c:forEach items="${criteria.stratificationCriterion.permissibleAnswers}" var="option">
						<option value="${option.id }" <c:if test="${option.id== command.studySubject.scheduledEpoch.subjectStratificationAnswers[status.index].stratificationCriterionAnswer.id}">selected</c:if>>${option.permissibleAnswer }</option>
					</c:forEach>
					</form:select>
				</td>
			</tr>
			
		</c:forEach>
		</table>
	</c:otherwise>
	</c:choose>
</tags:formPanelBox>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
