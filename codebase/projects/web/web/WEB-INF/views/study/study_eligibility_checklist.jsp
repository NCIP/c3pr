<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>
<head>
<tags:includeScriptaculous />
<tags:dwrJavascriptLink objects="createStudy" />

<title>${tab.longTitle}</title>
<style type="text/css">
    .label {
        width: 12em;
        text-align: right;
        padding: 4px;
    }
</style>
<script language="JavaScript" type="text/JavaScript">

function fireAction(action,selectedEpoch, selected, area) {
    document.getElementById('command')._target.name = '_noname';
    document.form._action.value = action;
    document.form._selected.value = selected;
    $('_selectedEpoch').value = selectedEpoch;


    if(action == 'removeExclusionCriteria'){
        //need to disable validations while removing
        source = 'excCriterias[' + selected + '].questionText';
        $(source).className = 'none';
        Effect.Puff('bex-' + selected);
    }
    else if(action == 'removeInclusionCriteria'){
        type = 'incCriterias[' + selected + '].questionText';
        $(type).className = 'none';
    }

    document.form.submit();

}

function add(area, count)
{

    //if (area == 'InclusionTable')
    var str = createInclusionRow(count);
    //else
    //	var str = createExclusionRow(count);
    document.getElementById(area).innerHTML = document.getElementById(area).innerHTML + str;
    document.getElementById('bex-' + count).style.display = 'none';
    Effect.Appear('bex-' + count);
    count++;
    new Ajax.Request('createStudy', {method:'post', postBody:'_target5=5&_page5=5&_action=addInclusionCriteria&_selected=', onSuccess:handlerFunc, onFailure:errFunc});
}
var handlerFunc = function(t) {
    //alert(t.responseText);
}

var errFunc = function(t) {
    alert('Error ' + t.status + ' -- ' + t.statusText);
}
function remove(count, area)
{
    document.getElementById('bex-' + count).innerHTML = ' ';
}

function createInclusionRow(count)
{
    var str = '<tr id="bex-' + count + '">' +
              '<td width="1%">' +
              '<input type="hidden" name=command.incCriterias[' + count + '].questionNumber value=' + count + '/>' +
              count + '</td>' +
              '<td width="78%">' +
              '<input type="text" name=command.incCriterias[' + count + '].questionText value=command.inclusionEligibilityCriterias[' + count + '].questionText size="150" "' +
              '</td>' +
              '<td width=10%>' +
              '	<input type="checkbox" name=command.incCriterias[' + count + '].notApplicableIndicator value=command.inclusionEligibilityCriterias[' + count + '].notApplicableIndicator>NA' +
              '</td>' +
              '<td width=5%>' +
              '	<a href="javascript:remove(count,InclusionTable);"><img' +
              '		 src="<tags:imageUrl name="checkno.gif"/>" border="0">' +
              '	</a>' +
              '</td>'

    return str;
}

function createExclusionRow(count)
{
    var str = '<tr id="bex-' + count + '">' +
              '<td width="1%">' +
              '<input type="hidden" name=command.excCriterias[' + count + '].questionNumber value=' + count + '/>' +
              count + '</td>' +
              '<td width="78%">' +
              '<input type="text" name=command.excCriterias[' + count + '].questionText value=command.inclusionEligibilityCriterias[' + count + '].questionText size="150" "' +
              '</td>' +
              '<td width=10%>' +
              '	<input type="checkbox" name=command.excCriterias[' + count + '].notApplicableIndicator value=command.inclusionEligibilityCriterias[' + count + '].notApplicableIndicator>NA' +
              '</td>' +
              '<td width=5%>' +
              '	<a href="javascript:remove(count,ExclusionTable);"><img' +
              '		 src="<tags:imageUrl name="checkno.gif"/>" border="0">' +
              '	</a>' +
              '</td>'

    return str;
}
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
var instanceInclusionRow = {
	add_row_division_id: "addInclusionRowTable-${epochCount.index}",
	skeleton_row_division_id: "dummy-inclusionRow-${epochCount.index}",
	initialIndex: ${fn:length(command.epochs[epochCount.index].inclusionEligibilityCriteria)},
	path: "epochs[${epochCount.index }].inclusionEligibilityCriteriaAliased"
};
var instanceExclusionRow = {
	add_row_division_id: "addExclusionRowTable-${epochCount.index}",
	skeleton_row_division_id: "dummy-exclusionRow-${epochCount.index}",
	initialIndex: ${fn:length(command.epochs[epochCount.index].exclusionEligibilityCriteria)}, 
	path: "epochs[${epochCount.index }].exclusionEligibilityCriteriaAliased"
};
</script>
</head>
<body>

