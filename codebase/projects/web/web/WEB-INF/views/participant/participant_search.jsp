<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
<title>Manage Subject</title>
<tags:dwrJavascriptLink objects="SearchParticipantAjaxFacade"/>
   <style type="text/css">
        div.content {
            padding: 5px 15px;
        }
   </style>
   <script type="text/javascript">
	   function buildTable(form) {
	   	params = new Array(2);
			var parameterMap = getParameterMap(form);
	
	       params[0] = document.getElementById("searchType").value;
	       params[1] = document.getElementById("searchText").value;
	       
	       SearchParticipantAjaxFacade.getTable(parameterMap, params, showTable);
	   }
	
	   function showTable(table) {
	   	$('resultsDiv').style.display="block";
	   	$('search-indicator').style.display='none'
	       document.getElementById('tableDiv').innerHTML=table;
	   }

	   function catchKey(e){
       	var evt=(e)?e:(window.event)?window.event:null;
       	if(evt){  
           	if (evt.keyCode == 13) {
           		var idElement ;
           		if(is_ie){
						idElement = evt.srcElement.id 
               	}else{
               		idElement = evt.target.id
               	}
                   if (  idElement == "searchText" ){
                   	$('search-indicator').style.display=''
                   	buildTable('searchForm');
                   }
                   return false;
               
           	}
       	}
         }
       document.onkeypress = catchKey;
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
		                <tags:button type="button" icon="search" size="small" color="blue" value="Search" onclick="$('search-indicator').style.display='';buildTable('searchForm');"/>
		                    <img id="search-indicator" src="<c:url value="/images/indicator.white.gif"/>" alt="activity indicator" style="display:none"/>
		                </div>
		            </div>
				</div>
		<!-- MAIN BODY ENDS HERE -->
	</form:form>
</chrome:search>

<chrome:box title="Search Results" id="resultsDiv" style="display:none">
    <chrome:division id="single-fields">
        <div id="tableDiv">
        </div>
    </chrome:division>
</chrome:box>
</body>
</html>
<!-- SUBJECT SEARCH ENDS HERE -->
