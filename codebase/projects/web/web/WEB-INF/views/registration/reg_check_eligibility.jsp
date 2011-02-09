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
<tags:instructions code="reg_check_eligibility" />
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
													<c:set var="waiverAnswerIndex" value="${eligAnswerStatus.index}" />
													<td width="15%" valign="middle">
													<c:choose>
														<c:when test="${eligAnswer.allowWaiver}">
															Waived <img src='<tags:imageUrl name="eligibility_waived.png"/>'/>
														</c:when>
														<c:otherwise>
																<form:select id="scheduledEpoch.subjectEligibilityAnswers[${eligAnswerStatus.index}].answerText" path="studySubject.scheduledEpoch.subjectEligibilityAnswers[${eligAnswerStatus.index}].answerText">
																	<option value="">Please select</option>
																	<form:option value="Yes" />
																	<form:option value="No" />
																	<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA" label="Not Applicable"/></c:if>
																</form:select>
														</c:otherwise>
													</c:choose>
													</td>
													<c:set var="inclusionAnswerSelected" value="true"></c:set>
													<c:set var="eligibilityAnswer" value="${eligAnswer}"/>
												</c:if>
											</c:forEach>
											
											<c:if test="${inclusionAnswerSelected == 'false'}">
												<td width="15%" valign="middle">
													<form:select id="scheduledEpoch.subjectEligibilityAnswers[${index}].answerText" path="studySubject.scheduledEpoch.subjectEligibilityAnswers[${index}].answerText">
														<option value="">Please select</option>
														<form:option value="Yes" />
														<form:option value="No" />
														<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA" label="Not Applicable"/></c:if>
													</form:select>
												</td>
											</c:if>
											
										</tr>
										<c:if test="${inclusionAnswerSelected && eligibilityAnswer.allowWaiver}">
											<tr align="center">
												<td colspan="3">
												<table width="90%" border="0">
													<tr>
														<td style="border:0px;">
															<table>
																<tr>
																	<td style="border:0px;"><tags:requiredIndicator /><b>Waiver id</b> 
																	<form:input path="studySubject.scheduledEpoch.subjectEligibilityAnswers[${waiverAnswerIndex}].waiverId" size="10" cssClass="required validate-notEmpty" />
																	</td>
																</tr>
																<tr>
																	<td style="border:0px;"><b>Waiver allowed by</b> ${eligibilityAnswer.waivedBy.fullName}</td>
																</tr>
															</table>
														</td>
														<td style="border:0px;"><tags:requiredIndicator /><b>Waiver reason</b>
														<form:textarea path="studySubject.scheduledEpoch.subjectEligibilityAnswers[${waiverAnswerIndex}].waiverReason" cols="55" rows="2" cssClass="required validate-notEmpty&&maxlength190"/></td>
													</tr>
												</table>
												</td>
											</tr>
										</c:if>
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
													<c:set var="waiverAnswerIndex" value="${eligAnswerStatus.index}" />
													<td width="15%" valign="middle">
													<c:choose>
														<c:when test="${eligAnswer.allowWaiver}">
															Waived <img src='<tags:imageUrl name="eligibility_waived.png"/>'/>
														</c:when>
														<c:otherwise>
																<form:select id="scheduledEpoch.subjectEligibilityAnswers[${eligAnswerStatus.index}].answerText" path="studySubject.scheduledEpoch.subjectEligibilityAnswers[${eligAnswerStatus.index}].answerText">
																	<option value="">Please select</option>
																	<form:option value="Yes" />
																	<form:option value="No" />
																	<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA" label="Not Applicable"/></c:if>
																</form:select>
														</c:otherwise>
													</c:choose>
													</td>
													<c:set var="eligibilityAnswer" value="${eligAnswer}"/>
													<c:set var="exclusionAnswerSelected" value="true"></c:set>
												</c:if>
											</c:forEach>
											<c:if test="${exclusionAnswerSelected == 'false'}">
												<td width="15%" valign="middle">
													<form:select id="scheduledEpoch.subjectEligibilityAnswers[${index}].answerText" path="studySubject.scheduledEpoch.subjectEligibilityAnswers[${index}].answerText">
														<option value="">Please select</option>
														<form:option value="Yes" />
														<form:option value="No" />
														<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA" label="Not Applicable"/></c:if>
													</form:select>
												</td>
											</c:if>
										</tr>
										<c:if test="${exclusionAnswerSelected  && eligibilityAnswer.allowWaiver}">
										<tr>
											<td colspan="3">
											<table width="90%" border="0">
												<tr>
													<td style="border:0px;">
														<table>
															<tr>
																<td style="border:0px;"><tags:requiredIndicator /><b>Waiver id</b> 
																<form:input path="studySubject.scheduledEpoch.subjectEligibilityAnswers[${waiverAnswerIndex}].waiverId" size="10" cssClass="required validate-notEmpty" />
																</td>
															</tr>
															<tr>
																<td style="border:0px;"><b>Waiver allowed by</b> ${eligibilityAnswer.waivedBy.fullName}</td>
															</tr>
														</table>
													</td>
													<td style="border:0px;"><tags:requiredIndicator /><b>Waiver reason</b>
													<form:textarea path="studySubject.scheduledEpoch.subjectEligibilityAnswers[${waiverAnswerIndex}].waiverReason" cols="55" rows="2" cssClass="required validate-notEmpty&&maxlength190"/></td>
												</tr>
											</table>
											</td>
										</tr>
										</c:if>
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
