<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>


<html>
<head>
    <style type="text/css">
        .label {
            width: 10em;
            text-align: right;
            padding: 4px;
        }

        .label13 {
            width: 13em;
            text-align: right;
            padding: 4px;
        }

        .label130 {
            width: 11em;
            text-align: right;
            padding: 0px;
        }

        .label20 {
            width: 20em;
            text-align: right;
            padding: 4px;
        }
    </style>
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
<form:form method="post" name="studyDetails" cssClass="standard">
<tags:tabFields tab="${tab}"/>
<chrome:box title="${tab.shortTitle}">

<div>
    <input type="hidden" name="_action" value="">
    <input type="hidden" name="_selected" value="">
</div>

<chrome:division id="study-details" title="Basic Details">

<table border="0" width="90%" cellspacing="0" cellpadding="0">
<div><input type="hidden" name="_action" value=""></div>
<tags:hasErrorsMessage/>
<tr>
<td width="400">
    <table border="0" cellspacing="0" cellpadding="0" id="table1">
        <tr>
            <td class="label"><span class="red">*</span><em></em>Short Title:</td>
            <td><form:input path="shortTitleText" size="40" maxlength="30"
                            cssClass="validate-notEmpty"/></td>
        </tr>
        <tr>
            <td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
                     height="1" class="heightControl"></td>
        </tr>
        <tr>
            <td class="label"><span class="red">*</span><em></em>Long Title:</td>
            <td><form:textarea path="longTitleText" rows="3" cols="50"
                               cssClass="validate-notEmpty&&maxlength200"/></td>
        </tr>
        <tr>
            <td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
                     height="1" class="heightControl"></td>
        </tr>
        <tr>
            <td class="label">Description:</td>
            <td><form:textarea path="descriptionText" rows="5" cols="50"
                               cssClass="validate-maxlength2000"/></td>
        </tr>
        <tr>
            <td class="label"><img src="<tags:imageUrl name="spacer.gif"/>"
                                   width="1" height="1" class="heightControl"></td>
        </tr>
        <tr>
            <td class="label">Precis:</td>
            <td><form:textarea path="precisText" rows="2" cols="50"
                               cssClass="validate-maxlength200"/></td>
        </tr>
        <tr>
            <td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
                     height="1" class="heightControl"></td>
        </tr>
    </table>
</td>
<td width="100"></td>
<td class="contentAreaR">
<table border="0" width="100%" cellspacing="0" cellpadding="0"
       id="table1">
    <tr>
        <td width="150"></td>
        <td></td>
    </tr>
    <tr>
        <td align="right"><b>Target Accrual:</b>&nbsp;</td>
        <td><form:input path="targetAccrualNumber" size="10"
                        cssClass="validate-numeric"/></td>
    </tr>
    <tr>
        <td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
                 height="1" class="heightControl"></td>
    </tr>
    <tr>
        <td align="right"><span class="red">*</span><em></em><b>Type:</b>&nbsp;</td>
        <td><form:select path="type" cssClass="validate-notEmpty">
            <option value="">--Please Select--</option>
            <form:options items="${typeRefData}" itemLabel="desc"
                          itemValue="desc"/>
        </form:select></td>
    </tr>
    <tr>
        <td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
                 height="1" class="heightControl"></td>
    </tr>
    <tr>
        <td align="right"><span class="red">*</span><em></em><b>Status:</b>&nbsp;</td>
        <td><form:select path="status" cssClass="validate-notEmpty">
            <option value="">--Please Select--</option>
            <form:options items="${statusRefData}" itemLabel="desc"
                          itemValue="desc"/>
        </form:select></td>
    </tr>
    <tr>
        <td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
                 height="1" class="heightControl"></td>
    </tr>
    <tr>
        <td align="right"><span class="red">*</span><em></em><b>Phase:</b>&nbsp;</td>
        <td><form:select path="phaseCode" cssClass="validate-notEmpty">
            <option value="">--Please Select--</option>
            <form:options items="${phaseCodeRefData}" itemLabel="desc"
                          itemValue="desc"/>
        </form:select></td>
    </tr>
    <tr>
        <td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
                 height="1" class="heightControl"></td>
    </tr>
    <tr>
        <td align="right"><b>Randomized:</b>&nbsp;</td>
        <td><form:select path="randomizedIndicator">
            <option value="">--Please Select--</option>
            <form:options items="${randomizedIndicatorRefData}"
                          itemLabel="desc" itemValue="code"/>
        </form:select></td>
    </tr>
    <tr>
        <td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
                 height="1" class="heightControl"></td>
    </tr>
    <tr>
        <td align="right"><b>Blinded:</b>&nbsp;</td>
        <td><form:select path="blindedIndicator">
            <option value="">--Please Select--</option>
            <form:options items="${blindedIndicatorRefData}" itemLabel="desc"
                          itemValue="code"/>
        </form:select></td>
    </tr>
    <tr>
        <td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
                 height="1" class="heightControl"></td>
    </tr>
    <tr>
        <td align="right"><span class="red">*</span><b>Sponsor:</b>&nbsp;</td>
        <td><form:select path="identifiers[0].source" cssClass="validate-notEmpty">
            <option value="">--Please Select--</option>
            <form:options items="${sponsorCodeRefData}" itemLabel="desc"
                          itemValue="desc"/>
        </form:select></td>
    </tr>
    <tr>
        <td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
                 height="1" class="heightControl"></td>
    </tr>
    <tr>
        <td width="150" align="right"><span class="red">*</span><em></em><b>Sponsor
            Study Identifier:&nbsp;</td>
        <td><form:input path="identifiers[0].value" size="30"
                        maxlength="30" cssClass="validate-notEmpty"/></td>
        <input type="hidden" name="identifiers[0].type"
               value="Protocol Authority Identifier"/>
    </tr>
    <tr>
        <td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
                 height="1" class="heightControl"></td>
    </tr>
</table>
</td>
</tr>
</table>
</chrome:division>


<chrome:division title="Multi-Institutional Details">
    <table width="60%" border="0">

        <tr>
            <td width="100"></td>
            <td></td>
        </tr>
        <tr>
            <td class="label"><span class="red">*</span>Multi-Institution:</td>
            <td><form:select path="multiInstitutionIndicator"
                             onchange="manageSelectBox(this);" cssClass="validate-notEmpty">
                <option value="">--Please Select--</option>
                <form:options items="${multiInstitutionIndicatorRefData}"
                              itemLabel="desc" itemValue="code"/>
            </form:select></td>
        </tr>
    </table>
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
</chrome:division>
<tags:tabControls tab="${tab}" flow="${flow}"/>
</chrome:box>
</form:form>
</body>
</html>
