
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
}
</script>
</head>
<body>

<tags:formPanelBox tab="${tab}" flow="${flow}"
	action="createResearchStaff">
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
	<input type="hidden" name="_finish" value="true">
	<input type="hidden" name="type1" value="">
	<tags:errors path="*" />

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>

			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><!-- TABS LEFT START HERE -->
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<!-- LEFT CONTENT STARTS HERE -->
							<td valign="top" class="additionals2"><!-- LEFT FORM STARTS HERE -->
							<!-- RIGHT CONTENT STARTS HERE --> <c:if
								test="${type == 'confirm'}">
								<div id="confirmationMessage">
								<h3><font color="green"> You have successfully created a
								research staff person with name : ${fullName}</font></h3>
								<br>
								<a href="javascript:handleConfirmation()">Click here to create
								another research staff person</a></div>
							</c:if>

							<div id="createRS"
								<c:if test="${type == 'confirm'}">style="display:none"</c:if>>
							<table width="600" border="0" cellspacing="0" cellpadding="0"
								id="details">
								<tr>
									<td width="350" valign="top">
									<table width="350" border="0" cellspacing="1" cellpadding="1"
										id="table1">
										<tr>
											<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
												height="1" class="heightControl"></td>
											<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
												width="1" height="1" class="heightControl"></td>
										</tr>
										<p id="instructions">Please choose a healthcare site</p>
										<tr>
											<td align="right"><b> <span class="red">*</span><em></em><b>Site:</b>&nbsp;</b></td>
											<td><form:select path="healthcareSite"
												id="selectedHealthcareSite" cssClass="validate-notEmpty">
												<option value="">--Please Select--</option>
												<form:options items="${healthcareSites}" itemLabel="name"
													itemValue="id" />
											</form:select></td>
										</tr>
										<tr>
											<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
												height="1" class="heightControl"></td>
											<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
												width="1" height="1" class="heightControl"></td>
										</tr>
									</table>
									</td>
									<td width="320" valign="top">
									<table width="320" border="0" cellspacing="1" cellpadding="1"
										id="table1">

									</table>
									</td>
								</tr>
							</table>
							<hr align="left" width="95%">

							<table width="800" border="0" cellspacing="0" cellpadding="0"
								id="details">
								<tr>
									<td width="400" valign="top">
									<table width="400" border="0" cellspacing="1" cellpadding="1"
										id="table1">
										<tr>
											<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
												height="1" class="heightControl"></td>
											<td width="70%"><img src="<tags:imageUrl name="spacer.gif"/>"
												width="1" height="1" class="heightControl"></td>
										</tr>
										<tr valign="top">
											<td align="right"><span class="red">*</span><b>First Name:</b>&nbsp;</td>
											<td><form:input size="25" path="firstName"
												cssClass="validate-notEmpty" /></td>
										</tr>
										<tr valign="top">
											<td align="right"><span class="red">*</span><b>Last Name:<b>&nbsp;</td>
											<td><form:input size="25" path="lastName"
												cssClass="validate-notEmpty" /></td>
										</tr>
										<tr>
											<td align="right"><em></em> <b>Maiden Name:</b>&nbsp;</td>
											<td align="left"><form:input path="maidenName" size="25" />&nbsp;&nbsp;&nbsp;</td>
										</tr>
									</table>
									</td>
									<td width="320" valign="top">
									<table width="320" border="0" cellspacing="1" cellpadding="1"
										id="table1">
										<tr valign="top">
											<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
												height="1" class="heightControl"></td>
											<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
												width="1" height="1" class="heightControl"></td>
										</tr>

										<tr valign="top">
											<td align="right"><em></em><em></em> <b>${command.contactMechanisms[0].type
											}:</b>&nbsp;</td>
											<td align="left"><form:input size="25"
												path="contactMechanisms[0].value" />&nbsp;&nbsp;&nbsp;</td>
										</tr>
										<tr>
											<td align="right"><em></em><em></em> <b>${command.contactMechanisms[1].type
											}:</b>&nbsp;</td>
											<td align="left"><form:input size="25"
												path="contactMechanisms[1].value" />&nbsp;&nbsp;&nbsp;</td>
										</tr>
										<tr>
											<td align="right"><em></em><em></em> <b>${command.contactMechanisms[2].type
											}:</b>&nbsp;</td>
											<td align="left"><form:input size="25"
												path="contactMechanisms[2].value" />&nbsp;&nbsp;&nbsp;</td>
										</tr>

									</table>
									</td>
								</tr>
							</table>
							</div>
							</td>

							<!-- LEFT CONTENT ENDS HERE -->
						</tr>
					</table>
					</td>
				</tr>
			</table>
			<br>
			</td>
			<!-- LEFT CONTENT ENDS HERE -->
		</tr>
	</table>

</tags:formPanelBox>
</body>
</html>
