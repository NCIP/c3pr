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
	<form:form method="post" cssClass="standard" name="studySiteForm"
		>
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
						<td ><form:input path="firstName" cssClass="validate-notEmpty" /></td>
					</tr>
					<tr valign="top">
						<td class="label"><span class="red">*</span>Last Name:</td>
						<td><form:input path="lastName" cssClass="validate-notEmpty"/></td>
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
			</tr>
		</table>

		<br>
		<br>

		<br>
		<br>


		<table border="0" id="table1" cellspacing="10" width="600">

			<tr>
				<td align="center"><b> <span class="red">*</span><em></em>Site</b></td>

				<td align="center"><b> <span class="red">*</span><em></em>Status</b>
				</td>
				<td align="center"><b><a
					href="javascript:fireAction('addSite','0');"><img
					src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a></b>
				</td>
			</tr>

			<c:forEach varStatus="status"
				items="${command.healthcareSiteInvestigators}">
				<tr>
					<td align="center" width="15%"><form:select
						path="healthcareSiteInvestigators[${status.index}].healthcareSite">
						<form:options items="${healthcareSites}" itemLabel="name"
							itemValue="id" />
					</form:select></td>
					<td align="center" width="15%"><form:select
						path="healthcareSiteInvestigators[${status.index}].statusCode">
						<form:options items="${studySiteStatusRefData}" itemLabel="desc"
							itemValue="code" />
					</form:select></td>

					<td align="center" width="10%"><a
						href="javascript:fireAction('removeSite',${status.index});"><img
						src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="delete"></a>
					</td>
				</tr>
			</c:forEach>

		</table>

	</form:form>
</tabs:division>
</body>
</html>
