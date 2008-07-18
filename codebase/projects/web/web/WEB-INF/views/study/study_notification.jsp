<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><studyTags:htmlTitle study="${command}" /></title>
    
<script>
            var roleRowInserterProps= {
                add_row_division_id: "table2",
                skeleton_row_division_id: "dummy-roleRow",
                initialIndex: ${fn:length(command.notifications[nStatus.index].roleBasedRecipient)},
                softDelete: ${softDelete == 'true'},
                isAdmin: ${isAdmin == 'true'},
                row_index_indicator: "SECONDARY.NESTED.PAGE.ROW.INDEX",
                path: "notifications[PAGE.ROW.INDEX].roleBasedRecipient"
           };
            var emailRowInserterProps= {
                add_row_division_id: "table1",
                skeleton_row_division_id: "dummy-emailRow",
                initialIndex: ${fn:length(command.notifications[nStatus.index].emailBasedRecipient)},
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
                initialIndex: ${fn:length(command.notifications)},
                softDelete: ${softDelete == 'true'},
                isAdmin: ${isAdmin == 'true'},
                path: "notifications"
            };
            RowManager.addRowInseter(notificationRowInserterProps);
            RowManager.registerRowInserters();
        </script>
</head>
<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}"
	formName="form" displayErrors="false">
	<jsp:attribute name="singleFields">
		<div><input type="hidden" id="_action" name="_action" value=""></div>
		<br />
		<table id="notification" class="tablecontent">
			<div id="notificationHeader"
				style=<c:if test="${fn:length(command.notifications) == 0}">"display:none"</c:if>>
			<tr>
				<th><span class="required-indicator">Threshold</span><tags:hoverHint
					keyProp="study.notification.threshold" /></th>
				<th>Email</th>
				<th>Role</th>
				<th></th>
			</tr>
			</div>
			<c:forEach items="${command.notifications}" var="notification"
				varStatus="nStatus">
				<script>
                    RowManager.getNestedRowInserter(notificationRowInserterProps,${nStatus.index}).updateIndex(${fn:length(command.notifications[nStatus.index].emailBasedRecipient)});
                    RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}).updateIndex(${fn:length(command.notifications[nStatus.index].roleBasedRecipient)});
                </script>
				<tr id="notification-${nStatus.index}">
					<td><form:input size="5"
						path="notifications[${nStatus.index}].threshold" maxlength="6"
						cssClass="validate-notEmpty&&NUMERIC" /></td>
					<td>
					<table class="tablecontent" id="table1" width="50%">
						<tr>
							<th></th>
							<th><input type="button" value="Add Email"
								onclick="RowManager.addRow(RowManager.getNestedRowInserter(notificationRowInserterProps,${nStatus.index}));" />
							</th>
						</tr>
						<c:forEach var="email" varStatus="emailStatus"
							items="${command.notifications[nStatus.index].emailBasedRecipient}">
							<tr id="table1-${emailStatus.index}">
								<td class="alt"><form:input
									path="notifications[${nStatus.index }].emailBasedRecipient[${emailStatus.index}].emailAddress"
									size="30" cssClass="validate-notEmpty&&EMAIL" /></td>
								<td class="alt"><a
									href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(notificationRowInserterProps,${nStatus.index}),${emailStatus.index},'${email.id==null?'HC#':'ID#'}${email.id==null?email.hashCode:email.id}');">
								<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
							</tr>
						</c:forEach>
					</table>
					</td>
					<td>
					<table class="tablecontent" id="table2" width="50%">
						<tr>
							<th></th>
							<th><input type="button" value="Add Role"
								onclick="RowManager.addRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}));" />
							</th>
						</tr>
						<c:forEach var="role" varStatus="roleStatus"
							items="${command.notifications[nStatus.index].roleBasedRecipient}">
							<tr id="table2-${roleStatus.index}">
								<td class="alt"><form:select
									path="notifications[${nStatus.index }].roleBasedRecipient[${roleStatus.index}].role" cssClass="validate-notEmpty">
									<option value="">Please Select</option>
									<form:options items="${notificationPersonnelRoleRefData}"
										itemLabel="desc" itemValue="code" />
								</form:select>
								<td class="alt"><a
									href="javascript:RowManager.deleteRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}),${roleStatus.index},'${role.id==null?'HC#':'ID#'}${role.id==null?role.hashCode:role.id}');">
								<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
							</tr>
						</c:forEach>
					</table>
					</td>
					<td class="alt"><a
						href="javascript:RowManager.deleteRow(notificationRowInserterProps,${nStatus.index},'${notification.id==null?'HC#':'ID#'}${notification.id==null?notification.hashCode:notification.id}');">
					<img src="<tags:imageUrl name="checkno.gif"/>" border="0"
						alt="Delete"></a></td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<div align="right"><input type="button" value="Add Notification"
			onclick="RowManager.addRow(notificationRowInserterProps);" /></div>
		<br />
		</jsp:attribute>
</tags:tabForm>


<div id="dummy-notification" style="display:none">
<table>
	<tr>
		<td><input type="text" size="5"
			name="notifications[PAGE.ROW.INDEX].threshold" maxlength="6"
			class="validate-notEmpty&&NUMERIC" /></td>
		<td>
		<table class="tablecontent" id="table1" width="50%">
			<tr>
				<th></th>
				<th><input type="button" value="Add Email"
					onclick="RowManager.addRow(RowManager.getNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX));" />
				</th>
			</tr>
		</table>
		</td>
		<td>
		<table class="tablecontent" id="table2" width="50%">
			<tr>
				<th></th>
				<th><input type="button" value="Add Role"
					onclick="RowManager.addRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX));" />
				</th>
			</tr>
		</table>
		</td>
		<td class="alt"><a
			href="javascript:RowManager.deleteRow(notificationRowInserterProps,PAGE.ROW.INDEX,-1);">
		<img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="Delete"></a>
		</td>
	</tr>
</table>
</div>

<div id="dummy-emailRow" style="display:none">
<table>
	<tr>
		<td class="alt"><input type="text"
			name="notifications[PAGE.ROW.INDEX].emailBasedRecipient[NESTED.PAGE.ROW.INDEX].emailAddress"
			size="30" class="validate-notEmpty&&EMAIL" /></td>
		<td class="alt"><a
			href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX,-1);">
		<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

<div id="dummy-roleRow" style="display:none">
<table>
	<tr>
		<td class="alt"><select
			id="notifications[PAGE.ROW.INDEX].roleBasedRecipient[SECONDARY.NESTED.PAGE.ROW.INDEX].role"
			name="notifications[PAGE.ROW.INDEX].roleBasedRecipient[SECONDARY.NESTED.PAGE.ROW.INDEX].role" class="validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${notificationPersonnelRoleRefData}" var="role">
				<option value="${role.code}">${role.desc}</option>
			</c:forEach>
		</select></td>
		<td class="alt"><a
			href="javascript:RowManager.deleteRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX),SECONDARY.NESTED.PAGE.ROW.INDEX,-1);">
		<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

</body>
</html>
