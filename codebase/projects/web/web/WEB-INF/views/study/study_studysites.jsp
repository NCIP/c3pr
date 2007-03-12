<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<link href="resources/search.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script language="JavaScript" id="js1">
	var cal1 = new CalendarPopup();
</script>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function validatePage(){
	return true;
}
function fireAction(action, selected){
	if(validatePage()){
		document.studySiteForm._action.value=action;
		document.studySiteForm._selected.value=selected;
		document.studySiteForm.submit();
	}
}
</script>
</head>
<body>
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<tr>
			<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->
			<td id="current">Short Title: ${command.trimmedShortTitleText}</td>
			<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
	</tr>

	<tr>

		<td class="display"><!-- TABS LEFT START HERE -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
							align="absmiddle">
						1.study details <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
							align="absmiddle"></span> <span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						2.identifiers <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
							align="absmiddle"></span><span class="current"><img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16" align="absmiddle">
						3.study site <img src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						4. study design <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						5. review and submit <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"></span>
						</td>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotL"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="7"></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>

				<!-- LEFT CONTENT STARTS HERE -->

				<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
				<!-- RIGHT CONTENT STARTS HERE -->
				<form:form name="studySiteForm" method="post">
				<div><input type="hidden" name="_page" value="2">
					<input type="hidden" name="_action" value="">
					<input type="hidden" name="_selected" value="">
				</div>
					<strong>Step 2. Study Site </strong> (<span class="red">*</span>
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
								<td width="11%" align="center">Status Code<span class="red">*</span></td>
								<td width="11%" align="center">Role Code<span class="red">*</span></td>
								<td width="17%" align="center">Start Date (mm/dd/yyyy)<span class="red">*</span> </td>
								<td width="17%" align="center">End Date (mm/dd/yyyy)</td>
								<td width="17%" align="center">IRB Approval Date (mm/dd/yyyy)<span class="red">*</span></td>
							</tr>
							<c:forEach items="${command.studySites}" varStatus="status">
								<tr align="center" class="results">
									<td width="5%"><a href="javascript:fireAction('removeStudySite',${status.index});"><img
										src="<tags:imageUrl name="b-delete.gif"/>" border="0"></a></td>
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
										path="studySites[${status.index}].endDate" />&nbsp;<a href="#"
											onClick="cal1.select(document.getElementById('studySites[${status.index}].endDate'),'anchor1','MM/dd/yyyy');return false;" name="anchor1" id="anchor1"><img
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
									src="<tags:imageUrl name="b-addLine.gif"/>" border="0" alt="Add another Study Site"></a>
								</td>
							</tr>
							</table>
						</td>

						<tr>
							<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="3"
								class="heightControl"></td>
						</tr>
						<tr>
							<td align="center" colspan="3"><!-- action buttons begins -->
							<table cellpadding="4" cellspacing="0" border="0">
								<tr>
									<td colspan=2 valign="top"><br>
										<br>
										<input type="image" name="_target1" src="<tags:imageUrl name="b-prev.gif"/>" border="0"
											alt="goto previous page">
										<input type="image" name="_target0" src="<tags:imageUrl name="b-startOver.gif"/>" border="0"
											alt="start over from start page">
										<input type="image" name="_target3" src="<tags:imageUrl name="b-continue.gif"/>" border="0"
											alt="continue to next page">
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</form:form></td>
			</tr>
		</table>
		</td>
		<!-- LEFT CONTENT ENDS HERE -->
	</tr>
</table>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
