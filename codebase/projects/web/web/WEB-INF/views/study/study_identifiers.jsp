<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>

<html>
<head>
<tags:includeScriptaculous />
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
    initialIndex: ${fn:length(command.systemAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
    softDelete: ${flowType!='CREATE_STUDY'},
    path: "systemAssignedIdentifiers"                               /* this is the path of the collection that holds the rows  */
};
var organizationIdentifierRowInserterProps = {
       add_row_division_id: "organizationIdentifier", 	        /* this id belongs to element where the row would be appended to */
       skeleton_row_division_id: "dummy-row-organizationIdentifier",
       initialIndex: ${fn:length(command.organizationAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
       path: "organizationAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
       softDelete: ${flowType!='CREATE_STUDY'},
       postProcessRowInsertion: function(object){
	       clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
		   clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
		   AutocompleterManager.registerAutoCompleter(clonedRowInserter);
	   },
       onLoadRowInitialize: function(object, currentRowIndex){
			clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
			clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
			AutocompleterManager.registerAutoCompleter(clonedRowInserter);
       }
};
RowManager.addRowInseter(systemIdentifierRowInserterProps);
RowManager.addRowInseter(organizationIdentifierRowInserterProps);
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
					<th><span class="required-indicator">Assigning Authority</span></th>
					<th><span class="required-indicator">Identifier Type</span></th>
					<th><span class="required-indicator">Identifier</span></th>
					<th>Primary&nbsp;Indicator</th>
					<th></th>
				</tr>
				<c:forEach var="orgIdentifier" items="${command.organizationAssignedIdentifiers}"
					begin="0" varStatus="organizationStatus">
					<c:choose>
					<c:when test="${(orgIdentifier.type eq 'Protocol Authority Identifier') || (orgIdentifier.type eq 'Coordinating Center Identifier')}">
					<tr>
						<td>${orgIdentifier.healthcareSite.name}</td>
						<td>${orgIdentifier.type}</td>
						<td>${orgIdentifier.value}</td>
						<td>${orgIdentifier.primaryIndicator}</td>
					</tr>
					</c:when>
					<c:otherwise>
					
					<tr id="organizationIdentifier-${organizationStatus.index}">
						<td><input type="hidden"
							id="healthcareSite${organizationStatus.index}-hidden"
							name="organizationAssignedIdentifiers[${organizationStatus.index}].healthcareSite"
							value="${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.id}" />
						<input class="validate-notEmpty" type="text"
							id="healthcareSite${organizationStatus.index}-input" size="50"
							value="${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.name}" />
						<input type="button"
							id="healthcareSite${organizationStatus.index}-clear"
							value="Clear" /> <tags:indicator
							id="healthcareSite${organizationStatus.index}-indicator" />
						<div id="healthcareSite${organizationStatus.index}-choices"
							class="autocomplete"></div>
						</td>
						<td><form:select
							path="organizationAssignedIdentifiers[${organizationStatus.index}].type"
							cssClass="validate-notEmpty">
							<option value="">--Please Select--</option>
							<form:options items="${identifiersTypeRefData}" itemLabel="desc"
								itemValue="desc" />
						</form:select></td>
						<td><form:input
							path="organizationAssignedIdentifiers[${organizationStatus.index}].value"
							onfocus="clearField(this)" cssClass="validate-notEmpty" /></td>
						<td><form:radiobutton
							path="organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator"
							value="true" /></td>
						<td><a
							href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,${organizationStatus.index});"><img
							src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
					</tr>
					</c:otherwise>
					</c:choose>
					
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
					<th><span class="required-indicator">System Name</span></th>
					<th><span class="required-indicator">Identifier Type</span></th>
					<th><span class="required-indicator">Identifier</span></th>
					<th>Primary&nbsp;Indicator</th>
					<th></th>
				</tr>
				<c:forEach items="${command.systemAssignedIdentifiers}"
					varStatus="status">
					<tr id="systemIdentifier-${status.index}">
						<td><form:input
							path="systemAssignedIdentifiers[${status.index}].systemName"
							cssClass="validate-notEmpty" /></td>
						<td><form:select
							path="systemAssignedIdentifiers[${status.index}].type"
							cssClass="validate-notEmpty">
							<option value="">--Please Select--</option>
							<form:options items="${identifiersTypeRefData}" itemLabel="desc"
								itemValue="desc" />
						</form:select></td>
						<td><form:input
							path="systemAssignedIdentifiers[${status.index}].value"
							onfocus="clearField(this)" cssClass="validate-notEmpty" /></td>
						<td><form:radiobutton
							path="systemAssignedIdentifiers[${status.index}].primaryIndicator"
							value="true" /></td>
						<td><a
							href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,${status.index});"><img
							src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
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
			name="systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName" type="text" 
			class="validate-notEmpty" /></td>
		<td><select id="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="validate-notEmpty">
			<option value="">--Please Select--</option>
			<c:forEach items="${identifiersTypeRefData}" var="id">
				<option value="${id.desc}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td><input id="systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			onfocus="javascript:clearField(this)" type="text"
			class="validate-notEmpty" /></td>
		<td><input type="radio"
			id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"
			name="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"
			value="true" /></td>
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
			name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite" />
		<input class="validate-notEmpty" type="text"
			id="healthcareSitePAGE.ROW.INDEX-input" size="50"
			value="${command.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite.name}" />
		<input type="button" id="healthcareSitePAGE.ROW.INDEX-clear"
			value="Clear" /> <tags:indicator
			id="healthcareSitePAGE.ROW.INDEX-indicator" />
		<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete">
		</td>
		<td><select id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="validate-notEmpty">
			<option value="">--Please Select--</option>
			<c:forEach items="${identifiersTypeRefData}" var="id">
				<option value="${id.desc}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td><input id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			onfocus="javascript:clearField(this)" type="text"
			class="validate-notEmpty" /></td>
		<td><input type="radio"
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"
			name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"
			value="true" /></td>
		<td><a
			href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,PAGE.ROW.INDEX);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

</body>
</html>