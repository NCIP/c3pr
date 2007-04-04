<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<html>
<head>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function getPage(s){
	parent.window.location="reg_patient_search.htm";
}
function updatePage(s){
	document.getElementById("_page").name=s;
	document.getElementById("_page").value="next";
	document.getElementById("form").submit();
}
function clearField(field){
field.value="";
}
</script>
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script language="JavaScript" id="js1">
	var cal1 = new CalendarPopup();
</script>
</head>
<body>
<!-- MAIN BODY STARTS HERE -->

<tabs:body
	title="Edit Registration: ${command.participant.firstName}  ${command.participant.lastName}">
	<form:form method="post" name="form" id="form">
		<div><input type="hidden" name="_page" id="_page" value="0"> <input
			type="hidden" name="_action" id="_action" value=""> <input
			type="hidden" name="_selected" id="_selected" value=""> <input
			type="hidden" name="_updateaction" id="_updateaction" value=""></div>
		<table border="10" id="table1" cellspacing="10" width="100%">

			<table border="0" id="table1" cellspacing="10" width="100%">
				<tr>
					<td valign="top" width="30%"><registrationTags:participantSummary />
					</td>
					<td width="40%" valign="top"><tabs:levelTwoTabs tab="${tab}"
						flow="${flow}" showNumber="false"/> <tabs:division id="Editing">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="details">
							<tr>
								<td align="left" width="50%" border="0" valign="top"
									class="contentAreaL">
								<form name="form2" method="post" action="" id="form1">
								<table width="650" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td width="200" align="right"><em></em><b> Primary Identifier:<b>&nbsp;</td>
										<td align="left">${command.studySite.studyInvestigators[0].healthcareSiteInvestigator.investigator.nciIdentifier}</td>
									</tr>
									<tr>
										<td width="200" align="right"><em></em><b>Informed Consent
										Version:<b>&nbsp;</td>
										<td align="left">${command.informedConsentVersion}</td>
									</tr>
									<tr>
										<td width="200" align="right"><em></em><b>Informed Consent
										Signed Date:<b>&nbsp;</td>
										<td align="left" valign="top">${command.informedConsentSignedDateStr}</td>
									</tr>
									<tr>
										<td width="200" align="right"><em></em><b> Treating Physician:<b>&nbsp;</td>
										<td align="left">${command.treatingPhysician}</td>
									</tr>
								</table>
								</form>
							</tr>
							<c:forEach begin="1" end="7">
								<tr>
									<td><br>
									</td>
								</tr>
							</c:forEach>
						</table>
					</tabs:division></td>

					<td valign="top" width="30%"><registrationTags:studySummary /></td>
				</tr>
				<tr>
					<registrationTags:registrationHistory />
				</tr>


			</table>
			</form:form>
			</tabs:body>
			<!-- MAIN BODY ENDS HERE -->
</body>
</html>