<form:form method="post" name="form">
<tags:tabFields tab="${tab}"/>
	<div><input type="hidden" name="_action" value=""> <input
		type="hidden" name="_selected" value=""> <input type="hidden"
		id="_selectedEpoch" name="_selectedEpoch" value=""></div>
	<!-- MAIN BODY STARTS HERE -->
	
	<c:forEach items="${command.epochs}" var="epoch" varStatus="epochCount">
	<script>
		var instanceInclusionRow_${epochCount.index} = {
			add_row_division_id: "addInclusionRowTable-${epochCount.index}",
			skeleton_row_division_id: "dummy-inclusionRow-${epochCount.index}",
			initialIndex: ${fn:length(command.epochs[epochCount.index].inclusionEligibilityCriteria)},
			path: "epochs[${epochCount.index }].inclusionEligibilityCriteriaAliased"
		};
		var instanceExclusionRow_${epochCount.index} = {
			add_row_division_id: "addExclusionRowTable-${epochCount.index}",
			skeleton_row_division_id: "dummy-exclusionRow-${epochCount.index}",
			initialIndex: ${fn:length(command.epochs[epochCount.index].exclusionEligibilityCriteria)}, 
			path: "epochs[${epochCount.index }].exclusionEligibilityCriteriaAliased"
		};
		rowInserters.push(instanceInclusionRow_${epochCount.index});
		rowInserters.push(instanceExclusionRow_${epochCount.index});
	</script>
	
	<c:if test="${epoch.class.name=='edu.duke.cabig.c3pr.domain.TreatmentEpoch' }">
		
		<tags:minimizablePanelBox	title="${epoch.name} : ${epoch.descriptionText }"	boxId="${epoch.name}">
			<table border="0" id="table1" cellspacing="5">
				<tr>
					<td valign="top" width="600">
					<tags:minimizablePanelBox	boxId="InclusionTable" title="Inclusion Criteria">
						<p id="instructions">*NA - Allow Not Applicable answer<br>
						Yes and No are permissible answers</p>
						<p><b>Inclusion Criterion</b><a
							href="javascript:RowManager.addRow(instanceInclusionRow_${epochCount.index});"><img
							src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a></p>
						<table border="0" cellspacing="0" cellpadding="0" id="addInclusionRowTable-${epochCount.index}" class="mytable1">
							<tr>
								<td class="alt"><b>Question<span class="red">*</span></b></td>
								<td class="alt"><b>NA</b></td>
								<th class="specalt"></th>
							</tr>
							<c:forEach varStatus="status"
								items="${command.epochs[epochCount.index].inclusionEligibilityCriteria}">
								<tr id="addInclusionRowTable-${epochCount.index}-${status.index}">
									<td class="alt"><form:hidden
										path="epochs[${epochCount.index }].inclusionEligibilityCriteriaAliased[${status.index}].questionNumber" />
									<form:textarea
										path="epochs[${epochCount.index }].inclusionEligibilityCriteriaAliased[${status.index}].questionText"
										rows="1" cols="50" cssClass="validate-notEmpty" /></td>
									<td class="alt"><form:checkbox
										path="epochs[${epochCount.index }].inclusionEligibilityCriteriaAliased[${status.index}].notApplicableIndicator" />
									</td>
									<td class="alt"><a
										href="javascript:RowManager.deleteRow(instanceInclusionRow_${epochCount.index},PAGE.ROW.INDEX);"><img
										src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
								</tr>
							</c:forEach>
						</table>
					</tags:minimizablePanelBox></td>
					<td valign="top" width="600">
					<tags:minimizablePanelBox	boxId="ExclusionTable" title="Exclusion Criteria">
						<p id="instructions">*NA - Allow Not Applicable answer <br>
						Yes and No are permissible answers</p>
	
						<p><b>Exclusion Criterion</b><a
							href="javascript:RowManager.addRow(instanceExclusionRow_${epochCount.index});"><img
							src="<tags:imageUrl name="checkyes.gif"/>" border="0"
							alt="Add"></a></p>
						<table border="0" cellspacing="0" cellpadding="0" class="mytable1" id="addExclusionRowTable-${epochCount.index}">
							<tr>
								<td class="alt"><b>Question<span class="red">*</span></b></td>
								<td class="alt"><b>*NA</b></td>
								<th class="specalt"></th>
	
							</tr>
							<c:forEach varStatus="status"
								items="${command.epochs[epochCount.index].exclusionEligibilityCriteria}">
								<tr id="addExclusionRowTable-${epochCount.index}-${status.index}">
									<td class="alt" align="left"><form:hidden
										path="epochs[${epochCount.index }].exclusionEligibilityCriteriaAliased[${status.index}].questionNumber" />
									<form:textarea
										path="epochs[${epochCount.index }].exclusionEligibilityCriteriaAliased[${status.index}].questionText"
										rows="1" cols="50" cssClass="validate-notEmpty" /></td>
									<td class="alt" align="left"><form:checkbox
										path="epochs[${epochCount.index }].exclusionEligibilityCriteriaAliased[${status.index}].notApplicableIndicator" />
									</td>
									<td class="alt"><a
										href="javascript:RowManager.deleteRow(instanceExclusionRow_${epochCount.index},PAGE.ROW.INDEX);"><img
										src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
								</tr>
							</c:forEach>
						</table>
					</tags:minimizablePanelBox></td>
				</tr>
			</table>
		</tags:minimizablePanelBox>
	</c:if>
	</c:forEach>
	<!-- MAIN BODY ENDS HERE -->
