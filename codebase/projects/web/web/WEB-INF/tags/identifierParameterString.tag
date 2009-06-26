<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="identifier" required="true" type="edu.duke.cabig.c3pr.domain.Identifier" %>
<c:choose>
	<c:when test="${identifier.class.name=='edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier'}">
		<c:set var="assignedBy" value="organization"/><c:set var="sourceType" value="organizationNciId"/>
		<c:set var="sourceValue" value="${identifier.healthcareSite.ctepCode}"/>
	</c:when>
	<c:otherwise>
		<c:set var="assignedBy" value="system"/><c:set var="sourceType" value="systemName"/>
		<c:set var="sourceValue" value="${identifier.systemName}"/>
	</c:otherwise>
</c:choose>
<c:set var="idValue" value="${identifier.value}" scope="request"/>
<c:if test="${not empty idValue}">
	assignedBy=${assignedBy}&${sourceType}=${sourceValue}&identifierType=${identifier.type}&identifier=<%=java.net.URLEncoder.encode((java.lang.String)request.getAttribute("idValue"))%>
</c:if>