<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tabs:division id="Summary" title="Study Summary">

<table width="100%" border="0" cellspacing="2" cellpadding="0" id="table1">
<tr>
	<td><b><fmt:message key="c3pr.common.primaryIdentifier"/>:</b></td>
</tr>
<tr>
	<td>${command.study.primaryIdentifier}</td>
</tr>
<tr>
	<td><b><fmt:message key="study.shortTitle"/>:</b></td>
</tr>
<tr>
	<td>${command.study.trimmedShortTitleText}</td>
</tr>
<tr>
	<td><b><fmt:message key="c3pr.common.targetAccrual"/>:</b></td>
</tr>
<tr>
	<td>${command.study.targetAccrualNumber}</td>
</tr>
<tr>
	<td><b><fmt:message key="c3pr.common.status"/>:</b></td>
</tr>
<tr>
	<td>${command.study.coordinatingCenterStudyStatus.code}</td>
</tr>
<tr>
	<td><b><fmt:message key="study.sponsor"/>:</b></td>
</tr>
<tr>
	<td>${command.study.identifiers[0].value}</td>
</tr>
<tr>
	<td><b><fmt:message key="study.phase"/>:</b></td>
</tr>
<tr>
	<td>${command.study.phaseCode}</td>
</tr>
<tr>
	<td><b><fmt:message key="c3pr.common.type"/>:</b></td>
</tr>
<tr>
	<td>${command.study.type}</td>
</tr>
<c:forEach begin="1" end="6">
<tr>
	<td>
		<br>
	</td>
	</tr>
</c:forEach>
</table>
</tabs:division>