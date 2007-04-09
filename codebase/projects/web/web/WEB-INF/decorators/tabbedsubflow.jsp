<%-- This is the standard tabbedFlow decorator for respective C3PR pages --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <link rel="shortcut icon" href="<tags:imageUrl name="favicon.ico"/>" type="image/x-icon">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>C3PRV2</title>

    <script>
        function postRegistrationController(targetPage, currentPage) {
            document.getElementById('RegistrationControllerForm')._target.name = "_target" + targetPage;
            document.getElementById('RegistrationControllerForm')._page.name = "_page" + currentPage;
            document.getElementById('RegistrationControllerForm').submit();
        }
    </script>

    <tags:include/>
    <tags:stylesheetLink name="tabbedflow"/>
    <tags:javascriptLink name="tabbedflow"/>
    <decorator:head/>
</head>

<body>

<div id="content">
    <layout:header/>
    <layout:navigation/>

    <div class="tabpane">
        <tabs:levelTwoTabs tab="${tab}" flow="${flow}"/>

        <div class="tabcontent workArea">
            <decorator:body/>
            <tabs:tabControls tabNumber="${tab.number}" isLast="${tab.number < flow.tabCount - 1}"/>
        </div>

    </div>

    <form:form id="RegistrationControllerForm">
        <input type="hidden" name="_target" id="_target"/>
        <input type="hidden" name="_page" id="_page"/>
    </form:form>
    <form:form id="flowredirect">
        <input type="hidden" name="_target${tab.targetNumber}" id="flowredirect-target"/>
        <input type="hidden" name="_page${tab.number}"/>
    </form:form>

</div>

<div id="footer">
    <layout:footer/>
</div>

</body>
</html>
