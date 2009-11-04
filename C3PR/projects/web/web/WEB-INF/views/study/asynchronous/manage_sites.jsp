<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<chrome:box title="Multisite messages for ${localSite.healthcareSite.name}">
<c:forEach items="${site.endpoints}" var="endpoint" varStatus="status">
	<chrome:division title="${endpoint.apiName.displayName } at ${site.healthcareSite.name }" style="text-align: left;">
	<div align="left" style="font-size: 1.4em; ${endpoint.status=='MESSAGE_SEND_FAILED'?'color: red;':'color: green;' }">${endpoint.status.code }</div>
	<c:if test="${endpoint.status=='MESSAGE_SEND_FAILED'}"><tags:displayErrors id="endpoint-errors" errors="${endpoint.errors}"></tags:displayErrors></c:if>
	</chrome:division>
</c:forEach>
<c:if test="${fn:length(site.endpoints)==0 && fn:length(site.possibleEndpoints)==0}">There are no messages.</c:if>
</chrome:box>