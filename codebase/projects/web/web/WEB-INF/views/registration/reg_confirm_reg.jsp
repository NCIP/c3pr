<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
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
function accessApp(url,app,targetWindow){
//	alert("in");
	if(url=="")
		document.caaersForm.action="/"+app;
	else
		document.caaersForm.action=url+"/"+app;
	document.caaersForm.target=targetWindow;
	document.caaersForm.submit();
}
</script>
</head>
<body>
<form name="navigationForm" id="navigationForm" method="post"><input
	type="hidden" name="gridProxy" value="${proxy}"></form>
<tags:panelBox title="Registration Confirmation Message" boxId="ConfMessage">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="table1">
				<tr>
	
					<!-- LEFT CONTENT STARTS HERE -->
					<td valign="top" class="additionals2">
					<c:choose><c:when test="${command.registrationStatus=='Incomplete'}">
						<font color="Red"><!-- LEFT FORM STARTS HERE -->
						<!-- RIGHT CONTENT STARTS HERE --> <input type="hidden"
							name="nextView"> <strong>Subject Registration has
						been saved in an Incomplete Status successfully.  </strong></font></c:when>
					<c:otherwise>
						<font color="Green"><!-- LEFT FORM STARTS HERE -->
						<!-- RIGHT CONTENT STARTS HERE --> <input type="hidden"
							name="nextView"> <strong>Subject Registration has
						been successfully completed. Please <a href="javascript:doNothing()">print</a>
						and save this confirmation in the subject study records </strong></font>
					</c:otherwise> </c:choose>
					<br>
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						id="details">
						<tr>
							<td width="100%" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="table1">
								<tr>
									<td width="20%"><img
										src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
										class="heightControl"></td>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
										height="1" class="heightControl"></td>
								</tr>
								<tr>
									<td class="label">Study Primary Identifier:</td>
									<td>${command.studySite.study.primaryIdentifier}</td>
								</tr>
								<tr>
									<td class="label">Study Short Title:</td>
									<td valign="top">${command.studySite.study.shortTitleText}</td>
								</tr>
								<tr>
									<td class="label">Subject MRN:</td>
									<td>${command.participant.primaryIdentifier}</td>
								</tr>
								<tr>
									<td class="label">Registration Date:</td>
									<td><fmt:formatDate type="date" value="${command.startDate }"/></td>
								</tr>
								<tr>
									<td class="label">Site:</td>
									<td>${command.studySite.healthcareSite.name}</td>
								</tr>
								<tr>
									<td class="label">Treating Physician:</td>
									<td>${command.treatingPhysician.healthcareSiteInvestigator.investigator.fullName}</td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
										height="1" class="heightControl"></td>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
										height="1" class="heightControl"></td>
								</tr>
							</table>
							<br>
							<hr align="left" width="95%">
							<table width="60%" border="0" cellspacing="0" cellpadding="0"
								id="details">
								<tr>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td class="label" align="left"><a
										href="javascript:accessApp('http://10.10.10.2:8030','caaers/pages/ae/list?assignment=${command.gridId }','_caaers');">
									<b>Adverse Event Reporting</a> </b></td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
										height="1" class="heightControl"></td>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
										height="1" class="heightControl"></td>
								</tr>
								<tr>
									<td class="label" align="left"><a
										href="javascript:accessApp('http://10.10.10.2:8041','studycalendar/pages/schedule?assignment=${command.gridId }','_psc');">
									<b>Study Calendar</a></b></td>
								</tr>
								<tr>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
										height="1" class="heightControl"></td>
									<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
										height="1" class="heightControl"></td>
								</tr>
								<tr>
									<td class="label" align="left"><a
										href="javascript:accessApp('https://octrials-train.nci.nih.gov','/opa45/rdclaunch.htm','_c3d');">
									<b>Clinical Database</a></b></td>
								</tr>
	
							</table>
							</td>
						</tr>
					</table>
					</td>
	
					<!-- LEFT CONTENT ENDS HERE -->
				</tr>
			</table>
			</td>
		</tr>
	</table>
</tags:panelBox>
</body>
</html>
