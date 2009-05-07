<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    
<script>
           var roleRowInserterProps= {
                add_row_division_id: "table2",
                skeleton_row_division_id: "dummy-roleRow",
                initialIndex: ${fn:length(command.study.plannedNotifications[nStatus.index].roleBasedRecipient)},
                softDelete: ${softDelete == 'true'},
                isAdmin: ${isAdmin == 'true'},
                row_index_indicator: "SECONDARY.NESTED.PAGE.ROW.INDEX",
                path: "study.plannedNotifications[PAGE.ROW.INDEX].roleBasedRecipient"
           };
            var emailRowInserterProps= {
                add_row_division_id: "table1",
                skeleton_row_division_id: "dummy-emailRow",
                initialIndex: ${fn:length(command.study.plannedNotifications[nStatus.index].contactMechanismBasedRecipient)},
                softDelete: ${softDelete == 'true'},
                isAdmin: ${isAdmin == 'true'},
                row_index_indicator: "NESTED.PAGE.ROW.INDEX",
                path: "study.plannedNotifications[PAGE.ROW.INDEX].contactMechanismBasedRecipient"
            };
            var notificationRowInserterProps = {
                nested_row_inserter: emailRowInserterProps,
                secondary_nested_row_inserter: roleRowInserterProps,
                add_row_division_id: "notification",
                skeleton_row_division_id: "dummy-notification",
                initialIndex: ${fn:length(command.study.plannedNotifications)},
                softDelete: ${softDelete == 'true'},
                isAdmin: ${isAdmin == 'true'},
                path: "study.plannedNotifications"
            };
            RowManager.addRowInseter(notificationRowInserterProps);
            RowManager.registerRowInserters();
        </script>
</head>
<body>
<tags:panelBox>
<form:form>
		<div><input type="hidden" id="_action" name="_action" value=""></div>
		<table id="notification" width="100%">
			<tr></tr>
			<c:forEach items="${command.study.plannedNotifications}" var="notification" varStatus="nStatus">
			<tr>
			<script>
                    RowManager.getNestedRowInserter(notificationRowInserterProps,${nStatus.index}).updateIndex(${fn:length(command.study.plannedNotifications[nStatus.index].contactMechanismBasedRecipient)});
                    RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}).updateIndex(${fn:length(command.study.plannedNotifications[nStatus.index].roleBasedRecipient)});
            </script>
            <td>
			<chrome:deletableDivision id="notificationDiv-${nStatus.index }" divTitle="notificationTitle-${nStatus.index}" onclick="RowManager.deleteRow(notificationRowInserterProps,${nStatus.index},'${notification.id==null?'HC#':'ID#'}${notification.id==null?notification.hashCode:notification.id}');" >
				<table id="notificationTable"  width="100%">
					<tr id="notification-${nStatus.index}">
						<td valign="top" width="40%">
							<table>
								<tr><td><b><fmt:message key="c3pr.common.targetAccrual"/></b></td>
									<td>
										<form:input size="5" path="study.plannedNotifications[${nStatus.index}].studyThreshold" maxlength="6" cssClass="validate-notEmpty&&NUMERIC" />
									</td>
								</tr>
							</table>
						</td>
						<td valign="top" width="30%">
							<table id="table1">
								<tr>
									<td valign="top">
									<tags:button type="button" color="blue" icon="add" value="Add Email" id="addEmail" 
										onclick="RowManager.addRow(RowManager.getNestedRowInserter(notificationRowInserterProps,${nStatus.index}));" size="small"/>
									</td>
									<td></td>
								</tr>
								<c:forEach var="cmbr" varStatus="emailStatus" items="${command.study.plannedNotifications[nStatus.index].contactMechanismBasedRecipient}">
									<tr id="table1-${emailStatus.index}">
										<td class="alt"><form:input						 
											path="study.plannedNotifications[${nStatus.index}].contactMechanismBasedRecipient[${emailStatus.index}].contactMechanisms[0].value"
											size="30" cssClass="validate-notEmpty&&EMAIL" /></td>
										<td class="alt"><a
											href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(notificationRowInserterProps,${nStatus.index}),${emailStatus.index},'${cmbr.id==null?'HC#':'ID#'}${cmbr.id==null?cmbr.hashCode:cmbr.id}');">
										<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
									</tr>
								</c:forEach>
							</table>
						</td>
						<td valign="top" width="30%">
							<table  id="table2">
								<tr>
									<td valign="top">
									<tags:button type="button" color="blue" icon="add" value="Add Role" 
										onclick="RowManager.addRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}));" size="small"/>
									</td>
									<td></td>
								</tr>
								<c:forEach var="role" varStatus="roleStatus"  items="${command.study.plannedNotifications[nStatus.index].roleBasedRecipient}">
									<tr id="table2-${roleStatus.index}">
										<td class="alt">
											<form:select path="study.plannedNotifications[${nStatus.index }].roleBasedRecipient[${roleStatus.index}].role" cssClass="validate-notEmpty">
												<option value="">Please Select</option>
												<form:options items="${notificationPersonnelRoleRefData}" itemLabel="desc" itemValue="code" />
											</form:select>
										</td>	
										<td class="alt">
											<a href="javascript:RowManager.deleteRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,${nStatus.index}),${roleStatus.index},'${role.id==null?'HC#':'ID#'}${role.id==null?role.hashCode:role.id}');">
											<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
										</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
				</table>
			</chrome:deletableDivision>
			</td>
			</tr>
			</c:forEach>
		</table>
		<br>
	<hr noshade size="1" width="100%" style="border-top: 1px black dotted;" align="left">
		<div align="left">
		<tags:button type="button" color="blue" icon="add" value="Add Notification" id="addNotification"
		onclick="RowManager.addRow(notificationRowInserterProps);" size="small"/>
		</div>
		<br />
