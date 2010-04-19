<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
	<c:when test="${command.studySubject.scheduledEpoch.eligibilityIndicator}">
		<div class="value"><fmt:message key="c3pr.common.yes"/></div>
	</c:when>
	<c:otherwise>
		<div class="value"><fmt:message key="c3pr.common.no"/></div>
		<div align="left"><span class="red"><fmt:message key="registartion.eligibiltyRequired"/></span></div>
	</c:otherwise>
</c:choose>