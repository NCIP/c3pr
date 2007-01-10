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
		<td width="99%"><img src="images/c3prLogo.gif" alt="C3Pr V2"
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
		<td width="99%" height="43" valign="middle" id="title">Add Study</td>

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
			<tr>
				<c:url value="/createstudy.do" var="formAction" />
				<form:form name="searchDetailsForm" method="post">
					<div><input type="hidden" name="_page" value="2">
					<td class="display"><!-- TABS LEFT START HERE -->
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								class="tabs">
								<tr>
									<td width="100%" id="tabDisplay"><span class="current"><img
										src="images/tabWhiteL.gif" width="3" height="16"
										align="absmiddle"> Epochs & Arms<img
										src="images/tabWhiteR.gif" width="3" height="16"
										align="absmiddle"></span></td>

								</tr>
								<tr>
									<td colspan="2" class="tabBotL"><img
										src="images/spacer.gif" width="1" height="7"></td>
								</tr>
							</table>
							<!-- TABS LEFT END HERE --></td>

						</tr>
						<tr>
							<td valign="top" class="contentL"><!-- LEFT CONTENT STARTS HERE -->

							<table width="60%" border="0" cellspacing="0" cellpadding="0"
								id="additionalList">
								<tr>
									<td class="label"><span class="red">*</span><em></em>select:</td>
									<td class="label"><span class="red">*</span><em></em>Epoch
									Name:</td>
									<td class="label"><span class="red">*</span><em></em>Description:</td>
									<td class="label"><span class="red">*</span><em></em>Arms:</td>
								</tr>
								<tr>
									<td><form:checkbox path="epochs[0].id" value="id" /></td>
									<td><form:input path="epochs[0].name" /></td>
									<td><form:input path="epochs[0].descriptionText" /></td>
								</tr>
								<tr>
									<td class="label"></td>
									<td class="label"></td>
									<td class="label"></td>
									<td><form:input path="epochs[0].arms[0].name" /></td>
									<td><form:input
										path="epochs[0].arms[0].targetAccrualNumber" /></td>
								</tr>
								<tr>
									<td><form:checkbox path="epochs[1].id" value="id" /></td>
									<td><form:input path="epochs[1].name" /></td>
									<td><form:input path="epochs[1].descriptionText" /></td>
								</tr>
								<tr>
									<td class="label"></td>
									<td class="label"></td>
									<td class="label"></td>
									<td><form:input path="epochs[1].arms[0].name" /></td>
									<td><form:input
										path="epochs[1].arms[0].targetAccrualNumber" /></td>
								</tr>
								<tr>
									<td class="label"></td>
									<td class="label"></td>
									<td class="label"></td>
									<td><form:input path="epochs[1].arms[1].name" /></td>
									<td><form:input
										path="epochs[1].arms[1].targetAccrualNumber" /></td>
								</tr>

								<tr>
									<td class="label"></td>
									<td class="label"></td>
									<td class="label"></td>
									<td><form:input path="epochs[1].arms[2].name" /></td>
									<td><form:input
										path="epochs[1].arms[2].targetAccrualNumber" /></td>
								</tr>
								<tr>
									<td><form:checkbox path="epochs[2].id" value="id" /></td>
									<td><form:input path="epochs[2].name" /></td>
									<td><form:input path="epochs[2].descriptionText" /></td>
								</tr>
								<tr>
									<td class="label">
									<td class="label"></td>
									<td class="label"></td>
									<td><form:input path="epochs[2].arms[0].name" /></td>
									<td><form:input
										path="epochs[2].arms[0].targetAccrualNumber" /></td>
								</tr>
							</table>
						<tr align="center">
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
						</tr>
					</table>
				</form:form>

				<div id="copyright">&copy; 2006 SemanticBits. All Rights
				Reserved</div>

				<!-- MAIN BODY ENDS HERE -->
</body>
</html>
