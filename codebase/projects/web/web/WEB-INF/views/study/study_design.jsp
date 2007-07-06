
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
	<jsp:attribute name="singleFields">
		<div><input type="hidden" name="_action" value=""> <input
			type="hidden" name="_selectedEpoch" value=""> <input type="hidden"
			name="_selectedArm" value=""></div>

		<table border="0" cellspacing="2" cellpadding="2">

			<tr>
				<td><input id="addEpoch" type="button"
					value="Add Treatment Epoch & Arms"
					onclick="$('dummy-row').innerHTML=$('treatmentHtml').innerHTML;RowManager.addRow(instanceRowEpoch)" />

				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
					id="addEpoch" type="button" value="Add NonTreatment Epoch"
					onclick="$('dummy-row').innerHTML=$('non-treatmentHtml').innerHTML;RowManager.addRow(instanceRowEpoch)" /><br>
				</td>

			</tr>

			<tr>
				<td>
				<table border="0" cellspacing="2" cellpadding="2" id="addRowTable">
					<tr>
						<td></td>
					</tr>
					<c:forEach items="${command.epochs}" var="epoch" varStatus="status">
						<tr id="addRowTable-${status.index }">

							<c:choose>
								<c:when
									test="${epoch.class.name=='edu.duke.cabig.c3pr.domain.TreatmentEpoch'}">
									<td><chrome:division
										title="New Treatment Epoch-${status.index }">
										<table>
											<tr>
												<td>
												<td width="50" valign="top">
												<table width="50%" border="0" cellspacing="1"
													cellpadding="1" id="mytable1">
													<tr>
														<td align="right" class="alt"><span class="red">*</span><em></em>
														<b>Epoch Name :</b>&nbsp;</td>
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

													<tr>
														<td class="alt"><a
															href="javascript:RowManager.deleteRow(instanceRowEpoch,${status.index});"><img
															src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
													</tr>

												</table>
												</td>
												<td width="100" valign="top">
												<table border="0" cellspacing="0" cellpadding="0"
													id="mytable" width="50%">
													<tr>
														<th class="alt">Arm</th>
														<th class="alt">Target&nbsp;Accrual&nbsp;Number</th>
														<th class="alt"></th>
														<td class="alt"><a
															href="javascript:fireAction('addArm',${status.index},0);"><img
															src="<tags:imageUrl name="checkyes.gif"/>" border="0"></a></td>
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
									</chrome:division></td>
								</c:when>
								<c:otherwise>

									<td><chrome:division
										title="New NonTreatment Epoch-${status.index }">

										<table>
											<tr>
												<td>
												<table width="100" border="0" cellspacing="0"
													cellpadding="0" id="details">
													<tr>
														<td width="50" valign="top">


														<table border="0" cellspacing="0" cellpadding="0"
															id="mytable" width="50%">
															<tr>
																<th class="alt"></th>
																<th class="alt">*Epoch Name</th>
																<th class="alt">Description</th>
																<th class="alt">*Accrual Indicator</th>
																<th class="alt">*Accrual Ceiling</th>
																<th class="alt">*Enrollment Indicator</th>
																<th class="alt">Reservation Indicator</th>
															</tr>
															<tr>
																<td class="alt"><a
																	href="javascript:RowManager.deleteRow(instanceRowEpoch,${status.index});"><img
																	src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
																<td class="alt"><form:input
																	path="epochs[${status.index}].name"
																	cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span></td>
																<td class="alt"><form:textarea
																	path="epochs[${status.index}].descriptionText" rows="2"
																	cols="20" />&nbsp;&nbsp;&nbsp;</td>
																<td class="alt"><form:select
																	path="epochs[${status.index}].accrualIndicator"
																	cssClass="validate-notEmpty">
																	<option value="">--Please Select--</option>
																	<form:options items="${fn:split('yes,no',',')}" />
																</form:select></td>
																<td class="alt"><form:input
																	path="epochs[${status.index}].accrualCeiling" size="12"
																	maxlength="10" cssClass="validate-numeric" /></td>
																<td class="alt"><form:select
																	id="epochs[${status.index}].enrollmentIndicator"
																	path="epochs[${status.index}].enrollmentIndicator"
																	cssClass="validate-notEmpty">
																	<option value="">--Please Select--</option>
																	<form:options items="${fn:split('yes,no',',')}" />
																</form:select></td>
																<td class="alt"><form:select
																	path="epochs[${status.index}].reservationIndicator"
																	cssClass="validate-notEmpty">
																	<option value="">--Please Select--</option>
																	<form:options items="${fn:split('yes,no',',')}" />
																</form:select></td>

															</tr>
														</table>

														</td>
													</tr>
												</table>
												</td>
											</tr>
										</table>

									</chrome:division></td>
								</c:otherwise>
							</c:choose>

						</tr>
						&nbsp;&nbsp;						

					</c:forEach>



				</table>
				</td>
			</tr>
		</table>

	</jsp:attribute>
