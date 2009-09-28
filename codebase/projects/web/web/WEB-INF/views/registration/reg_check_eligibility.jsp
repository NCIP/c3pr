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
<style>
	#main {
		top:35px;
	}
</style>
</head>
<body>
<c:choose>
	<c:when test="${fn:length(command.studySubject.scheduledEpoch.subjectEligibilityAnswers) == 0}">
			<tags:formPanelBox tab="${tab}" flow="${flow}" boxClass="grayed-out">
				<div><fmt:message key="REGISTRATION.NO_ELIGIBILITY"/></div><br>
			</tags:formPanelBox>
	</c:when>
	<c:otherwise>
		<form:form method="post">
		<tags:tabFields tab="${tab}"/>
		<table id="" width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
			<tr>
				<td>
					<c:set var="index" value="0"/>
					<c:choose>
						<c:when test="${fn:length(command.studySubject.scheduledEpoch.epoch.inclusionEligibilityCriteria) == 0}">
							<tags:panelBox boxId="Inclusion" title="Inclusion Criteria (<i>Expected answer 'yes'</i>)" boxClass="grayed-out">
								<div><fmt:message key="REGISTRATION.NO_INCLUSION_ELIGIBILITY"/></div><br>
							</tags:panelBox>
						</c:when>
						<c:otherwise>
							<tags:minimizablePanelBox boxId="Inclusion" title="Inclusion Criteria (<i>Expected answer 'yes'</i>)">
								<tags:instructions code="reg_check_eligibility" />
								<table width="100%" border="0" class="tablecontent">
									<tr>
										<th align="left"><b><fmt:message key="study.criterion"/></b></th>
										<th align="left"><b><fmt:message key="study.answers"/></b></th>
									</tr>
									<c:forEach var="criteria" varStatus="status" items="${command.studySubject.scheduledEpoch.epoch.inclusionEligibilityCriteria}">
										<tr>
											<td width="85%">
												${criteria.questionText}
											</td>
											<c:set var="inclusionAnswerSelected" value="false"></c:set>
											<c:forEach var="eligAnswer" varStatus="eligAnswerStatus" items="${command.studySubject.scheduledEpoch.subjectEligibilityAnswers}">
												<c:if test="${eligAnswer.eligibilityCriteria.id == criteria.id}">
													<td width="15%" valign="center">
														<form:select id="scheduledEpoch.subjectEligibilityAnswers[${eligAnswerStatus.index}].answerText" path="studySubject.scheduledEpoch.subjectEligibilityAnswers[${eligAnswerStatus.index}].answerText">
															<option value="">Please select</option>
															<form:option value="Yes" />
															<form:option value="No" />
															<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA" label="Not Applicable"/></c:if>
														</form:select>
													</td>
													<c:set var="inclusionAnswerSelected" value="true"></c:set>
												</c:if>
											</c:forEach>
											
											<c:if test="${inclusionAnswerSelected == 'false'}">
												<td width="15%" valign="center">
													<form:select id="scheduledEpoch.subjectEligibilityAnswers[${index}].answerText" path="studySubject.scheduledEpoch.subjectEligibilityAnswers[${index}].answerText">
														<option value="">Please select</option>
														<form:option value="Yes" />
														<form:option value="No" />
														<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA" label="Not Applicable"/></c:if>
													</form:select>
												</td>
											</c:if>
											
										</tr>
										<c:set var="index" value="${index+1}"/>
									</c:forEach>
								</table>
							</tags:minimizablePanelBox>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
					<c:choose>
						<c:when test="${fn:length(command.studySubject.scheduledEpoch.epoch.exclusionEligibilityCriteria) == 0}">
							<tags:panelBox boxId="Exclusion" title="Exclusion Criteria (<i>Expected answer 'no'</i>)" boxClass="grayed-out">
								<div><fmt:message key="REGISTRATION.NO_EXCLUSION_ELIGIBILITY"/></div><br>
							</tags:panelBox>
						</c:when>
						<c:otherwise>
							<tags:minimizablePanelBox boxId="Exclusion" title="Exclusion Criteria (<i>Expected answer 'no'</i>)">
								<table width="100%" border="0" class="tablecontent">
									<tr>
										<th align="left"><b><fmt:message key="study.criterion"/></b></th>
										<th align="left"><b><fmt:message key="study.answers"/></b></th>
									</tr>
									<c:forEach var="criteria" varStatus="status" items="${command.studySubject.scheduledEpoch.epoch.exclusionEligibilityCriteria}">
										<tr>
											<td width="85%">
												${criteria.questionText}
											</td>
											<c:set var="exclusionAnswerSelected" value="false"></c:set>
											<c:forEach var="eligAnswer" varStatus="eligAnswerStatus" items="${command.studySubject.scheduledEpoch.subjectEligibilityAnswers}">
												<c:if test="${eligAnswer.eligibilityCriteria.id == criteria.id}">
													<td width="15%" valign="center">
														<form:select id="scheduledEpoch.subjectEligibilityAnswers[${eligAnswerStatus.index}].answerText" path="studySubject.scheduledEpoch.subjectEligibilityAnswers[${eligAnswerStatus.index}].answerText">
															<option value="">Please select</option>
															<form:option value="Yes" />
															<form:option value="No" />
															<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA" label="Not Applicable"/></c:if>
														</form:select>
													</td>
													<c:set var="exclusionAnswerSelected" value="true"></c:set>
												</c:if>
											</c:forEach>
											<c:if test="${exclusionAnswerSelected == 'false'}">
												<td width="15%" valign="center">
													<form:select id="scheduledEpoch.subjectEligibilityAnswers[${index}].answerText" path="studySubject.scheduledEpoch.subjectEligibilityAnswers[${index}].answerText">
														<option value="">Please select</option>
														<form:option value="Yes" />
														<form:option value="No" />
														<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA" label="Not Applicable"/></c:if>
													</form:select>
												</td>
											</c:if>
										</tr>
										<c:set var="index" value="${index+1}"/>										
									</c:forEach>
								</table>
							</tags:minimizablePanelBox>
						</c:otherwise>
					</c:choose>
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
