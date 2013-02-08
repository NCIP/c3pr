<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><registrationTags:htmlTitle registration="${studySubject}" /></title>
    
<%--<tags:includeScriptaculous />--%>
<tags:dwrJavascriptLink objects="StudyAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">

var personUserAutocompleterProps = {
        basename: "personUser",
        populator: function(autocompleter, text) {
        	StudyAjaxFacade.getAllStaffAssociatedToStudyAndStudyOrganizations( text,${command.studySubject.studySite.study.id},function(values) {
                autocompleter.setChoices(values)
            })
        },
        valueSelector: function(obj) {
        	return (obj.fullName +" ("+obj.assignedIdentifier+")" )
        },
         afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
								hiddenField=inputElement.id.split("-")[0]+"-hidden";
 								$(hiddenField).value=selectedChoice.id;
		}
    };
    
var notifiedStudyStaffAutocompleterProps = {
        basename: "notifiedStudyStaff",
        populator: function(autocompleter, text) {
        	StudyAjaxFacade.getAllStaffAssociatedToStudyAndStudyOrganizations( text,${command.studySubject.studySite.study.id},function(values) {
                autocompleter.setChoices(values)
            })
        },
        valueSelector: function(obj) {
        	return (obj.fullName +" ("+obj.assignedIdentifier+")" )
        },
         afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
								hiddenField=inputElement.id.split("-")[0]+"-hidden";
 								$(hiddenField).value=selectedChoice.id;
		}
    };
var notifiedStudyStaffInserterProps= {
        add_row_division_id: "notifiedStudyStaff",
        skeleton_row_division_id: "dummy-row-notifiedStudyStaff",
        initialIndex: ${fn:length(command.studySubject.correspondences[status.index].notifiedStudyPersonnel)},
        row_index_indicator: "NESTED.PAGE.ROW.INDEX",
        path: "correspondences[PAGE.ROW.INDEX].notifiedStudyPersonnel",
      	      	postProcessRowInsertion: function(object){
           		clonedRowInserter=Object.clone(notifiedStudyStaffAutocompleterProps);
           		clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
         			AutocompleterManager.registerAutoCompleter(clonedRowInserter);
           	 },
             	onLoadRowInitialize: function(object, currentRowIndex){
          		clonedRowInserter=Object.clone(notifiedStudyStaffAutocompleterProps);
         		clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
          		AutocompleterManager.registerAutoCompleter(clonedRowInserter);
             }
    };
