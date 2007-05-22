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

<tabs:division id="research_staff">
	<form:form method="post" name="studySiteForm">
		<input type="hidden" name="_action" value="">
		<input type="hidden" name="_selected" value="">
		<input type="hidden" name="_finish" value="true">
		<tags:errors path="*" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%">
				<table width="40%" border="0" cellspacing="0" cellpadding="0"
					id="table1">
					<tr valign="top">
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
							height="1" class="heightControl"></td>
						<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="1" class="heightControl"></td>
					</tr>
					<tr valign="top">
						<td class="label" align="right"><span class="red">*</span>First
						Name:&nbsp;</td>
						<td><form:input path="firstName" cssClass="validate-notEmpty" />
						</td>
					</tr>
					<tr valign="top">
						<td class="label" align="right"><span class="red">*</span>Last
						Name:&nbsp;</td>
						<td><form:input path="lastName" cssClass="validate-notEmpty" /></td>
					</tr>
					<tr>
						<td class="label" align="right"><b> <span class="red">*</span><em></em>Site:&nbsp;</b></td>
						<td><form:select path="healthcareSite" id="selectedHealthcareSite">
							<option value="">--Please Select--</option>
							<form:options items="${healthcareSites}" itemLabel="name"
								itemValue="id" />
						</form:select></td>
					</tr>
				</table>

				</td>
			</tr>

			<tr>
				<td>
				<hr align="left" width="95%">

				<table border="0" width="40%" cellspacing="1" cellpadding="1">
					<tr valign="top">
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
							height="1" class="heightControl"></td>
						<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="1" class="heightControl"></td>
					</tr>
					<tr>
						<td colspan="2">Enter Contact Information for the Research Staff</td>
					</tr>
					<tr valign="top">
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
							height="1" class="heightControl"></td>
						<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="1" class="heightControl"></td>
					</tr>
					<tr>
						<td class="label" align="right"><em></em><em></em> <b>${command.contactMechanisms[0].type
						}:</b>&nbsp;</td>
						<td align="left"><form:input path="contactMechanisms[0].value" />&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td class""label" align="right"><em></em><em></em> <b>${command.contactMechanisms[1].type
						}:</b>&nbsp;</td>
						<td align="left"><form:input path="contactMechanisms[1].value" />&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td class="label" align="right"><em></em><em></em> <b>${command.contactMechanisms[2].type
						}:</b>&nbsp;</td>
						<td align="left"><form:input path="contactMechanisms[2].value" />&nbsp;&nbsp;&nbsp;</td>
					</tr>

				</table>


				</td>
			</tr>
		</table>
	</form:form>
</tabs:division>
</body>
</html>
