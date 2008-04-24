
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="/c3pr/public/css/tables1" />
    <script type="text/javascript">
     
     function manageAccrualIndicatorSelectBox(box,index) {
            if (box.value == 'true') {
                //		document.getElementById('accrualCeiling-index').style.display='show';
                Effect.OpenUp('accrualCeiling-'+index);
            }
            if (box.value == 'false') {
                	//	document.getElementById('accrualCeiling-index').style.display='none';
                Effect.CloseDown('accrualCeiling-'+index);
            }
        }
        
         function manageEnrollingIndicatorSelectBox(box,index) {
            if (box.value == 'false') {
                		//document.getElementById('reservationIndicator').style.display='show';
           //   Form.reset('reservationIndicator-'+index);
                Effect.OpenUp('reservationIndicator-'+index);
            }
            if (box.value == 'true') {
             	//document.getElementById('reservationIndicator').style.display='none';
                Effect.CloseDown('reservationIndicator-'+index);
             $('nonTreatmentEpochs['+index+'].reservationIndicator').value=false;
            }
        }
        
        var armInserterProps= {
            add_row_division_id: "arm",
            skeleton_row_division_id: "dummy-arm",
            initialIndex: ${fn:length(command.treatmentEpochs[treatmentEpochCount.index].arms)},
            softDelete: ${softDelete == 'true'},
            row_index_indicator: "NESTED.PAGE.ROW.INDEX",
            deleteMsgPrefix: "Book Randomization Entries(if any) for this Treatment Epoch will be deleted.",
            path: "treatmentEpochs[PAGE.ROW.INDEX].arms"
        };
        var treatmentEpochRowInserterProps= {
            nested_row_inserter: armInserterProps,
            add_row_division_id: "treatmentEpoch",
            skeleton_row_division_id: "dummy-treatmentEpoch",
            initialIndex: ${fn:length(command.treatmentEpochs)},
            softDelete: ${softDelete == 'true'},
            path: "treatmentEpochs"
        };
        var nonTreatmentEpochRowInserterProps= {
            add_row_division_id: "nonTreatmentEpoch",
            skeleton_row_division_id: "dummy-nonTreatmentEpoch",
            initialIndex: ${fn:length(command.nonTreatmentEpochs)},
            softDelete: ${softDelete == 'true'},
            path: "nonTreatmentEpochs"
        };
        RowManager.addRowInseter(treatmentEpochRowInserterProps);
        RowManager.addRowInseter(nonTreatmentEpochRowInserterProps);
        RowManager.registerRowInserters();
    </script>

</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}"
	formName="studyDesignForm" displayErrors="false">
	<jsp:attribute name="singleFields">
	
	<tags:errors path="epochs"/> 

<div><input type="hidden" name="_action" value=""> <input
        type="hidden" name="_selectedEpoch" value=""> <input type="hidden"
                                                             name="_selectedArm" value=""></div>

