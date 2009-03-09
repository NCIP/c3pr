<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Investigator: ${command.firstName} ${command.lastName}</title>
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

<div id="main">

<tags:basicFormPanelBox tab="${tab}" flow="${flow}" title="Investigator" >
<tags:instructions code="investigator_details" />
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
	<input type="hidden" name="_finish" value="true">

<chrome:division id="site" title="Organization">
	<table class="tablecontent" width="60%" border="0" cellspacing="0" id="invesitgatorTable" cellpadding="0">
		<tr>
			<th class="label" scope="col" align="left"><tags:requiredIndicator /><b><fmt:message key="c3pr.common.organization"/></b><tags:hoverHint keyProp="healthcareSiteInvestigator.organization"/></th>
			<th class="label" scope="col" align="left"><tags:requiredIndicator /><b><fmt:message key="investigator.investigatorStatus"/></b><tags:hoverHint keyProp="healthcareSiteInvestigator.statusCode"/></th>
			<th>&nbsp;</th>
		</tr>

        <c:forEach items="${command.healthcareSiteInvestigators}" var="hcsInv" varStatus="status">
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
						class="autocomplete" style="display: none;"></div>
				</td>
                <td class="alt">
                    <form:select path="healthcareSiteInvestigators[${status.index}].statusCode" cssClass="validate-notEmpty">
                    <option value="">Please Select</option>
                    <form:options items="${studySiteStatusRefData}" itemLabel="desc" itemValue="code" />
                    </form:select>
                </td>

                <c:choose>
					<c:when test="${(status.index == 0) || (not empty hcsInv.id)}"><td>&nbsp;</td></c:when>
					<c:otherwise>
						<td class="alt"><a href="javascript:RowManager.deleteRow(investigatorAutocompleterProps,${status.index},'${hcsInv.id==null?'HC#':'ID#'}${hcsInv.id==null?hcsInv.hashCode:hcsInv.id}');"><img src="<tags:imageUrl name="checkno.gif"/>"></a></td>
					</c:otherwise>
				</c:choose>
					
			</tr>
		</c:forEach>
        
    </table>
    <tags:button type="button" color="blue" value="Add Organization" icon="add" onclick="javascript:RowManager.addRow(investigatorAutocompleterProps);" size="small"/>
	
</chrome:division>
<chrome:division id="staff-details" title="Basic Details">
    <div class="leftpanel">
        <div class="row">
            <div class="label"><tags:requiredIndicator />
                <fmt:message key="c3pr.common.firstName"/></div>
            <div class="value">
                <form:input size="25" path="firstName"
                            cssClass="validate-notEmpty" />
            </div>
        </div>
        <div class="row">
            <div class="label"><tags:requiredIndicator />
                <fmt:message key="c3pr.common.lastName"/></div>
            <div class="value">
                <form:input path="lastName" cssClass="validate-notEmpty" size="25" />
            </div>
        </div>
        <div class="row">
            <div class="label">
                <fmt:message key="c3pr.common.middleName"/></div>
            <div class="value">
                <form:input path="middleName" size="25" />
            </div>
        </div>
        <div class="row">
            <div class="label">
                <fmt:message key="c3pr.common.maidenName"/></div>
            <div class="value">
                <form:input path="maidenName" size="25" />
            </div>
        </div>
    </div>

    <div class="rightpanel">
        <div class="row">
            <div class="label"><tags:requiredIndicator />
                <fmt:message key="c3pr.common.NCIIdentifier"/>
            </div>
            <div class="value">
                <form:input path="nciIdentifier" size="25" cssClass="validate-notEmpty" />
                <tags:hoverHint keyProp="healthcareSiteInvestigator.nciIdentifier"/>
            </div>
        </div>

        <div class="row">
            <div class="label"><tags:requiredIndicator />
                    ${command.contactMechanisms[0].type.displayName} (Username)
            </div>
            <div class="value">
                <form:input size="30"
                            path="contactMechanisms[0].value" cssClass="validate-notEmpty&&EMAIL" />
            </div>
        </div>
        <div class="row">
            <div class="label">
                    ${command.contactMechanisms[1].type.displayName}
            </div>
            <div class="value">
                <form:input size="25"
                            path="contactMechanisms[1].value" cssClass="validate-US_PHONE_NO" /> e.g. 7035600296 or 703-560-0296

            </div>
        </div>
        <div class="row">
            <div class="label">
                    ${command.contactMechanisms[2].type.displayName}
            </div>
            <div class="value">
                <form:input size="25"
                            path="contactMechanisms[2].value" cssClass="validate-US_PHONE_NO" /> e.g. 7035600296 or 703-560-0296
            </div>
        </div>
    </div>
</chrome:division>

<div id="dispButton"
		<c:if test="${param.type == 'confirm'}">style="display:none"</c:if>><tags:tabControls />
</div>
</tags:basicFormPanelBox>

<div id="dummy-row" style="display: none;">
<table>
	<tr>
		<td class="alt">
            <input type="hidden" id="healthcareSitePAGE.ROW.INDEX-hidden" name="healthcareSiteInvestigators[PAGE.ROW.INDEX].healthcareSite" />
            <input class="autocomplete validate-notEmpty" type="text" id="healthcareSitePAGE.ROW.INDEX-input" size="50" value="${command.healthcareSiteInvestigators[PAGE.ROW.INDEX].healthcareSite.name}" />
            <tags:indicator id="healthcareSitePAGE.ROW.INDEX-indicator" />
            <div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete"></div>
    	</td>
	
		<td class="alt"><select id="healthcareSiteInvestigators[PAGE.ROW.INDEX].statusCode" name="healthcareSiteInvestigators[PAGE.ROW.INDEX].statusCode" class="validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${studySiteStatusRefData}" var="siteRef">
				<option value="${siteRef.code}">${siteRef.desc }</option>
			</c:forEach>
		    </select>
        </td>
		<td class="tdalt"><a href="javascript:RowManager.deleteRow(investigatorAutocompleterProps,PAGE.ROW.INDEX,-1);">
            <img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
        </td>
	</tr>
</table>
</div>

</div>

</body>
</html>