<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
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
       	function manageReservingEpoch(box, index) {
       		if(box.value == 'RESERVING'){
       			$('study.epochs[' + index + '].enrollmentIndicator').value = 'false';
       		}
        }
        
        function manageEnrollmentIndicator(box, index) {
       		if(box.value == 'true' && $('study.epochs['+index+'].type').value == 'RESERVING'){
       			$('study.epochs['+index+'].type').selectedIndex=0;
       		}
        }
        
        var armInserterProps= {
            add_row_division_id: "arm",
            skeleton_row_division_id: "dummy-arm",
            initialIndex: ${fn:length(command.study.epochs[treatmentEpochCount.index].arms)},
            row_index_indicator: "NESTED.PAGE.ROW.INDEX",
            deleteMsgPrefix: "Book Randomization Entries(if any) for this Epoch will be deleted.",
            path: "epochs[PAGE.ROW.INDEX].arms"
        };
        var genericEpochRowInserterProps= {
          	nested_row_inserter: armInserterProps,
            add_row_division_id: "genericEpoch",
            skeleton_row_division_id: "dummy-genericEpoch",
            initialIndex: ${fn:length(command.study.epochs)},
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
<!--[if lte IE 7]>
	<style>
/* IE hasLayout hack for dissapearing content on this page */
		*{zoom:1}
		#taskbar {
			margin-left:-230px;
		}
		#workflow-tabs {
			top:-25px;
		}
		#workflow-tabs li.selected {
			margin-top:-4px;
			padding-top:4px;
		}
		#workflow-tabs li.selected a{
			padding-bottom:1px;
			padding-top:1px;
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
 <tags:errors path="study.epochs[${treatmentEpochCount.index}].type" />
 <tags:errors path="study.epochs[${treatmentEpochCount.index}].enrollmentIndicator" />
	<table width="100%" border="0">
	<tr>
	  <td valign="top" width="50%">
	      <table width="100%" border="0" cellspacing="4" cellpadding="2">
	      <tr>
	          <td align="right"><tags:requiredIndicator /><b><fmt:message key="c3pr.common.name"/></b></td>
	          <td align="left" valign="top">
	              <form:input path="study.epochs[${treatmentEpochCount.index}].name" size="43" cssClass="required validate-notEmpty"
												onkeyup="updateName('genericTitle-${treatmentEpochCount.index}', 'Epoch: ' + this.value);" />
				  <tags:hoverHint id="study.treatmentEpoch.name-${treatmentEpochCount.index}" keyProp="study.treatmentEpoch.name" />
	          </td>
	      </tr>
	      <tr>
	          <td align="right"> 
	          	<tags:requiredIndicator /><b><fmt:message key="study.epoch.order"/></b>
	          </td>
	          <td>
	          		<form:select path="study.epochs[${treatmentEpochCount.index}].epochOrder" id="epochOrder" cssClass="epochOrder required validate-notEmpty">
	          			<option value="">Please Select</option>
	          			<form:options items="${epochOrders}" />
	          		</form:select>
	              <tags:hoverHint id="study.treatmentEpoch.epochorder-${treatmentEpochCount.index}" keyProp="study.treatmentEpoch.epochOrder" />
	          </td>
	      </tr>
	      <tr>
			<td align="right">
			  	<tags:requiredIndicator /><b><fmt:message key="study.epoch.type"/></b> 
			</td>
	        <td>
				<form:select path="study.epochs[${treatmentEpochCount.index}].type"
												cssClass="required validate-notEmpty" onchange="manageReservingEpoch(this,${treatmentEpochCount.index});">
	                    <option value="">Please Select</option>
	                    <form:option value="SCREENING" label="Screening"/>
	                    <form:option value="TREATMENT" label="Treatment"/>
	                    <form:option value="FOLLOWUP" label="Follow-up"/>
	                    <form:option value="RESERVING" label="Reserving"/>
				</form:select>
	            <tags:hoverHint id="study.treatmentEpoch.type-${treatmentEpochCount.index}" keyProp="study.epoch.type" />
			</td>
	      </tr>
	      <tr>
	          <td align="right"><tags:requiredIndicator /><b><fmt:message key="c3pr.common.enrolling"/></b> </td>
	          <td align="left">
	            <form:select id="study.epochs[${treatmentEpochCount.index}].enrollmentIndicator" path="study.epochs[${treatmentEpochCount.index}].enrollmentIndicator"
							cssClass="required validate-notEmpty" onchange="manageEnrollmentIndicator(this,${treatmentEpochCount.index});">
		                    <option value="">Please Select</option>
		                    <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
				</form:select>
			    <tags:hoverHint id="study.nonTreatmentEpoch.enrollmentIndicator-${treatmentEpochCount.index}" keyProp="study.nonTreatmentEpoch.enrollmentIndicator" />
	          </td>
	      </tr>
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
											cssClass="validate-numeric$$nonzero_numeric" />
	                                		<tags:hoverHint
											id="study.nonTreatmentEpoch.accrualCeiling-${treatmentEpochCount.index}"
											keyProp="study.nonTreatmentEpoch.accrualCeiling" />
          </td>
          </tr>
          <c:if test="${command.study.randomizedIndicator== true }">
	      <tr>
	              <td align="right"><tags:requiredIndicator /><b><fmt:message key="study.randomized"/></b> </td>
	              <td>
	                  <form:select
													path="study.epochs[${treatmentEpochCount.index}].randomizedIndicator"
													cssClass="required validate-notEmpty">
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
        
    <c:if test="${command.study.stratificationIndicator== true }">
      <tr>
              <td align="right"><tags:requiredIndicator /><b><fmt:message key="study.stratified"/></b></td>
              <td>
                  <form:select
												path="study.epochs[${treatmentEpochCount.index}].stratificationIndicator"
												cssClass="required validate-notEmpty">
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
	                	<form:input path="study.epochs[${treatmentEpochCount.index}].arms[${statusArms.index}].name" size="43" cssClass="required validate-notEmpty" />
	                </td>
	                <td valign="top">
	                	<form:textarea path="study.epochs[${treatmentEpochCount.index}].arms[${statusArms.index}].descriptionText" rows="2" cols="40" cssClass="validate-MAXLENGTH2000"/>
	                </td>
	                <td valign="top" align="left">
	                	<form:input path="study.epochs[${treatmentEpochCount.index}].arms[${statusArms.index}].targetAccrualNumber" size="6" maxlength="6"
													cssClass="validate-numeric$$nonzero_numeric" />
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
      <div id="armButton-${treatmentEpochCount.index}" style="${command.study.epochs[treatmentEpochCount.index].type=='RESERVING'?'display:none':''}">
      	<tags:button id="addArm-${treatmentEpochCount.index}" type="button" color="blue" icon="add" value="Add Arm"
					onclick="$('addArmMessage-${treatmentEpochCount.index}') != null ? $('addArmMessage-${treatmentEpochCount.index}').hide():''; javascript:RowManager.addRow(RowManager.getNestedRowInserter(genericEpochRowInserterProps,${treatmentEpochCount.index}));" size="small"/>
 	  </div>
  </td>
