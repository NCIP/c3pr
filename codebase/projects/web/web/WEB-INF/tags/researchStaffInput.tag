<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@attribute name="commandClass" required="true"%>
<%@attribute name="value" %>
<%@attribute name="path" required="true" %>
<%@attribute name="size" %>
<%@attribute name="cssClass" %>
<c:if test="${empty size}">
	<c:set property="size" value="25"></c:set>
</c:if>
<c:choose>
	<c:when test="${commandClass eq 'class edu.duke.cabig.c3pr.domain.RemoteResearchStaff'}">
		<c:choose>
			<c:when test="${!empty value}">
				<div class="value">${value}</div>
			</c:when>
			<c:otherwise>
				<div class="value">
					<span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span>
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<div class="value">
			<form:input size="${size}" path="${path}" cssClass="${cssClass}" />
		  </div>
	</c:otherwise>
</c:choose>