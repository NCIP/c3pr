<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<tabs:division id="Summary" title="Study Summary">

<table width="100%" border="0" cellspacing="2" cellpadding="0" id="table1">
<tr>
	<td><font size="2" color="blue"><b>Short Title:</b></font></td>
</tr>
<tr>
	<td>${command.trimmedShortTitleText}</td>
</tr>
<tr>
	<td><font size="2" color="blue"><b>Primary Identifier:</b></font></td>
</tr>
<tr>
	<td>${command.primaryIdentifier}</td>
</tr>
<tr>
	<td><font size="2" color="blue"><b>Target Accrual No:</b></font></td>
</tr>
<tr>
	<td>${command.targetAccrualNumber}</td>
</tr>
<tr>
	<td><font size="2" color="blue"><b>Status:</b></font></td>
</tr>
<tr>
	<td>${command.status}</td>
</tr>
<tr>
	<td><font size="2" color="blue"><b>Sponsor:</b></font></td>
</tr>
<tr>
	<td>${command.sponsorCode}</td>
</tr>
<tr>
	<td><font size="2" color="blue"><b>Type:</b></font></td>
</tr>
<tr>
	<td>${command.type}</td>
</tr>
<c:forEach begin="1" end="10">
<tr>
	<td>
		<br>
	</td>
	</tr>
</c:forEach>
</table>
</tabs:division>