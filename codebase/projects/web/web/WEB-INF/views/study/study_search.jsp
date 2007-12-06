<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>

<html>
<head>
    <script type="text/javascript" src="<c:url value="/js/extremecomponents.js"/>"></script>
        
   <tags:dwrJavascriptLink objects="StudyAjaxFacade"/>


    <script type="text/javascript">
        function buildTable(form) {
            var parameterMap = getParameterMap(form);
            StudyAjaxFacade.getTable(parameterMap, showTable);
        }

        function showTable(table) {
            document.getElementById('tableDiv').innerHTML=table;
        }
        
        function submitPostProcess(formElement, flag){
        	if(formElement.id="searchForm"){
        		if(flag)
	        		buildTable('searchForm');
    	    	return false;
    	    }
    	    return flag;
        }
    </script>
</head>
<!-- MAIN BODY STARTS HERE -->
<body>


<chrome:search title="Search">

    <form:form id="searchForm" method="post">
        <div>
            <input type="hidden" name="_selected" id="_selected" value="">
            <input type="hidden" name="_action" id="_action" value="">
        </div>
        <div class="content">
            <div class="row">
                <div class="label">
                    Search By:
                </div>
                <div class="value">
                    <form:select path="searchType">
                        <form:options items="${searchTypeRefData}" itemLabel="desc" itemValue="code"/>
                    </form:select>
                </div>
            </div>
            <div class="row">
                <div class="label required-indicator">
                    Search Criteria:
                </div>
                <div class="value">
                    <form:input path="searchText" cssClass="validate-notEmpty"/>
                </div>
            </div>

            <div class="row">
                <div class="value">
                    <input class='ibutton' type='submit' value='Search'  title='Search Study'/>
                </div>
            </div>
        </div>
    </form:form>
</chrome:search>

<br>

<chrome:box title="Search Results">
    <chrome:division id="single-fields">
            <div id="tableDiv">
                <c:out value="${studies}" escapeXml="false"/>
            </div>
        </chrome:division>

</chrome:box>

</body>
</html>