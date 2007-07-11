
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="/c3pr/public/css/tables1" />
    <script type="text/javascript">
        function fireAction(action, selectedEpoch, selectedArm) {
            document.getElementById('command')._target.name = '_noname';
            document.studyDesignForm._action.value = action;
            document.studyDesignForm._selectedEpoch.value = selectedEpoch;
            document.studyDesignForm._selectedArm.value = selectedArm;
            document.studyDesignForm.submit();
        }
        function fireActionNontTreatmentEpoch(action, selectedEpoch) {
            document.getElementById('command')._target.name = '_noname';
            document.studyDesignForm._action.value = action;
            document.studyDesignForm._selectedEpoch.value = selectedEpoch;
            document.studyDesignForm.submit();
        }
        var instanceRowEpoch = {
            add_row_division_id: "addRowTable", 	        /* this id belongs to element where the row would be appended to */
            skeleton_row_division_id: "dummy-row",  	/* this id belongs to the element which hold the dummy row html to be inserted   */
            initialIndex: ${fn:length(command.epochs)},                            /* this is the initial count of the rows when the page is loaded  */
            path: "epochs",                               /* this is the path of the collection that holds the rows  */
        };


        rowInserters.push(instanceRowEpoch);

        function addRows(){
        }
    </script>
</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" formName="studyDesignForm">
<jsp:attribute name="localButtons">
    <input id="addEpoch" type="button"
           value="Add Treatment Epoch & Arms"
           onclick="$('dummy-row').innerHTML=$('treatmentHtml').innerHTML;RowManager.addRow(instanceRowEpoch)" />
    
    <input
            id="addEpoch" type="button" value="Add Non-Treatment Epoch"
            onclick="$('dummy-row').innerHTML=$('non-treatmentHtml').innerHTML;RowManager.addRow(instanceRowEpoch)" /><br>
				    
</jsp:attribute>

