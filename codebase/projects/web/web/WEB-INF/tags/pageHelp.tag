<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@attribute name="propertyKey"%>

<c:url value="https://cabig-kc.nci.nih.gov/CTMS/KC/index.php/C3PR_End_User_Guide" scope="request" var="_c3prHelpURL" />
<c:set var="roboHelpKey">ROBOHELP_${propertyKey}</c:set>

<spring:message var="roboHelpLink" code="${roboHelpKey}" text="NO_${roboHelpKey}"/>
<script>
Event.observe(window, "load",updateHelpLink('${_c3prHelpURL}','${roboHelpLink}'));
</script>