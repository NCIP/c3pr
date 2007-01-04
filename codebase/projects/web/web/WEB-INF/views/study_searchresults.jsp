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
</script>
</head>
<body>
<!-- TOP LOGOS START HERE -->
<table width="100%"  border="0" cellspacing="0" cellpadding="0">

<tr>

<td width="99%"><img src="images/C3PRLogo.gif" alt="C3PR" width="181" height="36" class="gelogo"></td>
</tr>
</table>
<!-- TOP LOGOS END HERE -->
<!-- TOP NAVIGATION STARTS HERE -->
<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="topNav">

<tr valign="middle">
<td width="99%" class="left"><a href="home.htm">Registration</a><img src="images/topNavL.gif" width="2" height="20" align="absmiddle" class="currentL"><span class="current"><img src="images/topNavArrowDown.gif" width="5" height="20" align="absmiddle"> Study </span><img src="images/topNavR.gif" width="2" height="20" align="absmiddle" class="currentR"><a href="participant.htm">Participant</a><img src="images/topDivider.gif" width="2" height="20" align="absmiddle" class="divider"><a href="analysis">Reports</a><img src="images/topDivider.gif" width="2" height="20" align="absmiddle" class="divider"></td>

<td class="right"><img src="images/topDivider.gif" width="2" height="20" align="absmiddle" class="divider"><a href="logOff">Log Off</a> </td>
</tr>
</table>
<!-- TOP NAVIGATION ENDS HERE -->
<!-- SUB NAV STARTS HERE -->
<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="subNav">

</table>
<!-- SUB NAV ENDS HERE -->
<!-- MAIN BODY STARTS HERE -->


<td id="current">Study Search Results </td>
	<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
  <tr>

<td class="display">

<!-- TABS LEFT START HERE -->
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>

<!-- LEFT CONTENT STARTS HERE -->

<td valign="top" class="additionals">

<c:out value="${study}"/>
<!-- LEFT FORM STARTS HERE -->
<br>
<tr>
	 <display:table name="${study}" >
		<display:column property="descriptionText"  />
		<display:column property="status" sortable="true" />
		<display:column property="type" sortable="true" />
		<display:column property="sponsorCode" sortable="true" />
		<display:column property="phaseCode" sortable="true" />
	 </display:table>
	
</tr>

<div id="copyright">&copy; 2006 SemanticBits. All Rights Reserved</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
