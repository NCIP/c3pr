<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><studyTags:htmlTitle study="${command.study}" /></title>
<link rel="stylesheet" type="text/css" href="/c3pr/public/css/tables1" />
<script type="text/javascript">

      function updateName(divID, stringValue) {
          if ($(divID)) {
              $(divID).innerHTML = stringValue;
          }
      }

       function manageEnrollingIndicatorSelectBox(box, index) {
           if (isNaN(index)) return;
           try {
               if (box.value == 'false') {
                   $('study.epochs[' + index + '].reservationIndicator').disabled = false;
               } else {
                   $('study.epochs[' + index + '].reservationIndicator').disabled = true;
                   $('study.epochs[' + index + '].reservationIndicator').value = false;
               }
           } catch(ex) {
           }
        }
        
       	function manageReservingIndicatorSelectBox(box, index) {
             if (isNaN(index)) return;
             try {
                 if (box.value == 'false') {
                	 Effect.OpenUp('addArm-' + index);
                     $('study.epochs[' + index + '].enrollmentIndicator').disabled = false;
                     $('study.epochs[' + index + '].treatmentIndicator').disabled = false;
                     $('study.epochs[' + index + '].randomizedIndicator').disabled = false;
                      
                 } else {
                	 Effect.CloseDown('addArm-' + index);
                     $('study.epochs[' + index + '].enrollmentIndicator').disabled = true;
                     $('study.epochs[' + index + '].enrollmentIndicator').value = false;
                     $('study.epochs[' + index + '].treatmentIndicator').disabled = true;
                     $('study.epochs[' + index + '].treatmentIndicator').value = false;
                     $('study.epochs[' + index + '].randomizedIndicator').disabled = true;
                     $('study.epochs[' + index + '].randomizedIndicator').value = false;
                 }
             } catch(ex) {
             }
        }
        
        var armInserterProps= {
            add_row_division_id: "arm",
            skeleton_row_division_id: "dummy-arm",
            initialIndex: ${fn:length(command.study.epochs[treatmentEpochCount.index].arms)},
            softDelete: ${softDelete == 'true'},
            isAdmin: ${isAdmin == 'true'},
            row_index_indicator: "NESTED.PAGE.ROW.INDEX",
            deleteMsgPrefix: "Book Randomization Entries(if any) for this Epoch will be deleted.",
            path: "epochs[PAGE.ROW.INDEX].arms"
        };
        var genericEpochRowInserterProps= {
          	nested_row_inserter: armInserterProps,
            add_row_division_id: "genericEpoch",
            skeleton_row_division_id: "dummy-genericEpoch",
            initialIndex: ${fn:length(command.study.epochs)},
            softDelete: ${softDelete == 'true'},
            isAdmin: ${isAdmin == 'true'},
            path: "epochs",
            postProcessRowInsertion: function(object){
      								var inputName="study.epochs["+object.localIndex+"].name";
      								setTimeout("enableFocus(\'"+inputName+"\')",10);
      								addEpochOrderOptions(object.localIndex + 1);
      							},
            postProcessRowDeletion: function(t){
      								removeEpochOrderOptions();                	
      						    }
            };
 		RowManager.addRowInseter(genericEpochRowInserterProps);
        RowManager.registerRowInserters();
        function enableFocus(inputName2){
        	$$("input[name='"+inputName2+"']")[0].focus();
        }


        function addEpochOrderOptions(order){
        	$$('.epochOrder').each(function(element){
        		var optn = document.createElement("OPTION");
            	optn.text = order;
            	optn.value = order;
				element.options.add(optn);
    		});
        }

        function removeEpochOrderOptions(){
        	$$('.epochOrder').each(function(element){
				element.remove(element.options.length-1);
    		});
        }
    	
    </script>
<style>
	.descTextarea {
		width: 280px;
	}
	*{zoom:1}
	#workflow-tabs {
	top:25px;
}
</style>
<!--[if IE]>
<style>
	.descTextarea {
    	width:280px;
	}
</style>
<![endif]-->

<%--
DELETED TD
--%>
</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" formName="studyDesignForm" displayErrors="false">
	<jsp:attribute name="singleFields">
	<tags:errors path="study.epochs" /> 
	<div>
		<input type="hidden" name="_action" value=""> 
	</div>
