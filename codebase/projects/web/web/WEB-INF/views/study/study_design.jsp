
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
                Effect.OpenUp('reservationIndicator-'+index);
            }
            if (box.value == 'true') {
                		//document.getElementById('reservationIndicator').style.display='none';
                Effect.CloseDown('reservationIndicator-'+index);
            }
        }
        
        var armInserterProps= {
            add_row_division_id: "arm",
            skeleton_row_division_id: "dummy-arm",
            initialIndex: ${fn:length(command.treatmentEpochs[treatmentEpochCount.index].arms)},
            softDelete: ${softDelete == 'true'},
            row_index_indicator: "NESTED.PAGE.ROW.INDEX",
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
<tags:formPanelBox tab="${tab}" flow="${flow}" formName="studyDesignForm">

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
                                        onDelete="RowManager.deleteRow(treatmentEpochRowInserterProps,${treatmentEpochCount.index})">

                <table>
                    <tr>
                        <td valign="top">
                            <table class="tablecontent">
                                <tr>
                                    <th>
                                        <span class="required-indicator">Name:</span>
                                        
                                    <td align="left"><form:input
                                            path="treatmentEpochs[${treatmentEpochCount.index}].name"
                                            size="41" cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
                                </tr>
                                <tr>
                                    <th>Description:</th>
                                    <td><form:textarea
                                            path="treatmentEpochs[${treatmentEpochCount.index}].descriptionText"
                                            rows="5" cols="40" />&nbsp;&nbsp;&nbsp;</td>
                                </tr>
                            </table>
                        </td>
                        <td valign="top">
                            <table  id="arm"
                                    class="tablecontent">
                                <tr>
                                    <th><span class="required-indicator">Arm </span></th>
                                    <th>Accrual Ceiling</th>
                                    <th><input id="addArm" type="button"
                                               value="Add Arm"
                                               onclick="javascript:RowManager.addRow(RowManager.getNestedRowInserter(treatmentEpochRowInserterProps,${treatmentEpochCount.index}));" /></th>
                                </tr>

                                <c:forEach items="${treatmentEpoch.arms}" var="arm"
                                           varStatus="statusArms">
                                    <tr id="arm-${statusArms.index}">
                                        <td><form:input
                                                path="treatmentEpochs[${treatmentEpochCount.index}].arms[${statusArms.index}].name"
                                                cssClass="validate-notEmpty" /></td>
                                        <td><form:input
                                                path="treatmentEpochs[${treatmentEpochCount.index}].arms[${statusArms.index}].targetAccrualNumber"
                                                size="12" maxlength="10" cssClass="validate-numeric" /></td>
                                        <td><a
                                                href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(treatmentEpochRowInserterProps,${treatmentEpochCount.index}),${statusArms.index });"><img
                                                src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                </table>
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
                                     onDelete="RowManager.deleteRow(nonTreatmentEpochRowInserterProps,${nonTreatmentEpochCount.index})">
                <table width="100%" border="0">
                    <tr><td>
                        <div class="leftpanel">
                            <div class="row">
                                <div class="label">
                                    <span class="required-indicator">Name:</span>
                                    </div>
                                <div class="value"><form:input
                                        path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].name"
                                        size="41" cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span>
                                </div>
                            </div>
                            <div class="row">
                                <div class="label">Description:</div>
                                <div class="value"><form:textarea
                                        path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].descriptionText"
                                        rows="5" cols="40" />&nbsp;&nbsp;&nbsp;</div>
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
                                    <option value="">--Please Select--</option>
                                     <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
                                </form:select></div>
                            </div>
                            
                            <div  id="accrualCeiling-${nonTreatmentEpochCount.index}" <c:if test="${command.nonTreatmentEpochs[nonTreatmentEpochCount.index].accrualIndicator==false}">
                                      style="display:none;"</c:if> >
                            <div class="row">
                                <div class="label">Accrual Ceiling:</div>
                                <div class="value"><form:input
                                        path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].accrualCeiling"
                                        size="12" maxlength="10" cssClass="validate-numeric" /></div>
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
                                    <option value="">--Please Select--</option>
                                    <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
                                </form:select></div>
                            </div>
                            
                            <div id="reservationIndicator-${nonTreatmentEpochCount.index}" <c:if test="${command.nonTreatmentEpochs[nonTreatmentEpochCount.index].enrollmentIndicator==true}">style="display:none;"</c:if>>
                            <div class="row">
                                <div class="label"> <span class="required-indicator">Reservation Indicator:</span>
                                    </div>
                                <div class="value"><form:select
                                        path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].reservationIndicator"
                                        cssClass="validate-notEmpty">
                                    <option value="">--Please Select--</option>
                                     <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
                                </form:select></div>
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

</tags:formPanelBox>
<div id="dummy-treatmentEpoch" style="display:none"></div>

<div id="dummy-nonTreatmentEpoch" style="display:none"></div>

