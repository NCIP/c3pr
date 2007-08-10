<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>

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
	document.studyDesignForm._target0.value=s;
	document.studyDesignForm.submit();
}

</script>
</head>
<body>
<tags:tabForm tab="${tab}" flow="${flow}" title="Subject Overview"
	willSave="false" formName="review">
	<jsp:attribute name="repeatingFields">
		<div><input type="hidden" name="_finish" value="true" /></div>

		<chrome:division id="subject-details" title="Basic Details">
			<table class="tablecontent">
				<tr>
					<td class="alt" align="left"><b>First Name:<b></td>
					<td class="alt" align="left">${command.firstName}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Last Name:<b></td>
					<td class="alt" align="left">${command.lastName}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Middle Name:<b></td>
					<td class="alt" align="left">${command.middleName}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Maiden Name:<b></td>
					<td class="alt" align="left">${command.maidenName}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Gender:</b></td>
					<td class="alt" align="left">${command.administrativeGenderCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Birth Date:</b></td>
					<td class="alt" align="left">${command.birthDateStr}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Ethnicity:<b></td>
					<td class="alt" align="left">${command.ethnicGroupCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Race(s):<b></td>
					<td class="alt" align="left">${command.raceCode}</td>
				</tr>
			</table>
		</chrome:division>

		<tabs:tabButtonControls text="edit" target="0" />

		<chrome:division title="Address">
			<table class="tablecontent">
				<tr>
					<td class="alt" align="left"><b>Street Address:<b></td>
					<td class="alt" align="left">${command.address.streetAddress}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>City:<b></td>
					<td class="alt" align="left">${command.address.city}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>State:</b></td>
					<td class="alt" align="left">${command.address.stateCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Country:</b></td>
					<td class="alt" align="left">${command.address.countryCode}</td>
				</tr>
				<tr>
					<td class="alt" align="left"><b>Zip:<b></td>
					<td class="alt" align="left">${command.address.postalCode}</td>
				</tr>
			</table>
		</chrome:division>

		<tabs:tabButtonControls text="edit" target="1" />

		<chrome:division title="Contact Information">
			<table class="tablecontent">
				<tr>
					<th scope="col" align="left">Email</th>
					<th scope="col" align="left">Phone</th>
					<th scope="col" align="left">Fax</th>
				</tr>
				<tr class="results">
					<c:forEach items="${command.contactMechanisms}"
						var="contactMechanism">
						<td class="alt" align="left">${contactMechanism.value}</td>
					</c:forEach>
				</tr>
			</table>
		</chrome:division>

		<tabs:tabButtonControls text="edit" target="1" />

		<chrome:division title="Identifiers">

			<h4>Organization Assigned Identifiers</h4>
			<br>

			<table class="tablecontent">
				<tr>
					<th scope="col" align="left">Assigning Authority</th>
					<th scope="col" align="left">Identifier Type</th>
					<th scope="col" align="left">Identifier</th>
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
			<br>
			<h4>System Assigned Identifiers</h4>

			<br>

			<table class="tablecontent">
				<tr>
					<th scope="col" align="left">System Name</th>
					<th scope="col" align="left">Identifier Type</th>
					<th scope="col" align="left">Identifier</th>
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

		<tabs:tabButtonControls text="edit" target="0" />


	</jsp:attribute>
</tags:tabForm>
</body>
</html>
