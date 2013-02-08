<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
<script>
function handleSaveSubjectDetailsAndReturnToRegistration(){
	$('participantId').value='${command.participant.id}';
	$('goToRegistration').value="true";
	$('command').submit();
}

var confirmWin ;

function confirmationPopup(id){
	$('_selectedAddressId').value=id;
	confirmWin = new Window({className :"mac_os_x", title: "Confirm", 
		hideEffect:Element.hide, 
		zIndex:100, width:400, height:180 , minimizable:false, maximizable:false,
		showEffect:Element.show 
		}); 
	confirmWin.setContent($('confirmationMessage')) ;
	confirmWin.showCenter(true);
}

var addressRowInserterProps = {
    add_row_division_id: "addressTable", 	        /* this id belongs to element where the row would be appended to */
    skeleton_row_division_id: "dummy-row-address",
    initialIndex: ${fn:length(command.addresses)},                            /* this is the initial count of the rows when the page is loaded  */
    path: "addresses",                            /* this is the path of the collection that holds the rows  */
    	 postProcessRowInsertion: function(object){
   	  	  inputDateElementLocal="addresses["+object.localIndex+"].startDate";
	      inputDateElementLink="addresses["+object.localIndex+"].startDate-calbutton";
	      Calendar.setup(
		        {
		            inputField  : inputDateElementLocal,         // ID of the input field
		            ifFormat    : "%m/%d/%Y",    // the date format
		            button      : inputDateElementLink       // ID of the button
		        }
	        );
	      inputDateElementLocal="addresses["+object.localIndex+"].endDate";
		  inputDateElementLink="addresses["+object.localIndex+"].endDate-calbutton";
	      Calendar.setup(
		        {
		            inputField  : inputDateElementLocal,         // ID of the input field
		            ifFormat    : "%m/%d/%Y",    // the date format
		            button      : inputDateElementLink       // ID of the button
		        }
	        );
		   }
};
RowManager.addRowInseter(addressRowInserterProps);
</script>
    <title>Address of <participanttags:htmlTitle subject="${command.participant}" /></title>
</head>
<body>
<form id="addAddressForm" name="addAddressForm" method="post" cssClass="standard">
	<input type="hidden" name="_action" id="_action" value="addAddress"/>
</form>
<form id="removeAddressForm" name="removeAddressForm" method="post" cssClass="standard">
	<input type="hidden" name="_action" id="_action" value="removeAddress"/>
	<input type="hidden" name="removeAddress" id="removeAddress" value=""/>
	<input type="hidden" name="_selectedAddressId" id="_selectedAddressId" value=""/>
