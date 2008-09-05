<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
	
<html>
	<head> 
	<tags:dwrJavascriptLink objects="UserAjaxFacade" />   
	<style type="text/css">
		.divider{
	  			border-left-color: grey;
	  			border-left-style: solid;
	  			border-left-width: 1px
		}
	</style>
	<script>
		var win;
		var currentMessageIndex;
	     	
	     function showMessageBody(index){
	     	currentMessageIndex = index;
	     	if(win == null){
	     		win = new Window({className: "dialog", width:550, height:550, zIndex: 100, resizable: true, title: "Email Message", 
	        	showEffect:Effect.BlindDown, hideEffect: Effect.BlindUp, draggable:true, wiredDrag: true}); 
	        	win.setContent('emailMessageDetails', true, true);
	     	}else {
			    $('plannedNotifications.title').value = $('plannedNotifications['+currentMessageIndex+'].title').value;
	     		$('plannedNotifications.message').value = $('plannedNotifications['+currentMessageIndex+'].message').value;
	     	}
	     	win.showCenter();
	     }   
	     
	     function updateMessage(index){
	     	$('plannedNotifications['+currentMessageIndex+'].title').value = $('plannedNotifications.title').value;
	     	$('plannedNotifications['+currentMessageIndex+'].message').value = $('plannedNotifications.message').value;
	     	win.close();
	     	//$('emailMessageDetails-'+index).innerHTML = win.getContent().innerHTML;
	     }
	</script>
	<script language="JavaScript" type="text/JavaScript">
			var roleRowInserterProps= {
	            add_row_division_id: "table2",
	            skeleton_row_division_id: "dummy-roleRow",
	            initialIndex: ${fn:length(command.plannedNotifications[nStatus.index].roleBasedRecipient)},
	            softDelete: ${softDelete == 'true'},
	            isAdmin: ${isAdmin == 'true'},
	            row_index_indicator: "SECONDARY.NESTED.PAGE.ROW.INDEX",
	            path: "plannedNotifications[PAGE.ROW.INDEX].roleBasedRecipient"
	       };
	        var emailRowInserterProps= {
	            add_row_division_id: "table1",
	            skeleton_row_division_id: "dummy-emailRow",
	            initialIndex: ${fn:length(command.plannedNotifications[nStatus.index].userBasedRecipient)},
	            softDelete: ${softDelete == 'true'},
	            isAdmin: ${isAdmin == 'true'},
	            row_index_indicator: "NESTED.PAGE.ROW.INDEX",
	            path: "plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient",
	            postProcessRowInsertion: function(object){
			       clonedRowInserter=Object.clone(userEmailAutocompleterProps);
				   clonedRowInserter.basename=clonedRowInserter.basename + "[" +object.parent_row_index + "][" +object.localIndex + "]";
				   AutocompleterManager.registerAutoCompleter(clonedRowInserter);
			   },
		       onLoadRowInitialize: function(object, currentRowIndex){
					clonedRowInserter1=Object.clone(userEmailAutocompleterProps);
					clonedRowInserter1.basename=clonedRowInserter1.basename+ "[" +object.parent_row_index + "][" +currentRowIndex + "]";
					AutocompleterManager.registerAutoCompleter(clonedRowInserter1);
		       }
	        };
	        var notificationRowInserterProps = {
	                nested_row_inserter: emailRowInserterProps,
	                secondary_nested_row_inserter: roleRowInserterProps,
	                add_row_division_id: "notification",
	                skeleton_row_division_id: "dummy-notification",
	                initialIndex: ${fn:length(command.plannedNotifications)},
	                softDelete: ${softDelete == 'true'},
	                isAdmin: ${isAdmin == 'true'},
	                path: "plannedNotifications"
	        };
	        RowManager.addRowInseter(notificationRowInserterProps);
	        RowManager.registerRowInserters();
	</script>

	<script>
		var userEmailAutocompleterProps = {
            basename: "userEmail",
            populator: function(autocompleter, text) {
                UserAjaxFacade.matchNameAndEmail(text,function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
                return (obj.firstName +" " +obj.lastName + " (" +obj.contactMechanisms[0].value+ ")")
            },
            afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								//hiddenField=userEmailAutocompleterProps.basename+"-hidden"
	    							hiddenField=inputElement.id.split("-")[0]+"-hidden";
	    							$(hiddenField).value=selectedChoice.contactMechanisms[0].value;
			 }
        }         
	</script>
	
	<script type="text/javascript">
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
				sel.text = myValue.value;
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

        	if(eventElement.value == 'NEW_REGISTRATION_EVENT'){
				//disable message fields and enable frequency element
				frequencyElement.disabled = false;
				if(frequencyElement.value == 'IMMEDIATE'){
					messageElement.disabled = false;
				} else {
					messageElement.disabled = true;
				}
        	} else {
        		//set frequency to IMMEDIATE and disable it and enable message element.
        		frequencyElement.options[3].selected = true;
        		frequencyElement.value= 'IMMEDIATE';
        		frequencyElement.disabled = true;
        		messageElement.disabled = false;
        	}
        }
	</script> 
	</head>
	
	<body>
	<div id="main">	
	<tags:basicFormPanelBox tab="${tab}" flow="${flow}" title="Notifications" action="createNotification">
		<input type="hidden" name="_action" value="">
		<input type="hidden" name="_selected" value="">
		<input type="hidden" name="_finish" value="true">
		<br/>
		
			<table id="notification" width="100%">
			<tr></tr>
			<c:forEach items="${command.plannedNotifications}" var="notification" varStatus="nStatus">
				<script>
			         RowManager.getNestedRowInserter(notificationRowInserterProps,${nStatus.index}).updateIndex(${fn:length(command.plannedNotifications[nStatus.index].userBasedRecipient)});
			         RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}).updateIndex(${fn:length(command.plannedNotifications[nStatus.index].roleBasedRecipient)});
			  	</script>
				<tr id="notification-${nStatus.index}"><td>
				<chrome:deletableDivision id="notification-details-${nStatus.index}" title="Notification Details" divTitle="a-${nStatus.index}"
				onclick="RowManager.deleteRow(notificationRowInserterProps,${nStatus.index},'${notification.id==null?'HC#':'ID#'}${notification.id==null?notification.hashCode:notification.id}')">
	
				
				<table width="60%">
				<tr>
					<td width="10%" align="right">Event:</td>
					<td align="left">
						<form:select path="plannedNotifications[${nStatus.index}].eventName" cssClass="validate-notEmpty" 
									 onchange="displayAccrualField('${nStatus.index}');runReportBasedLogic('${nStatus.index}');">
		                    <option value="">Please Select</option>
		                    <form:options items="${notificationEventsRefData}" itemLabel="desc" itemValue="code" />
		                </form:select>
					</td>
					<td></td>			
					<td align="right" valign="top">Message Details:
						
						<!-- liteView popup -->
						<div id="emailMessageDetails-${nStatus.index}" style="display:none">	
							<form:input path="plannedNotifications[${nStatus.index}].title" size="100" cssStyle="width:96%;" onfocus="lastElement = this;" />
						</div>
						<!-- liteview popup -->
						
					</td> 
		            <td align="left" rowspan="2">
		            	<c:if test="${notification.eventName == 'NEW_REGISTRATION_EVENT'}">
		            		<form:textarea title="Click to Edit"  rows="3" cols="33" path="plannedNotifications[${nStatus.index}].message" 
		            				   onclick="showMessageBody('${nStatus.index}');" disabled="true" />
		            	</c:if>
		            	<c:if test="${notification.eventName != 'NEW_REGISTRATION_EVENT'}">
		            		<form:textarea title="Click to Edit"  rows="3" cols="33" path="plannedNotifications[${nStatus.index}].message" 
		            				   onclick="showMessageBody('${nStatus.index}');" disabled="false" />
		            	</c:if>
		            </td>
		        </tr>
		        
		        <tr><td align="right">Frequency:</td>
		            <td>
		            <c:if test="${notification.eventName != 'NEW_REGISTRATION_EVENT'}">
						<form:select path="plannedNotifications[${nStatus.index}].frequency" cssClass="validate-notEmpty" disabled="true" onchange="runReportBasedLogic('${nStatus.index}');">
		                    <form:options items="${notificationFrequencyRefData}" itemLabel="desc" itemValue="code" />
		                </form:select>
		            </c:if>
		            <c:if test="${notification.eventName == 'NEW_REGISTRATION_EVENT'}">
		            	<form:select path="plannedNotifications[${nStatus.index}].frequency" cssClass="validate-notEmpty" onchange="runReportBasedLogic('${nStatus.index}');">
		                    <form:options items="${notificationFrequencyRefData}" itemLabel="desc" itemValue="code" />
		                </form:select>
		            </c:if>
		        	</td>
		        	<td></td>  
		        	<td></td>		        	    	
		        </tr>
		        
		       
		        <tr>
		        	<td align="right"><div id="studyAccrual[${nStatus.index}]label" 
		        		style="<c:if test="${command.plannedNotifications[nStatus.index].eventName != 'STUDY_ACCRUAL_EVENT'}">display:none</c:if>">Threshold:</div></td>
		            <td><div id="studyAccrual[${nStatus.index}]value" 
		            		style="<c:if test="${command.plannedNotifications[nStatus.index].eventName != 'STUDY_ACCRUAL_EVENT'}">display:none</c:if>">
						<form:select path="plannedNotifications[${nStatus.index}].studyThreshold">
		                    <option value="">Please Select</option>
		                    <form:options items="${notificationStudyAccrualRefData}" itemLabel="desc" itemValue="code" />
		                </form:select>
		                </div>
		        	</td>
		        	<td colspan="3"></td>  
		        </tr>
		        
		        <tr>
		        	<td align="right"><div id="studySiteAccrual[${nStatus.index}]label"  
		        		style="<c:if test="${command.plannedNotifications[nStatus.index].eventName != 'STUDY_SITE_ACCRUAL_EVENT'}">display:none</c:if>">Threshold:</div></td>
		            <td><div id="studySiteAccrual[${nStatus.index}]value" 
		            		style="<c:if test="${command.plannedNotifications[nStatus.index].eventName != 'STUDY_SITE_ACCRUAL_EVENT'}">display:none</c:if>">
						<form:select path="plannedNotifications[${nStatus.index}].studySiteThreshold">
		                    <option value="">Please Select</option>
		                    <form:options items="${notificationStudySiteAccrualRefData}" itemLabel="desc" itemValue="code" />
		                </form:select>
		                </div>
		        	</td>
		        	<td colspan="3"></td>  
		        </tr>
		        
		        
		        <tr><td colspan="5"><hr size="1"/></td></tr>
		         
		         <tr><td width="49%" align="center" colspan="2"><input type="button" value="Add Email/Name" onmouseover="this.style.cursor='pointer';"
									onclick="RowManager.addRow(RowManager.getNestedRowInserter(notificationRowInserterProps,${nStatus.index}));" /> </td>
					 <td colspan="1" width="2%" class="divider" rowspan="2"></td>
			         <td width="49%" align="center" colspan="2"><input type="button" value="Add Role" onmouseover="this.style.cursor='pointer';"
								onclick="RowManager.addRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}));" /></td>
			     </tr>
			     <tr><td align="center" colspan="2">
		      		 <table id="table1">
		      		 	<tr><td></td><td></td></tr>
						<c:forEach var="email" varStatus="emailStatus" items="${command.plannedNotifications[nStatus.index].userBasedRecipient}">
							<tr id="table1-${emailStatus.index}">
								<td class="alt">
									<input type="hidden" id="userEmail[${nStatus.index}][${emailStatus.index}]-hidden" 
										name="plannedNotifications[${nStatus.index}].userBasedRecipient[${emailStatus.index}].emailAddress" 
										value="${command.plannedNotifications[nStatus.index].userBasedRecipient[emailStatus.index].emailAddress}" />
									<input id="userEmail[${nStatus.index}][${emailStatus.index}]-input" size="40" type="text" 
										value="${command.plannedNotifications[nStatus.index].userBasedRecipient[emailStatus.index].fullName} (${command.plannedNotifications[nStatus.index].userBasedRecipient[emailStatus.index].emailAddress})" class="autocomplete validate-notEmpty" />
									<tags:indicator id="userEmail[${nStatus.index}][${emailStatus.index}]-indicator" />
									<div id="userEmail[${nStatus.index}][${emailStatus.index}]-choices" class="autocomplete"></div>
								</td>
								<td class="alt"><a
									href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(notificationRowInserterProps,${nStatus.index}),${emailStatus.index},'${email.id==null?'HC#':'ID#'}${email.id==null?email.hashCode:email.id}');">
									<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
								</td>
							</tr>
						</c:forEach>
					</table>
		      		</td>
			      	<td align="center" colspan="2">
			      		<table id="table2">
			      			<tr><td></td><td></td></tr>
							<c:forEach var="role" varStatus="roleStatus" items="${command.plannedNotifications[nStatus.index].roleBasedRecipient}">
								<tr id="table2-${roleStatus.index}">
								<td class="alt">
									<form:select path="plannedNotifications[${nStatus.index}].roleBasedRecipient[${roleStatus.index}].role" cssClass="validate-notEmpty">
						                <option value="">Please Select</option>
						                <form:options items="${notificationPersonnelRoleRefData}" itemLabel="desc" itemValue="code" />
						            </form:select></td>
								<td class="alt"><a
									href="javascript:RowManager.deleteRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}),${roleStatus.index},'${role.id==null?'HC#':'ID#'}${role.id==null?role.hashCode:role.id}');">
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
						
			</c:forEach>
			</table>
			
			<div align="right">
				<input id="addNotification" type="button" value="Add Notification" onmouseover="this.style.cursor='pointer';" 
					onclick="RowManager.addRow(notificationRowInserterProps);" />
			</div><br/>
				
	<div id="dispButton"><tags:tabControls /></div>
	</tags:basicFormPanelBox>
	</div>
	
	
	<div id="dummy-notification" style="display:none">
		<table> 
		<tr><td><chrome:deletableDivision id="notification-details-PAGE.ROW.INDEX" title="Notification Details" divTitle="a-PAGE.ROW.INDEX"
				onclick="RowManager.deleteRow(notificationRowInserterProps,PAGE.ROW.INDEX,-1)">
		
		<table>
			<tr>
				<td width="10%" align="right">Event:
	            </td>
				<td align="left">
	                <select id="plannedNotifications[PAGE.ROW.INDEX].eventName"  
	                onchange="displayAccrualField('PAGE.ROW.INDEX');runReportBasedLogic('PAGE.ROW.INDEX');"
		            		name="plannedNotifications[PAGE.ROW.INDEX].eventName" class="validate-notEmpty">
	                    <c:forEach items="${notificationEventsRefData}" var="event">
							<option value="${event.code}">${event.desc}</option>
						</c:forEach>
	                </select>
				</td>
				<td></td>
				
				<td align="right" valign="top">Message Details:
					<!-- liteView popup -->
					<div id="emailMessageDetails-PAGE.ROW.INDEX" style="display:none">	
						<input type="text" id="plannedNotifications[PAGE.ROW.INDEX].title" name="plannedNotifications[PAGE.ROW.INDEX].title" size="100" class="width:96%;" onfocus="lastElement = this;" />
					</div>
					<!-- liteview popup -->					
				</td> 
	            <td align="left" rowspan="2">
	            	<c:set var="eventName" value="${notification.eventName}" />
	            	<textarea title="Click to Edit"  rows="3" cols="25" id="plannedNotifications[PAGE.ROW.INDEX].message"
	            			disabled="<c:if test='${notification.eventName == eventName}'>true</c:if>"
	            			name="plannedNotifications[PAGE.ROW.INDEX].message" onclick="showMessageBody('PAGE.ROW.INDEX');"></textarea>
	            </td>
	        </tr>
	        
	        <tr><td align="right">Frequency:</td>
	            <td>
		            <select id="plannedNotifications[PAGE.ROW.INDEX].frequency" 
		            		name="plannedNotifications[PAGE.ROW.INDEX].frequency" class="validate-notEmpty">
	                    <c:forEach items="${notificationFrequencyRefData}" var="frequency">
							<option value="${frequency.code}">${frequency.desc}</option>
						</c:forEach>
	                </select>
	        	</td>
	        	<td></td>
	        	<td></td>
	        </tr>
	        
	        <tr>
	        	<td align="right"><div id="studyAccrual[PAGE.ROW.INDEX]label" style="display:none">
	        	Threshold:</div></td>
	            <td><div id="studyAccrual[PAGE.ROW.INDEX]value" style="display:none">
						<select id="plannedNotifications[PAGE.ROW.INDEX].studyThreshold" 
								name="plannedNotifications[PAGE.ROW.INDEX].studyThreshold">
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
	        	<td align="right"><div id="studySiteAccrual[PAGE.ROW.INDEX]label" style="display:none">Threshold:</div></td>
	            <td><div id="studySiteAccrual[PAGE.ROW.INDEX]value" style="display:none">
					<select id="plannedNotifications[PAGE.ROW.INDEX].studySiteThreshold" 
							name="plannedNotifications[PAGE.ROW.INDEX].studySiteThreshold">
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
			<tr>
	         	<td width="49%" align="center" colspan="2"><input type="button" value="Add Email/Name" onmouseover="this.style.cursor='pointer';"
						onclick="RowManager.addRow(RowManager.getNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX));" /> </td>
			 	<td colspan="1" width="2%" class="divider" rowspan="2"></td>
	         	<td width="49%" align="center" colspan="2"><input type="button" value="Add Role" onmouseover="this.style.cursor='pointer';"
						onclick="RowManager.addRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX));" /></td>
		    </tr>
		    
		    <tr><td align="center" colspan="2">
					<table id="table1">
					<tr></tr>				
					</table>
		      	</td>
		      	<td align="center" colspan="2">      		
					<table id="table2">
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
							name="plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress" 
							value="${command.plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress}" />
					<input id="userEmail[PAGE.ROW.INDEX][NESTED.PAGE.ROW.INDEX]-input" size="40" type="text" 
							value="${command.plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress}" class="autocomplete validate-notEmpty" />
					<tags:indicator id="userEmail[PAGE.ROW.INDEX][NESTED.PAGE.ROW.INDEX]-indicator" />
					<div id="userEmail[PAGE.ROW.INDEX][NESTED.PAGE.ROW.INDEX]-choices" class="autocomplete"></div></td>
				<td class="alt"><a
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
		            		name="plannedNotifications[PAGE.ROW.INDEX].roleBasedRecipient[SECONDARY.NESTED.PAGE.ROW.INDEX].role" class="validate-notEmpty">
	                    <c:forEach items="${notificationPersonnelRoleRefData}" var="role">
							<option value="${role.code}">${role.desc}</option>
						</c:forEach>
	                </select>
	            </td>
				<td class="alt"><a
					href="javascript:RowManager.deleteRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX),SECONDARY.NESTED.PAGE.ROW.INDEX,-1);">
					<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
				</td>
			</tr>
			</table>
		</div>
	
		<div id="emailMessageDetails" style="display:none">	
			<table width="650" style="font-size: 11px;">
				<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20" align="middle" class="spacer"></td></tr>
				<tr><td width="17%" align="right" >Subject Line:</td> 								 
				<td><input type="text" id="plannedNotifications.title" size="80" class="width:96%;" onfocus="lastElement = this;" />
				</td></tr>
				
				<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="10" align="middle" class="spacer"></td></tr>
				
				<tr>
					 <td valign="top" align="right">Substitution Variables:</td>
					 <td>
						<select id="subVar" name="subVar" onchange="insertAtCursor()">
								<option value="" selected="selected">Please Select</option>
							<c:forEach items="${notificationEmailSubstitutionVariablesRefData}" var="subVar">
								<option value="${subVar.code}">${subVar.desc}</option>
							</c:forEach>
						</select>	
					 </td>			
				</tr>
					
				<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="10" align="middle" class="spacer"></td></tr>
				
				<tr><td valign="top" align="right">Message:</td>
					<td>
						<textarea rows="20" cols="77" id="plannedNotifications.message"></textarea>
					</td>
				</tr>
				<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="10" align="middle" class="spacer"></td></tr>
				<tr><td colspan="2" align="right">
						<input type="button" value="Update" onclick="updateMessage('${nStatus.index}');" />
						<input type="button" value="Cancel" onclick="win.close();" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td></tr>				
			</table>
		</div>
	</body>
</html>