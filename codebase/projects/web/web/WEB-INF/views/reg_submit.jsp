<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>C3Pr V2</title>
<link href="resources/styles.css" rel="stylesheet" type="text/css">
<link href="resources/search.css" rel="stylesheet" type="text/css">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function getPage(s){
	parent.window.location="reg_patient_search.htm";
}
function add(){
var action = confirm ("You have not completed adding this protocol.\r\rStarting over will lose current protocol data?")
if (action){
	parent.window.location="reg_enroll_patient.htm";
}}
function validatePage(){
	return true;
}
function doNothing(){
}
function updateTargetPage(s){
	if(validatePage()){
		document.getElementById("nextView").value=s;
		document.reviewForm.submit();
	}
}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="99%"><img src="images/c3prLogo.gif" alt="C3PR"
			width="181" height="36" class="gelogo"></td>


	</tr>
</table>
<!-- TOP LOGOS END HERE -->
<!-- TOP NAVIGATION STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="topNav">

	<tr valign="middle">
		<td width="99%" class="left"><img src="images/topNavL.gif"
			width="2" height="20" align="absmiddle" class="currentL"><span
			class="current"><img src="images/topNavArrowDown.gif"
			width="5" height="20" align="absmiddle"> Registration</span><a
			href="protocol.htm"> Study </a><img src="images/topNavR.gif"
			width="2" height="20" align="absmiddle" class="currentR"><a
			href="/c3pr/searchparticipant.do">Subject</a><img
			src="images/topDivider.gif" width="2" height="20" align="absmiddle"
			class="divider"><a href="analysis">Reports</a><img
			src="images/topDivider.gif" width="2" height="20" align="absmiddle"
			class="divider"></td>

		<td class="right"><img src="images/topDivider.gif" width="2"
			height="20" align="absmiddle" class="divider"><a href="logOff">Log
		Off</a></td>
	</tr>
</table>
<!-- TOP NAVIGATION ENDS HERE -->
<!-- SUB NAV STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="subNav">
	<tr>
		<td width="99%" valign="middle" class="welcome">Welcome, User
		Name</td>
		<td valign="middle" class="right"><a href="help">Help</a></td>
	</tr>
