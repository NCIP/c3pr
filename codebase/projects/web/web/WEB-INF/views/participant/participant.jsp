<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<html>
<head>
	<title><participanttags:htmlTitle subject="${command.participant}" /></title>
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
    	return (obj.name+" ("+obj.primaryIdentifier+")")
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
            add_row_division_id: "identifiersTable", 	        /* this id belongs to element where the row would be appended to */
            row_id_discriminator: "systemIdentifiersTable", // override the above! see row-manager.js for explanation. 
            skeleton_row_division_id: "dummy-systemIdentifierRow",
            initialIndex: ${fn:length(command.participant.systemAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
            path: "participant.systemAssignedIdentifiers",                            /* this is the path of the collection that holds the rows  */
            postProcessRowInsertion: function(object){
        	  	inputDateElementLocal="participant.systemAssignedIdentifiers["+object.localIndex+"].startDate";
		        inputDateElementLink="participant.systemAssignedIdentifiers["+object.localIndex+"].startDate-calbutton";
		        Calendar.setup(
		        {
		            inputField  : inputDateElementLocal,         // ID of the input field
		            ifFormat    : "%m/%d/%Y",    // the date format
		            button      : inputDateElementLink       // ID of the button
		        }
		                );
		        inputDateElementLocal="participant.systemAssignedIdentifiers["+object.localIndex+"].endDate";
		        inputDateElementLink="participant.systemAssignedIdentifiers["+object.localIndex+"].endDate-calbutton";
		        Calendar.setup(
		        {
		            inputField  : inputDateElementLocal,         // ID of the input field
		            ifFormat    : "%m/%d/%Y",    // the date format
		            button      : inputDateElementLink       // ID of the button
		        }
		                );
		    }
  		};
 var organizationIdentifierRowInserterProps = {
          add_row_division_id: "identifiersTable", 	        /* this id belongs to element where the row would be appended to */
          skeleton_row_division_id: "dummy-organizationIdentifierRow",
          initialIndex: ${fn:length(command.participant.organizationAssignedIdentifiers)==0?1:fn:length(command.participant.organizationAssignedIdentifiers)},                            /* this is the initial count of the rows when the page is loaded  */
          path: "participant.organizationAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
          postProcessRowInsertion: function(object){
        	  	inputDateElementLocal="participant.organizationAssignedIdentifiers["+object.localIndex+"].startDate";
		        inputDateElementLink="participant.organizationAssignedIdentifiers["+object.localIndex+"].startDate-calbutton";
		        Calendar.setup(
		        {
		            inputField  : inputDateElementLocal,         // ID of the input field
		            ifFormat    : "%m/%d/%Y",    // the date format
		            button      : inputDateElementLink       // ID of the button
		        }
		                );
		        inputDateElementLocal="participant.organizationAssignedIdentifiers["+object.localIndex+"].endDate";
		        inputDateElementLink="participant.organizationAssignedIdentifiers["+object.localIndex+"].endDate-calbutton";
		        Calendar.setup(
		        {
		            inputField  : inputDateElementLocal,         // ID of the input field
		            ifFormat    : "%m/%d/%Y",    // the date format
		            button      : inputDateElementLink       // ID of the button
		        }
		                );
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
       	return (obj.name+" ("+obj.primaryIdentifier+")")
       },
        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
							hiddenField=inputElement.id.split("-")[0]+"-hidden";
							$(hiddenField).value=selectedChoice.id;
	}
   };
      
   var participantAutocompleterProps = {
           basename: "familyMember",
           populator: function(autocompleter, text) {
          	 ParticipantAjaxFacade.matchParticipants( text,0,function(values) {
                   autocompleter.setChoices(values)
               })
           },
           valueSelector: function(obj) {
           	return (obj.fullName +" ("+obj.primaryIdentifierValue+")" )
           },
            afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
   								hiddenField=inputElement.id.split("-")[0]+"-hidden";
    							$(hiddenField).value=selectedChoice.id;
		}
       };
      
    var familyMemberRowInserterProps = {
            add_row_division_id: "familyMembersTable", 	        /* this id belongs to element where the row would be appended to */
            skeleton_row_division_id: "dummy-familyMember",
            initialIndex: ${fn:length(command.participant.relatedTo)},                            /* this is the initial count of the rows when the page is loaded  */
            path: "participant.relatedTo",                               /* this is the path of the collection that holds the rows  */
            postProcessRowInsertion: function(object){
				        clonedRowInserter=Object.clone(participantAutocompleterProps);
						clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
						AutocompleterManager.registerAutoCompleter(clonedRowInserter);
				    },
		    onLoadRowInitialize: function(object, currentRowIndex){
				clonedRowInserter=Object.clone(participantAutocompleterProps);
				clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
				AutocompleterManager.registerAutoCompleter(clonedRowInserter);
		    }
        };
        AutocompleterManager.addAutocompleter(mrnAutocompleterProps);
        RowManager.addRowInseter(systemIdentifierRowInserterProps);
        RowManager.addRowInseter(organizationIdentifierRowInserterProps);
        RowManager.addRowInseter(familyMemberRowInserterProps);
