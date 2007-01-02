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
<script language="javascript" type="text/JavaScript">
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
<!-- TOP NAVIGATION ENDS HERE -->
<!-- SUB NAV STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="subNav">

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
		<td class="display"><!-- TABS LEFT START HERE -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						1. <a href="protocol_add.htm">Study Site</a> <img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle"></span><span
							class="current"><img src="images/tabWhiteL.gif" width="3"
							height="16" align="absmiddle"> 2. Study <img
							src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						3. Study Design <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"><img
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
			<tr>

				<!-- LEFT CONTENT STARTS HERE -->

				<td valign="top" class="additionals2">
			
				<!-- RIGHT CONTENT STARTS HERE --> 
				<c:url value="/createstudy.do" var="formAction" />
				<form:form method="post" action="${formAction}">

				<div>
					<input type = "hidden" name="_page" value="1">
					<input type = "hidden" name="_target" value="2">				
				</div>	
				<table width="222" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td width="70" class="label"><strong>&nbsp;Site
							Name</strong>:</td>
							<td width="45">CALGB</td>
							<td width="57" class="label"><strong>&nbsp;</strong>Site Id:</td>
							<td width="68">0034XYZ</td>
						</tr>
					</table>
					<hr>
					<br>
					<strong>Step 2. Study Details </strong> (<span class="red">*</span>
					<em>Required Information </em>)<br>
					<br>

					<table width="700" border="0" cellspacing="0" cellpadding="0"
						id="details">
						<tr>
							<td width="50%" valign="top">
							<table width="308" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td class="label"><span class="red">*</span><em></em>Short
									Title:</td>
									<td><form:textarea path="study.shortTitleText" rows="2" cols="15" />
									</td>
									<!--<td><input name="driverfield1" type="text" size="34"></td>-->
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Description
									Text:</td>
									<td><form:textarea path="studysite.irbApprovalDate" rows="3" cols="20" />
									</td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Long
									Title:</td>
									<td><form:textarea path="study.longTitleText" rows="5" cols="40" />
									</td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Status:</td>
									<td><form:select path="study.status">
										<form:options items="${status}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em><strong>Disease
									Code:</strong>
									<td><form:select path="study.diseaseCode">
										<form:options items="${diseaseCode}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
								</tr>
								<tr>
									<td class="label"><em></em><span class="red">*</span><em></em>Monitor
									Code:</td>
									<td><form:select path="study.monitorCode">
										<form:options items="${monitorCode}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Phase
									Code:</td>
									<td><form:select path="study.phaseCode">
										<form:options items="${phaseCode}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Sponsor
									Code:</td>
									<td><form:select path="study.sponsorCode">
										<form:options items="${sponsorCode}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
								</tr>
							</table>
							</td>
							<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
							<table width="308" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td width="46%" class="label"><span class="red">*</span><em></em>NCI
									Identifier:</td>
									<td width="54%"><input name="driverfield1" type="text"
										size="34"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Randomized
									Indicator</td>
									<td><form:select path="study.randomizedIndicator">
										<form:options items="${randomizedIndicator}" itemLabel="str"
											itemValue="str" />
									</form:select></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Multi
									Institution:</td>
									<td><form:select path="study.multiInstitutionIndicator">
										<form:options items="${multiInstitutionIndicator}"
											itemLabel="str" itemValue="str" />
									</form:select></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Blinded
									Indicator:</td>
									<td><form:select path="study.blindedIndicator">
										<form:options items="${blindedIndicator}" itemLabel="str"
											itemValue="str" />
									</form:select></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl "></td>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Precis
									Text:</td>
									<td><form:textarea path="study.precisText" rows="2" cols="15" />
									</td>
								</tr>

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
							</td>
							</tr>
					</table>
					</form:form>
					
					<strong>Precis Text:</strong> (1000 character limit)<br>
					<table width="550" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><textarea name="textfield" class="notesField">Precis Text appear here.</textarea></td>
						</tr>
					</table>
					<strong>Description Text:</strong> (1000 character limit)<br>
					<table width="550" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><textarea name="textfield" class="notesField">Description Text appear here.</textarea></td>
						</tr>
					</table>

					<br>
					<table width="700" border="0" cellspacing="0" cellpadding="0"
						id="details">
						<tr align="center">
							<td colspan="2" valign="top"><br>
							<br>
							<a href="protocol_add3.htm"><img src="images/b-continue.gif"
								alt="Continue" width="59" height="16" border="0"></a> <a
								href="protocol_add.htm.htm" onClick="add();return false;"><img
								src="images/b-startOver.gif" alt="Start Over" width="67"
								height="16" border="0"></a></td>
						</tr>
					</table>
					</td>
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
