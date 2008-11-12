<%@ include file="taglibs.jsp"%>

<html>
<head>
	<title><participanttags:htmlTitle subject="${command}" /></title>
<link href="calendar-blue.css" rel="stylesheet" type="text/css" />
<%--<tags:includeScriptaculous />--%>
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
    	return (obj.name+" ("+obj.nciInstituteCode+")")
    },
    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=inputElement.id.split("-")[0]+"-hidden";
	    							$(hiddenField).value=selectedChoice.id;
									}
}
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function getPage(s){
	parent.window.location="reg_patient_search.htm";
}
function clearField(field) {
            field.value = "";
        }
  var systemIdentifierRowInserterProps = {
            add_row_division_id: "systemIdentifiersTable", 	        /* this id belongs to element where the row would be appended to */
            skeleton_row_division_id: "dummy-systemIdentifierRow",
            initialIndex: ${fn:length(command.systemAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
            path: "systemAssignedIdentifiers"                            /* this is the path of the collection that holds the rows  */
        };
   var organizationIdentifierRowInserterProps = {
            add_row_division_id: "organizationIdentifiersTable", 	        /* this id belongs to element where the row would be appended to */
            skeleton_row_division_id: "dummy-organizationIdentifierRow",
            initialIndex: ${command.MRN!=null?fn:length(command.organizationAssignedIdentifiers):fn:length(command.organizationAssignedIdentifiers)+1},                            /* this is the initial count of the rows when the page is loaded  */
            path: "organizationAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
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
         var mrnAutocompleterProps = {
            basename: "mrnOrganization",
            populator: function(autocompleter, text) {
                 ParticipantAjaxFacade.matchHealthcareSites( text,function(values) {
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
        };
        AutocompleterManager.addAutocompleter(mrnAutocompleterProps);
        RowManager.addRowInseter(systemIdentifierRowInserterProps);
        RowManager.addRowInseter(organizationIdentifierRowInserterProps);
ValidationManager.submitPostProcess= function(formElement, flag){	
	if(formElement.id!='command')
		return flag;
	if(!flag)
		return false;
		
	if(formElement.id="command"){
		flag=false;
		for(i=1 ; i<8 ; i++){
			if($('raceCodes'+i).checked){
				flag=true;
				break;
			}
		}
		if(!flag){
			ValidationManager.showError($("raceCodes"), "required")	
			return false;
		}
		if(compareDateWithToday($('birthDate').value)==0)
			return true;
		alert("Birth Date cannot be greater than today's date");
		return false;
	}
}
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
<style>
#workflow-tabs {
top:76px;
}
</style>
</head>
<body>
<form:form method="post" name="participantDetailsForm" cssClass="standard">
<tags:tabFields tab="${tab}" />

<chrome:box title="${tab.shortTitle}">

<chrome:division id="participant-details" title="Basic Details">
				<div class="leftpanel">
					<div class="row">
						<div class="label required-indicator">First Name:</div>
						<div class="value"><form:input path="firstName"
							cssClass="validate-notEmpty" /></div>
					</div>
					<div class="row">
						<div class="label required-indicator">Last Name:</div>
						<div class="value"><form:input path="lastName"
							cssClass="validate-notEmpty" /></div>
					</div>
					<div class="row">
						<div class="label">Middle Name:</div>
						<div class="value"><form:input path="middleName" /></div>
					</div>
					<div class="row">
						<div class="label">Maiden Name:</div>
						<div class="value"><form:input path="maidenName" /></div>
					</div>
					<div class="row">
						<div class="label required-indicator">Gender:</div>
						<div class="value"><form:select path="administrativeGenderCode"
							cssClass="validate-notEmpty">
							<option value="">Please Select</option>
							<form:options items="${administrativeGenderCode}"
								itemLabel="desc" itemValue="code" />
							</form:select> 
						</div> 
					</div>
				</div>
				<div class="rightpanel">
					<div class="row">
						<div class="label required-indicator">Birth Date:</div>
						<div class="value"><form:input path="birthDate" cssClass="validate-notEmpty&&DATE" /> (mm/dd/yyyy)&nbsp;</div>
					</div>
					<div class="row">
						<div class="label required-indicator">Ethnicity:</div>
						<div class="value"><form:select path="ethnicGroupCode"
							cssClass="validate-notEmpty">
							<option value="">Please Select</option>
							<form:options items="${ethnicGroupCode}" itemLabel="desc"
								itemValue="code" />
						</form:select><tags:hoverHint keyProp="subject.ethnicGroupCode"/></div>
					</div>
					<div class="row">
						<div class="label required-indicator">Races: </div>
						<table>
						<tr><td><div id="raceCodes" style="display:inline"><form:checkbox path="raceCodes" value="Asian"/> Asian</div></td>
							<td><div id="raceCodes" style="display:inline"><form:checkbox path="raceCodes" value="Black_or_African_American"/> Black or African American</div></td>
							<td><tags:hoverHint keyProp="subject.raceCode"/></td>
						</tr>
						<tr><td><div id="raceCodes" style="display:inline"><form:checkbox path="raceCodes" value="White"/> White</div></td>
							<td><div id="raceCodes" style="display:inline"><form:checkbox path="raceCodes" value="American_Indian_or_Alaska_Native"/> American Indian or Alaska Native</div></td>
							<td></td>
						</tr>
						<tr><td colspan=2><div id="raceCodes" style="display:inline"><form:checkbox path="raceCodes" value="Native_Hawaiian_or_Pacific_Islander"/> Native Hawaiian or Pacific Islander</div></td>
							
							<td></td>
						</tr>
						<tr><td><div id="raceCodes" style="display:inline"><form:checkbox path="raceCodes" value="Not_Reported"/> Not Reported</div></td>
							<td><div id="raceCodes" style="display:inline"><form:checkbox path="raceCodes" value="Unknown"/> Unknown</div></td>
							<td> </td>
						</tr>
						</table>
					</div>
					
				</div>
		</chrome:division>
		
		<chrome:division title="MRN">
		<tags:errors path="primaryIdentifier"/>
    		<div class="leftpanel">
         		<div id="mrnDetails">
                	 <div class="row">
								<c:set var="_code" value="" />
								<c:set var="_name" value="" />
								<c:if test="${fn:length(command.organizationAssignedIdentifiers)>0}">				
								<c:set var="_code" value="(${command.organizationAssignedIdentifiers[0].healthcareSite.nciInstituteCode})" />
								<c:set var="_name" value="${command.organizationAssignedIdentifiers[0].healthcareSite.name}" />
								</c:if>
		                        <div class="label required-indicator">Organization:</div>
		                        <div class="value">
								<input type="hidden" id="mrnOrganization-hidden"
									name="organizationAssignedIdentifiers[0].healthcareSite"
									value="${command.organizationAssignedIdentifiers[0].healthcareSite.id}" />
								<input id="mrnOrganization-input" size="38" type="text"
								name="xyz"
								value='<c:out value="${_name} ${_code}" />'
								class="autocomplete validate-notEmpty" />
								<tags:hoverHint keyProp="subject.MRN.organization"/>
								<tags:indicator id="mrnOrganization-indicator" />
								<div id="mrnOrganization-choices" class="autocomplete" style="display: none;"></div>
							    </div>
                    </div>
                    <div class="row">
		                        <div class="label required-indicator">Medical Record Number:</div>
		                        <div class="value"><input type="text" name="organizationAssignedIdentifiers[0].value" 
								size="30" maxlength="30"
								value="${command.organizationAssignedIdentifiers[0].value}" class="validate-notEmpty" />
								<tags:hoverHint keyProp="subject.MRN.value"/>
							     <input type="hidden" name="organizationAssignedIdentifiers[0].type"
								value="MRN"/>
								<input type="hidden" name="organizationAssignedIdentifiers[0].primaryIndicator" value="true"/></div>
					</div>
          		</div>
    		</div>
		</chrome:division>
		
		<chrome:division title="Organization Assigned Identifiers">
		<tags:errors path="organizationAssignedIdentifiers"/>

				<table id="organizationIdentifiersTable" border="0"
					cellspacing="0" cellpadding="0" class="tablecontent">
					<tr id="hOrganizationAssignedIdentifier" <c:if test="${fn:length(command.organizationAssignedIdentifiers) < 2}">style="display:none;"</c:if>>
						<th><span
							class="required-indicator">Assigning Organization</span><tags:hoverHint keyProp="identifier.organization"/></th>
						<th><span class="required-indicator">Identifier
						Type</span><tags:hoverHint keyProp="identifier.type"/></th>
						<th><span class="required-indicator">Identifier</span><tags:hoverHint keyProp="identifier.value"/></th>
						<th><span>Primary Indicator</span><tags:hoverHint keyProp="identifier.primary"/></th>
						<th ></th>
					</tr>
					<c:forEach items="${command.organizationAssignedIdentifiers}" begin="1"
						varStatus="organizationStatus" var="orgId">
						<tr
							id="organizationIdentifiersTable-${organizationStatus.index}">
							<c:set var="_code" value="" />
							<c:set var="_name" value="" />
							<c:set var="_code" value="(${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.nciInstituteCode})" />
							<c:set var="_name" value="${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.name}" />
							<td class="alt"><input type="hidden"
								id="healthcareSite${organizationStatus.index}-hidden"
								name="organizationAssignedIdentifiers[${organizationStatus.index}].healthcareSite"
								value="${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.id}" />
							<input class="autocomplete validate-notEmpty" type="text"
								id="healthcareSite${organizationStatus.index}-input" size="50"
								value='<c:out value="${_name} ${_code}" />'/>
							<input type="button"
								id="healthcareSite${organizationStatus.index}-clear"
								value="Clear" /> <tags:indicator
								id="healthcareSite${organizationStatus.index}-indicator" />
							<div id="healthcareSite${organizationStatus.index}-choices"
								class="autocomplete"></div>
							</td>
							<td class="alt"><form:select
								path="organizationAssignedIdentifiers[${organizationStatus.index}].type"
								cssClass="validate-notEmpty">
								<option value="">Please Select</option>
								<form:options items="${identifiersTypeRefData}" itemLabel="desc"
									itemValue="desc" />
							</form:select></td>
							<td class="alt"><form:input
								path="organizationAssignedIdentifiers[${organizationStatus.index}].value"
								cssClass="validate-notEmpty" /></td>
							<td><input type="radio"	id="organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator-radio"
							value="${command.organizationAssignedIdentifiers[organizationStatus.index].primaryIndicator}" class="identifierRadios" onClick="manageIdentifierRadio(this);"/>
							<form:hidden path="organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator"/></td>
							<td class="alt"><a
								href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,${organizationStatus.index},'${orgId.id==null?'HC#':'ID#'}${orgId.id==null?orgId.hashCode:orgId.id}');"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<div align="right"><input id="addIdentifier" type="button"
					value="Add Another Identifier"
					onclick="$('hOrganizationAssignedIdentifier').show();javascript:RowManager.addRow(organizationIdentifierRowInserterProps);" />
				</div>
			</chrome:division></td>

			<chrome:division title="System Assigned Identifiers">
				<tags:errors path="systemAssignedIdentifiers"/>
				<table id="systemIdentifiersTable" border="0" cellspacing="0" cellpadding="0"
					class="tablecontent">
					<tr id="hSystemAssignedIdentifier" <c:if test="${fn:length(command.systemAssignedIdentifiers) == 0}">style="display:none;"</c:if>>
						<th><span
							class="required-indicator">System Name</span><tags:hoverHint keyProp="identifier.systemName"/></th>
						<th><span class="required-indicator">Identifier Type</span><tags:hoverHint id="1" keyProp="identifier.type"/></th>
						<th><span class="required-indicator">Identifier</span><tags:hoverHint id="2" keyProp="identifier.value"/></th>
						<th>Primary&nbsp;Indicator<tags:hoverHint id="3" keyProp="identifier.primary"/></th>
						<th></th>
					</tr>
					<c:forEach items="${command.systemAssignedIdentifiers}"
						varStatus="status" var="sysId">
						<tr id="systemIdentifiersTable-${status.index}">
							<td class="alt"><form:input
								path="systemAssignedIdentifiers[${status.index}].systemName"
								cssClass="validate-notEmpty" /></td>
							<td class="alt"><form:select
								path="systemAssignedIdentifiers[${status.index}].type"
								cssClass="validate-notEmpty">
								<option value="">Please Select</option>
								<form:options items="${identifiersTypeRefData}" itemLabel="desc"
									itemValue="desc" />
							</form:select></td>
							<td class="alt"><form:input
								path="systemAssignedIdentifiers[${status.index}].value"
								cssClass="validate-notEmpty" /></td>
							<td><input type="radio"	id="systemAssignedIdentifiers[${status.index}].primaryIndicator-radio"
							value="${command.systemAssignedIdentifiers[status.index].primaryIndicator}" class="identifierRadios" onClick="manageIdentifierRadio(this);"/>
							<form:hidden path="systemAssignedIdentifiers[${status.index}].primaryIndicator"/></td>
							<td class="alt"><a
								href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,${status.index},'${sysId.id==null?'HC#':'ID#'}${sysId.id==null?sysId.hashCode:sysId.id}');"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<div align="right"><input id="addIdentifier" type="button"
					value="Add Another Identifier"
					onclick="$('hSystemAssignedIdentifier').show();javascript:RowManager.addRow(systemIdentifierRowInserterProps);" />
				</div>
			</chrome:division></td>

<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" />

</chrome:box>
</form:form>
<script>
		$$("form .identifierRadios").each(function(e)
										{
											if(e.value=="true")
												e.checked=true;
										}
									);
	</script>

<div id="dummy-systemIdentifierRow" style="display:none;">
<table>
	<tr>
		<td class="alt"><input
			id="systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName"
			name="systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName" type="text"
			class="validate-notEmpty" /></td>
		<td class="alt"><select
			id="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${identifiersTypeRefData}" var="id">
				<option value="${id.desc}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td class="alt"><input
			id="systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="systemAssignedIdentifiers[PAGE.ROW.INDEX].value" type="text"
			onfocus="javascript:clearField(this)" class="validate-notEmpty" /></td>
		<td><input type="radio"	id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-radio"
			value="false" class="identifierRadios" onClick="manageIdentifierRadio(this);"/>
			<input type="hidden" id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" name="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"/></td>
		<td class="alt"><a
			href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,PAGE.ROW.INDEX,-1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>


<div id="dummy-organizationIdentifierRow" style="display:none;">
<table>
	<tr>

		<td class="alt"><input type="hidden"
			id="healthcareSitePAGE.ROW.INDEX-hidden"
			name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite" />
		<input class="autocomplete validate-notEmpty" type="text"
			id="healthcareSitePAGE.ROW.INDEX-input" size="50"
			value="${command.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite.name}" />
		<input type="button" id="healthcareSitePAGE.ROW.INDEX-clear"
			value="Clear" /> <tags:indicator
			id="healthcareSitePAGE.ROW.INDEX-indicator" />
		<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete"></div>
		</td>

		<td class="alt"><select
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${identifiersTypeRefData}" var="id">
				<option value="${id.desc}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td class="alt"><input
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value" type="text"
			onfocus="javascript:clearField(this)" class="validate-notEmpty" /></td>
		<td><input type="radio"	id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-radio"
			value="false" class="identifierRadios" onClick="manageIdentifierRadio(this);"/>
			<input type="hidden" name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"/></td>
		<td class="alt"><a
			href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,PAGE.ROW.INDEX,-1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>


</body>
</html>