ValidationManager.submitPostProcess= function(formElement, flag){	
	if(formElement.id!='command')
		return flag;
	if(!flag)
		return false;
		
	if(formElement.id="command"){
		flag=false;
		for(i=0 ; i<7 ; i++){
			if($('raceCodes'+i).checked){
				flag=true;
				break;
			}
		}
		if(!flag){
			ValidationManager.showError($("raceCodes"), "required")	
			return false;
		}
		if(compareDateWithToday($('participant.birthDate').value)==0)
			return true;
		alert("Birth Date cannot be greater than today's date");
		return false;
	}
}
function manageIdentifierRadio(element){
	$$("form .identifierRadios").each(function(e)
										{
											e.checked=false;
											var indicatorValue = $(e.id+"-hidden").value;
											$(e.id+"-hidden").value="false"
										}
									);
	$('organizationAssignedIdentifiers[0].primaryIndicator').value = false;
	element.checked=true;
	$(element.id+"-hidden").value="true";
}
function handleSaveSubjectDetailsAndReturnToRegistration(){
	$('participantId').value='${command.participant.id}';
	$('goToRegistration').value="true";
	$('command').submit();
}

</script>
</head>
<body>
<form:form method="post" name="command" id="command" cssClass="standard">
	<input type="hidden" name="goToRegistration" id="goToRegistration" value="false"/>
	<input type="hidden" name="participantId" id="participantId" value="${command.participant.id}"/>
<tags:tabFields tab="${tab}" />

