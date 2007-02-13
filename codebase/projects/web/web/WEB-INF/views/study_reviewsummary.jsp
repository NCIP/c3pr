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
function updateTargetPage(target){
	if(validatePage()){
		document.studyDesignForm._target0.value=s;
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
	</tr>`
	<tr>
		<td class="display"><!-- TABS LEFT START HERE -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"1
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						1.study details <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span> <span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						2.identifiers <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						3.study site <img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						4. study design <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span><span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16"
							align="absmiddle">
						5. review and submit <img
							src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span>
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
				<!-- RIGHT CONTENT STARTS HERE --> <form:form method="post">

					<strong>Step 5. Review and Submit</strong>
					<br>
					<hr>
					<strong>Study Details </strong>
					<br>
					<div class="review">
					<table width="50%" border="0" cellspacing="0" cellpadding="0"
						id="table1">

						<tr>
							<td width="20%" class="label">Short Title:</td>
							<td>${command.shortTitleText}</td>
						</tr>
						<tr>
							<td width="20%" class="label">Target Accrual Number:</td>
							<td>${command.targetAccrualNumber}</td>
						</tr>
						<tr>
							<td width="20%" class="label">Status:</td>
							<td>${command.status}</td>
						</tr>
						<tr>
							<td width="20%" class="label">Disease Code:</td>
							<td>${command.diseaseCode}</td>
						</tr>
						<tr>
							<td width="20%" class="label">Disease Type:</td>
							<td>${command.type}</td>
						</tr>
						<tr>
							<td width="20%" class="label">Monitor Code:</td>
							<td>${command.monitorCode}</td>
						</tr>
						<tr>
							<td width="20%" class="label">Phase Code:</td>
							<td>${command.phaseCode}</td>
						</tr>
						<tr>
							<td width="20%" class="label">Multi Institution:</td>
							<td><form:checkbox path="multiInstitutionIndicator" disabled="true"/>
							Blinded: <form:checkbox path="blindedIndicator" disabled="true"/>
							Randomized: <form:checkbox path="randomizedIndicator" disabled="true"/></td>
						</tr>
						<tr>
					<td><br><br>
						<input type="image" name="_target0" src="images/b-edit.gif" border="0"
						alt="edit this page">
						</tr>

					</table>
					</div>
					<br>

					<hr>
					<strong>Study Identifiers</strong>
					<br>
					<br>
					<div class="review">

					<table width="50%" border="0" cellspacing="0" cellpadding="0"
						id="table1">

						<tr>
							<td valign="top">
							<table width="100%" border="1" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td width="20% align="left" class="label">Source</td>
									<td width="20%" align="left" class="label">Type</td>
									<td width="20%" align="left" class="label">Identifier</td>
								</tr>
								<c:forEach items="${command.identifiers}" var="identifier">
								<tr class="results">
									<td>${identifier.source}</td>
									<td>${identifier.type}</td>
									<td>${identifier.value}</td>
								</tr>
								</c:forEach>

							</table>
							</td>
						</tr>
						<tr>
							<td><br><br>
								<input type="image" name="_target1" src="images/b-edit.gif" border="0"
									alt="edit this page"></td>
						</tr>
					</table>
					</div>
					<br>
					<br>
					<hr>
					<strong>Study Site</strong>
					<br>
					<br>
					<div class="review">

					<table width="50%" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td valign="top">
							<table width="100%" border="1" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td width="20% align="left" class="label">Study Site</td>
									<td width="20%" align="left" class="label">Status</td>
									<td width="20%" align="left" class="label">Role</td>
									<td width="20%" align="left" class="label">Start Date</td>
									<td width="20%" align="left" class="label">IRB Approval Date</td>
								</tr>
								<c:forEach items="${command.studySites}" var="studySite">
								<tr class="results">
									<td>${studySite.site.name}</td>
									<td>${studySite.statusCode}</td>
									<td>${studySite.roleCode}</td>
									<td>${studySite.startDate}</td>
									<td>${studySite.irbApprovalDate}</td>
								</tr>
								</c:forEach>
							</table>
							</td>
						</tr>
						<tr>
						<td><br><br>
							<input type="image" name="_target2" src="images/b-edit.gif" border="0"
								alt="edit this page"></td>
						</tr>
					</table>
					</div>
					<br>
					<hr>
					<strong>Study Design</strong>
					<br>
					<br>
					<div class="review">

					<table width="50%" border="0" cellspacing="10" cellpadding="0"
						id="table1">
						<tr>
							<td valign="top">
							<table width="100%" border="1" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td width="20% align="left" class="label">Epochs</td>
									<td width="80%" align="left" class="label">Arms</td>
								</tr>
								<c:forEach items="${command.epochs}" var="epoch">
								<tr class="results">
									<td lclass="label">${epoch.name}</td>
									<td >
										<table width="100%" border="0" cellspacing="2" cellpadding="2"
											id="table1">
										<tr>
											<c:forEach items="${epoch.arms}" var="arm">
												<tr>
													<td width="50%">${arm.name}</td>
													<td width="50%">${arm.targetAccrualNumber}</td>
												</tr>
											</c:forEach>
										</tr>
										</table>
									</td>
								</tr>
								</c:forEach>
							</table>
							</td>
						</tr>
						<tr>
								<td><br><br>
									<input type="image" name="_target3" src="images/b-edit.gif" border="0"
								alt="edit this page">	</td>
								</tr>
								<BR>
							<tr>
							<td align="center" colspan="3"><!-- action buttons begins -->
							<table width="40%" cellpadding="4" cellspacing="0" border="0">
								<tr>
									<td colspan=2 valign="top"><br>
										<br>
										<input type="image" name="_target3" src="images/b-prev.gif" border="0"
											alt="goto previous page">
										<input type="image" name="_target5" src="images/b-submit.gif" border="0"
											alt="continue to next page">
										<input type="image" name="_target0" src="images/b-startOver.gif" border="0"
											alt="start over from start page">
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<br>
				</form:form></td>
				<!-- LEFT CONTENT ENDS HERE -->
		</table>
		</td>
	</tr>
</table>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
