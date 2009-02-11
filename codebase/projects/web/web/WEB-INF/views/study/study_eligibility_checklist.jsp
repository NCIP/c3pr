<%@ include file="taglibs.jsp"%>

<html>
<head>
<title><studyTags:htmlTitle study="${command.study}" /></title>
<tags:dwrJavascriptLink objects="StudyAjaxFacade" />
<style type="text/css">
.label {
	width: 12em;
	text-align: right;
	padding: 4px;
}
</style>
<script language="JavaScript" type="text/JavaScript">
        //AJAX
        Effect.OpenUp = function(element) {
            element = $(element);
            new Effect.BlindDown(element, arguments[1] || {});
        }

        Effect.CloseDown = function(element) {
            element = $(element);
            new Effect.BlindUp(element, arguments[1] || {});
        }

        Effect.Combo = function(element) {
            element = $(element);
            if (element.style.display == 'none') {
                new Effect.OpenUp(element, arguments[1] || {});
            } else {
                new Effect.CloseDown(element, arguments[1] || {});
            }
        }
        function PanelCombo(element) {
            panelDiv = $(element);
            imageId = element + '-image';
            imageSource = document.getElementById(imageId).src;
            if (panelDiv.style.display == 'none') {
                new Effect.OpenUp(panelDiv, arguments[1] || {});
                document.getElementById(imageId).src = imageSource.replace('minimize', 'maximize');
            } else {
                new Effect.CloseDown(panelDiv, arguments[1] || {});
                document.getElementById(imageId).src = imageSource.replace('maximize', 'minimize');
            }
        }
        function displayDiv(id, flag) {
            if (flag == 'true') {
                document.getElementById(id).style.display = 'block';
            } else
                document.getElementById(id).style.display = 'none';
        }
        
        
    </script>
</head>
<body>
<tags:minimizablePanelBox title="Upload Criteria" boxId="criteria">
	<tags:instructions code="study_eligibility_checklist" />
	<form:form method="post" id="eligibilityForm_${epochCount.index}"
		enctype="multipart/form-data">
		<table border="0" width="50%" id="table0" cellspacing="5">
			<tr>
				<td width="35%" align="right" class=""><tags:requiredIndicator /><b><fmt:message key="study.eligibility.selectEpoch"/>
				</b></td>
				<td width="65%"><select name="name" class="validate-notEmpty">
					<option value="">Please Select</option>
					<c:forEach items="${command.study.epochs}" var="epoch"
						varStatus="epochCount">
						<option value="${epoch.name}">${epoch.name}</option>
					</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td width="35%" align="right"><b><fmt:message key="study.eligibility.selectcaDSRFileToImport"/>
				</b></td>
				<td><input type="file" name="study.criteriaFile" /> <input
					type='submit' value='Upload' /> <tags:hoverHint
					keyProp="study.criteriafile" /></td>
			</tr>
		</table>
	</form:form>
