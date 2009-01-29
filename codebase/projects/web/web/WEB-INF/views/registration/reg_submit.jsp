<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
<style type="text/css">
        .labelR { text-align: right; padding: 4px;  font-weight: bold;}
</style>
<style type="text/css">
        .label { text-align: left; padding: 4px; font-weight: bold;}
		.instructions .summaryvalue {width:85%;}
		div.row div.label {
			width:15em;
		}
		div.row div.value {
			margin-left:16em;
		}
		#main {
			top:35px;
		}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
ValidationManager.submitPostProcess= function(formElement, continueSubmission){
	if(formElement.id=='command' && ${registerable && !empty command.studySubject.studySite.targetAccrualNumber && command.studySubject.studySite.targetAccrualNumber <= command.studySubject.studySite.currentAccrualCount}){
		return confirm("This registration will exceed the accrual ceiling at ${command.studySubject.studySite.healthcareSite.name}. Do you want to continue?");
	}
	return continueSubmission;
}
</script>
</head>
<body>
<tags:formPanelBox tab="${tab}" flow="${flow}" title="${tabTitle}" continueLabel="${empty actionLabel? 'Save' : actionLabel}">
	<input type="hidden" name="_finish" value="true"/>
	<tags:instructions code="reg_submit" />
	<chrome:division id="Subject Information" title="Subject">
			<div class="leftpanel">
				<div class="row">
						<div class="label">First name:</div>
						<div class="value">${command.studySubject.participant.firstName}</div>
				</div>
				<div class="row">
					<div class="label">Last name:</div>
					<div class="value">${command.studySubject.participant.lastName}</div>
				</div>
				<div class="row">
					<div class="label">Gender:</div>
					<div class="value">${command.studySubject.participant.administrativeGenderCode}</div>
				</div>
				<div class="row">
					<div class="label">MRN:</div>
					<div class="value">${command.studySubject.participant.medicalRecordNumber.value }</div>
				</div>
			</div>
			<div class="rightpanel">
				<div class="row">
					<div class="label">Assigning authority:</div>
					<div class="value">${command.studySubject.participant.medicalRecordNumber.healthcareSite.name }</div>
				</div>
				<div class="row">
					<div class="label">Birth date:</div>
					<div class="value">${command.studySubject.participant.birthDateStr}</div>
				</div>
				<div class="row">
					<div class="label">Ethnicity:</div>
					<div class="value">${command.studySubject.participant.ethnicGroupCode}</div>
				</div>
				<div class="row">
					<div class="label">Race(s):</div>
					<div class="value">
						<c:forEach items="${command.studySubject.participant.raceCodes}" var="raceCode">
				            <div class="row">
				                <div class="left">${raceCode.displayName}</div>
				            </div>
				        </c:forEach>
					</div>
				</div>
			</div>
	</chrome:division>
	<chrome:division id="Study Information" title="Study">
			<div class="leftpanel">
				<div class="row">
					<div class="label">Short title:</div>
					<div class="value">${command.studySubject.studySite.study.shortTitleText}</div>
				</div>
				<div class="row">
					<div class="label">Long title:</div>
					<div class="value">${command.studySubject.studySite.study.longTitleText}</div>
				</div>
				<div class="row">
					<div class="label">Randomized:</div>
					<div class="value">${command.studySubject.studySite.study.randomizedIndicator?'Yes':'No'}</div>
				</div>
			</div>
			<div class="rightpanel">
				<div class="row">
					<div class="label">Multi institutional:</div>
					<div class="value">${command.studySubject.studySite.study.multiInstitutionIndicator?'Yes':'No'}</div>
				</div>
				<div class="row">
					<div class="label">Coordinating center identifier:</div>
					<div class="value">${command.studySubject.studySite.study.identifiers[0].value}</div>
				</div>
			</div>
	</chrome:division>
	<chrome:division id="Study Site Information" title="Study Site">
			<div class="leftpanel">
				<div class="row">
					<div class="label">Name:</div>
					<div class="value">${command.studySubject.studySite.healthcareSite.name}</div>
				</div>
				<div class="row">
					<div class="label">Address:</div>
					<div class="value">
							<c:if test="${!empty command.studySubject.studySite.healthcareSite.address.streetAddress}">${command.studySubject.studySite.healthcareSite.address.streetAddress},</c:if>
							<c:if test="${!empty command.studySubject.studySite.healthcareSite.address.city}">${command.studySubject.studySite.healthcareSite.address.city},</c:if>
							<c:if test="${! empty command.studySubject.studySite.healthcareSite.address.stateCode}">${command.studySubject.studySite.healthcareSite.address.stateCode},</c:if>
							${command.studySubject.studySite.healthcareSite.address.postalCode}
					</div>
				</div>
			</div>
			<div class="rightpanel">
				<div class="row">
					<div class="label">NCI institution code:</div>
					<div class="value">${command.studySubject.studySite.healthcareSite.nciInstituteCode}</div>
				</div>
				<div class="row">
					<div class="label">IRB approval date:</div>
					<div class="value">${command.studySubject.studySite.irbApprovalDateStr}
					</div>
				</div>
				<div class="row">
					<div class="label">Activation date:</div>
					<div class="value">${command.studySubject.studySite.startDateStr}
					</div>
				</div>
			</div>
	</chrome:division>
	<chrome:division id="Current Epoch Information" title="Current Epoch">
		<div class="leftpanel">
			<div class="row">
				<div class="label"><fmt:message key="registration.currentEpoch"/>:</div>
				<div class="value">${command.studySubject.scheduledEpoch.epoch.name}</div>
			</div>
		</div>
		<div class="rightpanel">
			<div class="row">
				<div class="label">Enrolling:</div>
				<div class="value">${command.studySubject.scheduledEpoch.epoch.enrollmentIndicator?'Yes':'No'}</div>
			</div>
		</div>
	</chrome:division>
	<chrome:division id="enrollment" title="Enrollment Details">
			<div class="leftpanel">
				<div class="row">
					<div class="label">Registration start date:</div>
					<div class="value"><tags:requiredFieldEmptyIndicator value='${command.studySubject.startDateStr }' workflow='registration'/></div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="registration.consentSignedDate"/>:</div>
					<div class="value"><tags:requiredFieldEmptyIndicator value='${command.studySubject.informedConsentSignedDateStr}' workflow='registration'/></div>
				</div>
				<div class="row">
					<div class="label"><fmt:message key="registration.consentVesion"/>:</div>
					<div class="value"><tags:requiredFieldEmptyIndicator value='${command.studySubject.informedConsentVersion}' workflow='registration'/></div>
				</div>
			</div>
			<div class="rightpanel">
				<div class="row">
					<div class="label">Treating physician:</div>
					<c:choose>
						<c:when test="${!empty command.studySubject.treatingPhysicianFullName}">
							<div class="value">${command.studySubject.treatingPhysicianFullName}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="row">
					<div class="label">Primary disease:</div>
					<c:choose>
						<c:when test="${!empty command.studySubject.diseaseHistory.primaryDiseaseStr}">
							<div class="value">${command.studySubject.diseaseHistory.primaryDiseaseStr}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="row">
					<div class="label">Primary disease site:</div>
					<c:choose>
						<c:when test="${!empty command.studySubject.diseaseHistory.primaryDiseaseSiteStr }">
							<div class="value">${command.studySubject.diseaseHistory.primaryDiseaseSiteStr }</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="row">
					<div class="label">Payment method:</div>
					<c:choose>
						<c:when test="${!empty command.studySubject.paymentMethod}">
							<div class="value">${command.studySubject.paymentMethod}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
	</chrome:division>
	<chrome:division id="Eligibility" title="Eligibility">
		<div class="row">
			<div class="label"><fmt:message key="registration.eligibilty"/>:</div>
			<c:choose>
				<c:when test="${studySubject.studySubject.scheduledEpoch.eligibilityIndicator}">
					<div class="value"><fmt:message key="c3pr.common.yes"/></div>
				</c:when>
				<c:otherwise>
					<div class="value"><fmt:message key="c3pr.common.no"/></div>
					<br>
					<span class="value"><fmt:message key="registartion.eligibiltyRequired"/></span>
				</c:otherwise>
			</c:choose>
		</div>
	</chrome:division>
	<chrome:division id="stratification" title="Stratification">
		<c:choose>
		<c:when test="${fn:length(command.studySubject.scheduledEpoch.subjectStratificationAnswers) == 0}">
			<div align="left">There are no stratification factors available for this epoch.</div>
		</c:when>
		<c:otherwise>
			<table border="0" cellspacing="0" cellpadding="0" class="tablecontent"  width="70%">
				<tr>
					<th width="35%" scope="col" align="left">Strata</th>
					<th scope="col" align="left"><b>Answer</th>
				</tr>
				<c:forEach items="${command.studySubject.scheduledEpoch.subjectStratificationAnswers}" var="criteria">
					<tr class="results">
						<td class="alt" align="left">${criteria.stratificationCriterion.questionText}</td>
						<td class="alt" align="left">
						<tags:requiredFieldEmptyIndicator value='${criteria.stratificationCriterionAnswer.permissibleAnswer}' workflow='registration'/></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
		</c:choose>
	</chrome:division>
	<registrationTags:randomization registration="${command.studySubject}"></registrationTags:randomization>
</tags:formPanelBox>
</body>
</html>
