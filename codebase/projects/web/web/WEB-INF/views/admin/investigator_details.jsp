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
	new Effect.SlideDown('createInv');
	new Effect.SlideUp('confirmationMessage');
}
</script>

</head>
<body>
<tags:formPanelBox tab="${tab}" flow="${flow}">
		<form:form method="post" action="createInvestigator"
			cssClass="standard" name="studySiteForm">
			<input type="hidden" name="_action" value="">
			<input type="hidden" name="_selected" value="">
			<input type="hidden" name="_finish" value="true">
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
										test="${param.type == 'confirm'}">
										<div id="confirmationMessage">
										<h3><font color="green"> You have successfully created an
										investigator with name : ${param.fullName}</font></h3>
										<br>
										<a href="javascript:handleConfirmation()">Click here to create
										another investigator</a></div>
									</c:if>
									<div id="createInv"
										<c:if test="${param.type == 'confirm'}">style="display:none"</c:if>>



									<table border="0" width="100%" cellspacing="0" cellpadding="0">
										<tr>
											<td>
											<p id="instructions">Please choose healthcare site(s) for the
											Investigator <a href="javascript:fireAction('addSite','0');"><img
												src="<tags:imageUrl name="checkyes.gif"/>" border="0"
												alt="Add another Site"></a><br>
											</p>
											<table id="mytable" width="40%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<th class="alt" scope="col" align="left"><b>Site<span
														class="red">*</span></b></th>
													<th scope="col" align="left"><b>Status<span class="red">*</span></b></th>
												</tr>
												<c:forEach items="${command.healthcareSiteInvestigators}"
													varStatus="status">
													<tr>
														<td class="alt"><form:select
															path="healthcareSiteInvestigators[${status.index}].healthcareSite"
															cssClass="validate-notEmpty">
															<option value="">--Please Select--</option>
															<form:options items="${healthcareSites}" itemLabel="name"
																itemValue="id" />
														</form:select></td>
														<td class="alt"><form:select
															path="healthcareSiteInvestigators[${status.index}].statusCode"
															cssClass="validate-notEmpty">
															<option value="">--Please Select--</option>
															<form:options items="${studySiteStatusRefData}"
																itemLabel="desc" itemValue="code" />
														</form:select></td>
														<td class="tdalt"><a
															href="javascript:fireAction('removeSite',${status.index});"><img
															src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
													</tr>
												</c:forEach>
											</table>
											</td>
										</tr>
									</table>
									<table width="600" border="0" cellspacing="0" cellpadding="0"
										id="details">
										<hr align="left" width="95%">
										<tr>
											<td width="360" valign="top">

											<table width="360" border="0" cellspacing="1" cellpadding="1"
												id="table1">
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
														height="1" class="heightControl"></td>
													<td width="75%"><img
														src="<tags:imageUrl name="spacer.gif"/>" width="1"
														height="1" class="heightControl"></td>
												</tr>
												<tr>
													<td align="right"><span class="red">*</span><em></em> <b>First
													Name: &nbsp;</b></td>
													<td align="left"><form:input path="firstName" size="25"
														cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
												</tr>
												<tr>
													<td align="right"><span class="red">*</span><em></em> <b>Last
													Name:</b>&nbsp;</td>
													<td align="left"><form:input path="lastName" size="25"
														cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
												</tr>
												<tr>
													<td align="right"><em></em> <b>Maiden
													Name:</b>&nbsp;</td>
													<td align="left"><form:input path="maidenName" size="25" />&nbsp;&nbsp;&nbsp;</td>
												</tr>
												<tr>
													<td align="right"><em></em><em></em> <b>NCI Identifier:</b>&nbsp;</td>
													<td align="left"><form:input path="nciIdentifier" size="25" />&nbsp;&nbsp;&nbsp;</td>
												</tr>
											</table>
											</td>
											<td width="350" valign="top">
											<table width="350" border="0" cellspacing="1" cellpadding="1"
												id="table1">
												<tr>
													<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
														height="1" class="heightControl"></td>
													<td width="75%"><img
														src="<tags:imageUrl name="spacer.gif"/>" width="1"
														height="1" class="heightControl"></td>
												</tr>
												<tr>
													<td align="right"><em></em><em></em> <b>${command.contactMechanisms[0].type
													}:</b>&nbsp;</td>
													<td align="left"><form:input
														path="contactMechanisms[0].value" size="25" />&nbsp;&nbsp;&nbsp;</td>
												</tr>
												<tr>
													<td align="right"><em></em><em></em> <b>${command.contactMechanisms[1].type
													}:</b>&nbsp;</td>
													<td align="left"><form:input
														path="contactMechanisms[1].value" size="25" />&nbsp;&nbsp;&nbsp;</td>
												</tr>
												<tr>
													<td align="right"><em></em><em></em> <b>${command.contactMechanisms[2].type
													}:</b>&nbsp;</td>
													<td align="left"><form:input
														path="contactMechanisms[2].value" size="25" />&nbsp;&nbsp;&nbsp;</td>
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


		</form:form>
</tags:formPanelBox>
</body>
</html>