<jsp:attribute name="singleFields">
		<div><input type="hidden" name="_action" value=""> <input
                type="hidden" name="_selectedEpoch" value=""> <input type="hidden"
                                                                     name="_selectedArm" value=""></div>



				<table border="0" cellspacing="0" cellpadding="0" id="addRowTable"
                       width="100%">
                <tr>
                    <td></td>
                </tr>
                <c:forEach items="${command.epochs}" var="epoch" varStatus="status">
                <tr id="addRowTable-${status.index }">

                <c:choose>
                <c:when
                        test="${epoch.class.name=='edu.duke.cabig.c3pr.domain.TreatmentEpoch'}">
                    <td><chrome:deletableDivision title="Treatment Epoch"
                                                  onclick="RowManager.deleteRow(instanceRowEpoch,${status.index})">
                        <table>
                            <tr>
                                <td>
                                <td width="50" valign="top">
                                    <table width="50%" border="0" cellspacing="1"
                                           cellpadding="1" id="mytable1">
                                        <tr>
                                            <td align="right" class="alt"><span class="red">*</span><em></em>
                                                <b>Name :</b>&nbsp;</td>
                                            <td align="left"><form:input
                                                    path="epochs[${status.index}].name" size="26"
                                                    cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
                                        </tr>
                                        <tr>
                                            <td align="right" class="alt"><em></em> <b>Description:</b>&nbsp;</td>
                                            <td align="left"><form:textarea
                                                    path="epochs[${status.index}].descriptionText" rows="2"
                                                    cols="20" />&nbsp;&nbsp;&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="100" valign="top">
                                    <table border="0" cellspacing="0" cellpadding="0"
                                           id="mytable" width="50%">
                                        <tr>
                                            <th class="alt">Arm</th>
                                            <th class="alt">Target&nbsp;Accrual&nbsp;Number</th>
                                            <th class="alt"><input id="addArm" type="button"
                                                                   value="Add Arm"
                                                                   onclick="javascript:fireAction('addArm',${status.index},0)" ;/></th>
                                        </tr>
                                        <c:forEach items="${epoch.arms}" var="arm"
                                                   varStatus="statusArms">
                                            <tr>
                                                <td class="alt"><form:input
                                                        path="epochs[${status.index}].arms[${statusArms.index}].name"
                                                        cssClass="validate-notEmpty" /></td>
                                                <td class="alt"><form:input
                                                        path="epochs[${status.index}].arms[${statusArms.index}].targetAccrualNumber"
                                                        size="12" maxlength="10" cssClass="validate-numeric" /></td>
                                                <td class="alt"><a
                                                        href="javascript:fireAction('removeArm',${status.index},${statusArms.index});"><img
                                                        src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                                            </tr>
                                        </c:forEach>
                                    </table>

                                </td>

                            </tr>
                        </table>
                    </chrome:deletableDivision></td>
                </c:when>
                <c:otherwise>

                    <td><chrome:deletableDivision title="Non-Treatment Epoch"
                                                  onclick="RowManager.deleteRow(instanceRowEpoch,${status.index})">
                        <div class="leftpanel">
                            <div class="row">
                                <div class="label">*Name:</div>
                                <div class="value"><form:input
                                        path="epochs[${status.index}].name" size="50"
                                        cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span></div>
                            </div>

                            <div class="row">
                                <div class="label">Description:</div>
                                <div class="value"><form:textarea
                                        path="epochs[${status.index}].descriptionText" rows="5" cols="40" />&nbsp;&nbsp;&nbsp;</div>
                            </div>
                        </div>

                        <div class="rightpanel">
                            <div class="row">
                                <div class="label">*Accrual Indicator:</div>
                                <div class="value"><form:select
                                        path="epochs[${status.index}].accrualIndicator"
                                        cssClass="validate-notEmpty">
                                    <option value="">--Please Select--</option>
                                    <form:options items="${fn:split('Yes,No',',')}" />
                                </form:select></div>
                            </div>

                            <div class="row">
                                <div class="label">*Accrual Ceiling:</div>
                                <div class="value"><form:input
                                        path="epochs[${status.index}].accrualCeiling" size="12"
                                        maxlength="10" cssClass="validate-numeric" /></div>
                            </div>

                            <div class="row">
                                <div class="label">*Enrollment Indicator:</div>
                                <div class="value"><form:select
                                        id="epochs[${status.index}].enrollmentIndicator"
                                        path="epochs[${status.index}].enrollmentIndicator"
                                        cssClass="validate-notEmpty">
                                    <option value="">--Please Select--</option>
                                    <form:options items="${fn:split('Yes,No',',')}" />
                                </form:select></div>
                            </div>

                            <div class="row">
                                <div class="label">*Reservation Indicator:</div>
                                <div class="value"><form:select
                                        path="epochs[${status.index}].reservationIndicator"
                                        cssClass="validate-notEmpty">
                                    <option value="">--Please Select--</option>
                                    <form:options items="${fn:split('Yes,No',',')}" />
                                </form:select></div>
                            </div>
                        </div>
                    </chrome:deletableDivision></td>
                </c:otherwise>
                </c:choose>

                </tr>
                &nbsp;&nbsp;

                </c:forEach>



                </table>
			 

	</jsp:attribute>
</tags:tabForm>
<div id="dummy-row" style="display:none"></div>

