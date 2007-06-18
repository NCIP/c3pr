<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>


<html>
<head>
    <script type="text/javascript">

        function manageSelectBox(box) {
            if (box.value == 'true') {
                //		document.getElementById('cooperativeGroups').style.display='none';
                Effect.OpenUp('cooperativeGroups');
            }
            if (box.value == 'false') {
                //		document.getElementById('cooperativeGroups').style.display='none';
                Effect.CloseDown('cooperativeGroups');
            }
        }

    </script>

</head>
<body>
<%-- Can't use tags:tabForm b/c there are two boxes in the form --%>
<tags:tabForm tab="${tab}" flow="${flow}">
<jsp:attribute name="singleFields">

<div>
    <input type="hidden" name="_action" value="">
    <input type="hidden" name="_selected" value="">
</div>

<div class="content">
<div class="row">
    <div class="label">*Short Title:</div>
    <div class="value">
        <form:input path="shortTitleText" size="40" maxlength="30"
                    cssClass="validate-notEmpty"/>
    </div>
</div>

<div class="row">
    <div class="label">*Long Title:</div>
    <div class="value">
        <form:textarea path="longTitleText" rows="3" cols="50"
                       cssClass="validate-notEmpty&&maxlength200"/>
    </div>
</div>
<div class="row">
    <div class="label">Description:</div>
    <div class="value">
        <form:textarea path="descriptionText" rows="5" cols="50"
                       cssClass="validate-maxlength2000"/>
    </div>
</div>

<div class="row">

    <div class="label">Precis:</div>
    <div class="value">
        <form:textarea path="precisText" rows="2" cols="50"
                       cssClass="validate-maxlength200"/>
    </div>
</div>

<div class="row">
    <div class="label">Target Accrual:</div>
    <div class="value">
        <form:input path="targetAccrualNumber" size="10"
                    cssClass="validate-numeric"/>
    </div>
</div>

<div class="row">
    <div class="label">*Type:</div>
    <div class="value">
        <form:select path="type" cssClass="validate-notEmpty">
            <option value="">--Please Select--</option>
            <form:options items="${typeRefData}" itemLabel="desc"
                          itemValue="desc"/>
        </form:select>
    </div>
</div>

<div class="row">
    <div class="label">*Status:</div>
    <div class="value">
        <form:select path="status" cssClass="validate-notEmpty">
            <option value="">--Please Select--</option>
            <form:options items="${statusRefData}" itemLabel="desc"
                          itemValue="desc"/>
        </form:select>
    </div>
</div>

<div class="row">
    <div class="label">*Phase:</div>
    <div class="value">

        <form:select path="phaseCode" cssClass="validate-notEmpty">
            <option value="">--Please Select--</option>
            <form:options items="${phaseCodeRefData}" itemLabel="desc"
                          itemValue="desc"/>
        </form:select>
    </div>
</div>

<div class="row">
    <div class="label">Randomized:</div>
    <div class="value">
        <form:select path="randomizedIndicator">
            <option value="">--Please Select--</option>
            <form:options items="${yesNo}"
                          itemLabel="desc" itemValue="code"/>
        </form:select>
    </div>
</div>


<div class="row">
    <div class="label">Blinded:</div>
    <div class="value">
        <form:select path="blindedIndicator">
            <option value="">--Please Select--</option>
            <form:options items="${yesNo}" itemLabel="desc"
                          itemValue="code"/>
        </form:select>
    </div>
</div>

<div class="row">
    <div class="label">*Sponsor:</div>
    <div class="value">
        <form:select path="identifiers[0].source" cssClass="validate-notEmpty">
            <option value="">--Please Select--</option>
            <form:options items="${sponsorCodeRefData}" itemLabel="desc"
                          itemValue="desc"/>
        </form:select>
    </div>
</div>

<div class="row">
    <div class="label">*Sponsor Study Identifier:</div>
    <div class="value">
        <form:input path="identifiers[0].value" size="30"
                    maxlength="30" cssClass="validate-notEmpty"/>
        <input type="hidden" name="identifiers[0].type"
               value="Protocol Authority Identifier"/>
    </div>
</div>


<div class="row">
    <div class="label">*Multi-Institution:</div>
    <div class="value">
        <form:select path="multiInstitutionIndicator"
                     onchange="manageSelectBox(this);" cssClass="validate-notEmpty">
            <option value="">--Please Select--</option>
            <form:options items="${yesNo}"
                          itemLabel="desc" itemValue="code"/>
        </form:select>
    </div>
</div>


<div id="cooperativeGroups"
     <c:if test="${ (empty command.multiInstitutionIndicator) || command.multiInstitutionIndicator=='false'}">style="display:none;"</c:if>>
    <table width="80%" border="0">
        <tr>
            <td class="label130"><b>Coordinating Center:</td>
            <td align="left" width="39%"><form:select
                    path="identifiers[1].source">
                <option value="">--Please Select--</option>
                <form:options items="${coordinatingCenters}" itemLabel="desc"
                              itemValue="desc"/>
            </form:select></td>
            <td class="label20"><span class="red">*</span><b>Coordinating Center
                Study Identifier:</td>
            <td><form:input path="identifiers[1].value" size="30" maxlength="30"/>
                <input type="hidden" name="identifiers[1].type"
                       value="Coordinating Center Identifier"/></td>
        </tr>
    </table>
</div>

</div>
</jsp:attribute>
</tags:tabForm>
</body>
</html>
