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
<tags:panelBox tab="${tab}" flow="${flow}">
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
	<table width="80%" border="0" cellspacing="1" cellpadding="1" id="table1">
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
</tags:panelBox>
</body>
</html>
