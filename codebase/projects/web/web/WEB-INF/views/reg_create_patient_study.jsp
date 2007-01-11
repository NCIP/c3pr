<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
function doNothing(){
}
function getPage(s){
	parent.window.location="reg_patient_search.htm";
}
function submitPage(){
	document.getElementById("searchParticipant").submit();
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
			href="/c3pr/searchstudy.do"> Study </a><img src="images/topNavR.gif"
			width="2" height="20" align="absmiddle" class="currentR"><a
			href="searchparticipant.do">Subject</a><img
			src="images/topDivider.gif" width="2" height="20" align="absmiddle"
			class="divider"><a href="javascript:doNothing();">Reports</a><img
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
							align="absmiddle"> 1. <a href="reg_protocol_search.htm">Select
						Study </a><img src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span><span class="current"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						2. Select Subject <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						3. Check Eligibility <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						4. Stratify <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"><img src="images/tabGrayL.gif" width="3"
							height="16" align="absmiddle"> 5. Randomize <img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						6. Review and Submit <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span></td>
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

						<td id="current">Search Subject</td>
						<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
					</tr>
					<form:form id="searchParticipant" name="searchParticipant"
						action="/c3pr/SearchRegisterPatient.do?studySiteId=58"
						method="post">

						<tr>
							<td class="display"><!-- TABS LEFT START HERE -->
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="search">
								<tr>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;</td>
									<td class="searchType">Search Subject by <select
										name="searchType">
										<c:forEach var="opt" items="${searchType}">
											<option value="${opt.code }">${opt.desc }</option>
										</c:forEach>
									</select></td>
									<td align="left" class="labels">Search String:</td>
									<td><input type="text" name="searchText" /></td>
									<td><input name="imageField" type="image" class="button"
										onClick="submitPage()" src="images/b-go.gif" alt="GO"
										align="middle" width="22" height="10" border="0"></td>
									<td width=65%>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
							</table>
							</td>
						</tr>
					</form:form>
				</table>
				</td>
			</tr>

			<tr>
				<td>
				<div class="workArea"><img src="images/tabWhiteL.gif"
					width="3" height="16" align="absmiddle"> <img
					src="images/tabWhiteL.gif" width="3" height="16" align="absmiddle">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

						<td id="current">Create Subject</td>
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
											align="absmiddle"> 1. Subject Information <img
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
								<!-- RIGHT CONTENT STARTS HERE --> <form:form method="post"
									action="createparticipant.do">
									<div><input type="hidden" name="_page" value="0">
									</div>
									<strong>Step 1. Subject Information </strong> 
							(<span class="red">*</span>
									<em>Required Information </em>)<br>
									<br>
									<div class="review"><strong>Current Information:</strong>

									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr>
											<td width="50%" valign="top">
											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
												<tr>
													<td><img src="images/spacer.gif" width="1" height="1"
														class="heightControl"></td>
													<td width="65%"><img src="images/spacer.gif" width="1"
														height="1" class="heightControl"></td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>
													First Name</td>
													<td><form:input path="firstName" /></td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>
													Last Name</td>
													<td><form:input path="lastName" /></td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span> <em></em>
													Administrative Gender Code</td>
													<td><form:select path="administrativeGenderCode">
														<form:options items="${administrativeGenderCode}"
															itemLabel="desc" itemValue="code" />
													</form:select></td>
												</tr>
											</table>
											</td>
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
													<td class="label"><span class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</span><em></em>Birth
													Date</td>
													<td valign="top"><form:input path="birthDate" />&nbsp;<a
														href="#"
														onClick="parent.OpenWins('calendar.htm','calendar',200,236);return false;"><img
														src="images/b-calendar.gif" alt="Calendar" width="17"
														height="16" border="0" align="absmiddle"></a></td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>Ethnic
													Group Code</td>
													<td><form:select path="ethnicGroupCode">
														<form:options items="${ethnicGroupCode}" itemLabel="desc"
															itemValue="code" />
													</form:select></td>
												</tr>
												<tr>
													<td class="label"><span class="red">*</span><em></em>Race
													Code</td>
													<td><form:select path="raceCode">
														<form:options items="${raceCode}" itemLabel="desc"
															itemValue="code" />
													</form:select></td>
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
											Type:</td>
											<td align="center"><span class="red">*</span><em></em><B>Other
											Value:</td>
											<td align="center"><span class="red">*</span><em></em><B>Other
											Source:</td>
											<td align="center"><B>Is Primary:</td>
										</tr>

										<c:forEach var="index" begin="0" end="4">
											<tr>
												<td align="center"><form:input
													path="identifiers[${index}].type" /></td>
												<td align="center"><form:input
													path="identifiers[${index}].value" /></td>
												<td align="center"><form:select
													path="identifiers[${index}].source">
													<form:options items="${source}" itemLabel="desc"
														itemValue="code" />
												</form:select></td>

												<td align="center"><form:radiobutton
													path="identifiers[${index}].primaryIndicator" /></td>
											</tr>

										</c:forEach>

									</table>

									<hr align="left" width="95%">
									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr align="center">
											<td colspan="4"><br>
											<br>
										<tr>
											<td align="center" colspan="3"><!-- action buttons begins -->
											<table cellpadding="4" cellspacing="0" border="0">
												<tr>
													<td><input class="actionButton" type="submit"
														name="_target1" value="Next"></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</div>
								</form:form></td>

								<!-- LEFT CONTENT ENDS HERE -->
							</tr>
						</table>

						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<div id="copyright">&copy; 2006 SemanticBits Company. All Rights
		Reserved</div>
		</div>
		<!-- MAIN BODY ENDS HERE -->
</body>
</html>
