<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>

<html>
<head>    
<style type="text/css">
.divider{
  			border-left-color: grey;
  			border-left-style: solid;
  			border-left-width: 1px
		}
</style>
<script>
        //this contains the liteview js stuff
        Event.observe(window, "load", function() {
                $('emailBody').observe('click', function(event) {
                        Lightview.show({
                          href: "<c:url value='/pages/admin/notification_emailDetails' />",
                          rel: 'ajax',
                          title: ':: C3PR Project::',
                          caption: "Enter the message details...",
                          options: {
                          autosize: false,
                          width: 850,
                          height:600,
                          ajax: {
                                onComplete: function() {
                                    // alert('QQQ');
                                    $('submitAJAXForm').observe('click', postSubmitSkinForm);
                                }
                            }
                          }
                        });
                });
        })	    
	
		function postSubmitSkinForm() {
		    var action = document.forms[0].action;
		    var value = getSelectedValue();		
		    var url = action + "?conf['skinPath'].value=" + value;		    
		    new Ajax.Request(url, {
		      method: 'post',
		      onSuccess: function(transport) {
		          Lightview.hide();
		          setTimeout('reloadPage()', 500);
		      }
		    });
		}
		
		function reloadPage() {
		    location.reload(true);
		}
	    
		function getSelectedValue() {
		    if ($('rdBLUE').checked) return ('blue');
		    if ($('rdGREEN').checked) return ('green');
		    if ($('rdORANGE').checked) return ('orange');
		}
</script>
<script language="JavaScript" type="text/JavaScript">
		
		var roleRowInserterProps= {
            add_row_division_id: "table2",
            skeleton_row_division_id: "dummy-roleRow",
            initialIndex: 1,  //${fn:length(command.notifications[nStatus.index].roleBasedRecipient)},
            softDelete: ${softDelete == 'true'},
            isAdmin: ${isAdmin == 'true'},
            row_index_indicator: "SECONDARY.NESTED.PAGE.ROW.INDEX",
            path: "notifications[PAGE.ROW.INDEX].roleBasedRecipient"
       };
        var emailRowInserterProps= {
            add_row_division_id: "table1",
            skeleton_row_division_id: "dummy-emailRow",
            initialIndex: 1, // ${fn:length(command.notifications[nStatus.index].emailBasedRecipient)},
            softDelete: ${softDelete == 'true'},
            isAdmin: ${isAdmin == 'true'},
            row_index_indicator: "NESTED.PAGE.ROW.INDEX",
            path: "notifications[PAGE.ROW.INDEX].emailBasedRecipient"
        };
        var notificationRowInserterProps = {
                nested_row_inserter: emailRowInserterProps,
                secondary_nested_row_inserter: roleRowInserterProps,
                add_row_division_id: "notification",
                skeleton_row_division_id: "dummy-notification",
                initialIndex: 1, //${fn:length(command.notifications)},
                softDelete: ${softDelete == 'true'},
                isAdmin: ${isAdmin == 'true'},
                path: "notifications"
        };
        RowManager.addRowInseter(notificationRowInserterProps);
        RowManager.registerRowInserters();

</script>
</head>

<body>
<div id="main">