</tr>
</table>
</div>
<!-- GENERIC END-->
</chrome:deletableDivision>
</td>
</tr>
</c:forEach>
</table>
<!-- BIG TABLE END -->
	<div align="left">
	<tags:button type="button" color="blue" icon="add" value="Add Epoch" size="small"
	onclick="$('dummy-genericEpoch').innerHTML=$('genericHtml').innerHTML;RowManager.addRow(genericEpochRowInserterProps)" />
    <br></div>
</jsp:attribute>

		<jsp:attribute name="localButtons">
			<c:if test="${!empty param.parentStudyFlow}">
			<tags:button type="button" color="blue" icon="back" value="Return to parent" onclick="returnToParentUsingButton('${param.parentStudyFlow}', '${command.study.parentStudyAssociations[0].parentStudy.id}')" />
			</c:if>
		</jsp:attribute>

</tags:tabForm>


<!-- DUMMY SECIION START -->

<div id="dummy-genericEpoch" style="display: none"></div>

<div id="dummy-arm" style="display: none">
<table id="arm" class="tablecontent" width="50%">
	<tr>
		<td valign="top"><input type="text" size="43"
			name="study.epochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].name"
			class="required validate-notEmpty" value="Arm A" /></td>
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
								class="required validate-notEmpty"
								onkeyup="updateName('divGenericEpochBox-PAGE.ROW.INDEX', 'Epoch: ' + this.value);" /><tags:hoverHint
								id="study.treatmentEpoch.name-PAGE.ROW.INDEX"
								keyProp="study.treatmentEpoch.name" /></td>
						</tr>

						<tr>
							<td align="right"><tags:requiredIndicator /><b><fmt:message key="study.epoch.order"/></b></td>
							<td align="left">
								<select name="study.epochs[PAGE.ROW.INDEX].epochOrder" class="epochOrder required validate-notEmpty">
									<option value="">Please Select</option>
									<c:forEach var="epochOrder" items="${epochOrders}" varStatus="status">
								        <option value="${epochOrder}">${epochOrder}</option>
								    </c:forEach>
								</select>
								<tags:hoverHint id="study.treatmentEpoch.epochOrder-PAGE.ROW.INDEX"
								keyProp="study.treatmentEpoch.epochOrder" /></td>
						</tr>

						<tr>
							<td align="right"><tags:requiredIndicator /><b><fmt:message key="study.epoch.type"/></b></td>
							<td align="left"><select id="study.epochs[PAGE.ROW.INDEX].type"
								name="study.epochs[PAGE.ROW.INDEX].type"
								onchange="manageReservingEpoch(this,PAGE.ROW.INDEX);"
								class="required validate-notEmpty">
								<option value="">Please Select</option>
								<option value="SCREENING">Screening</option>
			                    <option value="TREATMENT">Treatment</option>
			                    <option value="FOLLOWUP">Follow-up</option>
			                    <option value="RESERVING">Reserving</option>
							</select> <tags:hoverHint id="study.treatmentEpoch.type-PAGE.ROW.INDEX"
								keyProp="study.epoch.type" /></td>
						</tr>

						<tr>
							<td align="right"><tags:requiredIndicator /><b><fmt:message key="c3pr.common.enrolling"/></b></td>
							<td align="left"><select
								id="study.epochs[PAGE.ROW.INDEX].enrollmentIndicator"
								name="study.epochs[PAGE.ROW.INDEX].enrollmentIndicator"
								class="required validate-notEmpty"  onchange="manageEnrollmentIndicator(this,PAGE.ROW.INDEX);">
								<option value="">Please Select</option>
								<option value="true" selected="selected">Yes</option>
								<option value="false">No</option>
							</select> <tags:hoverHint
								id="study.nonTreatmentEpoch.enrollmentIndicator-PAGE.ROW.INDEX"
								keyProp="study.nonTreatmentEpoch.enrollmentIndicator" /></td>
						</tr>
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
								maxlength="5" class="validate-numeric$$nonzero_numeric" /> <tags:hoverHint
								id="study.nonTreatmentEpoch.accrualCeiling-PAGE.ROW.INDEX"
								keyProp="study.nonTreatmentEpoch.accrualCeiling" />
							</td>
						</tr>
						<c:if test="${command.study.randomizedIndicator == true}">
							<tr>
								<td align="right"><tags:requiredIndicator /><b><fmt:message key="study.randomized"/></b></td>
								<td align="left"><select
									id="study.epochs[PAGE.ROW.INDEX].randomizedIndicator"
									name="study.epochs[PAGE.ROW.INDEX].randomizedIndicator"
									class="required validate-notEmpty">
									<option value="">Please Select</option>
									<option value="true" selected="selected">Yes</option>
									<option value="false">No</option>
								</select> <tags:hoverHint
									id="study.treatmentEpoch.randomizedIndicator-PAGE.ROW.INDEX"
									keyProp="study.treatmentEpoch.randomizedIndicator" /></td>
							</tr>
						</c:if>
						<c:if test="${command.study.stratificationIndicator == true}">
							<tr>
								<td align="right"><tags:requiredIndicator /><b><fmt:message key="study.stratified"/></b></td>
								<td align="left"><select
									name="study.epochs[PAGE.ROW.INDEX].stratificationIndicator"
									class="required validate-notEmpty">
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