<chrome:box title="${tab.shortTitle}">
<tags:instructions code="participant" />
<tags:errors path="*"/>
<chrome:division id="participant-details" title="Basic Details">
				<div class="leftpanel">
					<div class="row">
						<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.firstName"/></div>
						<div class="value"><form:input path="participant.firstName"
							cssClass="required validate-notEmpty" /></div>
					</div>
					<div class="row">
						<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.lastName"/></div>
						<div class="value"><form:input path="participant.lastName"
							cssClass="required validate-notEmpty" /></div>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.middleName"/></div>
						<div class="value"><form:input path="participant.middleName" /></div>
					</div>
					<div class="row">
						<div class="label"><fmt:message key="c3pr.common.maidenName"/></div>
						<div class="value"><form:input path="participant.maidenName" /></div>
					</div>
					<div class="row">
						<div class="label"><tags:requiredIndicator /><fmt:message key="participant.gender"/></div>
						<div class="value"><form:select path="participant.administrativeGenderCode"
							cssClass="required validate-notEmpty">
							<option value="">Please Select</option>
							<form:options items="${administrativeGenderCode}"
								itemLabel="desc" itemValue="code" />
							</form:select> 
						</div> 
					</div>
					<div class="row">
						<div class="label"><tags:requiredIndicator /><fmt:message key="participant.birthDate"/></div>
						<div class="value"><form:input path="participant.birthDate" cssClass="required validate-notEmpty&&DATE" /> (mm/dd/yyyy)&nbsp;</div>
					</div>
				</div>
				<div class="rightpanel">
					<div class="row">
						<div class="label"><tags:requiredIndicator /><fmt:message key="participant.ethnicity"/></div>
						<div class="value"><form:select path="participant.ethnicGroupCode"
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
                            	<c:forEach items="${raceCodes}" var="raceCode" varStatus="raceCodeStatus">
                            		<form:checkbox path="raceCodeHolderList[${raceCodeStatus.index}].raceCode" id="raceCodes${raceCodeStatus.index}" value="${raceCode.name}"/> ${raceCode.displayName}
                            	<br>
                            	</c:forEach>
		                    </td><td align="left" id="raceCodes" style="display:inline"/>
		                </tr>
						</table>
					</div>
				</div>
		</chrome:division>
		<chrome:division title="Familial Relationships">
			<table id="familyMembersTable" border="0"
					cellspacing="0" cellpadding="0" class="tablecontent">
				<tr id="hfamilyMember" <c:if test="${fn:length(command.participant.relatedTo)==0}"> style="display:none"</c:if>>
					<th><tags:requiredIndicator /><fmt:message key="participant.subjectName"/></th>
					<th><tags:requiredIndicator /><fmt:message key="participant.familialRelationship.name"/></th>
					<th></th>
				</tr>
					<c:forEach items="${command.participant.relatedTo}" varStatus="familyMemberStatus" var="familyMember">
						<c:set var="_identifier" value="(${command.participant.relatedTo[familyMemberStatus.index].secondaryParticipant.primaryIdentifierValue})" />
						<c:set var="_name" value="${command.participant.relatedTo[familyMemberStatus.index].secondaryParticipant.fullName}" />
						<tr
							id="familyMembersTable-${familyMemberStatus.index}">
							<td class="alt"><input type="hidden"
								id="familyMember${familyMemberStatus.index}-hidden"
								name="participant.relatedTo[${familyMemberStatus.index}].secondaryParticipant"
								value="${command.participant.relatedTo[familyMemberStatus.index].secondaryParticipant.id}" />
								<input type="hidden"
								id="relation-category-familyMember${familyMemberStatus.index}-hidden"
								name="participant.relatedTo[${familyMemberStatus.index}].category" value="FAMILIAL" />
							<input class="autocomplete validate-notEmpty" type="text"
								id="familyMember${familyMemberStatus.index}-input" size="38"
								value='<c:out value="${_name} ${_identifier}" />'/>
							<tags:indicator
								id="familyMember${familyMemberStatus.index}-indicator" /> 
							<div id="familyMember${familyMemberStatus.index}-choices"
								class="autocomplete"  style="display: none;"></div>
							</td>
							<td class="alt"><form:select
								path="participant.relatedTo[${familyMemberStatus.index}].name"
								cssClass="required validate-notEmpty">
								<option value="">Please Select</option>
								<form:options items="${familialRelationshipNames}" itemLabel="value"
									itemValue="key" />
							</form:select></td>
							<td class="alt"><a
								href="javascript:RowManager.deleteRow(familyMemberRowInserterProps,${familyMemberStatus.index},'${familyMember.id==null?'HC#':'ID#'}${familyMember.id==null?familyMember.hashCode:familyMember.id}');"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
						</tr>
					</c:forEach>
				</table>
			<div align="right">
			<tags:button type="button" color="blue" icon="add" value="Add Family Member" 
				onclick="$('hfamilyMember').show();javascript:RowManager.addRow(familyMemberRowInserterProps);" size="small"/>
			</div>
		</chrome:division>
		
		<chrome:division title="Primary Identifier">
		<tags:errors path="participant.primaryIdentifierValue"/>
         		<div id="mrnDetails">
					 <div class="leftpanel">
	                	 <div class="row">
									<c:set var="_code" value="" />
									<c:set var="_name" value="" />
									<c:if test="${fn:length(command.participant.organizationAssignedIdentifiers)>0}">				
									<c:set var="_code" value="(${command.participant.organizationAssignedIdentifiers[0].healthcareSite.primaryIdentifier})" />
									<c:set var="_name" value="${command.participant.organizationAssignedIdentifiers[0].healthcareSite.name}" />
									</c:if>
			                        <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.organization"/></div>
		                         	<div class="value">
			                        <c:choose>
										<c:when test="${c3pr:hasAllSiteAccess('UI_SUBJECT_UPDATE')}">
											<input type="hidden" id="mrnOrganization-hidden" name="participant.organizationAssignedIdentifiers[0].healthcareSite"
											value="${command.participant.organizationAssignedIdentifiers[0].healthcareSite.id}" />
											<input id="mrnOrganization-input" size="32" type="text" name="xyz" value='<c:out value="${_name} ${_code}" />' class="autocomplete validate-notEmpty" />
											<tags:hoverHint keyProp="subject.MRN.organization"/>
											<tags:indicator id="mrnOrganization-indicator" />
											<div id="mrnOrganization-choices" class="autocomplete" style="display:none;"></div>
										</c:when>
										<c:otherwise>
											<form:select path="participant.organizationAssignedIdentifiers[0].healthcareSite" cssClass="required validate-notEmpty" cssStyle="width: 350px;">
												<tags:userOrgOptions preSelectedSiteId="${command.participant.organizationAssignedIdentifiers[0].healthcareSite.id}" privilege="UI_SUBJECT_UPDATE"/>
											</form:select>
										</c:otherwise>
									</c:choose>
								    </div>
	                    </div>
						<div class="row">
			                        <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/></div>
			                        <div class="value"><input type="text" name="participant.organizationAssignedIdentifiers[0].value" 
									size="30" maxlength="33"
									value="${command.participant.organizationAssignedIdentifiers[0].value}" class="required validate-notEmpty&&HTML_SPECIAL_CHARS" />
									<tags:hoverHint keyProp="subject.MRN.value"/>
									<input type="hidden" name="participant.organizationAssignedIdentifiers[0].primaryIndicator" 
										id="organizationAssignedIdentifiers[0].primaryIndicator"value="true"/></div>
						</div>
					</div>

					 <div class="rightpanel">
						<div class="row">
								<div class="label"><fmt:message key="c3pr.common.identifierType"/></div>
								<div class="value">
								<form:select
									path="participant.organizationAssignedIdentifiers[0].type" cssClass="required validate-notEmpty"> 
									<form:options items="${orgIdentifiersTypeRefData}" itemLabel="desc" itemValue="code" />
								</form:select>
								</div>
						</div>
					</div>
          		</div>
		</chrome:division>
		
		<chrome:division title="Additional Identifiers">
			<table id="identifiersTable" border="0"
					cellspacing="0" cellpadding="0" class="tablecontent">
				<tr id="hOrganizationAssignedIdentifier" <c:if test="${fn:length(command.participant.identifiers) < 2}">style="display:none;"</c:if>>
					<th with="30%"><tags:requiredIndicator /><fmt:message key="c3pr.common.assigningAuthority"/><tags:hoverHint keyProp="identifier.organization"/></th>
						<th width="22%"><tags:requiredIndicator /><fmt:message key="c3pr.common.identifierType"/>
						<tags:hoverHint keyProp="identifier.type"/></th>
						<th width="16%"><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/><tags:hoverHint keyProp="identifier.value"/></th>
						<th width="11%"><fmt:message key="c3pr.common.startDate"/><tags:hoverHint keyProp="identifier.startDate"/></th>
						<th width="11%"><fmt:message key="c3pr.common.endDate"/><tags:hoverHint keyProp="identifier.endDate"/></th>
						<th width="11%"><fmt:message key="c3pr.common.primaryIndicator"/><tags:hoverHint keyProp="study.healthcareSite.primaryIndicator"/></th>
						<th width="2%"></th>
				</tr>
					<c:forEach items="${command.participant.organizationAssignedIdentifiers}" begin="1"
						varStatus="organizationStatus" var="orgId">
						<tr
							id="identifiersTable-${organizationStatus.index}">
							<c:set var="_code" value="" />
							<c:set var="_name" value="" />
							<c:set var="_code" value="(${command.participant.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.primaryIdentifier})" />
							<c:set var="_name" value="${command.participant.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.name}" />
							<td class="alt"><input type="hidden"
								id="healthcareSite${organizationStatus.index}-hidden"
								name="participant.organizationAssignedIdentifiers[${organizationStatus.index}].healthcareSite"
								value="${command.participant.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.id}" />
							<input class="autocomplete validate-notEmpty" type="text"
								id="healthcareSite${organizationStatus.index}-input" size="38"
								value='<c:out value="${_name} ${_code}" />'/>
							<tags:indicator
								id="healthcareSite${organizationStatus.index}-indicator" /> 
							<div id="healthcareSite${organizationStatus.index}-choices"
								class="autocomplete"  style="display: none;"></div>
							</td>
							<td class="alt"><form:select
								path="participant.organizationAssignedIdentifiers[${organizationStatus.index}].type" 
								cssClass="required validate-notEmpty">
								<option value="">Please Select</option>
								<form:options items="${orgIdentifiersTypeRefData}" itemLabel="desc"
									itemValue="code" />
							</form:select></td>
							<td class="alt"><form:input
								path="participant.organizationAssignedIdentifiers[${organizationStatus.index}].value"
								cssClass="required validate-notEmpty" /></td>
							<td align="left"><tags:dateInput path="participant.organizationAssignedIdentifiers[${organizationStatus.index}].startDate" 
			         			 size="11" />
			         		</td>
				            <td align="left"><tags:dateInput path="participant.organizationAssignedIdentifiers[${organizationStatus.index}].endDate" 
			         			 size="11" />
			         		</td>
				            <td>
								<form:hidden path="participant.organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator" id="identifier-org-${organizationStatus.index}-hidden"/>
								<input type="radio" class="identifierRadios" id="identifier-org-${organizationStatus.index}" onclick="manageIdentifierRadio(this);"
								<c:if test="${orgId.primaryIndicator}"> checked </c:if>/>
							</td>
							<td class="alt"><a
								href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,${organizationStatus.index},'${orgId.id==null?'HC#':'ID#'}${orgId.id==null?orgId.hashCode:orgId.id}');"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
						</tr>
					</c:forEach>
					<c:forEach items="${command.participant.systemAssignedIdentifiers}"
						varStatus="status" var="sysId">
						<c:choose>
							<c:when test="${sysId.systemName == 'C3PR' && sysId.type == 'SUBJECT_IDENTIFIER'}">
								<tr id="systemIdentifiersTable-${status.index}">
									<td class="value">${sysId.systemName}</td>
									<td class="value">Subject Identifier</td>
									<td class="value">${sysId.value}</td>
									<td class="value">${sysId.startDateStr}</td>
									<td class="value">${sysId.endDateStr}</td>
									<td>
										<form:hidden path="participant.systemAssignedIdentifiers[${status.index}].primaryIndicator" id="identifier-sys-${status.index}-hidden"/>
										<input type="radio" class="identifierRadios" id="identifier-sys-${status.index}" onclick="manageIdentifierRadio(this);"
										<c:if test="${sysId.primaryIndicator}"> checked </c:if>/>
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr id="systemIdentifiersTable-${status.index}">
									<td class="alt"><form:input
										path="participant.systemAssignedIdentifiers[${status.index}].systemName"
										cssClass="required validate-notEmpty" /></td>
									<td class="alt"><form:select
										path="participant.systemAssignedIdentifiers[${status.index}].type" 
										cssClass="required validate-notEmpty">
										<option value="">Please Select</option>
										<form:options items="${sysIdentifiersTypeRefData}" itemLabel="desc"
											itemValue="code" />
									</form:select></td>
									<td class="alt"><form:input
										path="participant.systemAssignedIdentifiers[${status.index}].value"
										cssClass="required validate-notEmpty" /></td>
									<td align="left"><tags:dateInput path="participant.systemAssignedIdentifiers[${status.index}].startDate" 
			         			 		size="11" />
			         				</td>
				            		<td align="left"><tags:dateInput path="participant.systemAssignedIdentifiers[${status.index}].endDate" 
			         			 		size="11" />
			         				</td>
									<td>
										<form:hidden path="participant.systemAssignedIdentifiers[${status.index}].primaryIndicator" id="identifier-sys-${status.index}-hidden"/>
										<input type="radio" class="identifierRadios" id="identifier-sys-${status.index}" onclick="manageIdentifierRadio(this);"
										<c:if test="${sysId.primaryIndicator}"> checked </c:if>/>
									</td>
									<td class="alt"><a
										href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,${status.index},'${sysId.id==null?'HC#':'ID#'}${sysId.id==null?sysId.hashCode:sysId.id}');"><img
										src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</table>
				<br>
				
		<div align="right">
			<tags:button type="button" color="blue" icon="add" value="Add Organization Assigned Identifier" 
				onclick="$('hOrganizationAssignedIdentifier').show();javascript:RowManager.addRow(organizationIdentifierRowInserterProps);" size="small"/>
			<tags:button type="button" color="blue" icon="add" value="Add System Assigned Identifier" 
				onclick="$('hOrganizationAssignedIdentifier').show();javascript:RowManager.addRow(systemIdentifierRowInserterProps);" size="small"/>
		</div>
	</chrome:division>
