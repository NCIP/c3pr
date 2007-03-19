<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator"
prefix="decorator"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>

</head>
<body>
<tabs:body title="${flow.name}: ${tab.longTitle} - Short Title: ${command.trimmedShortTitleText}">
<form:form method="post" cssClass="standard">
<tabs:tabFields tab="${tab}" />
<div><tabs:division id="study-details">
<!-- MAIN BODY STARTS HERE -->
<table width="75%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="50%" valign="top">

	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		id="table1">
		<tr>
			<td class="label">Short Title:</td>
			<td><form:textarea path="shortTitleText" rows="2"
				cols="50" /></td>
			<td width="15%"></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em>Long
			Title:</td>
			<td><form:textarea path="longTitleText" rows="5" cols="50" /></td>
			<td width="15%"><em><span class="red"><form:errors
				path="longTitleText" /></em></span></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label">Precis Text:</td>
			<td><form:textarea path="precisText" rows="2" cols="50" /></td>
			<td width="15%"></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label">Description Text:</td>
			<td><form:textarea path="descriptionText" rows="3"
				cols="50" /></td>
			<td width="15%"></td>
		</tr>
	</table>
	</td>
	<td width="100%" valign="top" class="contentAreaR"><strong><strong><strong></strong></strong></strong>
	<table width="80%" border="0" cellspacing="0" cellpadding="0"
		id="table1">
		<tr>
			<td class="label">Target Accrual Number:</td>
			<td><form:input path="targetAccrualNumber" size="34" /></td>
			<td width="10%"></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em>Status:</td>
			<td><form:select path="status">
				<option value="">--Please Select-- <form:options
					items="${statusRefData}" itemLabel="desc" itemValue="desc" />
			</form:select></td>
			<td width="10%"><em><span class="red"><form:errors
				path="status" /></em></span></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em><strong>Disease:</strong>
			<td><form:select path="diseaseCode">
				<option value="">--Please Select-- <form:options
					items="${diseaseCodeRefData}" itemLabel="desc"
					itemValue="desc" />
			</form:select></td>
			<td width="10%"><em><span class="red"><form:errors
				path="diseaseCode" /></em></span></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><em></em>Monitor:</td>
			<td><form:select path="monitorCode">
				<option value="">--Please Select-- <form:options
					items="${monitorCodeRefData}" itemLabel="desc"
					itemValue="desc" />
			</form:select></td>
			<td width="10%"></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em>Phase:</td>
			<td><form:select path="phaseCode">
				<option value="">--Please Select-- <form:options
					items="${phaseCodeRefData}" itemLabel="desc" itemValue="desc" />
			</form:select></td>
			<td width="10%"><em><span class="red"><form:errors
				path="phaseCode" /></em></span></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em>Sponsor:</td>
			<td><form:select path="sponsorCode">
				<option value="">--Please Select-- <form:options
					items="${sponsorCodeRefData}" itemLabel="desc"
					itemValue="desc" />
			</form:select></td>
			<td width="10%"><em><span class="red"><form:errors
				path="sponsorCode" /></em></span></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em>Randomized
			Indicator</td>
			<td><form:select path="randomizedIndicator">
				<form:options items="${randomizedIndicatorRefData}"
					itemLabel="desc" itemValue="code" />
			</form:select></td>
			<td width="10%"></td>
		</tr>

		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em>Multi
			Institution:</td>
			<td><form:select path="multiInstitutionIndicator">
				<form:options items="${multiInstitutionIndicatorRefData}"
					itemLabel="desc" itemValue="code" />
			</form:select></td>
			<td width="15%"></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label">Blinded Indicator:</td>
			<td><form:select path="blindedIndicator">
				<form:options items="${blindedIndicatorRefData}"
					itemLabel="desc" itemValue="code" />
			</form:select></td>
			<td width="15%"></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em>Type:</td>
			<td><form:select path="type">
				<option value="">--Please Select-- <form:options
					items="${typeRefData}" itemLabel="desc" itemValue="code" />
			</form:select></td>
			<td width="15%"><em><span class="red"><form:errors
				path="type" /></em></span></td>
		</tr>
	</table>
	</td>
</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</tabs:division>
</form:form>
</tabs:body>
</table>
</body>
</html>