</form>
<form:form method="post" name="command" id="command" cssClass="standard">   
	<tags:tabFields tab="${tab}" />
    <input type="hidden" name="goToRegistration" id="goToRegistration" value="false"/>
	<input type="hidden" name="participantId" id="participantId" value=""/>
	<chrome:box title="Addresses">
		<input type="hidden" id="currentPage" name="_page${tab.number}"/>
	    <tags:instructions code="participant_address" />
		<input type="hidden" name="_action" value="">
		<input type="hidden" name="_selected" value="">
		<table id="addressTable" width="100%" border="0">
			<tr></tr>
			  <c:forEach items="${command.addresses}" var="address" varStatus="status">
								<tr id="address-${status.index}">
			        <tr id="address-${status.index}">
			            <td>
			      			<chrome:deletableDivision divTitle="status-${status.index}" id="address-${status.index}"
									title="${command.addresses[status.index].addressString eq ''?'New Address':command.addresses[status.index].addressString}" minimize="true" divIdToBeMinimized="addressDiv-${status.index}"
									onclick="RowManager.deleteRow(addressRowInserterProps,${status.index},'${addresses[status.index].id==null?'HC#':'ID#'}${addresses[status.index].id==null?addresses[status.index].hashCode:addresses[status.index].id}')">
								<div id="addressDiv-${status.index}" style="display: none">
									<table width="100%" border="0">
									<tr>
								  	<td valign="top" width="50%">
								      <table width="100%" border="0" cellspacing="4" cellpadding="2">
									       <tr>
									          <td align="right"><b><fmt:message key="c3pr.common.streetAddress"/></b></td>
									          <td><form:input path="addresses[${status.index}].streetAddress" size="58" cssClass="validate-MAXLENGTH58"/></td>
									       </tr>
									       <tr>
									          <td align="right"><b><fmt:message key="c3pr.common.city"/></b></td>
									          <td><form:input path="addresses[${status.index}].city" cssClass="validate-MAXLENGTH30"/></td>
									       </tr>
									       <tr>
									          <td align="right"><b><fmt:message key="c3pr.common.state"/></b></td>
									          <td><form:input path="addresses[${status.index}].stateCode" cssClass="validate-MAXLENGTH20"/></td>
									       </tr>
									       <tr>
									          <td align="right"><b><fmt:message key="c3pr.common.zip"/></b></td>
									          <td><form:input path="addresses[${status.index}].postalCode" cssClass="validate-ZIPCODE"/></td>
									       </tr>
									       <tr>
									          <td align="right"><b><fmt:message key="c3pr.common.country"/></b></td>
									          <td><form:input path="addresses[${status.index}].countryCode" cssClass="validate-MAXLENGTH20"/></td>
									       </tr>
								     </table>
								  </td>
								  <td valign="top" width="50%">
								      <table width="100%" border="0" cellspacing="4" cellpadding="2">
									       <tr>
									          <td align="right">&nbsp;</td>
									          <td>&nbsp;</td>
									       </tr>
									       <tr>
									          <td align="right"><b><fmt:message key="c3pr.common.startDate"/></b></td>
									          <td><tags:dateInput path="addresses[${status.index}].startDate" size="14"/></td>
									       </tr>
									       <tr>
									          <td align="right"><b><fmt:message key="c3pr.common.endDate"/></b></td>
									          <td><tags:dateInput path="addresses[${status.index}].endDate" size="14"/></td>
									       </tr>
									       <tr>
									          <td align="right"><b><fmt:message key="c3pr.common.identifier"/></b></td>
									          <td><form:input path="addresses[${status.index}].identifier" cssClass="validate-MAXLENGTH50"/></td>
									       </tr>
 									   </table>
								  </td>
								</tr>
								</table>
								</div>
						</chrome:deletableDivision>
					</td>
				</tr>
			</c:forEach>
			</table>
		
		<div align="left">
				<tags:button type="button" color="blue" icon="add" value="Add Address" 
				onclick="javascript:RowManager.addRow(addressRowInserterProps);" size="small"/>
		</div>
		
		<chrome:division title="Contact Information">
			<div class="row">
				<div class="label"><fmt:message key="c3pr.common.email" />&nbsp;</div>
				<div class="value"><form:input path="participant.email"  cssClass="validate-EMAIL" size="30" /><tags:hoverHint keyProp="contactMechanism.email"/></div>
			</div>
			<div class="row">
				<div class="label"><fmt:message key="c3pr.common.phone" />&nbsp;</div>
				<div class="value"><form:input path="participant.phone"  cssClass="validate-US_PHONE_NO"/><tags:hoverHint keyProp="contactMechanism.phone"/></div>
			</div>
			<div class="row">
				<div class="label"><fmt:message key="c3pr.common.fax" />&nbsp;</div>
				<div class="value"><form:input path="participant.fax" cssClass="validate-US_PHONE_NO"/><tags:hoverHint keyProp="contactMechanism.fax"/></div>
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

