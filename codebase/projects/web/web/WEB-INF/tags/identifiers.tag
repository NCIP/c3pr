<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@attribute name="identifiersTypes" required="true" type="java.util.Collection"%>
<%@attribute name="displayOrgs"%>
<%@attribute name="displaySys"%>
<tags:dwrJavascriptLink objects="ParticipantAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
	function toggleIdSection(){
		var el = document.getElementById('idSection');
		if ( el.style.display != 'none' ) {
			new Effect.BlindUp(el);
		}
		else {
			new Effect.BlindDown(el);
		}
  	}
  	
var healthcareSiteAutocompleterProps = {
    basename: "healthcareSite",
    populator: function(autocompleter, text) {

        ParticipantAjaxFacade.matchHealthcareSites( text,function(values) {
            autocompleter.setChoices(values)
        })
    },
    valueSelector: function(obj) {
        return obj.name
    },
    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=inputElement.id.split("-")[0]+"-hidden";
	    							$(hiddenField).value=selectedChoice.id;
									}
}
var systemIdentifierRowInserterProps = {
    add_row_division_id: "mytable-system", 	        /* this id belongs to element where the row would be appended to */
    skeleton_row_division_id: "dummy-row",
    initialIndex: 0,                            /* this is the initial count of the rows when the page is loaded  */
    path: "systemAssignedIdentifiers"                             /* this is the path of the collection that holds the rows  */
};
var organizationIdentifierRowInserterProps = {
    add_row_division_id: "mytable-organizationIdentifier", 	        /* this id belongs to element where the row would be appended to */
    skeleton_row_division_id: "dummy-row-organizationIdentifier",
   initialIndex:1,                            /* this is the initial count of the rows when the page is loaded  */
    path: "organizationAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
    postProcessRowInsertion: function(object){
	    clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
		AutocompleterManager.registerAutoCompleter(clonedRowInserter);
	}
};
RowManager.addRowInseter(systemIdentifierRowInserterProps);
RowManager.addRowInseter(organizationIdentifierRowInserterProps);
</script>

<chrome:division title="Assigned Identifier">
	<div class="row">
			<div class="label"><tags:requiredIndicator />Organization</div>
			<div class="value"><input type="hidden" id="mrnOrganization-hidden"
					name="organizationAssignedIdentifiers[0].healthcareSite" />
				<input id="mrnOrganization-input" size="50" type="text" name="abcxyz"
				 class="autocomplete validate-notEmpty" />
				<tags:hoverHint keyProp="subject.MRN.organization"/>
				<tags:indicator id="mrnOrganization-indicator" />
				<div id="mrnOrganization-choices" class="autocomplete" style="display: none;"></div>
			</div>
		</div>
		<div class="row">
			<div class="label"><tags:requiredIndicator />Medical Record Number</div>
			<div class="value"><input type="text" name="organizationAssignedIdentifiers[0].value" 
				size="30" maxlength="30" class="validate-notEmpty" />
			     <input type="hidden" name="organizationAssignedIdentifiers[0].type"
				value="MRN"/>
				<tags:hoverHint keyProp="subject.MRN.value"/>
				<input type="hidden" name="organizationAssignedIdentifiers[0].primaryIndicator" value="true"/>
			</div>
		</div>
	 		
</chrome:division>


<c:if test="${empty displayOrgs || displayOrgs!='false'}">
	
<br>
<chrome:division title="Organization Assigned Identifiers" minimize="true" divIdToBeMinimized="idSection">
<div id="idSection" style="display:none;">
<table id="mytable-organizationIdentifier" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
	<tr>
		<th><tags:requiredIndicator />Assigning Authority<tags:hoverHint keyProp="identifier.organization"/></th>
		<th><tags:requiredIndicator />Identifier Type<tags:hoverHint keyProp="identifier.type"/></th>
		<th><tags:requiredIndicator />Identifier<tags:hoverHint keyProp="identifier.value"/></th>
		<th><span>Primary Indicator</span><tags:hoverHint keyProp="identifier.primary"/></th>
		<th ></th>
	</tr>
</table>
<div align="right">
<input id="addIdentifier" type="button" value="Add Another Identifier"
	onclick="javascript:RowManager.addRow(organizationIdentifierRowInserterProps);"  />
</div>
</div>
</chrome:division>
</c:if>

<c:if test="${empty displaySys || displaySys!='false'}">

<br>		
<chrome:division title="System Assigned Identifiers">
<table id="mytable-system" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
	<tr>
		<th class="scope=" col" align="left"><b><span
			class="red">*</span>System Name</b></th>
		<th scope="col" align="left"><b><span class="red">*</span>Identifier Type</b></th>
		<th scope="col" align="left"><b><span class="red">*</span>Identifier</b></th>
		<th scope="col" align="left"><b>Primary&nbsp;Indicator</b></th>
		<th class="specalt" scope="col" align="left"></th>
	</tr>
</table>
	<div align="right">
		<input id="addIdentifier" type="button" value="Add Another Identifier"
		onclick="javascript:RowManager.addRow(systemIdentifierRowInserterProps);"  />
	</div>
</chrome:division>
<br> <br>
</c:if>
