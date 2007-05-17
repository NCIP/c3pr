<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<html>
<head>
<style type="text/css">
        div.label {
            width: 35%;
        }
        div.submit {
            text-align: right;
        }
        form {
            width: 20em;
        }
</style>

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
		<div><input type="hidden" name="_action" value=""> <input
			type="hidden" name="_selected" value=""><input type="hidden"
			name="_finish" value="true"></div>
		<tags:errors path="*" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="table1">
			<tr>
				<td>

				<table width="50%" border="0" cellspacing="0" cellpadding="0"
					class="contentAreaL">
					<tr valign="top">
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
							height="1" class="heightControl"></td>
						<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="1" class="heightControl"></td>
					</tr>
					<tr valign="top">
						<td class="label"><span class="red">*</span>First Name:</td>
						<td><form:input path="firstName" cssClass="validate-notEmpty" /></td>
					</tr>
					<tr valign="top">
						<td class="label"><span class="red">*</span>Last Name:</td>
						<td><form:input path="lastName" cssClass="validate-notEmpty" /></td>
					</tr>
					<tr>
						<td class="label">NCI Identifier:</td>
						<td><form:input path="nciIdentifier" /></td>
					</tr>

				</table>


				</td>
				<td><img src="<tags:imageUrl name="spacer.gif"/>" width="30"
					height="1" class="heightControl"></td>
				<td>
				<table width="50%" border="0" cellspacing="0" cellpadding="0"
					class="contentAreaL">
					<tr valign="top">
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
							height="1" class="heightControl"></td>
						<td width="75%"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="1" class="heightControl"></td>
					</tr>

				</table>
				</td>

				<table border="0" width="60%" cellspacing="0" cellpadding="0">
					<tr>
						<td>
						<p id="instructions">Add Healthcare Sites for the Investigator <a
							href="javascript:fireAction('addSite','0');"><img
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
										path="healthcareSiteInvestigators[${status.index}].healthcareSite">
										<option value="">--Please Select--</option>
										<form:options items="${healthcareSites}" itemLabel="name"
											itemValue="id" />
									</form:select></td>
									<td class="alt"><form:select
										path="healthcareSiteInvestigators[${status.index}].statusCode">
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
				<table border="0" width="60%" cellspacing="0" cellpadding="0">
					<tr>
						<td>
						<p id="instructions">Add Contacts for the Investigator <a
							href="javascript:fireAction('addContact','0');"><img
							src="<tags:imageUrl name="checkyes.gif"/>" border="0"
							alt="Add another Contact"></a><br>
						</p>
						<table id="mytable" width="40%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<th class="alt" scope="col" align="left"><b>Contact Type<span
									class="red">*</span></b></th>
								<th scope="col" align="left"><b>Contact Value<span class="red">*</span></b></th>
							</tr>
							<c:forEach items="${command.contactMechanisms}"
								varStatus="status">
								<tr>
									<td class="alt"><form:input
										path="contactMechanisms[${status.index}].type" /></td>
									<td class="alt"><form:input
										path="contactMechanisms[${status.index}].value" /></td>
									<td class="tdalt"><a
										href="javascript:fireAction('removeContact',${status.index});"><img
										src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
								</tr>
							</c:forEach>
						</table>
						</td>
					</tr>
				</table>



			</tr>
		</table>


	</form:form>
</tabs:division>
</body>
</html>
