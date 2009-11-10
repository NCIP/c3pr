<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<chrome:division title="Primary Identifier">
		<div class="leftpanel">
		<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.organization"/></div>
			<div class="value">
				<input type="hidden" id="mrnOrganization-hidden" name="organizationAssignedIdentifiers[0].healthcareSite" />
				<input id="mrnOrganization-input" size="30" type="text" name="abcxyz"
				 class="autocomplete validate-notEmpty" />
				<tags:indicator id="mrnOrganization-indicator" />
				<div id="mrnOrganization-choices" class="autocomplete" style="display: none;"><tags:hoverHint keyProp="subject.MRN.organization"/></div>
			</div>
		</div>
		<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/></div>
			<div class="value">
				<input type="text" name="organizationAssignedIdentifiers[0].value" size="30" maxlength="30" class="required validate-notEmpty" />
				<tags:hoverHint keyProp="subject.MRN.value"/>
				<input type="hidden" name="organizationAssignedIdentifiers[0].primaryIndicator" value="true"/>
			</div>
		</div>
		</div>
		<div class="rightpanel">
			<div class="row">
				<div class="label"><fmt:message key="c3pr.common.identifierType"/></div>
				<div class="value">
					<select name="organizationAssignedIdentifiers[0].type"  class="required validate-notEmpty">
						<c:forEach var="identifierType" items="${identifiersTypeRefData}">
							<option value="${identifierType.desc}">${identifierType.desc}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
	 		
</chrome:division>


<c:if test="${empty displayOrgs || displayOrgs!='false'}">
	
<br>
<chrome:division title="Organization Assigned Identifiers" minimize="true" divIdToBeMinimized="idSection">
<div id="idSection" style="display:none;">
<table id="mytable-organizationIdentifier" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
	<tr>
		<th><tags:requiredIndicator /><fmt:message key="c3pr.common.assigningAuthority"/><tags:hoverHint keyProp="identifier.organization"/></th>
		<th><tags:requiredIndicator /><fmt:message key="c3pr.common.identifierType"/><tags:hoverHint keyProp="identifier.type"/></th>
		<th><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/><tags:hoverHint keyProp="identifier.value"/></th>
		<th><span><fmt:message key="c3pr.common.primaryIndicator"/></span><tags:hoverHint keyProp="identifier.primary"/></th>
		<th ></th>
	</tr>
</table>
<div style="padding:10px 0;">
<tags:button type="button" color="blue" icon="add" value="Add Identifier" 
onclick="javascript:RowManager.addRow(organizationIdentifierRowInserterProps);" size="small"/>
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
			class="red">*</span><fmt:message key="c3pr.common.systemName"/></b></th>
		<th scope="col" align="left"><b><span class="red">*</span><fmt:message key="c3pr.common.identifierType"/></b></th>
		<th scope="col" align="left"><b><span class="red">*</span><fmt:message key="c3pr.common.identifier"/></b></th>
		<th scope="col" align="left"><b><fmt:message key="c3pr.common.primaryIndicator"/></b></th>
		<th class="specalt" scope="col" align="left"></th>
	</tr>
</table>
	<div style="padding:10px 0;">
		<input id="addIdentifier" type="button" value="Add Identifier"
		onclick="javascript:RowManager.addRow(systemIdentifierRowInserterProps);"  />
	</div>
</chrome:division>
<br> <br>
</c:if>
