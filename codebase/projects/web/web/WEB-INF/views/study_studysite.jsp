<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el"%>

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
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="99%"><img src="images/C3PRLogo.gif" alt="C3Pr V2"
			width="181" height="36" class="gelogo"></td>
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
		<td width="99%" height="43" valign="middle" id="title">Add
		Study</td>

	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<tr>
			<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->
			<td id="current">Site Name-Id: ${sites[0].site.name}</td>
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
						<td width="100%" id="tabDisplay"><span class="tab"><img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle">
						<a href="protocol_add.htm">1.study details</a> <img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle"></span>
						<span class="current"><img src="images/tabWhiteL.gif"
							width="3" height="16" align="absmiddle"> 2.study site <img
							src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						3. study design <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						4. review and submit <img src="images/tabGrayR.gif" width="3"
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
			<tr>

				<!-- LEFT CONTENT STARTS HERE -->

				<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
				<!-- RIGHT CONTENT STARTS HERE --> <c:url value="/createstudy.do"
					var="formAction" /> <form:form method="post"
					action="${formAction}">

					<div><input type="hidden" name="_page" value="1"> <!-- input type = "hidden" name="_target1" value="1"-->
					</div>
					<strong>Step 2. Study Site </strong> (<span class="red">*</span>
					<em>Required Information </em>)<br>

					<br>
					<table width="600" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td class="label"><span class="red">*</span><em></em>Choose HealthCare Site:</td>
							<td><form:select path="studySites[0].site">
								<form:options items="${healthCareSitesRefData}" itemLabel="name" itemValue="id" />
								</form:select></td>
						</tr>
						<tr>
							<td><img src="images/spacer.gif" width="1" height="3"
								class="heightControl"></td>
						</tr>						
						
						<tr>
							<td><img src="images/spacer.gif" width="1" height="3"
								class="heightControl"></td>
						</tr>
						<tr>
							<td class="label"><span class="red">*</span><em></em>Status
							Code:</td>
							<td><form:input path="studySites[0].statusCode" /></td>
						</tr>
							<tr>
							<td><img src="images/spacer.gif" width="1" height="3"
								class="heightControl"></td>
						</tr>
						<tr>
							<td class="label"><span class="red">*</span><em></em>Role
							Code:</td>
							<td><form:input path="studySites[0].roleCode" /></td>
						</tr>
						<tr>
							<td><img src="images/spacer.gif" width="1" height="3"
								class="heightControl"></td>
						</tr>
						<tr>
							<td class="label"><span class="red">*</span><em></em>Start Date:</td>
							<td valign="top" align="left"><form:input
								path="studySites[0].startDate" />&nbsp;<a href="#"
								onClick="parent.OpenWins('calendar.htm','calendar',200,236);return false;"><img
								src="images/b-calendar.gif" alt="Calendar" width="17"
								height="16" border="0" align="absmiddle"></a></td>
						</tr>
						<tr>
							<td class="label"><span class="red">*</span><em></em>End Date:</td>
							<td valign="top" align="left"><form:input
								path="studySites[0].endDate" />&nbsp;<a href="#"
								onClick="parent.OpenWins('calendar.htm','calendar',200,236);return false;"><img
								src="images/b-calendar.gif" alt="Calendar" width="17"
								height="16" border="0" align="absmiddle"></a></td>
						<tr>
						<tr>
							<td class="label" align="right"><span class="red">&nbsp;&nbsp;&nbsp;*</span><em></em>IRB
								Approval Date:</td>
							<td valign="top" align="left"><form:input
								path="studySites[0].irbApprovalDate" />&nbsp;<a href="#"
								onClick="parent.OpenWins('calendar.htm','calendar',200,236);return false;"><img
								src="images/b-calendar.gif" alt="Calendar" width="17"
								height="16" border="0" align="absmiddle"></a></td>
						</tr>
						<tr>
							<td><img src="images/spacer.gif" width="1" height="3"
								class="heightControl"></td>
						</tr>
						<tr>
							<td align="center" colspan="3"><!-- action buttons begins -->
							<table cellpadding="4" cellspacing="0" border="0">
								<tr>
									<td><input class="actionButton" type="submit"
										name="_target0" value="Prev"></td>
									<td><input class="actionButton" type="submit"
										name="_target2" value="Next"></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</form:form></td>
			</tr>
		</table>
		<br>
		</td>
		<!-- LEFT CONTENT ENDS HERE -->
	</tr>
</table>
<div id="copyright">&copy; 2006 SemanticBits. All Rights Reserved</div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