<tags:basicFormPanelBox tab="${tab}" flow="${flow}" title="Notifications" action="createInvestigator">
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
	<input type="hidden" name="_finish" value="true">


	<table id="notification" width="100%"><tr><td>
	<chrome:deletableDivision id="staff-details" title="Notification Details" divTitle="a"
	onclick="javascript:RowManager.deleteRow(notificationRowInserterProps,${nStatus.index},'${notification.id==null?'HC#':'ID#'}${notification.id==null?notification.hashCode:notification.id}');">

	<table><tr>
			<td width="10%" align="right">Event:</td>
			<td align="left">
               <select id="healthcareSiteInvestigators[0].statusCode" name="healthcareSiteInvestigators[0].statusCode" class="validate-notEmpty">
				<option value="">Please Select</option>
				<option value="AC" selected="selected">Study Status Changes to Active</option>
				<option value="AC">Study Status Changes to Amendment Pending</option>
				<option value="AC">Study Status Changes to Active</option>
				<option value="IN">Registration status</option>
				<option value="IN">Patient Moved off Study</option>
				</select>
			</td>
			<td></td>			
			<td align="right" valign="top">Message Details:</td> 
            <td align="left" rowspan="2"><textarea id="emailBody" rows="3" cols="25"></textarea></td>
        </tr>
        
        <tr><td align="right">Frequency:</td>
            <td>
				<select id="healthcareSiteInvestigators[0].statusCode" name="healthcareSiteInvestigators[0].statusCode" class="validate-notEmpty">
					<option value="">Please Select</option>
					<option value="IN">Immediate</option>
					<option value="AC" selected="selected">Daily</option>
					<option value="IN">Weekly</option>
					<option value="IN">Monthly</option>	
				</select>
        	</td>
        	<td></td>  
        	<td></td>      	
        </tr>
        
         <tr><td colspan="5"><hr size="1"/></td>
         </tr>
         
        <tr>
         <td width="49%" align="center" colspan="2"><input type="button" value="Add Email/Name" onmouseover="this.style.cursor='pointer';"
						onclick="RowManager.addRow(RowManager.getNestedRowInserter(notificationRowInserterProps,1));" /> </td>
		 <td colspan="1" width="2%" class="divider" rowspan="2"></td>
         <td width="49%" align="center" colspan="2"><input type="button" value="Add Role" onmouseover="this.style.cursor='pointer';"
					onclick="RowManager.addRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,1));" /></td>
	     </tr>
	     <tr><td align="center" colspan="2">
      		<%--  <table class="tablecontent"  width="50%">
				<c:forEach var="email" varStatus="emailStatus" items="${command.notifications[nStatus.index].emailBasedRecipient}">
					<tr id="table1-${emailStatus.index}">
						<td class="alt">
							<form:input path="notifications[${nStatus.index }].emailBasedRecipient[${emailStatus.index}].emailAddress"
										size="30" cssClass="validate-notEmpty&&EMAIL" /></td>
						<td class="alt"><a
							href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(notificationRowInserterProps,${nStatus.index}),${emailStatus.index},'${email.id==null?'HC#':'ID#'}${email.id==null?email.hashCode:email.id}');">
							<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
						</td>
					</tr>
				</c:forEach>
			</table>     --%> 
			<table id="table1"><tr><td> 
			<input id="notifications[1].emailBasedRecipient[0].emailAddress" name="notifications[1].emailBasedRecipient[0].emailAddress" 
	 			class="validate-notEmpty" type="text" value="admin@duke.edu (John. Smith)" size="30"/>
			</td><td>
	 		<a href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(notificationRowInserterProps,1),1,'${email.id==null?'HC#':'ID#'}${email.id==null?email.hashCode:email.id}');">
					<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td></tr>
			</table>
      	</td>
   
      	<td align="center" colspan="2">
      		<%-- <table class="tablecontent" id="table2" width="50%">
				<c:forEach var="role" varStatus="roleStatus" items="${command.notifications[nStatus.index].roleBasedRecipient}">
					<tr id="table2-${roleStatus.index}">
						<td class="alt">
							<form:select path="notifications[${nStatus.index }].roleBasedRecipient[${roleStatus.index}].role" cssClass="validate-notEmpty">
								<option value="">Please Select</option>
								<form:options items="${notificationPersonnelRoleRefData}" itemLabel="desc" itemValue="code" />
							</form:select>
						<td class="alt"><a
							href="javascript:RowManager.deleteRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}),${roleStatus.index},'${role.id==null?'HC#':'ID#'}${role.id==null?role.hashCode:role.id}');">
							<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
						</td>
					</tr>
				</c:forEach>
			</table> --%>
			<table id="table2"><tr><td> 
			<select id="notifications[1].roleBasedRecipient[0].role" name="notifications[1].roleBasedRecipient[0].role" class="validate-notEmpty">
					<option value="">Please Select</option>
					<option value="IN">Study Coordinator</option>
					<option value="AC" selected="selected">Administrator</option>
					<option value="IN">Registrar</option>
					<option value="IN">Site Coordinator</option>	
				</select></td><td>
	 		<a href="javascript:RowManager.deleteRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,1),1,'${role.id==null?'HC#':'ID#'}${role.id==null?role.hashCode:role.id}');">
			<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td></tr>
			</table>
      	</td>         
      </tr>
      <tr><td colspan="5"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20" align="middle" class="spacer"></td></tr>
      </table>	
	</chrome:deletableDivision>
	</td></tr></table>
	
	<br/><div align="right">
	<input id="addNotification" type="button" value="Add Notification" onmouseover="this.style.cursor='pointer';" onclick="RowManager.addRow(notificationRowInserterProps);" /></div>
	</div><br/>
			
<div id="dispButton"><tags:tabControls /></div>
</tags:basicFormPanelBox>
</div>


