<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>

<html>
<head>
    <script type="text/javascript">
        function navRollOver(obj, state) {
            document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
        }
        function submitPage() {
            document.getElementById("searchForm").submit();
        }
    </script>
</head>
<!-- MAIN BODY STARTS HERE -->
<body>


<chrome:search title="Search">

    <form:form id="searchForm" name="searchForm" method="post">
        <div>
            <input type="hidden" name="_selected" id="_selected" value="">
            <input type="hidden" name="_action" id="_action" value="">
        </div>
        <div class="content">
            <div class="row">
                <div class="label">
                    Search Studies By:
                </div>
                <div class="value">
                    <form:select path="searchType">
                        <form:options items="${searchTypeRefData}" itemLabel="desc" itemValue="code"/>
                    </form:select>
                </div>
            </div>
            <div class="row">
                <div class="label">
                    Search Criteria:
                </div>
                <div class="value">
                    <form:input path="searchText"/>
                </div>
            </div>
            <div class="row">
                <div class="value">
                    <input type="submit" value="Search" />
                </div>
            </div>
        </div>
    </form:form>
</chrome:search>

<br>

<chrome:box title="Search Results">
    <form:form>
        <chrome:division id="single-fields">
            <studyTags:searchResults url="editStudy"/>
        </chrome:division>
    </form:form>
</chrome:box>

</body>
</html>