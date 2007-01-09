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

		<td width="99%"><img src="images/c3prLogo.gif" alt="C3PR" width="181"
			height="36" class="gelogo"></td>
	</tr>
</table>
<!-- TOP LOGOS END HERE -->
<!-- TOP NAVIGATION STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="topNav">

	<tr valign="middle">
		<td width="99%" class="left"><a href="home.htm">Registration</a><img
			src="images/topDivider.gif" width="2" height="20" align="absmiddle"
			class="divider"><a href="protocol.htm">Study</a><img
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
		<td width="99%" valign="middle"><img src="images/arrowRight.gif"
			width="3" height="5" align="absmiddle"> Participant Management <img
			src="images/spacer.gif" width="1" height="20" align="absmiddle"
			class="spacer"><a href="createparticipant.do">Add Participant</a><img
			src="images/spacer.gif" width="1" height="20" align="absmiddle"
			class="spacer"><a href="#">Add Tab</a></td>
		<td valign="middle" class="right"><a href="help">Help</a></td>
	</tr>
</table>
<!-- SUB NAV ENDS HERE -->
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

		<td id="current">Participant Search Results</td>
		<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
	</tr>
	<tr>

		<td class="display"><!-- TABS LEFT START HERE -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>

				<!-- LEFT CONTENT STARTS HERE -->

				<td valign="top" class="additionals"><!-- LEFT FORM STARTS HERE -->


				<br>
				<br>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="additionalList">
					<tr align="center" class="label">
						<td>Last Name, First MI</td>
						<td>Primary Id</td>
						<td>Gender</td>
						<td>Race</td>
						<td>Brith Date</td>
						<td></td>
					</tr>
					<c:forEach var="participant" items="${participants}">
						<a href="participantresults.do?participantId=${participant.id}"
							onMouseOver="navRollOver('row1', 'on')"
							onMouseOut="navRollOver('row1', 'off')">
						<tr align="center" id="row1" class="results">
							<td>${participant.lastName},${participant.firstName}</td>
							<td>${participant.id}</td>
							<td>${participant.administrativeGenderCode}</td>
							<td>${participant.raceCode}</td>
							<td>${participant.birthDate}</td>
						</tr>
						
					</c:forEach>
				</table>
				<br>
				<!-- LEFT FORM ENDS HERE --></td>
				<!-- LEFT CONTENT ENDS HERE -->
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
