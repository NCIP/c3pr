<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>

<html>
<head>
    <title>${tab.longTitle}</title>
    <script type="text/javascript" src="<c:url value="/js/extremecomponents.js"/>"></script>
    <tags:dwrJavascriptLink objects="StudySubjectXMLFileImportAjaxFacade"/>


    <script type="text/javascript">
        function buildTable(form) {
            var parameterMap = getParameterMap(form);
            StudySubjectXMLFileImportAjaxFacade.getTable(parameterMap, showTable);
        }

        function showTable(table) {
            document.getElementById('tableDiv').innerHTML=table;
        }
    </script>

</head>
<!-- MAIN BODY STARTS HERE -->
<body>

<chrome:box title="Import Registration">
    <form action="importRegistration" method="post"
          enctype="multipart/form-data">

        <div class="content">
            <div class="row">
                <div class="label">
                    Select XML file to Import:
                </div>
                <div class="value">
                    <div class="fileinputs">
                        <input type="file"  name="file" />
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="value">
                    <input type="submit" value="Import" />
                </div>
            </div>

            <div class="row">
                <div class="value">
                    <spring:hasBindErrors name="command">
                        <ul class="errors">
                            <spring:bind path="command.*">
                                <c:forEach items="${status.errorCodes}" var="error">
                                    <li><c:out value="${error}"/></li>
                                </c:forEach>
                            </spring:bind>
                        </ul>
                    </spring:hasBindErrors>
                </div>
            </div>
        </div>
    </form>

</chrome:box>


<chrome:box title="Uploaded Registrations">

    <chrome:division id="single-fields">
        <div id="tableDiv">
            <c:out value="${registrations}" escapeXml="false"/>
        </div>
    </chrome:division>

</chrome:box>


</body>
</html>