<tags:instructions code="study_design" />
<!-- BIG TABLE START -->
<table id="genericEpoch" width="100%" border="0">
<tr></tr>
    <c:forEach items="${command.study.epochs}" var="treatmentEpoch"  varStatus="treatmentEpochCount">
        <tr id="genericEpoch-${treatmentEpochCount.index}">
            <script type="text/javascript">
                RowManager.getNestedRowInserter(genericEpochRowInserterProps,${treatmentEpochCount.index}).updateIndex(${fn:length(command.study.epochs[treatmentEpochCount.index].arms)});
            </script>
            <td>
      			<chrome:deletableDivision divTitle="genericTitle-${treatmentEpochCount.index}" id="genericEpochBox-${treatmentEpochCount.index}"
						title="Epoch: ${command.study.epochs[treatmentEpochCount.index].name}" minimize="true" divIdToBeMinimized="epoch-${treatmentEpochCount.index}"
						onclick="RowManager.deleteRow(genericEpochRowInserterProps,${treatmentEpochCount.index},'${treatmentEpoch.id==null?'HC#':'ID#'}${treatmentEpoch.id==null?treatmentEpoch.hashCode:treatmentEpoch.id}')">
<!-- GENERIC START-->
<div id="epoch-${treatmentEpochCount.index}" style="display: none">
<table width="100%" border="0">
<tr>
  <td valign="top" width="50%">
      <table width="100%" border="0" cellspacing="4" cellpadding="2">
      <tr>
          <td align="right"><tags:requiredIndicator /><b><fmt:message key="c3pr.common.name"/></b></td>
          <td align="left" valign="top">
              <form:input path="study.epochs[${treatmentEpochCount.index}].name" size="43" cssClass="validate-notEmpty"
											onkeyup="updateName('genericTitle-${treatmentEpochCount.index}', 'Epoch: ' + this.value);" />
			  <tags:hoverHint id="study.treatmentEpoch.name-${treatmentEpochCount.index}" keyProp="study.treatmentEpoch.name" />
          </td>
      </tr>
      <tr>
          <td align="right"> 
          	<tags:requiredIndicator /><b><fmt:message key="study.epoch.order"/></b>
          </td>
          <td>
          		<form:select path="study.epochs[${treatmentEpochCount.index}].epochOrder" id="epochOrder" cssClass="epochOrder validate-notEmpty">
          			<option value="">Please Select</option>
          			<form:options items="${epochOrders}" />
          		</form:select>
              <tags:hoverHint id="study.treatmentEpoch.epochorder-${treatmentEpochCount.index}" keyProp="study.treatmentEpoch.epochOrder" />
          </td>
      </tr>
      <tr>
		<td align="right">
		  	<tags:requiredIndicator /><b><fmt:message key="study.epoch.treating"/></b> 
		</td>
        <td>
			<form:select path="study.epochs[${treatmentEpochCount.index}].treatmentIndicator" disabled="${treatmentEpoch.reservationIndicator}"
											cssClass="validate-notEmpty">
                    <option value="">Please Select</option>
                    <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
			</form:select>
            <tags:hoverHint id="study.treatmentEpoch.treatmentIndicator-${treatmentEpochCount.index}" keyProp="study.epoch.treatmentIndicator" />
		</td>
      </tr>
      <tr>
          <td align="right"><tags:requiredIndicator /><b><fmt:message key="c3pr.common.enrolling"/></b> </td>
          <td align="left">
            <form:select id="study.epochs[${treatmentEpochCount.index}].enrollmentIndicator" disabled="${treatmentEpoch.reservationIndicator}"
						path="study.epochs[${treatmentEpochCount.index}].enrollmentIndicator" onchange="manageEnrollingIndicatorSelectBox(this,${treatmentEpochCount.index});"
						cssClass="validate-notEmpty">
	                    <option value="">Please Select</option>
	                    <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
			</form:select>
		    <tags:hoverHint id="study.nonTreatmentEpoch.enrollmentIndicator-${treatmentEpochCount.index}" keyProp="study.nonTreatmentEpoch.enrollmentIndicator" />
          </td>
      </tr>
      <c:if test="${command.study.randomizedIndicator== true }">
      <tr>
              <td align="right"><tags:requiredIndicator /><b><fmt:message key="study.randomized"/></b> </td>
              <td>
                  <form:select
												path="study.epochs[${treatmentEpochCount.index}].randomizedIndicator"
												disabled="${treatmentEpoch.reservationIndicator}"
												cssClass="validate-notEmpty">
                    <option value="">Please Select</option>
                    <form:options items="${yesNo}" itemLabel="desc"
													itemValue="code" />
                  </form:select>
                  <tags:hoverHint
												id="study.treatmentEpoch.randomizedIndicator-${treatmentEpochCount.index}"
												keyProp="study.treatmentEpoch.randomizedIndicator" />
              </td>
      </tr>
  	</c:if>

      </table>

  </td>

  <td valign="top">
      <table width="100%" border="0">
      <tr>
          <td align="right"><b><fmt:message key="c3pr.common.description"/></b></td>
          <td align="left" colspan="3"><form:textarea
											path="study.epochs[${treatmentEpochCount.index}].descriptionText"
											rows="2" cssClass="validate-MAXLENGTH2000"/>
                                        <tags:hoverHint
											id="study.treatmentEpoch.description-${treatmentEpochCount.index}"
											keyProp="study.treatmentEpoch.description" /></td>
      </tr>
      
      <tr>
          <td align="right"><b><fmt:message key="study.accrualCeiling"/></b> </td>
          <td align="left">
              <form:input
											path="study.epochs[${treatmentEpochCount.index}].accrualCeiling"
											size="14" maxlength="5"
											cssClass="validate-numeric&&nonzero_numeric" />
	                                		<tags:hoverHint
											id="study.nonTreatmentEpoch.accrualCeiling-${treatmentEpochCount.index}"
											keyProp="study.nonTreatmentEpoch.accrualCeiling" />
          </td>
          </tr>
          <tr>
                        <td align="right">
										<div
											id="reservationIndicatorLabel-${treatmentEpochCount.index}"><span
											class=""><tags:requiredIndicator /><b><fmt:message key="study.epoch.reserving"/></b></div></td>
                        <td align="left">
                           <div
											id="reservationIndicator-${treatmentEpochCount.index}">
                               <c:set var="_disabled" value="false" />
                               <c:if
											test="${command.study.epochs[treatmentEpochCount.index].enrollmentIndicator}">
                                    <c:set var="_disabled" value="true" />
                               </c:if>
                               <form:select disabled="${_disabled}"
											path="study.epochs[${treatmentEpochCount.index}].reservationIndicator"
											onchange="manageReservingIndicatorSelectBox(this,${treatmentEpochCount.index});"
											cssClass="validate-notEmpty">
                                   <option value="">Please Select</option>
                                   <form:options items="${yesNo}"
												itemLabel="desc" itemValue="code" />
                              </form:select>
                               <tags:hoverHint
											id="study.nonTreatmentEpoch.reservationIndicator-${treatmentEpochCount.index}"
											keyProp="study.nonTreatmentEpoch.reservationIndicator" />
                           </div>
                        </td>
        </tr>
        
    <c:if test="${command.study.stratificationIndicator== true }">
      <tr>
              <td align="right"><tags:requiredIndicator /><b><fmt:message key="study.stratified"/></b></td>
              <td>
                  <form:select
												path="study.epochs[${treatmentEpochCount.index}].stratificationIndicator"
												cssClass="validate-notEmpty">
                    <option value="">Please Select</option>
                    <form:options items="${yesNo}" itemLabel="desc"
													itemValue="code" />
                  </form:select>
                  <tags:hoverHint
												id="study.treatmentEpoch.stratificationIndicator-${treatmentEpochCount.index}"
												keyProp="study.epoch.stratificationIndicator" />
              </td>
      </tr>
  	</c:if>
      </table>
  </td>
