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
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
</head>
<body>
<div><tabs:division id="enrollment-details">
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="60%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
			<form:form method="post">
			<tabs:tabFields tab="${tab}" />		
			<input type="hidden" name="_finish" value="true"/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="table1">
				<tr>
					<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
					<!-- RIGHT CONTENT STARTS HERE --> <strong>Step 1.
					Subject Information </strong><br>
					<div class="review">
					<table width="50%" border="0" cellspacing="0" cellpadding="0"
						id="details">
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
									<td width="25%" class="label"><em></em>First Name:</td>
									<td>${command.participant.firstName}</td>
								</tr>
								<tr>
									<td class="label"><em></em>Last Name:</td>
									<td>${command.participant.lastName}</td>
								</tr>
								<tr>
									<td class="label"><em></em>Gender:</td>
									<td>${command.participant.administrativeGenderCode}</td>
								</tr>
								<tr>
									<td class="label"><em></em>Subject MRN:</td>
									<td>${command.participant.primaryIdentifier }</td>
								</tr>
							</table>
							</td>
							<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td width="25%" class="label"><em></em>Birth Date:</td>
									<td valign="top">${command.participant.birthDateStr}</td>
								</tr>
								<tr>
									<td class="label"><em></em>Ethnicity:</td>
									<td>${command.participant.ethnicGroupCode}</td>
								</tr>
								<tr>
									<td class="label"><em></em>Race(s):</td>
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
					<table width="50%" border="0" cellspacing="0" cellpadding="0"
						id="details">
						<tr>
							<td width="50%" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td width="25%" class="label">Short Title:</td>
									<td>${command.studySite.study.shortTitleText}</td>
								</tr>
								<tr>
									<td class="label"><em></em> Status:</td>
									<td>${command.studySite.study.status}</td>
								</tr>
								<tr>
									<td class="label"><em></em> Disease Code:</td>
									<td>${command.studySite.study.diseaseCode}</td>
								</tr>
								<tr>
									<td class="label"><em></em> Monitor Code:</span></td>
									<td>${command.studySite.study.monitorCode}</td>
								</tr>
								<tr>
									<td class="label"><em></em><em></em> Phase Code:</td>
									<td>${command.studySite.study.phaseCode}</td>
								</tr>
							</table>
							</td>
							<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td width="25%" class="label"><em></em>Sponsor Code:</td>
									<td>${command.studySite.study.sponsorCode}</td>
								</tr>
								<tr>
									<td class="label"><em></em>Randomized Indicator:</td>
									<td>${command.studySite.study.randomizedIndicator}</td>
								</tr>
								<tr>
									<td class="label">Multi Institution:</td>
									<td>${command.studySite.study.multiInstitutionIndicator}</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</div>
					<hr align="left" width="95%">
					<br>
					<strong>Study Site Information:</strong><br>
					<div class="review">
					<table width="50%" border="0" cellspacing="0" cellpadding="0"
						id="details">
						<tr>
							<td width="0%" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td width="25%" class="label"><em></em> Name:</td>
									<td>${command.studySite.site.name}</td>
								</tr>
								<tr>
									<td class="label"><em></em> NCI Institution Code:</td>
									<td>${command.studySite.site.nciInstituteCode}</td>
								</tr>
								<tr>
									<td class="label">Address:</td>
									<td>${command.studySite.site.address.streetAddress},
									${command.studySite.site.address.city},
									${command.studySite.site.address.stateCode},
									${command.studySite.site.address.postalCode}</td>
								</tr>
								<tr>
									<td class="label">IRB Approval Date:</td>
									<td>${command.studySite.irbApprovalDateStr}</td>
								</tr>
								<tr>
									<td class="label"><em></em><em></em> Status Code:</td>
									<td>${command.studySite.statusCode}</td>
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
					<table width="50%" border="0" cellspacing="0" cellpadding="0"
						id="details">
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
									<td width="25%" class="label"><em></em>Study Subject
									Identifier:</td>
									<td>${command.studyParticipantIdentifier }</td>
								</tr>
								<tr>
									<td class="label"><em></em>Start Date:</td>
									<td>${command.startDate }</td>
								</tr>
								<tr>
									<td class="label"><em></em>Informed Consent Signed Date:</td>
									<td>${command.informedConsentSignedDateStr}</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</div>
					<input type="image" name="_target3" src="<tags:imageUrl name="b-edit.gif"/>" border="0"
							alt="edit this page">
					<br>
					<strong>Step 4. Check Eligibility </strong><br>
					<table width="50%" border="0" cellspacing="0" cellpadding="0"
						id="details">
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
									<td width="25%" class="label"><em></em>Eligibility
									Indicator:</td>
									<td>Yes</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<input type="image" name="_target4" src="<tags:imageUrl name="b-edit.gif"/>" border="0"
							alt="edit this page">

					<hr align="left" width="95%">
					<br>
					<strong>Step 5. Stratification Information </strong><br>
					<div class="review">
					<table width="50%" border="0" cellspacing="0" cellpadding="0"
						id="details">
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
					<input type="image" name="_target5" src="<tags:imageUrl name="b-edit.gif"/>" border="0"
							alt="edit this page">

					</td>
					<!-- LEFT CONTENT ENDS HERE -->
				</tr>
			</table>
			</form:form>
		</td>
	</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</div>
</tabs:division>
</div>
</body>
</html>
