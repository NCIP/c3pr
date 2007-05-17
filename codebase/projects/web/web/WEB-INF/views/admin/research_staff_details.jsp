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

<tabs:division id="research_staff">
	<form:form method="post" cssClass="standard" name="studySiteForm">
		<div><input type="hidden" name="_action" value=""> <input
			type="hidden" name="_selected" value=""> <input type="hidden"
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
						<td><form:input path="firstName" cssClass="validate-notEmpty" />
						</td>
					</tr>
					<tr valign="top">
						<td class="label"><span class="red">*</span>Last Name:</td>
						<td><form:input path="lastName" cssClass="validate-notEmpty" /></td>
					</tr>
					<tr>
						<td class="label"><b> <span class="red">*</span><em></em>Site:</b></td>
						<td><form:select path="healthcareSite" id="selectedHealthcareSite">
							<option value="">--Please Select--</option>
							<form:options items="${healthcareSites}" itemLabel="name"
								itemValue="id" />
						</form:select></td>
					</tr>

					<tr>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="30"
							height="2" class="heightControl"></td>
						<td>
						<p id="instructions">Add Contacts for the Research Staff <a
							href="javascript:fireAction('addContact','0');"><img
							src="<tags:imageUrl name="checkyes.gif"/>" border="0"
							alt="Add another Identifier"></a><br>
						</p>
						<table id="mytable" width="30%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<th scope="col" align="left"><b>Contact Type<span
									class="red">*</span></b></th>
								<th scope="col" align="left"><b>Contact Value<span class="red">*</span></b></th>
							</tr>
							<c:forEach items="${command.contactMechanisms}"
								varStatus="status">
								<tr>
									<td class="alt"><form:input
										path="contactMechanisms[${status.index}].type"
										cssClass="validate-notEmpty" /></td>
									<td class="alt"><form:input
										path="contactMechanisms[${status.index}].value"
										cssClass="validate-notEmpty" /></td>
										<td class="tdalt"><a
										href="javascript:fireAction('removeContact',${status.index});"><img
										src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
								</tr>
							</c:forEach>
						</table>
						</td>
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
			</tr>
		</table>
	</form:form>
</tabs:division>
</body>
</html>
