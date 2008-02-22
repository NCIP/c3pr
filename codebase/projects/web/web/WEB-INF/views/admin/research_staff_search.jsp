<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>

<html>
<head>
   <script type="text/javascript" src="<c:url value="/js/extremecomponents.js"/>"></script>  
    <tags:includeScriptaculous />       
   <tags:dwrJavascriptLink objects="SearchResearchStaffAjaxFacade"/>
    <tags:dwrJavascriptLink objects="OrganizationAjaxFacade"/>
   <style type="text/css">
        div.content {
            padding: 5px 15px;
        }
   </style>
    <script type="text/javascript">
    	var sponsorSiteAutocompleterProps = {
            basename: "healthcareSite",
            populator: function(autocompleter, text) {
                OrganizationAjaxFacade.matchHealthcareSites(text,function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
                return (obj.name+" ("+obj.nciInstituteCode+")")
            },
            afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=sponsorSiteAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
			 }
        }
        AutocompleterManager.addAutocompleter(sponsorSiteAutocompleterProps);
        function buildTable(form) {
        	params = new Array(3);
			var parameterMap = getParameterMap(form);

            params[0] = document.getElementById("firstName").value;
            params[1] = document.getElementById("lastName").value;
            params[2] = document.getElementById("nciIdentifier").value;
            params[3] = document.getElementById("healthcareSite-hidden").value;
            
            SearchResearchStaffAjaxFacade.getTable(parameterMap, params, showTable);
        }

        function showTable(table) {
            document.getElementById('tableDiv').innerHTML=table;
        }
    </script>
</head>
<!-- MAIN BODY STARTS HERE -->
<body>
<div class="tabpane">
  <ul id="workflow-tabs" class="tabs autoclear">
    <li class="tab selected"><div>
        <a href="../admin/searchResearchStaff">Search Research Staff</a>
    </div></li>
    <li class="tab"><div>
        <a href="../admin/createResearchStaff">Create Research Staff</a>
    </div></li>
  </ul>
</div>

<div id="main">
<br />
<chrome:search title="Search">

    <form name="searchForm" id="searchForm" method="post">
    	
        <div>
            <input type="hidden" name="_selected" id="_selected" value="">
            <input type="hidden" name="_action" id="_action" value="">
        </div>
        <div class="content">
            <div class="row">
                <div class="label">
                    First Name:
                </div>
                <div class="value">
                    <input type="text" name="firstName" id="firstName"/>
                </div>
            </div>
            <div class="row">
                <div class="label">
                    Last Name:
                </div>
                <div class="value">
                    <input type="text" name="lastName" id="lastName"/>
                </div>
            </div>
            <div class="row">
                <div class="label">
                    NCI Identifier:
                </div>
                <div class="value">
                    <input type="text" name="nciIdentifier" id="nciIdentifier"/>
                    <tags:hoverHint keyProp="researchStaff.nciIdentifier"/>
                </div>
            </div>
            <div class="row">
                <div class="label">
                    Organization:
                </div>
                <div class="value">
                	 <input type="hidden" id="healthcareSite-hidden"
					name="healthcareSite" value="${command.healthcareSite.id}" /> <input
					id="healthcareSite-input" size="60" type="text" name="xyz"
					value="${command.healthcareSite.name}"
					class="autocomplete validate-notEmpty" /> 
					<input type="button" id="healthcareSite-clear"
                        value="Clear"/>
                    <tags:hoverHint keyProp="researchStaff.organization"/>
					<tags:indicator	id="healthcareSite-indicator" />
				<div id="healthcareSite-choices" class="autocomplete"></div>
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