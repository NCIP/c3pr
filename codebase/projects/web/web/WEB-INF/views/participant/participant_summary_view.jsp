<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
<form:form name="viewDetails">
	<chrome:box title="Subject Summary">
<input type="hidden" name="_finish" value="true" />
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="top"><strong>Details </strong> <br>
			<div class="review">
			<table width="50%" border="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<td width="20%" class="labelR"><b>First Name:<b></td>
					<td>${command.firstName}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Last Name:<b></td>
					<td>${command.lastName}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Maiden Name:<b></td>
					<td>${command.maidenName}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Gender:</b></td>
					<td>${command.administrativeGenderCode}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Birth Date:</b></td>
					<td>${command.birthDateStr}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Ethnicity:<b></td>
					<td>${command.ethnicGroupCode}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Race(s):<b></td>
					<td>${command.raceCode}</td>
				</tr>
			</table>
			</div>
			<br>
			<tabs:tabButtonControls text="edit" target="0"/>
			<hr>
			<strong>Address</strong> <br>
			<br>
			<div class="review">

			<table order="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<td width="20%" class="labelR"><b>Street Address:<b></td>
					<td>${command.address.streetAddress}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>City:<b></td>
					<td>${command.address.city}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>State:</b></td>
					<td>${command.address.stateCode}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Country:</b></td>
					<td>${command.address.countryCode}</td>
				</tr>
				<tr>
					<td width="20%" class="labelR"><b>Zip:<b></td>
					<td>${command.address.postalCode}</td>
				</tr>
			</table>
			</div>

			<br>
			<tabs:tabButtonControls text="edit" target="1"/>
			<hr>
			<strong>Contact Information</strong> <br>
			<br>
			<div class="review">

		<table border="0" cellspacing="0" cellpadding="0" id="mytable">
				<c:forEach items="${command.contactMechanisms}" var="contactMechanism">
					<tr class="results">
						<td width="22%"class="alt" align="right">${contactMechanism.type}:</td>
						<td width="78%"class="alt" align="left">${contactMechanism.value}</td>
					</tr>
				</c:forEach>
			</table>
			</div>

			<br>
			<tabs:tabButtonControls text="edit" target="1"/>
			<hr>
			<strong>Subject Identifiers</strong> <br>
			<br>
			<div class="review">

			<table border="0" cellspacing="0" cellpadding="0" id="mytable">
				<tr>
					<th scope="col" align="left">Assigning Authority</th>
					<th scope="col" align="left">Identifier Type</th>
					<th scope="col" align="left">Identifier</th>
				</tr>
				<c:forEach items="${command.identifiers}" var="identifier">
					<tr class="results">
						<td class="alt" align="left">${identifier.source}</td>
						<td class="alt" align="left">${identifier.type}</td>
						<td class="alt" align="left">${identifier.value}</td>
					</tr>
				</c:forEach>
			</table>
			</div>
			<br>
			<br>
			<tabs:tabButtonControls text="edit" target="0"/>
			<hr>

			</td>
		</tr>
	</table>
</chrome:box>
</form:form>
</body>
</html>
