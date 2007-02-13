<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>C3PR V2</title>
<link href="resources/styles.css" rel="stylesheet" type="text/css">
<link href="resources/search.css" rel="stylesheet" type="text/css">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function search(s){

}
function submitPage(s){
	document.getElementById("searchCategory").value=s;
	document.getElementById("searchForm").submit();
}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><img src="images/c3prLogo.gif" alt="C3PR" width="181"
			height="36" class="mainlogo"></td>
		<td>&nbsp;</td>
	</tr>
</table>
<!-- TOP LOGOS END HERE -->
<!-- TOP NAVIGATION STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="topNav">
	<tr valign="middle">

		<td width="99%" class="left"><span class="current"><img
			src="images/topNavArrowDown.gif" width="5" height="20"
			align="absmiddle"> Registration </span><img src="images/topNavR.gif"
			width="2" height="20" align="absmiddle" class="currentR"><a
			href="/c3pr/searchstudy.do">Study</a><img src="images/topDivider.gif"
			width="2" height="20" align="absmiddle" class="divider"><a
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

		<td width="99%" valign="middle" class="left">&nbsp;</td>
		<td valign="middle" class="right"><a href="help">Help</a></td>
	</tr>
</table>
<!-- SUB NAV ENDS HERE -->
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="titleArea">
	<tr>
		<!-- TITLE STARTS HERE -->
		<td width="99%" height="25" valign="middle" id="title">Welcome</td>
		<!-- TITLE ENDS HERE -->
	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="details">
	<form:form id="searchForm" name="searchForm" method="post">
		<form:hidden path="searchCategory" />
		<tr>
			<!-- CURRENT PROTOCOL/Subject TITLE STARTS HERE -->

			<td id="current">Search</td>
			<!-- CURRENT PROTOCOL/Subject TITLE ENDS HERE -->
		</tr>
		<tr>
			<td class="display"><!-- TABS LEFT START HERE -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="50%">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="tabs">
						<tr>

							<td width="100%" id="tabDisplay"><span class="current"><img
								src="images/tabWhiteL.gif" width="3" height="16"
								align="absmiddle"> Study Search <img
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
					<td width="50%"><!-- TABS RIGHT START HERE -->
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="tabs">
						<tr>

							<td width="100%" id="tabDisplay"><span class="current"><img
								src="images/tabWhiteL.gif" width="3" height="16"
								align="absmiddle"> Subject Search <img
								src="images/tabWhiteR.gif" width="3" height="16"
								align="absmiddle"></span></td>
							<td><img src="images/spacer.gif" width="7" height="1"></td>
						</tr>
						<tr>
							<td colspan="2" class="tabBotR"><img src="images/spacer.gif"
								width="1" height="7"></td>
						</tr>
					</table>
					<!-- TABS RIGHT END HERE --></td>
				</tr>
				<tr>
					<!-- LEFT CONTENT STARTS HERE -->
					<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
					<table width="100%" border="0" cellpadding="0" cellspacing="0"  id="table1">
						<tr valign="top">
							<td><img src="images/Study.gif" alt="Study Search"
								width="100" height="100" align="absmiddle"></td>
							<td width="100%">
							<table width="50%"  border="0" cellspacing="5" cellpadding="0" id="table1">
								<tr>
									<td align="left" class="label">Search Studies By:</td>
									<td align="left" >
									<form:select path="searchType">
										<form:options items="${searchType}" itemLabel="desc" itemValue="code" />
									</form:select></td>
								</tr>
								<tr>
									<td align="left" class="label">Search Criteria: </td>
									<td align="left"><form:input path="searchTypeText" /></td>
								</tr>
								<tr>
									<td align="left" class="label">&nbsp;</td>
									<td align="left" class="label">&nbsp;</td>
								</tr>
								<tr>
									<td></td>
									<td align="center">
									<a href="" onClick="submitPage('study');return false;"><img
										src="images/b-search2.gif" alt="Search" border="0"></a>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<!-- LEFT FORM ENDS HERE --></td>
					<!-- LEFT CONTENT ENDS HERE -->
					<td><img src="images/spacer.gif" width="2" height="1"></td>
					<td valign="top" class="additionals2">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr valign="top">
							<td><img src="images/Patient.gif" alt="Subject Search"
								width="100" height="100" align="absmiddle"></td>
							<td width="100%">
							<table width="50%"  border="0" cellspacing="5" cellpadding="0" id="table1">
								<tr>
									<td align="left" class="label">Search Subject By:</td>
									<td align="left">
									<form:select path="searchTypePart">
										<form:options items="${searchTypePart}" itemLabel="desc" itemValue="code" />
									</form:select></td>
								</tr>
								<tr>
									<td align="left" class="label">Search Criteria: </td>
									<td align="left"><form:input path="searchTypeTextPart" /></td>
								</tr>
								<tr>
									<td align="left" class="label">&nbsp;</td>
									<td align="left" class="label">&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td align="center">
									<a href="" onClick="submitPage('participant');return false;"><img
										src="images/b-search2.gif" alt="Search" border="0"></a>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</form:form>
</table>
<div id="copyright"></div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
