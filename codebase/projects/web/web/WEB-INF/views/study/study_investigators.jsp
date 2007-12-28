<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>

<html>
<c:set var="selected_site" value="0"/>
<c:if test="${not empty selectedSite}">
	<c:set var="selected_site" value="${selectedSite}"/>
</c:if>
<head>
<tags:includeScriptaculous/>
<tags:dwrJavascriptLink objects="StudyAjaxFacade"/>

<title>${tab.longTitle}</title>

<script type="text/javascript">
function validatePage() {
    return true;
}

function fireAction(action, selected) {
    if (validatePage()) {
        addDiseasesToCart()
        document.getElementById('command')._target.name = '_noname';
        $('_actionx').value = action;
        $('_selectedSite').value = document.getElementById('site').selectedIndex;
        $('_selected').value = selected;
        document.myform.submit();
    }
}

function clearField(field) {
    field.value = "";
}

function hover(index)
{
}

function acPostSelect(mode, selectedChoice) {
    updateCategories(selectedChoice.id);
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
        $(mode.basename + "-selected").hide()
        $(mode.basename).value = ""
        $(mode.basename + "-input").value = ""
    })
}


function updateCategories(id) {
	if(id  == null || id  == ''){
		id = document.getElementById('site').value;
	}
    StudyAjaxFacade.matchGroupsByOrganizationId(id, function(categories) {
        var sel = $("disease-sub-category")
        sel.size = categories.length < 10 ? categories.length + 2 : 10;
        //sel.size= 10
        sel.options.length = 0
        sel.options.add(new Option("All", ""))
        sel.options[0].selected = true;
        categories.each(function(cat) {
            var opt = new Option(cat.name, cat.id)
            sel.options.add(opt)
        })
        showDiseases()
    })
}

function showDiseases() {
    var categoryId = $("disease-sub-category").value    
    var subCategorySelect = $("disease-sub-category")
    var siteSelect = $("site").value;
    
    // If all is selected
    if (subCategorySelect.value == "") {
        var diseaseTermSelect = $("disease-term")
        diseaseTermSelect.options.length = 0
        diseaseTermSelect.size = 10
        diseaseTermSelect.options.add(new Option("All", ""))
        diseaseTermSelect.options[0].selected = true;
        //call the getAll method for the selected organization instead of calling getInvestigatorsById() for every group.
        //for (i = 0; i < siteSelect.length; i++) {
            var catId = siteSelect
	        StudyAjaxFacade.getAllInvestigators(catId, function(diseases) {
	              diseases.each(function(cat) {
	               	var name = cat.investigator.firstName + " " +  cat.investigator.lastName;
	                var opt = new Option(name, cat.id)
	                diseaseTermSelect.options.add(opt)
	            })
	        })
	    //}
    }
    else {
        StudyAjaxFacade.matchInvestigatorsByGroupId(categoryId, function(diseases) {
            var sel = $("disease-term")
            sel.size = diseases.length + 2;
            sel.options.length = 0
            sel.options.add(new Option("All", ""))
            sel.options[0].selected = true;
            diseases.each(function(cat) {
            	var name = cat.investigator.firstName + " " +  cat.investigator.lastName;
                var opt = new Option(name, cat.id)
                sel.options.add(opt)
            })
        })
    }
}

/**
 * Copy Diseases from  [Diseases MultiSelect]
 *   to the [Selected Diseases MultiSelect]
 *
 */
function addDiseasesToCart() {
    var diseaseTerm = $("disease-term");
    var diseaseSelected = $("disease-sel");
    var diseaseSelectedHidden = $("disease-sel-hidden");
    if (diseaseSelected.options[0].value == "") {
        diseaseSelected.options.length = 0
    }
    // If all is selected  in the [Diseases MultiSelect]
    if (diseaseTerm.options[0].selected) {
        for (i = 1; i < diseaseTerm.length; i++)
        {
            var opt = new Option(diseaseTerm.options[i].text, diseaseTerm.options[i].value)
            var opt1 = new Option(diseaseTerm.options[i].text, diseaseTerm.options[i].value)
            diseaseSelected.options.add(opt)
            diseaseSelectedHidden.options.add(opt1)
        }
    }
    // If anything other than all is selected
    else {
        for (i = 1; i < diseaseTerm.length; i++)
        {
            if (diseaseTerm.options[i].selected) {
                var opt = new Option(diseaseTerm.options[i].text, diseaseTerm.options[i].value)
                diseaseSelected.options.add(opt)
            }
        }
    }
    // Copy over [Selected Diseases MultiSelect] to [Hidden Selected Diseases MultiSelect]
    //selectAll(diseaseSelectedHidden)
    synchronizeSelects(diseaseSelected, diseaseSelectedHidden);
}

