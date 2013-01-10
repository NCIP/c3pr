<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title>Manage Personnel</title>
    <tags:dwrJavascriptLink objects="SearchResearchStaffAjaxFacade"/>
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
            	$('healthcareSite-indicator').style.display='';
                OrganizationAjaxFacade.matchHealthcareSites(text,function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
            	if(obj.externalId != null){
            		image = '&nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>';
            	} else {
            		image = '';
            	}

            	return (obj.name+" ("+obj.primaryIdentifier+")" + image)
            },
            afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=sponsorSiteAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
	    							$('healthcareSite-indicator').style.display='none';
			 }
        }
        
        AutocompleterManager.addAutocompleter(sponsorSiteAutocompleterProps);

        function searchStudy() {
            new Ajax.Updater('temp','../personOrUser/searchPersonOrUser?fromRegistration=true&decorator=nullDecorator',
            {
                method:'post',
                postBody:Form.serialize('searchForm'), 
                onSuccess:callbackSearchSuccess,
                onFailure:callbackSearchFailure
            });
        }

        function callbackSearchSuccess(t){
        	var resultsDiv = document.getElementById("resultsDiv");
        	var tableDiv = document.getElementById("tableDiv");
        	
        	resultsDiv.style.display="block";
        	tableDiv.innerHTML = t.responseText;
    		//new Effect.SlideDown(resultsDiv);
        	$('search-indicator').style.display='none';
        }

        function callbackSearchFailure(t){
        	alert("Search Failed");
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
                    if ( idElement == "assignedIdentifier" || idElement == "firstName" || idElement == "lastName" || idElement == "emailAddress" || idElement == "loginName"){
                    	$('search-indicator').style.display=''
                   		searchStudy();
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
<tags:instructions code="research_staff_search" />
    <form name="searchForm" id="searchForm" method="post">
        <div>
            <input type="hidden" name="_selected" id="_selected" value="">
            <input type="hidden" name="_action" id="_action" value="">
        </div>
        
        <chrome:division id="personUserCriteria" title="Basic Criteria" insertHelp="true" insertHelpKeyProp="researchStaff.personUserCriteria">
		    <div class="leftpanel">
		        <div class="row">
					<div class="label">
	                    <fmt:message key="c3pr.common.firstName"/>
	                </div>
	                <div class="value">
	                    <input type="text" name="firstName" id="firstName"/>
	                </div>
				</div>
				<div class="row">
					<div class="label">
	                    <fmt:message key="c3pr.common.lastName"/>
	                </div>
	                <div class="value">
	                    <input type="text" name="lastName" id="lastName"/>
	                </div>
                </div>
			</div>
		
		    <div class="rightpanel" align="left">
		    	<div class="row" align="left">
					<div class="label" align="left">
	                    <fmt:message key="c3pr.common.email" />
	                </div>
	                <div class="value" align="left">
	                    <input type="text" name="emailAddress" id="emailAddress" size="30"/>
	                </div>
				</div>
		    </div>
		</chrome:division>
        
        <chrome:division id="staffCriteria" title="Staff Criteria" insertHelp="true" insertHelpKeyProp="researchStaff.staffCriteria">
	        <div class="row">
	        	<div class="label">
                    <fmt:message key="c3pr.common.organization"/>
                </div>
                <div class="value">
                	<input type="hidden" id="healthcareSite-hidden" name="healthcareSite" value="${command.healthcareSite.id}" /> 
                	<input id="healthcareSite-input" size="50" type="text" name="xyz" value="${command.healthcareSite.name}" class="autocomplete validate-notEmpty" /> 
                    <tags:hoverHint keyProp="researchStaff.organization" />
                    <img id="healthcareSite-indicator" src="<c:url value="/images/indicator.white.gif"/>" alt="activity indicator" style="display:none"/>
					<div id="healthcareSite-choices" class="autocomplete" style="display: none;"></div>
                </div>
			</div>
			<div class="row">
				<div class="label">
                    <fmt:message key="c3pr.person.identifier"/>
                </div>
                <div class="value">
                    <input type="text" name="assignedIdentifier" id="assignedIdentifier"/>
                    <tags:hoverHint keyProp="researchStaff.assignedIdentifier"/>
                </div>
			</div>
		</chrome:division>
     
        <chrome:division id="userCriteria" title="User Criteria" insertHelp="true" insertHelpKeyProp="researchStaff.userCriteria">
	        <div class="row">
	        	<div class="label">
                    <fmt:message key="c3pr.common.username"/>
                </div>
                <div class="value">
                    <input type="text" name="loginName" id="loginName"/>
                </div>
			</div>
		</chrome:division>    
		
		<br/>
		<div align="center">
       		<tags:button type="button" icon="search" size="small" color="blue" value="Search" onclick="$('search-indicator').style.display='';searchStudy('searchForm');"/>
           	<img id="search-indicator" src="<c:url value="/images/indicator.white.gif"/>" alt="activity indicator" style="display:none"/>
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