</tags:minimizablePanelBox>
<form:form method="post" name="form">
	<tags:tabFields tab="${tab}" />
	<c:forEach items="${command.study.epochs}" var="epoch"
		varStatus="epochCount">
		<script>
                var instanceInclusionRow_${epochCount.index} = {
                    add_row_division_id: "addInclusionRowTable-${epochCount.index}",
                    skeleton_row_division_id: "dummy-inclusionRow-${epochCount.index}",
                    initialIndex: ${fn:length(command.study.epochs[epochCount.index].inclusionEligibilityCriteria)},
                    softDelete: ${softDelete == 'true'},
                    isAdmin: ${isAdmin == 'true'},
                    path: "study.epochs[${epochCount.index }].inclusionEligibilityCriteria"
                };
                var instanceExclusionRow_${epochCount.index} = {
                    add_row_division_id: "addExclusionRowTable-${epochCount.index}",
                    skeleton_row_division_id: "dummy-exclusionRow-${epochCount.index}",
                    initialIndex: ${fn:length(command.study.epochs[epochCount.index].exclusionEligibilityCriteria)},
					softDelete: ${softDelete == 'true'},
					isAdmin: ${isAdmin == 'true'},
                    path: "study.epochs[${epochCount.index }].exclusionEligibilityCriteria"
                };
                RowManager.addRowInseter(instanceInclusionRow_${epochCount.index});
                RowManager.addRowInseter(instanceExclusionRow_${epochCount.index});
            </script>

		<tags:minimizablePanelBox title="${epoch.name}" boxId="${epoch.name}">
			<chrome:division title="Inclusion Criteria" minimize="true"
				divIdToBeMinimized="inclusionCriteria-${epochCount.index}">
				<div id="inclusionCriteria-${epochCount.index}">
				<table width="100%">
					<tr>
						<td>
						<table border="0" cellspacing="0" width="95%" cellpadding="0"
							id="addInclusionRowTable-${epochCount.index}"
							class="tablecontent">
							<tr id="hInclusionEligibility--${epochCount.index}"
								<c:if test="${fn:length(epoch.inclusionEligibilityCriteria) == 0}">style="display:none;"</c:if>>
								<th><span class="label"><tags:requiredIndicator /><fmt:message key="study.question"/></span>&nbsp;<tags:hoverHint
									id="study.inclusionEligibilityCriteria.questionText-${epochCount.index}"
									keyProp="study.inclusionEligibilityCriteria.questionText" /></th>
								<th><span class="label"><fmt:message key="c3pr.common.notApplicable"/></span>&nbsp;<tags:hoverHint
									id="NA" keyProp="NA" /></th>
								<th></th>
							</tr>
							<c:forEach varStatus="status" var="ieCrit"
								items="${command.study.epochs[epochCount.index].inclusionEligibilityCriteria}">
								<tr
									id="addInclusionRowTable-${epochCount.index}-${status.index}">
									<td><form:textarea
										path="study.epochs[${epochCount.index }].inclusionEligibilityCriteria[${status.index}].questionText"
										rows="1" cols="95" cssClass="validate-notEmpty" /></td>
									<td><form:checkbox
										path="study.epochs[${epochCount.index }].inclusionEligibilityCriteria[${status.index}].notApplicableIndicator" />
									</td>
									<td><a
										href="javascript:RowManager.deleteRow(instanceInclusionRow_${epochCount.index},${status.index},'${ieCrit.id==null?'HC#':'ID#'}${ieCrit.id==null?ieCrit.hashCode:ieCrit.id}');"><img
										src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
								</tr>
							</c:forEach>
						</table>
						</td>
						<td valign="bottom" align="right">
						<table width="5%">
							<tr>
								<td><input type="button" value="Add Inclusion Criterion"
									onclick="$('hInclusionEligibility--${epochCount.index}').show();RowManager.addRow(instanceInclusionRow_${epochCount.index});">
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</div>
			</chrome:division>
			<chrome:division title="Exclusion Criteria" minimize="true"
				divIdToBeMinimized="exclusionCriteria-${epochCount.index}">
				<div id="exclusionCriteria-${epochCount.index}">
				<table width="100%">
					<tr>
						<td>
						<table border="0" width="95%" cellspacing="0" cellpadding="0"
							class="tablecontent"
							id="addExclusionRowTable-${epochCount.index}">
							<tr id="hExclusionEligibility-${epochCount.index}"
								<c:if test="${fn:length(epoch.exclusionEligibilityCriteria) == 0}">style="display:none;"</c:if>>
								<th><span class="label"><tags:requiredIndicator /><fmt:message key="study.question"/></span>&nbsp;<tags:hoverHint
									id="study.exclusionEligibilityCriteria.questionText-${epochCount.index}"
									keyProp="study.exclusionEligibilityCriteria.questionText" /></th>
								<th><fmt:message key="c3pr.common.notApplicable"/></th>
								<th></th>
							</tr>
							<c:forEach varStatus="status" var="eeCrit"
								items="${command.study.epochs[epochCount.index].exclusionEligibilityCriteria}">
								<tr
									id="addExclusionRowTable-${epochCount.index}-${status.index}">
									<td><form:textarea
										path="study.epochs[${epochCount.index }].exclusionEligibilityCriteria[${status.index}].questionText"
										rows="1" cols="95" cssClass="validate-notEmpty" /></td>
									<td><form:checkbox
										path="study.epochs[${epochCount.index }].exclusionEligibilityCriteria[${status.index}].notApplicableIndicator" />
									</td>
									<td><a
										href="javascript:RowManager.deleteRow(instanceExclusionRow_${epochCount.index},${status.index},'${eeCrit.id==null?'HC#':'ID#'}${eeCrit.id==null?eeCrit.hashCode:eeCrit.id}');">
									<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
								</tr>
							</c:forEach>
						</table>
						</td>
						<td valign="bottom" align="right">
						<table width="5%">
							<tr>
								<td><input type="button" value="Add Exclusion Criterion"
									onclick="$('hExclusionEligibility-${epochCount.index}').show();RowManager.addRow(instanceExclusionRow_${epochCount.index});" />
								</td>
							</tr>




						</table>
						</td>
					</tr>
				</table>

				</div>
			</chrome:division>
			</td>

		</tags:minimizablePanelBox>
	</c:forEach>
	<tags:tabControls tab="${tab}" flow="${flow}"
		localButtons="${localButtons}" willSave="${willSave}" />
</form:form>
<!-- MAIN BODY ENDS HERE -->


<c:forEach items="${command.study.epochs}" var="epoch"
	varStatus="epochCount">
	<div id="dummy-inclusionRow-${epochCount.index}" style="display: none">
	<table border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<td><textarea
				id="study.epochs[${epochCount.index }].inclusionEligibilityCriteria[PAGE.ROW.INDEX].questionText"
				name="study.epochs[${epochCount.index }].inclusionEligibilityCriteria[PAGE.ROW.INDEX].questionText"
				rows="1" cols="95" class="validate-notEmpty"></textarea></td>
			<td><input type="checkbox"
				id="study.epochs[${epochCount.index }].inclusionEligibilityCriteria[PAGE.ROW.INDEX].notApplicableIndicator"
				name="study.epochs[${epochCount.index }].inclusionEligibilityCriteria[PAGE.ROW.INDEX].notApplicableIndicator" />
			</td>
			<td><a
				href="javascript:RowManager.deleteRow(instanceInclusionRow_${epochCount.index},PAGE.ROW.INDEX,-1);"><img
				src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		</tr>
	</table>
	</div>
	<div id="dummy-exclusionRow-${epochCount.index}" style="display: none">
	<table border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<td><textarea
				id="study.epochs[${epochCount.index }].exclusionEligibilityCriteria[PAGE.ROW.INDEX].questionText"
				name="study.epochs[${epochCount.index }].exclusionEligibilityCriteria[PAGE.ROW.INDEX].questionText"
				rows="1" cols="95" class="validate-notEmpty"></textarea></td>
			<td><input type="checkbox"
				id="study.epochs[${epochCount.index }].exclusionEligibilityCriteria[PAGE.ROW.INDEX].notApplicableIndicator"
				name="study.epochs[${epochCount.index }].exclusionEligibilityCriteria[PAGE.ROW.INDEX].notApplicableIndicator" />
			</td>
			<td><a
				href="javascript:RowManager.deleteRow(instanceExclusionRow_${epochCount.index},PAGE.ROW.INDEX,-1);"><img
				src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		</tr>
	</table>
	</div>
</c:forEach>


</body>
</html>