<div id="dummy-notification" style="display:none">
	<table width="100%"><tr><td>
	<chrome:deletableDivision id="staff-details" title="Notification Details" divTitle="a"
	onclick="javascript:RowManager.deleteRow(notificationRowInserterProps,${nStatus.index},'${notification.id==null?'HC#':'ID#'}${notification.id==null?notification.hashCode:notification.id}');">
	<table><tr>
			<td width="10%" align="right">Event:
            </td>
			<td align="left">
                <select id="healthcareSiteInvestigators[0].statusCode" name="healthcareSiteInvestigators[0].statusCode" class="validate-notEmpty">
					<option value="">Please Select</option>
					<option value="AC" selected="selected">Study Status Changes to Active</option>
					<option value="AC">Study Status Changes to Amendment Pending</option>
					<option value="AC">Study Status Changes to Active</option>
					<option value="IN">Registration status</option>
					<option value="IN">Patient Moved off Study</option>
				</select>
			</td>
			<td></td>
			<td align="right" valign="top">Message Details:</td> 
            <td align="left" rowspan="2"><textarea id="emailBody" rows="3" cols="25"></textarea></td>
        </tr>
        
        <tr><td align="right">Frequency:</td>
            <td>
				<select id="healthcareSiteInvestigators[0].statusCode" name="healthcareSiteInvestigators[0].statusCode" class="validate-notEmpty">
					<option value="">Please Select</option>
					<option value="IN">Immediate</option>
					<option value="AC" selected="selected">Daily</option>
					<option value="IN">Weekly</option>
					<option value="IN">Monthly</option>	
				</select>
        	</td>
        	<td></td>
        	<td></td>
        </tr>
          <tr><td colspan="5"><hr size="1"/></td>
         </tr>
        <tr>
         <td width="49%" align="center" colspan="2"><input type="button" value="Add Email/Name" onmouseover="this.style.cursor='pointer';"
						onclick="RowManager.addRow(RowManager.getNestedRowInserter(notificationRowInserterProps,1));" /> </td>
		 <td colspan="1" width="2%" class="divider" rowspan="2"></td>
         <td width="49%" align="center" colspan="2"><input type="button" value="Add Role" onmouseover="this.style.cursor='pointer';"
					onclick="RowManager.addRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,1));" /></td>
	     </tr>
	     <tr><td align="center" colspan="2">
			<table id="table1"><tr><td> 
			<input id="notifications[1].emailBasedRecipient[0].emailAddress" name="notifications[1].emailBasedRecipient[0].emailAddress" 
	 			class="validate-notEmpty" type="text" value="admin@duke.edu (John. Smith)" size="30"/></td><td>
	 		<a href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(notificationRowInserterProps,1),1,'${email.id==null?'HC#':'ID#'}${email.id==null?email.hashCode:email.id}');">
					<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td></tr>
			</table>
      	</td>
   
      	<td align="center" colspan="2">      		
			<table id="table2"><tr><td> 
			<select id="notifications[1].roleBasedRecipient[0].role" name="notifications[1].roleBasedRecipient[0].role" class="validate-notEmpty">
					<option value="" selected="selected">Please Select</option>
					<option value="IN">Study Coordinator</option>
					<option value="AC">Administrator</option>
					<option value="IN">Registrar</option>
					<option value="IN">Site Coordinator</option>	
				</select></td><td>
	 		<a href="javascript:RowManager.deleteRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,1),1,'${role.id==null?'HC#':'ID#'}${role.id==null?role.hashCode:role.id}');">
			<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td></tr>
			</table>
      	</td>         
      </tr>
      <tr><td colspan="5"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20" align="absmiddle" class="spacer"></td></tr>
      </table></chrome:deletableDivision>	
      </td></tr></table>
</div>


<div id="dummy-emailRow" style="display:none">
		<table><tr><td> 
			<input id="notifications[PAGE.ROW.INDEX].emailBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress" 
		   name="notifications[PAGE.ROW.INDEX].emailBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress" 
		   class="validate-notEmpty" type="text" value="" size="30"/></td>
			<td>
	 		<a href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(notificationRowInserterProps,1),1,'${email.id==null?'HC#':'ID#'}${email.id==null?email.hashCode:email.id}');">
			<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td></tr>
		</table>
</div>

<div id="dummy-roleRow" style="display:none">

		<table><tr><td> 
			<select id="notifications[PAGE.ROW.INDEX].roleBasedRecipient[SECONDARY.NESTED.PAGE.ROW.INDEX].role" 
			name="notifications[PAGE.ROW.INDEX].roleBasedRecipient[SECONDARY.NESTED.PAGE.ROW.INDEX].role" class="validate-notEmpty">
					<option value="" selected="selected">Please Select</option>
					<option value="IN">Study Coordinator</option>
					<option value="AC">Administrator</option>
					<option value="IN">Registrar</option>
					<option value="IN">Site Coordinator</option>	
				</select></td>
			<td>
	 		<a href="javascript:RowManager.deleteRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,1),1,'${role.id==null?'HC#':'ID#'}${role.id==null?role.hashCode:role.id}');">
			<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td></tr>
		</table>
</div>


</body>
</html>