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
							<td><form:input path="firstName" cssClass="validate-notEmpty" /> </td>
						</tr>
						<tr valign="top">
							<td class="label"><span class="red">*</span>Last Name:</td>
							<td><form:input path="lastName" cssClass="validate-notEmpty"/></td>
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
