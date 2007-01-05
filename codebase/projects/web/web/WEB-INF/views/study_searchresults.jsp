<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


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

<tr>
  <td width="99%" valign="middle"><img src="images/arrowRight.gif" width="3" height="5" align="absmiddle"> Study Management <img src="images/spacer.gif" width="1" height="20" align="absmiddle" class="spacer"><a href="/createstudy.do">Add Study</a></td>
 </tr>
</table>
<!-- SUB NAV ENDS HERE -->
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">

  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
	<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

<td id="current">Study Search Results </td>
	<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
  </tr>
  <tr>

<td class="display">

<!-- TABS LEFT START HERE -->
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>

<!-- LEFT CONTENT STARTS HERE -->

<td valign="top" class="additionals">

<!-- LEFT FORM STARTS HERE -->


  <br>
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">

  </table>
  <br>
<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="additionalList">
  <tr align="center" class="label">
    <td>id </td>    
    <td>Short Title </td>
    <td>Target Accrual Number</td>        
    <td>Sponsor Code</td>
    <td>StatusCode</td>
    <td>Monitor Code</td>
    <td>Disease Code</td> 
    <td>Phase Code </td>
    <td>Type </td>  
   </tr>

<a href="protocol_details.htm" onMouseOver="navRollOver('row13', 'on')" onMouseOut="navRollOver('row13', 'off')">
  <tr align="center">
    <c:forEach items="${study}" var="study">
        <tr align="center"> 
            <td> <a href="<c:url value=""/>"> ${study.id} </a></td>            
            <td> ${study.shortTitleText}</td>
            <td> ${study.targetAccrualNumber}</td>       
            <td> ${study.sponsorCode}</td>
            <td> ${study.status} </td>           
            <td> ${study.monitorCode}</td>    
            <td> ${study.phaseCode}</td>
            <td> ${study.type}</td>                              
        </tr>
    </c:forEach>
</tr>

</table>
<br>
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">

  </table>
  <!-- LEFT FORM ENDS HERE --></td>
<!-- LEFT CONTENT ENDS HERE -->
          </tr>
</table>
    </td>
</tr>
</table>
<div id="copyright">&copy; 2006 SemanticBits. All Rights Reserved</div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
