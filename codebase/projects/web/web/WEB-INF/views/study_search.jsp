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

		<td width="99%"><img src="images/C3PRLogo.gif" alt="C3Pr V2"
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
			src="images/topNavL.gif" width="2" height="20" align="absmiddle"
			class="currentL"><span class="current"><img
			src="images/topNavArrowDown.gif" width="5" height="20"
			align="absmiddle"> Protocol </span><img src="images/topNavR.gif"
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
		<td width="99%" valign="middle"><img src="images/arrowRight.gif"
			width="3" height="5" align="absmiddle"> Protocol Management <img
			src="images/spacer.gif" width="1" height="20" align="absmiddle"
			class="spacer"><a href="protocol_add.htm">Add Protocol</a></td>
		<td valign="middle" class="right"><a href="help">Help</a></td>
	</tr>
</table>
<!-- SUB NAV ENDS HERE -->
<!-- MAIN BODY STARTS HERE -->

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

		<td id="current">Search</td>
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
							align="absmiddle"> Protocol Search <img
							src="images/tabWhiteR.gif" width="3" height="16"
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
			<tr>

				<!-- LEFT CONTENT STARTS HERE -->

				<td valign="top" class="additionals">

				<!-- LEFT FORM STARTS HERE -->
				<form:form method="post">

				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr valign="top">
						<td><img src="images/Protocol.gif" alt="Protocol Search"
							width="100" height="100" align="absmiddle\"></td>
						<td width="99%">
						<h3>Study Search (select any)</h3>
						<br>
							<table border="0" cellspacing="0" cellpadding="0" id="search">
								<tr>
									<td align="left" class="labels">Search String</td>
									<td align="left" class="labels"  colspan="10"></td>
									<td align="left" class="labels">Search Studies by: </td>
									<td align="left" class="labels"></td>
									<td align="left" class="labels">Study Id:</td>
									<td align="left" class="labels" ></td>
									<td align="left" class="labels" >Short Title Text</td>														
								</tr>
								<tr>
									<td><form:input path="searchTypeText" /></td>
									<td align="left" class="labels"  colspan="10"></td>
									<td align="left" class="labels">
									<form:select path="searchType">
										<form:options items="${searchType}" itemLabel="desc" itemValue="code" />
									</form:select></td>
									<td align="left" class="labels">									
									<td><form:input path="studyid" /></td>
									<td align="left" class="labels">
									<td><form:input path="shortTitleText" /></td>																		
								</tr>
								<tr>
									<td align="left" class="labels"  colspan="10">^ Minimum two characters</td><br>
								</tr>
							</table>
						<table border="0" cellspacing="0" cellpadding="0" id="search">
							<tr>
								<tr>
									<td align="left" class="labels"  colspan="10"></td><br>
								</tr>
								<td align="center" colspan="20"><!-- action buttons begins -->
								<tr>
									<td><input class="actionButton" type="submit"
										value="search study"></td>
								</tr>						
						</table>
				<!-- LEFT FORM ENDS HERE --></td>
			</tr>
		</table>
		</form:form>
				
		</td>
	</tr>
</table>
<div id="copyright">&copy; 2006 Semanticbits.com. All Rights
Reserved</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
