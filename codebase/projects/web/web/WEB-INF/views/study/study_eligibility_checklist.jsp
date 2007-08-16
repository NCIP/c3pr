<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>

<html>
<head>
    <tags:includeScriptaculous />
    <tags:dwrJavascriptLink objects="StudyAjaxFacade" />

    <title>${tab.longTitle}</title>
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
<form:form method="post" name="form">
    <tags:tabFields tab="${tab}"/>

    <c:forEach items="${command.epochs}" var="epoch" varStatus="epochCount">
        <c:if test="${epoch.class.name=='edu.duke.cabig.c3pr.domain.TreatmentEpoch' }">
            <script>
                var instanceInclusionRow_${epochCount.index} = {
                    add_row_division_id: "addInclusionRowTable-${epochCount.index}",
                    skeleton_row_division_id: "dummy-inclusionRow-${epochCount.index}",
                    initialIndex: ${fn:length(command.epochs[epochCount.index].inclusionEligibilityCriteria)},
                    path: "epochs[${epochCount.index }].inclusionEligibilityCriteria"
                };
                var instanceExclusionRow_${epochCount.index} = {
                    add_row_division_id: "addExclusionRowTable-${epochCount.index}",
                    skeleton_row_division_id: "dummy-exclusionRow-${epochCount.index}",
                    initialIndex: ${fn:length(command.epochs[epochCount.index].exclusionEligibilityCriteria)},
                    path: "epochs[${epochCount.index }].exclusionEligibilityCriteria"
                };
                RowManager.addRowInseter(instanceInclusionRow_${epochCount.index});
                RowManager.addRowInseter(instanceExclusionRow_${epochCount.index});
            </script>


            <tags:minimizablePanelBox	title="${epoch.name}"	boxId="${epoch.name}">
                <table border="0" width="100%" id="table1" cellspacing="5">
                    <tr>
                        <td valign="top">
                            <p id="instructions">*NA - Allow Not Applicable answer<br>
                                Yes and No are permissible answers</p>
                            <p>
                                <chrome:division title="Inclusion Criteria">
                            <p>
                                <input type="button" value="Add Inclusion Criterion"
                                       onclick="RowManager.addRow(instanceInclusionRow_${epochCount.index});">
                            </p>
                            <table border="0" cellspacing="0" width="100%" cellpadding="0"
                                   id="addInclusionRowTable-${epochCount.index}" class="mytable1">
                                <tr>
                                    <th class="alt" align="left">Question<span class="red">*</span></th>
                                    <th class="alt" align="left">NA<span class="red">*</span></th>
                                    <th class="alt"></th>
                                </tr>
                                <c:forEach varStatus="status"
                                           items="${command.epochs[epochCount.index].inclusionEligibilityCriteria}">
                                    <tr id="addInclusionRowTable-${epochCount.index}-${status.index}">
                                        <td class="alt" align="left">
                                            <form:textarea
                                                    path="epochs[${epochCount.index }].inclusionEligibilityCriteria[${status.index}].questionText"
                                                    rows="1" cols="80" cssClass="validate-notEmpty" /></td>
                                        <td class="alt" align="left"><form:checkbox
                                                path="epochs[${epochCount.index }].inclusionEligibilityCriteria[${status.index}].notApplicableIndicator" />
                                        </td>
                                        <td class="alt"><a
                                                href="javascript:RowManager.deleteRow(instanceInclusionRow_${epochCount.index},${status.index});"><img
                                                src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                                    </tr>
                                </c:forEach>
                            </table>
                            </chrome:division></td></tr>
                    <tr>
                        <td valign="top">
                            <chrome:division title="Exclusion Criteria">
                                <p>
                                    <input type="button" value="Add Exclusion Criterion"
                                           onclick="RowManager.addRow(instanceExclusionRow_${epochCount.index});"/>
                                   </p>
                                <table border="0" width="100%" cellspacing="0" cellpadding="0" class="mytable1" id="addExclusionRowTable-${epochCount.index}">
                                    <tr>
                                        <th class="alt" align="left">Question<span class="red">*</span></th>
                                        <th class="alt" align="left">NA<span class="red">*</span></th>
                                        <th class="alt"></th>

                                    </tr>
                                    <c:forEach varStatus="status"
                                               items="${command.epochs[epochCount.index].exclusionEligibilityCriteria}">
                                        <tr id="addExclusionRowTable-${epochCount.index}-${status.index}">
                                            <td class="alt" align="left">
                                                <form:textarea
                                                        path="epochs[${epochCount.index }].exclusionEligibilityCriteria[${status.index}].questionText"
                                                        rows="1" cols="80" cssClass="validate-notEmpty" /></td>
                                            <td class="alt" align="left"><form:checkbox
                                                    path="epochs[${epochCount.index }].exclusionEligibilityCriteria[${status.index}].notApplicableIndicator" />
                                            </td>
                                            <td class="alt"><a
                                                    href="javascript:RowManager.deleteRow(instanceExclusionRow_${epochCount.index},${status.index});"><img
                                                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </chrome:division></td>
                    </tr>
                </table>
            </tags:minimizablePanelBox>
        </c:if>
    </c:forEach>
    <!-- MAIN BODY ENDS HERE -->
    <tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
</form:form>
<c:forEach items="${command.epochs}" var="epoch" varStatus="epochCount">
    <c:if test="${epoch.class.name=='edu.duke.cabig.c3pr.domain.TreatmentEpoch' }">
        <div id="dummy-inclusionRow-${epochCount.index}" style="display:none">
            <table border="0" cellspacing="0" cellpadding="0" class="mytable1">
                <tr>
                    <td class="alt" align="left">
                        <textarea
                                id="epochs[${epochCount.index }].inclusionEligibilityCriteria[PAGE.ROW.INDEX].questionText"
                                name="epochs[${epochCount.index }].inclusionEligibilityCriteria[PAGE.ROW.INDEX].questionText"
                                rows="1" cols="80" class="validate-notEmpty"></textarea></td>
                    <td class="alt" align="left"><input type="checkbox"
                                                        id="epochs[${epochCount.index }].inclusionEligibilityCriteria[PAGE.ROW.INDEX].notApplicableIndicator"
                                                        name="epochs[${epochCount.index }].inclusionEligibilityCriteria[PAGE.ROW.INDEX].notApplicableIndicator" />
                    </td>
                    <td class="alt"><a
                            href="javascript:RowManager.deleteRow(instanceInclusionRow_${epochCount.index},PAGE.ROW.INDEX);"><img
                            src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                </tr>
            </table>
        </div>
        <div id="dummy-exclusionRow-${epochCount.index}" style="display:none">
            <table border="0" cellspacing="0" cellpadding="0" class="mytable1">
                <tr>
                    <td class="alt" align="left">
                        <textarea
                                id="epochs[${epochCount.index }].exclusionEligibilityCriteria[PAGE.ROW.INDEX].questionText"
                                name="epochs[${epochCount.index }].exclusionEligibilityCriteria[PAGE.ROW.INDEX].questionText"
                                rows="1" cols="80" class="validate-notEmpty"></textarea></td>
                    <td class="alt" align="left"><input type="checkbox"
                                                        id="epochs[${epochCount.index }].exclusionEligibilityCriteria[PAGE.ROW.INDEX].notApplicableIndicator"
                                                        name="epochs[${epochCount.index }].exclusionEligibilityCriteria[PAGE.ROW.INDEX].notApplicableIndicator" />
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
