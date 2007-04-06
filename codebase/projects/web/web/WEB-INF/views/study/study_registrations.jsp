<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script language="JavaScript" id="js1">
	var cal1 = new CalendarPopup();
</script>
<script>
function fireAction(action, selected){
	document.getElementsByName('_target3')[0].name='_target2';
	document.studySiteForm._action.value=action;
	document.studySiteForm._selected.value=selected;
	document.studySiteForm.submit();
}
</script>
</head>

<!-- MAIN BODY STARTS HERE -->
<body>
<form:form name="form" method="post">
<tabs:tabFields tab="${tab}" />
<div>
<tabs:division id="Summary">
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
	<tr align="center" class="label">
		<td width="20%" align="left"><b>Primary Id</b></td>	
		<td width="20%" align="left"><b>Study Site</b></td>
		<td width="20%" align="left"><b>Registration Date</b></td>	
		<td width="20%" align="left"><b>Informed Consent Signed Date</b></td>		
		<td width="20%" align="left"><b>Subject</b></td>
	</tr>
	<c:forEach items="${participantAssignments}" var="partAssgn">
		<tr align="center" class="results">
		<td>${partAssgn.participant.primaryIdentifier}</td>			
		<td>${partAssgn.studySite}</td>
		<td>${partAssgn.participant.startDate}</td>
		<td>${partAssgn.participant.informedConsentSignedDate}</td>
		<td>${partAssgn.participant.lastName}</td>
		</tr>
	</c:forEach>
	<tr>
		<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
		class="heightControl"></td>
	</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</tabs:division>
</div>
</form:form>
</body>
</html>