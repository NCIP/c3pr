<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<html>
<head>
<link href="calendar-blue.css" rel="stylesheet" type="text/css" />
<tags:includeScriptaculous />
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
        return obj.name
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
            initialIndex: ${command.MRN?fn:length(command.organizationAssignedIdentifiers):fn:length(command.organizationAssignedIdentifiers)+1},                            /* this is the initial count of the rows when the page is loaded  */
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
                return obj.name
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
	if(compareDateWithToday($('birthDate').value)==0)
		return true;
	alert("Birth Date cannot be greater than today's date");
	return false;
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
<tags:tabForm tab="${tab}" flow="${flow}"
	formName="participantDetailsForm">
	<jsp:attribute name="singleFields">
		<input type="hidden" name="_action" id="_action" value="">
		<input type="hidden" name="_selected" id="_selected" value="">
		<input type="hidden" name="_page" id="_page" value="0">
		<br>

		<table width="80%" border="0" cellspacing="0" cellpadding="0"
			id="details">
			<tr>
				<td width="40%" valign="top">
				<table width="100%" border="0" cellspacing="1" cellpadding="1"
					id="table1">
					<tr>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
							height="1" class="heightControl"></td>
						<td width="70%"><img src="<tags:imageUrl name="spacer.gif"/>"
							width="1" height="1" class="heightControl"></td>
					</tr>
					<tr>
						<td align="right"><span class="required-indicator"><b>First Name:&nbsp;</b></span></td>
						<td align="left"><form:input path="firstName"
							cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
					</tr>
					<tr>
						<td align="right"><span class="required-indicator"><b>Last Name:</b></span>&nbsp;</td>
						<td align="left"><form:input path="lastName"
							cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
					</tr>
					<tr>
						<td align="right"><em></em> <b>Middle Name:</b>&nbsp;</td>
						<td align="left"><form:input path="middleName" />&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><em></em> <b>Maiden Name:</b>&nbsp;</td>
						<td align="left"><form:input path="maidenName" />&nbsp;&nbsp;&nbsp;</td>
					</tr>

				</table>
				</td>
				<td width="40%" valign="top">
				<table width="100%" border="0" cellspacing="1" cellpadding="1"
					id="table1">
					<tr>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
							height="1" class="heightControl"></td>
						<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
							height="1" class="heightControl"></td>
					</tr>
					<tr>
						<td align="right"><span class="required-indicator"><b>Gender:</b>&nbsp;</span></td>
						<td align="left"><form:select path="administrativeGenderCode"
							cssClass="validate-notEmpty">
							<option value="">--Please Select--</option>
							<form:options items="${administrativeGenderCode}"
								itemLabel="desc" itemValue="code" />
						</form:select></td>
					</tr>
					<tr>
						<td align="right"><span class="required-indicator"><b>Birth Date: </b></span></td>
						<td><form:input path="birthDate" cssClass="validate-date" />&nbsp;(mm/dd/yyyy)&nbsp;&nbsp;<span
							class="red"><em></em></span></td>
					</tr>
					<tr>
						<td align="right"><span class="required-indicator"><b>Ethnicity:</b>&nbsp;</span></td>
						<td align="left"><form:select path="ethnicGroupCode"
							cssClass="validate-notEmpty">
							<option value="">--Please Select--</option>
							<form:options items="${ethnicGroupCode}" itemLabel="desc"
								itemValue="code" />
						</form:select></td>
					</tr>
					<tr>
						<td align="right"><span class="required-indicator"><b>Race(s):</b> &nbsp;</span></td>
						<td align="left"><form:select path="raceCode"
							cssClass="validate-notEmpty">
							<option value="">--Please Select--</option>
							<form:options items="${raceCode}" itemLabel="desc"
								itemValue="code" />
						</form:select></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>
		<br>
		<br>
		
		
		<chrome:division title="MRN">
    		<div class="leftpanel">
         		<div id="mrnDetails">
                	 <div class="row">
		                        <div class="label required-indicator">Organization:</div>
		                        <div class="value">
								<input type="hidden" id="mrnOrganization-hidden"
									name="organizationAssignedIdentifiers[0].healthcareSite"
									value="${command.organizationAssignedIdentifiers[0].healthcareSite.id}" />
								<input id="mrnOrganization-input" size="50" type="text"
								name="organizationAssignedIdentifiers[0].healthcareSite.name"
								value="${command.organizationAssignedIdentifiers[0].healthcareSite.name}" class="autocomplete validate-notEmpty" />
								<tags:indicator id="mrnOrganization-indicator" />
								<div id="mrnOrganization-choices" class="autocomplete"></div>
							    </div>
                    </div>
                    <div class="row">
		                        <div class="label required-indicator">Identifier:</div>
		                        <div class="value"><input type="text" name="organizationAssignedIdentifiers[0].value" 
								size="30" maxlength="30"
								value="${command.organizationAssignedIdentifiers[0].value}" class="validate-notEmpty" />
							     <input type="hidden" name="organizationAssignedIdentifiers[0].type"
								value="MRN"/>
								<input type="hidden" name="organizationAssignedIdentifiers[0].primaryIndicator" value="true"/></div>
					</div>
          		</div>
    		</div>
		</chrome:division>
		
		<tr>

			<td><chrome:division title="Organization Assigned Identifiers">

				<table id="organizationIdentifiersTable" border="0"
					cellspacing="0" cellpadding="0" class="tablecontent">
					<tr>
						<th><span
							class="required-indicator">Assigning Authority</span></th>
						<th><span class="required-indicator">Identifier
						Type</span></th>
						<th><span class="required-indicator">Identifier</span></th>
						<th>Primary Indicator</th>
						<th ></th>
					</tr>
					<c:forEach items="${command.organizationAssignedIdentifiers}" begin="1"
						varStatus="organizationStatus">
						<tr
							id="organizationIdentifiersTable-${organizationStatus.index}">
							<td class="alt"><input type="hidden"
								id="healthcareSite${organizationStatus.index}-hidden"
								name="organizationAssignedIdentifiers[${organizationStatus.index}].healthcareSite"
								value="${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.id}" />
							<input class="autocomplete validate-notEmpty" type="text"
								id="healthcareSite${organizationStatus.index}-input" size="50"
								value="${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.name}" />
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
								<option value="">--Please Select--</option>
								<form:options items="${identifiersTypeRefData}" itemLabel="desc"
									itemValue="desc" />
							</form:select></td>
							<td class="alt"><form:input
								path="organizationAssignedIdentifiers[${organizationStatus.index}].value"
								cssClass="validate-notEmpty" /></td>
							<td><input type="radio"	id="organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator-radio"
							value="${command.organizationAssignedIdentifiers[organizationStatus.index].primaryIndicator}" class="identifierRadios" onclick="manageIdentifierRadio(this);"/>
							<form:hidden path="organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator"/></td>
							<td class="alt"><a
								href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,${organizationStatus.index});"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<div align="right"><input id="addIdentifier" type="button"
					value="Add Another Identifier"
					onclick="javascript:RowManager.addRow(organizationIdentifierRowInserterProps);" />
				</div>
			</chrome:division></td>

		</tr>
		
		<tr>

			<td><chrome:division title="System Assigned Identifiers">

				<table id="systemIdentifiersTable" border="0" cellspacing="0" cellpadding="0"
					class="tablecontent">
					<tr>
						<th><span
							class="required-indicator">System Name</span></th>
						<th><span class="required-indicator">Identifier
						Type</span></th>
						<th><span class="required-indicator">Identifier</span></th>
						<th>Primary&nbsp;Indicator</th>
						<th></th>
					</tr>
					<c:forEach items="${command.systemAssignedIdentifiers}"
						varStatus="status">
						<tr id="systemIdentifiersTable-${status.index}">
							<td class="alt"><form:input
								path="systemAssignedIdentifiers[${status.index}].systemName"
								cssClass="validate-notEmpty" /></td>
							<td class="alt"><form:select
								path="systemAssignedIdentifiers[${status.index}].type"
								cssClass="validate-notEmpty">
								<option value="">--Please Select--</option>
								<form:options items="${identifiersTypeRefData}" itemLabel="desc"
									itemValue="desc" />
							</form:select></td>
							<td class="alt"><form:input
								path="systemAssignedIdentifiers[${status.index}].value"
								cssClass="validate-notEmpty" /></td>
							<td><input type="radio"	id="systemAssignedIdentifiers[${status.index}].primaryIndicator-radio"
							value="${command.systemAssignedIdentifiers[status.index].primaryIndicator}" class="identifierRadios" onclick="manageIdentifierRadio(this);"/>
							<form:hidden path="systemAssignedIdentifiers[${status.index}].primaryIndicator"/></td>
							<td class="alt"><a
								href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,${status.index});"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<div align="right"><input id="addIdentifier" type="button"
					value="Add Another Identifier"
					onclick="javascript:RowManager.addRow(systemIdentifierRowInserterProps);" />
				</div>
			</chrome:division></td>
		</tr>

	</jsp:attribute>
</tags:tabForm>
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
			<option value="">--Please Select--</option>
			<c:forEach items="${identifiersTypeRefData}" var="id">
				<option value="${id.desc}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td class="alt"><input
			id="systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="systemAssignedIdentifiers[PAGE.ROW.INDEX].value" type="text"
			onfocus="javascript:clearField(this)" class="validate-notEmpty" /></td>
		<td><input type="radio"	id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-radio"
			value="false" class="identifierRadios" onclick="manageIdentifierRadio(this);"/>
			<input type="hidden" id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" name="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"/></td>
		<td class="alt"><a
			href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,PAGE.ROW.INDEX);"><img
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
			<option value="">--Please Select--</option>
			<c:forEach items="${identifiersTypeRefData}" var="id">
				<option value="${id.desc}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td class="alt"><input
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value" type="text"
			onfocus="javascript:clearField(this)" class="validate-notEmpty" /></td>
		<td><input type="radio"	id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-radio"
			value="false" class="identifierRadios" onclick="manageIdentifierRadio(this);"/>
			<input type="hidden" name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"/></td>
		<td class="alt"><a
			href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,PAGE.ROW.INDEX);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>


</body>
</html>
