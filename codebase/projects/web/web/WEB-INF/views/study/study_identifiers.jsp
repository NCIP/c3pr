<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
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

    	return (obj.name+" ("+obj.primaryIdentifier+")" + image)
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
    getColumnDivisionID: function(index){
    							return "systemIdentifier-"+index;
    						},
    path: "study.systemAssignedIdentifiers"                               /* this is the path of the collection that holds the rows  */
};
var organizationIdentifierRowInserterProps = {
       add_row_division_id: "organizationIdentifier", 	        /* this id belongs to element where the row would be appended to */
       skeleton_row_division_id: "dummy-row-organizationIdentifier",
       initialIndex: ${fn:length(command.study.organizationAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
       path: "study.organizationAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
       softDelete: ${softDelete == 'true'},
       postProcessRowInsertion: function(object){
	       clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
		   clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
		   AutocompleterManager.registerAutoCompleter(clonedRowInserter);
	   }
};
RowManager.addRowInseter(systemIdentifierRowInserterProps);
RowManager.addRowInseter(organizationIdentifierRowInserterProps);
function manageIdentifierRadio(element){
	$$("form .identifierRadios").each(function(e)
										{
											e.checked=false;
											$(e.id+"-hidden").value="false"
										}
									);
	element.checked=true;
	$(element.id+"-hidden").value="true";
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
		<th width="10%"><fmt:message key="c3pr.common.class"/><tags:hoverHint keyProp="study.identifier.type"/></th>
		<th width="25%"><tags:requiredIndicator /><fmt:message key="c3pr.common.assigningAuthority"/><tags:hoverHint keyProp="study.healthcareSite.name"/></th>
		<th width="35%"><tags:requiredIndicator /><fmt:message key="c3pr.common.identifierType"/><tags:hoverHint keyProp="study.healthcareSite.identifierType"/></th>
		<th width="20%"><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/><tags:hoverHint keyProp="study.coordinatingcenter.identifier"/></th>
		<th width="5%"><fmt:message key="c3pr.common.primaryIndicator"/><tags:hoverHint keyProp="study.healthcareSite.primaryIndicator"/></th>
		<th></th>
	</tr>
	<c:forEach var="orgIdentifier" items="${command.study.organizationAssignedIdentifiers}" varStatus="organizationStatus">
		<c:choose><c:when test="${orgIdentifier.type eq 'PROTOCOL_AUTHORITY_IDENTIFIER' || orgIdentifier.type eq 'COORDINATING_CENTER_IDENTIFIER'}">
		<tr id="organizationIdentifier-${organizationStatus.index}">
			<td><fmt:message key="c3pr.common.organization" /></td>
			<td>${orgIdentifier.healthcareSite.name} (${orgIdentifier.healthcareSite.primaryIdentifier})</td>
			<td>${orgIdentifier.type.code}</td>
			<td>${orgIdentifier.value}</td>
			<td>
				<form:hidden path="study.organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator" id="identifier-org-${organizationStatus.index}-hidden"/>
				<input type="radio" class="identifierRadios" id="identifier-org-${organizationStatus.index}" onclick="manageIdentifierRadio(this);"/>
			</td>
	         <td>
	         <c:if test="${orgIdentifier.id == null}"><a
				href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,${organizationStatus.index},'${orgIdentifier.id==null?'HC#':'ID#'}${orgIdentifier.id==null?orgIdentifier.hashCode:orgIdentifier.id}');"><img
				src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
				</c:if>
	         </td>
	    </tr>
		</c:when>
		<c:otherwise>
		<tr id="organizationIdentifier-${organizationStatus.index}">
			<td><fmt:message key="c3pr.common.organization" /></td>
			<td> ${orgIdentifier.healthcareSite.name} (${orgIdentifier.healthcareSite.primaryIdentifier}) </td>
			<td>
				<form:select
					path="study.organizationAssignedIdentifiers[${organizationStatus.index}].type"
					cssClass="required validate-notEmpty">
					<option value="">Please Select</option>
					<form:options items="${orgIdentifiersTypeRefData}" itemLabel="desc"
						itemValue="code" />
				</form:select>
			</td>
			<td><form:input
				path="study.organizationAssignedIdentifiers[${organizationStatus.index}].value"
				onfocus="clearField(this)" cssClass="required validate-notEmpty"/></td>
			<td>
				<form:hidden path="study.organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator" id="identifier-org-${organizationStatus.index}-hidden"/>
				<input type="radio" class="identifierRadios" id="identifier-org-${organizationStatus.index}" onclick="manageIdentifierRadio(this);"/>
			</td>
			<td>			
				<c:if test="${!orgIdentifier.primaryIndicator}"><a
				href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,${organizationStatus.index},'${orgIdentifier.id==null?'HC#':'ID#'}${orgIdentifier.id==null?orgIdentifier.hashCode:orgIdentifier.id}');"><img
				src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
				</c:if>
			</td>
		</tr>
		</c:otherwise>	
		</c:choose>
	</c:forEach>
	<c:forEach items="${command.study.systemAssignedIdentifiers}" var="sysIdentifier" varStatus="status">
		<tr id="systemIdentifier-${status.index}">
			<td><fmt:message key="c3pr.common.system" /></td>
			<td>${sysIdentifier.systemName}</td>
			<td><form:select
				path="study.systemAssignedIdentifiers[${status.index}].type"
				cssClass="required validate-notEmpty">
				<option value="">Please Select</option>
				<form:options items="${orgIdentifiersTypeRefData}" itemLabel="desc"
					itemValue="code" />
			</form:select></td>
			<td><form:input
				path="study.systemAssignedIdentifiers[${status.index}].value"
				onfocus="clearField(this)" cssClass="required validate-notEmpty" /></td>
			<td>
				<form:hidden path="study.systemAssignedIdentifiers[${status.index}].primaryIndicator" id="identifier-sys-${status.index}-hidden"/>
				<input type="radio" class="identifierRadios" id="identifier-sys-${status.index}" onclick="manageIdentifierRadio(this);"/>
			</td>
			<td><a
				href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,${status.index},'${sysIdentifier.id==null?'HC#':'ID#'}${sysIdentifier.id==null?sysIdentifier.hashCode:sysIdentifier.id}');"><img
				src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		</tr>
	</c:forEach>
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
									if($(e.id+"-hidden").value=="true")
										e.checked=true;
								}
							);
	</script>
<div id="dummy-row-systemIdentifier" style="display:none;">
<table>
	<tr>
		<td>System</td>
		<td><input id="systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName"
			name="study.systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName" type="text" size="30"
			class="required validate-notEmpty" /></td>
		<td><select id="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="study.systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="required validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${orgIdentifiersTypeRefData}" var="id">
				<option value="${id.code}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td><input id="systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="study.systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			onfocus="javascript:clearField(this)" type="text"
			class="required validate-notEmpty" /></td>
		<td><input type="radio"	id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-radio"
			value="false" class="identifierRadios" onclick="manageIdentifierRadio(this);"/>
			<input type="hidden" id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-radio-hidden" name="study.systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"/></td>
		<td><a
			href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,PAGE.ROW.INDEX, -1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

<div id="dummy-row-organizationIdentifier" style="display:none;">
<table>
	<tr>
		<td>Organization</td>
		<td><input type="hidden" id="healthcareSitePAGE.ROW.INDEX-hidden"
			name="study.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite" />
		<input class="autocomplete validate-notEmpty" type="text"
			id="healthcareSitePAGE.ROW.INDEX-input" size="30"
			value="${command.study.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite.name}" />
		<tags:indicator
			id="healthcareSitePAGE.ROW.INDEX-indicator" />
		<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete" style="display: none;"/>
		</td>
		<td><select id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="study.organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="required validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${orgIdentifiersTypeRefData}" var="id">
				<option value="${id.code}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td><input id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="study.organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			onfocus="javascript:clearField(this)" type="text"
			class="required validate-notEmpty" /></td>
		<td><input type="radio"	id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-radio"
			value="false" class="identifierRadios" onclick="manageIdentifierRadio(this);"/>
			<input type="hidden" name="study.organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-radio-hidden"/></td>
		<td><a
			href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,PAGE.ROW.INDEX,-1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

</body>
</html>
