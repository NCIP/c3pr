<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<html>
<head>
<style type="text/css">
        .label { width: 20em; text-align: right; padding: 2px; }
</style>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
</head>
<body>
<tags:formPanelBox tab="${tab}" flow="${flow}">
<strong>Step 1. Informed Consent Details </strong><br>
<table width="60%" border="0" cellspacing="0" cellpadding="0" id="table1">
	<tr><td colspan="2">&nbsp;</td></tr>
	<tr>
		<td class="label" width="80%">Informed Consent Signed Date:</td>
		<td><tags:dateInput path="informedConsentSignedDate" /><em> (mm/dd/yyyy)</em></td>
	</tr>
	<tr>
		<td class="label">Informed Consent Version:</td>
		<td><form:input path="informedConsentVersion"/></td>
	</tr>
</table>
<hr align="left" width="95%">
<strong>Step 2. Enrolling Physician Details </strong><br>
<table width="60%" border="0" cellspacing="0" cellpadding="0" id="table1">
	<tr><td colspan='2'>&nbsp;</td></tr>
	<tr>
		<td class="label" width="80%"><em></em>Enrolling Physician:</td>
		<td width="42%">
			<form:select path="treatingPhysician">
				<option value="">--Please Select--</options><form:options
					items="${command.studySite.studyInvestigators}" itemLabel="healthcareSiteInvestigator.investigator.fullName" itemValue="id" />
			</form:select>
		</td>
	</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</tags:formPanelBox>
</body>
</html>
