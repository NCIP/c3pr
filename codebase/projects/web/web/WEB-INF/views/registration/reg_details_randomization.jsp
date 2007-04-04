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
		<div><input type="hidden" name="_page" id="_page" value="4"> <input
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
								<table width="650" border="0" cellspacing="0" cellpadding="0"
									id="table1">

									<tr valign="top">
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
											height="1" class="heightControl"></td>
										<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
											width="1" height="1" class="heightControl"></td>
									</tr>

									<tr>
										<td align="right"><span class="red">*</span><em></em><b>Randomization
										Type:</b>&nbsp;</td>
										<td align="left"><select name="select" class="field1">
											<option selected>Book</option>
											<option>Call-Out</option>
											<option>Hub</option>
										</select></td>
									</tr>

									<tr valign="top">
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
											height="1" class="heightControl"></td>
										<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
											width="1" height="1" class="heightControl"></td>
									</tr>

									<tr valign="top">
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
											height="1" class="heightControl"></td>
										<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
											width="1" height="1" class="heightControl"></td>
									</tr>


									<tr>
										<td align="right"><span class="red">*</span><em></em><b>Treatment
										Arm:</b>&nbsp;</td>
										<td align="left"><select name="select" class="field1">
											<option selected>Arm A</option>
											<option>Arm B</option>
											<option>Arm C</option>
										</select></td>
									</tr>

									<tr valign="top">
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
											height="1" class="heightControl"></td>
										<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
											width="1" height="1" class="heightControl"></td>
									</tr>
								</table>
								</form>
							</tr>
							<c:forEach begin="1" end="6">
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
