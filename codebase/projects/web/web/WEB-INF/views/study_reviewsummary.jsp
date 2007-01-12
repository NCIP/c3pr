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
<script language="JavaScript" type="text/JavaScript">
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
<script language="JavaScript" type="text/JavaScript">

function validatePage(){
	if(document.getElementById("longTitleText") != null)
		return true;
	else
		return false;	
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
		<td width="99%" height="43" valign="middle" id="title">Add Study</td>

	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

		<td id="current">Site Name-Id: ${sites[0].site.name}</td>
		<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
	</tr>
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
							align="absmiddle"> 5. review and submit <img
							src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						6. confirmation <img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"> </span></td>
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
					<br>
					<hr>					
					<strong>Study Details </strong>
					<br>
					<br>
					<div class="review">
					<table width="250" border="0" cellspacing="0" cellpadding="0"
						id="table1">
						<tr>
							<td class="label">Short Title:</td>
							<td class="label">${command.shortTitleText}</td>
						</tr>
						<tr>
							<td class="label">Target Accrual Number:</td>
							<td>${command.targetAccrualNumber}</td>
						</tr>
						<tr>
							<td class="label">Status:</td>
							<td>${command.status}</td>
						</tr>
						<tr>
							<td class="label">Disease Code:</td>
							<td>${command.diseaseCode}</td>
						</tr>
						<tr>
							<td class="label">Disease Type:</td>
							<td>${command.type}</td>
						</tr>
						<tr>
							<td class="label">Monitor Code:</td>
							<td>${command.monitorCode}</td>
						</tr>
						<tr>
							<td class="label">Phase Code:</td>
							<td>${command.phaseCode}</td>
						</tr>
						<tr>
							<td class="label">Multi Institution:</td>
							<td><form:checkbox path="multiInstitutionIndicator" /></td>
							<td class="label">Blinded:</td>
							<td><form:checkbox path="blindedIndicator" /></td>
							<td class="label">Randomized:</td>
							<td><form:checkbox path="randomizedIndicator" /></td>
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

					<table width="250" border="0" cellspacing="0" cellpadding="0"
						id="details">
						<tr>
							<td class="label">Source:</td>
							<td>${command.identifiers[0].source}</td>
						</tr>
						<tr>
							<td class="label">Type:</td>
							<td>${command.identifiers[0].type}</td>
						</tr>
						<tr>
							<td class="label">Value:</td>
							<td>${command.identifiers[0].value}</td>
						</tr>
						<tr>
						<td>
						<br><br>
							<input type="image" name="_target1" src="images/b-edit.gif" border="0"
							alt="edit this page">
						</td>
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

					<table width="250" border="0" cellspacing="0" cellpadding="0"
						id="details">
						<tr>
							<td class="label">Status:</td>
							<td>${command.studySites[0].statusCode}</td>
						</tr>
						<tr>
							<td class="label">Role:</td>
							<td>${command.studySites[0].roleCode}</td>
						</tr>
						<tr>
							<td class="label">Start Date:</td>
							<td>${command.studySites[0].startDate}</td>
						</tr>
						<tr>
							<td class="label">IRB Approval Date::</td>
							<td>${command.studySites[0].irbApprovalDate}</td>
						</tr>
						<tr>
						<td><br><br>
							<input type="image" name="_target2" src="images/b-edit.gif" border="0"
						alt="edit this page">	</td>
						</tr>
						
					</table>
					</div>
					<br>

					
					<hr>
					<strong>Study Design</strong>
					<br>
					<br>
					<div class="review">

					<table width="250" border="0" cellspacing="0" cellpadding="0"
						id="details">
						<tr>
							<td valign="top">
							<table width="50%" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr align="left" class="label">
									<td width="20%">Name</td>
									<td width="30%">Description Text</td>	
									<td width="50%">Arms</td>								
								</tr>
								<c:forEach items="${command.epochs}" var="epoch">
									<tr align="left" class="results">						
										<td width="20%">${epoch.name}</td>
										<td width="30%">${epoch.descriptionText}</td>
										<td width="50%">
											<table width="300" border="1" cellspacing="0" cellpadding="0"
												id="table1">
											<tr>
												<c:forEach items="${epoch.arms}" var="arm">
													<tr align="left" class="results">						
														<td width="10%">${arm.name}</td>
														<td width="10%">${arm.targetAccrualNumber}</td>																								
													</tr>
												</c:forEach>
											</tr>
											</table>
										</td>											
									</tr>
								</c:forEach>
								<tr>
								<td><br><br>
									<input type="image" name="_target3" src="images/b-edit.gif" border="0"
								alt="edit this page">	</td>
								</tr>
							</table>
							</div>
							</td>

						</tr>
							<tr>
							<td align="center" colspan="3"><!-- action buttons begins -->
							<table cellpadding="4" cellspacing="0" border="0">
								<tr>
									<td colspan=2 valign="top"><br>
										<br>
										<input type="image" name="_target0" src="images/b-prev.gif" border="0"
											alt="goto previous page">									
										<input type="image" name="_finish" src="images/b-done.gif" border="0"
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
<div id="copyright">&copy; 2006 SemanticBits. All Rights Reserved</div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