<table border="0" cellspacing="0" cellpadding="0" id="treatmentEpoch"
       width="100%">
    <tr>
        <td></td>
    </tr>
    <c:forEach items="${command.treatmentEpochs}" var="treatmentEpoch"
               varStatus="treatmentEpochCount">
		
        <tr id="treatmentEpoch-${treatmentEpochCount.index}">
            <script>
                RowManager.getNestedRowInserter(treatmentEpochRowInserterProps,${treatmentEpochCount.index}).updateIndex(${fn:length(command.treatmentEpochs[treatmentEpochCount.index].arms)});
            </script>

            <td><chrome:minimizableBox  id="treatmentEpochBox-${treatmentEpochCount.index}"
                                        title="Treatment Epoch"
                                        isDeletable="true"
                                        onDelete="RowManager.deleteRow(treatmentEpochRowInserterProps,${treatmentEpochCount.index},'${treatmentEpoch.id==null?'HC#':'ID#'}${treatmentEpoch.id==null?treatmentEpoch.hashCode:treatmentEpoch.id}')">

                <table>
                    <tr align="center">
                        <td width="50%" valign="top">
                            <table>
                                <tr>
                                    <td align="right">
                                        <span class="required-indicator"><b>Name:</b></span>
                                    </td>
                                        
                                    <td align="left"><form:input
                                            path="treatmentEpochs[${treatmentEpochCount.index}].name"
                                            size="43" cssClass="validate-notEmpty" />
                                        <tags:hoverHint id="study.treatmentEpoch.name-${treatmentEpochCount.index}" keyProp="study.treatmentEpoch.name"/></td>
                                </tr>
                                <tr>
                                    <td align="right"><b>Description:</b></td>
                                    <td><form:textarea
                                            path="treatmentEpochs[${treatmentEpochCount.index}].descriptionText"
                                            rows="5" cols="40" />
                                        <tags:hoverHint id="study.treatmentEpoch.description-${treatmentEpochCount.index}" keyProp="study.treatmentEpoch.description"/></td>
                                </tr>
                                 <tr>
                                    <td align="right">
                                        <span class="required-indicator"><b>Order:</b></span>
                                        
                                    </td>
                                    <td><form:input path="treatmentEpochs[${treatmentEpochCount.index}].epochOrder" size="5" maxlength="1"
                                               cssClass="validate-notEmpty&&numeric" />
                                        <tags:hoverHint id="study.treatmentEpoch.epochorder-${treatmentEpochCount.index}" keyProp="study.treatmentEpoch.epochOrder"/></td>
                                </tr>
                                <c:if test="${command.randomizedIndicator== true}">
                                 <tr>
                                    <td align="right">
                                        <span class="required-indicator"><b>Randomized:</b></span>
                                    </td>
                                    <td><form:select
                                        path="treatmentEpochs[${treatmentEpochCount.index}].randomizedIndicator"
                                        cssClass="validate-notEmpty">
                                   <option value="">Please Select</option>
                                     <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
                                </form:select><tags:hoverHint id="study.treatmentEpoch.randomizedIndicator-${treatmentEpochCount.index}" keyProp="study.treatmentEpoch.randomizedIndicator"/></td>
                                </tr>
                                </c:if>
                            </table>
                        </td>
                        <td width="50%" valign="top">
                        <tags:errors path="treatmentEpochs[${treatmentEpochCount.index}].arms"/>
                            <table  id="arm"
                                    class="tablecontent">
                                <tr>
                                    <th><span class="required-indicator">Arm </span>
                                    <tags:hoverHint id="study.arm.name-${treatmentEpochCount.index}" keyProp="study.arm.name"/></th>
                                    <th>Description<tags:hoverHint id="study.arm.description-${treatmentEpochCount.index}" keyProp="study.arm.description"/></th>
                                    <th>Accrual Ceiling<tags:hoverHint id="study.arm.targetAccrualNumber-${treatmentEpochCount.index}" keyProp="study.arm.targetAccrualNumber"/></th>
                                    <th><input id="addArm" type="button"
                                               value="Add Arm"
                                               onclick="javascript:RowManager.addRow(RowManager.getNestedRowInserter(treatmentEpochRowInserterProps,${treatmentEpochCount.index}));" /></th>
                                </tr>

                                <c:forEach items="${treatmentEpoch.arms}" var="arm"
                                           varStatus="statusArms">
                                    <tr id="arm-${statusArms.index}">
                                        <td valign="top"><form:input
                                                path="treatmentEpochs[${treatmentEpochCount.index}].arms[${statusArms.index}].name" size="12"
                                                cssClass="validate-notEmpty" /></td>
                                        <td valign="top"><form:textarea
                                                path="treatmentEpochs[${treatmentEpochCount.index}].arms[${statusArms.index}].descriptionText"
                      					 rows="3" cols="20" /></td>
                                        <td valign="top"><form:input
                                                path="treatmentEpochs[${treatmentEpochCount.index}].arms[${statusArms.index}].targetAccrualNumber"
                                                size="6" maxlength="6" cssClass="validate-numeric&&nonzero_numeric" /></td>
                                        <td valign="top">
                                        	<a href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(treatmentEpochRowInserterProps,${treatmentEpochCount.index}),${statusArms.index },'${arm.id==null?'HC#':'ID#'}${arm.id==null?arm.hashCode:arm.id}');">
                                        	<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                </table>
                <div id="dummyDiv"></div>
            </chrome:minimizableBox></td>
        </tr>
    </c:forEach>
