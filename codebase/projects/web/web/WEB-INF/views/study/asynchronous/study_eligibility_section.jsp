<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    var instanceInclusionRow_${index} = {
          add_row_division_id: "addInclusionRowTable-${index}",
          skeleton_row_division_id: "dummy-inclusionRow-${index}",
          initialIndex: ${fn:length(command.study.epochs[index].inclusionEligibilityCriteria)},
          softDelete: ${softDelete == 'true'},
          path: "study.epochs[${index }].inclusionEligibilityCriteria"
    };
    var instanceExclusionRow_${index} = {
  		add_row_division_id: "addExclusionRowTable-${index}",
        skeleton_row_division_id: "dummy-exclusionRow-${index}",
        initialIndex: ${fn:length(command.study.epochs[index].exclusionEligibilityCriteria)},
	   	softDelete: ${softDelete == 'true'},
        path: "study.epochs[${index }].exclusionEligibilityCriteria"
    };
	RowManager.addRowInseter(instanceInclusionRow_${index});
    RowManager.addRowInseter(instanceExclusionRow_${index});
</script>
<tags:minimizablePanelBox title="${epoch.name}" boxId="${epoch.name}">
	<div class="row">
		<div class="label">
			<fmt:message key="study.eligibility.selectcaDSRFileToImport"/>
		</div>
		<div class="value" id="uploadCaDSRFile-${index}">
			<input type="hidden" name="name" value="${epoch.name}" />
			<input type="file" name="study.criteriaFile"/>
			<tags:button type="button" size="small" color="blue" value="Upload himanshu" onclick="uploadCaDSRFile('${index}','${epoch.name}')"/>
			<tags:hoverHint id="study.criteriafile-${epochCount.index}" keyProp="study.criteriafile" />
		</div>
	</div>
	<chrome:division title="Inclusion Criteria" minimize="false" divIdToBeMinimized="inclusionCriteria-${index}">
		<div id="inclusionCriteria-${index}">
				<table border="0" cellspacing="0"  cellpadding="0" id="addInclusionRowTable-${index}"
					class="tablecontent">
					<tr id="hInclusionEligibility--${index}" 
						<c:if test="${fn:length(epoch.inclusionEligibilityCriteria) == 0}">style="display:none;"</c:if>>
						<th width="80%">
							<span class="label">
								<tags:requiredIndicator /><fmt:message key="study.question"/>
							</span>
							<tags:hoverHint id="study.inclusionEligibilityCriteria.questionText-${index}"
							keyProp="study.inclusionEligibilityCriteria.questionText" />
						</th>
						<th width="10%">
							<span class="label">
								<fmt:message key="c3pr.common.na"/>
							</span>
							<tags:hoverHint id="NA" keyProp="NA" /></th>
						<th width="5%"></th>
					</tr>
					<c:forEach varStatus="status" var="ieCrit" items="${command.study.epochs[index].inclusionEligibilityCriteria}">
						<tr id="addInclusionRowTable-${index}-${status.index}">
							<td>
								<textarea name="study.epochs[${index }].inclusionEligibilityCriteria[${status.index}].questionText"
								rows="1" cols="80" class="required validate-notEmpty&&MAXLENGTH1024" />
							</td>
							<td>
								<input type="checkbox" name="study.epochs[${index }].inclusionEligibilityCriteria[${status.index}].notApplicableIndicator" />
							</td>
							<td>
								<a href="javascript:RowManager.deleteRow(instanceInclusionRow_${index},${status.index},'${ieCrit.id==null?'HC#':'ID#'}${ieCrit.id==null?ieCrit.hashCode:ieCrit.id}');"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0">
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<br>
			<tags:button type="button" color="blue" icon="add" value="Add Inclusion Criterion" onclick="$('hInclusionEligibility--${index}').show();RowManager.addRow(instanceInclusionRow_${index});" size="small"/>
	</chrome:division>
	<br>
	<chrome:division title="Exclusion Criteria" minimize="false" divIdToBeMinimized="exclusionCriteria-${index}">
		<div id="exclusionCriteria-${index}">
				<table border="0" width="95%" cellspacing="0" cellpadding="0" class="tablecontent"
					id="addExclusionRowTable-${index}">
					<tr id="hExclusionEligibility-${index}"
						<c:if test="${fn:length(epoch.exclusionEligibilityCriteria) == 0}">style="display:none;"</c:if>>
						<th width="80%">
							<span class="label">
								<tags:requiredIndicator /><fmt:message key="study.question"/>
							</span>
							<tags:hoverHint id="study.exclusionEligibilityCriteria.questionText-${index}" keyProp="study.exclusionEligibilityCriteria.questionText" />
						</th>
						<th width="10%">
							<fmt:message key="c3pr.common.na"/>
						</th>
						<th width="5%"></th>
					</tr>
					<c:forEach varStatus="status" var="eeCrit" items="${command.study.epochs[index].exclusionEligibilityCriteria}">
						<tr id="addExclusionRowTable-${index}-${status.index}">
							<td>
								<textarea name="study.epochs[${index }].exclusionEligibilityCriteria[${status.index}].questionText"
								rows="1" cols="80" class="required validate-notEmpty&&MAXLENGTH1024" />
							</td>
							<td>
								<input type="checkbox" name="study.epochs[${index }].exclusionEligibilityCriteria[${status.index}].notApplicableIndicator" />
							</td>
							<td>
								<a href="javascript:RowManager.deleteRow(instanceExclusionRow_${index},${status.index},'${eeCrit.id==null?'HC#':'ID#'}${eeCrit.id==null?eeCrit.hashCode:eeCrit.id}');">
									<img src="<tags:imageUrl name="checkno.gif"/>" border="0">
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<br>
			<tags:button type="button" color="blue" icon="add" value="Add Exclusion Criterion"
						onclick="$('hExclusionEligibility-${index}').show();RowManager.addRow(instanceExclusionRow_${index});" size="small"/>
	</chrome:division>
</tags:minimizablePanelBox>
