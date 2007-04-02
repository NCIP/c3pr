<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
function getPage(s){
	parent.window.location="reg_patient_search.htm";
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
function add(){
var action = confirm ("You have not completed adding this protocol.\r\rStarting over will lose current protocol data?")
if (action){
	parent.window.location="reg_enroll_patient.htm";
}}
function validatePage(){
	return true;
}
function doNothing(){
}
function updateTargetPage(s){
	if(validatePage()){
		document.getElementById("nextView").value=s;
		document.reviewForm.submit();
	}
}
function submitCaaersPage(){
	document.getElementById("caaersForm").submit();
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="display">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tabs">
					<tr>
						<td width="100%" id="tabDisplay"><span class="tab"> <img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"> 1. Select Study <img
							src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16"
							align="absmiddle"><img src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
							height="16" align="absmiddle"> 2. Select Subject <img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16" align="absmiddle"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						3. Enrollment Details <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						4. Check Eligibility <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						5. Stratify <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
							align="absmiddle"><img src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
							height="16" align="absmiddle"> 6. Review and Submit <img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16" align="absmiddle"></span><span
							class="current"><img src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
							height="16" align="absmiddle"> 7. Confirmation <img
							src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16" align="absmiddle"></span></td>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="7" height="1"></td>
					</tr>
					<tr>
						<td colspan="2" class="tabBotL"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="7"></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- MAIN BODY STARTS HERE -->
			<tr>
				<td>
				<div class="workArea">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<form:form name="reviewForm" method="post">
						<tr>
							<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

							<td id="current">Registration Successful for
							${command.participant.firstName} ${command.participant.lastName}
							on ${command.studySite.study.trimmedShortTitleText}</td>
							<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
						</tr>
						<tr>

							<td class="display"><!-- TABS LEFT START HERE -->
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>

									<!-- LEFT CONTENT STARTS HERE -->
									<td valign="top" class="additionals2"><font color="Green"><!-- LEFT FORM STARTS HERE -->
									<!-- RIGHT CONTENT STARTS HERE --> <input type="hidden"
										name="nextView"> <strong>Subject Registration
									has been successfully completed. Please <a
										href="javascript:doNothing()">print</a> and save this
									confirmation in the subject study records </strong></font><br>
									<table width="50%" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr>
											<td width="100%" valign="top">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" id="table1">
												<tr>
													<td width="20%"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
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
													<td class="label">Study Subject Identifier:</td>
													<td>${command.studyParticipantIdentifier}</td>
												</tr>
												<tr>
													<td class="label">Registration Date:</td>
													<td><%= edu.duke.cabig.c3pr.utils.DateUtil.getCurrentDate("MM/dd/yyyy")%></td>
												</tr>
												<tr>
													<td class="label">Site:</td>
													<td>${command.studySite.site.name}</td>
												</tr>
												<tr>
													<td class="label">Investigator:</td>
													<td></td>
												</tr>																																				
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
												</tr>
												<tr>
													<td class="label"><a
														href="javascript:updateTargetPage('randomizeView');">Click
													Here</a> to Assign Arms</td>
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
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
												</tr>
												<tr>
													<td class="label" align="left"><a
														href="javascript:accessApp('http://10.10.10.2:8041','studycalendar/pages/schedule?assignment=${command.gridId }','_psc');">
													<b>Study Calendar</a></b></td>
												</tr>
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
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
					</form:form>
				</table>
				</td>
			</tr>
			<tr>
				<td class="display">

				<form name="caaersForm" id="caaersForm" method="post"><input
					type="hidden" name="gridProxy" value="${proxy}"></form>

				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
