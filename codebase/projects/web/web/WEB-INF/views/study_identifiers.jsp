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
<script language="JavaScript" type="text/JavaScript">
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
<script language="JavaScript" type="text/JavaScript">

function validatePage(){
	return true;
}
function fireAction(action, selected){
	if(validatePage()){
		document.studyIdentifiersForm._action.value=action;
		document.studyIdentifiersForm._selected.value=selected;
		document.studyIdentifiersForm.submit();
	}
}
function clearField(field){
field.value="";
}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="99%"><img src="images/c3prLogo.gif" alt="C3Pr V2"
			width="181" height="36" class="mainlogo"></td>
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
		<td id="current">Short Title: ${command.trimmedShortTitleText}</td>
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
							src="images/tabGrayL.gif" width="3" height="16"
							align="absmiddle">
						1.study details <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span> <span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16" align="absmiddle">
						2.identifiers <img src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						3.study site <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						4. study design <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						5. review and submit <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span>
						</td>
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
				<form:form name="studyIdentifiersForm" method="post">
					<div><input type="hidden" name="_page" value="1">
					<input type="hidden" name="_action" value="">
					<input type="hidden" name="_selected" value=""></div>
					<br>
					<strong>Step 2. Identifiers : Add Identifiers associated with the Study </strong> (<span class="red">*</span>
					<em>Required Information </em>)<br>
					<be>

					<table width="70%" border="0" cellspacing="0" cellpadding="0"
						id="details">
						<tr>
							<td width="100%" valign="top">
								<table width="50%" border="0" cellspacing="10" cellpadding="0"
									id="table1">
								<tr align="center" class="label">
									<td width="10%" align="center"></td>
									<td width="15%" align="center">Source<span class="red">*</span></td>
									<td width="15%" align="center">Type<span class="red">*</span></td>
									<td width="15%" align="center">Identifier<span class="red">*</span></td>
									<td width="15%" align="center">Primary Indicator</td>
									<td width="15%" align="center"></td>
								</tr>
								<c:forEach items="${command.identifiers}" varStatus="status">
									<tr align="center" class="results">
										<td width="10%"><a href="javascript:fireAction('removeIdentifier',${status.index});"><img
											src="images/b-delete.gif" border="0"></a></td>
										<td width="20%"><form:select path="identifiers[${status.index}].source">
											<option value="">--Please Select--
											<form:options items="${identifiersSourceRefData}" itemLabel="name"
												itemValue="name" /></form:select></td>
										<td width="20%"><form:select path="identifiers[${status.index}].type">
											<option value="">--Please Select--
											<form:options items="${identifiersTypeRefData}" itemLabel="desc"
												itemValue="desc" /></form:select></td>
										<td width="20%"><form:input path="identifiers[${status.index}].value" onclick="javascript:clearField(this)();"/></td>
										<td width="25%" aligh="center"><form:radiobutton path="identifiers[${status.index}].primaryIndicator" value="true"/></td>
										<td width="10%"><em><span class="red"><form:errors path="identifiers[${status.index}].source"/>
											<form:errors path="identifiers[${status.index}].type"/>
											<form:errors path="identifiers[${status.index}].value"/>
											</em></span></td>
									</tr>
								</c:forEach>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
									class="heightControl"></td>
								</tr>
								<tr>
									<td align="center"><a href="javascript:fireAction('addIdentifier','0');"><img
										src="images/b-addLine.gif" border="0" alt="Add another Identifier"></a>
									</td>
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
											<input type="image" name="_target0" src="images/b-prev.gif" border="0"
												alt="goto previous page">
											<input type="image" name="_target0" src="images/b-startOver.gif" border="0"
												alt="start over from start page">
											<input type="image" name="_target2" src="images/b-continue.gif" border="0"
												alt="continue to next page">
										</td>
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
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
