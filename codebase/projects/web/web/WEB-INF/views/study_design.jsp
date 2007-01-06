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
<script language="javascript">
function add(){
var action = confirm ("You have not completed adding this protocol.\r\rStarting over will lose current protocol data?")
if (action){
	parent.window.location="protocol_add.htm";
}}
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
			href="/c3pr/searchparticipant.do">Participant</a><img
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
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						1. <a href="protocol_add.htm">study details</a> <img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						2. <a href="protocol_add2.htm">study site</a> <img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle"></span><span
							class="current"><img src="images/tabwhiteL.gif" width="3"
							height="16" align="absmiddle"> 3. study design <img
							src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
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
				<!-- RIGHT CONTENT STARTS HERE -->
				<c:url value="/createstudy.do" var="formAction" />
				<form:form method="post" action="${formAction}">
				<div>
					<input type = "hidden" name="_page" value="2">
				</div>

					<br>
					<strong>Step 3. Study Design - Epochs & Arms </strong>
					<br>
					<br>
					<table width="700" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
						<td class="label"> --- Epochs --- </td>
						</tr>
						<tr>
							<td class="label"><span class="red">*</span><em></em>Epoch
							Name:</td>
							<td><form:input path="epochs[0].name" /></td>
							<td class="label"><span class="red">*</span><em></em>Description:</td>
							<td><form:input path="epochs[0].descriptionText" /></td>
						</tr>
						<tr>
							<td><img src="images/spacer.gif" width="1" height="1"
								class="heightControl"></td>
							<td><img src="images/spacer.gif" width="1" height="1"
								class="heightControl"></td>
						</tr>
						<tr>
						<td class="label"> --- Arms --- </td>
						<tr>
							<td class="label"><span class="red">*</span><em></em>Arm Name
							Name:</td>
							<td><form:input path="epochs[0].arms[0].name" /></td>
							<td class="label"><span class="red">*</span><em></em>Target Accrual Number:</td>
							<td><form:input path="epochs[0].arms[0].targetAccrualNumber" /></td>
						</tr>
						<tr>
						<tr align="center">
							<td colspan="4"><br>
							<br>
							<tr>
							<td align="center" colspan="3"><!-- action buttons begins -->
							<table cellpadding="4" cellspacing="0" border="0">
								<tr>
									<td><input class="actionButton" type="submit"
										name="_target1" value="Prev"></td>
									<td><input class="actionButton" type="submit"
										name="_target3" value="Next"></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</form:form></td>
				<!-- LEFT CONTENT ENDS HERE -->
			</tr>
		</table>
		</td>
	</tr>
</table>
<div id="copyright">&copy; 2006 SemanticBits. All Rights Reserved</div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
