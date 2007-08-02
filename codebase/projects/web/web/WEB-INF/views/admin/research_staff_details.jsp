<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<html>
<head>
<script>

function fireAction(action, selected){	
			document.getElementById("command")._finish.name='xyz';		    
			document.getElementById("command")._action.value=action;
			document.getElementById("command")._selected.value=selected;		
			document.getElementById("command").submit();
		
	}
function handleConfirmation(){
	new Effect.SlideDown('createRS');
	new Effect.SlideUp('confirmationMessage');
	new Effect.SlideDown('dispButton');
}
</script>
</head>
<body>

<tags:basicFormPanelBox tab="${tab}" flow="${flow}"
	title="Research Staff" action="createResearchStaff">
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
	<input type="hidden" name="_finish" value="true">
	<input type="hidden" name="type1" value="">
	<tags:errors path="*" />

<c:if test="${type == 'confirm'}">
	<div id="confirmationMessage">
	<table width="100%" border="0">
		<tr>
			<td>
			<div class="content">
			<div class="row">
			<div>
			<h1>Research Staff member Succesfully Created</h1>
			</div>
			</div>
			<div class="row">
			<div class="label">Full Name:</div>
			<div class="value">${fullName}</div>
			</div>
			<div class="row"><a href="javascript:handleConfirmation()">Click
			here to create another research staff member</a></div>
			</div>
			</td>
		</tr>
	</table>
	</div>
	</c:if>

<div id="createRS"	<c:if test="${type == 'confirm'}">style="display:none"</c:if>>

<p>Choose a healthcare site</p>
<table width="60%" border="0" cellspacing="1" cellpadding="1">
	<tr>
		<td align="right" width="20%"><b> <span class="red">*</span><em></em><b>Site:</b>&nbsp;</b></td>
		<td><form:select path="healthcareSite"
			id="selectedHealthcareSite" cssClass="validate-notEmpty">
			<option value="">--Please Select--</option>
			<form:options items="${healthcareSites}" itemLabel="name"
				itemValue="id" />
		</form:select></td>
	</tr>

</table>
<hr align="left" width="95%">

<table width="60%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
				<td  width="40%" align="right"><span class="red">*</span><b>First Name:</b>&nbsp;</td>
				<td><form:input size="25" path="firstName"
					cssClass="validate-notEmpty" /></td>
			</tr>
			<tr>
				<td align="right"><span class="red">*</span><b>Last Name:<b>&nbsp;</td>
				<td><form:input size="25" path="lastName"
					cssClass="validate-notEmpty" /></td>
			</tr>
			<tr>
				<td align="right"><em></em> <b>Middle Name:</b>&nbsp;</td>
				<td><form:input path="middleName" size="25" />&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td align="right"><em></em> <b>Maiden Name:</b>&nbsp;</td>
				<td><form:input path="maidenName" size="25" />&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
		</td>
		<td valign="top">
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
				<td align="right" width="40%"><em></em><em></em> <b>NCI Identifier:</b>&nbsp;</td>
				<td ><form:input path="nciIdentifier" size="25" />&nbsp;&nbsp;&nbsp;</td>
			</tr>

			<tr>
				<td align="right"><em></em><em></em> <b>${command.contactMechanisms[0].type
				}:</b>&nbsp;</td>
				<td><form:input size="25"
					path="contactMechanisms[0].value" />&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td align="right"><em></em><em></em> <b>${command.contactMechanisms[1].type
				}:</b>&nbsp;</td>
				<td><form:input size="25"
					path="contactMechanisms[1].value" />&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td align="right"><em></em><em></em> <b>${command.contactMechanisms[2].type
				}:</b>&nbsp;</td>
				<td><form:input size="25"
					path="contactMechanisms[2].value" />&nbsp;&nbsp;&nbsp;</td>
			</tr>

		</table>
		</td>
	</tr>
</table>
</div>
			<br>
	<div id="dispButton"
		<c:if test="${type == 'confirm'}">style="display:none"</c:if>><tags:tabControls />
	</div>
</tags:basicFormPanelBox>
</body>
</html>
