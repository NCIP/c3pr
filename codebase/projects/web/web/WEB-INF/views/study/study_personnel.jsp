<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>


<html>
<c:set var="selected_site" value="0"/>
<c:if test="${not empty selectedSite}">
	<c:set var="selected_site" value="${selectedSite}"/>
</c:if>
<head>
<style type="text/css">
    .label {
        width: 12em;
        text-align: right;
        padding: 4px;
    }
</style>
<tags:includeScriptaculous/>
<tags:dwrJavascriptLink objects="StudyAjaxFacade"/>

<title>${tab.longTitle}</title>
<style type="text/css">
    .label {
        width: 12em;
        text-align: right;
        padding: 4px;
    }
</style>
<script language="JavaScript" type="text/JavaScript">


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
            StudyAjaxFacade.matchResearchStaffs(text, document.getElementById('site').value, function(values) {
                autocompleter.setChoices(values)
            })
        },
        valueSelector: function(obj) {
            return obj.fullName
        },
          afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=inputElement.id.split("-")[0]+"-hidden";
	    							$(hiddenField).value=selectedChoice.id;
								}
    }
    
  var instanceRowInserterProps = {
       add_row_division_id: "studyPersonnelTable", 	        /* this id belongs to element where the row would be appended to */
       skeleton_row_division_id: "dummy-row",
       initialIndex: ${fn:length(command.studyOrganizations[selected_site].studyPersonnel)},   /* this is the initial count of the rows when the page is loaded  */
       softDelete: ${softDelete == 'true'},
	   path: "studyOrganizations[${selected_site}].studyPersonnel",                            /* this is the path of the collection that holds the rows  */
       postProcessRowInsertion: function(object){
        clonedRowInserter=Object.clone(personnelAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
		AutocompleterManager.registerAutoCompleter(clonedRowInserter);
    },
    onLoadRowInitialize: function(object, currentRowIndex){
		clonedRowInserter=Object.clone(personnelAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
		AutocompleterManager.registerAutoCompleter(clonedRowInserter);
    }
};
RowManager.addRowInseter(instanceRowInserterProps); 



</script>
</head>
<body>

<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" formName="form">
<jsp:attribute name="singleFields">
<c:choose>
	<c:when test="${fn:length(command.studyOrganizations) == 0}">
        <tr>
			<td>Choose a study site before adding study personnel</td>
		</tr>
    </c:when>
    <c:otherwise>
        <c:set var="selected_site" value="0"/>
        <c:if test="${not empty selectedSite}">
            <c:set var="selected_site" value="${selectedSite}"/>
        </c:if>
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
    Choose an organization
</p>

<table border="0" id="table1" cellspacing="0">
    <tr>
        <td align="left"> <span class="required-indicator"><b>Organization:</b></span></td>
        <td align="left">
            <select id="site" name="site" onchange="javascript:chooseSites();">
                <c:forEach items="${command.studyOrganizations}" var="studySite" varStatus="status">
                    <csmauthz:accesscontrol domainObject="${studySite.healthcareSite}"
                                                  hasPrivileges="ACCESS"  authorizationCheckName="siteAuthorizationCheck">
                    <c:if test="${selected_site == status.index }">
                        <option selected="true" value=${status.index}>${studySite.healthcareSite.name}</option>
                    </c:if>
                    <c:if test="${selected_site != status.index }">
                        <option value=${status.index}>${studySite.healthcareSite.name} <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter' }"> (Study Coordinating Center) </c:if>
                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudyFundingSponsor' }"> (Study Funding Sponsor) </c:if></option>
                    </c:if>
                        </csmauthz:accesscontrol>
                </c:forEach>
            </select>
        </td>
    </tr>
</table>
<input type="hidden" id="selectedStudySite" value="${selectedStudySite }"/>
<br>
<hr>

<table border="0" id="studyPersonnelTable" cellspacing="0" class="tablecontent">
    <tr>
        <th><b> <span class="required-indicator">Name</span></th>
        <th><b> <span class="required-indicator">Role</span></th>
        <th><b> <span class="required-indicator">Status</span></th>
        <th></th>
    </tr>

    <c:forEach varStatus="status" items="${command.studyOrganizations[selected_site].studyPersonnel}" var="sPersonnel">
        <tr id="studyPersonnelTable-${status.index}">
            <td>
                <form:hidden id="personnel${status.index}-hidden"
                             path="studyOrganizations[${selected_site}].studyPersonnel[${status.index}].researchStaff"/>
                <input type="text" class="autocomplete validate-notEmpty" id="personnel${status.index}-input" size="30"
                       value="${command.studyOrganizations[selected_site].studyPersonnel[status.index].researchStaff.fullName}"
                       class="autocomplete"/>
                <input type="button" id="personnel${status.index}-clear" value="Clear"/>
                <tags:indicator id="personnel${status.index}-indicator"/>
                <div id="personnel${status.index}-choices" class="autocomplete"></div>
            </td>
            <td>
                <form:select path="studyOrganizations[${selected_site}].studyPersonnel[${status.index}].roleCode"
                             cssClass="validate-notEmpty">
                    <option value="">--Please Select--</option>
                    <form:options items="${studyPersonnelRoleRefData}" itemLabel="desc" itemValue="desc"/>
                </form:select></td>
            <td>
                <form:select path="studyOrganizations[${selected_site}].studyPersonnel[${status.index}].statusCode"
                             cssClass="validate-notEmpty">
                    <option value="">--Please Select--</option>
                    <form:options items="${studyPersonnelStatusRefData}" itemLabel="desc" itemValue="desc"/>
                </form:select></td>
           <td>
                    <a href="javascript:RowManager.deleteRow(instanceRowInserterProps,${status.index},${sPersonnel.hashCode});"><img
                            src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="delete"></a></td>
        </tr>
    </c:forEach>
</table>
</td>

<td valign="top" width="25%">
    <chrome:box id="Summary" title="Personnel Summary">
        <font size="2"><b> Study Organizations </b> </font>
        <br><br>
        <table border="0" id="table1" cellspacing="0" cellpadding="0" width="100%">
            <c:forEach var="studySite" varStatus="status" items="${command.studyOrganizations}">
                <tr>
                    <td>
                        <a onclick="javascript:chooseSitesfromSummary(${status.index});"
                           title="click here to edit personnel assigned to study"> <font size="2">
                            <b> ${studySite.healthcareSite.name} <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter' }"> (Study Coordinating Center) </c:if>
                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudyFundingSponsor' }"> (Study Funding Sponsor) </c:if> </b> </font> </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        Personnel Assigned: <b> ${fn:length(studySite.studyPersonnel)} </b>
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
        </c:otherwise>
 </c:choose>
</jsp:attribute>

<jsp:attribute name="localButtons">
        <input type="button" onclick="RowManager.addRow(instanceRowInserterProps);" value="Add Research Staff Person"/>
    </jsp:attribute>

</tags:tabForm>

<div id="dummy-row" style="display:none;">
    <table>
        <tr id="studyPersonnelTable-PAGE.ROW.INDEX">
            <td>
                <input type="hidden" id="personnelPAGE.ROW.INDEX-hidden"
                       name="studyOrganizations[${selected_site}].studyPersonnel[PAGE.ROW.INDEX].researchStaff"
                       value="studyOrganizations[${selected_site}].studyPersonnel[PAGE.ROW.INDEX].researchStaff"/>
                <input type="text" class="autocomplete validate-notEmpty" id="personnelPAGE.ROW.INDEX-input" size="30"
                       value="${command.studyOrganizations[selected_site].studyPersonnel[PAGE.ROW.INDEX].researchStaff.fullName}"/>
                <input type="button" id="personnelPAGE.ROW.INDEX-clear" value="Clear"/>
                <tags:indicator id="personnelPAGE.ROW.INDEX-indicator"/>
                <div id="personnelPAGE.ROW.INDEX-choices" class="autocomplete"></div>
            </td>
            <td>
                <select id="studyOrganizations[${selected_site}].studyPersonnel[PAGE.ROW.INDEX].roleCode"
                        name="studyOrganizations[${selected_site}].studyPersonnel[PAGE.ROW.INDEX].roleCode"
                        class="validate-notEmpty">
                    <option value="">--Please Select--</option>
                    <c:forEach items="${studyPersonnelRoleRefData}" var="role">
                        <option value="${role.desc}">${role.desc}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <select id="studyOrganizations[${selected_site}].studyPersonnel[PAGE.ROW.INDEX].statusCode"
                        name="studyOrganizations[${selected_site}].studyPersonnel[PAGE.ROW.INDEX].statusCode"
                        class="validate-notEmpty">
                    <option value="">--Please Select--</option>
                    <c:forEach items="${studyPersonnelStatusRefData}" var="status">
                        <option value="${status.desc}">${status.desc}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <a href="javascript:RowManager.deleteRow(instanceRowInserterProps,PAGE.ROW.INDEX, -1);"><img
                        src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="delete"></a></td>
        </tr>
        
    </table>
</div>
</body>
</html>