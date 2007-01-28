<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>C3Pr V2</title>
<link href="resources/styles.css" rel="stylesheet" type="text/css" />
<link href="resources/search.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/JavaScript">
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function submitSearchPage(){
	document.getElementById("searchForm").submit();
}
function updatePage(s){
	document.getElementById("_page").name=s;
	document.getElementById("_page").value="next";
	document.getElementById("form").submit();	
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

		<td width="99%"><img src="images/c3prLogo.gif" alt="C3Pr V2"
			width="181" height="36" class="gelogo" /></td>
	</tr>
</table>
<!-- TOP LOGOS END HERE -->
<!-- TOP NAVIGATION STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="topNav">
	<tr valign="middle">

		<td width="100%" class="left"><a href="/c3pr/SearchAndRegister.do">Registration</a><img
			src="images/topNavL.gif" width="2" height="20" align="absmiddle"
			class="currentL" /><span class="current"><img
			src="images/topNavArrowDown.gif" width="5" height="20"
			align="absmiddle" /> Study </span><img src="images/topNavR.gif" width="2"
			height="20" align="absmiddle" class="currentR" /><a
			href="/c3pr/searchparticipant.do">Subject</a><img
			src="images/topDivider.gif" width="2" height="20" align="absmiddle"
			class="divider" /><a href="analysis">Reports</a><img
			src="images/topDivider.gif" width="2" height="20" align="absmiddle"
			class="divider" /></td>
		<td class="right"><img src="images/topDivider.gif" width="2"
			height="20" align="absmiddle" class="divider" /><a href="logOff">Log
		Off</a></td>
	</tr>
</table>
<!-- TOP NAVIGATION ENDS HERE -->
<!-- SUB NAV STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="subNav">
	<tr>
		<td width="99%" valign="middle"><img src="images/arrowRight.gif"
			width="3" height="5" align="absmiddle" /> Study Management <img
			src="images/spacer.gif" width="1" height="20" align="absmiddle"
			class="spacer" /><a href="/c3pr/createstudy.do">Add Study</a></td>
		<td valign="middle" class="right"><a href="help">Help</a></td>
	</tr>
</table>
<!-- SUB NAV ENDS HERE -->
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
				<td align="left" class="labels">Search String:</td>
				<td class="labels">&nbsp;</td>
			</tr>
			<tr>
				<td><input type=text name="searchText" size="25" /></td>
				<td><input name="imageField" type="image" class="button"
					onClick="submitSearchPage();return false;" src="images/b-go.gif"
					alt="GO" align="middle" width="22" height="10" border="0"></td>
			</tr>
		</table>
		<span class="notation">^ Minimum two characters for search.</span></td>
	</tr>
</form:form>
</table>
<!-- MAIN BODY STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->
		<td id="current">Short Title: ${command.trimmedShortTitleText}</td>
		<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
	</tr>
	<tr>
		<td class="display"><!-- TABS LEFT START HERE -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%">
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
				<!-- TABS LEFT END HERE --></td>
				<td><img src="images/spacer.gif" width="2" height="1"></td>
				<td width="100%"><!-- TABS RIGHT START HERE -->
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16"
							align="absmiddle"> 1.study details <img
							src="images/tabWhiteR.gif" width="3" height="16"
							align="absmiddle"></span> <span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						<a href="javascript:updatePage('_target1');">2.identifiers </a><img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						<a href="javascript:updatePage('_target2');">3.study sites </a><img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						<a href="javascript:updatePage('_target3');">4.study design </a><img src="images/tabGrayR.gif" width="3"
							height="16" align="absmiddle"></span></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotR"><img src="images/spacer.gif"
							width="1" height="7"></td>
					</tr>
				</table>			
				<!-- TABS RIGHT END HERE --></td>
			</tr>
			<tr>
				<form:form method="post" id="form" name="form">
				<div><input type="hidden" name="_page" value="0"></div>				
					<!-- LEFT CONTENT STARTS HERE -->
					<td valign="top" class="contentL">
					<table width="100%" border="0" cellspacing="2" cellpadding="0"
						id="table1">
						<tr valign="top">
							<td><img src="images/spacer.gif" width="1" height="1"
								class="heightControl" /></td>
						<tr>
							<td class="label">Short Title:</td>
							<td>${command.shortTitleText}</td>
						</tr>
						<tr>
							<td class="label">Long Title:</td>
							<td>${command.longTitleText}</td>
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
							<td class="label">Sponsor:</td>
							<td>${command.sponsorCode}</td>
						</tr>
						<tr>
							<td class="label">Type:</td>
							<td>${command.type}</td>
						</tr>
						</tr>
					</table>
					<!-- LEFT CONTENT ENDS HERE --></td>
					<td width=50%"></td>
					<!-- RIGHT CONTENT STARTS HERE -->
					<td width="%50%" valign="top" class="contentR">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						id="details">
						<tr>
							<td width="50%" valign="top" class="contentAreaL">
							<br>
							<div><input type="hidden" name="_page" value="1"> <input
								type="hidden" name="_action" value=""> <input
								type="hidden" name="_selected" value=""></div>
							<table width="50%" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td class="label">Short Title:</td>
									<td><form:textarea path="shortTitleText" rows="2"
										cols="30" /></td>
									<td width="15%"></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Long
									Title:</td>
									<td><form:textarea path="longTitleText" rows="2" cols="30" /></td>
									<td width="15%"><em><span class="red"><form:errors
										path="longTitleText" /></em></span></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label">Precis Text:</td>
									<td><form:textarea path="precisText" rows="2" cols="30" /></td>
									<td width="15%"></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label">Description Text:</td>
									<td><form:textarea path="descriptionText" rows="2"
										cols="30" /></td>
									<td width="15%"></td>
								</tr>
							</table>
							<td>
							<table width="50%" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td class="label">Target Accrual Number:</td>
									<td><form:input path="targetAccrualNumber" size="34" /></td>
									<td width="10%"></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Status:</td>
									<td><form:select path="status">
										<option value="">--Please Select-- <form:options
											items="${statusRefData}" itemLabel="desc" itemValue="desc" />
									</form:select></td>
									<td width="10%"><em><span class="red"><form:errors
										path="status" /></em></span></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em><strong>Disease:</strong>
									<td><form:select path="diseaseCode">
										<option value="">--Please Select-- <form:options
											items="${diseaseCodeRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="10%"><em><span class="red"><form:errors
										path="diseaseCode" /></em></span></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><em></em>Monitor:</td>
									<td><form:select path="monitorCode">
										<option value="">--Please Select-- <form:options
											items="${monitorCodeRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="10%"></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Phase:</td>
									<td><form:select path="phaseCode">
										<option value="">--Please Select-- <form:options
											items="${phaseCodeRefData}" itemLabel="desc" itemValue="desc" />
									</form:select></td>
									<td width="10%"><em><span class="red"><form:errors
										path="phaseCode" /></em></span></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Sponsor:</td>
									<td><form:select path="sponsorCode">
										<option value="">--Please Select-- <form:options
											items="${sponsorCodeRefData}" itemLabel="desc"
											itemValue="desc" />
									</form:select></td>
									<td width="10%"><em><span class="red"><form:errors
										path="sponsorCode" /></em></span></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Randomized
									Indicator</td>
									<td><form:checkbox path="randomizedIndicator" /></td>
									<td width="15%"></td>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label">Multi	Institution:</td>
									<td><form:checkbox path="multiInstitutionIndicator" /></td>
									<td width="15%"></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label">Blinded Indicator:</td>
									<td><form:checkbox path="blindedIndicator" /></td>
									<td width="15%"></td>
								</tr>
								<tr>
									<td><img src="images/spacer.gif" width="1" height="1"
										class="heightControl"></td>
								</tr>
								<tr>
									<td class="label"><span class="red">*</span><em></em>Type:</td>
									<td><form:select path="type">
										<option value="">--Please Select-- <form:options
											items="${typeRefData}" itemLabel="desc" itemValue="desc" />
									</form:select></td>
									<td width="15%"><em><span class="red"><form:errors
										path="type" /></em></span></td>
								</tr>
							</table>
							</td>
							<tr>
								<td align="center" colspan="3"><!-- action buttons begins -->
								<table cellpadding="4" cellspacing="0" border="0">
									<tr>
									<td colspan=2 valign="top"><br>
										<br>
										<input type="image" name="_target0" src="images/b-saveChanges.gif" border="0"
											alt="Save the Changes">
									</tr>
								</table>
								</td>
						    </tr>
						</tr>
					</table>
				
					</td>
			</tr>
		</table>
		</td>
		<!-- RIGHT CONTENT ENDS HERE -->
		</form:form>
	</tr>
</table>
<!-- BOTTOM LIST STARTS HERE -->
<a name="additional"></a>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="tabs">
	<tr>
		<td width="100%" id="tabDisplay"><span class="current"><img
			src="images/tabWhiteL.gif" width="3" height="16" align="absmiddle">
		Registration History <img src="images/tabWhiteR.gif" width="3"
			height="16" align="absmiddle"></span></td>
		<td width="7"><img src="images/spacer.gif" width="7" height="1"></td>
	</tr>
	<tr>
		<td colspan="2" class="tabBotL"><img src="images/spacer.gif"
			width="1" height="7"></td>
	</tr>
	<tr>
		<td colspan="2" valign="top" class="additionals">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="40%" align="right"><a href="#"><img
					src="images/b-prev.gif" alt="Previous" width="41" height="16"
					border="0" align="absmiddle"></a></td>
				<td width="20%" align="center"><strong>Showing 1-2 of
				2 </strong></td>
				<td width="40%"><a href="#"><img src="images/b-next.gif"
					alt="Next" width="41" height="16" border="0" align="absmiddle"></a></td>
			</tr>
		</table>
		<br>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			id="additionalList">
			<tr align="center" class="label">
				<td width="11">&nbsp;</td>
				<td>Status<br>
				<select name="select">
					<option>Filter</option>
					<option>Status</option>
					<option>Filter2</option>
				</select></td>
				<td>Reg Id</td>
				<td>Short<br>
				Title</td>
				<td>Status</td>
				<td>Disease<br>
				Code</td>
				<td>Amendment<br>
				Date</td>
				<td>Stratification<br>
				Factor</td>
				<td>Randomization</td>
				<td>Current Accrual<br>
				Number</td>
				<td>Target Accrual<br>
				Number</td>
			</tr>
			<tr align="center" class="current">
				<td><img src="images/currentView.gif" alt="Currently Viewing"
					width="11" height="11" /></td>
				<td>Filter1</td>
				<td>01-123456</td>
				<td>CALGB_XYZ</td>
				<td>Active</td>
				<td>1234</td>
				<td>02/05/2006</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>35</td>
				<td>50</td>
			</tr>		
		</table>
		</td>
	</tr>
</table>
<!-- BOTTOM LIST ENDS HERE -->
</td>
</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
