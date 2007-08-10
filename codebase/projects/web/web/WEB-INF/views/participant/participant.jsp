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
            add_row_division_id: "mytable", 	        /* this id belongs to element where the row would be appended to */
            skeleton_row_division_id: "dummy-row",
            initialIndex: ${fn:length(command.systemAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
            path: "systemAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
        };
   var organizationIdentifierRowInserterProps = {
            add_row_division_id: "mytable-organizationIdentifier", 	        /* this id belongs to element where the row would be appended to */
            skeleton_row_division_id: "dummy-row-organizationIdentifier",
            initialIndex: ${fn:length(command.organizationAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
            path: "organizationAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
            postProcessRowInsertion: function(object){
        clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
		registerAutoCompleter(clonedRowInserter);
    },
    onLoadRowInitialize: function(object, currentRowIndex){
		clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
		registerAutoCompleter(clonedRowInserter);
    },
        };
        rowInserters.push(systemIdentifierRowInserterProps);
        rowInserters.push(organizationIdentifierRowInserterProps);

function submitPostProcess(formElement, flag){	
	if(formElement.id!='command')
		return flag;
	if(!flag)
		return false;
	if(compareDateWithToday($('birthDate').value)==0)
		return true;
	alert("Birth Date cannot be greater than today's date");
	return false;
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
						<td align="right"><span class="red">*</span><em></em> <b>First
						Name:&nbsp;</b></td>
						<td align="left"><form:input path="firstName"
							cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
					</tr>
					<tr>
						<td align="right"><span class="red">*</span><em></em> <b>Last
						Name:</b>&nbsp;</td>
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
						<td align="right"><span class="red">*</span> <em></em> <b>Gender:</b>
						&nbsp;</td>
						<td align="left"><form:select path="administrativeGenderCode"
							cssClass="validate-notEmpty">
							<option value="">--Please Select--</option>
							<form:options items="${administrativeGenderCode}"
								itemLabel="desc" itemValue="code" />
						</form:select></td>
					</tr>
					<tr>
						<td align="right"><span class="red">&nbsp;&nbsp;&nbsp;*</span><em></em><b>Birth
						Date: </b>&nbsp;</td>
						<td><form:input path="birthDate" cssClass="validate-date" />&nbsp;(mm/dd/yyyy)&nbsp;&nbsp;<span
							class="red"><em></em></span></td>
					</tr>
					<tr>
						<td align="right"><span class="red">&nbsp;&nbsp;&nbsp;*</span><em></em><b>Ethnicity:</b> &nbsp;</td>
						<td align="left"><form:select path="ethnicGroupCode" cssClass="validate-notEmpty">
							<option value="">--Please Select--</option>
							<form:options items="${ethnicGroupCode}" itemLabel="desc"
								itemValue="code" />
						</form:select></td>
					</tr>
					<tr>
						<td align="right"><span class="red">&nbsp;&nbsp;&nbsp;*</span><em></em><b>Race(s):</b> &nbsp;</td>
						<td align="left"><form:select path="raceCode" cssClass="validate-notEmpty">
							<option value="">--Please Select--</option>
							<form:options items="${raceCode}" itemLabel="desc"
								itemValue="code" />
						</form:select></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>

		<hr align="left" width="95%">
		
		<input id="addIdentifier" type="button" value="Add System Identifier"
			onclick="javascript:RowManager.addRow(systemIdentifierRowInserterProps);"  />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br> <br>
			
		<tr>	
		
		<td>
		<chrome:division title="System Identifiers">

		<table id="mytable" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<th class="scope=" col" align="left"><b><span
					class="red">*</span>System Name</b></th>
				<th scope="col" align="left"><b><span class="red">*</span>Identifier Type</b></th>
				<th scope="col" align="left"><b><span class="red">*</span>Identifier</b></th>
				<th scope="col" align="left"><b>Primary&nbsp;Indicator</b></th>
				<th class="specalt" scope="col" align="left"></th>
			</tr>
			<c:forEach items="${command.systemAssignedIdentifiers}" varStatus="status">
				 <tr id="mytable-${status.index}">
					<td class="alt"><form:input
						path="systemAssignedIdentifiers[${status.index}].systemName"
						cssClass="validate-notEmpty"/>
						</td>
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
					<td class="alt"><form:radiobutton
						path="systemAssignedIdentifiers[${status.index}].primaryIndicator" value="true" /></td>
					<td class="alt"><a href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,${status.index});"><img
                                src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
				</tr>
			</c:forEach>
		</table>
		</chrome:division>
		</td>
		</tr>
		<tr>
		
		<br> <br>
			<input id="addIdentifier" type="button" value="Add Organization Identifier"
			onclick="javascript:RowManager.addRow(organizationIdentifierRowInserterProps);"  />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br> <br>
			
			<td>
			
			<chrome:division title="Organization Identifiers">

		<table id="mytable-organizationIdentifier" border="0" cellspacing="0" cellpadding="0" class="mytable">
			<tr>
				<th class="scope=" col" align="left"><b><span
					class="red">*</span>Assigning Authority</b></th>
				<th scope="col" align="left"><b><span class="red">*</span>Identifier Type</b></th>
				<th scope="col" align="left"><b><span class="red">*</span>Identifier</b></th>
				<th scope="col" align="left"><b>Primary&nbsp;Indicator</b></th>
				<th class="specalt" scope="col" align="left"></th>
			</tr>
			<c:forEach items="${command.organizationAssignedIdentifiers}" varStatus="organizationStatus">
				 <tr id="mytable-organizationIdentifier-${organizationStatus.index}">
					<td class="alt">
   							<input type="hidden" id="healthcareSite${organizationStatus.index}-hidden"
            				name="organizationAssignedIdentifiers[${organizationStatus.index}].healthcareSite"
          					 value="${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.id}"/>
    						<input class="validate-notEmpty" type="text" id="healthcareSite${organizationStatus.index}-input"
           					size="50"
          				 	value="${command.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.name}"/>
    						<input type="button" id="healthcareSite${organizationStatus.index}-clear"
           				 	value="Clear"/>
      		 				<tags:indicator id="healthcareSite${organizationStatus.index}-indicator"/>
      						<div id="healthcareSite${organizationStatus.index}-choices" class="autocomplete"></div>
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
					<td class="alt"><form:radiobutton
						path="organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator" value="true" /></td>
					<td class="alt"><a href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,${organizationStatus.index});"><img
                                src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
				</tr>
			</c:forEach>
		</table>
		</chrome:division>
		</td>
		
		</tr>
		
	</jsp:attribute>
</tags:tabForm>

<div id="dummy-row" style="display:none;">
        <table>
            <tr>
                <td class="alt"><input id="systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName"
                                        name="systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName"
                                        class="validate-notEmpty"/>
                </td>
                <td class="alt"><select id="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
                                        name="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
                                        class="validate-notEmpty">
                    <option value="">--Please Select--</option>
                    <c:forEach items="${identifiersTypeRefData}" var="id">
                        <option value="${id.desc}">${id.desc}</option>
                    </c:forEach>
                </select>
                </td>
                <td class="alt"><input id="systemAssignedIdentifiers[PAGE.ROW.INDEX].value" name="systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
                                       onfocus="javascript:clearField(this)" class="validate-notEmpty"/></td>
                <td class="alt"><input type="radio" id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" name="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"
                                       value="true"/></td>
                <td class="alt"><a href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,PAGE.ROW.INDEX);"><img
                        src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
            </tr>
        </table>
    </div>
    
    
    <div id="dummy-row-organizationIdentifier" style="display:none;">
        <table>
            <tr>
                              
                 <td class="alt">
                	<input type="hidden" id="healthcareSitePAGE.ROW.INDEX-hidden"
                        name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite"/>
                	<input class="validate-notEmpty" type="text" id="healthcareSitePAGE.ROW.INDEX-input"
                       size="50"
                       value="${command.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite.name}"/>
                	<input type="button" id="healthcareSitePAGE.ROW.INDEX-clear"
                        value="Clear"/>
                   	<tags:indicator id="healthcareSitePAGE.ROW.INDEX-indicator"/>
                  	<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete"></div>
            	</td>
                
                <td class="alt"><select id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
                                        name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
                                        class="validate-notEmpty">
                    <option value="">--Please Select--</option>
                    <c:forEach items="${identifiersTypeRefData}" var="id">
                        <option value="${id.desc}">${id.desc}</option>
                    </c:forEach>
                </select>
                </td>
                <td class="alt"><input id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value" name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
                                       onfocus="javascript:clearField(this)" class="validate-notEmpty"/></td>
                <td class="alt"><input type="radio" id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"
                                       value="true"/></td>
                <td class="alt"><a href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,PAGE.ROW.INDEX);"><img
                        src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
            </tr>
        </table>
    </div>


</body>
</html>
