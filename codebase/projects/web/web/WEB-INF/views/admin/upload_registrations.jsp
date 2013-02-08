<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Upload Registrations</title>
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
	<style type="text/css">
		.eXtremeTable .filter input[type="text"] {
		width:83px;
		}
	</style>


</head>
<!-- MAIN BODY STARTS HERE -->
<body>

<chrome:box title="Import Registration">
<tags:instructions code="upload_registrations" />
    <form action="importRegistration" method="post"
          enctype="multipart/form-data">

        <div class="content">
            <div class="row">
                <div class="label">
                    <fmt:message key="import.selectXMLFileToImport"/>
                </div>
                <div class="value">
                    <div class="fileinputs">
                        <input type="file"  name="file" />
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="value">
                    <tags:button type="submit" size="small" color="blue" value="Import"/>
                </div>
            </div>

             <div class="row">
                <div class="value">
                   <tags:downloadClasspathResource filename="c3pr-domain.xsd" label="Schema File"/>&nbsp;
                </div>
            </div>

            <div class="row">
                <div class="value">
                   <tags:downloadClasspathResource filename="c3pr-sample-registration-import.xml" label="Sample XML File"/>&nbsp;
                </div>
            </div>

			<c:if test="${!empty filePath}">
			<div class="row">
                <div class="value">
                   <tags:downloadResource filename="${filePath}" label="Output XML File"/>
                   &nbsp;<tags:hoverHint keyProp="upload.registration.downloadOutput"/>
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


<chrome:box title="Uploaded Registrations">

    <chrome:division id="single-fields">
        <div id="tableDiv">
            <c:out value="${registrations}" escapeXml="false"/>
        </div>
    </chrome:division>

</chrome:box>


</body>
</html>
