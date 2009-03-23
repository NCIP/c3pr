<%@ include file="taglibs.jsp"%>

<html>
<head>
<title>Manage Subject</title>
   <style type="text/css">
        div.content {
            padding: 5px 15px;
        }
   </style>
   <script type="text/javascript">
   		function showSearchResults(){
   	   		$('searchForm').submit();
   		 	
   		}
   </script>
</head>
<body>
<chrome:search title="Search">
 <tags:instructions code="participant_search" />
	<form:form id="searchForm" name="searchForm" method="post">
				<div class="content">
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.searchBy"/></div>
                		<div class="value"><form:select path="searchType">
										<form:options items="${searchTypeRefData}" itemLabel="desc" itemValue="code" />
									</form:select></div>
            		</div>
            		<div class="row">
						<div class="label"><fmt:message key="c3pr.common.searchCriteria"/></div>
                		<div class="value"><form:input path="searchText" cssClass="value"/></div>
            		</div>
            		<div class="row">
		                <div class="value">
		                <tags:button type="button" icon="search" size="small" color="blue" value="Search" onclick="$('search-indicator').style.display='';showSearchResults();"/>
		                    <img id="search-indicator" src="<c:url value="/images/indicator.white.gif"/>" alt="activity indicator" style="display:none"/>
		                </div>
		            </div>
            		<!-- <div class="row" align="left">
						<tags:button type="submit" icon="search" size="small" color="blue" value="Search"/>
            		</div> -->
				</div>
		<!-- MAIN BODY ENDS HERE -->
	</form:form>
</chrome:search>

<br>
<c:if test="${participants!=null}">
<chrome:box title="Search Results" id="resultsDiv" >
    <chrome:division id="single-fields">
        <div id="tableDiv">
        	<participanttags:searchResults url="viewParticipant" />
        </div>
    </chrome:division>
</chrome:box>
</c:if>
</body>
</html>
<!-- SUBJECT SEARCH ENDS HERE -->
