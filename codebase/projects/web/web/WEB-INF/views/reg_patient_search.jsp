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
		<td width="99%" class="left"><img src="images/topNavL.gif"
			width="2" height="20" align="absmiddle" class="currentL"><span
			class="current"><img src="images/topNavArrowDown.gif"
			width="5" height="20" align="absmiddle"> Registration</span><a
			href="protocol.htm"> Protocol </a><img src="images/topNavR.gif"
			width="2" height="20" align="absmiddle" class="currentR"><a
			href="participant.htm">Participant</a><img
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
							align="absmiddle"> 1. <a href="reg_protocol_search.htm">Select
						Protocol </a><img src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span><span class="current"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						2. Enroll Patient <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						3. Stratify <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"><img src="images/tabGrayL.gif" width="3"
							height="16" align="absmiddle"> 3. Randomize <img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						4. Review and Submit <img src="images/tabGrayR.gif" width="3"
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
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="titleArea">
					<tr>
						<!-- TITLE STARTS HERE -->
						<td width="99%" height="43" valign="middle" id="title">Patient
						Search</td>
						<td valign="top">
						<form method="post" action="" name="searchMeth" class="search">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="search">
							<tr>
								<td class="labels">&nbsp;</td>
							</tr>
							<tr>
								<td class="searchType">Search <select name="select"
									class="field1">
									<option selected>Patient</option>
								</select> by <select name="select" class="field1">
									<option selected>Patient Name</option>
									<option>Patient Registration#</option>
								</select></td>
							</tr>
						</table>
						</form>
						<span class="notation">&nbsp;</span></td>
						<td valign="top">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="search">
							<tr>
								<td align="left" class="labels">Last Name:</td>
								<td align="left" class="labels">First Name:</td>
								<td class="labels">&nbsp;</td>
							</tr>
							<tr>
								<td><input name="textfield2" type="text" class="field1"
									size="25"></td>
								<td><input name="textfield3" type="text" class="field1"
									size="25"></td>
								<td><input name="imageField" type="image" class="button"
									onClick="getPage('search.htm')" src="images/b-go.gif" alt="GO"
									align="middle" width="22" height="10" border="0"></td>
							</tr>
						</table>
						<span class="notation">^ Minimum two characters for Last
						Name search.</span></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

						<td id="current">Patient Search Results</td>
						<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
					</tr>
					<tr>

						<td class="display"><!-- TABS LEFT START HERE -->
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>

								<!-- LEFT CONTENT STARTS HERE -->

								<td valign="top" class="additionals"><!-- LEFT FORM STARTS HERE -->


								<br>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="30%" align="right"><a href="#"><img
											src="images/b-prev.gif" alt="Previous" width="41" height="16"
											border="0" align="absmiddle"></a></td>
										<td width="40%" align="center"><strong>Showing
										1-20 of 100</strong> | Page&nbsp;&nbsp; <a href="#">&laquo;</a>&nbsp;&nbsp;<strong>1</strong>-<a
											href="#">2</a>-<a href="#">3</a>-<a href="#">4</a>-<a
											href="#">5</a>-<a href="#">6</a>-<a href="#">7</a>-<a
											href="#">8</a>-<a href="#">9</a>-<a href="#">10</a>&nbsp;&nbsp;&nbsp;<a
											href="#">&raquo;</a></td>
										<td width="30%"><a href="#"><img
											src="images/b-next.gif" alt="Next" width="41" height="16"
											border="0" align="absmiddle"></a></td>
									</tr>
								</table>
								<br>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									id="additionalList">
									<tr align="center" class="label">
										<td>Last Name, First MI</td>
										<td>Primary Id</td>
										<td>Gender</td>
										<td>Brith Date</td>
										<td>First Visit<br>
										Date</td>
										<td></td>
									</tr>
									<%
									int i = 1;
									%>
									<c:forEach var="participant" items="${participants}">
										<a
											href="register.do?participantId=${participant.id}&studySiteId=${studySiteId}"
											onMouseOver="navRollOver('row<%= i %>', 'on')"
											onMouseOut="navRollOver('row<%= i %>', 'off')">
										<tr align="center" id="row<%= i++ %>" class="results">
											<td>${participant.lastName},${participant.firstName}</td>
											<td>${participant.id}</td>
											<td>${participant.administrativeGenderCode}</td>
											<td>${participant.raceCode}</td>
											<td>${participant.birthDate}</td>
										</tr>
										</a>
									</c:forEach>
								</table>
								<br>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="30%" align="right"><a href="#"><img
											src="images/b-prev.gif" alt="Previous" width="41" height="16"
											border="0" align="absmiddle"></a></td>
										<td width="40%" align="center"><strong>Showing
										1-20 of 100</strong> | Page&nbsp;&nbsp; <a href="#">&laquo;</a>&nbsp;&nbsp;<strong>1</strong>-<a
											href="#">2</a>-<a href="#">3</a>-<a href="#">4</a>-<a
											href="#">5</a>-<a href="#">6</a>-<a href="#">7</a>-<a
											href="#">8</a>-<a href="#">9</a>-<a href="#">10</a>&nbsp;&nbsp;&nbsp;<a
											href="#">&raquo;</a></td>
										<td width="30%"><a href="#"><img
											src="images/b-next.gif" alt="Next" width="41" height="16"
											border="0" align="absmiddle"></a></td>
									</tr>
								</table>
								<!-- LEFT FORM ENDS HERE --></td>
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
</table>
<div id="copyright">&copy; 2006 SemanticBits Company. All Rights
Reserved</div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
