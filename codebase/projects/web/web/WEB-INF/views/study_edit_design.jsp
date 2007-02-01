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
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script language="JavaScript" id="js1">
	var cal1 = new CalendarPopup();
</script>
<script language="JavaScript" type="text/JavaScript">
function submitSearchPage(){
	document.getElementById("searchForm").submit();
}
function updatePage(s){
	document.getElementById("_page").name=s;
	document.getElementById("_page").value="next";
	document.getElementById("form").submit();
}
function validatePage(){
	return true;
}
function fireAction(action, selectedEpoch, selectedArm){
	if(validatePage()){
		document.form._action.value=action;
		document.form._selectedEpoch.value=selectedEpoch;
		document.form._selectedArm.value=selectedArm;
		document.form.submit();
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
			src="images/topDivider.gif" width="2" height="20" align="absmiddle"
			class="divider"><a href="/c3pr/searchstudy.do">Study</a><img
			src="images/topNavL.gif" width="2" height="20" align="absmiddle"
			class="currentL"><span class="current"><img
			src="images/topNavArrowDown.gif" width="5" height="20"
			align="absmiddle"> Subject </span><img src="images/topNavR.gif"
			width="2" height="20" align="absmiddle" class="currentR"><a
			href="">Reports</a><img src="images/topDivider.gif" width="2"
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
			width="3" height="5" align="absmiddle"> Study Management <img
			src="images/spacer.gif" width="1" height="20" align="absmiddle"
			class="spacer"><a href="createstudy.do">Add Study</a></td>
		<td valign="middle" class="right"><a href="">Help</a></td>
	</tr>
</table>
<!-- SUB NAV ENDS HERE -->
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="titleArea">
	<form:form id="searchForm" name="searchForm"
			method="post" action="/c3pr/searchstudy.do">
	<tr>
		<!-- TITLE STARTS HERE -->
		<td width="100%" valign="middle" id="title">Study Management</td>
		<!-- TITLE STARTS HERE -->
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="search">
				<tr>
					<td class="labels">&nbsp;</td>
				</tr>
				<tr>
					<td class="searchType">Search Study by <select name="searchType">
						<c:forEach items="${studySearchTypeRefData}" var="opt">
							<option value="${opt.code }">${opt.desc }</option>
						</c:forEach></td>
				</tr>
			</table>
			<span class="notation">&nbsp;</span></td>
		<td valign="top">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			id="search">
			<tr>
				<td align="left" class="labels">Search Criteria:</td>
				<td class="labels">&nbsp;</td>
			</tr>
			<tr>
				<td><input type=text name="searchText" size="25" /></td>
				<td><input name="imageField" type="image" class="button"
					onClick="submitSearchPage();return false;" src="images/b-go.gif"
					alt="GO" align="middle" width="22" height="10" border="0"></td>
			</tr>
		</table>
		</td>
	</tr>
</form:form>
</table>
<!-- MAIN BODY STARTS HERE -->


<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td id="current">Short Title: ${command.trimmedShortTitleText}</td>
		<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
	</tr>
	<tr>
		<td class="display">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<!-- TABS LEFT START HERE -->
				<td width="27%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16"
							align="absmiddle"> Study Summary <img
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
				<td><img src="images/spacer.gif" width="2" height="1"></td>
				<!-- TABS CENTER START HERE -->
				<td width="45%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16"
							align="absmiddle">
						<a href="javascript:updatePage('_target0');">Details</a> <img src="images/tabGrayR.gif"
							width="3" height="16" align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						<a href="javascript:updatePage('_target1');">Identifiers</a><img src="images/tabGrayR.gif"
							width="3" height="16" align="absmiddle"></span> <span class="tab">
							<img src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						<a href="javascript:updatePage('_target2');">Study Sites</a><img src="images/tabGrayR.gif"
							width="3" height="16" align="absmiddle"></span><span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16" align="absmiddle">
						Study Design<img src="images/tabWhiteR.gif" width="3" height="16" align="absmiddle"></span></td>
						<td><img src="images/spacer.gif" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotR"><img src="images/spacer.gif"
							width="1" height="7"></td>
					</tr>
				</table>
				</td>
				<td><img src="images/spacer.gif" width="2" height="1"></td>
				<!-- TABS LEFT START HERE -->
				<td width="27%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16"
							align="absmiddle">Participant Assignments <img
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
				<form:form name="form" id="form" method="post">
				<div><input type="hidden" name="_page" id="_page" value="3">
					<input type="hidden" name="_action" value="">
					<input type="hidden" name="_selectedEpoch" value="">
					<input type="hidden" name="_selectedArm" value="">
				</div>
				<td valign="top" class="contentL"><!-- LEFT CONTENT STARTS HERE -->
						<table width="100%" border="0" cellspacing="2" cellpadding="0"
						id="table1">
						<tr valign="top">
							<td><img src="images/spacer.gif" width="1" height="1"
								class="heightControl" /></td>
						</tr>
						<tr>
							<td class="label">Short Title:</td>
							<td>${command.trimmedShortTitleText}</td>
						</tr>
						<tr>
							<td class="label">Primary Identifier:</td>
							<td>${command.primaryIdentifier}</td>
						</tr>
						<tr>
							<td class="label">Target Accrual No:</td>
							<td>${command.targetAccrualNumber}</td>
						</tr>
						<tr>
							<td class="label">Status:</td>
							<td>${command.status}</td>
						</tr>
						<tr>
							<td class="label">Sponsor:</td>
							<td>${command.sponsorCode}</td>
						</tr>
						<tr>
							<td class="label">Type:</td>
							<td>${command.type}</td>
						</tr>
					</table>
				<!-- LEFT CONTENT ENDS HERE --></td>
				<td><img src="images/spacer.gif" width="2" height="1"></td>
				<!-- CENTER CONTENT STARTS HERE -->
				<td valign="top" class="contentL">
					<table width="60%" border="0" cellspacing="0" cellpadding="0"
						id="table1">

						<td width="100%" valign="top">
							<table width="70%" border="0" cellspacing="10" cellpadding="0"
								id="table1">
							<tr align="center" class="label">
								<td width="5%" align="center"></td>
								<td width="20%" align="center">Epoch <span class="red">*</span></td>
								<td width="20%" align="center">Description</td>
								<td width="3%" align="center">(add arms)(<span class="red">*</span></td>
								<td width="50%" align="center">[Name, Target Accrual Number]</td>

							</tr>
							<c:forEach items="${command.epochs}" var="epoch" varStatus="status">
							<tr align="center" class="results">
								<td width="8%"><a href="javascript:fireAction('removeEpoch',${status.index},'0');"><img
									src="images/b-delete.gif" border="0"></a>
								</td>
								<td width="20%"><form:input path="epochs[${status.index}].name" /></td>
								<td width="20%"><form:input path="epochs[${status.index}].descriptionText" /></td>
								<td width="3%"><a href="javascript:fireAction('addArm',${status.index},'0');"><img
									src="images/b-addLine.gif" border="0"></a></td>
								<td width="50%" >
									<table width="100%" border="1" cellspacing="0" cellpadding="0"
										id="table1">
										<c:forEach items="${epoch.arms}" var="arm" varStatus="statusArms">

										<tr align="center" class="results">
											<td width="8%"><a href="javascript:fireAction('removeArm',${status.index},${statusArms.index});"><img
												src="images/b-delete.gif" border="0"></a>
											</td>
											<td ><form:input path="epochs[${status.index}].arms[${statusArms.index}].name" /></td>
											<td ><form:input path="epochs[${status.index}].arms[${statusArms.index}].targetAccrualNumber" /></td>
										</tr>
										</c:forEach>
									</table>

								</td>
							</tr>
							</c:forEach>
							<tr>
								<td><img src="images/spacer.gif" width="1" height="1"
								class="heightControl"></td>
							</tr>
							<tr>
								<td align="center"><a href="javascript:fireAction('addEpoch','0');"><img
									src="images/b-addLine.gif" border="0" alt="Add another Epoch"></a>
								</td>
							</tr>
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
									<a href="javascript:fireAction('update','0','0');"><img
										src="images/b-saveChanges.gif" border="0" alt="Save the Changes"></a>
								</tr>
							</table>
							</td>
					    	</tr>
				</table>
				</td>

				<td><img src="images/spacer.gif" width="2" height="1"></td>
				<!-- RIGHT CONTENT STARTS HERE -->
				<td width="29%" valign="top" class="contentR">
				<strong>Participant Assigned to Study</strong><br>
				<br>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="table1">
					<tr align="center" class="label">
						<td width="35%" align="center">Last Name</td>
						<td width="35%" align="center">Start Date</td>
						<td width="35%" align="center">Primary Id</td>
					</tr>
					<c:forEach items="${participantAssignments}" var="partAssgn">
						<tr align="center" class="results">
						<td>${partAssgn.participant.lastName}</td>
						<td>${partAssgn.startDate}</td>
						<td>${partAssgn.participant.primaryIdentifier}</td>
						</tr>
					</c:forEach>
					<tr>
						<td><img src="images/spacer.gif" width="1" height="1"
						class="heightControl"></td>
					</tr>
				</table>
				</td>
				<!-- LEFT CONTENT ENDS HERE --></td>
			</form:form>
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
