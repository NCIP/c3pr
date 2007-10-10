<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration"%>
<html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>C3Pr V2</title>
<link href="resources/styles.css" rel="stylesheet" type="text/css">
<link href="resources/search.css" rel="stylesheet" type="text/css">
<script>
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
<c:choose>
<c:when test="${not empty registrationException}">
<tags:panelBox title="Error Registering" boxId="ConfMessage">
<font color="Red"> <strong>Error registering subject.<br> ${registrationException.message}</strong></font>
</tags:panelBox>
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>
<form name="navigationForm" id="navigationForm" method="post"></form>
<tags:panelBox title="Confirmation Message" boxId="ConfMessage">
	<c:choose>
	<c:when test="${newRegistration}">
		<c:choose>
		<c:when test="${reg_registered}">
			<font color="Green"><!-- LEFT FORM STARTS HERE -->
			<!-- RIGHT CONTENT STARTS HERE --> <strong>Subject has
			been successfully Registered. Please <a href="javascript:doNothing()">print</a>
			and save this confirmation in the subject study records </strong></font></c:when>
		<c:when test="${reg_pending}">
			<font color="Green"><!-- LEFT FORM STARTS HERE -->
			<!-- RIGHT CONTENT STARTS HERE --> <strong>Subject Registration request has
			been successfully sent to the Coordinating center. </strong></font></c:when>
		<c:when test="${reg_disapproved}">
			<font color="Red"><!-- LEFT FORM STARTS HERE -->
			<!-- RIGHT CONTENT STARTS HERE --> <strong>Subject Registration request has
			been disapproved by the Coordinating center. </strong></font></c:when>
		<c:when test="${reg_reserved}">
			<font color="Green"><!-- LEFT FORM STARTS HERE -->
			<!-- RIGHT CONTENT STARTS HERE --><strong>Subject has
			been successfully Reserved. Please <a href="javascript:doNothing()">print</a>
			and save this confirmation in the subject study records </strong></font></c:when>
		<c:when test="${reg_unregistered}">
			<font color="Red"><!-- LEFT FORM STARTS HERE -->
			<!-- RIGHT CONTENT STARTS HERE --><strong>Subject not registered. Subject Registration record has
			been successfully saved.</strong></font></c:when>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:choose>
		<c:when test="${epoch_approved}">
			<font color="Green"><!-- LEFT FORM STARTS HERE -->
			<!-- RIGHT CONTENT STARTS HERE --><strong>Subject has
			been successfully transferred.</strong></font></c:when>
		<c:when test="${epoch_pending}">
			<font color="Green"><!-- LEFT FORM STARTS HERE -->
			<!-- RIGHT CONTENT STARTS HERE --><strong>Subject Transfer request has
			been successfully sent to the Coordinating center. </strong></font></c:when>
		<c:when test="${epoch_disapproved}">
			<font color="Red"><!-- LEFT FORM STARTS HERE -->
			<!-- RIGHT CONTENT STARTS HERE --><strong>Subject Transfer request has
			been disapproved by the Coordinating center. </strong></font></c:when>
		<c:when test="${epoch_unapproved}">
			<font color="Red"><!-- LEFT FORM STARTS HERE -->
			<!-- RIGHT CONTENT STARTS HERE --><strong>Subject not transferred. Subject Registration record has
			been successfully saved.</strong></font></c:when>
		</c:choose>
	</c:otherwise>
	</c:choose>
	<br>
	<table width="100%">
		<tr>
			<td width="20%"><img
				src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
				class="heightControl"></td>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label">Subject MRN:</td>
			<td>${command.participant.primaryIdentifier}</td>
		</tr>
		<tr>
			<td class="label">Study Sponsor Identifier:</td>
			<td>${command.studySite.study.organizationAssignedIdentifiers[0].value}</td>
		</tr>
		<tr>
			<td class="label">Study Short Title:</td>
			<td valign="top">${command.studySite.study.shortTitleText}</td>
		</tr>
		<tr>
			<td class="label">Registration Status:</td>
			<td valign="top">${command.regWorkflowStatus.code }</td>
		</tr>		
		<tr>
			<td class="label">Current Epoch:</td>
			<td valign="top">${command.scheduledEpoch.epoch.name}</td>
		</tr>
		<tr>
			<td class="label">Current Epoch Status:</td>
			<td valign="top">${command.scheduledEpoch.scEpochWorkflowStatus.code}</td>
		</tr>
		<c:if test="${!empty armAssigned}">
			<tr>
				<td class="label">${armAssignedLabel }:</td>
				<td valign="top">${armAssigned}</td>
			</tr>
		</c:if>
		<tr>
			<td class="label">Data Entry Status:</td>
			<td valign="top">${command.dataEntryStatusString }</td>
		</tr>
		<tr>
			<td class="label">Site:</td>
			<td>${command.studySite.healthcareSite.name}</td>
		</tr>
		<tr>
			<td class="label">Registration Date:</td>
			<td><fmt:formatDate type="date" value="${command.startDate }"/></td>
		</tr>
		<tr>
			<td class="label">Treating Physician:</td>
			<td>${command.treatingPhysicianFullName}</td>
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
	<table width="60%">
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
</tags:panelBox>
<c:if test="${actionRequired}">
	<registrationTags:register registration="${command}" newReg="${newRegistration}" actionButtonLabel="${actionLabel}"/>
</c:if>
</body>
</html>