</form:form>
<c:forEach items="${command.epochs}" var="epoch" varStatus="epochCount">
<c:if test="${epoch.class.name=='edu.duke.cabig.c3pr.domain.TreatmentEpoch' }">
	<div id="dummy-inclusionRow-${epochCount.index}" style="display:none">
	<table>
		<tr>
			<td class="alt" align="left"><input type="hidden"
				id="epochs[${epochCount.index }].inclusionEligibilityCriteriaAliased[PAGE.ROW.INDEX].questionNumber"
				name="epochs[${epochCount.index }].inclusionEligibilityCriteriaAliased[PAGE.ROW.INDEX].questionNumber" />
			</td>
			<td class="alt" align="left"><textarea
				id="epochs[${epochCount.index }].inclusionEligibilityCriteriaAliased[PAGE.ROW.INDEX].questionText"
				name="epochs[${epochCount.index }].inclusionEligibilityCriteriaAliased[PAGE.ROW.INDEX].questionText"
				rows="1" cols="50" cssClass="validate-notEmpty"></textarea></td>
			<td class="alt" align="left"><input type="checkbox"
				id="epochs[${epochCount.index }].inclusionEligibilityCriteriaAliased[PAGE.ROW.INDEX].notApplicableIndicator"
				name="epochs[${epochCount.index }].inclusionEligibilityCriteriaAliased[PAGE.ROW.INDEX].notApplicableIndicator" />
			</td>
			<td class="alt"><a
				href="javascript:RowManager.deleteRow(instanceInclusionRow_${epochCount.index},PAGE.ROW.INDEX);"><img
				src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		</tr>
	</table>
	</div>
	<div id="dummy-exclusionRow-${epochCount.index}" style="display:none">
	<table border="0" cellspacing="0" cellpadding="0" id="mytable1">
		<tr>
			<td class="alt" align="left"><input type="hidden"
				id="epochs[${epochCount.index }].exclusionEligibilityCriteriaAliased[PAGE.ROW.INDEX].questionNumber"
				name="epochs[${epochCount.index }].exclusionEligibilityCriteriaAliased[PAGE.ROW.INDEX].questionNumber" />
			</td>
			<td class="alt" align="left"><textarea
				id="epochs[${epochCount.index }].exclusionEligibilityCriteriaAliased[PAGE.ROW.INDEX].questionText"
				name="epochs[${epochCount.index }].exclusionEligibilityCriteriaAliased[PAGE.ROW.INDEX].questionText"
				rows="1" cols="50" cssClass="validate-notEmpty"></textarea></td>
			<td class="alt" align="left"><input type="checkbox"
				id="epochs[${epochCount.index }].exclusionEligibilityCriteriaAliased[PAGE.ROW.INDEX].notApplicableIndicator"
				name="epochs[${epochCount.index }].exclusionEligibilityCriteriaAliased[PAGE.ROW.INDEX].notApplicableIndicator" />
			</td>
			<td class="alt"><a
				href="javascript:RowManager.deleteRow(instanceExclusionRow_${epochCount.index},PAGE.ROW.INDEX);"><img
				src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		</tr>
	</table>
	</div>
</c:if>
</c:forEach>
</body>
</html>
