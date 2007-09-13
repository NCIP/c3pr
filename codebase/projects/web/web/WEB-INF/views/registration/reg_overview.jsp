<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration"%>
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
function activateInPlaceEditing(arrayElements){
	for(aE=0 ; aE<arrayElements.length ; aE++){
		arrayElements[aE].enterEditMode('click');
	}
}
</script>
</head>
<body>
<form:form method="post">
	<tags:tabFields tab="${tab}"/>
</form:form>
<c:if test="${actionRequired}">
	<registrationTags:register registration="${command}" newReg="${newRegistration}" actionButtonLabel="${actionLabel}"/>
</c:if>
<tags:panelBox>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
			<strong>Subject Information </strong><br>
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
				<tr>
					<td width="25%" class="labelR">First Name:</td>
					<td>${command.participant.firstName}</td>
				</tr>
				<tr>
					<td class="labelR">Last Name:</td>
					<td>${command.participant.lastName}</td>
				</tr>
				<tr>
					<td class="labelR">Gender:</td>
					<td>${command.participant.administrativeGenderCode}</td>
				</tr>
				<tr>
					<td class="labelR">Subject MRN:</td>
					<td>${command.participant.primaryIdentifier }</td>
				</tr>
				<tr>
					<td width="25%" class="labelR">Birth Date:</td>
					<td>${command.participant.birthDateStr}</td>
				</tr>
				<tr>
					<td class="labelR">Ethnicity:</td>
					<td>${command.participant.ethnicGroupCode}</td>
				</tr>
				<tr>
					<td class="labelR">Race(s):</td>
					<td>${command.participant.raceCode}</td>
				</tr>
			</table>
			</div>
			<hr align="left" width="95%">
			<strong>Study Information </strong> <br>
			<br>
			<div class="review">
			<strong>Study Details:</strong><br>
			<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
				<tr>
					<td class="labelR"> Status:</td>
					<td>${command.studySite.study.status}</td>
				</tr>
				<tr>
					<td width="25%" class="labelR">Short Title:</td>
					<td>${command.studySite.study.shortTitleText}</td>
				</tr>
				<tr>
					<td class="labelR">Randomized Indicator:</td>
					<td>${command.studySite.study.randomizedIndicator}</td>
				</tr>
				<tr>
					<td class="labelR">Multi Institution:</td>
					<td>${command.studySite.study.multiInstitutionIndicator}</td>
				</tr>
				<tr>
					<td class="labelR"> Phase Code:</td>
					<td>${command.studySite.study.phaseCode}</td>
				</tr>
				<tr>
					<td width="25%" class="labelR">Sponsor Code:</td>
					<td>${command.studySite.study.identifiers[0].value}</td>
				</tr>
				<tr>
					<td width="25%" class="labelR">Current Epoch:</td>
					<td>${command.scheduledEpoch.epoch.name}</td>
				</tr>
			</table>
			<table width="70%"><tr><td><p style="border-bottom: 1px dotted #000000;">&nbsp;</p></td></tr></table>
			<strong>Study Site Information:</strong><br>
			<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
				<tr>
					<td width="25%" class="labelR"> Name:</td>
					<td>${command.studySite.healthcareSite.name}</td>
				</tr>
				<tr>
					<td class="labelR">Address:</td>
					<td>${command.studySite.healthcareSite.address.streetAddress},
					${command.studySite.healthcareSite.address.city},
					${command.studySite.healthcareSite.address.stateCode},
					${command.studySite.healthcareSite.address.postalCode}</td>
				</tr>
				<tr>
					<td class="labelR"> Status Code:</td>
					<td>${command.studySite.statusCode}</td>
				</tr>
				<tr>
					<td class="labelR"> NCI Institution Code:</td>
					<td>${command.studySite.healthcareSite.nciInstituteCode}</td>
				</tr>
				<tr>
					<td class="labelR">IRB Approval Date:</td>
					<td>${command.studySite.irbApprovalDateStr}</td>
				</tr>
			</table>
			</div>
			<hr align="left" width="95%">
			<br>
			<strong>Enrollment Information </strong><br>
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
				<tr>
					<td class="labelR">Registration Start Date:</td>
					<td><tags:inPlaceEdit value="${command.startDateStr }" path="startDate" /></td>
				</tr>
				<tr>
					<td width="25%" class="labelR">Registration Status:</td>
					<td>${command.regWorkflowStatus}&nbsp;</td>
				</tr>
				<tr>
					<td class="labelR" width="25%">Informed Consent Signed Date:</td>
					<td><tags:inPlaceEdit value="${command.informedConsentSignedDateStr }" path="informedConsentSignedDate" /></td>
				</tr>
				<tr>
					<td class="labelR">Informed Consent Version:</td>
					<td><tags:inPlaceEdit value="${command.informedConsentVersion}" path="informedConsentVersion" /></td>
				</tr>
				<tr>
					<td width="25%" class="labelR">Current Epoch:</td>
					<td >${command.scheduledEpoch.epoch.name}</td>
				</tr>
				<tr>
					<td width="25%" class="labelR">Current Epoch Status:</td>
					<td>${command.scheduledEpoch.scEpochWorkflowStatus}</td>
				</tr>
				<tr>
					<td width="25%" class="labelR">Treating Physician:</td>
					<td>${command.treatingPhysicianFullName}&nbsp;</td>
				</tr>
				<tr>
					<td width="25%" class="labelR">Data Entry Status:</td>
					<td>${command.dataEntryStatusString}&nbsp;</td>
				</tr>
			</table>
			</div>
			<script>
			eArray=new Array();
			eArray.push(editor_startDate);
			eArray.push(editor_informedConsentSignedDate);
			eArray.push(editor_informedConsentVersion);
			</script>
			<input type="button" value="Edit" onclick="activateInPlaceEditing(eArray)"/>
			<hr align="left" width="95%">					
			<br>
			<strong>Disease Information </strong><br>
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
				<tr>
					<td width="25%" class="labelR">Primary Disease:</td>
					<td>${command.diseaseHistory.primaryDiseaseStr }</td>
				</tr>
				<tr>
					<td width="20%" class="labelR">Primary Disease Site:</td>
					<td>${command.diseaseHistory.primaryDiseaseSiteStr }</td>
				</tr>
			</table>
			</div>
			<c:if test="${command.ifTreatmentScheduledEpoch}">			
				<hr align="left" width="95%">					
				<br>
				<strong>Check Eligibility </strong><br>
				<div class="review">
				<c:choose>
				<c:when test="${fn:length(command.scheduledEpoch.inclusionEligibilityAnswers) == 0 && fn:length(command.scheduledEpoch.exclusionEligibilityAnswers) == 0}">
				<span class="red"><strong>This epoch does not have eligibility check list</strong></span>
				</c:when>
				<c:otherwise>
				<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
					<tr>
						<td width="25%" class="labelR">Eligibility
						Indicator:</td>
						<td>${command.scheduledEpoch.eligibilityIndicator }</td>
					</tr>
				</table>
				<br><br>
					<strong>Inclusion Criteria:</strong>
					
					<div class="review">
					<table border="0" cellspacing="0" cellpadding="0" class="tablecontent">
						<tr>
							<th scope="col" align="left">Question</th>
							<th scope="col" align="left">Answer</th>
						</tr>
						<c:forEach items="${command.scheduledEpoch.inclusionEligibilityAnswers}" var="criteria">
							<tr class="results">
								<td class="alt" align="left">${ criteria.eligibilityCriteria.questionText}</td>
								<td class="alt" align="left">${criteria.answerText==''?'<span class="red"><b>Unanswered</span>':criteria.answerText }</td>
							</tr>
						</c:forEach>
					</table>
					</div>
					<br><br>
					<strong>Exclusion Criteria:</strong>
					
					<div class="review">
					<table border="0" cellspacing="0" cellpadding="0" class="tablecontent">
						<tr>
							<th scope="col" align="left">Question</th>
							<th scope="col" align="left">Answer</th>
						</tr>
						<c:forEach items="${command.scheduledEpoch.exclusionEligibilityAnswers}" var="criteria">
							<tr class="results">
								<td class="alt" align="left">${ criteria.eligibilityCriteria.questionText}</td>
								<td class="alt" align="left">${criteria.answerText==''?'<span class="red"><b>Unanswered</span>':criteria.answerText }</td>
							</tr>
						</c:forEach>
					</table>
					</div>
				</c:otherwise>
				</c:choose>
				</div>
	
				<hr align="left" width="95%">
				<br>
				<strong>Stratification Information </strong><br>
				<div class="review">
				<c:choose>
				<c:when test="${fn:length(command.scheduledEpoch.subjectStratificationAnswers) == 0}">
				<table width="50%" border="0" cellspacing="0" cellpadding="0" id="table1">
					<tr>
						<td class="label red" align=left>This epoch does not involve Stratification</td>
					</tr>
				</table>
				</c:when>
				<c:otherwise>
				<br>
				<table border="0" cellspacing="0" cellpadding="0" class="tablecontent">
					<tr>
						<th scope="col" align="left">Strata</th>
						<th scope="col" align="left"><b>Answer</th>
					</tr>
					<c:forEach items="${command.scheduledEpoch.subjectStratificationAnswers}" var="criteria">
						<tr class="results">
							<td class="alt" align="left">${criteria.stratificationCriterion.questionText}</td>
							<td class="alt" align="left">${criteria.stratificationCriterionAnswer.permissibleAnswer==''?'<span class="red"><b>Unanswered</span>':criteria.stratificationCriterionAnswer.permissibleAnswer }</td>
						</tr>
					</c:forEach>
				</table>
				</c:otherwise>
				</c:choose>
				</div>
				<c:if test="${!empty armAssigned}">
				<hr align="left" width="95%">					
				<br>
				<strong>Assigned Arm </strong><br>
				<div class="review">
				<table width="50%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
					<tr>
						<td width="25%" class="labelR">${armAssignedLabel }:</td>
						<td>${armAssigned}</td>
					</tr>
				</table>
				</div>
				</c:if>
			</c:if>
			<hr align="left" width="95%">					
			<br>
			<div align="right"><input type="button" value="Export" onClick="$('exportForm')._target.name='xxxx';$('exportForm').submit();"/></div>
		</td>
	</tr>
</table>
</tags:panelBox>
<form:form id="exportForm" method="post">
	<tags:tabFields tab="${tab}"/>
	<input type="hidden" name="_action" value="export"/>
</form:form>
</body>
</html>
