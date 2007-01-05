
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>C3Pr V2</title>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link href="css/search.css" rel="stylesheet" type="text/css">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function getPage(s){
	parent.window.location="reg_patient_search.htm";
}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="99%"><img src="images/C3PRLogo.gif" alt="C3PR"
			width="181" height="36" class="gelogo"></td>
		<td align="right"><img src="images/t-drivers.gif" alt="Protocol"
			width="200" height="79"></td>
	</tr>
</table>
<!-- TOP LOGOS END HERE -->
<!-- TOP NAVIGATION STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="topNav">

	<tr valign="middle">
		<td width="99%" class="left"><a href="home.htm">Registration</a><img
			src="images/topDivider.gif" width="2" height="20" align="absmiddle"
			class="divider"><a href="protocol.htm">Protocol</a><img
			src="images/topNavL.gif" width="2" height="20" align="absmiddle"
			class="currentL"><span class="current"><img
			src="images/topNavArrowDown.gif" width="5" height="20"
			align="absmiddle"> Participant </span><img src="images/topNavR.gif"
			width="2" height="20" align="absmiddle" class="currentR"><a
			href="analysis">Reports</a><img src="images/topDivider.gif" width="2"
			height="20" align="absmiddle" class="divider"></td>
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
<div class="workArea">
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="titleArea">
	<tr>
		<!-- TITLE STARTS HERE -->
		<td width="99%" height="43" valign="middle" id="title">Add
		Participant</td>
	</tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

				<td id="current">Create Participant</td>
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
									src="images/tabGrayL.gif" width="3" height="16"
									align="absmiddle"> 1. Patient Information <img
									src="images/tabGrayR.gif" width="3" height="16"
									align="absmiddle"></span><span class="tab"><img
									src="images/tabGrayL.gif" width="3" height="16"
									align="absmiddle"> 2. Address Information <img
									src="images/tabGrayR.gif" width="3" height="16"
									align="absmiddle"><img src="images/tabGrayL.gif"
									width="3" height="16" align="absmiddle"> 3. Review and
								Submit <img src="images/tabGrayR.gif" width="3" height="16"
									align="absmiddle"></span></td>
								<td><img src="images/spacer.gif" width="7" height="1"></td>
							</tr>
							<tr>
								<td colspan="2" class="tabBotL"><img
									src="images/spacer.gif" width="1" height="7"></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>

						<!-- LEFT CONTENT STARTS HERE -->
						<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
						<!-- RIGHT CONTENT STARTS HERE -->

						<form:form method="post" action="createparticipant.do">
							<strong>Step 1. Patient Information </strong> 
							(<span class="red">*</span><em>Required Information </em>)<br> <br>
						<div class="review"><strong>Current Information:</strong><br>
						<br>
						<table width="700" border="0" cellspacing="0" cellpadding="0"
							id="details">
							<tr>
								<td width="50%" valign="top">
								<table width="308" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td>
											<img src="images/spacer.gif" width="1" height="1"
												class="heightControl">
										</td>
										<td width="65%">
											<img src="images/spacer.gif" width="1"
												height="1" class="heightControl">
										</td>
									</tr>
									<tr>
										<td class="label">
											<span class="red">*</span><em></em>
											First Name
										</td>
										<td>
											<form:input path="firstName"/>
										</td>
									</tr>
									<tr>
										<td class="label">
											<span class="red">*</span><em></em>
											Last Name
										</td>
										<td>
											<form:input path="lastName"/>
										</td>
									</tr>
									<tr>
										<td class="label">
											<span class="red">*</span> <em></em>
											Administritative Gender Code
										</td>
										<td>
											<form:select path="administritativeGenderCode">
												<form:options items="${administritativeGenderCode}" itemLabel="desc" itemValue="code" />
											</form:select>
										</td>										
									</tr>
								</table>
								</td>
								<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
								<table width="308" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td class="label"><span class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</span><em></em>Birth Date</td>
										<td valign="top"><form:input path="birthDate"/>&nbsp;<a href="#"
											onClick="parent.OpenWins('calendar.htm','calendar',200,236);return false;"><img
											src="images/b-calendar.gif" alt="Calendar" width="17"
											height="16" border="0" align="absmiddle"></a></td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em>Ethnic Group Code</td>
										<td>
											<form:select path="ethnicGroupCode">
												<form:options items="${ethnicGroupCode}" itemLabel="desc" itemValue="code"/>
											</form:select>										
										</td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em>Race Code</td>
										<td>
											<form:select path="raceCode">
												<form:options items="${raceCode}" itemLabel="desc" itemValue="code"/>
											</form:select>										
										</td>
									</tr>
									
								</table>
								</td>
							</tr>
						</table>
						
						<hr align="left" width="95%">
						<table width="700" border="0" cellspacing="0" cellpadding="0"
							id="table1">					
							<tr>
								<td align="center"><span class="red">*</span><em></em><B>Other
								ID:</td>
								<td align="center"><span class="red">*</span><em></em><B>Other
								Source:</td>
							</tr>
							<tr>
								<td>
								<c:forEach items="${participant.participantIdentifiers}" varStatus="status" var="participantIdentifier">
									<form:input path="participantIdentifiers[${status.index}].healthcareSite"/> 
								</c:forEach>
								</td>
								<td></td>																
							</tr>

						</table>

						<hr align="left" width="95%">
						<table width="700" border="0" cellspacing="0" cellpadding="0"
							id="details">
							<tr>
								<td align="center" colspan="2" valign="top"><br>
								<br>
								<input class="actionButton" type="submit"
														value="Create Partcipant" id="assign-button">
								<a href="createParticipant.do"><img
									src="images/b-continue.gif" alt="Continue" width="59"
									height="16" border="0"></a> <a href="home.htm"
									onClick="add();return false;"><img
									src="images/b-startOver.gif" alt="Start Over" width="67"
									height="16" border="0"></a></td>
							</tr>
						</table>
						</div>
						</form:form>
						</td>

						<!-- LEFT CONTENT ENDS HERE -->
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<br>
		</form>
		</td>
		<!-- LEFT CONTENT ENDS HERE -->
	</tr>
</table>

<div id="copyright">&copy; 2006 SemanticBits Company. All Rights
Reserved</div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>