</table>
<table border="0" cellspacing="0" cellpadding="0"
       id="nonTreatmentEpoch" width="100%">
    <tr>
        <td></td>
    </tr>
    <c:forEach items="${command.nonTreatmentEpochs}"
               var="nonTreatmentEpoch" varStatus="nonTreatmentEpochCount">

        <tr id="nonTreatmentEpoch-${nonTreatmentEpochCount.index}">

            <td><chrome:minimizableBox id="NonTreatmentEpochBox-${nonTreatmentEpochCount.index}" title="Non-Treatment Epoch"
                                     isDeletable="true"
                                     onDelete="RowManager.deleteRow(nonTreatmentEpochRowInserterProps,${nonTreatmentEpochCount.index},'${nonTreatmentEpoch.id==null?'HC#':'ID#'}${nonTreatmentEpoch.id==null?nonTreatmentEpoch.hashCode:nonTreatmentEpoch.id}')">
                <table width="100%" border="0">
                    <tr><td>
                        <div class="leftpanel">
                            <div class="row">
                                <div class="label">
                                    <span class="required-indicator">Name:</span>
                                    </div>
                                <div class="value"><form:input
                                        path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].name"
                                        size="41" cssClass="validate-notEmpty" />
                                <tags:hoverHint id="study.nonTreatmentEpoch.name-${nonTreatmentEpochCount.index}" keyProp="study.nonTreatmentEpoch.name"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="label">Description:</div>
                                <div class="value"><form:textarea
                                        path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].descriptionText"
                                        rows="5" cols="40" />
                                 <tags:hoverHint id="study.nonTreatmentEpoch.description-${nonTreatmentEpochCount.index}" keyProp="study.nonTreatmentEpoch.description"/></div>
                            </div>
                            <div class="row">
                                <div class="label">
                                    <span class="required-indicator">Order:</span>
                                    </div>
                                <div class="value"><form:input size="5" maxlength="1"
                                                          path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].epochOrder"
                                                          cssClass="validate-notEmpty&&numeric" />
                                <tags:hoverHint id="study.nonTreatmentEpoch.epochOrder-${nonTreatmentEpochCount.index}" keyProp="study.nonTreatmentEpoch.epochOrder"/></div>
                            </div>
                        </div>

                        <div class="rightpanel">
                            <div class="row">
                                <div class="label"><span class="required-indicator">Accrual Indicator:</span>
                                    </div>
                                <div class="value"><form:select
                                        path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].accrualIndicator"
                                        onchange="manageAccrualIndicatorSelectBox(this,${nonTreatmentEpochCount.index});"
                                        cssClass="validate-notEmpty">
                                    <option value="">Please Select</option>
                                     <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
                                </form:select>
                                <tags:hoverHint id="study.nonTreatmentEpoch.accrualIndicator-${nonTreatmentEpochCount.index}" keyProp="study.nonTreatmentEpoch.accrualIndicator"/></div>
                            </div>
                            
                            <div  id="accrualCeiling-${nonTreatmentEpochCount.index}" <c:if test="${command.nonTreatmentEpochs[nonTreatmentEpochCount.index].accrualIndicator==false}">
                                      style="display:none;"</c:if> >
                            <div class="row">
                                <div class="label">Accrual Ceiling:</div>
                                <div class="value"><form:input
                                        path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].accrualCeiling"
                                        size="12" maxlength="5" cssClass="validate-numeric&&nonzero_numeric" />
                                        <tags:hoverHint id="study.nonTreatmentEpoch.accrualCeiling-${nonTreatmentEpochCount.index}" keyProp="study.nonTreatmentEpoch.accrualCeiling"/></div>
                            </div>
                            </div>

                            <div class="row">
                                <div class="label"> <span class="required-indicator">Enrollment Indicator:</span>
                                    </div>
                                <div class="value"><form:select
                                        id="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].enrollmentIndicator"
                                        path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].enrollmentIndicator"
                                        onchange="manageEnrollingIndicatorSelectBox(this,${nonTreatmentEpochCount.index});"
                                        cssClass="validate-notEmpty">
                                    <option value="">Please Select</option>
                                    <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
                                </form:select><tags:hoverHint id="study.nonTreatmentEpoch.enrollmentIndicator-${nonTreatmentEpochCount.index}" keyProp="study.nonTreatmentEpoch.enrollmentIndicator"/></div>
                            </div>
                            
                            <div id="reservationIndicator-${nonTreatmentEpochCount.index}" <c:if test="${command.nonTreatmentEpochs[nonTreatmentEpochCount.index].enrollmentIndicator==true}">style="display:none;"</c:if>>
                            <div class="row">
                                <div class="label"> <span class="required-indicator">Reservation Indicator:</span>
                                    </div>
                                <div class="value"><form:select
                                        path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].reservationIndicator"
                                        cssClass="validate-notEmpty">
                                    <option value="">Please Select</option>
                                     <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
                                </form:select><tags:hoverHint id="study.nonTreatmentEpoch.reservationIndicator-${nonTreatmentEpochCount.index}" keyProp="study.nonTreatmentEpoch.reservationIndicator"/></div>
                            </div>
                            </div>
                        </div>
                    </td></tr></table>
            </chrome:minimizableBox></td>

        </tr>
        &nbsp;&nbsp;
    </c:forEach>
