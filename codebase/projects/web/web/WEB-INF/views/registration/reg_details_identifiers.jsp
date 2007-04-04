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
function fireAction(action, selected){
	document.form._action.value=action;
	document.form._selected.value=selected;
	document.form.submit();
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
			type="hidden" name="_selected" id="_selected" value=""> <input
			type="hidden" name="_updateaction" id="_updateaction" value=""></div>
		<table border="0" id="table1" cellspacing="10" width="100%">

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
								<table width="650" border="0" cellspacing="10" cellpadding="0"
									id="table1">
									<tr align="center" class="label">
										<th width="10%" align="left" class="label"></th>
										<th width="15%" align="left" class="label"><span class="red">*</span>Assigning
										Authority</th>
										<th width="15%" align="left" class="label"><span class="red">*</span>
										Identifier Type</th>
										<th width="15%" align="left" class="label"><span class="red">*</span>Identifier</th>
										<th width="15%" align="left" class="label">Primary Indicator</th>
										<th width="15%" align="left" class="label"></th>
									</tr>
									<c:forEach items="${command.identifiers}" varStatus="status">
										<tr align="center" class="results">
											<td width="10%"><a
												href="javascript:fireAction('removeIdentifier',${status.index});"><img
												src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
											<td width="20%"><form:input
												path="identifiers[${status.index}].source" /></td>
											<td width="20%"><form:input
												path="identifiers[${status.index}].type" /></td>
											<td width="20%"><form:input
												path="identifiers[${status.index}].value"
												onclick="javascript:clearField(this)();" /></td>
											<td width="25%" align="center"><form:radiobutton
												path="identifiers[${status.index}].primaryIndicator"
												value="true" /></td>
											<td width="10%"><em></em><span class="red"><form:errors
												path="identifiers[${status.index}].type" /> <form:errors
												path="identifiers[${status.index}].source" /> <form:errors
												path="identifiers[${status.index}].value" /><em></em></span></td>
										</tr>
									</c:forEach>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
											height="1" class="heightControl"></td>
									</tr>
									<tr>
										<td align="center"><a
											href="javascript:fireAction('addIdentifier','0');"><img
											src="<tags:imageUrl name="checkyes.gif"/>" border="0"
											alt="Add another Identifier"></a></td>
									</tr>
								</table>
								</form>
							</tr>
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
