<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
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
<script language="JavaScript" type="text/JavaScript">
function updatePage(s){
	document.getElementById("_page").name=s;
	document.getElementById("_page").value="next";
	document.getElementById("form").submit();
}
function validatePage(){
	return true;
}
function fireAction(action, selected){
	if(validatePage()){
		document.form._action.value=action;
		document.form._selected.value=selected;
		document.form.submit();
	}
}
function clearField(field){
field.value="";
}
</script>
</script>
</head>
<body>

<tags:search action="searchStudy"/>

<!-- MAIN BODY STARTS HERE -->


<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td id="current">Short Title: ${command.trimmedShortTitleText}</td>
		<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
	</tr>
	<tr>
		<td class="display" width="100%">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<!-- TABS LEFT START HERE -->
				<td width="27%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="current"><img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"> Study Summary <img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"></span></td>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotL"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="7"></td>
					</tr>
				</table>
				</td>
				<td><img src="<tags:imageUrl name="spacer.gif"/>" width="2" height="1"></td>
				<!-- TABS CENTER START HERE -->
				<td width="45%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16"
							align="absmiddle">
						<a href="javascript:updatePage('_target0');">Details</a> <img src="<tags:imageUrl name="tabGrayR.gif"/>"
							width="3" height="16" align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						<a href="javascript:updatePage('_target1');">Identifiers</a><img src="<tags:imageUrl name="tabGrayR.gif"/>"
							width="3" height="16" align="absmiddle"></span> <span class="current">
							<img src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16" align="absmiddle">
						Study Sites<img	src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16" align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						<a href="javascript:updatePage('_target3');">Study Design</a> <img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16" align="absmiddle"></span></td>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotR"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="7"></td>
					</tr>
				</table>
				</td>
				<td><img src="<tags:imageUrl name="spacer.gif"/>" width="2" height="1"></td>
				<!-- TABS LEFT START HERE -->
				<td width="27%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="current"><img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle">Participant Assignments <img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"></span></td>
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
				<form:form name="form" id="form" method="post">
				<div><input type="hidden" name="_page" id="_page" value="2">
				<input type="hidden" name="_action" value="">
				<input type="hidden" name="_selected" value=""></div>
				<td width="27%" valign="top" class="contentL"><!-- LEFT CONTENT STARTS HERE -->
						<table width="100%" border="0" cellspacing="2" cellpadding="0"
						id="table1">
						<tr valign="top">
							<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
								class="heightControl" /></td>
						</tr>
						<tr>
							<td class="label">Short Title:</td>
							<td>${command.trimmedShortTitleText}</td>
						</tr>
						<tr>
							<td class="label">Primary Identifier:</td>
							<td>${command.primaryIdentifier}</td>
						</tr>
						<tr>
							<td class="label">Target Accrual No:</td>
							<td>${command.targetAccrualNumber}</td>
						</tr>
						<tr>
							<td class="label">Status:</td>
							<td>${command.status}</td>
						</tr>
						<tr>
							<td class="label">Sponsor:</td>
							<td>${command.sponsorCode}</td>
						</tr>
						<tr>
							<td class="label">Type:</td>
							<td>${command.type}</td>
						</tr>
					</table>
				<!-- LEFT CONTENT ENDS HERE --></td>
				<td><img src="<tags:imageUrl name="spacer.gif"/>" width="2" height="1"></td>
				<!-- CENTER CONTENT STARTS HERE -->
				<td width="47%" valign="top" class="contentL">
				<table width="50%" border="0" cellspacing="0" cellpadding="0"
						id="table1">

						<td width="70%" valign="top">
							<table width="100%" border="0" cellspacing="10" cellpadding="0"
								id="table1">

							<tr align="center" class="label">
								<td width="5%" align="center"></td>
								<td width="11%" align="center">HealthCare Site<span class="red">*</span></td>
								<td width="11%" align="center">Status Code<span class="red">*</span></td>
								<td width="11%" align="center">Role Code<span class="red">*</span></td>
								<td width="17%" align="center">Start Date <br><em> (mm/dd/yyyy)</em><span class="red">*</span> </td>
								<td width="17%" align="center">End Date <br><em> (mm/dd/yyyy)</em></td>
								<td width="17%" align="center">IRB Approval<br>Date<em> (mm/dd/yyyy)</em><span class="red">*</span></td>
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
									<a href="javascript:fireAction('update','0');"><img
										src="<tags:imageUrl name="b-saveChanges.gif"/>" border="0" alt="Save Changes"></a>
								</tr>
							</table>
							</td>
					    </tr>
					</table>	</td>
				<td><img src="<tags:imageUrl name="spacer.gif"/>" width="2" height="1"></td>
				<!-- RIGHT CONTENT STARTS HERE -->
				<td width="29%" valign="top" class="contentR">
				<strong>Participant Assigned to Study</strong><br>
				<br>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="table1">
					<tr align="center" class="label">
						<td width="35%" align="center">Last Name</td>
						<td width="35%" align="center">Start Date</td>
						<td width="35%" align="center">Primary Id</td>
					</tr>
					<c:forEach items="${participantAssignments}" var="partAssgn">
						<tr align="center" class="results">
						<td>${partAssgn.participant.lastName}</td>
						<td>${partAssgn.startDateStr}</td>
						<td>${partAssgn.participant.primaryIdentifier}</td>
						</tr>
					</c:forEach>
					<tr>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
						class="heightControl"></td>
					</tr>
				</table>
				</td>
				<!-- LEFT CONTENT ENDS HERE --></td>
			</form:form>
			</tr>
		</table>
		</td>
	</tr>
</table>
<div id="copyright"></div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