var correspondenceRowInserterProps = {
	nested_row_inserter: notifiedStudyStaffInserterProps,
    add_row_division_id: "correspondenceTable", 	        /* this id belongs to element where the row would be appended to */
    skeleton_row_division_id: "dummy-row-correspondence",
    initialIndex: ${fn:length(command.studySubject.correspondences)},                            /* this is the initial count of the rows when the page is loaded  */
    path: "studySubject.correspondences",                            /* this is the path of the collection that holds the rows  */
  	postProcessRowInsertion: function(object){
  		clonedRowInserter=Object.clone(personUserAutocompleterProps);
  		clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
		AutocompleterManager.registerAutoCompleter(clonedRowInserter);
   	  	inputDateElementLocal="studySubject.correspondences["+object.localIndex+"].time";
    	inputDateElementLink="studySubject.correspondences["+object.localIndex+"].time-calbutton";
      	Calendar.setup(
	        {
	            inputField  : inputDateElementLocal,         // ID of the input field
	            ifFormat    : "%m/%d/%Y",    // the date format
	            button      : inputDateElementLink       // ID of the button
	        }
       	);
  	 },
   	onLoadRowInitialize: function(object, currentRowIndex){
		clonedRowInserter=Object.clone(personUserAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
		AutocompleterManager.registerAutoCompleter(clonedRowInserter);
    }
};
RowManager.addRowInseter(correspondenceRowInserterProps);
RowManager.registerRowInserters();

function manageAddNotifiedStudyStaffButton(box,row_id){
	var add_button_id = 'addNotifiedStudyStaffButton-' + row_id; 
	if(box.value == 'true'){
		$('addNotifiedStudyStaffButton-' + row_id).style.display='block';
	}else{
		$('addNotifiedStudyStaffButton-' + row_id).style.display='none';
	}
}

function sendNotification(id){
	$('notifiedCorrespondenceId').value=id;
	$('_target').name="_target2";
	$('command').submit();
}

</script>
</head>
<body>


<tags:tabForm tab="${tab}" flow="${flow}" displayErrors="false" willSave="true" formName="studySubjectCorrespondencesForm">
	<jsp:attribute name="singleFields">
	<input type="hidden" name="notifiedCorrespondenceId" id="notifiedCorrespondenceId" value=""/>
<table id="correspondenceTable" width="100%" border="0">
<tr></tr>
   <c:forEach items="${command.studySubject.correspondences}" varStatus="status" var="correspondence">
        <tr id="correspondenceTable-${status.index}">
        	 <script type="text/javascript">
                RowManager.getNestedRowInserter(correspondenceRowInserterProps,${status.index}).updateIndex(${fn:length(command.studySubject.correspondences[status.index].notifiedStudyPersonnel)});
            </script>
            <td>
      			<chrome:deletableDivision divTitle="correspondence-${status.index}" id="correspondence-${status.index}"
						title="${command.studySubject.correspondences[status.index].timeStr}" minimize="true" divIdToBeMinimized="correspondenceDiv-${status.index}"
						onclick="RowManager.deleteRow(correspondenceRowInserterProps,${status.index},'${correspondence.id==null?'HC#':'ID#'}${correspondence.id==null?correspondence.hashCode:correspondence.id}')">
					<div id="correspondenceDiv-${status.index}" style="display: none">
							<table width="100%" border="0">
							<tr>
							  <td valign="top" width="50%">
							      <table width="100%" border="0" cellspacing="4" cellpadding="2">
							      <tr>
							          <td align="right"><tags:requiredIndicator/><b><fmt:message key="registration.correspondence.type"/></b></td>
							          <td class="alt"><form:select	path="studySubject.correspondences[${status.index}].type" 
														cssClass="required validate-notEmpty">
														<option value="">Please Select</option>
														<form:options items="${correspondenceTypes}" itemLabel="value" itemValue="key" />
													 </form:select>
									 </td>
							      </tr>
							      <tr>
									<td align="right">
									  	<tags:requiredIndicator /><b><fmt:message key="c3pr.common.timezone"/></b> 
									</td>
							        <td class="alt"><form:select path="studySubject.correspondences[${status.index}].timeZone" 
														cssClass="required validate-notEmpty">
														<option value="">Please Select</option>
														<c:forEach items="${timeZonesRefData}" var="tz" varStatus="tzIndex">
		            										<form:option value="${tz.key}">${tz.key}</form:option>
		            									</c:forEach>
													 </form:select>
									 </td>
							      </tr>
							      <tr>
									<td align="right"><b><fmt:message key="c3pr.common.startTime"/></b></td>
									<td>
										<table>
											<tr>
												<td><form:select path="studySubject.correspondences[${status.index}].startTimeHours">
														<form:option value="">Hr</form:option>
														<form:options items="${hoursRefData}" itemLabel="value" itemValue="key" />
													</form:select>
												</td>
												<td><form:select path="studySubject.correspondences[${status.index}].startTimeMinutes">
														<form:option value="">Min</form:option>
														<form:options items="${minsRefData}" itemLabel="value" itemValue="key" />
													</form:select>
												</td>
												<td><form:select path="studySubject.correspondences[${status.index}].startTimeAmPm">
														<option value="">..</option>
														<form:options items="${amPmRefData}" itemLabel="value" itemValue="key" />
													</form:select>
												</td>
											  </tr>
										   </table>
										</td>
								  </tr>
								   <tr>
									<td align="right"><b><fmt:message key="c3pr.common.endTime"/></b></td>
									<td>
										<table>
											<tr>
												<td><form:select path="studySubject.correspondences[${status.index}].endTimeHours">
														<option value="">Hr</option>
														<form:options items="${hoursRefData}" itemLabel="value" itemValue="key" />
													</form:select>
												</td>
												<td><form:select path="studySubject.correspondences[${status.index}].endTimeMinutes">
														<option value="">Min</option>
														<form:options items="${minsRefData}" itemLabel="value" itemValue="key" />
													</form:select>
												</td>
												<td><form:select path="studySubject.correspondences[${status.index}].endTimeAmPm">
														<option value="">..</option>
														<form:options items="${amPmRefData}" itemLabel="value" itemValue="key" />
													</form:select>
												</td>
											  </tr>
										   </table>
										</td>
								  </tr>
								  <tr>
									<td align="right"><b><fmt:message key="registration.correspondence.personSpokenTo"/></b></td>
									<td class="alt"> 
											<input type="hidden" id="personUser${status.index}-hidden"
												name="studySubject.correspondences[${status.index}].personSpokenTo" 
												value="${command.studySubject.correspondences[status.index].personSpokenTo.id}" />
											<input class="autocomplete" type="text"
												id="personUser${status.index}-input" size="38"
												value="${command.studySubject.correspondences[status.index].personSpokenTo.fullName}" />
									 		<tags:indicator	id="personUser${status.index}-indicator" />
											<div id="personUser${status.index}-choices" class="autocomplete"  style="display: none;"></div>
									</td>
								  </tr>
							      <tr>
							              <td align="right"><b><fmt:message key="registration.correspondence.text"/></b> </td>
							              <td><form:textarea path="studySubject.correspondences[${status.index}].text" rows="4" cols="50" cssClass="validate-MAXLENGTH2000"/></td>
							  	 </tr>
						      </table>
						  	</td>
						  	<td valign="top">
						      <table width="100%" border="0" cellspacing="4" cellpadding="2">
							      <tr>
								          <td align="right"> 
								          	<tags:requiredIndicator /><b><fmt:message key="registration.correspondence.purpose"/></b>
								          </td>
								          <td class="alt"><form:select	path="studySubject.correspondences[${status.index}].purpose" 
															cssClass="required validate-notEmpty">
															<option value="">Please Select</option>
															<form:options items="${correspondencePurposes}" itemLabel="value" itemValue="key" />	
														   </form:select>
											</td>
								  </tr>
								  <tr>
									<td align="right">
									  	<tags:requiredIndicator /><b><fmt:message key="registration.correspondence.time"/></b> 
									</td>
							        <td align="left"><tags:dateInput path="studySubject.correspondences[${status.index}].time" size="11" /> </td>
							      </tr>
							      <tr>
							          <td align="right"><b><fmt:message key="registration.correspondence.action"/></b> </td>
							          <td class="alt"><form:textarea path="studySubject.correspondences[${status.index}].action" rows="2" cols="50"/></td>
							       </tr>
							       <tr>
							          <td align="right"><b><fmt:message key="registration.correspondence.notes"/></b></td>
							          <td><form:textarea path="studySubject.correspondences[${status.index}].notes" rows="4" cols="50" cssClass="validate-MAXLENGTH2000"/></td>
							       </tr>
							       <tr>
							          <td align="right"><tags:requiredIndicator /><b><fmt:message key="registration.correspondence.resolved"/></b> </td>
							          <td align="left">
							            <form:select id="studySubject.correspondences[${status.index}].resolved" path="studySubject.correspondences[${status.index}].resolved"
													cssClass="required validate-notEmpty">
								                    <option value="">Please Select</option>
								                    <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
										</form:select>
							          </td>
							      </tr>
								  <tr>
							          <td align="right"><tags:requiredIndicator /><b><fmt:message key="registration.correspondence.followUpNeeded"/></b> </td>
							          <td align="left">
							            <form:select id="studySubject.correspondences[${status.index}].followUpNeeded" 
							            	path="studySubject.correspondences[${status.index}].followUpNeeded" 
							            	onchange="manageAddNotifiedStudyStaffButton(this,${status.index})"
													cssClass="required validate-notEmpty">
								                    <option value="">Please Select</option>
								                    <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
										</form:select>
							          </td>
							      </tr>
							      <tr>
									<td colspan="3" align="left">
									<table id="notifiedStudyStaff">
										<tr id="h-${status.index}">
											<th></th>
										</tr>
										<c:forEach items="${correspondence.notifiedStudyPersonnel}" var="notifiedStudyPerson" varStatus="notifiedStudyPersonStatus">
								            <tr id="notifiedStudyStaff-${notifiedStudyPersonStatus.index}">
											    <td align="right"><b><fmt:message key="registration.correspondence.notifiedStudyStaff"/></b></td>
												<td class="alt"> 
														<input type="hidden" id="notifiedStudyStaff${notifiedStudyPersonStatus.index}-hidden"
															name="studySubject.correspondences[${status.index}].notifiedStudyPersonnel[${notifiedStudyPersonStatus.index}]" 
															value="${command.studySubject.correspondences[status.index].notifiedStudyPersonnel[notifiedStudyPersonStatus.index].id}"/>
														<input class="autocomplete" type="text" class="required validate-notEmpty"
															id="notifiedStudyStaff${notifiedStudyPersonStatus.index}-input" size="38"
															value="${command.studySubject.correspondences[status.index].notifiedStudyPersonnel[notifiedStudyPersonStatus.index].fullName}" />
												 		<tags:indicator	id="notifiedStudyStaff${notifiedStudyPersonStatus.index}-indicator" />
														<div id="notifiedStudyStaff${notifiedStudyPersonStatus.index}-choices" class="autocomplete" style="display:none"></div>
												</td>
												<td valign="top" align="left"><a
													href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(correspondenceRowInserterProps,${status.index}),${notifiedStudyPersonStatus.index},-1);"><img
													src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
								            </tr>
							            </c:forEach>
									</table>
									<div id="addNotifiedStudyStaffButton-${status.index}" 
						     			style="${command.studySubject.correspondences[status.index].followUpNeeded != 'true'?'display:none':''}" align="right">
										<tags:button id="addNotifiedStudyStaff-${status.index}" type="button" color="blue" icon="add" value="Add Study Staff to Notify"
										onclick="javascript:RowManager.addRow(RowManager.getNestedRowInserter(correspondenceRowInserterProps,${status.index}));" size="small"/>
									</div>
									<br>
									</td>
							  	  </tr>
						     </table>
						  </td>
						</tr>
						</table>
						<div id="sendNotificationButton-${correspondence.id}" align="right">
							<tags:button id="sendNotificationButton-${status.index}" type="button" color="blue" icon="mail" value="Notify Study Staff"
							onclick="javascript:sendNotification(${correspondence.id});" size="small"/>
						</div>
				</div>
			</chrome:deletableDivision>
		</td>
	</tr>
