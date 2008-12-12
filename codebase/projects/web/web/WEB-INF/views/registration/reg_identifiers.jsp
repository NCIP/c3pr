<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
    
<%--<tags:includeScriptaculous />--%>
<tags:dwrJavascriptLink objects="StudyAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
var healthcareSiteAutocompleterProps = {
    basename: "healthcareSite",
    populator: function(autocompleter, text) {

        StudyAjaxFacade.matchHealthcareSites( text,function(values) {
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

function clearField(field) {
    field.value = "";
}

              
var systemIdentifierRowInserterProps = {
    add_row_division_id: "systemIdentifier", 	        /* this id belongs to element where the row would be appended to */
    skeleton_row_division_id: "dummy-row-systemIdentifier",
    initialIndex: ${fn:length(command.studySubject.systemAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
    softDelete: true,
    path: "systemAssignedIdentifiers"                               /* this is the path of the collection that holds the rows  */
};
var organizationIdentifierRowInserterProps = {
       add_row_division_id: "organizationIdentifier", 	        /* this id belongs to element where the row would be appended to */
       skeleton_row_division_id: "dummy-row-organizationIdentifier",
       initialIndex: ${fn:length(command.studySubject.organizationAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
       path: "organizationAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
       softDelete: ${flowType!='CREATE_STUDY'},
       postProcessRowInsertion: function(object){
	       clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
		   clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
		   AutocompleterManager.registerAutoCompleter(clonedRowInserter);
	   },
       onLoadRowInitialize: function(object, currentRowIndex){
       		if(currentRowIndex>object.initialIndex){
				clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
				clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
				AutocompleterManager.registerAutoCompleter(clonedRowInserter);
			}
       }
};
RowManager.addRowInseter(systemIdentifierRowInserterProps);
RowManager.addRowInseter(organizationIdentifierRowInserterProps);
function manageIdentifierRadio(element){
	$$(".identifierRadios").each(function(e){e.checked=false;e.value="false"});
	element.checked=true;
	element.value="true";
}
</script>
</head>
<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}"
	formName="studyIdentifiersForm">
	<jsp:attribute name="singleFields">
		
	<br>
	<table width="100%"><tr><td>
		<chrome:division title="Organization Assigned Identifiers">
		
			<table id="organizationIdentifier" class="tablecontent">
				<tr>
					<th><tags:requiredIndicator />Assigning Organization</th>
					<th><tags:requiredIndicator />Identifier Type</th>
					<th><tags:requiredIndicator />Identifier</th>
					<th>Primary&nbsp;Indicator</th>
					<th></th>
				</tr>
				<c:forEach var="orgIdentifier" items="${command.studySubject.organizationAssignedIdentifiers}"
					begin="0" varStatus="organizationStatus">
					<tr id="organizationIdentifier-${organizationStatus.index}">
						<td>${orgIdentifier.healthcareSite.name}</td>
						<td>${orgIdentifier.type}</td>
						<td>${orgIdentifier.value}</td>
						<td>${orgIdentifier.primaryIndicator}<form:radiobutton value="true" cssClass="identifierRadios" path="command.studySubject.organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator"/></td>
					</tr>
				</c:forEach>
			</table>

			<br>
			<div align="right">

			<input id="addOrganizationIdentifier" type="button"
				value="Add Another Identifier"
				onclick="javascript:RowManager.addRow(organizationIdentifierRowInserterProps);" />
				</div>

		</chrome:division>
		<chrome:division title="System Assigned Identifiers">
			<table id="systemIdentifier" class="tablecontent">
				<tr>
					<th><tags:requiredIndicator />System Name</th>
					<th><tags:requiredIndicator />Identifier Type</th>
					<th><tags:requiredIndicator />Identifier</th>
					<th>Primary&nbsp;Indicator</th>
					<th></th>
				</tr>
				<c:forEach items="${command.studySubject.systemAssignedIdentifiers}"	varStatus="status" var="sysIdentifier">
					<tr id="systemIdentifier-${status.index}">
						<td>${sysIdentifier.systemName}</td>
						<td>${sysIdentifier.type}</td>
						<td>${orgIsysIdentifierdentifier.value}</td>
						<td>${sysIdentifier.primaryIndicator}<form:radiobutton value="true" cssClass="identifierRadios" path="command.studySubject.systemAssignedIdentifiers[${status.index}].primaryIndicator"/></td>
					</tr>
				</c:forEach>
			</table>

			<br>
<div align="right">
			<input id="addSystemIdentifier" type="button"
				value="Add Another Identifier"
				onclick="RowManager.addRow(systemIdentifierRowInserterProps);" />
				</div>

		</chrome:division>
</td></tr></table>
	</jsp:attribute>

</tags:tabForm>

<div id="dummy-row-systemIdentifier" style="display:none;">
<table>
	<tr>
		<td><input id="systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName"
			name="studySubject.systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName" type="text" 
			class="validate-notEmpty" /></td>
		<td><select id="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="studySubject.systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="validate-notEmpty">
			<option value="">--Please Select--</option>
			<c:forEach items="${identifiersTypeRefData}" var="id">
				<option value="${id.desc}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td><input id="systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="studySubject.systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			onfocus="javascript:clearField(this)" type="text"
			class="validate-notEmpty" /></td>
		<td><input type="radio"
			id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" class="identifierRadios"
			name="studySubject.systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" onclick="manageIdentifierRadio(this);"/></td>
		<td><a
			href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,PAGE.ROW.INDEX);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

<div id="dummy-row-organizationIdentifier" style="display:none;">
<table>
	<tr>
		<td><input type="hidden" id="healthcareSitePAGE.ROW.INDEX-hidden"
			name="studySubject.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite" />
		<input class="autocomplete validate-notEmpty" type="text"
			id="healthcareSitePAGE.ROW.INDEX-input" size="50"
			value="${command.studySubject.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite.name}" />
		<input type="button" id="healthcareSitePAGE.ROW.INDEX-clear"
			value="Clear" /> <tags:indicator
			id="healthcareSitePAGE.ROW.INDEX-indicator" />
		<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete">
		</td>
		<td><select id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="studySubject.organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="validate-notEmpty">
			<option value="">--Please Select--</option>
			<c:forEach items="${identifiersTypeRefData}" var="id">
				<option value="${id.desc}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td><input id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="studySubject.organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			onfocus="javascript:clearField(this)" type="text"
			class="validate-notEmpty" /></td>
		<td><input type="radio"
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" class="identifierRadios"
			name="studySubject.organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" onclick="manageIdentifierRadio(this);"/></td>
		<td><a
			href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,PAGE.ROW.INDEX);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

</body>
</html>