</chrome:box>

<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}">
	<jsp:attribute name="localButtons">
			<c:if test="${!empty fromCreateRegistration}">
			<tags:button type="button" color="blue" icon="back" value="Save & Return to Registration" onclick="handleSaveSubjectDetailsAndReturnToRegistration()" />
			</c:if>
	</jsp:attribute>
</tags:tabControls>
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
			name="participant.systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName" type="text"
			class="required validate-notEmpty" /></td>
		<td class="alt"><select
			id="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="participant.systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="required validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${sysIdentifiersTypeRefData}" var="id">
				<option value="${id.code}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td class="alt"><input
			id="systemAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="participant.systemAssignedIdentifiers[PAGE.ROW.INDEX].value" type="text"
			 class="required validate-notEmpty" /></td>
			 
		<td><input type="text" id="participant.systemAssignedIdentifiers[PAGE.ROW.INDEX].startDate" size="11"
                name="participant.systemAssignedIdentifiers[PAGE.ROW.INDEX].startDate" class="validate-DATE">
                <a href="#" id="participant.systemAssignedIdentifiers[PAGE.ROW.INDEX].startDate-calbutton">
                   <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
               </a>
		</td>
		<td><input type="text" id="participant.systemAssignedIdentifiers[PAGE.ROW.INDEX].endDate" size="11"
                name="participant.systemAssignedIdentifiers[PAGE.ROW.INDEX].endDate" class="validate-DATE">
                <a href="#" id="participant.systemAssignedIdentifiers[PAGE.ROW.INDEX].endDate-calbutton">
                   <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
               </a>
		</td>
		<td>
			<input type="hidden" name="participant.systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-hidden"/>
			<input type="radio" id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" class="identifierRadios"
			name="participant.systemAssignedIdentifiers.primaryIndicator-PAGE.ROW.INDEX" onclick="manageIdentifierRadio(this);"/>
		</td>
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
			name="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite" />
		<input class="autocomplete validate-notEmpty" type="text"
			id="healthcareSitePAGE.ROW.INDEX-input" size="38"
			value="${command.participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite.name}" />
		 <tags:indicator
			id="healthcareSitePAGE.ROW.INDEX-indicator" />
		<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete"  style="display: none;"></div>
		</td>

		<td class="alt"><select
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="required validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${orgIdentifiersTypeRefData}" var="id">
				<option value="${id.code}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td class="alt"><input
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].value" type="text""
			 class="required validate-notEmpty" /></td>
		<td><input type="text" id="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].startDate" size="11"
                name="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].startDate" class="validate-DATE">
		<a href="#" id="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].startDate-calbutton">
                   <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
               </a> 
        </td>
		<td><input type="text" id="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].endDate" size="11"
                name="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].endDate" class="validate-DATE"> <a href="#" id="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].endDate-calbutton">
                   <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
               </a>
		</td>
		<td>
			<input type="radio"	id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" class="identifierRadios"
			name="participant.organizationAssignedIdentifiers.primaryIndicator-PAGE.ROW.INDEX" onclick="manageIdentifierRadio(this);"/>
			<input type="hidden" name="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" 
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-hidden"/>
		</td>
		<td class="alt"><a
			href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,PAGE.ROW.INDEX,-1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

