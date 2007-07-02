<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>

<html>
<head>
<style type="text/css">
    .label {
        width: 12em;
        text-align: right;
        padding: 4px;
    }
</style>
<tags:includeScriptaculous/>
<tags:dwrJavascriptLink objects="createStudy"/>

<title>${tab.longTitle}</title>
<style type="text/css">
    .label {
        width: 12em;
        text-align: right;
        padding: 4px;
    }
</style>
<script language="JavaScript" type="text/JavaScript">

function fireAction(action, selectedSite, selectedPersonnel) {
    if(selectedSite >-1){
        document.getElementById('command')._target.name = '_noname';
        document.form._action.value = action;
        document.form._selectedSite.value = selectedSite;
        document.form._selectedPersonnel.value = selectedPersonnel;


        //  need to disable validations while submitting
        if(action == 'removeStudyPersonnel'){
            input = 'personnel' + selectedPersonnel + '-input';
            $(input).className = 'none';
            role = 'studySites['+selectedSite+'].studyPersonnels['+selectedPersonnel+'].roleCode';
            $(role).className='none';
            status = 'studySites['+selectedSite+'].studyPersonnels['+selectedPersonnel+'].statusCode';
            $(status).className='none';
        }

        document.form.submit();
        fireListeners(selected);
    }
}

function chooseSites() {
    document.getElementById('command')._target.name = '_noname';
    document.form._action.value = "siteChange";
    document.form._selectedSite.value = document.getElementById('site').value;
    document.form.submit();
}

function chooseSitesfromSummary(_selectedSite) {
    document.getElementById('command')._target.name = '_noname';
    document.form._action.value = "siteChange";
    document.form._selectedSite.value = _selectedSite;
    document.form.submit();
}

/// AJAX

var personnelAutocompleterProps = {
    basename: "personnel",
    populator: function(autocompleter, text) {
        createStudy.matchResearchStaffs(text, document.getElementById('site').value, function(values) {
            autocompleter.setChoices(values)
        })
    },
    valueSelector: function(obj) {
        return obj.fullName
    }
}


function acPostSelect(mode, selectedChoice) {
    $(mode.basename).value = selectedChoice.id;
}

function updateSelectedDisplay(mode) {

    if ($(mode.basename).value) {
        Element.update(mode.basename + "-selected-name", $(mode.basename + "-input").value)
        $(mode.basename + '-selected').show()
    }
}

function acCreate(mode) {
    new Autocompleter.DWR(mode.basename + "-input", mode.basename + "-choices",
            mode.populator, {
        valueSelector: mode.valueSelector,
        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
            acPostSelect(mode, selectedChoice)
        },
        indicator: mode.basename + "-indicator"
    })
    Event.observe(mode.basename + "-clear", "click", function() {
        //$(mode.basename + "-selected").hide()
        $(mode.basename).value = ""
        $(mode.basename + "-input").value = ""
    })
}

function fireListeners(count)
{
    index = 0;
    autoCompleteId = 'personnel' + index;
    for (i = 0; i < count; i++)
    {
        // change the basename property to agent0 ,agent1 ...
        personnelAutocompleterProps.basename = autoCompleteId
        acCreate(personnelAutocompleterProps)
        index++
        autoCompleteId = 'personnel' + index;
    }

    personnelAutocompleterProps.basename = 'personnel' + count;
    acCreate(personnelAutocompleterProps);
}

Event.observe(window, "load", function() {
    index = 0;
    autoCompleteId = 'personnel' + index;
    while ($(autoCompleteId))
    {
        // change the basename property to personnel0 ,personnel1 ...
        personnelAutocompleterProps.basename = autoCompleteId
        acCreate(personnelAutocompleterProps)
        index++
        autoCompleteId = 'personnel' + index;
    }
    //Element.update("flow-next", "Continue &raquo;")
})

</script>
</head>
<body>

<tags:tabForm tab="${tab}" flow="${flow}" formName="form">
<jsp:attribute name="singleFields">
<div>
    <input type="hidden" name="_action" value="">
    <input type="hidden" name="_selected" value="">
    <input type="hidden" name="_selectedSite" value="">
    <input type="hidden" name="_selectedPersonnel" value="">
</div>

