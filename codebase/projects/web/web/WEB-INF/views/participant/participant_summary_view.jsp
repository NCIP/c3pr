<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@ taglib prefix="participanttags" tagdir="/WEB-INF/tags/participant"%>
<%@ taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration"%>

<html>
<head>
<style type="text/css">
        .labelR { width: 12em; text-align: right; padding: 4px; }
</style>
<style type="text/css">
        .label { width: 12em; text-align: left; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">

function updateTargetPage(target){
	document.formName._target0.value=s;
	document.formName.submit();
}

</script>
</head>
<body>
<form:form>
	<tags:tabFields tab="${tab}" />

	<chrome:box title="Subject Summary" autopad="true">
		<div><input type="hidden" name="_finish" value="true" /> <input
			type="hidden" name="_action" value=""></div>
		<div id="printable">
		<chrome:division id="subject-details" title="Basic Details">
			<table class="tablecontent" width="60%">
				<tr>
					<td width="35%" class="alt" align="left"><b>First Name<b></td>
					<td class="alt" align="left">${command.firstName}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Last Name<b></td>
					<td class="alt" align="left">${command.lastName}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Middle Name<b></td>
					<td class="alt" align="left">${command.middleName}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Maiden Name<b></td>
					<td class="alt" align="left">${command.maidenName}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Gender</b></td>
					<td class="alt" align="left">${command.administrativeGenderCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Birth Date</b></td>
					<td class="alt" align="left">${command.birthDateStr}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Ethnicity<b></td>
					<td class="alt" align="left">${command.ethnicGroupCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Race<b></td>
					<td class="alt" align="left">${command.raceCode}</td>
				</tr>
			</table>
		</chrome:division>

		<chrome:division title="Address">
			<table class="tablecontent" width="60%">
				<tr>
					<td width="35%" class="alt" align="left"><b>Street Address<b></td>
					<td class="alt" align="left">${command.address.streetAddress}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>City<b></td>
					<td class="alt" align="left">${command.address.city}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>State</b></td>
					<td class="alt" align="left">${command.address.stateCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Country</b></td>
					<td class="alt" align="left">${command.address.countryCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Zip<b></td>
					<td class="alt" align="left">${command.address.postalCode}</td>
				</tr>
			</table>
		</chrome:division>
		<chrome:division title="Contact Information">
			<table class="tablecontent" width="60%">
				<tr>
					<th width="40%" scope="col" align="left">Email</th>
					<th width="30%" scope="col" align="left">Phone</th>
					<th width="30%" scope="col" align="left">Fax</th>
				</tr>
				<tr class="results">
					<c:forEach items="${command.contactMechanisms}"
						var="contactMechanism">
						<td class="alt" align="left">${contactMechanism.value}</td>
					</c:forEach>
				</tr>
			</table>
		</chrome:division>

		<chrome:division title="Identifiers">

			<h4>Organization Assigned Identifiers</h4>
			<br>

			<table class="tablecontent" width="60%">
				<tr>
					<th width="50%" scope="col" align="left">Assigning Authority</th>
					<th width="35%" scope="col" align="left">Identifier Type</th>
					<th width="15%" scope="col" align="left">Identifier</th>
				</tr>
				<c:forEach items="${command.organizationAssignedIdentifiers}"
					var="orgIdentifier">
					<tr class="results">
						<td class="alt" align="left">${orgIdentifier.healthcareSite.name}</td>
						<td class="alt" align="left">${orgIdentifier.type}</td>
						<td class="alt" align="left">${orgIdentifier.value}</td>
					</tr>
				</c:forEach>
			</table>
			<br>
			<h4>System Assigned Identifiers</h4>

			<br>

			<table class="tablecontent" width="60%">
				<tr>
					<th width="50%" scope="col" align="left">System Name</th>
					<th width="35%" scope="col" align="left">Identifier Type</th>
					<th width="15%" scope="col" align="left">Identifier</th>
				</tr>
				<c:forEach items="${command.systemAssignedIdentifiers}"
					var="identifier">
					<tr class="results">
						<td class="alt" align="left">${identifier.systemName}</td>
						<td class="alt" align="left">${identifier.type}</td>
						<td class="alt" align="left">${identifier.value}</td>
					</tr>
				</c:forEach>
			</table>
		</chrome:division>
		<c:if test="${flowType == 'VIEW_SUBJECT'}">
			<div class="content buttons autoclear">
			<div class="flow-buttons"><span class="next"> <input type="button"
				value="Edit Subject"
				onclick="document.location='editParticipant?participantId=${command.id}'" />
				<input type="button" value="Print" onClick="javascript:C3PR.printElement('printable');"/>
			</span></div>
		</div>
		</c:if>


	</chrome:box>
</form:form>
</body>
</html>
