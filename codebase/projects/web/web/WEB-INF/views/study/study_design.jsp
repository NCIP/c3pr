
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

		var armInserterProps= {
			add_row_division_id: "arm",
			skeleton_row_division_id: "dummy-arm",
			initialIndex: 0,
			row_index_indicator: "NESTED.PAGE.ROW.INDEX",
			path: "treatmentEpochs[${treatmentEpochCount.index }].arms",
		};
		var treatmentEpochRowInserterProps= {
			nested_row_inserter: armInserterProps,
			add_row_division_id: "treatmentEpoch",
			skeleton_row_division_id: "dummy-treatmentEpoch",
			initialIndex: ${fn:length(command.treatmentEpochs)},
			path: "treatmentEpochs",
		};	
		var nonTreatmentEpochRowInserterProps= {
			add_row_division_id: "nonTreatmentEpoch",
			skeleton_row_division_id: "dummy-nonTreatmentEpoch",
			initialIndex: ${fn:length(command.nonTreatmentEpochs)},
			path: "nonTreatmentEpochs",
		};
		rowInserters.push(treatmentEpochRowInserterProps);
		rowInserters.push(nonTreatmentEpochRowInserterProps);
		registerRowInserters();
</script>

</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" formName="studyDesignForm">
	<jsp:attribute name="localButtons">
		<input id="addEpoch" type="button" value="Add Treatment Epoch"
			onclick="$('dummy-treatmentEpoch').innerHTML=$('treatmentHtml').innerHTML;RowManager.addRow(treatmentEpochRowInserterProps)" />

		<input id="addEpoch" type="button" value="Add Non-Treatment Epoch"
			onclick="$('dummy-nonTreatmentEpoch').innerHTML=$('non-treatmentHtml').innerHTML;RowManager.addRow(nonTreatmentEpochRowInserterProps)" />
		<br>

	</jsp:attribute>

	<jsp:attribute name="singleFields">
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

					<td><chrome:deletableDivision title="Treatment Epoch"
						onclick="RowManager.deleteRow(treatmentEpochRowInserterProps,${treatmentEpochCount.index})">
						<table>
							<tr>
								<td>
								<td width="50" valign="top">
								<table width="50%" border="0" cellspacing="1" cellpadding="1"
									id="mytable1">
									<tr>
										<td align="right" class="alt"><span class="red">*</span><em></em>
										<b>Name :</b>&nbsp;</td>
										<td align="left"><form:input
											path="treatmentEpochs[${treatmentEpochCount.index}].name"
											size="26" cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
									</tr>
									<tr>
										<td align="right" class="alt"><em></em> <b>Description:</b>&nbsp;</td>
										<td align="left"><form:textarea
											path="treatmentEpochs[${treatmentEpochCount.index}].descriptionText"
											rows="2" cols="20" />&nbsp;&nbsp;&nbsp;</td>
									</tr>
								</table>
								</td>
								<td width="100" valign="top">
								<table border="0" cellspacing="0" cellpadding="0" id="arm"
									width="50%" class="mytable">
									<tr>
										<th class="alt">Arm</th>
										<th class="alt">Target&nbsp;Accrual&nbsp;Number</th>
										<th class="alt"><input id="addArm" type="button"
											value="Add Arm"
											onclick="javascript:RowManager.addRow(RowManager.getNestedRowInserter(treatmentEpochRowInserterProps,${treatmentEpochCount.index}));" /></th>
									</tr>

									<c:forEach items="${treatmentEpoch.arms}" var="arm"
										varStatus="statusArms">
										<tr>
											<td class="alt"><form:input
												path="treatmentEpochs[${treatmentEpochCount.index}].arms[${statusArms.index}].name"
												cssClass="validate-notEmpty" /></td>
											<td class="alt"><form:input
												path="treatmentEpochs[${treatmentEpochCount.index}].arms[${statusArms.index}].targetAccrualNumber"
												size="12" maxlength="10" cssClass="validate-numeric" /></td>
											<td class="alt"><a
												href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(treatmentEpochRowInserterProps,${treatmentEpochCount.index}),${statusArms.index });"><img
												src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
										</tr>
									</c:forEach>
								</table>
								</td>
							</tr>
						</table>
					</chrome:deletableDivision></td>
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

					<td><chrome:deletableDivision title="Non-Treatment Epoch"
						onclick="RowManager.deleteRow(nonTreatmentEpochRowInserterProps,${nonTreatmentEpochCount.index})">
						<div class="leftpanel">
						<div class="row">
						<div class="label">*Name:</div>
						<div class="value"><form:input
							path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].name"
							size="50" cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span></div>
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
						<div class="label">*Accrual Indicator:</div>
						<div class="value"><form:select
							path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].accrualIndicator"
							cssClass="validate-notEmpty">
							<option value="">--Please Select--</option>
							<form:options items="${fn:split('Yes,No',',')}" />
						</form:select></div>
						</div>

						<div class="row">
						<div class="label">*Accrual Ceiling:</div>
						<div class="value"><form:input
							path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].accrualCeiling"
							size="12" maxlength="10" cssClass="validate-numeric" /></div>
						</div>

						<div class="row">
						<div class="label">*Enrollment Indicator:</div>
						<div class="value"><form:select
							id="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].enrollmentIndicator"
							path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].enrollmentIndicator"
							cssClass="validate-notEmpty">
							<option value="">--Please Select--</option>
							<form:options items="${fn:split('Yes,No',',')}" />
						</form:select></div>
						</div>

						<div class="row">
						<div class="label">*Reservation Indicator:</div>
						<div class="value"><form:select
							path="nonTreatmentEpochs[${nonTreatmentEpochCount.index}].reservationIndicator"
							cssClass="validate-notEmpty">
							<option value="">--Please Select--</option>
							<form:options items="${fn:split('Yes,No',',')}" />
						</form:select></div>
						</div>
						</div>
					</chrome:deletableDivision></td>

				</tr>
                &nbsp;&nbsp;
                </c:forEach>
		</table>
	</jsp:attribute>
