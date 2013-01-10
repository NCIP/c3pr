<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
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
function fireAction(action, selected){
	document.form._action.value=action;
	document.form._selected.value=selected;
	document.form.submit();
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
	title="Edit Registration: ${command.participant.firstName}  ${command.participant.lastName}">
	<form:form method="post" name="form" id="form">
		<div><input type="hidden" name="_page" id="_page" value="1"> <input
			type="hidden" name="_action" id="_action" value=""> <input
			type="hidden" name="_selected" id="_selected" value=""></div>
		<table border="0" id="table1" cellspacing="10" width="100%">

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
								<table width="650" border="0" cellspacing="10" cellpadding="0"
									id="table1">
									<tr align="center" class="label">
										<th width="10%" align="left" class="label"></th>
										<th width="15%" align="left" class="label"><span class="red">*</span>
										<fmt:message key="c3pr.common.assigningAuthority"/></th>
										<th width="15%" align="left" class="label"><span class="red">*</span>
										<fmt:message key="c3pr.common.identifierType"/></th>
										<th width="15%" align="left" class="label"><span class="red">*</span><fmt:message key="c3pr.common.identifier"/></th>
										<th width="15%" align="left" class="label"><fmt:message key="c3pr.common.primaryIndicator"/></th>
										<th width="15%" align="left" class="label"></th>
									</tr>
									<c:forEach items="${command.studySubject.identifiers}" varStatus="status">
										<tr align="center" class="results">
											<td width="10%"><a
												href="javascript:fireAction('removeIdentifier',${status.index});"><img
												src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
											<td width="20%"><form:input
												path="studySubject.identifiers[${status.index}].source" /></td>
											<td width="20%"><form:input
												path="studySubject.identifiers[${status.index}].type" /></td>
											<td width="20%"><form:input
												path="studySubject.identifiers[${status.index}].value"
												onclick="javascript:clearField(this)();" /></td>
											<td width="25%" align="center"><form:radiobutton
												path="studySubject.identifiers[${status.index}].primaryIndicator"
												value="true" /></td>
											<td width="10%"><em></em><span class="red"><form:errors
												path="studySubject.identifiers[${status.index}].type" /> <form:errors
												path="studySubject.identifiers[${status.index}].source" /> <form:errors
												path="studySubject.identifiers[${status.index}].value" /><em></em></span></td>
										</tr>
									</c:forEach>
									<tr>
										<td align="center"><a
											href="javascript:fireAction('addIdentifier','0');"><img
											src="<tags:imageUrl name="checkyes.gif"/>" border="0"
											alt="Add another Identifier"></a></td>
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
