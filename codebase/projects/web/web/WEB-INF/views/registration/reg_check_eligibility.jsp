<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><registrationTags:htmlTitle registration="${command}"></title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
</head>
<body>

<c:choose>
	<c:when test="${fn:length(command.scheduledEpoch.subjectEligibilityAnswers) == 0}">
			<tags:formPanelBox tab="${tab}" flow="${flow}"><br/><br><div align="center"><fmt:message key="REGISTRATION.NO_ELIGIBILITY"/></div><br><br>
			</tags:formPanelBox>
	</c:when>
	<c:otherwise>
		<form:form method="post">
		<tags:tabFields tab="${tab}"/>
		<table id="" width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
			<tr>
				<td>
				<tags:minimizablePanelBox boxId="Inclusion" title="Inclusion Criteria (<i>Expected answer 'yes'</i>)">
					<table width="100%" border="0" class="tablecontent">
						<tr>
							<th align="left"><b>Question</b></th>
							<th align="left"><b>Answer</b></th>
						</tr>
						<c:set var="index" value="0"/>
						<c:forEach var="criteria" varStatus="status" items="${command.scheduledEpoch.epoch.inclusionEligibilityCriteria}">
							<tr>
								<td width="85%">
									${criteria.questionText}
								</td>
								<td width="15%" valign="center">
									<form:select id="scheduledEpoch.subjectEligibilityAnswers[${index}].answerText" path="scheduledEpoch.subjectEligibilityAnswers[${index}].answerText">
										<option value="">Please select</option>
										<form:option value="Yes" />
										<form:option value="No" />
										<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA" label="Not Applicable"/></c:if>
									</form:select>
								</td>
							</tr>
							<c:set var="index" value="${index+1}"/>
						</c:forEach>
					</table>
				</tags:minimizablePanelBox>
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
				<tags:minimizablePanelBox boxId="Exclusion" title="Exclusion Criteria (<i>Expected answer 'no'</i>)">
					<table width="100%" border="0" class="tablecontent">
						<tr>
							<th align="left"><b>Question</b></th>
							<th align="left"><b>Answer</b></th>
						</tr>
						<c:forEach var="criteria" varStatus="status" items="${command.scheduledEpoch.epoch.exclusionEligibilityCriteria}">
							<tr>
								<td width="85%">
									${criteria.questionText}
								</td>
								<td width="15%" valign="center">
									<form:select id="scheduledEpoch.subjectEligibilityAnswers[${index}].answerText" path="scheduledEpoch.subjectEligibilityAnswers[${index}].answerText">
										<option value="">Please select</option>
										<form:option value="Yes" />
										<form:option value="No" />
										<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA" label="Not Applicable"/></c:if>
									</form:select>
								</td>
							</tr>
							<c:set var="index" value="${index+1}"/>										
						</c:forEach>
					</table>
				</tags:minimizablePanelBox>
				</td>
			</tr>
		</table>
		<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
		</form:form>
	</c:otherwise>
</c:choose>
<!-- MAIN BODY ENDS HERE -->


	</body>
</html>
