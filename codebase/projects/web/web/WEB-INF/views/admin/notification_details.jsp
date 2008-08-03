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
	        var win = new Window({className: "dialog", width:350, height:400, zIndex: 100, resizable: true, title: "CCTS Error Messages", showEffect:Effect.BlindDown, hideEffect: Effect.SwitchOff, draggable:true, wiredDrag: true}); win.getContent().innerHTML= $('built-cctsErrorMessage').innerHTML; win.setStatusBar("Error"); win.showCenter();
	        
	        
	        
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
				   clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
				   AutocompleterManager.registerAutoCompleter(clonedRowInserter);
			   },
		       onLoadRowInitialize: function(object, currentRowIndex){
					clonedRowInserter=Object.clone(userEmailAutocompleterProps);
					clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
					AutocompleterManager.registerAutoCompleter(clonedRowInserter);
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
	    							$(hiddenField).value=selectedChoice.id;
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
	
				
				<table>
				<tr>
					<td width="10%" align="right">Event:</td>
					<td align="left">
						<form:select path="plannedNotifications[${nStatus.index}].eventName" cssClass="validate-notEmpty">
		                    <option value="">Please Select</option>
		                    <form:options items="${notificationEventsRefData}" itemLabel="desc" itemValue="code" />
		                </form:select>
					</td>
					<td></td>			
					<td align="right" valign="top">Message Details:
					
					</td> 
		            <td align="left" rowspan="2"><textarea id="emailBody" rows="3" cols="25"></textarea></td>
		        </tr>
		        
		        <tr><td align="right">Frequency:</td>
		            <td>
						<form:select path="plannedNotifications[${nStatus.index}].frequency" cssClass="validate-notEmpty">
		                    <option value="">Please Select</option>
		                    <form:options items="${notificationFrequencyRefData}" itemLabel="desc" itemValue="code" />
		                </form:select>
		        	</td>
		        	<td></td>  
		        	<td></td>      	
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
		      		 	<tr></tr>
						<c:forEach var="email" varStatus="emailStatus" items="${command.plannedNotifications[nStatus.index].userBasedRecipient}">
							<tr id="table1-${emailStatus.index}">
								<td class="alt">
									<input type="hidden" id="userEmail${nStatus.index}-hidden" 
										name="plannedNotifications[${nStatus.index}].userBasedRecipient[emailStatus.index].emailAddress" 
										value="${command.plannedNotifications[nStatus.index].userBasedRecipient[emailStatus.index].emailAddress}" />
									<input id="plannedNotifications[${nStatus.index}].userBasedRecipient[emailStatus.index].emailAddress-input" size="40" type="text" 
										value="${command.plannedNotifications[nStatus.index].userBasedRecipient[emailStatus.index].emailAddress}" class="autocomplete validate-notEmpty" />
									<tags:indicator id="plannedNotifications[${nStatus.index}].userBasedRecipient[emailStatus.index].emailAddress-indicator" />
									<div id="plannedNotifications[${nStatus.index}].userBasedRecipient[emailStatus.index].emailAddress-choices" class="autocomplete"></div>
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
			      		<table id="table2" width="50%">
			      			<tr></tr>
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
		
		      	<tr><td colspan="5"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20" align="middle" class="spacer">
		      	
		      	</chrome:deletableDivision></td>
				</tr>
				</table>
				</td>
				</tr>			
			</c:forEach>
			</table>
			
			<div align="right">
				<input id="addNotification" type="button" value="Add Notification" onmouseover="this.style.cursor='pointer';" 
					onclick="RowManager.addRow(notificationRowInserterProps);" />
			</div>	<br/>
	</div>
				
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
		            		name="plannedNotifications[PAGE.ROW.INDEX].eventName" class="validate-notEmpty">
	                    <option value="">Please Select</option>
	                    <option value="NEW_REGISTRATION">New Registration</option>
	                    <option value="STUDY_STATUS_CHANGED_EVENT">Study Status Change</option>
	                    <option value="SUBJECT_REMOVED_OFF_STUDY">Subject Moved Off Study</option>
	                </select>
				</td>
				<td></td>
				<td align="right" valign="top">Message Details:</td> 
	            <td align="left" rowspan="2"><textarea id="emailBody" rows="3" cols="25"></textarea></td>
	        </tr>
	        
	        <tr><td align="right">Frequency:</td>
	            <td><select id="plannedNotifications[PAGE.ROW.INDEX].frequency" 
		            		name="plannedNotifications[PAGE.ROW.INDEX].frequency" class="validate-notEmpty">
	                    <option value="">Please Select</option>
	                    <option value="ANNUAL">Annual</option>
	                    <option value="MONTHLY">Monthly</option>
	                    <option value="WEEKLY">Weekly</option>
	                    <option value="IMMEDIATE">Immediate</option>
	                </select>
	        	</td>
	        	<td></td>
	        	<td></td>
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
					<input type="hidden" id="userEmail[PAGE.ROW.INDEX]-hidden" 
							name="plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress" 
							value="${command.plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress}" />
					<input id="plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress-input" size="40" type="text" 
							value="${command.plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress}" class="autocomplete validate-notEmpty" />
					<tags:indicator id="plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress-indicator" />
					<div id="plannedNotifications[PAGE.ROW.INDEX].userBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress-choices" class="autocomplete"></div></td>
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
	                    <option value="">Please Select</option>
	                    <option value="REGISTRAR">Registrar</option>
	                    <option value="STUDY_COORDINATOR">Study Coordinator</option>
	                    <option value="SITE_COORDINATOR">Site Coordinator</option>
	                    <option value="C3PR_Admin">C3PR Admin</option>
	                    <option value="SI">Site Investigator</option>
	                </select>
				<td class="alt"><a
					href="javascript:RowManager.deleteRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX),SECONDARY.NESTED.PAGE.ROW.INDEX,-1);">
					<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
				</td>
			</tr>
			</table>
		</div>
	
	</body>
</html>