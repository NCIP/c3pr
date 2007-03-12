<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script language="JavaScript" type="text/JavaScript">
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
</script>
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script language="JavaScript" id="js1">
	var cal1 = new CalendarPopup();
</script>

<script language="JavaScript" type="text/JavaScript">
function submitSearchPage(){
	document.getElementById("searchForm").submit();
}
function updatePage(s){
	document.getElementById("_page").name=s;
	document.getElementById("_page").value="next";
	document.getElementById("form").submit();
}
function validatePage(){
	return true;
}
function fireAction(action){

	if(validatePage()){
		document.form._action.value=action;
		document.form.submit();
	}
}
</script>
</head>
<body>

<tags:search action="searchStudy"/>

<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td id="current">Short Title: ${command.trimmedShortTitleText}</td>
		<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
	</tr>
	<tr>
		<td class="display">
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
						<td width="100%" id="tabDisplay"><span class="current"><img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"> Details <img src="<tags:imageUrl name="tabWhiteR.gif"/>"
							width="3" height="16" align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						<a href="javascript:updatePage('_target1');">Identifiers</a> <img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16" align="absmiddle"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						<a href="javascript:updatePage('_target2');">Study Sites</a> <img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16" align="absmiddle"><img
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
				<div><input type="hidden" name="_page" id="_page" value="0">
				<input type="hidden" name="_action" value="0"></div>
				<td valign="top" class="contentL"><!-- LEFT CONTENT STARTS HERE -->
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
				<td valign="top" class="contentL">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="details">
					<tr>
						<td height="2" border="0"><b><span class="black"></span></b></td>
					</tr>
					<tr>
						<td align="left" width="50%" border="0" valign="top" class="contentAreaL">
						<form name="form2" method="post" action="" id="form1">
						<table width="700" border="0" cellspacing="0" cellpadding="0"
							id="table1">
							<td width="50%" valign="top" class="contentAreaL">
								<br>
								<table width="50%" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td class="label">Short Title:</td>
										<td><form:textarea path="shortTitleText" rows="2"
											cols="30" /></td>
										<td width="15%"></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em>Long
										Title:</td>
										<td><form:textarea path="longTitleText" rows="2" cols="30" /></td>
										<td width="15%"><em><span class="red"><form:errors
											path="longTitleText" /></em></span></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label">Precis Text:</td>
										<td><form:textarea path="precisText" rows="2" cols="30" /></td>
										<td width="15%"></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label">Description Text:</td>
										<td><form:textarea path="descriptionText" rows="2"
											cols="30" /></td>
										<td width="15%"></td>
									</tr>
								</table>
								<td>
								<table width="50%" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td class="label">Target Accrual Number:</td>
										<td><form:input path="targetAccrualNumber" size="34" /></td>
										<td width="10%"></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em>Status:</td>
										<td><form:select path="status">
											<option value="">--Please Select-- <form:options
												items="${statusRefData}" itemLabel="desc" itemValue="desc" />
										</form:select></td>
										<td width="10%"><em><span class="red"><form:errors
											path="status" /></em></span></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em><strong>Disease:</strong>
										<td><form:select path="diseaseCode">
											<option value="">--Please Select-- <form:options
												items="${diseaseCodeRefData}" itemLabel="desc"
												itemValue="desc" />
										</form:select></td>
										<td width="10%"><em><span class="red"><form:errors
											path="diseaseCode" /></em></span></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><em></em>Monitor:</td>
										<td><form:select path="monitorCode">
											<option value="">--Please Select-- <form:options
												items="${monitorCodeRefData}" itemLabel="desc"
												itemValue="desc" />
										</form:select></td>
										<td width="10%"></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em>Phase:</td>
										<td><form:select path="phaseCode">
											<option value="">--Please Select-- <form:options
												items="${phaseCodeRefData}" itemLabel="desc" itemValue="desc" />
										</form:select></td>
										<td width="10%"><em><span class="red"><form:errors
											path="phaseCode" /></em></span></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em>Sponsor:</td>
										<td><form:select path="sponsorCode">
											<option value="">--Please Select-- <form:options
												items="${sponsorCodeRefData}" itemLabel="desc"
												itemValue="desc" />
										</form:select></td>
										<td width="10%"><em><span class="red"><form:errors
											path="sponsorCode" /></em></span></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em>Randomized
										Indicator</td>
										<td><form:checkbox path="randomizedIndicator" /></td>
										<td width="15%"></td>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label">Multi	Institution:</td>
										<td><form:checkbox path="multiInstitutionIndicator" /></td>
										<td width="15%"></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label">Blinded Indicator:</td>
										<td><form:checkbox path="blindedIndicator" /></td>
										<td width="15%"></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td class="label"><span class="red">*</span><em></em>Type:</td>
										<td><form:select path="type">
											<option value="">--Please Select-- <form:options
												items="${typeRefData}" itemLabel="desc" itemValue="desc" />
										</form:select></td>
										<td width="15%"><em><span class="red"><form:errors
											path="type" /></em></span></td>
									</tr>
								</table>
							</td>

							<tr>
								<td align="center" colspan="3"><!-- action buttons begins -->
								<table cellpadding="4" cellspacing="0" border="0">
									<tr>
									<td colspan=2 valign="top"><br>
										<br>
										<a href="javascript:fireAction('update');"><img
											src="<tags:imageUrl name="b-saveChanges.gif"/>" border="0" alt="Save the Changes"></a>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</form>

						</td>
					</tr>
				</table>
				</td>
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
