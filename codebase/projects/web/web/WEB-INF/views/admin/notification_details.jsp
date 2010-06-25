<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
<html>
	<head> 
	<tags:dwrJavascriptLink objects="UserAjaxFacade"/>   
	<tags:dwrJavascriptLink objects="OrganizationAjaxFacade"/>
	<style type="text/css">
		.divider{
	  			border-left-color: black;
	  			border-left-style: solid;
	  			border-left-width: 1px;
		}
	</style>
	
	<script language="JavaScript" type="text/JavaScript">
			var roleRowInserterProps= {
	            add_row_division_id: "table2",
	            skeleton_row_division_id: "dummy-roleRow",
	            initialIndex: ${fn:length(command.healthcareSite.plannedNotifications[nStatus.index].roleBasedRecipient)},
	            softDelete: ${softDelete == 'true'},
	            row_index_indicator: "SECONDARY.NESTED.PAGE.ROW.INDEX",
	            path: "healthcareSite.plannedNotifications[PAGE.ROW.INDEX].roleBasedRecipient"
	       };
	        var emailRowInserterProps= {
	            add_row_division_id: "table1",
	            skeleton_row_division_id: "dummy-emailRow",
	            initialIndex: ${fn:length(command.healthcareSite.plannedNotifications[nStatus.index].userBasedRecipient)},
	            softDelete: ${softDelete == 'true'},
	            row_index_indicator: "NESTED.PAGE.ROW.INDEX",
	            path: "healthcareSite.plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient",
	            postProcessRowInsertion: function(object){
			       clonedRowInserter=Object.clone(userEmailAutocompleterProps);
				   clonedRowInserter.basename=clonedRowInserter.basename + "[" +object.parent_row_index + "][" +object.localIndex + "]";
				   AutocompleterManager.registerAutoCompleter(clonedRowInserter);
			   },
			   onUpdateIndex: function(object, currentRowIndex){
				   	for(rowIndex=0 ; rowIndex<currentRowIndex ; rowIndex++){
						clonedRowInserter1=Object.clone(userEmailAutocompleterProps);
						clonedRowInserter1.basename=clonedRowInserter1.basename+ "[" +object.parent_row_index + "][" +rowIndex + "]";
						AutocompleterManager.registerAutoCompleter(clonedRowInserter1);
				   	}
		       }
	        };
	        var contactMechanismRowInserterProps= {
	            add_row_division_id: "table3",
	            skeleton_row_division_id: "dummy-cmRow",
	            initialIndex: ${fn:length(command.healthcareSite.plannedNotifications[nStatus.index].contactMechanismBasedRecipient)},
	            softDelete: ${softDelete == 'true'},
	            row_index_indicator: "TERTIARY.NESTED.PAGE.ROW.INDEX",
	            path: "healthcareSite.plannedNotifications[PAGE.ROW.INDEX].contactMechanismBasedRecipient"
	       };
	        var notificationRowInserterProps = {
	                nested_row_inserter: emailRowInserterProps,
	                secondary_nested_row_inserter: roleRowInserterProps,
	                tertiary_nested_row_inserter: contactMechanismRowInserterProps,
	                add_row_division_id: "notification",
	                skeleton_row_division_id: "dummy-notification",
	                initialIndex: ${fn:length(command.healthcareSite.plannedNotifications)},
	                softDelete: true,
	                path: "healthcareSite.plannedNotifications"
	        };

	        var sponsorSiteAutocompleterProps = {
	                basename: "healthcareSite",
	                populator: function(autocompleter, text) {
	                	$('healthcareSite-indicator').style.display='';
	                    OrganizationAjaxFacade.matchHealthcareSites(text,function(values) {
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
	        								hiddenField=sponsorSiteAutocompleterProps.basename+"-hidden"
	    	    							$(hiddenField).value=selectedChoice.id;
	    	    							$('healthcareSite-indicator').style.display='none';
	    	    							$('hcs_id').value=selectedChoice.id;
	    	    							$('testGetForm').submit();
	    			 }
	            }
	            
	        AutocompleterManager.addAutocompleter(sponsorSiteAutocompleterProps);
	        
	        RowManager.addRowInseter(notificationRowInserterProps);
	        RowManager.registerRowInserters();
	
		var userEmailAutocompleterProps = {
            basename: "userEmail",
            populator: function(autocompleter, text) {
                UserAjaxFacade.matchNameAndEmail(text, document.getElementById('assignedIdentifier').value,  function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
            	if(obj.uniqueIdentifier != null){
            		image = '&nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="NCI data" width="17" height="16" border="0" align="middle"/>';
            	} else {
            		image = '';
            	}
            	
                return (obj.firstName +" " +obj.lastName + " (" +obj.email+ ") " + image)
            },
            afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
	    							hiddenField1=inputElement.id.split("-")[0]+"-hidden";
	    							$(hiddenField1).value=selectedChoice.email;
	    							hiddenField2=inputElement.id.split("-")[0]+"-hiddenResearchStaff";
	    							$(hiddenField2).value=selectedChoice.id;
			 }
        }         
	
		var win;
		var currentMessageIndex;
	     	
	     function showMessageBody(index){
	     	currentMessageIndex = index;
	     	if(win == null){
	     		win = new Window({className: "dialog", width:550, height:550, zIndex: 100, resizable: true, title: "Email Message", 
	        	showEffect:Effect.BlindDown, hideEffect: Effect.BlindUp, draggable:true, wiredDrag: true}); 
	        	win.setContent('emailMessageDetails', true, true);
	     	}else {
			    //$('plannedNotifications.title').value = $('plannedNotifications['+currentMessageIndex+'].title').value;
	     		//$('plannedNotifications.message').value = $('plannedNotifications['+currentMessageIndex+'].message').value;
	     	}
	     	$('plannedNotifications.title').value = $('plannedNotifications['+currentMessageIndex+'].title').value;
     		$('plannedNotifications.message').value = $('plannedNotifications['+currentMessageIndex+'].message').value;
	     	win.showCenter(true);
	     	updateSubvars(index);
	     }   
	     
	     function updateSubvars(index){
        	var elementId = "plannedNotifications[" + index + "].eventName";
          	var stringValue = $(elementId).options[$(elementId).selectedIndex].value;
			var subVarElmt = $("subVar");
			if(stringValue == 'NEW_STUDY_SAVED_EVENT'){
				for(i=0;i<subVarElmt.length;i++){
					subVarElmt.options[i].disabled = "disabled";
				}
				subVarElmt.options[1].disabled = "";
				subVarElmt.options[3].disabled = "";
				subVarElmt.options[4].disabled = "";
			} else if(stringValue == 'STUDY_STATUS_CHANGED_EVENT'){
				for(i=0;i<subVarElmt.length;i++){
					subVarElmt.options[i].disabled = "disabled";
				}
				subVarElmt.options[1].disabled = "";
				subVarElmt.options[3].disabled = "";
				subVarElmt.options[4].disabled = "";
			} else if(stringValue == 'NEW_STUDY_SITE_SAVED_EVENT'){
				for(i=0;i<subVarElmt.length;i++){
					subVarElmt.options[i].disabled = "disabled";
				}
				subVarElmt.options[2].disabled = "";
				subVarElmt.options[3].disabled = "";
				subVarElmt.options[4].disabled = "";
			} else if(stringValue == 'STUDY_SITE_STATUS_CHANGED_EVENT'){
				for(i=0;i<subVarElmt.length;i++){
					subVarElmt.options[i].disabled = "disabled";
				}
				subVarElmt.options[2].disabled = "";
				subVarElmt.options[3].disabled = "";
				subVarElmt.options[4].disabled = "";
			} else if(stringValue == 'NEW_REGISTRATION_EVENT'){
				for(i=0;i<subVarElmt.length;i++){
					subVarElmt.options[i].disabled = "disabled";
				}
				subVarElmt.options[3].disabled = "";
				subVarElmt.options[4].disabled = "";
				subVarElmt.options[5].disabled = "";
				subVarElmt.options[6].disabled = "";
				
			} else if(stringValue == 'REGISTATION_STATUS_CHANGE'){
				for(i=0;i<subVarElmt.length;i++){
					subVarElmt.options[i].disabled = "disabled";
				}
				subVarElmt.options[3].disabled = "";
				subVarElmt.options[4].disabled = "";
				subVarElmt.options[5].disabled = "";
				subVarElmt.options[6].disabled = "";
			} else if(stringValue == 'SUBJECT_REMOVED_OFF_STUDY'){
				for(i=0;i<subVarElmt.length;i++){
					subVarElmt.options[i].disabled = "disabled";
				}
				subVarElmt.options[3].disabled = "";
				subVarElmt.options[4].disabled = "";
				subVarElmt.options[5].disabled = "";
				subVarElmt.options[6].disabled = "";
			} else if(stringValue == 'STUDY_ACCRUAL_EVENT'){
				for(i=0;i<subVarElmt.length;i++){
					subVarElmt.options[i].disabled = "disabled";
				}
				subVarElmt.options[3].disabled = "";
				subVarElmt.options[4].disabled = "";
				subVarElmt.options[7].disabled = "";
				subVarElmt.options[9].disabled = "";
			} else if(stringValue == 'STUDY_SITE_ACCRUAL_EVENT'){
				for(i=0;i<subVarElmt.length;i++){
					subVarElmt.options[i].disabled = "disabled";
				}
				subVarElmt.options[3].disabled = "";
				subVarElmt.options[4].disabled = "";
				subVarElmt.options[8].disabled = "";
				subVarElmt.options[10].disabled = "";
			} else if(stringValue == 'NEW_REGISTRATION_EVENT_REPORT'){
				for(i=0;i<subVarElmt.length;i++){
					subVarElmt.options[i].disabled = "disabled";
				}
				subVarElmt.options[3].disabled = "";
				subVarElmt.options[4].disabled = "";
				subVarElmt.options[5].disabled = "";
				subVarElmt.options[6].disabled = "";
			} else if(stringValue == 'MULTISITE_REGISTRATION_FAILURE'){
				for(i=0;i<subVarElmt.length;i++){
					subVarElmt.options[i].disabled = "disabled";
				}
				subVarElmt.options[3].disabled = "";
				subVarElmt.options[4].disabled = "";
				subVarElmt.options[5].disabled = "";
				subVarElmt.options[6].disabled = "";
				
			}  else {
				for(i=0;i<subVarElmt.length;i++){
					subVarElmt.options[i].disabled = "";
				}
			}
        }
        
	     function updateMessage(index){
	     	$('plannedNotifications['+currentMessageIndex+'].title').value = $('plannedNotifications.title').value;
	     	$('plannedNotifications['+currentMessageIndex+'].message').value = $('plannedNotifications.message').value;
	     	win.close();
	     	//$('emailMessageDetails-'+index).innerHTML = win.getContent().innerHTML;
	     }
	
		function insertAtCursor() {
			var myField = document.getElementById('plannedNotifications.message');
			var myValue = document.getElementById('subVar');
			//IE support
			if (document.selection) {
				myField.focus();
				//in effect we are creating a text range with zero
				//length at the cursor location and replacing it
				//with myValue
				sel = document.selection.createRange();
				sel.text = "$" + "{" + myValue.value + "}";
			}
			//Mozilla/Firefox/Netscape 7+ support
			else if (myField.selectionStart == '0' || myField.selectionStart == '0') {
				//Here we get the start and end points of the
				//selection. Then we create substrings up to the
				//start of the selection and from the end point
				//of the selection to the end of the field value.
				//Then we concatenate the first substring, myValue,
				//and the second substring to get the new value.
				var startPos = myField.selectionStart;
				var endPos = myField.selectionEnd;
				myField.value = myField.value.substring(0, startPos) + "$" + "{" + myValue.value + "}" + myField.value.substring(endPos, myField.value.length);
			} else {
				myField.value +=  "$" + "{" + myValue.value + "}";
			}
		}
		
		
		function displayAccrualField(index){
			var studyLabel = document.getElementById('studyAccrual[' + index + ']label');
			var studySiteLabel = document.getElementById('studySiteAccrual[' + index + ']label');
			var studyValue = document.getElementById('studyAccrual[' + index + ']value');
			var studySiteValue = document.getElementById('studySiteAccrual[' + index + ']value');
			
			var event = document.getElementById('plannedNotifications[' + index + '].eventName');
			if(event.value == 'STUDY_ACCRUAL_EVENT'){
				Effect.CloseDown(studySiteLabel.id);
				Effect.CloseDown(studySiteValue.id);
				
				Effect.OpenUp(studyLabel.id);
				Effect.OpenUp(studyValue.id);
			} else if(event.value == 'STUDY_SITE_ACCRUAL_EVENT'){
				Effect.CloseDown(studyLabel.id);
				Effect.CloseDown(studyValue.id);
				
				Effect.OpenUp(studySiteLabel.id);
				Effect.OpenUp(studySiteValue.id);
			} else {
				Effect.CloseDown(studySiteLabel.id);
				Effect.CloseDown(studySiteValue.id);
				Effect.CloseDown(studyLabel.id);
				Effect.CloseDown(studyValue.id);
			}	
		}
		
		Effect.OpenUp = function(element) {
            element = $(element);
            new Effect.BlindDown(element, arguments[1] || {});
        }

        Effect.CloseDown = function(element) {
            element = $(element);
            new Effect.BlindUp(element, arguments[1] || {});
        }
        
        //This method disables/enables frequency and message textarea based on whether the selected event is report based.
        function runReportBasedLogic(index){
        	var frequencyElement = document.getElementById("plannedNotifications[" + index + "].frequency");
        	var eventElement = document.getElementById("plannedNotifications[" + index + "].eventName");
        	var messageElement = document.getElementById("plannedNotifications[" + index + "].message");

        	if(eventElement.value == 'NEW_REGISTRATION_EVENT_REPORT'){
				//disable message fields and enable frequency element
				frequencyElement.disabled = false;
				if(frequencyElement.value == 'IMMEDIATE'){
					frequencyElement.options[0].selected = true;
				} else {
					//messageElement.disabled = true;
				}
				messageElement.disabled = true;
				frequencyElement.options[3].disabled = true;
        	} else {
        		//set frequency to IMMEDIATE and disable it and enable message element.
        		frequencyElement.options[3].selected = true;
        		frequencyElement.value= 'IMMEDIATE';
        		frequencyElement.disabled = true;
        		messageElement.disabled = false;
        	}
        }
        
        function updateName(divID, index) {
        //$('plannedNotifications[0].eventName').options[$('plannedNotifications[0].eventName').selectedIndex].text;
          var elementId = "plannedNotifications[" + index + "].eventName";
          var stringValue = 'Notification: ' + $(elementId).options[$(elementId).selectedIndex].text;
          if ($(divID)) {
              $(divID).innerHTML = stringValue;
          }
        }

        function testGet(){
        	$('testGetForm').submit();
            
        }
	</script> 
	</head>
	
	<body>	
	<form action="createNotification" method="get" id="testGetForm">
		<input type="hidden" name="organization" id="hcs_id" value="16831"/>
	</form>
	
	<tags:basicFormPanelBox tab="${tab}" flow="${flow}" title="Notifications" action="createNotification">
		<input type="hidden" name="_action" value="">
		<input type="hidden" name="_selected" value="">
		<input type="hidden" name="_finish" value="true">
		<input type="hidden" id="assignedIdentifier" name="assignedIdentifier" value="${assignedIdentifier}" />
	<tags:instructions code="notification_details" />	
			<div class="row">
                <div class="label">
                    <fmt:message key="c3pr.common.organization"/>
                </div>
                <div class="value">
                	<input type="hidden" id="healthcareSite-hidden" name="healthcareSite" value="${command.healthcareSite.id}" /> 
                	<input id="healthcareSite-input" size="60" type="text" name="xyz" value="${command.healthcareSite.name} (${command.healthcareSite.primaryIdentifier})" class="autocomplete validate-notEmpty" /> 
                    <tags:hoverHint keyProp="notification.organization"/>
                    <img id="healthcareSite-indicator" src="<c:url value="/images/indicator.white.gif"/>" alt="activity indicator" style="display:none"/>
					<div id="healthcareSite-choices" class="autocomplete" style="display: none;" />
                	</div>
            	</div>
    		 </div> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<table id="notification" width="100%">
			<tr></tr>
			
			<c:forEach items="${command.healthcareSite.plannedNotifications}" var="notification" varStatus="nStatus">
				<tr id="notification-${nStatus.index}"><td>
				<chrome:deletableDivision id="notification-details-${nStatus.index}" title="${notification.eventName.displayName}" divTitle="a-${nStatus.index}"
				onclick="RowManager.deleteRow(notificationRowInserterProps,${nStatus.index},'${notification.id==null?'HC#':'ID#'}${notification.id==null?notification.hashCode:notification.id}')">
	
				
				<table width="100%">
				<tr>
					<td width="10%" align="right"><b><fmt:message key="notification.event"/></b></td>
					<td align="left">
						<form:select path="healthcareSite.plannedNotifications[${nStatus.index}].eventName" cssClass="required validate-notEmpty" 
									 onchange="displayAccrualField('${nStatus.index}');runReportBasedLogic('${nStatus.index}');"
									 onclick="updateName('a-${nStatus.index}', '${nStatus.index}');">
		                    <option value="" disabled="disabled">-- Select an Event -- </option>
		                    <form:options items="${notificationEventsRefData}" itemLabel="desc" itemValue="code" />
		                    <option value="" disabled="disabled">-- Select a Report -- </option>
		                    <form:options items="${notificationReportEventsRefData}" itemLabel="desc" itemValue="code" />
		                </form:select>
					</td>
					<td></td>			
					<td align="right" valign="top"><b><fmt:message key="notification.messageDetails"/></b>
						
						<!-- liteView popup -->
						<div id="emailMessageDetails-${nStatus.index}" style="display:none">	
							<form:input path="healthcareSite.plannedNotifications[${nStatus.index}].title" size="100" cssStyle="width:96%;" onfocus="lastElement = this;" />
						</div>
						<!-- liteview popup -->
						
					</td> 
		            <td align="left" rowspan="2">
		            	<c:if test="${notification.eventName == 'NEW_REGISTRATION_EVENT_REPORT'}">
		            		<form:textarea title="Click to Edit"  rows="3" cols="33" path="healthcareSite.plannedNotifications[${nStatus.index}].message" 
		            				   onclick="showMessageBody('${nStatus.index}');" disabled="true" />
		            	</c:if>
		            	<c:if test="${notification.eventName != 'NEW_REGISTRATION_EVENT_REPORT'}">
		            		<form:textarea title="Click to Edit"  rows="3" cols="33" path="healthcareSite.plannedNotifications[${nStatus.index}].message" 
		            				   onclick="showMessageBody('${nStatus.index}');" disabled="false" />
		            	</c:if>
		            </td>
		        </tr>
		        
		        <tr><td align="right"><b><fmt:message key="notification.frequency"/></b></td>
		            <td>
		            <c:if test="${notification.eventName != 'NEW_REGISTRATION_EVENT_REPORT'}">
						<form:select path="healthcareSite.plannedNotifications[${nStatus.index}].frequency" cssClass="required validate-notEmpty" disabled="true" onchange="runReportBasedLogic('${nStatus.index}');">
		                    <form:options items="${notificationFrequencyRefData}" itemLabel="desc" itemValue="code" />
		                </form:select>
		            </c:if>
		            <c:if test="${notification.eventName == 'NEW_REGISTRATION_EVENT_REPORT'}">
		            	<form:select path="healthcareSite.plannedNotifications[${nStatus.index}].frequency" cssClass="required validate-notEmpty" onchange="runReportBasedLogic('${nStatus.index}');">
		                    <form:options items="${notificationFrequencyRefData}" itemLabel="desc" itemValue="code" />
		                </form:select>
		            </c:if>
		        	</td>
		        	<td></td>  
		        	<td></td>		        	    	
		        </tr>
		        
		       
		        <tr>
		        	<td align="right"><div id="studyAccrual[${nStatus.index}]label" 
		        		style="<c:if test="${command.healthcareSite.plannedNotifications[nStatus.index].eventName != 'STUDY_ACCRUAL_EVENT'}">display:none</c:if>">Threshold:</div></td>
		            <td><div id="studyAccrual[${nStatus.index}]value" 
		            		style="<c:if test="${command.healthcareSite.plannedNotifications[nStatus.index].eventName != 'STUDY_ACCRUAL_EVENT'}">display:none</c:if>">
						<form:select path="healthcareSite.plannedNotifications[${nStatus.index}].studyThreshold">
		                    <option value="">Please Select</option>
		                    <form:options items="${notificationStudyAccrualRefData}" itemLabel="desc" itemValue="code" />
		                </form:select>
		                </div>
		        	</td>
		        	<td colspan="3"></td>  
		        </tr>
		        
		        <tr>
		        	<td align="right"><div id="studySiteAccrual[${nStatus.index}]label"  
		        		style="<c:if test="${command.healthcareSite.plannedNotifications[nStatus.index].eventName != 'STUDY_SITE_ACCRUAL_EVENT'}">display:none</c:if>">Threshold:</div></td>
		            <td><div id="studySiteAccrual[${nStatus.index}]value" 
		            		style="<c:if test="${command.healthcareSite.plannedNotifications[nStatus.index].eventName != 'STUDY_SITE_ACCRUAL_EVENT'}">display:none</c:if>">
						<form:select path="healthcareSite.plannedNotifications[${nStatus.index}].studySiteThreshold">
		                    <option value="">Please Select</option>
		                    <form:options items="${notificationStudySiteAccrualRefData}" itemLabel="desc" itemValue="code" />
		                </form:select>
		                </div>
		        	</td>
		        	<td colspan="3"></td>  
		        </tr>
		        
		        <tr><td colspan="5"><hr size="1"/></td></tr>
		    </table>
		    
		    
		    <table width="100%">     
		        <tr><td width="32%" align="center" colspan="1"><tags:button type="button" color="blue" icon="add" value="Add Email/Name" 
								onclick="RowManager.addRow(RowManager.getNestedRowInserter(notificationRowInserterProps,${nStatus.index}));" size="small"/></td>
					<td colspan="1" width="2%" class="divider" rowspan="2">&nbsp;</td>
			        <td width="32%" align="center" colspan="1"><tags:button type="button" color="blue" icon="add" value="Add Role" 
								onclick="RowManager.addRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}));" size="small"/></td>
					<td colspan="1" width="2%" class="divider" rowspan="2">&nbsp;</td>
					<td width="32%" align="center" colspan="1"><tags:button type="button" color="blue" icon="add" value="Add Contact" 
								onclick="RowManager.addRow(RowManager.getTertiaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}));" size="small"/></td>
			     </tr>
			     <tr><td align="center" colspan="1" style="vertical-align: top;">
		      		 <table id="table1">
		      		 	<tr><td></td><td></td></tr>
						<c:forEach var="email" varStatus="emailStatus" items="${command.healthcareSite.plannedNotifications[nStatus.index].userBasedRecipient}">
							<tr id="table1-${emailStatus.index}">
								<td class="alt">
								<div id="userEmail[${nStatus.index}][${emailStatus.index}]-choices" class="autocomplete" style="display: none;"></div>
									<input type="hidden" id="userEmail[${nStatus.index}][${emailStatus.index}]-hidden" 
										name="healthcareSite.plannedNotifications[${nStatus.index}].userBasedRecipient[${emailStatus.index}].emailAddress" 
										value="${command.healthcareSite.plannedNotifications[nStatus.index].userBasedRecipient[emailStatus.index].emailAddress}" />
									<input type="hidden" id="userEmail[${nStatus.index}][${emailStatus.index}]-hiddenResearchStaff" 
										name="healthcareSite.plannedNotifications[${nStatus.index}].userBasedRecipient[${emailStatus.index}].researchStaff" />
									<input id="userEmail[${nStatus.index}][${emailStatus.index}]-input" size="40" type="text"  
										value="${command.healthcareSite.plannedNotifications[nStatus.index].userBasedRecipient[emailStatus.index].fullName} (${command.healthcareSite.plannedNotifications[nStatus.index].userBasedRecipient[emailStatus.index].emailAddress})" class="autocomplete validate-notEmpty" />
									<tags:indicator id="userEmail[${nStatus.index}][${emailStatus.index}]-indicator" />
									
								</td>
								<td class="alt" valign="top"><a
									href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(notificationRowInserterProps,${nStatus.index}),${emailStatus.index},'${email.id==null?'HC#':'ID#'}${email.id==null?email.hashCode:email.id}');">
									<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
								</td>
							</tr>
						</c:forEach>
					</table>
		      		</td>
			      	<td align="center" colspan="1" style="vertical-align: top;">
			      		<table id="table2">
			      			<tr><td></td><td></td></tr>
							<c:forEach var="role" varStatus="roleStatus" items="${command.healthcareSite.plannedNotifications[nStatus.index].roleBasedRecipient}">
								<tr id="table2-${roleStatus.index}">
								<td class="alt">
									<form:select path="healthcareSite.plannedNotifications[${nStatus.index}].roleBasedRecipient[${roleStatus.index}].role" cssClass="required validate-notEmpty">
						                <option value="">Please Select</option>
						                <form:options items="${notificationPersonnelRoleRefData}" itemLabel="desc" itemValue="code" />
						            </form:select></td>
								<td class="alt" valign="top"><a
									href="javascript:RowManager.deleteRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}),${roleStatus.index},'${role.id==null?'HC#':'ID#'}${role.id==null?role.hashCode:role.id}');">
									<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
								</td>
								</tr>
							</c:forEach>
						</table>			
			      	</td>  
			      	<td align="center" colspan="1" style="vertical-align: top;">
			      		<table id="table3">
			      			<tr><td></td><td></td></tr>																	  
							<c:forEach var="cm" varStatus="cmStatus" items="${command.healthcareSite.plannedNotifications[nStatus.index].contactMechanismBasedRecipient}">
								<tr id="table3-${cmStatus.index}">
								<td class="alt">
									<form:input size="30" path="healthcareSite.plannedNotifications[${nStatus.index}].contactMechanismBasedRecipient[${cmStatus.index}].contactMechanisms[0].value" 
											cssClass="required validate-notEmpty&&EMAIL" /></td>
								<td class="alt" valign="top"><a
									href="javascript:RowManager.deleteRow(RowManager.getTertiaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}),${cmStatus.index},'${cm.id==null?'HC#':'ID#'}${cm.id==null?cm.hashCode:cm.id}');">
									<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
								</td>
								</tr>
							</c:forEach>
						</table>			
			      	</td>          
		      	</tr>
		
		      	<tr><td colspan="5"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20" align="middle" class="spacer"></td></tr>
		      	</table>
		      	</chrome:deletableDivision></td>
				</tr>
				<script>
			         RowManager.getNestedRowInserter(notificationRowInserterProps,${nStatus.index}).updateIndex(${fn:length(command.healthcareSite.plannedNotifications[nStatus.index].userBasedRecipient)});
			         RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}).updateIndex(${fn:length(command.healthcareSite.plannedNotifications[nStatus.index].roleBasedRecipient)});
			         RowManager.getTertiaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}).updateIndex(${fn:length(command.healthcareSite.plannedNotifications[nStatus.index].contactMechanismBasedRecipient)});
			  	</script>
			</c:forEach>
			</table>
			<div align="right">
				<tags:button type="button" color="blue" icon="add" value="Add Notification" onclick="RowManager.addRow(notificationRowInserterProps);"/>
			</div>
	</tags:basicFormPanelBox>
	</div>
	
	
	<div id="dummy-notification" style="display:none">
		<table> 
		<tr><td><chrome:deletableDivision id="notification-details-PAGE.ROW.INDEX" title="Notification Details" divTitle="a-PAGE.ROW.INDEX"
				onclick="RowManager.deleteRow(notificationRowInserterProps,PAGE.ROW.INDEX,-1)">
		
		<table width="100%">
			<tr>
				<td width="10%" align="right"><b><fmt:message key="notification.event"/></b>
	            </td>
				<td align="left">
	                <select id="plannedNotifications[PAGE.ROW.INDEX].eventName"  
	                onchange="displayAccrualField('PAGE.ROW.INDEX');runReportBasedLogic('PAGE.ROW.INDEX');"
		            		name="healthcareSite.plannedNotifications[PAGE.ROW.INDEX].eventName" class="required validate-notEmpty">
		            		<option value="" disabled="disabled">-- Select an Event --</option>
	                    <c:forEach items="${notificationEventsRefData}" var="event">
							<option value="${event.code}">${event.desc}</option>
						</c:forEach>
						<option value="" disabled="disabled">-- Select a report --</option>
	                    <c:forEach items="${notificationReportEventsRefData}" var="event">
							<option value="${event.code}">${event.desc}</option>
						</c:forEach>
	                </select>
				</td>
				<td></td>
				
				<td align="right" valign="top"><b><fmt:message key="notification.messageDetails"/></b>
					<!-- liteView popup -->
					<div id="emailMessageDetails-PAGE.ROW.INDEX" style="display:none">	
						<input type="text" id="plannedNotifications[PAGE.ROW.INDEX].title" name="healthcareSite.plannedNotifications[PAGE.ROW.INDEX].title" size="100" class="width:96%;" onFocus="lastElement = this;" />
					</div>
					<!-- liteview popup -->					
				</td> 
	            <td align="left" rowspan="2">
	            	<c:set var="eventName" value="NEW_REGISTRATION_EVENT_REPORT" />
	            	<textarea title="Click to Edit"  rows="3" cols="33" id="plannedNotifications[PAGE.ROW.INDEX].message"
	            			name="healthcareSite.plannedNotifications[PAGE.ROW.INDEX].message" onClick="showMessageBody('PAGE.ROW.INDEX');"></textarea>
	            </td>
	            <td>
	            	<input type="hidden" name="healthcareSite.plannedNotifications[PAGE.ROW.INDEX].healthcareSite" value="${command.healthcareSite.id }"/>
	        	</td>
	        </tr>
	        
	        <tr><td align="right"><b><fmt:message key="notification.frequency"/></b></td>
	            <td>
		            <select id="plannedNotifications[PAGE.ROW.INDEX].frequency" 
		            		name="healthcareSite.plannedNotifications[PAGE.ROW.INDEX].frequency" class="required validate-notEmpty" disabled="disabled">
		                    <option value="ANNUAL">Annual</option>
							<option value="MONTHLY">Monthly</option>
							<option value="WEEKLY">Weekly</option>
							<option value="IMMEDIATE" selected="selected">Immediate</option>
	                </select>
	        	</td>
	        	<td></td>
	        	<td></td>
	        </tr>
	        
	        <tr>
	        	<td align="right"><div id="studyAccrual[PAGE.ROW.INDEX]label" style="display:none">
	        	<b><fmt:message key="notification.threshold"/></b></div></td>
	            <td><div id="studyAccrual[PAGE.ROW.INDEX]value" style="display:none">
						<select id="plannedNotifications[PAGE.ROW.INDEX].studyThreshold" 
								name="healthcareSite.plannedNotifications[PAGE.ROW.INDEX].studyThreshold">
		                     <option value="" selected>Please Select</option>
		                     <c:forEach items="${notificationStudyAccrualRefData}" var="studyAcc">
								<option value="${studyAcc.code}">${studyAcc.desc}</option>
							</c:forEach>
		                </select>
	                </div>
	        	</td>
	        	<td colspan="3"></td>  
	        </tr>
	        
	        <tr>
	        	<td align="right"><div id="studySiteAccrual[PAGE.ROW.INDEX]label" style="display:none"><b><fmt:message key="notification.threshold"/></b></div></td>
	            <td><div id="studySiteAccrual[PAGE.ROW.INDEX]value" style="display:none">
					<select id="plannedNotifications[PAGE.ROW.INDEX].studySiteThreshold" 
							name="healthcareSite.plannedNotifications[PAGE.ROW.INDEX].studySiteThreshold">
	                   		 <option value="" selected>Please Select</option>
		                     <c:forEach items="${notificationStudySiteAccrualRefData}" var="studySiteAcc">
								<option value="${studySiteAcc.code}">${studySiteAcc.desc}</option>
							</c:forEach>
	                </select>
	                </div>
	        	</td>
	        	<td colspan="3"></td>  
	        </tr>
	        
	        
	        <tr><td colspan="5"><hr size="1"/></td>
	        </tr>
	</table>
		    
		    
	<table width="100%"> 
			<tr>
				<td width="32%" align="center" colspan="1"><span style="width:40" ><tags:button type="button" color="blue" icon="add" value="Add Email/Name" 
								onclick="RowManager.addRow(RowManager.getNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX));" size="small"/></span></td>
				<td colspan="1" width="2%" class="divider" rowspan="2">&nbsp;</td>
		        <td width="32%" align="center" colspan="1"><tags:button type="button" color="blue" icon="add" value="Add Role" 
								onclick="RowManager.addRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX));" size="small"/></td>
				<td colspan="1" width="2%" class="divider" rowspan="2">&nbsp;</td>
				<td width="32%" align="center" colspan="1"><tags:button type="button" color="blue" icon="add" value="Add Contact" 
								onclick="RowManager.addRow(RowManager.getTertiaryNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX));" size="small"/></td>
		    </tr>
		    
		    <tr><td align="center" colspan="1" style="vertical-align: top;">
					<table id="table1">
					<tr></tr>				
					</table>
		      	</td>
		      	<td align="center" colspan="1" style="vertical-align: top;">      		
					<table id="table2">
					<tr></tr>				
					</table>
		      	</td> 
		      	<td align="center" colspan="1" style="vertical-align: top;">      		
					<table id="table3">
					<tr></tr>				
					</table>
		      	</td>         
		    </tr>
	       
	      	<tr><td colspan="5"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20" align="middle" class="spacer"></td></tr>
	      </table>
	      </chrome:deletableDivision>
	      </td></tr>
	      </table>
		</div>
	
		<div id="dummy-emailRow" style="display:none">
			<table>
			<tr>
				<td class="alt">
					<input type="hidden" id="userEmail[PAGE.ROW.INDEX][NESTED.PAGE.ROW.INDEX]-hidden" 
							name="healthcareSite.plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress" 
							value="${command.healthcareSite.plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress}" />
					<input id="userEmail[PAGE.ROW.INDEX][NESTED.PAGE.ROW.INDEX]-input" size="40" type="text" 
							value="${command.healthcareSite.plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress}" class="autocomplete validate-notEmpty" />
					<tags:indicator id="userEmail[PAGE.ROW.INDEX][NESTED.PAGE.ROW.INDEX]-indicator" />
					<div id="userEmail[PAGE.ROW.INDEX][NESTED.PAGE.ROW.INDEX]-choices" class="autocomplete"></div></td>
				<td class="alt" valign="top"><a
					href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX, -1);">
					<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
				</td>
			</tr>
			</table>
		</div>
	
		<div id="dummy-roleRow" style="display:none">
			<table>
			<tr>
				<td class="alt">
		            <select id="plannedNotifications[PAGE.ROW.INDEX].roleBasedRecipient[SECONDARY.NESTED.PAGE.ROW.INDEX].role" 
		            		name="healthcareSite.plannedNotifications[PAGE.ROW.INDEX].roleBasedRecipient[SECONDARY.NESTED.PAGE.ROW.INDEX].role" class="required validate-notEmpty">
	                    <c:forEach items="${notificationPersonnelRoleRefData}" var="role">
							<option value="${role.code}">${role.desc}</option>
						</c:forEach>
	                </select>
	            </td>
				<td class="alt" valign="top"><a
					href="javascript:RowManager.deleteRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX),SECONDARY.NESTED.PAGE.ROW.INDEX,-1);">
					<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
				</td>
			</tr>
			</table>
		</div>
		
		<div id="dummy-cmRow" style="display:none">
			<table>
			<tr>
				<td class="alt">
		            <input id="plannedNotifications[PAGE.ROW.INDEX].contactMechanismBasedRecipient[TERTIARY.NESTED.PAGE.ROW.INDEX].contactMechanisms[0].value" 
		            	   name="healthcareSite.plannedNotifications[PAGE.ROW.INDEX].contactMechanismBasedRecipient[TERTIARY.NESTED.PAGE.ROW.INDEX].contactMechanisms[0].value"
						   class="required validate-notEmpty&&EMAIL" size="30" type="text" />
	            </td>
				<td class="alt" valign="top"><a
					href="javascript:RowManager.deleteRow(RowManager.getTertiaryNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX),TERTIARY.NESTED.PAGE.ROW.INDEX,-1);">
					<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
				</td>
			</tr>
			</table>
		</div>
			      	
	
		<div id="emailMessageDetails" style="display:none">	
			<table width="650" style="font-size: 11px;">
				<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20" align="middle" class="spacer"></td></tr>
				<tr><td width="17%" align="right" ><b><fmt:message key="notification.subjectLine"/></b></td> 								 
				<td><input type="text" id="plannedNotifications.title" size="80" class="width:96%;" onFocus="lastElement = this;" />
				</td></tr>
				
				<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="10" align="middle" class="spacer"></td></tr>
				
				<tr>
					 <td valign="top" align="right"><b><fmt:message key="notification.substitutionVariables"/></b></td>
					 <td>
						<select id="subVar" name="subVar" onChange="insertAtCursor()" >
								<option value="" selected="selected">Please Select</option>
							<c:forEach items="${notificationEmailSubstitutionVariablesRefData}" var="subVar">
								<option value="${subVar.code}">${subVar.desc}</option>
							</c:forEach>
						</select>	
					 </td>			
				</tr>
					
				<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="10" align="middle" class="spacer"></td></tr>
				
				<tr><td valign="top" align="right"><b><fmt:message key="notification.message"/></b></td>
					<td>
						<textarea rows="20" cols="77" id="plannedNotifications.message"></textarea>
					</td>
				</tr>
				<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="10" align="middle" class="spacer"></td></tr>
				<tr><td></td>
					<td align="right">
						<tags:button type="button" color="blue" value="Update" 
								onclick="updateMessage('${nStatus.index}');" />
						
						<!--[if IE]>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<![endif]-->
						
						<tags:button type="button" color="red" icon="x" value="Cancel" 
								onclick="win.close();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>				
			</table>
	</body>
</html>