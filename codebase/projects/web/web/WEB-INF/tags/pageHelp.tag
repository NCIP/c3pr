<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@attribute name="propertyKey"%>

<c:url value="/help/Sample_project.htm" scope="request" var="_c3prHelpURL" />
<c:set var="roboHelpKey">ROBOHELP_${propertyKey}</c:set>

<!-- 
<c:out value="In page help "></c:out>
<c:out value="PropertyKey: ${propertyKey}"></c:out>
<c:out value="C3pr help url: ${_c3prHelpURL}"></c:out>
<c:out value=" RoboHelp link : ${roboHelpLink}"></c:out>  -->

<spring:message var="roboHelpLink" code="${roboHelpKey}" text="NO_${roboHelpKey}"/>
<script>
Event.observe(window, "load",updateHelpLink('${_c3prHelpURL}','${roboHelpLink}'));
</script>