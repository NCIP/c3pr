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
function getPage(s){
	parent.window.location="reg_patient_search.htm";
}
function add(){
var action = confirm ("You have not completed adding this protocol.\r\rStarting over will lose current protocol data?")
if (action){
	parent.window.location="reg_enroll_patient.htm";
}}
function validatePage(){
	return true;
}
function clearField(field){
field.value="";
}
function updateTargetPage(action, selected,s){
	if(validatePage()){
		document.IdentifierForm._action.value=action;
		document.IdentifierForm._selected.value=selected;
		document.getElementById("nextView").value=s;
		document.IdentifierForm.submit();

	}
}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="99%"><img src="images/c3prLogo.gif" alt="C3PR"
			width="181" height="36" class="mainlogo"></td>


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
							align="absmiddle"> 1. Select Study <img
							src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"><img src="images/tabGrayL.gif" width="3"
							height="16" align="absmiddle"> 2. Select Subject <img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						3. Enrollment Details <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						4. Check Eligibility <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						5. Stratify <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"><img src="images/tabGrayL.gif" width="3"
							height="16" align="absmiddle"> 6. Review and Submit <img
							src="images/tabGrayR.gif" width="3" height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						7. Confirmation <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						8. Randomize <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"><span class="current"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						9. Identifiers <img src="images/tabGrayR.gif" width="3"
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
				<div class="workArea"><img src="images/tabWhiteL.gif"
					width="3" height="16" align="absmiddle"> <img
					src="images/tabWhiteL.gif" width="3" height="16" align="absmiddle">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<form:form name="IdentifierForm" method="post">
						<input type="hidden" name="_action" value="">
						<input type="hidden" name="_selected" value="">
						<input type="hidden" name="nextView" value="">
						<tr>
							<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

							<td id="current">Identifiers for
							${command.participant.firstName} ${command.participant.lastName}
							on ${command.studySite.study.trimmedShortTitleText}</td>
							<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
						</tr>
						<tr>

							<td class="display"><!-- TABS LEFT START HERE -->
							<table width="70%" border="0" cellspacing="0" cellpadding="0"
								id="details">
								<tr>
									<td width="100%" valign="top">
									<table width="50%" border="0" cellspacing="10" cellpadding="0"
										id="table1">

										<tr align="center" class="label">
											<td width="20%" align="center">Source<span class="red">*</span></td>
											<td width="20%" align="center">Identifier Type<span
												class="red">*</span></td>
											<td width="20%" align="center">Identifier<span
												class="red">*</span></td>
											<td width="25%" align="center">Primary Indicator</td>
											<td>&nbsp;</td>
										</tr>
										<c:forEach items="${command.identifiers}" varStatus="status">
											<tr align="center" class="results">
												<td width="20%"><form:select
													path="identifiers[${status.index}].source">
													<option value="">--Please Select-- <form:options
														items="${identifiersSourceRefData}" itemLabel="desc"
														itemValue="code" />
												</form:select></td>
												<td width="20%"><form:select
													path="identifiers[${status.index}].type">
													<option value="">--Please Select-- <form:options
														items="${identifiersTypeRefData}" itemLabel="desc"
														itemValue="code" />
												</form:select></td>
												<td width="20%"><form:input
													path="identifiers[${status.index}].value"
													onclick="javascript:clearField(this)();" /></td>
												<td width="25%" aligh="center"><form:radiobutton
													path="identifiers[${status.index}].primaryIndicator" /></td>
												<td width="10%"><a
													href="javascript:updateTargetPage('removeIdentifier',${status.index},'identifiersView');"><img
													src="images/b-delete.gif" border="0"></a></td>
											</tr>
										</c:forEach>
										<tr align="center" class="label">
											<td colspan='4' width="10%" align="center"><a
												href="javascript:updateTargetPage('addIdentifier','0','identifiersView');"><img
												src="images/b-addLine.gif" border="0"
												alt="Add another Identifier"></a></td>
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
											<td colspan=2 valign="top"><br>
											<br>
											<a href=""
												onClick="updateTargetPage('none','none','processFinish');return false;"><img
												src="images/b-update.gif" alt="Save" width="59" height="16"
												border="0"></a> <a href="/c3pr/SearchAndRegister.do"><img
												src="images/b-cancel.gif" alt="Start Over" width="67"
												height="16" border="0"></a></td>
										</tr>
									</table>
									</td>
								</tr>
							</table>

							</td>

						</tr>
					</form:form>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<div id="copyright"></div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
