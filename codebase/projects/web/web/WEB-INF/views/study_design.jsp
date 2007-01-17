<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
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
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function validatePage(){
	return true;
}
function fireAction(action, selectedEpoch, selectedArm){
	if(validatePage()){
		document.studyDesignForm._action.value=action;
		document.studyDesignForm._selectedEpoch.value=selectedEpoch;
		document.studyDesignForm._selectedArm.value=selectedArm;
		document.studyDesignForm.submit();
	}
}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="99%"><img src="images/c3prLogo.gif" alt="C3Pr V2"
			width="181" height="36" class="gelogo"></td>
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
		<td width="99%" height="43" valign="middle" id="title">Add
		Study</td>

	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<tr>
			<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->
			<td id="current">Short Title: ${command.shortTitleText}</td>
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
							align="absmiddle"></span> <span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						2.identifiers <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						3.study site <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span><span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16" align="absmiddle">
						4. study design <img src="images/tabWhiteR.gif" width="3"
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

				<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
				<!-- RIGHT CONTENT STARTS HERE -->
				<form:form name="studyDesignForm" method="post">
				<div><input type="hidden" name="_page" value="3">
					<input type="hidden" name="_action" value="">
					<input type="hidden" name="_selectedEpoch" value="">
					<input type="hidden" name="_selectedArm" value="">
				</div>
					<strong>Step 4. Enter Epochs & Arms </strong> (<span class="red">*</span>
					<em>Required Information </em>)<br>

					<br>
					<table width="60%" border="0" cellspacing="0" cellpadding="0"
						id="table1">

						<td width="100%" valign="top">
							<table width="70%" border="0" cellspacing="10" cellpadding="0"
								id="table1">

							<tr align="center" class="label">
								<td width="5%" align="center"><a href="javascript:fireAction('addEpoch','0','0');"><img
									src="images/checkyes.gif" border="0"></a>Epochs (add)</td>
								<td width="20%" align="center">Epoch</td>
								<td width="20%" align="center">Description</td>
								<td width="3%" align="center">Arms (add)</td>
								<td width="50%" align="center">[Name, Target Accrual Number]</td>

							</tr>
							<c:forEach items="${command.epochs}" var="epoch" varStatus="status">
								<tr align="center" class="results">
									<td width="8%"><a href="javascript:fireAction('removeEpoch',${status.index},'0');"><img
										src="images/checkno.gif" border="0"></a>
									</td>
									<td width="20%"><form:input path="epochs[${status.index}].name" /></td>
									<td width="20%"><form:input path="epochs[${status.index}].descriptionText" /></td>
									<td width="3%"><a href="javascript:fireAction('addArm',${status.index},'0');"><img
										src="images/checkyes.gif" border="0"></a></td>
									<td width="50%" >
										<table width="100%" border="1" cellspacing="0" cellpadding="0"
											id="table1">
											<c:forEach items="${epoch.arms}" var="arm" varStatus="statusArms">

											<tr align="center" class="results">
												<td width="8%"><a href="javascript:fireAction('removeArm',${status.index},${statusArms.index});"><img
													src="images/checkno.gif" border="0"></a>
												</td>
												<td ><form:input path="epochs[${status.index}].arms[${statusArms.index}].name" /></td>
												<td ><form:input path="epochs[${status.index}].arms[${statusArms.index}].targetAccrualNumber" /></td>
											</tr>
											</c:forEach>
										</table>

									</td>
								</tr>
							</c:forEach>
							</table>
						</td>

						<tr>
							<td><img src="images/spacer.gif" width="1" height="3"
								class="heightControl"></td>
						</tr>
						<tr>
							<td align="center" colspan="3"><!-- action buttons begins -->
							<table cellpadding="4" cellspacing="0" border="0">
								<tr>
									<td colspan=2 valign="top"><br>
										<br>
										<input type="image" name="_target2" src="images/b-prev.gif" border="0"
											alt="goto previous page">
										<input type="image" name="_target4" src="images/b-continue.gif" border="0"
											alt="continue to next page">
										<input type="image" name="_target0" src="images/b-startOver.gif" border="0"
											alt="start over from start page">
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</form:form></td>
			</tr>
		</table>
		<br>
		</td>
		<!-- LEFT CONTENT ENDS HERE -->
	</tr>
</table>
<div id="copyright">&copy; 2006 SemanticBits. All Rights Reserved</div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
