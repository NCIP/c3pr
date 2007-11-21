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
                    softDelete: ${softDelete == 'true'},
                    path: "epochs[${epochCount.index }].inclusionEligibilityCriteria"
                };
                var instanceExclusionRow_${epochCount.index} = {
                    add_row_division_id: "addExclusionRowTable-${epochCount.index}",
                    skeleton_row_division_id: "dummy-exclusionRow-${epochCount.index}",
                    initialIndex: ${fn:length(command.epochs[epochCount.index].exclusionEligibilityCriteria)},
					softDelete: ${softDelete == 'true'},
                    path: "epochs[${epochCount.index }].exclusionEligibilityCriteria"
                };
                RowManager.addRowInseter(instanceInclusionRow_${epochCount.index});
                RowManager.addRowInseter(instanceExclusionRow_${epochCount.index});
            </script>


            <tags:minimizablePanelBox	title="${epoch.name}"	boxId="${epoch.name}">
                <table border="0" width="100%" id="table1" cellspacing="5">
                    <tr>
                        <td valign="top">
                            <br /><p>
                            <chrome:division title="Inclusion Criteria">
                            
                            <table border="0" cellspacing="0" width="100%" cellpadding="0"
                                   id="addInclusionRowTable-${epochCount.index}" class="tablecontent">
                                <tr>
                                    <th><span class="label required-indicator">Question</span></th>
                                    <th>NA</th>
                                    <th></th>
                                </tr>
                                <c:forEach varStatus="status" var="ieCrit"
                                           items="${command.epochs[epochCount.index].inclusionEligibilityCriteria}">
                                    <tr id="addInclusionRowTable-${epochCount.index}-${status.index}">
                                        <td>
                                            <form:textarea
                                                    path="epochs[${epochCount.index }].inclusionEligibilityCriteria[${status.index}].questionText"
                                                    rows="1" cols="95" cssClass="validate-notEmpty"/></td>
                                        <td><form:checkbox
                                                path="epochs[${epochCount.index }].inclusionEligibilityCriteria[${status.index}].notApplicableIndicator"/>
                                        </td>
                                        <td><a
                                                href="javascript:RowManager.deleteRow(instanceInclusionRow_${epochCount.index},${status.index},${ieCrit.hashCode});"><img
                                                src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                                    </tr>
                                    
                                </c:forEach>
                            </table>
                            <p align="right">
                                <input type="button" value="Add Inclusion Criterion"
                                       onclick="RowManager.addRow(instanceInclusionRow_${epochCount.index});">
                            </p>
                            </chrome:division></td></tr>
                    <tr>
                        <td valign="top">
                            <chrome:division title="Exclusion Criteria">
                                
                                <table border="0" width="100%" cellspacing="0" cellpadding="0" class="tablecontent" id="addExclusionRowTable-${epochCount.index}">
                                    <tr>
                                        <th><span class="label required-indicator">Question</span></th>
                                        <th>NA</th>
                                        <th></th>

                                    </tr>
                                    <c:forEach varStatus="status" var="eeCrit"
                                               items="${command.epochs[epochCount.index].exclusionEligibilityCriteria}">
                                        <tr id="addExclusionRowTable-${epochCount.index}-${status.index}">
                                            <td>
                                                <form:textarea
                                                        path="epochs[${epochCount.index }].exclusionEligibilityCriteria[${status.index}].questionText"
                                                        rows="1" cols="95" cssClass="validate-notEmpty" /></td>
                                            <td><form:checkbox
                                                    path="epochs[${epochCount.index }].exclusionEligibilityCriteria[${status.index}].notApplicableIndicator" />
                                            </td>
                                            <td><a
                                                    href="javascript:RowManager.deleteRow(instanceExclusionRow_${epochCount.index},${status.index},${eeCrit.hashCode});"><img
                                                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <p align="right">
                                    <input type="button" value="Add Exclusion Criterion"
                                           onclick="RowManager.addRow(instanceExclusionRow_${epochCount.index});"/>
                                   </p>
                            </chrome:division></td>                            
                    </tr>
                    <tr><td>
                    	<span id="instructions">&nbsp;&nbsp;*NA - Allow not applicable answer.</span>
                    </td></tr>
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
            <table border="0" cellspacing="0" cellpadding="0" class="tablecontent">
                <tr>
                    <td>
                        <textarea
                                id="epochs[${epochCount.index }].inclusionEligibilityCriteria[PAGE.ROW.INDEX].questionText"
                                name="epochs[${epochCount.index }].inclusionEligibilityCriteria[PAGE.ROW.INDEX].questionText"
                                rows="1" cols="95" class="validate-notEmpty"></textarea></td>
                    <td><input type="checkbox"
                                                        id="epochs[${epochCount.index }].inclusionEligibilityCriteria[PAGE.ROW.INDEX].notApplicableIndicator"
                                                        name="epochs[${epochCount.index }].inclusionEligibilityCriteria[PAGE.ROW.INDEX].notApplicableIndicator" />
                    </td>
                    <td><a
                            href="javascript:RowManager.deleteRow(instanceInclusionRow_${epochCount.index},PAGE.ROW.INDEX,-1);"><img
                            src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                </tr>
            </table>
        </div>
        <div id="dummy-exclusionRow-${epochCount.index}" style="display:none">
            <table border="0" cellspacing="0" cellpadding="0" class="tablecontent">
                <tr>
                    <td>
                        <textarea
                                id="epochs[${epochCount.index }].exclusionEligibilityCriteria[PAGE.ROW.INDEX].questionText"
                                name="epochs[${epochCount.index }].exclusionEligibilityCriteria[PAGE.ROW.INDEX].questionText"
                                rows="1" cols="95" class="validate-notEmpty"></textarea></td>
                    <td><input type="checkbox"
                                                        id="epochs[${epochCount.index }].exclusionEligibilityCriteria[PAGE.ROW.INDEX].notApplicableIndicator"
                                                        name="epochs[${epochCount.index }].exclusionEligibilityCriteria[PAGE.ROW.INDEX].notApplicableIndicator" />
                    </td>
                    <td><a
                            href="javascript:RowManager.deleteRow(instanceExclusionRow_${epochCount.index},PAGE.ROW.INDEX,-1);"><img
                            src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                </tr>
            </table>
        </div>
    </c:if>
</c:forEach>
</body>
</html>
