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
<script language="JavaScript" type="text/JavaScript">
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
<script language="JavaScript" type="text/JavaScript">

function validatePage(){
	if(document.getElementById("longTitleText") != null)
		return true;
	else
		return false;
}

function trim(s) {
   if (s.length >40)
   {
     s = s.substring(0,39);
     s +="...";
   }
   return s;

}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="99%"><img src="images/c3prLogo.gif" alt="C3Pr V2"
			width="181" height="36" class="mainlogo"></td>
	</tr>
</table>
<!-- TOP LOGOS END HERE -->
<!-- TOP NAVIGATION STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="topNav">
	<tr valign="middle">
		<td width="99%" class="left"><a href="/c3pr/SearchAndRegister.do">Registration</a><img
			src="images/topNavL.gif" width="2" height="20" align="absmiddle"
			class="currentL"><span class="current"><img
			src="images/topNavArrowDown.gif" width="5" height="20"
			align="absmiddle"> Study </span><img src="images/topNavR.gif"
			width="2" height="20" align="absmiddle" class="currentR"><a
			href="/c3pr/searchparticipant.do">Subject</a><img
			src="images/topDivider.gif" width="2" height="20" align="absmiddle"
			class="divider"><a href="">Reports</a><img
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
		<td valign="middle" class="right"><a href="">Help</a></td>
	</tr>

</table>
<!-- SUB NAV ENDS HERE -->
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="titleArea">
	<tr>
		<!-- TITLE STARTS HERE -->
		<td width="99%" height="43" valign="middle" id="title">Add Study</td>

	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<form:form name="searchDetailsForm" method="post" >
					
	<tr>
		<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->
		<td id="current">Short Title: ${command.trimmedShortTitleText}</td>
		<td>	</td>
		<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
	</tr>
	<tr>
		<td class="display"><!-- TABS LEFT START HERE -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16"
							align="absmiddle">
						1.study details <img src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span> <span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						2.identifiers <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						3.study site <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						4. study design <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						5. review and submit <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotL"><img src="images/spacer.gif"
							width="1" height="7"></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>

				<!-- LEFT CONTENT STARTS HERE -->

				<td valign="top" class="additionals2"><!-- RIGHT CONTENT STARTS HERE -->
					<div><input type="hidden" name="_page" value="0"></div>

					<br>
					<strong>Step 1. Study Details </strong> (<span class="red">*</span>
					<em>Required Information </em>)<br>
					<br>

					<table width="75%" border="0" cellspacing="0" cellpadding="0"
						id="details">
						<tr>
							<td width="50%" valign="top">

							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td class="label">Short Title:</td>
									<td><form:textarea path="shortTitleText" rows="2"
										cols="50" /></td>
									<td width="15%"></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Long Title:</td>
									<td><form:textarea path="longTitleText" rows="5" cols="50" /></td>
									<td width="15%"><em><span class="red"><form:errors path="longTitleText" /></em></span></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label">Precis Text:</td>
									<td><form:textarea path="precisText" rows="2" cols="50" /></td>
									<td width="15%"></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label">Description Text:</td>
									<td><form:textarea path="descriptionText" rows="3"
										cols="50" /></td>
									<td width="15%"></td>
								</tr>
							</table>
							</td>
							<td width="100%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
							<table width="80%" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td class="label">Target Accrual Number:</td>
									<td ><form:input path="targetAccrualNumber"
										size="34" /></td>
									<td width="10%"></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Status:</td>
									<td><form:select path="status">
										<option value="">--Please Select--
										<form:options items="${statusRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="10%"><em><span class="red"><form:errors path="status"/></em></span></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em><strong>Disease:</strong>
									<td><form:select path="diseaseCode">
										<option value="">--Please Select--
										<form:options items="${diseaseCodeRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="10%"><em><span class="red"><form:errors path="diseaseCode"/></em></span></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><em></em>Monitor:</td>
									<td><form:select path="monitorCode">
										<option value="">--Please Select--
										<form:options items="${monitorCodeRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="10%"></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Phase:</td>
									<td><form:select path="phaseCode">
										<option value="">--Please Select--
										<form:options items="${phaseCodeRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="10%"><em><span class="red"><form:errors path="phaseCode" /></em></span></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Sponsor:</td>
									<td><form:select path="sponsorCode">
										<option value="">--Please Select--
										<form:options items="${sponsorCodeRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="10%"><em><span class="red"><form:errors path="sponsorCode" /></em></span></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Randomized
									Indicator</td>
									<td><form:checkbox path="randomizedIndicator"/></td>
									<td width="10%"></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Multi
									Institution:</td>
									<td><form:checkbox path="multiInstitutionIndicator"/></td>
									<td width="15%"></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label">Blinded Indicator:</td>
									<td><form:checkbox path="blindedIndicator"/></td>
									<td width="15%"></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Type:</td>
									<td><form:select path="type">
										<option value="">--Please Select--
										<form:options items="${typeRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="15%"><em><span class="red"><form:errors path="type" /></em></span></td>
								</tr>

							</table>
							</td>
						<tr>
							<td align="center" colspan="3"><!-- action buttons begins -->
							<table cellpadding="4" cellspacing="0" border="0">
								<tr>
								<td colspan=2 valign="top"><br>
									<br>
									<input type="image" name="_target1" src="images/b-continue.gif" border="0"
										alt="continue to next page">
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</form:form> <!-- LEFT CONTENT ENDS HERE -->
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
