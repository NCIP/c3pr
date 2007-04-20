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
<table width="60%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
			<strong>Step 1. Subject Information </strong><br>
			<div class="review">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="50%" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
						<tr>
							<td><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="1" class="heightControl"></td>
							<td><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="1" class="heightControl"></td>
						</tr>
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
					</table>
					</td>
					<td valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
						<tr>
							<td><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="1" class="heightControl"></td>
							<td><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="1" class="heightControl"></td>
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
					</td>
				</tr>
			</table>
			</div>
			<hr align="left" width="95%">
			<strong><br>
			Step 2. Study Information </strong> <br>
			<br>
			<div class="review"><strong>Study Details:</strong><br>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="50%" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td class="labelR"> Status:</td>
							<td>${command.studySite.study.status}</td>
						</tr>
						<tr>
							<td width="25%" class="labelR">Short Title:</td>
							<td>${command.studySite.study.shortTitleText}</td>
						</tr>
						<tr>
							<td class="labelR"> Disease Code:</td>
							<td>${command.studySite.study.diseaseCode}</td>
						</tr>
						<tr>
							<td class="labelR"> Monitor Code:</span></td>
							<td>${command.studySite.study.monitorCode}</td>
						</tr>
					</table>
					</td>
					<td width="50%" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
							<td width="35%" class="labelR">Sponsor Code:</td>
							<td>${command.studySite.study.sponsorCode}</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			<table width="70%"><tr><td><p style="border-bottom: 1px dotted #000000;">&nbsp;</p></td></tr></table>
			<strong>Study Site Information:</strong><br>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="table1">
				<tr>
					<td valign="top">
					<table width="80%" border="0" cellspacing="0" cellpadding="0"
						id="table1">
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
					</td>
				</tr>
			</table>
			</div>
			<hr align="left" width="95%">
			<br>
			<strong>Step 3. Enrollment Information </strong><br>
			<div class="review">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="table1">
				<tr>
					<td width="50%" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="1" class="heightControl"></td>
							<td><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="1" class="heightControl"></td>
						</tr>
						<tr>
							<td width="40%" class="labelR">Study Subject
							Identifier:</td>
							<td>${command.studyParticipantIdentifier }</td>
						</tr>
						<tr>
							<td class="labelR">Registration Start Date:</td>
							<td>${command.startDate }</td>
						</tr>
					</table>
					</td>
					<td valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="1" class="heightControl"></td>
							<td><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="1" class="heightControl"></td>
						</tr>
						<tr>
							<td class="labelR" width="50%">Informed Consent Signed Date:</td>
							<td>${command.informedConsentSignedDateStr}</td>
						</tr>
						<tr>
							<td class="labelR">Informed Consent Version:</td>
							<td>${command.informedConsentVersion}</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			<table width="70%"><tr><td><p style="border-bottom: 1px dotted #000000;">&nbsp;</p></td></tr></table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="30%" class="labelR">Treating Physician:</td>
					<td>${command.treatingPhysician.healthcareSiteInvestigator.investigator.fullName}&nbsp;</td>
				</tr>
			</table>
			</div>
			<tabs:tabButtonControls text="edit" target="3"/>
			<hr align="left" width="95%">					
			<br>
			<strong>Step 4. Check Eligibility </strong><br>
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" id="table1">
				<tr>
					<td><img src="<tags:imageUrl name="spacer.gif"/>"
						width="1" height="1" class="heightControl"></td>
					<td><img src="<tags:imageUrl name="spacer.gif"/>"
						width="1" height="1" class="heightControl"></td>
				</tr>
				<tr>
					<td width="45%" class="labelR">Eligibility
					Indicator:</td>
					<td>${command.eligibilityIndicator }</td>
				</tr>
			</table>
			<table width="70%"><tr><td><p style="border-bottom: 1px dotted #000000;">&nbsp;</p></td></tr></table>			
			<c:choose>
			<c:when test="${fn:length(command.inclusionEligibilityAnswers) == 0 && fn:length(command.exclusionEligibilityAnswers) == 0}">
			There is no eligibility check list available for this subject
			</c:when>
			<c:otherwise>
				<strong>Inclusion Criteria:</strong>
				<div class="review">
				<table width="50%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="label">Question</td>
						<td><b>Answer</td>
					</tr>
					<c:forEach items="${command.inclusionEligibilityAnswers}" var="criteria">
						<tr>
							<td width="85%" class="label">${ criteria.eligibilityCriteria.questionText}</td>
							<td>${criteria.answerText==''?'<span class="red"><b>Unanswered</span>':criteria.answerText }</td>
						</tr>
					</c:forEach>
				</table>
				</div>
				<strong>Exclusion Criteria:</strong>
				<div class="review">
				<table width="50%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="label">Question</td>
						<td><b>Answer</td>
					</tr>
					<c:forEach items="${command.exclusionEligibilityAnswers}" var="criteria">
						<tr>
							<td width="85%" class="label">${ criteria.eligibilityCriteria.questionText}</td>
							<td>${criteria.answerText==''?'<span class="red"><b>Unanswered</span>':criteria.answerText }</td>
						</tr>
					</c:forEach>
				</table>
				</div>
			</c:otherwise>
			</c:choose>
			</div>
			<tabs:tabButtonControls text="edit" target="4"/>
			<hr align="left" width="95%">
			<br>
			<strong>Step 5. Stratification Information </strong><br>
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0"
				id="table1">
				<tr>
					<td width="50%" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="1" class="heightControl"></td>
							<td><img src="<tags:imageUrl name="spacer.gif"/>"
								width="1" height="1" class="heightControl"></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</div>
			<tabs:tabButtonControls text="edit" target="5"/>
		</td>
	</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</tabs:division>
</div>
</form:form>
</body>
</html>