</table>
<div align="right">
    <input id="addEpoch" type="button" value="Add Treatment Epoch"
           onclick="$('dummy-treatmentEpoch').innerHTML=$('treatmentHtml').innerHTML;RowManager.addRow(treatmentEpochRowInserterProps)" />

    <input id="addEpoch" type="button" value="Add Non-Treatment Epoch"
           onclick="$('dummy-nonTreatmentEpoch').innerHTML=$('non-treatmentHtml').innerHTML;RowManager.addRow(nonTreatmentEpochRowInserterProps)" />
    <br>
    </div>

</jsp:attribute>
</tags:tabForm>
<div id="dummy-treatmentEpoch" style="display:none"></div>

<div id="dummy-nonTreatmentEpoch" style="display:none"></div>

<div id="treatmentHtml" style="display:none">

    <table>
        <tr valign="top">
            <td><chrome:minimizableBox id="AddedTreatmentBox-PAGE.ROW.INDEX"
                                     title="Treatment Epoch"
                                     isDeletable="true"
                                     onDelete="RowManager.deleteRow(treatmentEpochRowInserterProps,PAGE.ROW.INDEX,-1)">
                <table>
                    <tr>
                        <td>
                            <table style="border:none;">
                                <tr>
                                    <td align="right">
                                        <span class="required-indicator"><b>Name:</b></span>
                                        
                                    </td>
                                    <td><input type="text"
                                               name="treatmentEpochs[PAGE.ROW.INDEX].name" size="43"
                                               class="validate-notEmpty" /><tags:hoverHint id="study.treatmentEpoch.name-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.name"/></td>
                                </tr>
                                <tr>
                                    <td align="right"><b>Description:<b></td>
                                    <td><textarea
                                            name="treatmentEpochs[PAGE.ROW.INDEX].descriptionText" rows="5"
                                            cols="40"></textarea><tags:hoverHint id="study.treatmentEpoch.description-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.description"/></td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        <span class="required-indicator"><b>Order:<b></span>
                                        
                                    </td>
                                    <td><input type="text"
                                               name="treatmentEpochs[PAGE.ROW.INDEX].epochOrder" size="5" maxlength="1"
                                               class="validate-notEmpty&&numeric" /><tags:hoverHint id="study.treatmentEpoch.epochOrder-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.epochOrder"/></td>
                                </tr>
                                 <c:if test="${command.randomizedIndicator== true}">
                                 <tr>
                                    <td align="right">
                                        <span class="required-indicator"><b>Randomized:<b></span>
                                        
                                    </td>
                                    <td><select
                                        name="treatmentEpochs[PAGE.ROW.INDEX].randomizedIndicator" 
                                        class="validate-notEmpty">
                                    <option value="">Please Select</option>
                                    <option value="true" selected="selected">Yes</option>
                                    <option value="false">No</option>
                                </select><tags:hoverHint id="study.treatmentEpoch.randomizedIndicator-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.randomizedIndicator"/></td>
                                </tr>
                                </c:if>
                            </table>
                        </td>
                        <td valign="top">
                            <table  id="arm"
                                     class="tablecontent">
                                <tr>
                                    <th><span class="required-indicator">Arm</span><tags:hoverHint id="study.arm.name-PAGE.ROW.INDEX" keyProp="study.arm.name"/></th>
                                    <th>Description<tags:hoverHint id="study.arm.description-PAGE.ROW.INDEX" keyProp="study.arm.description"/></th>
                                    <th>Accrual Ceiling<tags:hoverHint id="study.arm.targetAccrualNumber-PAGE.ROW.INDEX" keyProp="study.arm.targetAccrualNumber"/></th>
                                    <th><input id="addArm" type="button" value="Add Arm"
                                               onclick="javascript:RowManager.addRow(RowManager.getNestedRowInserter(treatmentEpochRowInserterProps,PAGE.ROW.INDEX));" /></th>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </chrome:minimizableBox></td>
        </tr>
    </table>
