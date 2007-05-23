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
	
	// need to disable validations while removing
	name = 'studySites['+selected+'].site';
	$(name).className='none';
	status = 'studySites['+selected+'].statusCode';
	$(status).className='none';
	date = 'studySites['+selected+'].irbApprovalDate';
	$(date).className='none';
	
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
<table border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<p id="instructions">
			Add StudySites associated with the Study
			<a href="javascript:fireAction('addStudySite','0');"><img
				src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add another Study Site"></a><br>
		</p>
		<br>
			<table id="mytable" border="0" cellspacing="0" cellpadding="0">
			<tr align="left" class="label">
				<th scope="col" align="left"><b>HealthCare Site</b><span class="red">*</span></th>
				<th scope="col" align="left"><b>Status<span class="red">*</span></th>
				<th scope="col" align="left"><b>Activation&nbsp;Date</b> </th>
				<th scope="col" align="left"><b>IRB&nbsp;Approval&nbsp;Date</b></th>
				<th scope="col" class="specalt" align="left"></th>
			</tr>
			<c:forEach items="${command.studySites}" varStatus="status">
				<tr>
					<td class="alt"><form:select path="studySites[${status.index}].site" cssClass="validate-notEmpty">
						<option value="">--Please Select--</option>
						<form:options items="${healthCareSitesRefData}" itemLabel="name" itemValue="id" />
						</form:select></th>
					<td class="alt"><form:select path="studySites[${status.index}].statusCode" cssClass="validate-notEmpty">
						<option value="">--Please Select--</option>
						<form:options items="${studySiteStatusRefData}" itemLabel="desc"
							itemValue="desc" />
					</form:select></td>
					<!--TODO:HACK Remove this once more roles are present -->
					<input type="hidden" name="studySites[${status.index}].roleCode" value="Affiliate Site"/>
						
					<td class="alt"><tags:dateInput path="studySites[${status.index}].startDate" />&nbsp;&nbsp;&nbsp;<span
						class="red"><em></em></span></td>
					<td class="alt"><tags:dateInput path="studySites[${status.index}].irbApprovalDate" />&nbsp;&nbsp;&nbsp;<span
						class="red"><em></em></span></td>
					<td class="specalt"class="specalt"><a href="javascript:fireAction('removeStudySite',${status.index});"><img
						src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
				
				</tr>
			</c:forEach>
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