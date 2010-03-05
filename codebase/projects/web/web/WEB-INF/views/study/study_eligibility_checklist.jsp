<%@ include file="taglibs.jsp"%>

<html>
<head>
<title><studyTags:htmlTitle study="${command.study}" /></title>
<tags:dwrJavascriptLink objects="StudyAjaxFacade" />

<script language="JavaScript" type="text/JavaScript">
	function uploadFile(index, name, epochs){
		$('uploadIndicator-'+index).style.display=''
		$('name').value=name;
		for (i=0;i<epochs;i++){
			if(i != index){
				$('criteriaFile-'+i).name='criteriaFile-'+i
			}
		}
		$('epochIndex').value=index ;
		$('_target').name='_target${tab.number}';
		 $('command').submit();
	}
</script>
</head>
<body>
<c:choose>
	<c:when test="${fn:length(command.study.epochs) > 0}">
		<tags:instructions code="study_eligibility_checklist" />
	</c:when>
	<c:otherwise>
		<tags:instructions code="study_no_epoch_eligibility" />
	</c:otherwise>
</c:choose>

<form:form method="post" enctype="multipart/form-data">
	<input type="hidden" name="name" id="name">
	<input type="hidden" name="epochIndex" id="epochIndex">
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
			<div class="row">
				<div class="label"><fmt:message key="study.eligibility.selectcaDSRFileToImport"/></div>
				<div class="value">
					<input type="file" name="study.criteriaFile" id="criteriaFile-${epochCount.index}">
					<tags:button type="button" size="small" color="blue" value="Upload" onclick="uploadFile('${epochCount.index}','${epoch.name}', '${fn:length(command.study.epochs)}');"/>
					<img id="uploadIndicator-${epochCount.index }" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none" />
					<tags:hoverHint id="study.criteriafile-${epochCount.index}" keyProp="study.criteriafile" />
				</div>
			</div>
			<chrome:division title="Inclusion Criteria" minimize="${(not empty epochIndex &&  epochIndex == epochCount.index)?'false':'true'}"
				divIdToBeMinimized="inclusionCriteria-${epochCount.index}">
				<div id="inclusionCriteria-${epochCount.index}" style="${(not empty epochIndex &&  epochIndex == epochCount.index) ? '': 'display: none'}">
				<table width="100%">
					<tr>
						<td>
						<table border="0" cellspacing="0"  cellpadding="0"
							id="addInclusionRowTable-${epochCount.index}"
							class="tablecontent">
							<tr id="hInclusionEligibility--${epochCount.index}">
								<th width="80%"><span class="label"><tags:requiredIndicator /><fmt:message key="study.question"/></span>&nbsp;<tags:hoverHint
									id="study.inclusionEligibilityCriteria.questionText-${epochCount.index}"
									keyProp="study.inclusionEligibilityCriteria.questionText" /></th>
								<th width="10%"><span class="label"><fmt:message key="c3pr.common.na"/></span>&nbsp;<tags:hoverHint
									id="NA" keyProp="NA" /></th>
								<th width="5%"></th>
							</tr>
							<c:choose>
								<c:when
									test="${fn:length(epoch.inclusionEligibilityCriteria) == 0}">
									<tr>
										<td align="left"
											id="addInclusionEligibilityCriteria-${epochCount.index}"><fmt:message
											key="epoch.addInclusionCriteria" /></td>
									</tr>
								</c:when>
								<c:otherwise>
							<c:forEach varStatus="status" var="ieCrit"
								items="${command.study.epochs[epochCount.index].inclusionEligibilityCriteria}">
								<tr
									id="addInclusionRowTable-${epochCount.index}-${status.index}">
									<td><form:textarea
										path="study.epochs[${epochCount.index }].inclusionEligibilityCriteria[${status.index}].questionText"
										rows="1" cols="80" cssClass="required validate-notEmpty" /></td>
									<td><form:checkbox
										path="study.epochs[${epochCount.index }].inclusionEligibilityCriteria[${status.index}].notApplicableIndicator" />
									</td>
									<td><a
										href="javascript:RowManager.deleteRow(instanceInclusionRow_${epochCount.index},${status.index},'${ieCrit.id==null?'HC#':'ID#'}${ieCrit.id==null?ieCrit.hashCode:ieCrit.id}');"><img
										src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
								</tr>
							</c:forEach>
									</c:otherwise>
							</c:choose>
						</table>
						</td>
					</tr>
				</table>
				</div>
				<br>
				<tags:button type="button" color="blue" icon="add" value="Add Inclusion Criterion"
								onclick="$('inclusionCriteria-${epochCount.index}').show();RowManager.addRow(instanceInclusionRow_${epochCount.index});
								$('addInclusionEligibilityCriteria-${epochCount.index}') != null ? $('addInclusionEligibilityCriteria-${epochCount.index}').hide():'';" size="small"/>
				<br>				
			</chrome:division>
			<chrome:division title="Exclusion Criteria" minimize="${(not empty epochIndex &&  epochIndex == epochCount.index)?'false':'true'}"
				divIdToBeMinimized="exclusionCriteria-${epochCount.index}">
				<div id="exclusionCriteria-${epochCount.index}" style="${(not empty epochIndex &&  epochIndex == epochCount.index) ? '': 'display: none'}">
				<table width="100%">
					<tr>
						<td>
						<table border="0"  cellspacing="0" cellpadding="0"
							class="tablecontent"
							id="addExclusionRowTable-${epochCount.index}">
							<tr id="hExclusionEligibility-${epochCount.index}">
								<th width="80%"><span class="label"><tags:requiredIndicator /><fmt:message key="study.question"/></span>&nbsp;<tags:hoverHint
									id="study.exclusionEligibilityCriteria.questionText-${epochCount.index}"
									keyProp="study.exclusionEligibilityCriteria.questionText" /></th>
								<th width="10%"><span class="label"><fmt:message key="c3pr.common.na"/></span>&nbsp;<tags:hoverHint
									id="NA" keyProp="NA" /></th>
								<th width="5%"></th>
							</tr>
							
							<c:choose>
								<c:when
									test="${fn:length(epoch.exclusionEligibilityCriteria) == 0}">
									<tr>
										<td align="left"
											id="addExclusionEligibilityCriteria-${epochCount.index}"><fmt:message
											key="epoch.addExclusionCriteria" /></td>
									</tr>
								</c:when>
								<c:otherwise>
							<c:forEach varStatus="status" var="eeCrit"
								items="${command.study.epochs[epochCount.index].exclusionEligibilityCriteria}">
								<tr
									id="addExclusionRowTable-${epochCount.index}-${status.index}">
									<td><form:textarea
										path="study.epochs[${epochCount.index }].exclusionEligibilityCriteria[${status.index}].questionText"
										rows="1" cols="80" cssClass="required validate-notEmpty" /></td>
									<td><form:checkbox
										path="study.epochs[${epochCount.index }].exclusionEligibilityCriteria[${status.index}].notApplicableIndicator" />
									</td>
									<td><a
										href="javascript:RowManager.deleteRow(instanceExclusionRow_${epochCount.index},${status.index},'${eeCrit.id==null?'HC#':'ID#'}${eeCrit.id==null?eeCrit.hashCode:eeCrit.id}');">
									<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
								</tr>
							</c:forEach>
							</c:otherwise>
							</c:choose>
						</table>
						</td>
					</tr>
				</table>

				</div>
				<br>
				<tags:button type="button" color="blue" icon="add" value="Add Exclusion Criterion"
								onclick="$('exclusionCriteria-${epochCount.index}').show();RowManager.addRow(instanceExclusionRow_${epochCount.index});
								$('addExclusionEligibilityCriteria-${epochCount.index}') != null ? $('addExclusionEligibilityCriteria-${epochCount.index}').hide():'';" size="small"/>
								<br>
			</chrome:division>
			</td>

		</tags:minimizablePanelBox>
	</c:forEach>
	<tags:tabControls tab="${tab}" flow="${flow}"
		 willSave="${willSave}" >
				<jsp:attribute name="localButtons">
			<c:if test="${!empty param.parentStudyFlow}">
			<script>
			function returnToParentUsingButton(parentStudyFlow, parentStudyId){
				if(parentStudyFlow == 'Amend Study'){
					$('parentStudyFormButton').action = "/c3pr/pages/study/amendStudy?studyId="+parentStudyId ;
				}else{
					$('parentStudyFormButton').action = "/c3pr/pages/study/editStudy?studyId="+parentStudyId ;
				}
				$('parentStudyFormButton').submit();
			}
			</script>
			<tags:button type="button" color="blue" icon="back" value="Return to parent" onclick="returnToParentUsingButton('${param.parentStudyFlow}', '${command.study.parentStudyAssociations[0].parentStudy.id}')" />
			</c:if>
		</jsp:attribute>
</tags:tabControls>
		
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
				rows="1" cols="80" class="required validate-notEmpty&&MAXLENGTH1024"></textarea></td>
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
				rows="1" cols="80" class="required validate-notEmpty&&MAXLENGTH1024"></textarea></td>
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
<script>
$('criteriaFile-'+${epochIndex}).scrollIntoView();
</script>

</body>
</html>

