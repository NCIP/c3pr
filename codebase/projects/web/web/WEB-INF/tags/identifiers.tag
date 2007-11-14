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

<chrome:division title="Medical Record Number">
	<table width="47%" border="0" cellspacing="1" cellpadding="1" id="table1">
		<tr>
			<td align="right"><span class="required-indicator"><b>Organization:</b></span></td>
			<td align="left"><input type="hidden" id="mrnOrganization-hidden"
					name="organizationAssignedIdentifiers[0].healthcareSite" />
				<input id="mrnOrganization-input" size="50" type="text"
				name="organizationAssignedIdentifiers[0].healthcareSite.name" class="autocomplete validate-notEmpty" />
				<tags:indicator id="mrnOrganization-indicator" />
				<div id="mrnOrganization-choices" class="autocomplete"></div>
			</td>
		</tr>
		<tr>
			<td align="right"><span class="required-indicator"><b>Identifier:</b></span></td>
			<td align="left"><input type="text" name="organizationAssignedIdentifiers[0].value" 
				size="30" maxlength="30" class="validate-notEmpty" />
			     <input type="hidden" name="organizationAssignedIdentifiers[0].type"
				value="MRN"/>
				<input type="hidden" name="organizationAssignedIdentifiers[0].primaryIndicator" value="true"/>
			</td>
		</tr>
	</table>  		
</chrome:division>


<c:if test="${empty displayOrgs || displayOrgs!='false'}">
	
<br>
<chrome:division title="<a href='#' onclick='toggleIdSection()'>Organization Assigned Identifiers</a>">
<div id="idSection" style="display:none;">
<table id="mytable-organizationIdentifier" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
	<tr>
		<th scope="col" align="left"><b><span class="red">*</span>Assigning Authority</b></th>
		<th scope="col" align="left"><b><span class="red">*</span>Identifier Type</b></th>
		<th scope="col" align="left"><b><span class="red">*</span>Identifier</b></th>
		<th scope="col" align="left"><b>Primary Indicator</b></th>
		<th class="specalt" scope="col" align="left"></th>
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