<table border="0" id="table1" cellspacing="10" width="100%">
<tr>
<td valign="top">
    <p id="instructions">
        Choose a study site first
    </p>

    <c:choose>
        <c:when test="${fn:length(command.studySites) > 0}">
            <c:set var="selected_site" value="0"/>
            <c:if test="${not empty selectedSite}">
                <c:set var="selected_site" value="${selectedSite}"/>
            </c:if>
        </c:when>
        <c:otherwise>
            <c:set var="selected_site" value="-1"/>
        </c:otherwise>
    </c:choose>

    <table border="0" id="table1" cellspacing="0">
        <tr>
            <td align="left"><b> <span class="red">*</span><em></em>Site:</b></td>
            <td align="left">
                <select id="site" name="site" onchange="javascript:chooseSites();">
                    <c:forEach items="${command.studySites}" var="studySite" varStatus="status">
                        <c:if test="${selected_site == status.index }">
                            <option selected="true" value=${status.index}>${studySite.site.name}</option>
                        </c:if>
                        <c:if test="${selected_site != status.index }">
                            <option value=${status.index}>${studySite.site.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>
    <br>
    <hr>
    <p id="instructions">
        Add Research Staff <a href="javascript:fireAction('addStudyPersonnel',${selected_site}, '0');"><img
            src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add Research Staff"></a></a>
    </p>
    <table border="0" id="mytable" cellspacing="0">
        <tr>
            <th scope="col" align="left"><b> <span class="red">*</span><em></em>Name:</b></th>
            <th scope="col" align="left"><b> <span class="red">*</span><em></em>Role:</b></th>
            <th scope="col" align="left"><b> <span class="red">*</span><em></em>Status:</b></th>
            <th scope="col" align="left" class="specalt"></th>
        </tr>

        <c:forEach varStatus="status" items="${command.studySites[selected_site].studyPersonnels}">
            <tr>
                <td class="alt">
                    <form:hidden id="personnel${status.index}"
                                 path="studySites[${selected_site}].studyPersonnels[${status.index}].researchStaff"/>
                    <input type="text" class="validate-notEmpty" id="personnel${status.index}-input" size="30"
                           value="${command.studySites[selected_site].studyPersonnels[status.index].researchStaff.fullName}"/>
                    <input type="button" id="personnel${status.index}-clear" value="Clear"/>
                    <tags:indicator id="personnel${status.index}-indicator"/>
                    <div id="personnel${status.index}-choices" class="autocomplete"></div>
                </td>
                <td class="alt">
                    <form:select path="studySites[${selected_site}].studyPersonnels[${status.index}].roleCode"
                                 cssClass="validate-notEmpty">
                        <option value="">--Please Select--</option>
                        <form:options items="${studyPersonnelRoleRefData}" itemLabel="desc" itemValue="desc"/>
                    </form:select></td>
                <td class="alt">
                    <form:select path="studySites[${selected_site}].studyPersonnels[${status.index}].statusCode"
                                 cssClass="validate-notEmpty">
                        <option value="">--Please Select--</option>
                        <form:options items="${studyPersonnelStatusRefData}" itemLabel="desc" itemValue="desc"/>
                    </form:select></td>
                <td class="alt">
                    <a href="javascript:fireAction('removeStudyPersonnel',${selected_site}, ${status.index});"><img
                            src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="delete"></a></td>
            </tr>
        </c:forEach>
    </table>
</td>

<td valign="top" width="25%">
    <chrome:box id="Summary" title="Personnel Summary">
        <font size="2"><b> Study Sites </b> </font>
        <br><br>
        <table border="0" id="table1" cellspacing="0" cellpadding="0" width="100%">
            <c:forEach var="studySite" varStatus="status" items="${command.studySites}">
                <tr>
                    <td>
                        <a onclick="javascript:chooseSitesfromSummary(${status.index});"
                           title="click here to edit personnel assigned to study"> <font size="2">
                            <b> ${studySite.site.name} </b> </font> </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        Personnel Assigned: <b> ${fn:length(studySite.studyPersonnels)} </b>
                    </td>
                </tr>
                <tr>
                    <td>
                        <br>
                    </td>
                </tr>
            </c:forEach>
            <c:forEach begin="1" end="11">
                <tr>
                    <td>
                        <br>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </chrome:box>
</td>
</tr>
</table>
</jsp:attribute>
</tags:tabForm>
</body>
</html>