</tr>
<tr bgcolor="eeffee">
  <td colspan="3" align="left">
      <!--ARMS TABLE-->
      <table id="arm" class="tablecontent" border="0">
      <tags:errors path="study.epochs[${treatmentEpochCount.index}].arms" />
      <tr id="h-${treatmentEpochCount.index}" >
          <th>
          	<tags:requiredIndicator /><fmt:message key="study.epoch.arm"/>
          	<tags:hoverHint id="study.arm.name-${treatmentEpochCount.index}" keyProp="study.arm.name" />
          </th>
          <th>
          	<fmt:message key="c3pr.common.description"/>
          	<tags:hoverHint id="study.arm.description-${treatmentEpochCount.index}" keyProp="study.arm.description" />
          </th>
          <th>
          	<fmt:message key="study.accrualCeiling"/>
          	<tags:hoverHint id="study.arm.targetAccrualNumber-${treatmentEpochCount.index}" keyProp="study.arm.targetAccrualNumber" />
          </th>
          <th></th>
      </tr>
      <c:choose>
      	<c:when test="${fn:length(treatmentEpoch.arms) == 0}">
      		<tr>
      			<td align="left" id="addArmMessage-${treatmentEpochCount.index}"><fmt:message key="epoch.arm.addArm"/></td>
      		</tr>
      	</c:when>
      	<c:otherwise>
      		<c:forEach items="${treatmentEpoch.arms}" var="arm" varStatus="statusArms">
	            <tr id="arm-${statusArms.index}">
	                <td valign="top">
	                	<form:input path="study.epochs[${treatmentEpochCount.index}].arms[${statusArms.index}].name" size="43" cssClass="validate-notEmpty" />
	                </td>
	                <td valign="top">
	                	<form:textarea path="study.epochs[${treatmentEpochCount.index}].arms[${statusArms.index}].descriptionText" rows="2" cols="40" cssClass="validate-MAXLENGTH2000"/>
	                </td>
	                <td valign="top" align="left">
	                	<form:input path="study.epochs[${treatmentEpochCount.index}].arms[${statusArms.index}].targetAccrualNumber" size="6" maxlength="6"
													cssClass="validate-numeric&&nonzero_numeric" />
					</td>
	                <td valign="top" align="left">
	                    <a href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(genericEpochRowInserterProps,${treatmentEpochCount.index}),${statusArms.index },'${arm.id==null?'HC#':'ID#'}${arm.id==null?arm.hashCode:arm.id}');">
	                    	<img src="<tags:imageUrl name="checkno.gif"/>" border="0">
	                    </a>
	                </td>
	            </tr>
            </c:forEach>
      	</c:otherwise>
      </c:choose>    
      </table>
      <br>
      <tags:button id="addArm-${treatmentEpochCount.index}" type="button" color="blue" icon="add" value="Add Arm"
					onclick="$('addArmMessage-${treatmentEpochCount.index}') != null ? $('addArmMessage-${treatmentEpochCount.index}').hide():''; javascript:RowManager.addRow(RowManager.getNestedRowInserter(genericEpochRowInserterProps,${treatmentEpochCount.index}));" size="small"/>
  </td>
