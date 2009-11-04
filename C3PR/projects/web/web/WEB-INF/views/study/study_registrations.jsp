<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
    <title><studyTags:htmlTitle study="${command.study}" /></title>

<!-- MAIN BODY STARTS HERE -->
<body>
<form:form name="form" method="post">
    <tags:tabFields tab="${tab}"/>
</form:form>
    <chrome:box title="${tab.shortTitle}">
    	<c:choose>
    		<c:when test="${fn:length(participantAssignments) != 0}">
    			<registrationTags:searchResults registrations="${participantAssignments }"/>
    		</c:when>
    		<c:otherwise>
				<fmt:message key="study.noRegistration"></fmt:message>    			
    		</c:otherwise>
    	</c:choose>
            
    </chrome:box>

</body>
<!-- MAIN BODY ENDS HERE -->
</html>