<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>[Study Search]</title>
</head>
<!-- MAIN BODY STARTS HERE -->
<body>

<chrome:search title="Search">
<tags:instructions code="study_search" />
    <form:form id="searchForm" method="post">
        <div class="content">
            <div class="row">
                <div class="label">
                    <fmt:message key="c3pr.common.searchBy"/>
                </div>
                <div class="value">
                    <form:select path="searchType">
                        <form:options items="${searchTypeRefData}" itemLabel="desc" itemValue="code"/>
                    </form:select>
                </div>
            </div>
            <div class="row">
                <div class="label">
                    <fmt:message key="c3pr.common.searchCriteria"/>
                </div>
                <div class="value">
                    <form:input path="searchText" cssClass="value"/>
                    <span id="searchTextMsg" style="display:inline"> </span>
                </div>
            </div>

            <div class="row">
                <div class="value">
                    <tags:button type="submit" icon="search" size="small" color="blue" value="Search" />
                </div>
            </div>
        </div>
    </form:form>
</chrome:search>

<br>
<c:if test="${studies!=null}">
<chrome:box title="Search Results">
    <chrome:division id="single-fields">
            <div id="tableDiv">
                <c:out value="${studies}" escapeXml="false"/>
            </div>
        </chrome:division>
</chrome:box>
</c:if>
</body>
</html>