</c:forEach>
</table>
<div align="left">
	<tags:button type="button" color="blue" icon="add" value="Add Correspondence" 
				onclick="javascript:RowManager.addRow(correspondenceRowInserterProps);" size="small"/>
</div>
</jsp:attribute>
</tags:tabForm>


<div id="dummy-row-correspondence" style="display: none">
<table width="100%">
	<tr valign="top">
		<td><chrome:deletableDivision
			divTitle="correspondence-PAGE.ROW.INDEX"
			id="correspondenceDiv-PAGE.ROW.INDEX" title="New Correspondence"
			onclick="RowManager.deleteRow(correspondenceRowInserterProps,PAGE.ROW.INDEX,-1)">
			<table style="border: 0px red dotted;" width="100%">
				<tr>
					<td valign="top" width="50%">

					<table width="100%" border="0" cellspacing="4" cellpadding="2">
						<tr>
							<td align="right"><tags:requiredIndicator /><b><fmt:message key="registration.correspondence.type"/></b></td>
							<td><select id="studySubject.correspondences[PAGE.ROW.INDEX].type"
									name="studySubject.correspondences[PAGE.ROW.INDEX].type"
									class="required validate-notEmpty">
									<option value="">Please Select</option>
									<c:forEach items="${correspondenceTypes}" var="id">
										<option value="${id.key}">${id.value}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right"><b><fmt:message key="c3pr.common.timezone"/></b></td>
							<td><select id="studySubject.correspondences[PAGE.ROW.INDEX].timeZone"
									name="studySubject.correspondences[PAGE.ROW.INDEX].timeZone"
									class="required validate-notEmpty">
									<option value="">Please Select</option>
									<c:forEach items="${timeZonesRefData}" var="id">
										<option value="${id.key}">${id.key}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right"><b><fmt:message key="c3pr.common.startTime"/></b></td>
							<td>
								<table>
									<tr>
										
										<td><select id="studySubject.correspondences[PAGE.ROW.INDEX].startTimeHours"
												name="studySubject.correspondences[PAGE.ROW.INDEX].startTimeHours">
												<option value="">Hr</option>
												<c:forEach items="${hoursRefData}" var="id">
													<option value="${id.key}">${id.value}</option>
												</c:forEach>
											</select>
										</td>
										<td><select id="studySubject.correspondences[PAGE.ROW.INDEX].startTimeMinutes"
												name="studySubject.correspondences[PAGE.ROW.INDEX].startTimeMinutes">
												<option value="">Min</option>
												<c:forEach items="${minsRefData}" var="id">
													<option value="${id.key}">${id.value}</option>
												</c:forEach>
											</select>
										</td>
										<td><select id="studySubject.correspondences[PAGE.ROW.INDEX].startTimeAmPm"
												name="studySubject.correspondences[PAGE.ROW.INDEX].startTimeAmPm">
												<option value="">..</option>
												<c:forEach items="${amPmRefData}" var="id">
													<option value="${id.key}">${id.value}</option>
												</c:forEach>
											</select>
										</td>
									  </tr>
								   </table>
								</td>
						</tr>
						<tr>
							<td align="right"><b><fmt:message key="c3pr.common.endTime"/></b></td>
							<td>
								<table>
									<tr>
										
										<td><select id="studySubject.correspondences[PAGE.ROW.INDEX].endTimeHours"
												name="studySubject.correspondences[PAGE.ROW.INDEX].endTimeHours">
												<option value="">Hr</option>
												<c:forEach items="${hoursRefData}" var="id">
													<option value="${id.key}">${id.value}</option>
												</c:forEach>
											</select>
										</td>
										<td><select id="studySubject.correspondences[PAGE.ROW.INDEX].endTimeMinutes"
												name="studySubject.correspondences[PAGE.ROW.INDEX].endTimeMinutes">
												<option value="">Min</option>
												<c:forEach items="${minsRefData}" var="id">
													<option value="${id.key}">${id.value}</option>
												</c:forEach>
											</select>
										</td>
										<td><select id="studySubject.correspondences[PAGE.ROW.INDEX].endTimeAmPm"
												name="studySubject.correspondences[PAGE.ROW.INDEX].endTimeAmPm">
												<option value="">..</option>
												<c:forEach items="${amPmRefData}" var="id">
													<option value="${id.key}">${id.value}</option>
												</c:forEach>
											</select>
										</td>
									  </tr>
								   </table>
								</td>
						</tr>
						<tr>
							<td align="right"><b><fmt:message key="registration.correspondence.personSpokenTo"/></b></td>
							<td class="alt"> 
									<input type="hidden" id="personUserPAGE.ROW.INDEX-hidden"
										name="studySubject.correspondences[PAGE.ROW.INDEX].personSpokenTo" />
									<input class="autocomplete" type="text"
										id="personUserPAGE.ROW.INDEX-input" size="38"
										value="${command.studySubject.correspondences[PAGE.ROW.INDEX].personSpokenTo.fullName}" />
							 		<tags:indicator	id="personUserPAGE.ROW.INDEX-indicator" />
									<div id="personUserPAGE.ROW.INDEX-choices" class="autocomplete" style="display:none"></div>
							</td>
						</tr>
						<tr>
								<td align="right"><b><fmt:message key="registration.correspondence.text"/></b></td>
								<td><textarea name="studySubject.correspondences[PAGE.ROW.INDEX].text"
									rows="4" cols="50" class="validate-MAXLENGTH2000"></textarea></td>
						</tr>
					</table>

					</td>
					<td valign="top">
						<table width="100%" border="0" cellspacing="4" cellpadding="2">
							<tr>
								<td align="right"><tags:requiredIndicator /><b><fmt:message key="registration.correspondence.purpose"/></b></td>
								<td><select id="studySubject.correspondences[PAGE.ROW.INDEX].purpose"
									name="studySubject.correspondences[PAGE.ROW.INDEX].purpose"
									class="required validate-notEmpty">
									<option value="">Please Select</option>
									<c:forEach items="${correspondencePurposes}" var="id">
										<option value="${id.key}">${id.value}</option>
									</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right"><tags:requiredIndicator /><b><fmt:message key="registration.correspondence.time"/></b></td>
								<td><input type="text" id="studySubject.correspondences[PAGE.ROW.INDEX].time" size="11"
						                name="studySubject.correspondences[PAGE.ROW.INDEX].time" class="required validate-notEmpty$$DATE"
						                onkeyup="updateName('correspondence-PAGE.ROW.INDEX', 'Correspondence: ' + this.value);">
										<a href="#" id="studySubject.correspondences[PAGE.ROW.INDEX].time-calbutton">
						                   <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0"/>
						               </a> 
						        </td>
							</tr>
							<tr>
								<td align="right"><b><fmt:message key="registration.correspondence.action"/></b></td>
								<td><textarea id="studySubject.correspondences[PAGE.ROW.INDEX].action" rows="2" cols="50"
										name="studySubject.correspondences[PAGE.ROW.INDEX].action"></textarea>
								</td>
							</tr>
							<tr>
								<td align="right"><b><fmt:message key="registration.correspondence.notes"/></b></td>
								<td><textarea name="studySubject.correspondences[PAGE.ROW.INDEX].notes"
								rows="4" cols="50" class="validate-MAXLENGTH2000"></textarea></td>
							</tr>
							<tr>
						          <td align="right"><tags:requiredIndicator /><b><fmt:message key="registration.correspondence.resolved"/></b> </td>
						          <td align="left">
						            <select id="studySubject.correspondences[PAGE.ROW.INDEX].resolved" name="studySubject.correspondences[PAGE.ROW.INDEX].resolved"
												class="required validate-notEmpty">
							                    <option value="">Please Select</option>
							                    <option value="true">Yes</option>
												<option value="false">No</option>
									</select>
						          </td>
							</tr>
							<tr>
						          <td align="right"><tags:requiredIndicator /><b><fmt:message key="registration.correspondence.followUpNeeded"/></b> </td>
						          <td align="left">
						            <select id="studySubject.correspondences[PAGE.ROW.INDEX].followUpNeeded" name="studySubject.correspondences[PAGE.ROW.INDEX].followUpNeeded"
												class="required validate-notEmpty" onchange="manageAddNotifiedStudyStaffButton(this,PAGE.ROW.INDEX)">
							                    <option value="">Please Select</option>
							                    <option value="true">Yes</option>
												<option value="false">No</option>
									</select>
						          </td>
							</tr>
					      <tr>
								<td colspan="3" align="left">
								<table id="notifiedStudyStaff">
									<tr id="h-PAGE.ROW.INDEX">
									</tr>
								</table>
								<br>
								</td>
						  </tr>
						</table>
						<div id="addNotifiedStudyStaffButton-PAGE.ROW.INDEX" style="display:none" align="right">
									<tags:button id="addNotifiedStudyStaff-PAGE.ROW.INDEX" type="button" color="blue" icon="add" value="Add Study Staff to Notify"
									onclick="javascript:RowManager.addRow(RowManager.getNestedRowInserter(correspondenceRowInserterProps,PAGE.ROW.INDEX));" size="small"/>
						</div>
					</td>
				</tr>
			</table>
		</chrome:deletableDivision></td>
	</tr>
