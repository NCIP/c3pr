<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Manage Investigator</title>
   <tags:dwrJavascriptLink objects="SearchInvestigatorAjaxFacade"/>
     <tags:dwrJavascriptLink objects="OrganizationAjaxFacade"/>
   <style type="text/css">
        div.content {
            padding: 5px 15px;
        }
   </style>
    <script type="text/javascript">
	 dwr.engine.setErrorHandler(handleDWRError);
	 function handleDWRError(err){
	 }
	 
     var sponsorSiteAutocompleterProps = {
            basename: "healthcareSite",
            populator: function(autocompleter, text) {
            	if(text != '(Begin typing here)'){
            		$('healthcareSite-indicator').style.display='';
            	}
                OrganizationAjaxFacade.matchHealthcareSites(text,function(values) {
                    autocompleter.setChoices(values)
                });
                
            },
            valueSelector: function(obj) {
            	if(obj.externalId != null){
            		image = '&nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>';
            	} else {
            		image = '';
            	}

            	return (obj.name+" ("+obj.ctepCode+")" + image)
            },
            afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=sponsorSiteAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
	    							$('healthcareSite-indicator').style.display='none';
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
            
            SearchInvestigatorAjaxFacade.getTable(parameterMap, params, showTable);
        }

        function showTable(table) {
        	$('resultsDiv').style.display="block";
            document.getElementById('tableDiv').innerHTML=table;
            $('search-indicator').style.display="none";
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
                    if ( idElement == "nciIdentifier" || idElement == "firstName" || idElement == "lastName" ){
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

    <chrome:search title="Search Investigator">
	<tags:instructions code="investigator_search" />
    <form name="searchForm" id="searchForm" method="post">
    	
        <div>
            <input type="hidden" name="_selected" id="_selected" value="">
            <input type="hidden" name="_action" id="_action" value="">
        </div>
        <div class="content">
            <div class="row">
                <div class="label">
                    <fmt:message key="c3pr.common.firstName"/>
                </div>
                <div class="value">
                    <input type="text" name="firstName" id="firstName"/>
                </div>
            </div>
            <div class=row>
                <div class="label">
                    <fmt:message key="c3pr.common.lastName"/>
                </div>
                <div class="value">
                    <input type="text" name="lastName" id="lastName"/>
                </div>
            </div>
            <div class="row">
                <div class="label">
                    <fmt:message key="c3pr.common.NCIIdentifier"/>
                </div>
                <div class="value">
                    <input type="text" name="nciIdentifier" id="nciIdentifier"/>
                    <tags:hoverHint keyProp="healthcareSiteInvestigator.nciIdentifier"/>
                </div>
            </div>
			<div class="row">
                <div class="label">
                    <fmt:message key="c3pr.common.organization"/>
                </div>
                <div class="value">
                	 <input type="hidden" id="healthcareSite-hidden"
					name="healthcareSite" value="${command.healthcareSite.id}" /> <input
					id="healthcareSite-input" size="60" type="text" name="xyz"
					value="${command.healthcareSite.name}"
					class="autocomplete validate-notEmpty" /> 
                    <tags:hoverHint keyProp="healthcareSiteInvestigator.organization"/>
                    <img id="healthcareSite-indicator" src="<c:url value="/images/indicator.white.gif"/>" alt="activity indicator" style="display:none"/>
				<div id="healthcareSite-choices" class="autocomplete" style="display: none;"></div>
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