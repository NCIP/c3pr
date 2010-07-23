<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="value"%>
<c:choose>
	<c:when test="${not empty value}">
		${value} 
	</c:when> 
	<c:otherwise>
		<span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span>
	</c:otherwise>
</c:choose>