</table>
</div>

<div id="dummy-row-notifiedStudyStaff" style="display: none">
	<table>
		<tr>
			<td align="right"><b><fmt:message key="registration.correspondence.notifiedStudyStaff"/></b></td>
			<td class="alt"> 
					<input type="hidden" id="notifiedStudyStaffNESTED.PAGE.ROW.INDEX-hidden"
						name="studySubject.correspondences[PAGE.ROW.INDEX].notifiedStudyPersonnel[NESTED.PAGE.ROW.INDEX]" />
					<input class="autocomplete" type="text" class="required validate-notEmpty"
						id="notifiedStudyStaffNESTED.PAGE.ROW.INDEX-input" size="38"
						value="${command.studySubject.correspondences[PAGE.ROW.INDEX].notifiedStudyPersonnel[NESTED.PAGE.ROW.INDEX].fullName}" />
			 		<tags:indicator	id="notifiedStudyStaffNESTED.PAGE.ROW.INDEX-indicator" />
					<div id="notifiedStudyStaffNESTED.PAGE.ROW.INDEX-choices" class="autocomplete" style="display:none"></div>
			</td>
			<td valign="top" align="left"><a
				href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(correspondenceRowInserterProps,PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX,-1);"><img
				src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		</tr>
	</table>

</div>


</body>
</html>
