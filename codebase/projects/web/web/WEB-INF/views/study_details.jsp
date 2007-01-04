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
						<td width="100%" id="tabDisplay"><span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16" align="absmiddle">
							<a href="protocol_add.htm">1.study details</a> <img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle"></span>
							<span class="tab"><img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"> 2.study site <img
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

				<td valign="top" class="additionals2"><!-- RIGHT CONTENT STARTS HERE -->
				<c:url value="/createstudy.do" var="formAction" /> <form:form
					method="post" action="${formAction}">

					<div><input type="hidden" name="_page" value="0"> 
					<!-- input type = "hidden" name="_target" value="1"-->
					</div>

					<br>
					<strong>Step 1. Study Details </strong> (<span class="red">*</span>
					<em>Required Information </em>)<br>
					<br>

					<table width="700" border="0" cellspacing="0" cellpadding="0"
						id="details">
						<tr>
							<td width="50%" valign="top">
							<table width="308" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td class="label"><span class="red">*</span><em></em>Status:</td>
									<td><form:select path="status">
										<form:options items="${status}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em><strong>Disease
									Code:</strong>
									<td><form:select path="diseaseCode">
										<form:options items="${diseaseCode}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><em></em><span class="red">*</span><em></em>Monitor
									Code:</td>
									<td><form:select path="monitorCode">
										<form:options items="${monitorCode}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Phase
									Code:</td>
									<td><form:select path="phaseCode">
										<form:options items="${phaseCode}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Sponsor
									Code:</td>
									<td><form:select path="sponsorCode">
										<form:options items="${sponsorCode}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td width="46%" class="label"><span class="red">*</span><em></em>NCI
									Identifier:</td>
									<td width="54%"><form:input path="nciIdentifier"
										size="34" /></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Randomized
									Indicator</td>
									<td><form:select path="randomizedIndicator">
										<form:options items="${randomizedIndicator}" itemLabel="str"
											itemValue="str" />
									</form:select></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Multi
									Institution:</td>
									<td><form:select path="multiInstitutionIndicator">
										<form:options items="${multiInstitutionIndicator}"
											itemLabel="str" itemValue="str" />
									</form:select></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Blinded
									Indicator:</td>
									<td><form:select path="blindedIndicator">
										<form:options items="${blindedIndicator}" itemLabel="str"
											itemValue="str" />
									</form:select></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
									<td class="label"><span class="red">*</span><em></em>Sponsor
									Code:</td>
									<td><form:select path="type">
										<form:options items="${type}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>								
							</table>
							</td>

							<td width="50%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
							<table width="308" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td class="label"><span class="red">*</span><em></em>Precis
									Text:</td>
									<td><form:textarea path="precisText" rows="2"
										cols="50" /></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Short
									Title:</td>
									<td><form:textarea path="shortTitleText" rows="2"
										cols="50" /></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Description
									Text:</td>
									<td><form:textarea path="descriptionText" rows="3"
										cols="50" /></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Long
									Title:</td>
									<td><form:textarea path="longTitleText" rows="5"
										cols="50" /></td>
								</tr>
							</table>
							</td>
						<tr>
							<td><img src="images/spacer.gif" width="1" height="1"
								class="heightControl"></td>
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
				</form:form> <!-- LEFT CONTENT ENDS HERE -->
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
