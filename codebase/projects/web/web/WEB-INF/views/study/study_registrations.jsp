<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>

<html>
    <title><studyTags:htmlTitle study="${command}" /></title>

<!-- MAIN BODY STARTS HERE -->
<body>
<form:form name="form" method="post">
    <tags:tabFields tab="${tab}"/>
</form:form>
    <chrome:box title="${tab.shortTitle}">
        <chrome:division title="Registrations" id="registration">
            <registrationTags:searchResults registrations="${participantAssignments }"/>
        </chrome:division>
    </chrome:box>

</body>
<!-- MAIN BODY ENDS HERE -->
</html>