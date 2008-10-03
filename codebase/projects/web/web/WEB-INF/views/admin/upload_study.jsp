<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Upload Studies</title>
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
    <form action="importStudy" method="post"
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
                   <tags:downloadClasspathResource filename="c3pr-domain.xsd" label="Schema File"/>
                </div>
            </div>

            <div class="row">
                <div class="value">
                   <tags:downloadClasspathResource filename="C3PR-JointStudy.xml" label="Sample XML File"/>
                </div>
            </div>
            
            <c:if test="${!empty filePath}">
			<div class="row">
                <div class="value">
                   <tags:downloadResource filename="${filePath}" label="Output XML File"/>
                   &nbsp;<tags:hoverHint keyProp="upload.study.downloadOutput"/>
                </div>
            </div>
            </c:if>


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


<chrome:box title="Uploaded Studies">

    <chrome:division id="single-fields">
        <div id="tableDiv">
            <c:out value="${studies}" escapeXml="false"/>
        </div>
    </chrome:division>

</chrome:box>


</body>
</html>