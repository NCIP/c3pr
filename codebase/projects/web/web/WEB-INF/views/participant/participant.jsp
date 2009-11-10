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
    	return (obj.name+" ("+obj.ctepCode+")")
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
            	return (obj.name+" ("+obj.ctepCode+")")
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
</head>
<body>
<form:form method="post" name="participantDetailsForm" cssClass="standard">
<tags:tabFields tab="${tab}" />

<chrome:box title="${tab.shortTitle}">
<tags:instructions code="participant" />
<chrome:division id="participant-details" title="Basic Details">
				<div class="leftpanel">
					<div class="row">
						<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.firstName"/></div>
						<div class="value"><form:input path="firstName"
							cssClass="required validate-notEmpty" /></div>
					</div>
					<div class="row">
						<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.lastName"/></div>
						<div class="value"><form:input path="lastName"
							cssClass="required validate-notEmpty" /></div>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.middleName"/></div>
						<div class="value"><form:input path="middleName" /></div>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.maidenName"/></div>
						<div class="value"><form:input path="maidenName" /></div>
					</div>
					<div class="row">
						<div class="label"><tags:requiredIndicator /><fmt:message key="participant.gender"/></div>
						<div class="value"><form:select path="administrativeGenderCode"
							cssClass="required validate-notEmpty">
							<option value="">Please Select</option>
							<form:options items="${administrativeGenderCode}"
								itemLabel="desc" itemValue="code" />
							</form:select> 
						</div> 
					</div>
					<div class="row">
						<div class="label"><tags:requiredIndicator /><fmt:message key="participant.birthDate"/></div>
						<div class="value"><form:input path="birthDate" cssClass="required validate-notEmpty&&DATE" /> (mm/dd/yyyy)&nbsp;</div>
					</div>
				</div>
				<div class="rightpanel">
					<div class="row">
						<div class="label"><tags:requiredIndicator /><fmt:message key="participant.ethnicity"/></div>
						<div class="value"><form:select path="ethnicGroupCode"
							cssClass="required validate-notEmpty">
							<option value="">Please Select</option>
							<form:options items="${ethnicGroupCode}" itemLabel="desc"
								itemValue="code" />
						</form:select><tags:hoverHint keyProp="subject.ethnicGroupCode"/></div>
					</div>
					<div class="row">
						<div class="label"><tags:requiredIndicator /><fmt:message key="participant.race"/> <span style="text-align:left;"><tags:hoverHint keyProp="subject.raceCode"/></span></div>
						<table>
						<tr>
                            <td align="left" class="race">
		                        <form:checkbox id="raceCodes1" path="raceCodes" value="Asian"/> Asian<br>
								<form:checkbox id="raceCodes2" path="raceCodes" value="Black_or_African_American"/> Black or African American<br>
                                <form:checkbox id="raceCodes3" path="raceCodes" value="White"/> White<br>
								<form:checkbox id="raceCodes4" path="raceCodes" value="American_Indian_or_Alaska_Native"/> American Indian or Alaska Native<br>
		                        <form:checkbox id="raceCodes5" path="raceCodes" value="Native_Hawaiian_or_Pacific_Islander"/> Native Hawaiian or Pacific Islander<br>
		                        <form:checkbox id="raceCodes6" path="raceCodes" value="Not_Reported"/> Not Reported<br>
	 							<form:checkbox id="raceCodes7" path="raceCodes" value="Unknown"/> Unknown
		                    </td><td align="left" id="raceCodes" style="display:inline"/>
		                </tr>
						</table>
					</div>
					
				</div>
		</chrome:division>
		<chrome:division title="Primary Identifier">
		<tags:errors path="primaryIdentifierValue"/>
         		<div id="mrnDetails">
					 <div class="leftpanel">
	                	 <div class="row">
									<c:set var="_code" value="" />
									<c:set var="_name" value="" />
									<c:if test="${fn:length(command.organizationAssignedIdentifiers)>0}">				
									<c:set var="_code" value="(${command.organizationAssignedIdentifiers[0].healthcareSite.ctepCode})" />
									<c:set var="_name" value="${command.organizationAssignedIdentifiers[0].healthcareSite.name}" />
									</c:if>
			                        <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.organization"/></div>
			                        <div class="value">
									<input type="hidden" id="mrnOrganization-hidden"
										name="organizationAssignedIdentifiers[0].healthcareSite"
										value="${command.organizationAssignedIdentifiers[0].healthcareSite.id}" />
									<input id="mrnOrganization-input" size="28" type="text"
									name="xyz"
									value='<c:out value="${_name} ${_code}" />'
									class="autocomplete validate-notEmpty" />
									<tags:hoverHint keyProp="subject.MRN.organization"/>
									<tags:indicator id="mrnOrganization-indicator" />
									<div id="mrnOrganization-choices" class="autocomplete" style="display:none;"></div>
								    </div>
	                    </div>
						<div class="row">
			                        <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/></div>
			                        <div class="value"><input type="text" name="organizationAssignedIdentifiers[0].value" 
									size="30" maxlength="33"
									value="${command.organizationAssignedIdentifiers[0].value}" class="required validate-notEmpty&&HTML_SPECIAL_CHARS" />
									<tags:hoverHint keyProp="subject.MRN.value"/>
									<input type="hidden" name="organizationAssignedIdentifiers[0].primaryIndicator" value="true"/></div>
						</div>
					</div>

					 <div class="rightpanel">
						<div class="row">
								<div class="label"><fmt:message key="c3pr.common.identifierType"/></div>
								<div class="value">
								<form:select
									path="organizationAssignedIdentifiers[0].type" cssClass="required validate-notEmpty"> 
									<form:options items="${identifiersTypeRefData}" itemLabel="desc" itemValue="code" />
								</form:select>
								</div>
						</div>
					</div>
          		</div>
		</chrome:division>
		
		<chrome:division title="Additional Organization Assigned Identifiers">
		<tags:errors path="organizationAssignedIdentifiers"/>

				<table id="organizationIdentifiersTable" border="0"
					cellspacing="0" cellpadding="0" class="tablecontent">
					<tr id="hOrganizationAssignedIdentifier" <c:if test="${fn:length(command.organizationAssignedIdentifiers) < 2}">style="display:none;"</c:if>>
						<th><span
							class=""><tags:requiredIndicator /><fmt:message key="c3pr.common.assigningAuthority"/></span><tags:hoverHint keyProp="identifier.organization"/></th>
						<th><span class=""><tags:requiredIndicator /><fmt:message key="c3pr.common.identifierType"/>
						</span><tags:hoverHint keyProp="identifier.type"/></th>
						<th><span class=""><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/></span><tags:hoverHint keyProp="identifier.value"/></th>
						<th ></th>
					</tr>
					<c:forEach items="${command.organizationAssignedIdentifiers}" begin="1"
						varStatus="organizationStatus" var="orgId">
						<tr
							id="organizationIdentifiersTable-${organizationStatus.index}">
							<c:set var="_code" value="" />
							<c:set var="_name" value="" />
							<c:set var="_code" value="(${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.ctepCode})" />
							<c:set var="_name" value="${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.name}" />
							<td class="alt"><input type="hidden"
								id="healthcareSite${organizationStatus.index}-hidden"
								name="organizationAssignedIdentifiers[${organizationStatus.index}].healthcareSite"
								value="${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.id}" />
							<input class="autocomplete validate-notEmpty" type="text"
								id="healthcareSite${organizationStatus.index}-input" size="50"
								value='<c:out value="${_name} ${_code}" />'/>
							<tags:indicator
								id="healthcareSite${organizationStatus.index}-indicator" /> 
							<div id="healthcareSite${organizationStatus.index}-choices"
								class="autocomplete"  style="display: none;"></div>
							</td>
							<td class="alt"><form:select
								path="organizationAssignedIdentifiers[${organizationStatus.index}].type"
								cssClass="required validate-notEmpty">
								<option value="">Please Select</option>
								<form:options items="${identifiersTypeRefData}" itemLabel="desc"
									itemValue="code" />
							</form:select></td>
							<td class="alt"><form:input
								path="organizationAssignedIdentifiers[${organizationStatus.index}].value"
								cssClass="required validate-notEmpty" /></td>
							<td class="alt"><a
								href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,${organizationStatus.index},'${orgId.id==null?'HC#':'ID#'}${orgId.id==null?orgId.hashCode:orgId.id}');"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<tags:button type="button" color="blue" icon="add" value="Add Identifier" onclick="$('hOrganizationAssignedIdentifier').show();javascript:RowManager.addRow(organizationIdentifierRowInserterProps);" size="small"/>
			</chrome:division></td>

			<chrome:division title="System Assigned Identifiers">
				<tags:errors path="systemAssignedIdentifiers"/>
				<table id="systemIdentifiersTable" border="0" cellspacing="0" cellpadding="0"
					class="tablecontent">
					<tr id="hSystemAssignedIdentifier" <c:if test="${fn:length(command.systemAssignedIdentifiers) == 0}">style="display:none;"</c:if>>
						<th><span
							class=""><tags:requiredIndicator /><fmt:message key="c3pr.common.systemName"/></span><tags:hoverHint keyProp="identifier.systemName"/></th>
						<th><span class=""><tags:requiredIndicator /><fmt:message key="c3pr.common.identifierType"/></span><tags:hoverHint id="1" keyProp="identifier.type"/></th>
						<th><span class=""><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/></span><tags:hoverHint id="2" keyProp="identifier.value"/></th>
						<th></th>
					</tr>
					<c:forEach items="${command.systemAssignedIdentifiers}"
						varStatus="status" var="sysId">
						<tr id="systemIdentifiersTable-${status.index}">
							<td class="alt"><form:input
								path="systemAssignedIdentifiers[${status.index}].systemName"
								cssClass="required validate-notEmpty" /></td>
							<td class="alt"><form:select
								path="systemAssignedIdentifiers[${status.index}].type"
								cssClass="required validate-notEmpty">
								<option value="">Please Select</option>
								<form:options items="${identifiersTypeRefData}" itemLabel="desc"
									itemValue="code" />
							</form:select></td>
							<td class="alt"><form:input
								path="systemAssignedIdentifiers[${status.index}].value"
								cssClass="required validate-notEmpty" /></td>
							<td class="alt"><a
								href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,${status.index},'${sysId.id==null?'HC#':'ID#'}${sysId.id==null?sysId.hashCode:sysId.id}');"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<tags:button type="button" color="blue" icon="add" value="Add Identifier" onclick="$('hSystemAssignedIdentifier').show();javascript:RowManager.addRow(systemIdentifierRowInserterProps);" size="small"/>
			</chrome:division></td>
</chrome:box>
<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" />
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
			class="required validate-notEmpty" /></td>
		<td class="alt"><select
			id="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="required validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${identifiersTypeRefData}" var="id">
				<option value="${id.desc}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td class="alt"><input
			id="systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="systemAssignedIdentifiers[PAGE.ROW.INDEX].value" type="text"
			onfocus="javascript:clearField(this)" class="required validate-notEmpty" /></td>
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
		 <tags:indicator
			id="healthcareSitePAGE.ROW.INDEX-indicator" />
		<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete"  style="display: none;"></div>
		</td>

		<td class="alt"><select
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="required validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${identifiersTypeRefData}" var="id">
				<option value="${id.desc}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td class="alt"><input
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value" type="text"
			onfocus="javascript:clearField(this)" class="required validate-notEmpty" /></td>
		<td class="alt"><a
			href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,PAGE.ROW.INDEX,-1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>


</body>
</html>
