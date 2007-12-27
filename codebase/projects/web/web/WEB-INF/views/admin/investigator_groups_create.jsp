<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>

<html>
<c:set var="selected_site" value="0"/>
<c:if test="${not empty selectedSite}">
	<c:set var="selected_site" value="${selectedSite}"/>
</c:if>
<head>
<style type="text/css">
        div.content {
            padding: 5px 15px;
        }
 </style>
<tags:includeScriptaculous />
<tags:dwrJavascriptLink objects="OrganizationAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
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
	    							document.getElementById("_siteChanged").value="true";
	    							document.getElementById("command").submit();
			 }
        }
       
    var principalInvestigatorAutocompleterProps = {
            basename: "investigator",
            populator: function(autocompleter, text) {
                OrganizationAjaxFacade.matchStudyOrganizationInvestigatorsGivenOrganizationId(text,document.getElementById("healthcareSite-hidden").value, function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
                return obj.investigator.fullName
            },
             afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=inputElement.id.split("-")[0]+"-hidden";
	    							$(hiddenField).value=selectedChoice.id;
			}
        }
        
         AutocompleterManager.addAutocompleter(sponsorSiteAutocompleterProps);
         
         
       var instanceRowInserterProps = {
       add_row_division_id: "investigatorsTable", 	        /* this id belongs to element where the row would be appended to */
       skeleton_row_division_id: "dummy-row",
       initialIndex: ${fn:length(command.healthcareSite.investigatorGroups[selected_site].siteInvestigatorGroupAffiliations)},   /* this is the initial count of the rows when the page is loaded  */
       softDelete: ${softDelete == 'true'},
	   path: "healthcareSite.investigatorGroups[${selected_site}].siteInvestigatorGroupAffiliations",                            /* this is the path of the collection that holds the rows  */
       postProcessRowInsertion: function(object){
        clonedRowInserter=Object.clone(principalInvestigatorAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
		AutocompleterManager.registerAutoCompleter(clonedRowInserter);
    },
    onLoadRowInitialize: function(object, currentRowIndex){
		clonedRowInserter=Object.clone(principalInvestigatorAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
		AutocompleterManager.registerAutoCompleter(clonedRowInserter);
    }
};

var instanceGroupRowInserterProps = {
       add_row_division_id: "groupsTable", 	        /* this id belongs to element where the row would be appended to */
       skeleton_row_division_id: "dummy-group",
       initialIndex: ${fn:length(command.healthcareSite.investigatorGroups)},   /* this is the initial count of the rows when the page is loaded  */
       softDelete: ${softDelete == 'true'},
	   path: "healthcareSite.investigatorGroups",                            /* this is the path of the collection that holds the rows  */
};
RowManager.addRowInseter(instanceGroupRowInserterProps);
RowManager.addRowInseter(instanceRowInserterProps);  
         
         function handleShowGroups(){
         	var organizationId = document.getElementById("healthcareSite-hidden").value;
         	<tags:tabMethod method="getInvestigatorGroups"  divElement="'groupsList'" 
						javaScriptParam="'organizationId='+organizationId" params="'showGroups='+showGroups" formName="'tabMethodForm'"/>
         };
         function handleShowSiteAffiliations(){
         	var selected_site = document.getElementById("selected_site").value;
         	<tags:tabMethod method="getSiteAffiliations"  divElement="'investigators'" 
						javaScriptParam="'selected_site='+selected_site" params="'showGroups='+showGroups" formName="'tabMethodForm'"/>
         };
         function handleAddGroup(){
         	document.getElementById("_addGroup").value="true";
			document.getElementById("command").submit();
         };
         function chooseSites() {
		    document.getElementById("_action").value="siteChange";
		    document.getElementById("_selectedSite").value=document.getElementById('site').value;
		    document.getElementById("command").submit();
		};
</script>
</head>
<body>
<div class="tabpane">
<ul id="workflow-tabs" class="tabs autoclear">
	<li class="tab">
	<div><a href="../admin/searchInvestigator">Search Investigator</a></div>
	</li>
	<li class="tab">
	<div><a href="../admin/createInvestigator">Create Investigator</a></div>
	</li>
	<li class="tab selected">
	<div><a href="../admin/createInvestigatorGroups">Investigator Groups</a>
	</div>
	</li>
</ul>
</div>

<div id="main"><br />

<tags:tabForm tab="${tab}" flow="${flow}" title="Investigator Groups"
	formName="investigatorGroups">

	<jsp:attribute name="singleFields">
		<div>
			<input type="hidden" name="_finish" value="true"> 
			<input type="hidden" name="type1" value=""> 
			<input type="hidden" name="_addGroup" id="_addGroup" value="false">
			<input type="hidden" name="_action" id="_action" value="">
		    <input type="hidden" name="_selected" id="_selected" value="">
		    <input type="hidden" name="_selectedSite" id="_selectedSite" value="">
		    <input type="hidden" name="_selectedInvestigator" id="_selectedInvestigator" value="">
		    <input type="hidden" name="_siteChanged" id="_siteChanged" value="false">
		</div>
		<tags:errors path="*" />

<chrome:division id="organization" title="Organization">
		<div class="leftpanel">
			<div class="row">
				<div class="label required-indicator">Organization:</div>
				<div class="value"><input type="hidden" id="healthcareSite-hidden"
					name="healthcareSite" value="${command.healthcareSite.id}" /> <input
					id="healthcareSite-input" size="50" type="text" name="xyz"
					value="${command.healthcareSite.name}"
					class="autocomplete validate-notEmpty" /> <tags:indicator
					id="healthcareSite-indicator" />
				<div id="healthcareSite-choices" class="autocomplete"></div>
				</div>
			</div>
		</div>
</chrome:division>

<div align="right"><input id="viewGroups" type="button"
	value="View Groups" onclick="handleShowGroups()" /></div>

<chrome:division id="groups" title="Groups">

	<div id="groupsList">
		<table border="0" cellspacing="0" cellpadding="0" class="tablecontent" id="groupsTable"
			width="50%">
			<tr>
	            <th width="40%"scope="col" align="left">Name</th>
	            <th width="60%"scope="col" align="left">Description</th>
	        </tr>
			<c:forEach items="${command.healthcareSite.investigatorGroups}"
				var="treatmentEpoch" varStatus="treatmentEpochCount">
					<tr id="groupsTable-${treatmentEpochCount.index}">
						<td align="left"><form:input
							path="healthcareSite.investigatorGroups[${treatmentEpochCount.index}].name"
							size="41" cssClass="validate-notEmpty" /></td>
					</tr>
			</c:forEach>
		</table>
	</div>
		
		<div align="right"><input type="button" onclick="RowManager.addRow(instanceGroupRowInserterProps);" value="Add Group"/></div>
</chrome:division>

<chrome:division title="Site Affiliations">
    <table border="0" id="table1" cellspacing="0">
    <tr>
	    <td width="51%" align="right">
	    </td>
   </tr>
        <tr>
            <td align="right"><span class="required-indicator"><b>Group:</b></span></td>
            <td align="left">
                <select id="site" name="site" onchange="javascript:chooseSites();">
                	<option value="">Please Select</option>
                    <c:forEach items="${command.healthcareSite.investigatorGroups}" var="group" varStatus="status">
                        <c:if test="${selected_site == status.index }">
                            <option selected="true" value=${status.index}>${group.name}</option>
                            <c:set var="selectedStudySite" value="${group.id}"></c:set>
                        </c:if>
                        <c:if test="${selected_site != status.index }">
                            <option value=${status.index}>${group.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>
      <input type="hidden" id="selectedStudySite" value="${selectedStudySite }"/>
      
      <div align="right"><input id="viewAffiliations" type="button"
	value="View Affiliations" onclick="handleShowSiteAffiliations()" /></div>
    <br>
    <hr>
    
    <div id="investigators">
     <table border="0" id="investigatorsTable" cellspacing="0" class="tablecontent">
        <tr>
            <th><span class="required-indicator">Investigator</span></th>
            <th><span class="required-indicator">Start Date</span></th>
            <th></th>
        </tr>

       <c:forEach varStatus="status" var="studyInvestigator" items="${command.healthcareSite.investigatorGroups[selected_site].siteInvestigatorGroupAffiliations}">
            <tr id="investigatorsTable-${status.index}">
                <td>
                    <form:hidden id="investigator${status.index}-hidden"
                                 path="healthcareSite.investigatorGroups[${selected_site}].siteInvestigatorGroupAffiliations[${status.index}].healthcareSiteInvestigator"/>
                    <input class="autocomplete validate-notEmpty" type="text" id="investigator${status.index}-input" size="30"
                           value="${command.healthcareSite.investigatorGroups[selected_site].siteInvestigatorGroupAffiliations[status.index].healthcareSiteInvestigator.investigator.fullName}"/>
                    <input type="button" id="investigator${status.index}-clear" value="Clear"/>
                    <tags:indicator id="investigator${status.index}-indicator"/>
                    <div id="investigator${status.index}-choices" class="autocomplete"></div>
                </td>
                <td>
                    <form:input path="healthcareSite.investigatorGroups[${selected_site}].siteInvestigatorGroupAffiliations[${status.index}].startDate"
                                 cssClass="validate-notEmpty"/>
                    </td>
                <td>
                    <a href="javascript:RowManager.deleteRow(instanceRowInserterProps,${status.index},${studyInvestigator.hashCode});"><img
                            src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="delete"></a></td>
            </tr>
        </c:forEach>

    </table>
    </div>
    <div align="right">
        <input type="button" onclick="RowManager.addRow(instanceRowInserterProps);" value="Add Investigator"/>
    </div>
			
</chrome:division>

</jsp:attribute>
</tags:tabForm></div>

<div id="dummy-arm" style="display:none">
<table id="arm" class="tablecontent" width="50%">
	<tr>
		<td valign="top"><input type="hidden"
			id="investigatorPAGE.ROW.INDEXNESTED.PAGE.ROW.INDEX-hidden"
			name="healthcareSite.investigatorGroups[PAGE.ROW.INDEX].siteInvestigatorGroupAffiliations[NESTED.PAGE.ROW.INDEX].healthcareSiteInvestigator" />
		<input class="autocomplete validate-notEmpty" type="text"
			id="investigatorPAGE.ROW.INDEXNESTED.PAGE.ROW.INDEX-input" size="50"
			value="${command.healthcareSite.investigatorGroups[PAGE.ROW.INDEX].siteInvestigatorGroupAffiliations[NESTED.PAGE.ROW.INDEX].healthcareSiteInvestigator.investigator.fullName}" />
		<input type="button"
			id="investigatorPAGE.ROW.INDEXNESTED.PAGE.ROW.INDEX-clear"
			value="Clear" /> <tags:indicator
			id="investigatorPAGE.ROW.INDEXNESTED.PAGE.ROW.INDEX-indicator" />
		<div id="investigatorPAGE.ROW.INDEXNESTED.PAGE.ROW.INDEX-choices"
			class="autocomplete">
		</td>
	</tr>
</table>
</div>

 <div id="dummy-row" style="display:none;">
    <table width="50%" class="tablecontent">
        <tr  id="investigatorsTable-PAGE.ROW.INDEX">
            <td>
                <input type="hidden" id="investigatorPAGE.ROW.INDEX-hidden"
                        name="healthcareSite.investigatorGroups[${selected_site}].siteInvestigatorGroupAffiliations[PAGE.ROW.INDEX].healthcareSiteInvestigator"
                       value="healthcareSite.investigatorGroups[${selected_site}].siteInvestigatorGroupAffiliations[PAGE.ROW.INDEX].healthcareSiteInvestigator"/>
                <input class="autocomplete validate-notEmpty" type="text" id="investigatorPAGE.ROW.INDEX-input"
                       size="30"
                       value="${command.healthcareSite.investigatorGroups[selected_site].siteInvestigatorGroupAffiliations[PAGE.ROW.INDEX].healthcareSiteInvestigator.investigator.fullName}"/>
                <input type="button" id="investigatorPAGE.ROW.INDEX-clear"
                        value="Clear"/>
                   <tags:indicator id="investigatorPAGE.ROW.INDEX-indicator"/>
                  <div id="investigatorPAGE.ROW.INDEX-choices" class="autocomplete"></div>
            </td>
            <td>
                <input type="text" id="healthcareSite.investigatorGroups[${selected_site}].siteInvestigatorGroupAffiliations[PAGE.ROW.INDEX].startDate"
                        name="healthcareSite.investigatorGroups[${selected_site}].siteInvestigatorGroupAffiliations[PAGE.ROW.INDEX].startDate"
                        class="validate-notEmpty">
            </td>
            <td>
                <a href="javascript:RowManager.deleteRow(instanceRowInserterProps,PAGE.ROW.INDEX, -1);"><img
                        src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="delete"></a></td> 
        </tr>
    </table>
</div> 

<div id="dummy-group" style="display:none">
<table class="tablecontent" width="100%">
        <tr  id="groupsTable-PAGE.ROW.INDEX">
            <td width="20%">
                <input type="text" valign="top" id="healthcareSite.investigatorGroups[PAGE.ROW.INDEX].name" size="42"
                        name="healthcareSite.investigatorGroups[PAGE.ROW.INDEX].name"
                        class="validate-notEmpty">
            </td>
            <td>
                <textarea id="healthcareSite.investigatorGroups[PAGE.ROW.INDEX].descriptionText"
                        name="healthcareSite.investigatorGroups[PAGE.ROW.INDEX].descriptionText" rows="5" cols="40"></textarea>
            </td>
            <td>
                <a href="javascript:RowManager.deleteRow(instanceGroupRowInserterProps,PAGE.ROW.INDEX, -1);"><img
                        src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="delete"></a></td>
        </tr>
    </table>
</div>
</body>
</html>

