<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Address of <participanttags:htmlTitle subject="${command}" /></title>
    
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
<style>
#workflow-tabs {
top:62px;
}
</style>
</head>
<body>
<tags:tabForm tab="${tab}" flow="${flow}"
	formName="participantAddressForm">    
	<jsp:attribute name="singleFields">
    <tags:instructions code="participant_address" />
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
	<p id="instructions"><h2>Address</h2><br>
			</p>
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
			<b>Zip:</b>&nbsp;<form:input path="address.postalCode" id="postalCode" cssClass="validate-ZIPCODE" /></td>
		</tr>
        
        <tr>
			<td width="150" align="right"><em></em><em></em> <b>Country:</b>&nbsp;</td>
			<td align="left"><form:input path="address.countryCode" />&nbsp;&nbsp;&nbsp;</td>
		</tr>
	</table>
	<hr align="left" width="95%">
	<table border="0" width="60%" cellspacing="1" cellpadding="1">
		<tr>
			<p id="instructions"><h2>Contact Information</h2><br>
			</p>
		<tr>
			<td width="150" align="right"><em></em><em></em> <b>${command.contactMechanisms[0].type.displayName }:</b>&nbsp;</td>
			<td align="left"><form:input path="contactMechanisms[0].value"  cssClass="validate-EMAIL" size="30" /><tags:hoverHint keyProp="contactMechanism.email"/></td>
		</tr>
		<tr>
			<td width="150" align="right"><em></em><em></em> <b>${command.contactMechanisms[1].type.displayName }:</b>&nbsp;</td>
			<td align="left"><form:input path="contactMechanisms[1].value"  cssClass="validate-US_PHONE_NO"/><tags:hoverHint keyProp="contactMechanism.phone"/> e.g. 7035600296 or 703-560-0296</td>
		</tr>
		<tr>
			<td width="150" align="right"><em></em><em></em> <b>${command.contactMechanisms[2].type.displayName }:</b>&nbsp;</td>
			<td align="left"><form:input path="contactMechanisms[2].value" cssClass="validate-US_PHONE_NO"/><tags:hoverHint keyProp="contactMechanism.fax"/> e.g. 7035600296 or 703-560-0296</td>
		</tr>

	</table>
	</jsp:attribute>
</tags:tabForm>

</body>
</html>
