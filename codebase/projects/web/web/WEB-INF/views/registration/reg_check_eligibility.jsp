<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
function add(){
var action = confirm ("You have not completed adding this protocol.\r\rStarting over will lose current protocol data?")
if (action){
	parent.window.location="reg_enroll_patient.htm";
}}
function validatePage(){
	if(document.getElementById("eligibilityIndicator").checked==true)
		return true;
	else
		return false;
}
function updateTargetPage(s){
	if(validatePage()){
		document.checkEligibility.nextView.value=s;
		document.checkEligibility.submit();
	}
	else
		alert("Eligibility check is must...");
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
						3. <a href="javascript:updateTargetPage('enrollView');">Enrollment
						Details </a> <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
							align="absmiddle"></span><span class="current"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						4. Check Eligibility <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3"
							height="16" align="absmiddle"></span><span class="tab"><img
							src="<tags:imageUrl name="tabGrayL.gif"/>" width="3" height="16" align="absmiddle">
						5. Stratify <img src="<tags:imageUrl name="tabGrayR.gif"/>" width="3" height="16"
							align="absmiddle"><img src="<tags:imageUrl name="tabGrayL.gif"/>" width="3"
							height="16" align="absmiddle"> 6. Review and Submit <img
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
				<div class="workArea"><img src="<tags:imageUrl name="tabWhiteR.gif"/>"
					width="3" height="16" align="absmiddle"> <img
					src="<tags:imageUrl name="tabWhiteR.gif"/>" width="3" height="16" align="absmiddle">

				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<form:form name="checkEligibility" method="post">
						<tr>
							<!-- CURRENT DRIVER/UNIT TITLE STARTS HERE -->

							<td id="current">Check Eligibility for
							${command.participant.firstName} ${command.participant.lastName}
							on ${command.studySite.study.trimmedShortTitleText}</td>
							<!-- CURRENT DRIVER/UNIT TITLE ENDS HERE -->
						</tr>
						<tr>

							<td class="display"><!-- TABS LEFT START HERE -->
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>

									<!-- LEFT CONTENT STARTS HERE -->
									<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
									<!-- RIGHT CONTENT STARTS HERE --> <input type="hidden"
										name="nextView" value="">
									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr>
											<td width="50%" valign="top">

											<table width="308" border="0" cellspacing="0" cellpadding="0"
												id="table1">
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
														class="heightControl"></td>
												</tr>
												<tr>
													<td class="label" align=left><span class="red">*</span><em></em>Eligibility
													Indicator</td>
													<td class="label"><form:checkbox
														path="eligibilityIndicator" id ="eligibilityIndicator"/></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>

									<table width="700" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<tr align="left">
											<td colspan=2 valign="top"><br>
											<br>
											<a href=""><img
												src="<tags:imageUrl name="b-startOver.gif"/>" alt="Start Over" width="67"
												height="16" border="0"></a></td>
											<td colspan=2 valign="top"><br>
											<br>
											<a href="javascript:updateTargetPage('stratifyView');"><img
												src="<tags:imageUrl name="b-continue.gif"/>" alt="Continue" width="59"
												height="16" border="0"></a></td>
										</tr>
									</table>
									</div>
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
		</table>
		</td>
	</tr>
</table>
<div id="copyright"></div>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
