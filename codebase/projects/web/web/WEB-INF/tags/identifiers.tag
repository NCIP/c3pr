<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@attribute name="identifiersTypes" required="true" type="java.util.Collection"%>
<%@attribute name="displayOrgs"%>
<%@attribute name="displaySys"%>
<tags:dwrJavascriptLink objects="ParticipantAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
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
    add_row_division_id: "mytable", 	        /* this id belongs to element where the row would be appended to */
    skeleton_row_division_id: "dummy-row",
    initialIndex: ${fn:length(command.systemAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
    path: "systemAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
};
var organizationIdentifierRowInserterProps = {
    add_row_division_id: "mytable-organizationIdentifier", 	        /* this id belongs to element where the row would be appended to */
    skeleton_row_division_id: "dummy-row-organizationIdentifier",
    initialIndex: ${fn:length(command.organizationAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
    path: "organizationAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
    postProcessRowInsertion: function(object){
	    clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
		registerAutoCompleter(clonedRowInserter);
	},
};
rowInserters.push(systemIdentifierRowInserterProps);
rowInserters.push(organizationIdentifierRowInserterProps);
</script>
<c:if test="${empty displaySys || displaySys!='false'}">
<input id="addIdentifier" type="button" value="Add System Identifier"
	onclick="javascript:RowManager.addRow(systemIdentifierRowInserterProps);"  />
<br> <br>
		
<chrome:division title="System Identifiers">
<table id="mytable" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<th class="scope=" col" align="left"><b><span
			class="red">*</span>System Name</b></th>
		<th scope="col" align="left"><b><span class="red">*</span>Identifier Type</b></th>
		<th scope="col" align="left"><b><span class="red">*</span>Identifier</b></th>
		<th scope="col" align="left"><b>Primary&nbsp;Indicator</b></th>
		<th class="specalt" scope="col" align="left"></th>
	</tr>
	<c:forEach items="${command.systemAssignedIdentifiers}" varStatus="status">
		 <tr id="mytable-${status.index}">
			<td class="alt"><form:input
				path="systemAssignedIdentifiers[${status.index}].systemName"
				cssClass="validate-notEmpty"/>
				</td>
			<td class="alt"><form:select
				path="systemAssignedIdentifiers[${status.index}].type"
				cssClass="validate-notEmpty">
				<option value="">--Please Select--</option>
				<form:options items="${identifiersTypes}" itemLabel="desc"
					itemValue="desc" />
			</form:select></td>
			<td class="alt"><form:input
				path="systemAssignedIdentifiers[${status.index}].value"
				cssClass="validate-notEmpty" /></td>
			<td class="alt"><form:radiobutton
				path="systemAssignedIdentifiers[${status.index}].primaryIndicator" value="true" /></td>
			<td class="alt"><a href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,${status.index});"><img
                              src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		</tr>
	</c:forEach>
</table>
</chrome:division>

<br> <br>
</c:if>
<c:if test="${empty displayOrgs || displayOrgs!='false'}">
<input id="addIdentifier" type="button" value="Add Organization Identifier"
	onclick="javascript:RowManager.addRow(organizationIdentifierRowInserterProps);"  />
<br> <br>
<chrome:division title="Organization Identifiers">
<table id="mytable-organizationIdentifier" border="0" cellspacing="0" cellpadding="0" class="mytable">
	<tr>
		<th class="scope=" col" align="left"><b><span
			class="red">*</span>Assigning Authority</b></th>
		<th scope="col" align="left"><b><span class="red">*</span>Identifier Type</b></th>
		<th scope="col" align="left"><b><span class="red">*</span>Identifier</b></th>
		<th scope="col" align="left"><b>Primary&nbsp;Indicator</b></th>
		<th class="specalt" scope="col" align="left"></th>
	</tr>
	<c:forEach items="${command.organizationAssignedIdentifiers}" varStatus="organizationStatus">
		 <tr id="mytable-organizationIdentifier-${organizationStatus.index}">
			<td class="alt">
 							<input type="hidden" id="healthcareSite${organizationStatus.index}-hidden"
          				name="organizationAssignedIdentifiers[${organizationStatus.index}].healthcareSite"
        					 value="${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.id}"/>
  						<input class="validate-notEmpty" type="text" id="healthcareSite${organizationStatus.index}-input"
         					size="50"
        				 	value="${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.name}"/>
  						<input type="button" id="healthcareSite${organizationStatus.index}-clear"
         				 	value="Clear"/>
    		 				<tags:indicator id="healthcareSite${organizationStatus.index}-indicator"/>
    						<div id="healthcareSite${organizationStatus.index}-choices" class="autocomplete"></div>
         			 </td>
			<td class="alt"><form:select
				path="organizationAssignedIdentifiers[${organizationStatus.index}].type"
				cssClass="validate-notEmpty">
				<option value="">--Please Select--</option>
				<form:options items="${identifiersTypes}" itemLabel="desc"
					itemValue="desc" />
			</form:select></td>
			<td class="alt"><form:input
				path="organizationAssignedIdentifiers[${organizationStatus.index}].value"
				cssClass="validate-notEmpty" /></td>
			<td class="alt"><form:radiobutton
				path="organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator" value="true" /></td>
			<td class="alt"><a href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,${organizationStatus.index});"><img
                              src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		</tr>
	</c:forEach>
</table>
</chrome:division>
</c:if>