<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<chrome:box title="Multisite messages for ${site.healthcareSite.name}">
<c:forEach items="${site.endpoints}" var="endpoint" varStatus="status">
	<chrome:division title="${endpoint.apiName.displayName }" style="text-align: left;">
	<div align="left" style="font-size: 1.4em; ${endpoint.status=='MESSAGE_SEND_FAILED'?'color: red;':'color: green;' }">${endpoint.status.code }</div>
	<c:if test="${endpoint.status=='MESSAGE_SEND_FAILED'}"><tags:displayErrors id="endpoint-errors" errors="${endpoint.errors}"></tags:displayErrors></c:if>
	</chrome:division>
</c:forEach>
</chrome:box>