function synchronizeSelects(selectFrom, selectTo)
{
    // Delete everything from the target
    selectTo.options.length = 0;
    // iterate over the source and add to target
    for (i = 0; i < selectFrom.length; i++) {
        var opt = new Option(selectFrom.options[i].text, selectFrom.options[i].value)
        selectTo.options.add(opt)
        selectTo.options[i].selected = true;
    }
}

function removeDiseasesFromCart()
{
    var diseaseSelected = $("disease-sel");
    var diseaseSelectedHidden = $("disease-sel-hidden");

    for (i = 0; i < diseaseSelected.length; i++)
    {
        if (diseaseSelected.options[i].selected) {
            diseaseSelected.options[i] = null
        }
    }
    synchronizeSelects(diseaseSelected, diseaseSelectedHidden)
}

function populateSelectsOnLoad()
{
    if ($('site').value.length > 0)
    {
        updateCategories($('site').value);
    }
}

Event.observe(window, "load", function() {
    $('disease-sel').style.display = 'none';
    $('disease-sel-hidden').style.display = 'none';

    //acCreate(diseaseAutocompleterProps)
    //updateSelectedDisplay(diseaseAutocompleterProps)

    Event.observe("disease-sub-category", "change", function() {
        showDiseases()
    })
    populateSelectsOnLoad();
})

</script>
</head>

<body>
<!-- selected_site is a c:set var which is request attrubute. 
it has the value that is set in the _selectedSite by the controller.
and the controller gets the selected index via the hidden variable _selectedSite.
(Look at the fireAction() method in the js on top.) -->
<c:set var="selected_site" value="0"/>
<c:if test="${not empty _selectedSite}">
	<c:set var="selected_site" value="${_selectedSite}"/>
</c:if>

<form:form method="post" name="myform" cssClass="standard">
<tags:tabFields tab="${tab}"/>

<input type="hidden" id="_actionx" name="_actionx" value="">
<input type="hidden" id="_selected" name="_selected" value="">
<input type="hidden" id="_selectedSite" name="_selectedSite" value="">

