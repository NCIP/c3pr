<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<link href="calendar-blue.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script language="JavaScript" id="js1">
	var cal1 = new CalendarPopup();
</script>
<script>
function fireAction(action, selected){
	document.getElementById('command').targetPage.name='_noname';
	document.studySiteForm._action.value=action;
	document.studySiteForm._selected.value=selected;
	document.studySiteForm.submit();
}
</script>
</head>
<body>
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
			<p id="instructions">
				Add StudySites associated with the Study (<span class="red">*</span><em>Required Information </em>)<br>
				Use Date format as (mm/dd/yyyy)
			</p>
			<br>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="table1">

				<td width="100%" valign="top">
					<table width="100%" border="0" cellspacing="5" cellpadding="0"
						id="table1">

					<tr align="left" class="label">
						<td width="5%" align="left"><a href="javascript:fireAction('addStudySite','0');"><img
							src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add another Study Site"></a>
						</td>
						<td width="11%" align="left"><b>HealthCare Site</b><span class="red">*</span></td>
						<td width="11%" align="left"><b>Status<span class="red">*</span></td>
						<td width="11%" align="left"><b>Role<span class="red">*</span></td>
						<td width="17%" align="left"><b>Activation Date</b><span class="red">*</span> </td>
						<td width="17%" align="left"><b>IRB Approval Date</b><span class="red">*</span></td>
					</tr>
					<c:forEach items="${command.studySites}" varStatus="status">
						<tr class="results">
							<td width="5%"><a href="javascript:fireAction('removeStudySite',${status.index});"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
							<td width="11%"><form:select path="studySites[${status.index}].site">
								<option value="" selected>--Please Select--<form:options items="${healthCareSitesRefData}" itemLabel="name" itemValue="id" />
								</form:select></td>
							<td width="11%"><form:select path="studySites[${status.index}].statusCode">
								<option value="" selected>--Please Select--<form:options items="${studySiteStatusRefData}" itemLabel="desc"
									itemValue="desc" />
							</form:select></td>
							<td width="11%"><form:select path="studySites[${status.index}].roleCode">
								<option value="" selected>--Please Select--<form:options items="${studySiteRoleCodeRefData}" itemLabel="desc"
									itemValue="desc" />
							</form:select></td>
							<td width="17%">
								<tags:dateInput path="studySites[${status.index}].startDate" />&nbsp;&nbsp;&nbsp;<span
									class="red"><em></em></span></td>
							<td width="17%">
								<tags:dateInput path="studySites[${status.index}].irbApprovalDate" />&nbsp;&nbsp;&nbsp;<span
									class="red"><em></em></span></td>
						</tr>
					</c:forEach>
					</table>
				</td>
			</table>
		</td>
	</tr>
</table>
<!-- MAIN CONTENT ENDS HERE -->
</tabs:division>
</form:form>
</table>
</div>
</body>
</html>