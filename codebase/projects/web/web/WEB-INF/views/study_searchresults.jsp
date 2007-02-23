<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
<title>C3Pr V2</title>
<link href="resources/styles.css" rel="stylesheet" type="text/css"/>
<link href="resources/search.css" rel="stylesheet" type="text/css"/>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">

	<tr>
		<td width="99%"><img src="images/c3prLogo.gif" alt="C3PR"
			width="181" height="36" class="mainlogo"/></td>
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
			class="divider"> <a href="">Reports</a><img
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
		<td width="99%" valign="middle"><img src="images/spacer.gif"
			width="1" height="20" align="absmiddle" class="spacer"><a
			href="/c3pr/createstudy.do">Add Study</a></td>
	</tr>
</table>
<!-- SUB NAV ENDS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="titleArea">
	<form:form id="searchForm" name="searchForm" method="post">
		<tr>
			<!-- TITLE STARTS HERE -->

			<td width="99%" height="43" valign="middle" id="title"></td>
			<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="search">
				<tr>
					<td class="labels">&nbsp;</td>
				</tr>
				<tr>
					<td class="searchType">Search Study by <form:select
						path="searchType">
						<form:options items="${searchType}" itemLabel="desc"
							itemValue="code" />
					</form:select></td>
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
					<td><form:input path="searchTypeText" size="25" /></td>
					<td><input name="imageField" type="image" class="button"
						onClick="submitPage('study');return false;"
						src="images/b-go.gif" alt="GO" align="middle" width="22"
						height="10" border="0"></td>
				</tr>
			</table>
			</td>
		</tr>
	</form:form>
</table>
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

		<td id="current">Study Search Results</td>
		<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
	</tr>
	<tr>

		<td class="display"><!-- TABS LEFT START HERE -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>

				<!-- LEFT CONTENT STARTS HERE -->

				<td valign="top" class="additionals"><!-- LEFT FORM STARTS HERE -->


				<br>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">

				</table>
				<br>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="additionalList">
					<tr align="center" class="label">
						<td>Short Title</td>
						<td>Primary Identifier</td>
						<td>Status</td>
						<td>Sponsor Code</td>
						<td>Multi Institution Indicator</td>
						<td>Target Accrual Number</td>
						<td>Long Title</td>
					</tr>
					<%int i=0; %>

					<c:forEach items="${study}" var="study">
						<tr align="center" id="row<%= i++ %>" class="results" onMouseOver="navRollOver('row<%= i-1 %>', 'on')"
							onMouseOut="navRollOver('row<%= i-1 %>', 'off')"
							onClick="document.location='/c3pr/editstudy.do?studyId=${study.id}'">
							<td>${study.trimmedShortTitleText}</td>
							<td>${study.primaryIdentifier}</td>
							<td>${study.status}</td>
							<td>${study.sponsorCode}</td>
							<td>${study.multiInstitutionIndicator}</td>
							<td>${study.targetAccrualNumber}</td>
							<td>${study.longTitleText}</td>
						</a>
						</tr>
					</c:forEach>

				</table>
				<br>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">

				</table>
				<!-- LEFT FORM ENDS HERE --></td>
				<!-- LEFT CONTENT ENDS HERE -->
			</tr>
		</table>
		</td>
	</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
