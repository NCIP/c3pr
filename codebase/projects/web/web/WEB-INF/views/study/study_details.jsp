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
<style type="text/css">
        .label { width: 10em; text-align: right; padding: 4px; }
</style>
</head>
<body>
<form:form name="form" method="post">
<tabs:tabFields tab="${tab}" />
<div><tabs:division id="study-details">
<!-- MAIN BODY STARTS HERE -->
<table border="0" cellspacing="0" cellpadding="0">
<div>
		<input type="hidden" name="_action" value="">
</div>
<tags:hasErrorsMessage/>
<tr>
	<td>
	<table border="0" cellspacing="0" cellpadding="0" id="table1">
		<tr>
			<td class="label">Short Title:</td>
			<td><form:input path="shortTitleText" size="40" maxlength="30"/></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em>Long Title:</td>
			<td><form:textarea path="longTitleText" rows="4" cols="40" cssClass="validate-notEmpty&&maxlength200" /></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label">Precis:</td>
			<td><form:textarea path="precisText" rows="2" cols="40" cssClass="validate-maxlength200" /></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label">Description:</td>
			<td><form:textarea path="descriptionText" rows="3" cols="40" cssClass="validate-maxlength200" /></td>
		</tr>
	</table>
	</td>
	<td class="contentAreaR"><strong><strong><strong></strong></strong></strong>
	<table  border="0" cellspacing="0" cellpadding="0"
		id="table1">
		<tr>
			<td class="label">Target Accrual:</td>
			<td><form:input path="targetAccrualNumber" size="10" cssClass="validate-numeric"/></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em>Status:</td>
			<td><form:select path="status" cssClass="validate-notEmpty">
				<option value="">--Please Select-- </option>
				<form:options
					items="${statusRefData}" itemLabel="desc" itemValue="desc" />
			</form:select></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em>Phase:</td>
			<td><form:select path="phaseCode"  cssClass="validate-notEmpty">
				<option value="">--Please Select-- </option>
				<form:options
					items="${phaseCodeRefData}" itemLabel="desc" itemValue="desc" />
			</form:select></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em>Sponsor:</td>
			<td><form:select path="sponsorCode" cssClass="validate-notEmpty">
				<option value="">--Please Select-- </option>
				<form:options
					items="${sponsorCodeRefData}" itemLabel="desc"
					itemValue="desc" />
			</form:select></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em>Randomized</td>
			<td><form:select path="randomizedIndicator">
				<form:options items="${randomizedIndicatorRefData}"
					itemLabel="desc" itemValue="code" />
			</form:select></td>
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
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label">Blinded</td>
			<td><form:select path="blindedIndicator">
				 <form:options items="${blindedIndicatorRefData}"
					itemLabel="desc" itemValue="code" />
			</form:select></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>"
				width="1" height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td class="label"><span class="red">*</span><em></em>Type:</td>
			<td><form:select path="type" cssClass="validate-notEmpty">
				<option value="">--Please Select--</option>
				<form:options
					items="${typeRefData}" itemLabel="desc" itemValue="desc" />
			</form:select></td>
		</tr>
	</table>
	</td>
</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</tabs:division>
</form:form>
</table>
</body>
</html>
