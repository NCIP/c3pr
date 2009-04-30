<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>

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
    	if(obj.externalId != null){
    		image = '&nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>';
    	} else {
    		image = '';
    	}

    	return (obj.name+" ("+obj.nciInstituteCode+")" + image)
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
    add_row_division_id: "organizationIdentifier", 	        /* this id belongs to element where the row would be appended to */
    skeleton_row_division_id: "dummy-row-systemIdentifier",
    initialIndex: ${fn:length(command.study.systemAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
    softDelete: ${softDelete == 'true'},
    isAdmin: ${isAdmin == 'true'},
    path: "study.systemAssignedIdentifiers"                               /* this is the path of the collection that holds the rows  */
};
var organizationIdentifierRowInserterProps = {
       add_row_division_id: "organizationIdentifier", 	        /* this id belongs to element where the row would be appended to */
       skeleton_row_division_id: "dummy-row-organizationIdentifier",
       initialIndex: ${fn:length(command.study.organizationAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
       path: "study.organizationAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
       softDelete: ${softDelete == 'true'},
       isAdmin: ${isAdmin == 'true'},
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
function manageIdentifierRadio(element){
	$$("form .identifierRadios").each(function(e)
										{
											e.checked=false;
											$(e.id.split("-")[0]).value="false"
										}
									);
	element.checked=true;
	$(element.id.split("-")[0]).value="true";
}
</script>
</head>
<body>

<form:form>
<tags:panelBox>
	<tags:errors path="study.organizationAssignedIdentifiers"/> 	
	<tags:errors path="study.systemAssignedIdentifiers"/> 	
			<table id="organizationIdentifier" class="tablecontent">
				<tr>
					<th><tags:requiredIndicator /><fmt:message key="c3pr.common.assigningAuthority"/><tags:hoverHint keyProp="study.healthcareSite.name"/></th>
					<th><tags:requiredIndicator /><fmt:message key="c3pr.common.identifierType"/><tags:hoverHint keyProp="study.healthcareSite.identifierType"/></th>
					<th><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/><tags:hoverHint keyProp="study.coordinatingcenter.identifier"/></th>
					<th><fmt:message key="c3pr.common.primaryIndicator"/><tags:hoverHint keyProp="study.healthcareSite.primaryIndicator"/></th>
					<th>&nbsp;</th>
				</tr>
				<tr>
					<td>${command.study.organizationAssignedIdentifiers[0].healthcareSite.name}</td>
					<td>${command.study.organizationAssignedIdentifiers[0].type}</td>
					<td>${command.study.organizationAssignedIdentifiers[0].value}</td>
					<td><input type="radio" class="identifierRadios" value="${command.study.organizationAssignedIdentifiers[0].primaryIndicator}" id="organizationAssignedIdentifiers[0].primaryIndicator-radio" onclick="manageIdentifierRadio(this);"/></td>
                    <td>&nbsp;</td>
                </tr>
				<c:if test="${!empty command.study.fundingSponsorAssignedIdentifier}">
				<tr>
					<td>${command.study.fundingSponsorAssignedIdentifier.healthcareSite.name}</td>
					<td>${command.study.fundingSponsorAssignedIdentifier.type}</td>
					<td>${command.study.fundingSponsorAssignedIdentifier.value}</td>
					<td><input type="radio" class="identifierRadios" value="${command.study.organizationAssignedIdentifiers[command.study.fundingSponsorIdentifierIndex].primaryIndicator }" id="organizationAssignedIdentifiers[${command.study.fundingSponsorIdentifierIndex}].primaryIndicator-radio" onclick="manageIdentifierRadio(this);"/></td>
                    <td>&nbsp;</td>
                </tr>
				</c:if>
				<c:forEach var="orgIdentifier" items="${command.study.organizationAssignedIdentifiers}"
					begin="0" varStatus="organizationStatus">
					<c:if test="${(orgIdentifier.type eq 'Protocol Authority Identifier') || (orgIdentifier.type eq 'Coordinating Center Identifier')}">
						<c:set var="handleDifferently" value="true"></c:set>
					</c:if>
					<tr id="organizationIdentifier-${organizationStatus.index}">
						<c:set var="_code" value="" />
							<c:set var="_name" value="" />
							<c:set var="_code" value="(${command.study.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.nciInstituteCode})" />
							<c:set var="_name" value="${command.study.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.name}" />
						<td><form:hidden id="healthcareSite${organizationStatus.index}-hidden"
							path="study.organizationAssignedIdentifiers[${organizationStatus.index}].healthcareSite"
							 />
						<input class="autocomplete validate-notEmpty" type="text"
							id="healthcareSite${organizationStatus.index}-input" size="50"
							value='<c:out value="${_name} ${_code}" />'/>
						<tags:indicator
							id="healthcareSite${organizationStatus.index}-indicator" />
						<div id="healthcareSite${organizationStatus.index}-choices"
							class="autocomplete" style="display: none;"></div>
						</td>
						<td>
						<c:choose>
						<c:when test="${handleDifferently}">
							<form:input path="study.organizationAssignedIdentifiers[${organizationStatus.index}].type"/>
						</c:when>
						<c:otherwise>
							<form:select
								path="study.organizationAssignedIdentifiers[${organizationStatus.index}].type"
								cssClass="validate-notEmpty">
								<option value="">Please Select</option>
								<form:options items="${identifiersTypeRefData}" itemLabel="desc"
									itemValue="desc" />
							</form:select>
						</c:otherwise>
						</c:choose>
						</td>
						<td><form:input
							path="study.organizationAssignedIdentifiers[${organizationStatus.index}].value"
							onfocus="clearField(this)" cssClass="validate-notEmpty" /></td>
						<td><input type="radio"	id="organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator-radio"
							value="${command.study.organizationAssignedIdentifiers[organizationStatus.index].primaryIndicator }" class="${!handleDifferently?'identifierRadios':''}" onclick="manageIdentifierRadio(this);"/>
							<form:hidden path="study.organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator"/></td>
						<td><a
							href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,${organizationStatus.index},'${orgIdentifier.id==null?'HC#':'ID#'}${orgIdentifier.id==null?orgIdentifier.hashCode:orgIdentifier.id}');"><img
							src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
					</tr>
                    <c:if test="${handleDifferently}">
						<script>new Element.hide("organizationIdentifier-${organizationStatus.index}");</script>
					</c:if>
					<c:set var="handleDifferently" value="false"></c:set>
				</c:forEach>
				<c:forEach items="${command.study.systemAssignedIdentifiers}" var="sysIdentifier"
					varStatus="status">
					<tr id="systemIdentifier-${status.index}">
						<td><form:input
							path="study.systemAssignedIdentifiers[${status.index}].systemName"
							cssClass="validate-notEmpty" size="50"/></td>
						<td><form:select
							path="study.systemAssignedIdentifiers[${status.index}].type"
							cssClass="validate-notEmpty">
							<option value="">Please Select</option>
							<form:options items="${identifiersTypeRefData}" itemLabel="desc"
								itemValue="desc" />
						</form:select></td>
						<td><form:input
							path="study.systemAssignedIdentifiers[${status.index}].value"
							onfocus="clearField(this)" cssClass="validate-notEmpty" /></td>
						<td><input type="radio"	id="systemAssignedIdentifiers[${status.index}].primaryIndicator-radio"
							value="${command.study.systemAssignedIdentifiers[status.index].primaryIndicator }" class="identifierRadios" onclick="manageIdentifierRadio(this);"/>
							<form:hidden path="study.systemAssignedIdentifiers[${status.index}].primaryIndicator"/></td>
						<td><a
							href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,${status.index},'${sysIdentifier.id==null?'HC#':'ID#'}${sysIdentifier.id==null?sysIdentifier.hashCode:sysIdentifier.id}');"><img
							src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
					</tr>
				</c:forEach>
                <tr></tr>
            </table>

			<br>
			<div align="left">
			<tags:button type="button" color="blue" icon="add" value="Add Organization Assigned Identifier" 
			onclick="javascript:RowManager.addRow(organizationIdentifierRowInserterProps);" size="small"/>
			<tags:button type="button" color="blue" icon="add" value="Add System Assigned Identifier" 
			onclick="RowManager.addRow(systemIdentifierRowInserterProps);" size="small"/>
				</div>
</tags:panelBox>
<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" isFlow="false"/>
</form:form>

	<script>
		$$("form .identifierRadios").each(function(e)
										{
											if(e.value=="true")
												e.checked=true;
										}
									);
	</script>
<div id="dummy-row-systemIdentifier" style="display:none;">
<table>
	<tr>
		<td><input id="systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName"
			name="study.systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName" type="text" size="50"
			class="validate-notEmpty" /></td>
		<td><select id="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="study.systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${identifiersTypeRefData}" var="id">
				<option value="${id.desc}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td><input id="systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="study.systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			onfocus="javascript:clearField(this)" type="text"
			class="validate-notEmpty" /></td>
		<td><input type="radio"	id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-radio"
			value="false" class="identifierRadios" onclick="manageIdentifierRadio(this);"/>
			<input type="hidden" id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" name="study.systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"/></td>
		<td><a
			href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,PAGE.ROW.INDEX, -1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

<div id="dummy-row-organizationIdentifier" style="display:none;">
<table>
	<tr>
		<td><input type="hidden" id="healthcareSitePAGE.ROW.INDEX-hidden"
			name="study.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite" />
		<input class="autocomplete validate-notEmpty" type="text"
			id="healthcareSitePAGE.ROW.INDEX-input" size="50"
			value="${command.study.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite.name}" />
		<tags:indicator
			id="healthcareSitePAGE.ROW.INDEX-indicator" />
		<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete" style="display: none;"/>
		</td>
		<td><select id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="study.organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${identifiersTypeRefData}" var="id">
				<option value="${id.desc}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td><input id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="study.organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			onfocus="javascript:clearField(this)" type="text"
			class="validate-notEmpty" /></td>
		<td><input type="radio"	id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-radio"
			value="false" class="identifierRadios" onclick="manageIdentifierRadio(this);"/>
			<input type="hidden" name="study.organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"/></td>
		<td><a
			href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,PAGE.ROW.INDEX,-1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

</body>
</html>