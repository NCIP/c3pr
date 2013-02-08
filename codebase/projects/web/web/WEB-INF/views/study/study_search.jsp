<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>[Study Search]</title>
    <script type="text/javascript">
	    function toggleStatus(){
	    	if($('searchType').value == 'status'){
	    		$('searchTextMsg').style.display = "none";
	    		$('statusSearchTextMsg').style.display = "";
	    	} else {
	    		$('searchTextMsg').style.display = "";
	    		$('statusSearchTextMsg').style.display = "none";
	    	}
	    }
    </script>
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
                    <form:select path="searchType" onchange="toggleStatus();">
                        <form:options items="${searchTypeRefData}" itemLabel="desc" itemValue="code"/>
                    </form:select>
                </div>
            </div>
            <div class="row">
                <div class="label">
                    <fmt:message key="c3pr.common.searchCriteria"/>
                </div>
                <div class="value">
                    <span id="searchTextMsg" style="${command.searchType=='status'?'display:none':'display:inline'}">
                    	<form:input path="searchText" cssClass="value"/>
                    </span>
                    
                    <span id="statusSearchTextMsg" style="${command.searchType=='status'?'display:':'display:none'}"> 
	                    <form:select path="statusSearchText">
	                        <form:options items="${studyStatus}" itemLabel="desc" itemValue="code"/>
	                    </form:select>
                    </span>
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
