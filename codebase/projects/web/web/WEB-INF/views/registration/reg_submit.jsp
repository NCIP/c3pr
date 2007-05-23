<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
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
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
</head>
<body>
<form:form method="post">
<input type="hidden" name="_finish" value="true"/>
<div><tabs:division id="enrollment-table1">
<!-- MAIN BODY STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
			<strong>Subject Information </strong><br>
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" id="mytable">
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
			<table width="50%" border="0" cellspacing="0" cellpadding="0" id="mytable">
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
			</table>
			<table width="70%"><tr><td><p style="border-bottom: 1px dotted #000000;">&nbsp;</p></td></tr></table>
			<strong>Study Site Information:</strong><br>
			<table width="50%" border="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<td width="25%" class="labelR"> Name:</td>
					<td>${command.studySite.site.name}</td>
				</tr>
				<tr>
					<td class="labelR">Address:</td>
					<td>${command.studySite.site.address.streetAddress},
					${command.studySite.site.address.city},
					${command.studySite.site.address.stateCode},
					${command.studySite.site.address.postalCode}</td>
				</tr>
				<tr>
					<td class="labelR"> Status Code:</td>
					<td>${command.studySite.statusCode}</td>
				</tr>
				<tr>
					<td class="labelR"> NCI Institution Code:</td>
					<td>${command.studySite.site.nciInstituteCode}</td>
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
			<table width="50%" border="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<td width="25%" class="labelR">Study Subject
					Identifier:</td>
					<td>${command.studyParticipantIdentifier }</td>
				</tr>
				<tr>
					<td class="labelR">Registration Start Date:</td>
					<td>${command.startDate }</td>
				</tr>
				<tr>
					<td class="labelR" width="25%">Informed Consent Signed Date:</td>
					<td>${command.informedConsentSignedDateStr}</td>
				</tr>
				<tr>
					<td class="labelR">Informed Consent Version:</td>
					<td>${command.informedConsentVersion}</td>
				</tr>
				<tr>
					<td width="25%" class="labelR">Treating Physician:</td>
					<td>${command.treatingPhysician.healthcareSiteInvestigator.investigator.fullName}&nbsp;</td>
				</tr>
			</table>
			</div>
			<c:if test="${empty editable}">
				<tabs:tabButtonControls text="edit" target="3"/>
			</c:if>
			<hr align="left" width="95%">					
			<br>
			<strong>Disease Information </strong><br>
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" id="mytable">
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
			<c:if test="${empty editable}">
				<tabs:tabButtonControls text="edit" target="4"/>
			</c:if>

			<hr align="left" width="95%">					
			<br>
			<strong>Check Eligibility </strong><br>
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<td width="25%" class="labelR">Eligibility
					Indicator:</td>
					<td>${command.eligibilityIndicator }</td>
				</tr>
			</table>
			<c:choose>
			<c:when test="${fn:length(command.inclusionEligibilityAnswers) == 0 && fn:length(command.exclusionEligibilityAnswers) == 0}">
			There is no eligibility check list available for this subject
			</c:when>
			<c:otherwise>
				<strong>Inclusion Criteria:</strong>
				<div class="review">
				<table border="0" cellspacing="0" cellpadding="0" id="mytable">
					<tr>
						<th scope="col" align="left">Question</td>
						<th scope="col" align="left">Answer</td>
					</tr>
					<c:forEach items="${command.inclusionEligibilityAnswers}" var="criteria">
						<tr class="results">
							<td class="alt" align="left">${ criteria.eligibilityCriteria.questionText}</td>
							<td class="alt" align="left">${criteria.answerText==''?'<span class="red"><b>Unanswered</span>':criteria.answerText }</td>
						</tr>
					</c:forEach>
				</table>
				</div>
				<strong>Exclusion Criteria:</strong>
				<div class="review">
				<table border="0" cellspacing="0" cellpadding="0" id="mytable">
					<tr>
						<th scope="col" align="left">Question</td>
						<th scope="col" align="left">Answer</td>
					</tr>
					<c:forEach items="${command.exclusionEligibilityAnswers}" var="criteria">
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
			<c:if test="${empty editable}">
				<tabs:tabButtonControls text="edit" target="5"/>
			</c:if>

			<hr align="left" width="95%">
			<br>
			<strong>Stratification Information </strong><br>
			<div class="review">
			<c:choose>
			<c:when test="${fn:length(command.subjectStratificationAnswers) == 0}">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" id="table1">
				<tr>
					<td class="label" align=left>The Selected Study does not have Stratification Factors</td>
				</tr>
			</table>
			</c:when>
			<c:otherwise>
			<table border="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<th scope="col" align="left">Strata</td>
					<th scope="col" align="left"><b>Answer</td>
				</tr>
				<c:forEach items="${command.subjectStratificationAnswers}" var="criteria">
					<tr class="results">
						<td class="alt" align="left">${criteria.stratificationCriterion.questionText}</td>
						<td class="alt" align="left">${criteria.stratificationCriterionAnswer.permissibleAnswer==''?'<span class="red"><b>Unanswered</span>':criteria.stratificationCriterionAnswer.permissibleAnswer }</td>
					</tr>
				</c:forEach>
			</table>
			</c:otherwise>
			</c:choose>
			</div>
			<c:if test="${empty editable}">
				<tabs:tabButtonControls text="edit" target="6"/>
			</c:if>
						<hr align="left" width="95%">					
			<br>
			<strong>Randomization Information </strong><br>
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<td width="25%" class="labelR">Arm:</td>
					<td><c:if test="${!empty command.scheduledArms[0].arm }">${command.scheduledArms[0].arm.name }</c:if></td>
				</tr>
			</table>
			</div>
			<c:if test="${empty editable}">
				<tabs:tabButtonControls text="edit" target="7"/>
			</c:if>
		</td>
	</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</tabs:division>
</div>
</form:form>
</body>
</html>