<div id="dummy-row-address" style="display: none">
<table width="100%">
	<tr valign="top">
		<td><chrome:deletableDivision
			divTitle="address-PAGE.ROW.INDEX"
			id="addressDiv-PAGE.ROW.INDEX" title="New Address"
			onclick="RowManager.deleteRow(addressRowInserterProps,PAGE.ROW.INDEX,-1)">
			<table style="border: 0px red dotted;" width="100%">
				<tr>
					<td valign="top" width="50%">

					<table width="100%" border="0" cellspacing="4" cellpadding="2">
						<tr>
							<td align="right"><b><fmt:message key="c3pr.common.streetAddress"/></b></td>
							<td><input type="text" name="addresses[PAGE.ROW.INDEX].streetAddress" id="addresses[PAGE.ROW.INDEX].streetAddress" 
							      size="58" class="validate-MAXLENGTH58"/></td>
						</tr>
						<tr>
							<td align="right"><b><fmt:message key="c3pr.common.city"/></b></td>
							<td><input type="text" name="addresses[PAGE.ROW.INDEX].city" id="addresses[PAGE.ROW.INDEX].city"
								class="validate-MAXLENGTH30"/></td>
						</tr>
						<tr>
							<td align="right"><b><fmt:message key="c3pr.common.state"/></b></td>
							<td><input type="text" name="addresses[PAGE.ROW.INDEX].stateCode" id="addresses[PAGE.ROW.INDEX].stateCode"
							class="validate-MAXLENGTH20"/></td>
						</tr>
						<tr>
							<td align="right"><b><fmt:message key="c3pr.common.zip"/></b></td>
							<td><input type="text" name="addresses[PAGE.ROW.INDEX].postalCode" id="addresses[PAGE.ROW.INDEX].postalCode" 
								class="validate-ZIPCODE"/></td>
						</tr>
						<tr>
							<td align="right"><b><fmt:message key="c3pr.common.country"/></b></td>
							<td><input type="text" name="addresses[PAGE.ROW.INDEX].countryCode" id="addresses[PAGE.ROW.INDEX].countryCode"
							class="validate-MAXLENGTH20"/></td>
						</tr>
						</table>
					</td>
					<td valign="top" width="50%">
						<table width="100%" border="0" cellspacing="4" cellpadding="2">
							<tr>
								<td align="right">&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td align="right"><b><fmt:message key="c3pr.common.startDate"/></b></td>
								<td><input type="text" id="addresses[PAGE.ROW.INDEX].startDate" size="14"
					                name="addresses[PAGE.ROW.INDEX].startDate" class="validate-DATE">
									<a href="#" id="addresses[PAGE.ROW.INDEX].startDate-calbutton">
					                   <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0"/>
					               </a> 
					        	</td>
							</tr>
							<tr>
								<td align="right"><b><fmt:message key="c3pr.common.endDate"/></b></td>
								<td><input type="text" id="addresses[PAGE.ROW.INDEX].endDate" size="14"
					                name="addresses[PAGE.ROW.INDEX].endDate" class="validate-DATE">
									<a href="#" id="addresses[PAGE.ROW.INDEX].endDate-calbutton">
					                   <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0"/>
					               </a> 
					        	</td>
							</tr>
							<tr>
					          	<td align="right"><b><fmt:message key="c3pr.common.identifier"/></b></td>
					          	<td><input type="text" name="addresses[PAGE.ROW.INDEX].identifier" id="addresses[PAGE.ROW.INDEX].identifier"
					          		class="validate-MAXLENGTH50"/></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</chrome:deletableDivision></td>
	</tr>
</table>
</div>

<div id="HiddenPage" style="display:none;">
	<div id="confirmationMessage" style="padding: 15px;">
		<img src="<tags:imageUrl name="error-yellow.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="PARTICIPANT.ADDRESS.DELETE.WARNING"/>
		<div id="actionButtons">
		<div class="flow-buttons">
		   	<span class="next">
		   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="confirmWin.close();" />
				<tags:button type="button" color="green" icon="OK" onclick="removeAddress();" value="OK" />
			</span>
		</div>
	</div>
	</div>
</div>

</body>
</html>
