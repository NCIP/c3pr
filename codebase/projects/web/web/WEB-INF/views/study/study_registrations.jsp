<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>

<html>

<!-- MAIN BODY STARTS HERE -->
<body>
<form:form name="form" method="post">
    <tags:tabFields tab="${tab}"/>
    <chrome:box title="${tab.shortTitle}">
        <chrome:division title="Disease" id="disease">
            <registrationTags:searchResults registrations="${participantAssignments }"/>
        </chrome:division>
    </chrome:box>
</form:form>
</body>
<!-- MAIN BODY ENDS HERE -->
</html>