</tags:tabForm>
<div id="dummy-treatmentEpoch" style="display:none"></div>

<div id="dummy-nonTreatmentEpoch" style="display:none"></div>

<div id="treatmentHtml" style="display:none">

<table>
	<tr>
		<td><chrome:deletableDivision title="New Treatment Epoch"
			onclick="RowManager.deleteRow(treatmentEpochRowInserterProps,PAGE.ROW.INDEX)">
			<table>
				<tr>
					<td width="50" valign="top">
					<table width="50%" border="0" cellspacing="1" cellpadding="1"
						id="mytable1">
						<tr>
							<td align="right" class="alt"><span class="red">*</span><em></em>
							<b>Name:</b>&nbsp;</td>
							<td align="left"><input type="text"
								name="treatmentEpochs[PAGE.ROW.INDEX].name" size="26"
								cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
						</tr>
						<tr>
							<td align="right" class="alt"><em></em> <b>Description:</b>&nbsp;</td>
							<td align="left"><textarea
								name="treatmentEpochs[PAGE.ROW.INDEX].descriptionText" rows="2"
								cols="20"></textarea></td>
						</tr>
					</table>
					</td>
					<td width="100" valign="top">
					<table border="0" cellspacing="0" cellpadding="0" id="arm" class="mytable"
						width="50%">
						<tr>
							<th class="alt">Arm</th>
							<th class="alt">Target&nbsp;Accrual&nbsp;Number</th>
							<th class="alt"><input id="addArm" type="button" value="Add Arm"
								onclick="javascript:RowManager.addRow(RowManager.getNestedRowInserter(treatmentEpochRowInserterProps,PAGE.ROW.INDEX));" ;/></th>
						</tr>
					</table>
					</td>
				</tr>
			</table>
		</chrome:deletableDivision></td>
	</tr>
</table>
</div>

<div id="dummy-arm" style="display:none">
<table border="0" cellspacing="0" cellpadding="0" id="arm" width="50%" class="mytable">
	<tr>
		<td class="alt"><input type="text"
			name="treatmentEpochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].name"
			cssClass="validate-notEmpty" value="Arm A" /></td>
		<td class="alt"><input type="text"
			name="treatmentEpochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].targetAccrualNumber"
			size="12" maxlength="10" cssClass="validate-numeric" /></td>
		<td class="alt"><a
			href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(treatmentEpochRowInserterProps,PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

<div id="non-treatmentHtml" style="display:none">
<table border="10">
	<tr>
		<td><chrome:deletableDivision title="New Non-Treatment Epoch"
			onclick="RowManager.deleteRow(nonTreatmentEpochRowInserterProps,PAGE.ROW.INDEX)">
			<div class="leftpanel">
			<div class="row">
			<div class="label">*Name:</div>
			<div class="value"><input type="text" size="50"
				name="nonTreatmentEpochs[PAGE.ROW.INDEX].name"
				cssClass="validate-notEmpty" /><span class="red">&nbsp;&nbsp;&nbsp;</span></div>
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
			<div class="label">*Accrual Indicator:</div>
			<div class="value"><select
				name="nonTreatmentEpochs[PAGE.ROW.INDEX].accrualIndicator"
				cssClass="validate-notEmpty">
				<option value="">--Please Select--</option>
				<option value="Yes">Yes</option>
				<option value="No">No</option>
			</select></div>
			</div>

			<div class="row">
			<div class="label">*Accrual Ceiling:</div>
			<div class="value"><input type="text"
				id="nonTreatmentEpochs[PAGE.ROW.INDEX].accrualCeiling"
				name="nonTreatmentEpochs[PAGE.ROW.INDEX].accrualCeiling" size="12"
				maxlength="10" cssClass="validate-numeric" /></div>
			</div>

			<div class="row">
			<div class="label">*Enrollment Indicator:</div>
			<div class="value"><select
				id="nonTreatmentEpochs[PAGE.ROW.INDEX].enrollmentIndicator"
				name="nonTreatmentEpochs[PAGE.ROW.INDEX].enrollmentIndicator"
				cssClass="validate-notEmpty">
				<option value="">--Please Select--</option>
				<option value="Yes">Yes</option>
				<option value="No">No</option>
			</select></div>
			</div>

			<div class="row">
			<div class="label">*Reservation Indicator:</div>
			<div class="value"><select
				id="nonTreatmentEpochs[PAGE.ROW.INDEX].reservationIndicator"
				name="nonTreatmentEpochs[PAGE.ROW.INDEX].reservationIndicator"
				cssClass="validate-notEmpty">
				<option value="">--Please Select--</option>
				<option value="Yes">Yes</option>
				<option value="No">No</option>
			</select></div>
			</div>
			</div>
		</chrome:deletableDivision></td>
	</tr>
</table>
</div>
</body>
</html>