<c:choose>
	<c:when test="${fn:length(command.studyOrganizations) == 0}">
        <tr>
			<td>Choose a study organization before adding investigators</td>
		</tr>
    </c:when>
    <c:otherwise>

		<table border="0" id="table1" cellspacing="10" width="100%">
			<tr>
			<td valign="top" width="45%">
				<chrome:box title="${tab.shortTitle}">
					<div>
			            <br/>&nbsp;<b>Select an Organization</b><br>
			            <input:hidden id="disease"/>
			            <select id="site" name="site" onchange="fireAction('siteChange','0');">   
			                    <c:forEach items="${command.studyOrganizations}" var="studySite" varStatus="status">
			                        <csmauthz:accesscontrol domainObject="${studySite.healthcareSite}"
			                                                hasPrivileges="ACCESS"  authorizationCheckName="siteAuthorizationCheck">
			                        <c:if test="${selected_site == status.index }">
			                            <option selected="selected" value=${studySite.healthcareSite.id}>${studySite.healthcareSite.name}
			                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter' }"> (Coordinating Center) </c:if>
			                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudyFundingSponsor' }"> (Funding Sponsor) </c:if> 
			                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudySite' }"> (Site) </c:if>			                            
			                            </option>			                            
			                        </c:if>
			                        <c:if test="${selected_site != status.index }">
			                            <option value=${studySite.healthcareSite.id}>${studySite.healthcareSite.name} 
			                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter' }"> (Coordinating Center) </c:if>
			                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudyFundingSponsor' }"> (Funding Sponsor) </c:if> 
			                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudySite' }"> (Site) </c:if>
			                            </option>
			                        </c:if>
			                        </csmauthz:accesscontrol>
			                    </c:forEach>
			                </select>
			            <tags:indicator id="disease-indicator"/>
			            
			            <p id="disease-selected" style="display: none"></p>			
			            <br><br><b>&nbsp;Select a Group</b><br>
			            <select multiple size="1" onmouseover="javascript:hover()" style="width:400px" id="disease-sub-category">
			                <option value="">Please select a Group first</option>
			            </select>
			
			            <br><br><b>&nbsp;Investigators</b><br>
			            <select multiple size="1" style="width:400px" id="disease-term">
			                <option value="">Please select a Group first</option>
			            </select> <span id="disease-selected-name"></span>
			            <select multiple size="10" id="disease-sel">
			                <option value="">No Selected Investigators</option>
			            </select>
			            
			            <form:select id="disease-sel-hidden" size="1" path="studyOrganizations[${selected_site}].studyInvestigatorIds" />
			        <br/>
			        <br/>
	               </div>	        
			    </chrome:box>
			</td>
			<td valign="middle">
				<input type="button" value="Add Investigators >>" onclick="fireAction('addStudyDisease','0');" alt="Add Investigators"/>
	        </td>
			<td valign="top" width="45%">
			    <chrome:box title="${command.studyOrganizations[selected_site].healthcareSite.name}" id="diseases">
			        <br/><br/>
			        <c:choose>
			            <c:when test="${fn:length(command.studyOrganizations[selected_site].studyInvestigators) == 0}">
			                No Investigators Selected
			            </c:when>			
			            <c:otherwise>
			                <table border="1" class="tablecontent" >
			                    <tr>
			                        <th scope="col">Name</th>			                        
			                        <th width="20%">Status</th>
			                        <th width="5%"></th>
			                    </tr>
			                    <c:forEach items="${command.studyOrganizations[selected_site].studyInvestigators}" var="studyInvestigator"
			                               varStatus="status">
		                        <tr>
		                            <td>
		                              ${studyInvestigator.healthcareSiteInvestigator.investigator.firstName}&nbsp;${studyInvestigator.healthcareSiteInvestigator.investigator.lastName}
		                              <c:if test="${studyInvestigator.roleCode eq 'Principal Investigator'}">
		                               	- Principal Investigator
		                              </c:if>
		                            </td>
		                            <td>
		                            <form:select path="studyOrganizations[${selected_site}].studyInvestigators[${status.index}].statusCode" cssClass="validate-notEmpty">
				                        <option value="">Please Select</option>
				                        <form:options items="${studyInvestigatorStatusRefData}" itemLabel="desc" itemValue="desc"/>
				                    </form:select>
		                            </td>
		                            <td class="alt">
			                          <c:if test="${studyInvestigator.roleCode ne 'Principal Investigator'}">  
			                            <a href="javascript:fireAction('removeStudyDisease',${status.index});">
			                                <img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="remove">
			                            </a>&nbsp;
			                          </c:if>
			                        </td>
		                        </tr>
			                    </c:forEach>
			                </table>
			            </c:otherwise>
			        </c:choose>
			    </chrome:box>  
			</td>
			</tr>			
		</table>

  </c:otherwise>
</c:choose><br/>
<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}"/>  
</form:form>

<div id="dummy-row" style="display:none;">
    <table>
        <tr  id="investigatorsTable-PAGE.ROW.INDEX">
            <td>
                <input type="hidden" id="investigatorPAGE.ROW.INDEX-hidden"
                        name="studyOrganizations[${selected_site}].studyInvestigators[PAGE.ROW.INDEX].healthcareSiteInvestigator"
                       value="studyOrganizations[${selected_site}].studyInvestigators[PAGE.ROW.INDEX].healthcareSiteInvestigator"/>
                <input class="autocomplete validate-notEmpty" type="text" id="investigatorPAGE.ROW.INDEX-input"
                       size="30"
                       value="${command.studyOrganizations[selected_site].studyInvestigators[PAGE.ROW.INDEX].healthcareSiteInvestigator.investigator.fullName}"/>
                <input type="button" id="investigatorPAGE.ROW.INDEX-clear"
                        value="Clear"/>
                   <tags:indicator id="investigatorPAGE.ROW.INDEX-indicator"/>
                  <div id="investigatorPAGE.ROW.INDEX-choices" class="autocomplete"></div>
                  <input type="hidden" id="studyOrganizations[${selected_site}].studyInvestigators[PAGE.ROW.INDEX].roleCode"
                  name="studyOrganizations[${selected_site}].studyInvestigators[PAGE.ROW.INDEX].roleCode"
                    value="Site Investigator"/>
            </td>
            <td>
                <select id="studyOrganizations[${selected_site}].studyInvestigators[PAGE.ROW.INDEX].statusCode"
                        name="studyOrganizations[${selected_site}].studyInvestigators[PAGE.ROW.INDEX].statusCode"
                        class="validate-notEmpty">
                    <option value="">Please Select</option>
                    <c:forEach items="${studyInvestigatorStatusRefData}" var="studyInvStatus">
                        <option value="${studyInvStatus.desc}">${studyInvStatus.desc}</option>
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
