<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="c3pr" uri="http://edu.duke.cabig.c3pr.web/c3pr" %>
<%@attribute name="preSelectedSiteId" type="java.lang.String" %> <!-- this message will be displayed when value id blank -->
<%@attribute name="privilege" type="java.lang.String" %> 
<!-- for all roles connected to this privilege we load the associated orgs for logged in user-->
<option value="">Please Select</option>
<c:forEach items="${c3pr:getLoggedInUsersOrganizations(privilege, pageContext.servletContext)}" var="site">
	<c:choose>
		<c:when test="${!empty preSelectedSiteId && preSelectedSiteId == site.id}">
			<option value="${site.id}" selected>${site}</option>
		</c:when>
		<c:otherwise>
			<option value="${site.id}">${site}</option>
		</c:otherwise>
	</c:choose>
</c:forEach>