<div id="treatmentHtml" style="display:none">

    <table>
        <tr>
            <td><chrome:deletableDivision title="New Treatment Epoch"
                                          onclick="RowManager.deleteRow(instanceRowEpoch,PAGE.ROW.INDEX)">
                <table>
                    <tr>
                        <td width="50" valign="top">
                            <table width="50%" border="0" cellspacing="1" cellpadding="1"
                                   id="mytable1">

                                <tr>
                                    <td align="right" class="alt"><span class="red">*</span><em></em>
                                        <b>Name:</b>&nbsp;</td>
                                    <td align="left"><input type="text"
                                                            name="treatmentEpochsAliased[PAGE.ROW.INDEX].name" size="26"
                                                            cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
                                </tr>
                                <tr>
                                    <td align="right" class="alt"><em></em> <b>Description:</b>&nbsp;</td>
                                    <td align="left"><textarea
                                            name="treatmentEpochsAliased[PAGE.ROW.INDEX].descriptionText"
                                            rows="2" cols="20"></textarea></td>
                                </tr>
                            </table>
                        </td>
                        <td width="100" valign="top">
                            <table border="0" cellspacing="0" cellpadding="0" id="mytable"
                                   width="50%">
                                <tr>
                                    <th class="alt">Arm</th>
                                    <th class="alt">Target&nbsp;Accrual&nbsp;Number</th>
                                    <th class="alt"><input id="addArm" type="button" value="Add Arm"
                                                           onclick="javascript:fireAction('addArm',PAGE.ROW.INDEX,0)" ;/></th>
                                </tr>
                                <tr>
                                    <td class="alt"><input type="text"
                                                           name="treatmentEpochsAliased[PAGE.ROW.INDEX].arms[0].name"
                                                           cssClass="validate-notEmpty" value="Arm A" /></td>
                                    <td class="alt"><input type="text"
                                                           name="treatmentEpochsAliased[PAGE.ROW.INDEX].arms[0].targetAccrualNumber"
                                                           size="12" maxlength="10" cssClass="validate-numeric" /></td>
                                    <td class="alt"><a
                                            href="javascript:fireAction('removeArm',PAGE.ROW.INDEX,0);"><img
                                            src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                                </tr>
                                <tr>
                                    <td class="alt"><input type="text"
                                                           name="treatmentEpochsAliased[PAGE.ROW.INDEX].arms[1].name"
                                                           cssClass="validate-notEmpty" value="Arm B" /></td>
                                    <td class="alt"><input type="text"
                                                           name="treatmentEpochsAliased[PAGE.ROW.INDEX].arms[1].targetAccrualNumber"
                                                           size="12" maxlength="10" cssClass="validate-numeric" /></td>
                                    <td class="alt"><a
                                            href="javascript:fireAction('removeArm',PAGE.ROW.INDEX,1);"><img
                                            src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>

                                </tr>
                                <tr>
                                    <td class="alt"><input type="text"
                                                           name="treatmentEpochsAliased[PAGE.ROW.INDEX].arms[2].name"
                                                           cssClass="validate-notEmpty" value="Arm C" /></td>
                                    <td class="alt"><input type="text"
                                                           name="treatmentEpochsAliased[PAGE.ROW.INDEX].arms[2].targetAccrualNumber"
                                                           size="12" maxlength="10" cssClass="validate-numeric" /></td>
                                    <td class="alt"><a
                                            href="javascript:fireAction('removeArm',PAGE.ROW.INDEX,2);"><img
                                            src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>

                                </tr>
                            </table>
                        </td>


                    </tr>
                </table>
            </chrome:deletableDivision></td>
        </tr>
    </table>
</div>

<div id="non-treatmentHtml" style="display:none">
    <table border="10">
        <tr>
            <td><chrome:deletableDivision title="New Non-Treatment Epoch"
                                          onclick="RowManager.deleteRow(instanceRowEpoch,PAGE.ROW.INDEX)">
                <div class="leftpanel">
                    <div class="row">
                        <div class="label">*Name:</div>
                        <div class="value"><input type="text" size="50"
                                                  name="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].name"
                                                  cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span></div>
                    </div>

                    <div class="row">
                        <div class="label">Description:</div>
                        <div class="value"><textarea
                                name="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].descriptionText"
                                rows="5" cols="40"></textarea></div>
                    </div>
                </div>

                <div class="rightpanel">
                    <div class="row">
                        <div class="label">*Accrual Indicator:</div>
                        <div class="value"><select
                                name="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].accrualIndicator"
                                cssClass="validate-notEmpty">
                            <option value="">--Please Select--</option>
                            <option value="yes">Yes</option>
                            <option value="no">No</option>
                        </select></div>
                    </div>

                    <div class="row">
                        <div class="label">*Accrual Ceiling:</div>
                        <div class="value"><input type="text"
                                                  id="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].accrualCeiling"
                                                  name="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].accrualCeiling"
                                                  size="12" maxlength="10" cssClass="validate-numeric" /></div>
                    </div>

                    <div class="row">
                        <div class="label">*Enrollment Indicator:</div>
                        <div class="value"><select
                                id="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].enrollmentIndicator"
                                name="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].enrollmentIndicator"
                                cssClass="validate-notEmpty">
                            <option value="">--Please Select--</option>
                            <option value="yes">Yes</option>
                            <option value="no">No</option>
                        </select></div>
                    </div>

                    <div class="row">
                        <div class="label">*Reservation Indicator:</div>
                        <div class="value"><select
                                id="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].reservationIndicator"
                                name="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].reservationIndicator"
                                cssClass="validate-notEmpty">
                            <option value="">--Please Select--</option>
                            <option value="yes">Yes</option>
                            <option value="no">No</option>
                        </select></div>
                    </div>
                </div>
            </chrome:deletableDivision></td>
        </tr>
    </table>
</div>
</body>
</html>
