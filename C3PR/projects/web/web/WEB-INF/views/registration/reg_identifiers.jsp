<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><registrationTags:htmlTitle registration="${studySubject}" /></title>
    
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
    path: "studySubject.systemAssignedIdentifiers"                               /* this is the path of the collection that holds the rows  */
};
var organizationIdentifierRowInserterProps = {
       add_row_division_id: "organizationIdentifier", 	        /* this id belongs to element where the row would be appended to */
       skeleton_row_division_id: "dummy-row-organizationIdentifier",
       initialIndex: ${fn:length(command.studySubject.organizationAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
       path: "studySubject.organizationAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
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
					<th><tags:requiredIndicator /><fmt:message key="c3pr.common.assigningAuthority"/><tags:hoverHint keyProp="study.healthcareSite.name"/></th>
					<th><tags:requiredIndicator /><fmt:message key="c3pr.common.identifierType"/><tags:hoverHint keyProp="study.healthcareSite.identifierType"/></th>
					<th><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/><tags:hoverHint keyProp="study.coordinatingcenter.identifier"/></th>
					<th><fmt:message key="c3pr.common.primaryIndicator"/><tags:hoverHint keyProp="study.healthcareSite.primaryIndicator"/></th>
					<th></th>
				</tr>
				<c:forEach var="orgIdentifier" items="${command.studySubject.organizationAssignedIdentifiers}"
					begin="0" varStatus="organizationStatus">
					<tr id="organizationIdentifier-${organizationStatus.index}">
						<td>${orgIdentifier.healthcareSite.name}</td>
						<td>${orgIdentifier.type.displayName}</td>
						<td>${orgIdentifier.value}</td>
						<td>${orgIdentifier.primaryIndicator?"yes":"no"}</td>
						<td><a
							href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,${organizationStatus.index},'${orgIdentifier.id==null?'HC#':'ID#'}${orgIdentifier.id==null?orgIdentifier.hashCode:orgIdentifier.id}');"><img
							src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
					</tr>
				</c:forEach>
			</table>

			<br>
			<div align="right">
				<tags:button type="button" color="blue" icon="add" value="Add Identifier" 
				onclick="javascript:RowManager.addRow(organizationIdentifierRowInserterProps);" size="small"/>
			</div>

		</chrome:division>
		<chrome:division title="System Assigned Identifiers">
			<table id="systemIdentifier" class="tablecontent">
				<tr>
					<th><tags:requiredIndicator /><fmt:message key="c3pr.common.systemName"/><tags:hoverHint keyProp="study.systemAssignedIdentifier.systemName"/></th>
					<th><tags:requiredIndicator /><fmt:message key="c3pr.common.identifierType"/><tags:hoverHint keyProp="study.systemAssignedIdentifier.identifierType"/></th>
					<th><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/><tags:hoverHint id="study.systemAssignedIdentifier.identifier" keyProp="study.coordinatingcenter.identifier"/></th>
					<th><fmt:message key="c3pr.common.primaryIndicator"/><tags:hoverHint keyProp="study.systemAssignedIdentifier.primaryIndicator"/></th>
					<th></th>
				</tr>
				<c:forEach items="${command.studySubject.systemAssignedIdentifiers}"	varStatus="status" var="sysIdentifier">
					<tr id="systemIdentifier-${status.index}">
						<td>${sysIdentifier.systemName}</td>
						<td>${sysIdentifier.type}</td>
						<td>${sysIdentifier.value}</td>
						<td>${sysIdentifier.primaryIndicator?"yes":"no"}</td>
						<td><a
							href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,${status.index},'${sysIdentifier.id==null?'HC#':'ID#'}${sysIdentifier.id==null?sysIdentifier.hashCode:sysIdentifier.id}');"><img
							src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
					</tr>
				</c:forEach>
			</table>

			<br>
		<div align="right">

			<tags:button type="button" color="blue" icon="add" value="Add Identifier" 
				onclick="javascript:RowManager.addRow(systemIdentifierRowInserterProps);" size="small"/>
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
			<c:forEach items="${registrationIdentifiersType}" var="id">
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
			value="${studySubject.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite.name}" />
		<tags:indicator
			id="healthcareSitePAGE.ROW.INDEX-indicator" />
		<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete">
		</td>
		<td><select id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="studySubject.organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${orgIdentifiersTypeRefData}" var="id">
				<option value="${id.code}">${id.desc}</option>
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