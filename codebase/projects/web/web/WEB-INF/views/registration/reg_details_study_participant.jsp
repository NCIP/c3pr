<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>

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
function updateAction(action){
		document.getElementById("_updateaction").value=action;
		document.getElementById("form1").submit();
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
	title="Edit Registration: ${command.studySubject.participant.firstName}  ${command.studySubject.participant.lastName}">
	<form:form method="post" name="form" id="form">
		<div><input type="hidden" name="_page" id="_page" value="0"> <input
			type="hidden" name="_action" id="_action" value=""> <input
			type="hidden" name="_selected" id="_selected" value=""></div>
		<table border="10" id="table1" cellspacing="10" width="100%">

			<table border="0" id="table1" cellspacing="10" width="100%">
				<tr>
					<td valign="top" width="30%"><registrationTags:participantSummary />
					</td>
					<td width="40%" valign="top"><tabs:levelTwoTabs tab="${tab}"
						flow="${flow}" showNumber="false" /> <tabs:division id="Editing">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="details">
							<tr>
								<td align="left" width="50%" border="0" valign="top"
									class="contentAreaL">
								<form name="form2" method="post" action="" id="form1">
								<div><input type="hidden" name="_updateaction"
									id="_updateaction" value=""></div>
								<table width="650" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td width="200" align="right"><em></em><b> Primary Identifier:<b>&nbsp;</td>
										<td align="left">${command.studySubject.primaryIdentifier}</td>
									</tr>
									<tr>
										<td width="200" align="right"><em></em><b>Informed Consent
										Version:<b>&nbsp;</td>
										<td align="left">${command.studySubject.informedConsentVersion}</td>
									</tr>
									<tr>
										<td width="200" align="right"><em></em><b>Informed Consent
										Signed Date:<b>&nbsp;</td>
										<td align="left" valign="top">${command.studySubject.informedConsentSignedDateStr}</td>
									</tr>
									<tr>
										<td width="200" align="right"><em></em><b> Treating Physician:<b>&nbsp;</td>
										<td align="left">${command.studySubject.treatingPhysicianFullName}</td>
									</tr>
								</table>
								</form>
							</tr>
							<tr>
								<td align="center" colspan="3"><!-- action buttons begins -->
								<table cellpadding="4" cellspacing="0" border="0">
									<tr>
										<td colspan=2 valign="top"><br>
										<br>
										<a href="javascript:updateAction('update');"><img
											src="<tags:imageUrl name="b-saveChanges.gif"/>" border="0"
											alt="Save the Changes"></a>
									</tr>
								</table>
								</td>
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
					<registrationTags:registrationsSubjectHistory />
				</tr>


			</table>
			</form:form>
			</tabs:body>
			<!-- MAIN BODY ENDS HERE -->
</body>
</html>