<div id="dummy-familyMember" style="display:none;">
<table>
	<tr>
		<td class="alt"> <input type="hidden"
			id="relation-category-familyMemberPAGE.ROW.INDEX-hidden"
			name="participant.relatedTo[PAGE.ROW.INDEX].category" value="FAMILIAL" />
			<input type="hidden" id="familyMemberPAGE.ROW.INDEX-hidden"
			name="participant.relatedTo[PAGE.ROW.INDEX].secondaryParticipant" />
		<input class="autocomplete validate-notEmpty" type="text"
			id="familyMemberPAGE.ROW.INDEX-input" size="38"
			value="${command.participant.relatedTo[PAGE.ROW.INDEX].secondaryParticipant.fullName}" />
		 <tags:indicator
			id="familyMemberPAGE.ROW.INDEX-indicator" />
		<div id="familyMemberPAGE.ROW.INDEX-choices" class="autocomplete"  style="display: none;"></div>
		</td>

		<td class="alt"><select
			id="familialRelationships[PAGE.ROW.INDEX].name"
			name="participant.relatedTo[PAGE.ROW.INDEX].name"
			class="required validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${familialRelationshipNames}" var="id">
				<option value="${id.key}">${id.value}</option>
			</c:forEach>
		</select></td>
		<td class="alt"><a
			href="javascript:RowManager.deleteRow(familyMemberRowInserterProps,PAGE.ROW.INDEX,-1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

</body>
</html>