</div>

<div id="dummy-arm" style="display:none">
    <table id="arm" class="tablecontent" width="50%">
        <tr>
            <td valign="top"><input type="text" size="12"
                       name="treatmentEpochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].name"
                       class="validate-notEmpty" value="Arm A" /></td>
            <td><textarea
                       name="treatmentEpochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].descriptionText"
                       rows="3" cols="20"></textarea></td>
            <td valign="top"><input type="text"
                       name="treatmentEpochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].targetAccrualNumber"
                       size="6" maxlength="6" class="validate-numeric" /></td>
            <td valign="top"><a
                    href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(treatmentEpochRowInserterProps,PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX,-1);"><img
                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
        </tr>
    </table>
</div>

<div id="non-treatmentHtml" style="display:none">
    <table>
        <tr>
            <td><chrome:minimizableBox  id="AddedNonTreatmentEpochBox-PAGE.ROW.INDEX"
                                          title="Non-Treatment Epoch"
                                          isDeletable="true"
                                          onDelete="RowManager.deleteRow(nonTreatmentEpochRowInserterProps,PAGE.ROW.INDEX,-1)">
                <table width="100%" border="0">
                    <tr><td>
                    
                        <div class="leftpanel">
                            <div class="row">
                                <div class="label">
                                    <span class="required-indicator">Name:</span>
                                    </div>
                                <div class="value"><input type="text" size="41"
                                                          name="nonTreatmentEpochs[PAGE.ROW.INDEX].name"
                                                          class="validate-notEmpty" />
                                <tags:hoverHint id="study.nonTreatmentEpoch.name-PAGE.ROW.INDEX" keyProp="study.nonTreatmentEpoch.name"/></div>
                            </div>

                            <div class="row">
                                <div class="label">Description:</div>
                                <div class="value"><textarea
                                        name="nonTreatmentEpochs[PAGE.ROW.INDEX].descriptionText" rows="5"
                                        cols="40"></textarea>
                                <tags:hoverHint id="study.nonTreatmentEpoch.description-PAGE.ROW.INDEX" keyProp="study.nonTreatmentEpoch.description"/></div>
                            </div>
                            <div class="row">
                                <div class="label">
                                    <span class="required-indicator">Order:</span>
                                    </div>
                                <div class="value"><input type="text" size="5" maxlength="1"
                                                          name="nonTreatmentEpochs[PAGE.ROW.INDEX].epochOrder"
                                                          class="validate-notEmpty&&numeric" />
                                <tags:hoverHint id="study.nonTreatmentEpoch.epochOrder-PAGE.ROW.INDEX" keyProp="study.nonTreatmentEpoch.epochOrder"/></div>
                            </div>
                        </div>

                        <div class="rightpanel">
                            <div class="row">
                                <div class="label"><span class="required-indicator">Accrual Indicator:</span>
                                    </div>
                                <div class="value"><select
                                        name="nonTreatmentEpochs[PAGE.ROW.INDEX].accrualIndicator" onchange="manageAccrualIndicatorSelectBox(this,PAGE.ROW.INDEX);"
                                        class="validate-notEmpty">
                                    <option value="">Please Select</option>
                                    <option value="true">Yes</option>
                                    <option value="false">No</option>
                                </select><tags:hoverHint id="study.nonTreatmentEpoch.accrualIndicator-PAGE.ROW.INDEX" keyProp="study.nonTreatmentEpoch.accrualIndicator"/></div>
                            </div>
                            
                            <div id="accrualCeiling-PAGE.ROW.INDEX" style="display:none">
                            <div class="row">
                                <div class="label">Accrual Ceiling:</div>
                                <div class="value"><input type="text"
                                                          id="nonTreatmentEpochs[PAGE.ROW.INDEX].accrualCeiling"
                                                          name="nonTreatmentEpochs[PAGE.ROW.INDEX].accrualCeiling" size="12"
                                                          maxlength="5" class="validate-numeric&&nonzero_numeric" />
                                <tags:hoverHint id="study.nonTreatmentEpoch.accrualCeiling-PAGE.ROW.INDEX" keyProp="study.nonTreatmentEpoch.accrualCeiling"/></div>
                            </div>
                            </div>

                            <div class="row">
                                <div class="label"><span class="required-indicator">Enrollment Indicator:</span>
                                    </div>
                                <div class="value"><select
                                        id="nonTreatmentEpochs[PAGE.ROW.INDEX].enrollmentIndicator"
                                        name="nonTreatmentEpochs[PAGE.ROW.INDEX].enrollmentIndicator" onchange="manageEnrollingIndicatorSelectBox(this,PAGE.ROW.INDEX);"
                                        class="validate-notEmpty">
                                    <option value="">Please Select</option>
                                    <option value="true">Yes</option>
                                    <option value="false">No</option>
                                </select><tags:hoverHint id="study.nonTreatmentEpoch.enrollmentIndicator-PAGE.ROW.INDEX" keyProp="study.nonTreatmentEpoch.enrollmentIndicator"/></div>
                            </div>
                            
                            <div id="reservationIndicator-PAGE.ROW.INDEX" style="display:none">
                            <div class="row">
                                <div class="label"><span class="required-indicator">Reservation Indicator:</span>
                                    </div>
                                <div class="value"><select
                                        id="nonTreatmentEpochs[PAGE.ROW.INDEX].reservationIndicator"
                                        name="nonTreatmentEpochs[PAGE.ROW.INDEX].reservationIndicator"
                                        class="validate-notEmpty">
                                    <option value="">Please Select</option>
                                    <option value="true">Yes</option>
                                    <option value="false">No</option>
                                </select><tags:hoverHint id="study.nonTreatmentEpoch.reservationIndicator-PAGE.ROW.INDEX" keyProp="study.nonTreatmentEpoch.reservationIndicator"/></div>
                            </div>
                            </div>
                        </div>
                    </td></tr></table>
            </chrome:minimizableBox></td>
        </tr>
    </table>
</div>
</body>
</html>
