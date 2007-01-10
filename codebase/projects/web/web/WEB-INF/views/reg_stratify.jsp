<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
function updateTargetPage(s){
	if(validatePage()){
		document.getElementById("nextView").value=s;
		document.getElementById("stratifyForm").submit();
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
		<td align="right"><img src="images/t-drivers.gif" alt="Study"
			width="200" height="79"></td>
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
	<form:form name="stratifyForm" method="post">
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
								align="absmiddle"> 1. Select
							Study <img src="images/tabWhiteR.gif" width="3" height="16"
								align="absmiddle"><img src="images/tabGrayL.gif" width="3"
								height="16" align="absmiddle"> 2. Select Subject <img
								src="images/tabGrayR.gif" width="3" height="16"
								align="absmiddle"><img src="images/tabGrayL.gif" width="3"
								height="16" align="absmiddle"> 3. <a
								href="javascript:updateTargetPage('checkEligibilityView');">Check
							Eligibility</a> <img src="images/tabGrayR.gif" width="3" height="16"
								align="absmiddle"></span><span class="current"><img
								src="images/tabGrayL.gif" width="3" height="16"
								align="absmiddle"> 4. Stratify <img
								src="images/tabGrayR.gif" width="3" height="16"
								align="absmiddle"></span><span class="tab"><img
								src="images/tabGrayL.gif" width="3" height="16"
								align="absmiddle"> 5. Randomize <img
								src="images/tabGrayR.gif" width="3" height="16"
								align="absmiddle"><img src="images/tabGrayL.gif" width="3"
								height="16" align="absmiddle"> 6. Review and Submit <img
								src="images/tabGrayR.gif" width="3" height="16"
								align="absmiddle"></span></td>
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
					<div class="workArea"><img src="images/tabWhiteL.gif"
						width="3" height="16" align="absmiddle"> <img
						src="images/tabWhiteL.gif" width="3" height="16" align="absmiddle">

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

							<td id="current">Stratification for 
							${command.participant.firstName} ${command.participant.lastName} on
							${command.studySite.study.shortTitleText}</td>
							<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
						</tr>
						<tr>

							<td class="display"><!-- TABS LEFT START HERE -->
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>

									<!-- LEFT CONTENT STARTS HERE -->
									<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
									<!-- RIGHT CONTENT STARTS HERE --> <input type="hidden"
										name="nextView">
									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr align="center">
											<td colspan=2 valign="top"><br>
											<br>
											<a href=""
												onClick="updateTargetPage('randomizeView');return false;"><img
												src="images/b-continue.gif" alt="Continue" width="59"
												height="16" border="0"></a> <a href=""><img
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

					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</form:form>
</table>
<div id="copyright">&copy; 2006 SemanticBits Company. All Rights
Reserved</div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
