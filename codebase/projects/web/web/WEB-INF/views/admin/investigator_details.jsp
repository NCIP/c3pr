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
</script>

</head>
<body>

<tabs:division id="investigator">
	<form:form method="post" cssClass="standard" name="studySiteForm">
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
								<!-- RIGHT CONTENT STARTS HERE -->

								<table width="80%" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="20%" valign="top">
										<table width="60%" border="0" cellspacing="1" cellpadding="1"
											id="table1">
											<tr>
												<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
													height="1" class="heightControl"></td>
												<td width="60%"><img
													src="<tags:imageUrl name="spacer.gif"/>" width="1"
													height="1" class="heightControl"></td>
											</tr>
											<tr>
												<td align="right"><span class="red">*</span><em></em> <b>First
												Name: &nbsp;</b></td>
												<td align="left"><form:input path="firstName"
													cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
											</tr>
											<tr>
												<td align="right"><span class="red">*</span><em></em> <b>Last
												Name:</b>&nbsp;</td>
												<td align="left"><form:input path="lastName"
													cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
											</tr>
											<tr>
												<td align="right"><span class="red">*</span> <em></em> <b>NCI
												Identifier::</b> &nbsp;</td>
												<td><form:input path="nciIdentifier" /></td>
											</tr>
										</table>
										</td>
										<td width="20%" valign="top">
										<table width="60%" border="0" cellspacing="1" cellpadding="1"
											id="table1">
											<tr>
												<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
													height="1" class="heightControl"></td>
												<td width="60%"><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
													height="1" class="heightControl"></td>
											</tr>
											<tr>
												<td align="right"><em></em><em></em> <b>${command.contactMechanisms[0].type
												}:</b>&nbsp;</td>
												<td align="left"><form:input
													path="contactMechanisms[0].value" />&nbsp;&nbsp;&nbsp;</td>
											</tr>
											<tr>
												<td align="right"><em></em><em></em> <b>${command.contactMechanisms[1].type
												}:</b>&nbsp;</td>
												<td align="left"><form:input
													path="contactMechanisms[1].value" />&nbsp;&nbsp;&nbsp;</td>
											</tr>
											<tr>
												<td align="right"><em></em><em></em> <b>${command.contactMechanisms[2].type
												}:</b>&nbsp;</td>
												<td align="left"><form:input
													path="contactMechanisms[2].value" />&nbsp;&nbsp;&nbsp;</td>
											</tr>
										</table>
										</td>
									</tr>
								</table>

								<table border="0" width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td>
										<hr align="left" width="95%">
										<p id="instructions">Add Healthcare Sites for the Investigator
										<a href="javascript:fireAction('addSite','0');"><img
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
</tabs:division>
</body>
</html>
