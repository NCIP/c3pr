<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title>Organization Search</title>
   <tags:dwrJavascriptLink objects="SearchOrganizationAjaxFacade"/>
   <style type="text/css">
        div.content {
            padding: 5px 15px;
        }
   </style>
    <script type="text/javascript">
    dwr.engine.setErrorHandler(handleDWRError);
	 function handleDWRError(err){
	 }
	 
        function buildTable(form) {
        	params = new Array(2);
			var parameterMap = getParameterMap(form);

            params[0] = document.getElementById("name").value;
            params[1] = document.getElementById("primaryIdentifier").value;
            
            SearchOrganizationAjaxFacade.getTable(parameterMap, params, showTable);
        }

        function showTable(table) {
            $('resultsDiv').style.display="block";
            $('search-indicator').style.display="none";
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
                    if ( idElement == "primaryIdentifier" || idElement == "name") {
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
<!-- MAIN BODY STARTS HERE -->
<body>

<div id="main">
<chrome:search title="Search">
<tags:instructions code="organization_search" />
    <form name="searchForm" id="searchForm" method="post">
    	
        <div>
            <input type="hidden" name="_selected" id="_selected" value="">
            <input type="hidden" name="_action" id="_action" value="">
        </div>
        <div class="content">
            <div class="row">
                <div class="label">
                    <fmt:message key="c3pr.common.name"/>
                </div>
                <div class="value">
                    <input type="text" name="name" id="name" size="35"/>
                </div>
            </div>
            <div class="row">
                <div class="label">
                    <fmt:message key="c3pr.common.CTEPIdentifier"/>&nbsp;(Primary)
                </div>
                <div class="value">
                    <input type="text" name="primaryIdentifier" id="primaryIdentifier"/>
                    <tags:hoverHint keyProp="organization.ctepCode"/>
                </div>
            </div>

            <div class="row">
                <div class="value">
                <tags:button type="button" icon="search" size="small" color="blue" value="Search" onclick="$('search-indicator').style.display='';buildTable('searchForm');"/>
                    <img id="search-indicator" src="<c:url value="/images/indicator.white.gif"/>" alt="activity indicator" style="display:none"/>
                </div>
            </div>
        </div>
    </form>
</chrome:search>

<chrome:box title="Search Results" id="resultsDiv" style="display:none">
    <chrome:division id="single-fields">
        <div id="tableDiv">
        </div>
    </chrome:division>
</chrome:box>

</div>
</body>
</html>
