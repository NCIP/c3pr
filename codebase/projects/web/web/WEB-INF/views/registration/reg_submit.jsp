<%@ include file="taglibs.jsp"%>

<html>
<head>
<title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
<style type="text/css">
        .labelR { text-align: right; padding: 4px;  font-weight: bold;}
</style>
<style type="text/css">
        .label { text-align: left; padding: 4px; font-weight: bold;}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
ValidationManager.submitPostProcess= function(formElement, continueSubmission){
	if(formElement.id=='command' && ${registerable && !empty command.studySubject.studySite.targetAccrualNumber && command.studySubject.studySite.targetAccrualNumber <= command.studySubject.studySite.currentAccrualCount})
		return confirm("This registration will exceed the accrual ceiling at ${command.studySubject.studySite.healthcareSite.name}. Do you want to continue?");
	return continueSubmission;
}
</script>
</head>
<body>
<tags:formPanelBox tab="${tab}" flow="${flow}" continueLabel="${registerable?'Register':''}">
	<input type="hidden" name="_finish" value="true"/>
	<chrome:division id="Subject Information" title="Subject">
	<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<td width="35%"  align="left"><b>First Name</b></td>
			<td>${command.studySubject.participant.firstName}</td>
	</tr>
	<tr>
		<td align="left"><b>Last Name</b></td>
		<td>${command.studySubject.participant.lastName}</td>
	</tr>
	<tr>
		<td align="left"><b>Gender</b></td>
		<td>${command.studySubject.participant.administrativeGenderCode}</td>
	</tr>
	<tr>
		<td align="left"><b>MRN</b></td>
		<td>${command.studySubject.participant.medicalRecordNumber.value }</td>
	</tr>
	<tr>
		<td align="left"><b>Assigning Authority</b></td>
		<td>${command.studySubject.participant.medicalRecordNumber.healthcareSite.name }</td>
	</tr>
		<td align="left"><b>Birth Date</b></td>
		<td>${command.studySubject.participant.birthDateStr}</td>
	</tr>
	<tr>
		<td align="left"><b>Ethnicity</b></td>
		<td>${command.studySubject.participant.ethnicGroupCode}</td>
	</tr>
	<tr>
		<td align="left"><b>Race(s)</b></td>
		<td>
			<c:forEach items="${command.studySubject.participant.raceCodes}" var="raceCode">
	            <div class="row">
	                <div class="left">${raceCode.displayName}</div>
	            </div>
	        </c:forEach>
		</td>
		</tr>
	</table>
	</chrome:division>
	<chrome:division id="Study Information" title="Study">
	<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<td align="left" width="35%"><b>Status</b></td>
			<td>${command.studySubject.studySite.study.coordinatingCenterStudyStatus.displayName}</td>
	</tr>
	<tr>
		<td align="left"><b>Short Title</b></td>
		<td>${command.studySubject.studySite.study.shortTitleText}</td>
	</tr>
	<tr>
		<td align="left"><b>Long Title</b></td>
		<td>${command.studySubject.studySite.study.longTitleText}</td>
	</tr>
	<tr>
		<td align="left"><b>Randomized</b></td>
		<td>${command.studySubject.studySite.study.randomizedIndicator?'Yes':'No'}</td>
	</tr>
	<tr>
		<td align="left"><b>Multi Institutional</b></td>
		<td>${command.studySubject.studySite.study.multiInstitutionIndicator?'Yes':'No'}</td>
	</tr>
	<tr>
		<td align="left"><b>Phase</b></td>
		<td>${command.studySubject.studySite.study.phaseCode}</td>
	</tr>
	<tr>
		<td align="left"><b>Coordinating Center Identifier</b></td>
		<td>${command.studySubject.studySite.study.identifiers[0].value}</td>
		</tr>
	</table>
	</chrome:division>
	<chrome:division id="Study Site Information" title="Study Site">
	<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<td width="35%" align="left"><b>Name</b></td>
			<td>${command.studySubject.studySite.healthcareSite.name}</td>
	</tr>
	<tr>
		<td align="left"><b>Address</b></td>
		<td>${command.studySubject.studySite.healthcareSite.address.streetAddress},
	${command.studySubject.studySite.healthcareSite.address.city},
	${command.studySubject.studySite.healthcareSite.address.stateCode},
	${command.studySubject.studySite.healthcareSite.address.postalCode}</td>
	</tr>
	<tr>
		<td align="left"><b>Status</b></td>
		<td>${command.studySubject.studySite.siteStudyStatus.code}</td>
	</tr>
	<tr>
		<td align="left"><b>NCI Institution Code</b></td>
		<td>${command.studySubject.studySite.healthcareSite.nciInstituteCode}</td>
	</tr>
	<tr>
		<td align="left"><b>IRB Approval Date</b></td>
		<td>${command.studySubject.studySite.irbApprovalDateStr}</td>
		</tr>
	</table>
	</chrome:division>
	<chrome:division id="Current Epoch Information" title="Current Epoch">
	<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<td align="left" width="35%"><b>Current Epoch</b></td>
			<td>${command.studySubject.scheduledEpoch.epoch.name}</td>
	</tr>
	<tr>
		<td align="left"><b>Enrolling</b></td>
		<td>${command.studySubject.scheduledEpoch.epoch.enrollmentIndicator?'Yes':'No'}</td>
	</tr>
	<tr>
		<td align="left"><b>Epoch Status</b></td>
		<td>${command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code}</td>
	</tr>
	</table>
	</chrome:division>
	<chrome:division id="enrollment" title="Enrollment Details">
	<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
	<tr>
		<td align="left"><b>Registration Start Date</b></td>
		<td><tags:requiredFieldEmptyIndicator value='${command.studySubject.startDateStr }' workflow='registration'/></td>
	</tr>
	<tr>
		<td align="left" width="35%"><b>Informed Consent Signed Date</b></td>
		<td><tags:requiredFieldEmptyIndicator value='${command.studySubject.informedConsentSignedDateStr}' workflow='registration'/></td>
	</tr>
	<tr>
		<td align="left"><b>Informed Consent Version</b></td>
		<td><tags:requiredFieldEmptyIndicator value='${command.studySubject.informedConsentVersion}' workflow='registration'/></td>
	</tr>
	<tr>
		<td align="left"><b>Treating Physician</b></td>
		<td>${command.studySubject.treatingPhysicianFullName}&nbsp;</td>
	</tr>
	<tr>
		<td align="left"><b>Primary Disease</b></td>
		<td>${command.studySubject.diseaseHistory.primaryDiseaseStr }</td>
	</tr>
	<tr>
		<td align="left"><b>Primary Disease Site</b></td>
		<td>${command.studySubject.diseaseHistory.primaryDiseaseSiteStr }</td>
	</tr>
	<tr>
		<td align="left"><b>Payment Method</b></td>
		<td>${command.studySubject.paymentMethod}</td>
	</tr>
	
	</table>
	</chrome:division>
		<chrome:division id="Eligibility" title="Eligibility">
		<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
			<tr>
				<td width="35%" align="left"><b>Eligible</b>
				</td>
				<td><tags:requiredFieldInvalidIndicator value='${command.studySubject.scheduledEpoch.eligibilityIndicator?"True":"False" }' workflow='registration' compareWith='True'/></td>
			</tr>
		</table>
		<c:choose>
		<c:when test="${fn:length(command.studySubject.scheduledEpoch.inclusionEligibilityAnswers) == 0 && fn:length(command.studySubject.scheduledEpoch.exclusionEligibilityAnswers) == 0}">
		There is no eligibility check list available for this epoch
		</c:when>
		<c:otherwise>
		<br>
			<strong>Inclusion Criteria</strong>
			
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
				<tr>
					<th scope="col" align="left">Question</th>
					<th scope="col" align="left">Answer</th>
				</tr>
				<c:forEach items="${command.studySubject.scheduledEpoch.inclusionEligibilityAnswers}" var="criteria">
			<tr class="results">
				<td class="alt" align="left">${ criteria.eligibilityCriteria.questionText}</td>
				<td class="alt" align="left"><tags:requiredFieldEmptyIndicator value='${criteria.answerText}' workflow='registration'/></td>
			</tr>
		</c:forEach>
		</table>
		</div>
		<br>
		<strong>Exclusion Criteria</strong>
		
		<div class="review">
		<table border="0" cellspacing="0" cellpadding="0"  width="50%" class="tablecontent">
			<tr>
				<th scope="col" align="left">Question</th>
				<th scope="col" align="left">Answer</th>
			</tr>
			<c:forEach items="${command.studySubject.scheduledEpoch.exclusionEligibilityAnswers}" var="criteria">
			<tr class="results">
				<td class="alt" align="left">${ criteria.eligibilityCriteria.questionText}</td>
				<td class="alt" align="left"><tags:requiredFieldEmptyIndicator value='${criteria.answerText}' workflow='registration'/></td>
			</tr>
		</c:forEach>
		</table>
		</div>
		</c:otherwise>
		</c:choose>
		</chrome:division>
		<chrome:division id="stratification" title="Stratification Information">
		<c:choose>
		<c:when test="${fn:length(command.studySubject.scheduledEpoch.subjectStratificationAnswers) == 0}">
		<table width="50%" border="0" cellspacing="0" cellpadding="0" id="table1">
			<tr>
				<td class="label" align=left>The Selected Epoch does not have any stratification factors</td>
			</tr>
		</table>
		</c:when>
		<c:otherwise>
		<table border="0" cellspacing="0" cellpadding="0" class="tablecontent"  width="50%">
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
</tags:formPanelBox>
</body>
</html>
