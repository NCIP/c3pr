<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<html>
<head>
<style type="text/css">
        .labelR { text-align: right; padding: 4px;  font-weight: bold;}
</style>
<style type="text/css">
        .label { text-align: left; padding: 4px; font-weight: bold;}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
ValidationManager.submitPostProcess= function(formElement, continueSubmission){
	if(formElement.id=='command' && ${registerable && !empty command.studySite.targetAccrualNumber && command.studySite.targetAccrualNumber<=command.studySite.currentAccrualCount})
		return confirm("This registration will exceed the accrual ceiling at ${command.studySite.healthcareSite.name}. Do you want to continue?");
	return continueSubmission;
}
</script>
</head>
<body>
<tags:formPanelBox tab="${tab}" flow="${flow}" continueLabel="${registerable?'Register':''}">
	<input type="hidden" name="_finish" value="true"/>
	<chrome:division id="Subject Information" title="Subject Information">
	<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<td width="25%" class="labelR">First Name</td>
			<td>${command.participant.firstName}</td>
	</tr>
	<tr>
		<td class="labelR">Last Name</td>
		<td>${command.participant.lastName}</td>
	</tr>
	<tr>
		<td class="labelR">Gender</td>
		<td>${command.participant.administrativeGenderCode}</td>
	</tr>
	<tr>
		<td class="labelR">Subject MRN</td>
		<td>${command.participant.medicalRecordNumber.value }</td>
	</tr>
	<tr>
		<td class="labelR">Assigning Authority</td>
		<td>${command.participant.medicalRecordNumber.healthcareSite.name }</td>
	</tr>
		<td width="25%" class="labelR">Birth Date</td>
		<td>${command.participant.birthDateStr}</td>
	</tr>
	<tr>
		<td class="labelR">Ethnicity</td>
		<td>${command.participant.ethnicGroupCode}</td>
	</tr>
	<tr>
		<td class="labelR">Race(s)</td>
		<td>${command.participant.raceCode}</td>
		</tr>
	</table>
	</chrome:division>
	<chrome:division id="Study Information" title="Study Information">
	<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<td class="labelR">Status</td>
			<td>${command.studySite.study.coordinatingCenterStudyStatus.code}</td>
	</tr>
	<tr>
		<td width="25%" class="labelR">Short Title</td>
		<td>${command.studySite.study.shortTitleText}</td>
	</tr>
	<tr>
		<td width="25%" class="labelR">Long Title</td>
		<td>${command.studySite.study.longTitleText}</td>
	</tr>
	<tr>
		<td class="labelR">Randomized Indicator</td>
		<td>${command.studySite.study.randomizedIndicator?'True':'False'}</td>
	</tr>
	<tr>
		<td class="labelR">Multi Institutional</td>
		<td>${command.studySite.study.multiInstitutionIndicator?'True':'False'}</td>
	</tr>
	<tr>
		<td class="labelR"> Phase Code</td>
		<td>${command.studySite.study.phaseCode}</td>
	</tr>
	<tr>
		<td width="25%" class="labelR">Sponsor Code</td>
		<td>${command.studySite.study.identifiers[0].value}</td>
		</tr>
	</table>
	</chrome:division>
	<chrome:division id="Study Site Information:" title="Study Site Information:">
	<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<td width="25%" class="labelR">Name</td>
			<td>${command.studySite.healthcareSite.name}</td>
	</tr>
	<tr>
		<td class="labelR">Address</td>
		<td>${command.studySite.healthcareSite.address.streetAddress},
	${command.studySite.healthcareSite.address.city},
	${command.studySite.healthcareSite.address.stateCode},
	${command.studySite.healthcareSite.address.postalCode}</td>
	</tr>
	<tr>
		<td class="labelR">Status Code</td>
		<td>${command.studySite.siteStudyStatus.code}</td>
	</tr>
	<tr>
		<td class="labelR">NCI Institution Code</td>
		<td>${command.studySite.healthcareSite.nciInstituteCode}</td>
	</tr>
	<tr>
		<td class="labelR">IRB Approval Date</td>
		<td>${command.studySite.irbApprovalDateStr}</td>
		</tr>
	</table>
	</chrome:division>
	<chrome:division id="Current Epoch Information" title="Current Epoch Information">
	<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<td width="25%" class="labelR">Current Epoch</td>
			<td>${command.scheduledEpoch.epoch.name}</td>
	</tr>
	<tr>
		<td class="labelR">Type</td>
		<td>${command.ifTreatmentScheduledEpoch?'Treatment':'Non Treatment'}</td>
	</tr>
	<tr>
		<td class="labelR">Enrolling</td>
		<td>${!command.ifTreatmentScheduledEpoch?command.scheduledEpoch.epoch.enrollmentIndicator:'True'}</td>
	</tr>
	<tr>
		<td class="labelR">Epoch Status</td>
		<td>${command.scheduledEpoch.scEpochWorkflowStatus.code}</td>
	</tr>
	</table>
	</chrome:division>
	<chrome:division id="enrollment" title="Enrollment Details">
	<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<td class="labelR">Registration Start Date</td>
			<td>${command.startDate }</td>
	</tr>
	<tr>
		<td class="labelR" width="35%">Informed Consent Signed Date</td>
		<td><tags:requiredFieldEmptyIndicator value='${command.informedConsentSignedDateStr}' workflow='registration'/></td>
	</tr>
	<tr>
		<td class="labelR">Informed Consent Version</td>
		<td><tags:requiredFieldEmptyIndicator value='${command.informedConsentVersion}' workflow='registration'/></td>
	</tr>
	<tr>
		<td width="25%" class="labelR">Treating Physician</td>
		<td>${command.treatingPhysicianFullName}&nbsp;</td>
	</tr>
	<tr>
		<td width="25%" class="labelR">Primary Disease</td>
		<td>${command.diseaseHistory.primaryDiseaseStr }</td>
	</tr>
	<tr>
		<td width="20%" class="labelR">Primary Disease Site</td>
		<td>${command.diseaseHistory.primaryDiseaseSiteStr }</td>
		</tr>
	</table>
	</chrome:division>
	<c:if test="${command.ifTreatmentScheduledEpoch}">
		<chrome:division id="Eligibility" title="Eligibility information">
		<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
			<tr>
				<td width="25%" class="labelR">Eligibility
				Indicator</td>
				<td><tags:requiredFieldInvalidIndicator value='${command.scheduledEpoch.eligibilityIndicator?"True":"False" }' workflow='registration' compareWith='True'/></td>
			</tr>
		</table>
		<c:choose>
		<c:when test="${fn:length(command.scheduledEpoch.inclusionEligibilityAnswers) == 0 && fn:length(command.scheduledEpoch.exclusionEligibilityAnswers) == 0}">
		There is no eligibility check list available for this epoch
		</c:when>
		<c:otherwise>
		<br>
			<strong>Inclusion Criteria:</strong>
			
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
				<tr>
					<th scope="col" align="left">Question</th>
					<th scope="col" align="left">Answer</th>
				</tr>
				<c:forEach items="${command.scheduledEpoch.inclusionEligibilityAnswers}" var="criteria">
			<tr class="results">
				<td class="alt" align="left">${ criteria.eligibilityCriteria.questionText}</td>
				<td class="alt" align="left"><tags:requiredFieldEmptyIndicator value='${criteria.answerText}' workflow='registration'/></td>
			</tr>
		</c:forEach>
		</table>
		</div>
		<br>
		<strong>Exclusion Criteria:</strong>
		
		<div class="review">
		<table border="0" cellspacing="0" cellpadding="0"  width="50%" class="tablecontent">
			<tr>
				<th scope="col" align="left">Question</th>
				<th scope="col" align="left">Answer</th>
			</tr>
			<c:forEach items="${command.scheduledEpoch.exclusionEligibilityAnswers}" var="criteria">
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
		<c:when test="${fn:length(command.scheduledEpoch.subjectStratificationAnswers) == 0}">
		<table width="50%" border="0" cellspacing="0" cellpadding="0" id="table1">
			<tr>
				<td class="label" align=left>The Selected Epoch does not have any stratification factors</td>
			</tr>
		</table>
		</c:when>
		<c:otherwise>
		<table border="0" cellspacing="0" cellpadding="0" class="tablecontent"  width="50%">
			<tr>
				<th scope="col" align="left">Strata</th>
				<th scope="col" align="left"><b>Answer</th>
			</tr>
			<c:forEach items="${command.scheduledEpoch.subjectStratificationAnswers}" var="criteria">
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
	</c:if>
</tags:formPanelBox>
</body>
</html>
