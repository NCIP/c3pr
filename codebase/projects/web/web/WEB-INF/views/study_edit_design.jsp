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
function validatePage(){
	return true;
}
function fireAction(action, selected){
	if(validatePage()){
		document.form._action.value=action;
		document.form._selected.value=selected;
		document.form.submit();
	}
}
function clearField(field){
field.value="";
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
						<td width="100%" id="tabDisplay"><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16"
							align="absmiddle">
						<a href="javascript:updatePage('_target0');">1.study details </a><img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span> <span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						<a href="javascript:updatePage('_target1');">2.identifiers </a><img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="images/tabGrayL.gif" width="3" height="16" align="absmiddle">
						<a href="javascript:updatePage('_target2');">3.study sites </a><img src="images/tabGrayR.gif" width="3" height="16"
							align="absmiddle"></span><span class="current"><img
							src="images/tabWhiteL.gif" width="3" height="16" align="absmiddle">
						4.study design <img src="images/tabWhiteR.gif" width="3"
							height="16" align="absmiddle"></span>
						</td>
						<td><img src="images/spacer.gif" width="7" height="1"></td>
					</tr>					<tr>
						<td colspan="2" class="tabBotR"><img src="images/spacer.gif"
							width="1" height="7"></td>
					</tr>
				</table>			
				<!-- TABS RIGHT END HERE --></td>
			</tr>
			<tr>
				<form:form name="form" method="post">
					<div><input type="hidden" name="_page" value="3">
					<input type="hidden" name="_action" value="">
					<input type="hidden" name="_selected" value=""></div>		
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
									</td></tr>
								</table>
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
									<input type="image" name="_target3" src="images/b-saveChanges.gif" border="0"
										alt="Save the Changes">
								</tr>
							</table>
							</td>
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
