<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title>${tab.longTitle}</title>
    <script type="text/javascript" src="<c:url value="/js/extremecomponents.js"/>"></script>
    <tags:dwrJavascriptLink objects="StudyXMLFileImportAjaxFacade"/>


    <script type="text/javascript">
        function buildTable(form) {
            var parameterMap = getParameterMap(form);
            StudyXMLFileImportAjaxFacade.getTable(parameterMap, showTable);
        }

        function showTable(table) {
            document.getElementById('tableDiv').innerHTML=table;
        }
    </script>

</head>
<!-- MAIN BODY STARTS HERE -->
<body>

<chrome:box title="Import Study">
    <form:form action="importStudy" method="post"
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
        </div>
        
    </form:form>

</chrome:box>


<chrome:box title="Uploaded Studies">

        <chrome:division id="single-fields">
            <div id="tableDiv">
                <c:out value="${studies}" escapeXml="false"/>
            </div>
        </chrome:division>
     
</chrome:box>

 
</body>
</html>