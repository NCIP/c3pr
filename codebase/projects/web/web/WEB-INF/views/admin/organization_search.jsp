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
        function buildTable(form) {
        	params = new Array(2);
			var parameterMap = getParameterMap(form);

            params[0] = document.getElementById("name").value;
            params[1] = document.getElementById("nciIdentifier").value;
            
            SearchOrganizationAjaxFacade.getTable(parameterMap, params, showTable);
        }

        function showTable(table) {
            document.getElementById('tableDiv').innerHTML=table;
        }
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
                    <input type="text" name="name" id="name"/>
                </div>
            </div>
            <div class="row">
                <div class="label">
                    <fmt:message key="c3pr.common.NCIIdentifier"/>
                </div>
                <div class="value">
                    <input type="text" name="nciIdentifier" id="nciIdentifier"/>
                    <tags:hoverHint keyProp="organization.nciInstituteCode"/>
                </div>
            </div>

            <div class="row">
                <div class="value">
                    <input type='button' onClick="buildTable('searchForm');" value='Search' title='Search'/>
                </div>
            </div>
        </div>
    </form>
</chrome:search>

<br>
<script>
	//buildTable('searchForm');
</script>

<chrome:box title="Search Results">
    <chrome:division id="single-fields">
        <div id="tableDiv">
        </div>
    </chrome:division>
</chrome:box>

</div>
</body>
</html>