<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" isFlow="false"/>
</form:form>
</tags:panelBox>


<div id="dummy-notification" style="display:none">
<table width="100%">
	<tr>
		<td>
		<chrome:deletableDivision id="notificationDiv-[PAGE.ROW.INDEX]" divTitle="notificationTitle-[PAGE.ROW.INDEX]" onclick="javascript:RowManager.deleteRow(notificationRowInserterProps,PAGE.ROW.INDEX,-1);"   >
			<table id="notificationTable"  width="100%">
				<tr id="notification-[PAGE.ROW.INDEX]">
					<td valign="top" width="40%">
						<table>
							<tr><td><b><fmt:message key="c3pr.common.targetAccrual"/></b></td>
								<td>
									<input type="text" size="5" name="study.plannedNotifications[PAGE.ROW.INDEX].studyThreshold" maxlength="6" class="validate-notEmpty&&NUMERIC" />
								</td>
							</tr>
						</table>
					</td>
					<td valign="top" width="30%">
						<table id="table1">
							<tr>
								<td valign="top">
								<tags:button type="button" color="blue" icon="add" value="Add Email" id="addEmail"
								onclick="RowManager.addRow(RowManager.getNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX));" size="small"/>
								</td>
								<td></td>
							</tr>
						</table>
					</td>
					<td valign="top" width="30%">
						<table  id="table2">
							<tr>
								<td valign="top">
								<tags:button type="button" color="blue" icon="add" value="Add Role" 
								onclick="RowManager.addRow(RowManager.getSecondaryNestedRowInserter(notificationRowInserterProps,PAGE.ROW.INDEX));" size="small"/>
								</td>
								<td></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</chrome:deletableDivision>
		</td>	
	</tr>
</table>
</div>

<div id="dummy-emailRow" style="display:none">
<table>
	<tr>
		<td class="alt"><input type="text"
			name="study.plannedNotifications[PAGE.ROW.INDEX].contactMechanismBasedRecipient[NESTED.PAGE.ROW.INDEX].contactMechanisms[0].value"
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
			id="study.plannedNotifications[PAGE.ROW.INDEX].roleBasedRecipient[SECONDARY.NESTED.PAGE.ROW.INDEX].role"
			name="study.plannedNotifications[PAGE.ROW.INDEX].roleBasedRecipient[SECONDARY.NESTED.PAGE.ROW.INDEX].role" class="validate-notEmpty">
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