</tags:tabForm>
<div id="dummy-row" style="display:none"></div>

<div id="treatmentHtml" style="display:none">

<table>
	<tr>
		<td><chrome:division title="New Treatment Epoch-PAGE.ROW.INDEX">
			<table>

				<tr>

					<td width="50" valign="top">
					<table width="50%" border="0" cellspacing="1" cellpadding="1"
						id="mytable1">

						<tr>
							<td align="right" class="alt"><span class="red">*</span><em></em>
							<b>Epoch Name :</b>&nbsp;</td>
							<td align="left"><input type="text"
								name="treatmentEpochsAliased[PAGE.ROW.INDEX].name" size="26"
								cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
						</tr>
						<tr>
							<td align="right" class="alt"><em></em> <b>Description:</b>&nbsp;</td>
							<td align="left"><textarea
								name="treatmentEpochsAliased[PAGE.ROW.INDEX].descriptionText"
								rows="2" cols="20">&nbsp;&nbsp;&nbsp;</textarea></td>
						</tr>
						<tr>
							<td class="alt"><a
								href="javascript:RowManager.deleteRow(instanceRowEpoch,PAGE.ROW.INDEX);"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
						</tr>

					</table>
					</td>
					<td width="100" valign="top">
					<table border="0" cellspacing="0" cellpadding="0" id="mytable"
						width="50%">
						<tr>
							<th class="alt">Arm</th>
							<th class="alt">Target&nbsp;Accrual&nbsp;Number</th>
							<th class="alt"></th>
							<td class="alt"><a
								href="javascript:fireAction('addArm',PAGE.ROW.INDEX,0);"><img
								src="<tags:imageUrl name="checkyes.gif"/>" border="0"></a></td>
						</tr>
						<tr>
							<td class="alt"><input type="text"
								name="treatmentEpochsAliased[PAGE.ROW.INDEX].arms[0].name"
								cssClass="validate-notEmpty" /></td>
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
								cssClass="validate-notEmpty" /></td>
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
								cssClass="validate-notEmpty" /></td>
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
		</chrome:division></td>
	</tr>
</table>

</div>

<div id="non-treatmentHtml" style="display:none">
<table>
	<tr>
		<td><chrome:division title="New NonTreatment Epoch-PAGE.ROW.INDEX">

			<table width="100" border="0" cellspacing="0" cellpadding="0"
				id="details">
				<tr>
					<td width="50" valign="top">


					<table border="0" cellspacing="0" cellpadding="0" id="mytable"
						width="50%">
						<tr>
							<th class="alt"></th>
							<th class="alt">*Epoch Name</th>
							<th class="alt">Description</th>
							<th class="alt">*Accrual Indicator</th>
							<th class="alt">*Accrual Ceiling</th>
							<th class="alt">*Enrollment Indicator</th>
							<th class="alt">*Reservation Indicator</th>
						</tr>
						<tr>
							<td class="alt"><a
								href="javascript:RowManager.deleteRow(instanceRowEpoch,PAGE.ROW.INDEX);"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
							<td class="alt"><input type="text"
								name="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].name"
								cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span></td>
							<td class="alt"><textarea
								name="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].descriptionText"
								rows="2" cols="20">&nbsp;&nbsp;&nbsp;</textarea></td>
							<td class="alt"><select
								name="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].accrualIndicator"
								cssClass="validate-notEmpty">
								<option value="">--Please Select--</option>
								<option value="yes">yes</option>
								<option value="no">no</option>
							</select></td>
							<td class="alt"><input type="text"
								id="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].accrualCeiling"
								name="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].accrualCeiling"
								size="12" maxlength="10" cssClass="validate-numeric" /></td>
							<td class="alt"><select
								id="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].enrollmentIndicator"
								name="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].enrollmentIndicator"
								cssClass="validate-notEmpty">
								<option value="">--Please Select--</option>
								<option value="yes">yes</option>
								<option value="no">no</option>
							</select></td>
							<td class="alt"><select
								id="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].reservationIndicator"
								name="nonTreatmentEpochsAliased[PAGE.ROW.INDEX].reservationIndicator"
								cssClass="validate-notEmpty">
								<option value="">--Please Select--</option>
								<option value="yes">yes</option>
								<option value="no">no</option>
							</select></td>

						</tr>
					</table>

					</td>
				</tr>
			</table>
		</chrome:division></td>
	</tr>
</table>
</div>
</body>
</html>
