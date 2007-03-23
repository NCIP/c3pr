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
<body>
<tabs:body title="${flow.name}: ${tab.longTitle} - Short Title: ${command.trimmedShortTitleText}">
<form:form name="studySiteForm" method="post">
<div>
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
</div>
<tabs:tabFields tab="${tab}" />
<div><tabs:division id="study-studysites">

<!-- MAIN BODY STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
			(<span class="red">*</span>
			<em>Required Information </em>)<br>
			<br>
			<table width="70%" border="0" cellspacing="0" cellpadding="0"
				id="table1">

				<td width="100%" valign="top">
					<table width="100%" border="0" cellspacing="10" cellpadding="0"
						id="table1">

					<tr align="center" class="label">
						<td width="5%" align="center"></td>
						<td width="11%" align="center">HealthCare Site<span class="red">*</span></td>
						<td width="11%" align="center">Status<span class="red">*</span></td>
						<td width="11%" align="center">Role<span class="red">*</span></td>
						<td width="17%" align="center">Start Date (mm/dd/yyyy)<span class="red">*</span> </td>
						<td width="17%" align="center">IRB Approval Date (mm/dd/yyyy)<span class="red">*</span></td>
					</tr>
					<c:forEach items="${command.studySites}" varStatus="status">
						<tr align="center" class="results">
							<td width="5%"><a href="javascript:fireAction('removeStudySite',${status.index});"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
							<td width="11%"><form:select path="studySites[${status.index}].site">
								<form:options items="${healthCareSitesRefData}" itemLabel="name" itemValue="id" />
								</form:select></td>
							<td width="11%"><form:select path="studySites[${status.index}].statusCode">
								<form:options items="${studySiteStatusRefData}" itemLabel="desc"
									itemValue="desc" />
							</form:select></td>
							<td width="11%"><form:select path="studySites[${status.index}].roleCode">
								<form:options items="${studySiteRoleCodeRefData}" itemLabel="desc"
									itemValue="desc" />
							</form:select></td>
							<td width="17%"><form:input
								path="studySites[${status.index}].startDate" />&nbsp;<a href="#"
									onClick="cal1.select(document.getElementById('studySites[${status.index}].startDate'),'anchor1','MM/dd/yyyy');return false;" name="anchor1" id="anchor1"><img
								src="<tags:imageUrl name="b-calendar.gif"/>" alt="Calendar"
								height="16" border="0"></a></td>
							<td width="17%"><form:input
								path="studySites[${status.index}].irbApprovalDate" />&nbsp;<a href="#"
									onClick="cal1.select(document.getElementById('studySites[${status.index}].irbApprovalDate'),'anchor1','MM/dd/yyyy');return false;" name="anchor1" id="anchor1"><img
								src="<tags:imageUrl name="b-calendar.gif"/>" alt="Calendar"
								height="16" border="0"></a></td>
						</tr>
					</c:forEach>
					<tr>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
						class="heightControl"></td>
					</tr>
					<tr>
						<td align="center"><a href="javascript:fireAction('addStudySite','0');"><img
							src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add another Study Site"></a>
						</td>
					</tr>
					</table>
				</td>
			</table>
		</td>
	</tr>
</table>
<!-- MAIN CONTENT ENDS HERE -->
</tabs:division>
</form:form>
</tabs:body>
</table>
</div>
</body>
</html>