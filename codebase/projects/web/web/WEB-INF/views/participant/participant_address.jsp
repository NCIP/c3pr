<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function getPage(s){
	parent.window.location="reg_patient_search.htm";
}
function fireAction(action, selected){
	document.getElementById("_action").value=action;
	document.getElementById("_selected").value=selected;
	document.getElementById('targetPage').name='_noname';
	document.getElementById("command").submit();
}
</script>
</head>
<body>
<form:form method="post" cssClass="standard">
	<div><input type="hidden" name="_page" value="1"> <input type="hidden"
		name="_action" value=""> <input type="hidden" name="_selected"
		value=""></div>
	<tabs:tabFields tab="${tab}" />
	<tabs:division id="subject-details">

		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<td><!-- TABS LEFT START HERE -->
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>

						<!-- LEFT CONTENT STARTS HERE -->
						<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
						<!-- RIGHT CONTENT STARTS HERE -->
						<td>
						<table width="80%" border="0" cellspacing="1" cellpadding="1"
							id="table1">
							<tr>
								<td width="150" align="right"><em></em> <b>Street Address:</b>&nbsp;</td>
								<td width align="left"><form:input size="58"
									path="address.streetAddress" />&nbsp;&nbsp;&nbsp;</td>
							</tr>
							<tr>
								<td width="150" align="right"><em></em> <b>City:</b>&nbsp;</td>
								<td align="left"><form:input path="address.city" />&nbsp;&nbsp;&nbsp;</td>
							</tr>
							<tr>
								<td width="150" align="right"><span class="data"><em></em> <b>State:</b>&nbsp;</span></td>
								<td align="left"><form:input path="address.stateCode" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<b>Zip:</b>&nbsp;<form:input path="address.postalCode" /></td>
							</tr>
							<tr>
								<td width="150" align="right"><em></em><em></em> <b>Country:</b>&nbsp;</td>
								<td align="left"><form:input path="address.countryCode" />&nbsp;&nbsp;&nbsp;</td>
							</tr>
						</table>
						<hr align="left" width="95%">
						<table border="0" width="60%" cellspacing="1" cellpadding="1">
							<tr>

								<p id="instructions">Enter Contact Information for the Subject <br>
								</p>
							<tr>
								<td width="150" align="right"><em></em><em></em> <b>${command.contactMechanisms[0].type }:</b>&nbsp;</td>
								<td align="left"><form:input path="contactMechanisms[0].value" />&nbsp;&nbsp;&nbsp;</td>
							</tr>
							<tr>
								<td width="150" align="right"><em></em><em></em> <b>${command.contactMechanisms[1].type }:</b>&nbsp;</td>
								<td align="left"><form:input path="contactMechanisms[1].value" />&nbsp;&nbsp;&nbsp;</td>
							</tr>
							<tr>
								<td width="150" align="right"><em></em><em></em> <b>${command.contactMechanisms[2].type }:</b>&nbsp;</td>
								<td align="left"><form:input path="contactMechanisms[2].value" />&nbsp;&nbsp;&nbsp;</td>
							</tr>

						</table>
						</td>
						<!-- LEFT CONTENT ENDS HERE -->
					</tr>
				</table>
			</tr>
		</table>
		<!-- MAIN BODY ENDS HERE -->
	</tabs:division>
</form:form>
</body>
</html>
