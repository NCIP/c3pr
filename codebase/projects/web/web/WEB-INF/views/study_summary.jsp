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
<script language="JavaScript" type="text/JavaScript">
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
<script language="JavaScript" type="text/JavaScript">
function OpenWins(target,name,width,height,scrolling){
	// I've used a var to refer to the opened window
	features = 'location=no,width='+width +',height='+height+',left=300,top=260,scrollbars='+scrolling;
	myWin = window.open(target,name,features);
}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">

	<tr>

		<td width="99%"><img src="images/C3PRLogo.gif" alt="C3Pr V2"
			width="181" height="36" class="gelogo"></td>
		<td align="right"><img src="images/t-drivers.gif" alt=""
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
			align="absmiddle"> Study </span><img src="images/topNavR.gif"
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

<!-- SUB NAV ENDS HERE -->
<!-- MAIN BODY STARTS HERE -->

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->
		<td id="current"></td>
		<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
	</tr>
	<tr>
		<td class="display"><!-- TABS LEFT START HERE -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="30%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16"
							align="absmiddle"> Study Summary <img
							src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span></td>

					</tr>
					<tr>
						<td colspan="2" class="tabBotL"><img src="images/spacer.gif"
							width="1" height="7"></td>
					</tr>
				</table>
				<!-- TABS LEFT END HERE --></td>
				<td><img src="images/spacer.gif" width="2" height="1"></td>
				<td width="70%"><!-- TABS RIGHT START HERE -->
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16"
							align="absmiddle"> Study Design <img
							src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						<td><img src="images/spacer.gif" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotR"><img src="images/spacer.gif"
							width="1" height="7"></td>
					</tr>
				</table>
				<!-- TABS RIGHT END HERE --></td>
			</tr>
			<tr>
				<td valign="top" class="contentL"><!-- LEFT CONTENT STARTS HERE -->
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="table1">
					<tr valign="top">
						<td><img src="images/spacer.gif" width="1" height="1"
							class="heightControl"></td>
						<td width="75%"><img src="images/spacer.gif" width="1"
							height="1" class="heightControl"></td>
					</tr>
					<tr valign="top">
						<td><img src="images/spacer.gif" width="1" height="1"
							class="heightControl"></td>
						<td width="75%"><img src="images/spacer.gif" width="1"
							height="1" class="heightControl"></td>
					</tr>
					<tr valign="top">
						<td><img src="images/spacer.gif" width="1" height="1"
							class="heightControl"></td>
						<td width="75%"><img src="images/spacer.gif" width="1"
							height="1" class="heightControl"></td>
					</tr>
					<tr valign="top">
						<td class="label"><span class="red">*</span><em></em>Short
						Title:</td>
						<td>CALGB-XYZ</td>
					</tr>
					<tr valign="top">
						<td class="label"><span class="red">*</span><em></em>Long
						Title:</td>
						<td>1234567XYZ</td>
					</tr>
					<tr valign="top">
						<td class="label"><span class="red">*</span><em></em>Precis
						Text:</td>
						<td>1234567XYZ</td>
					</tr>
					<tr valign="top">
						<td class="label"><span class="red">*</span><em></em>Disease
						Code:</td>
						<td>D-126754</td>
					</tr>
					<tr>
						<td class="label"><span class="red">*</span><em></em>Status:</td>
						<td></td>
					</tr>
					<tr>
						<td class="label"><span class="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</span><em></em>NCI
						Identifier:</td>
						<td valign="top">nci-abc</td>
					</tr>
					<tr>
						<td class="label"><em></em>Sponsor Code:</td>
						<td valign="top">spn-ttt</td>
					</tr>
					<tr>
						<td class="label"><span class="red">*</span><em></em>Phase
						Code:</td>
						<td>p-rst</td>
					</tr>
					<tr>
						<td class="label"><span class="red">*</span><em></em>Monitor
						Code:</td>
						<td>p-rst</td>
					</tr>
					<tr>
						<td class="label"><span class="red">*</span><em></em>Blinded
						Indicator</td>
						<td>Not Reported</td>
					</tr>
					<tr>
						<td class="label"><em></em> Randomized Indicator</td>
						<td>name-contact</td>
					</tr>
					<tr>
						<td class="label"><em></em> Multi Institution Indicator</td>
						<td>name-contact</td>
					</tr>
					<tr>
						<td class="label"><em></em> Target Accrual Number</td>
						<td>123-123-1234</td>
					</tr>
				</table>
				<!-- LEFT CONTENT ENDS HERE --></td>
				<td><img src="images/spacer.gif" width="2" height="1"></td>
				<!-- RIGHT CONTENT STARTS HERE -->
				<td valign="top" class="contentR">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="details">
					<tr>
						<td width="50%" valign="top" class="contentAreaL"><strong>Epoch
						Details </strong><br>
						<br>
							<c:url value="/createstudy.do" var="formAction" />
							<form:form method="post" action="${formAction}">
							<div>
								<input type ="hidden" name="_page" value="3">
								<input type = "hidden" name="_finish" value="true">				
							</div>	

							<table width="308" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
									<td width="65%"><img src="images/spacer.gif" width="1"
										height="1" class="heightControl"></td>
								</tr>
								<tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>name
									:</td>
									<td><input name="driverfield1" type="text" size="34"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>description
									:</td>
									<td><input name="driverfield1" type="text" size="34"></td>
								</tr>

							</table>
						</form:form></td>

					</tr>
				</table>
				</td>
				<!-- RIGHT CONTENT ENDS HERE -->
			</tr>
		</table>
	</tr>
</table>
<div id="copyright">&copy; 2006 SemanticBits. All Rights Reserved</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