</tr>
</table>
<!-- GENERIC END-->
</chrome:deletableDivision>
</td>
</tr>
</c:forEach>
</table>
<!-- BIG TABLE END -->
 <hr noshade size="1" width="100%" style="border-top: 1px black dotted;" align="left">
	<div align="left">
	<tags:button type="button" color="blue" icon="add" value="Add Epoch"
	onclick="$('dummy-genericEpoch').innerHTML=$('genericHtml').innerHTML;RowManager.addRow(genericEpochRowInserterProps)" />
    <br></div>
</jsp:attribute>
</tags:tabForm>


<!-- DUMMY SECIION START -->

<div id="dummy-genericEpoch" style="display: none"></div>

<div id="dummy-arm" style="display: none">
<table id="arm" class="tablecontent" width="50%">
	<tr>
		<td valign="top"><input type="text" size="43"
			name="study.epochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].name"
			class="validate-notEmpty" value="Arm A" /></td>
		<td><textarea
			name="study.epochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].descriptionText"
			rows="2" cols="40" class="validate-MAXLENGTH2000"></textarea></td>
		<td valign="top" align="left"><input type="text"
			name="study.epochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].targetAccrualNumber"
			size="6" maxlength="6" class="validate-numeric" /></td>
		<td valign="top" align="left"><a
			href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(genericEpochRowInserterProps,PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX,-1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

<div id="genericHtml" style="display: none">
<table width="100%">
	<tr valign="top">
		<td><chrome:deletableDivision
			divTitle="divGenericEpochBox-PAGE.ROW.INDEX"
			id="genericEpochBox-PAGE.ROW.INDEX" title="Epoch: "
			onclick="RowManager.deleteRow(genericEpochRowInserterProps,PAGE.ROW.INDEX,-1)">
			<!-- GENERIC START-->

			<table style="border: 0px red dotted;" width="100%">
				<tr>
					<td valign="top" width="50%">

					<table width="100%" border="0" cellspacing="4" cellpadding="2">
						<tr>
							<td align="right"><tags:requiredIndicator /><b><fmt:message key="c3pr.common.name"/></b></td>
							<td align="left"><input type="text"
								name="study.epochs[PAGE.ROW.INDEX].name" size="43"
								class="validate-notEmpty"
								onkeyup="updateName('divGenericEpochBox-PAGE.ROW.INDEX', 'Epoch: ' + this.value);" /><tags:hoverHint
								id="study.treatmentEpoch.name-PAGE.ROW.INDEX"
								keyProp="study.treatmentEpoch.name" /></td>
						</tr>

						<tr>
							<td align="right"><tags:requiredIndicator /><b><fmt:message key="study.epoch.order"/></b></td>
							<td align="left">
								<select name="study.epochs[PAGE.ROW.INDEX].epochOrder" class="epochOrder validate-notEmpty">
									<option value="">Please Select</option>
									<c:forEach var="epochOrder" items="${epochOrders}" varStatus="status">
								        <option value="${epochOrder}">${epochOrder}</option>
								    </c:forEach>
								</select>
								<tags:hoverHint id="study.treatmentEpoch.epochOrder-PAGE.ROW.INDEX"
								keyProp="study.treatmentEpoch.epochOrder" /></td>
						</tr>

						<tr>
							<td align="right"><tags:requiredIndicator /><b><fmt:message key="study.epoch.treating"/></b></td>
							<td align="left"><select
								id="study.epochs[PAGE.ROW.INDEX].treatmentIndicator"
								name="study.epochs[PAGE.ROW.INDEX].treatmentIndicator"
								onchange="manageEnrollingIndicatorSelectBox(this,PAGE.ROW.INDEX);"
								class="validate-notEmpty">
								<option value="">Please Select</option>
								<option value="true">Yes</option>
								<option value="false">No</option>
							</select> <tags:hoverHint
								id="study.nonTreatmentEpoch.treatmentIndicator-PAGE.ROW.INDEX"
								keyProp="study.epoch.treatmentIndicator" /></td>
						</tr>

						<tr>
							<td align="right"><tags:requiredIndicator /><b><fmt:message key="c3pr.common.enrolling"/></b></td>
							<td align="left"><select
								id="study.epochs[PAGE.ROW.INDEX].enrollmentIndicator"
								name="study.epochs[PAGE.ROW.INDEX].enrollmentIndicator"
								onchange="manageEnrollingIndicatorSelectBox(this,PAGE.ROW.INDEX);"
								class="validate-notEmpty">
								<option value="">Please Select</option>
								<option value="true" selected="selected">Yes</option>
								<option value="false">No</option>
							</select> <tags:hoverHint
								id="study.nonTreatmentEpoch.enrollmentIndicator-PAGE.ROW.INDEX"
								keyProp="study.nonTreatmentEpoch.enrollmentIndicator" /></td>
						</tr>

						<c:if test="${command.study.randomizedIndicator == true}">
							<tr>
								<td align="right"><tags:requiredIndicator /><b><fmt:message key="study.randomized"/></b></td>
								<td align="left"><select
									id="study.epochs[PAGE.ROW.INDEX].randomizedIndicator"
									name="study.epochs[PAGE.ROW.INDEX].randomizedIndicator"
									class="validate-notEmpty">
									<option value="">Please Select</option>
									<option value="true" selected="selected">Yes</option>
									<option value="false">No</option>
								</select> <tags:hoverHint
									id="study.treatmentEpoch.randomizedIndicator-PAGE.ROW.INDEX"
									keyProp="study.treatmentEpoch.randomizedIndicator" /></td>
							</tr>
						</c:if>


					</table>

					</td>
					<td valign="top">
					<table width="100%" border="0">
						<tr>
							<td align="right"><b><fmt:message key="c3pr.common.description"/></b></td>
							<td align="left" colspan="3"><textarea
								name="study.epochs[PAGE.ROW.INDEX].descriptionText" rows="2"
								class="validate-MAXLENGTH2000"></textarea><tags:hoverHint
								id="study.treatmentEpoch.description-PAGE.ROW.INDEX"
								keyProp="study.treatmentEpoch.description" /></td>
						</tr>
						<tr>
							<td align="right"><b><fmt:message key="study.accrualCeiling"/></b></td>
							<td align="left"><input type="text"
								id="study.epochs[PAGE.ROW.INDEX].accrualCeiling"
								name="study.epochs[PAGE.ROW.INDEX].accrualCeiling" size="14"
								maxlength="5" class="validate-numeric&&nonzero_numeric" /> <tags:hoverHint
								id="study.nonTreatmentEpoch.accrualCeiling-PAGE.ROW.INDEX"
								keyProp="study.nonTreatmentEpoch.accrualCeiling" />
							</td>
						</tr>
						<tr>
							<td align="right">
							<div id="reservationIndicatorLabel-PAGE.ROW.INDEX"><span
								class=""><tags:requiredIndicator /><b><fmt:message key="study.epoch.reserving"/></b></div>
							</td>
							<td align="left">
							<div id="reservationIndicator-PAGE.ROW.INDEX"><select
								disabled="true" id="study.epochs[PAGE.ROW.INDEX].reservationIndicator"
								name="study.epochs[PAGE.ROW.INDEX].reservationIndicator"
								onchange="manageReservingIndicatorSelectBox(this,PAGE.ROW.INDEX);"
								class="validate-notEmpty">
								<option value="">Please Select</option>
								<option value="true">Yes</option>
								<option value="false" selected="selected">No</option>
							</select> <tags:hoverHint
								id="study.nonTreatmentEpoch.reservationIndicator-PAGE.ROW.INDEX"
								keyProp="study.nonTreatmentEpoch.reservationIndicator" /></div>
							</td>
						</tr>
						<c:if test="${command.study.stratificationIndicator == true}">
							<tr>
								<td align="right"><tags:requiredIndicator /><b><fmt:message key="study.stratified"/></b></td>
								<td align="left"><select
									name="study.epochs[PAGE.ROW.INDEX].stratificationIndicator"
									class="validate-notEmpty">
									<option value="">Please Select</option>
									<option value="true" selected="selected">Yes</option>
									<option value="false">No</option>
								</select> <tags:hoverHint
									id="study.treatmentEpoch.stratificationIndicator-PAGE.ROW.INDEX"
									keyProp="study.epoch.stratificationIndicator" /></td>
							</tr>
						</c:if>
					</table>
					</td>
				</tr>

				<tr>
					<td colspan="3" align="left">
					<table id="arm" class="tablecontent">
						<tr id="h-PAGE.ROW.INDEX">
							<th><span class=""><tags:requiredIndicator /><fmt:message key="study.epoch.arm"/></span><tags:hoverHint
								id="study.arm.name-PAGE.ROW.INDEX" keyProp="study.arm.name" /></th>
							<th><fmt:message key="c3pr.common.description"/><tags:hoverHint
								id="study.arm.description-PAGE.ROW.INDEX"
								keyProp="study.arm.description" /></th>
							<th><fmt:message key="study.accrualCeiling"/><tags:hoverHint
								id="study.arm.targetAccrualNumber-PAGE.ROW.INDEX"
								keyProp="study.arm.targetAccrualNumber" /></th>
							<th></th>
						</tr>
						<tr>
			      			<td  colspan="3" align="left" id="addArmMessage-PAGE.ROW.INDEX"><fmt:message key="epoch.arm.addArm"/></td>
			      		</tr>
					</table>
					<br>
			<tags:button id="addArm-PAGE.ROW.INDEX" type="button" color="blue" icon="add" value="Add Arm"
					onclick="$('addArmMessage-PAGE.ROW.INDEX').hide();javascript:RowManager.addRow(RowManager.getNestedRowInserter(genericEpochRowInserterProps,PAGE.ROW.INDEX));" size="small"/>
					</td>
				</tr>

			</table>
			

			<!-- GENERIC END-->
		</chrome:deletableDivision></td>
	</tr>
</table>
</div>

<!-- DUMMY SECIION END -->

</body>
</html>
