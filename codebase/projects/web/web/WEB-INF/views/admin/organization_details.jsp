<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>

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
	new Effect.SlideDown('createOrg');
	new Effect.SlideDown('dispButton');
	new Effect.SlideUp('confirmationMessage');
	
}
function submitPage(){
	document.getElementById("organizationForm").submit();
}
</script>
</head>
<body>

<tags:basicFormPanelBox tab="${tab}" flow="${flow}" title="Organization">
<br/>
	<form:form id="organizationForm" method="post" action="createOrganization">
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
								<c:if test="${type == 'confirm'}">
									<div id="confirmationMessage">
										<table width="100%" border="0">
											<tr>
												<td>
												<div class="content">
												<div class="row">
												<div>
												<h1>Organization Succesfully Created</h1>
												</div>
												</div>
												<div class="row">
												<div class="label">Name:</div>
												<div class="value">${orgName}</div>
												</div>
												<div class="row"><a href="javascript:handleConfirmation()">Click
												here to create another organization</a></div>
												</div>
												</td>
											</tr>
										</table>
									</div>
								</c:if>
								<div id="createOrg"
									<c:if test="${type == 'confirm'}">style="display:none"</c:if>>
										<table width="525" border="0" cellspacing="1" cellpadding="1" id="table1">
											<tr>
												<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
													height="1" class="heightControl"></td>
												<td width="75%"><img
													src="<tags:imageUrl name="spacer.gif"/>" width="1"
													height="1" class="heightControl"></td>
											</tr>
											<tr valign="top">
												<td align="right"><span class="red">*</span><b>Name:</b>&nbsp;</td>
												<td><form:input size="60" path="name" cssClass="validate-notEmpty" /></td>
											</tr>
											<tr valign="top">
												<td align="right"><b>Description:<b>&nbsp;</td>
												<td><form:textarea rows="3" cols="45" path="descriptionText" /></td>
											</tr>
											<tr>
												<td align="right"><b>NCI Institute Code:</b>&nbsp;</td>
												<td align="left"><form:input path="nciInstituteCode" size="25" /></td>
											</tr>
										</table>							
										<hr align="left" width="95%">
																							
										<table width="625" border="0" cellspacing="1" cellpadding="1" id="table1">
											<tr>
												<td align="right"><b>Street Address:</b>&nbsp;</td>
												<td align="left"><form:input size="60" path="address.streetAddress" />&nbsp;&nbsp;&nbsp;</td>
											</tr>
											<tr>
												<td align="right"><b>City:</b>&nbsp;</td>
												<td align="left"><form:input path="address.city" size="25"/>&nbsp;&nbsp;&nbsp;</td>
											</tr>
											<tr>
												<td align="right"><span class="data"><b>State:</b>&nbsp;</span></td>
												<td align="left"><form:input path="address.stateCode" size="25"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<b>Zip:</b>&nbsp;<form:input path="address.postalCode" size="22"/></td>
											</tr>
											<tr>
												<td align="right"><b>Country:</b>&nbsp;</td>
												<td align="left"><form:input path="address.countryCode" size="25"/>&nbsp;&nbsp;&nbsp;</td>
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
				<br />
				</td>
				<!-- LEFT CONTENT ENDS HERE -->
			</tr>
		</table>
	</form:form>
	<div id="dispButton" <c:if test="${type == 'confirm'}">style="display:none"</c:if>>
	<tags:tabControls />
	</div>
</tags:basicFormPanelBox>
</body>
</html>
