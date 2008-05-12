<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>

<html>
<head>
<tags:includeScriptaculous />
<tags:dwrJavascriptLink objects="InvestigatorAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
var healthcareSiteAutocompleterProps = {
    basename: "healthcareSite",
    populator: function(autocompleter, text) {
        InvestigatorAjaxFacade.matchHealthcareSites( text,function(values) {
            autocompleter.setChoices(values)
        })
    },
    valueSelector: function(obj) {
        return (obj.name+" ("+obj.nciInstituteCode+")")
    },
    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=inputElement.id.split("-")[0]+"-hidden";
	    							$(hiddenField).value=selectedChoice.id;
									}
}
var investigatorAutocompleterProps = {
            add_row_division_id: "invesitgatorTable", 	        /* this id belongs to element where the row would be appended to */
            skeleton_row_division_id: "dummy-row",
            initialIndex:  ${fn:length(command.healthcareSiteInvestigators)},                            /* this is the initial count of the rows when the page is loaded  */
            path: "healthcareSiteInvestigators",                               /* this is the path of the collection that holds the rows  */
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
        
 RowManager.addRowInseter(investigatorAutocompleterProps);

function fireAction(action, selected){	
			document.getElementById("command")._finish.name='xyz';		    
			document.getElementById("command")._action.value=action;
			document.getElementById("command")._selected.value=selected;		
			document.getElementById("command").submit();
		
	}
</script>

</head>
<body>
<div class="tabpane">
  <ul id="workflow-tabs" class="tabs autoclear">
    <li class="tab"><div>
        <a href="../admin/searchInvestigator">Search Investigator</a>
    </div></li>
    <li class="tab selected"><div>
        <a href="../admin/createInvestigator">Create Investigator</a>
    </div></li>
    <li class="tab"><div>
        <a href="../admin/createInvestigatorGroups">Investigator Groups</a>
    </div></li>
  </ul>
</div>

<div id="main">
<br />
<tags:basicFormPanelBox tab="${tab}" flow="${flow}" title="Investigator"
	action="createInvestigator">
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
	<input type="hidden" name="_finish" value="true">

	
<chrome:division id="site" title="Organization">
	<table class="tablecontent" width="60%" border="0" cellspacing="0" id="invesitgatorTable"
		cellpadding="0">
		<tr>
			<th class="label required-indicator" scope="col" align="left"><b>Organization</b><tags:hoverHint keyProp="healthcareSiteInvestigator.organization"/></th>
			<th class="label required-indicator" scope="col" align="left"><b>Investigator Status</b><tags:hoverHint keyProp="healthcareSiteInvestigator.statusCode"/></th>
			<th></th>
		</tr>
		<c:forEach items="${command.healthcareSiteInvestigators}" var="hcsInv"
			varStatus="status">
			<tr id="invesitgatorTable-${status.index}">
				<td class="alt"><input type="hidden"
					id="healthcareSite${status.index}-hidden"
					name="healthcareSiteInvestigators[${status.index}].healthcareSite"
					value="${command.healthcareSiteInvestigators[status.index].healthcareSite.id}" />
					<input class="autocomplete validate-notEmpty" type="text"
						id="healthcareSite${status.index}-input" size="50"
						value="${command.healthcareSiteInvestigators[status.index].healthcareSite.name}" />
					<tags:indicator
						id="healthcareSite${status.index}-indicator" />
					<div id="healthcareSite${status.index}-choices"
						class="autocomplete"></div>
				</td>
					<td class="alt"><form:select
						path="healthcareSiteInvestigators[${status.index}].statusCode"
						cssClass="validate-notEmpty">
						<option value="">Please Select</option>
						<form:options items="${studySiteStatusRefData}"
							itemLabel="desc" itemValue="code" />
					</form:select></td>
					
				<c:choose>
					<c:when test="${(status.index == 0) || (not empty hcsInv.id)}">
					</c:when>																			
					<c:otherwise>
						<td class="alt"><a
						href="javascript:RowManager.deleteRow(investigatorAutocompleterProps,${status.index},'${hcsInv.id==null?'HC#':'ID#'}${hcsInv.id==null?hcsInv.hashCode:hcsInv.id}');"><img
						src="<tags:imageUrl name="checkno.gif"/>"></a></td>
					</c:otherwise>
				</c:choose>
					
			</tr>
		</c:forEach>
	</table>
	<div align="right"><input id="addOrganization" type="button"
		value="Add Organization"
		onclick="javascript:RowManager.addRow(investigatorAutocompleterProps);" />
	</div>
	
</chrome:division>
<chrome:division id="staff-details" title="Basic Details">
    <div class="leftpanel">
        <div class="row">
            <div class="label required-indicator">
                First Name:</div>
            <div class="value">
                <form:input size="25" path="firstName"
                            cssClass="validate-notEmpty" />
            </div>
        </div>
        <div class="row">
            <div class="label required-indicator">
                Last Name:</div>
            <div class="value">
                <form:input path="lastName" cssClass="validate-notEmpty" size="25" />
            </div>
        </div>
        <div class="row">
            <div class="label">
                Middle Name:</div>
            <div class="value">
                <form:input path="middleName" size="25" />
            </div>
        </div>
        <div class="row">
            <div class="label">
                Maiden Name:</div>
            <div class="value">
                <form:input path="maidenName" size="25" />
            </div>
        </div>
    </div>

    <div class="rightpanel">
        <div class="row">
            <div class="label required-indicator">
                NCI Identifier:
            </div>
            <div class="value">
                <form:input path="nciIdentifier" size="25" cssClass="validate-notEmpty" />
                <tags:hoverHint keyProp="healthcareSiteInvestigator.nciIdentifier"/>
            </div>
        </div>

        <div class="row">
            <div class="label required-indicator">
                    ${command.contactMechanisms[0].type.displayName} (Username):
            </div>
            <div class="value">
                <form:input size="30"
                            path="contactMechanisms[0].value" cssClass="validate-notEmpty&&EMAIL" />
            </div>
        </div>
        <div class="row">
            <div class="label">
                    ${command.contactMechanisms[1].type.displayName}:
            </div>
            <div class="value">
                <form:input size="25"
                            path="contactMechanisms[1].value" cssClass="validate-US_PHONE_NO" /> e.g. 7035600296 or 703-560-0296

            </div>
        </div>
        <div class="row">
            <div class="label">
                    ${command.contactMechanisms[2].type.displayName}:
            </div>
            <div class="value">
                <form:input size="25"
                            path="contactMechanisms[2].value" cssClass="validate-US_PHONE_NO" /> e.g. 7035600296 or 703-560-0296
            </div>
        </div>
    </div>
</chrome:division>
</div>
<br>
			
<div id="dispButton"
		<c:if test="${param.type == 'confirm'}">style="display:none"</c:if>><tags:tabControls />
</div>
</tags:basicFormPanelBox>
<div id="dummy-row" style="display: none;">
<table>
	<tr>
		<td class="alt"><input type="hidden"
					id="healthcareSitePAGE.ROW.INDEX-hidden"
					name="healthcareSiteInvestigators[PAGE.ROW.INDEX].healthcareSite" />
					<input class="autocomplete validate-notEmpty" type="text"
						id="healthcareSitePAGE.ROW.INDEX-input" size="50"
						value="${command.healthcareSiteInvestigators[PAGE.ROW.INDEX].healthcareSite.name}" />
					<tags:indicator
						id="healthcareSitePAGE.ROW.INDEX-indicator" />
					<div id="healthcareSitePAGE.ROW.INDEX-choices"
						class="autocomplete"></div>
				</td>
	
		<td class="alt"><select
			id="healthcareSiteInvestigators[PAGE.ROW.INDEX].statusCode"
			name="healthcareSiteInvestigators[PAGE.ROW.INDEX].statusCode"
			class="validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${studySiteStatusRefData}" var="siteRef">
				<option value="${siteRef.code}">${siteRef.desc }</option>
			</c:forEach>
		</select></td>
		<td class="tdalt"><a
			href="javascript:RowManager.deleteRow(investigatorAutocompleterProps,PAGE.ROW.INDEX,-1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>
</div>
</body>
</html>