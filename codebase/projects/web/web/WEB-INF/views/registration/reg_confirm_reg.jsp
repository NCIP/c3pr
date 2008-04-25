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
<link href="resources/styles.css" rel="stylesheet" type="text/css">
<link href="resources/search.css" rel="stylesheet" type="text/css">
<script>
function accessApp(url,targetWindow){
	$('hotlinksForm').target=targetWindow;
	$('hotlinksForm').action=url;
	$('hotlinksForm').submit();
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

<br/>
	<!-- newRegistration: ${newRegistration}<br>
	reg_registered :${reg_registered }<br>
	reg_nonenrolled:${reg_nonenrolled }<br>
	reg_pending:${reg_pending }<br>
	reg_disapproved:${ reg_disapproved}<br>
	reg_reserved:${reg_reserved }<br>
	reg_unregistered:${reg_unregistered }<br>
	reg_unrandomized:${ reg_unrandomized}<br>
	epoch_registered :${epoch_approved }<br>
	epoch_nonenrolled:${epoch_nonenrolled }<br>
	epoch_pending:${epoch_pending }<br>
	epoch_disapproved:${ epoch_disapproved}<br>
	epoch_unapproved:${epoch_unapproved }<br>
	epoch_unrandomized:${ epoch_unrandomized}<br>-->
	
	<c:choose>
	<c:when test="${newRegistration}">
		<c:choose>
		<c:when test="${reg_registered}">
			<font color='<fmt:message key="REGISTRATION.SUCCESS.COLOR"/>'><strong><fmt:message key="REGISTRATION.SUCCESS"/> Please <a href="javascript:C3PR.printElement('printable');">print</a>
			and save this confirmation in the subject study records </strong></font></c:when>
		<c:when test="${reg_pending}">
			<font color='<fmt:message key="REGISTRATION.PENDING.COLOR"/>'><strong><fmt:message key="REGISTRATION.PENDING"/> </strong></font></c:when>
		<c:when test="${reg_disapproved}">
			<font color='<fmt:message key="REGISTRATION.DISAPPROVED.COLOR"/>'><strong><fmt:message key="REGISTRATION.DISAPPROVED"/> </strong></font></c:when>
		<c:when test="${reg_reserved}">
			<font color='<fmt:message key="REGISTRATION.RESERVED.COLOR"/>'><strong><fmt:message key="REGISTRATION.RESERVED"/> Please <a href="javascript:C3PR.printElement('printable');">print</a>
			and save this confirmation in the subject study records </strong></font></c:when>
		<c:when test="${reg_nonenrolled }">
			<font color='<fmt:message key="REGISTRATION.NONENROLLED.COLOR"/>'><strong><fmt:message key="REGISTRATION.NONENROLLED"/></strong></font></c:when>			
		<c:when test="${reg_unrandomized}">
			<font color='<fmt:message key="REGISTRATION.UNRANDOMIZED.COLOR"/>'><strong><fmt:message key="REGISTRATION.UNRANDOMIZED"/></strong></font></c:when>			
		<c:otherwise>
			<font color='<fmt:message key="REGISTRATION.INCOMPLETE.COLOR"/>'><strong><fmt:message key="REGISTRATION.INCOMPLETE"/></strong></font></c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:choose>
		<c:when test="${epoch_approved}">
			<font color='<fmt:message key="TRANSFER.SUCCESS.COLOR"/>'><strong><fmt:message key="TRANSFER.SUCCESS"/></strong></font></c:when>
		<c:when test="${epoch_pending}">
			<font color='<fmt:message key="TRANSFER.PENDING.COLOR"/>'><strong><fmt:message key="TRANSFER.PENDING"/></strong></font></c:when>
		<c:when test="${epoch_disapproved}">
			<font color='<fmt:message key="TRANSFER.DISAPPROVED.COLOR"/>'><strong><fmt:message key="TRANSFER.DISAPPROVED"/></strong></font></c:when>
		<c:when test="${epoch_nonenrolled}">
			<font color='<fmt:message key="TRANSFER.NONENROLLED.COLOR"/>'><strong><fmt:message key="TRANSFER.NONENROLLED"/></strong></font></c:when>
		<c:when test="${epoch_unrandomiized}">
			<font color='<fmt:message key="TRANSFER.UNRANDOMIZED.COLOR"/>'><strong><fmt:message key="TRANSFER.UNRANDOMIZED"/></strong></font></c:when>
		<c:otherwise>
			<font color='<fmt:message key="TRANSFER.INCOMPLETE.COLOR"/>'><strong><fmt:message key="TRANSFER.INCOMPLETE"/></strong></font></c:otherwise>
		</c:choose>
	</c:otherwise>
	</c:choose>
	<br><br>
	<div id="printable">
	<table width="50%" class="tablecontent">
		<tr>
			<td width="35%" align="left"><b>Subject MRN</b></td>
			<td>${command.participant.primaryIdentifier}</td>
		</tr>
		<tr>
			<td align="left"><b>Study Sponsor Identifier</b></td>
			<td>${command.studySite.study.organizationAssignedIdentifiers[0].value}</td>
		</tr>
		<tr>
			<td align="left"><b>Study Short Title</b></td>
			<td valign="top">${command.studySite.study.shortTitleText}</td>
		</tr>
		<tr>
			<td align="left"><b>Registration Status</b></td>
			<td valign="top">${command.regWorkflowStatus.code }</td>
		</tr>		
		<tr>
			<td align="left"><b>Current Epoch</b></td>
			<td valign="top">${command.scheduledEpoch.epoch.name}</td>
		</tr>
		<tr>
			<td align="left"><b>Current Epoch Status</b></td>
			<td valign="top">${command.scheduledEpoch.scEpochWorkflowStatus.code}</td>
		</tr>
		<c:if test="${!empty armAssigned}">
			<tr>
				<td align="left"><b>${armAssignedLabel }</b></td>
				<td valign="top">${armAssigned}</td>
			</tr>
		</c:if>
		<tr>
			<td align="left"><b>Data Entry Status</b></td>
			<td valign="top">${command.dataEntryStatusString }</td>
		</tr>
		<tr>
			<td align="left"><b>Site</b></td>
			<td>${command.studySite.healthcareSite.name}</td>
		</tr>
		<tr>
			<td align="left"><b>Registration Date</b></td>
			<td>${command.startDateStr }</td>
		</tr>
		<tr>
			<td align="left"><b>Enrolling Physician</b></td>
			<td>${command.treatingPhysicianFullName}</td>
		</tr>
	</table>
	<br>
	</div>
	<c:if test="${hotlinkEnable}">
	<table width="60%">
		<c:if test="${!empty caaersBaseUrl}">
		<tr>
			<td align="left"><a
				href="javascript:accessApp('${caaersBaseUrl }','_caaers');">
			<b>Adverse Event Reporting</a> </b></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
		</c:if>
		<c:if test="${!empty pscBaseUrl}">
		<tr>
			<td align="left"><a
				href="javascript:accessApp('${pscBaseUrl }','_psc');">
			<b>Study Calendar</a></b></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
		</c:if>
		<c:if test="${!empty c3dBaseUrl}">
		<tr>
			<td align="left"><a
				href="javascript:accessApp('${c3dBaseUrl }','_c3d');">
			<b>Clinical Database</a></b></td>
		</tr>
		</c:if>
	</table>
	</c:if>
</tags:panelBox>
<form id="hotlinksForm" action="" method="get">
<input type="hidden" name="assignment" value="${command.gridId }"/>
</form>
<c:if test="${actionRequired}">
	<registrationTags:register registration="${command}" newReg="${newRegistration}" actionButtonLabel="${actionLabel}" requiresMultiSite="${requiresMultiSite}"/>
</c:if>
</body>
</html>