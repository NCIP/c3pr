<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
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
    add_row_division_id: "identifier", 	        /* this id belongs to element where the row would be appended to */
    skeleton_row_division_id: "dummy-row-systemIdentifier",
    initialIndex: ${fn:length(command.studySubject.systemAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
    softDelete: false,
    path: "studySubject.systemAssignedIdentifiers"                               /* this is the path of the collection that holds the rows  */
};
var organizationIdentifierRowInserterProps = {
       add_row_division_id: "identifier", 	        /* this id belongs to element where the row would be appended to */
       skeleton_row_division_id: "dummy-row-organizationIdentifier",
       initialIndex: ${fn:length(command.studySubject.organizationAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
       path: "studySubject.organizationAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
       softDelete: false,
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
	$$("form .identifierRadios").each(function(e)
										{
											e.checked=false;
											var indicatorValue = $(e.id+"-hidden").value;
											$(e.id+"-hidden").value="false"
										}
									);
	element.checked=true;
	$(element.id+"-hidden").value="true";
}
</script>
</head>
<body>

<tags:tabForm tab="${tab}" flow="${flow}" willSave="true"
	formName="studySubjectIdentifiersForm">
	<jsp:attribute name="singleFields">
	<br>
	<table width="100%"><tr><td>
		
			<table id="identifier" class="tablecontent">
				<tr>
					<th width="10%"><fmt:message key="c3pr.common.class"/><tags:hoverHint keyProp="study.identifier.type"/></th>
					<th width="25%"><tags:requiredIndicator /><fmt:message key="c3pr.common.assigningAuthority"/><tags:hoverHint keyProp="study.healthcareSite.name"/></th>
					<th width="35%"><tags:requiredIndicator /><fmt:message key="c3pr.common.identifierType"/><tags:hoverHint keyProp="study.healthcareSite.identifierType"/></th>
					<th width="25%"><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/><tags:hoverHint keyProp="study.coordinatingcenter.identifier"/></th>
					<th width="5%"><fmt:message key="c3pr.common.primaryIndicator"/><tags:hoverHint keyProp="study.healthcareSite.primaryIndicator"/></th>
				</tr>
				<c:forEach var="orgIdentifier" items="${command.studySubject.organizationAssignedIdentifiers}"
					varStatus="organizationStatus">
					<tr id="organizationIdentifier-${organizationStatus.index}">
						<td><fmt:message key="c3pr.common.organization" /></td>
						<td>${orgIdentifier.healthcareSite.name}</td>
						<td>${orgIdentifier.type.displayName}</td>
						<td>${orgIdentifier.value}</td>
						<td>
							<form:hidden path="studySubject.organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator" id="identifier-org-${organizationStatus.index}-hidden"/>
							<input type="radio" class="identifierRadios" id="identifier-org-${organizationStatus.index}" onclick="manageIdentifierRadio(this);"
							<c:if test="${orgIdentifier.primaryIndicator}"> checked </c:if>/>
						</td>
					</tr>
				</c:forEach>

				<c:forEach items="${command.studySubject.systemAssignedIdentifiers}"	varStatus="status" var="sysIdentifier">
					<tr id="systemIdentifier-${status.index}">
						<td><fmt:message key="c3pr.common.system" /></td>
						<td>${sysIdentifier.systemName}</td>
						<td>${sysIdentifier.type}</td>
						<td>${sysIdentifier.value}</td>
						<td>
							<form:hidden path="studySubject.systemAssignedIdentifiers[${status.index}].primaryIndicator" id="identifier-sys-${status.index}-hidden"/>
							<input type="radio" class="identifierRadios" id="identifier-sys-${status.index}" onclick="manageIdentifierRadio(this);"
							<c:if test="${sysIdentifier.primaryIndicator}"> checked </c:if>/>
						</td>
					</tr>
				</c:forEach>
			</table>

			<br>
		<div align="left">
			<tags:button type="button" color="blue" icon="add" value="Add Organization Assigned Identifier" 
			onclick="javascript:RowManager.addRow(organizationIdentifierRowInserterProps);" size="small"/>
		</div>
		<div align="right">

			<tags:button type="button" color="blue" icon="add" value="Add System Assigned Identifier" 
				onclick="javascript:RowManager.addRow(systemIdentifierRowInserterProps);" size="small"/>
		</div>

</td></tr></table>
	</jsp:attribute>

</tags:tabForm>

<div id="dummy-row-systemIdentifier" style="display:none;">
<table>
	<tr>
		<td>System</td>
		<td><input id="systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName"
			name="studySubject.systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName" type="text" 
			class="required validate-notEmpty" /></td>
		<td><select id="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="studySubject.systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="required validate-notEmpty">
			<option value="">--Please Select--</option>
			<c:forEach items="${orgIdentifiersTypeRefData}" var="id">
				<option value="${id.desc}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td><input id="systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="studySubject.systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			 type="text"
			class="required validate-notEmpty" /></td>
		<td>
			<input type="hidden" name="studySubject.systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-hidden"/>
			<input type="radio" id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" class="identifierRadios"
			name="studySubject.systemAssignedIdentifiers.primaryIndicator-PAGE.ROW.INDEX" onclick="manageIdentifierRadio(this);"/>
		</td>
		<td><a
			href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,PAGE.ROW.INDEX);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

<div id="dummy-row-organizationIdentifier" style="display:none;">
<table>
	<tr>
		<td>Organization</td>
		<td><input type="hidden" id="healthcareSitePAGE.ROW.INDEX-hidden"
			name="studySubject.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite" />
		<input class="autocomplete validate-notEmpty" type="text"
			id="healthcareSitePAGE.ROW.INDEX-input" size="35"
			value="${studySubject.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite.name}" />
		<tags:indicator
			id="healthcareSitePAGE.ROW.INDEX-indicator" />
		<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete"/>
		</td>
		<td><select id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="studySubject.organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="required validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${orgIdentifiersTypeRefData}" var="id">
				<option value="${id.code}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td><input id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="studySubject.organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			 type="text"
			class="required validate-notEmpty" /></td>
		<td>
			<input type="radio"	id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" class="identifierRadios"
			name="studySubject.organizationAssignedIdentifiers.primaryIndicator-PAGE.ROW.INDEX" onclick="manageIdentifierRadio(this);"/>
			<input type="hidden" name="studySubject.organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" 
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-hidden"/>
		</td>
		<td><a
			href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,PAGE.ROW.INDEX);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

</body>
</html>
