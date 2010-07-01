<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c3pr" uri="http://edu.duke.cabig.c3pr.web/c3pr" %>

<%@attribute name="commandClass" required="true"%>
<%@attribute name="value" %>
<%@attribute name="path" required="true" %>
<%@attribute name="size" %>
<%@attribute name="cssClass" %>
<%@attribute name="onkeyup" %>
<c:if test="${empty size}">
	<c:set var="size" value="25"></c:set>
</c:if>
<c:set var="showTextOnly" value="true" />

<c3pr:checkprivilege hasPrivileges="RESEARCHSTAFF_CREATE,RESEARCHSTAFF_UPDATE">
	<c:set var="showTextOnly" value="false"></c:set>
</c3pr:checkprivilege>
<c:choose>
	<c:when test="${FLOW == 'SETUP_FLOW'}">
		<c:set var="showTextOnly" value="false" />
	</c:when>
	<c:when test="${commandClass eq 'class edu.duke.cabig.c3pr.domain.RemoteResearchStaff'}">
		<c:set var="showTextOnly" value="true" />
	</c:when>
	<c:when test="${!empty showTextOnly}">
		<c:set var="showTextOnly" value="${showTextOnly}" />
	</c:when>
	<c:otherwise>
		<c:set var="showTextOnly" value="false" />
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${showTextOnly}">
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