</table>
<!-- SUB NAV ENDS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="display">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="tab"> <img
							src="images/tabWhiteL.gif" width="3" height="16"
							align="absmiddle"> 1. Select Study <img
							src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"><img src="images/tabGrayL.gif" width="3"
							height="16" align="absmiddle"> 2. Select Subject <img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						3. <a href="javascript:updateTargetPage('enrollView');">Enrollment
						Details </a> <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"><img src="images/tabGrayL.gif" width="3"
							height="16" align="absmiddle"> 4. <a
							href="javascript:updateTargetPage('checkEligibilityView');">Check
						Eligibility</a> <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"><img src="images/tabGrayL.gif" width="3"
							height="16" align="absmiddle"> 5. <a
							href="javascript:updateTargetPage('enrollView');">Enroll
						Subject </a> <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"><img src="images/tabGrayL.gif" width="3"
							height="16" align="absmiddle"> 6. <a
							href="javascript:updateTargetPage('stratifyView');">Stratify</a>
						<img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"><img src="images/tabGrayL.gif" width="3"
							height="16" align="absmiddle"> 7. <a
							href="javascript:updateTargetPage('randomizeView');">Randomize</a>
						<img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span><span class="current"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						7. Review and Submit <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span></td>
						<td><img src="images/spacer.gif" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotL"><img src="images/spacer.gif"
							width="1" height="7"></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- MAIN BODY STARTS HERE -->
			<tr>
				<td>
				<div class="workArea">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<form:form name="reviewForm" method="post">
						<tr>
							<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

							<td id="current">Confirm Registration for
							${command.participant.firstName} ${command.participant.lastName}
							on ${command.studySite.study.shortTitleText}</td>
							<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
						</tr>
						<tr>

							<td class="display"><!-- TABS LEFT START HERE -->
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>

									<!-- LEFT CONTENT STARTS HERE -->
									<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
									<!-- RIGHT CONTENT STARTS HERE --> <input type="hidden"
										name="nextView"> <strong>Step 1. Subject
									Information </strong><br>
									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr>
											<td width="50%" valign="top">
											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
												<tr>
													<td><img src="images/spacer.gif" width="1" height="1"
														class="heightControl"></td>
													<td><img src="images/spacer.gif" width="1" height="1"
														class="heightControl"></td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>First
													Name:</td>
													<td>${command.participant.firstName}</td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>Last
													Name:</td>
													<td>${command.participant.lastName}</td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>Gender:
													</td>
													<td>${command.participant.administrativeGenderCode}</td>
												</tr>
											</table>
											</td>
											<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
												<tr>
													<td class="label"><span class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</span><em></em>Birth
													Date:</td>
													<td valign="top">${command.participant.birthDate}</td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>Ethnicity:
													</td>
													<td>${command.participant.ethnicGroupCode}</td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>Race(s):</td>
													<td>${command.participant.raceCode}</td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									<a href="javascript:doNothiong();"><img
										src="images/b-edit.gif" alt="Edit" width="39" height="16"
										border="0"></a>

									<hr align="left" width="95%">
									<strong><br>
									Step 2. Study Information </strong> <br>
									<br>
									<div class="review"><strong>Study Details:</strong><br>
									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr>
											<td width="50%" valign="top">
											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
												<tr>
													<td class="label">Short Title:</td>
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
											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
												<tr>
													<td class="label"><em></em>Sponsor Code:</td>
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
												<tr>
													<td class="label">Sponsor Code:</td>
													<td>${command.studySite.study.type}</td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									<br>
									<strong>Study Site Information:</strong><br>
									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr>
											<td width="50%" valign="top">
											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
												<tr>
													<td class="label"><em></em> Name:</td>
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
													<td>${command.studySite.irbApprovalDate}</td>
												</tr>
												<tr>
													<td class="label"><em></em><em></em> Status Code:</td>
													<td>${command.studySite.statusCode}</td>
												</tr>
											</table>
											</td>
											<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
											</table>
											</td>
										</tr>
									</table>
									<hr align="left" width="95%">
									<br>
									<strong>Step 3. Enrollment Information </strong><br>
									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr>
											<td width="50%" valign="top">
											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
												<tr>
													<td><img src="images/spacer.gif" width="1" height="1"
														class="heightControl"></td>
													<td><img src="images/spacer.gif" width="1" height="1"
														class="heightControl"></td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>Study Subject Identifier:</td>
													<td>${command.studyParticipantIdentifier }</td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>Start Date:</td>
													<td>${command.startDate }</td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>Informed Consent Signed Date:</td>
													<td>${command.informedConsentSignedDate }</td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									<a href="javascript:updateTargetPage('enrollView');"><img
										src="images/b-edit.gif" alt="Edit" width="39" height="16"
										border="0"></a>
									<hr align="left" width="95%">
									<br>
									<strong>Step 4. Check Eligibility </strong><br>
									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr>
											<td width="50%" valign="top">
											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
												<tr>
													<td><img src="images/spacer.gif" width="1" height="1"
														class="heightControl"></td>
													<td><img src="images/spacer.gif" width="1" height="1"
														class="heightControl"></td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>Is
													Eligible:</td>
													<td>Yes</td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									<a href="javascript:updateTargetPage('checkEligibilityView');"><img
										src="images/b-edit.gif" alt="Edit" width="39" height="16"
										border="0"></a>

									<hr align="left" width="95%">
									<br>
									<strong>Step 5. Stratification Information </strong><br>
									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr>
											<td width="50%" valign="top">
											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
												<tr>
													<td><img src="images/spacer.gif" width="1" height="1"
														class="heightControl"></td>
													<td><img src="images/spacer.gif" width="1" height="1"
														class="heightControl"></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									<a href="javascript:updateTargetPage('stratifyView');"><img
										src="images/b-edit.gif" alt="Edit" width="39" height="16"
										border="0"></a>

									<hr align="left" width="95%">
									<br>
									<strong>Step 6. Randomization Information </strong><br>
									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr>
											<td width="50%" valign="top">
											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
												<tr>
													<td><img src="images/spacer.gif" width="1" height="1"
														class="heightControl"></td>
													<td><img src="images/spacer.gif" width="1" height="1"
														class="heightControl"></td>
												</tr>
												<c:forEach var="epoch"
													items="${command.studySite.study.epochs}">
													<c:choose>
														<c:when test="${epoch.name=='Treatment'}">
															<tr>
																<td class="label"><em></em> Epoch Name:</td>
																<td>${epoch.name}</td>
															</tr>
															<tr>
																<td class="label"><em></em> Epoch Description:</td>
																<td>${epoch.descriptionText}</td>
															</tr>
															<c:forEach var="arm" items="${epoch.arms}">
																<c:choose>
																	<c:when
																		test="${arm.id==command.scheduledArms[0].arm.id}">
																		<tr>
																			<td class="label">Arm:</td>
																			<td>${arm.name}</td>
																		</tr>
																		<tr>
																			<td class="label">Arm Descripstion:</td>
																			<td>${arm.descriptionText}</td>
																		</tr>
																		<tr>
																			<td class="label">Arm Target Accrual Number:</td>
																			<td>${arm.targetAccrualNumber}</td>
																		</tr>

																	</c:when>
																</c:choose>
															</c:forEach>
														</c:when>
													</c:choose>
												</c:forEach>

											</table>
											</td>
										</tr>
									</table>
									<a href="javascript:updateTargetPage('randomizeView');"><img
										src="images/b-edit.gif" alt="Edit" width="39" height="16"
										border="0"></a>

									<hr align="left" width="95%">
									<br>

									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr align="center">
											<td colspan=2 valign="top"><br>
											<br>
											<a href="javascript:updateTargetPage('confirmationView')"><img
												src="images/b-submit.gif" alt="Continue" width="59"
												height="16" border="0"></a> <a href="/c3pr/"
												onClick="add();return false;"><img
												src="images/b-startOver.gif" alt="Start Over" width="67"
												height="16" border="0"></a></td>
										</tr>
									</table>
									</div>
									</td>

									<!-- LEFT CONTENT ENDS HERE -->
								</tr>
							</table>
							</td>
						</tr>
					</form:form>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<div id="copyright"></div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
