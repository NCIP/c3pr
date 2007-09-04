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
	new Effect.SlideDown('createInv');
	new Effect.SlideUp('confirmationMessage');
	new Effect.SlideDown('dispButton');
}
var instanceRowInserterProps = {

	add_row_division_id: "invesitgatorTable", 	        /* this id belongs to element where the row would be appended to */
	skeleton_row_division_id: "dummy-row",  	/* this id belongs to the element which hold the dummy row html to be inserted   */
	initialIndex: ${fn:length(command.healthcareSiteInvestigators)},                            /* this is the initial count of the rows when the page is loaded  */
	path: "healthcareSiteInvestigators"                             /* this is the path of the collection that holds the rows  */
};
RowManager.addRowInseter(instanceRowInserterProps);
</script>

</head>
<body>
<tags:basicFormPanelBox tab="${tab}" flow="${flow}"
	action="createInvestigator">
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
								<table width="100%" border="0">
									<tr>
										<td>
										<div class="content">
										<div class="row">
										<div>
										<h1>Invesitgator Succesfully Created</h1>
										</div>
										</div>
										<div class="row">
										<div class="label">Full Name:</div>
										<div class="value">${param.fullName}</div>
										</div>
										<div class="row">
										<a href="javascript:handleConfirmation()">Click here to create
								       another investigator</a></div>
										</div>
										</td>
									</tr>
								</table>
								</div>
								<div id="confirmationMessage">
							</c:if>
							<div id="createInv"
								<c:if test="${param.type == 'confirm'}">style="display:none"</c:if>>
								<br>

							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
								<td width="40"></td>
									<td>

									<table class="tablecontent" width="40%" border="0" cellspacing="0" id="invesitgatorTable"
										cellpadding="0">
										<tr>
											<th class="label required-indicator" scope="col" align="left"><b>Organization</b></th>
											<th class="label required-indicator" scope="col" align="left"><b>Investigator Status</b></th>
											<th></th>
										</tr>
										<c:forEach items="${command.healthcareSiteInvestigators}"
											varStatus="status">
											<tr id="invesitgatorTable-${status.index}">
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
													href="javascript:RowManager.deleteRow(instanceRowInserterProps,${status.index});"><img
													src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
											</tr>
										</c:forEach>
									</table>
									</td>
									<td valign="bottom" align="right">
									<input id="addInvestigator" type="button"
										value="Add healthcare site"
										onclick="javascript:RowManager.addRow(instanceRowInserterProps);" />
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							</table>
							<table width="740" border="0" cellspacing="0" cellpadding="0"
								id="details">
								<hr align="left" width="95%">
								<tr>
									<td width="370" valign="top">

									<table width="370" border="0" cellspacing="1" cellpadding="1"
										id="table1">
										<tr>
											<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
												height="1" class="heightControl"></td>
											<td width="60%"><img src="<tags:imageUrl name="spacer.gif"/>"
												width="1" height="1" class="heightControl"></td>
										</tr>
										<tr>
											<td class="label required-indicator" align="right"><em></em> <b>First
											Name:&nbsp;</b></td>
											<td align="left"><form:input path="firstName" size="25"
												cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
										</tr>
										<tr>
											<td class="label required-indicator" align="right"><em></em> <b>Last
											Name:</b>&nbsp;</td>
											<td align="left"><form:input path="lastName" size="25"
												cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
										</tr>
										<tr>
											<td align="right"><em></em> <b>Middle Name:</b>&nbsp;</td>
											<td align="left"><form:input path="middleName" size="25" />&nbsp;&nbsp;&nbsp;</td>
										</tr>
										<tr>
											<td align="right"><em></em> <b>Maiden Name:</b>&nbsp;</td>
											<td align="left"><form:input path="maidenName" size="25" />&nbsp;&nbsp;&nbsp;</td>
										</tr>

									</table>
									</td>
									<td width="370" valign="top">
									<table width="370" border="0" cellspacing="1" cellpadding="1"
										id="table1">
										<tr>
											<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
												height="1" class="heightControl"></td>
											<td width="70%"><img src="<tags:imageUrl name="spacer.gif"/>"
												width="1" height="1" class="heightControl"></td>
										</tr>
										<tr>
											<td align="right"><em></em><em></em> <b>NCI Identifier:</b>&nbsp;</td>
											<td align="left"><form:input path="nciIdentifier" size="25" />&nbsp;&nbsp;&nbsp;</td>
										</tr>
										<tr>
											<td align="right"><em></em><em></em> <b>${command.contactMechanisms[0].type.displayName
											}:</b>&nbsp;</td>
											<td align="left"><form:input
												path="contactMechanisms[0].value" size="25" />&nbsp;&nbsp;&nbsp;</td>
										</tr>
										<tr>
											<td align="right"><em></em><em></em> <b>${command.contactMechanisms[1].type.displayName
											}:</b>&nbsp;</td>
											<td align="left"><form:input
												path="contactMechanisms[1].value" size="25" />&nbsp;&nbsp;&nbsp;</td>
										</tr>
										<tr>
											<td align="right"><em></em><em></em> <b>${command.contactMechanisms[2].type.displayName
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
	<div id="dispButton"
		<c:if test="${param.type == 'confirm'}">style="display:none"</c:if>><tags:tabControls />
	</div>
</tags:basicFormPanelBox>
<div id="dummy-row" style="display: none;">
<table>
	<tr>
		<td class="alt"><select
			id="healthcareSiteInvestigators[PAGE.ROW.INDEX].healthcareSite"
			name="healthcareSiteInvestigators[PAGE.ROW.INDEX].healthcareSite"
			cssClass="validate-notEmpty">
			<option value="">--Please Select--</option>
			<c:forEach items="${healthcareSites}" var="site">
				<option value="${site.id}">${site.name }</option>
			</c:forEach>
		</select></td>
		<td class="alt"><select
			id="healthcareSiteInvestigators[PAGE.ROW.INDEX].statusCode"
			name="healthcareSiteInvestigators[PAGE.ROW.INDEX].statusCode"
			cssClass="validate-notEmpty">
			<option value="">--Please Select--</option>
			<c:forEach items="${studySiteStatusRefData}" var="siteRef">
				<option value="${siteRef.code}">${siteRef.desc }</option>
			</c:forEach>
		</select></td>
		<td class="tdalt"><a
			href="javascript:RowManager.deleteRow(instanceRowInserterProps,PAGE.ROW.INDEX);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>
</body>
</html>
