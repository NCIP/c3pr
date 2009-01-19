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
<tags:formPanelBox tab="${tab}" flow="${flow}" continueLabel="${empty command.shouldReserve? 'Save':command.shouldReserve?'Reserve':command.shouldRegister?'Register':command.shouldRandomize?'Register':command.shouldTransfer?'Transfer':'Enroll'}">
	<input type="hidden" name="_finish" value="true"/>
	<tags:instructions code="reg_submit" />
	<chrome:division id="Subject Information" title="Subject">
	<div class="row">
			<div class="label">First Name</div>
			<div class="value">${command.studySubject.participant.firstName}</div>
	</div>
	<div class="row">
		<div class="label">Last Name</div>
		<div class="value">${command.studySubject.participant.lastName}</div>
	</div>
	<div class="row">
		<div class="label">Gender</div>
		<div class="value">${command.studySubject.participant.administrativeGenderCode}</div>
	</div>
	<div class="row">
		<div class="label">MRN</div>
		<div class="value">${command.studySubject.participant.medicalRecordNumber.value }</div>
	</div>
	<div class="row">
		<div class="label">Assigning Authority</div>
		<div class="value">${command.studySubject.participant.medicalRecordNumber.healthcareSite.name }</div>
	</div>
	<div class="row">
		<div class="label">Birth Date</div>
		<div class="value">${command.studySubject.participant.birthDateStr}</div>
	</div>
	<div class="row">
		<div class="label">Ethnicity</div>
		<div class="value">${command.studySubject.participant.ethnicGroupCode}</div>
	</div>
	<div class="row">
		<div class="label">Race(s)</div>
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
		<div class="row">
			<div class="label">Status</div>
			<div class="value">${command.studySubject.studySite.study.coordinatingCenterStudyStatus.displayName}</div>
	</div>
	<div class="row">
		<div class="label">Short Title</div>
		<div class="value">${command.studySubject.studySite.study.shortTitleText}</div>
	</div>
	<div class="row">
		<div class="label">Long Title</div>
		<div class="value">${command.studySubject.studySite.study.longTitleText}</div>
	</div>
	<div class="row">
		<div class="label">Randomized</div>
		<div class="value">${command.studySubject.studySite.study.randomizedIndicator?'Yes':'No'}</div>
	</div>
	<div class="row">
		<div class="label">Multi Institutional</div>
		<div class="value">${command.studySubject.studySite.study.multiInstitutionIndicator?'Yes':'No'}</div>
	</div>
	<div class="row">
		<div class="label">Phase</div>
		<div class="value">${command.studySubject.studySite.study.phaseCode}</div>
	</div>
	<div class="row">
		<div class="label">Coordinating Center Identifier</div>
		<div class="value">${command.studySubject.studySite.study.identifiers[0].value}</div>
		</div>
	
	</chrome:division>
	<chrome:division id="Study Site Information" title="Study Site">
		<div class="row">
			<div class="label">Name</div>
			<div class="value">${command.studySubject.studySite.healthcareSite.name}</div>
	</div>
	<div class="row">
		<div class="label">Address</div>
		<div class="value">${command.studySubject.studySite.healthcareSite.address.streetAddress},
	${command.studySubject.studySite.healthcareSite.address.city},
	${command.studySubject.studySite.healthcareSite.address.stateCode},
	${command.studySubject.studySite.healthcareSite.address.postalCode}</div>
	</div>
	<div class="row">
		<div class="label">Status</div>
		<div class="value">${command.studySubject.studySite.siteStudyStatus.code}</div>
	</div>
	<div class="row">
		<div class="label">NCI Institution Code</div>
		<div class="value">${command.studySubject.studySite.healthcareSite.nciInstituteCode}</div>
	</div>
	<div class="row">
		<div class="label">IRB Approval Date</div>
		<div class="value">${command.studySubject.studySite.irbApprovalDateStr}</div>
		</div>
	
	</chrome:division>
	<chrome:division id="Current Epoch Information" title="Current Epoch">
		<div class="row">
			<div class="label">Current Epoch</div>
			<div class="value">${command.studySubject.scheduledEpoch.epoch.name}</div>
	</div>
	<div class="row">
		<div class="label">Enrolling</div>
		<div class="value">${command.studySubject.scheduledEpoch.epoch.enrollmentIndicator?'Yes':'No'}</div>
	</div>
	<div class="row">
		<div class="label">Epoch Status</div>
		<div class="value">${command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code}</div>
	</div>
	
	</chrome:division>
	<chrome:division id="enrollment" title="Enrollment Details">
	<div class="row">
		<div class="label">Registration Start Date</div>
		<div class="value"><tags:requiredFieldEmptyIndicator value='${command.studySubject.startDateStr }' workflow='registration'/></div>
	</div>
	<div class="row">
		<div class="label">Informed Consent Signed Date</div>
		<div class="value"><tags:requiredFieldEmptyIndicator value='${command.studySubject.informedConsentSignedDateStr}' workflow='registration'/></div>
	</div>
	<div class="row">
		<div class="label">Informed Consent Version</div>
		<div class="value"><tags:requiredFieldEmptyIndicator value='${command.studySubject.informedConsentVersion}' workflow='registration'/></div>
	</div>
	<div class="row">
		<div class="label">Treating Physician</div>
		<div class="value">${command.studySubject.treatingPhysicianFullName}&nbsp;</div>
	</div>
	<div class="row">
		<div class="label">Primary Disease</div>
		<div class="value">${command.studySubject.diseaseHistory.primaryDiseaseStr }</div>
	</div>
	<div class="row">
		<div class="label">Primary Disease Site</div>
		<div class="value">${command.studySubject.diseaseHistory.primaryDiseaseSiteStr }</div>
	</div>
	<div class="row">
		<div class="label">Payment Method</div>
		<div class="value">${command.studySubject.paymentMethod}</div>
	</div>
	
	
	</chrome:division>
		<chrome:division id="Eligibility" title="Eligibility">
			<div class="row">
				<div class="label">Eligible</div>
				<div class="value"><tags:requiredFieldInvalidIndicator value='${command.studySubject.scheduledEpoch.eligibilityIndicator?"Yes":"No" }' workflow='registration' compareWith='Yes'/></div>
			</div>
		<c:choose>
		<c:when test="${fn:length(command.studySubject.scheduledEpoch.inclusionEligibilityAnswers) == 0 && fn:length(command.studySubject.scheduledEpoch.exclusionEligibilityAnswers) == 0}">
		There is no eligibility check list available for this epoch
		</c:when>
		<c:otherwise>
		<br>
			<strong>Inclusion Criteria</strong>
			
			<div class="review">
			<table width="70%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
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
		<table border="0" cellspacing="0" cellpadding="0"  width="70%" class="tablecontent">
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
		<table width="70%" border="0" cellspacing="0" cellpadding="0" id="table1">
			<tr>
				<td class="label" align=left>The Selected Epoch does not have any stratification factors</td>
			</tr>
		</table>
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
</tags:formPanelBox>
</body>
</html>