<div id="treatmentHtml" style="display:none">

    <table>
        <tr>
            <td><chrome:minimizableBox id="AddedTreatmentBox-PAGE.ROW.INDEX"
                                     title="New Treatment Epoch"
                                     isDeletable="true"
                                     onDelete="RowManager.deleteRow(treatmentEpochRowInserterProps,PAGE.ROW.INDEX)">
                <table>
                    <tr>
                        <td>
                            <table  class="tablecontent" style="border:none;">
                                <tr>
                                    <th>
                                        <span class="required-indicator">Name:</span>
                                        
                                    </th>
                                    <td><input type="text"
                                               name="treatmentEpochs[PAGE.ROW.INDEX].name" size="41"
                                               class="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
                                </tr>
                                <tr>
                                    <th>Description:</th>
                                    <td><textarea
                                            name="treatmentEpochs[PAGE.ROW.INDEX].descriptionText" rows="5"
                                            cols="40"></textarea></td>
                                </tr>
                            </table>
                        </td>
                        <td valign="top">
                            <table  id="arm"
                                     class="tablecontent">
                                <tr>
                                    <th><span class="required-indicator">Arm </span></th>
                                    <th>Accrual Ceiling</th>
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
            <td><input type="text"
                       name="treatmentEpochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].name"
                       class="validate-notEmpty" value="Arm A" /></td>
            <td><input type="text"
                       name="treatmentEpochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].targetAccrualNumber"
                       size="12" maxlength="10" class="validate-numeric" /></td>
            <td><a
                    href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(treatmentEpochRowInserterProps,PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX);"><img
                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
        </tr>
    </table>
</div>

<div id="non-treatmentHtml" style="display:none">
    <table>
        <tr>
            <td><chrome:minimizableBox      id="AddedNonTreatmentEpochBox-PAGE.ROW.INDEX"
                                          title="New Non-Treatment Epoch"
                                          isDeletable="true"
                                          onDelete="RowManager.deleteRow(nonTreatmentEpochRowInserterProps,PAGE.ROW.INDEX)">
                <table width="100%" border="0">
                    <tr><td>
                        <div class="leftpanel">
                            <div class="row">
                                <div class="label">
                                    <span class="required-indicator">Name:</span>
                                    </div>
                                <div class="value"><input type="text" size="41"
                                                          name="nonTreatmentEpochs[PAGE.ROW.INDEX].name"
                                                          class="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span></div>
                            </div>

                            <div class="row">
                                <div class="label">Description:</div>
                                <div class="value"><textarea
                                        name="nonTreatmentEpochs[PAGE.ROW.INDEX].descriptionText" rows="5"
                                        cols="40"></textarea></div>
                            </div>
                        </div>

                        <div class="rightpanel">
                            <div class="row">
                                <div class="label"><span class="required-indicator">Accrual Indicator:</span>
                                    </div>
                                <div class="value"><select
                                        name="nonTreatmentEpochs[PAGE.ROW.INDEX].accrualIndicator" onchange="manageAccrualIndicatorSelectBox(this,PAGE.ROW.INDEX);"
                                        class="validate-notEmpty">
                                    <option value="">--Please Select--</option>
                                    <option value="true">Yes</option>
                                    <option value="false">No</option>
                                </select></div>
                            </div>
                            
                            <div id="accrualCeiling-PAGE.ROW.INDEX" style="display:none">
                            <div class="row">
                                <div class="label">Accrual Ceiling:</div>
                                <div class="value"><input type="text"
                                                          id="nonTreatmentEpochs[PAGE.ROW.INDEX].accrualCeiling"
                                                          name="nonTreatmentEpochs[PAGE.ROW.INDEX].accrualCeiling" size="12"
                                                          maxlength="10" class="validate-numeric" /></div>
                            </div>
                            </div>

                            <div class="row">
                                <div class="label"><span class="required-indicator">Enrollment Indicator:</span>
                                    </div>
                                <div class="value"><select
                                        id="nonTreatmentEpochs[PAGE.ROW.INDEX].enrollmentIndicator"
                                        name="nonTreatmentEpochs[PAGE.ROW.INDEX].enrollmentIndicator" onchange="manageEnrollingIndicatorSelectBox(this,PAGE.ROW.INDEX);"
                                        class="validate-notEmpty">
                                    <option value="">--Please Select--</option>
                                    <option value="true">Yes</option>
                                    <option value="false">No</option>
                                </select></div>
                            </div>
                            
                            <div id="reservationIndicator-PAGE.ROW.INDEX" style="display:none">
                            <div class="row">
                                <div class="label"><span>Reservation Indicator:</span>
                                    </div>
                                <div class="value"><select
                                        id="nonTreatmentEpochs[PAGE.ROW.INDEX].reservationIndicator"
                                        name="nonTreatmentEpochs[PAGE.ROW.INDEX].reservationIndicator">
                                    <option value="">--Please Select--</option>
                                    <option value="true">Yes</option>
                                    <option value="false">No</option>
                                </select></div>
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
