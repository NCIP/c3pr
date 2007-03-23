<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<tabs:division id="Summary" title="Summary">

<table width="100%" border="0" cellspacing="2" cellpadding="0" id="table1">
<tr>
	<td><b>Short Title:</b></td>
</tr>
<tr>
	<td>${command.trimmedShortTitleText}</td>
</tr>
<tr>
	<td><b>Primary Identifier:</b></td>
</tr>
<tr>
	<td>${command.primaryIdentifier}</td>
</tr>
<tr>
	<td><b>Target Accrual No:</b></td>
</tr>
<tr>
	<td>${command.targetAccrualNumber}</td>
</tr>
<tr>
	<td><b>Status:</b></td>
</tr>
<tr>
	<td>${command.status}</td>
</tr>
<tr>
	<td><b>Sponsor:</b></td>
</tr>
<tr>
	<td>${command.sponsorCode}</td>
</tr>
<tr>
	<td><b>Type:</b></td>
</tr>
<tr>
	<td>${command.type}</td>
</tr>
</table>
</tabs:division>